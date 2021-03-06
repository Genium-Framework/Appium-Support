/*Copyright 2014 Genium Testing Framework
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.*/
package com.github.genium_framework.appium.support.server;

import com.github.genium_framework.server.IMobileServer;
import com.github.genium_framework.appium.support.command.CommandManager;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.OS;
import org.apache.commons.io.FileUtils;
import com.github.genium_framework.appium.support.server.arguments.AppiumCommonArgs;
import com.github.genium_framework.server.ServerArguments;
import com.github.genium_framework.server.exception.InvalidAppiumJSFilePathException;
import com.github.genium_framework.server.exception.InvalidNodeFilePathException;
import com.github.genium_framework.server.exception.InvalidServerFileException;
import com.github.genium_framework.server.exception.ServerDirectoryNotFoundException;
import com.github.genium_framework.server.exception.ServerTimeoutException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * An implementation to Appium server which is responsible for all server
 * operations including: Start, Stop &amp; Checking if the server is alive.
 *
 * @author Hassan Radi
 */
public class AppiumServer implements IMobileServer {

    private final File _absoluteServerDirectory;
    private final File _nodeExecutableFilePath;
    private final File _appiumJavaScriptFilePath;
    private final ServerArguments _serverArguments;
    private final static Logger LOGGER = Logger.getLogger(AppiumServer.class.getName());
    private static final String NODE_RELATIVE_PATH_WINDOWS = "/node.exe";
    private static final String NODE_RELATIVE_PATH_MAC_OS = "/node/bin/node";
    private static final String APPIUM_FILE_RELATIVE_PATH = "/node_modules/appium/lib/server/main.js";
    private static final String APPIUM_SERVER_MAC_DEFAULT_DIRECTORY = "/Applications/Appium.app/Contents/Resources";

    /**
     * Constructs an Appium server instance. Searches automatically for an
     * installed Appium server on your machine using the default installation
     * location according to your operating system.
     *
     * The searched directories are: <br><ul><li>Windows OS: "C:/Program
     * Files/Appium" &amp; "C:/Program Files (x86)/Appium"</li> <li>Mac OS:
     * "/Applications/Appium.app/Contents/Resources" </li></ul>zz
     *
     * @param serverArguments The server arguments to be used when working with
     * the server.
     */
    public AppiumServer(ServerArguments serverArguments) {
        this._serverArguments = serverArguments;

        // search for installed Appium server
        _absoluteServerDirectory = searchForServerDirectory();

        // make sure to get the node executable file path along with the appium.js path too.
        _nodeExecutableFilePath = new File(OS.isFamilyWindows()
                ? _absoluteServerDirectory + NODE_RELATIVE_PATH_WINDOWS
                : _absoluteServerDirectory + NODE_RELATIVE_PATH_MAC_OS);
        _appiumJavaScriptFilePath = new File(_absoluteServerDirectory
                + APPIUM_FILE_RELATIVE_PATH);
    }

    /**
     * Constructs an Appium server instance. You specify the custom directory to
     * your Appium server.
     *
     * @param absoluteServerDirectory The custom directory to your Appium
     * server. The directory that contains the "node_modules" directory &amp;
     * the NodeJS executable.
     * @param serverArguments The server arguments to be used when working with
     * the server.
     */
    public AppiumServer(File absoluteServerDirectory, ServerArguments serverArguments) {
        this._absoluteServerDirectory = absoluteServerDirectory;
        this._serverArguments = serverArguments;

        // make sure to get the node executable file path along with the appium.js path too.
        _nodeExecutableFilePath = new File(OS.isFamilyWindows()
                ? _absoluteServerDirectory + NODE_RELATIVE_PATH_WINDOWS
                : _absoluteServerDirectory + NODE_RELATIVE_PATH_MAC_OS);
        _appiumJavaScriptFilePath = new File(_absoluteServerDirectory
                + APPIUM_FILE_RELATIVE_PATH);
    }

    /**
     * Constructs an Appium server instance. This constructor can be used in
     * case the Appium server is installed via npm or in case the necessary
     * files to start the server are not in their default locations.
     *
     * @param nodeExecutableFilePath The absolute path to the node executable
     * file and not a shortcut to it.
     * @param appiumJavaScriptFilePath The absolute path to the appium.js file
     * and not a shortcut to it.
     * @param serverArguments The server arguments to be used when working with
     * the server.
     */
    public AppiumServer(File nodeExecutableFilePath, File appiumJavaScriptFilePath,
            ServerArguments serverArguments) {
        this._nodeExecutableFilePath = nodeExecutableFilePath;
        this._appiumJavaScriptFilePath = appiumJavaScriptFilePath;
        this._serverArguments = serverArguments;
        this._absoluteServerDirectory = new File("DUMMY_DATA");
    }

    /**
     * Search the operating system for an Appium server installation directory.
     *
     * @return A File representation to the Appium server installation
     * directory.
     */
    private File searchForServerDirectory() {
        if (OS.isFamilyWindows()) {
            if (getArch().equals("32")) {
                return doesDirectoryExists(System.getenv("ProgramFiles")
                        + "/Appium");
            } else {
                // must be the x86_64
                return doesDirectoryExists(System.getenv("ProgramFiles")
                        + " (x86)/Appium");
            }
        } else if (OS.isFamilyMac()) {
            return doesDirectoryExists(APPIUM_SERVER_MAC_DEFAULT_DIRECTORY);
        }

        // server directrory was not found.
        throw new ServerDirectoryNotFoundException();
    }

    /**
     * Checks if a certain directory exists or not on your HDD.
     *
     * @param directoryString A string representation of the directory you want
     * to check.
     * @return File representation of the directory.
     */
    private File doesDirectoryExists(String directoryString) {
        File directory = new File(directoryString);
        if (directory.exists()) {
            return directory;
        } else {
            throw new ServerDirectoryNotFoundException();
        }
    }

    /**
     * Checks whether an Appium server instance is running or not.
     *
     * @return True if an Appium server is already running, false otherwise.
     */
    @Override
    public boolean isServerRunning() {
        // public exposed function should always log info messages.
        return isServerRunning(false);
    }

    /**
     * Checks whether an Appium server instance is running or not by sending an
     * HTTP request to the server asking for status.
     *
     * @param silentMode Whether or not to log info messages. True will activate
     * silent mode and no messages would be logged.
     * @return True if an Appium server is already running, false otherwise.
     */
    private boolean isServerRunning(boolean silentMode) {
        HttpURLConnection openConnection;

        try {
            if (silentMode == false) {
                LOGGER.log(Level.INFO, "Checking to see if a server instance is running or not ...");
            }

            String serverIPAddress = _serverArguments.get(AppiumCommonArgs.IP_ADDRESS).toString();
            String serverPortNumber = _serverArguments.get(AppiumCommonArgs.PORT_NUMBER).toString();
            URL url = new URL("http://" + serverIPAddress + ":"
                    + serverPortNumber + "/wd/hub/status");
            openConnection = (HttpURLConnection) url.openConnection();
            openConnection.connect();

            /**
             * If the server is up an running it will return a response massage
             * that says "OK"
             */
            return openConnection.getResponseMessage().equalsIgnoreCase("ok");
        } catch (java.net.ConnectException ex) {
            return false;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "An exception was thrown.", ex);
        }
        return false;
    }

    /**
     * Stops an already running Appium server.
     */
    @Override
    public void stopServer() {
        String[] stopServerCommand = null;

        try {
            if (OS.isFamilyWindows()) {
                stopServerCommand = new String[]{"cmd", "/c",
                    "echo off & FOR /F \"usebackq tokens=5\" %a in (`netstat -nao ^| findstr /R /C:\""
                    + _serverArguments.get(AppiumCommonArgs.PORT_NUMBER)
                    + " \"`) do (FOR /F \"usebackq\" %b in (`TASKLIST /FI \"PID "
                    + "eq %a\" ^| findstr /I node.exe`) do taskkill /F /PID %a)"};
            } else if (OS.isFamilyMac()) {
                // Using command substitution
                stopServerCommand = new String[]{"/bin/sh", "-c",
                    "PID=\"$(lsof -i -P | pgrep -f node)\";kill -9 $PID"};
            } else if (OS.isFamilyUnix()) {
                // Using command substitution
                stopServerCommand = new String[]{"/bin/env",
                    "PID=\"$(lsof -i -P | pgrep -f node)\";kill -9 $PID"};
            } else {
                throw new UnsupportedOperationException("Not supported yet for this operating system...");
            }

            LOGGER.log(Level.INFO, "Stopping the server with the command: {0}",
                    CommandManager.convertCommandStringArrayToString(stopServerCommand));
            String stopServerOutput = CommandManager.executeCommandUsingJavaRuntime(stopServerCommand, true);
            LOGGER.log(Level.INFO, "Server stopping output: {0}", stopServerOutput);
        } catch (UnsupportedOperationException ex) {
            throw ex;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "An exception was thrown.", ex);
        }
    }

    /**
     * Start an Appium server instance. The server is started asynchronously, so
     * as not to block the execution. By default the function checks to see if
     * the server has actually started or not and times out after 30 seconds.
     */
    public void startServer() {
        // pass the default timeout value of 30 seconds
        this.startServer(30000);
    }

    /**
     * Start an Appium server instance. The server is started asynchronously, so
     * as not to block the execution. By default the function checks to see if
     * the server has actually started or not.
     *
     * @param timeoutInMilliseonds The amount of milliseconds to wait before
     * declaring that the server failed to start and throw a
     * ServerTimeoutException.
     */
    public void startServer(long timeoutInMilliseonds) {
        try {
            // make sure that the above files exist
            if (_nodeExecutableFilePath.exists() == false) {
                throw new InvalidNodeFilePathException(_nodeExecutableFilePath.getAbsolutePath());
            }
            if (_appiumJavaScriptFilePath.exists() == false) {
                throw new InvalidAppiumJSFilePathException(_appiumJavaScriptFilePath.getAbsolutePath());
            }

            // create the command line to be executed
            LOGGER.log(Level.INFO, "Server is starting...");
            CommandLine cmdLine = CommandManager.createCommandLine(
                    _nodeExecutableFilePath.getAbsolutePath(),
                    new String[]{_appiumJavaScriptFilePath.getAbsolutePath()},
                    _serverArguments.toStringArray());
            final File processOutputError = Files.createTempFile("AppiumServerStreamHandler",
                    ".txt").toFile();
            CommandManager.executeCommandUsingApacheExec(cmdLine,
                    new FileOutputStream(processOutputError));

            /**
             * Register a shutdown hook to delete the process output error
             * stream file.
             */
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    processOutputError.deleteOnExit();
                }
            });

            /**
             * Make sure that the server has started. Check for the server
             * running in silent mode to prevent showing a lot of info messages
             * to the user.
             */
            long startTime = System.currentTimeMillis();
            long endTime = startTime + timeoutInMilliseonds;
            while (isServerRunning(true) == false) {
                if (System.currentTimeMillis() > endTime) {
                    throw new ServerTimeoutException(FileUtils.readFileToString(
                            processOutputError, "UTF8"), timeoutInMilliseonds);
                }
                LOGGER.log(Level.INFO, "Server has not started yet. Trying again in one second...");
                Thread.sleep(1000);
            }

            LOGGER.log(Level.INFO, "Server has been started successfully.");
        } catch (ServerTimeoutException ex) {
            throw ex;
        } catch (InvalidServerFileException ex) {
            throw ex;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "An exception was thrown.", ex);
        }
    }

    /**
     * Get the real architecture of the operating system (Not the architecture
     * of the underlying Java VM).
     *
     * @return A string representation of the operating system architecture.
     */
    private String getArch() {
        String arch = System.getenv("PROCESSOR_ARCHITECTURE");
        String wow64Arch = System.getenv("PROCESSOR_ARCHITEW6432");

        return arch.endsWith("64")
                || wow64Arch != null && wow64Arch.endsWith("64")
                ? "64" : "32";
    }
}

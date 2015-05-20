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
import com.github.genium_framework.server.exception.InvalidServerDirectoryException;
import com.github.genium_framework.server.exception.ServerDirectoryNotFoundException;
import com.github.genium_framework.server.exception.ServerTimeoutException;

/**
 * An implementation to Appium server which is responsible for all server
 * operations including: Start, Stop &amp; Checking if the server is alive.
 *
 * @author Hassan Radi
 */
public class AppiumServer implements IMobileServer {

    private final File _absoluteServerDirectory;
    private final ServerArguments _serverArguments;
    private final static Logger LOGGER = Logger.getLogger(AppiumServer.class.getName());

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
            return doesDirectoryExists("/Applications/Appium.app/Contents/Resources");
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
    }

    /**
     * Checks whether an Appium server instance is running on the specified
     * port.
     *
     * @return True if an Appium server is already running on the specified port
     * number, false otherwise.
     */
    @Override
    public boolean isServerRunning() {
        // public exposed function should always log info messages.
        return isServerRunning(false);
    }

    /**
     * Checks whether an Appium server instance is running on the specified
     * port.
     *
     * @param silentMode Whether or not to log info messages. True will activate
     * silent mode and no messages would be logged.
     * @return True if an Appium server is already running on the specified port
     * number, false otherwise.
     */
    private boolean isServerRunning(boolean silentMode) {
        String checkServerAliveCommand = null;
        String isServerAliveOutput = null;

        try {
            if (OS.isFamilyWindows()) {
                checkServerAliveCommand = "cmd /c echo off & FOR /F \"usebackq tokens=5\" %a in (`netstat -nao ^| findstr /R /C:\""
                        + _serverArguments.get(AppiumCommonArgs.PORT_NUMBER)
                        + " \"`) do (FOR /F \"usebackq\" %b in (`TASKLIST /FI \"PID eq %a\" ^| findstr /I node.exe`) do @echo %a)";
            } else if (OS.isFamilyMac()) {
                // Using command substitution
                checkServerAliveCommand = "/bin/sh PID=\"$(lsof -i -P | pgrep -f node)\";echo $PID";
            } else if (OS.isFamilyUnix()) {
                // Using command substitution
                checkServerAliveCommand = "/bin/env PID=\"$(lsof -i -P | pgrep -f node)\";echo $PID";
            } else {
                throw new UnsupportedOperationException("Not supported yet for this operating system...");
            }

            if (silentMode == false) {
                LOGGER.log(Level.INFO, "Checking to see if a server instance is running with the command: {0}", checkServerAliveCommand);
            }
            isServerAliveOutput = CommandManager.executeCommandUsingJavaRuntime(checkServerAliveCommand, true);
        } catch (UnsupportedOperationException ex) {
            throw ex;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "An exception was thrown.", ex);
        }

        if (isServerAliveOutput == null || isServerAliveOutput.isEmpty() == true) {
            if (silentMode == false) {
                LOGGER.log(Level.INFO, "Server is not running.");
            }
            return false;
        } else {
            // try to parse the returning string to an integer
            int processId = Integer.valueOf(isServerAliveOutput);
            if (silentMode == false) {
                LOGGER.log(Level.INFO, "Found a running server instance with PID = {0}", processId);
            }
            return true;
        }
    }

    /**
     * Stops an already running Appium server. You must provide the correct port
     * number of the server instance that you want to close.
     */
    @Override
    public void stopServer() {
        String stopServerCommand = "";

        try {
            if (OS.isFamilyWindows()) {
                stopServerCommand = "cmd /c echo off & FOR /F \"usebackq tokens=5\" %a in (`netstat -nao ^| findstr /R /C:\""
                        + _serverArguments.get(AppiumCommonArgs.PORT_NUMBER)
                        + " \"`) do (FOR /F \"usebackq\" %b in (`TASKLIST /FI \"PID eq %a\" ^| findstr /I node.exe`) do taskkill /F /PID %a)";
            } else if (OS.isFamilyMac()) {
                // Using command substitution
                stopServerCommand = "/bin/sh PID=\"$(lsof -i -P | pgrep -f node)\";kill -9 $PID";
            } else if (OS.isFamilyUnix()) {
                // Using command substitution
                stopServerCommand = "/bin/env PID=\"$(lsof -i -P | pgrep -f node)\";kill -9 $PID";
            } else {
                throw new UnsupportedOperationException("Not supported yet for this operating system...");
            }

            LOGGER.log(Level.INFO, "Stopping the server with the command: {0}", stopServerCommand);
            String stopServerOutput = CommandManager.executeCommandUsingJavaRuntime(stopServerCommand, true);
            LOGGER.log(Level.INFO, "Server stopping output: {0}", stopServerOutput);
        } catch (UnsupportedOperationException ex) {
            throw ex;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "An exception was thrown.", ex);
        }
    }

    /**
     * Start the Appium server with the specified server arguments. The server
     * is started asynchronously, so as not to block the execution. Make sure to
     * pass each server argument as a separate string.
     */
    @Override
    public void startServer() {
        try {
            String nodeExecutableFilePath = OS.isFamilyWindows()
                    ? _absoluteServerDirectory + "/node.exe" : _absoluteServerDirectory + "/node/bin/node";
            String appiumJavaScriptFilePath = _absoluteServerDirectory
                    + "/node_modules/appium/bin/appium.js";

            // make sure that the above files exist
            if (new File(nodeExecutableFilePath).exists() == false) {
                throw new InvalidServerDirectoryException(nodeExecutableFilePath);
            }
            if (new File(appiumJavaScriptFilePath).exists() == false) {
                throw new InvalidServerDirectoryException(appiumJavaScriptFilePath);
            }

            // create the command line to be executed
            CommandLine cmdLine = CommandManager.createCommandLine(
                    nodeExecutableFilePath, new String[]{appiumJavaScriptFilePath},
                    _serverArguments.toStringArray());
            final File processOutputError = Files.createTempFile("AppiumServerStreamHandler", ".txt").toFile();
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
            long endTime = startTime + 30000;
            while (isServerRunning(true) == false) {
                if (System.currentTimeMillis() > endTime) {
                    throw new ServerTimeoutException(FileUtils.readFileToString(processOutputError, "UTF8"));
                }
                LOGGER.log(Level.INFO, "Server has not started yet. Trying again in one second...");
                Thread.sleep(1000);
            }
        } catch (ServerTimeoutException ex) {
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

/*Copyright 2014 Genium
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.*/
package org.genium.appium.support.server;

import org.genium.appium.support.command.CommandManager;
import org.genium.appium.support.io.IO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.OS;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * An implementation to Appium server which is responsible for all server
 * operations including: Start, Stop & Checking if the server is alive.
 *
 * @author Hassan Radi
 */
public class AppiumServer {

    private static File _serverFolderPath;

    /**
     * Checks whether an Appium server instance is running on the specified
     * port.
     *
     * @param serverPortNumber The port number to check.
     * @return True if an Appium server is already running on the specified port
     * number, false otherwise.
     */
    public static boolean isServerRunning(int serverPortNumber) {
        return isServerRunning(serverPortNumber, true);
    }

    /**
     * Checks whether an Appium server instance is running on the specified
     * port.
     *
     * @param serverPortNumber The port number to check.
     * @param silentMode Whether or not to print info messages to the console.
     * True will activate silent mode and no messages would be printed.
     * @return True if an Appium server is already running on the specified port
     * number, false otherwise.
     */
    private static boolean isServerRunning(int serverPortNumber, boolean silentMode) {
        String checkServerAliveCommand = null;
        String isServerAliveOutput = null;

        try {
            if (OS.isFamilyWindows()) {
                checkServerAliveCommand = "cmd /c echo off & FOR /F \"usebackq tokens=5\" %a in (`netstat -nao ^| findstr /R /C:\"" + serverPortNumber
                        + " \"`) do (FOR /F \"usebackq\" %b in (`TASKLIST /FI \"PID eq %a\" ^| findstr /I node.exe`) do @echo %a)";
            } else if (OS.isFamilyMac() || OS.isFamilyUnix()) {
                // Using command substitution
                checkServerAliveCommand = "PID=\"$(lsof -i -P | pgrep -f node)\";echo $PID";
            } else {
                throw new UnsupportedOperationException("Not supported yet for this operating system...");
            }

            if (silentMode == false) {
                System.out.println("Checking to see if a server instance is running with the command: " + checkServerAliveCommand);
            }
            isServerAliveOutput = CommandManager.executeCommandUsingJavaRuntime(checkServerAliveCommand, true);
        } catch (Exception ex) {
            System.out.println(getStackTraceAsString(ex));
        }

        if (isServerAliveOutput == null || isServerAliveOutput.isEmpty() == true) {
            if (silentMode == false) {
                System.out.println("Server is not running.");
            }
            return false;
        } else {
            // try to parse the returning string to an integer
            int processId = Integer.valueOf(isServerAliveOutput);
            if (silentMode == false) {
                System.out.println("Found a running server instance with PID = " + processId);
            }
            return true;
        }
    }

    /**
     * Stops an already running Appium server. You must provide the correct port
     * number of the server instance that you want to close.
     *
     * @param serverPortNumber The port number of the running server instance.
     */
    public static void stopServer(int serverPortNumber) {
        String stopServerCommand = "";

        try {
            if (OS.isFamilyWindows()) {
                stopServerCommand = "cmd /c echo off & FOR /F \"usebackq tokens=5\" %a in (`netstat -nao ^| findstr /R /C:\"" + serverPortNumber
                        + " \"`) do (FOR /F \"usebackq\" %b in (`TASKLIST /FI \"PID eq %a\" ^| findstr /I node.exe`) do taskkill /F /PID %a)";
            } else if (OS.isFamilyMac() || OS.isFamilyUnix()) {
                // Using command substitution
                stopServerCommand = "PID=\"$(lsof -i -P | pgrep -f node)\";kill -9 $PID";
            } else {
                throw new UnsupportedOperationException("Not supported yet for this operating system...");
            }

            System.out.println("Stopping the server with the command: " + stopServerCommand);
            String stopServerOutput = CommandManager.executeCommandUsingJavaRuntime(stopServerCommand, true);
            System.out.println("Server stopping output: " + stopServerOutput);
        } catch (Exception ex) {
            System.out.println(getStackTraceAsString(ex));
        }
    }

    /**
     * Start the Appium server with the specified server arguments. The server
     * is started asynchronously, so as not to block the execution. Make sure to
     * pass each server argument as a separate string.
     *
     * Here is an example of valid server arguments: String[] args = new String
     * []{"--address", "127.0.0.1", "--chromedriver-port", "9516",
     * "--bootstrap-port", "4725", "--selendroid-port", "8082", "--no-reset",
     * "--local-timezone"}
     *
     * @param serverArguments The server arguments to use to initialize the
     * server instance.
     */
    public static void startServer(String[] serverArguments) {
        try {
            // extract the server files to a temp directory
            extractServerFilesToTempDirectory();
            String nodeExecutableFilePath = OS.isFamilyWindows()
                    ? _serverFolderPath + "/node.exe" : _serverFolderPath + "/node/bin/node";
            String[] AppiumJavaScriptFilePath = new String[]{_serverFolderPath
                + "/node_modules/appium/bin/appium.js"};

            // create the command line to be executed
            CommandLine cmdLine = CommandManager.createCommandLine(
                    nodeExecutableFilePath, AppiumJavaScriptFilePath, serverArguments);
            CommandManager.executeCommandUsingApacheExec(cmdLine,
                    new FileOutputStream(Files.createTempFile("AppiumServerStreamHandler", ".txt").toFile()));

            /**
             * add a shutdown hook to clear all the temporary created files
             * after the end of the execution for this VM
             */
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    try {
                        System.out.println("VM is terminating. Requesting to delete all created temp files...");
                        FileUtils.deleteDirectory(_serverFolderPath);
                    } catch (Exception ex) {
                        System.out.println(getStackTraceAsString(ex));
                    }
                }
            });
        } catch (Exception ex) {
            System.out.println(getStackTraceAsString(ex));
        }
    }

    /**
     * Extract the Appium server files to a temporary directory to be used to
     * start the server.
     *
     * @throws IOException For any exceptions during the input/output
     * operations.
     */
    private static void extractServerFilesToTempDirectory() throws IOException {
        _serverFolderPath = Files.createTempDirectory("Appium Server ").toFile();

        // extract the node module file and decompress it 
        extract7ZipCompressedResourceFile("Appium.NodeModules");

        // extract the node compressed file depending on the OS
        if (OS.isFamilyWindows()) {
            extract7ZipCompressedResourceFile("Node.Windows");
        } else if (OS.isFamilyMac() || OS.isFamilyUnix()) {
            extract7ZipCompressedResourceFile("Node.Unix");
        } else {
            throw new UnsupportedOperationException("Not supported yet for this operating system...");
        }
    }

    /**
     * Copies a compressed 7z file from the resources, extracts it to the
     * mentioned directory in a tar format, extract the tar format archive and
     * then cleans up any unnecessary files.
     *
     * @param resourceFileName The name of the resource file to process without
     * extension.
     * @throws IOException For any exceptions during the input/output
     * operations.
     */
    private static void extract7ZipCompressedResourceFile(String resourceFileName) throws IOException {
        File nodeModulesCompressedFile = new File(_serverFolderPath.getAbsoluteFile()
                + "/" + resourceFileName + ".7z");
        File tarCompressedFile = new File(_serverFolderPath.getAbsoluteFile()
                + "/" + resourceFileName + ".tar");

        // copy node modules compressed file to the temporary directory
        IOUtils.copy(AppiumServer.class.getClassLoader().getResourceAsStream(
                "appium/server/" + resourceFileName + ".7z"),
                new FileOutputStream(nodeModulesCompressedFile));

        // extract the node modules compressed file
        IO.extract7ZipFile(nodeModulesCompressedFile, _serverFolderPath.getAbsolutePath());
        IO.extractTarFile(tarCompressedFile, _serverFolderPath.getAbsolutePath());

        // delete intermediate compressed files
        nodeModulesCompressedFile.delete();
        tarCompressedFile.delete();
    }

    /**
     * Converts a stacktrace into a String format to be able to display it to
     * the user.
     *
     * @param throwable the exception object to convert to string.
     * @return A String representation of a stackTrace.
     */
    private static String getStackTraceAsString(Throwable throwable) {
        try {
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            throwable.printStackTrace(printWriter);

            return writer.toString();
        } catch (Exception ex) {
            System.out.println("An exception occurred. Message = " + ex.getMessage());
            return "";
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting server deployment process...");
        long startTime = System.currentTimeMillis();
        startServer(new String[]{"--address", "127.0.0.1",
            "--chromedriver-port", "9516", "--bootstrap-port", "4725",
            "--selendroid-port", "8082", "--no-reset", "--local-timezone"});

        System.out.println("Server deployed in "
                + String.valueOf((System.currentTimeMillis() - startTime) / 1000));

        startTime = System.currentTimeMillis();
        while (isServerRunning(4723) == false) {
            System.out.println("Server has not started yet. Trying again in one second...");
            Thread.sleep(1000);
        }
        System.out.println("Server has started. Time taken = "
                + String.valueOf((System.currentTimeMillis() - startTime) / 1000));

        startTime = System.currentTimeMillis();
        stopServer(4723);
        System.out.println("Server has been stopped. Time taken = "
                + String.valueOf((System.currentTimeMillis() - startTime) / 1000));
    }
}

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
package org.genium.appium.support.command;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.OS;
import org.apache.commons.exec.PumpStreamHandler;

/**
 * A command manager class responsible for creating operating system dependent
 * commands and executing them.
 *
 * @author Hassan Radi
 */
public class CommandManager {

    private final static Logger LOGGER = Logger.getLogger(CommandManager.class.getName());

    public static String executeCommandUsingJavaRuntime(String command, boolean getOutput) {
        String output = "";

        try {
            Process p = Runtime.getRuntime().exec(command);

            // read the output from the command if requested by the user
            if (getOutput) {
                BufferedReader stdOutput = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String out = null;

                while ((out = stdOutput.readLine()) != null) {
                    // remove duplicate results
                    if (out.equals(output) == false) {
                        output += out;
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "An exception was thrown while executing the command (" + command + ")", ex);
        }

        return output;
    }

    /**
     * Execute a command on the operating system.
     *
     * @param commandLine The command to be executed.
     * @param outputStreamHandler An output stream to dump the process stderr
     * and stdout to it.
     */
    public static void executeCommandUsingApacheExec(CommandLine commandLine, OutputStream outputStreamHandler) {
        try {
            DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
            PumpStreamHandler streamHandler = new PumpStreamHandler(outputStreamHandler);

            Executor process = new DefaultExecutor();
            process.setExitValue(0);
            process.setStreamHandler(streamHandler);
            process.execute(commandLine, resultHandler);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "An exception was thrown.", ex);
        }
    }

    /**
     * Constructs a CommandLine object depending on the current running
     * operating system using the number of arguments passed to it.
     *
     * @param command The native OS command to run or an absolute path to an
     * executable to run.
     * @param parameters The command parameters.
     * @param arguments String arguments of the command to formulate from.
     * @return CommandLine object that represents the command you want to
     * execute.
     */
    public static CommandLine createCommandLine(String command, String[] parameters, String... arguments) {
        CommandLine commanddLine = null;

        // add the command to be executed
        if (OS.isFamilyWindows()) {
            commanddLine = new CommandLine("\"" + command + "\"");
        } else if (OS.isFamilyMac() || OS.isFamilyUnix()) {
            commanddLine = new CommandLine(command.contains(" ") ? "'" + command + "'" : command);
        } else {
            throw new UnsupportedOperationException("Unsupported operating system.");
        }

        // add the command parameters
        if (OS.isFamilyWindows()) {
            for (String parameter : parameters) {
                commanddLine.addArgument("\"" + parameter + "\"", false);
            }
        } else if (OS.isFamilyMac() || OS.isFamilyUnix()) {
            for (String parameter : parameters) {
                commanddLine.addArgument(parameter.contains(" ") ? "'" + parameter + "'" : parameter, false);
            }
        }

        // add the command arguments
        for (String argument : arguments) {
            // you have to pass the false value and disable handling quoting
            // otherwise the OS won't be able to run the shell file on MAc OS
            commanddLine.addArgument(argument, false);
        }

        return commanddLine;
    }
}

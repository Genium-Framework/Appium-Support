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
package org.genium.appium.support.server.arguments;

/**
 * Common Appium Server configurations between Android and iOS operating
 * systems. Useful in case of changing any flag name.
 *
 * @author Hassan Radi
 */
public class CommonArgs {

    public static final String ENTER_REPL_MODE = "--shell";
    public static final String APP_ABSOLUTE_PATH = "--app";
    public static final String UDID = "--udid"; // also "-U"
    public static final String IP_ADDRESS = "--address"; // also "-a"
    public static final String PORT_NUMBER = "--port"; // also "-p"
    public static final String CALLBACK_IP_ADDRESS = "--callback-address"; // also "-ca"
    public static final String CALLBACK_PORT_NUMBER = "--callback-port"; // also "-cp"
    public static final String KEEP_ARTIFACTS = "--keep-artifacts";
    public static final String ENABLE_SESSION_OVERRIDE = "--session-override";
    public static final String ENABLE_FULL_RESET = "--full-reset";
    public static final String ENABLE_NO_RESET = "--no-reset";
    public static final String PRE_LAUNCH_APP = "--pre-launch"; // also "-l"
    public static final String LOG_TO_FILE = "--log"; // also "-g"
    public static final String LOG_LEVEL = "--log-level";
    public static final String LOG_TIMESTAMP_IN_CONSOLE = "--log-timestamp";
    public static final String LOCAL_TIMEZONE_WITH_TIMESTAMPS = "--local-timezone";
    public static final String ENABLE_COLORS_IN_CONSOLE_OUTPUT = "--log-no-colors";
    public static final String SEND_LOG_OUTPUT_TO_HTTP_LISTENER = "--webhook"; // also "-G"
    public static final String DEVICE_NAME_TO_USE = "--device-name";
    public static final String MOBILE_PLATFORM_NAME = "--platform-name";
    public static final String MOBILE_PLATFORM_VERSION = "--platform-version";
    public static final String AUTOMATION_TOOL_NAME = "--automation-name";
    public static final String MOBILE_BROWSER_NAME = "--browser-name";
    public static final String DEVICE_LANGUAGE = "--language";
    public static final String DEVICE_LOCALE = "--locale";
    public static final String SELENIUM_GRID_NODE_CONFIGURATION_FILE = "--nodeconfig";
    public static final String ROBOT_IP_ADDRESS = "--robot-address"; // also "-ra"
    public static final String ROBOT_PORT_NUMBER = "--robot-port"; // also "-rp"
    public static final String CHROME_DRIVER_PORT_NUMBER = "--chromedriver-port";
    public static final String CHROME_DRIVER_FULL_PATH = "--chromedriver-executable";
    public static final String SHOW_SERVER_CONFIGURATIONS_AND_EXIT = "--show-config";
    public static final String BYPASS_APPIUM_CHECKS = "--no-perms-check";
    public static final String COMMAND_TIMEOUT = "--command-timeout";
    public static final String STRICT_CAPABILITIES = "--strict-caps";
    public static final String TEMPORARY_DIRECTORY = "--tmp";
    public static final String TRACE_DIRECTORY_ABSOLUTE_PATH = "--trace-dir";
}

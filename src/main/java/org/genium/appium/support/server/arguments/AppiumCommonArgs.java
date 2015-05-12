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
public class AppiumCommonArgs {

    /**
     * Enter REPL mode
     */
    public static final String ENTER_REPL_MODE = "--shell";
    /**
     * IOS: abs path to simulator-compiled .app file or the bundle_id of the
     * desired target on device; Android: abs path to .apk file
     */
    public static final String APP_ABSOLUTE_PATH = "--app";
    /**
     * Unique device identifier of the connected physical device
     */
    public static final String UDID = "--udid"; // also "-U"
    /**
     * IP Address to listen on
     */
    public static final String IP_ADDRESS = "--address"; // also "-a"
    /**
     * port to listen on
     */
    public static final String PORT_NUMBER = "--port"; // also "-p"
    /**
     * callback IP Address (default: same as address)
     */
    public static final String CALLBACK_IP_ADDRESS = "--callback-address"; // also "-ca"
    /**
     * callback port (default: same as port)
     */
    public static final String CALLBACK_PORT_NUMBER = "--callback-port"; // also "-cp"
    /**
     * Enables session override (clobbering)
     */
    public static final String ENABLE_SESSION_OVERRIDE = "--session-override";
    /**
     * (iOS) Delete the entire simulator folder. (Android) Reset app state by
     * uninstalling app instead of clearing app data. On Android, this will also
     * remove the app after the session is complete.
     */
    public static final String ENABLE_FULL_RESET = "--full-reset";
    /**
     * Don’t reset app state between sessions (IOS: don’t delete app plist
     * files; Android: don’t uninstall app before new session)
     */
    public static final String ENABLE_NO_RESET = "--no-reset";
    /**
     * Pre-launch the application before allowing the first session (Requires
     * –app and, for Android, –app-pkg and –app-activity)
     */
    public static final String PRE_LAUNCH_APP = "--pre-launch"; // also "-l"
    /**
     * Also send log output to this file
     */
    public static final String LOG_TO_FILE = "--log"; // also "-g"
    /**
     * log level; default (console[:file]): debug[:debug]
     */
    public static final String LOG_LEVEL = "--log-level";
    /**
     * Show timestamps in console output
     */
    public static final String LOG_TIMESTAMP_IN_CONSOLE = "--log-timestamp";
    /**
     * Use local timezone for timestamps
     */
    public static final String LOCAL_TIMEZONE_WITH_TIMESTAMPS = "--local-timezone";
    /**
     * Don’t use colors in console output
     */
    public static final String ENABLE_COLORS_IN_CONSOLE_OUTPUT = "--log-no-colors";
    /**
     * Also send log output to this HTTP listener
     */
    public static final String SEND_LOG_OUTPUT_TO_HTTP_LISTENER = "--webhook"; // also "-G"
    /**
     * Name of the mobile device to use
     */
    public static final String DEVICE_NAME_TO_USE = "--device-name";
    /**
     * Name of the mobile platform: iOS, Android, or FirefoxOS
     */
    public static final String MOBILE_PLATFORM_NAME = "--platform-name";
    /**
     * Version of the mobile platform
     */
    public static final String MOBILE_PLATFORM_VERSION = "--platform-version";
    /**
     * Name of the automation tool: Appium or Selendroid
     */
    public static final String AUTOMATION_TOOL_NAME = "--automation-name";
    /**
     * Name of the mobile browser: Safari or Chrome
     */
    public static final String MOBILE_BROWSER_NAME = "--browser-name";
    /**
     * Language for the iOS simulator / Android Emulator
     */
    public static final String DEVICE_LANGUAGE = "--language";
    /**
     * Locale for the iOS simulator / Android Emulator
     */
    public static final String DEVICE_LOCALE = "--locale";
    /**
     * Configuration JSON file to register Appium with selenium grid
     */
    public static final String SELENIUM_GRID_NODE_CONFIGURATION_FILE = "--nodeconfig";
    /**
     * IP Address of robot
     */
    public static final String ROBOT_IP_ADDRESS = "--robot-address"; // also "-ra"
    /**
     * port for robot
     */
    public static final String ROBOT_PORT_NUMBER = "--robot-port"; // also "-rp"
    /**
     * Port upon which ChromeDriver will run
     */
    public static final String CHROME_DRIVER_PORT_NUMBER = "--chromedriver-port";
    /**
     * ChromeDriver executable full path
     */
    public static final String CHROME_DRIVER_FULL_PATH = "--chromedriver-executable";
    /**
     * Show info about the Appium server configuration and exit
     */
    public static final String SHOW_SERVER_CONFIGURATIONS_AND_EXIT = "--show-config";
    /**
     * Bypass Appium’s checks to ensure we can read/write necessary files
     */
    public static final String BYPASS_APPIUM_CHECKS = "--no-perms-check";
    /**
     * The default command timeout for the server to use for all sessions. Will
     * still be overridden by newCommandTimeout cap
     */
    public static final String COMMAND_TIMEOUT = "--command-timeout";
    /**
     * Cause sessions to fail if desired caps are sent in that Appium does not
     * recognize as valid for the selected device
     */
    public static final String STRICT_CAPABILITIES = "--strict-caps";
    /**
     * Absolute path to directory Appium can use to manage temporary files, like
     * built-in iOS apps it needs to move around. On *nix/Mac defaults to /tmp,
     * on Windows defaults to C:\Windows\Temp
     */
    public static final String TEMPORARY_DIRECTORY = "--tmp";
    /**
     * Add exaggerated spacing in logs to help with visual inspection
     */
    public static final String DEBUG_LOG_SPACING = "--debug-log-spacing";
}

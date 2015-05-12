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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.genium.appium.support.server.arguments;

/**
 * Represents all server configurations for Appium - iOS
 *
 * @author Hassan Radi
 */
public class AppiumIOSArgs extends AppiumCommonArgs {

    /**
     * IOS only: the relative path of the dir where Localizable.strings file
     * resides
     */
    public static final String LOCALIZABLE_FILE_RELATIVE_PATH = "--localizable-strings-dir";
    /**
     * (IOS-only) abs path to compiled .ipa file
     */
    public static final String IPA_ABSOLUTE_PATH = "--ipa";
    /**
     * @deprecated Trace is now in tmp dir by default and is cleared before each
     * run. Please also refer to the -–trace-dir flag.
     */
    public static final String KEEP_ARTIFACTS = "--keep-artifacts";
    /**
     * (iOS-only) How many times to retry launching Instruments before saying it
     * crashed or timed out
     */
    public static final String RETRIES_WHILE_LAUNCHING_INSTRUMENTS = "--backend-retries"; // also "-r"
    /**
     * (iOS-only) how long in ms to wait for Instruments to launch
     */
    public static final String TIMEOUT_WHEN_LAUNCHING_INSTRUMENTS = "--launch-timeout"; // also "-lt"
    /**
     * (IOS-only) IOS has a weird built-in unavoidable delay. We patch this in
     * appium. If you do not want it patched, pass in this flag.
     */
    public static final String USE_NATIVE_INSTRUMENTS = "--native-instruments-lib";
    /**
     * (IOS-Only) Use the safari app
     */
    public static final String USE_SAFARI = "--safari";
    /**
     * (IOS-Simulator-only) use the default simulator that instruments launches
     * on its own
     */
    public static final String USE_DEFAULT_SIMULATOR_DEVICE = "--default-device"; // also "-dd"
    /**
     * (IOS-only) Use the iPhone Simulator no matter what the app wants
     */
    public static final String FORCE_USE_IPHONE_SIMULATOR = "--force-iphone";
    /**
     * (IOS-only) Use the iPad Simulator no matter what the app wants
     */
    public static final String FORCE_USE_IPAD_SIMULATOR = "--force-ipad";
    /**
     * (IOS-only) calendar format for the iOS simulator
     */
    public static final String SIMULATOR_CALENDAR_FORMAT = "--calendar-format";
    /**
     * (IOS-only) use LANDSCAPE or PORTRAIT to initialize all requests to this
     * orientation
     */
    public static final String DEVICE_ORIENTATION = "--orientation";
    /**
     * (IOS-only) .tracetemplate file to use with Instruments
     */
    public static final String TRACE_TEMPLATE_FILE_PATH = "--tracetemplate";
    /**
     * (IOS-only) custom path to the instruments commandline tool
     */
    public static final String CUSTOM_INSTRUMENTS_PATH = "--instruments";
    /**
     * (IOS-only) if set, the iOS simulator log will be written to the console
     */
    public static final String SHOW_SIMULATOR_LOG = "--show-sim-log";
    /**
     * (IOS-only) if set, the iOS system log will be written to the console
     */
    public static final String SHOW_IOS_LOG = "--show-ios-log";
    /**
     * (iOS) Whether to keep keychains (Library/Keychains) when reset app
     * between sessions
     */
    public static final String KEEP_KEYCHAINS_WHEN_RESETING_APP = "--keep-keychains";
    /**
     * Xcode 6 has a bug on some platforms where a certain simulator can only be
     * launched without error if all other simulator devices are first deleted.
     * This option causes Appium to delete all devices other than the one being
     * used by Appium. Note that this is a permanent deletion, and you are
     * responsible for using simctl or xcode to manage the categories of devices
     * used with Appium.
     */
    public static final String ISOLATE_SIM_DEVICE = "--isolate-sim-device";
    /**
     * Absolute path to directory Appium use to save ios instruments traces,
     * defaults to /appium-instruments
     */
    public static final String TRACE_DIRECTORY_ABSOLUTE_PATH = "--trace-dir";
}

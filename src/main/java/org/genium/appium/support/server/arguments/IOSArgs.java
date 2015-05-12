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
public class IOSArgs extends CommonArgs {

    public static final String LOCALIZABLE_FILE_RELATIVE_PATH = "--localizable-strings-dir";
    public static final String IPA_ABSOLUTE_PATH = "--ipa";
    public static final String RETRIES_WHILE_LAUNCHING_INSTRUMENTS = "--backend-retries"; // also "-r"
    public static final String TIMEOUT_WHEN_LAUNCHING_INSTRUMENTS = "--launch-timeout"; // also "-lt"
    public static final String USE_NATIVE_INSTRUMENTS = "--native-instruments-lib";
    public static final String USE_SAFARI = "--safari";
    public static final String USE_DEFAULT_SIMULATOR_DEVICE = "--default-device"; // also "-dd"
    public static final String FORCE_USE_IPHONE_SIMULATOR = "--force-iphone";
    public static final String FORCE_USE_IPAD_SIMULATOR = "--force-ipad";
    public static final String SIMULATOR_CALENDAR_FORMAT = "--calendar-format";
    public static final String DEVICE_ORIENTATION = "--orientation";
    public static final String TRACE_TEMPLATE_FILE_PATH = "--tracetemplate";
    public static final String SHOW_SIMULATOR_LOG = "--show-sim-log";
    public static final String SHOW_IOS_LOG = "--show-ios-log";
    public static final String KEEP_KEYCHAINS_WHEN_RESETING_APP = "--keep-keychains";
    public static final String ISOLATE_SIM_DEVICE = "--isolate-sim-device";
}

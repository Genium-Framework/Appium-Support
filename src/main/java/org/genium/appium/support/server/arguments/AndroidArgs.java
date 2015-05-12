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
 * Represents all server configurations for Appium - Android OS
 *
 * @author Hassan Radi
 */
public class AndroidArgs extends CommonArgs {

    public static final String BOOTSTRAP_PORT_NUMBER = "--bootstrap-port"; // also "-bp"
    public static final String PACKAGE_NAME_OF_APP = "--app-pkg";
    public static final String ACTIVITY_NAME_TO_RUN_FROM_PACKAGE = "--app-activity";
    public static final String PACKAGE_NAME_TO_WAIT_FOR = "--app-wait-package";
    public static final String ACTIVITY_NAME_TO_WAIT_FOR = "--app-wait-activity";
    public static final String FULLY_QUALIFIED_INSTRUMENTATION = "--android-coverage";
    public static final String AVD_NAME = "--avd";
    public static final String AVD_ARGUMENTS = "--avg-args";
    public static final String TIMEOUT_WAITING_FOR_DEVICE_TO_BE_READY = "--device-ready-timeout";
    public static final String SELENDROID_PORT_NUMBER = "--selendroid-port";
    public static final String USE_KEY_STORE_TO_SIGN_APK_FILE = "--use-keystore";
    public static final String KEY_STORE_FILE_PATH = "--keystore-path";
    public static final String KEY_STORE_PASSWORD = "--keystore-password";
    public static final String KEY_ALIAS = "--key-alias";
    public static final String KEY_PASSWORD = "--key-password";
    public static final String INTENT_ACTION = "--intent-action";
    public static final String INTENT_CATEGORY = "--intent-category";
    public static final String INTENT_FLAGS = "--intent-flags";
    public static final String INTENT_ARGUMENTS = "--intent-args";
    public static final String KILL_ADB_SERVER = "--suppress-adb-kill-server";
}

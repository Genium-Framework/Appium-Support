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
package com.github.genium_framework.appium.support.server.arguments;

/**
 * Represents all server configurations for Appium - Android OS
 *
 * @author Hassan Radi
 */
public class AppiumAndroidArgs extends AppiumCommonArgs {

    /**
     * (Android-only) port to use on device to talk to Appium
     */
    public static final String BOOTSTRAP_PORT_NUMBER = "--bootstrap-port"; // also "-bp"
    /**
     * (Android-only) Java package of the Android app you want to run (e.g.,
     * com.example.android.myApp)
     */
    public static final String PACKAGE_NAME_OF_APP = "--app-pkg";
    /**
     * (Android-only) Activity name for the Android activity you want to launch
     * from your package (e.g., MainActivity)
     */
    public static final String ACTIVITY_NAME_TO_RUN_FROM_PACKAGE = "--app-activity";
    /**
     * (Android-only) Package name for the Android activity you want to wait for
     * (e.g., com.example.android.myApp)
     */
    public static final String PACKAGE_NAME_TO_WAIT_FOR = "--app-wait-package";
    /**
     * (Android-only) Activity name for the Android activity you want to wait
     * for (e.g., SplashActivity)
     */
    public static final String ACTIVITY_NAME_TO_WAIT_FOR = "--app-wait-activity";
    /**
     * (Android-only) Fully qualified instrumentation class.
     *
     * Passed to -w in adb shell am instrument -e coverage true -w
     */
    public static final String FULLY_QUALIFIED_INSTRUMENTATION = "--android-coverage";
    /**
     * (Android-only) Name of the avd to launch
     */
    public static final String AVD_NAME = "--avd";
    /**
     * (Android-only) Additional emulator arguments to launch the avd
     */
    public static final String AVD_ARGUMENTS = "--avg-args";
    /**
     * (Android-only) Timeout in seconds while waiting for device to become
     * ready
     */
    public static final String TIMEOUT_WAITING_FOR_DEVICE_TO_BE_READY = "--device-ready-timeout";
    /**
     * Local port used for communication with Selendroid
     */
    public static final String SELENDROID_PORT_NUMBER = "--selendroid-port";
    /**
     * (Android-only) When set the keystore will be used to sign apks.
     */
    public static final String USE_KEY_STORE_TO_SIGN_APK_FILE = "--use-keystore";
    /**
     * (Android-only) Path to keystore
     */
    public static final String KEY_STORE_FILE_PATH = "--keystore-path";
    /**
     * (Android-only) Password to keystore
     */
    public static final String KEY_STORE_PASSWORD = "--keystore-password";
    /**
     * (Android-only) Key alias
     */
    public static final String KEY_ALIAS = "--key-alias";
    /**
     * (Android-only) Key password
     */
    public static final String KEY_PASSWORD = "--key-password";
    /**
     * (Android-only) Intent action which will be used to start activity
     */
    public static final String INTENT_ACTION = "--intent-action";
    /**
     * (Android-only) Intent category which will be used to start activity
     */
    public static final String INTENT_CATEGORY = "--intent-category";
    /**
     * (Android-only) Flags that will be used to start activity
     */
    public static final String INTENT_FLAGS = "--intent-flags";
    /**
     * (Android-only) Additional intent arguments that will be used to start
     * activity
     */
    public static final String INTENT_ARGUMENTS = "--intent-args";
    /**
     * (Android-only) When included, refrains from stopping the app before
     * restart
     */
    public static final String DONT_STOP_APP_ON_RESET = "--dont-stop-app-on-reset";
    /**
     * (Android-only) If set, prevents Appium from killing the adb server
     * instance
     */
    public static final String SUPPRESS_ADB_KILL_SERVER = "--suppress-adb-kill-server";
}

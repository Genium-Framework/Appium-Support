/* Copyright 2015 Genium Testing Framework.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.genium_framework.appium.support.server;

import com.github.genium_framework.server.ServerArguments;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * A set of tests to verify that that the Appium server functions are working
 * correctly.
 *
 * @author Hassan Radi
 */
public class AppiumServerTests {

    AppiumServer _appiumServer;

    @Before
    public void setUp() {
        ServerArguments serverArguments = new ServerArguments();
        serverArguments.setArgument("--address", "127.0.0.1");
        serverArguments.setArgument("--chromedriver-port", 9516);
        serverArguments.setArgument("--bootstrap-port", 4725);
        serverArguments.setArgument("--no-reset", true);
        serverArguments.setArgument("--local-timezone", false);

        _appiumServer = new AppiumServer(serverArguments);

        System.out.println("Starting the Appium server...");
        _appiumServer.startServer();
    }

    @After
    public void tearDown() {
        if (_appiumServer.isServerRunning()) {
            System.out.println("Stopping the Appium Server...");
            _appiumServer.stopServer();
        }
    }

    @Test
    public void checkServerIsRunning() {
        System.out.println("Making sure that the Appium server is running...");
        final boolean serverRunning = _appiumServer.isServerRunning();
        Assert.assertTrue("Appium server should be running.", serverRunning);
    }
}

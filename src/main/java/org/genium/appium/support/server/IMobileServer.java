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

/**
 * An interface to all the functions a mobile server should implement.
 *
 * @author Hassan Radi
 */
public interface IMobileServer {

    /**
     * Checks whether a server instance is running on the specified port.
     *
     * @return True if a server is already running on the specified port number,
     * false otherwise.
     */
    public abstract boolean isServerRunning();

    /**
     * Stops an already running server instance. You must provide the correct
     * port number of the server instance that you want to close.
     */
    public abstract void stopServer();

    /**
     * Start a server instance asynchronously, so as not to block the execution.
     */
    public abstract void startServer();
}

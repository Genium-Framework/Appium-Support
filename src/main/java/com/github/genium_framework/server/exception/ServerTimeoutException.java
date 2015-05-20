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
package com.github.genium_framework.server.exception;

/**
 * A server exception that is thrown in case the server timeout out while
 * starting.
 *
 * @author Hassan Radi
 */
public class ServerTimeoutException extends ServerException {

    public ServerTimeoutException(String serverError) {
        super("The server didn't start after 30 seconds with error:\n"
                + serverError);
    }
}

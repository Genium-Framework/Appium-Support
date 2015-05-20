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
package com.github.genium_framework.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.github.genium_framework.appium.support.server.arguments.AppiumCommonArgs;

/**
 * Key/Map representation for server configuration. Must implement the
 * serializable interface to be able to serialize it.
 *
 * @author Hassan Radi
 */
public class ServerArguments implements Serializable {

    protected HashMap<String, Object> _argumentsMap = new HashMap<String, Object>();

    public ServerArguments() {
        _argumentsMap.put(AppiumCommonArgs.PORT_NUMBER, 4723);
    }

    /**
     * Add a server argument to the current object.
     *
     * @param argumentKey The unique entry key to refer to the server argument.
     * @param argumentValue The value to map to the unique key.
     */
    public void setArgument(String argumentKey, Object argumentValue) {
        _argumentsMap.put(argumentKey, argumentValue);
    }

    /**
     * Retrieve a map value using a unique server argument key.
     *
     * @param argumentKey The unique entry key to refer to the server argument.
     * @return The value that maps to the key.
     */
    public Object get(String argumentKey) {
        return _argumentsMap.get(argumentKey);
    }

    /**
     * Converts the server arguments to a String array that will be used to
     * start a server instance.
     *
     * @return A String array representation of the arguments map.
     */
    public String[] toStringArray() {
        List<String> arguments = new ArrayList<String>();
        for (String key : _argumentsMap.keySet()) {
            // only add the key if it is required
            if (_argumentsMap.get(key) instanceof Boolean == false
                    || _argumentsMap.get(key).equals(true)) {
                arguments.add(key);
            }

            /**
             * only add the value if it is not boolean value.
             */
            if (_argumentsMap.get(key) instanceof Boolean == false) {
                arguments.add(_argumentsMap.get(key).toString());
            }
        }

        return arguments.toArray(new String[arguments.size()]);
    }
}

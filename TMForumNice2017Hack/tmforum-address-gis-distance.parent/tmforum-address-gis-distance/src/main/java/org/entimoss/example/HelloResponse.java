/*
 * Copyright 2014 OpenNMS Group Inc., Entimoss ltd.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.entimoss.example;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "hello-response")
public final class HelloResponse {
    
    static public final int SUCCESS = 0;
    static public final int ERROR = 1;
    static public final String DEFAULT_NAME = "world";
    
    private int status;
    private String error;
    private String name;

    public HelloResponse() {
        this.status = SUCCESS;
        this.error = null;
        this.name = DEFAULT_NAME;
    }
    
    public HelloResponse(String name) {
        this();
        this.setName(name);
    }
    
    public HelloResponse(int status, String error) {
        this();
        this.status = status;
        this.error = error;
    }
    
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        } else {
            this.name = DEFAULT_NAME;
        }
    }
    
    @Override
    public String toString() {
        return "hello " + name + "!!!";
    }   
}
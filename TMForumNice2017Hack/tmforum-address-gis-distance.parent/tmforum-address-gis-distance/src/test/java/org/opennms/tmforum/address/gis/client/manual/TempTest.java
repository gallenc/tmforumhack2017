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

package org.opennms.tmforum.address.gis.client.manual;

import static org.junit.Assert.*;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.junit.Test;

public class TempTest {

	@Test
	public void test() {
		MultivaluedMap<String, String> queryParams = new MultivaluedHashMap<String, String>();
		
		queryParams.add("key0ne", "1value1");
		queryParams.add("key0ne", "1value2");
		queryParams.add("keyTwo", "2value1");
		queryParams.add("keyTwo", "2value2");
		queryParams.add("keyTwe", "3value1");
		queryParams.add("keyTwe", "3value2");
		
		
		System.out.println("one "+queryParams);
		
		queryParams = new MultivaluedHashMap<String, String>(queryParams);
		
		System.out.println("two "+queryParams);
	}

}

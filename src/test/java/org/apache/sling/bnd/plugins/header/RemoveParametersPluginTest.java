/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.sling.bnd.plugins.header;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import aQute.bnd.header.Attrs;
import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Jar;
import aQute.service.reporter.Reporter;
import org.apache.sling.bnd.plugins.header.RemoveParametersPlugin;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;

public class RemoveParametersPluginTest {

    @Test
    public void testSlingResourceresolverRequireCapability() throws Exception {
        final File bundle = new File(System.getProperty("test.bundles.sling.resourceresolver"));
        final Jar jar = new Jar(bundle);
        final Analyzer analyzer = new Analyzer(jar);
        analyzer.analyze();

        final Reporter reporter = mock(Reporter.class);
        final Map<String, String> properties = Collections.singletonMap("Require-Capability", "osgi.service;filter:=\"(objectClass=org.osgi.service.event.EventAdmin)\";effective:=active,osgi.service;filter:=\"(objectClass=org.osgi.service.event.EventHandler)\";effective:=active;cardinality:=multiple");

        final RemoveParametersPlugin plugin = new RemoveParametersPlugin();
        plugin.setProperties(properties);
        plugin.setReporter(reporter);

        final List<String> before = analyzer.getRequireCapability().values().stream().map(Attrs::toString).collect(Collectors.toList());
        assertThat(before).containsAllOf(
            "filter:=\"(objectClass=org.osgi.service.event.EventAdmin)\";effective:=active",
            "filter:=\"(objectClass=org.osgi.service.event.EventHandler)\";effective:=active;cardinality:=multiple"
        );

        final boolean result = plugin.analyzeJar(analyzer);
        assertThat(result).isFalse();

        final List<String> after = analyzer.getRequireCapability().values().stream().map(Attrs::toString).collect(Collectors.toList());
        assertThat(after).containsNoneOf(
            "filter:=\"(objectClass=org.osgi.service.event.EventAdmin)\";effective:=active",
            "filter:=\"(objectClass=org.osgi.service.event.EventHandler)\";effective:=active;cardinality:=multiple"
        );
    }

}

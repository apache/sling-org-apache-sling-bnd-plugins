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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import aQute.bnd.header.Attrs;
import aQute.bnd.header.OSGiHeader;
import aQute.bnd.header.Parameters;
import aQute.bnd.osgi.Analyzer;
import aQute.bnd.service.AnalyzerPlugin;
import aQute.bnd.service.Plugin;
import aQute.service.reporter.Reporter;

public class RemoveParametersPlugin implements Plugin, AnalyzerPlugin {

    /**
     * the header parameters, e.g.
     * key: 'Require-Capability'
     * value: 'osgi.service;filter:="(objectClass=org.osgi.service.event.EventAdmin)";effective:=active,osgi.service;filter:="(objectClass=org.osgi.service.event.EventHandler)";effective:=active;cardinality:=multiple'
     */
    private Map<String, String> properties;

    private Reporter reporter;

    @Override
    public void setProperties(final Map<String, String> properties) throws Exception {
        this.properties = properties;
    }

    @Override
    public void setReporter(final Reporter reporter) {
        this.reporter = reporter;
    }

    @Override
    public boolean analyzeJar(final Analyzer analyzer) throws Exception {
        process(analyzer, properties, reporter);
        return false;
    }

    private void process(final Analyzer analyzer, final Map<String, String> headers, final Reporter reporter) {
        for (final Entry<String, String> header : headers.entrySet()) {
            reporter.trace("Processing header '%s'", header.getKey());

            // create mapping, e.g. 'osgi.service' → ['filter:="(objectClass=org.apache.sling.api.resource.ResourceResolverFactory)";effective:=active']
            final Map<String, Set<String>> mapping = new HashMap<>();
            for (final Entry<String, Attrs> parameter : OSGiHeader.parseHeader(header.getValue()).entrySet()) {
                final String key = cleanKey(parameter.getKey());
                mapping.computeIfAbsent(key, k -> new HashSet<>());
                reporter.trace("Mapping '%s' → '%s'", key, parameter.getValue());
                mapping.get(key).add(parameter.getValue().toString());
            }

            // collect parameters for removal
            final Parameters parameters = analyzer.getParameters(header.getKey());
            final Set<String> remove = new HashSet<>();
            for (final Entry<String, Attrs> parameter : parameters.entrySet()) {
                final String key = cleanKey(parameter.getKey());
                if (mapping.containsKey(key) && mapping.get(key).contains(parameter.getValue().toString())) {
                    reporter.trace("Parameter for removal found: %s;%s", key, parameter.getValue());
                    remove.add(parameter.getKey());
                }
            }

            // finally remove parameters
            if (!remove.isEmpty()) {
                for (final String key : remove) {
                    final Attrs attrs = parameters.remove(key);
                    reporter.trace("Parameter removed: %s;%s", cleanKey(key), attrs);
                }
                reporter.trace("Setting header '%s'", header.getKey());
                analyzer.set(header.getKey(), parameters.toString());
            }
        }
    }

    private String cleanKey(final String key) {
        return key.replace("~", "");
    }

}

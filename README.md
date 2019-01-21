[<img src="http://sling.apache.org/res/logos/sling.png"/>](http://sling.apache.org)

 [![Build Status](https://builds.apache.org/buildStatus/icon?job=sling-org-apache-sling-bnd-plugins-1.8)](https://builds.apache.org/view/S-Z/view/Sling/job/sling-org-apache-sling-bnd-plugins-1.8) [![Test Status](https://img.shields.io/jenkins/t/https/builds.apache.org/view/S-Z/view/Sling/job/sling-org-apache-sling-bnd-plugins-1.8.svg)](https://builds.apache.org/view/S-Z/view/Sling/job/sling-org-apache-sling-bnd-plugins-1.8/test_results_analyzer/) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.apache.sling/org.apache.sling.bnd.plugins/badge.svg)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22org.apache.sling%22%20a%3A%22org.apache.sling.bnd.plugins%22) [![JavaDocs](https://www.javadoc.io/badge/org.apache.sling/org.apache.sling.bnd.plugins.svg)](https://www.javadoc.io/doc/org.apache.sling/org.apache.sling.bnd.plugins) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

# Apache Sling bnd Plugins

This module is part of the [Apache Sling](https://sling.apache.org) project.

This module provides plugins for [bnd](https://bnd.bndtools.org).

## RemoveParametersPlugin

Removes parameters from bundle headers.

Example instruction (for bnd file):

````
-plugin:\
  org.apache.sling.bnd.plugins.header.RemoveParametersPlugin;\
    'Require-Capability'='osgi.service;filter:="(objectClass=org.osgi.service.event.EventAdmin)";effective:=active,osgi.service;filter:="(objectClass=org.osgi.service.event.EventHandler)";effective:=active;cardinality:=multiple'
````

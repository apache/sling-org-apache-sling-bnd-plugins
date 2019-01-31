[<img src="https://sling.apache.org/res/logos/sling.png"/>](https://sling.apache.org)

 [![Build Status](https://builds.apache.org/buildStatus/icon?job=Sling/sling-org-apache-sling-bnd-plugins/master)](https://builds.apache.org/job/Sling/job/sling-org-apache-sling-bnd-plugins/job/master) [![Test Status](https://img.shields.io/jenkins/t/https/builds.apache.org/job/Sling/job/sling-org-apache-sling-bnd-plugins/job/master.svg)](https://builds.apache.org/job/Sling/job/sling-org-apache-sling-bnd-plugins/job/master/test_results_analyzer/) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

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

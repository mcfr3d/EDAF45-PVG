#!/bin/bash
mkdir -p bin
javac -d bin -cp ../json-20160810.jar src/**/*.java
mkdir -p jar
cd bin
jar cfm ../jar/registration.jar ../registration.manifest registration/*.class util/*.class
jar cfm ../jar/resultMerge.jar ../resultMerge.manifest resultMerge/*.class util/*.class

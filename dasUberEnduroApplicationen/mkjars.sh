#!/bin/bash
mkdir -p bin
javac -d bin src/**/*.java
mkdir -p jar
cd bin
jar cfm ../jar/registration.jar ../registration.manifest registration/*.class util/RegistrationWriter.class
jar cfm ../jar/resultMerge.jar ../resultMerge.manifest resultMerge/*.class util/*.class

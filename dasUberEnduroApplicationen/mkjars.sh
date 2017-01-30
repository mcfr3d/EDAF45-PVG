#!/bin/bash
mkdir -p bin
javac -d bin src/**/*.java
mkdir -p jar
cd bin
jar cfm ../jar/registration.jar ../registration.manifest registration/*.class util/RegistrationWriter.class
jar cfm ../jar/resultMerge.jar ../resultMerge.manifest resultMerge/*.class util/*.class


cd ../
#pwd
mkdir DasUberEnduroApplikationen
cp jar/registration.jar DasUberEnduroApplikationen/dasUberAnmeldung.jar 
cp jar/resultMerge.jar DasUberEnduroApplikationen/dasUberResultat.jar
cp Manual.html DasUberEnduroApplikationen/dasUberHandbuch.html
zip -r DasUberEnduroApplikationen.zip DasUberEnduroApplikationen/
rm -rf DasUberEnduroApplikationen
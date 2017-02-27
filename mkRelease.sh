#!/bin/bash
cd dasUberEnduroApplicationen && ./gradlew build && cd ..
mkdir -p release
cp dasUberEnduroApplicationen/registration/build/libs/registration.jar release/.
cp dasUberEnduroApplicationen/resultMerge/build/libs/resultMerge.jar release/.
cp dasUberEnduroApplicationen/Manual.html release/.
cp README.md release/.
cp teknisk-dokumentation.pdf release/.
cp -r AcT/ release/.
cp -r dasUberEnduroApplicationen/ release/. 
cp runAcT.sh release/.
rm -r release/dasUberEnduroApplicationen/registration
rm -r release/dasUberEnduroApplicationen/bin
rm -r release/dasUberEnduroApplicationen/resultMerge
zip -r release.zip release
rm -r release/

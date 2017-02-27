#!/bin/bash
cd dasUberEnduroApplicationen && ./gradlew build && cd ..
mkdir -p release
cp dasUberEnduroApplicationen/registration/build/libs/registration.jar release/.
cp dasUberEnduroApplicationen/resultMerge/build/libs/resultMerge.jar release/.
cp dasUberEnduroApplicationen/Manual.html release/.
cp README.md release/.
cp -r config release/.
cp -r AcT/ release/.
zip -r release.zip release
rm -r release

mkdir -p src_release
cp dasUberEnduroApplicationen/registration/build/libs/registration.jar src_release/.
cp dasUberEnduroApplicationen/resultMerge/build/libs/resultMerge.jar src_release/.
cp dasUberEnduroApplicationen/Manual.html src_release/.
cp README.md src_release/.
cp teknisk-dokumentation.pdf src_release/.
cp -r AcT/ src_release/.
cp -r dasUberEnduroApplicationen/ src_release/. 
cp runAcT.sh src_release/.
cp -r config src_release/.
rm -r src_release/dasUberEnduroApplicationen/registration
rm -r src_release/dasUberEnduroApplicationen/bin
rm -r src_release/dasUberEnduroApplicationen/resultMerge
zip -r src_release.zip src_release
rm -r src_release/

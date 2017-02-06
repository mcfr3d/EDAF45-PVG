#!/bin/bash
cd dasUberEnduroApplicationen && ./mkjars.sh && cd ..
mkdir -p dasUberEnduroApplikationen
cp dasUberEnduroApplicationen/jar/registration.jar dasUberEnduroApplikationen/.
cp dasUberEnduroApplicationen/jar/resultMerge.jar dasUberEnduroApplikationen/.
cp dasUberEnduroApplicationen/Manual.html dasUberEnduroApplikationen/.
zip -r dasUberEnduroApplikationen.zip dasUberEnduroApplikationen
rm -r dasUberEnduroApplikationen/

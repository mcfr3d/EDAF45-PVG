#!/bin/bash
home=$(pwd)
cd dasUberEnduroApplicationen && ./gradlew build && cd ..
for d in AcT/*/*/ ; do
	cd $d
	echo $d
	java -jar ${home}/dasUberEnduroApplicationen/resultMerge/build/libs/resultMerge.jar
	
	diff output.txt resultat.txt
	
	if diff output.txt resultat.txt >/dev/null; then
    	echo "Passed";
	else
    	echo "Failed";
	fi
	
	# rm -f output.txt
	# rm -f output_sorterad.txt
    echo "";
    echo "";
    cd $home
done

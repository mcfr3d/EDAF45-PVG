#!/bin/bash
home=$(pwd)
cd dasUberEnduroApplicationen && ./gradlew build && cd ..
for d in AcT/*/*/ ; do
	cd $d
	echo $d
	java -jar ${home}/dasUberEnduroApplicationen/resultMerge/build/libs/resultMerge.jar -c config.json
	
	diff output_unsorted.txt resultat.txt
	
	if diff output_unsorted.txt resultat.txt >/dev/null; then
    	echo "Unsorted passed";
	else
    	echo "Unsorted failed";
	fi
	
	if [ -f sortresultat.txt ]; then
		diff output.txt sortresultat.txt
	
		if diff output.txt sortresultat.txt >/dev/null; then
    		echo "Sorted passed";
		else
    		echo "Sorted failed";
		fi
	fi
	
	rm -f output.txt
	rm -f output_unsorted.txt
	rm -f output.html
    echo "";
    echo "";
    cd $home
done

#!/bin/bash
home=$(pwd)
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m'
cd dasUberEnduroApplicationen && ./gradlew build && cd ..
for d in AcT/*/*/ ; do
	cd $d
	echo $d
	java -jar ${home}/dasUberEnduroApplicationen/resultMerge/build/libs/resultMerge.jar -c config.json
	
	diff output_unsorted.txt resultat.txt
	
	if diff output_unsorted.txt resultat.txt >/dev/null; then
    	echo -e "${GREEN}Unsorted passed${NC}";
	else
    	echo -e "${RED}Unsorted failed${NC}";
	fi
	
	if [ -f sortresultat.txt ]; then
		echo "";
		diff output.txt sortresultat.txt
	
		if diff output.txt sortresultat.txt >/dev/null; then
    		echo -e "${GREEN}Sorted passed${NC}";
		else
    		echo -e "${RED}Sorted failed${NC}";
		fi
	fi
	
	rm -f output.txt
	rm -f output_unsorted.txt
	rm -f output.html
    echo "";
    echo "";
    cd $home
done

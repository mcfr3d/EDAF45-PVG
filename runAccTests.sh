#!/bin/bash
cd dasUberEnduroApplicationen && ./mkjars.sh && cd ..
for d in AcT/*/*/ ; do
	args="-o "${d}"output.txt"
	
	for f in ${d}namnfil* ; do
	    args="$args -n $f"
	done
	
	for f in ${d}starttider* ; do
	    args="$args -s $f"
	done
	
	for f in ${d}maltider* ; do
	    args="$args -f $f"
	done
	
	echo $d
	java -jar dasUberEnduroApplicationen/jar/resultMerge.jar $args
	
	diff ${d}output.txt ${d}resultat.txt

	if diff ${d}output.txt ${d}resultat.txt >/dev/null; then
    	echo "Passed";
	else
    	echo "Failed";
	fi
    echo "";
    echo "";
	
	
done

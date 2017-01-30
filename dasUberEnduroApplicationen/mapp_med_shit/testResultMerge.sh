#!/bin/bash
java -jar ../jar/resultMerge.jar -o resultat2.txt -s starttider.txt -f maltider.txt -n namnfil.txt -t maraton
diff resultat.txt resultat2.txt
rm resultat2.txt
java -jar ../jar/resultMerge.jar -o resultat3.txt -m 00.01.37 -f maltider.txt -n namnfil.txt -t varvlopp



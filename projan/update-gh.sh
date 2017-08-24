if [ "$TRAVIS_COMMIT_MESSAGE" != "Travis build pushed to Benchmarking gh-pages [skip ci]" ]; then
 echo -e "Starting to update Benchmarking gh-pages\n"
#moving files created in build into home
#moving scripts to be run into home
 # cp -R testTen $HOME
 # cp -R refBench $HOME
  cp -R testOrig $HOME
  cp -R testMine $HOME
  cp -R testIndex $HOME
  cp -R projan/normFromRef.py $HOME
  cp -R projan/timePlot.py $HOME
  cp -R projan/plotWithSize.py $HOME

  cd $HOME
  git config --global user.email "travis@travis-ci.org"
  git config --global user.name "Travis"
  #cloning the relevant repo
  git clone --quiet --branch=gh-pages https://${TOKEN}@github.com/Esmae/Benchmarking.git  Benchmarking> /dev/null
  
#normalising the benchmarking data just collected
#echo -e "calling normFromRef"
#python normFromRef.py


#attaching the time and date to the data files
 # today=$(date +%Y-%m-%d_%H-%M)
 # mv testTen testTen."$today"
 # cp Benchmarking/projan/normData/testTenNorm Benchmarking/projan/normData/testTenNorm."$today" 
   
  
#putting the files in local repo
#  cp -Rf testTen* Benchmarking/projan/data
#  cp -Rf refBench Benchmarking/projan
   cp -Rf testOrig Benchmarking/projan  
   cp -Rf testMine Benchmarking/projan
   cp -Rf testIndex Benchmarking/projan
  

  
  
  #calls the python script that creates the 'with time' plot
  #echo -e "calling timePlot"
  #python timePlot.py
  
 # plotting the throughput score against different sizes of dataset
 python plotWithSize.py

  #putting the new 'with time' plot in the repo (overwritting the latest one)
  #cp -Rf TimePlot.png Benchmarking/projan/figures
  #cp -Rf TimePlotTen.png Benchmarking/projan/figures
  
  cd Benchmarking
 #adding the new files and changing files so they are ready to commit 
  git add -f --ignore-removal .
#commiting the added changes
  git commit -m "Travis build pushed to Benchmarking gh-pages [skip ci]"
#pushes the changes the github on the gh-pages branch of Benchmarking
  git push -fq https://${TOKEN}@github.com/Esmae/Benchmarking.git gh-pages > /dev/null

  echo -e "Success? \n"
fi

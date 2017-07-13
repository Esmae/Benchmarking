if [ "$TRAVIS_COMMIT_MESSAGE" != "Travis build pushed to Benchmarking testing" ]; then
 echo -e "Starting to update Benchmarking testing\n"
#moving files created in build into home
#moving scripts to be run into home
  cp -R testTen $HOME
  cp -R refBench $HOME
  cp -R projan/normFromRef.py $HOME
  cp -R timePlot.py $HOME
  
  cd $HOME
  git config --global user.email "travis@travis-ci.org"
  git config --global user.name "Travis"
  #cloning the relevant repo
  git clone --quiet --branch=testing https://${TOKEN}@github.com/Esmae/Benchmarking.git  testing> /dev/null
  
#normalising the benchmarking data just collected
python normFromRef.py


#attaching the time and date to the data files
  today=$(date +%Y-%m-%d_%H-%M)
  mv testTen testTen."$today"
  mv testing/projan/normData/testTenNorm testing/projan/normData/testTenNorm."$today"  
  
#putting the files in local repo
  cp -Rf $HOME/test* testing/projan/data
  cp -Rf $HOME/refBench testing/projan
  

  
  #need to change the python script if change the name of the folder the clone is going into (currently it's called testing)
  #calls the python script that creates the 'with time' plot
  python timePlot.py

  #putting the new 'with time' plot in the repo (possibly overwritting the latest one)
  cp -Rf TimePlot.png testing/projan/figures
  cp -Rf TimePlotTen.png testing/projan/figures
 
  
  cd testing
 #adding the new files and changing files so they are ready to commit 
  git add -f --ignore-removal .
#commiting the added changes
  git commit -m "Travis build pushed to Benchmarking testing"
#pushes the changes the github on the testing branch of Benchmarking
  git push -fq https://${TOKEN}@github.com/Esmae/Benchmarking.git testing > /dev/null

  echo -e "Success? \n"
fi

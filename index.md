# Documentation


## What I've done
* Set up a maven project in java, set up GitHub
* Wrote some new iterators for the summation, addition and tensor Dot Product of datasets/tensors, before and after they had been transposed.  Used junit testing to test my code.
* Wrote benchmarks for my iterators and the original iterators in Eclipse January
* Set up Travis CI so that it builds my project, runs the junit tests and if passses those will then run the benchmarking
* Wrote some python scripts that are called after success in Travis  - store data and plot graphs, Travis then pushes back to GitHub

## Contents
[Maven Project](#Maven Project)
### GitHub
### Benchmarking
### Travis CI
### Pushing to GitHub
### JUnit
### Original Eclipse January Iterator
### Summing Datasets
### Summation Results
### Adding Datasets
### Addition Results
### Tensor Dot Product
### Tensor Dot Product Results
### Scripts
### Changing what is run on Travis

## Maven Project
### Build automation tool, normally for Java projects
* POM (Project Object Model): provides the configuration for the project
* Using the maven shade plugin in the POM (need to put in)
* Can change the mainClass tag so outputs the runnable class you want
* Dependencies used:
  1. Junit: 4.12
  2. Jmh-core: 1.19
  3. Jmh-generator-annprocess: 1.19
  4. Org.eclipse.january: 2.0.2
* *I remember having to delete something in the POM, as otherwise got lots of error messages in eclipse. I can’t remember what it was but if having this problem, compare with my POM on GitHub.*
* Maven build automatically runs the junit testing 

## GitHub
* Github deletes empty directories
* GitHub pages : Can have a repo for this (e.g. Esmae.github.io), can also have a page for each repo, with either the master or gh-pages branch set as the publishing source


## Benchmarking 
### Need to stop the JVM from performing optimizations you don’t want
* Don’t write void tests – defend against dead code elimination
* Can use blackholes instead of returning if would need to return more than one object. E.g. blackhole.consume(sum)
* Don’t use loops
* Overall class must be marked @State and be public
* Should close all other applications if possible when running benchmarks as they can take time away from the CPU. *Notice this on my machine, e.g. if I scroll down a web page I notice the throughput score of my benchmarking decrease.*
* If need to initialize some variables for the benchmarking run, need to put them in an internal static state class. Don’t use constants as the JVM can optimise this.
  
  @Param({“5”, “10”}) <br />
  public int size

  This will run the benchmarking with size=5, and then size = 10
* Try to make sure only the objects you need for that run are initialised in the internal class. *I had lots of different benchmarks and wouldn't run all of them at the same time – but realised I was still creating lots of large datasets that wouldn't be used – so split up my internal class into smaller classes.* 
* Can set up run variables, such as number of iterations, before method in code e.g. @Measurement(iterations = 5)  or on the command line, -i 10 
* Want to run different benchmarks on different forks (this is the default setting anyway) so different tests don’t mix their profilings together which would result in the score being dependent on the order in which the benchmarks are called
* Can change the value of the parameters on the command line as well, e.g. -p size=10,20,30
* Can have @Setup and @TearDown methods, which are called to setup up the state object before the benchmark and then clean up the state object after the benchmark (Time for this isn’t included)
* Can specify the benchmarking tests to run on the command line, e.g. by adding com.testjan.projan.MyBenchmarkMult.testRef. If there also existed a testRef2, this would also be run etc. *This is the main reason why a lot of my benchmarks are currently commented out – so they're not run.*
* Usually the benchmarking runs are roughly the same between different builds – though not always close enough so that MyIterator will always (or nearly always) be faster than the original, even with the normalisation I was doing. *I run a reference benchmark at the start of every build and normalise the data based on the result. I didn't do any testing as to whether the normalisation provided a significant improvement or not.*

## Travis CI 
### Configured by adding a file called .travis.yml to the root directory of the repository
* This YAML file tells travis what to do, e.g. the language, which branches to run on, the script commands themselves
* Can have before and after success sections (the after_success section is where I call my script which deals with the data obtained)
* Can run on a full VM (sudo-enabled) or on a container (faster boot time but can’t use sudo)
* When using the trusty environment on Travis, have to use install : “pip install -r requirements.txt” in the .travis.yml file – couldn’t get anything else to work. (Though other methods worked on precise)
* Can stop travis from building a commit by putting [skip ci] in commit message
* By default the gh-pages branch isn’t built in Travis unless it’s added to the safelist in .travis.yml

* Limits of Travis:
	1. 50 mins per build
	2. If >10 mins with no output -> aborts as assumed error
	3. Max console output: 4MB of log data

## Pushing to GitHub
* GitHub Personal API Tokens can be revoked & you can create lots of them
* Can put in environment varibles in Travis so Travis can push back to GitHub
* You can configure Travis such that it deploys files to github pages after a success *(I haven't used this)*
* You can re-run builds on Travis – useful for seeing if benchmarking is repeatable
* *In my project, after a success I pull down a copy of the repo and save the changes to that one rather than adding the changes to the version of the repo Travis already has when it's running the project. This is so I can re-run Travis builds later and still push back to github without issue, when the travis repo doesn't match the one on github.*


## Junit
* JUnit can test whether an exception has been thrown:

  @Test(expected = IndexOutOfBoundException.class)
* *I used  eclemma in eclipse to test for coverage*
* Can also use Expectedexception rule: allows you to verify your code has thrown a specified exception
  
  @Rule <br />
  public ExpectedException thrown = ExpectedException.none();
  
  Returns a rule that expects no exception to be thrown, so won’t affect existing tests

## Original Eclipse January Iterator
* Using a 3D tensor/dataset as an example,  with the axis labelled {0,1,2} and a shape of {3,3,3} the original iterators in eclipse january iterate through this dataset by incrementing axis 2 first, then axis 1 and finally axis 0. This is iterating in memory order. i.e. <br />
  {0,0,0},{0,0,1},{0,0,2},{0,1,0},{0,1,1},{0,1,2},{0,2,0},{0,2,1},{0,2,2},{1,0,0},{1,0,1},{1,0,2},{1,1,0},{1,1,1},{1,1,2},{1,2,0},{1,2,1},{1,2,2},{2,0,0},{2,0,1},{2,0,2},{2,1,0},{2,1,1},{2,1,2},{2,2,0},{2,2,1},{2,2,2}
* If a transposeView of the dataset was taken, e.g. axis 2 ↔ axis 1, so the axis are now 'labelled' {0,2,1} the original iterator will iterate through this dataset by incrementing axis 1 first, then axis 2 and finally axis 0. When a transposeView is taken, only the view of the dataset changes – the elements stored in memory won't change positions. Therefore when a dataset has been transposed, the original iterator will no longer iterate through the dataset in memory order. This means it will be slower – it is no longer exploiting the fact that accessing elements from the same cache line is cheap. 
* Strides (the distance in memory between adjacent elements) from a dataset are only created once a transposeView is taken, the datasets don't start with strides

## Summing Datasets
* I wrote a simple index iterator (not as a class), useful as a reference – can't run faster than this. Just increments the 1D memory index. My method sumMyIndexIterator, uses this iterator to add up all the elements in a dataset and returns the value.
* I also wrote a stride iterator (as a class), based on the stride iterator in Eclipse January. My iterator uses the strides of each axis to determine the order in which to iterate through the axis. The axis with the smallest stride is incremented along first and the axis with the largest stride is incremented last.
* I wrote this in 2 slightly different ways, so MyStrideIterator has 2 hasNext functions. HasNext runs faster than hasNext2.

## Summation Results:
* Benchmarked these 3 iterators (original, simple index, and the fast MyIterator), for 4D datasets from size of 10x10x10x10 to 60x60x60x60.
* For untransposed datasets, Original iterator runs ~3x faster than MyIterator across the whole range measured. This is because in my Original Iterator method I've used an Index Iterator which is an abstract class, and the actual Iterator type created in memory depends upon the dataset. If the dataset doesn't have any strides it creates a simple Contiguous Iterator. The hasNext function in this iterator just simply increments the 1D memory index, so the run speed of the Original Iterator is similar to my simple Index Iterator.
![alt text](https://github.com/Esmae/Benchmarking/blob/gh-pages/projan/indeximages/sumindex.png "Summing a rank 4 tensor")
![alt text](https://github.com/Esmae/Benchmarking/blob/gh-pages/projan/indeximages/sumuntrans.png "Summing rank 4 untransposed tensors")
* When the Original Iterator is run so that the position is updated as well (by using a.getIterator(true)), myIterator and Original Iterator run at similar speeds.
![alt text](https://github.com/Esmae/Benchmarking/blob/gh-pages/projan/indeximages/sumpos.png "Summing rank 4 untransposed tensors with position updated")

![alt text](https://github.com/Esmae/Benchmarking/blob/gh-pages/projan/indeximages/sumtrans.png "Summing rank 4 transposed tensors")





## Adding Datasets
* Looking at adding one dataset to another dataset and producing a third dataset (the datasets must have the same shape)
* The 'Original' Eclipse January Iterator method I benchmarked uses PositionIterator
* My first addition method (myAddPartOne) just orders based on the strides of the first dataset, whilst my second addition method (myAdd2PartOne) compares the strides of the two datasets (multiplies the corresponding strides together to create a strideMult array and orders the strides based on this). Both use MyPositionIterator, which iterates through the axis based on the axes order given to the constructor. 

## Addition Results
* For cases where the tensors have the same shape but not the same strides, myAdd2PartOne runs faster than myAddPartOne. *At least in the cases that I've tested.* 
![alt text](https://github.com/Esmae/Benchmarking/blob/gh-pages/projan/indeximages/adduntrans.png "Adding untranposed tensors")
![alt text](https://github.com/Esmae/Benchmarking/blob/gh-pages/projan/indeximages/addtrans.png "Adding tranposed tensors")


## Tensor Dot Product
* Also looked at the tensorDotProduct, which is the same as numpy.tensordot (would recommend reading that documentation to find out more about what this operation is.) 
* I wrote two variations on Eclipse January's tensor dot product, the start and end points of my code are labelled in both  (though I've also added comments in other places)
* Both of my variations work on the same principle, they order the axes in which to iterate over, based on the strides. **They do not change the iterator used later (SliceIterator) – which is a possible area for improvement.** 
* The first variation multiplies the strides of the first dataset together and then does the same for the second dataset. The axes are then ordered based on the strides of whichever dataset had the larger multStride value. 
* The second variation multiplies the strides of the first dataset with the corresponding strides of the second dataset to create a multStride array. The axes are then ordered based on the values in multStride. 

## Tensor Dot Product Results
* Most of my testing was done with identical tensors (though not necessarily the same objects), however I did some testing with different tensors and it looks as though the second variation performs better than the first in these scenarios. 
* In both cases, an improvement on the original tensor dot product was achieved 

## Scripts
* There are 3 python scripts in this project. The .travis.yml file calls update-gh.sh which pulls down a recent copy of the repo, calls the python files and does some data copying and renaming before pushing back to github.
* NormFromRef:  contains 3 functions, one which reads in the reference data for the normalisation, one which normalises data and one that appends data to a file.
* TimePlot:  contatins 1 function, reads in the dataWithTime and plots a graph of relative throughput score against relative commit number for all time and for the last 10 values. 
* PlotWithSize:  contains 1 function, which plots data with throughput score against axes length (of dataset). There is also a resonable chunk of floating code that calls this function and labels the axes etc

## Changing what is run on Travis 
### Might want/need to do just some or all of these
1. Need to change the java commands in .travis.yml file so you're running the benchmarks you want
2. In update-gh.sh, change which data files and scripts are copied into $HOME
3. Change the files you want to append the time and date to in update-gh.sh– if any
4. Change the files that you're copying over to the local repository in update-gh.sh
5. Change the python scripts you're calling if need be int update-gh.sh
6. Change what happens to the files that these python scripts create in update-gh.sh
7. In the python scripts themselves, change the filenames – both the ones you start with and the ones you create


    

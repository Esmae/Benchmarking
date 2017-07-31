"""
Normalising the benchmarking data with respect to the reference benchmark run
"""

import csv

#reading in the actual normalisation
def readRef(filename):
    with (open(filename,"rb")) as ref:
        reader = csv.reader(ref)
        i = 0
        for row in reader:
            if i != 0:
                #skipping the first row as this is the header
                #the file should only contain 1 other row
                return row[4]
            i = i + 1
 
       
#opening the file which holds the refData with time
def appendData(dataToAdd,filename):
    with open(filename,"a") as f:
        with open(dataToAdd) as g:
            i=int(1)
            for line in g:
                #skipping writing the first line as this contains the header
                if i!=1:
                    f.write(line)
                i=i+1

#normalising the data
def normaliseFile(unNormFile,normFile,normalisation):
    with open(unNormFile, "rb") as noNorm:
        with open(normFile, "wb") as norm:
            reader = csv.reader(noNorm)
            writer = csv.writer(norm)
            j=0
            for row in reader:
                if j!=0:
                    #normalising the score
                    row[4] = str(float(row[4])/float(normalisation))
                    #normalising the error associated with the score
                    row[5] = str(float(row[5])/float(normalisation))
                writer.writerow(row)
                j=j+1

normalise = readRef("refBench")
appendData("Benchmarking/projan/refBench","Benchmarking/projan/refBenchWithTime")
normaliseFile("testTen","Benchmarking/projan/normData/testTenNorm",normalise)
appendData("Benchmarking/projan/normData/testTenNorm","Benchmarking/projan/dataWithTime")

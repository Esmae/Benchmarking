import matplotlib as mpl
mpl.use("Agg")
from matplotlib import pyplot as mplot
import csv
import sys

def plot_size(sizeData,theLabel):
    #declaring the lists
    listScoreLog = []
    listPar = []
    with open(sizeData) as csvfile:
        reader = csv.DictReader(csvfile)
        for row in reader:
            #storing the values in the Score and Parameters columns in lists
           # x = float(row['Score'])
           # print(type(x))
           listScoreLog.append(row['Score'])
           listPar.append(row['Param: S'])
    #plotting the graph
    mplot.plot(listPar,listScoreLog,label = theLabel)
  
mplot.xscale('log')
mplot.yscale('log')
i = int(1)

#first argument should be a list of filenames
#second argument should be a list of labels
#third argument should be the title of the graph

file_list = sys.argv[1]
label_list = sys.argv[2]
title = sys.argv[3]

while(i<len(file_list)):
    plot_size(file_list[i], label_list[i])
    i = i + 1

#plot_size("testIndex",'Index Iterator')
#adding another line to the same graph
#plot_size("testMine",'My Iterator')
#adding another line to the same graph
#plot_size("testOrig",'Original Iterator')

#labelling the graph 
mplot.xlabel('Axes Length')
mplot.ylabel('Throughput Score/ op/s')
mplot.title('Score with Size - Summing rank 4 tensors (untransposed)')
mplot.legend()
#saving the plot as a svg file
mplot.savefig("Benchmarking/projan/testingfigure1.svg",format='svg')

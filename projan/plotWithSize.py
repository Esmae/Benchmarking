import numpy as np
import matplotlib as mpl
mpl.use("Agg")
from matplotlib import pyplot as mplot
import csv

def plot_size(sizeData, savefigure):
    #declaring the lists
    listScoreLog = []
    listPar = []
    with open(sizeData) as csvfile:
        reader = csv.DictReader(csvfile)
        for row in reader:
            #storing the values in the Score and Parameters columns in lists
            listScoreLog.append(np.log(row['Score']))
            listPar.append(row['Param: S'])
    #plotting the graph
    mplot.plot(listPar,listScore,'k')
    #labelling the graph 
    mplot.xlabel('Axes Length')
    mplot.ylabel('ln(Throughput Score/ op/s)')
    mplot.title('Score with Size - rank 4 tensors')
    
    #saving the plot as a png file
    mplot.savefig(savefigure,format='eps')
    
plot_size("Benchmarking/projan/testIndex","Benchmarking/projan/plotSize.eps")

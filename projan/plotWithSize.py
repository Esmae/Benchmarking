import matplotlib as mpl
mpl.use("Agg")
from matplotlib import pyplot as mplot
import csv

def plot_size(sizeData, colour, theLabel):
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
    mplot.plot(listPar,listScoreLog,colour, label = theLabel)
  
mplot.xscale('log')
mplot.yscale('log')
plot_size("testIndex", "r", 'Index Iterator')
plot_size("testAddMine","k",'My Iterator')
plot_size("testAddOrig", "g", 'Original Iterator')
#labelling the graph 
mplot.xlabel('Axes Length')
mplot.ylabel('Throughput Score/ op/s')
mplot.title('Score with Size - Summing rank 4 tensors (untransposed)')
mplot.legend()
#saving the plot as a svg file
mplot.savefig("Benchmarking/projan/plotSizeSumUntransposeTesting.svg",format='svg')

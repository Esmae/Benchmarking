import numpy as np
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
           listScoreLog.append(np.log(float(row['Score'])))
           listPar.append(row['Param: S'])
    #plotting the graph
    mplot.plot(listPar,listScoreLog,colour, label = theLabel)
  
#mplot.figure(0)   
#plot_size("testIndex","k",'Simple Index')
#plot_size("testOrig","r", 'Original Iterator')
#plot_size("testMine", "g", 'My Iterator')
#labelling the graph 
#mplot.xlabel('Axes Length')
#mplot.ylabel('ln(Throughput Score/ op/s)')
#mplot.title('Score with Size - rank 4 tensors')
#mplot.legend()
#saving the plot as a png file
#mplot.savefig("plotSize.eps",format='eps')

#plotting for tranposed tensors
#mplot.figure(1)
plot_size("testIndexTranspose","k",'Simple Index')
plot_size("testOrigTranspose","r", 'Original Iterator')
plot_size("testMineTranspose", "g", 'My Iterator')
#labelling the graph 
mplot.xlabel('Axes Length')
mplot.ylabel('ln(Throughput Score/ op/s)')
mplot.title('Score with Size - rank 4 transposed tensors')
mplot.legend()
#saving the plot as a png file
mplot.savefig("plotSizeTransposed.eps",format='eps')

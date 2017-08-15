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
  
#mplot.figure(0)   
mplot.xscale('log')
mplot.yscale('log')
#plot_size("testIndexTranspose", "r", 'Index Iterator')
plot_size("testAddTransposeMine","k",'My Iterator')
plot_size("testAddTransposeOrig", "g", 'Original Iterator')
#labelling the graph 
mplot.xlabel('Axes Length')
mplot.ylabel('Throughput Score/ op/s')
mplot.title('Score with Size - Adding rank 4 tensors (transposed)')
mplot.legend()
#saving the plot as a png file
mplot.savefig("plotSizeAddTransposeTesting.emf",format='emf')
#mplot.savefig("plotSizeSumUntransposeTestingIndex.png", format='png')

#plotting for tranposed tensors
#mplot.figure(1)
#plot_size("testAddOrig","k",'Original Iterator')
#plot_size("testAddMine","r", 'My Iterator')
#plot_size("testMineTranspose", "g", 'My Iterator')
#labelling the graph 
#mplot.xlabel('Axes Length')
#mplot.ylabel('ln(Throughput Score/ op/s)')
#mplot.title('Score with Size - rank 4 transposed tensors')
#mplot.legend()
#saving the plot as a png file
#mplot.savefig("plotSizeTransposed.eps",format='eps')

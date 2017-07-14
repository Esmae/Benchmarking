#!/usr/bin/env python2.7
# -*- coding: utf-8 -*-

import numpy as np
import matplotlib as mpl
mpl.use("Agg")
from matplotlib import pyplot as mplot
import csv 

#declaring the lists
listScore = []
listCommit = []

#opening the file which holds the data with time
f = open("gh-pages/projan/dataWithTime","a")
with open("gh-pages/projan/normData/testTenNorm") as g:
    i = int(1)
    for line in g:
        #skipping writing the first line as this contains the header
        if i!=1:
            f.write(line)
        i = i+1

f.close()
g.close()


with open("gh-pages/projan/dataWithTime") as csvfile:
    #reading in the data against commits/time file
    reader = csv.DictReader(csvfile)
    i = int(0)
    for row in reader: 
        #storing the values in the Score columns in a list
        listScore.append(row['Score'])
        listCommit.append(i)
        i=i+1
#converting the lists to arrays so can plot
arrScore = np.asarray(listScore)
arrCommit = np.asarray(listCommit)
arrScore = arrScore.astype(np.float)
#plotting the graph

mplot.plot(arrCommit,arrScore,'k')
#labeling the graph
mplot.xlabel('Relative commit number (highest is most recent)')
mplot.ylabel('Relative Throughput Score/ op/s')
mplot.title('Score over time')

#saving the plot as a png file
mplot.savefig("TimePlot.png")

#plotting a figure with only the most recent 10 results in
listLen = len(listScore)
if listLen <= 10:
    mplot.savefig("TimePlotTen.png")
else:
    listTenScore = []
    mplot.clf()
    for i in range(0,10):
        listTenScore.append(listScore[listLen-10+i])
    arrTenScore = np.asarray(listTenScore)
    arrTenCommit = np.asarray([0,1,2,3,4,5,6,7,8,9])
    mplot.plot(arrTenCommit,arrTenScore,'k')
    #labeling the graph
    mplot.xlabel('Relative commit number (highest is most recent)')
    mplot.ylabel('Relative Throughput Score/ op/s')
    mplot.title('Score over last 10')
    #saving the plot as a png file
mplot.savefig("TimePlotTen.png")

#!/usr/bin/env python2.7
# -*- coding: utf-8 -*-

import numpy as np
import matplotlib as mpl
mpl.use("Agg")
from matplotlib import pyplot as mplot
import csv 


def PlotData(filename,savefigure,savefigure10):
    #declaring the lists
    listScore = []
    with open(filename) as csvfile:
        #reading in the data against commits/time file
        reader = csv.DictReader(csvfile)
        i = int(0)
        for row in reader: 
            #storing the values in the Score columns in a list
            listScore.append(row['Score'])
            i=i+1
    #plotting the graph
    arrCommit = np.arange(i)
    mplot.plot(arrCommit,listScore,'k')
    #labeling the graph
    mplot.xlabel('Relative commit number (highest is most recent)')
    mplot.ylabel('Relative Throughput Score/ op/s')
    mplot.title('Score over time')
    
    #saving the plot as a png file
    mplot.savefig(savefigure)
    
    #plotting a figure with only the most recent 10 results in
    listLen = len(listScore)
    if listLen <= 10:
        mplot.savefig(savefigure10)
    else:
    #putting the last 10 values in listTenScore
        listTenScore = listScore[-10::1]
        mplot.clf()
        arrTenCommit = np.arange(10)
        mplot.plot(arrTenCommit,listTenScore,'k')
        #labeling the graph
        mplot.xlabel('Relative commit number (highest is most recent)')
        mplot.ylabel('Relative Throughput Score/ op/s')
        mplot.title('Score over last 10')
        #saving the plot as a png file
    mplot.savefig(savefigure10)

    
    
PlotData("gh-pages/projan/dataWithTime","TimePlot.png","TimePlotTen.png")

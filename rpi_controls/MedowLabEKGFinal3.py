#%%
import pyqtgraph as pg
import pyqtgraph.opengl as gl
import pyqtgraph.examples
import numpy as np
import os, sys, re
import shutil
import glob
import wfdb
from PyQt5.QtGui import * 
from wfdb import processing
from pyqtgraph.Qt import QtGui, QtCore
from pyqtgraph.dockarea import * 

#https://archive.physionet.org/physiobank/database/html/mitdbdir/records.htm#100



diagnoses = {
    "N" : "Normal Sinus Rythym",
    "L" : "Left Bundle Branch Block (LBB Block)",
    "R" : "Right Bundle Branch Block (RBB Block",
    "A" : "Premature Atrial",
    "a" : "Aberrated Premature Atrial",
    "J" : "Nodal Premature", 
    "S" : "Supraventricular Premature Beat (SVT)", 
    "V" : "Premature Ventricular Contraction (PVC)", 
    "F" : "Ventricular and Normal Beat Fusion", 
    "(VFL" : "Ventricular Flutter",
    "e" : "Atrial Escape Beat",
    "j" : "Nodal/Junctional Escape Beat", 
    "E" : "Ventricular Escape Beat", 
    "f" : "Fusion of Paced and Normal Beat", 
    "x" : "Non-Conducted P-wave ('Blocked APB'?)", 
    "/" : "Paced Beat (?)",
    "(P" : "Paced Rythym (?)",
    "(AB" : "Atrial Bigeminy", 
    "(AFIB" : "Atrial Fibrillation", 
    "(AFL" : "Atrial Flutter", 
    "(B" : "Ventricular Bigeminy", 
    "(BII" : "2nd Degree Heart Block", 
    "(IVR" : "Idioventricular Rythym",
    "(NOD" : "Nodal A-V Junction", 
    "(SBR" : "Sinus Bradycardia", 
    "(T" : "Ventricular Trigeminy", 
    "(VT" : "Ventricular Tachycardia"
}


names = glob.glob("dataset/ECG/mitdb/*.atr")
indices = []
for name in names: 
    indices.append(re.findall(r'\b\d+\b',name)[0])
print (indices)
x = np.sort(np.array(indices).astype(np.int))
currSignal = x[0]
print(x)
record = wfdb.rdrecord(str.format('dataset/ECG/mitdb/' + str(x[0])), sampfrom=0, sampto=10000, channels=[0])
annotation = wfdb.rdann(str.format('dataset/ECG/mitdb/' + str(x[0])), 'atr')
annotationSym = max(set(annotation.symbol), key = annotation.symbol.count)
print(annotation.symbol)
print(len(annotation.symbol))
print(annotationSym)

print(diagnoses.get(annotationSym))

# Use the gqrs algorithm to detect qrs locations in the first channel
print("after record")
qrs_inds = processing.gqrs_detect(sig=record.p_signal[:,0], fs=record.fs)
image = np.array(record.p_signal).transpose()
print(image)
print(len(image[0]))
print (image[0])
print("after image")

#SEND SIDE

#Import Libraries
import time
import spidev
import bluetooth

# configure client BT 
bd_addr = "B8:27:EB:F2:B3:7B"
port = 1
sock=bluetooth.BluetoothSocket( bluetooth.RFCOMM )
sock.connect((bd_addr, port))

#https://pypi.org/project/spidev/
spi = spidev.SpiDev()

spi.open(0, 0) # Opens the SPI Slave State Port for communication
spi.mode = 0b11
spi.bits_per_word = 8
spi.max_speed_hz = 50000


for data in image:
    resp = spi.xfer2([0x10, 0x10]) #read 2 bytes
    first_byte = '{0:08b}'.format(resp[0]) # format in binary
    second_byte = '{0:08b}'.format(resp[1])# format in binary
    extracted_data = first_byte[1:] + second_byte[:3] # remove first bit from first byte and keep first 3 bits from
    extracted_data = data
    extracted_data = str(extracted_data)
    sock.send(extracted_data)
    time.sleep(.001)

spi.close()
sock.close()

#RECEIVE SIDE


server_sock=bluetooth.BluetoothSocket( bluetooth.RFCOMM )

port = 1
server_sock.bind(("",port))
server_sock.listen(1)

client_sock,address = server_sock.accept()
print("Accepted connection from ",address)

data = client_sock.recv(1024)
print("received [%s]" % data)

client_sock.close()
server_sock.close()
#!/bin/bash

export HT_OR=0

for i in `seq 1 10000`;
do
		mosquitto_pub -h localhost -t /DynoCloud/Queue -m hello		
	    #sleep 0.25

 done
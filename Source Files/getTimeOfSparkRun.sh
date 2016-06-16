#!/bin/bash
START=($(date +%s%N)/1000000)
# command for spark program
date
./spark-submit /root/spark_sort.py
date
END=($(date +%s%N)/1000000)
DIFF=$(( $END - $START ))
echo "It took $DIFF milliseconds"


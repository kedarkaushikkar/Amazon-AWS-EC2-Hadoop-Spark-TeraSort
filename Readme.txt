CS553 : CLOUD COMPUTING
PROGRAMMING ASSIGNMENT 2 - Terasort on Hadoop/Spark

Author :
1. Kedar Kaushikkar(CWID : A20355218) - kkaushik@hawk.iit.edu

The Assignment Directory contains following documents and folders:

1. Source Code of the program for Terasort: (Source Code)
2. Performance Evaluation Report - prog2_report.pdf
3. Output Files

1. Source Code folder
-This folder contains all the executables and source files to run the Shared Memory , Hadoop-Terasort and Spark Sort Programs.
- The file listing is as follows,
  - JARS_AND_JAVA :  Folder that contains java files and their respective jar files to run shared memory sort on 1 ,2 , 4 and 8 Threads
  - Configuration Files : Folder contains the hadoop configuration files that need to be set up for 1 node and 16 nodes.
  - Spark_Sort.py - Python Code for Spark Sort
  - getTimeOfSparkRun.sh - Bash script that will runn the spark sort python file and output the time it took to execute the experiment.
  - SharedMemorySort.txt - Text File containing the source code of shared memory sort java program.

******Steps to run the Shared Memory*********

- Create an amazon instance with java runtime envoirnment by following the steps mentioned in performance report.

- Add the Jar files present in JARS_AND_JAVA folder for all threads using ssh the instance DNS.

- Generate input file using gensort following the steps in the report

- Run the below command to execute the JAR files

java - jar FILENAME INPUT_FILE_PATH OUTPUT_FILE_NAME

- Evaluate the output file using Valsort.

********************************************


******Steps to run the Hadoop Terasort*********

- Create a virtual hadoop cluster following the steps mentioned in performance report.

- Copy the configuration files stored in the Source Code folder to /hadoop/etc/bin folder

- Authorize the Keys (.pem file)

- Start the Hadoop Cluster (make sure that the data node and name nodes are running)

- Copy the Map_Reduce_Terasort Jar file at the home directory of our instance.

- Once the cluster is started ,run the hadoop tera sort using the command below,

Creating Input and Output Directory.
Go to Hadoop/bin
./hdfs dfs -mkdir /user/hduser/input

Gensort to create input file

RUN the program at hadoop/sbin
./hadoop jar /home/ubuntu/Map_Reduce_TeraSort.jar Map_Reduce /user/hduser/input /user/hduser/output

Check Output Files at hadoop/bin
./bin/hdfs dfs -ls /user/hduser/output

Copy Output File to Local
./hadoop fs -get /user/hduser/output/part-00000 /home/ubuntu/64/

Valsort to evaluate the output

********************************************

******Steps to run the Spark Terasort*********

- Launch the Spark virtual cluster following the steps mentioned in evaluation report.

- Copy the bash script getTimeOfSparkRun.sh to the spark/bin directory

-Copy the Spark_Sort.py file at the root directory

- Generate input file using gensort following the steps in the report

- Run the below command to execute the bash script.

  chmod 777 getTimeOfSparkRun.sh

  ./getTimeOfSparkRun.sh

- Evaluate the output file using Valsort.

********************************************

2. Performance_Evaluation_Report
- This file prog2_report.pdf contains all the steps to set up virtual clusters, terasort results and data charts to analyze the results.

3. Output Logs.
- This folder contains the output logs generated during running those sorts on amazon d2.xlarge and c3.large instance.
- The results being large in size , hence only the first and last 10 lines of the output have been stored in the text files.

from pyspark import SparkContext

sc=SparkContext(appName="Sort")
file_data=sc.textFile("/usr/sparkusr/input/input.txt")
key_value_data=file_data.map(lambda x: (str(x[0:10]), str(x[10:100])))
sorted_data=key_value_data.sortByKey(True)
output_data=sorted_data.map(lambda x :(''.join(str(a) for a in x)))
output_data.saveAsTextFile("/usr/sparkusr/output/")

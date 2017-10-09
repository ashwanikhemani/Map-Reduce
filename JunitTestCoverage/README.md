1 . Insturmentation of code using jacoco plugin for gradle and sbt :

individual coverage reports were generated by running the specific test case and then using jacocoTestReport command to generate 
coverage report . 

gradle  -Dtest.single=TestMillisDurationField test
gradle  -Dtest.single=TestOffsetDateTimeField test
gradle  -Dtest.single=TestOffsetDateTimeField_2 test
gradle  -Dtest.single=TestOffsetDateTimeField_3 test
gradle  -Dtest.single=TestPreciseDateTimeField test
gradle  -Dtest.single=TestPreciseDurationDateTimeField test
gradle  -Dtest.single=TestPreciseDurationField test
gradle  -Dtest.single=TestBaseDateTimeField test
gradle  -Dtest.single=TestBaseDateTimeField_1 test
gradle  -Dtest.single=TestFieldUtils_2 test
gradle  -Dtest.single=TestFieldUtils test

The above reports can also be generated using emma plugin with maven :

mvn emma:mvn emma:emma -Dmaven.emma.report.xml=true

2. The JunitTestCoverage application is used to create a program using the map/reduce model for parallelizing software testing .

JunitTestCoverage : This class results in mapping the line number of different classes with the junits they are covered by 
JunitTestCoverageSorted : This class give the results sorted line of codes by the number of junit that cover them in descending order .

3. The above application was then tested on cloudera platform with hadoop installation using below steps :

Preparation of the input : the coverage reports of the test were kept in separte folder for the different tests and then 
passed as input the Map-Reducer application 

make the input directory on the hadoop file system 

transfer the file to the input directory 

hdfs dfs -put source destination

and run the hadoop application on the cloudera hadoop platform 

hadoop jar jar_name.jar main_class \input 

4. The application was then tested on Amazon EMR platform to test the functionality .


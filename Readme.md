1. Compile and create JAR
`bash
javac -classpath `${HADOOP_HOME}/share/hadoop/common/*`:`${HADOOP_HOME}/share/hadoop/mapreduce/*` -d classes WeatherAverage.java
jar -cvf weatheravg.jar -C classes/ .
`
3. Run the program
`bash
hadoop jar weatheravg.jar WeatherAverage input output
`
5. View output
`bash
cat output/part-r-00000
`
Expected:
`nginx
DewPoint    19.233334
Temperature 26.7
WindSpeed   12.333333
`

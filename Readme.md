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


### Setup Hadoop in Linux
1. Install Java
`sudo apt update
sudo apt install openjdk-11-jdk -y
java -version
`

2. Download Hadoop
`wget https://downloads.apache.org/hadoop/common/hadoop-3.3.6/hadoop-3.3.6.tar.gz
tar -xzf hadoop-3.3.6.tar.gz
sudo mv hadoop-3.3.6 /usr/local/hadoop
`

3. Configure Basic Env Variables
Edit ~/.bashrc and add:
`export HADOOP_HOME=/usr/local/hadoop
export HADOOP_INSTALL=$HADOOP_HOME
export HADOOP_COMMON_LIB_NATIVE_DIR=$HADOOP_HOME/lib/native
export PATH=$PATH:$HADOOP_HOME/sbin:$HADOOP_HOME/bin
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
`
Then run:
`bash
source ~/.bashrc
`


4. Set Hadoop's Java Path:

Edit:
`nano $HADOOP_HOME/etc/hadoop/hadoop-env.sh`
Find this line:
`# export JAVA_HOME=`

Replace with:
`export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64`


5. Run in Standalone Mode:
```
hadoop fs -mkdir /input
hadoop fs -put input.txt /input

hadoop jar your-jar.jar YourMainClass /input /output
```


6. Test the setup:
   ```
   cd $HADOOP_HOME
    mkdir input
    echo "hello world hadoop hadoop" > input/file.txt
  
    hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-3.3.6.jar wordcount input output
    cat output/part-r-00000

   ```

7. Expected Output:
   ```
   hadoop  2
    hello   1
    world   1

   ```

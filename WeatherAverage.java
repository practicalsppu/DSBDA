import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WeatherAverage {

    public static class WeatherMapper extends Mapper<LongWritable, Text, Text, FloatWritable> {
        private Text metric = new Text();

        public void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            String line = value.toString().trim();
            if (line.startsWith("Date")) return; // Skip header

            String[] parts = line.split("\\s+");
            if (parts.length >= 4) {
                context.write(new Text("Temperature"), new FloatWritable(Float.parseFloat(parts[1])));
                context.write(new Text("DewPoint"), new FloatWritable(Float.parseFloat(parts[2])));
                context.write(new Text("WindSpeed"), new FloatWritable(Float.parseFloat(parts[3])));
            }
        }
    }

    public static class AverageReducer extends Reducer<Text, FloatWritable, Text, FloatWritable> {
        public void reduce(Text key, Iterable<FloatWritable> values, Context context)
                throws IOException, InterruptedException {
            float sum = 0;
            int count = 0;
            for (FloatWritable val : values) {
                sum += val.get();
                count++;
            }
            con

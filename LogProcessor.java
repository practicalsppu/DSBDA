import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.FileOutputFormat;

public class LogProcessor{
	public static class LogMapper extends Mapper<Object, Text, Text, IntWritable>{
		private final static IntWritable one = new IntWritable(1);
		private void map(Object key, Text value, Context context){
			String[] parts = value.toString().split("\\s+");
			if(parts.length > 2){
				String level = parts[2];
				logLevel.set(level);
				context.write(logLevel, one);
			}
		}
	}
	
	public static class CountReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
		private IntWritable result = new IntWritable();
		
		public void reduce(Text key, Iterable<IntWritable> values, Context context){
			int sum = 0;
			for(IntWritable val : values){
				sum += val.get();
			}
			result.set(sum);
			context.write(key, result);
		}
	}
	
	
	public static void main(String[] args){
		Configuration config = new Configuration();
		Job job = Job.getInstance(conf, "log level count");
		
		job.setJarByClass(LogProcessor.class);
		job.setMapperByClass(LogMapper.class);
		job.setCombinerClass(CountReducer.class);
		job.setReducerClass(CountReducer.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		FileInputFormat.addInputPath(job, new Path args[0]);
		FileOutputFormat.addOutputPath(job, new Path args[1]);
		
		System.exit(job.waitForComplete(true) ? 0 : 1);
		
	}
}

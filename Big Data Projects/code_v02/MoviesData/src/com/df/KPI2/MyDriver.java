package com.df.KPI2_a;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
public class MyDriver
{
	public static void main(String[] args) throws Exception {
		Path firstPath = new Path(args[0]);
		Path sencondPath = new Path(args[1]);
		Path outputPath = new Path(args[2]);
		Path outputPath1 = new Path(args[3]);
		   Configuration conf = new Configuration();
		    Job job = Job.getInstance(conf, "Most Rated Movies");
		//set Driver class

		job.setJarByClass(MyDriver.class);

	        //output format for mapper
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);

	        //output format for reducer
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

	        //use MultipleOutputs and specify different Record class and Input formats
		MultipleInputs.addInputPath(job, firstPath, TextInputFormat.class, MovieMapper.class);
		MultipleInputs.addInputPath(job, sencondPath, TextInputFormat.class, RatingMapper.class);
		//set Reducer class
		job.setReducerClass(MyReducer.class);

		FileOutputFormat.setOutputPath(job, outputPath);

		job.waitForCompletion(true);


		    Job job1 = Job.getInstance(conf, "Most Rated Movies2");
			//set Driver class
			job1.setJarByClass(MyDriver.class);
			//set Mapper class
			job1.setMapperClass(SortingMapper.class);
			//set Reducer class
			job1.setReducerClass(SortingReducer.class);

		        //output format for mapper
			job1.setMapOutputKeyClass(DoubleWritable.class);
			job1.setMapOutputValueClass(Text.class);

		        //output format for reducer
			job1.setOutputKeyClass(DoubleWritable.class);
			job1.setOutputValueClass(Text.class);

			 FileInputFormat.addInputPath(job1, outputPath);
			FileOutputFormat.setOutputPath(job1, outputPath1);

			job1.waitForCompletion(true);

	}

	
}


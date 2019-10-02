package com.df.KPI3_a;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.filecache.DistributedCache;
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
		Path thirdPath = new Path(args[3]);

		Path outputPath1 = new Path(args[4]);
		Path outputPath2 = new Path(args[5]);
//		Path outputPath3 = new Path(args[6]);

		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "Avg Rating");
		DistributedCache.addFileToClassPath(new Path("/home/dataflair/occupationCodeName.txt"), conf);
//		conf.set("mapreduce.output.textoutputformat.separator", ",");
//		job.set("mapreduce.textoutputformat.separator", ",");
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
		job.setReducerClass(MovieRatingReducer.class);

		FileOutputFormat.setOutputPath(job, outputPath);

		job.waitForCompletion(true);



	    Job job1 = Job.getInstance(conf, "Avg Rating1");
		//set Driver class
		job1.setJarByClass(MyDriver.class);
		//set Reducer class
		job1.setReducerClass(UserMovieRatingReducer.class);

		//output format for mapper
		job1.setMapOutputKeyClass(Text.class);
		job1.setMapOutputValueClass(Text.class);

        //output format for reducer
		job1.setOutputKeyClass(Text.class);
		job1.setOutputValueClass(IntWritable.class);

        //use MultipleOutputs and specify different Record class and Input formats
		MultipleInputs.addInputPath(job1, thirdPath, TextInputFormat.class, UserMapper.class);
		MultipleInputs.addInputPath(job1, outputPath, TextInputFormat.class, MovieRatingMapper.class);
		FileOutputFormat.setOutputPath(job1, outputPath1);

		job1.waitForCompletion(true);
//		conf2.set("mapred.textoutputformat.separator", ",");
		
	    Job job2 = Job.getInstance(conf, "Avg Rating2");
		//set Driver class
		job2.setJarByClass(MyDriver.class);
		//set Mapper class
		job2.setMapperClass(AvgRatingMapper.class);
		//set Reducer class
		job2.setReducerClass(AvgRatingReducer.class);

		//output format for mapper
		job2.setMapOutputKeyClass(Text.class);
		job2.setMapOutputValueClass(IntWritable.class);

        //output format for reducer
		job2.setOutputKeyClass(Text.class);
		job2.setOutputValueClass(Text.class);

		 FileInputFormat.addInputPath(job2, outputPath1);
		FileOutputFormat.setOutputPath(job2, outputPath2);

		job2.waitForCompletion(true);
		


	}

	
}


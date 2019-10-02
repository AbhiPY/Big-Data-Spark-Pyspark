package com.df.KPI3_a;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.StringTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
public class AvgRatingReducer
extends Reducer<Text,IntWritable,Text,Text> {
//private IntWritable result = new IntWritable();

	public void reduce(Text key, Iterable<IntWritable> values, Context context
	                ) throws IOException, InterruptedException {
		
	// find out the average of the rating
		// when key is concatenation of genres occupation and age
	   		int count=0;
	   		int sum=0;
	   		double avg=0.0;
	   		for (IntWritable val :values)
	   		{
	   			sum=sum+val.get();
	   			count++;
	   		}
	   		avg=(double)sum/(double)count;
	   		// output of the third job is concatenation of genres occupation and age and average rating 
	   		context.write(new Text(key), new Text(""+avg));
	//result.set(sum);
	
	}

}


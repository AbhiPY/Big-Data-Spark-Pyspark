package com.df.KPI1_a;

import java.io.IOException;
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
public class MyReducer
extends Reducer<Text,Text,Text,Text> {

	public void reduce(Text key, Iterable<Text> values, Context context
			) throws IOException, InterruptedException {
		//initialize movie_name and view count
		String movieName="movieName";
		int count=0;
		
		for (Text val :values)
		{
			String currValue = val.toString();
			// check condition for null value
			if(currValue != null){
					// if mapper output from Rating mapper then increase the counter
				if(currValue.equalsIgnoreCase("1"))
				{
					count++;
				}
				else //else mapper output from movieMapper then assign moviename
				{
					movieName=currValue;
				}
			}
		}
		// here our first job output is movie_name and total no of view
		context.write(new Text(movieName), new Text("" + count));

	}

}


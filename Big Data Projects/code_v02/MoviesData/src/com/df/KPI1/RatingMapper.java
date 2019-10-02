package com.df.KPI1_a;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class RatingMapper extends Mapper<LongWritable, Text, Text, Text>{
	
	public void map(LongWritable key, Text value, Context context
			) throws IOException, InterruptedException {
		// convert text into String
		String line = value.toString();
		// Split it with ::
		String splitarray[] = line.split("::");
		//select movie_id
		String movie_id = splitarray[1].trim();
		//Intermediate output will be movie_id and count 1
		context.write(new Text(movie_id), new Text("1"));
	}
}

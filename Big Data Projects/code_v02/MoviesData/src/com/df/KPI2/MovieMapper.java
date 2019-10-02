 package com.df.KPI2_a;

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
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
public class MovieMapper
extends Mapper<Object, Text, Text, Text>{

private Text word = new Text();
private String movie_name  ="";
private String movie_id  ="";
private final static IntWritable one = new IntWritable(1);
public void map(Object key, Text value, Context context
             ) throws IOException, InterruptedException {
	// convert text into String
        String line = value.toString();
		// Split it with ::
        String splitarray[] = line.split("::");
		// select movid_id
        movie_id = splitarray[0].trim();
		// select movie_name
        movie_name = splitarray[1].trim();
		// Intermediate output will be movie_id and movie_name and we pass the 0 code to distinguish between two mappers
        context.write(new Text(movie_id), new Text(movie_name+":0"));

}
}

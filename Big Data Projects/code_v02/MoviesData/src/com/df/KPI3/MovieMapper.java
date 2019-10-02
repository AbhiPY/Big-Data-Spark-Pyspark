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
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
public class MovieMapper
extends Mapper<Object, Text, Text, Text>{

private String movie_id  ="";
public void map(Object key, Text value, Context context
             ) throws IOException, InterruptedException {
	// convert text into String
	String line = value.toString();
	// Split it with ::
    String splitarray[] = line.split("::");
	// select movid_id
    movie_id = splitarray[0].trim();
    //select genres
    String genres = splitarray[2].trim();
 
    	// intermediate output will be movie_id and it's genres
    	context.write(new Text( movie_id ), new Text(genres));
    

}
}

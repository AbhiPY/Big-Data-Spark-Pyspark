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
public class MovieRatingMapper
extends Mapper<Object, Text, Text, Text>{


private String movie_id  ="";
private final static IntWritable one = new IntWritable(1);
public void map(Object key, Text value, Context context
             ) throws IOException, InterruptedException {
	//we will use output of the 1st job
	String line = value.toString();
	// split value with tab
    String splitarray[] = line.split("\\t");
    // get user_id
    String user_id = splitarray[0].trim();
    // get concatenation of movie_id genres and rating in MultipleValue variable
    String MultipleValues = splitarray[1].trim();
    // output of the movieratingmapper will be user_id and MultipleValue variable
 context.write(new Text( user_id ), new Text(MultipleValues));


}
}

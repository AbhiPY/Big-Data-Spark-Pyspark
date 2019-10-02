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
public class SortingMapper
extends Mapper<Object, Text, DoubleWritable, Text>{
	 private String movie_name  ="";
	 
public void map(Object key, Text value, Context context
             ) throws IOException, InterruptedException {
String line = value.toString();
//output of the first job movie name and view split it with tab

        String splitarray[] = line.split("\t");
		//get the movie_name

        movie_name = splitarray[0].trim();
        Double count=0.0;
		// get the average rating

        NumberFormat _format = NumberFormat.getInstance(Locale.US);
        Number number = null;
        try {
            number = _format.parse(splitarray[1].trim());
            count = Double.parseDouble(number.toString());
        } catch (ParseException e) {
        	
        }   
		// multiply the view with -1 because we need to sort the value in Descending order 
        count=count*-1;
		// output of the 2nd mapper will be average rating and movie_name

 context.write(new DoubleWritable(count), new Text(movie_name));

}
}

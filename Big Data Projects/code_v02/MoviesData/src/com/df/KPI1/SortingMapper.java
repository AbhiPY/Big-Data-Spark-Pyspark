package com.df.KPI1_a;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SortingMapper extends Mapper<Object, Text, IntWritable, Text>{
	
	public void map(Object key, Text value, Context context
			) throws IOException, InterruptedException {
		
		String line = value.toString();
		//output of the first job movie name and view split it with tab
		String splitarray[] = line.split("\t");
		//get the movie_name
		String movie_name = splitarray[0].trim();
		// get the view
		Integer count=0;
		NumberFormat format = NumberFormat.getInstance(Locale.US);
		Number number = null;
		try {
			number = format.parse(splitarray[1].trim());
			count = Integer.parseInt(number.toString());
			
		} catch (ParseException e) {
			e.printStackTrace();
		}   
		// multiply the view with -1 because we need to sort the value in Descending order 
		count = count*-1;
		// output of the 2nd mapper wil be no of view and movie_name
		context.write(new IntWritable(count), new Text(movie_name));

	}
}

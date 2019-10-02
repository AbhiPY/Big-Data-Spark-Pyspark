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
public class RatingMapper
extends Mapper<Object, Text, Text, Text>{

    private int rating=0;

private String movie_name  ="";
private String movie_id  ="";
private final static IntWritable one = new IntWritable(1);
public void map(Object key, Text value, Context context
             ) throws IOException, InterruptedException {
	// convert text into String
        String line = value.toString();
		// Split it with ::

        String splitarray[] = line.split("::");
        Integer rating=0;
		//select movie_id
        movie_id = splitarray[1].trim();
        // select rating
        NumberFormat _format = NumberFormat.getInstance(Locale.US);
        Number number = null;
        try {
            number = _format.parse(splitarray[2].trim());
            rating = Integer.parseInt(number.toString());
//            System.err.println("Double Value is :"+satisfaction_level);
        } catch (ParseException e) {

        }
		//Intermediate output will be movie_id and rating and we pass 1 code to distinguish between mapper
     
 context.write(new Text(movie_id), new Text(rating+":1"));

//while (itr.hasMoreTokens()) {
// word.set(itr.nextToken());
// context.write(word, one);
//}
}
}

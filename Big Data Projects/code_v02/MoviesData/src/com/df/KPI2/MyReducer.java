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
import org.apache.hadoop.mapreduce.Reducer.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
public class MyReducer
extends Reducer<Text,Text,Text,Text> {
//private IntWritable result = new IntWritable();

public void reduce(Text key, Iterable<Text> values,
                Context context
                ) throws IOException, InterruptedException {
	 String movieName="movieName";
	 Integer rating=0;
     Integer total_rating=0;
     Double avg_rating=0.0;
   	int count=0;
     for (Text val :values)
     {
          String currValue = val.toString();
         String valueSplitted[] = currValue.split(":");
            // if code is 1  then we will get rating 
             if(valueSplitted[1].equals("1"))
             {
            	 NumberFormat _format = NumberFormat.getInstance(Locale.US);
                 Number number = null;
                 try {
                     number = _format.parse(valueSplitted[0].trim());
                     rating = Integer.parseInt(number.toString());
                 } catch (ParseException e) {

                 }
                 // sum all rating 
                 total_rating=total_rating+rating;
               count++;
             }
             else 
             {// if code 0 then we will get movie name assign this value to movie_name variable
              movieName=valueSplitted[0];
             }
        }
     // if user count is greater than 400 then only count average rating
        if(count >=40){
        	// calculate the average rating
        	avg_rating=(double)total_rating/(double)count;
        	// out put of the first job will be movie name and average rating
     context.write(new Text(movieName), new Text(""+avg_rating));

         }

}

}


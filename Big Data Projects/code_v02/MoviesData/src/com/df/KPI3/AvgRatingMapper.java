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
public class AvgRatingMapper
extends Mapper<Object, Text, Text, IntWritable>{


private final static IntWritable one = new IntWritable(1);
public void map(Object key, Text value, Context context
             ) throws IOException, InterruptedException {
	// convert text into string
	  String line = value.toString();
	  // split the output of the 2nfd job
      String splitarray[] = line.split("\\t");
      // get the key which is concatenation of genres occupation and age store it in MultipleValue
      String MultipleValue  = splitarray[0].trim();
      
      // get the value which is rating
	Integer rating=0;
        NumberFormat _format = NumberFormat.getInstance(Locale.US);
        Number number = null;
        try {
            number = _format.parse(splitarray[1].trim());
            rating = Integer.parseInt(number.toString());
//            System.err.println("Double Value is :"+satisfaction_level);
        } catch (ParseException e) {

        }
		
     // output of the third mapper will be MultipleValue and rating
 context.write(new Text(MultipleValue), new IntWritable(rating));

//while (itr.hasMoreTokens()) {
// word.set(itr.nextToken());
// context.write(word, one);
//}
}
}

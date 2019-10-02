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
public class RatingMapper
extends Mapper<Object, Text, Text, Text>{

private String movie_id  ="";
private String user_id  ="";
public void map(Object key, Text value, Context context
             ) throws IOException, InterruptedException {
	  String line = value.toString();

      String splitarray[] = line.split("::");
      user_id = splitarray[0].trim();
      movie_id = splitarray[1].trim();
	Integer rating=0;
        NumberFormat _format = NumberFormat.getInstance(Locale.US);
        Number number = null;
        try {
            number = _format.parse(splitarray[2].trim());
            rating = Integer.parseInt(number.toString());
//            System.err.println("Double Value is :"+satisfaction_level);
        } catch (ParseException e) {

        }


     
 context.write(new Text(movie_id), new Text(user_id+":"+rating));

//while (itr.hasMoreTokens()) {
// word.set(itr.nextToken());
// context.write(word, one);
//}
}
}

package com.df.KPI3_a;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
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
public class UserMovieRatingReducer
extends Reducer<Text,Text,Text,IntWritable> {
//private IntWritable result = new IntWritable();

public void reduce(Text key, Iterable<Text> values,
                Context context
                ) throws IOException, InterruptedException {
	 String genres="genres";
	 String movie_id="movie_id";
	 String age="age";
	 String occupation="occupation";
	 Integer rating=0;

	 List<Text> cache = new ArrayList<Text>();
     // we group data on user_id (key)
   	 for(Text val:values)
   	 {
	   	 String currValue = val.toString();
	   	 // check null condition
	     if(currValue != null){
	    	 // if value contains :: then its our usernmapper output
	    	 if(currValue.contains("::"))
	         {
	    		 // get age
		       	  String splitarray[] = currValue.split("::");
		       	  age = splitarray[0].trim();
		       	  // get occupation
		 		  occupation= splitarray[1].trim();
	
	         }
	    	 else
	    	 {// otherwise cache data
	    		 cache.add(val);
			 }
	     
	     }
   	 }
   	// iterate cache data
	     for (Text val1 :cache)
	     {
	          String currValue1 = val1.toString();
	          // check null condition
	         if(currValue1 != null){
	        	 //if value contains : then it is  movieRating mapper
	        	 if(currValue1.contains(":"))
	             {
	        		//split value with :
	        		String splitarray[] = currValue1.split(":");
	       			// get the genres
	       			genres= splitarray[1].trim();
	       			// get rating
	       	        NumberFormat _format = NumberFormat.getInstance(Locale.US);
	       	        Number number = null;
	       	        try {
	       	            number = _format.parse(splitarray[2].trim());
	       	            rating = Integer.parseInt(number.toString());
	       	        } catch (ParseException e) {

	       	        }
	       	        // split genres with |
		       	    String splitarray1[] = genres.split("\\|");
		       	    int size = splitarray1.length;
		       	    int i=0;
		       	    for(i=0;i<size;i++){
		       	    //	intermediate output will be movie_id and it's genres
		       	    	if(!genres.equalsIgnoreCase(""))
			  	          context.write(new Text(occupation+","+age+","+splitarray1[i]), new IntWritable(rating));
		       	    
		       	    }
	       	        // ignore data when genre is not available

	               }
	         
	
	         }
	     }
   	
//result.set(sum);

}

}


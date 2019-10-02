package com.df.KPI3_a;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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
public class MovieRatingReducer
extends Reducer<Text,Text,Text,Text> {
	//private IntWritable result = new IntWritable();

	public void reduce(Text key, Iterable<Text> values,
			Context context
			) throws IOException, InterruptedException {
		String genres="genres";
		//	 String user_id="";
		//		Integer rating=0;
		List<Text> cache = new ArrayList<Text>();
//		Iterator<Text> iteratorToList =values.iterator();
//		ListIterator<Text> lit = cache.listIterator();
//		
		
		for(Text val:values)
		{
			
			String currValue = val.toString().trim();

			if(currValue != null)
			{	// if value does not contain : then it is our movie mapper
				if(!currValue.contains(":"))
				{// get the geners value
					genres=currValue;
				}
				else
				{// else cache the data
					   cache.add(val);
				}
			}
		}

		//iterate our cached data  
		for (Text val1 :cache)
		{

			String currValue1 = val1.toString();
			int rating=0;
			String user_id = "";
			if(currValue1 != null){
				// if value contains : then we will get user-id and rating
				if(currValue1.contains(":"))
				{
					String splitarray[] = currValue1.split(":");
					// get user_id
					user_id = splitarray[0].trim();
					// get rating
					NumberFormat _format = NumberFormat.getInstance(Locale.US);
					Number number = null;
					try {
						number = _format.parse(splitarray[1].trim());
						rating = Integer.parseInt(number.toString());
					} catch (ParseException e) {

					}
					// output of the 1st job is user_id and value will be concatenation of movie_id genres and rating

					context.write(new Text(user_id), new Text(key+":"+genres+":"+rating));

				}
				

			}
		}

	}

}


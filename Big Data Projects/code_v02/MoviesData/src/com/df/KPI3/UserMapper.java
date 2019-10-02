 package com.df.KPI3_a;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
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
import org.apache.hadoop.mapreduce.filecache.DistributedCache;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
public class UserMapper
extends Mapper<Object, Text, Text, Text>{

private String Age  ="";
private String occupation  ="";
private String user_id  ="";
Path[] cachefiles = new Path[0]; //To store the path of lookup files


public void map(Object key, Text value, Context context
             ) throws IOException, InterruptedException {
	  String line = value.toString();
		// Split it with ::
	  String splitarray[] = line.split("::");
	  // get user_id
	  user_id = splitarray[0].trim();
	  // get occupation
	  occupation = splitarray[3].trim();
	  Configuration conf = context.getConfiguration();
	  Path[] cachefiles = new Path[0];

	  try 
	  {

		  cachefiles = DistributedCache.getLocalCacheFiles(conf);
		  BufferedReader reader = new BufferedReader(new FileReader(cachefiles[0].toString())); 

		  String line1;

		  while ((line1 = reader.readLine())!= null) 
		  {
			  String codename[] = line1.split(":");
			  String code=codename[0].trim();
			  String name=codename[1].trim();
			  if(occupation.equalsIgnoreCase(code))
			  {
				  occupation= name;
				  break;
			  }

		  }

	  }
		 catch (IOException e) 
		 {
		 e.printStackTrace();
		 }

	// convert text into String
	  
      
	Integer age=0;
	// get age
        NumberFormat _format = NumberFormat.getInstance(Locale.US);
        Number number = null;
        try {
            number = _format.parse(splitarray[2].trim());
            age = Integer.parseInt(number.toString());
        } catch (ParseException e) {

        }
        // group the age
		if(age >= 18 && age <35) 
			Age="18-35";
		else if(age >= 35 && age <50)
			Age="35-50";
		else if(age >= 50 )
			Age="50+";
		else 
			Age="below 18";
     // output of the usermapper will be user_id and concatenation of age and occupation 
 context.write(new Text(user_id), new Text(Age+"::"+occupation));


}
}

package com.df.KPI2_a;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Reducer;

public class SortingReducer
extends Reducer<DoubleWritable,Text,DoubleWritable,Text> {
	private static int count;
@Override
protected void setup(Context context) throws IOException, InterruptedException {
    Configuration conf = context.getConfiguration();
	 		conf.setInt("count", 0);
	}
//private IntWritable result = new IntWritable();
	public void reduce(DoubleWritable key, Iterable<Text> values,
            Context context
            ) throws IOException, InterruptedException {
		// now we will get the sorted output

				for (Text val : values) {


					DoubleWritable key1 = new DoubleWritable(-1*key.get());
					// we only want Top 20 value for this we need to set counter
					if(count < 20)
						context.write(key1, new Text (val));
					count++;
	}

}

}


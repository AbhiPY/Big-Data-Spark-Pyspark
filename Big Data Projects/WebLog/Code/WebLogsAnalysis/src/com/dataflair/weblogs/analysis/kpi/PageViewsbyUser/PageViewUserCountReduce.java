package com.dataflair.weblogs.analysis.kpi.PageViewsbyUser;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public  final class PageViewUserCountReduce extends Reducer<Text, IntWritable, Text, IntWritable>
{
	public final void reduce(final Text key, final Iterable<IntWritable> values, final Context context)	throws IOException, InterruptedException
	{
		int  sum = 0;
		for (final IntWritable val : values)
		{
			sum += val.get();
		}
		context.write(key, new IntWritable(sum));
	}
}

package com.dataflair.weblogs.analysis.kpi.PageViewsbyUser;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public  final class PageViewUserCountMapper extends Mapper<LongWritable, Text, Text, IntWritable>
{
	public final void map(final LongWritable key, final Text value, final Context context) throws IOException, InterruptedException
	{
		String record = value.toString();
		String[] tokens = record.trim().split("\t");
		if (tokens.length == 15)
		{
			String ipAddress = tokens[0];
			context.write(new Text(ipAddress), new IntWritable(1));
		}
	}
}
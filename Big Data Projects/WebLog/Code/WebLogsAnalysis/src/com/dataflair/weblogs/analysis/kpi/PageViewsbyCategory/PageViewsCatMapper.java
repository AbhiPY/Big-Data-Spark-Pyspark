package com.dataflair.weblogs.analysis.kpi.PageViewsbyCategory;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public  final class PageViewsCatMapper extends Mapper<LongWritable, Text, Text, IntWritable>
{
	public final void map(final LongWritable key, final Text value, final Context context) throws IOException, InterruptedException
	{
		final String line = value.toString();
		final String[] data = line.trim().split("\t");
		if (data.length == 15)
		{
			final String category1 = data[5];
			context.write(new Text (category1), new IntWritable(1));
		}
	}
}
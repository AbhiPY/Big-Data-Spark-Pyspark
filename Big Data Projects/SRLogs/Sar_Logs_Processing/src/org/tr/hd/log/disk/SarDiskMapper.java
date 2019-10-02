package org.tr.hd.log.disk;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SarDiskMapper extends Mapper<LongWritable, Text, Text, FloatWritable>
{
	private FloatWritable percentValue = new FloatWritable();
	private Text mapOutKey = new Text();

	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
	{
		
//		hdtr001 240613,20:50                        19G  2.9G   16G  "16%" /
		String valueTokens[] = value.toString().split(" ");

		String hostName = valueTokens[0];
		String date = "";
		String timestamp = "";
		for (int cnt = 1; cnt < valueTokens.length; cnt++)
		{
			if (valueTokens[cnt].length() > 0)
			{
				timestamp = valueTokens[cnt];
				break;
			}
		}
		try
		{
			date = timestamp.split(",")[0];
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		String diskPercent = valueTokens[valueTokens.length - 2];
		
		mapOutKey.set(hostName + "\t" + date);
		percentValue.set(Float.parseFloat(diskPercent.substring(0, diskPercent.length() - 1)));
		context.write(mapOutKey, percentValue);
	}
}

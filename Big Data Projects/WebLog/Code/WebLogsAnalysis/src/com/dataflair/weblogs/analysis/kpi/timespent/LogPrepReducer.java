package com.dataflair.weblogs.analysis.kpi.timespent;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import com.dataflair.weblogs.analysis.util.ParamUtil;


public class LogPrepReducer extends Reducer<Text, LogOutputWritable, NullWritable, Text> 
{
	public static final String DATE_FORMAT = "dd/MMM/yyyy:HH:mm:ss Z";
	
	MultipleOutputs<NullWritable, Text> mos = null;

	public void setup(Context context)
    {
         mos = new MultipleOutputs<NullWritable, Text>(context);
    }
	
	protected void cleanup(Context context) throws IOException, InterruptedException
	{
        mos.close();
	}
	
	public void reduce(Text key, Iterable<LogOutputWritable> values, Context context) throws IOException, InterruptedException 
	{
		List<LogDateBean> recList = new ArrayList<LogDateBean>();
		for (LogOutputWritable low : values)
		{
			String value = low.getMrValue().toString();
			boolean flag = low.getMrFlag().get();
			if (flag)
			{
				String tokens [] = value.toString().split("\\t");
				String date = tokens[3];
				recList.add(new LogDateBean(getFormattedDate(date), value.toString()));
			}
			else
			{
				mos.write("BadRecords", NullWritable.get(), new Text(value));
			}
			
		}
		
		Collections.sort(recList);
		
		for (int index = 0; index < recList.size(); index++)
		{
			LogDateBean logDateBean = recList.get(index);
			Date date = logDateBean.getDate();
			StringBuffer record = new StringBuffer(logDateBean.getRecord());
			
			int duration = 0;
			if (index + 1 < recList.size())
			{
				Date nextDate = recList.get(index + 1).getDate();
				duration = (int)((nextDate.getTime() - date.getTime()) / 1000L);
			}
			record.append(ParamUtil.DELIMITER_TAB).append(duration);
			logDateBean.setRecord(record.toString());
		}
		
		for (LogDateBean logDateBean : recList)
		{
//			context.write(NullWritable.get(), new Text(logDateBean.getRecord()));
			mos.write("ParsedRecords", NullWritable.get(), new Text(logDateBean.getRecord()));
		}	
		
	}
	public static Date getFormattedDate(String date)
	{
		Date dt = null;
		DateFormat format = new SimpleDateFormat(DATE_FORMAT);
		try 
		{
			dt = format.parse(date);
		}
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		return dt;
	}
 
}
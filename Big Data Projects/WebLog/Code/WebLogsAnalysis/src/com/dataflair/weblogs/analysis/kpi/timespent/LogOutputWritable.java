package com.dataflair.weblogs.analysis.kpi.timespent;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;


public class LogOutputWritable implements WritableComparable<LogOutputWritable>
{

	private Text mrValue;
	private BooleanWritable mrFlag;

	public LogOutputWritable()
	{
		set(new Text(), new BooleanWritable());
	}

	public LogOutputWritable(String mrValue, boolean mrFlag)
	{
		set(new Text(mrValue), new BooleanWritable(mrFlag));
	}

	public LogOutputWritable(Text mrValue, BooleanWritable mrFlag)
	{
		set(mrValue, mrFlag);
	}

	public void set(Text mrValue, BooleanWritable mrFlag)
	{
		this.mrValue = mrValue;
		this.mrFlag = mrFlag;
	}

	public Text getMrValue()
	{
		return mrValue;
	}

	public BooleanWritable getMrFlag()
	{
		return mrFlag;
	}

	@Override
	public void write(DataOutput out) throws IOException
	{
		mrValue.write(out);
		mrFlag.write(out);
	}

	@Override
	public void readFields(DataInput in) throws IOException
	{
		mrValue.readFields(in);
		mrFlag.readFields(in);
	}

	@Override
	public int hashCode()
	{
		return mrValue.hashCode() * 163 + mrFlag.hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		if (o instanceof LogOutputWritable)
		{
			LogOutputWritable low = (LogOutputWritable) o;
			return mrValue.equals(low.mrValue) && mrFlag.equals(low.mrFlag);
		}
		return false;
	}

	@Override
	public String toString()
	{
		return mrValue + "\t" + mrFlag;
	}

	@Override
	public int compareTo(LogOutputWritable tow)
	{
		int cmp = mrValue.compareTo(tow.mrValue);
		if (cmp != 0)
		{
			return cmp;
		}
		return mrFlag.compareTo(tow.mrFlag);
	}
}

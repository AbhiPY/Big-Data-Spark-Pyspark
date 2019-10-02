package com.dataflair.weblogs.analysis.kpi.timespent;


import java.util.Date;

public class LogDateBean implements Comparable<LogDateBean>
{
	private Date date;
	private String record;

	public LogDateBean(Date date, String rec)
	{
		this.date = date;
		this.record = rec;
	}
	
	public Date getDate() 
	{
		return date;
	}
	
	public void setDate(Date date)
	{
		this.date = date;
	}
	
	public String getRecord()
	{
		return record;
	}
	
	public void setRecord(String record) 
	{
		this.record = record;
	}
	
	@Override
	public int compareTo(LogDateBean logDateBean) 
	{
		return getDate().compareTo(logDateBean.getDate());
	}
}

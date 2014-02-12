package com.developcenter.broker.mysql.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

	public static String getSysTime()
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// new Date()为获取当前系统时间
		String time = df.format(new Date());
		return time;
	}
}

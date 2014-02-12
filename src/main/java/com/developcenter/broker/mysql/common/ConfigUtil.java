package com.developcenter.broker.mysql.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.jmx.snmp.Timestamp;




public class ConfigUtil {
	
	private  static  final  Log  logger=LogFactory.getLog(ConfigUtil.class) ;   
	
	private  static  Properties  property=new Properties();
	
	private static ConfigUtil instance=new ConfigUtil();
	
	private ConfigUtil()
	{
		loadProperties();
	}
	
	public static ConfigUtil getInstance()
	{
		return instance;
	}
	private  void  loadProperties(){
		

			try {
				ClassLoader loader=Thread.currentThread().getContextClassLoader();
				InputStream is=loader.getResourceAsStream("mysql.properties");
				property.load(is);
			} catch (IOException e) {
			
			}
		

	}
	
	public  String  getMysqlServerIp(){
		
		return property.getProperty("ip");
	}
	
	public  String  getMysqlServerPort(){
		
		return property.getProperty("port");
	}
	
	public String getServiceNodes()
	{
		return property.getProperty("servicenodes");
	}
	
	public static void main(String[] args) {
		
		System.out.println(new Timestamp());
	}
}

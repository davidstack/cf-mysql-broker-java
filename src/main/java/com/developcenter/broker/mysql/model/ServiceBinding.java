package com.developcenter.broker.mysql.model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.springframework.core.env.Environment;

import com.developcenter.broker.mysql.common.ConfigUtil;

public class ServiceBinding {

	  private Credentials credentials;
	  
	  private String serviceInstanceId;
	  
	  private String appId;
	  
	  private String jdbcUrlTemplate="jdbc:mysql://username:password@host:port/database";
	  
	  private String mysqlUriTemplate="mysql://username:password@host:port/database?reconnect=true";
	  public ServiceBinding(String bindingId, ServiceInstance instance, Environment environment) {
	    MessageDigest digest = null;
	    credentials =new Credentials();
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    digest.update(bindingId.getBytes());
	    String userName=new BigInteger(1, digest.digest()).toString(16).replaceAll("/[^a-zA-Z0-9]+/", "''").substring(0, 16);
	    String password=UUID.randomUUID().toString();
	    String host=ConfigUtil.getInstance().getMysqlServerIp();
	    String port=ConfigUtil.getInstance().getMysqlServerPort();
	    String database=instance.getName();
	    this.credentials.setUsername(userName);
	    this.credentials.setPassword(password);
	    this.credentials.setHost(host);
	    this.credentials.setPort(Integer.valueOf(port));
	    this.credentials.setJdbc_url(getJdbcUrl(userName, password, host, port, database));
	    this.credentials.setUri(getUri(userName, password, host, port, database));
	    this.credentials.setDatabase(database);
	  }
	
	  public ServiceBinding()
	  {
		  
	  }

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public String getServiceInstanceId() {
		return serviceInstanceId;
	}

	public void setServiceInstanceId(String serviceInstanceId) {
		this.serviceInstanceId = serviceInstanceId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	 private String getUri(String userName,String password,String host,String port,String database)
	 { 
		 return mysqlUriTemplate.replaceFirst("username", userName)
					.replaceFirst("password", password).replaceFirst("host", host)
					.replaceFirst("port", port).replaceFirst("database", database);
	 }

	private String getJdbcUrl(String userName, String password, String host,
			String port, String database) {
		return jdbcUrlTemplate.replaceFirst("username", userName)
				.replaceFirst("password", password).replaceFirst("host", host)
				.replaceFirst("port", port).replaceFirst("database", database);
	}
}

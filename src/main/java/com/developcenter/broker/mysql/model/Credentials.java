package com.developcenter.broker.mysql.model;

public class Credentials {
	private String host;
	private String database;
	private String username;
	private String password;
	private String uri;
	private String jdbc_url;
	private int port;
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getJdbc_url() {
		return jdbc_url;
	}
	public void setJdbc_url(String jdbc_url) {
		this.jdbc_url = jdbc_url;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
}

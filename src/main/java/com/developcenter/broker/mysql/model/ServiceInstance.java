package com.developcenter.broker.mysql.model;

public class ServiceInstance {

	static String SERVICE_NAME_PREFIX = "cf_";

	/**
	 * 保存服务实例ID，以及ServiceName
	 * @param id
	 */
	public ServiceInstance(String id) {
		this.id = id.replaceAll("-", "_");
		this.setName(SERVICE_NAME_PREFIX+this.id);
	}
	
	public ServiceInstance() {
		
	}

	private String name;
	
	private String id;

	private String planId;

	private String orgId;

	private String spaceId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getSpaceId() {
		return spaceId;
	}

	public void setSpaceId(String spaceId) {
		this.spaceId = spaceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

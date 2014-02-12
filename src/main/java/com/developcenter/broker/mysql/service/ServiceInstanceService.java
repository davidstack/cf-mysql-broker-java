package com.developcenter.broker.mysql.service;


import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developcenter.broker.mysql.common.Util;
import com.developcenter.broker.mysql.model.ServiceInstance;
import com.sun.jmx.snmp.Timestamp;

@Service
public class ServiceInstanceService {
  
  
  @Autowired 
  JdbcTemplate jdbcTemplate;

 public ServiceInstance constructServiceInstance(String id) {
    return new ServiceInstance(id);
  }

  public boolean isExists(String instanceId) {
	  String sql = "SELECT * FROM serviceinstance WHERE id = ?";
	 Object[] params = new Object[] {instanceId};
	 int[] types = new int[] {Types.VARCHAR};

	 List<Map<String,Object>> databases= jdbcTemplate.queryForList(sql, params, types);
     return databases.size() > 0;
  }

  public int getNumberOfExistingInstances() {
    return 1;
  }

 
  /**
   * 创建Service 实例
   * @param instanceId
   */
  public void create(String instanceId) {
	  
	  //TODO 设置回滚机制
	  ServiceInstance instance=new ServiceInstance(instanceId);
	  String sql = "INSERT INTO serviceinstance (id,name,date) VALUES(?,?,?)";
	  Object[] params = new Object[] {instance.getId(),instance.getName(),Util.getSysTime()};
	  int[] types = new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR};
	 // jdbcTemplate.
	  int updateResult=jdbcTemplate.update(sql, params, types);

	  
	  jdbcTemplate.execute("CREATE DATABASE "+instance.getName()+" DEFAULT CHARSET utf8 COLLATE utf8_general_ci;");

	  //jdbcTemplate.execute("FLUSH PRIVILEGES");
  }

  /**
   * 删除Service 实例
   * @param instanceId
   */
	public void delete(String instanceId) {

		 //TODO 设置回滚机制
		ServiceInstance instance = new ServiceInstance(instanceId);
		String sql = "DELETE FROM serviceinstance where id=?";
		Object[] params = new Object[] { instanceId };
		int[] types = new int[] { Types.VARCHAR };
		jdbcTemplate.update(sql, params, types);
		jdbcTemplate.execute("DROP DATABASE IF EXISTS " + instance.getName());
	}
}

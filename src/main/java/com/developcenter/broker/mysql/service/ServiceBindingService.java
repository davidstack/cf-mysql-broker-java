package com.developcenter.broker.mysql.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developcenter.broker.mysql.common.Util;
import com.developcenter.broker.mysql.model.ServiceBinding;
import com.developcenter.broker.mysql.model.ServiceInstance;
import com.sun.jmx.snmp.Timestamp;

/**
 * Author: Sridharan Kuppa sridharan.kuppa@gmail.com
 * Date: 12/12/13
 */
@Service
@Transactional
public class ServiceBindingService implements EnvironmentAware {
  @Autowired 
  JdbcTemplate jdbcTemplate;
 
  @Autowired 
  ServiceInstanceService instanceService;

  Environment environment;
  private Log logger = LogFactory.getLog(ServiceBindingService.class);
  @Override
public void setEnvironment(Environment environment) {
    this.environment = environment;
  }

  public ServiceBinding constructServiceBinding(String bindingId, String instanceId) {
   
	  ServiceInstance instance = instanceService.constructServiceInstance(instanceId);
		if (!instanceService.isExists(instance.getId())) {
			logger.error("ServiceBindingService service instanceId does not exist.instanceId="+instanceId);
			return null;
		}
		
		ServiceBinding binding = new ServiceBinding(bindingId, instance, environment);
		return binding;
  }

  public String findById(String bindingId, String instanceId) {
	   
	  ServiceInstance tempServiceInstance=new ServiceInstance(instanceId);
		if (!instanceService.isExists(tempServiceInstance.getId())) {
			logger.error("ServiceBindingService service instanceId does not exist.instanceId="
					+ instanceId);
			return null;
		}
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		digest.update(bindingId.getBytes());
		String userName = new BigInteger(1, digest.digest()).toString(16)
				.replaceAll("/[^a-zA-Z0-9]+/", "''").substring(0, 16);

		try {
			jdbcTemplate.execute("SHOW GRANTS FOR "+userName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String sql = "SELECT * FROM credentials WHERE username = ?";
		Object[] params = new Object[] { userName };
		int[] types = new int[] { Types.VARCHAR };

		List<Map<String, Object>> databases = jdbcTemplate.queryForList(sql,
				params, types);
		if (databases.size() == 0) {
			logger.error("ServiceBindingService service bindingId does not exist.bindingId="
					+ bindingId+"  userName="+userName);
			return null;
		}
		return userName;
  }
  
	/**
	 * 
	 * @param binding
	 * @throws Exception 
	 */
	public void create(ServiceBinding binding) throws Exception {
		
		try {

			String userName = binding.getCredentials().getUsername();

			String password = binding.getCredentials().getPassword();

			String dataBase = binding.getCredentials().getDatabase();

			String host = binding.getCredentials().getHost();

			String port = String.valueOf(binding.getCredentials().getPort());

			String url = binding.getCredentials().getUri();

			String sql = "INSERT INTO credentials (username,password,host,port,url,date) VALUES(?,?,?,?,?,?)";
			Object[] params = new Object[] { userName, password, host, port,
					url ,Util.getSysTime()};
			int[] types = new int[] { Types.VARCHAR, Types.VARCHAR , Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};
			
			
			// jdbcTemplate.
			int updateResult = jdbcTemplate.update(sql, params, types);

			/**
			 * 保存证书信息
			 */
			jdbcTemplate.execute("CREATE USER " + userName + " IDENTIFIED BY "
					+ "'"+password+"'");
			jdbcTemplate.execute("GRANT ALL PRIVILEGES ON " + dataBase
					+ ".* TO " + userName + "@'%'");
			jdbcTemplate.execute("FLUSH PRIVILEGES");
		} catch (Exception e) {
			
			
			throw e;
		}
	}

	/**
	 * unbinding
	 * @param binding
	 */
	public void destroy(String userName) {
		
		/**
		 * 清除数据库
		 */
		String sql = "DELETE FROM credentials where userName=?";
		Object[] params = new Object[] { userName };
		int[] types = new int[] { Types.VARCHAR };
		jdbcTemplate.update(sql, params, types);
		
		jdbcTemplate.execute("DROP USER " + userName);
	}
}


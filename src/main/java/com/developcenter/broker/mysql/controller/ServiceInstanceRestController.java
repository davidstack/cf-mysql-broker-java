package com.developcenter.broker.mysql.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.developcenter.broker.mysql.model.ServiceInstance;
import com.developcenter.broker.mysql.service.ServiceInstanceService;

/**
 * Author: Sridharan Kuppa sridharan.kuppa@gmail.com
 * Date: 12/12/13
 */

@Controller
@RequestMapping("/v2/service_instances/{id}")
class ServiceInstanceRestController {
  @Autowired
  private ServiceInstanceService service;

  private Log logger = LogFactory.getLog(ServiceInstanceRestController.class);
  
  @RequestMapping(method = RequestMethod.PUT)
  @ResponseBody
  Map update(@PathVariable String id,HttpServletRequest request,HttpServletResponse response) {
    
		try {
			if (!service.isExists(id)) {
				service.create(id);
			}
			else
			{
				logger.error("ServiceInstanceRestController create instance faile ,instance has exist id="+id);
				 response.setStatus(409);	
				 return new HashMap<String,String>();
			}
		} catch (Exception e) {
			logger.error("ServiceInstanceRestController create service instance exception",e);
			logger.error("ServiceInstanceRestController create service instance id="+id);
			response.setStatus(500);
			 return new HashMap<String,String>();
		}
		response.setStatus(201);
        return new HashMap<String,String>();
  }

	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	Map destroy(@PathVariable String id, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			if (service.isExists(id)) {
				service.delete(id);
			}
		} catch (Exception e) {
			logger.error(
					"ServiceInstanceRestController delete service instance exception",
					e);
			logger.error("ServiceInstanceRestController delete service instance id="
					+ id);
			response.setStatus(500);
			return null;
		}
		return new HashMap<String, String>();
	}
}

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

import com.developcenter.broker.mysql.model.ServiceBinding;
import com.developcenter.broker.mysql.service.ServiceBindingService;

@Controller
@RequestMapping("/v2/service_instances/{instanceId}/service_bindings/{bindingId}")
class ServiceBindingRestController {
  @Autowired 
  ServiceBindingService bindingService;
 
  private Log logger = LogFactory.getLog(ServiceBindingRestController.class);
 
  
  /**
   * 
   * @param instanceId mysql id
   * @param bindingId
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(method = RequestMethod.PUT)
  @ResponseBody
  ServiceBinding update(@PathVariable String instanceId, @PathVariable String bindingId,HttpServletRequest request,HttpServletResponse response) {
    ServiceBinding binding = bindingService.constructServiceBinding(bindingId, instanceId);
    if(null==binding)
    {
    	logger.error("ServiceBinding service instance does not exits");
    	response.setStatus(500);
    	return null;
    }
    try {
		bindingService.create(binding);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		response.setStatus(500);
    	return null;
	}
    return binding;
  }

  /**
   * 解除服务
   * @param instanceId
   * @param bindingId
   * @return
   */
  @RequestMapping(method = RequestMethod.DELETE)
  @ResponseBody
  Map destroy(@PathVariable String instanceId, @PathVariable String bindingId,HttpServletRequest request,HttpServletResponse response) {
   
	  /**
	   * 查找给bind 对应的用户名
	   */
	  String userName = bindingService.findById(bindingId, instanceId);
    if(null==userName)
    {
    	logger.error("ServiceBinding service instance does not exits");
    	response.setStatus(500);
    	return null;
    }
    bindingService.destroy(userName);
    return new HashMap<String,String>();
  }

}

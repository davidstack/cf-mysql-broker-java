package com.developcenter.broker.mysql.controller;
import java.io.InputStream;
import java.util.Map;

import org.ho.yaml.Yaml;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author: Sridharan Kuppa sridharan.kuppa@gmail.com
 * Date: 12/12/13
 */
@Controller
class CatalogRestController {
  private Map<Object,Object> settings;

  @SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
  @RequestMapping(value="/v2/catalog",method=RequestMethod.GET)
  @ResponseBody
  synchronized Map getCatalog() {
   try
   {if (null==settings) {
      Yaml yaml = new Yaml();
      ClassLoader loader=Thread.currentThread().getContextClassLoader();
		InputStream is=loader.getResourceAsStream("settings.yml");
      settings = (Map<Object, Object>) yaml.load(is);
    }
   }
   catch(Exception e)
   {
	   e.printStackTrace();
   }
    return settings;
  }

}

package com.yl.realAuth.core.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthConfigController {
	private static final Logger logger=LoggerFactory.getLogger(AuthConfigController.class);
	
	@RequestMapping("loadingAuthConfigEnums")
	public String loadingAuthConfigEnums(){
		return "/jsp/RealIdentify/RealFaceQuery.jsp"; 	
	} 
}

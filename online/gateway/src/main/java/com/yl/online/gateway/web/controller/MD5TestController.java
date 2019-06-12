package com.yl.online.gateway.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.security.DigestUtils;

/**
 * MD5测试控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月22日
 * @version V1.0.0
 */
@Controller
public class MD5TestController {
	private static final Logger logger = LoggerFactory.getLogger(MD5TestController.class);

	@RequestMapping("MD5Test")
	public void forward1(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String[]> map = request.getParameterMap();
		ArrayList<String> paramNames = new ArrayList<>(map.keySet());
		Collections.sort(paramNames);
		// 组织待签名信息
		StringBuilder signSource = new StringBuilder();
		Iterator<String> iterator = paramNames.iterator();
		while (iterator.hasNext()) {
			String paramName = iterator.next();
			if (!"sign".equals(paramName) && !"gatewayUrl".equals(paramName) && !"url".equals(paramName) && map.get(paramName)[0] != "" && !"key".equals(paramName)) {
				signSource.append(paramName).append("=").append(map.get(paramName)[0]);
				signSource.append("&");
			}
		}

		String partnerString = signSource.toString();
		partnerString = partnerString.substring(0, partnerString.length()-1);
		partnerString += request.getParameter("key");
		logger.info("签名串：{}", partnerString);
		try {
			if (StringUtils.notBlank(request.getParameter("inputCharset"))) {
				partnerString = DigestUtils.md5DigestAsHex(partnerString.getBytes(request.getParameter("inputCharset")));
			} else {
				partnerString = DigestUtils.md5DigestAsHex(partnerString.getBytes());
			}
			response.getWriter().print(partnerString);
		} catch (Exception e) {
			logger.error("MD5出异常了！！", e);
		}
	}
}

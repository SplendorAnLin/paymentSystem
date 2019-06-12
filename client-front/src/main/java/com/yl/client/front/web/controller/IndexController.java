package com.yl.client.front.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yl.client.front.common.PushMSGUtils;
import com.yl.client.front.common.ResponseMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.security.DigestUtils;
import com.yl.client.front.common.AppRuntimeException;
import com.yl.client.front.common.DistinParams;
import com.yl.client.front.enums.AppExceptionEnum;
import com.yl.client.front.handler.AppHandler;
import com.yl.client.front.model.ReqAllInfo;

import net.sf.json.JSONObject;

/**
 * 客户端
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月28日
 * @version V1.0.0
 */
@Controller
public class IndexController {
	private static Logger log = LoggerFactory.getLogger(IndexController.class);

	private static final String INPUT_CHARSET = "UTF-8";
	
	@Resource
	private Map<String, AppHandler> appHandlers;

	@SuppressWarnings("unchecked")
	@RequestMapping("/interface")
	public void index(HttpServletRequest request, HttpServletResponse response) {
		ReqAllInfo reqInfo=(ReqAllInfo)request.getAttribute("reqInfo");
		long startTime = System.currentTimeMillis();
		if (reqInfo!=null) {
			Object resInfo=null;
			AppHandler appHandler=appHandlers.get(reqInfo.getReqSys());
			Map<String,Object> reqMap=JsonUtils.toJsonToObject(reqInfo.getReqInfo(), Map.class);
			log.info("APP请求参数:{}",JsonUtils.toJsonString(reqMap));
			reqMap.put("msg", reqInfo.getClientInfo().getMsg());
			reqMap.put("sysType", reqInfo.getClientInfo().getClientType());
			reqMap.put("oem", reqInfo.getClientInfo().getOem());
			try {
				log.info("調用前 接口处理时间:{},返回信息时间:{}",System.currentTimeMillis()-startTime,System.currentTimeMillis());
				resInfo=appHandler.getClass().getDeclaredMethod(reqInfo.getReqType(), Map.class).invoke(appHandler,reqMap);
                log.info("調用后 接口处理时间:{},返回信息时间:{}",System.currentTimeMillis()-startTime,System.currentTimeMillis());
			} catch (ReflectiveOperationException e) {
				log.error("APP接口匹配失败或者运行异常:{},参数:{},返回参数:{},调用接口:{}",e.getMessage(),JsonUtils.toJsonString(reqMap),
						resInfo,reqInfo.getReqSys()+"."+reqInfo.getReqType());
				String userCode=userCode(reqMap);
				ResponseMsg.exception(e, response,userCode);
				return;
			}
			try {
				JSONObject params =new JSONObject();
				if (resInfo!=null) {
					//组装信息签名
					String temp=JsonUtils.toJsonString(resInfo);
					String calSign = DigestUtils.md5DigestAsHex(temp.getBytes(INPUT_CHARSET));
					params.put("md5", calSign);
					params.put("responseCode", AppExceptionEnum.SUCCESS.getCode());
					params.put("responseMsg", AppExceptionEnum.SUCCESS.getMessage());
					params.put("responseData", temp);
					params.put("responseName", reqInfo.getReqCode());
					String json=JsonUtils.toJsonString(DistinParams.encryptApp(params.toString()));
					//加密
					response.getWriter().write(json);
					log.info("接口处理时间:{},返回信息时间:{}",System.currentTimeMillis()-startTime,System.currentTimeMillis());
					response.getWriter().close();
				}else {
					throw new AppRuntimeException(AppExceptionEnum.RETURNEMPTY.getCode(),AppExceptionEnum.RETURNEMPTY.getMessage());
				}
			} catch (Exception e) {
				ResponseMsg.exception(e, response);
			}
		}
	}
	
	String userCode(Map<String,Object> reqMap){
		if (reqMap.get("ownerId")!=null) {
			return "用户识别码:"+reqMap.get("ownerId").toString();
		}
		if (reqMap.get("customerNo")!=null) {
			return "商户号:"+reqMap.get("customerNo").toString();
		}
		if (reqMap.get("userName")!=null) {
			return "手机号:"+reqMap.get("userName").toString();
		}
		return null;
	}

}

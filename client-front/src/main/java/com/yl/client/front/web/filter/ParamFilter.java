package com.yl.client.front.web.filter;

import com.lefu.commons.web.spring.ApplicationContextUtils;
import com.lefu.commons.web.springmvc.interceptor.LoginPermissionInterceptor;
import com.yl.client.front.common.DistinParams;
import com.yl.client.front.common.ResponseMsg;
import com.yl.client.front.enums.AppExceptionEnum;
import com.yl.client.front.model.ReqAllInfo;
import com.yl.client.front.service.ReqInfoService;
import com.yl.client.front.service.VersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 请求拦截器
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月28日
 * @version V1.0.0
 */
public class ParamFilter extends LoginPermissionInterceptor{
	
	private static Logger log = LoggerFactory.getLogger(ParamFilter.class);
	
	private ReqInfoService reqInfoService;
	private VersionService versionService;
	
	public ParamFilter(String redirectPath) {
		super(redirectPath);
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
		if (handler instanceof HandlerMethod) {
			long start = System.currentTimeMillis();
			String servletPath = request.getServletPath();
			if (isUrl(servletPath)) {
				reqInfoService = (ReqInfoService) ApplicationContextUtils.getApplicationContext().getBean("reqInfoService");
				versionService = (VersionService) ApplicationContextUtils.getApplicationContext().getBean("versionService");
				ReqAllInfo reqInfo;
				try {
					reqInfo = DistinParams.signValidate(request);
                    log.info("签名处理时间:"+ (System.currentTimeMillis() - start) +"ms");
					Map<String, Object> info=versionService.checkVersion(reqInfo.getClientInfo().getClientType(), reqInfo.getClientInfo().getOem());
					if (info!=null) {
						if ("TRUE".equals(info.get("enforce").toString())&&!reqInfo.getClientInfo().getClientVersion().equals(info.get("version").toString())) {
							//强制更新
							ResponseMsg.msg(response,AppExceptionEnum.UPAPP,info.get("url").toString());
							return false;
						}
					}else {
						ResponseMsg.msg(response,AppExceptionEnum.CLIENTTYPEERR);
						return false;
					}
					log.info("版本检查处理时间:"+ (System.currentTimeMillis() - start) +"ms");
					if (!isAuth(reqInfo.getReqCode())&&!DistinParams.authValidate(reqInfo)) {
						ResponseMsg.msg(response,AppExceptionEnum.AUTHERR);
						return false;
					}
					if (reqInfo!=null) {
						start=System.currentTimeMillis();
						final ReqAllInfo tempReq=new ReqAllInfo(reqInfo);
						new Thread(new Runnable() {
							@Override
							public void run() {
								reqInfoService.save(tempReq);
							}
						}).start();
						request.setAttribute("reqInfo", reqInfo);
                        log.info("拦截处理时间:"+ (System.currentTimeMillis() - start) +"ms");
						return true;
					}
				} catch (Exception e) {
					log.info("参数异常:{}",e);
					ResponseMsg.exception(e, response);
				}
			}else {

				return true;
			}
		}
		return false;
	}

	/**
	 * 集合中的url不拦截
	 * @param servletPath
	 * @return
	 */
	public boolean isUrl(String servletPath){
		String[] url={"completeOrder","addNoticeConfig","upNoticeConfig","findNoticeConfig","findNoticeConfigByOem","findAllNoticeConfigByInfo","sendAllNotice"};
		for (String temp:url) {
			if (("/"+temp).equals(servletPath)){
				return false;
			}
		}
		return true;
	}
	/**
	 * 在数组中的不认证身份
	 * @param code
	 * @return
	 */
	public boolean isAuth(String code){
		switch (code){
		    case "001":
            case "002":
            case "018":
            case "027":
            case "028":
            case "030":
            case "999":
                return true;
            default:
                return false;
        }
	}
}

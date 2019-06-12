package com.yl.client.front.common;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.client.front.enums.AppExceptionEnum;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class ResponseMsg {
    private static Logger log = LoggerFactory.getLogger(ResponseMsg.class);
    public static void msg(HttpServletResponse response, AppExceptionEnum exc){
        msg(response,exc,null);
    }

    public static void msg(HttpServletResponse response,AppExceptionEnum exc,Object data){
        Map<String,Object> params = new HashMap<>();
        params.put("responseCode", exc.getCode());
        params.put("responseMsg", exc.getMessage());
        if(data!=null){
            params.put("responseData", data);
        }
        try {
            response.getWriter().write(com.lefu.commons.utils.lang.JsonUtils.toJsonString(DistinParams.encryptApp(com.lefu.commons.utils.lang.JsonUtils.toJsonString(params))));
            response.getWriter().close();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public static void msgNotSuccess(Object data,PrintWriter pw){
        JSONObject params =new JSONObject();
        params.put("responseCode", "000000");
        params.put("responseMsg", "请求成功");
        params.put("responseData", data);
        pw.print(params);
    }
    public static void msgNotEncrypt(PrintWriter pw, AppExceptionEnum exc){
        msgNotEncrypt(pw,exc,null);
    }
    public static void msgNotEncrypt(PrintWriter pw, AppExceptionEnum exc,Object data){
        Map<String,Object> params = new HashMap<>();
        params.put("responseCode", exc.getCode());
        params.put("responseMsg", exc.getMessage());
        if(data!=null){
            params.put("responseData", data);
        }
        pw.print(JsonUtils.toJsonString(params));
    }

    public static void msgNotEncrypt(HttpServletResponse response, AppExceptionEnum exc){
        msgNotEncrypt(response,exc,null);
    }
    public static void msgNotEncrypt(HttpServletResponse response, AppExceptionEnum exc,Object data){
        Map<String,Object> params = new HashMap<>();
        params.put("responseCode", exc.getCode());
        params.put("responseMsg", exc.getMessage());
        if(data!=null){
            params.put("responseData", data);
        }
        try {
            response.getWriter().write(JsonUtils.toJsonString(params));
            response.getWriter().close();
        } catch (Exception e) {
            log.error("", e);
        }
    }
    public static void exception(Exception e, HttpServletResponse response){
    	exception(e,response,null);
    }

    public static void exception(Exception e, HttpServletResponse response,String userCode){
        try {
            response(((AppRuntimeException)e).getCode(),((AppRuntimeException)e).getMessage(),response);
        } catch (Exception e1) {
            try {
                response(((AppRuntimeException) e.getCause()).getCode(),e.getCause().getMessage(),response);
            } catch (Exception e2) {
            	if (StringUtils.notBlank(userCode)) {
            		log.error("系统异常信息:{},用户识别:{}",e,userCode);
				}else {
					log.error("系统异常信息:{}",e);
				}
                response(AppExceptionEnum.SYSERR.getCode(),AppExceptionEnum.SYSERR.getMessage(),response);
            }
        }
    }

    public static void response(String responseCode, String responseMsg, HttpServletResponse response){
    	log.error("业务异常码:{},提示信息:{}",responseCode,responseMsg);
        Map<String, Object> resParams = new HashMap<>();
        resParams.put("responseCode", responseCode);
        resParams.put("responseMsg", responseMsg);
        try {
            response.getWriter().write(com.lefu.commons.utils.lang.JsonUtils.toJsonString(DistinParams.encryptApp(JsonUtils.toJsonString(resParams))));
            response.getWriter().close();
        } catch (Exception e) {
            log.error("", e);
        }
    }
}

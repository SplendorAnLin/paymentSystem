package com.yl.boss.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.util.ValueStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Struts2Action
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
public class Struts2ActionSupport extends ActionSupport implements Preparable {
    
	private static final long serialVersionUID = 5933405026402912125L;
	public static final Logger logger = LoggerFactory.getLogger(Struts2ActionSupport.class);

    public void putInValueStack(String key, Object value) {
        ValueStack stack = ActionContext.getContext().getValueStack();
        stack.set(key, value);        
    }
	
	public Map getParamsMap(){
		return ActionContext.getContext().getParameters();
	}

	public Map getSessionMap() {
		return ActionContext.getContext().getSession();
	}

	public HttpServletRequest getHttpRequest() {
		return ServletActionContext.getRequest();
	}

	public HttpServletResponse getHttpResponse() {
		return ServletActionContext.getResponse();
	}

	public HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}
	
	public void prepare() throws Exception {		
	}	
	  /**
     * 供AJAX返回使用(ajax的一种返回方式)
     * 
     * @param msg
     */
    public void write(String msg) {
        PrintWriter write = null;
        try {
        	ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
        	ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
            write = ServletActionContext.getResponse().getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(write != null){
        	write.write(msg);
            write.close();
        }
        
    }
}

package com.yl.customer.taglib.boss ;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;

/**
 * BOSS标签类
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月20日
 * @version V1.0.0
 */
public class PanTag extends TagSupport {
	private Logger log = Logger.getLogger(PanTag.class) ;

	public String pan;
	
	@Override
    public int doEndTag() throws JspException{
    	try{
    		if(pan.length()>10){
    			pan = pan.substring(0,6) + " * " + pan.substring(pan.length()-4, pan.length());
    		}
        	pageContext.getOut().write(pan);
    	}catch(Exception e){
    		log.error(" write select tag error ", e);
    	}
        return EVAL_PAGE;
    }

	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}	
}

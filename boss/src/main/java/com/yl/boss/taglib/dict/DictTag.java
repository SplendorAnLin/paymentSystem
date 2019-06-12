package com.yl.boss.taglib.dict;

import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DictTag extends TagSupport{
	
	protected String name;
	protected String style;
	protected boolean disabled = false ;
	protected String onclick;
	protected String onfocus;
	protected String onmouseover;
	protected String onmouseout;
	protected String customvalue;
	protected Log log = LogFactory.getLog(DictTag.class);
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getOnfocus() {
		return onfocus;
	}

	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}

	public String getOnmouseover() {
		return onmouseover;
	}

	public void setOnmouseover(String onmouseover) {
		this.onmouseover = onmouseover;
	}

	public String getOnmouseout() {
		return onmouseout;
	}

	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}


	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getCustomvalue() {
		return customvalue;
	}

	public void setCustomvalue(String customvalue) {
		this.customvalue = customvalue;
	}
}
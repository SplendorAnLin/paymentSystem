package com.yl.agent.taglib.dict ;

import java.util.List;

import javax.servlet.jsp.JspException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.common.util.StringUtil;
import com.yl.agent.Constant;
import com.yl.agent.cache.CacheService;

import net.sf.json.JSONObject;

/**
 * Radio标签类
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月16日
 * @version V1.0.0
 */
public class RadioTag extends DictTag {
	
	private static final long serialVersionUID = -4071115858444703260L;
	private Logger log = LoggerFactory.getLogger(RadioTag.class) ;
	String value;
	String dictTypeId;
	String style;
	String styleClass;
	String filterExpr;
	String filterVal;
	boolean displayReverse = false;
	private CacheService cacheService;
	@SuppressWarnings("unchecked")
	@Override
    public int doEndTag() throws JspException{
    	try{
    		StringBuffer tagContent = new StringBuffer();
//    		List<Dictionary> dicts = DictUtil.getDictsByDictTypeId(dictTypeId);
    		
    	   /*	List<Dictionary> dicts = new ArrayList<Dictionary>();
        	if(StringUtil.notNull(filterExpr) && StringUtil.notNull(filterVal) && DictUtil.filterSymbols.contains(filterExpr)){
        		dicts = DictUtil.getDictsByDictTypeIdAndFilterExpre(dictTypeId,filterExpr,filterVal);
        	}else{
        		dicts = DictUtil.getDictsByDictTypeId(dictTypeId);
        	}
        	if(displayReverse){
        		dicts = DictUtil.reverseByDisplayOrder(dicts);
        	}*/
    		List<JSONObject> dicts=Constant.DICTS.get(dictTypeId).getJSONArray("dictionaries");
    		createRadios(tagContent,dicts);
        	pageContext.getOut().write(tagContent.toString());
    	}catch(Exception e){
    		log.error(" write select tag error ", e);
    	}
        return EVAL_PAGE;
    }
	public void createRadios(StringBuffer tagContent,List<JSONObject> dicts){
		for(JSONObject dict :dicts){
			String val =dict.getString("key").replaceAll(Constant.DICTIONARY, "");
			tagContent.append("<input type=\"radio\" value=\""+val+"\"");
			if(StringUtil.notNull(name)){
				tagContent.append(" name=\""+name+"\"");
			}
			if(disabled == true){
				tagContent.append(" disabled=\"true\"");
			}
			if(StringUtil.notNull(style)){
				tagContent.append(" style=\""+style+"\"");
			}
			if(StringUtil.notNull(onclick)){
				tagContent.append(" onclick=\""+onclick+"\"");
			}
			if(StringUtil.notNull(onfocus)){
				tagContent.append(" onfocus=\""+onfocus+"\"");
			}
			if(StringUtil.notNull(onmouseover)){
				tagContent.append(" onmouseover=\""+onmouseover+"\"");
			}
			if(StringUtil.notNull(onmouseout)){
				tagContent.append(" onmouseout=\""+onmouseout+"\"");
			}
			if(val.equals(value)){
				tagContent.append(" checked=\"true\"");
			}
			if(StringUtil.notNull(styleClass)){
	    		tagContent.append(" class=\"" + styleClass +"\"");
	    	}
			if(StringUtil.notNull(style)){
	    		tagContent.append(" style=\"" + style +"\"");
	    	}
			if(val.equals("TRUE")){
				tagContent.append(" id=u-on >\r\n<lable for=\"u-on\">"+ dict.getString("value") +"</lable>\r\n");
			}
			if (val.equals("FALSE")) {
				tagContent.append(" id=u-off >\r\n<lable for=\"u-off\">"+ dict.getString("value") +"</lable>\r\n");
			}
			//tagContent.append(">&nbsp;&nbsp;").append(dict.getValue()).append("&nbsp;&nbsp;\r\n");
		}
	}

	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDictTypeId() {
		return dictTypeId;
	}
	public void setDictTypeId(String dictTypeId) {
		this.dictTypeId = dictTypeId;
	}
	public CacheService getCacheService() {
		return cacheService;
	}
	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getStyleClass() {
		return styleClass;
	}
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	public String getFilterExpr() {
		return filterExpr;
	}
	public void setFilterExpr(String filterExpr) {
		this.filterExpr = filterExpr;
	}
	public String getFilterVal() {
		return filterVal;
	}
	public void setFilterVal(String filterVal) {
		this.filterVal = filterVal;
	}
	public boolean isDisplayReverse() {
		return displayReverse;
	}
	public void setDisplayReverse(boolean displayReverse) {
		this.displayReverse = displayReverse;
	}
	
}

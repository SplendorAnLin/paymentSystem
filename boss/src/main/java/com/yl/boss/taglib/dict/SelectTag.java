package com.yl.boss.taglib.dict;

import com.lefu.commons.utils.lang.StringUtils;
import com.pay.common.util.StringUtil;
import com.yl.boss.Constant;
import com.yl.boss.cache.CacheService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Select标签类
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月16日
 * @version V1.0.0
 */
public class SelectTag extends DictTag {
	
	private static final long serialVersionUID = -4409743654274686527L;
	private Logger log = LoggerFactory.getLogger(SelectTag.class) ; 
	boolean nullOption = false;
	boolean multiple = false;
	boolean displayReverse = false;
	String id ;
	String value ;	
	String escapeValue ; 
	String nullLabel = null;
	String dictTypeId;
	String styleClass;
	String filterExpr;
	String filterVal;
	String diy;
	String onchange ;
	String shield;
	private  CacheService cacheService;
	@Override
    public int doStartTag() throws JspException {
        return SKIP_BODY;
    }
	
	@Override
    public int doEndTag() throws JspException{
    	try{
    		StringBuffer tagContent = new StringBuffer();
        	//根据传入属性值生成对应的属性头
        	createSelectHead(tagContent);
        	//将标题写入<option></option>
        	createTitleOption(tagContent);
        	//添加其他<option></option>
        	createValueOptions(tagContent);
        	//添加</slect>结束</select>
        	createSelectEnd(tagContent);
        	//输出
        	pageContext.getOut().write(tagContent.toString());
    	}catch(Exception e){
    		e.printStackTrace() ;
    		log.error(" write select tag error ", e);
    	}
        return EVAL_PAGE;
    }
    /**
     * 生成<select name="" class="" style="" ...... >
     * @param tagContent
     */
    public void createSelectHead(StringBuffer tagContent){
    	tagContent.append("<select");
    	if(StringUtil.notNull(id )){
    		tagContent.append(" id =\"" + id  +"\"");
    	} 
    	if(StringUtil.notNull(name )){
    		tagContent.append(" name =\"" + name  +"\"");
    	} 
    	if(disabled){
    		tagContent.append(" readonly=\"readonly\"");
    	}
    	if(StringUtil.notNull(diy )){
    		tagContent.append(" "+diy);
    	} 
    	if(multiple){
    		tagContent.append(" multiple=\"" + multiple +"\"");
    	} 
    	if(StringUtil.notNull(style)){
    		tagContent.append(" style=\"" + style +"\"");
    	}
    	if(StringUtil.notNull(onchange)){
    		tagContent.append(" onchange=\"" + onchange +"\"");
    	}
    	if(StringUtil.notNull(onclick)){
    		tagContent.append(" onclick=\"" + onclick +"\"");
    	} 
    	if(StringUtil.notNull(onfocus)){
    		tagContent.append(" onfocus=\"" + onfocus +"\"");
    	}  
    	if(StringUtil.notNull(onmouseout)){
    		tagContent.append(" onmouseout=\"" + onmouseout +"\"");
    	}
    	if(StringUtil.notNull(onmouseover)){
    		tagContent.append(" onmouseover=\"" + onmouseover +"\"");
    	}
    	if(StringUtil.notNull(styleClass)){
    		tagContent.append(" class=\"" + styleClass +"\"");
    	}
    	tagContent.append(">\r\n");
    }
    
    /**
     * 生成一个<option value="value">label</option>
     * @param tagContent 最终输出的内容
     * @param value 值
     * @param label 名
     * @param selected 是否选中
     */
    protected void createSingleOption(StringBuffer tagContent, String value, String label, boolean selected) {
    	value = value.indexOf(Constant.DICTIONARY)>-1?value.replace(Constant.DICTIONARY, ""):value;
		if (!StringUtils.isBlank(shield)){
			String []temp =shield.split(",");
			for (String str:temp) {
				if (str.equals(value))
					return;
			}
		}
        tagContent.append("\t\t<option ");
        if(StringUtil.notNull(customvalue)){
        	 tagContent.append(" "+customvalue+"=\"");
        	 tagContent.append(value + "\"");
        }
        if(!value.equals("")){
        	tagContent.append(" value=\"");
        } else {
        	tagContent.append(" value=\"");
        }
        tagContent.append(value);
        tagContent.append("\"");
        if (selected) {
            tagContent.append(" selected ");
        }
        tagContent.append(">"); 
        tagContent.append(StringUtil.safeValue(label));
        tagContent.append("</option>\r\n");
    }
    /**
     * 创建第一个<option>(默认:)</option>,
     * @param tagContent
     */
    protected void createTitleOption(StringBuffer tagContent) {
    	if( nullOption == true ){
    		createSingleOption( tagContent, "", "全部", false ) ;
    	}
    }
    /**
     * 根据查询出来的值创建其余的<option></option>
     * @param tagContent
     */
    @SuppressWarnings("unchecked")
	protected void createValueOptions(StringBuffer tagContent) {
    	//根据svnName获取值
    	//List<com.lefu.commons.cache.bean.Dictionary> dicts = new ArrayList<com.lefu.commons.cache.bean.Dictionary>();
    	List<JSONObject> dicts=Constant.DICTS.get(dictTypeId).getJSONArray("dictionaries");
    	if(displayReverse){
    		Collections.sort(dicts,new Comparator<JSONObject>() {
				@Override
				public int compare(JSONObject o1, JSONObject o2) {
					if(o1.getInt("order")>o2.getInt("order")){
				        return -1;
				    }else if(o1.getInt("order")<o2.getInt("order")){
				        return 1;
				    }else{
				        return 0;
				    }
				}
			});
    	}
    	//List个数为0
    	if( dicts==null || dicts.size()==0 ){
    		return ;
    	}
    	//将每一个Dictionary转化成一个<option></option>
    	//如果设置选中可以在这里做一个标记既可
    	for(JSONObject dict:dicts){
    		String key = dict.getString("key").indexOf(Constant.DICTIONARY)>-1?dict.getString("key").replace(Constant.DICTIONARY, ""):dict.getString("key");
    		if(key.equals(value)){
    			createSingleOption(tagContent,key,dict.getString("value"),true);
    		}else if(key.equals(escapeValue)){
    			continue;
    		}else{
    			createSingleOption(tagContent,key,dict.getString("value"),false);
    		}
    	}
    }
    
    /**
     * 创建结束的</select>
     * @param tagContent
     */
    protected void createSelectEnd(StringBuffer tagContent){
    	tagContent.append("</select>");
    }
    
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getOnchange() {
		return onchange;
	}
	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public String getOnclick() {
		return onclick;
	}
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}


	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}

	public String getDictTypeId() {
		return dictTypeId;
	}
	public void setDictTypeId(String dictTypeId) {
		this.dictTypeId = dictTypeId;
	}
	public String getOnfocus() {
		return onfocus;
	}
	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}

	public String getOnmouseout() {
		return onmouseout;
	}
	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getEscapeValue() {
		return escapeValue;
	}
	public void setEscapeValue(String escapeValue) {
		this.escapeValue = escapeValue;
	}

	public boolean isNullOption() {
		return nullOption;
	}

	public void setNullOption(boolean nullOption) {
		this.nullOption = nullOption;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	public String getNullLabel() {
		return nullLabel;
	}

	public void setNullLabel(String nullLabel) {
		this.nullLabel = nullLabel;
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

	public String getDiy() {
		return diy;
	}

	public void setDiy(String diy) {
		this.diy = diy;
	}

	public String getShield() {
		return shield;
	}

	public void setShield(String shield) {
		this.shield = shield;
	}
}

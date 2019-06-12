package com.yl.boss.taglib.dict ;

import java.util.List;

import javax.servlet.jsp.JspException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pay.common.util.StringUtil;
import com.yl.boss.Constant;

import net.sf.json.JSONObject;

/**
 * Write标签类
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月16日
 * @version V1.0.0
 */
public class WriteTag extends DictTag {
	
	private static final long serialVersionUID = -3229587579131626460L;

	private Logger log = LoggerFactory.getLogger(WriteTag.class) ;
	
	String dictTypeId = null; 
	String dictId = null;
	@Override
    public int doEndTag() throws JspException{
        try{
        	String labelName = getLabelName(dictTypeId,dictId); 
    		pageContext.getOut().write(labelName);
        }catch(Exception e){
        	log.info("create write tag error ", e);
        }
        return EVAL_PAGE;
    }
    
    /**
     * 根据keyName与其属性获取名称
     * @param svcName(dictTypeId)
     * @param code(dictId)
     * @return
     */
    @SuppressWarnings("unchecked")
	public String getLabelName(String keyName, String code) {
    	List<JSONObject> dicts=Constant.DICTS.get(dictTypeId).getJSONArray("dictionaries");
		if(dicts == null)
			return null;
		String label = null;
		for (JSONObject dict : dicts) {
			if (dict.getString("key").replaceAll(Constant.DICTIONARY, "").equals(code)) {
				label = dict.getString("value");
				break;
			}
		}
		return StringUtil.safeValue( label ) ;
	}
    
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}

	public String getDictId() {
		return dictId;
	}
	public void setDictId(String dictId) {
		this.dictId = dictId;
	}

	public String getDictTypeId() {
		return dictTypeId;
	}
	public void setDictTypeId(String dictTypeId) {
		this.dictTypeId = dictTypeId;
	}

	
}

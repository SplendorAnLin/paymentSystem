package com.yl.agent.action;

import java.util.List;

import com.lefu.commons.cache.bean.Dictionary;
import com.lefu.commons.cache.bean.DictionaryType;
import com.lefu.commons.cache.tags.DictionaryUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.agent.Constant;

/**
 * 字典控制器
 * 
 * @author 聚合支付有限公司
 * @since 2017年7月7日
 * @version V1.0.0
 */
public class DictionaryAction extends Struts2ActionSupport {
	
	private static final long serialVersionUID = 1472183769666077889L;
	
	private DictionaryType dictionaryTypeRanged;
	private List<Dictionary> dictionaryRangedList;
	private String msg;

	/**
	 * 根据字典类型编号Ajax查询旗下所有字典
	 * @return
	 */
	public String ajaxQueryDictionaryByTypeCode(){
		try {
			dictionaryTypeRanged = DictionaryUtils.getDictionaryTypeBy(Constant.DICTIONARY_TYPE + dictionaryTypeRanged.getCode());
			dictionaryRangedList = dictionaryTypeRanged.getDictionaries();
			msg = JsonUtils.toJsonString(dictionaryRangedList);
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return SUCCESS;
	}

	public DictionaryType getDictionaryTypeRanged() {
		return dictionaryTypeRanged;
	}

	public void setDictionaryTypeRanged(DictionaryType dictionaryTypeRanged) {
		this.dictionaryTypeRanged = dictionaryTypeRanged;
	}

	public List<Dictionary> getDictionaryRangedList() {
		return dictionaryRangedList;
	}

	public void setDictionaryRangedList(List<Dictionary> dictionaryRangedList) {
		this.dictionaryRangedList = dictionaryRangedList;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}

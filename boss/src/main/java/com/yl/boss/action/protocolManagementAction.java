package com.yl.boss.action;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.security.PreventAttackUtil;
import com.yl.boss.Constant;
import com.yl.boss.entity.Authorization;
import com.yl.boss.entity.ProtocolManagement;
import com.yl.boss.service.ProtocolManagementService;

import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.web.mvc.ValueListHandlerHelper;
import net.sf.json.JSONObject;

/**
 * 文档管理控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
public class protocolManagementAction extends Struts2ActionSupport{
	
	private static final long serialVersionUID = 5771740628585675184L;
	
	protected static Log logger = LogFactory.getLog(protocolManagementAction.class);
	
	private ProtocolManagement protocolManagement;
	private ProtocolManagementService protocolManagementService;
	private ValueListHandlerHelper valueListHelper;
	private Long id;
	private String msg;
	
	@SuppressWarnings("unchecked")
	public String findAppHelp(){
		if (id!=null&&id>0) {
			protocolManagement=protocolManagementService.getprotolById(id);
			return "detail";
		}
		Map<String, Object> params = retrieveParam(getHttpRequest().getParameterMap()); // request 参数转换
		ValueListInfo info = new ValueListInfo(params);
		if (params.get("pagingPage") == null) {
			info.setPagingPage(1);
		}
		ValueList valueList = valueListHelper.getValueList("appHelpList", info);
		getHttpRequest().setAttribute("appHelpList", valueList.getList());
		getHttpRequest().setAttribute("pagingPage", valueList.getValueListInfo().getPagingPage());
		getHttpRequest().setAttribute("countPage", valueList.getValueListInfo().getTotalNumberOfPages());
		getHttpRequest().setAttribute("showCount", valueList.getValueListInfo().getPagingNumberPer());
		return "list";
	}
	
	public String findHelpList() {
		Map<String, Object> params = retrieveParam(getHttpRequest().getParameterMap()); // request 参数转换
		ValueListInfo info = new ValueListInfo(params);
		if (params.get("pagingPage") == null) {
			info.setPagingPage(1);
		}
		ValueList valueList = valueListHelper.getValueList("appHelpList", info);
		msg=JsonUtils.toJsonString(valueList.getList());
		return SUCCESS;
	}
	
	public String protocolManagementAdd(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		protocolManagement.setContent(protocolManagement.getContent().replace(System.lineSeparator(), ""));
		protocolManagement.setCreateTime(new Date());
		protocolManagement.setOperator(auth.getRealname());
		try {
			protocolManagementService.insert(protocolManagement);
			msg="true";
		} catch (Exception e) {
			logger.info("添加文档信息失败:{}",e);
			msg="false";
		}
		return SUCCESS;
	}
	public String protocolManagementFindById(){
		protocolManagement=protocolManagementService.getprotolById(id);
		return SUCCESS;
	}
	public String protocolManagementUpdate(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		protocolManagement.setContent(protocolManagement.getContent().replace(System.lineSeparator(), ""));
		protocolManagement.setOperator(auth.getRealname());
		try {
			protocolManagementService.updateProtol(protocolManagement);
			msg="true";
		} catch (Exception e) {
			logger.info("更新文档信息失败:{}",e);
			msg="false";
		}
		return SUCCESS;
	}
	// 原始请求参数转换
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map retrieveParam(Map requestMap) {
		Map resultMap = new HashMap();
		for (Object key : requestMap.keySet()) {
			Object value = requestMap.get(key);
			if (value != null) {
				if (value.getClass().isArray()) {
					int length = Array.getLength(value);
					if (length == 1) {
						boolean includedInSQLInjectionWhitelist = false;
						boolean includedInXSSWhitelist = false;
						if (!includedInSQLInjectionWhitelist) {
							resultMap.put(key,
									PreventAttackUtil.filterSQLInjection(Array.get(value, 0).toString()).trim());
						}
						if (!includedInXSSWhitelist) {
							if (resultMap.containsKey(key)) {
								resultMap.put(key, PreventAttackUtil.filterXSS(resultMap.get(key).toString()).trim());
							} else {
								resultMap.put(key, PreventAttackUtil.filterXSS(Array.get(value, 0).toString()).trim());
							}
						}
						if (includedInSQLInjectionWhitelist && includedInXSSWhitelist) {
							resultMap.put(key, Array.get(value, 0).toString().trim());
						}
					}
					if (length > 1) {
						resultMap.put(key, value);
					}
				} else {
					boolean includedInSQLInjectionWhitelist = false;
					boolean includedInXSSWhitelist = false;
					if (!includedInSQLInjectionWhitelist) {
						resultMap.put(key, PreventAttackUtil.filterSQLInjection(value.toString()).trim());
					}
					if (!includedInXSSWhitelist) {
						if (resultMap.containsKey(key)) {
							resultMap.put(key, PreventAttackUtil.filterXSS(resultMap.get(key).toString()).trim());
						} else {
							resultMap.put(key, PreventAttackUtil.filterXSS(value.toString()).trim());
						}
					}
					if (includedInSQLInjectionWhitelist && includedInXSSWhitelist) {
						resultMap.put(key, value.toString().trim());
					}
				}
			}
		}
		return resultMap;
	}	
	
	public ProtocolManagement getProtocolManagement() {
		return protocolManagement;
	}

	public void setProtocolManagement(ProtocolManagement protocolManagement) {
		this.protocolManagement = protocolManagement;
	}
	
	public ProtocolManagementService getProtocolManagementService() {
		return protocolManagementService;
	}
	
	public void setProtocolManagementService(ProtocolManagementService protocolManagementService) {
		this.protocolManagementService = protocolManagementService;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ValueListHandlerHelper getValueListHelper() {
		return valueListHelper;
	}

	public void setValueListHelper(ValueListHandlerHelper valueListHelper) {
		this.valueListHelper = valueListHelper;
	}
}

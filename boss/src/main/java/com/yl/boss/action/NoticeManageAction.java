package com.yl.boss.action;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.lefu.commons.utils.Page;
import com.yl.boss.Constant;
import com.yl.boss.bean.NoticeConfigBean;
import com.yl.boss.entity.Authorization;
import com.yl.boss.service.NoticeManageService;

import net.sf.json.JSONObject;

@SuppressWarnings("serial")
public class NoticeManageAction extends Struts2ActionSupport {

	@Resource
	NoticeManageService noticeManageService;
	
	private NoticeConfigBean noticeConfigBean;
	
	private Page page;
	
	private String msg;
	
	public String addNoticeConfig(){
		msg = noticeManageService.addNoticeConfig(noticeConfigBean);
		return SUCCESS;
	}
	
	public String upNoticeConfig(){
		msg = noticeManageService.upNoticeConfig(noticeConfigBean);
		return SUCCESS;
	}
	
	public String findNoticeConfig(){
		noticeConfigBean = noticeManageService.findNoticeConfig(Long.parseLong(getHttpRequest().getParameter("id")));
		return SUCCESS;
	}
	
	public String findNoticeConfigByOem(){
		noticeConfigBean = noticeManageService.findNoticeConfigByOem(getHttpRequest().getParameter("oem"));
		return SUCCESS;
	}
	
	public String findAllNoticeConfigByInfo(){
		Map<String, Object> params = retrieveParams(getHttpRequest().getParameterMap());
		page = new Page();
		int currentPage = getHttpRequest().getParameter("currentPage") == null ? 1 : Integer.parseInt(getHttpRequest().getParameter("currentPage"));
		if (currentPage > 1) {
			page.setCurrentPage(currentPage);
		}
		page.setCurrentResult((currentPage - 1) * page.getShowCount());
		params.put("totalPage", page.getTotalPage());
		params.put("currentPage", page.getCurrentPage());
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		params.put("oper", auth.getUsername());
		params.put("sys", "ylzf");
		params = noticeManageService.findAllNoticeConfigByInfo(params);
		if(params != null){
			page = (Page) JSONObject.toBean((JSONObject) params.get("page"), Page.class);
			page.setObject(params.get("noticeConfigs"));
		}
		return SUCCESS;
	}
	
	/**
	 * 群发消息
	 * @return
	 */
	public String sendAllNotice(){
		Map<String, Object> params = retrieveParams(getHttpRequest().getParameterMap());
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		params.put("oper", auth.getUsername());
		params.put("sys", "ylzf");
		
		params.put("systemCode", auth.getUsername());
		params.put("createDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		params.put("voice", "");
		params.put("extTime", String.valueOf(params.get("extTime")));
		params.put("content", JSONObject.fromObject(params.get("content")));
		msg = noticeManageService.sendAllNotice(params);
		return SUCCESS;
	}
	
	/**
	 * 参数转换
	 */
	private Map<String, Object> retrieveParams(Map<String, String[]> requestMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		for (String key : requestMap.keySet()) {
			String[] value = requestMap.get(key);
			if (value != null) {
				resultMap.put(key, Array.get(value, 0).toString().trim());
			}
		}
		return resultMap;
	}
	
	
	public NoticeConfigBean getNoticeConfigBean() {
		return noticeConfigBean;
	}

	public void setNoticeConfigBean(NoticeConfigBean noticeConfigBean) {
		this.noticeConfigBean = noticeConfigBean;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	
}

package com.yl.boss.service;

import java.util.Map;

import com.yl.boss.bean.NoticeConfigBean;

public interface NoticeManageService {

	String addNoticeConfig(NoticeConfigBean noticeConfigBean);
	
	String upNoticeConfig(NoticeConfigBean noticeConfigBean);
	
	NoticeConfigBean findNoticeConfig(Long id);
	
	NoticeConfigBean findNoticeConfigByOem(String oem);
	
	Map<String,Object> findAllNoticeConfigByInfo(Map<String, Object> params);
	
	String sendAllNotice(Map<String, Object> params);
	
}

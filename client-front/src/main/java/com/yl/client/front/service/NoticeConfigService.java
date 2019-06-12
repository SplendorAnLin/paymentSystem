package com.yl.client.front.service;

import com.lefu.commons.utils.Page;
import com.yl.client.front.model.NoticeConfig;

import java.util.List;
import java.util.Map;

public interface NoticeConfigService {
	/**
	 * 查找所有配置
	 * @return
	 */
    List<NoticeConfig> findNoticeConfigs();
    /**
     * 创建通知配置信息
     * @param noticeConfig
     */
    void createNoticeConfig(NoticeConfig noticeConfig);
    /**
     * 更新通知配置信息
     * @param noticeConfig
     */
    void updateNoticeConfig(NoticeConfig noticeConfig);
    /**
     * 根据通知查询ID
     * @param id
     * @return
     */
    NoticeConfig findNoticeConfig(long id);
    /**
     * 根据OEM查询配置信息
     * @param oem
     * @return
     */
    NoticeConfig findNoticeConfigByOem(String oem);
    /**
     * 分页查询配置信息
     * @param params
     * @param page
     * @return
     */
    List<NoticeConfig> findAllNoticeConfigByInfo(Map<String,String> params, Page page);
    /**
     * 根据所有者查询配置信息
     * @param ownerId
     * @return
     */
    List<NoticeConfig>  findNoticeConfigByAgentNo(String ownerId);
}

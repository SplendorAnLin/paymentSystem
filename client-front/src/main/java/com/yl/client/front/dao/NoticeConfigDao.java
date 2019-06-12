package com.yl.client.front.dao;

import com.lefu.commons.utils.Page;
import com.yl.client.front.enums.Status;
import com.yl.client.front.model.NoticeConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface NoticeConfigDao {
    /**
     *  创建通知配置
     * @param noticeConfig
     */
    void createNoticeConfig(NoticeConfig noticeConfig);

    /**
     * 更新通知配置
     * @param noticeConfig
     */
    void updateNoticeConfig(NoticeConfig noticeConfig);

    /**
     * 根据ID查询通知配置
     * @param id
     * @return
     */
    NoticeConfig findNoticeConfig(@Param("id")long id);

    List<NoticeConfig> findNoticeConfigs(@Param("stauts")Status stauts);

    /**
     * 根据所有者编号查询通道信息
     * @param ownerId
     * @return
     */
    List<NoticeConfig> findNoticeConfigByAgentNo(@Param("ownerId")String ownerId);

    /**
     * 根据OEM查询通知配置
     * @param oem
     * @return
     */
    NoticeConfig findNoticeConfigByOem(@Param("oem")String oem);

    /**
     * 分页查询通知配置
     * @param params
     * @param page
     * @return
     */
    List<NoticeConfig> findAllNoticeConfigByInfo(@Param("params")Map<String,String> params, @Param("page")Page page);
}

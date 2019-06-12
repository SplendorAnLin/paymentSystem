package com.yl.client.front.service.impl;

import com.lefu.commons.utils.Page;
import com.yl.client.front.dao.NoticeConfigDao;
import com.yl.client.front.enums.Status;
import com.yl.client.front.model.NoticeConfig;
import com.yl.client.front.service.NoticeConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("noticeConfigService")
public class NoticeConfigServiceImpl implements NoticeConfigService{

    private static Logger log = LoggerFactory.getLogger(NoticeConfigServiceImpl.class);
    @Resource
    NoticeConfigDao noticeConfigDao;

    @Override
    public List<NoticeConfig> findNoticeConfigs(){
        List<NoticeConfig> noticeConfigs=noticeConfigDao.findNoticeConfigs(Status.TRUE);
        return  noticeConfigs;
    }

    @Override
    public void createNoticeConfig(NoticeConfig noticeConfig) {
        noticeConfig.setStauts(Status.TRUE);
        noticeConfig.setCreateDate(new Date());
        noticeConfig.setSource("HTTP");
        try{
            noticeConfigDao.createNoticeConfig(noticeConfig);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void updateNoticeConfig(NoticeConfig noticeConfig) {
        NoticeConfig nowNoticeConfig= findNoticeConfig(noticeConfig.getId());
        nowNoticeConfig.setConfig(noticeConfig.getConfig());
        nowNoticeConfig.setName(noticeConfig.getName());
        nowNoticeConfig.setOem(noticeConfig.getOem());
        nowNoticeConfig.setOper(noticeConfig.getOper());
        nowNoticeConfig.setOwnerId(noticeConfig.getOwnerId());
        nowNoticeConfig.setStauts(noticeConfig.getStauts());
        nowNoticeConfig.setType(noticeConfig.getType());
        nowNoticeConfig.setSource("HTTP");
        noticeConfigDao.updateNoticeConfig(noticeConfig);
    }

    @Override
    public NoticeConfig findNoticeConfig(long id) {
        return noticeConfigDao.findNoticeConfig(id);
    }

    public List<NoticeConfig> findNoticeConfigByAgentNo(String ownerId){
        return noticeConfigDao.findNoticeConfigByAgentNo(ownerId);
    }

    @Override
    public NoticeConfig findNoticeConfigByOem(String oem) {
        return noticeConfigDao.findNoticeConfigByOem(oem);
    }

    @Override
    public List<NoticeConfig> findAllNoticeConfigByInfo(Map<String,String> params, Page page) {
        return noticeConfigDao.findAllNoticeConfigByInfo(params,page);
    }
}

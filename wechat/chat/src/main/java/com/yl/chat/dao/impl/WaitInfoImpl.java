package com.yl.chat.dao.impl;

import com.yl.chat.dao.WaitInfoDao;
import com.yl.chat.mapper.WaitInfoMapper;
import com.yl.chat.wecaht.model.WaitInfo;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.util.List;

@Repository("waitInfoDao")
public class WaitInfoImpl implements WaitInfoDao {

    @Resource
    private WaitInfoMapper waitInfoMapper;

    @Override
    public void saveWaitInfo(WaitInfo waitInfo) {
        waitInfoMapper.saveWaitInfo(waitInfo);
    }

    @Override
    public List<WaitInfo> findWaitInfo(String time, String status) {
        return waitInfoMapper.findWaitInfo(time, status);
    }

    @Override
    public void upWatiInfo(WaitInfo waitInfo) {
        waitInfoMapper.upWatiInfo(waitInfo);
    }
}
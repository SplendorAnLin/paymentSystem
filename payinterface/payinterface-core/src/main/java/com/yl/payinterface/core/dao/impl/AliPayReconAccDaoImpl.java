package com.yl.payinterface.core.dao.impl;

import com.lefu.commons.utils.Page;
import com.yl.payinterface.core.dao.AliPayReconAccDao;
import com.yl.payinterface.core.dao.mapper.AliPayReconAccDaoMapper;
import com.yl.payinterface.core.model.AliPayReconAcc;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository("aliPayReconAccDao")
public class AliPayReconAccDaoImpl implements AliPayReconAccDao {

    @Resource
    private AliPayReconAccDaoMapper aliPayReconAccDaoMapper;

    @Override
    public void save(AliPayReconAcc aliPayReconAcc) {
        aliPayReconAccDaoMapper.save(aliPayReconAcc);
    }

    @Override
    public List<AliPayReconAcc> findByTask() {
        return aliPayReconAccDaoMapper.findByTask();
    }

    @Override
    public void updateStatus(String acc, String status) {
        aliPayReconAccDaoMapper.updateStatus(acc, status);
    }

    @Override
    public void update(AliPayReconAcc aliPayReconAcc) {
        aliPayReconAccDaoMapper.update(aliPayReconAcc);
    }

    @Override
    public Page queryAll(Page page, Map<String, Object> map) {
        return aliPayReconAccDaoMapper.queryAll(page, map);
    }
}
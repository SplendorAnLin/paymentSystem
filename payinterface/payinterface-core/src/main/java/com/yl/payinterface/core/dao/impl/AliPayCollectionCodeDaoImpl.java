package com.yl.payinterface.core.dao.impl;

import com.yl.payinterface.core.dao.AliPayCollectionCodeDao;
import com.yl.payinterface.core.dao.mapper.AliPayCollectionCodeMapper;
import com.yl.payinterface.core.model.AliPayCollectionCode;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.util.List;

/**
 * 支付宝个人码数据访问接口实现
 * 
 * @author AnLin
 * @version V1.0.0
 * @since 2018/8/15
 */
@Repository("aliPayCollectionCodeDao")
public class AliPayCollectionCodeDaoImpl implements AliPayCollectionCodeDao {

    @Resource
    private AliPayCollectionCodeMapper aliPayCollectionCodeMapper;

    @Override
    public void save(AliPayCollectionCode aliPayCollectionCode) {
        aliPayCollectionCodeMapper.save(aliPayCollectionCode);
    }

    @Override
    public void update(AliPayCollectionCode aliPayCollectionCode) {
        aliPayCollectionCodeMapper.update(aliPayCollectionCode);
    }

    @Override
    public List<AliPayCollectionCode> queryCode(String amount) {
        return aliPayCollectionCodeMapper.queryCode(amount);
    }

    @Override
    public void updateStatus(String aliPayAcc, String status) {
        aliPayCollectionCodeMapper.updateStatus(aliPayAcc, status);
    }
}
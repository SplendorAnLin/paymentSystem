package com.yl.payinterface.core.service.impl;

import com.yl.payinterface.core.dao.AliPayCollectionCodeDao;
import com.yl.payinterface.core.model.AliPayCollectionCode;
import com.yl.payinterface.core.service.AliPayCollectionCodeService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 支付宝个人码服务接口实现
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/8/15
 */
@Service("aliPayCollectionCodeService")
public class AliPayCollectionCodeServiceImpl implements AliPayCollectionCodeService {

    @Resource
    private AliPayCollectionCodeDao aliPayCollectionCodeDao;

    @Override
    public void save(AliPayCollectionCode aliPayCollectionCode) {
        aliPayCollectionCodeDao.save(aliPayCollectionCode);
    }

    @Override
    public void update(AliPayCollectionCode aliPayCollectionCode) {
        aliPayCollectionCode.setCount(aliPayCollectionCode.getCount() + 1);
        aliPayCollectionCode.setLastTime(new Date());
        aliPayCollectionCodeDao.update(aliPayCollectionCode);
    }

    @Override
    public List<AliPayCollectionCode> queryCode(String amount) {
        return aliPayCollectionCodeDao.queryCode(amount);
    }

    @Override
    public AliPayCollectionCode getCodeByAmt(String amount) {
        return null;
    }

    @Override
    public void updateAliPayStatus(String aliPayAcc, String status) {
        aliPayCollectionCodeDao.updateStatus(aliPayAcc, status);
    }
}
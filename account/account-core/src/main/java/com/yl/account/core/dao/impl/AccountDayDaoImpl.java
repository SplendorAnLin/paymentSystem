package com.yl.account.core.dao.impl;

import com.yl.account.core.dao.AccountDayDao;
import com.yl.account.core.dao.mapper.AccountDayMapper;
import com.yl.account.core.utils.CodeBuilder;
import com.yl.account.model.AccountDay;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 商户余额表接口实现类
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年3月24日
 */
@Repository("accountDayDao")
public class AccountDayDaoImpl implements AccountDayDao {

    @Resource
    private AccountDayMapper accountDayMapper;

    /*
     * (non-Javadoc)
     * @see com.lefu.account.core.dao.AccountDayDao#insert(com.lefu.account.model.AccountDay)
     */
    @Override
    public void insert(AccountDay accountDay) {
        accountDay.setCode("ACCDAY" + CodeBuilder.getOrderIdByUUId());
        accountDay.setCreateTime(new Date());
        accountDay.setVersion(System.currentTimeMillis());
        accountDayMapper.insert(accountDay);
    }

}

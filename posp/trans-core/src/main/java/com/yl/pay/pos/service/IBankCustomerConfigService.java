package com.yl.pay.pos.service;

import com.yl.pay.pos.entity.BankChannel;
import com.yl.pay.pos.entity.BankCustomerConfig;

import java.util.List;

/**
 * Title: 银行商户路由配置服务
 * Description:
 * Copyright: Copyright (c)2012
 * Company: com.yl.pay
 *
 * @author jun
 */

public interface IBankCustomerConfigService {

    //根据接口编号、通道编号、银行商户号查找，默认取状态为“可用”的
    public List<BankCustomerConfig> findByBankInterfaceAndBankChannelAndBankCustomerNo(String bankInterface, BankChannel bankChannel, String bankCustomerNo);

    public List<BankCustomerConfig> findByBankInterfaceAndBankChannelAndCustomerNo(String bankInterface, BankChannel bankChannel, String customerNo);
    
    public List<BankCustomerConfig> findByBankInterfaceAndBankChannelAndCustomerNoAndPosCati(String bankInterface, BankChannel bankChannel, String customerNo,String posCati);

}

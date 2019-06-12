package com.yl.rate.core.test.interfaces.impl;

import com.yl.rate.core.test.BaseTest;
import com.yl.rate.interfaces.beans.RateRequestBean;
import com.yl.rate.interfaces.beans.RateResponseBean;
import com.yl.rate.interfaces.enums.InterfaceCardType;
import com.yl.rate.interfaces.enums.InterfaceOwnerRole;
import com.yl.rate.interfaces.enums.InterfaceProductType;
import com.yl.rate.interfaces.enums.InterfaceStatus;
import com.yl.rate.interfaces.interfaces.RateInterface;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author Shark
 * @Description
 * @Date 2017/12/8 17:34
 */
public class RateInterfaceTest extends BaseTest {

    @Resource
    private RateInterface rateInterface;

    @Test
    public void getFee() {
        RateRequestBean rateRequestBean = new RateRequestBean();
        rateRequestBean.setAmount(504D);
        rateRequestBean.setOwnerId("C100000");
        rateRequestBean.setOwnerRole(InterfaceOwnerRole.CUSTOMER);
        rateRequestBean.setProductType(InterfaceProductType.AUTHPAY);
        rateRequestBean.setCardType(InterfaceCardType.DEBIT);
        rateRequestBean.setStandard(InterfaceStatus.TRUE);
        RateResponseBean rateResponseBean = rateInterface.getFee(rateRequestBean);
        System.out.println(rateResponseBean);
    }
}

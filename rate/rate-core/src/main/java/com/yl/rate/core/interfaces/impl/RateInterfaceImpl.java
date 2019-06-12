package com.yl.rate.core.interfaces.impl;

import com.yl.rate.core.service.RateHandler;
import com.yl.rate.interfaces.beans.RateRequestBean;
import com.yl.rate.interfaces.beans.RateResponseBean;
import com.yl.rate.interfaces.interfaces.RateInterface;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Shark
 * @Description
 * @Date 2017/12/3 17:41
 */
@Service("rateInterface")
public class RateInterfaceImpl implements RateInterface{

    @Resource
    private RateHandler rateHandler;



    @Override
    public RateResponseBean getFee(RateRequestBean rateRequestBean) {
        return rateHandler.getFee(rateRequestBean);
    }

}

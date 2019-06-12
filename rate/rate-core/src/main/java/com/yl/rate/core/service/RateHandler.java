package com.yl.rate.core.service;

import com.yl.rate.interfaces.beans.RateRequestBean;
import com.yl.rate.interfaces.beans.RateResponseBean;

/**
 * @author Shark
 * @Description
 * @Date 2017/12/7 11:08
 */
public interface RateHandler {

    RateResponseBean getFee(RateRequestBean rateRequestBean);
}

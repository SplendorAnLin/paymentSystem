package com.yl.rate.interfaces.interfaces;

import com.yl.rate.interfaces.beans.RateRequestBean;
import com.yl.rate.interfaces.beans.RateResponseBean;

/**
 * @author Shark
 * @Description
 * @Date 2017/12/8 17:18
 */
public interface RateInterface {
    /**
     * 获取手续费
     * @param rateRequestBean
     * @return
     */
    RateResponseBean getFee(RateRequestBean rateRequestBean);

}

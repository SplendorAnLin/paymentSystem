package com.yl.boss.service;

/**
 * Service异常接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public class ServiceException extends RuntimeException {
    
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable ex) {
        super(ex);
    }
    
    public ServiceException(String message,Throwable ex) {
        super(message,ex);
    }
    
}

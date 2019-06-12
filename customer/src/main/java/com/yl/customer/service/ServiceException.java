package com.yl.customer.service;

/**
 * Service异常
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月20日
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

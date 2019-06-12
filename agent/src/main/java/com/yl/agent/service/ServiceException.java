package com.yl.agent.service;

/**
 * Service异常
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月13日
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

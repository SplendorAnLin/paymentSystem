package com.yl.agent.dao;

/**
 * DAO异常
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月1日
 * @version V1.0.0
 */
public class DAOException extends RuntimeException {
    
	public static final String OBJECT_NOT_NULL = "object.not.null";

	
    public DAOException(String message) {
        super(message);
    }

    public DAOException(Throwable ex) {
        super(ex);
    }
    
    public DAOException(String message,Throwable ex) {
        super(message,ex);
    }
    
}

package com.yl.dpay.core.mybatis.Exception;

import org.apache.ibatis.exceptions.PersistenceException;

/**
 * 乐观锁异常
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月28日
 * @version V1.0.0
 */
public class OptimisticLockException extends PersistenceException{

	private static final long serialVersionUID = -5924129894255646155L;

}

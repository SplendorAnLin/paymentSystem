package com.yl.boss.api.interfaces;

import java.util.Map;

import com.lefu.commons.utils.Page;


/**
 * Mcc信息远程接口
 * 
 * @author 聚合支付有限公司
 * @since 2017年6月20日
 * @version V1.0.0
 */
public interface MccInterface {

	/**
	 * 根据条件查询，返回Page
	 * @param params
	 * @return
	 */
	Page mccInfoQuery(Map<String,Object> params);
}

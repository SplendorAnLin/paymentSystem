package com.yl.boss.api.interfaces;

import java.util.Map;

import com.lefu.commons.utils.Page;

/**
 * 入网协议远程服务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月19日
 * @version V1.0.0
 */
public interface ProtocolInterface {
	
	/**
	 * 条件查询协议管理
	 * @param param
	 * @return
	 */
	Page getProtolList(Map<String,Object> params);
	
	
	/**
	 * wap 入网协议获取
	 */
	Map<String, Object> wapProtocol(String sort, String type);
	
	
	/**
	 * 根据sort 类型和id查询
	 */ 
	Map<String, Object> selectWechathelp(String sort, int id,String status);
	
}
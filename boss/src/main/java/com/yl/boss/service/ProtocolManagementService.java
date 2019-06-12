package com.yl.boss.service;

import java.util.Map;

import com.yl.boss.entity.ProtocolManagement;

/**
 * 协议管理业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public interface ProtocolManagementService {
	/**
	 * 根据Id查询文档信息
	 */
	ProtocolManagement getprotolById(Long id);
	/**
	 * 根据Id查询标题文档信息
	 */
	ProtocolManagement getprotolByTitle(String title);
	/**
	 * 修改文档信息
	 */
	void updateProtol(ProtocolManagement pro);
	/**
	 * 新增文档信息
	 */
	void insert(ProtocolManagement pro);
	/**
	 * wap扫码入网协议获取
	 */
	Map<String, Object> wapProtocol(String sort, String type);
	
	/**
	 * 根据sort 类型和id查询
	 */ 
	Map<String, Object> selectWechathelp(String sort, int id,String status);
}

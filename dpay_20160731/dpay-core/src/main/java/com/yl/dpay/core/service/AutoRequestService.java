package com.yl.dpay.core.service;

import java.util.List;
import java.util.Map;

import com.yl.dpay.core.entity.AutoRequest;
import com.yl.dpay.core.enums.OwnerRole;

/**
 * 自动发起service
 *
 * @author AnLin
 * @since 2017年4月24日
 */
public interface AutoRequestService {

	/**
	 * @param autoRequest
	 */
	public void insert(AutoRequest autoRequest);

	/**
	 * @param id
	 * @return
	 */
	public AutoRequest findById(Long id);

	/**
	 * @param autoRequest
	 */
	public void update(AutoRequest autoRequest);
	
	/**
	 * @param autoRequest
	 */
	public void handle(AutoRequest autoRequest);
	
	/**
	 * @param autoRequests
	 */
	public void apply(List<AutoRequest> autoRequests);
	
	/**
	 * 查询待结算商户
	 * @return List<Map<String, String>> 持有者编号, 持有者角色
	 */
	public List<Map<String, String>> findAllWait(); 
	
	/**
	 * 根据持有者信息查询待结算订单
	 * @param ownerId
	 * @param ownerRole
	 * @return
	 */
	public List<AutoRequest> findWaitByOwner(String ownerId, OwnerRole ownerRole);
	
}

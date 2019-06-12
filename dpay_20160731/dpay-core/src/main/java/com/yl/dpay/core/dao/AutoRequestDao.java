package com.yl.dpay.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yl.dpay.core.entity.AutoRequest;
import com.yl.dpay.core.enums.OwnerRole;


/**
 * 自动发起dao
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
public interface AutoRequestDao extends BaseDao<AutoRequest>{
	
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
	public List<AutoRequest> findWaitByOwner(@Param("ownerId")String ownerId, @Param("ownerRole")OwnerRole ownerRole);

}

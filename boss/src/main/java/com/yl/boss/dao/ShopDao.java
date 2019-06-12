package com.yl.boss.dao;

import java.util.List;

import com.yl.boss.entity.Shop;

/**
 * 网点数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2017年6月15日
 * @version V1.0.0
 */
public interface ShopDao {

	/**
	 * 网点信息新增
	 * @param shop
	 */
	void shopAdd(Shop shop);
	
	/**
	 * 根据ID查询单条网点信息
	 * @param id
	 * @return
	 */
	Shop shopById(Long id);
	
	/**
	 * 根据商户编号查询对应的最高网点编号
	 * @param customerNo
	 * @return
	 */
	String queryMaxShopNo(String customerNo);
	
	/**
	 * 网点信息修改
	 * @param shop
	 */
	void ShopUpdate(Shop shop);
	
	/**
	 * 根据商户编号查询旗下所有网点信息
	 * @param customerNo
	 * @return
	 */
	List<Shop> queryShopList(String customerNo);
	
	/**
	 * 根据网点编号查询单条网点信息
	 * @param shopNo
	 * @return 网点信息Bean
	 */
	Shop queryByShopNo(String shopNo);
	/**
	 * 查询待同步信息
	 * @return
	 */
	List<Shop> findSyncShop();
	
	/**
	 * 根据网点编号查询网点名称
	 * @param shopNo
	 * @return
	 */
	String queryShopNameByShopNo(String shopNo);
	
	/**
	 * 根据商户编号查询旗下所有网点编号
	 * @param customerNo
	 * @return
	 */
	List<String> queryShopNoByShopCustNo(String customerNo);
	
	/**
	 * 根据网点编号删除当前网点
	 * @param shopNo
	 */
	void shopDelByShopNo(Shop shop);
	
	/**
	 * 根据商户编号查询单条网点信息
	 * @param customerNo
	 * @return
	 */
	Shop queryShopByCustNo(String customerNo);
	
}

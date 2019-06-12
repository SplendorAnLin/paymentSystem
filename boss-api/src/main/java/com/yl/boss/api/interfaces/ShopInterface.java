package com.yl.boss.api.interfaces;

import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.boss.api.bean.Shop;

/**
 * 网点信息信息远程接口
 * 
 * @author 聚合支付有限公司
 * @since 2017年6月20日
 * @version V1.0.0
 */         
public interface ShopInterface {

	/**
	 * 根据条件查询网点信息
	 * @param params
	 * @return
	 */
	Page shopQuery(Map<String,Object> params);
	
	/**
	 * 根据商户编号查询旗下所有网点信息
	 * @param customerNo
	 * @return
	 */
	List<Shop> queryShopList(String customerNo);
}

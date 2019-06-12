package com.yl.boss.api.interfaces;

import java.util.List;
import java.util.Map;

import com.yl.boss.api.bean.Ad;


/**
 * 广告远程接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月19日
 * @version V1.0.0
 */
public interface AdInterface {

	/**
	 * 根据条件查询
	 * @param m 查询条件
	 * @return 广告List
	 */
	public Map<String, Object> query(String oem);
	/**
	 * APP欢迎页广告查询
	 * @return
	 */
	public List findAdByType(String type,String oem);
	/**
	 * 根据广告Bean进行新增操作
	 * @param ad 广告Bean
	 */
	public boolean create(Ad ad);
	
	/**
	 * 根据广告Bean进行修改操作
	 * @param ad 广告Bean
	 */
	public boolean updateAd(Ad ad);
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public Ad fingAdById(int id);
	/**
	 * 根据oem查询服务商信息
	 * @param oem
	 * @return
	 */
	public String findAgentByOem(String oem);
	/**
	 * 根据代理商编号查询匹配信息
	 * @return
	 */
	public String findAgentByAgentNo(String agentNo);
}

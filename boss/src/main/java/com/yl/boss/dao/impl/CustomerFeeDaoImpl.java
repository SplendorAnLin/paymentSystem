package com.yl.boss.dao.impl;

import java.util.List;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.enums.ProductType;
import com.yl.boss.dao.CustomerFeeDao;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.dao.SyncInfoDao;
import com.yl.boss.entity.CustomerFee;
import com.yl.boss.enums.Status;
import com.yl.boss.enums.SyncType;

/**
 * 商户费率数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public class CustomerFeeDaoImpl implements CustomerFeeDao {
	
	private EntityDao entityDao;
	private SyncInfoDao syncInfoDao;
	
	@Override
	public void create(CustomerFee customerFee) {
		entityDao.persist(customerFee);
		syncInfo(customerFee, Status.TRUE);
	}

	@Override
	public void update(CustomerFee customerFee) {
		entityDao.update(customerFee);
		syncInfo(customerFee, Status.FALSE);
	}
	
	@Override
	public void delete(CustomerFee customerFee) {
		entityDao.delete(customerFee);
	}
	
	/**
	 * 插入POS费率同步信息
	 * @param customerFee
	 * @param isUpdate
	 */
	public void syncInfo(CustomerFee customerFee,Status isCreate){
		if (customerFee.getProductType()==ProductType.POS) {
			syncInfoDao.syncInfoAddFormPosp(SyncType.FEE_SYNC,JsonUtils.toJsonString(customerFee),isCreate);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public CustomerFee findBy(String custNo, ProductType productType) {
		String hql = "from CustomerFee where customerNo = ? and productType = ?";
		List<CustomerFee> list = entityDao.find(hql,custNo,productType);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerFee> findByCustNo(String custNo) {
		String hql = "from CustomerFee where customerNo = ?";
		return entityDao.find(hql,custNo);
	}
	
	@Override
	public CustomerFee findById(long id) {
		return entityDao.findById(CustomerFee.class, id);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	public SyncInfoDao getSyncInfoDao() {
		return syncInfoDao;
	}

	public void setSyncInfoDao(SyncInfoDao syncInfoDao) {
		this.syncInfoDao = syncInfoDao;
	}

	

}

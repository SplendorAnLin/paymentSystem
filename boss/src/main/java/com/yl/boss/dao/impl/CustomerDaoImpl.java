package com.yl.boss.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.enums.ProductType;
import com.yl.boss.enums.Status;
import com.yl.boss.enums.SyncType;
import com.yl.boss.dao.CustomerDao;
import com.yl.boss.dao.CustomerFeeDao;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.dao.SyncInfoDao;
import com.yl.boss.entity.Customer;
import com.yl.boss.entity.CustomerFee;
import com.yl.boss.enums.CustomerStatus;

/**
 * 商户信息数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public class CustomerDaoImpl implements CustomerDao {

	private EntityDao entityDao;
	private SyncInfoDao syncInfoDao;
	private CustomerFeeDao customerFeeDao;
	
	/**
	 * 同步商户信息到posp
	 * @param customer
	 */
	public void syncCustomer(Customer customer,Status isCreate){
		CustomerFee fee=customerFeeDao.findBy(customer.getCustomerNo(), ProductType.POS);
		if (fee!=null&&(CustomerStatus.TRUE==customer.getStatus()||CustomerStatus.FALSE==customer.getStatus())) {//代表有pos费率信息并且开通或关闭状态的商户
			syncInfoDao.syncInfoAddFormPosp(SyncType.CUSTOMER_SYNC, JsonUtils.toJsonString(customer), isCreate);
		}
	}

	@Override
	public void create(Customer customer) {
		entityDao.persist(customer);
		syncCustomer(customer, Status.TRUE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Customer findByNo(String customerNo) {
		String hql = "from Customer where customerNo = ?";
		List<Customer> list = entityDao.find(hql, customerNo);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Customer findByFullName(String fullName) {
		String hql = "from Customer where fullName = ?";
		List<Customer> list = entityDao.find(hql, fullName);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Customer findByShortName(String shortName) {
		String hql = "from Customer where shortName = ?";
		List<Customer> list = entityDao.find(hql, shortName);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void update(Customer customer) {
		entityDao.update(customer);
		syncCustomer(customer, Status.FALSE);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> findByAgentNo(String AgentNo) {
		String hql = "from Customer where Agent_No = ?";
		return entityDao.find(hql, AgentNo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getMaxCustomerNo() {
		String hql = "select max(customerNo) from Customer";
		List<String> list = entityDao.find(hql);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Customer findByPhone(String phone) {
		String hql = "from Customer where phoneNo = ?";
		List<Customer> list = entityDao.find(hql, phone);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map<String, String> checkCustomerName(String fullName, String shortName) {
		Map<String,String> m = new HashMap<String, String>();
		try {
			if(fullName != null){
				String fullNameHql = "select COUNT(*) from Customer where fullName = ?";
				List<String> fullList = entityDao.find(fullNameHql,fullName);
				if (fullList != null && JsonUtils.toJsonToObject(fullList.get(0), Integer.class) > 0) {
					m.put("fullName", "TRUE");
				}else {
					m.put("fullName", "FALSE");
				}
			}
		} catch (Exception e) {
			m.put("message", e.getMessage());
		}
		
		try {
			if(shortName != null){
				String shortNameHql = "select COUNT(*) from Customer where shortName = ?";
				List<String> shortList = entityDao.find(shortNameHql,shortName);
				if (shortList != null && JsonUtils.toJsonToObject(shortList.get(0), Integer.class) > 0) {
					m.put("shortName", "TRUE");
				}else {
					m.put("shortName", "FALSE");
				}
			}
		} catch (Exception e) {
			m.put("message", e.getMessage());
		}
		return m;
	}
	
	@Override
	public String findShortNameByCustomerNo(String customerNo) {
		String hql = "select shortName from Customer where customerNo = ?";
		List list = entityDao.find(hql, customerNo);
		if(list != null){
			return (String) list.get(0);
		}
		return null;
	}
	
	@Override
    public String queryAgentNoByCustomerNo(String customerNo) {
        String hql = "select agentNo from Customer where customerNo = ?";
        List list = entityDao.find(hql, customerNo);
        if(list != null && list.size() > 0){
            return (String) list.get(0);
        }
        return null;
    }

	public SyncInfoDao getSyncInfoDao() {
		return syncInfoDao;
	}

	public CustomerFeeDao getCustomerFeeDao() {
		return customerFeeDao;
	}

	public void setSyncInfoDao(SyncInfoDao syncInfoDao) {
		this.syncInfoDao = syncInfoDao;
	}

	public void setCustomerFeeDao(CustomerFeeDao customerFeeDao) {
		this.customerFeeDao = customerFeeDao;
	}

	@Override
	public String findCustomerCardByNo(String customerNo) {
		String hql = "select idCard from Customer where customerNo = ?";
		List list = entityDao.find(hql, customerNo);
		if(list != null){
			return (String) list.get(0);
		}
		return null;
	}

	@Override
	public boolean customerNoBool(String customerNo) {
		String hql = "from Customer where customerNo = ?";
		List list = entityDao.find(hql, customerNo);
		if(list != null && list.size() > 0){
			return true;
		}
		return false;
	}

	@Override
	public String fullNameByCustNo(String customerNo) {
		String hql = "select fullName from Customer where customerNo = ?";
		List list = entityDao.find(hql,customerNo);
		if(list != null && list.size() > 0){
			return (String)list.get(0);
		}
		return null;
	}

}

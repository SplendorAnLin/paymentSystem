package com.yl.boss.dao.impl;

import java.util.List;
import java.util.Map;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.api.enums.ProcessStatus;
import com.yl.boss.dao.DeviceDao;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.entity.Device;
import com.yl.boss.entity.DeviceConfig;

/**
 * 设备数据接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public class DeviceDaoImpl implements DeviceDao{
	private EntityDao entityDao;
	
	@Override
	public Map findDecIvestate(Map<String, Object> params) {
		String sql="SELECT sum(CASE WHEN d.STATUS='ALLOT' THEN 1 ELSE 0 END) as ALLOT,sum(CASE WHEN d.STATUS='BINDED' THEN 1 ELSE 0 END) as BINDED,sum(CASE WHEN d.STATUS='MAKING' THEN 1 ELSE 0 END) as MAKING,sum(CASE WHEN d.STATUS='ORDER_WAIT' THEN 1 ELSE 0 END) as ORDER_WAIT,sum(CASE WHEN d.STATUS='ORDER_CONFIRM' THEN 1 ELSE 0 END) as ORDER_CONFIRM,sum(CASE WHEN d.STATUS='ORDER_FAIL' THEN 1 ELSE 0 END) as ORDER_FAIL,sum(CASE WHEN d.STATUS='OK' THEN 1 ELSE 0 END) as OK FROM  Device d";
		return  Devicetions(sql,params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String getConfigKey() {
		String hql = "from DeviceConfig";
		List<DeviceConfig> list = entityDao.find(hql);
		if(list != null && list.size() > 0){
			return list.get(0).getConfigKey();
		}
		return null;
	}
	
	/**
	 * 查看合计的条件方法
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private Map Devicetions(String sql, Map<String, Object> params) {
		StringBuffer hql = new StringBuffer(" where 1=1");
		if (params.get("activate_time_start") != null && (!params.get("activate_time_start").equals(""))) {
			hql.append(" and activatetime >= '" + params.get("activate_time_start").toString() + "'");
		}
		if (params.get("activate_time_end") != null && (!params.get("activate_time_end").equals(""))) {
			hql.append(" and activatetime <= '" + params.get("activate_time_end").toString() + "'");
		}
		if (params.get("create_time_start") != null && (!params.get("create_time_start").equals(""))) {
			hql.append(" and createtime >= '" + params.get("create_time_start").toString() + "'");
		}
		if (params.get("activate_time_end") != null && (!params.get("activate_time_end").equals(""))) {
			hql.append(" and createtime <= '" + params.get("activate_time_end").toString() + "'");
		}
		if (params.get("agent_no") != null && (!params.get("agent_no").equals(""))) {
			hql.append(" and agentno = '" + params.get("agent_no").toString() + "'");
		}
		if (params.get("customer_pay_no") != null && (!params.get("customer_pay_no").equals(""))) {
			hql.append(" and customerpayno = '" + params.get("customer_pay_no").toString() + "'");
		}
		if (params.get("status") != null && (!params.get("status").equals(""))) {
			hql.append(" and status = '" + params.get("status").toString() + "'");
		}
		if (params.get("batch_number") != null && (!params.get("batch_number").equals(""))) {
			hql.append(" and batchnumber = '" + params.get("batch_number").toString() + "'");
		}
		if (params.get("customer_no") != null && (!params.get("customer_no").equals(""))) {
			hql.append(" and customerno = '" + params.get("customer_no").toString() + "'");
		}
		if (params.get("purchase_serial_number") != null && (!params.get("purchase_serial_number").equals(""))) {
			hql.append(" and purchaseserialnumber = '" + params.get("purchase_serial_number").toString() + "'");
		}
		
		sql += hql;
		Object o=entityDao.find(sql.toString());
		
		return (Map) JsonUtils.toJsonToObject(o, Map.class);
	}
	@Override
	public void create(Device device) {
		entityDao.persist(device);
	}

	@Override
	public void update(Device device) {
		entityDao.update(device);
	}

	@Override
	public Device findById(long id) {
		return entityDao.findById(Device.class,id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Device findByPayId(String cardNo) {
		String hql = "from Device where customerPayNo = ?";
		List<Device> list = entityDao.find(hql,cardNo);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Device> findByCardNo(String cardNo) {
		String hql = "from Device where customerNo = ? ";
		List<Device> list = entityDao.find(hql,cardNo);
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Device> findDeviceByPsn(String purchaseSerialNumber) {
		String hql = "from Device where purchaseSerialNumber = ? ";
		return entityDao.find(hql,purchaseSerialNumber);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Device> findDeviceBySize(int size,Long type) {
		try {
			String hql = "from Device where agentNo is null and status='OK' and deviceTypeId=? order by createTime asc limit 0,"+size;
			return entityDao.find(hql,type);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String getMaxDeviceNo() {
		String hql = "select max(customerPayNo) from Device where to_days(createTime)=to_days(now())";//当天最大
		List<String> list = entityDao.find(hql);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Device> findByBatchNo(String batchNo) {
		String hql = "from Device where batchNumber = ?";
		return entityDao.find(hql,batchNo);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Device findDevice() {
		String hql = "from Device where status='OK' order by createTime asc";
		List<Device> list = entityDao.find(hql);
		Device device = new Device();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				device = list.get(i);
				if (device.getAgentNo() == null) {
					device.setStatus(ProcessStatus.LOCKING);
					entityDao.update(device);
					return device;
				}
			}
		}
		return null;
	}
}
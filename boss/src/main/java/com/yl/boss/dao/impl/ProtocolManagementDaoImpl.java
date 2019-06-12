package com.yl.boss.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yl.boss.dao.EntityDao;
import com.yl.boss.dao.ProtocolManagementDao;
import com.yl.boss.entity.ProtocolManagement;

/**
 * 协议管理数据接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public class ProtocolManagementDaoImpl implements ProtocolManagementDao {
	private EntityDao entityDao;
	
	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	@Override
	public ProtocolManagement getprotolById(Long id) {
		return entityDao.findById(ProtocolManagement.class,id);
	}

	@Override
	public void updateProtol(ProtocolManagement pro) {
		entityDao.update(pro);
	}

	@Override
	public void insert(ProtocolManagement pro) {
		entityDao.save(pro);
	}
	
	@SuppressWarnings("unchecked")
	public ProtocolManagement getprotolByTitle(String title){
		String sql="from ProtocolManagement where title=?";
		List<ProtocolManagement> list=entityDao.find(sql,title);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> wapProtocol(String sort, String type) {
		String hql = "from ProtocolManagement where sort = ? and type = ? order by id desc";
		List<ProtocolManagement> list = entityDao.find(hql,sort,type);
		if(list != null && list.size() > 0){
			Map<String, Object> map = new HashMap<>();
			map.put("title", list.get(0).getTitle());
			map.put("content", list.get(0).getContent());
			return map;
		}
		return null;
	}



	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectWechathelp(String sort, int id,String status) {
		String hql = "from ProtocolManagement where sort = ? and id = ? and status = ? order by id desc";
		List<ProtocolManagement> list = entityDao.find(hql,sort,id,status);
		if(list != null && list.size() > 0){
			Map<String, Object> map = new HashMap<>();
			map.put("title", list.get(0).getTitle());
			map.put("content", list.get(0).getContent());
			return map;
		}
		return null;
	}
}
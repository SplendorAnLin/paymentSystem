package com.yl.boss.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.dao.AgentDao;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.entity.Agent;

/**
 * 服务商信息数据访问
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public class AgentDaoImpl implements AgentDao {
	
	private EntityDao entityDao;

	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> findAgentByParen(String paren) {
		String hql = "from Agent where parenId = ?";
		List<Agent> list = entityDao.find(hql,paren);
		if(list != null && list.size() > 0){
			List<String> all = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				all.add(list.get(i).getAgentNo());
			}
			return all;
		}
		return null;
	}
	
	@Override
	public void create(Agent agent) {
		entityDao.persist(agent);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Agent findByNo(String agentNo) {
		String hql = "from Agent where agentNo = ?";
		List<Agent> list = entityDao.find(hql,agentNo);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Agent findByFullName(String fullName) {
		String hql = "from Agent where fullName = ?";
		List<Agent> list = entityDao.find(hql,fullName);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Agent findByPhone(String phone) {
		String hql = "from Agent where phoneNo = ?";
		List<Agent> list = entityDao.find(hql,phone);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Agent findByShortName(String shortName) {
		String hql = "from Agent where shortName = ?";
		List<Agent> list = entityDao.find(hql,shortName);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void update(Agent agent) {
		entityDao.update(agent);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getMaxAgentNo() {
		String hql = "select max(agentNo) from Agent";
		List<String> list = entityDao.find(hql);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	@Override
	public Map<String, String> checkAgentName(String fullName, String shortName) {
		Map<String,String> m = new HashMap<String, String>();
		try {
			if(fullName != null){
				String fullNameHql = "select COUNT(*) from Agent where fullName = ?";
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
				String shortNameHql = "select COUNT(*) from Agent where shortName = ?";
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
	public int queryAgentLevelByAgentNo(String agentNo) {
		String hql = "select agentLevel from Agent where agentNo = ?";
		return Integer.parseInt(JsonUtils.toJsonString(entityDao.find(hql, agentNo).get(0)));
	}

	@Override
	public List<String> findAllCustomerNoByAgentNo(String agentNo) {
		String hql = "select customerNo from Customer where agentNo = ?";
		List<String> list = entityDao.find(hql, agentNo);
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
	
	@Override
    public String queryAgentFullNameByAgentNo(String agentNo) {
        String hql = "select fullName from Agent where agentNo = ?";
        List list = entityDao.find(hql, agentNo);
        if(list != null && list.size() > 0){
            return (String) list.get(0);
        }
        return null;
    }

    @Override
    public String queryParenIdByAgentNo(String agentNo) {
        String hql = "select parenId from Agent where agentNo = ?";
        List list = entityDao.find(hql, agentNo);
        if(list != null && list.size() > 0){
            return (String) list.get(0);
        }
        return null;
    }

	@Override
	public String queryAgentShortNameByAgentNo(String agentNo) {
		String hql = "select shortName from Agent where agentNo = ?";
		List list = entityDao.find(hql, agentNo);
        if(list != null && list.size() > 0){
            return (String) list.get(0);
        }
		return null;
	}
}
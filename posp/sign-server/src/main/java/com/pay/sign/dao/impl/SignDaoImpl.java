package com.pay.sign.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.stereotype.Component;

import com.pay.sign.dao.ISignDao;
import com.pay.sign.dao.util.EntityDao;
import com.pay.sign.dbentity.SignPic;

@Component("signDao")
public class SignDaoImpl implements ISignDao{

	/**posp_boss数据库*/
	@Resource
	private EntityDao entityDaoSign;

	

	public EntityDao getEntityDaoSign() {
		return entityDaoSign;
	}

	public void setEntityDaoSign(EntityDao entityDaoSign) {
		this.entityDaoSign = entityDaoSign;
	}

	@Override
	public void createSignPic(SignPic signPic) {
		
		entityDaoSign.save(signPic);
	}

	@Override
	public boolean isExist(Long orderId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SignPic.class).
				add(Property.forName("orderId").eq(orderId));

		List<SignPic> list = entityDaoSign.findByCriteria(criteria, 0,2);
		if(list == null || list.isEmpty()){
			return false;
		}else{
			return true;
		}
	}

}

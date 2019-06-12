package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.BankChannelDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.BankChannel;
import com.yl.pay.pos.enums.BankBillType;
import com.yl.pay.pos.enums.BankConnectType;
import com.yl.pay.pos.enums.Status;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Title: BankChannelDaoImpl
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class BankChannelDaoImpl implements BankChannelDao{

	private EntityDao entityDao;
	
	public BankChannel create(BankChannel bankChannel) {
		entityDao.persist(bankChannel);
		return bankChannel;
	}

	public BankChannel findById(Long id) {
		return entityDao.findById(BankChannel.class, id);
	}

	public BankChannel update(BankChannel bankChannel) {
		return entityDao.merge(bankChannel);
	}
	
	@Override
	public BankChannel findByCode(String code) {
		String hql="from BankChannel where code=? ";
		return entityDao.findUnique(BankChannel.class, hql, code);
	}

	@Override
	public List<BankChannel> findBankChannel(String issuer,List<String> rateAssortCodeList,String cardType,Status status,
			Boolean testFlag,String code) {
		
		List<BankChannel> list=null;
		StringBuffer rateAssortBuf=new StringBuffer();
		if(rateAssortCodeList.isEmpty()){
			return null;
		}
		
		rateAssortCodeList.add("ALL");
		for(int i=0;i<rateAssortCodeList.size();i++){
			rateAssortBuf.append("'"+rateAssortCodeList.get(i)+"'");
			if(rateAssortCodeList.size()-i>1){
				rateAssortBuf.append(",");
			}
		}
		
		String hql="from BankChannel b where b.status=? and (b.supportCardType=? or b.supportCardType='ALL') " +
				"  and b.rateAssortCode in(" + rateAssortBuf.toString() + ")" +
				"  and ((b.supportIssuerType='OWN2OWN' and b.bank.code=? ) or " +
				"  (b.supportIssuerType='OWN2OTHER' and b.bank.code!=?) or b.supportIssuerType='ALL')";
		
		if(StringUtils.isNotBlank(code)&&testFlag!=null){
			hql+=" and b.testFlag=? and b.code=? ";
			return entityDao.find(hql,status,cardType,issuer,issuer,testFlag,code);
		}else if(testFlag!=null){
			hql+=" and b.testFlag=? ";
			return entityDao.find(hql,status,cardType,issuer,issuer,testFlag);
		}else if(StringUtils.isNotBlank(code)){
			hql+=" and b.code=? ";
			list=entityDao.find(hql,status,cardType,issuer,issuer,code);
			return list;
		}
		
		return entityDao.find(hql,status,cardType,issuer,issuer);
	}
	
	@Override
	public List<BankChannel> findBankChannel(String code, String bankInterface,
			BankConnectType bankConnectType, BankBillType bankBillType,
			String mccCategory) {
		String hql="from BankChannel b where b.status=? ";
		return entityDao.find(hql, Status.TRUE);
	}
	
	@Override
	public List<BankChannel> findBankChannel(String code) {
		String hql="from BankChannel b where b.status=? and b.code=?";
		return entityDao.find(hql, Status.TRUE,code);
	}

	@Override
	public List<BankChannel> findAllActiveByConnectTypeAndBillType(
			BankConnectType bankConnectType, BankBillType bankBillType) {
		String hql="from BankChannel b where b.status=? and b.bankConnectType=? and b.bankBillType=? ";
		return entityDao.find(hql, Status.TRUE,bankConnectType,bankBillType);
	}

	@Override
	public List<BankChannel> findAllActiveByConnectType(BankConnectType bankConnectType) {
		String hql="from BankChannel b where b.status=? and b.bankConnectType=? ";
		return entityDao.find(hql, Status.TRUE,bankConnectType);
	}

	@Override
	public List<BankChannel> findAllActiveByBillType(BankBillType bankBillType) {
		String hql="from BankChannel b where b.status=? and b.bankBillType=? ";
		return entityDao.find(hql, Status.TRUE,bankBillType);
	}

	@Override
	public List<BankChannel> findAllActive() {
		String hql="from BankChannel b where b.status=? ";
		return entityDao.find(hql, Status.TRUE);
	}

	@Override
	public List<BankChannel> findActiveByBankInterfaceAndBillTypeAndMccCategory(
			String bankInterface, BankBillType bankBillType, String mccCategory) {
		String hql="from BankChannel b where b.bankInterface.code=? and b.bankBillType=? and b.mccCategory=? and b.status=? ";
		return entityDao.find(hql, bankInterface,bankBillType,mccCategory,Status.TRUE);
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}


}


package com.yl.pay.pos.service.impl;

import com.pay.common.util.SequenceUtils;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.dao.*;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.*;
import com.yl.pay.pos.enums.RunStatus;
import com.yl.pay.pos.enums.Status;
import com.yl.pay.pos.interfaces.P360001.Constant;
import com.yl.pay.pos.service.ITransPayService;
import com.yl.pay.pos.service.PosService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PosServiceImpl implements PosService {
	private static final Log log = LogFactory.getLog(PosServiceImpl.class);
	
	private PosDao posDao;
	
	public EntityDao entityDao;
	
	private CustomerDao customerDao;
	
	private ShopDao shopDao;
	
	private SecretKeyDao secretKeyDao;
	
	private ITransPayService transPayService;

	public Map<String,String> handleP001(String posSn,String posType) {
		
		Pos posDb = posDao.findByPosSn(posSn);
		Map<String,String> result = new HashMap<String,String>();
		if(posDb!=null){
			result.put("posCati", posDb.getPosCati());
			result.put("code", "00");
			return result;
		}
		
		Pos pos = new Pos();
		//随机生成8位pos终端号
		pos.setPosSn(posSn);
		pos.setPosType(posType);
		pos.setStatus(Status.TRUE);
		pos.setCreateTime(new Date());
		Customer customers = customerDao.findByCustomerNo("8619103854");
		pos.setCustomer(customers);
		Shop shop = shopDao.findById(1l);
		pos.setShop(shop);
		pos.setMKey("09B9E5967B053803");
		pos.setParamVersion("V0000000000");
		pos.setSoftVersion("V0000000000");
		pos.setType("NEW8210");
		pos.setPosBrand("HZR");
		pos.setRunStatus(RunStatus.SIGN_IN);
		pos.setBatchNo("000001");
		pos=posDao.create(pos);
		SecretKey key=new SecretKey();
		key.setKeyName("pos."+pos.getPosCati()+".zmk");
		key.setKey("5EAE19A86AEA9E40");
		key.setCheckValue("A8795C4590A31E24");
		secretKeyDao.create(key);
		SecretKey key1=new SecretKey();
		key1.setKeyName("pos."+pos.getPosCati()+".zak");
		key1.setKey("5EAE19A86AEA9E40");
		key1.setCheckValue("A8795C4590A31E24");
		secretKeyDao.create(key1);
		SecretKey key2=new SecretKey();
		key2.setKeyName("pos."+pos.getPosCati()+".zpk");
		key2.setKey("5EAE19A86AEA9E40");
		key2.setCheckValue("A8795C4590A31E24");
		secretKeyDao.create(key2);
		result.put("posCati", pos.getPosCati());
		result.put("code", "00");
		return result;
	}
	
	public UnionPayBean handleT001(String posSn) {
		
		ExtendPayBean extendPayBean=new ExtendPayBean();
		UnionPayBean unionPayBean=new UnionPayBean();
		unionPayBean.setMti("0800");
		unionPayBean.setSystemsTraceAuditNumber(generateSequence(Constant.SEQ_SYSTEM_TRACKE_NUMBER));
		Pos posDb = posDao.findByPosSn(posSn);
		unionPayBean.setCardAcceptorTerminalId(posDb.getPosCati());
		Shop shop = shopDao.findById(posDb.getShop().getId());
		unionPayBean.setCardAcceptorId(shop.getShopNo());
		unionPayBean.setUpdateFlag("00");
		unionPayBean.setBatchNo("000001");
		unionPayBean.setMsgTypeCode("00");
		unionPayBean.setNetMngInfoCode("001");
		extendPayBean.setUnionPayBean(unionPayBean);
		extendPayBean=transPayService.transPay(extendPayBean);
		
		return extendPayBean.getUnionPayBean();
	}

	public SequenceDao sequenceDao;
	
	public String generateSequence(String seqSystemTrackeNumber){		
		return sequenceDao.generateSequence(seqSystemTrackeNumber);
	}

	public SequenceDao getSequenceDao() {
		return sequenceDao;
	}

	public void setSequenceDao(SequenceDao sequenceDao) {
		this.sequenceDao = sequenceDao;
	}
	
	public PosDao getPosDao() {
		return posDao;
	}

	public void setPosDao(PosDao posDao) {
		this.posDao = posDao;
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
	
	/**
     * 生成pos终端号
     * @return
     * @author HuangNanJian   2015年5月8日 下午4:52:51
     */
	public String createPosCati(long poscatiSeq){
		return  SequenceUtils.createSequence(poscatiSeq , new int[] {8,0,8,5,9,3,8,0}, new int[] {4,5}, new int[] {6,8}, new int[] {7,8});
	}

	public CustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public ShopDao getShopDao() {
		return shopDao;
	}

	public void setShopDao(ShopDao shopDao) {
		this.shopDao = shopDao;
	}

	public SecretKeyDao getSecretKeyDao() {
		return secretKeyDao;
	}

	public void setSecretKeyDao(SecretKeyDao secretKeyDao) {
		this.secretKeyDao = secretKeyDao;
	}

	public ITransPayService getTransPayService() {
		return transPayService;
	}

	public void setTransPayService(ITransPayService transPayService) {
		this.transPayService = transPayService;
	}

	@Override
	public UnionPayBean handleT002(UnionPayBean unionPayBean,String posSn) {
		ExtendPayBean extendPayBean=new ExtendPayBean();
		unionPayBean.setMti("0200");
		unionPayBean.setPan(unionPayBean.getTrack2().split("=")[0]);
		unionPayBean.setProcessCode("000000");
		unionPayBean.setSystemsTraceAuditNumber(generateSequence(Constant.SEQ_SYSTEM_TRACKE_NUMBER));
		unionPayBean.setPosConditionCode("00");
		
		Pos posDb = posDao.findByPosSn(posSn);
		unionPayBean.setCardAcceptorTerminalId(posDb.getPosCati());
		Shop shop = shopDao.findById(posDb.getShop().getId());
		unionPayBean.setCardAcceptorId(shop.getShopNo());
		unionPayBean.setCurrencyCode("156");
		
		unionPayBean.setUpdateFlag("00");
		unionPayBean.setBatchNo("000001");
		unionPayBean.setMsgTypeCode("22");
		unionPayBean.setNetMngInfoCode("000");
		unionPayBean.setIcConditionCode("0");
		extendPayBean.setUnionPayBean(unionPayBean);
		extendPayBean=transPayService.transPay(extendPayBean);
		return extendPayBean.getUnionPayBean();
	}
	
	
	@Override
	public UnionPayBean handleT003(UnionPayBean unionPayBean,String posSn) {
		ExtendPayBean extendPayBean=new ExtendPayBean();
		unionPayBean.setMti("0200");
		unionPayBean.setPan(unionPayBean.getTrack2().split("=")[0]);
		unionPayBean.setProcessCode("310000");
		unionPayBean.setSystemsTraceAuditNumber(generateSequence(Constant.SEQ_SYSTEM_TRACKE_NUMBER));
		unionPayBean.setPosConditionCode("00");
		
		Pos posDb = posDao.findByPosSn(posSn);
		unionPayBean.setCardAcceptorTerminalId(posDb.getPosCati());
		Shop shop = shopDao.findById(posDb.getShop().getId());
		unionPayBean.setCardAcceptorId(shop.getShopNo());
		unionPayBean.setCurrencyCode("156");
		
		unionPayBean.setUpdateFlag("00");
		unionPayBean.setBatchNo("000001");
		unionPayBean.setMsgTypeCode("01");
		unionPayBean.setNetMngInfoCode("000");
		unionPayBean.setIcConditionCode("0");
		extendPayBean.setUnionPayBean(unionPayBean);
		extendPayBean=transPayService.transPay(extendPayBean);
		return extendPayBean.getUnionPayBean();
	}
	
	
	@Override
	public SignPic handleT004(String externalId) {
		SignPic signPic = (SignPic)entityDao.find("from SignPic s where s.externalId = ?", externalId).get(0);
		return signPic;
	}

	@Override
	public boolean createPos(Pos pos) {
		try {
			posDao.createPos(pos);
			return true;
		} catch (Exception e) {
			log.error(e);
			return false;
		}
	}
	
	public boolean update(Pos pos){
		try {
			posDao.update(pos);
			return true;
		}catch (Exception e) {
			log.error(e);
			return false;
		}
	}

	@Override
	public Pos findByPosCati(String posCati) {
		return posDao.findByPosCati(posCati);
	}
}

package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.BankInterfaceTerminalDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.BankInterface;
import com.yl.pay.pos.entity.BankInterfaceTerminal;
import com.yl.pay.pos.enums.BankPosRunStatus;
import com.yl.pay.pos.enums.BankPosUseStatus;
import com.yl.pay.pos.enums.Status;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.*;

/**
 * Title: BankInterfaceTerminalDao 实现类
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

public class BankInterfaceTerminalDaoImpl implements BankInterfaceTerminalDao {
	private EntityDao entityDao;
	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public BankInterfaceTerminal create(BankInterfaceTerminal bankInterfaceTerminal) {		
		entityDao.persist(bankInterfaceTerminal);
		return bankInterfaceTerminal;
	}

	@Override
	public BankInterfaceTerminal updateByTrans(BankInterfaceTerminal bankInterfaceTerminal) {
		String sql="update bank_interface_terminal bt set "
				+ "bt.bank_interface=?,"
				+ "bt.bank_customer_no=?,"
				+ "bt.bank_pos_cati=?,"
				+ "bt.mcc=?,"
				+ "bt.bank_batch=?,"
				+ "bt.bank_pos_use_status=?,"
				+ "bt.bank_pos_run_status=?,"
				+ "bt.status=?,"
				+ "bt.lock_time=?,"
				+ "bt.last_use_time=?,"
				+ "bt.p_secret_data=?,"
				+ "bt.m_secret_data=?,"
				+ "bt.bank_customer_name=?,"
				+ "bt.encrypt_terminal=?,"
				+ "bt.encrypt_customer_no=?,"
				+ "bt.optimistic=?"
				+ " where bt.id=? and bt.optimistic=?";
		int row=jdbcTemplate.update(sql, new Object[]{
				bankInterfaceTerminal.getBankInterface()==null?"":bankInterfaceTerminal.getBankInterface().getCode(),
				bankInterfaceTerminal.getBankCustomerNo()==null?"":bankInterfaceTerminal.getBankCustomerNo(),
				bankInterfaceTerminal.getBankPosCati()==null?"":bankInterfaceTerminal.getBankPosCati(),
				bankInterfaceTerminal.getMcc()==null?"":bankInterfaceTerminal.getMcc(),
				bankInterfaceTerminal.getBankBatch()==null?"":bankInterfaceTerminal.getBankBatch(),
				bankInterfaceTerminal.getBankPosUseStatus().name()==null?"":bankInterfaceTerminal.getBankPosUseStatus().name(),
				bankInterfaceTerminal.getBankPosRunStatus()==null?"":bankInterfaceTerminal.getBankPosRunStatus().name(),
				bankInterfaceTerminal.getStatus()==null?"":bankInterfaceTerminal.getStatus().name(),
				bankInterfaceTerminal.getLockTime()==null?"":bankInterfaceTerminal.getLockTime(),
				bankInterfaceTerminal.getLastUseTime()==null?"":bankInterfaceTerminal.getLastUseTime(),
				bankInterfaceTerminal.getpSecretData()==null?"":bankInterfaceTerminal.getpSecretData(),
				bankInterfaceTerminal.getmSecretData()==null?"":bankInterfaceTerminal.getmSecretData(),
				bankInterfaceTerminal.getBankCustomerName()==null?"":bankInterfaceTerminal.getBankCustomerName(),
				bankInterfaceTerminal.getEncryptTerminal()==null?"":bankInterfaceTerminal.getEncryptTerminal(),
				bankInterfaceTerminal.getEncryptCustomerNo()==null?"":bankInterfaceTerminal.getEncryptCustomerNo(),
				bankInterfaceTerminal.getOptimistic()+1,
				bankInterfaceTerminal.getId(),bankInterfaceTerminal.getOptimistic()});
		
		if(row==0){
			return null;
		}else{
			String sql2="select f.id,f.bank_interface,f.bank_customer_no,f.bank_pos_cati,f.mcc,f.bank_batch,f.bank_pos_use_status,f.bank_pos_run_status,"
					+ "f.status,f.lock_time,f.last_use_time,f.p_secret_data,f.m_secret_data,f.optimistic,f.bank_customer_name,"
					+ "f.encrypt_terminal,f.encrypt_customer_no from bank_interface_terminal f  "
					+ "where f.id=?";
			List tt=jdbcTemplate.queryForList(sql2, new Object[]{bankInterfaceTerminal.getId()});
			if(tt==null||tt.size()!=1){
				return null;
			}
			Iterator  i=tt.iterator();
			while(i.hasNext()){
				Map  f=(Map)i.next();
				Long tId=(new BigDecimal(String.valueOf(f.get("id")))).longValue();
				String bankInterfaceCode=(String)f.get("bank_interface");
				String bankCustomerNo1=(String)f.get("bank_customer_no");
				String bankPosCati=(String)f.get("bank_pos_cati");
				String mcc=(String)f.get("mcc");
				String bankBatch=(String)f.get("bank_batch");
				String bankPosUseStatus1=(String)f.get("bank_pos_use_status");
				String bankPosRunStatus1=(String)f.get("bank_pos_run_status");
				String status1=(String)f.get("status");
				Date lockTime=(Date)f.get("lock_time");
				Date lastUseTime=(Date)f.get("last_use_time");
				String pSecretData=(String)f.get("p_secret_data");
				String mSecretData=(String)f.get("m_secret_data");
				Integer optimistic=Integer.parseInt(String.valueOf(f.get("optimistic")));
				String bankCustomerName=(String)f.get("bank_customer_name");
				String encryptTerminal=(String)f.get("encrypt_terminal");
				String encryptCustomerNo=(String)f.get("encrypt_customer_no");
				BankInterface bankInterface=new BankInterface();
				bankInterface.setCode(bankInterfaceCode);
				bankInterfaceTerminal.setId(tId);
				bankInterfaceTerminal.setBankInterface(bankInterface);
				bankInterfaceTerminal.setBankCustomerNo(bankCustomerNo1);
				bankInterfaceTerminal.setBankPosCati(bankPosCati);
				bankInterfaceTerminal.setMcc(mcc);
				bankInterfaceTerminal.setBankBatch(bankBatch);
				bankInterfaceTerminal.setBankPosUseStatus(BankPosUseStatus.valueOf(bankPosUseStatus1));
				bankInterfaceTerminal.setBankPosRunStatus(BankPosRunStatus.valueOf(bankPosRunStatus1));
				bankInterfaceTerminal.setStatus(Status.valueOf(status1));
				bankInterfaceTerminal.setLockTime(lockTime);
				bankInterfaceTerminal.setLastUseTime(lastUseTime);
				bankInterfaceTerminal.setpSecretData(pSecretData);
				bankInterfaceTerminal.setmSecretData(mSecretData);
				bankInterfaceTerminal.setOptimistic(optimistic);
				bankInterfaceTerminal.setBankCustomerName(bankCustomerName);
				bankInterfaceTerminal.setEncryptTerminal(encryptTerminal);
				bankInterfaceTerminal.setEncryptCustomerNo(encryptCustomerNo);
			}
		}
		return bankInterfaceTerminal;
	}
	
	@Override
	public BankInterfaceTerminal update(
			BankInterfaceTerminal bankInterfaceTerminal) {		
		entityDao.update(bankInterfaceTerminal);
		return bankInterfaceTerminal;
	}
	
	@Override
	public List<BankInterfaceTerminal> findByUseStatus(BankPosUseStatus bankPosUseStatus){
		String hql = "from BankInterfaceTerminal f where f.bankPosUseStatus = ?";
		return entityDao.find(hql, bankPosUseStatus);
	}

	@Override
	public List<BankInterfaceTerminal> findByBankInterfaceAndBankCustomerNoAndStatusAndBankPosRunStatus(
			String bankInterfaceCode, String bankCustomerNo, Status status,
			BankPosRunStatus bankPosRunStatus) {
		String hql = "from BankInterfaceTerminal f where f.bankInterface.code=? and f.bankCustomerNo=? and f.status=? and f.bankPosRunStatus=?";
		return entityDao.find(hql, bankInterfaceCode, bankCustomerNo, status, bankPosRunStatus);
	}

	@Override
	public List<BankInterfaceTerminal> findUseableBankInterfaceTerminal(
			String bankInterfaceCode, String bankCustomerNo,
			BankPosUseStatus bankPosUseStatus,
			BankPosRunStatus bankPosRunStatus, Status status) {
		List<BankInterfaceTerminal> bankInterfaceTerminals=null;
		String sql="select f.id,f.bank_interface,f.bank_customer_no,f.bank_pos_cati,f.mcc,f.bank_batch,f.bank_pos_use_status,f.bank_pos_run_status,"
				+ "f.status,f.lock_time,f.last_use_time,f.p_secret_data,f.m_secret_data,f.optimistic,f.bank_customer_name,"
				+ "f.encrypt_terminal,f.encrypt_customer_no from bank_interface_terminal f  "
				+ "where f.bank_interface=? and f.bank_customer_no=? and f.bank_pos_use_status=? and f.bank_pos_run_status=? "
				+ "and f.status=? order by f.last_use_time";
		List tt=jdbcTemplate.queryForList(sql, new Object[]{bankInterfaceCode,bankCustomerNo,bankPosUseStatus.name(),bankPosRunStatus.name(),status.name()});
		if(tt!=null&&tt.size()>0){
			bankInterfaceTerminals=new ArrayList<BankInterfaceTerminal>();
		}
		Iterator  i=tt.iterator();
		while(i.hasNext()){
			Map  f=(Map)i.next();
			Long tId=new BigDecimal(String.valueOf(f.get("id"))).longValue();
//			String bankInterfaceCode=(String)f.get("bank_interface");
//			String bankCustomerNo1=(String)f.get("bank_customer_no");
			String bankPosCati=(String)f.get("bank_pos_cati");
			String mcc=(String)f.get("mcc");
			String bankBatch=(String)f.get("bank_batch");
//			String bankPosUseStatus1=(String)f.get("bank_pos_use_status");
//			String bankPosRunStatus1=(String)f.get("bank_pos_run_status");
//			String status1=(String)f.get("status");
			Date lockTime=(Date)f.get("lock_time");
			Date lastUseTime=(Date)f.get("last_use_time");
			String pSecretData=(String)f.get("p_secret_data");
			String mSecretData=(String)f.get("m_secret_data");
			Integer optimistic=Integer.parseInt(String.valueOf(f.get("optimistic")));
			String bankCustomerName=(String)f.get("bank_customer_name");
			String encryptTerminal=(String)f.get("encrypt_terminal");
			String encryptCustomerNo=(String)f.get("encrypt_customer_no");
			BankInterface bankInterface=new BankInterface();
			bankInterface.setCode(bankInterfaceCode);
			BankInterfaceTerminal bankInterfaceTerminal=new BankInterfaceTerminal();
			bankInterfaceTerminal.setId(tId);
			bankInterfaceTerminal.setBankInterface(bankInterface);
			bankInterfaceTerminal.setBankCustomerNo(bankCustomerNo);
			bankInterfaceTerminal.setBankPosCati(bankPosCati);
			bankInterfaceTerminal.setMcc(mcc);
			bankInterfaceTerminal.setBankBatch(bankBatch);
			bankInterfaceTerminal.setBankPosUseStatus(bankPosUseStatus);
			bankInterfaceTerminal.setBankPosRunStatus(bankPosRunStatus);
			bankInterfaceTerminal.setStatus(status);
			bankInterfaceTerminal.setLockTime(lockTime);
			bankInterfaceTerminal.setLastUseTime(lastUseTime);
			bankInterfaceTerminal.setpSecretData(pSecretData);
			bankInterfaceTerminal.setmSecretData(mSecretData);
			bankInterfaceTerminal.setOptimistic(optimistic);
			bankInterfaceTerminal.setBankCustomerName(bankCustomerName);
			bankInterfaceTerminal.setEncryptTerminal(encryptTerminal);
			bankInterfaceTerminal.setEncryptCustomerNo(encryptCustomerNo);
			bankInterfaceTerminals.add(bankInterfaceTerminal);
		}
		return bankInterfaceTerminals;
//		return entityDao.find(hql, bankInterfaceCode, bankCustomerNo, bankPosUseStatus, bankPosRunStatus, status);
	}

	@Override
	public BankInterfaceTerminal findUseableBankInterfaceTerminal(
			String bankInterfaceCode, String bankCustomerNo,
			String bankPosCati, BankPosUseStatus bankPosUseStatus,
			BankPosRunStatus bankPosRunStatus, Status status) {
		BankInterfaceTerminal bankInterfaceTerminal=null;
		String sql="select f.id,f.bank_interface,f.bank_customer_no,f.bank_pos_cati,f.mcc,f.bank_batch,f.bank_pos_use_status,f.bank_pos_run_status,"
				+ "f.status,f.lock_time,f.last_use_time,f.p_secret_data,f.m_secret_data,f.optimistic,f.bank_customer_name,"
				+ "f.encrypt_terminal,f.encrypt_customer_no from bank_interface_terminal f  "
				+ "where f.bank_interface=? and f.bank_customer_no=? and f.bank_pos_use_status=? and f.bank_pos_run_status=? "
				+ "and f.status=? and f.bank_pos_cati=?";
		List tt=jdbcTemplate.queryForList(sql, new Object[]{bankInterfaceCode,bankCustomerNo,bankPosUseStatus.name(),bankPosRunStatus.name(),status.name(),bankPosCati});
		if(tt!=null&&tt.size()>0){
		  bankInterfaceTerminal=new BankInterfaceTerminal();
		}
		Iterator  i=tt.iterator();
		while(i.hasNext()){
			Map  f=(Map)i.next();
			Long tId=new BigDecimal(String.valueOf(f.get("id"))).longValue();
//			String bankInterfaceCode=(String)f.get("bank_interface");
//			String bankCustomerNo1=(String)f.get("bank_customer_no");
//			String bankPosCati=(String)f.get("bank_pos_cati");
			String mcc=(String)f.get("mcc");
			String bankBatch=(String)f.get("bank_batch");
//			String bankPosUseStatus1=(String)f.get("bank_pos_use_status");
//			String bankPosRunStatus1=(String)f.get("bank_pos_run_status");
//			String status1=(String)f.get("status");
			Date lockTime=(Date)f.get("lock_time");
			Date lastUseTime=(Date)f.get("last_use_time");
			String pSecretData=(String)f.get("p_secret_data");
			String mSecretData=(String)f.get("m_secret_data");
			Integer optimistic=Integer.parseInt(String.valueOf(f.get("optimistic")));
			String bankCustomerName=(String)f.get("bank_customer_name");
			String encryptTerminal=(String)f.get("encrypt_terminal");
			String encryptCustomerNo=(String)f.get("encrypt_customer_no");
			BankInterface bankInterface=new BankInterface();
			bankInterface.setCode(bankInterfaceCode);
			
			bankInterfaceTerminal.setId(tId);
			bankInterfaceTerminal.setBankInterface(bankInterface);
			bankInterfaceTerminal.setBankCustomerNo(bankCustomerNo);
			bankInterfaceTerminal.setBankPosCati(bankPosCati);
			bankInterfaceTerminal.setMcc(mcc);
			bankInterfaceTerminal.setBankBatch(bankBatch);
			bankInterfaceTerminal.setBankPosUseStatus(bankPosUseStatus);
			bankInterfaceTerminal.setBankPosRunStatus(bankPosRunStatus);
			bankInterfaceTerminal.setStatus(status);
			bankInterfaceTerminal.setLockTime(lockTime);
			bankInterfaceTerminal.setLastUseTime(lastUseTime);
			bankInterfaceTerminal.setpSecretData(pSecretData);
			bankInterfaceTerminal.setmSecretData(mSecretData);
			bankInterfaceTerminal.setOptimistic(optimistic);
			bankInterfaceTerminal.setBankCustomerName(bankCustomerName);
			bankInterfaceTerminal.setEncryptTerminal(encryptTerminal);
			bankInterfaceTerminal.setEncryptCustomerNo(encryptCustomerNo);
		}
		return bankInterfaceTerminal;
//		String hql = "from BankInterfaceTerminal f where f.bankInterface.code=? and f.bankCustomerNo=? and f.bankPosCati=? and f.bankPosUseStatus=? " +
//		"and f.bankPosRunStatus=? and status=? ";
//		return entityDao.findUnique(BankInterfaceTerminal.class, hql, bankInterfaceCode, bankCustomerNo,bankPosCati, bankPosUseStatus, bankPosRunStatus, status);
	}

	@Override
	public BankInterfaceTerminal findByInterfaceAndCustomerNoAndPosCatiByJdbc(String bankInterfaceCode, String bankCustomerNo, String bankPosCati){

		String sql="select f.id,f.bank_interface,f.bank_customer_no,f.bank_pos_cati,f.mcc,f.bank_batch,f.bank_pos_use_status,f.bank_pos_run_status,"
				+ "f.status,f.lock_time,f.last_use_time,f.p_secret_data,f.m_secret_data,f.optimistic,f.bank_customer_name,"
				+ "f.encrypt_terminal,f.encrypt_customer_no from bank_interface_terminal f  "
				+ "where f.bank_interface=? and f.bank_customer_no=? and f.bank_pos_cati=?";
		List tt=jdbcTemplate.queryForList(sql, new Object[]{bankInterfaceCode,bankCustomerNo,bankPosCati});
		if(tt==null||tt.size()!=1){
			return null;
		}
		BankInterfaceTerminal bankInterfaceTerminal=new BankInterfaceTerminal();
		Iterator  i=tt.iterator();
		while(i.hasNext()){
			Map  f=(Map)i.next();
			Long tId=(new BigDecimal(String.valueOf(f.get("id")))).longValue();
//			String bankInterfaceCode=(String)f.get("bank_interface");
			String bankCustomerNo1=(String)f.get("bank_customer_no");
//			String bankPosCati=(String)f.get("bank_pos_cati");
			String mcc=(String)f.get("mcc");
			String bankBatch=(String)f.get("bank_batch");
			String bankPosUseStatus1=(String)f.get("bank_pos_use_status");
			String bankPosRunStatus1=(String)f.get("bank_pos_run_status");
			String status1=(String)f.get("status");
			Date lockTime=(Date)f.get("lock_time");
			Date lastUseTime=(Date)f.get("last_use_time");
			String pSecretData=(String)f.get("p_secret_data");
			String mSecretData=(String)f.get("m_secret_data");
			Integer optimistic=Integer.parseInt(String.valueOf(f.get("optimistic")));
			String bankCustomerName=(String)f.get("bank_customer_name");
			String encryptTerminal=(String)f.get("encrypt_terminal");
			String encryptCustomerNo=(String)f.get("encrypt_customer_no");
			BankInterface bankInterface=new BankInterface();
			bankInterface.setCode(bankInterfaceCode);
			bankInterfaceTerminal.setId(tId);
			bankInterfaceTerminal.setBankInterface(bankInterface);
			bankInterfaceTerminal.setBankCustomerNo(bankCustomerNo1);
			bankInterfaceTerminal.setBankPosCati(bankPosCati);
			bankInterfaceTerminal.setMcc(mcc);
			bankInterfaceTerminal.setBankBatch(bankBatch);
			bankInterfaceTerminal.setBankPosUseStatus(BankPosUseStatus.valueOf(bankPosUseStatus1));
			bankInterfaceTerminal.setBankPosRunStatus(BankPosRunStatus.valueOf(bankPosRunStatus1));
			bankInterfaceTerminal.setStatus(Status.valueOf(status1));
			bankInterfaceTerminal.setLockTime(lockTime);
			bankInterfaceTerminal.setLastUseTime(lastUseTime);
			bankInterfaceTerminal.setpSecretData(pSecretData);
			bankInterfaceTerminal.setmSecretData(mSecretData);
			bankInterfaceTerminal.setOptimistic(optimistic);
			bankInterfaceTerminal.setBankCustomerName(bankCustomerName);
			bankInterfaceTerminal.setEncryptTerminal(encryptTerminal);
			bankInterfaceTerminal.setEncryptCustomerNo(encryptCustomerNo);
		}
		return bankInterfaceTerminal;
	}
	
	@Override
	public BankInterfaceTerminal findByInterfaceAndCustomerNoAndPosCati(String bankInterfaceCode, String bankCustomerNo, String bankPosCati){
		String hql = "from BankInterfaceTerminal f where f.bankInterface.code =? and f.bankCustomerNo = ? and f.bankPosCati = ?";
		return entityDao.findUnique(BankInterfaceTerminal.class, hql, bankInterfaceCode, bankCustomerNo, bankPosCati);
	}
	@Override
	public List<BankInterfaceTerminal> findByBankInterfaceAndStatus(String bankInterfaceCode, Status status){
		String hql = "from BankInterfaceTerminal f where f.bankInterface.code =? and f.status =?";
		return entityDao.find(hql, bankInterfaceCode, status);
	}
	
	@Override
	public List<BankInterfaceTerminal> findByBankInterfaceAndStatusAndRunStatus(String bankInterfaceCode, Status status,BankPosRunStatus runStatus){
		String hql = "from BankInterfaceTerminal f where f.bankInterface.code =? and f.status =? and f.bankPosRunStatus =?";
		return entityDao.find(hql, bankInterfaceCode, status,runStatus);
	}
	
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	@Override
	public BankInterfaceTerminal findByInterfaceAndEncryCustomerAndEncryPosCati(
			String bankInterfaceCode, String bankCustomerNo, String bankPosCati) {
		String hql = "from BankInterfaceTerminal f where f.bankInterface.code =? and f.encryptCustomerNo = ? and f.encryptTerminal = ?";
		return entityDao.findUnique(BankInterfaceTerminal.class, hql, bankInterfaceCode, bankCustomerNo, bankPosCati);
	}
}

package com.pay.test;

import com.pay.pos.dao.BankChannelCustomerConfigDao;
import com.pay.pos.dao.BankChannelDao;
import com.pay.pos.dao.BankChannelFeeDao;
import com.pay.pos.dao.BankChannelFunctionDao;
import com.pay.pos.dao.BankDao;
import com.pay.pos.dao.BankIdNumberDao;
import com.pay.pos.dao.BankInterfaceDao;
import com.pay.pos.dao.BankInterfaceTerminalDao;
import com.pay.pos.dao.CustomerDao;
import com.pay.pos.dao.CustomerFeeDao;
import com.pay.pos.dao.CustomerFunctionDao;
import com.pay.pos.dao.MccRateMapDao;
import com.pay.pos.interfaces.P110005.PreAuth;
import com.pay.pos.interfaces.P110005.PreAuthComp;
import com.pay.pos.interfaces.P110005.PreAuthCompReversal;
import com.pay.pos.interfaces.P110005.PreAuthCompVoid;
import com.pay.pos.interfaces.P110005.PreAuthCompVoidReversal;
import com.pay.pos.interfaces.P110005.PreAuthReversal;
import com.pay.pos.interfaces.P110005.PreAuthVoid;
import com.pay.pos.interfaces.P110005.PreAuthVoidReversal;
import com.pay.pos.interfaces.P110005.PurchaseRefund;
import com.pay.pos.interfaces.P110005.Settle;
import com.pay.pos.interfaces.P440304.SignIn;


public class ModelTest extends BaseServiceTest {

	private BankDao bankDao;
	private BankInterfaceDao bankInterfaceDao;
	private BankInterfaceTerminalDao bankInterfaceTerminalDao;
	private BankChannelCustomerConfigDao bankChannelCustomerConfigDao;
	private BankChannelFeeDao bankChannelFeeDao;
	private BankChannelDao bankChannelDao;
	private BankChannelFunctionDao bankChannelFunctionDao;
	private MccRateMapDao mccRateMapDao;
	private CustomerFeeDao customerFeeDao;
	private CustomerFunctionDao customerFunctionDao;
	private CustomerDao customerDao;
	private SignIn signInP110005;
	private Settle settleP110005;
	private PreAuth preAuthP110005;
	private PreAuthVoid  preAuthVoidP110005;
	private PreAuthComp  preAuthCompP110005;
	private PreAuthReversal preAuthReversalP110005;
	private PreAuthCompVoid preAuthCompVoidP110005;
	private PreAuthCompVoidReversal preAuthCompVoidReversalP110005;
	private PreAuthCompReversal preAuthCompReversalP110005;
	private PreAuthVoidReversal preAuthVoidReversalP110005;
	private PurchaseRefund purchaseRefundP110005;
	private SignIn signInP440304;
	
	private BankIdNumberDao bankIdNumberDao;
	
	protected void setUp() throws Exception {
		super.setUp();
		bankDao=(BankDao)context.getBean("bankDao");
		bankIdNumberDao=(BankIdNumberDao)context.getBean("bankIdNumberDao");
		bankInterfaceDao=(BankInterfaceDao)context.getBean("bankInterfaceDao");
		
		customerFeeDao=(CustomerFeeDao)context.getBean("customerFeeDao");
		mccRateMapDao=(MccRateMapDao)context.getBean("mccRateMapDao");
		bankInterfaceTerminalDao=(BankInterfaceTerminalDao)context.getBean("bankInterfaceTerminalDao");
		bankChannelCustomerConfigDao=(BankChannelCustomerConfigDao)context.getBean("bankChannelCustomerConfigDao");
		bankChannelFeeDao=(BankChannelFeeDao)context.getBean("bankChannelFeeDao");
		bankChannelDao=(BankChannelDao)context.getBean("bankChannelDao");
		bankChannelFunctionDao=(BankChannelFunctionDao)context.getBean("bankChannelFunctionDao");
		
		customerFunctionDao=(CustomerFunctionDao)context.getBean("customerFunctionDao");
		customerDao=(CustomerDao)context.getBean("customerDao");
		
//		signInP110005=(SignIn)context.getBean("signInP110005");
		settleP110005=(Settle)context.getBean("settleP110005");
		preAuthP110005=(PreAuth)context.getBean("preAuthP110005");
		preAuthVoidP110005=(PreAuthVoid)context.getBean("preAuthVoidP110005");
		preAuthCompP110005=(PreAuthComp)context.getBean("preAuthCompP110005");
		preAuthReversalP110005=(PreAuthReversal)context.getBean("preAuthReversalP110005");
		preAuthCompVoidP110005=(PreAuthCompVoid)context.getBean("preAuthCompVoidP110005");
		preAuthCompVoidReversalP110005=(PreAuthCompVoidReversal)context.getBean("preAuthCompVoidReversalP110005");
		preAuthCompReversalP110005=(PreAuthCompReversal)context.getBean("preAuthCompReversalP110005");
		preAuthVoidReversalP110005=(PreAuthVoidReversal)context.getBean("preAuthVoidReversalP110005");
		purchaseRefundP110005=(PurchaseRefund)context.getBean("purchaseRefundP110005");
		signInP440304=(SignIn)context.getBean("signInP440304");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

		
	public void testCreate(){
		
//		List<String> pans=new LinkedList<String>();
//		pans.add("5316930020285892");
//		for(String pan:pans){
//			List<BankIdNumber> bankIdNumberList =bankIdNumberDao.findByPan(pan, pan.length(), pan.substring(0, 3));
//			for (BankIdNumber bankIdNumber : bankIdNumberList) {
//				int verifyLength = bankIdNumber.getVerifyLength();
//				String verifyCode = bankIdNumber.getVerifyCode();					
//				String panStr = pan.substring(0, verifyLength);			
//				if (!panStr.equals(verifyCode)){
//					continue;
//				}
//				Bank issuer = bankDao.findByCode(bankIdNumber.getBank().getCode());
//				System.out.println("+++++++++"+pan+"-"+issuer.getFullName()+"-"+bankIdNumber.getCardType());
//			}
//		}
		
		
		//签到测试
//		signInP110005.test();
//		settleP110005.test();
		
		signInP440304.test();
		
//		preAuthP110005.test();
//		preAuthVoidP110005.test();
//		preAuthCompP110005.test();
//		preAuthReversalP110005.test();
//		preAuthCompVoidP110005.test();
//		preAuthCompVoidReversalP110005.test();
//		preAuthCompReversalP110005.test();
//		preAuthVoidReversalP110005.test();
//		purchaseRefundP110005.test();
		
//		Bank bank=bankDao.findByCode("BOC");
//		BankInterface binterface=new BankInterface();
//		binterface.setBank(bank);
//		binterface.setAverageRespTime(1);
//		binterface.setCode("P110005");
//		binterface.setCreateTime(new Date());
//		binterface.setName("北京中行");
//		binterface.setScale(11);
//		binterface.setStatus(Status.TRUE);
//		bankInterfaceDao.create(binterface);
////		
//		BankInterface bi=bankInterfaceDao.findByCode("P110005");
//		BankInterfaceTerminal terminal=new BankInterfaceTerminal();
//		terminal.setBankBatch("000001");
//		terminal.setBankCustomerNo("104110073922188");
//		terminal.setBankInterface(bi);
//		terminal.setBankPosCati("11059798");
//		terminal.setBankPosRunStatus(BankPosRunStatus.SIGNIN);
//		terminal.setBankPosUseStatus(BankPosUseStatus.IDLE);
//		terminal.setLastUseTime(new Date());
//		terminal.setLockTime(new Date());
//		terminal.setMcc("7392");
//		terminal.setStatus(Status.TRUE);
//		bankInterfaceTerminalDao.create(terminal);
//		
//		BankChannelFunction fun1=new BankChannelFunction();
//		fun1.setSortCode("S00001");
//		fun1.setStatus(Status.TRUE);
//		fun1.setTransType(TransType.PURCHASE);
//		bankChannelFunctionDao.create(fun1);
//		BankChannelFunction fun2=new BankChannelFunction();
//		fun2.setSortCode("S00001");
//		fun2.setStatus(Status.TRUE);
//		fun2.setTransType(TransType.PURCHASE_VOID);
//		bankChannelFunctionDao.create(fun2);
//		
//		MccRateMap mccRateMap=new MccRateMap();
//		mccRateMap.setMcc("4722");
//		mccRateMap.setRateAssortCode("R0005");
//		mccRateMap.setScale(10);
//		mccRateMapDao.create(mccRateMap);
		
		
//		Bank bank=bankDao.findByCode("BOC");
//		BankInterface bi=bankInterfaceDao.findByCode("P110005");
//		BankChannel bankChannel=new BankChannel();
//		bankChannel.setBank(bank);
//		bankChannel.setBankInterface(bi);
//		bankChannel.setCode("P110005001");
//		bankChannel.setEffectTime(new Date());
//		bankChannel.setName("北京中行全通道");
//		bankChannel.setRateAssortCode("R0005");
//		bankChannel.setStatus(Status.TRUE);
//		bankChannel.setTransFunction("S00001");
//		bankChannel.setTestFlag(false);
//		bankChannel.setSupportIssuerType(BankChannelSupportIssuerType.ALL);
//		bankChannel.setSupportCardType("ALL");
//		bankChannelDao.create(bankChannel);
//		
//		BankChannelFee bankFee=new BankChannelFee();
//		bankFee.setBankChannelCode("P110005001");
//		bankFee.setCode("CF000002");
//		bankFee.setComputeFeeMode(ComputeFeeMode.RATE);
//		bankFee.setRate("0.003");
//		bankFee.setStatus(Status.TRUE);
//		bankChannelFeeDao.create(bankFee);
		
//		Bank bank=bankDao.findByCode("BOC");
//		BankInterface bi=bankInterfaceDao.findByCode("P110005");
//		BankChannelCustomerConfig channelConfig=new BankChannelCustomerConfig();
//		channelConfig.setBank(bank);
//		channelConfig.setBankChannelCode("P110005001");
//		channelConfig.setBankCustomerNo("104110073922188");
//		channelConfig.setBankInterface(bi);
//		channelConfig.setCustomerLevel(BankCustomerLevel.SYSTEM);
//		channelConfig.setStatus(Status.TRUE);
//		bankChannelCustomerConfigDao.create(channelConfig);
////		
//		CustomerFee customerFee=new CustomerFee();
//		customerFee.setCardType("DEFAULT");
//		customerFee.setCode("F0000001");
//		customerFee.setComputeMode(ComputeFeeMode.RATE);
//		customerFee.setCreateTime(new Date());
//		customerFee.setCustomerNo("0000000001");
//		customerFee.setEffectTime(new Date());
//		customerFee.setIssuer("DEFAULT");
//		customerFee.setMcc("4511");
//		customerFee.setRate("0.005");
//		customerFee.setStatus(Status.TRUE);
//		customerFeeDao.create(customerFee);
		
		
//		Customer customer=customerDao.findByCustomerNo("0000000001");
//		
//		CustomerFunction customerFunction=new CustomerFunction();
//		customerFunction.setCustomer(customer);
//		customerFunction.setCardType("ALL");
//		customerFunction.setTrsanType("BALANCE_INQUIRY");
//		customerFunction.setLastUpdateTime(new Date());
//		customerFunctionDao.create(customerFunction);
//		
		
		
		
		
		
	}
	
}


	

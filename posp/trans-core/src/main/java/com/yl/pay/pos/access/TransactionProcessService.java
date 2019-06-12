package com.yl.pay.pos.access;

import com.pay.common.util.ISO8583.ISO8583Utile;
import com.pay.common.util.StringUtil;
import com.yl.pay.pos.Constant;
import com.yl.pay.pos.TransTypeMapping;
import com.yl.pay.pos.action.trans.BaseTransaction;
import com.yl.pay.pos.action.trans.ITransaction;
import com.yl.pay.pos.action.trans.TransactionFactory;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.entity.Pos;
import com.yl.pay.pos.entity.TransResponseDict;
import com.yl.pay.pos.exception.TransException;
import com.yl.pay.pos.exception.TransExceptionConstant;
import com.yl.pay.pos.exception.TransRunTimeException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Title: POS交易服务入口
 * Description: 
 * Copyright: Copyright (c)2011
 * @author jun.yu
 */

public class TransactionProcessService extends BaseTransaction {

	private Log log = LogFactory.getLog(TransactionProcessService.class);	
	private TransactionFactory transactionFactory;	
	@SuppressWarnings("finally")
	public byte[] execute(byte[] inputData){		
		log.info("pos input data:"+ISO8583Utile.bytesToHexString(inputData));
		
		String sourceTPDU = null;
		byte[] tpdubyte = new byte[Constant.LEN_POS_TPUD];
		System.arraycopy(inputData, Constant.LEN_POS_MESSAGETYPE, tpdubyte, 0, Constant.LEN_POS_TPUD);
		sourceTPDU = ISO8583Utile.bytesToHexString(tpdubyte);
		
		ExtendPayBean extendPayBean = new ExtendPayBean();
		UnionPayBean unionPayBean = new UnionPayBean();
		UnionPayBean sourceUnionPayBean=null;
		
		byte[] returnMsg = null;	
		
		try {
			unionPayBean = PosMessageDecoder.Decoding(inputData, unionPayBean);				//原始报文解析
			extendPayBean.setUnionPayBean(unionPayBean);
			sourceUnionPayBean=unionPayBean.clone();
			extendPayBean = TransTypeMapping.getTransType(extendPayBean);					//识别交易类型
			extendPayBean = transTypeCheckMac(extendPayBean);                               //校验MAC是否有上送
			extendPayBean = extendPayBeanService.loadExtendPayInfo(extendPayBean);			//公共参数加载				
			extendPayBean = posRequestService.create(extendPayBean);						//保存POS请求信息			
			extendPayBean = cardMatchService.execute(extendPayBean);						//卡识别	
			extendPayBean = cardInfoService.create(extendPayBean);							//记录卡信息	
			extendPayBean = permissionCheckService.execute(extendPayBean);					//权限检查
			extendPayBean = transAmountLimitService.execute(extendPayBean);					//限额校验
			
			ITransaction transaction = transactionFactory.getTransaction(extendPayBean.getTransType().name());
			extendPayBean = transaction.execute(extendPayBean);								//调用交易类型实现类
			processTransException(extendPayBean, null);
			extendPayBean = posRequestService.complete(extendPayBean);						//更新POS请求信息
			extendPayBean=transAmountLimitService.completeAccumulatedAmount(extendPayBean); //交易限额累计
		}catch (Throwable e) {
			log.info("+++++++++ Transaction Error +++++++++ ", e);	
			extendPayBean.setUnionPayBean(sourceUnionPayBean==null?unionPayBean:sourceUnionPayBean);
			if(extendPayBean.getTransType()==null){
				extendPayBean = TransTypeMapping.getTransType(extendPayBean);
			}			
			processTransException(extendPayBean, e);
			extendPayBean = posRequestService.complete(extendPayBean);
		}finally{	
			try {
				sysRouteCustomerConfigDetailService.createRecover(extendPayBean.getSysRouteCustConfDetail());	//系统路由商编状态还原
				bankInterfaceTerminalService.terminalRecover(extendPayBean.getBankRequestTerminal());	//银行终端回收
				returnMsg = PosMessageEncoder.encoding(extendPayBean);
				returnMsg=pre8583Message(returnMsg, sourceTPDU);
			} catch (final Throwable e) {
				log.info("pos msg return error!",e);
			}
			log.info("return pos:"+ISO8583Utile.bytesToHexString(returnMsg));
			return returnMsg;
		}		
	}
	
	/**对交易类的交易应必有mac
	 * @param extendPayBean
	 * @return
	 */
	private ExtendPayBean transTypeCheckMac(ExtendPayBean extendPayBean) {
		UnionPayBean unionPayBean=extendPayBean.getUnionPayBean();
		if(TransTypeMapping.transTypeMAC.contains(extendPayBean.getTransType())){
			if(StringUtil.isNull(unionPayBean.getMac())){
				throw new TransRunTimeException(TransExceptionConstant.MAC_NOT_EMPTY);
			}
		}
		return extendPayBean;
	}

	private byte[] pre8583Message(byte[] data,String sourceTPDU){
		if(data == null){
			log.error("the message return to pos is null !");
			return null;
		}
		byte[] realdata = new byte[data.length];
		byte[] tpdubytes = new byte[Constant.LEN_POS_TPUD];
		System.arraycopy(data, Constant.LEN_POS_MESSAGETYPE, tpdubytes, 0, Constant.LEN_POS_TPUD);
		String orgTPDU = ISO8583Utile.bcd2String(tpdubytes, false);
		String returnTPDU = getTPDU(sourceTPDU);
		byte[] realTPDUbytes = new byte[Constant.LEN_POS_TPUD];
		realTPDUbytes = ISO8583Utile.string2Bcd(returnTPDU);
		int startPosition = 0;
		System.arraycopy(data, 0,realdata, startPosition , Constant.LEN_POS_MESSAGETYPE);
		startPosition += Constant.LEN_POS_MESSAGETYPE;
		System.arraycopy(realTPDUbytes, 0,realdata , startPosition, Constant.LEN_POS_TPUD);
		startPosition += Constant.LEN_POS_TPUD;
		System.arraycopy(data, startPosition, realdata, startPosition, data.length-startPosition);
		return realdata;
	}
	
	private String getTPDU(String TPDU){
		if(StringUtils.isBlank(TPDU)){
			log.error("the return message's TPDU is null!");
			return Constant.CONTENT_POS_TPUD;
		}
		String constantMessage = TPDU.substring(0,2);//前两位固定
		String front = TPDU.substring(2,6);//中间4位数据
		String back = TPDU.substring(6);//后4位数据
		String newTPDU = constantMessage + back + front;
		return newTPDU;
	}
	
	//交易异常处理 
	private void processTransException(ExtendPayBean extendPayBean, Throwable e){
		TransResponseDict resDict = null;
		if(null == e){
			try {
				if(!Constant.SUCCESS_POS_RESPONSE.equals(extendPayBean.getUnionPayBean().getResponseCode())){
					if(StringUtils.isNotBlank(extendPayBean.getExceptionCode())){
						resDict = transResponseDictDao.findByExceptionCode(extendPayBean.getExceptionCode());
					}
				}
			} catch (Exception e2) {
				log.error("Changing msg  error .. ",e2);
			}
		}else{
			//默认系统异常
			extendPayBean.getUnionPayBean().setResponseCode(Constant.SYSTEM_EXCEPTION_RESPONSE);
			extendPayBean.setExceptionCode(TransExceptionConstant.SYSTEM_EXCEPTION);
			if((e instanceof TransException || e instanceof TransRunTimeException) && e.getMessage().length()<=10){
				resDict = transResponseDictDao.findByExceptionCode(e.getMessage());
			}	
		}
		if(null != resDict){
			extendPayBean.getUnionPayBean().setResponseCode(resDict.getResponseCode());
			extendPayBean.setExceptionCode(resDict.getExceptionCode());
			//boolean flag = verifyIsResponse56(extendPayBean);
//			if(flag && StringUtils.isNotBlank(resDict.getResponseMsg())){
//				extendPayBean.getUnionPayBean().setNoUse56(resDict.getResponseMsg());
//			}
			if(StringUtils.isNotBlank(resDict.getResponseMsg())){
				extendPayBean.getUnionPayBean().setNoUse56(resDict.getResponseMsg());
			}
		}
	}
	
	//校验是否下发56域错误信息
	private boolean verifyIsResponse56(ExtendPayBean extendPayBean){
		boolean flag = false;
		try {
			Pos pos = extendPayBean.getPos();
			if(pos==null){
				return false;
			}
			if(!Constant.ISHUA_POS_TYPE.contains(pos.getType())){
				if(Constant.XINDALU_POS_TYPE.contains(pos.getType().trim())
						&& Constant.XINDALU_POS_VERSION.compareTo(pos.getSoftVersion())<=0){
					flag = true;
				}else if(Constant.POS_VERSION_NEW.compareTo(pos.getSoftVersion())<=0){
					flag=true;
				}
			}
		} catch (Exception e) {
			log.info("verifyIsResponse56 error ..",e);
		}
		return flag;
	}
	
	public TransactionFactory getTransactionFactory() {
		return transactionFactory;
	}
	
	public void setTransactionFactory(TransactionFactory transactionFactory) {
		this.transactionFactory = transactionFactory;
	}
}

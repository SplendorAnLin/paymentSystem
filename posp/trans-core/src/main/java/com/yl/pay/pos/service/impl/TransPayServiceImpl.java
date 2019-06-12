package com.yl.pay.pos.service.impl;

import com.yl.pay.pos.Constant;
import com.yl.pay.pos.TransTypeMapping;
import com.yl.pay.pos.action.trans.BaseTransaction;
import com.yl.pay.pos.action.trans.ITransaction;
import com.yl.pay.pos.action.trans.TransactionFactory;
import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.entity.TransResponseDict;
import com.yl.pay.pos.exception.TransException;
import com.yl.pay.pos.exception.TransExceptionConstant;
import com.yl.pay.pos.exception.TransRunTimeException;
import com.yl.pay.pos.service.ITransPayService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TransPayServiceImpl extends BaseTransaction implements ITransPayService {

	private Log log = LogFactory.getLog(TransPayServiceImpl.class);	
	private TransactionFactory transactionFactory;	
	@Override
	public ExtendPayBean transPay(ExtendPayBean extendPayBean) {
		
		UnionPayBean unionPayBean = extendPayBean.getUnionPayBean();
		UnionPayBean sourceUnionPayBean=null;
		
		try {
			sourceUnionPayBean=unionPayBean.clone();
			extendPayBean = TransTypeMapping.getTransType(extendPayBean);					//识别交易类型
			extendPayBean = extendPayBeanService.loadExtendPayInfo(extendPayBean);			//公共参数加载				
			extendPayBean = posRequestService.create(extendPayBean);						//保存POS请求信息			
			extendPayBean = cardMatchService.execute(extendPayBean);						//卡识别	
			extendPayBean = cardInfoService.create(extendPayBean);							//记录卡信息	
			extendPayBean = permissionCheckService.execute(extendPayBean);					//权限检查
			extendPayBean = transAmountLimitService.execute(extendPayBean);					//限额校验
			
			ITransaction transaction = transactionFactory.getTransaction(extendPayBean.getTransType().name());
			extendPayBean = transaction.execute(extendPayBean);								//调用交易类型实现类
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
				bankInterfaceTerminalService.terminalRecover(extendPayBean.getBankRequestTerminal());	//银行终端回收
			} catch (final Throwable e) {
				log.info("pos msg return error!",e);
			}	
		}	
		
		
		return extendPayBean;
	}
	
	//交易异常处理 
		private void processTransException(ExtendPayBean extendPayBean, Throwable e){

			//默认系统异常	
			extendPayBean.getUnionPayBean().setResponseCode(Constant.SYSTEM_EXCEPTION_RESPONSE);
			extendPayBean.setExceptionCode(TransExceptionConstant.SYSTEM_EXCEPTION);
			
			if((e instanceof TransException || e instanceof TransRunTimeException) && e.getMessage().length()<=10){
				TransResponseDict resDict = transResponseDictDao.findByExceptionCode(e.getMessage());
				if(resDict!=null){
					extendPayBean.getUnionPayBean().setResponseCode(resDict.getResponseCode());
					extendPayBean.setExceptionCode(resDict.getExceptionCode());
				}
			}		
		}

		public void setTransactionFactory(TransactionFactory transactionFactory) {
			this.transactionFactory = transactionFactory;
		}

}

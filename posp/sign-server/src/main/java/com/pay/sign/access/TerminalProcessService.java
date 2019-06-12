package com.pay.sign.access;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.pay.common.util.DateUtil;
import com.pay.common.util.ISO8583.ISO8583Utile;
import com.pay.sign.Constant;
import com.pay.sign.TransTypeMapping;
import com.pay.sign.bean.ExtendPayBean;
import com.pay.sign.bean.UnionPayBean;
import com.pay.sign.business.IPosRequestService;
import com.pay.sign.business.ITerminalBusiness;
import com.pay.sign.business.TerminalBusinessFactory;
import com.pay.sign.dao.TransResponseDictDao;
import com.pay.sign.dbentity.TransResponseDict;
import com.pay.sign.exception.TransException;
import com.pay.sign.exception.TransExceptionConstant;
import com.pay.sign.exception.TransRunTimeException;
import com.pay.sign.utils.PosMessageDecoder;
import com.pay.sign.utils.PosMessageEncoder;

/**
 * Title: 电子签名交易服务入口
 * Description:   
 * Copyright: 2015年6月12日下午2:56:42
 * Company: SDJ
 * @author zhongxiang.ren
 */
@Component
public class TerminalProcessService{

	private Log log = LogFactory.getLog(TerminalProcessService.class);
	@Resource
	private TerminalBusinessFactory terminalBusinessFactory;	
	@Resource
	private IPosRequestService posRequestService;
	@Resource
	private TransResponseDictDao transResponseDictDao;
	
	@SuppressWarnings("finally")
	public byte[] execute(byte[] inputData){		
		
		log.info("pos input data:"+ISO8583Utile.bytesToHexString(inputData));
		
		String sourceTPDU = null;
		byte[] tpdubyte = new byte[Constant.LEN_POS_TPUD];
		System.arraycopy(inputData, Constant.LEN_POS_MESSAGETYPE, tpdubyte, 0, Constant.LEN_POS_TPUD);
		sourceTPDU = ISO8583Utile.bytesToHexString(tpdubyte);
		
		ExtendPayBean extendPayBean = new ExtendPayBean();
		UnionPayBean unionPayBean = new UnionPayBean();
		UnionPayBean sourceUnionPayBean = null;
		byte[] returnMsg = null;	
		
		try {
			log.info("#####   call sign service Decoding [ " + DateUtil.formatDate(new Date()) + " ]  #####");
			long start = System.currentTimeMillis();
			
			unionPayBean = PosMessageDecoder.Decoding(inputData, unionPayBean);				//原始报文解析
			sourceUnionPayBean = unionPayBean.clone();
			
			extendPayBean.setUnionPayBean(unionPayBean);
			extendPayBean = TransTypeMapping.getTransType(extendPayBean);					//识别交易类型
			ITerminalBusiness business = terminalBusinessFactory.getBusiness(extendPayBean.getTransType().name());
			
			long useTime1 = (System.currentTimeMillis()-start);
			start = System.currentTimeMillis();
			log.info("#####   call sign service Decoding [ " +  DateUtil.formatDate(new Date()) + ", time-consume:" + useTime1 + "ms ] #####\n");
			
			extendPayBean = posRequestService.create(extendPayBean);						//保存POS请求信息，除了图片数据之外的。
			
			long useTime2 = (System.currentTimeMillis()-start);
			log.info("#####   call sign service save posRequest ,DB [ " +  DateUtil.formatDate(new Date()) + ", time-consume:" + useTime2 + "ms ] #####\n");
			
			extendPayBean.setUnionPayBean(unionPayBean);
			//完成1数据校验，2图片存储处理，图片路径信息持久化
			business.execute(extendPayBean);
			
			start = System.currentTimeMillis();
			
			extendPayBean = posRequestService.complete(extendPayBean);                      //更新POS请求信息
			
			long useTime3 = (System.currentTimeMillis()-start);
			log.info("#####   call sign service update posRequest ,DB [ " +  DateUtil.formatDate(new Date()) + ", time-consume:" + useTime3 + "ms ] #####\n");
			
		}catch (Throwable e) {
			log.info("+++++++++ Transaction Error +++++++++ ", e);	
			extendPayBean.setUnionPayBean(sourceUnionPayBean == null? unionPayBean : sourceUnionPayBean);
			if(extendPayBean.getTransType() == null){
				extendPayBean = TransTypeMapping.getTransType(extendPayBean);
			}			
			processTransException(extendPayBean, e);
			extendPayBean = posRequestService.complete(extendPayBean);
		}finally{			
			try {
				long start = System.currentTimeMillis();
				
				returnMsg = PosMessageEncoder.encoding(extendPayBean);
				returnMsg = pre8583Message(returnMsg, sourceTPDU);
				
				long useTime3 = (System.currentTimeMillis()-start);
				log.info("#####   call sign service encoding [ " +  DateUtil.formatDate(new Date()) + ", time-consume:" + useTime3 + "ms ] #####\n");
				
			} catch (final Throwable e) {
				log.info("pos msg return error!",e);
			}	
			log.info("Sign return hexString :" + ISO8583Utile.bytesToHexString(returnMsg));
			return returnMsg;
		}		
		
	}
	
	private byte[] pre8583Message(byte[] data,String sourceTPDU){
		if(data == null){
			log.error("the message return to pos is null !");
			return null;
		}
		byte[] realdata = new byte[data.length];
		byte[] tpdubytes = new byte[Constant.LEN_POS_TPUD];
		System.arraycopy(data, Constant.LEN_POS_MESSAGETYPE, tpdubytes, 0, Constant.LEN_POS_TPUD);
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

		//默认系统异常	
		extendPayBean.getUnionPayBean().setResponseCode(Constant.SYSTEM_EXCEPTION_RESPONSE);
		extendPayBean.setExceptionCode(TransExceptionConstant.SYSTEM_EXCEPTION);
		
		if((e instanceof TransException || e instanceof TransRunTimeException) && e.getMessage().length() <= 10){
			TransResponseDict resDict = transResponseDictDao.findByExceptionCode(e.getMessage());
			if(resDict != null){
				extendPayBean.getUnionPayBean().setResponseCode(resDict.getResponseCode());
				extendPayBean.setExceptionCode(resDict.getExceptionCode());
			}
		}		
	}
}

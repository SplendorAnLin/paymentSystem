package com.pay.sign.business;

import java.io.File;

import java.io.FileOutputStream;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.pay.common.util.AmountUtil;
import com.pay.common.util.DateUtil;
import com.pay.common.util.PropertyUtil;
import com.pay.common.util.StringUtil;
import com.pay.common.util.ISO8583.ISO8583Utile;
import com.pay.sign.Constant;
import com.pay.sign.bean.ExtendPayBean;
import com.pay.sign.bean.UnionPayBean;
import com.pay.sign.dbentity.Order;
import com.pay.sign.dbentity.SignPic;
import com.pay.sign.dbservice.IOrderDBService;
import com.pay.sign.dbservice.ISignDBService;
import com.pay.sign.enums.OrderStatus;
import com.pay.sign.exception.TransExceptionConstant;
import com.pay.sign.exception.TransRunTimeException;
/**
 * Title: 
 * Description:   
 * Copyright: 2015年6月12日下午2:49:21
 * Company: SDJ
 * @author zhongxiang.ren
 */
@Component
public class SignBusiness implements ITerminalBusiness{
	private Log log = LogFactory.getLog(SignBusiness.class);
	
	@Resource
	private ISignDBService signDBService;
	@Resource
	private IOrderDBService orderDBService;
	
	private static PropertyUtil propertyUtil =null;
	private static String filePath = null;
	
	static{
		propertyUtil = PropertyUtil.getInstance("signConfig");
		filePath = propertyUtil.getProperty("filePath");
		//如果没有指定图片存储目录，默认使用下面,一般是:H:/eclipse/signPictures/
		if("".equals(filePath.trim())){
			String userDir = System.getProperty("user.dir");
			//将“\”全部替换为“/”
			filePath = userDir.replaceAll("\\\\", "/") + "/signPictures/";
		}
		//格式化路径
		filePath = filePath.replaceAll("\\\\", "/");
		if(!filePath.endsWith("/")) filePath += "/" ;
	}
	
	@Override
	public ExtendPayBean execute(ExtendPayBean extendPayBean) throws Exception {
		try{
			long start = System.currentTimeMillis();
			
			//字节数据解析
			UnionPayBean unionPayBean = extendPayBean.getUnionPayBean();
			String signPhotoHexStr = unionPayBean.getSwitchingData();
			byte[] signPhotoByte = ISO8583Utile.hexStringToByte(signPhotoHexStr);
			String orderNO = unionPayBean.getRetrievalReferenceNumber();
			//金额单位转换
			if(StringUtil.notNull(unionPayBean.getAmount())){
				Double transAmount =AmountUtil.div(Double.parseDouble(unionPayBean.getAmount()), 100.00);
				extendPayBean.setTransAmount(transAmount);
			}else{
				extendPayBean.setTransAmount(0.0);
			}
			Order order = orderDBService.getOrder(orderNO);
			
			long useTime1 = (System.currentTimeMillis()-start);
			start = System.currentTimeMillis();
			log.info("#####   call sign service getOrder ,DB[ " +  DateUtil.formatDate(new Date()) + ", time-consume:" + useTime1 + "ms ] #####\n");
			
			if(order == null){
				throw new TransRunTimeException(TransExceptionConstant.SOURCE_ORDER_ISNULL);
			}
			//数据校验
			if(!check(order,unionPayBean)){
				throw new TransRunTimeException(TransExceptionConstant.TRANSTYPE_NOT_PERMIT);
			}
			
			long useTime2 = (System.currentTimeMillis()-start);
			start = System.currentTimeMillis();
			log.info("#####   call sign service check ,DB[ " +  DateUtil.formatDate(new Date()) + ", time-consume:" + useTime2 + "ms ] #####\n");
			
			//图片数据处理
			String fileAbsolutePath = picBusiness(signPhotoByte,filePath,orderNO);
			
			long useTime3 = (System.currentTimeMillis()-start);
			start = System.currentTimeMillis();
			log.info("#####   call sign service save pic file [ " +  DateUtil.formatDate(new Date()) + ", time-consume:" + useTime3 + "ms ] #####\n");
			
			//数据持久化处理
			SignPic signPic = new SignPic();
			signPic.setExternalId(unionPayBean.getRetrievalReferenceNumber());
			signPic.setCreditTime(new Date());
			signPic.setSignFilePath(fileAbsolutePath);
			signPic.setOrderId(order == null ? null : order.getId());
			signPic.setICSystemRelated(unionPayBean.getICSystemRelated());
			signPic.setSynMark("N");
			
			signDBService.createSignPic(signPic);
			
			long useTime4 = (System.currentTimeMillis()-start);
			start = System.currentTimeMillis();
			log.info("#####   call sign service createSignPic ,DB [ " +  DateUtil.formatDate(new Date()) + ", time-consume:" + useTime4 + "ms ] #####\n");
			
			//设置返回报文		
			unionPayBean.setTimeLocalTransaction(DateUtil.formatNow("HHmmss"));		//12	受卡方所在地时间
			unionPayBean.setDateLocalTransaction(DateUtil.formatNow("MMdd"));		//13	受卡方所在地日期
			unionPayBean.setAquiringInstitutionId("10000001");						//32	受理方标识码
			unionPayBean.setResponseCode(Constant.SUCCESS_POS_RESPONSE);			//39	应答码
			
		}catch(Exception e){
			log.info("---Exception--->：" ,e);
			throw e;
		}
		return extendPayBean;
	}
	/**
	 * 将图片数据按照指定的路径保存
	 * 存储路径格式：filePath/日期/订单号
	 * 订单号作为文件名
	 * @param signPhotoByte
	 * @throws Exception 
	 * @return  return file 's absolutePath
	 */
	private String picBusiness(byte[] signPhotoByte,String filePath,String orderID) throws Exception{
		File file = null;
		String absolutePath = null;
		String dateStr = DateUtil.formatDate(new Date(), "yyyy-MM-dd");
		filePath += dateStr ;
		file = new File(filePath);
		//创建目录
		if(!file.exists()){
			file.mkdirs();
		}
		filePath += "/" + orderID;
		file = new File(filePath);
		
		log.info("orderID:" + orderID + "filePath:" + file);
		if(! file.exists()){
			file.createNewFile();
			//写入到硬盘中
		}
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(signPhotoByte);
		fos.close(); 
		
		log.info("---signPic save success---!");
		absolutePath = file.getAbsolutePath();
		return absolutePath;
	}
	/**
	 * @Description: TODO数据校验
	 * @date 2013年9月9日 上午9:55:25
	 * @author guangdong.yao
	 */
	public boolean check(Order order,UnionPayBean unionPayBean){
		String shopNO = orderDBService.getShopNO(order.getShopId());
		//getCardAcceptorId()店铺号，getCardAcceptorTerminalId()终端号
//		if(!OrderStatus.SUCCESS.equals(order.getStatus())&&!OrderStatus.SETTLED.equals(order.getStatus())){
//			return false;
//		}
		if(
//			unionPayBean.getCardAcceptorId() != null && 
//				unionPayBean.getCardAcceptorId().equals(shopNO) &&
				unionPayBean.getCardAcceptorTerminalId() != null &&
				unionPayBean.getCardAcceptorTerminalId().equals(order.getPosCati())&&
				(OrderStatus.SUCCESS.equals(order.getStatus())||OrderStatus.SETTLED.equals(order.getStatus())||
				OrderStatus.REPEAL.equals(order.getStatus())||OrderStatus.AUTHORIZED.equals(order.getStatus()))) {
			return true;
		} else {
			return false;
		}
	}

}

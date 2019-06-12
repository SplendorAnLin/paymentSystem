package com.yl.payinterface.core;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.cmbc.unps.service.BasePostService;
import com.cmbc.unps.util.Constants;

/**
 * 下载日终结算文件(对账文件，交易明细文件)，后台类交易，多次交互，分段下载
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月27日
 * @version V1.0.0
 */
public class PAY_F001Demo {

	public static void main(String[] args) {
		// 加载payment.ini配置文件
//		Configuration.loadConfiguation(Cmbc584002Constant.PAYMENT_PATH);

		Map<String, Object> reqMap = new HashMap<String, Object>();

		// 技术参数
		reqMap.put("version", "1.0"); // 版本
		reqMap.put("source", "PC"); // 交易渠道
		reqMap.put("merchantNum", "3300065000000354"); // 商户ID
		reqMap.put("merchantSeq", "3300065000000354201612338193854TD-177398ae0fca0000"); // 商户请求流水号
		reqMap.put("transDate", Constants.sf1.format(new Date())); // 交易日期 
		reqMap.put("transTime", Constants.sf2.format(new Date())); // 交易时间
		reqMap.put("transCode", "PAY_F001"); // 交易码
		reqMap.put("securityType", "SM203"); // 安全规则

		// 业务参数
		reqMap.put("contractId", "01201604250913070007"); // 签约Id
		reqMap.put("fileTranDate", "20161203"); // 日期
		reqMap.put("fileType", "detail"); // 文件类型
		reqMap.put("segmentIndex", "0"); // 当前块，从零开始
		

		//追加
// 		reqMap.put("transbackurl","http://111.205.207.103:18180/pay/trans/service.do");
//		reqMap.put("queryurl","http://111.205.207.103:18180/pay/query/service.do");
		reqMap.put("fileurl","http://111.205.207.103:18180/pay/file/service.do");
//		reqMap.put("transfronturl","http://111.205.207.103:18180/pay/trans/service.html");
 		reqMap.put("custsm2path","/cmbc584002/cert/merTest.sm2");
 		reqMap.put("custsm2pass","123123");
 		reqMap.put("custcertpass","/cmbc584002/cert/merTest.cer");
 		reqMap.put("cmbccertpath","/cmbc584002/cert/cmbcTest.cer");
 		

		BasePostService basePostService = new BasePostService();
		String path = "/Users/guanfenglin/unps_test/" + reqMap.get("fileType") + "/" + reqMap.get("fileTranDate");

		try {
			System.out.println("请求明文：" +reqMap);
			String ret = basePostService.postFile(reqMap, path);
			System.out.println("文件路径" + ret);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 仅参考，商户自定义流水号生成
	// 商户发起交易时使用的流水，为防止与其他交易系统流水号重复，特定义流水号组成规则如下：字母、数字、下划线，长度须为50位。 以商户编号为前缀，需补齐后总长度50位
	public static long seq = 0;
	public static synchronized String genMerchantSeq(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String dateTime = sdf.format(new Date());
		String merchantSeqPre ="3300065000000354"/* Configuration.getConfig(Constants.PARAM_MERCHANT_NUM) + dateTime*/;
		int seqLength = 50 - merchantSeqPre.length();
		String merchantSeq = merchantSeqPre + String.format("%0" + seqLength + "d", seq); // 仅参考，商户自定义流水号生成
		seq++;
		return merchantSeq;
	}

}

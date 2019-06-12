package com.yl.payinterface.core.handle.impl.wallet.shand100001;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import com.yl.payinterface.core.handle.impl.wallet.shand100001.request.*;
import com.yl.payinterface.core.handle.impl.wallet.shand100001.utils.CertUtil;
import com.yl.payinterface.core.handle.impl.wallet.shand100001.utils.PacketTool;
import com.yl.payinterface.core.handle.impl.wallet.shand100001.utils.SandpayClient;
import com.yl.payinterface.core.handle.impl.wallet.shand100001.utils.SandpayResponseHead;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.DateUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.enums.InterfaceType;
import com.yl.payinterface.core.handle.impl.wallet.cncb330000.Cncb330000HandlerImpl;
import com.yl.payinterface.core.handle.impl.wallet.shand100001.request.QrOrderCancelRequest.QrOrderCancelRequestBody;
import com.yl.payinterface.core.handle.impl.wallet.shand100001.request.QrOrderCreateRequest.QrOrderCreateRequestBody;
import com.yl.payinterface.core.handle.impl.wallet.shand100001.request.QrOrderQueryRequest.QrOrderQueryRequestBody;
import com.yl.payinterface.core.handle.impl.wallet.shand100001.request.QrOrderRefundRequest.QrOrderRefundRequestBody;
import com.yl.payinterface.core.handle.impl.wallet.shand100001.response.QrOrderCancelResponse;
import com.yl.payinterface.core.handle.impl.wallet.shand100001.response.QrOrderCreateResponse;
import com.yl.payinterface.core.handle.impl.wallet.shand100001.response.QrOrderQueryResponse;
import com.yl.payinterface.core.handle.impl.wallet.shand100001.response.QrOrderRefundResponse;
import com.yl.payinterface.core.handler.ChannelRecionHandler;
import com.yl.payinterface.core.handler.CloseOrderHandler;
import com.yl.payinterface.core.handler.RefundHandler;
import com.yl.payinterface.core.handler.WalletPayHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.utils.CodeBuilder;

/**
 * 杉德100001处理
 * 
 * @author AnLin
 * @since 2016年11月14日
 * @version V1.0.0
 */
@Service("walletShand100001Handler")
public class Shand100001Handler implements WalletPayHandler, RefundHandler, CloseOrderHandler, ChannelRecionHandler{
	
	private static Logger logger = LoggerFactory.getLogger(Cncb330000HandlerImpl.class);

	static {
		try {
            CertUtil.init("/home/cer/sand/sand.cer", "/home/cer/sand/sand.pfx", "123456");
		} catch (Exception e) {
			logger.error("衫德扫码支付加载证书异常:{}", e);
		}
	}
	
	public static void main(String[] args) {
		String url = "https://cashier.sandpay.com.cn/qr/api/order/create";
		String mid = "10707272";
		String notifyUrl = "https://kd.alblog.cn/front/callback";
		Properties properties = new Properties();
		properties.setProperty("payUrl", url);
		properties.setProperty("mid", mid);
		properties.setProperty("notifyUrl", notifyUrl);
		Map<String, String> params = new HashMap<>();
		params.put("tradeConfigs", JsonUtils.toJsonString(properties));
		params.put("interfaceType", "UNIONPAYNATIVE");
		params.put("interfaceRequestID", CodeBuilder.build("TD","yyyyMMdd"));
		params.put("amount", "0.01");
		params.put("productName", "测试");
		params.put("interfaceInfoCode", "SHAND_100001-WALLET");
		new Shand100001Handler().pay(params);
	}
	
	@Override
	public Map<String, String> pay(Map<String, String> map) {
		
		Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
		SandpayRequestHead head = new SandpayRequestHead();
		QrOrderCreateRequestBody body = new QrOrderCreateRequestBody();
		 
		QrOrderCreateRequest req = new QrOrderCreateRequest();
		req.setHead(head);
		req.setBody(body);
		
		//支付工具 0401：支付宝扫码 0402：微信扫码
		if (InterfaceType.UNIONPAYNATIVE.toString().equals(map.get("interfaceType"))) {
            PacketTool.setDefaultRequestHead(head, SandpayConstants.QR_ORDERCREATE, SandpayConstants.UNIONPAYNATIVE, properties.getProperty("mid"));
            body.setPayTool("0403");
        } else if(InterfaceType.ALIPAY.toString().equals(map.get("interfaceType"))){
			PacketTool.setDefaultRequestHead(head, SandpayConstants.QR_ORDERCREATE, SandpayConstants.ALIPAY_PRODUCTID, properties.getProperty("mid"));
			body.setPayTool("0401");
		} else if(InterfaceType.WXNATIVE.toString().equals(map.get("interfaceType"))){
			PacketTool.setDefaultRequestHead(head, SandpayConstants.QR_ORDERCREATE, SandpayConstants.WXPAY_PRODUCTID, properties.getProperty("mid"));
			body.setPayTool("0402");
		} else {
			throw new RuntimeException("interfaceType is error!");
		}
		body.setOrderCode(map.get("interfaceRequestID"));  //商户订单号
//		body.setLimitPay("0");  //限定支付方式 支付工具为微信扫码有效 1-限定不能使用信用卡
		if(map.get("amount").length() != 12){
			String tmpAmt = String.valueOf((int)AmountUtils.multiply(Double.valueOf(map.get("amount")), 100));
			for(;;){
				tmpAmt = "0"+tmpAmt;
				if(tmpAmt.length() == 12){
					body.setTotalAmount(tmpAmt);  //订单金额
					break;
				}
			}
		}else{
			body.setTotalAmount(map.get("amount"));
		}
		body.setSubject(map.get("productName"));  //订单标题
		body.setBody(map.get("productName"));  //订单描述
		body.setTxnTimeOut(new SimpleDateFormat("yyyyMMddHHmmss").format(DateUtils.addDays(new Date(), 1)));  //订单超时时间
		body.setStoreId("");  //商户门店编号
		body.setTerminalId("");  //商户终端编号
		body.setOperatorId("");  //操作员编号
		body.setNotifyUrl(properties.getProperty("notifyUrl"));  //异步通知地址
		body.setBizExtendParams("");  //业务扩展参数
		body.setMerchExtendParams(map.get("interfaceInfoCode"));  //商户扩展参数
		body.setExtend(map.get("interfaceInfoCode"));  //扩展域
		
		logger.info("WalletShand100001 下单请求明文  接口编号:[{}],订单号:[{}],请求报文:[{}]", map.get("interfaceInfoCode"),map.get("interfaceRequestID"),JsonUtils.toJsonString(body));
		QrOrderCreateResponse resp;
		try {
			resp = SandpayClient.execute(req, properties.getProperty("payUrl"));
			logger.info("WalletShand100001 下单响应明文  接口编号:[{}],订单号:[{}],响应报文:[{}]", map.get("interfaceInfoCode"),map.get("interfaceRequestID"),JsonUtils.toJsonString(resp));
			SandpayResponseHead respHead = resp.getHead();
			if(SandpayConstants.SUCCESS_RESP_CODE.equals(respHead.getRespCode())) {
				logger.info("txn success.");
			} else {
				throw new RuntimeException("接口编号:["+map.get("interfaceInfoCode")+"],订单号:[{"+map.get("interfaceRequestID")+"}],下单失败");
			}
		} catch (Exception e) {
			logger.info("WalletShand100001 下单响应明文  接口编号:[{}],订单号:[{}],下单异常:[{}]", map.get("interfaceInfoCode"),e);
			throw new RuntimeException(e.getMessage());
		}
		Map<String,String> resParams = new HashMap<>();
		resParams.put("code_url", resp.getBody().getQrCode());
		resParams.put("code_img_url", "");
		resParams.put("interfaceInfoCode", map.get("interfaceInfoCode"));
		resParams.put("interfaceRequestID", map.get("interfaceRequestID"));
		resParams.put("gateway", "native");
		return resParams;
	}

	@Override
	public Map<String, String> query(Map<String, String> map) {
		SandpayRequestHead head = new SandpayRequestHead();
		QrOrderQueryRequestBody body = new QrOrderQueryRequestBody();
		
		QrOrderQueryRequest req = new QrOrderQueryRequest();
		req.setHead(head);
		req.setBody(body);
		
		PacketTool.setDefaultRequestHead(head, SandpayConstants.QR_ORDERQUERY, SandpayConstants.WXPAY_PRODUCTID, "800000000000000");
		body.setOrderCode("20161230222220001");  //商户订单号
		body.setExtend("");  //扩展域
		
		try {
			
			String url = "http://61.129.71.103:8003/qr/api/order/query";
			
			QrOrderQueryResponse resp = SandpayClient.execute(req, url);
			
			SandpayResponseHead respHead = resp.getHead();
			
			if(SandpayConstants.SUCCESS_RESP_CODE.equals(respHead.getRespCode())) {
				logger.info("txn success.");
			} else {
				logger.error("txn fail respCode[{}],respMsg[{}].", respHead.getRespCode(), respHead.getRespMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public InterfaceRequest complete(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> refund(Map<String, String> refundParams) {
		SandpayRequestHead head = new SandpayRequestHead();
		QrOrderRefundRequestBody body = new QrOrderRefundRequestBody();
		
		QrOrderRefundRequest req = new QrOrderRefundRequest();
		req.setHead(head);
		req.setBody(body);
		
		PacketTool.setDefaultRequestHead(head, SandpayConstants.QR_ORDERREFUND, SandpayConstants.WXPAY_PRODUCTID, "800000000000000");
		body.setOrderCode("20161230222220002");  //商户订单号
		body.setOriOrderCode("20161230222220001");  //原商户订单号
		body.setRefundAmount("000000000012");  //退货金额
		body.setRefundReason("test fail.");  //退货原因
		body.setExtend("");  //扩展域
		
		try {
			
			String url = "http://61.129.71.103:8003/qr/api/order/refund";
			QrOrderRefundResponse resp = SandpayClient.execute(req, url);
			
			SandpayResponseHead respHead = resp.getHead();
			
			if(SandpayConstants.SUCCESS_RESP_CODE.equals(respHead.getRespCode())) {
				logger.info("txn success.");
			} else {
				logger.error("txn fail respCode[{}],respMsg[{}].", respHead.getRespCode(), respHead.getRespMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Map<String, String> refundQuery(Map<String, String> refundParams) {
		return null;
	}

	@Override
	public Map<String, String> closeOrder(Map<String, String> closeOrderParams) {
		SandpayRequestHead head = new SandpayRequestHead();
		QrOrderCancelRequestBody body = new QrOrderCancelRequestBody();
		
		QrOrderCancelRequest req = new QrOrderCancelRequest();
		req.setHead(head);
		req.setBody(body);
		
		PacketTool.setDefaultRequestHead(head, SandpayConstants.QR_ORDERCANCEL, SandpayConstants.WXPAY_PRODUCTID, "800000000000000");
		body.setOrderCode("20161230222220002");  //商户订单号
		body.setOriOrderCode("20161230222220001");  //原商户订单号
		body.setOriTotalAmount("0000000000012");  //原订单金额
		body.setExtend("");  //扩展域
		
		try {
			
			String url = "http://61.129.71.103:8003/qr/api/order/cancel";
			QrOrderCancelResponse resp = SandpayClient.execute(req, url);
			
			SandpayResponseHead respHead = resp.getHead();
			
			if(SandpayConstants.SUCCESS_RESP_CODE.equals(respHead.getRespCode())) {
				logger.info("txn success.");
			} else {
				logger.error("txn fail respCode[{}],respMsg[{}].", respHead.getRespCode(), respHead.getRespMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Map<String, String> recion(Map<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}

}

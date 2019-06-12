package com.yl.boss.interfaces.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.yl.payinterface.core.bean.InterfaceInfoBean;

import net.mlw.vlh.ValueList;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.lefu.commons.cache.bean.Dictionary;
import com.lefu.commons.cache.bean.DictionaryType;
import com.lefu.commons.cache.tags.DictionaryUtils;
import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.security.DigestUtils;
import com.yl.account.api.bean.AccountBean;
import com.yl.account.api.bean.request.AccountQuery;
import com.yl.account.api.bean.response.AccountQueryResponse;
import com.yl.account.api.dubbo.AccountQueryInterface;
import com.yl.account.api.enums.UserRole;
import com.yl.boss.Constant;
import com.yl.boss.action.AdAction;
import com.yl.boss.api.bean.Customer;
import com.yl.boss.api.bean.CustomerCertBean;
import com.yl.boss.api.bean.CustomerFee;
import com.yl.boss.api.bean.CustomerKey;
import com.yl.boss.api.bean.CustomerSettle;
import com.yl.boss.api.bean.Shop;
import com.yl.boss.api.bean.TransCardBean;
import com.yl.boss.api.bean.TransCardHistoryBean;
import com.yl.boss.api.enums.KeyType;
import com.yl.boss.api.enums.OrderStatus;
import com.yl.boss.api.enums.ProcessStatus;
import com.yl.boss.api.enums.ProductType;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.boss.entity.AgentDeviceOrder;
import com.yl.boss.entity.CustomerCert;
import com.yl.boss.entity.CustomerHistory;
import com.yl.boss.entity.Device;
import com.yl.boss.entity.DeviceType;
import com.yl.boss.entity.Pos;
import com.yl.boss.entity.PosPurchase;
import com.yl.boss.entity.TransCard;
import com.yl.boss.entity.TransCardHistory;
import com.yl.boss.entity.UserFeedback;
import com.yl.boss.enums.CardAttr;
import com.yl.boss.enums.CardStatus;
import com.yl.boss.enums.CardType;
import com.yl.boss.enums.CustomerStatus;
import com.yl.boss.enums.CustomerType;
import com.yl.boss.enums.PosType;
import com.yl.boss.enums.PurchaseType;
import com.yl.boss.enums.RequestRecord;
import com.yl.boss.service.CustomerCertService;
import com.yl.boss.service.CustomerFeeService;
import com.yl.boss.service.CustomerHistoryService;
import com.yl.boss.service.CustomerKeyService;
import com.yl.boss.service.CustomerService;
import com.yl.boss.service.CustomerSettleService;
import com.yl.boss.service.DeviceService;
import com.yl.boss.service.PosService;
import com.yl.boss.service.TransCardHistoryService;
import com.yl.boss.service.TransCardService;
import com.yl.boss.service.UserFeedBackService;
import com.yl.boss.utils.CodeBuilder;
import com.yl.boss.utils.HttpUtil;
import com.yl.boss.valuelist.ValueListRemoteAction;
import com.yl.dpay.hessian.beans.ServiceConfigBean;
import com.yl.online.trade.hessian.OnlinePartnerRouterHessianService;
import com.yl.online.trade.hessian.bean.InterfaceInfoForRouterBean;
import com.yl.online.trade.hessian.bean.InterfacePolicyProfileBean;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;
import com.yl.receive.hessian.ReceiveQueryFacade;
import com.yl.receive.hessian.bean.ReceiveConfigInfoBean;

/**
 * 商户信息远程接口实现
 *
 * @author 聚合支付有限公司
 * @since 2016年9月19日
 * @version V1.0.0
 */
@SuppressWarnings("restriction")
public class CustomerInterfaceImpl implements CustomerInterface {

	private CustomerService customerService;
	private CustomerKeyService customerKeyService;
	private CustomerSettleService customerSettleService;
	private CustomerFeeService customerFeeService;
	private ValueListRemoteAction valueListRemoteAction;
	private AccountQueryInterface accountQueryInterface;
	private ReceiveConfigInfoBean receiveConfigInfoBean;
	private CustomerCertService customerCertService;
	private ReceiveQueryFacade receiveQueryFacade;
	private DeviceService deviceService;
	private CustomerHistoryService customerHistoryService;
	private UserFeedBackService userFeedBackService;
	private TransCardService transCardService;
	private PayInterfaceHessianService payInterfaceHessianService;
	private PosService posService;
	private TransCardHistoryService transCardHistoryService;
	private OnlinePartnerRouterHessianService onlinePartnerRouterHessianService;
	private DictionaryType dictionaryTypeRanged;
	private List<Dictionary> dictionaryRangedList;

	private static final Logger logger = LoggerFactory.getLogger(CustomerInterface.class);

	private static Properties prop = new Properties();

	static {
		try {
			prop.load(new InputStreamReader(AdAction.class.getClassLoader().getResourceAsStream("system.properties")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, Object> querySettle(String customerNo) {
		Map<String, Object> result = new HashMap<>();
		try {
			com.yl.boss.entity.CustomerSettle customerSettle = customerSettleService.findByCustNo(customerNo);
			CustomerCert customerCert = customerCertService.findByCustNo(customerNo);
			com.yl.boss.entity.Customer customer = customerService.findByCustNo(customerNo);
			if (customerSettle == null || customerCert == null || customer == null) {
				result.put("resCode", "ERROR");
				return result;
			}
			result.put("customerNo", customerNo);
			result.put("handheldBank", GetImageStr(customerCert.getOrganizationCert()));
			result.put("handheldId", GetImageStr(customerCert.getIdCard()));
			result.put("accountName", customerSettle.getAccountName());
			result.put("accountNo", customerSettle.getAccountNo());
			result.put("idCard", customer.getIdCard()==null?"":customer.getIdCard());
			result.put("bankCode", customerSettle.getBankCode());
			result.put("customerType", customerSettle.getCustomerType().name());
			result.put("openBankName", customerSettle.getOpenBankName());
			result.put("resCode", "SUCCESS");
			return result;
		} catch (Exception e) {
			result.put("resCode", "ERROR");
			logger.error("APP查询商户结算卡信息失败!{}", e);
		}
		return result;
	}

	@Override
	public String customerSettle(Map<String, Object> params) {
		String customerNo = params.get("customerNo").toString();
		// 商户证件信息填充
		String idImgFilePath = prop.getProperty("sweepTheNetwork") + CodeBuilder.build("ID", "yyyyMMdd") + ".jpg";
		String bankImgFilePath = prop.getProperty("sweepTheNetwork") + CodeBuilder.build("BK", "yyyyMMdd") + ".jpg";

		// 图像数据为空
		if (params.get("handheldBank") == null || params.get("handheldId") == null) {
			logger.error("************** 扫码入网未获取到图片数据{} **************", customerNo);
			return "ERROR";
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] bank = decoder.decodeBuffer(params.get("handheldBank").toString());
			for (int i = 0; i < bank.length; ++i) {
				// 调整异常数据
				if (bank[i] < 0) {
					bank[i] += 256;
				}
			}
			// Base64解码
			byte[] id = decoder.decodeBuffer(params.get("handheldId").toString());
			for (int i = 0; i < id.length; ++i) {
				// 调整异常数据
				if (id[i] < 0) {
					id[i] += 256;
				}
			}
			// 生成jpeg图片
			OutputStream handheldBank = new FileOutputStream(bankImgFilePath);
			OutputStream handheldId = new FileOutputStream(idImgFilePath);
			handheldBank.write(bank);
			handheldId.write(id);
			handheldBank.flush();
			handheldId.flush();
			handheldBank.close();
			handheldId.close();
			CustomerCert customerCert = new CustomerCert();
			customerCert.setIdCard(idImgFilePath);
			customerCert.setOrganizationCert(bankImgFilePath);
			customerCert.setCustomerNo(customerNo);

			// 商户结算卡信息补充
			com.yl.boss.entity.CustomerSettle customerSettle = new com.yl.boss.entity.CustomerSettle();
			customerSettle.setAccountName(params.get("accountName").toString());
			customerSettle.setAccountNo(params.get("accountNo").toString());
			JSONObject recognition = recognition(customerSettle.getAccountNo());
			if (recognition.containsKey("providerCode")) {
				customerSettle.setBankCode(recognition.getString("providerCode"));
			}
			customerSettle.setCustomerType(CustomerType.INDIVIDUAL);
			customerSettle.setOpenBankName(params.get("openBankName").toString());

			customerService.createSettleInfo(customerNo, customerCert, customerSettle, params.get("idCard").toString(), "APP结算信息补录", "APP");
			return "SUCCESS";
		} catch (Exception e) {
			logger.error("快速入网提交结算卡信息失败！商户编号：{} 异常信息：{}", customerNo, e);
			return "ERROR";
		}
	}

	@Override
	public String updateCreateSettle(Map<String, Object> params) {
		String customerNo = params.get("customerNo").toString();
		// 商户证件信息填充
		String idImgFilePath = prop.getProperty("sweepTheNetwork") + CodeBuilder.build("ID", "yyyyMMdd") + ".jpg";
		String bankImgFilePath = prop.getProperty("sweepTheNetwork") + CodeBuilder.build("BK", "yyyyMMdd") + ".jpg";

		// 图像数据为空
		if (params.get("handheldBank") == null || params.get("handheldId") == null) {
			logger.error("************** 扫码入网未获取到图片数据{} **************", customerNo);
			return "ERROR";
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] bank = decoder.decodeBuffer(params.get("handheldBank").toString());
			for (int i = 0; i < bank.length; ++i) {
				// 调整异常数据
				if (bank[i] < 0) {
					bank[i] += 256;
				}
			}
			// Base64解码
			byte[] id = decoder.decodeBuffer(params.get("handheldId").toString());
			for (int i = 0; i < id.length; ++i) {
				// 调整异常数据
				if (id[i] < 0) {
					id[i] += 256;
				}
			}
			// 生成jpeg图片
			OutputStream handheldBank = new FileOutputStream(bankImgFilePath);
			OutputStream handheldId = new FileOutputStream(idImgFilePath);
			handheldBank.write(bank);
			handheldId.write(id);
			handheldBank.flush();
			handheldId.flush();
			handheldBank.close();
			handheldId.close();
			CustomerCert customerCert = new CustomerCert();
			customerCert.setIdCard(idImgFilePath);
			customerCert.setOrganizationCert(bankImgFilePath);

			
			// 商户结算卡信息补充
			com.yl.boss.entity.CustomerSettle customerSettle = new com.yl.boss.entity.CustomerSettle();
			customerSettle.setAccountName(params.get("accountName").toString());
			customerSettle.setAccountNo(params.get("accountNo").toString());
			JSONObject recognition = recognition(customerSettle.getAccountNo());
			if (recognition.containsKey("providerCode")) {
				customerSettle.setBankCode(recognition.getString("providerCode"));
			}
			customerSettle.setCustomerType(CustomerType.INDIVIDUAL);
			customerSettle.setOpenBankName(params.get("openBankName").toString());
			
			customerService.updateCreateSettle(customerNo, customerCert, customerSettle, "APP结算信息修改");
		} catch (Exception e) {
			logger.error("快速入网修改结算卡信息失败！商户编号：{} 异常信息：{}", customerNo, e);
			return "ERROR";
		}
		return "SUCCESS";
	}

	public static String GetImageStr(String imgFilePath) {
		byte[] data = null;
		try {
			InputStream in = new FileInputStream(imgFilePath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			logger.error("Base64处理失败!{}", e);
		}
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}

	@Override
	public List<Map<String, Object>> getDevicesInfo(Map<String, Object> params) {
		List<Map<String, Object>> res = new ArrayList<>();
		List<DeviceType> list = deviceService.getDeviceType();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = new HashMap<>();
				map.put("id", list.get(i).getId());
				map.put("parentId", list.get(i).getParentId());
				map.put("deviceName", list.get(i).getDeviceName());
				map.put("status", list.get(i).getStatus().name());
				map.put("remark", list.get(i).getRemark());
				map.put("unitPrice", list.get(i).getUnitPrice());
				res.add(map);
			}
		}
		return res;
	}

	@Override
	public Map<String, Object> appDevice(Map<String, Object> params) {
		Map<String, Object> result = new HashMap<>();
		try {
			// 水牌订单检验
			AgentDeviceOrder agentDeviceOrder = new AgentDeviceOrder();
			agentDeviceOrder.setDeviceTypeId(Long.valueOf(params.get("id").toString()));
			agentDeviceOrder.setUser(params.get("userName").toString());
			agentDeviceOrder.setFlowStatus(ProcessStatus.ORDER_WAIT);
			agentDeviceOrder.setQuantity(1);
			agentDeviceOrder.setPurchaseChannel(PurchaseType.CUSTOMER);
			agentDeviceOrder.setOrderStatus(OrderStatus.WAIT_PAY);
			agentDeviceOrder.setOwnerId(params.get("customerNo").toString());
			int count = deviceService.yOrNoOrder(agentDeviceOrder);
			if (count != 0) {
				result.put("resMsg", "请勿重复申请！");
				result.put("resCode", "EXIST");
				return result;
			}
			deviceService.createAgentOrder(agentDeviceOrder, "APP");
			result.put("resMsg", "申请成功！");
			result.put("resCode", "SUCCESS");

		} catch (Exception e) {
			result.put("resMsg", "申请失败！请联系管理员或稍后再试！");
			result.put("resCode", "ERROR");
			logger.error("App 水牌申请失败！{}", e);
		}
		return result;
	}

	@Override
	public Map<String, Object> getPosApplicationInfo(Map<String, Object> params) {
		Map<String, Object> result = new HashMap<>();
		try {
			PosPurchase posPurchase = new PosPurchase();
			posPurchase.setAddr(params.get("addr").toString());
			posPurchase.setCreateTime(new Date());
			posPurchase.setCustomerNo(params.get("customerNo").toString());
			posPurchase.setPhone(params.get("phone").toString());
			posPurchase.setPosType(PosType.valueOf(params.get("posType").toString()));
			posPurchase.setRemark(params.get("remark").toString());
			posPurchase.setRequestRecord(RequestRecord.valueOf(params.get("requestRecord").toString()));
			posService.posPurchaseAdd(posPurchase);
			result.put("resMsg", "申请成功！工作人员将尽快与您取得联系！");
			result.put("resCode", "SUCCESS");
		} catch (Exception e) {
			result.put("resMsg", "申请失败！请联系管理员或稍后再试！");
			result.put("resCode", "ERROR");
			logger.error("POS申请记录失败！{}", e);
		}
		return result;
	}

	@Override
	public Map<String, Object> fastAddTransCard(String customerNo, String accountNo, String cardAttr) {
		com.yl.boss.entity.Customer customer = customerService.findByCustNo(customerNo);
		com.yl.boss.entity.CustomerSettle customerSettle = customerSettleService.findByCustNo(customerNo);
		Map<String, Object> param = new HashMap<>();
		if (customerSettle == null || StringUtils.isBlank(customer.getIdCard()) || StringUtils.isBlank(customer.getPhoneNo())) {
			logger.error("该商户暂无结算卡信息或未填写身份证号手机号！");
	        param = new HashMap<>();
	        param.put("responseMsg", "该商户暂无结算卡信息或未填写身份证号手机号！");
	        param.put("msg", "ERROR");
	        return param;
		}
		TransCard transCard = new TransCard();
		// 商户绑定交易卡 结算卡连带绑定
		if ("TRANS_CARD".equals(cardAttr)) {
			TransCard settle = new TransCard();
			// 商户结算卡检测
			boolean seet = false;
			List<TransCard> cards = transCardService.findAllByCustNo(customerNo);
			TransCard seettle = null;
			if (cards != null && cards.size() > 0) {
				for (int i = 0; i < cards.size(); i++) {
					seettle = cards.get(i);
					// 匹配原结算卡
					if (seettle.getCardAttr().name().equals("SETTLE_CARD")
							&& seettle.getCardStatus().name().equals("NORAML")) {
						param.put("settleCode", seettle.getCode());
						param.put("cleAccName", seettle.getAccountName());
						settle = seettle;
						seet = true;
						break;
					}
				}
			}
			TransCard db = transCardService.findByCustNoAndAccNo(customerNo, accountNo, CardAttr.TRANS_CARD);
			if (db != null) {
				if (!db.getCardStatus().name().equals("UNBUNDLED")) {
					param = new HashMap<>();
					param.put("responseMsg", "此卡已绑定或已被禁用！");
					param.put("msg", "ERROR");
					return param;
				}
			}
			if (!seet) {
				// 从商户结算卡获取
				settle.setAccountNo(customerSettle.getAccountNo());
				// 卡信息补充
				supplement(settle);
				settle.setAccountName(customerSettle.getAccountName());
				settle.setBankName(customerSettle.getOpenBankName());
				settle.setCardAttr(CardAttr.SETTLE_CARD);
				settle.setCardStatus(CardStatus.NORAML);
				settle.setCode(CodeBuilder.build("TC", "yyyyMMdd"));
				settle.setCustomerNo(customerNo);
				settle.setIdNumber(customer.getIdCard());
				settle.setPhone(customer.getPhoneNo());
				settle.setTiedTime(new Date());
				param.put("settleCode", settle.getCode());
			}
			transCard.setAccountNo(accountNo);
			// 卡信息补充
			supplement(transCard);
			transCard.setAccountName(customerSettle.getAccountName());
			transCard.setCardAttr(CardAttr.TRANS_CARD);
			transCard.setCardStatus(CardStatus.NORAML);
			transCard.setCode(CodeBuilder.build("TC", "yyyyMMdd"));
			transCard.setCustomerNo(customerNo);
			transCard.setIdNumber(customer.getIdCard());
			transCard.setPhone(customer.getPhoneNo());
			transCard.setTiedTime(new Date());
			transCard.setSettleCode(param.get("settleCode").toString());
			// 实名认证参数组装
			param.put("customerNo", customerNo);
			param.put("accountNo", accountNo);
			param.put("phone", customer.getPhoneNo());
			param.put("idNumber", customer.getIdCard());
			if (!seet) {
				param.put("cleAccName", settle.getAccountName());
				param.put("settleCode", settle.getCode());
			}
			JSONObject result = verified(param);
			if (result.containsKey("responseCode")) {
				// 认证成功，结果正确
				if (result.get("responseCode").equals("0001")) {
					if (!seet) {
						transCardService.addTransCard(settle);
					}
					transCardService.addTransCard(transCard);
					param = new HashMap<>();
					param.put("msg", "SUCCESS");
					return param;
				} else {
					param = new HashMap<>();
					param.put("responseMsg", result.get("responseMsg"));
					param.put("msg", "ERROR");
					return param;
				}
			}
		} else {
			// 结算卡绑定
			List<TransCard> settles = transCardService.findByCustAttr(customerNo, CardAttr.SETTLE_CARD);
			if (settles != null && settles.size() > 0) {
				param = new HashMap<>();
				param.put("responseMsg", "商户已绑定结算卡！");
				param.put("msg", "ERROR");
				return param;
			}
			
			TransCard settle = new TransCard();
			settle.setAccountNo(accountNo);
			// 卡信息补充
			supplement(settle);
			settle.setAccountName(customerSettle.getAccountName());
			settle.setCardAttr(CardAttr.SETTLE_CARD);
			settle.setCardStatus(CardStatus.NORAML);
			settle.setCode(CodeBuilder.build("TC", "yyyyMMdd"));
			settle.setCustomerNo(customerNo);
			settle.setIdNumber(customer.getIdCard());
			settle.setPhone(customer.getPhoneNo());
			settle.setTiedTime(new Date());

			// 实名认证参数组装
			param.put("customerNo", customerNo);
			param.put("accountNo", accountNo);
			param.put("phone", customer.getPhoneNo());
			param.put("idNumber", customer.getIdCard());
			param.put("cleAccName", settle.getAccountName());
			param.put("settleCode", settle.getCode());
			JSONObject result = verified(param);
			if (result.containsKey("responseCode")) {
				// 认证成功，结果正确
				if (result.get("responseCode").equals("0001")) {
					transCardService.addTransCard(settle);
					param = new HashMap<>();
					param.put("msg", "SUCCESS");
					return param;
				} else {
					param = new HashMap<>();
					param.put("responseMsg", result.get("responseMsg"));
					param.put("msg", "ERROR");
					return param;
				}
			}
		}
		return null;
	}

	@Override
	public Map<String, Object> partnerRouter(String customerNo) {
		List<Map<String, Object>> list = onlinePartnerRouterHessianService.queryCustRouterByCustomerNo(customerNo);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map<String, Object> addTransCardForApi(Map<String, Object> param) {
		String responseCode = "REP00000";
		String responseMsg = "绑定成功！";
		//boolean nullFlag = false;
		TransCard settle = transCardService.findByCustAndAcc(param.get("customerNo").toString(),
				param.get("cleAccNo").toString(), CardAttr.SETTLE_CARD);
		TransCard transCard = transCardService.findByCustAndAcc(param.get("customerNo").toString(),
				param.get("accountNo").toString(), CardAttr.TRANS_CARD);
		if (transCard != null && !transCard.getCardStatus().name().equals("UNBUNDLED")) {
			responseMsg = "交易卡已绑定或已被禁用！";
			responseCode = "REP99994";
			param = new HashMap<>();
			param.put("responseCode", responseCode);
			param.put("responseMsg", responseMsg);
			return param;
		}
		/*// 判断结算卡
		List<TransCard> settles = transCardService.findByCustAttr(param.get("customerNo").toString(), CardAttr.SETTLE_CARD);
		if (settles != null && settles.size() > 0) {
			for (int i = 0; i < settles.size(); i++) {
				TransCard db = settles.get(i);
				if (db.getAccountNo().equals(param.get("cleAccNo"))) {
					nullFlag = true;
				}
			}
		} else {
			nullFlag = true;
		}
		if (!nullFlag) {
			responseCode = "REP99993";
			responseMsg = "结算卡信息不正确！";
			logger.error("结算卡信息不正确！");
			param = new HashMap<>();
			param.put("responseCode", responseCode);
			param.put("responseMsg", responseMsg);
			return param;
		}*/
		// 实名认证
		JSONObject result = verified(param);
		Map<String, Object> res = checkVerified(result);
		boolean flag = false;
		if (res != null && "REP00001".equals(res.get("responseCode"))) {
			try {
				if (settle == null) {
					flag = true;
					settle = new TransCard();
				}
				settle.setAccountNo(param.get("cleAccNo").toString());
				// 卡信息补充
				supplement(settle);
				settle.setAccountName(param.get("cleAccName").toString());
				settle.setBankName(param.get("cleBankName").toString());
				settle.setCardAttr(CardAttr.SETTLE_CARD);
				if (flag) {
					settle.setCardStatus(CardStatus.NORAML);
					settle.setCode(CodeBuilder.build("TC", "yyyyMMdd"));
					settle.setCustomerNo(param.get("customerNo").toString());
					settle.setIdNumber(param.get("idNumber").toString());
					settle.setPhone(param.get("phone").toString());
					settle.setTiedTime(new Date());
					transCardService.addTransCard(settle);
				} else {
					if (settle.getCardStatus().name().equals("UNBUNDLED")) {
						settle.setTiedTime(new Date());
						settle.setUnlockTime(null);
						settle.setCardStatus(CardStatus.NORAML);
					}
					transCardService.updateTransCard(settle);
				}
			} catch (Exception e) {
				responseCode = "REP99999";
				responseMsg = "绑定结算卡信息失败！";
				logger.error("绑定结算卡信息出错：{}", e);
			}

			try {
				flag = false;
				if (transCard == null) {
					flag = true;
					transCard = new TransCard();
				}
				transCard.setAccountNo(param.get("accountNo").toString());
				// 卡信息补充
				supplement(transCard);
				transCard.setAccountName(param.get("accountName").toString());
				transCard.setBankName(param.get("bankName").toString());
				transCard.setCardAttr(CardAttr.TRANS_CARD);
				transCard.setCardType(CardType.valueOf(param.get("cardType").toString()));
				transCard.setIdNumber(param.get("idNumber").toString());
				transCard.setPhone(param.get("phone").toString());
				transCard.setCardStatus(CardStatus.NORAML);
				transCard.setSettleCode(settle.getCode());
				if (flag) {
					transCard.setCode(CodeBuilder.build("TC", "yyyyMMdd"));
					transCard.setCustomerNo(param.get("customerNo").toString());
					transCard.setTiedTime(new Date());
					transCardService.addTransCard(transCard);
				} else {
					if (transCard.getCardStatus().name().equals("UNBUNDLED")) {
						transCard.setTiedTime(new Date());
						transCard.setUnlockTime(null);
						transCard.setCardStatus(CardStatus.NORAML);
					}
					transCardService.updateTransCard(transCard);
				}
			} catch (Exception e) {
				responseCode = "REP99998";
				responseMsg = "绑定卡交易卡失败！";
				logger.error("绑定卡交易卡出错：{}", e);
			}
		} else {
			return res;
		}
		param = new HashMap<>();
		param = transBeanMap(transCard);
		param.put("responseCode", responseCode);
		param.put("responseMsg", responseMsg);
		return param;
	}

	@Override
	public Map<String, Object> addTransCard(Map<String, Object> param) {
		String responseCode = "REP00000";
		String responseMsg = "绑定成功！";
		// 商户结算卡获取
		com.yl.boss.entity.CustomerSettle customerSettle = customerSettleService.findByCustNo(param.get("customerNo").toString());
		if (customerSettle == null) {
			responseMsg = "结算卡信息获取失败！";
			responseCode = "REP99993";
			param = new HashMap<>();
			param.put("resultCode", responseCode);
			param.put("resultMsg", responseMsg);
			return param;
		}
		TransCard settle = transCardService.findByCustNoAndAccNo(param.get("customerNo").toString(),
				customerSettle.getAccountNo(), CardAttr.SETTLE_CARD);
		TransCard transCard = transCardService.findByCustNoAndAccNo(param.get("customerNo").toString(),
				param.get("accountNo").toString(), CardAttr.TRANS_CARD);
		/*boolean nullFlag = false;
		// 判断结算卡
		List<TransCard> settles = transCardService.findByCustAttr(param.get("customerNo").toString(), CardAttr.SETTLE_CARD);
		if (settles != null && settles.size() > 0) {
			for (int i = 0; i < settles.size(); i++) {
				TransCard db = settles.get(i);
				if (db.getAccountNo().equals(param.get("cleAccNo"))) {
					settle = db;
					nullFlag = true;
				}
			}
		} else {
			nullFlag = true;
		}
		if (!nullFlag) {
			responseCode = "REP99993";
			responseMsg = "结算卡信息不正确！";
			logger.error("结算卡信息不正确！");
			param = new HashMap<>();
			param.put("responseCode", responseCode);
			param.put("responseMsg", responseMsg);
			return param;
		}

		if (transCard != null) {
			if (!transCard.getCardStatus().name().equals("UNBUNDLED")) {
				responseMsg = "此卡已绑定或已被禁用！";
				responseCode = "REP99994";
				param = new HashMap<>();
				param.put("resultCode", responseCode);
				param.put("resultMsg", responseMsg);
				return param;
			}
		}*/
		// 实名认证
		param.put("cleAccName", customerSettle.getAccountName());
		JSONObject result = verified(param);
		Map<String, Object> res = checkVerified(result);
		boolean flag = false;
		if (res != null && "REP00001".equals(res.get("responseCode"))) {
			try {
				if (settle == null) {
					flag = true;
					settle = new TransCard();
				}
				settle.setAccountNo(customerSettle.getAccountNo());
				// 卡信息补充
				supplement(settle);
				settle.setAccountName(customerSettle.getAccountName());
				settle.setBankName(customerSettle.getOpenBankName());
				settle.setCardAttr(CardAttr.SETTLE_CARD);
				settle.setCardStatus(CardStatus.NORAML);
				if (flag) {
					settle.setIdNumber(param.get("idNumber").toString());
					settle.setPhone(param.get("phone").toString());
					settle.setCustomerNo(param.get("customerNo").toString());
					settle.setCode(CodeBuilder.build("TC", "yyyyMMdd"));
					settle.setTiedTime(new Date());
					transCardService.addTransCard(settle);
				} else {
					transCardService.updateTransCard(settle);
				}
			} catch (Exception e) {
				responseCode = "REP99999";
				responseMsg = "绑定结算卡信息失败！";
				logger.error("绑定结算卡信息出错：{}", e);
			}

			try {
				flag = false;
				if (transCard == null) {
					flag = true;
					transCard = new TransCard();
				}
				transCard.setAccountNo(param.get("accountNo").toString());
				// 卡信息补充
				supplement(transCard);
				transCard.setAccountName(param.get("accountName").toString());
				transCard.setBankCode(param.get("bankCode").toString());
				transCard.setBankName(param.get("bankName").toString());
				transCard.setCardAlias(param.get("cardAlias").toString());
				transCard.setCardAttr(CardAttr.valueOf(param.get("cardAttr").toString()));
				transCard.setCardType(CardType.valueOf(param.get("cardType").toString()));
				transCard.setIdNumber(param.get("idNumber").toString());
				transCard.setPhone(param.get("phone").toString());
				transCard.setCardStatus(CardStatus.NORAML);
				if (flag) {
					transCard.setSettleCode(settle.getCode());
					transCard.setCustomerNo(param.get("customerNo").toString());
					transCard.setTiedTime(new Date());
					transCard.setCode(CodeBuilder.build("TC", "yyyyMMdd"));
					transCardService.addTransCard(transCard);
				} else {
					transCardService.updateTransCard(transCard);
				}
			} catch (Exception e) {
				responseCode = "REP99998";
				responseMsg = "绑定卡交易卡失败！";
				logger.error("绑定卡交易卡出错：{}", e);
			}
		} else {
			param = new HashMap<>();
			param.put("resultCode", res.get("responseCode"));
			param.put("resultMsg", res.get("responseMsg"));
			return param;
		}
		param = new HashMap<>();
		param = transBeanMap(transCard);
		param.put("resultCode", responseCode);
		param.put("resultMsg", responseMsg);
		return param;
	}

	@Override
	public List<Map<String, Object>> findByCustNo(String custNo) {
		List<Map<String, Object>> list = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			List<TransCard> transCards = transCardService.findByCustNo(custNo);
			if (transCards != null && transCards.size() > 0) {
				for (int i = 0; i < transCards.size(); i++) {
					Map<String, Object> map = transBeanMap(transCards.get(i));
					map.put("cardAttr", transCards.get(i).getCardAttr().name());
					map.put("cardType", transCards.get(i).getCardType().name());
					map.put("cardStatus", transCards.get(i).getCardStatus().name());
					map.put("tiedTime", sdf.format(transCards.get(i).getTiedTime()));
					list.add(map);
				}
				return list;
			}
		} catch (Exception e) {
			logger.error("查询所有交易卡信息出错：{}", e);
		}
		return list;
	}

	@Override
	public Map<String, Object> unlockTansCard(String custNo, String accountNo, String cardAttr) {
		TransCard transCard = transCardService.findByCustNoAndAccNo(custNo, accountNo, CardAttr.valueOf(cardAttr));
		String responseCode = "REP00000";
		String responseMsg = "解绑成功！";
		Map<String, Object> param = new HashMap<>();
		if (transCard == null) {
			responseCode = "REP99997";
			responseMsg = "卡片不存在！";
			param.put("resultCode", responseCode);
			param.put("resultMsg", responseMsg);
			return param;
		}
		try {
			transCard.setUnlockTime(new Date());
			transCard.setCardStatus(CardStatus.UNBUNDLED);
			transCardService.updateTransCard(transCard);
		} catch (Exception e) {
			responseCode = "REP99997";
			responseMsg = "解绑失败！";
			logger.error("解绑卡信息出错：{}", e);
		}
		param = transBeanMap(transCard);
		param.put("resultCode", responseCode);
		param.put("resultMsg", responseMsg);
		return param;
	}

	@Override
	public Map<String, Object> checkTransCard(Map<String, Object> params) {
		String responseCode = "REP999994";
		String responseMsg = "此卡已绑定或已被禁用！";
		TransCard transCard = transCardService.findByCustNoAndAccNo(params.get("customerNo").toString(),
				params.get("accountNo").toString(), CardAttr.TRANS_CARD);
		if (transCard != null) {
			if (transCard.getCardStatus().name().equals("UNBUNDLED")) {
				responseCode = "REP00000";
				responseMsg = "正常！";
			}
		} else {
			responseCode = "REP00000";
			responseMsg = "正常！";
		}
		Map<String, Object> map = new HashMap<>();
		map.put("resultCode", responseCode);
		map.put("resultMsg", responseMsg);
		return map;
	}

	@Override
	public TransCardBean findTransCardByAccNo(String custNo, String accountNo,
			com.yl.boss.api.enums.CardAttr cardAttr) {
		TransCard transCard = transCardService.findByCustNoAndAccNo(custNo, accountNo,
				CardAttr.valueOf(cardAttr.name()));
		if (transCard != null) {
			return JsonUtils.toObject(JsonUtils.toJsonString(transCard), new TypeReference<TransCardBean>() {
			});
		}
		return null;
	}


	/**
	 * 联行号匹配
	 */
	public JSONArray cnaps(String bankName) {
		StringBuffer url = new StringBuffer(prop.getProperty("cachecenterCnaps") + "?word=");
		url.append(bankName);
		url.append("&fields=clearingBankCode&fields=providerCode");
		return HttpUtil.getJsonArray(url.toString());
	}
	

	/**
	 * 联行号匹配
	 */
	public JSONArray cnapsCode(String providerCode) {
		StringBuffer url = new StringBuffer(prop.getProperty("cachecenterCnaps") + "?");
		url.append("word=&fields=clearingBankCode&fields=providerCode&providerCode=");
		url.append(providerCode);
		return HttpUtil.getJsonArray(url.toString());
	}


	/**
	 * 卡识别
	 */
	public JSONObject recognition(String accountNo) {
		StringBuffer url = new StringBuffer(prop.getProperty("cachecenterIin") + "?cardNo=");
		url.append(accountNo);
		url.append(
				"&fields=code&fields=length&fields=panLength&fields=providerCode&fields=cardType&fields=cardName&fields=agencyCode&fields=agencyName");
		return HttpUtil.getJsonObject(url.toString());
	}


	/**
	 * 卡信息补充
	 */
	public TransCard supplement(TransCard transCard){
		JSONObject recognition = recognition(transCard.getAccountNo());
		if (StringUtils.isNotBlank(recognition.getString("providerCode"))) {
			JSONArray tocnaps = cnapsCode(recognition.getString("providerCode"));
			transCard.setBankCode(recognition.getString("providerCode"));
			if (recognition.containsKey("cardName")) {
				transCard.setCardAlias(recognition.getString("cardName"));
			}
			if (recognition.containsKey("cardType")) {
				transCard.setCardType(CardType.valueOf(recognition.getString("cardType")));
			}
			if (recognition.containsKey("agencyName")) {
				if (StringUtils.isBlank(transCard.getBankName())) {
					transCard.setBankName(recognition.getString("agencyName"));
				}
			}
			JSONObject tocnap = (JSONObject) tocnaps.get(0);
	        if (tocnap.containsKey("clearingBankCode")) {
	        	transCard.setClearBankCode(tocnap.getString("clearingBankCode"));
	        }
	        if (tocnap.containsKey("providerCode")) {
	        	transCard.setCnapsCode(tocnap.getString("providerCode"));
	        }
		}
		return transCard;
	}


	@Override
	public JSONObject verified(Map<String, Object> params) {
		StringBuffer url = new StringBuffer(prop.getProperty("realAuthOrder") + "?");
		SimpleDateFormat myFmt2 = new SimpleDateFormat("yyyyMMddHHmmss");
		com.yl.boss.entity.CustomerKey custKey = customerKeyService.findBy(params.get("customerNo").toString(),
				KeyType.MD5);
		String key = custKey.getKey();
		String sign = "";
		Map<String, String> map = new HashMap<>();
		map.put("apiCode", "YL-REALAUTH");
		map.put("versionCode", "1.0");
		map.put("inputCharset", "UTF-8");
		map.put("partner", params.get("customerNo").toString());
		map.put("requestCode", CodeBuilder.build("ID", "yyyyMMdd"));
		map.put("submitTime", myFmt2.format(new Date()));
		map.put("timeout", "1D");
		map.put("signType", "MD5");
		map.put("bankCardNo", params.get("accountNo").toString());
		map.put("payerName", params.get("cleAccName").toString());
		map.put("certNo", params.get("idNumber").toString());
		map.put("payerMobNo", params.get("phone").toString());
		map.put("busiType", "BINDCARD_AUTH");
		ArrayList<String> paramNames = new ArrayList<>(map.keySet());
		Collections.sort(paramNames);
		// 组织待签名信息
		StringBuilder signSource = new StringBuilder();
		Iterator<String> iterator = paramNames.iterator();
		while (iterator.hasNext()) {
			String paramName = iterator.next();
			if (!"sign".equals(paramName) && !"gatewayUrl".equals(paramName) && !"url".equals(paramName)
					&& map.get(paramName).toString() != "" && !"key".equals(paramName)) {
				signSource.append(paramName).append("=").append(map.get(paramName).toString());
				if (iterator.hasNext())
					signSource.append("&");
			}
		}
		String partnerString = signSource.toString();
		partnerString += key;
		try {
			if (StringUtils.notBlank("UTF-8")) {
				partnerString = DigestUtils.md5DigestAsHex(partnerString.getBytes("UTF-8"));
			} else {
				partnerString = DigestUtils.md5DigestAsHex(partnerString.getBytes());
			}
			sign = partnerString;
		} catch (Exception e) {
			logger.error("MD5出异常了！！", e);
		}
		url.append("apiCode=YL-REALAUTH&versionCode=1.0&inputCharset=UTF-8&");
		url.append("partner=" + map.get("partner"));
		url.append("&requestCode=" + map.get("requestCode"));
		url.append("&submitTime=" + map.get("submitTime"));
		url.append("&timeout=" + map.get("timeout"));
		url.append("&signType=MD5&bankCardNo=" + map.get("bankCardNo"));
		url.append("&payerName=" + params.get("cleAccName").toString());
		url.append("&certNo=" + map.get("certNo"));
		url.append("&payerMobNo=" + map.get("payerMobNo"));
		url.append("&busiType=BINDCARD_AUTH&sign=" + sign);
		return HttpUtil.getJsonObject(url.toString());
	}

	// 实名认证处理
	public Map<String, Object> checkVerified(JSONObject result){
		String responseCode = "";
		String responseMsg = "";
		Map<String, Object> param = null;
		if (result.containsKey("responseCode")) {
			// 认证异常
			if (result.get("responseCode").equals("S0013")) {
				responseCode = "REP99995";
				responseMsg = "实名认证异常！";
				logger.error("实名认证异常！");
				param = new HashMap<>();
				param.put("responseCode", responseCode);
				param.put("responseMsg", responseMsg);
				return param;
			}
			// 信息错误
			if (result.get("responseCode").equals("0002")) {
				responseCode = "REP99995";
				responseMsg = "实名认证信息错误！";
				logger.error("实名认证信息错误！");
				param = new HashMap<>();
				param.put("responseCode", responseCode);
				param.put("responseMsg", responseMsg);
				return param;
			}
			// 实名认证认证中
			if (result.get("responseCode").equals("0004")) {
				responseCode = "REP99995";
				responseMsg = "实名认证认证中！";
				logger.error("实名认证认证中！");
				param = new HashMap<>();
				param.put("responseCode", responseCode);
				param.put("responseMsg", responseMsg);
				return param;
			}
			// 实名认证认证失败
			if (result.get("responseCode").equals("0003")) {
				responseCode = "REP99995";
				responseMsg = "实名认证认证失败！";
				logger.error("实名认证认证失败！");
				param = new HashMap<>();
				param.put("responseCode", responseCode);
				param.put("responseMsg", responseMsg);
				return param;
			}
			// 银行卡信息有误
			if (result.get("responseCode").equals("2008")) {
				responseCode = "REP99995";
				responseMsg = "银行卡信息有误！";
				logger.error("银行卡信息有误！");
				param = new HashMap<>();
				param.put("responseCode", responseCode);
				param.put("responseMsg", responseMsg);
				return param;
			}
			// 商户尚未开通此项业务
			if (result.get("responseCode").equals("2005")) {
				responseCode = "REP99995";
				responseMsg = "商户尚未开通此项业务！";
				logger.error("商户尚未开通此项业务！");
				param = new HashMap<>();
				param.put("responseCode", responseCode);
				param.put("responseMsg", responseMsg);
				return param;
			} else if (result.get("responseCode").equals("2006")) {
				responseCode = "REP99995";
				responseMsg = "商户账户状态异常！";
				logger.error("商户账户状态异常！");
				param = new HashMap<>();
				param.put("responseCode", responseCode);
				param.put("responseMsg", responseMsg);
				return param;
			} else if (result.get("responseCode").equals("2007")) {
				responseCode = "REP99995";
				responseMsg = "可用余额资金不足，实名认证手续费扣除失败！";
				logger.error("商户账户可用余额资金不足！");
				param = new HashMap<>();
				param.put("responseCode", responseCode);
				param.put("responseMsg", responseMsg);
				return param;
			}
			// 认证成功，结果正确
			if (result.get("responseCode").equals("0001")) {
				responseCode = "REP00001";
				responseMsg = "实名认证认证成功！";
				param = new HashMap<>();
				param.put("responseCode", responseCode);
				param.put("responseMsg", responseMsg);
				return param;
			}
			responseCode = "REP99995";
			responseMsg = "实名认证接口异常！";
			logger.error("实名认证接口异常！");
			param = new HashMap<>();
			param.put("resultCode", responseCode);
			param.put("resultMsg", responseMsg);
			return param;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> transBeanMap(Object obj) {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<>();
		try {
			map = JsonUtils.toObject(JsonUtils.toJsonString(obj), Map.class);
		} catch (Exception e) {
			logger.error("卡转换失败!", e);
		}
		return map;
	}

	public UserFeedBackService getUserFeedBackService() {
		return userFeedBackService;
	}

	public void setUserFeedBackService(UserFeedBackService userFeedBackService) {
		this.userFeedBackService = userFeedBackService;
	}

	@Override
	public String findCustNoByAgentNo(String agentNo) {
		StringBuffer sb = new StringBuffer();
		List<com.yl.boss.entity.Customer> list = customerService.findByAgentNo(agentNo);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				sb.append(list.get(i).getCustomerNo() + ",");
			}
			sb.deleteCharAt(sb.length() - 1);
		} else {
			return null;
		}
		return sb.toString();
	}

	@Override
	public String getLastInfo(String customerNo) {
		return customerHistoryService.getLastInfo(customerNo);
	}

	@Override
	public String getCustomerFullName(String customerNo) {
		com.yl.boss.entity.Customer customer = customerService.findByCustNo(customerNo);
		return customer.getFullName();
	}

	@Override
	public String getDevice(String custNo) {
		List<Device> list = deviceService.findDeviceByCustNo(custNo);
		if (list != null) {
			if (list.size() > 0) {
				return deviceService.getQRcode(list.get(0).getCustomerPayNo());
			} else {
				Map<String, Object> info = new HashMap<>();
				info.put("type", 3);
				info.put("data", "false");
				return JsonUtils.toJsonString(info);
			}
		} else {
			Map<String, Object> info = new HashMap<>();
			info.put("type", 3);
			info.put("data", "false");
			return JsonUtils.toJsonString(info);
		}
	}

	/**
	 * 单个支付类型手续费查询
	 */
	@Override
	public String getCustomerDfFee(String customerNo, ProductType productType) {
		com.yl.boss.entity.CustomerFee custFee = customerFeeService.findBy(customerNo,
				com.yl.boss.enums.ProductType.valueOf(productType.toString()));
		if (custFee != null) {
			return (String) JsonUtils.toJsonString(custFee.getFee());
		}
		return null;
	}
	
	@SuppressWarnings("unused")
	private static ThreadPoolExecutor pool = new ThreadPoolExecutor(20, 50, 200L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(200));
	/**
	 * 获取商户号的产品信息
	 * @param customerNo
	 * @return
	 */
	public String customerProductInfo(String customerNo) {
		List<com.yl.boss.entity.CustomerFee> fee=customerFeeService.findByCustNo(customerNo);
		if (fee.size()>0) {
			// 获取有效路由列表
			Map<String, List<InterfacePolicyProfileBean>> interfacePolicyProfileBeans ;
			try {
				interfacePolicyProfileBeans = onlinePartnerRouterHessianService.queryPartnerRouterBy("CUSTOMER",customerNo);
				 // 路由存在
		        if (interfacePolicyProfileBeans != null) {
		        	JSONArray json=new JSONArray();
					List<InterfaceInfoBean> interfaceInfoBeanList=payInterfaceHessianService.queryInterfaceInfo();
		        	for (com.yl.boss.entity.CustomerFee customerFee : fee) {
						JSONObject temp=JSONObject.fromObject(customerFee);
						if ("REMIT".equals(customerFee.getProductType().toString())||"RECEIVE".equals(customerFee.getProductType().toString())||
								"POS".equals(customerFee.getProductType().toString())|| "MPOS".equals(customerFee.getProductType().toString())||
								"HOLIDAY_REMIT".equals(customerFee.getProductType().toString())||"BINDCARD_AUTH".equals(customerFee.getProductType().toString())) {
							temp.put("remark","");
						}else {
							String interfaceCode=getInterfaceCode(interfacePolicyProfileBeans,customerFee.getProductType().toString());//接口编号
							if (StringUtils.isBlank(interfaceCode)) {
								temp.put("remark","");
							}else{
								boolean isExist=false;
								for (InterfaceInfoBean tempInterfaceInfo:interfaceInfoBeanList) {
									if (interfaceCode.equals(tempInterfaceInfo.getCode())){
										isExist=true;
										if (tempInterfaceInfo.getDescription()!=null){
											temp.put("remark",tempInterfaceInfo.getDescription());
										}else {
											temp.put("remark","");
										}
									}
								}
								if (!isExist){
									temp.put("remark","");
								}
							}
						}
						json.add(temp);
					}
		        	return json.toString();
		        }else {//无路由
		        	JSONArray json=JSONArray.fromObject(fee);
		        	for (Object customerFee : json) {
		        		JSONObject temp=JSONObject.fromObject(customerFee);
		        		temp.put("remark","");
					}
		        	return json.toString();
				}
			} catch (Exception e) {
				logger.error("商户号:{},商户路由查询异常:{}",customerNo,e.getMessage());
				JSONArray json=JSONArray.fromObject(fee);
	        	for (Object customerFee : json) {
	        		JSONObject temp=JSONObject.fromObject(customerFee);
	        		temp.put("remark","");
				}
	        	return json.toString();
			}
		}else {
			return null;
		}
	}
	private String getInterfaceCode(Map<String, List<InterfacePolicyProfileBean>> interfacePolicyProfileBeans, String payType) {
		if(interfacePolicyProfileBeans.get(payType) == null){
			return null;
		}
		List<InterfaceInfoForRouterBean> routeBeans = interfacePolicyProfileBeans.get(payType).get(0).getInterfaceInfos();
		Collections.sort(routeBeans, new Comparator<InterfaceInfoForRouterBean>() {
			@Override
			public int compare(InterfaceInfoForRouterBean o1, InterfaceInfoForRouterBean o2) {
				return o1.getScale() - o2.getScale();
			}
		});
		return routeBeans.get(0).getInterfaceCode();
	}

	@Override
	public String checkPhone(String phone) {
		com.yl.boss.entity.Customer customer = customerService.findByPhone(phone);
		if (customer == null) {
			return "TRUE";
		}
		return "FALSE";
	}

	@Override
	public String checkFullName(String fullName) {
		com.yl.boss.entity.Customer customer = customerService.findByFullName(fullName);
		if (customer == null) {
			return "TRUE";
		}
		return "FALSE";
	}

	@Override
	public String getCustomerStatus(String customerNo) {
		com.yl.boss.entity.Customer cust = customerService.findByCustNo(customerNo);
		if (cust != null) {
			return cust.getStatus().name();
		}
		return null;
	}

	@Override
	public Customer getCustomer(String customerNo) {
		com.yl.boss.entity.Customer cust = customerService.findByCustNo(customerNo);
		if (cust != null) {
			Customer customer = (Customer) JsonUtils.toJsonToObject(cust, Customer.class);
			com.yl.boss.entity.CustomerSettle customerSettle  = customerSettleService.findByCustNo(cust.getCustomerNo());
			if (customerSettle!= null) {
				customer.setLinkMan(customerSettle.getAccountName());
			} else {
				customer.setLinkMan(cust.getPhoneNo());
			}
			return customer;
		}
		return null;
	}

	@Override
	public CustomerKey getCustomerKeyBy(String customerNo, ProductType productType) {
		com.yl.boss.entity.CustomerKey custKey = customerKeyService.findBy(customerNo,
				ProductType.valueOf(productType.toString()));
		if (custKey != null) {
			return (CustomerKey) JsonUtils.toJsonToObject(custKey, CustomerKey.class);
		}
		return null;
	}

	@Override
	public CustomerSettle getCustomerSettle(String customerNo) {
		com.yl.boss.entity.CustomerSettle custSettle = customerSettleService.findByCustNo(customerNo);
		if (custSettle != null) {
			return (CustomerSettle) JsonUtils.toJsonToObject(custSettle, CustomerSettle.class);
		}
		return null;
	}

	@Override
	public CustomerFee getCustomerFee(String customerNo, String productType) {
		com.yl.boss.entity.CustomerFee custFee = customerFeeService.findBy(customerNo,
				com.yl.boss.enums.ProductType.valueOf(productType));
		if (custFee != null) {
			return (CustomerFee) JsonUtils.toJsonToObject(custFee, CustomerFee.class);
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Page queryCustomerByAgentNo(String queryId, Map<String, Object> params) {
		ValueList vl = valueListRemoteAction.execute(queryId, params).get(queryId);
		Page page = new Page<>();
		page.setCurrentPage(vl.getValueListInfo().getPagingPage());
		page.setTotalResult(vl.getValueListInfo().getTotalNumberOfEntries());
		page.setObject(vl.getList());
		return page;
	}

	@Override
	public String checkShortName(String shortName) {
		com.yl.boss.entity.Customer customer = customerService.findByShortName(shortName);
		return customer == null ? null : customer.getShortName();
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public CustomerKeyService getCustomerKeyService() {
		return customerKeyService;
	}

	public void setCustomerKeyService(CustomerKeyService customerKeyService) {
		this.customerKeyService = customerKeyService;
	}

	public CustomerSettleService getCustomerSettleService() {
		return customerSettleService;
	}

	public void setCustomerSettleService(CustomerSettleService customerSettleService) {
		this.customerSettleService = customerSettleService;
	}

	public CustomerFeeService getCustomerFeeService() {
		return customerFeeService;
	}

	public void setCustomerFeeService(CustomerFeeService customerFeeService) {
		this.customerFeeService = customerFeeService;
	}

	public void setValueListRemoteAction(ValueListRemoteAction valueListRemoteAction) {
		this.valueListRemoteAction = valueListRemoteAction;
	}

	public void setCustomerCertService(CustomerCertService customerCertService) {
		this.customerCertService = customerCertService;
	}

	public void setAccountQueryInterface(AccountQueryInterface accountQueryInterface) {
		this.accountQueryInterface = accountQueryInterface;
	}

	public ReceiveConfigInfoBean getReceiveConfigInfoBean() {
		return receiveConfigInfoBean;
	}

	public ReceiveQueryFacade getReceiveQueryFacade() {
		return receiveQueryFacade;
	}

	public void setReceiveQueryFacade(ReceiveQueryFacade receiveQueryFacade) {
		this.receiveQueryFacade = receiveQueryFacade;
	}

	public void setReceiveConfigInfoBean(ReceiveConfigInfoBean receiveConfigInfoBean) {
		this.receiveConfigInfoBean = receiveConfigInfoBean;
	}

	@Override
	public CustomerKey getCustomerKey(String customerNo, KeyType keyType) {
		com.yl.boss.entity.CustomerKey custKey = customerKeyService.findBy(customerNo,
				KeyType.valueOf(keyType.toString()));
		if (custKey != null) {
			return (CustomerKey) JsonUtils.toJsonToObject(custKey, CustomerKey.class);
		}
		return null;
	}

	@Override
	public String getCustomerMD5Key(String customerNo) {
		com.yl.boss.entity.CustomerKey custKey = customerKeyService.findBy(customerNo, KeyType.MD5);
		return custKey.getKey();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerFee> getCustomerFeeList(String customerNo) {
		List<CustomerFee> list = JsonUtils.toJsonToObject(customerFeeService.findByCustNo(customerNo), List.class);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	@Override
	public List<CustomerKey> getCustomerKeyByCustomerNo(String customerNo) {
		List<com.yl.boss.entity.CustomerKey> list = customerKeyService.findByCustNo(customerNo);
		if (list != null && list.size() > 0) {
			List<CustomerKey> list2 = new ArrayList<CustomerKey>();
			for (com.yl.boss.entity.CustomerKey key : list) {
				CustomerKey customerKey = new CustomerKey();
				customerKey.setCustomerNo(key.getCustomerNo());
				customerKey.setKey(key.getKey());
				customerKey.setKeyType(KeyType.valueOf(key.getKeyType().name()));
				customerKey.setCreateTime(key.getCreateTime());
				list2.add(customerKey);
			}
			return list2;
		}
		return null;
	}

	@Override
	public void openCustomer(Customer customer, CustomerCertBean customerCertBean, List<CustomerFee> custFees,
			String serviceConfigBean, String receiveConfigInfoBean, CustomerSettle customerSettle, String oper,
			String SystemCode, int cycle, List<Shop> shopList) {
		com.yl.boss.entity.Customer cus = JsonUtils.toJsonToObject(customer, com.yl.boss.entity.Customer.class);
		CustomerCert customerCert = JsonUtils.toJsonToObject(customerCertBean, CustomerCert.class);
		List<com.yl.boss.entity.CustomerFee> list = JsonUtils.toObject(JsonUtils.toJsonString(custFees),
				new TypeReference<List<com.yl.boss.entity.CustomerFee>>() {
				});
		com.yl.boss.entity.CustomerSettle settle = JsonUtils.toJsonToObject(customerSettle,
				com.yl.boss.entity.CustomerSettle.class);
		ServiceConfigBean configBean = JsonUtils.toObject(serviceConfigBean, ServiceConfigBean.class);
		ReceiveConfigInfoBean receiveConfigInfo = JsonUtils.toObject(receiveConfigInfoBean,
				ReceiveConfigInfoBean.class);
		cus.setStatus(CustomerStatus.AUDIT);
		List<com.yl.boss.entity.Shop> sList = JsonUtils.toObject(JsonUtils.toJsonString(shopList),
				new TypeReference<List<com.yl.boss.entity.Shop>>() {
				});
		customerService.create(cus, customerCert, list, configBean, receiveConfigInfo, settle, oper, SystemCode, cycle,
				sList);
	}

	@Override
	public void updateCustomer(Customer customer, String serviceConfigBean, CustomerCertBean customerCert,
			CustomerSettle customerSettle, List<CustomerFee> customerFees, String oper, String receiveConfigInfoBean,
			String SystemCode, int cycle, List<Shop> shopList) {
		try {
			com.yl.boss.entity.Customer cus = JsonUtils.toJsonToObject(customer, com.yl.boss.entity.Customer.class);
			com.yl.boss.entity.CustomerCert custCert = JsonUtils.toJsonToObject(customerCert,
					com.yl.boss.entity.CustomerCert.class);
			List<com.yl.boss.entity.CustomerFee> list = JsonUtils.toObject(JsonUtils.toJsonString(customerFees),
					new TypeReference<List<com.yl.boss.entity.CustomerFee>>() {
					});
			com.yl.boss.entity.CustomerSettle settle = JsonUtils.toJsonToObject(customerSettle,
					com.yl.boss.entity.CustomerSettle.class);
			ServiceConfigBean configBean = JsonUtils.toObject(serviceConfigBean, ServiceConfigBean.class);
			ReceiveConfigInfoBean receiveConfigInfo = JsonUtils.toObject(receiveConfigInfoBean,
					ReceiveConfigInfoBean.class);
			List<com.yl.boss.entity.Shop> shopListResult = JsonUtils.toObject(JsonUtils.toJsonString(shopList),
					new TypeReference<List<com.yl.boss.entity.Shop>>() {
					});
			customerService.updateCustomerOnlyForAgentSystem(cus, configBean, custCert, settle, list, oper,
					receiveConfigInfo, SystemCode, cycle, shopListResult);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public Map<String, Object> toUpdateCustomer(Map<String, Object> params) {
		Map<String, Object> bossReturnMap = new HashMap<String, Object>();
		if (null != params && null != params.get("customerNo")) {
			com.yl.boss.entity.Customer customer = customerService.findByCustNo(params.get("customerNo").toString());
			ServiceConfigBean serviceConfigBean = customerService
					.findServiceConfigBeanByCustNo(params.get("customerNo").toString());
			CustomerCert customerCert = customerCertService.findByCustNo(params.get("customerNo").toString());
			com.yl.boss.entity.CustomerSettle customerSettle = customerSettleService
					.findByCustNo(params.get("customerNo").toString());
			List<com.yl.boss.entity.CustomerFee> customerFees = customerFeeService
					.findByCustNo(params.get("customerNo").toString());
			receiveConfigInfoBean = new ReceiveConfigInfoBean();
			receiveConfigInfoBean = receiveQueryFacade.queryReceiveConfigBy(params.get("customerNo").toString());
			AccountQuery accountQuery = new AccountQuery();
			accountQuery.setUserNo(customer.getCustomerNo());
			accountQuery.setUserRole(UserRole.CUSTOMER);
			AccountQueryResponse accountResponse = accountQueryInterface.findAccountBy(accountQuery);
			if (accountResponse != null && accountResponse.getAccountBeans() != null) {
				AccountBean accountBean = accountResponse.getAccountBeans().get(0);
				bossReturnMap.put("cycle", accountBean.getCycle());
			}
			if (null != receiveConfigInfoBean) {
				bossReturnMap.put("receiveConfigInfoBean", receiveConfigInfoBean);
			}
			if (null != customer) {
				bossReturnMap.put("customer", (com.yl.boss.api.bean.Customer) JsonUtils.toJsonToObject(customer,
						com.yl.boss.api.bean.Customer.class));
			}
			if (null != serviceConfigBean) {
				bossReturnMap.put("serviceConfigBean", (com.yl.dpay.hessian.beans.ServiceConfigBean) JsonUtils
						.toJsonToObject(serviceConfigBean, com.yl.dpay.hessian.beans.ServiceConfigBean.class));

			}
			if (null != customerCert) {
				bossReturnMap.put("customerCert", (com.yl.boss.api.bean.CustomerCertBean) JsonUtils
						.toJsonToObject(customerCert, com.yl.boss.api.bean.CustomerCertBean.class));
			}
			if (null != customerSettle) {
				bossReturnMap.put("customerSettle", (com.yl.boss.api.bean.CustomerSettle) JsonUtils
						.toJsonToObject(customerSettle, com.yl.boss.api.bean.CustomerSettle.class));
			}
			if (null != customerFees && customerFees.size() > 0) {
				bossReturnMap.put("customerFees", JsonUtils.toObject(JsonUtils.toJsonString(customerFees),
						new TypeReference<List<CustomerFee>>() {
						}));
			}
		}
		return bossReturnMap;
	}

	@Override
	public Customer getCustomerByPhone(String phone) {
		com.yl.boss.entity.Customer cust = customerService.findByPhone(phone);
		if (cust != null) {
			return (Customer) JsonUtils.toJsonToObject(cust, Customer.class);
		}
		return null;
	}

	@Override
	public String initRD(String owner, String initSumFlag) throws ParseException {
		return customerService.initRD(owner, initSumFlag);
	}

	@Override
	public String initOrder(String owner, String initSumFlag) throws ParseException {
		return customerService.initOrder(owner, initSumFlag);
	}

	@Override
	public String initDayDate(String owner, String initSumFlag) throws ParseException {
		return customerService.initDayDate(owner, initSumFlag);
	}

	@Override
	public String initDate(String owner, String initSumFlag) {
		return customerService.initDate(owner, initSumFlag);
	}

	@Override
	public String initYear(String owner) {
		return customerService.initYear(owner);
	}

	@Override
	public Map<String, String> checkCustomerName(String fullName, String shortName) {
		return customerService.checkCustomerName(fullName, shortName);
	}

	@Override
	public void auditCustomerAction(Customer customer, String serviceConfigBean, CustomerCertBean customerCert,
			CustomerSettle customerSettle, List<CustomerFee> customerFees, String oper, String receiveConfigInfoBean,
			String SystemCode, int cycle, String msg, List<Shop> shopList) {
		try {
			com.yl.boss.entity.Customer cus = JsonUtils.toJsonToObject(customer, com.yl.boss.entity.Customer.class);
			com.yl.boss.entity.CustomerCert custCert = JsonUtils.toJsonToObject(customerCert,
					com.yl.boss.entity.CustomerCert.class);
			List<com.yl.boss.entity.CustomerFee> list = JsonUtils.toObject(JsonUtils.toJsonString(customerFees),
					new TypeReference<List<com.yl.boss.entity.CustomerFee>>() {
					});
			com.yl.boss.entity.CustomerSettle settle = JsonUtils.toJsonToObject(customerSettle,
					com.yl.boss.entity.CustomerSettle.class);
			ServiceConfigBean configBean = JsonUtils.toObject(serviceConfigBean, ServiceConfigBean.class);
			ReceiveConfigInfoBean receiveConfigInfo = JsonUtils.toObject(receiveConfigInfoBean,
					ReceiveConfigInfoBean.class);
			List<com.yl.boss.entity.Shop> sList = JsonUtils.toObject(JsonUtils.toJsonString(shopList),
					new TypeReference<List<com.yl.boss.entity.Shop>>() {
					});
			customerService.auditCustomerAction(cus, configBean, custCert, settle, list, oper, receiveConfigInfo,
					SystemCode, cycle, msg, sList);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public DeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	public CustomerHistoryService getCustomerHistoryService() {
		return customerHistoryService;
	}

	public void setCustomerHistoryService(CustomerHistoryService customerHistoryService) {
		this.customerHistoryService = customerHistoryService;
	}

	@Override
	public String findByKey(String key) {
		return customerKeyService.findByKey(key);
	}

	@Override
	public String AddUserFeedBack(Map<String, Object> params) {
		try {
			if (params.get("customerNo").toString() != null
					&& customerCertService.findByCustNo(params.get("customerNo").toString()) != null) {
				UserFeedback feedBack = new UserFeedback();
				feedBack.setCustomerNo(params.get("customerNo").toString());
				feedBack.setContent(params.get("content").toString());
				feedBack.setCreateTime(new Timestamp((new Date()).getTime()));
				feedBack.setStatus("TRUE");
				feedBack.setOem(params.get("oem").toString());
				userFeedBackService.save(feedBack);
				return "SUCCESS";
			} else {
				return "customerNo Not Found";
			}
		} catch (Exception e) {
			return "fail";
		}
	}

	@Override
	public Map<String, Object> goDrawCash(Map<String, Object> param) {
		com.yl.boss.entity.Customer customer = customerService.findByCustNo(param.get("customerNo").toString());
		com.yl.boss.entity.CustomerSettle custSettle = customerSettleService
				.findByCustNo(param.get("customerNo").toString());
		com.yl.boss.entity.CustomerFee custFee = customerFeeService.findBy(param.get("customerNo").toString(),
				com.yl.boss.enums.ProductType.REMIT);
		if (custSettle != null && custFee != null && customer != null) {
			Map<String, Object> result = new HashMap<>();
			result.put("fullName", customer.getFullName());
			result.put("customerNo", customer.getCustomerNo());
			result.put("openBankName", custSettle.getOpenBankName());
			result.put("accName", custSettle.getAccountName());
			result.put("accNo", custSettle.getAccountNo());
			result.put("payType", "提现");
			result.put("amount", param.get("amount"));
			if (custFee.getFeeType().name().equals("FIXED")) {
				result.put("fee", custFee.getFee());
				result.put("actual", Double.parseDouble(param.get("amount").toString()));
			} else {
				result.put("fee", Double.parseDouble(param.get("amount").toString()) * custFee.getFee());
				result.put("actual", Double.parseDouble(param.get("amount").toString()));
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			result.put("createTime", sdf.format(new Date()));
			return result;
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Page queryProtocolManagements(Map<String, Object> params) {
		ValueList vl = valueListRemoteAction.execute("protocolManagements", params).get("protocolManagements");
		Page page = new Page<>();
		page.setCurrentPage(vl.getValueListInfo().getPagingPage());
		page.setTotalResult(vl.getValueListInfo().getTotalNumberOfEntries());
		page.setObject(vl.getList());
		return page;
	}

	@Override
	public String findShortNameByCustomerNo(String customerNo) {
		return customerService.findShortNameByCustomerNo(customerNo);
	}

	@Override
	public String findCustomerCardByNo(String customerNo) {
		return customerService.findCustomerCardByNo(customerNo);
	}

	@Override
	public void addTransCardHistory(TransCardHistoryBean transCardHistoryBean) {
		transCardHistoryService.addTransCardHistory(
				JsonUtils.toObject(JsonUtils.toJsonString(transCardHistoryBean), new TypeReference<TransCardHistory>() {
				}));
	}

	@Override
	public void updateTransCardHistory(TransCardHistoryBean transCardHistoryBean) {
		transCardHistoryService.updateTransCardHistory(
				JsonUtils.toObject(JsonUtils.toJsonString(transCardHistoryBean), new TypeReference<TransCardHistory>() {
				}));
	}

	@Override
	public List<TransCardHistoryBean> findTransCardHistoryByCustAndAcc(String customerNo, String accountNo) {
		List<TransCardHistoryBean> list = new ArrayList<>();
		List<TransCardHistory> result = transCardHistoryService.findTransCardHistoryByCustAndAcc(customerNo, accountNo);
		if (result != null && result.size() > 0) {
			for (int j = 0; j < result.size(); j++) {
				list.add(JsonUtils.toObject(JsonUtils.toJsonString(result.get(j)),
						new TypeReference<TransCardHistoryBean>() {
						}));
			}
			return list;
		}
		return null;
	}

	public TransCardService getTransCardService() {
		return transCardService;
	}

	public void setTransCardService(TransCardService transCardService) {
		this.transCardService = transCardService;
	}

	public TransCardHistoryService getTransCardHistoryService() {
		return transCardHistoryService;
	}

	public void setTransCardHistoryService(TransCardHistoryService transCardHistoryService) {
		this.transCardHistoryService = transCardHistoryService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getDevices(String custNo) {
		Map<String, Object> devices = new HashMap<>();
		List<Device> qr = deviceService.findDeviceByCustNo(custNo);
		if (qr != null && qr.size() > 0) {
			List<Map<String, Object>> qrList = new ArrayList<>();
			for (Device device : qr) {
				qrList.add(JsonUtils.toJsonToObject(device, Map.class));
			}
			devices.put("qr", qrList);
		} else {
			devices.put("qr", null);
		}
		List<Pos> pos = posService.findPos(custNo);
		if (pos != null && pos.size() > 0) {
			List<JSONObject> posBrand = Constant.DICTS.get("POS_BRAND").getJSONArray("dictionaries");
			List<Map<String, Object>> posList = new ArrayList<>();
			for (Pos pos2 : pos) {
				pos2.setCustomer(null);
				pos2.setShop(null);
				posList.add(JsonUtils.toJsonToObject(pos2, Map.class));
			}
			for (Map<String, Object> map : posList) {
				try {
					for (int i = 0; i < posBrand.size(); i++) {
						if (map.get("posBrand")
								.equals(posBrand.get(i).getString("key").replace(Constant.DICTIONARY, ""))) {
							map.put("posBrand_CN", posBrand.get(i).getString("value"));
						}
					}
				} catch (Exception e) {

				}
			}
			devices.put("pos", posList);
		} else {
			devices.put("pos", null);
		}
		return devices;
	}

	public PosService getPosService() {
		return posService;
	}

	public void setPosService(PosService posService) {
		this.posService = posService;
	}

	@Override
	public boolean resetPwd(String custNo, String phone) {
		return customerService.resetPassWordByPhone(custNo, phone);
	}

	public OnlinePartnerRouterHessianService getOnlinePartnerRouterHessianService() {
		return onlinePartnerRouterHessianService;
	}

	public void setOnlinePartnerRouterHessianService(
			OnlinePartnerRouterHessianService onlinePartnerRouterHessianService) {
		this.onlinePartnerRouterHessianService = onlinePartnerRouterHessianService;
	}

	public static Properties getProp() {
		return prop;
	}

	public static void setProp(Properties prop) {
		CustomerInterfaceImpl.prop = prop;
	}

	public DictionaryType getDictionaryTypeRanged() {
		return dictionaryTypeRanged;
	}

	public void setDictionaryTypeRanged(DictionaryType dictionaryTypeRanged) {
		this.dictionaryTypeRanged = dictionaryTypeRanged;
	}

	public List<Dictionary> getDictionaryRangedList() {
		return dictionaryRangedList;
	}

	public void setDictionaryRangedList(List<Dictionary> dictionaryRangedList) {
		this.dictionaryRangedList = dictionaryRangedList;
	}

	@Override
	public String ajaxQueryDictionaryByTypeCode(String code) {
		String msg = "";
		try {
			dictionaryTypeRanged = DictionaryUtils.getDictionaryTypeBy(Constant.DICTIONARY_TYPE + code);
			dictionaryRangedList = dictionaryTypeRanged.getDictionaries();
			msg = JsonUtils.toJsonString(dictionaryRangedList);
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return msg;
	}

	@Override
	public void addTransCardHisForTrade(Map<String, Object> params) {
		transCardHistoryService.addTransCardHisForTrade(params);
	}

	@Override
	public TransCardBean findByInterfaceRequestId(String interfaceRequestId) {
		List<TransCardHistory> transCardHistories = transCardHistoryService
				.findByInterfaceRequestId(interfaceRequestId);
		if (transCardHistories != null && transCardHistories.size() > 0) {
			String settleCode = transCardHistories.get(0).getSettleCode();
			TransCard transCard = transCardService.findByCode(settleCode);
			if (transCard != null) {
				return JsonUtils.toObject(JsonUtils.toJsonString(transCard), new TypeReference<TransCardBean>() {
				});
			}
		}
		return null;
	}
	
	@Override
	public TransCardBean findTransCardByInterfaceRequestId(String interfaceRequestId) {
		List<TransCardHistory> transCardHistories = transCardHistoryService
				.findByInterfaceRequestId(interfaceRequestId);
		if (transCardHistories != null && transCardHistories.size() > 0) {
			String transCardCode = transCardHistories.get(0).getCode();
			TransCard transCard = transCardService.findByCode(transCardCode);
			if (transCard != null) {
				return JsonUtils.toObject(JsonUtils.toJsonString(transCard), new TypeReference<TransCardBean>() {
				});
			}
		}
		return null;
	}

	@Override
	public TransCardBean findByCustAndAccNo(String custNo, String accNo) {
		TransCard transCard = transCardService.findByCustNoAndAccNo(custNo, accNo, CardAttr.TRANS_CARD);
		if (transCard != null) {
			String settleCode = transCard.getSettleCode();
			TransCard settle = transCardService.findByCode(settleCode);
			if (settle != null) {
				return JsonUtils.toObject(JsonUtils.toJsonString(settle), new TypeReference<TransCardBean>() {
				});
			}
		}
		return null;
	}
	
	@Override
	public String updateCustomerBaseInfo(Map<String, Object> params) {
		try {
			Map<String, String> map = customerService.checkCustomerName(params.get("fullName").toString(), params.get("shortName").toString());
			logger.info("校验信息{}", JsonUtils.toJsonString(map));
			if (map != null) {
				String fullName = map.get("fullName");
				String shortName = map.get("shortName");
				if ("TRUE".equals(fullName)) {
					return "FULLNAME_ERROR";
				}
				if ("TRUE".equals(shortName)) {
					return "SHORTNAME_ERROR";
				}
			}
			
			com.yl.boss.entity.Customer customer = new com.yl.boss.entity.Customer();
			customer.setAddr(params.get("addr").toString());
			customer.setFullName(params.get("fullName").toString());
			customer.setShortName(params.get("shortName").toString());
			customer.setProvince(params.get("province").toString());
			customer.setCity(params.get("city").toString());
			customer.setCustomerNo(params.get("customerNo").toString());
			if (params.get("eMail") != null) {
				customer.setEMail(params.get("eMail").toString());
			}
			return customerService.updateCustomerBaseInfo(customer);
		} catch (Exception e) {
			logger.info("更新商户基本信息报错!", e);
			return "ERROR";
		}
	}
	
	@Override
	public void createQuick(Customer customer, String password) {
		com.yl.boss.entity.Customer cus = JsonUtils.toJsonToObject(customer, com.yl.boss.entity.Customer.class);
		customerService.createQuick(cus, password);
	}
	
	@Override
	public Boolean findUpdateBaseInfoByCustNo(String customerNo) {
		Boolean flag = false;
		List<CustomerHistory> list = customerService.findUpdateBaseInfoByCustNo(customerNo);
		if (list != null && list.size() != 0) {
			flag = true;
		}
		return flag;
		
	}
	
	public CustomerCertBean findCustomerCertByCustNo(String customerNo) {
		CustomerCert customerCert = customerCertService.findByCustNo(customerNo);
		return JsonUtils.toObject(JsonUtils.toJsonString(customerCert), new TypeReference<CustomerCertBean>() {});
	}

	public PayInterfaceHessianService getPayInterfaceHessianService() {
		return payInterfaceHessianService;
	}

	public void setPayInterfaceHessianService(PayInterfaceHessianService payInterfaceHessianService) {
		this.payInterfaceHessianService = payInterfaceHessianService;
	}
}
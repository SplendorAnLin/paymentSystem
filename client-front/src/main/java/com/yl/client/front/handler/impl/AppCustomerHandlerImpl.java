package com.yl.client.front.handler.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.security.DigestUtils;
import com.pay.common.util.DateUtil;
import com.pay.common.util.DigestUtil;
import com.yl.boss.api.bean.Customer;
import com.yl.boss.api.interfaces.AdInterface;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.boss.api.interfaces.ShareProfitInterface;
import com.yl.client.front.Constant;
import com.yl.client.front.common.AppRuntimeException;
import com.yl.client.front.common.Base64Utils;
import com.yl.client.front.common.CodeBuilder;
import com.yl.client.front.common.DictUtils;
import com.yl.client.front.enums.AppExceptionEnum;
import com.yl.client.front.handler.AppCustomerHandler;
import com.yl.client.front.model.User;
import com.yl.client.front.service.FeeInfoService;
import com.yl.client.front.service.TradeServivce;
import com.yl.client.front.service.UserService;
import com.yl.customer.api.bean.CustOperator;
import com.yl.customer.api.interfaces.CustOperInterface;
import com.yl.sms.SmsUtils;


/**
 * App商户处理实现
 *
 * @author 聚合支付有限公司
 * @since 2017年4月28日
 * @version V1.0.0
 */
@Service("customerHandLer")
@PropertySource("classpath:/system.properties")
public class AppCustomerHandlerImpl implements AppCustomerHandler {
    private static Logger log = LoggerFactory.getLogger(AppCustomerHandler.class);

    @Resource
    CustOperInterface custOperInterface;
    @Resource
    CustomerInterface customerInterface;
    @Resource
    TradeServivce tradeServivce;
    @Resource
    AdInterface adInterface;
    @Resource
    FeeInfoService feeInfoService;
    @Resource
    UserService userService;
    @Resource
    ShareProfitInterface shareProfitInterface;

    @Value("${appHelp}")
    private String appHelp;

    private static Properties prop = new Properties();
    static {
        try {
            prop.load(new InputStreamReader(AppCustomerHandlerImpl.class.getClassLoader().getResourceAsStream("serverHost.properties")));
        } catch (IOException e) {
            log.error("AppCustomerHandlerImpl load Properties error:", e);
        }
    }

    public Map<String,Object> login(Map<String,Object> param) throws AppRuntimeException{
        if (param!=null) {
            CustOperator oper=custOperInterface.findByUserName(param.get("userName").toString());
            if (oper!=null&&oper.getPassword().equalsIgnoreCase(DigestUtil.md5(param.get("password").toString()))) {
                try {
                    Map<String,Object> resMap=getCustInfo(oper.getCustomerNo(),param.get("oem").toString());
                    String sign=CodeBuilder.build("sign", "yyMMdd");
                    resMap.put("jiguangId", "G_"+oper.getCustomerNo());
                    resMap.put("appHelp", appHelp);
                    resMap.put("sign", sign);
                    //系统版本号
                    Map<String,Object> signInfo=new HashMap<>();//登录身份信息记录
                    signInfo.put("name", oper.getUsername());
                    signInfo.put("customerNo", oper.getCustomerNo());
                    signInfo.put("sign", sign);
                    signInfo.put("jiguangId", "G_"+oper.getCustomerNo());
                    if (!userService.findUserStauts("G_"+oper.getCustomerNo())) {
                        User user=new User();
                        user.setCustomerNo(oper.getCustomerNo());
                        user.setJgId("G_"+oper.getCustomerNo());
                        userService.createUser(user);
                    }
                    log.info("用户{}登录",oper.getUsername());
                    CacheUtils.setEx(Constant.OPERATOR_RESOURCE_CACHE_DB,Constant.OPERATOR_RESOUSE + "." + oper.getUsername(),signInfo,172800,true);
                    return resMap;
                } catch (Exception e) {
                    log.info("登录数据获取异常:{}",e);
                    throw new AppRuntimeException(AppExceptionEnum.SYSERR.getCode(),AppExceptionEnum.SYSERR.getMessage());
                }
            }else {
                throw new AppRuntimeException(AppExceptionEnum.LONGINERR.getCode(),AppExceptionEnum.LONGINERR.getMessage());
            }
        }else {
            throw new AppRuntimeException(AppExceptionEnum.PARAMSERR.getCode(),AppExceptionEnum.PARAMSERR.getMessage());
        }
    }

    public void outLogin(Map<String,Object> param){
        CustOperator oper=custOperInterface.findByUserName(param.get("userName").toString());
        String sign=CodeBuilder.build("sign", "yyMMdd");
        Map<String,Object> signInfo=new HashMap<>();//登录身份信息记录
        signInfo.put("name", oper.getUsername());
        signInfo.put("customerNo", oper.getCustomerNo());
        signInfo.put("sign", sign);
        log.info("用户{}退出",oper.getUsername());
        CacheUtils.set(Constant.OPERATOR_RESOURCE_CACHE_DB,Constant.OPERATOR_RESOUSE + "." + oper.getUsername(),signInfo ,true);
    }

    public Map<String,Object> getLoginInfo(Map<String,Object> param) throws AppRuntimeException{
        if (param!=null) {
            CustOperator oper=custOperInterface.findByUserName(param.get("userName").toString());
            return getCustInfo(oper.getCustomerNo(),param.get("oem").toString());
        }else {
            throw new AppRuntimeException(AppExceptionEnum.PARAMSERR.getCode(),AppExceptionEnum.PARAMSERR.getMessage());
        }
    }

    public Map<String,Object> resetPwd(Map<String,Object> param) throws AppRuntimeException{
        String userName,verifyCode;
        if (param!=null&&(userName=param.get("userName").toString())!=null&&(verifyCode=param.get("verifyCode").toString())!=null) {
            CustOperator oper=custOperInterface.findByUserName(userName);
            if (oper!=null) {
                if (verifyCode.equals(CacheUtils.get( "OPER-VERIFY-"+userName,String.class, true))) {
                    if (customerInterface.resetPwd(oper.getCustomerNo(), userName)) {
                        CacheUtils.del("OPER-VERIFY-"+userName);
                        Map<String,Object> info=new HashMap<>();
                        info.put("msg", AppExceptionEnum.RESETSUCCESS.getMessage());
                        return info;
                    }else {
                        throw new AppRuntimeException(AppExceptionEnum.SYSERR.getCode(),AppExceptionEnum.SYSERR.getMessage());
                    }
                }else {
                    throw new AppRuntimeException(AppExceptionEnum.VERIFYERR.getCode(),AppExceptionEnum.VERIFYERR.getMessage());
                }
            }else {
                throw new AppRuntimeException(AppExceptionEnum.AUTHEMPTY.getCode(),AppExceptionEnum.AUTHEMPTY.getMessage());
            }
        }else {
            throw new AppRuntimeException(AppExceptionEnum.PARAMSERR.getCode(),AppExceptionEnum.PARAMSERR.getMessage());
        }
    }

    public Map<String,Object> getVerifyCode(Map<String,Object> param) throws AppRuntimeException {
        String userName;
        if (param!=null&&(userName=param.get("userName").toString())!=null) {
            CustOperator oper=custOperInterface.findByUserName(userName);
            if (oper!=null) {
                Map<String,Object> resMap=new HashMap<>();
                try {
                    String verifyCode=CacheUtils.get( "OPER-VERIFY-"+userName,String.class, true);
                    if (verifyCode!=null&&StringUtils.notEmpty(verifyCode)) {
                        throw new AppRuntimeException(AppExceptionEnum.VERIFY.getCode(),AppExceptionEnum.VERIFY.getMessage());
                    }else {
                        resMap.put("verifyCode", sendVerify(userName));
                        return resMap;
                    }
                } catch (Exception e) {
                    resMap.put("verifyCode", sendVerify(userName));
                    return resMap;
                }
            }else {
                throw new AppRuntimeException(AppExceptionEnum.AUTHEMPTY.getCode(),AppExceptionEnum.AUTHEMPTY.getMessage());
            }
        }else {
            throw new AppRuntimeException(AppExceptionEnum.PARAMSERR.getCode(),AppExceptionEnum.PARAMSERR.getMessage());
        }
    }

    String sendVerify(String userName) throws AppRuntimeException{
        Random random =new Random();
        String verifyCode=""+random.nextInt(10)+random.nextInt(10)+random.nextInt(10)+random.nextInt(10)+random.nextInt(10)+random.nextInt(10);
        try {
            SmsUtils.sendCustom(String.format(Constant.SMS_VERIFY,verifyCode), userName);
            CacheUtils.setEx("OPER-VERIFY-"+userName,verifyCode,300, true);
            return verifyCode;
        } catch (IOException e1) {
            log.error("resetCustomerPassWord SMS:[" + userName + "] is failed! exception:{}",e1);
            throw new AppRuntimeException(AppExceptionEnum.SYSERR.getCode(),AppExceptionEnum.SYSERR.getMessage());
        }
    }

    private Map<String,Object> getCustInfo(String customerNo,String oem){
        long time=System.currentTimeMillis();
        Customer cust=customerInterface.getCustomer(customerNo);
        Map<String,Object> resMap=new HashMap<>();
        resMap.put("isPass", 1);
        resMap.put("cust", cust);
        resMap.put("customerImg",ImgUrl(userService.findUserImg(customerNo)));
        System.out.println("基本时间"+(System.currentTimeMillis()-time));
        time=System.currentTimeMillis();
        resMap.put("Ad", adInterface.query(oem));
        resMap.put("url", "https://www.bank-pay.com/");
//        resMap.put("phone", "400-860-7199");
        log.info("广告时间"+(System.currentTimeMillis()-time));
        time=System.currentTimeMillis();
        // 查询付款和交易费率
        List<Map<String, Object>> payFee = new ArrayList<>();
        List<Map<String, Object>> dpayFee = feeInfoService.getCustomerFee(customerNo, payFee);
        resMap.put("payFee", payFee);
        resMap.put("dpayFee", dpayFee);
        log.info("费率时间"+(System.currentTimeMillis()-time));
        time=System.currentTimeMillis();
        try {
        	Map<String, Object> map = shareProfitInterface.weeklySales(customerNo);
        	if (map != null ) {
        		resMap.put("tradeList",map.get("summaryGraph"));
        	}
        } catch (ParseException e) {
            log.error("{}",e);
        }
        log.info("周数据时间"+(System.currentTimeMillis()-time));
        time=System.currentTimeMillis();
        String onlineSales=shareProfitInterface.findOnlineSalesBy(customerNo, 1, 4, null, null, null);
        if (onlineSales!=null){
            List<Map<String,Object>> onlineSalesList=JsonUtils.toObject(onlineSales, new TypeReference<List<Map<String,Object>>>() {});
            resMap.put("order", DictUtils.listOfDict(onlineSalesList, "ONLINE_PAYTYPE", "PayType"));
        }else {
            resMap.put("order", new ArrayList<>());
        }
        log.info("订单率时间"+(System.currentTimeMillis()-time));
        return resMap;
    }

    public Map<String,Object> saveCustImg(Map<String, Object> param) throws AppRuntimeException {
        Map<String,Object> resMap=new HashMap<>();
        String img=param.get("customerImg").toString();
        String custNo=param.get("customerNo").toString();
        if (img!=null&&custNo!=null) {
            String imgName=custNo+"-" + Long.toHexString(System.nanoTime()) + ".jpg";
            String imgPath=prop.getProperty("cust.img")+DateUtil.formatDate(new Date(), "yyyyMMddHHMMss")+ "/";
            if (Base64Utils.base64ToFile(img, imgPath+imgName)) {
                userService.updateImg(imgPath+imgName, custNo);
                resMap.put("result", true);
                resMap.put("customerImg", ImgUrl(imgPath+imgName));
            }else {
                resMap.put("result", false);
                resMap.put("customerImg",ImgUrl(userService.findUserImg(custNo)));
            }
            resMap.put("customerNo", custNo);
            return resMap;
        }else {
            throw new AppRuntimeException(AppExceptionEnum.PARAMSERR.getCode(),AppExceptionEnum.PARAMSERR.getMessage());
        }
    }

    public String ImgUrl(String url){
        if (url!=null) {
            if (url.indexOf("http://")==-1) {
                url=prop.getProperty("imgOpen.path")+url;
            }
            return url;
        }else {
            return null;
        }
    }

    @Override
    public Map<String, Object> appUpdatePassword(Map<String, Object> param) throws AppRuntimeException {
        try {
            if(!param.get("userName").toString().equals("") && !param.get("passWord").toString().equals("") && !param.get("oldPassWord").toString().equals("")){
                String msg = custOperInterface.appUpdatePassword(param);
                Map<String,Object> resMap = new HashMap<>();
                resMap.put("msg", msg);
                outLogin(param);
                return resMap;
            } else {
                throw new AppRuntimeException(AppExceptionEnum.PARAMSERR.getCode(),AppExceptionEnum.PARAMSERR.getMessage());
            }
        } catch (Exception e) {
            log.error(e.toString());
            throw new AppRuntimeException(AppExceptionEnum.UPDATEPASSWORD.getCode(),AppExceptionEnum.UPDATEPASSWORD.getMessage());
        }
    }

    @Override
    public Map<String,Object> queryProtocolManagements(Map<String, Object> params) throws AppRuntimeException {
        params.put("sort", "APP_HELP");
        Page page = customerInterface.queryProtocolManagements(params);
        if(page != null){
            Map<String,Object> resMap = new HashMap<>();
            resMap.put("protocolManagements", page);
            return resMap;
        }else {
            throw new AppRuntimeException(AppExceptionEnum.RETURNEMPTY.getCode(),AppExceptionEnum.RETURNEMPTY.getMessage());
        }
    }

    @Override
    public Map<String, Object> authpay(Map<String, Object> params) throws AppRuntimeException {
        String payType=params.get("payType").toString();
        if ("AUTHPAY".equals(payType)) {
            return goAuthpay(params);
        }else if ("QUICKPAY".equals(payType)) {
            return quick(params);
        }
        throw new AppRuntimeException(AppExceptionEnum.PARAMSERR.getCode(),AppExceptionEnum.PARAMSERR.getMessage());
    }

    @Override
    public Map<String, Object> getDevices(Map<String, Object> params) throws AppRuntimeException {
        String custNo=params.get("customerNo").toString();
        if (custNo!=null) {
            return customerInterface.getDevices(custNo);
        }
        return null;
    }

    public Map<String, Object> goAuthpay(Map<String, Object> params) throws AppRuntimeException{
        StringBuffer url = new StringBuffer(prop.getProperty("gatewayPay") + "?");
        if (params.get("customerNo") != null && params.get("accountNo") != null && params.get("amount") != null && params.get("payType") != null) {
            String key = customerInterface.getCustomerMD5Key(params.get("customerNo").toString());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String sign = "";
            Map<String, Object> authpay = new HashMap<>();
            authpay.put("apiCode", "YL-PAY");
            authpay.put("inputCharset", "UTF-8");
            authpay.put("partner", params.get("customerNo"));
            authpay.put("outOrderId", CodeBuilder.build("yyMMdd",1));
            authpay.put("amount", params.get("amount"));
            authpay.put("currency", "CNY");
            authpay.put("payType", params.get("payType"));
            if (params.get("payType").equals("B2C")) {
                authpay.put("interfaceCode", "B2C_BOS");
            }
            authpay.put("retryFalg", "TRUE");
            authpay.put("submitTime", sdf.format(new Date()));
            authpay.put("timeout", "1D");
            authpay.put("productName", "APPAUTHPAY");
            authpay.put("notifyUrl", prop.getProperty("notifyUrl"));
            authpay.put("bankCardNo", params.get("accountNo"));
            authpay.put("accMode", "INTERFACE");
            authpay.put("wxNativeType", "PAGE");
            authpay.put("signType", "MD5");
            ArrayList<String> paramNames = new ArrayList<>(authpay.keySet());
            Collections.sort(paramNames);
            // 组织待签名信息
            StringBuilder signSource = new StringBuilder();
            Iterator<String> iterator = paramNames.iterator();
            while (iterator.hasNext()) {
                String paramName = iterator.next();
                if (!"sign".equals(paramName) && !"gatewayUrl".equals(paramName) && !"url".equals(paramName)
                        && authpay.get(paramName).toString() != "" && !"key".equals(paramName)) {
                    signSource.append(paramName).append("=").append(authpay.get(paramName).toString());
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

            }
            url.append("apiCode=YL-PAY&inputCharset=UTF-8&partner=");
            url.append(authpay.get("partner"));
            url.append("&outOrderId=");
            url.append(authpay.get("outOrderId"));
            url.append("&amount=" + authpay.get("amount"));
            url.append("&currency=CNY&payType=");
            url.append(authpay.get("payType"));
            if (authpay.get("payType").equals("B2C")) {
                url.append("&interfaceCode=B2C_BOS");
            }
            url.append("&retryFalg=TRUE&submitTime=");
            url.append(authpay.get("submitTime"));
            url.append("&timeout=1D&productName=APPAUTHPAY&notifyUrl=" + prop.getProperty("notifyUrl") + "&bankCardNo=");
            url.append(authpay.get("bankCardNo"));
            url.append("&accMode=INTERFACE&wxNativeType=PAGE&signType=MD5&sign=");
            url.append(sign);
            params = new HashMap<>();
            params.put("url", url.toString());
            return params;
        }
        return null;
    }

    public Map<String, Object> quick(Map<String, Object> params) throws AppRuntimeException {
        StringBuffer url = new StringBuffer(prop.getProperty("quickPay") + "?");
        if (params.get("customerNo") != null && params.get("accountNo") != null && params.get("amount") != null) {
            String key = customerInterface.getCustomerMD5Key(params.get("customerNo").toString());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String sign = "";
            Map<String, Object> authpay = new HashMap<>();
            authpay.put("apiCode", "YL-PAY");
            authpay.put("inputCharset", "UTF-8");
            authpay.put("partner", params.get("customerNo"));
            authpay.put("outOrderId", CodeBuilder.build("yyMMdd",1));
            authpay.put("amount", params.get("amount"));
            authpay.put("currency", "CNY");
            authpay.put("payType", params.get("payType"));
            authpay.put("retryFalg", "TRUE");
            authpay.put("payType", params.get("payType"));
            authpay.put("submitTime", sdf.format(new Date()));
            authpay.put("timeout", "1D");
            authpay.put("productName", "APPAUTHPAY");
            authpay.put("notifyUrl", prop.getProperty("notifyUrl"));
            authpay.put("bankCardNo", params.get("accountNo"));
            authpay.put("accMode", "GATEWAY");
            authpay.put("wxNativeType", "PAGE");
            authpay.put("signType", "MD5");
            ArrayList<String> paramNames = new ArrayList<>(authpay.keySet());
            Collections.sort(paramNames);
            // 组织待签名信息
            StringBuilder signSource = new StringBuilder();
            Iterator<String> iterator = paramNames.iterator();
            while (iterator.hasNext()) {
                String paramName = iterator.next();
                if (!"sign".equals(paramName) && !"gatewayUrl".equals(paramName) && !"url".equals(paramName)
                        && authpay.get(paramName).toString() != "" && !"key".equals(paramName)) {
                    signSource.append(paramName).append("=").append(authpay.get(paramName).toString());
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

            }
            url.append("apiCode=YL-PAY&inputCharset=UTF-8&partner=");
            url.append(authpay.get("partner"));
            url.append("&outOrderId=");
            url.append(authpay.get("outOrderId"));
            url.append("&amount=" + authpay.get("amount"));
            url.append("&currency=CNY");
            url.append("&payType=" + authpay.get("payType"));
            url.append("&retryFalg=TRUE&submitTime=");
            url.append(authpay.get("submitTime"));
            url.append("&timeout=1D&productName=APPAUTHPAY&notifyUrl=" + prop.getProperty("notifyUrl") + "&bankCardNo=");
            url.append(authpay.get("bankCardNo"));
            url.append("&accMode=GATEWAY&wxNativeType=PAGE&signType=MD5&sign=");
            url.append(sign);
            params = new HashMap<>();
            params.put("url", url.toString());
            return params;
        }
        return null;
    }

    @Override
    public Map<String, Object> appApplication(Map<String, Object> params) throws AppRuntimeException {
        if (params == null) {
            throw new AppRuntimeException(AppExceptionEnum.PARAMSERR.getCode(),AppExceptionEnum.PARAMSERR.getMessage());
        }
        return customerInterface.getPosApplicationInfo(params);
    }

    @Override
    public Map<String, Object> appDevice(Map<String, Object> params) throws AppRuntimeException {
        if (params == null) {
            throw new AppRuntimeException(AppExceptionEnum.PARAMSERR.getCode(),AppExceptionEnum.PARAMSERR.getMessage());
        }
        return customerInterface.appDevice(params);
    }

    @Override
    public Map<String, Object> getDevicesInfo(Map<String, Object> params) throws AppRuntimeException {
        List<Map<String, Object>> result = customerInterface.getDevicesInfo(params);
        Map<String,Object> map = new HashMap<>();
        if (result != null) {
            map.put("cardTypeList", result);
            return map;
        }
        return null;
    }

	@Override
	public Map<String, Object> queryCustomerStatus(Map<String, Object> params) throws AppRuntimeException {
		if (params == null || null == params.get("customerNo")) {
			throw new AppRuntimeException(AppExceptionEnum.PARAMSERR.getCode(),AppExceptionEnum.PARAMSERR.getMessage());
		}
		Map<String, Object> map = new HashMap<>();
		String suatus = customerInterface.getCustomerStatus(params.get("customerNo").toString());
		if (suatus != null) {
			map.put("customerStatus", suatus);
			map.put("resCode", "SUCCESS");
			return map;
		} else {
			map.put("customerStatus", "");
			map.put("resCode", "ERROR");
			return map;
		}
	}

	@Override
	public Map<String, Object> custSettleCard(Map<String, Object> params) throws AppRuntimeException {
		if (params == null) {
			throw new AppRuntimeException(AppExceptionEnum.PARAMSERR.getCode(),AppExceptionEnum.PARAMSERR.getMessage());
		}
		String resCode = (String) customerInterface.querySettle(params.get("customerNo").toString()).get("resCode");
		if ("SUCCESS".equals(resCode)) {
			return updateCreateSettle(params);
		} else {
			Map<String, Object> map = new HashMap<>();
			if (customerInterface.customerSettle(params).equals("SUCCESS")) {
				map.put("resCode", "SUCCESS");
				map.put("resStatus", "AUDIT");
			} else {
				map.put("resCode", "ERROR");
				map.put("resStatus", "PRE_TRUE");
			}
			log.info("提交结算卡信息返回APP信息：{}", JsonUtils.toJsonString(map));
			return map;
		}
	}

	@Override
	public Map<String, Object> updateCreateSettle(Map<String, Object> params) throws AppRuntimeException {
		if (params == null) {
			throw new AppRuntimeException(AppExceptionEnum.PARAMSERR.getCode(),AppExceptionEnum.PARAMSERR.getMessage());
		}
		Map<String, Object> map = new HashMap<>();
		if (customerInterface.updateCreateSettle(params).equals("SUCCESS")) {
			map.put("resCode", "SUCCESS");
			map.put("resStatus", "AUDIT");
		} else {
			map.put("resCode", "ERROR");
			map.put("resStatus", "PRE_TRUE");
		}
		log.info("提交结算卡信息返回APP信息：{}", JsonUtils.toJsonString(map));
		return map;
	}
	
	public Map<String, Object> updateCustomerBaseInfo(Map<String, Object> params) throws AppRuntimeException {
		if (params == null) {
			throw new AppRuntimeException(AppExceptionEnum.PARAMSERR.getCode(),AppExceptionEnum.PARAMSERR.getMessage());
		}
		Map<String, Object> map = new HashMap<>();
		String status = customerInterface.updateCustomerBaseInfo(params);
		if ("SUCCESS".equals(status)) {
			map.put("resCode", "SUCCESS");
			map.put("resMsg", "提交成功");
		} else if ("FULLNAME_ERROR".equals(status)) {
			map.put("resCode", "FULLNAME_ERROR");
			map.put("resMsg", "商户全称重复");
		} else if ("SHORTNAME_ERROR".equals(status)) {
			map.put("resCode", "SHORTNAME_ERROR");
			map.put("resMsg", "商户简称重复");
		} else {
			map.put("resCode", "ERROR");
			map.put("resMsg", "提交失败");
		}
		return map;
	}

	@Override
	public Map<String, Object> querySettle(Map<String, Object> params) throws AppRuntimeException {
		if (params != null && null != params.get("customerNo")) {
			return customerInterface.querySettle(params.get("customerNo").toString());
		}
		throw new AppRuntimeException(AppExceptionEnum.PARAMSERR.getCode(),AppExceptionEnum.PARAMSERR.getMessage());
	}

    @Override
    public Map<String, Object> wechatInfo(Map<String, Object> params) throws AppRuntimeException {
        if (params != null && null != params.get("customerNo")) {
            Map<String, Object> res = new HashMap<>();
            Customer cust = customerInterface.getCustomer(params.get("customerNo").toString());
            res.put("customerImg", ImgUrl(userService.findUserImg(params.get("customerNo").toString())));
            res.put("cust", cust);
            return res;
        }
        throw new AppRuntimeException(AppExceptionEnum.PARAMSERR.getCode(),AppExceptionEnum.PARAMSERR.getMessage());
    }
}
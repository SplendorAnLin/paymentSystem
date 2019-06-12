package com.yl.receive.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.StringUtils;
import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.request.AccountSpecialCompositeTally;
import com.yl.account.api.bean.request.SpecialTallyVoucher;
import com.yl.account.api.bean.response.AccountCompositeTallyResponse;
import com.yl.account.api.dubbo.AccountInterface;
import com.yl.account.api.enums.Currency;
import com.yl.account.api.enums.FundSymbol;
import com.yl.account.api.enums.HandlerResult;
import com.yl.account.api.enums.UserRole;
import com.yl.boss.api.bean.AgentFee;
import com.yl.boss.api.bean.Customer;
import com.yl.boss.api.bean.CustomerFee;
import com.yl.boss.api.bean.ShareProfitBean;
import com.yl.boss.api.enums.ProductType;
import com.yl.boss.api.enums.Status;
import com.yl.boss.api.interfaces.AgentInterface;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.boss.api.interfaces.ShareProfitInterface;
import com.yl.payinterface.core.bean.ReceiveTradeBean;
import com.yl.payinterface.core.hessian.ReceiveHessianService;
import com.yl.receive.core.dao.ReceiveRequestDao;
import com.yl.receive.core.entity.ReceiveConfigInfo;
import com.yl.receive.core.entity.ReceiveRequest;
import com.yl.receive.core.enums.AccountType;
import com.yl.receive.core.enums.CardType;
import com.yl.receive.core.enums.CerType;
import com.yl.receive.core.enums.FeeType;
import com.yl.receive.core.enums.OrderStatus;
import com.yl.receive.core.enums.OwnerRole;
import com.yl.receive.core.enums.RequestClearingStatus;
import com.yl.receive.core.enums.SourceType;
import com.yl.receive.core.service.ReceiveConfigInfoService;
import com.yl.receive.core.service.ReceiveTrade;
import com.yl.receive.core.service.RouteConfigService;
import com.yl.receive.core.utils.CodeBuilder;
import com.yl.receive.core.utils.FeeUtils;
import com.yl.receive.hessian.bean.ReceiveRequestBean;
import com.yl.receive.hessian.bean.ResponseBean;
import com.yl.receive.hessian.enums.ResponseMsg;
import com.yl.receive.hessian.exception.ReceiveException;

/**
 * 代收交易服务接口实现
 *
 * @author 聚合支付有限公司
 * @since 2017年1月4日
 * @version V1.0.0
 */
@Service("receiveTrade")
public class ReceiveTradeImpl implements ReceiveTrade {

    private static final Logger logger = LoggerFactory.getLogger(ReceiveTradeImpl.class);

    @Resource
    private CustomerInterface customerInterface;
    @Resource
    private AgentInterface agentInterface;
    @Resource
    private ReceiveRequestDao receiveRequestDao;
    @Resource
    private RouteConfigService routeConfigService;
    @Resource
    private AccountInterface accountInterface;
    @Resource
    private ReceiveConfigInfoService receiveConfigInfoService;
    @Resource
    private ReceiveHessianService receiveHessianService;
    @Resource
    private ShareProfitInterface shareProfitInterface;


    @Override
    public ResponseBean trade(ReceiveRequestBean receiveRequestBean) {
        logger.error("商户编号为:{},商户订单号：{}，接受代收参数为:{}", receiveRequestBean.getCustomerNo(), receiveRequestBean.getCustomerOrderCode(), receiveRequestBean);
        ReceiveRequest oldReceiveRequest = receiveRequestDao.queryBySeqId(receiveRequestBean.getCustomerOrderCode(), receiveRequestBean.getCustomerNo());
        if (oldReceiveRequest != null) {
            logger.error("商户编号为:{},商户订单号：{}，已存在订单！", receiveRequestBean.getCustomerNo(), receiveRequestBean.getCustomerOrderCode());
            throw new ReceiveException(ResponseMsg.SEQNOEXIST.getCode(), ResponseMsg.SEQNOEXIST.getMessage());
        }
        ReceiveRequest receiveRequest = buildReceiveRequest(receiveRequestBean);
        // 校验代收请求
        validateReceiveRequest(receiveRequest);
        // 获取商户手续费
        getCustomerFee(receiveRequest);
        logger.error("商户编号为:{},商户订单号：{}，调用计费后：{}", receiveRequestBean.getCustomerNo(), receiveRequestBean.getCustomerOrderCode(), receiveRequest);
        // 保存
        receiveRequestDao.save(receiveRequest);
        // 选择路由
        String interfaceCode = getInterfaceCode(receiveRequest);
        logger.error("商户编号为:{},商户订单号：{}，选择通道为:{}", receiveRequestBean.getCustomerNo(), receiveRequestBean.getCustomerOrderCode(), interfaceCode);
        // 组装送往接口的参数
        ReceiveTradeBean receiveTradeBean = new ReceiveTradeBean();
        receiveTradeBean.setInterfaceCode(interfaceCode);
        receiveTradeBean.setBusinessOrderID(receiveRequest.getReceiveId());
        receiveTradeBean.setAmount(receiveRequest.getAmount());
        receiveTradeBean.setCardType(receiveRequest.getAccNoType().toString());
        receiveTradeBean.setOwnerId(receiveRequest.getOwnerId());
        receiveTradeBean.setBusinessOrderTime(receiveRequest.getCreateTime());
        receiveTradeBean.setAccountName(receiveRequest.getAccountName());
        receiveTradeBean.setAccountNo(receiveRequest.getAccountNo());
        receiveTradeBean.setAccountType(receiveRequest.getAccType().toString());
        receiveTradeBean.setBankCode(receiveRequest.getPayerBankNo());
        receiveTradeBean.setBankName(receiveRequest.getOpenBank());
        receiveTradeBean.setCertType(receiveRequest.getCertificatesType().toString());
        receiveTradeBean.setCertCode(receiveRequest.getCertificatesCode());
        receiveTradeBean.setRemark(receiveRequest.getRemark());
        receiveTradeBean.setPhone(receiveRequest.getPhone());
        logger.error("商户编号为:{},商户订单号：{}，送往支付接口请求:{}", receiveRequestBean.getCustomerNo(), receiveRequestBean.getCustomerOrderCode(), receiveTradeBean);
        Map<String, String> resultMap;
        try {
            resultMap = receiveHessianService.trade(receiveTradeBean);
            resultMap.put("interfaceCode", interfaceCode);
        } catch (Exception e) {
            ReceiveRequest request = receiveRequestDao.queryByReceiveId(receiveRequest.getReceiveId());
            request.setOrderStatus(OrderStatus.FAILED);
            receiveRequestDao.update(request, System.currentTimeMillis());
            throw new ReceiveException(ResponseMsg.UNKNOW.getCode(), ResponseMsg.UNKNOW.getMessage());
        }
        logger.error("商户编号为:{},商户订单号：{}，调用支付接口返回:{}", receiveRequestBean.getCustomerNo(), receiveRequestBean.getCustomerOrderCode(), resultMap);
        // 处理返回信息(入账)
        ResponseBean responseBean = callback(resultMap, receiveRequest.getReceiveId());
        return responseBean;
    }

    private ReceiveRequest buildReceiveRequest(ReceiveRequestBean receiveRequestBean){
        ReceiveRequest receiveRequest = new ReceiveRequest();
        try {
            receiveRequest.setOwnerId(receiveRequestBean.getCustomerNo());// 商户编号
            receiveRequest.setOwnerRole(OwnerRole.valueOf(receiveRequestBean.getOwnerRole().toString()));
            receiveRequest.setSeqId(receiveRequestBean.getCustomerOrderCode());// 商户订单号
            receiveRequest.setCertificatesCode(receiveRequestBean.getCertificatesCode().toUpperCase());// 身份证号转大写
            receiveRequest.setCertificatesType(CerType.valueOf(receiveRequestBean.getCertificatesType()));
            receiveRequest.setAmount(receiveRequestBean.getAmount());
            receiveRequest.setAccountName(receiveRequestBean.getAccountName().trim());
            receiveRequest.setAccountNo(receiveRequestBean.getAccountNo());
            receiveRequest.setNotifyUrl(receiveRequestBean.getNotifyUrl());
            receiveRequest.setSourceType(SourceType.valueOf(receiveRequestBean.getSourceType().name()));
            receiveRequest.setProvince(receiveRequestBean.getProvince());
            receiveRequest.setCity(receiveRequestBean.getCity());
            receiveRequest.setAccType(AccountType.valueOf(receiveRequestBean.getAccType().name()));
            receiveRequest.setAccNoType(CardType.valueOf(receiveRequestBean.getAccNoType().name()));
            receiveRequest.setBatchNo(createRandom(true, 10));
            receiveRequest.setContractId("");// 授权号
            receiveRequest.setOpenBank(receiveRequestBean.getOpenBank());
            receiveRequest.setPayerBankNo(receiveRequestBean.getPayerBankNo());
        } catch (Exception e) {
            logger.error("商户编号为:{}代收请求构建参数异常:{}", receiveRequestBean.getCustomerNo(), e);
            throw new ReceiveException(ResponseMsg.PARAMSERROR.getCode(), ResponseMsg.PARAMSERROR.getMessage());
        }
        return receiveRequest;
    }

    /**
     * 校验代收请求参数
     * @param receiveRequest 代收请求
     */
    private void validateReceiveRequest(ReceiveRequest receiveRequest) {

        if (StringUtils.isBlank(receiveRequest.getOwnerId())) {
            logger.error("代收请求商户编号为空");
            throw new ReceiveException(ResponseMsg.PARAMSERROR.getCode(), ResponseMsg.PARAMSERROR.getMessage());
        }

        // 获取商户名称
        String customerName = "";
        try {
            Customer customer = customerInterface.getCustomer(receiveRequest.getOwnerId());
            if (customer == null) {
                throw new ReceiveException(ResponseMsg.INVALID_CUSTOMER.getCode(), ResponseMsg.INVALID_CUSTOMER.getMessage());
            }
            customerName = customer.getShortName();
        } catch (Exception e) {
            logger.error("获取商户名称接口异常:{}", e);
        }
        receiveRequest.setOwnerName(customerName);

        // 校验银行卡号
        if (checkCardNo(receiveRequest.getAccountNo())) {
            logger.info("商户编号为:{},代收请求银行卡号非法", receiveRequest.getOwnerId());
            throw new ReceiveException(ResponseMsg.CARDILLEGAL.getCode(), ResponseMsg.CARDILLEGAL.getMessage());
        }

        // 卡识别
		/*try {
			BankCardInfo bankCardInfo = bankCardManageInterface.getBankCardInfo(receiveRequest.getAccountNo().trim());
			if (null != bankCardInfo) {
				receiveRequest.setBelongsBank(bankCardInfo.getOpenBank());
				receiveRequest.setOpenBank(bankCardInfo.getOpenBank());
				receiveRequest.setPayerBankNo(bankCardInfo.getBankCode());
			} else {
				logger.error("商户编号:{}代收发起时调用业务中心卡识别返回信息:{null}", receiveRequest.getOwnerId());
				throw new ReceiveException(RetCodeEnum.NO_MATCHES_OPENBANK.getCode(), RetCodeEnum.NO_MATCHES_OPENBANK.getMessage());
			}
		} catch (Exception e) {
			logger.error("商户编号:{}代收发起时卡识别异常:{}", receiveRequest.getOwnerId(), e);
			throw new ReceiveException(RetCodeEnum.NO_MATCHES_OPENBANK.getCode(), RetCodeEnum.NO_MATCHES_OPENBANK.getMessage());
		}*/

        // 校验金额
        checkLimit(receiveRequest);
    }


    /**
     * 校验限额(最低限额、日限额和单笔限额)
     * @param receiveRequest 代收请求
     */
    public void checkLimit(ReceiveRequest receiveRequest) {
        if (receiveRequest.getAmount() < 10) {
            throw new ReceiveException(ResponseMsg.PAY_AMOUNT_IS_SMALL.getCode(), ResponseMsg.PAY_AMOUNT_IS_SMALL.getMessage());
        }
		/*// 校验日限额
		if (checkDailyAmount(receiveRequest.getAmount(), receiveRequest.getOwnerId())) {
			logger.info("商户编号为:{}的代收请求单日限额超限", receiveRequest.getOwnerId());
			throw new ReceiveException(RetCodeEnum.BEYOND_DAILY_AMOUNT.getCode(), RetCodeEnum.BEYOND_DAILY_AMOUNT.getMessage());
		}*/
        // 校验单笔限额
        if (checkSingleAmount(receiveRequest.getAmount(), receiveRequest.getOwnerId())) {
            logger.info("商户编号为:{}的代收请求单笔限额超限", receiveRequest.getOwnerId());
            throw new ReceiveException(ResponseMsg.TRADE_OUT_OF_LIMIT.getCode(), ResponseMsg.TRADE_OUT_OF_LIMIT.getMessage());
        }
    }

    /**
     *
     * @Description 检查单笔限额
     * @param amount
     * @param customerNo
     * @return
     * @date 2017年1月4日
     */
    private boolean checkSingleAmount(Double amount, String customerNo) {
        boolean flag = false;
        ReceiveConfigInfo receiveConfigInfo = receiveConfigInfoService.queryBy(customerNo);
        if (receiveConfigInfo != null) {
            if (org.apache.commons.lang.math.NumberUtils.compare(receiveConfigInfo.getSingleMaxAmount(), amount) < 0){
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 获取商户手续费
     * @param receiveRequest 代收请求
     */
    private void getCustomerFee(ReceiveRequest receiveRequest) {

        try {
            if (receiveRequest.getOwnerRole() == OwnerRole.CUSTOMER) {
                CustomerFee customerFee = customerInterface.getCustomerFee(receiveRequest.getOwnerId(), ProductType.RECEIVE.name());
                if(customerFee == null){
                    throw new ReceiveException(ResponseMsg.NOT_OPEN.getCode(), ResponseMsg.NOT_OPEN.getMessage());
                }
                if(customerFee.getStatus() == Status.FALSE){
                    throw new ReceiveException(ResponseMsg.FEE_DISABLE.getCode(), ResponseMsg.FEE_DISABLE.getMessage());
                }
                // 计算手续费
                receiveRequest.setFee(FeeUtils.computeFee(receiveRequest.getAmount(), FeeType.valueOf(customerFee.getFeeType().toString()), customerFee.getFee()));
            } else if (receiveRequest.getOwnerRole() == OwnerRole.AGENT) {
                AgentFee agentFee = agentInterface.getAgentFee(receiveRequest.getOwnerId(), ProductType.RECEIVE);
                if(agentFee == null){
                    throw new ReceiveException(ResponseMsg.NOT_OPEN.getCode(), ResponseMsg.NOT_OPEN.getMessage());
                }
                if(agentFee.getStatus() == Status.FALSE){
                    throw new ReceiveException(ResponseMsg.FEE_DISABLE.getCode(), ResponseMsg.FEE_DISABLE.getMessage());
                }
                // 计算手续费
                receiveRequest.setFee(FeeUtils.computeFee(receiveRequest.getAmount(), FeeType.valueOf(agentFee.getFeeType().toString()), agentFee.getFee()));
            }
        } catch (Exception e) {
            logger.info("商户编号为:{}的代收请求调用计费系统异常:{}", receiveRequest.getOwnerId(), e);
            throw new ReceiveException(ResponseMsg.UNKNOW.getCode(), ResponseMsg.UNKNOW.getMessage());
        }
    }


    /**
     * 校验银行卡号
     * @param cardNo cardNo 卡号
     * @return true:非法卡号；false:合法卡号
     */
    public boolean checkCardNo(String cardNo) {
        if (!NumberUtils.isNumber(cardNo) || cardNo.length() < 14) return false;

        String[] nums = cardNo.split("");
        int sum = 0;
        int index = 1;
        for (int i = 0; i < nums.length; i++) {
            if ((i + 1) % 2 == 0) {
                if ("".equals(nums[nums.length - index])) {
                    continue;
                }
                int tmp = Integer.parseInt(nums[nums.length - index]) * 2;
                if (tmp >= 10) {
                    String[] t = String.valueOf(tmp).split("");
                    tmp = Integer.parseInt(t[1]) + Integer.parseInt(t[2]);
                }
                sum += tmp;
            } else {
                if ("".equals(nums[nums.length - index])) continue;
                sum += Integer.parseInt(nums[nums.length - index]);
            }
            index++;
        }
        if (sum % 10 != 0) {
            return true;
        }
        return false;
    }


    /**
     * 创建指定数量的随机字符串
     * @param numberFlag
     *            是否是数字
     * @param length
     * @return
     */
    public String createRandom(boolean numberFlag, int length) {
        String retStr = "";
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);

        return retStr;
    }

    /**
     *
     * @Description 路由，选择接口
     * @param request
     * @return
     * @date 2016年12月19日
     */
    private String getInterfaceCode(ReceiveRequest request){
        String interfaceCode = null;
        if(StringUtils.notBlank(request.getPayerBankNo()) && request.getAccType() != null && request.getAccNoType() != null
                && request.getCertificatesType() != null){
            interfaceCode = routeConfigService.compRoute(request.getAccountNo(), request.getAccType(), request.getAccNoType(), request.getCertificatesType());
            if(StringUtils.notBlank(interfaceCode)){
                return interfaceCode;
            }
        }

        if(StringUtils.notBlank(request.getPayerBankNo()) && request.getAccType() != null && request.getAccNoType() != null){
            interfaceCode = routeConfigService.compRoute(request.getPayerBankNo(), request.getAccType(), request.getAccNoType());
            if(StringUtils.notBlank(interfaceCode)){
                return interfaceCode;
            }
        }

        if(StringUtils.notBlank(request.getPayerBankNo()) && request.getAccType() != null){
            interfaceCode = routeConfigService.compRoute(request.getPayerBankNo(),request.getAccType());
            if(StringUtils.notBlank(interfaceCode)){
                return interfaceCode;
            }
        }

        if(StringUtils.notBlank(request.getPayerBankNo())){
            interfaceCode = routeConfigService.compRoute(request.getPayerBankNo());
            if(StringUtils.notBlank(interfaceCode)){
                return interfaceCode;
            }
        }

        if(request.getAccType() != null){
            interfaceCode = routeConfigService.compRoute(request.getAccType());
            if(StringUtils.notBlank(interfaceCode)){
                return interfaceCode;
            }
        }

        throw new ReceiveException(ResponseMsg.NO_AVAILABLE_CHANNEL.getCode(), ResponseMsg.NO_AVAILABLE_CHANNEL.getMessage());
    }

    public ResponseBean callback(Map<String, String> resultMap, String receiveId) {
        ReceiveRequest receiveRequest = receiveRequestDao.queryByReceiveId(receiveId);
        if (receiveRequest == null) {
            logger.error("代收订单号:{}回调出错，未查询到代收订单！", receiveId);
            throw new ReceiveException(ResponseMsg.UNKNOW.getCode(), ResponseMsg.UNKNOW.getMessage());
        }
        ResponseBean responseBean = new ResponseBean();
        responseBean.setAmount(receiveRequest.getAmount());
        responseBean.setRequestStatus(resultMap.get("tranStat"));;
        responseBean.setCustomerOrderCode(receiveRequest.getSeqId());
        responseBean.setRemark(receiveRequest.getRemark());
        responseBean.setReceiveId(receiveRequest.getReceiveId());
        responseBean.setFee(receiveRequest.getFee());
        if ("SUCCESS".equals(resultMap.get("tranStat"))) {
            responseBean.setResponseCode(ResponseMsg.SUCCESS.getCode());
            responseBean.setResponseMsg(ResponseMsg.SUCCESS.getMessage());
            // 代付结果返回成功则入账
            try {
                boolean flag = this.credit(receiveRequest);
                if (flag) {
                    receiveRequest.setClearingStatus(RequestClearingStatus.CLEARING_SUCCESS);
                } else {
                    receiveRequest.setClearingStatus(RequestClearingStatus.CLEARING_FAILED);
                }
            } catch (Exception e) {
                receiveRequest.setClearingStatus(RequestClearingStatus.CLEARING_FAILED);
            }

        } else if ("FAILED".equals(resultMap.get("tranStat"))) {
            responseBean.setResponseCode(ResponseMsg.FAILED.getCode());
            responseBean.setResponseMsg(ResponseMsg.FAILED.getMessage());
        } else {
            responseBean.setResponseCode(ResponseMsg.PROCESS.getCode());
            responseBean.setResponseMsg(ResponseMsg.PROCESS.getMessage());
            return responseBean;
        }

        receiveRequest.setOrderStatus(OrderStatus.valueOf(resultMap.get("tranStat")));
        if (resultMap.get("cost") != null) {
            receiveRequest.setCost(Double.valueOf(resultMap.get("cost")));
        }
        receiveRequest.setInterfaceRequestId(resultMap.get("requestNo"));

        receiveRequestDao.update(receiveRequest, System.currentTimeMillis());

        if("SUCCESS".equals(resultMap.get("tranStat"))){
            // 分润
            shareProfit(receiveRequest, resultMap.get("interfaceCode"));
        }
        responseBean.setHandleTime(new SimpleDateFormat("yyyyMMddHHmmss").format(receiveRequest.getLastUpdateTime()));
        return responseBean;
    }

    /**
     *
     * @Description 交易成功入账
     * @param receiveRequest
     * @return
     * @date 2017年1月8日
     */
    private boolean credit(ReceiveRequest receiveRequest){
        AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
        accountBussinessInterfaceBean.setBussinessCode("RECEIVE_CREDIT");
        accountBussinessInterfaceBean.setOperator("RECEIVE");
        accountBussinessInterfaceBean.setRemark("代收");
        accountBussinessInterfaceBean.setRequestTime(new Date());
        accountBussinessInterfaceBean.setSystemCode("RECEIVE");
        accountBussinessInterfaceBean.setSystemFlowId(CodeBuilder.build("DD","yyyymmdd"));

        AccountSpecialCompositeTally accountCompositeTally = new AccountSpecialCompositeTally();

        // 交易金额
        SpecialTallyVoucher amount = new SpecialTallyVoucher();
        amount.setAccountType(com.yl.account.api.enums.AccountType.CASH);
        amount.setUserNo(receiveRequest.getOwnerId());
        amount.setUserRole(UserRole.valueOf(receiveRequest.getOwnerRole().toString()));
        amount.setAmount(receiveRequest.getAmount());
        amount.setSymbol(FundSymbol.PLUS);
        amount.setCurrency(Currency.RMB);
        amount.setFee(receiveRequest.getFee());
        amount.setFeeSymbol(FundSymbol.SUBTRACT);
        amount.setTransDate(receiveRequest.getCreateTime());
        amount.setTransOrder(receiveRequest.getReceiveId());
        amount.setWaitAccountDate(new Date());

        List<SpecialTallyVoucher> tallyVouchers = new ArrayList<SpecialTallyVoucher>();
        tallyVouchers.add(amount);

        accountCompositeTally.setSpecialTallyVouchers(tallyVouchers);
        accountBussinessInterfaceBean.setTradeVoucher(accountCompositeTally);

        AccountCompositeTallyResponse accountDebitResponse = accountInterface.specialCompositeTally(accountBussinessInterfaceBean);
        logger.info("代付订单号:{}, 扣款返回信息：{}", receiveRequest.getReceiveId(), accountDebitResponse);
        if(accountDebitResponse.getStatus() != com.yl.account.api.enums.Status.SUCCESS){
            return false;
        }

        if(accountDebitResponse.getResult() != HandlerResult.SUCCESS){
            return false;
        }

        return true;
    }

    private void shareProfit(ReceiveRequest request, String interfaceCode){
        // 分润
        try {
            ShareProfitBean shareProfit = new ShareProfitBean();
            shareProfit.setCustomerNo(request.getOwnerId());
            shareProfit.setFee(request.getFee());
            shareProfit.setChannelCost(request.getCost());
            shareProfit.setAmount(request.getAmount());
            shareProfit.setInterfaceCode(interfaceCode);
            shareProfit.setOrderCode(request.getReceiveId());
            shareProfit.setOrderTime(request.getLastUpdateTime());
            shareProfit.setProductType(ProductType.RECEIVE);
            shareProfit.setSource("RECEIVE");
            shareProfitInterface.createShareProfit(shareProfit);
        } catch (Exception e) {
            logger.error("shareProfit failed!... [receive request:{}]", request.getReceiveId(), e);
        }
    }

}

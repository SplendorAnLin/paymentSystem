package com.yl.dpay.core.service.impl;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.DateUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.account.api.bean.request.AccountBalanceQuery;
import com.yl.account.api.bean.response.AccountBalanceQueryResponse;
import com.yl.account.api.dubbo.AccountQueryInterface;
import com.yl.account.api.enums.AccountType;
import com.yl.account.api.enums.UserRole;
import com.yl.boss.api.bean.AgentFee;
import com.yl.boss.api.bean.CustomerFee;
import com.yl.boss.api.enums.ProductType;
import com.yl.boss.api.enums.Status;
import com.yl.boss.api.interfaces.AgentInterface;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.dpay.core.Utils.*;
import com.yl.dpay.core.bean.NotifyInfo;
import com.yl.dpay.core.dao.RequestDao;
import com.yl.dpay.core.entity.CustomerReqInfo;
import com.yl.dpay.core.entity.Request;
import com.yl.dpay.core.enums.FeeType;
import com.yl.dpay.core.enums.NotifyStatus;
import com.yl.dpay.core.enums.OwnerRole;
import com.yl.dpay.core.enums.RequestStatus;
import com.yl.dpay.core.service.CustomerReqInfoService;
import com.yl.dpay.core.service.RequestService;
import com.yl.dpay.core.service.ServiceConfigService;
import com.yl.dpay.hessian.beans.DpayRuntimeException;
import com.yl.dpay.hessian.enums.DpayExceptionEnum;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 代付信息业务接口实现
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年5月13日
 */
@Service("requestService")
public class RequestServiceImpl implements RequestService {

    private static Logger log = LoggerFactory.getLogger(RequestServiceImpl.class);

    @Resource
    private RequestDao requestDao;
    @Resource
    private CustomerInterface customerInterface;
    @Resource
    private AgentInterface agentInterface;
    @Resource
    private AccountQueryInterface accountQueryInterface;
    @Resource
    private CustomerReqInfoService customerReqInfoService;
    @Resource
    private ServiceConfigService serviceConfigService;

    @Override
    @Transactional
    public void create(Request request) {
        compFee(request);
        AccountBalanceQuery accountBalanceQuery = new AccountBalanceQuery();
        accountBalanceQuery.setUserNo(request.getOwnerId());
        accountBalanceQuery.setUserRole(UserRole.valueOf(request.getOwnerRole().toString()));
        accountBalanceQuery.setAccountType(AccountType.CASH);
        AccountBalanceQueryResponse response = accountQueryInterface.findAccountBalance(accountBalanceQuery);
        if (AmountUtils.add(request.getAmount(), request.getFee()) > response.getAvailavleBalance()) {
            log.error("可用余额不足 订单金额：{}，手续费：{}，账户可用余额：{}", request.getAmount(), request.getFee(), response.getAvailavleBalance());
            throw new DpayRuntimeException(DpayExceptionEnum.CUST_BAL_ERR.getCode(), DpayExceptionEnum.CUST_BAL_ERR.getMessage());
        }

        request.setFlowNo(CodeBuilder.build("DF", "yyMMdd"));
        request.setCreateDate(new Date());
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (request.getCreateDate() != null) {
                map.put("createDate", df.format(request.getCreateDate()).toString());
            }
            map.put("requestNo", request.getRequestNo());
            map.put("requestType", request.getRequestType());
            map.put("amount", request.getAmount());
            map.put("fee", request.getFee());
            map.put("cost", request.getCost());
            map.put("description", request.getDescription());
            map.put("completeMsg", request.getCompleteMsg());
            map.put("auditStatus", request.getAuditStatus());
            map.put("ownerId", request.getOwnerId());
            map.put("ownerRole", request.getOwnerRole());
            map.put("accountName", request.getAccountName());
            map.put("accountNo", request.getAccountNo());
            map.put("accountType", request.getAccountType());
            map.put("cardType", request.getCardType());
            map.put("cerNo", request.getCerNo());
            map.put("flowNo", request.getFlowNo());
            if (request.getAuditDate() != null) {
                map.put("auditDate", df.format(request.getAuditDate()).toString());
            }
            if (request.getCompleteDate() != null) {
                map.put("completeDate", df.format(request.getCompleteDate()).toString());
            }
            map.put("operator", request.getOperator());
            map.put("bankCode", request.getBankCode());
            map.put("bankName", request.getBankName());
//			producerClient.sendMessage(new ProducerMessage(Constant.DATATOPIC,Constant.DATATAG,JsonUtils.toJsonString(map).getBytes()));
        } catch (Exception e) {
            log.error("推送代付申请请求到数据中心错误，错误原因{}，错误请求单号{}", e, request.getRequestNo());
        }
        requestDao.insert(request);
    }

    @Override
    public Request updateAuditStatus(Request request) {
        requestDao.update(request);
        return request;
    }

    @Override
    public Request updateStatus(Request request) {
        Request r = requestDao.findByFlowNo(request.getFlowNo());
        if (r != null && (RequestStatus.WAIT == r.getStatus() || RequestStatus.HANDLEDING == r.getStatus())) {
            r.setStatus(request.getStatus());
            r.setCompleteDate(request.getCompleteDate());
            r.setCompleteMsg(request.getCompleteMsg());
            r.setInterfaceInfoCode(request.getInterfaceInfoCode());
            requestDao.update(r);
            if (r.getStatus() == RequestStatus.FAILED || RequestStatus.SUCCESS == r.getStatus()) {
                this.notifyCust(request);
            }
            return r;
        } else {
            throw new DpayRuntimeException(DpayExceptionEnum.SYSERR.getCode(), DpayExceptionEnum.getMessage(DpayExceptionEnum.SYSERR.getCode()));
        }
    }

    @Override
    public Request findByFlowNo(String flowNo) {
        return requestDao.findByFlowNo(flowNo);
    }

    @Override
    @Transactional
    public Request findByRequestNo(String requestNo, String ownerId) {
        return requestDao.findByRequestNo(requestNo, ownerId);
    }

    @Override
    public List<Request> findByStatus(RequestStatus status) {
        return requestDao.findByStatus(status);
    }

    @Override
    public void update(Request request) {
        Request r = requestDao.findByFlowNo(request.getFlowNo());
        if (r != null && RequestStatus.FAILED == r.getStatus()) {
            r.setStatus(RequestStatus.HANDLEDING);
            r.setAccountName(request.getAccountName());
            r.setAccountNo(request.getAccountNo());
            r.setAccountType(request.getAccountType());
            r.setAmount(request.getAmount());
            r.setBankCode(request.getBankCode());
            r.setCardType(request.getCardType());
            r.setCerNo(request.getCerNo());
            r.setCerType(request.getCerType());
            r.setCvv(request.getCvv());
            r.setValidity(request.getValidity());
            requestDao.update(r);
            request = r;
            if (request.getStatus() == RequestStatus.FAILED || RequestStatus.SUCCESS == request.getStatus()) {
                this.notifyCust(request);
            }
        } else {
            throw new DpayRuntimeException(DpayExceptionEnum.SYSERR.getCode(), DpayExceptionEnum.getMessage(DpayExceptionEnum.SYSERR.getCode()));
        }
    }

    @Override
    public void updateNotify(Request request) {
        Request r = requestDao.findByFlowNo(request.getFlowNo());
        if (r != null) {
            r.setNotifyCount(request.getNotifyCount());
            r.setNotifyStatus(request.getNotifyStatus());
            r.setNotifyTime(request.getNotifyTime());
            requestDao.update(r);
        }
    }

    @Override
    public List<Request> findWaitNotifyRequest(int count, int nums) {
        return requestDao.findWaitNotifyRequest(count, nums);
    }

    private void compFee(Request request) {
        if (HolidayUtils.isHoliday()) {
            if (request.getOwnerRole() == OwnerRole.CUSTOMER) {
                // 假日付 计算手续费
                CustomerFee customerFee = customerInterface.getCustomerFee(request.getOwnerId(), "HOLIDAY_REMIT");
                if (customerFee == null) {
                    throw new DpayRuntimeException(DpayExceptionEnum.NOTOPEN.getCode(), DpayExceptionEnum.NOTOPEN.getMessage());
                }
                if (customerFee.getStatus() == Status.FALSE) {
                    throw new DpayRuntimeException(DpayExceptionEnum.FEE_DISABLE.getCode(), DpayExceptionEnum.FEE_DISABLE.getMessage());
                }
                request.setFee(FeeUtils.computeFee(request.getAmount(), FeeType.valueOf(customerFee.getFeeType().toString()), customerFee.getFee()));
            }
            if (request.getOwnerRole() == OwnerRole.AGENT) {
                // 假日付 计算手续费
                AgentFee agentFee = agentInterface.getAgentFee(request.getOwnerId(), ProductType.HOLIDAY_REMIT);
                if (agentFee == null) {
                    throw new DpayRuntimeException(DpayExceptionEnum.CUST_NOT_OPEN_HOLIDAY.getCode(), DpayExceptionEnum.CUST_NOT_OPEN_HOLIDAY.getMessage());
                }
                if (agentFee.getStatus() == Status.FALSE) {
                    throw new DpayRuntimeException(DpayExceptionEnum.FEE_DISABLE.getCode(), DpayExceptionEnum.FEE_DISABLE.getMessage());
                }
                request.setFee(FeeUtils.computeFee(request.getAmount(), FeeType.valueOf(agentFee.getFeeType().toString()), agentFee.getFee()));
            }
        } else {
            if (request.getOwnerRole() == OwnerRole.CUSTOMER) {
                // 计算手续费
                CustomerFee customerFee = customerInterface.getCustomerFee(request.getOwnerId(), "REMIT");
                if (customerFee == null) {
                    throw new DpayRuntimeException(DpayExceptionEnum.NOTOPEN.getCode(), DpayExceptionEnum.NOTOPEN.getMessage());
                }
                if (customerFee.getStatus() == Status.FALSE) {
                    throw new DpayRuntimeException(DpayExceptionEnum.FEE_DISABLE.getCode(), DpayExceptionEnum.FEE_DISABLE.getMessage());
                }
                request.setFee(FeeUtils.computeFee(request.getAmount(), FeeType.valueOf(customerFee.getFeeType().toString()), customerFee.getFee()));
            }
            if (request.getOwnerRole() == OwnerRole.AGENT) {
                // 计算手续费
                AgentFee agentFee = agentInterface.getAgentFee(request.getOwnerId(), ProductType.REMIT);
                if (agentFee == null) {
                    throw new DpayRuntimeException(DpayExceptionEnum.NOTOPEN.getCode(), DpayExceptionEnum.NOTOPEN.getMessage());
                }
                if (agentFee.getStatus() == Status.FALSE) {
                    throw new DpayRuntimeException(DpayExceptionEnum.FEE_DISABLE.getCode(), DpayExceptionEnum.FEE_DISABLE.getMessage());
                }
                request.setFee(FeeUtils.computeFee(request.getAmount(), FeeType.valueOf(agentFee.getFeeType().toString()), agentFee.getFee()));
            }
        }
    }

    @Override
    public String orderSumByDay(Date orderTimeStart, Date orderTimeEnd, String owner, int day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
        StringBuffer sb = new StringBuffer();
        List<Map<String, String>> list = requestDao.orderSumByDay(sdf.format(orderTimeStart), sdf.format(orderTimeEnd), owner);
        Date over = DateUtils.addDays(orderTimeStart, -day); //结束日期
        for (int i = 0; i < day; i++) {
            String amount = "0";
            String days = sd.format(over);
            String counts = "0";
            for (int j = 0; j < list.size(); j++) {
                String db = JsonUtils.toJsonString(list.get(j));
                JSONObject jsonObject = JSONObject.fromObject(db);
                if (jsonObject.getString("days").equals(sd.format(over))) {
                    amount = jsonObject.getString("amount");
                    days = jsonObject.getString("days");
                    counts = jsonObject.getString("count");
                    break;
                }
            }
            sb.append("{\"amount\": " + amount + ",");
            sb.append("\"days\": " + days + ",");
            sb.append("\"count\": " + counts + "},");
            over = DateUtils.addDays(over, 1);
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    @Override
    public List<Map<String, String>> dfSuccess(Date orderTimeStart, Date orderTimeEnd, List owner) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return requestDao.dfSuccess(sdf.format(orderTimeStart), sdf.format(orderTimeEnd), owner);
    }

    @Override
    public List<Map<String, String>> dfSuccessCount(Date orderTimeStart, Date orderTimeEnd, String owner) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return requestDao.dfSuccessCount(sdf.format(orderTimeStart), sdf.format(orderTimeEnd), owner);
    }

    @Override
    public List<Map<String, Object>> selectRequest(Map<String, Object> params) {
        try {
            List<Map<String, Object>> request = requestDao.selectRequest(params);
            return request;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> selectRequestDetailed(Map<String, Object> params) {
        try {
            List<Map<String, Object>> request = requestDao.selectRequestDetailed(params);
            List<Map<String, Object>> requestDetailed = new ArrayList<Map<String, Object>>();
            if (request.size() != 0) {
                requestDetailed.add(request.get(0));
            }
            return requestDetailed;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Object> selectRequestsum(Map<String, Object> params) {
        return requestDao.selectRequestsum(params);
    }

    @Override
    public List<Map<String, Object>> findrequest(Map<String, Object> params, Page page) {
        return requestDao.findAllrequest(params, page);
    }

    @Override
    public Map<String, Object> findrequestDetail(Map<String, Object> params) {
        return requestDao.findrequestDetail(params);
    }


    @Override
    public List<Request> findByParams(Map<String, Object> params) {
        return requestDao.findByParams(params);
    }

    @Override
    public Map<String, Object> findSumCountAndFee(Map<String, Object> params) {
        return requestDao.findSumCountAndFee(params);
    }

    @Override
    public List<Request> findByCreateTime(Map<String, Object> params) {
        return requestDao.findByCreateTime(params);
    }

    @Override
    public Map<String, Object> customerReconHead(Map<String, Object> params) {
        return requestDao.customerReconHead(params);
    }

    public void insert(Request request) {
        requestDao.insert(request);
    }

    /**
     * 代付完成通知
     * @param request
     */
    private void notifyCust(Request request) {
        boolean flag = false;
        try {
            // 组装通知参数
            CustomerReqInfo customerReqInfo = customerReqInfoService.findByCutomerOrderCode(request.getOwnerId(), request.getRequestNo(), "TRADE");
            if(customerReqInfo == null || StringUtils.isBlank(customerReqInfo.getNotifyUrl())){
                return;
            }
            NotifyInfo notifyInfo = new NotifyInfo();
            notifyInfo.setCustomerOrderId(request.getRequestNo());
            notifyInfo.setDpayOrderId(request.getFlowNo());
            notifyInfo.setOrderTime(request.getCompleteDate());
            if (request.getStatus() == RequestStatus.SUCCESS) {
                notifyInfo.setResponseCode(DpayExceptionEnum.REMITSUCCESS.getCode());
                notifyInfo.setResponseMsg(DpayExceptionEnum.REMITSUCCESS.getMessage());
            } else {
                String errMsg = request.getCompleteMsg();
                if (StringUtils.isNotBlank(errMsg)) {
                    errMsg = "," + errMsg;
                }
                notifyInfo.setResponseCode(DpayExceptionEnum.REMITFAILED.getCode());
                notifyInfo.setResponseMsg(DpayExceptionEnum.REMITFAILED.getMessage() + errMsg);
            }
            notifyInfo.setResponseCode(DpayExceptionEnum.getConvertCode(notifyInfo.getResponseCode()));
            notifyInfo.setResponseMsg(DpayExceptionEnum.getConvertMsg(notifyInfo.getResponseCode()));
            notifyInfo.setAmount(Double.toString(request.getAmount()));
            notifyInfo.setFee(Double.toString(request.getFee()));
            log.info("商户号为:{},订单号为:{}的代付通知信息:{}", request.getOwnerId(), request.getFlowNo(), notifyInfo);

            // 查密钥
            String password = serviceConfigService.find(request.getOwnerId()).getPrivateKey();
            String encryptResult = Base64Utils.encode(RSAUtils.encryptByPrivateKey(JsonUtils.toJsonString(notifyInfo).getBytes(), password));
            Map<String, String> notifyParams = new HashMap<String, String>();
            notifyParams.put("cutomerNo", request.getOwnerId());
            notifyParams.put("versionCode", "1.0");
            notifyParams.put("customerParam", customerReqInfo.getCustomerParam());
            notifyParams.put("cipherText", encryptResult);
            log.info("商户号为:{},订单号为:{}的 代付通知地址: {},通知参数:{}", request.getOwnerId(), request.getFlowNo(), customerReqInfo.getNotifyUrl(), notifyParams);

            // 通知
            String resMsg = HttpUtils.sendReq(JsonUtils.toJsonString(notifyParams), customerReqInfo.getNotifyUrl());

            log.info("商户号为:{},订单号为:{}的代付通知响应参数:{}", request.getOwnerId(), request.getFlowNo(), resMsg);
            if (resMsg != null && resMsg.equals("SUCCESS")) {
                flag = true;
            }
        } catch (Exception e) {
            log.error("商户号为:{},订单号为:{}通知异常, 异常信息:{}", request.getOwnerId(), request.getFlowNo(), e);
        }

        // 更新通知信息
        request.setNotifyCount(request.getNotifyCount() + 1);
        request.setNotifyTime(new Date());
        if (flag) {
            request.setNotifyStatus(NotifyStatus.SUCCESS);
        }
        try {
            this.updateNotify(request);
        } catch (Exception e) {
            log.error("商户号为:{},订单号为:{}通知异常, 异常信息:{}", request.getOwnerId(), request.getFlowNo(), e);
        }
    }

}
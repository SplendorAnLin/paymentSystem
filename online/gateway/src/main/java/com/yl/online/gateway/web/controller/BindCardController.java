package com.yl.online.gateway.web.controller;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.security.DigestUtils;
import com.yl.boss.api.bean.Customer;
import com.yl.boss.api.bean.CustomerKey;
import com.yl.boss.api.bean.TransCardBean;
import com.yl.boss.api.enums.CardAttr;
import com.yl.boss.api.enums.CardStatus;
import com.yl.boss.api.enums.CustomerStatus;
import com.yl.boss.api.enums.KeyType;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.online.gateway.AuthExceptionMessages;
import com.yl.online.gateway.Constants;
import com.yl.online.gateway.ExceptionMessages;
import com.yl.online.gateway.exception.BusinessException;
import com.yl.online.gateway.utils.Base64Utils;
import com.yl.online.gateway.utils.CommonUtils;
import com.yl.online.gateway.utils.IdcardValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 绑卡控制器
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年9月23日
 */
@Controller
public class BindCardController {
    private static final Logger logger = LoggerFactory.getLogger(BindCardController.class);
    private static final String CHARSET = "UTF-8";
    @Resource
    private CustomerInterface customerInterface;

    @RequestMapping("bindCard")
    public void bindCard(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> reqParams;
        Map<String, String> resParams;
        Map<String, Object> resData = new HashMap<>();;
        Map<String, String> cardParams;
        Customer customer = null;
        reqParams = CommonUtils.pareseParams(request);
        logger.info("绑卡请求报文 : {}", reqParams);
        try {
            customer = checkCommonParam(reqParams);
            if (StringUtils.isBlank(reqParams.get(Constants.PARAM_NAME_CARD_NO))) {
                throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_CARD_NO).toString());
            } else {
                cardParams = checkCardNo(reqParams.get(Constants.PARAM_NAME_CARD_NO));
            }

            if ("01".equals(reqParams.get("bindType"))) {
                if ("TRANS_CARD".equals(reqParams.get("cardType"))) {
                    resData = customerInterface.fastAddTransCard(customer.getCustomerNo(), reqParams.get(Constants.PARAM_NAME_CARD_NO), CardAttr.TRANS_CARD.name());
                } else {
                    resData = customerInterface.fastAddTransCard(customer.getCustomerNo(), reqParams.get(Constants.PARAM_NAME_CARD_NO), CardAttr.SETTLE_CARD.name());
                }
            } else {
                Map<String, Object> bindCardParams = new HashMap<>();
                // 交易卡信息
                bindCardParams.put("cleAccNo", reqParams.get(Constants.SETTLE_CAED_NO)); // 结算卡 卡号
                bindCardParams.put("cleAccName", reqParams.get(Constants.USER_NAME)); // 结算卡 名称
                bindCardParams.put("cleBankName", reqParams.get(Constants.SETTLE_BANK_NAME)); // 结算卡 开户行名称
                bindCardParams.put("customerNo", customer.getCustomerNo()); // 商户编号
                bindCardParams.put("accountNo", reqParams.get(Constants.PARAM_NAME_CARD_NO)); // 交易卡 卡号
                bindCardParams.put("accountName", reqParams.get(Constants.USER_NAME)); // 交易卡名称
                bindCardParams.put("bankName", cardParams.get("agencyName")); // 交易卡银行名称
                bindCardParams.put("bankCode", cardParams.get("providerCode")); // 交易卡银行编码
                bindCardParams.put("cardAlias", cardParams.get("cardName")); // 交易卡 名称
                bindCardParams.put("cardType", cardParams.get("cardType")); // 卡类型
                bindCardParams.put("cardAttr", CardAttr.TRANS_CARD); // 卡属性

                bindCardParams.put("idNumber", reqParams.get(Constants.ID_CARD_NO)); // 身份证号
                bindCardParams.put("phone", reqParams.get(Constants.PHONE)); // 手机号
                resData = customerInterface.addTransCardForApi(bindCardParams);
            }
            resParams = new HashMap<>();
        } catch (Exception e) {
            logger.error("绑卡处理失败", e);
            resParams = new HashMap<>();
            resParams.put("respCode", "0001");
            resParams.put("respMsg", "失败");
        }
        convertBindResCode(resData);
        try {
            CustomerKey customerKey = customerInterface.getCustomerKey(customer.getCustomerNo(), KeyType.MD5);
            resParams.put("respCode", resData.get("responseCode").toString());
            resParams.put("respMsg", resData.get("responseMessage").toString());
            String sign = DigestUtils.md5DigestAsHex((CommonUtils.convertMap2Str(resParams) + customerKey.getKey()).getBytes(CHARSET));
            resParams.put("sign", sign);
            logger.error("绑卡响应报文 : {}", resParams);
            response.getWriter().write(Base64Utils.encode(JsonUtils.toJsonString(resParams).getBytes(CHARSET)));
        }catch (Exception e){
            logger.error("绑卡响应报文失败", e);
        }

    }

    @RequestMapping("unBindCard")
    public void unBindCard(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> reqParams;
        Map<String, String> resParams = new HashMap<>();
        reqParams = CommonUtils.pareseParams(request);
        logger.error("解绑请求报文 : {}", reqParams);
        Customer customer;
        try {
            customer = checkCommonParam(reqParams);
            Map<String, Object> resData = customerInterface.unlockTansCard(customer.getCustomerNo(), reqParams.get(Constants.PARAM_NAME_CARD_NO), CardAttr.TRANS_CARD.name());
            if ("REP00000".equals(resData.get("resultCode"))) {
                // 组装响应参数
                resParams.put("respCode", "0000");
                resParams.put("respMsg", "解绑成功");
            } else {
                resParams.put("respCode", "0001");
                resParams.put("respMsg", "解绑失败");
            }

            CustomerKey customerKey = customerInterface.getCustomerKey(customer.getCustomerNo(), KeyType.MD5);
            logger.error("解绑响应报文 : {}", resParams);
            String sign = DigestUtils.md5DigestAsHex((CommonUtils.convertMap2Str(resParams) + customerKey.getKey()).getBytes(CHARSET));
            resParams.put("sign", sign);
            logger.error("解绑响应报文 : {}", resParams);
            response.getWriter().write(Base64Utils.encode(JsonUtils.toJsonString(resParams).getBytes(CHARSET)));
        } catch (Exception e) {
            logger.error("解绑请求失败", e);
            resParams.put("respCode", "0003");
            resParams.put("respMsg", "处理失败");
            logger.error("解绑响应报文 : {}", resParams);
            try {
                response.getWriter().write(Base64Utils.encode(JsonUtils.toJsonString(resParams).getBytes(CHARSET)));
            } catch (IOException e1) {
                logger.error("解绑下单 响应参数失败", e1);
            }
        }
    }

    @RequestMapping("queryBindCard")
    public void queryBindCard(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> reqParams;
        Map<String, String> resParams = new HashMap<>();

        reqParams = CommonUtils.pareseParams(request);
        logger.error("绑卡查询 请求报文 : ", reqParams);
        Customer customer;
        try {
            customer = checkCommonParam(reqParams);

            // 查询绑卡信息
            TransCardBean transCardBean = customerInterface.findTransCardByAccNo(customer.getCustomerNo(), reqParams.get(Constants.PARAM_NAME_CARD_NO), CardAttr.TRANS_CARD);
            CustomerKey customerKey = customerInterface.getCustomerKey(customer.getCustomerNo(), KeyType.MD5);
            if (transCardBean == null) {
                resParams.put("respCode", "0001");
                resParams.put("respMsg", "未绑定");
            } else if (transCardBean.getCardStatus() == CardStatus.NORAML) {
                resParams.put("respCode", "0000");
                resParams.put("respMsg", "正常");
            } else if (transCardBean.getCardStatus() == CardStatus.DISABLED) {
                resParams.put("respCode", "0002");
                resParams.put("respMsg", "禁用");
            } else {
                resParams.put("respCode", "0003");
                resParams.put("respMsg", "已解绑");
            }
            String sign = DigestUtils.md5DigestAsHex((CommonUtils.convertMap2Str(resParams) + customerKey.getKey()).getBytes(CHARSET));
            resParams.put("sign", sign);
            logger.error("绑卡查询 响应报文 : {}", resParams);
            response.getWriter().write(Base64Utils.encode(JsonUtils.toJsonString(resParams).getBytes(CHARSET)));
        } catch (Exception e) {
            logger.error("绑卡请求失败", e);
            resParams.put("respCode", "9999");
            resParams.put("respMsg", "处理失败");
            logger.error("绑卡查询 响应报文 : {}", resParams);
            try {
                response.getWriter().write(Base64Utils.encode(JsonUtils.toJsonString(resParams).getBytes(CHARSET)));
            } catch (IOException e1) {
                logger.error("绑卡请求失败", e1);
            }
        }
    }

    /**
     * 签名检验
     *
     * @param params 原请求信息
     * @throws BusinessException
     */
    private void signValidate(Map<String, String> params) throws BusinessException {
        CustomerKey customerKey = customerInterface.getCustomerKey(params.get("partner"), KeyType.MD5);
        if (!CommonUtils.md5CheckSign(params, customerKey.getKey())) {
            throw new BusinessException(ExceptionMessages.SIGN_ERROR);
        }
    }

    private Customer checkCommonParam(Map<String, String> reqParams) throws BusinessException {
        if (reqParams == null || reqParams.size() == 0) {
            throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_API_CODE).toString());
        }
        // 检查接口名称
        if (StringUtils.isBlank(reqParams.get(Constants.PARAM_NAME_API_CODE)) || "YL-PAY".equals(StringUtils.isBlank(reqParams.get(Constants.PARAM_NAME_API_CODE)))) {
            throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_API_CODE).toString());
        }
        // 检查输入字符集
        if (StringUtils.isBlank(reqParams.get(Constants.PARAM_NAME_INPUT_CHARSET))) {
            throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_INPUT_CHARSET).toString());
        }
        // 检查合作方
        if (StringUtils.isBlank(reqParams.get(Constants.PARAM_NAME_PARTNER))) {
            throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.RECEIVER_NOT_EXISTS, "-", Constants.PARAM_NAME_PARTNER).toString());
        }
        // 检查签名方式
        if (StringUtils.isBlank(reqParams.get(Constants.PARAM_NAME_SIGN_TYPE)) || !"MD5".equals(reqParams.get(Constants.PARAM_NAME_SIGN_TYPE))) {
            throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_SIGN_TYPE).toString());
        }
        // 检查签名
        if (StringUtils.isBlank(reqParams.get(Constants.PARAM_NAME_SIGN))) {
            throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_SIGN).toString());
        }
        Customer customer = customerInterface.getCustomer(reqParams.get(Constants.PARAM_NAME_PARTNER));
        // 商户校验
        if (customer == null) {
            throw new BusinessException(ExceptionMessages.RECEIVER_NOT_EXISTS);
        }
        // 商户状态校验
        if (customer.getStatus() != CustomerStatus.TRUE) {
            throw new BusinessException(ExceptionMessages.RECEIVER_STATUS_ERROR);
        }
        // 校验银行卡号
        if (StringUtils.isBlank(reqParams.get(Constants.PARAM_NAME_CARD_NO))) {
            throw new BusinessException("4001");
        } else {
            checkCardNo(reqParams.get(Constants.PARAM_NAME_CARD_NO));
        }
        signValidate(reqParams);
        return customer;
    }

    /**
     * 卡识别
     *
     * @param cardNo
     * @return cardType
     */
    private Map<String, String> checkCardNo(String cardNo) throws BusinessException {
        Map<String, String> params;
        try {
            params = CommonUtils.getCardInfo(cardNo);
            if (params == null) {
                throw new BusinessException("4001");
            }
            return params;
        } catch (Exception e) {
            logger.error("卡识别 卡号:{},异常 ", cardNo, e);
            throw new BusinessException("4001");
        }
    }

    /**
     * 绑卡参数校验
     * @param params
     * @throws BusinessException
     */
    private void checkBindParams(Map<String, String> params) throws BusinessException{
        checkCardNo(params.get(Constants.SETTLE_CAED_NO)); // 结算卡 卡号
        if(StringUtils.isBlank(params.get(Constants.USER_NAME))){ // 结算卡 名称
            throw new BusinessException(ExceptionMessages.PARAM_NOT_EXISTS);
        }
        if(StringUtils.isBlank(params.get(Constants.PHONE)) || params.get(Constants.PHONE).length() != 11){
            throw new BusinessException(ExceptionMessages.PARAM_ERROR);
        }
        if(StringUtils.isBlank(params.get(Constants.ID_CARD_NO)) || !IdcardValidator.instance().isValidatedAllIdcard(params.get(Constants.ID_CARD_NO))){
            throw new BusinessException(ExceptionMessages.PARAM_ERROR);
        }
        if(StringUtils.isBlank(params.get(Constants.SETTLE_BANK_NAME))){
            throw new BusinessException(ExceptionMessages.PARAM_ERROR);
        }
    }

    private void convertBindResCode(Map<String, Object> params) {
        String responseCode = String.valueOf(params.get("responseCode"));
        switch (responseCode) {
            case "REP99994":
                params.put("responseCode", AuthExceptionMessages.TRNS_CARD_BINDED.getCode());
                params.put("responseMessage", AuthExceptionMessages.TRNS_CARD_BINDED.getMessage());
                break;
            case "REP99995":
                params.put("responseCode", AuthExceptionMessages.AUTH_FAIL.getCode());
                params.put("responseMessage", AuthExceptionMessages.AUTH_FAIL.getMessage());
                break;
            case "REP99999":
                params.put("responseCode", AuthExceptionMessages.BIND_FAILED.getCode());
                params.put("responseMessage", AuthExceptionMessages.BIND_FAILED.getMessage());
                break;
            case "REP99993":
                params.put("responseCode", AuthExceptionMessages.SETTLE_CARD_ERR.getCode());
                params.put("responseMessage", AuthExceptionMessages.SETTLE_CARD_ERR.getMessage());
                break;
            case "REP99998":
                params.put("responseCode", AuthExceptionMessages.BIND_FAILED.getCode());
                params.put("responseMessage", AuthExceptionMessages.BIND_FAILED.getMessage());
                break;
            case "REP99997":
                params.put("responseCode", AuthExceptionMessages.UNBIND_FAILED.getCode());
                params.put("responseMessage", AuthExceptionMessages.UNBIND_FAILED.getMessage());
                break;
            case "REP99990":
                params.put("responseCode", AuthExceptionMessages.UNSUPPORT_SETTLE.getCode());
                params.put("responseMessage", AuthExceptionMessages.UNSUPPORT_SETTLE.getMessage());
                break;
            case "REP99991":
                params.put("responseCode", AuthExceptionMessages.UNSUPPORT_TRANS.getCode());
                params.put("responseMessage", AuthExceptionMessages.UNSUPPORT_TRANS.getMessage());
                break;
            case "REP00000":
                params.put("responseCode", AuthExceptionMessages.SUCCESS.getCode());
                params.put("responseMessage", AuthExceptionMessages.SUCCESS.getMessage());
                break;
            default:
                params.put("responseCode", AuthExceptionMessages.FAILED.getCode());
                params.put("responseMessage", AuthExceptionMessages.FAILED.getMessage());
        }
    }
}

/**
 *
 */
package com.yl.account.core.service.impl;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.account.core.dao.AccountAdjustVoucherDao;
import com.yl.account.core.service.AccountAdjustVoucherService;
import com.yl.account.core.utils.CodeBuilder;
import com.yl.account.enums.*;
import com.yl.account.model.AccountAdjustVoucher;
import com.yl.mq.producer.client.ProducerClient;
import com.yl.mq.producer.client.ProducerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 账户调账业务逻辑处理接口实现
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年3月22日
 */
@Service("accountAdjustVoucherService")
public class AccountAdjustVoucherServiceImpl implements AccountAdjustVoucherService {
    private static final Logger logger = LoggerFactory.getLogger(AccountAdjustVoucherServiceImpl.class);
    @Resource
    private AccountAdjustVoucherDao accountAdjustVoucherDao;
    @Resource
    ProducerClient producerClient;

    /*
     * (non-Javadoc)
     * @see com.yl.account.core.service.AccountAdjustVoucherService#findBy(java.util.Map)
     */
    @Override
    public List<AccountAdjustVoucher> findBy(Map<String, Object> params) {
        return accountAdjustVoucherDao.findBy(params);
    }

    /*
     * (non-Javadoc)
     * @see com.yl.account.core.service.AccountAdjustVoucherService#findBy(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public AccountAdjustVoucher findBy(String system, String systemFlowId, String bussinessCode, String accountNo) {
        return accountAdjustVoucherDao.findVoucherBy(system, systemFlowId, bussinessCode, accountNo);
    }

    /*
     * (non-Javadoc)
     * @see com.yl.account.core.service.AccountAdjustVoucherService#modify(com.yl.account.model.AccountAdjustVoucher)
     */
    @Override
    public void modify(AccountAdjustVoucher accountAdjustVoucher) {
        accountAdjustVoucherDao.modify(accountAdjustVoucher, System.currentTimeMillis());
    }

    @Override
    public String create(String system, String bussinessCode, String accountNo, AccountType accountType, String userNo, UserRole userRole, AdjustStatus status,
                         FundSymbol fundSymbol, double amount, Integer expireTime, ExpireOperate expireOperate, String flowId, String freezeNo, String operator, String reason,
                         String remark, HandleStatus handleStatus, String processInstanceId, String transOrder, NoticeStatus noticeStatus) {
        String code = CodeBuilder.build("ADJ", "yyMMdd");

        AccountAdjustVoucher accountAdjustVoucher = new AccountAdjustVoucher();
        accountAdjustVoucher.setAccountNo(accountNo);
        accountAdjustVoucher.setAccountType(accountType);
        accountAdjustVoucher.setAmount(amount);
        accountAdjustVoucher.setBussinessCode(bussinessCode);
        accountAdjustVoucher.setCode(code);
        accountAdjustVoucher.setExpireOperate(expireOperate);
        accountAdjustVoucher.setExpireTime(expireTime);
        accountAdjustVoucher.setFlowId(flowId);
        accountAdjustVoucher.setFreezeNo(freezeNo);
        accountAdjustVoucher.setFundSymbol(fundSymbol);
        accountAdjustVoucher.setOperator(operator);
        accountAdjustVoucher.setReason(reason);
        accountAdjustVoucher.setRemark(remark);
        accountAdjustVoucher.setStatus(status);
        accountAdjustVoucher.setSystem(system);
        accountAdjustVoucher.setUserNo(userNo);
        accountAdjustVoucher.setUserRole(userRole);
        accountAdjustVoucher.setHandleStatus(handleStatus);
        accountAdjustVoucher.setNoticeStatus(NoticeStatus.NO);
        accountAdjustVoucher.setProcessInstanceId(processInstanceId);
        accountAdjustVoucher.setTransOrder(transOrder);
        accountAdjustVoucher.setNoticeStatus(noticeStatus);
        accountAdjustVoucherDao.insert(accountAdjustVoucher);
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            AccountAdjustVoucher find = accountAdjustVoucherDao.findByCode(code);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            map.put("system", find.getSystem());
            map.put("bussinessCode", find.getBussinessCode());
            map.put("accountNo", find.getAccountNo());
            map.put("accountType", find.getAccountType());
            map.put("userNo", find.getUserNo());
            map.put("userRole", find.getUserRole());
            map.put("transOrder", find.getTransOrder());
            map.put("status", find.getStatus());
            map.put("fundSymbol", find.getFundSymbol());
            map.put("amount", find.getAmount());
            map.put("unFreezeAmount", find.getUnFreezeAmount());
            map.put("expireTime", find.getExpireTime());
            map.put("expireOperate", find.getExpireOperate());
            map.put("flowId", find.getFlowId());
            map.put("freezeNo", find.getFreezeNo());
            map.put("handleStatus", find.getHandleStatus());
            map.put("noticeStatus", find.getNoticeStatus());
            map.put("processInstanceId", find.getProcessInstanceId());
            map.put("operator", find.getOperator());
            map.put("reason", find.getReason());
            map.put("lastModifyTime", df.format(find.getLastModifyTime()));
            map.put("remark", find.getRemark());
            producerClient.sendMessage(new ProducerMessage("yl-data-topic", "account_accountadjustvoucher_tag", JsonUtils.toJsonString(map).getBytes()));
            logger.info("推送主题 ：yl-data-topic,标签：调账记录  成功，商户编号{}，消息体{}", find.getAccountNo(), map);
        } catch (Exception e) {
            logger.error("推送调账记录到数据中心失败,原因{},code{}", e, map.get("code"));
        }
        return code;
    }

    @Override
    public void modifyHandleStatusAndAdjustStatus(AccountAdjustVoucher accountAdjustVoucher) {
        accountAdjustVoucherDao.modifyHandleStatusAndAdjustStatus(accountAdjustVoucher, System.currentTimeMillis());
    }

    @Override
    public AccountAdjustVoucher findByCode(String code) {
        return accountAdjustVoucherDao.findByCode(code);
    }

    @Override
    public void modifyVoucher(AccountAdjustVoucher accountAdjustVoucher) {
        accountAdjustVoucherDao.modifyVoucher(accountAdjustVoucher, System.currentTimeMillis());
    }

    @Override
    public List<AccountAdjustVoucher> findAllAdjHistory(String accountNo, Page<?> page) {
        return accountAdjustVoucherDao.findAllAdjHistory(accountNo, page);
    }

}

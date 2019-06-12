package com.yl.dpay.core.service.impl;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.lang.DateFormatUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.dpay.core.Constant;
import com.yl.dpay.core.Utils.HolidayUtils;
import com.yl.dpay.core.dao.DpayConfigDao;
import com.yl.dpay.core.dao.DpayConfigHistoryDao;
import com.yl.dpay.core.dao.RequestDao;
import com.yl.dpay.core.dao.ServiceConfigDao;
import com.yl.dpay.core.entity.DpayConfig;
import com.yl.dpay.core.entity.DpayConfigHistory;
import com.yl.dpay.core.entity.Request;
import com.yl.dpay.core.entity.ServiceConfig;
import com.yl.dpay.core.enums.AuditStatus;
import com.yl.dpay.core.enums.FireType;
import com.yl.dpay.core.enums.Status;
import com.yl.dpay.core.service.DpayConfigService;
import com.yl.dpay.hessian.beans.DpayRuntimeException;
import com.yl.dpay.hessian.enums.DpayExceptionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 代付出款配置服务接口实现
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年5月13日
 */
@Service
public class DpayConfigServiceImpl implements DpayConfigService {

    private static Logger logger = LoggerFactory.getLogger(DpayConfigServiceImpl.class);

    @Resource
    private DpayConfigDao dpayConfigDao;

    @Resource
    private DpayConfigHistoryDao dpayConfigHistoryDao;

    @Resource
    private RequestDao requestDao;

    @Resource
    private ServiceConfigDao serviceConfigDao;

    @Override
    public void insert(DpayConfig dpayConfig, String oper) {
        dpayConfig.setCreateDate(new Date());
        dpayConfigDao.insert(dpayConfig);

        dpayConfigHistoryDao.insert(new DpayConfigHistory(dpayConfig, oper));
    }

    @Override
    public DpayConfig findById(Long id) {
        return dpayConfigDao.findById(id);
    }

    @Override
    public void update(DpayConfig dpayConfig, String oper) {
        if (dpayConfig.getId() == null) {
            dpayConfig.setCreateDate(new Date());
            dpayConfigDao.insert(dpayConfig);
            dpayConfigHistoryDao.insert(new DpayConfigHistory(dpayConfig, oper));
        }
        DpayConfig dc = dpayConfigDao.findById(dpayConfig.getId());
        if (dc != null) {
            dc.setStatus(dpayConfig.getStatus());
            dc.setAuditAmount(dpayConfig.getAuditAmount());
            dc.setHolidayStatus(dpayConfig.getHolidayStatus());
            dc.setRemitType(dpayConfig.getRemitType());
            dc.setEndTime(dpayConfig.getEndTime());
            dc.setStartTime(dpayConfig.getStartTime());
            dc.setMaxAmount(dpayConfig.getMaxAmount());
            dc.setMinAmount(dpayConfig.getMinAmount());
            dc.setReRemitFlag(dpayConfig.getReRemitFlag());
            dpayConfigDao.update(dc);

            dpayConfigHistoryDao.insert(new DpayConfigHistory(dc, oper));
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void checkStart(Request request) {

        ServiceConfig serviceConfig = serviceConfigDao.find(request.getOwnerId(), "TRUE");
        if (serviceConfig == null) {
            throw new DpayRuntimeException(DpayExceptionEnum.NOTOPEN.getCode(), DpayExceptionEnum.NOTOPEN.getMessage());
        }

        // 代付时间
        Date now = new Date();

        if (serviceConfig.getFireType() == FireType.MAN) {
            // 金额大于用户最大付款限额
            if (serviceConfig.getMaxAmount() > 0 && request.getAmount() > serviceConfig.getMaxAmount()) {
                throw new DpayRuntimeException(DpayExceptionEnum.OUT_USER_MAX.getCode(), DpayExceptionEnum.OUT_USER_MAX.getMessage());
            }

            // 金额小于用户最小付款限额
            if (serviceConfig.getMinAmount() > 0 && request.getAmount() < serviceConfig.getMinAmount()) {
                throw new DpayRuntimeException(DpayExceptionEnum.UNDER_USER_MIN.getCode(), DpayExceptionEnum.UNDER_USER_MIN.getMessage());
            }

            if (Integer.parseInt((now.getHours() + "" + (now.getMinutes() < 10 ? "0" + now.getMinutes() : now.getMinutes()))) < Integer.parseInt(serviceConfig.getStartTime().replace(":", "")) ||
                    Integer.parseInt((now.getHours() + "" + (now.getMinutes() < 10 ? "0" + now.getMinutes() : now.getMinutes()))) >= Integer.parseInt(serviceConfig.getEndTime().replace(":", ""))) {
                throw new DpayRuntimeException(DpayExceptionEnum.NO_REMIT_TIME.getCode(), DpayExceptionEnum.NO_REMIT_TIME.getMessage());
            }

            List<Map<String, String>> list = requestDao.orderAllSumByDay(DateFormatUtils.format(new Date(), "yyyy-MM-dd") + " 00:00:00", DateFormatUtils.format(new Date(), "yyyy-MM-dd") + " 23:59:59", request.getOwnerId());
            if (list.size() > 0) {
                //日最大交易限额
                if (serviceConfig.getDayMaxAmount() < (Double) (Object) list.get(0).get("amount") + request.getAmount()) {
                    throw new DpayRuntimeException(DpayExceptionEnum.OUT_USER_DAY_MAX.getCode(), DpayExceptionEnum.OUT_USER_DAY_MAX.getMessage());
                }
            }
        }


        DpayConfig dc = dpayConfigDao.findDpayConfig();

        if (dc == null) {
            return;
        }

        // 付款状态
        if (dc.getStatus() == Status.FALSE) {
            throw new DpayRuntimeException(DpayExceptionEnum.NO_REMIT_TIME.getCode(), DpayExceptionEnum.NO_REMIT_TIME.getMessage());
        }

        // 假日付
        if (HolidayUtils.isHoliday()) {
            if (dc.getHolidayStatus() == Status.FALSE) {
                throw new DpayRuntimeException(DpayExceptionEnum.SYS_HOLIDAY_CLOSE.getCode(), DpayExceptionEnum.SYS_HOLIDAY_CLOSE.getMessage());
            }
        }

        if (serviceConfig.getFireType() == FireType.MAN) {
            if (Integer.parseInt((now.getHours() + "" + (now.getMinutes() < 10 ? "0" + now.getMinutes() : now.getMinutes()))) < Integer.parseInt(dc.getStartTime().replace(":", "")) ||
                    Integer.parseInt((now.getHours() + "" + (now.getMinutes() < 10 ? "0" + now.getMinutes() : now.getMinutes()))) >= Integer.parseInt(dc.getEndTime().replace(":", ""))) {
                throw new DpayRuntimeException(DpayExceptionEnum.NO_REMIT_TIME.getCode(), DpayExceptionEnum.NO_REMIT_TIME.getMessage());
            }

            // 金额大于最大付款金额
            if (dc.getMaxAmount() > 0 && request.getAmount() > dc.getMaxAmount()) {
                throw new DpayRuntimeException(DpayExceptionEnum.OUT_SYS_MAX.getCode(), DpayExceptionEnum.OUT_SYS_MAX.getMessage());
            }

            // 金额小于最小付款金额
            if (dc.getMinAmount() > 0 && request.getAmount() < dc.getMinAmount()) {
                throw new DpayRuntimeException(DpayExceptionEnum.UNDER_SYS_MIN.getCode(), DpayExceptionEnum.UNDER_SYS_MIN.getMessage());
            }
        }

        // 单日内收款人金额重复
        if (dc.getReRemitFlag() == Status.FALSE) {
            checkAccAmt(request);
        }
    }

    private synchronized void checkAccAmt(Request request){
        String lock = CacheUtils.get(Constant.LockPrefix + request.getAccountName() + request.getAccountNo() + request.getAmount(), String.class);
        if (StringUtils.isBlank(lock)) {
            logger.info("账户、金额：" + request.getAccountName() + request.getAccountNo() + request.getAmount() + "，商户订单号：" + request.getRequestNo() + " 加锁 ");
            CacheUtils.setEx(Constant.LockPrefix + request.getAccountName() + request.getAccountNo() + request.getAmount(), "lock", 60);
            if (requestDao.findByAccNameAmtDay(request.getAccountName(), request.getAccountNo(), request.getAmount(),
                    DateFormatUtils.format(new Date(), "yyyy-MM-dd") + " 00:00:00", DateFormatUtils.format(new Date(), "yyyy-MM-dd") + " 23:59:59") != null) {
                throw new DpayRuntimeException(DpayExceptionEnum.DAY_PAYER_REPEAT.getCode(), DpayExceptionEnum.DAY_PAYER_REPEAT.getMessage());
            }
        } else {
            throw new DpayRuntimeException(DpayExceptionEnum.DAY_PAYER_REPEAT.getCode(), DpayExceptionEnum.DAY_PAYER_REPEAT.getMessage());
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void checkAudit(Request request) {
        DpayConfig dc = dpayConfigDao.findDpayConfig();

        if (dc == null) {
            return;
        }

        // 付款状态
        if (dc.getStatus() == Status.FALSE) {
            throw new DpayRuntimeException(DpayExceptionEnum.NO_REMIT_TIME.getCode(), DpayExceptionEnum.NO_REMIT_TIME.getMessage());
        }

        ServiceConfig serviceConfig = serviceConfigDao.find(request.getOwnerId(), "TRUE");
        if (request.getAuditStatus() != AuditStatus.WAIT && serviceConfig.getFireType() != FireType.AUTO) {
            // 代付时间
            Date now = new Date();
            if (Integer.parseInt((now.getHours() + "" + (now.getMinutes() < 10 ? "0" + now.getMinutes() : now.getMinutes()))) < Integer.parseInt(dc.getStartTime().replace(":", "")) ||
                    Integer.parseInt((now.getHours() + "" + (now.getMinutes() < 10 ? "0" + now.getMinutes() : now.getMinutes()))) >= Integer.parseInt(dc.getEndTime().replace(":", ""))) {
                throw new DpayRuntimeException(DpayExceptionEnum.NO_REMIT_TIME.getCode(), DpayExceptionEnum.NO_REMIT_TIME.getMessage());
            }
        }

        // 假日付
        if (HolidayUtils.isHoliday()) {
            if (dc.getHolidayStatus() == Status.FALSE) {
                throw new DpayRuntimeException(DpayExceptionEnum.SYS_HOLIDAY_CLOSE.getCode(), DpayExceptionEnum.SYS_HOLIDAY_CLOSE.getMessage());
            }
        }

        // 单日内收款人金额重复
        /*if(dc.getReRemitFlag() == Status.FALSE){
			if(requestDao.findByAccNameAmtDay(request.getAccountName(), request.getAccountNo(), request.getAmount(), 
					DateFormatUtils.format(new Date(), "yyyy-MM-dd")+" 00:00:00", DateFormatUtils.format(new Date(), "yyyy-MM-dd")+" 23:59:59") != null){
				throw new DpayRuntimeException(DpayExceptionEnum.DAY_PAYER_REPEAT.getCode(), DpayExceptionEnum.DAY_PAYER_REPEAT.getMessage());
			}
		}*/
    }

    @Override
    public DpayConfig findDpayConfig() {
        return dpayConfigDao.findDpayConfig();
    }

}

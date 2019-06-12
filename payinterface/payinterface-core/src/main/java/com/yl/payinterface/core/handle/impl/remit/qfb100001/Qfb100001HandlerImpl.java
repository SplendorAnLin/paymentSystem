package com.yl.payinterface.core.handle.impl.remit.qfb100001;

import com.yl.payinterface.core.bean.Cnaps;
import com.yl.payinterface.core.bean.Recognition;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.handler.RemitHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代付挡板
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/8/28
 */
@Service("qfb100001Handler")
public class Qfb100001HandlerImpl implements RemitHandler {

    private static final Logger logger = LoggerFactory.getLogger(Qfb100001HandlerImpl.class);

    @Resource
    private InterfaceRequestService interfaceRequestService;

    @Override
    public Map<String, String> remit(Map<String, String> map) {
        Map<String, String> resMap = new HashMap<>();
        String accountNo = map.get("accountNo");
        String cnapsCode = "";
        Recognition recognition = CommonUtils.recognition(accountNo);
        List<Cnaps> cnaps = CommonUtils.cnapsCode(recognition.getProviderCode());
        if (cnaps != null && cnaps.size() > 0) {
            cnapsCode = cnaps.get(0).getClearingBankCode();
        }
        if (StringUtils.isBlank(cnapsCode)) {
            resMap.put("resCode", "9999");
            resMap.put("resMsg", "该银行卡不支持");
            resMap.put("tranStat", "FAILED");
            resMap.put("requestNo", map.get("requestNo"));
            resMap.put("amount", map.get("amount"));
            resMap.put("compType", BusinessCompleteType.NORMAL.name());
        } else {
            resMap.put("tranStat", "UNKNOWN");
            resMap.put("compType", BusinessCompleteType.NORMAL.name());
            resMap.put("amount", map.get("amount"));
            resMap.put("requestNo", map.get("requestNo"));
            resMap.put("resCode", "0000");
            resMap.put("resMsg", "已提交银行");
        }
        logger.info("代付挡板支付信息：{}", resMap);
        return resMap;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        String requestNo = map.get("requestNo");
        InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(requestNo);
        Map<String, String> respMap = new HashMap<>();
        respMap.put("resCode", "0000");
        respMap.put("resMsg", "付款成功");
        respMap.put("tranStat", "SUCCESS");
        respMap.put("requestNo", requestNo);
        respMap.put("amount", String.valueOf(interfaceRequest.getAmount()));
        respMap.put("compType", BusinessCompleteType.AUTO_REPAIR.name());
        respMap.put("interfaceOrderID", requestNo);
        logger.info("代付挡板完成代付信息：{}", respMap);
        return respMap;
    }
}
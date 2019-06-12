package com.yl.payinterface.core.utils;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.lang.AmountUtils;
import com.yl.payinterface.core.model.AliPayCollectionCode;
import com.yl.payinterface.core.service.AliPayCollectionCodeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.List;

/**
 * 个人码 资源锁
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/8/15
 */
@Component
public class CollectionCodeLock {

    private static final Logger logger = LoggerFactory.getLogger(CollectionCodeLock.class);

    @Resource
    private AliPayCollectionCodeService aliPayCollectionCodeService;

    /**
     * 获取收款码 支持容错
     * @param amount
     * @return
     */
    public synchronized AliPayCollectionCode getCode(String amount){
        try {
            AliPayCollectionCode aliPayCollectionCode = getAliPayCollectionCode(amount);
            if (aliPayCollectionCode != null) {
                return aliPayCollectionCode;
            }
        } catch (Exception e) {
            logger.error("个人码获取异常!异常信息：{}", e);
        }
        return null;
    }

    /**
     * 根据金额获取收款码  并加锁
     * @param amount
     * @return
     */
    private AliPayCollectionCode getAliPayCollectionCode(String amount) {
        try {
            AliPayCollectionCode aliPayCollectionCode;
            String lock;
            List<AliPayCollectionCode> aliPayCollectionCodes = aliPayCollectionCodeService.queryCode(amount);
            if (aliPayCollectionCodes != null && aliPayCollectionCodes.size() > 0) {
                for (int i = 0; i < aliPayCollectionCodes.size(); i++) {
                    aliPayCollectionCode = aliPayCollectionCodes.get(i);
                    lock = CacheUtils.get("ALIPAYCODELOCK:" + aliPayCollectionCode.getId(), String.class);
                    if (StringUtils.isBlank(lock)) {
                        CacheUtils.setEx("ALIPAYCODELOCK:" + aliPayCollectionCode.getId(), "lock", 610);
                        return aliPayCollectionCode;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("收款码资源获取异常!异常信息：{}", e);
        }
        return null;
    }
}
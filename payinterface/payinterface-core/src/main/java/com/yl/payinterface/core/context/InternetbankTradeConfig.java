package com.yl.payinterface.core.context;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.yl.payinterface.core.handler.InternetbankHandler;

/**
 * 网银支付相关配置
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年11月11日
 */
@Configuration
public class InternetbankTradeConfig {

    @Resource
    private InternetbankHandler b2cHfb100002Handler;

    @Resource
    private InternetbankHandler b2cXkl350102Handler;

    @Resource
    private InternetbankHandler b2cAl100001Handler;

    @Resource
    private InternetbankHandler b2cKlt100005Handler;

    @Resource
    private InternetbankHandler b2cYsb100001Handler;

    @Resource
    private InternetbankHandler b2cShand100001Handler;

    @Resource
    private InternetbankHandler b2cYw440301Handler;

    @Resource
    private InternetbankHandler b2cHee100001Handler;

    @Bean
    public Map<String, InternetbankHandler> internetbankHandlers() {
        Map<String, InternetbankHandler> tradeHandlers = new HashMap<>();
        tradeHandlers.put("HFB_100002-B2C", b2cHfb100002Handler);
        tradeHandlers.put("XKL_350102-B2C", b2cXkl350102Handler);
        tradeHandlers.put("AL_100001-B2C", b2cAl100001Handler);
        tradeHandlers.put("B2C_KLT-100005", b2cKlt100005Handler);
        tradeHandlers.put("B2C_KLT-100006", b2cKlt100005Handler);
        tradeHandlers.put("YSB_100001-B2C", b2cYsb100001Handler);
        tradeHandlers.put("SD_100001-B2C", b2cShand100001Handler);
        tradeHandlers.put("YW_440301-B2C", b2cYw440301Handler);
        tradeHandlers.put("HEE_100001-B2C", b2cHee100001Handler);
        return tradeHandlers;
    }
}
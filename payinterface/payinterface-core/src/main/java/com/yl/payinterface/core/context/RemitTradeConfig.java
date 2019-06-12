package com.yl.payinterface.core.context;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.yl.payinterface.core.handler.RemitHandler;

/**
 * 支付接口-付款相关配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月11日
 * @version V1.0.0
 */
@Configuration
public class RemitTradeConfig {
	
	@Resource
	private RemitHandler remitYiLian584001Handler;

	@Resource
    private RemitHandler remitHfb100004Handler;

	@Resource
    private RemitHandler remitLuck100002Handler;

	@Resource
    private RemitHandler remitHfb100005Handler;

	@Resource
    private RemitHandler remitXkl350103HandlerImpl;

	@Resource
    private RemitHandler hq350002Handler;

	@Resource
    private RemitHandler remitKlt100004Handler;

	@Resource
    private RemitHandler guoMei100001Handler;

	@Resource
    private RemitHandler remitYsb100002Handler;

	@Resource
    private RemitHandler shandHandler100001;

	@Resource
    private RemitHandler kingPassHandler100001;

	@Resource
    private RemitHandler hs100001Handler;

	@Resource
    private RemitHandler remitYzf440102Handler;

	@Resource
    private RemitHandler remitYw440301Handler;

	@Resource
    private RemitHandler zc100001Handler;

	@Resource
    private RemitHandler remitFt320001Handler;

    @Resource
    private RemitHandler remitYyg100002Handler;

    @Resource
    private RemitHandler qfb100001Handler;

    @Resource
    private RemitHandler ht440301Handler;

	@Bean
	public Map<String, RemitHandler> remitHandlers() {
		Map<String, RemitHandler> tradeHandlers = new HashMap<>();
		tradeHandlers.put("YILIAN_584001_REMIT", remitYiLian584001Handler);
		tradeHandlers.put("HFB_100004_REMIT", remitHfb100004Handler);
		tradeHandlers.put("LUCK_100002_REMIT", remitLuck100002Handler);
        tradeHandlers.put("HFB_100005_REMIT", remitHfb100005Handler);
        tradeHandlers.put("XKL_350103_REMIT", remitXkl350103HandlerImpl);
        tradeHandlers.put("XKL_350104_REMIT", remitXkl350103HandlerImpl);
        tradeHandlers.put("HQ_350002_REMIT", hq350002Handler);
        tradeHandlers.put("KLT_100004_REMIT", remitKlt100004Handler);
        tradeHandlers.put("GM_100001_REMIT", guoMei100001Handler);
        tradeHandlers.put("YSB_100002_REMIT", remitYsb100002Handler);
        tradeHandlers.put("SHAND_100001_REMIT", shandHandler100001);
        tradeHandlers.put("KingPass_100001_REMIT", kingPassHandler100001);
        tradeHandlers.put("HS_100001_REMIT", hs100001Handler);
        tradeHandlers.put("YZF_440102_REMIT", remitYzf440102Handler);
        tradeHandlers.put("YW_440301_REMIT", remitYw440301Handler);
        tradeHandlers.put("ZC_100001_REMIT", zc100001Handler);
        tradeHandlers.put("FT_320001_REMIT", remitFt320001Handler);
        tradeHandlers.put("YYG_100002_REMIT", remitYyg100002Handler);
        tradeHandlers.put("QFB_100001_REMIT", qfb100001Handler);
        tradeHandlers.put("HT_440301_REMIT", ht440301Handler);
		return tradeHandlers;
	}
}
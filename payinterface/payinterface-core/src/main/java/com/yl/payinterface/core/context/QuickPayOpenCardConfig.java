package com.yl.payinterface.core.context;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.yl.payinterface.core.handler.QuickPayOpenCardHandler;

/** 
 * @ClassName QuickPayOpenCardConfig 
 * @Description TODO(这里用一句话描述这个类的作用) 
 * @author Administrator 
 * @date 2017年11月20日 下午8:39:51  
 */
@Configuration
public class QuickPayOpenCardConfig {

	@Resource
	private QuickPayOpenCardHandler quickUnionPay120001Handler;

	@Resource
	private QuickPayOpenCardHandler quickUnionPay120002Handler;

	@Resource
	private QuickPayOpenCardHandler quickYinSheng584001Handler;

	@Resource
	private QuickPayOpenCardHandler quickPengRui310102Handler;

	@Resource
	private QuickPayOpenCardHandler quickLDZ42010103Handler;

	@Resource
	private QuickPayOpenCardHandler quickChcodepay420101Handler;

	@Resource
    private QuickPayOpenCardHandler quickKingPass100001Handler;

	@Bean
	public Map<String, QuickPayOpenCardHandler> quickPayOpenCardHandlers() {
		Map<String, QuickPayOpenCardHandler> tradeHandlers = new HashMap<>();
		tradeHandlers.put("QUICKPAY_UnionPay-120001", quickUnionPay120001Handler);
		tradeHandlers.put("QUICKPAY_UnionPay-120002", quickUnionPay120002Handler);
		tradeHandlers.put("QUICKPAY_YinSheng-584001", quickYinSheng584001Handler);
		tradeHandlers.put("QUICKPAY_PengRui-310102", quickPengRui310102Handler);
		tradeHandlers.put("QUICKPAY_LDZ-42010103", quickLDZ42010103Handler);
		tradeHandlers.put("QUICKPAY_ChcodePay-420101", quickChcodepay420101Handler);
		tradeHandlers.put("QUICKPAY_KINGPASS-100001", quickKingPass100001Handler);
		return tradeHandlers;
	}
}
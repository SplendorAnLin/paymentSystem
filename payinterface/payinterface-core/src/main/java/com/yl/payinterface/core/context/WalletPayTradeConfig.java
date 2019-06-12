package com.yl.payinterface.core.context;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.yl.payinterface.core.handler.WalletPayHandler;

/**
 * 钱包支付交易配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月11日
 * @version V1.0.0
 */
@Configuration
public class WalletPayTradeConfig {

	@Resource
	private WalletPayHandler walletYiLian584001Handler;

	@Resource
    private WalletPayHandler walletLucky100001Handler;

	@Resource
    private WalletPayHandler hfb100001Handler;

	@Resource
    private WalletPayHandler hfb100003Handler;

	@Resource
    private WalletPayHandler hfb100006Handler;

	@Resource
    private WalletPayHandler klt100003Handler;

	@Resource
    private WalletPayHandler walletXkl350101Handler;

	@Resource
    private WalletPayHandler walletYiLian584002Handler;

	@Resource
    private WalletPayHandler hQ350001Handler;

	@Resource
    private WalletPayHandler walletXkl350102Handler;

	@Resource
    private WalletPayHandler yunJian410701Handler;

	@Resource
    private WalletPayHandler kingPass100001WalletHandler;

	@Resource
    private WalletPayHandler zft100001Handler;

	@Resource
    private WalletPayHandler walletXkl350104Handler;

	@Resource
    private WalletPayHandler walletHs100001Handler;

	@Resource
    private WalletPayHandler yzf440101Handler;

	@Resource
    private WalletPayHandler ZC100001Handler;

	@Resource
    private WalletPayHandler yyg100001Handler;

    @Resource
    private WalletPayHandler ghf100001Handler;

    @Resource
    private WalletPayHandler ft320000Handler;

    @Resource
    private WalletPayHandler Zh100001Handler;

    @Resource
    private WalletPayHandler hl100001Handler;

    @Resource
    private WalletPayHandler cc100001Handler;

    @Resource
    private WalletPayHandler walletShand100001Handler;

	@Bean
	public Map<String, WalletPayHandler> walletPayHandlers() {
		Map<String, WalletPayHandler> tradeHandlers = new HashMap<>();
		tradeHandlers.put("YILIAN584001-QQ_NATIVE", walletYiLian584002Handler);
		tradeHandlers.put("YILIAN584001-QQ_H5", walletYiLian584002Handler);
		tradeHandlers.put("YILIAN584001-UNIONPAY_NATIVE", walletYiLian584002Handler);
        tradeHandlers.put("YILIAN584001-JD_NATIVE", walletYiLian584002Handler);
		tradeHandlers.put("LUCKY100001-JD_H5", walletLucky100001Handler);
		tradeHandlers.put("HFB100001-QQ_SCAN", hfb100001Handler);
		tradeHandlers.put("HFB100003-UNIONPAY_NATIVE", hfb100003Handler);
		tradeHandlers.put("HFB100006-QQ_H5", hfb100006Handler);
        tradeHandlers.put("KLT100003-UNIONPAY_NATIVE", klt100003Handler);
        tradeHandlers.put("XKL350101-QQ_NATIVE", walletXkl350101Handler);
        tradeHandlers.put("HQ350001-ALIPAY_H5", hQ350001Handler);
        tradeHandlers.put("XKL350102-ALIPAY_H5", walletXkl350102Handler);
        tradeHandlers.put("YJ410701-ALIPAY_H5", yunJian410701Handler);
        tradeHandlers.put("KINGPASS100001-UNIONPAY_NATIVE", kingPass100001WalletHandler);
        tradeHandlers.put("ZTF100001-JD_H5", zft100001Handler);
        tradeHandlers.put("XKL350104-ALIPAYJSAPI", walletXkl350104Handler);
        tradeHandlers.put("HS100001-QQ_NATIVE", walletHs100001Handler);
        tradeHandlers.put("HS100002-JD_H5", walletHs100001Handler);
        tradeHandlers.put("YZF440101-ALIPAY_H5", yzf440101Handler);
        tradeHandlers.put("ZF100001-ALIPAY_H5", ZC100001Handler);
        tradeHandlers.put("ZF100002-ALIPAY_H5", ZC100001Handler);
        tradeHandlers.put("YYG100001-ALIPAY_H5", yyg100001Handler);
        tradeHandlers.put("YYG100002-WXNATIVE", yyg100001Handler);
        tradeHandlers.put("GHF100001-ALIPAY_H5", ghf100001Handler);
        tradeHandlers.put("FT320000-ALIPAY_H5", ft320000Handler);
        tradeHandlers.put("ZH100002-WXNATIVE", Zh100001Handler);
        tradeHandlers.put("HL100001-ALIPAY_H5", hl100001Handler);
        tradeHandlers.put("CC100001-ALIPAY_H5", cc100001Handler);
        tradeHandlers.put("SD100001-UNIONPAY_NATIVE", walletShand100001Handler);
        return tradeHandlers;
	}
}
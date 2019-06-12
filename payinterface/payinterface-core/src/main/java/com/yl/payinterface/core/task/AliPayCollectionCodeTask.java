package com.yl.payinterface.core.task;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.lang.DateUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.yl.payinterface.core.bean.AliPayRecon;
import com.yl.payinterface.core.model.AliPayReconAcc;
import com.yl.payinterface.core.service.AliPayReconAccService;
import com.yl.payinterface.core.utils.AliPayAESUtils;
import com.yl.payinterface.core.utils.HttpClientUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 支付宝对账中心
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/8/18
 */
public class AliPayCollectionCodeTask {

	private static final Logger logger = LoggerFactory.getLogger(AliPayCollectionCodeTask.class);

	private static final String url = "http://nginx/payinterface-front/collectionCodeNotify/complete";

	private static final String key = "378747339B23F104AC1B2F029A0CFE9A";

    private static Executor executor = Executors.newFixedThreadPool(10);

	@Resource
	private AliPayReconAccService aliPayReconAccService;

	public void execute() {
	    logger.info("--------      收款信息获取开始      --------");
		List<AliPayReconAcc> aliPayReconAccs = aliPayReconAccService.findByTask();
		AliPayReconAcc aliPayReconAcc;
        AliPayRecon aliPayRecon;
		for (int index = 0; index < aliPayReconAccs.size(); index++) {
            aliPayRecon = new AliPayRecon();
            aliPayReconAcc = aliPayReconAccs.get(index);
            logger.info("当前进程同步账号为：{}", aliPayReconAcc.getUserName());
            aliPayRecon.setBillUserId(aliPayReconAcc.getBillUserId());
            aliPayRecon.setCookies(aliPayReconAcc.getCookies());
            aliPayRecon.setPageNum("1");
            aliPayRecon.setPartnerNo(aliPayReconAcc.getUserName());
            aliPayRecon.setToken(aliPayReconAcc.getToken());
            MerchantReverseTask task = new MerchantReverseTask();
            task.setAliPayRecon(aliPayRecon);
            executor.execute(task);
        }
        logger.info("--------      收款信息获取结束      --------");
	}



    private class MerchantReverseTask implements Runnable {

        /** 私有数据 */
        private AliPayRecon aliPayRecon;

        @Override
        public void run() {
            recon(aliPayRecon.getBillUserId(), aliPayRecon.getToken(), aliPayRecon.getCookies(), aliPayRecon.getPartnerNo(), aliPayRecon.getPageNum());
        }

        public void setAliPayRecon(AliPayRecon aliPayRecon) {
            this.aliPayRecon = aliPayRecon;
        }
    }


	private void recon(String billUserId, String token, String cookies, String partnerNo, String pageNum) {
		Map<String, String> params = new HashMap<>();
		Map<String, String> header = new HashMap<>();
		params.put("billUserId", billUserId);
		params.put("ctoken", token);
		params.put("queryEntrance", "1");
		params.put("status", "ALL");
		params.put("entityFilterType", "0");
		params.put("precisionValue", "");
		params.put("activeTargetSearchItem", "tradeNo");
		params.put("tradeFrom", "ALL");
		params.put("pageSize", "50");
		params.put("pageNum", pageNum);
		params.put("sortTarget", "gmtCreate");
		params.put("order", "descend");
		params.put("sortType", "0");
		params.put("_input_charset", "gbk");
		header.put("Cookies", cookies);
		header.put("Host", "mbillexprod.alipay.com");
		header.put("Origin", "https://mbillexprod.alipay.com");
		header.put("Referer", "https://mbillexprod.alipay.com/enterprise/tradeListQuery.htm");
		header.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
		header.put("X-Requested-With", "XMLHttpRequest");
		params.put("startTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateUtils.addMinutes(new Date(), -11)));
		params.put("endTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateUtils.addMinutes(new Date(), 1)));
		String result = HttpClientUtil.doPost("https://mbillexprod.alipay.com/enterprise/tradeListQuery.json", params, header, "UTF-8");
		JSONObject response = JSONObject.fromObject(result);
		if (response.containsKey("target")){
		    try {
                aliPayReconAccService.updateStatus(partnerNo, "FALSE");
                sendDown(partnerNo);
            } catch (Exception e) {
                logger.error("掉线处理异常!异常信息：{}", e);
            }
			return;
		}
		JSONArray details = response.getJSONObject("result").getJSONArray("detail");
		JSONObject orderInfo;
		Map<String, String> req;
		try {
			for (int i = 0; i < details.size(); i++) {
				orderInfo = details.getJSONObject(i);
				if (!"成功".equals(orderInfo.getString("tradeStatus"))) {
					continue;
				}
				String lock = CacheUtils.get("ALIPAYORDER:" + orderInfo.getString("tradeNo"), String.class);
				if (StringUtils.isBlank(lock)) {
					req = new HashMap<>();
					req.put("totalAmount", orderInfo.getString("totalAmount"));
					req.put("goodsTitle", orderInfo.getString("goodsTitle"));
					req.put("tradeNo", orderInfo.getString("tradeNo"));
					Map<String, String> reqPar = AliPayAESUtils.encryption(key, JsonUtils.toJsonString(req), partnerNo);
					result = HttpClientUtils.send(HttpClientUtils.Method.POST, url, reqPar, true, "UTF-8", 300000);
					if ("SUCCESS".equals(result) || "FAILURE".equals(result)) {
						CacheUtils.setEx("ALIPAYORDER:" + orderInfo.getString("tradeNo"), "LOCK", 910);
					}
					logger.info("通知结果：【{}】成功订单：【{}】", result, orderInfo.toString());
				}
			}
			if ("1".equals(pageNum)) {
				JSONObject paging = response.getJSONObject("result").getJSONObject("paging");
				int totalPage = getTotalPage(paging.getInt("sizePerPage"), paging.getInt("totalItems"));
				if (totalPage > 1) {
					for (int i = 2; i <= totalPage; i++) {
						recon(billUserId, token, cookies, partnerNo, String.valueOf(i));
					}
				}
			}
		} catch (Exception e) {
			logger.error("异常!异常信息:{}", e);
		}
	}

    private void sendDown(String partnerNo) {
	    try {
            qdSendSms( "【千付宝】支付账号已掉线：[" + partnerNo + "]。已停止该收款码，请及时处理。", "17607114151");
            logger.info("【千付宝】支付账号已掉线：[" + partnerNo + "]。已停止该收款码，请及时处理。");
            String res = HttpClientUtils.get("http://nginx/payinterface-front/collectionCodeNotify/updateStatus?aliPayAcc=" + partnerNo + "&status=NO");
            if (!"SUCCESS".equals(res)){
                sendDown(partnerNo);
            }
        } catch (Exception e) {
            logger.error("收款码下线发送异常!异常信息：{}", e);
            sendDown(partnerNo);
        }
    }

    public static int getTotalPage(int sizePerPage, int totalItems) {
		if (totalItems % sizePerPage == 0) {
			return totalItems / sizePerPage;
		} else {
			return totalItems / sizePerPage + 1;
		}
	}

    public static void main(String[] args) {
        qdSendSms( "【千付宝】支付账号已掉线：[" + "666@qq.com" + "]。已停止该收款码，请及时处理。", "17607114151");
    }

	public static void qdSendSms(String text, String mobile){
		Map<String, String> params = new HashMap<>();
		params.put("Uname", "bf23456");
		params.put("Upass", "44ab2afde1db696687709d9546baec02");
		params.put("Mobile", mobile);
		params.put("Content", text);
		try {
            HttpClientUtils.send(HttpClientUtils.Method.POST, "http://open.96xun.com/Api/SendSms", params);
        } catch (Exception e) {
            logger.error("异常!异常信息：{}", e);
        }
	}
}
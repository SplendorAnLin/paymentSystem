package com.yl.payinterface.core.handle.impl.wallet.yyg100001;

import com.alibaba.fastjson.JSON;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.handle.impl.wallet.yyg100001.bean.DataReq;
import com.yl.payinterface.core.handle.impl.wallet.yyg100001.bean.PP1017ReqBean;
import com.yl.payinterface.core.handle.impl.wallet.yyg100001.utils.HttpClientUtil;
import com.yl.payinterface.core.handle.impl.wallet.yyg100001.utils.yygSignUtils;
import com.yl.payinterface.core.handler.WalletPayHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * 吖吖谷
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/7/19
 */
@Service("yyg100001Handler")
public class Yyg100001HandlerImpl implements WalletPayHandler {

    private static final Logger logger = LoggerFactory.getLogger(Yyg100001HandlerImpl.class);

    public static HttpClientUtil httpClientUtil = new HttpClientUtil();

    public static String proxyMerid = "020054";//代理商id 对应excel中渠道商id
    // 商户私钥
    public static String privateKey = "MzA4MjAyNzgwMjAxMDAzMDBEMDYwOTJBODY0ODg2RjcwRDAxMDEwMTA1MDAwNDgyMDI2MjMwODIwMjVFMDIwMTAwMDI4MTgxMDBEM0IyMDBERjFGNTBDNDY1MUVBNURBNDJBQjNDRDlBNEZDNzJDNUU4QURDNjg2MzhCQjE0RkU0MkMzRUFEMkFBMTE2QUQxNTFCN0U2RjJFMUExODFFQTJDMEJFRkMyM0E2RkZCMzI0RUIzODMwOUIwOEIwOUQ4RjBFRUYyNDAxRDM2Q0Q2Q0QwRTdCRTMwRjJGRTlGNDNGNjI0OTNBNUQ0RjRFQTgwMTZGNDVGNDkzRjdGQzcwQTA3QjU2MDE5RDZBQjU2ODZFQTU0QUY5OThEQTkyQTE2RkNGNjlGMEY1NjE3MEU3MDYxQjc0REZBRjAxMzM0Q0MxODA0QjUxNjhEMDIwMzAxMDAwMTAyODE4MTAwQUI5RDhENTk1NDYwQkNBNjIxM0Y2MEZFNTUyNTgyNjc1RjA0ODg2RTg1OUEwMDJCQjM3NjY0NURBOUM4MEYzQTMxREU3RjhDOThFMjkwMTlEQzNEOUVBMjUwOTZGMEM1NDEwQ0NBMEVDNTM2NDBCRkFGMjdBRjg4M0E5RDg2RENFRUExRDVEM0FFQTQyNkNGNDM3QUZFOEYwQThGOTUwNDY2M0Q4RjdCNzMwMDBDNUQ4Nzk4NUMxNEY2RTYyQUQ5MTkzMDZFMDQ5OEMzNUFGMDQ2QTNENDU2OUE1QzQ2OEQ4MjBFMDA3NDZDQjVFMzQwRUVBRUNBQkE3Mzk5ODUyMTAyNDEwMEU5RDI3NTMxNkNFODFCNEVGRUU5RTYwRURCMTEyMkRFNzAwMUQyM0YxNDNBOUJFNkJCNkRGN0MxODM0ODBFN0NGMTcxQTFCNjkxMjEyMjlEMzA3MjNGMzdCQzY3QjI2QjEwOTFCRDhGNUIwRjM5REQ2RkY0MkJEMDZFNDc2REM5MDI0MTAwRTdDNjQ2NzM1Mzc1RUE1RkE4NDA4RDdDNEZEQTRCREJGMDc2NDJFMEQyMzRCNEQ2MkZEN0YwMkQ1REMyMTgwNkNFMTlBNkQyODExQUY3QjBCRDYzRUNCNTk5MDc5QTUwRTEzRTI1Q0FGRjM2QUVGNjA5MUVGMDMxNTBBQUI0QTUwMjQxMDBEODFDOTFGRUREQjZFNUNFQUNBMzQ4RTQ4MDJCQTU3QkNDMzA5MkJGODQ5NUVCMTQ2NjM1RUNBMTZBMTI4Njc5OEFCQjQ5MDkzMUI4NUI4MkM3OEQ0MENBRTYxMzNERkZFMEVGQTMzMUM1QUIxQzAzODNGRjNEMjNDNzU5QjNBOTAyNDEwMEJDMTAxNTMwMTRBMzk0MTYyNDE2QTRGNzFFNzAxOUE3OEZCOEQ1RDE0ODlGM0RCMEExQzc4Qzc5QjMxRUZGOTFEREZGRDA2REY0MTYyNkFFQzEyREU4NkVGRDg5QkVCQzJCRDdEQUEwOTk2RjBDOEU1NDlFNDZGNTJDRTgyNjg1MDI0MDdFNDE0MkZCRjFBNzAyRjBCMDY1MEJCQzdBQ0FCNjlBNDQ5QzRBRTE4OUU3RTQzNTBBRDVGNzY5NTE0REM2RDdBNjg3RTRDMjAxQUZDQjc4MDY5MTYwMTAzOUU4M0UxQjdBRTE0NzU1MkQwMzY2RjYwQjlFNzI0NTAxQzhENEQ4";

    public static String reqTestURL = "http://39.106.133.200:8011/api/polypay/core/1.0";

    public static void main(String[] args) {
        DataReq dataReqBean = new DataReq();
        PP1017ReqBean reqBean = new PP1017ReqBean("PP1017", "881644142781238", "TD-" + System.currentTimeMillis());
        reqBean.setOrderAmount("0.01");
        reqBean.setPayType("21");
        reqBean.setRemark("接口测试支付宝订单");
        reqBean.setBackNoticeUrl("www.baidu.com");
        List<PP1017ReqBean.ProductInfos.ProductInfo> productInfos = new ArrayList<PP1017ReqBean.ProductInfos.ProductInfo>();
        PP1017ReqBean.ProductInfos.ProductInfo productInfo = new PP1017ReqBean.ProductInfos.ProductInfo();
        productInfo.setProductName("测试商品");
        productInfo.setProductNumbers("1");
        productInfo.setProductPrice("0.1");
        productInfos.add(productInfo);
        PP1017ReqBean.ProductInfos productInfos1 = new PP1017ReqBean.ProductInfos();
        productInfos1.setProductInfo(productInfos);
        reqBean.setProductInfos(productInfos1);
        dataReqBean.setData(reqBean);
        String req = JSON.toJSONString(dataReqBean);
        try {
            logger.info("发送数据为:[" + req + "]");
            String res = httpClientUtil.doPost(reqTestURL, yygSignUtils.sign(req, privateKey, proxyMerid), req, "UTF-8");
            logger.info("响应数据为:[" + res + "]");
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
    }

    @Override
    public Map<String, String> pay(Map<String, String> map) {
        Map<String,String> resParams = new HashMap<>();
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
        String interfaceRequestID = map.get("interfaceRequestID");
        String amount = map.get("amount");
        String productName = map.get("productName");
        DataReq dataReqBean = new DataReq();
        PP1017ReqBean reqBean = new PP1017ReqBean("PP1017", properties.getProperty("userId"), interfaceRequestID);
        reqBean.setOrderAmount(amount);
        if ("YYG100001-ALIPAY_H5".equals(map.get("interfaceInfoCode"))) {
            reqBean.setPayType("21");
        } else {
            reqBean.setPayType("11");
        }
        reqBean.setRemark(productName);
        reqBean.setBackNoticeUrl(properties.getProperty("noticeUrl"));
        List<PP1017ReqBean.ProductInfos.ProductInfo> productInfos = new ArrayList<>();
        PP1017ReqBean.ProductInfos.ProductInfo productInfo = new PP1017ReqBean.ProductInfos.ProductInfo();
        productInfo.setProductName(productName);
        productInfo.setProductNumbers("1");
        productInfo.setProductPrice(amount);
        productInfos.add(productInfo);
        PP1017ReqBean.ProductInfos productInfos1 = new PP1017ReqBean.ProductInfos();
        productInfos1.setProductInfo(productInfos);
        reqBean.setProductInfos(productInfos1);
        dataReqBean.setData(reqBean);
        String req = JSON.toJSONString(dataReqBean);
        try {
            logger.info("发送数据为:{}", req);
            String res = httpClientUtil.doPost(properties.getProperty("url"), yygSignUtils.sign(req, properties.getProperty("key"), properties.getProperty("proxyMerid")), req, "UTF-8");
            logger.info("响应数据为:{}", res);
            Map<String, Object> resPar = JsonUtils.toObject(res, new TypeReference<Map<String, Object>>() {
            });
            resParams.put("code_url", resPar.get("qrCodeStr").toString());
            resParams.put("code_img_url", "https://api.zk1006.cn/qrCode?s=" + resParams.get("code_url"));
            resParams.put("interfaceInfoCode", interfaceRequestID);
            resParams.put("interfaceRequestID", interfaceRequestID);
            resParams.put("gateway", "native");
            resParams.put("merchantNo", properties.getProperty("userId"));
        } catch (Exception e) {
            logger.error("下单异常!异常信息:{}", e);
        }
        return resParams;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        return null;
    }

    @Override
    public InterfaceRequest complete(Map<String, Object> map) {
        return null;
    }
}
package com.yl.payinterface.core;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.handle.impl.remit.guomei100001.utils.GuoMeiSignUtils;
import com.yl.payinterface.core.utils.MD5Util;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

public class GmTest extends BaseTest {

    @Test
    public void df(){
        Map<String, String> desa = new HashMap<>();
        desa.put("login_token", "");
        desa.put("req_no", "RQ-" + System.currentTimeMillis());
        desa.put("app_code", "apc_02000001524");
        desa.put("app_version", "1.0.0");
        desa.put("service_code", "sne_00000000002");
        desa.put("plat_form", "03");
        desa.put("merchant_number", "SHID20180613029");
        desa.put("order_number", "DF-" + System.currentTimeMillis());
        desa.put("wallet_id", "0100851953871183");//付款钱包id
        desa.put("asset_id", "251865a0c51a4cce89d0affb079542ee");//付款资产id
        desa.put("password_type", "02");//付款方密码类型
        desa.put("encrypt_type", "02");//付款方加密类型
        desa.put("pay_password", MD5Util.md5("753951"));//付款方支付密码
        desa.put("customer_type", "01");//收款客户类型
        desa.put("customer_name", "周林");//收款客户姓名
        desa.put("currency", "CNY");//币种
        desa.put("amount", "10.00");//代付金额
        desa.put("async_notification_addr", "https://kd.alblog.cn/front/callback");//异步通知地址
        desa.put("asset_type_code", "000002");//收款客户资产大类编号
        desa.put("account_type_code", "01");//收款客户资产小类编号
        desa.put("account_number", "6215583202002031321");//收款人银行卡
        String access_token = GuoMeiSignUtils.getToken("https://api.gomepay.com/access_token?aid=AID&signature=SIGNATURE&timestamp=TIMESTAMP&nonce=NONCE", "8a179b8c63d8c63b0163fc2e11b7031c", "rongmei|m2|20180614");
        String res = GuoMeiSignUtils.sendPost("https://api.gomepay.com/CoreServlet?aid=AID&api_id=API_ID&access_token=ACCESS_TOKEN","8a179b8c63d8c63b0163fc2e11b7031c","epay_api_deal@agent_for_paying",access_token, JsonUtils.toJsonString(desa));
        System.out.println(res);
    }
}
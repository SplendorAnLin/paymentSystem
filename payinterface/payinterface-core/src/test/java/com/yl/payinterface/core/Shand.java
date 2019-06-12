package com.yl.payinterface.core;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.handler.RemitHandler;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Shand extends BaseTest{

    @Autowired
    private RemitHandler shandHandler100001;

    @Test
    public void remit(){
        String url = "https://caspay.sandpay.com.cn/agent-main/openapi/agentpay";
        String mid = "14066132";
        Properties properties = new Properties();
        properties.setProperty("url", url);
        properties.setProperty("mid", mid);
        Map<String, String> params = new HashMap<>();
        params.put("transConfig", JsonUtils.toJsonString(properties));
        params.put("requestNo", "TD-" + System.currentTimeMillis());
        params.put("orderTime", new SimpleDateFormat("YYYYMMddHHmmss").format(new Date()));
        params.put("amount", "10");
        params.put("accountNo", "6215583202002031321");
        params.put("accountName", "周林");
        params.put("remark", "结算款");
        params.put("interfaceInfoCode", "shandHandler100001");
        shandHandler100001.remit(params);
    }
}
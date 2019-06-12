package com.yl.rate.core.test.interfaces.impl;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.rate.core.entity.NormalRate;
import com.yl.rate.core.entity.NormalRateHistory;
import com.yl.rate.core.test.BaseTest;
import com.yl.rate.interfaces.beans.NormalRateBean;
import com.yl.rate.interfaces.interfaces.RateQueryInterface;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RateQueryInterface 测试
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/12/13
 */
public class RateQueryInterfaceTest extends BaseTest {

    @Resource
    private RateQueryInterface rateQueryInterface;

    @Test
    public void queryByIdHistory(){
        System.out.println(JsonUtils.toJsonString(rateQueryInterface.queryByIdHistory(Long.valueOf("99"))));
    }

    @Test
    public void getTemplateInfo(){
        System.out.println(rateQueryInterface.getTemplateInfo("CUSTOMER", "B2C"));
    }

    @Test
    public void queryByCode(){
        System.out.println(JsonUtils.toJsonString(rateQueryInterface.queryByCode("OW-20171215-100235864602")));
    }

    @Test
    public void findAllFeeConfigHistory(){
        Page page = new Page();
        page = rateQueryInterface.findAllFeeConfigHistory(page, "OW-20171212-1995666655");
        System.out.println(JsonUtils.toJsonString(page.getObject()));
    }

    @Test
    public void findAllFeeConfig(){
        Page page = new Page();
        Map<String, Object> params = new HashMap<>();
        page = rateQueryInterface.findAllFeeConfig(page, params);
        System.out.println(JsonUtils.toJsonString(page.getObject()));
    }
}
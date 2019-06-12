package com.yl.chat.web.controller;

import com.yl.chat.service.AppInteractionService;
import com.yl.chat.service.UserService;
import com.yl.chat.utils.IpUtil;
import com.yl.chat.wecaht.model.User;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用跳转
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/11/24
 */
@Controller
public class CommonController {

    @Resource
    private UserService userService;

    @Resource
    private AppInteractionService appInteractionService;

    @RequestMapping("toWeeklySales")
    public String toWeeklySales(HttpServletRequest request, HttpSession session) {
        getOpenId(request, session);
        return "statistics/static";
    }

    @RequestMapping("toWelcome")
    public String toWelcome(HttpServletRequest request, HttpSession session) {
        getOpenId(request, session);
        return "homePage/welcome";
    }

    @RequestMapping("toFindByCustNo")
    public String toFindByCustNo(HttpServletRequest request, HttpSession session) {
        getOpenId(request, session);
        return "myCenter/myCard";
    }

    @RequestMapping("toHome")
    public String toHome(HttpServletRequest request, HttpSession session) {
        getOpenId(request, session);
        return "homePage/home";
    }

    @RequestMapping("toMyCenter")
    public String toMyCenter(HttpServletRequest request, HttpSession session) {
        getOpenId(request, session);
        Map<String, Object> params = retrieveParams(request);
        params.put("userName", request.getAttribute("userName"));
        params.put("customerNo", request.getAttribute("customerNo"));
        String msg = appInteractionService.wechatInfo(params);
        JSONObject responseData = JSONObject.fromObject(msg).getJSONObject("responseData");
        String customerImg = responseData.getString("customerImg");
        request.setAttribute("customerImg", customerImg);
        return "myCenter/myCenter";
    }

    @RequestMapping("toAccountDetails")
    public String toAccountDetails(HttpServletRequest request, HttpSession session) {
        getOpenId(request, session);
        return "inquire/accountDetails";
    }

    @RequestMapping("toTallyOrder")
    public String toTallyOrder(HttpServletRequest request, HttpSession session) {
        getOpenId(request, session);
        return "inquire/tallyOrder";
    }

    @RequestMapping("toSelectTradeOrder")
    public String toSelectTradeOrder(HttpServletRequest request, HttpSession session) {
        getOpenId(request, session);
        return "inquire/transactionDetails";
    }

    @RequestMapping("toStoreQrcode")
    public String toStoreQrcode(HttpServletRequest request, HttpSession session) {
        getOpenId(request, session);
        return "qrade/storeQrcode";
    }

    @RequestMapping("toCustomerCenter")
    public String toCustomerCenter(HttpServletRequest request, HttpSession session) {
        getOpenId(request, session);
        return "myCenter/customerCenter";
    }

    @RequestMapping("toGathring")
    public String toGathring(HttpServletRequest request, HttpSession session) {
        getOpenId(request, session);
        return "gather/gathring";
    }

    @RequestMapping("toGathringResulet")
    public String toGathringResulet(HttpServletRequest request, HttpSession session, String url, String amount) {
        getOpenId(request, session);
        request.setAttribute("url", url);
        request.setAttribute("amount", amount);
        return "gather/gathringResult";
    }

    @RequestMapping("toAddcard")
    public String toAddcard(HttpServletRequest request, HttpSession session) {
        getOpenId(request, session);
        request.setAttribute("name", session.getAttribute("name"));
        request.setAttribute("idCard", session.getAttribute("idCard"));
        return "myCenter/addCard";
    }
    @RequestMapping("toAboutUs")
    public String toAboutUs(HttpServletRequest request, HttpSession session) {
        getOpenId(request, session);
        return "myCenter/aboutUs";
    }

    /**
     * 认证信息获取
     */
    public void getOpenId(HttpServletRequest request, HttpSession session) {
        String openid = session.getAttribute("openId").toString();
        User uopenid = userService.findUserOpenid(openid);
        if (uopenid != null) {
            request.setAttribute("userName", uopenid.getPhone());
            request.setAttribute("customerNo", uopenid.getCustomer());
            request.setAttribute("ownerId", uopenid.getCustomer());
        }
    }

    /**
     * 参数转换
     */
    private Map<String, Object> retrieveParams(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> ipInfo = IpUtil.getAddressByIp(request);
        if (ipInfo != null){
            if (ipInfo.get("address") != null){
                resultMap.put("ipAdd", ipInfo.get("address"));
            } else {
                resultMap.put("ipAdd", "暂无位置信息");
            }

            if (ipInfo.get("lng") != null){
                resultMap.put("lng", ipInfo.get("lng"));
            } else {
                resultMap.put("lng", 0);
            }

            if (ipInfo.get("lat") != null){
                resultMap.put("lat", ipInfo.get("lat"));
            } else {
                resultMap.put("lat", 0);
            }
        } else {
            resultMap.put("ipAdd", "暂无位置信息");
            resultMap.put("lng", 0);
            resultMap.put("lat", 0);
        }
        return resultMap;
    }
}
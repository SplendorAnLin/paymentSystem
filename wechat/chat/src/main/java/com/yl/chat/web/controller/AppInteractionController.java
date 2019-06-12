package com.yl.chat.web.controller;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.chat.Constant;
import com.yl.chat.service.AppInteractionService;
import com.yl.chat.utils.CookieUtils;
import com.yl.chat.utils.IpUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Controller
public class AppInteractionController {

    private static final Logger logger = LoggerFactory.getLogger(AppInteractionController.class);

    @Resource
    private AppInteractionService appInteractionService;

    public String msg;

    public String seriously;

    private static Properties prop = new Properties();

    static {
        try {
            prop.load(new InputStreamReader(AppInteractionController.class.getClassLoader().getResourceAsStream("serverHost.properties"), "UTF-8"));
        } catch (IOException e) {
            logger.error("system ClassLoader error:", e);
        }
    }

    /**
     * 001 商户登陆
     */
    @RequestMapping("appLogin")
    public void appLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.appLogin(params);
        uniteParams(request, response, msg);
        if ("SUCCESS".equals(seriously(msg))){
            JSONObject responseData = JSONObject.fromObject(msg).getJSONObject("responseData");
            String customerImg = responseData.getString("customerImg");
            String sign = responseData.getString("sign");
            String name = responseData.getJSONObject("cust").getString("linkMan");
            String idCard = responseData.getJSONObject("cust").getString("idCard");
            session.setAttribute("customerImg", customerImg);
            session.setAttribute("sign", sign);
            session.setAttribute("name", name);
            session.setAttribute("idCard", idCard);
            CookieUtils.addCookie(response, "customerImg", customerImg, 2678400);
//            CookieUtils.addCookie(response, "name", name, 2678400);
//            CookieUtils.addCookie(response, "idCard", idCard, 2678400);
        }
    }

    /**
     * 0001 退出登陆
     */
    @RequestMapping("loginOut")
    public void loginOut(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        appInteractionService.loginOut(params);
        printWriterInfo(response, "SUCCESS", request, "", "");
    }

    /**
     * 003 查询商户信息
     */
    @RequestMapping("queryCustomerInfo")
    public void queryCustomerInfo(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.queryCustomerInfo(params);
        String customerImg = CookieUtils.getCookieByName(request, "customerImg");
        request.setAttribute("customerImg", customerImg);
        uniteParams(request, response, msg);
    }

    /**
     * 004 提现
     */
    @RequestMapping("drawCash")
    public void drawCash(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.drawCash(params);
        unite(request, response, msg);
    }

    /**
     * 005 余额信息
     */
    @RequestMapping("findBalance")
    public void findBalance(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.findBalance(params);
        unite(request, response, msg);
    }

    /**
     * 006 首页信息刷新
     */
    @RequestMapping("getLoginInfo")
    public void getLoginInfo(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.getLoginInfo(params);
        uniteParams(request, response, msg);
    }

    /**
     * 007 当日交易金额、笔数
     */
    @RequestMapping("today")
    public void today(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.today(params);
        unite(request, response, msg);
    }

    /**
     * 008 充值
     */
    @RequestMapping("recharge")
    public void recharge(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.recharge(params);
        unite(request, response, msg);
    }

    /**
     * 009 提现订单
     */
    @RequestMapping("selectRequest")
    public void selectRequest(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.selectRequest(params);
        unite(request, response, msg);
    }

    /**
     * 010 交易订单
     */
    @RequestMapping("selectTradeOrder")
    public void selectTradeOrder(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.selectTradeOrder(params);
        unite(request, response, msg);
    }

    /**
     * 011 账务明细
     */
    @RequestMapping("findAccountChange")
    public void findAccountChange(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.findAccountChange(params);
        unite(request, response, msg);
    }

    /**
     * 012 登陆密码修改
     */
    @RequestMapping("appUpdatePassword")
    public void appUpdatePassword(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.appUpdatePassword(params);
        unite(request, response, msg);
    }

    /**
     * 013 提现订单详情
     */
    @RequestMapping("selectRequestDetailed")
    public void selectRequestDetailed(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.selectRequestDetailed(params);
        unite(request, response, msg);
    }

    /**
     * 014 周销售趋势统计
     */
    @RequestMapping("weeklySales")
    public void weeklySales(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.weeklySales(params);
        unite(request, response, msg);
    }

    /**
     * 015 交易订单详细
     */
    @RequestMapping("selectTradeOrderDetailed")
    public void selectTradeOrderDetailed(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.selectTradeOrderDetailed(params);
        unite(request, response, msg);
    }

    /**
     * 016 提现复核
     */
    @RequestMapping("goDrawCash")
    public void goDrawCash(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.goDrawCash(params);
        unite(request, response, msg);
    }

    /**
     * 017 微信条码
     */
    @RequestMapping("barcodePay")
    public void barcodePay(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.barcodePay(params);
        unite(request, response, msg);
    }

    /**
     * 0008 商户结算卡信息
     */
    @RequestMapping("getCustomerSettle")
    public void getCustomerSettle(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.getCustomerSettle(params);
        unite(request, response, msg);
    }

    /**
     * 0009 商户费率信息
     */
    @RequestMapping("getCustomerFeeList")
    public void getCustomerFeeList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.getCustomerFeeList(params);
        unite(request, response, msg);
    }

    /**
     * 0011 商户收款码
     */
    @RequestMapping("getQRcode")
    public void getQRcode(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.getQRcode(params);
        unite(request, response, msg);
    }

    /**
     * 0012 查询客户端信息
     */
    @RequestMapping("getProtolAll")
    public void getProtolAll(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.getProtolAll(params);
        unite(request, response, msg);
    }

    /**
     * 0014 意见反馈新增
     */
    @RequestMapping("userFeedbackAdd")
    public void userFeedbackAdd(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.userFeedbackAdd(params);
        unite(request, response, msg);
    }

    /**
     * 018 首页欢迎页广告
     */
    @RequestMapping("getWelcomeAd")
    public void getWelcomeAd(HttpServletRequest request, HttpServletResponse response) {

    }

    /**
     * 020 交易卡绑定
     */
    @RequestMapping("addTansCard")
    public void addTansCard(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.addTansCard(params);
        unite(request, response, msg);
    }

    /**
     * 021 交易卡解绑
     */
    @RequestMapping("unlockTansCard")
    public void unlockTansCard(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.unlockTansCard(params);
        unite(request, response, msg);
    }

    /**
     * 022 交易卡查询
     */
    @RequestMapping("findByCustNo")
    public void findByCustNo(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.findByCustNo(params);
        unite(request, response, msg);
    }

    /**
     * 023 交易卡重复验证
     */
    @RequestMapping("checkTransCard")
    public void checkTransCard(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.checkTransCard(params);
        unite(request, response, msg);
    }

    /**
     * 024 认证支付
     */
    @RequestMapping("authpay")
    public void authpay(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.authpay(params);
        unite(request, response, msg);
    }

    /**
     * 025 设备列表
     */
    @RequestMapping("getDevices")
    public void getDevices(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.getDevices(params);
        unite(request, response, msg);
    }

    /**
     * 026 保存商户头像
     */
    @RequestMapping("saveCustImg")
    public void saveCustImg(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.saveCustImg(params);
        unite(request, response, msg);
    }

    /**
     * 027 重置密码
     */
    @RequestMapping("resetPwd")
    public void resetPwd(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.resetPwd(params);
        unite(request, response, msg);
    }

    /**
     * 028 获取验证码
     */
    @RequestMapping("getVerifyCode")
    public void getVerifyCode(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.getVerifyCode(params);
        unite(request, response, msg);
    }

    /**
     * 029 快捷支付
     */
    @RequestMapping("quick")
    public void quick(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.quick(params);
        unite(request, response, msg);
    }

    /**
     * 030 快速入网地址获取
     */
    @RequestMapping("initialization")
    public void initialization(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.initialization(params);
        unite(request, response, msg);
    }

    /**
     * 031 POS申请
     */
    @RequestMapping("appApplication")
    public void appApplication(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.appApplication(params);
        unite(request, response, msg);
    }

    /**
     * 032 水牌申请
     */
    @RequestMapping("appDevice")
    public void appDevice(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.appDevice(params);
        unite(request, response, msg);
    }

    /**
     * 033 水牌类型获取
     */
    @RequestMapping("getDevicesInfo")
    public void getDevicesInfo(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.getDevicesInfo(params);
        unite(request, response, msg);
    }

    /**
     * 034 商户状态
     */
    @RequestMapping("queryCustomerStatus")
    public void queryCustomerStatus(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.queryCustomerStatus(params);
        unite(request, response, msg);
    }

    /**
     * 035 预开通商户录入结算卡信息
     */
    @RequestMapping("custSettleCard")
    public void custSettleCard(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.custSettleCard(params);
        unite(request, response, msg);
    }

    /**
     * 036 预开通商户修改结算卡信息
     */
    @RequestMapping("updateCreateSettle")
    public void updateCreateSettle(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.updateCreateSettle(params);
        unite(request, response, msg);
    }

    /**
     * 037 预开通商户结算卡信息查询
     */
    @RequestMapping("querySettle")
    public void querySettle(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.querySettle(params);
        unite(request, response, msg);
    }

    /**
     * 038 完善商户基本信息
     */
    @RequestMapping("updateCustomerBaseInfo")
    public void updateCustomerBaseInfo(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.updateCustomerBaseInfo(params);
        unite(request, response, msg);
    }

    /**
     * 039 查询银行商户
     */
    @RequestMapping("findBankCustomerByPage")
    public void findBankCustomerByPage(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.findBankCustomerByPage(params);
        unite(request, response, msg);
    }

    /**
     * 040 查询MCC
     */
    @RequestMapping("findMcc")
    public void findMcc(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.findMcc(params);
        unite(request, response, msg);
    }

    /**
     * 041 查询省
     */
    @RequestMapping("findProvince")
    public void findProvince(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.findProvince(params);
        unite(request, response, msg);
    }

    /**
     * 042 查询市
     */
    @RequestMapping("findCityByProvince")
    public void findCityByProvince(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.findCityByProvince(params);
        unite(request, response, msg);
    }

    /**
     * 043 用户关注商家
     */
    @RequestMapping("followBankCustomer")
    public void followBankCustomer(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = retrieveParams(request);
        msg = appInteractionService.followBankCustomer(params);
        unite(request, response, msg);
    }

    /**
     * 卡识别
     */
    @RequestMapping("recognition")
    public void recognition(String accountNo, HttpServletResponse response) {
        try {
            response.setContentType("application/json");
            response.setHeader("Access-Control-Allow-Methods", "POST");
            PrintWriter writer = response.getWriter();
            StringBuffer url = new StringBuffer(prop.getProperty("cachecenterIin") + "?cardNo=");
            url.append(accountNo);
            url.append(
                    "&fields=code&fields=length&fields=panLength&fields=providerCode&fields=cardType&fields=cardName&fields=agencyCode&fields=agencyName");
            writer.write(getJsonObject(url.toString()));
        } catch (Exception e){
            logger.error("卡识别失败：{}", e);
        }
    }

    /**
     * 联行号匹配
     */
    @RequestMapping("cnapsCode")
    public void cnapsCode(String providerCode, String word, HttpServletResponse response) {
        try {
            response.setContentType("application/json");
            response.setHeader("Access-Control-Allow-Methods", "POST");
            PrintWriter writer = response.getWriter();
            StringBuffer url = new StringBuffer(prop.getProperty("cachecenterCnaps") + "?");
            url.append("fields=clearingBankCode&fields=providerCode&fields=name&fields=providerCode&providerCode=");
            url.append(providerCode + "&word=");
            url.append(word);
            writer.write(getJsonObject(url.toString()));
        } catch (Exception e){
            logger.error("联行号匹配失败：{}", e);
        }
    }

    public static String getJsonObject(String url){
        try {
            URL u = new URL(url);
            try {
                HttpURLConnection uConnection = (HttpURLConnection) u.openConnection();
                try {
                    uConnection.connect();
                    InputStream is = uConnection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    String content = new String(sb);
                    br.close();
                    return content;
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * 参数转换
     */
    private Map<String, Object> retrieveParams(HttpServletRequest request) {
        Map<String, String[]> requestMap = request.getParameterMap();
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
        for (String key : requestMap.keySet()) {
            if (requestMap.get(key) != null) {
                resultMap.put(key, Array.get(requestMap.get(key), 0).toString().trim());
            }
        }
        return resultMap;
    }

    /**
     * 处理数据返回
     */
    public void printWriterInfo(HttpServletResponse response, String msg, HttpServletRequest request, String userName, String customerNo) {
        try {
            response.setContentType("application/json");
            response.setHeader("Access-Control-Allow-Methods", "POST");
            PrintWriter writer = response.getWriter();
            if ("AFRESH".equals(msg)){
                writer.print(JsonUtils.toJsonString(returnParams("AFRESH", "身份认证失效，请重新登录")));
                return;
            } else if ("FALSE".equals(msg)){
                writer.print(JsonUtils.toJsonString(returnParams("FALSE", userName)));
                return;
            } else if ("ERROR".equals(msg)){
                writer.print(JsonUtils.toJsonString(returnParams("ERROR", "系统异常，请稍后再试")));
                return;
            } else if ("NULL".equals(msg)){
                writer.print(JsonUtils.toJsonString(returnParams("NULL", "系统无返回参数，请联系管理员")));
                return;
            } else if ("SUCCESS".equals(msg)){
                if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(customerNo)){
                    request.setAttribute("userName", userName);
                    request.setAttribute("customerNo", customerNo);
                }
            }
            writer.print(msg);
        } catch (Exception e) {
            logger.error("系统异常:", e);
        }
    }

    /**
     * 身份认证是否过期
     */
    public String seriously(String msg){
        if (StringUtils.isBlank(msg)){
            // 无返回参数
            return "NULL";
        }
        JSONObject res = JSONObject.fromObject(msg);
        if ("666666".equals(res.getString("responseCode"))){
            // 身份认证失效 请重新登录
            return "AFRESH";
        }
        if ("000000".equals(res.getString("responseCode"))){
            // 处理成功
            return "SUCCESS";
        }
        if ("999999".equals(res.getString("responseCode"))){
            // 系统异常
            return "ERROR";
        }
        // 错误提示
        return "FALSE";
    }

    /**
     * 无参数返回信息统一处理
     */
    public void unite(HttpServletRequest request, HttpServletResponse response, String res){
        seriously = seriously(res);
        if ("SUCCESS".equals(seriously)){
            printWriterInfo(response, res, request, "", "");
        } else if ("FALSE".equals(seriously)){
            printWriterInfo(response, seriously, request, JSONObject.fromObject(res).getString("responseMsg"), null);
        } else {
            printWriterInfo(response, seriously, request, "", "");
        }
    }

    /**
     * 成功参数返回信息统一处理
     */
    public void uniteParams(HttpServletRequest request, HttpServletResponse response, String res){
        seriously = seriously(res);
        if ("SUCCESS".equals(seriously)){
            JSONObject json = JSONObject.fromObject(msg).getJSONObject("responseData").getJSONObject("cust");
            printWriterInfo(response, res, request, json.getString("phoneNo"), json.getString("customerNo"));
        } else if ("FALSE".equals(seriously)){
            printWriterInfo(response, seriously, request, JSONObject.fromObject(res).getString("responseMsg"), null);
        } else {
            printWriterInfo(response, seriously, request, "", "");
        }
    }

    /**
     * 返回处理
     */
    public Map<String, Object> returnParams(String responseCode, String responseMsg){
        Map<String, Object> params = new HashMap<>();
        params.put("responseMsg", responseMsg);
        params.put("responseCode", responseCode);
        return params;
    }
}
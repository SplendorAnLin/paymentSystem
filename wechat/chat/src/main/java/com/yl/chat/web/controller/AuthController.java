package com.yl.chat.web.controller;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.agent.api.bean.AgentOperator;
import com.yl.agent.api.interfaces.AgentOperInterface;
import com.yl.boss.api.interfaces.ProtocolInterface;
import com.yl.chat.Constant;
import com.yl.chat.service.UserService;
import com.yl.chat.wecaht.model.User;
import com.yl.customer.api.bean.CustOperator;
import com.yl.customer.api.interfaces.CustOperInterface;
import com.yl.sms.SmsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 微信绑定
 *
 * @author Administrator
 */
@Controller
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Resource
    private CustOperInterface custOperInterface;
    @Resource
    private AgentOperInterface agentOperInterface;
    @Resource
    private UserService userService;
    @Resource
    private ProtocolInterface protocolInterface;

    @RequestMapping("login")
    public String auth(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                       String username, String password) {
        try {
            String openid = session.getAttribute("openId").toString();
            User uopenid = userService.findUserOpenid(openid);
            if (uopenid != null) {
                session.setAttribute("message", "手机号已绑定！");
                return "redirect:/message";
            }
        } catch (Exception e) {
            logger.error("登录检测异常", e);
        }
        return "auth/chat_login";
    }

    @RequestMapping("message")
    public String message() {
        return "wchat/failed";
    }

    /**
     * 检测手机存在
     *
     * @param phone
     * @param request
     * @param response
     * @param session
     * @throws IOException
     */
    @RequestMapping("getphone")
    public void findphone(String phone, HttpServletRequest request, HttpServletResponse response,
                          HttpSession session) throws IOException {
        CustOperator custOper = null;
        AgentOperator agentOper = null;
        try {
            try {
                custOper = custOperInterface.findByUserName(phone);
            } catch (Exception e) {
                logger.error("商户查询失败", e);
            }
            if (custOper == null) {
                try {
                    agentOper = agentOperInterface.findByUserName(phone);
                } catch (Exception e) {
                    logger.error("代理商查询失败");
                }
            }
            if (custOper != null || agentOper != null) {
                Map<String, String> Params = new HashMap<>();
                Params.put("custOper", JsonUtils.toJsonString(custOper));
                Params.put("agentOper", JsonUtils.toJsonString(agentOper));
                response.getWriter().write(JsonUtils.toJsonString(Params));
            } else {
                response.getWriter().write("null");
            }
        } catch (Exception e) {
            logger.error("手机号检测失败！错误原因{}", e);
        }
    }

    /**
     * 发送手机验证码
     *
     * @param phone
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("getmessage")
    public void sendSmsCode(String phone, HttpServletRequest request, HttpServletResponse response,
                            HttpSession session) throws IOException {
        try {
            Random random = new Random();
            // 生成验证码6位随机数字
            String verifyCode = "" + random.nextInt(10) + random.nextInt(10) + random.nextInt(10) + random.nextInt(10)
                    + random.nextInt(10) + random.nextInt(10);
            // 放到session中
            session.setAttribute("verifyCode", verifyCode);
            SmsUtils.sendCustom(String.format(Constant.SMS_MESSAGE_TYPE3, verifyCode), phone);
            PrintWriter writer = response.getWriter();
            writer.println(verifyCode);
        } catch (IOException e) {
            logger.error("微信获取验证码失败！错误原因{}", e);
        }
    }

    /**
     * 验证手机验证码
     *
     * @throws IOException
     */
    @RequestMapping("getverify")
    public void verify(String smsCode, String phone, HttpServletRequest request, HttpServletResponse response,
                       HttpSession session, PrintWriter pw) throws IOException {
        CustOperator custOper = null;
        AgentOperator agentOper = null;
        if (session.getAttribute("verifyCode").equals(smsCode)) {
            try {
                custOper = custOperInterface.findByUserName(phone);
            } catch (Exception e) {
                logger.error("商户查询失败", e);
            }
            if (custOper == null) {
                try {
                    agentOper = agentOperInterface.findByUserName(phone);
                } catch (Exception e) {
                    logger.error("代理商查询失败");
                }
            }
            String openid = session.getAttribute("openId").toString();
            //根据当前设备编号去数据库查看是否存在，如果存在就修改当前绑定，若不存在则新增当前绑定
            User uopenid = userService.findUserOpenid(openid);
            User user = new User();
            if (uopenid != null) {
                user = uopenid;
                if (custOper != null) {
                    user.setCustomer(custOper.getCustomerNo());
                    user.setPhone(phone);
                    userService.updateWechat(user);
                    session.setAttribute("custOper", custOper);
                    pw.print(true);
                } else if (agentOper != null) {
                    user.setCustomer(agentOper.getAgentNo());
                    user.setPhone(phone);
                    userService.updateWechat(user);
                    session.setAttribute("agentOper", agentOper);
                    pw.print(true);
                }
            } else {
                if (custOper != null) {
                    user.setCustomer(custOper.getCustomerNo());
                    user.setOpenid(openid);
                    user.setPhone(phone);
                    userService.saveWechat(user);
                    session.setAttribute("custOper", custOper);
                    pw.print(true);
                } else if (agentOper != null) {
                    user.setCustomer(agentOper.getAgentNo());
                    user.setOpenid(openid);
                    user.setPhone(phone);
                    userService.saveWechat(user);
                    session.setAttribute("agentOper", agentOper);
                    pw.print(true);
                }
            }
        } else {
            pw.print(false);// 这是传到页面的（验证码不对）
        }
    }

    @RequestMapping("chatLogin")
    public String chatLogin() {
        return "auth/chat_login";
    }

    @RequestMapping("personalRights")
    public String personalRights(HttpSession session) {
        session.setAttribute("custOper", (CustOperator) session.getAttribute("custOper"));
        session.setAttribute("agentOper", (AgentOperator) session.getAttribute("agentOper"));
        return "auth/authority_Management";
    }

    @RequestMapping("success")
    public String success(HttpSession session) {
        session.setAttribute("message", "绑定完成");
        return "wchat/success";
    }
}
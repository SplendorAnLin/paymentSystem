package com.yl.chat.web.filter;

import com.lefu.commons.web.spring.ApplicationContextUtils;
import com.lefu.commons.web.springmvc.interceptor.LoginPermissionInterceptor;
import com.yl.chat.service.UserService;
import com.yl.chat.utils.ChatUtil;
import com.yl.chat.wecaht.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 请求拦截器
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017年4月28日
 */
public class AuthFilter extends LoginPermissionInterceptor {

    private static Logger log = LoggerFactory.getLogger(AuthFilter.class);

    private UserService userService;

    private static Properties prop = new Properties();

    static {
        try {
            prop.load(new InputStreamReader(ChatUtil.class.getClassLoader().getResourceAsStream("serverHost.properties")));
        } catch (IOException e) {
            log.error("AuthFilter load Properties error:", e);
        }
    }

    public AuthFilter(String redirectPath) {
        super(redirectPath);
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            String servletPath = request.getServletPath();
            String serverUrl = request.getScheme() + "://" + request.getServerName() + request.getContextPath();
            if (servletPath.equals("/wechatCore")) {
                return true;
            }
            userService = (UserService) ApplicationContextUtils.getApplicationContext().getBean("userService");
            if (!servletPath.equals("/callback")) {//排除地址
                Object openId = request.getSession().getAttribute("openId");
                if (servletPath.indexOf("hessian") > -1) {//hessian不拦截
                    return true;
                }
                if (openId != null && !"".equals(openId)) {
                    if (isPermit(servletPath))
                        return true;
                    User user = userService.findUserOpenid(openId.toString());
                    if (user != null)
                        return true;
                    request.getSession().setAttribute("message", "未绑定帐号,暂时无法使用!");
                    response.sendRedirect(serverUrl + "/message");
                    return false;
                } else {
                    String backUrl = serverUrl + "/callback";
                    String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + prop.getProperty("APPID")
                            + "&redirect_uri=" + URLEncoder.encode(backUrl)
                            + "&response_type=code&scope=snsapi_base&state=" + servletPath + "#wechat_redirect ";
                    response.sendRedirect(url);
                    return false;
                }
            } else {
                String code = request.getParameter("code");
                String openId = ChatUtil.getOpenId(code);
                request.getSession().setAttribute("openId", openId);
                User user = userService.findUserOpenid(openId);
                if (user != null) {
                    String path = serverUrl + request.getParameter("state");
                    response.sendRedirect(path);
                    return false;
                } else {
                    response.sendRedirect(serverUrl + "/login");
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isPermit(String str) {
        List<String> list = new ArrayList<>();
        list.add("/message");
        list.add("/login");
        list.add("/chatlist");
        list.add("/chathelp");
        list.add("/wechatindex");
        list.add("/getverify");
        list.add("/getmessage");
        list.add("/getphone");
        list.add("/personalRights");
        list.add("/success");
        for (String string : list) {
            if (string.equals(str)) {
                return true;
            }
        }
        return false;
    }
}
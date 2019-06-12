package com.yl.chat.interfaces.impl;

import com.yl.chat.Constant;
import com.yl.chat.interfaces.WechatInterface;
import com.yl.chat.service.MessageService;
import com.yl.chat.utils.ChatUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信菜单接口实现
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017年4月21日
 */
@Service("wechatInterface")
public class WechatInterfaceimpl implements WechatInterface {

    @Resource
    MessageService messageService;

    @Override
    public int createMenu(String menu) throws Exception {
        return ChatUtil.createMenu(menu);
    }

    @Override
    public Map findMenu() {
        return ChatUtil.findMenu();
    }

    public void sendEX(String sys, String level, String type, String oper) {
        sendEX(sys, level, type, oper, "您好，系统发生异常信息：", "请及时处理!");
    }

    public void sendEX(String sys, String level, String type, String oper, String first, String remark) {
        sendEX(sys, level, type, oper, first, remark, null);
    }

    @Override
    public void sendEX(String sys, String level, String type, String oper, String first, String remark, String date) {
        Map<String, String> info = new HashMap<>();
        SimpleDateFormat time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        info.put("first", first);
        info.put("keyword1", sys);
        info.put("keyword2", time.format(new Date()));
        info.put("keyword3", level);
        info.put("keyword4", type);
        info.put("keyword5", oper);
        info.put("remark", remark);
        if (date != null) {
            for (Object openId : ChatUtil.getGroupUserByName("技术组")) {
                info.put("color", "#FF0000");
                messageService.send(openId.toString(), Constant.templeEX, Constant.CLICK_URL, info, true, date);
            }
        } else {
            ChatUtil.sendEx(sys, level, type, oper, first, remark, "#FF0000");
        }
    }
}
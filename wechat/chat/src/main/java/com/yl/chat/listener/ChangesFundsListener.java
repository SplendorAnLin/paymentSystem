package com.yl.chat.listener;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.chat.Constant;
import com.yl.chat.service.MessageService;
import com.yl.chat.service.UserService;
import com.yl.chat.utils.DictUtils;
import com.yl.chat.wecaht.model.User;
import com.yl.mq.consumer.client.ConsumerMessage;
import com.yl.mq.consumer.client.listener.ConsumerMessageListener;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资金变动监听
 *
 * @author zk
 * @since 2017年4月24日
 */
@Service("changesFundsListener")
public class ChangesFundsListener implements ConsumerMessageListener {

    private static Logger logger = LoggerFactory.getLogger(ChangesFundsListener.class);

    @Resource
    UserService userService;

    @Resource
    MessageService messageService;

    @Override
    public void consumerListener(ConsumerMessage consumerMessage) {
        if (consumerMessage.getBody() == null || "".equals(consumerMessage.getBody())) {
            return;
        }
        if ("CHANGES_FUNDS".equals(consumerMessage.getTags())) {
            Map<String, Object> params = null;
            try {
                params = JsonUtils.toObject(consumerMessage.getBody(), new TypeReference<Map<String, Object>>() {
                });
            } catch (Exception e1) {
                logger.error("MQ消息ID:[{}],错误信息:[{}]", consumerMessage.getMsgId(), e1);
                throw new RuntimeException(e1);
            }
            String pushId = params.get("userNo").toString();
            String type = params.get("type").toString();
            String balance = params.get("balance").toString();
            params = JsonUtils.toObject(JsonUtils.toJsonString(params.get("info")), new TypeReference<Map<String, Object>>() {
            });
            params.put("type", type);
            params = DictUtils.mapOfDict(params, "BUSINESS_TYPE", "type");
            List<User> user = userService.findOpenIdByCustomerNo(pushId);
            if (user.size() > 0) {
                Map<String, String> info = new HashMap<>();
                boolean fundType = "SUBTRACT".equals(params.get("symbol").toString());
                String fundTypeStr = fundType ? "出账" : "到账";
                String adCharge = sum(params.get("amount"), params.get("fee"), fundType);
                SimpleDateFormat time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String date = time.format(params.get("transDate"));
                info.put("first", "您有一笔" + params.get("type_CN").toString() + fundTypeStr + "：");
                info.put("date", date);
                info.put("adCharge", "￥" + adCharge);
                info.put("cashBalance", "￥" + balance);
                info.put("remark", "点击立即查阅您的帐户财务记录");
                if (fundType) {
                    info.put("color", "#FF0000");
                } else {
                    info.put("color", "#32CD32");
                }
                for (User userInfo : user) {
                    messageService.send(userInfo.getOpenid(), Constant.templeFunds, "http://pay.feiyijj.com/customer", info, true);
                }
            }
        }
        return;
    }

    public String sum(Object num, Object num2, boolean isPuls) {
        if (num2 != null && "".equals(num2)) {
            if (isPuls) {
                Double result = Double.valueOf(num.toString()) + Double.valueOf(num2.toString());
                return result.toString();
            } else {
                Double result = Double.valueOf(num.toString()) - Double.valueOf(num2.toString());
                return result.toString();
            }
        } else {
            return num.toString();
        }
    }
}
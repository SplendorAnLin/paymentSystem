package com.yl.chat.task;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.chat.enums.MessageStatus;
import com.yl.chat.result.MsgResult;
import com.yl.chat.service.MessageService;
import com.yl.chat.wecaht.model.WaitInfo;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

public class ChatNoticeTask {

    private static final Logger logger = LoggerFactory.getLogger(ChatNoticeTask.class);

    @Resource
    MessageService messageService;

    public void execute() {
        logger.info("====定时微信通知开始====");
        List<WaitInfo> msgInfo = messageService.findWaitInfo();
        if (msgInfo != null && msgInfo.size() > 0) {
            for (WaitInfo waitInfo : msgInfo) {
                Map<String, String> info = JsonUtils.toObject(waitInfo.getInfo(), new TypeReference<Map<String, String>>() {
                });
                MsgResult result = messageService.send(waitInfo.getOpenId(), waitInfo.getTempleId(), waitInfo.getUrl(), info, waitInfo.isDiyColor());
                if (result.getErrcode() == 0) {
                    waitInfo.setStatus(MessageStatus.SUCCESS);
                    waitInfo.setRemark(result.getErrmsg());
                } else {
                    waitInfo.setStatus(MessageStatus.FAIL);
                    waitInfo.setRemark(result.getErrmsg());
                }
                messageService.upWatiInfo(waitInfo);
            }
        }
        logger.info("====定时微信通知结束====");
    }
}
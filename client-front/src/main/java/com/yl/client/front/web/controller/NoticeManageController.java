package com.yl.client.front.web.controller;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.client.front.common.DistinParams;
import com.yl.client.front.common.ResponseMsg;
import com.yl.client.front.model.Message;
import com.yl.client.front.model.NoticeConfig;
import com.yl.client.front.service.MessageService;
import com.yl.client.front.service.NoticeConfigService;
import net.sf.json.JSONObject;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class NoticeManageController{
    private static Logger log = LoggerFactory.getLogger(NoticeManageController.class);
    @Resource
    NoticeConfigService noticeConfigService;
    @Resource
    MessageService messageService;

    @RequestMapping("addNoticeConfig")
    public void addNoticeConfig(HttpServletRequest request, PrintWriter pw) throws IOException {
        Map info=DistinParams.manageSign(request,pw);

        NoticeConfig noticeConfig=JsonUtils.toObject(JsonUtils.toJsonString(info), new TypeReference<NoticeConfig>(){});
        noticeConfigService.createNoticeConfig(noticeConfig);
        ResponseMsg.msgNotSuccess(true,pw);
    }
    @RequestMapping("upNoticeConfig")
    public void upNoticeConfig(HttpServletRequest request, PrintWriter pw) throws IOException{
        Map info=DistinParams.manageSign(request,pw);
        NoticeConfig noticeConfig=JsonUtils.toObject(JsonUtils.toJsonString(info), new TypeReference<NoticeConfig>(){});
        noticeConfigService.updateNoticeConfig(noticeConfig);
        ResponseMsg.msgNotSuccess(true,pw);
    }
    @RequestMapping("findNoticeConfig")
    public void findNoticeConfig(HttpServletRequest request, PrintWriter pw) throws IOException{
        Map info=DistinParams.manageSign(request,pw);
        NoticeConfig noticeConfig=JsonUtils.toObject(JsonUtils.toJsonString(info), new TypeReference<NoticeConfig>(){});
        noticeConfig=noticeConfigService.findNoticeConfig(noticeConfig.getId());
        ResponseMsg.msgNotSuccess(noticeConfig,pw);
    }
    @RequestMapping("findNoticeConfigByOem")
    public void findNoticeConfigByOem(HttpServletRequest request, PrintWriter pw) throws IOException{
        Map info=DistinParams.manageSign(request,pw);
        NoticeConfig noticeConfig=JsonUtils.toObject(JsonUtils.toJsonString(info), new TypeReference<NoticeConfig>(){});
        noticeConfig=noticeConfigService.findNoticeConfigByOem(noticeConfig.getOem());
        ResponseMsg.msgNotSuccess(noticeConfig,pw);
    }
    @RequestMapping("findAllNoticeConfigByInfo")
    public void findAllNoticeConfigByInfo(HttpServletRequest request, PrintWriter pw) throws IOException{
        Map info=DistinParams.manageSign(request,pw);
        Page page=new Page();
        Object totalPage=info.get("totalPage");
        Object currentPage=info.get("currentPage");
        page.setTotalPage(totalPage!=null?(int)totalPage:1);
        page.setTotalPage(currentPage!=null&&(int)currentPage>1?(int)currentPage:1);
        List<NoticeConfig> noticeConfigs =noticeConfigService.findAllNoticeConfigByInfo(info,page);
        Map<String,Object> res=new HashMap<>();
        res.put("page",page);
        res.put("noticeConfigs",noticeConfigs);
        ResponseMsg.msgNotSuccess(res,pw);
    }
    @SuppressWarnings("rawtypes")
	@RequestMapping("sendAllNotice")
    public void sendNotice(HttpServletRequest request, PrintWriter pw) throws IOException{
        Map info=DistinParams.manageSign(request,pw);
        info.remove("sys");
        info.remove("oper");
        Message message=JsonUtils.toObject(JsonUtils.toJsonString(info), new TypeReference<Message>(){});
        try {
            messageService.pushAllMSG(message);
            ResponseMsg.msgNotSuccess(true,pw);
        }catch (Exception e){
            log.error("发送消息错误,{}",e);
        }

    }
}

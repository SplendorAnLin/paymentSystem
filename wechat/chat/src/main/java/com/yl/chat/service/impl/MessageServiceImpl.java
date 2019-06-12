package com.yl.chat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.chat.conn.MessageConn;
import com.yl.chat.dao.WaitInfoDao;
import com.yl.chat.enums.MessageStatus;
import com.yl.chat.message.BaseTemplet;
import com.yl.chat.result.MsgResult;
import com.yl.chat.service.MessageService;
import com.yl.chat.utils.ChatUtil;
import com.yl.chat.wecaht.model.WaitInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 发送模板消息
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017年6月13日
 */
@Service("messageService")
public class MessageServiceImpl extends MessageConn implements MessageService {

    private static Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Resource
    WaitInfoDao waitInfoDao;

    public List<WaitInfo> findWaitInfo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return waitInfoDao.findWaitInfo(sdf.format(new Date()), MessageStatus.WAIT_SEND.toString());
    }

    public void send(String openid, String templeId, String url, Map<String, String> info, boolean isDiyColor, String date) {
        WaitInfo waitInfo = new WaitInfo();
        waitInfo.setOpenId(openid);
        waitInfo.setTempleId(templeId);
        waitInfo.setUrl(url);
        waitInfo.setDiyColor(isDiyColor);
        waitInfo.setInfo(JsonUtils.toJsonString(info));
        waitInfo.setSendTime(date);
        waitInfo.setStatus(MessageStatus.WAIT_SEND);
        waitInfoDao.saveWaitInfo(waitInfo);
    }

    public MsgResult send(String openid, String templeId, String url, Map<String, String> info, boolean isDiyColor) {
        MsgResult result = null;
        TreeMap<String, TreeMap<String, String>> mremark = new TreeMap<String, TreeMap<String, String>>();
        if (isDiyColor) {
            String color = info.get("color").toString();
            info.remove("color");
            for (String key : info.keySet()) {
                mremark.put(key, BaseTemplet.ThempleItem(info.get(key).toString(), color));
            }
            info.put("color", color);
        } else {
            for (String key : info.keySet()) {
                mremark.put(key, BaseTemplet.ThempleItem(info.get(key).toString()));
            }
        }
        result = getCommonParams(openid, templeId, url, mremark);
        log.info("微信模版：【{}】通知推送openid:{},信息内容:{},结果:{}", templeId, openid, info, JsonUtils.toJsonString(result));
        return result;
    }

    public void send(String openid, String templeId, String url, Map<String, String> info, String date) {
        send(openid, templeId, url, info, false, date);
    }

    public MsgResult send(String openid, String templeId, String url, Map<String, String> info) {
        return send(openid, templeId, url, info, false);
    }

    /**
     * 设置公共参数并将其转换为json格式的数据
     *
     * @return MsgResult.errcode ==0 && MsgResult.errmsg=ok 表示成功
     */
    private MsgResult getCommonParams(String openid, String templeId, String url, TreeMap<String, TreeMap<String, String>> methodParams) {
        MsgResult result = null;
        TreeMap<String, Object> params = new TreeMap<String, Object>();
        params.put("touser", openid);
        params.put("template_id", templeId);
        params.put("url", url);
        params.put("data", methodParams);
        String data = JSONObject.toJSONString(params, true);
        result = sendTemplate(ChatUtil.getSysAccessToken(), data);
        return result;
    }

    @Override
    public void upWatiInfo(WaitInfo waitInfo) {
        waitInfoDao.upWatiInfo(waitInfo);
    }
}
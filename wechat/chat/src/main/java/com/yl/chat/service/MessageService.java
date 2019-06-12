package com.yl.chat.service;

import com.yl.chat.result.MsgResult;
import com.yl.chat.wecaht.model.WaitInfo;
import java.util.List;
import java.util.Map;

/**
 * 微信模板
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017年4月21日
 */
public interface MessageService {
    /**
     * @param openid     收信人
     * @param templeId   模版ID
     * @param url        信息详情
     * @param info       模版参数
     * @param isDiyColor 是否自定义颜色
     * @return
     */
    public MsgResult send(String openid, String templeId, String url, Map<String, String> info, boolean isDiyColor);

    /**
     * @param openid   收信人
     * @param templeId 模版ID
     * @param url      信息详情
     * @param info     模版参数
     * @return
     */
    public MsgResult send(String openid, String templeId, String url, Map<String, String> info);

    /**
     * @param openid     收信人
     * @param templeId   模版ID
     * @param url        信息详情
     * @param info       模版参数
     * @param isDiyColor 是否自定义颜色
     * @param date       发送时间
     * @return
     */
    public void send(String openid, String templeId, String url, Map<String, String> info, boolean isDiyColor, String date);

    /**
     * @param openid     收信人
     * @param templeId   模版ID
     * @param url        信息详情
     * @param info       模版参数
     * @param isDiyColor 是否自定义颜色
     * @param date       发送时间
     * @return
     */
    public void send(String openid, String templeId, String url, Map<String, String> info, String date);

    /**
     * 获取未发送信息
     *
     * @return
     */
    public List<WaitInfo> findWaitInfo();

    public void upWatiInfo(WaitInfo waitInfo);
}
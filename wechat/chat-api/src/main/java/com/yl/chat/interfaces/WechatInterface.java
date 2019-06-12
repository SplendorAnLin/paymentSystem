package com.yl.chat.interfaces;

import java.util.Map;


/**
 * 微信菜单接口
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017年4月21日
 */
public interface WechatInterface {

    public int createMenu(String menu) throws Exception;

    public Map<String, Object> findMenu();

    /**
     * 发送异常信息到微信
     *
     * @param sys   异常的系统
     * @param level 异常等级
     * @param type  异常类型
     * @param oper  操作人，相关帐号
     */
    public void sendEX(String sys, String level, String type, String oper);

    /**
     * 发送异常信息到微信
     *
     * @param sys    异常的系统
     * @param level  异常等级
     * @param type   异常类型
     * @param oper   操作人，相关帐号
     * @param first  头部信息
     * @param remark 尾部备注
     */
    public void sendEX(String sys, String level, String type, String oper, String first, String remark);

    /**
     * 发送异常信息到微信
     *
     * @param sys    异常的系统
     * @param level  异常等级
     * @param type   异常类型
     * @param oper   操作人，相关帐号
     * @param first  头部信息
     * @param remark 尾部备注
     * @param date   定时发送时间
     */
    public void sendEX(String sys, String level, String type, String oper, String first, String remark, String date);
}
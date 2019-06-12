package com.yl.chat.result;

/**
 * 消息 返回的结果数
 *
 * @author Andy
 */
public class MsgResult {

    private int errcode; // 错误码

    private String errmsg; // 错误信息

    private String msg_id; // 消息id

    private String template_id; // 模板消息id

    private String msgid;        //消息id(发送模板消息)


    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String templateId) {
        template_id = templateId;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msgId) {
        msg_id = msgId;
    }

    @Override
    public String toString() {
        return "MsgResult [errcode=" + errcode + ", errmsg=" + errmsg
                + ", msg_id=" + msg_id + ", msgid=" + msgid + ", template_id="
                + template_id + "]";
    }
}
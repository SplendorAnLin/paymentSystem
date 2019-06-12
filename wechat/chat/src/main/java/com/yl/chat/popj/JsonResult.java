package com.yl.chat.popj;

/**
 * 错误消息
 *
 * @author Andy
 */
public class JsonResult extends ResultState implements java.io.Serializable {

    private String short_url; // 短链接 (推广支持中的长连接转换为短链接时使用)

    private String kf_account; // 正在接待的客服，为空表示没有人在接待

    private long createtime; // 会话接入的时间

    public String getKf_account() {
        return kf_account;
    }

    public void setKf_account(String kfAccount) {
        kf_account = kfAccount;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public String getShort_url() {
        return short_url;
    }

    public void setShort_url(String shortUrl) {
        short_url = shortUrl;
    }
}
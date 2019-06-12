package com.yl.dpay.front.bean;

/**
 * 请求报文头
 *
 * @author AnLin
 * @since 2017/7/10
 */
public class RequestBody {

    /**
     * 交易码
     */
    private String _TransactionId;
    /**
     * 交易时间
     */
    private String _MCHTimestamp;
    /**
     * 渠道端流水号
     */
    private String _MCHJnlNo;

    public String get_TransactionId() {
        return _TransactionId;
    }

    public void set_TransactionId(String _TransactionId) {
        this._TransactionId = _TransactionId;
    }

    public String get_MCHTimestamp() {
        return _MCHTimestamp;
    }

    public void set_MCHTimestamp(String _MCHTimestamp) {
        this._MCHTimestamp = _MCHTimestamp;
    }

    public String get_MCHJnlNo() {
        return _MCHJnlNo;
    }

    public void set_MCHJnlNo(String _MCHJnlNo) {
        this._MCHJnlNo = _MCHJnlNo;
    }

    public RequestBody(String _TransactionId, String _MCHTimestamp, String _MCHJnlNo) {
        this._TransactionId = _TransactionId;
        this._MCHTimestamp = _MCHTimestamp;
        this._MCHJnlNo = _MCHJnlNo;
    }

    public RequestBody() {
    }
}

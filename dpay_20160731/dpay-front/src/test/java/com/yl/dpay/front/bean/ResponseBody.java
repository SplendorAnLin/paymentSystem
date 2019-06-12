package com.yl.dpay.front.bean;

/**
 * 响应报文头
 *
 * @author AnLin
 * @since 2017/7/10
 */
public class ResponseBody {

    /**
     * 整合平台流水号
     */
    private String _MCSJnlNo;
    /**
     * 响应代码
     */
    private String _RejCode;
    /**
     * 响应信息
     */
    private String _RejMsg;
    /**
     * 渠道流水号
     */
    private String _MCHJnlNo;
    /**
     * 交易码
     */
    private String _TransactionId;
    /**
     * 渠道交易时间
     */
    private String _MCHTimestamp;

    public String get_MCSJnlNo() {
        return _MCSJnlNo;
    }

    public void set_MCSJnlNo(String _MCSJnlNo) {
        this._MCSJnlNo = _MCSJnlNo;
    }

    public String get_RejCode() {
        return _RejCode;
    }

    public void set_RejCode(String _RejCode) {
        this._RejCode = _RejCode;
    }

    public String get_RejMsg() {
        return _RejMsg;
    }

    public void set_RejMsg(String _RejMsg) {
        this._RejMsg = _RejMsg;
    }

    public String get_MCHJnlNo() {
        return _MCHJnlNo;
    }

    public void set_MCHJnlNo(String _MCHJnlNo) {
        this._MCHJnlNo = _MCHJnlNo;
    }

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

}

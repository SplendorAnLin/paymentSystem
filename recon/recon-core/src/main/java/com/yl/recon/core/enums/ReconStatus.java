package com.yl.recon.core.enums;

/**
 * 对账状态
 *
 * @author AnLin
 * @since 2017/6/21
 */
public enum ReconStatus {

    /**
     * 系统平账
     */
    SYS_FLAT_ACC,
    /**
     * 人工平账
     */
    MAN_FLAT_ACC,
    /**
     * 差错处理中
     */
    ERR_HANDLING,
    /**
     * 对账失败
     */
    RECONCILIATION_FAILED,
    /**
     * 对账记录
     */
    RECONCILIATION_RECORD;
}
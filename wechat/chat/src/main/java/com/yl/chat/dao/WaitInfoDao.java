package com.yl.chat.dao;

import com.yl.chat.wecaht.model.WaitInfo;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 微信信息
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017年4月21日
 */
public interface WaitInfoDao {
    /**
     * 保存待发送信息
     *
     * @param waitInfo
     */
    void saveWaitInfo(WaitInfo waitInfo);

    /**
     * 查询未发送信息
     *
     * @return
     */
    List<WaitInfo> findWaitInfo(@Param("time") String time, @Param("status") String status);

    void upWatiInfo(@Param("info") WaitInfo waitInfo);
}
package com.yl.payinterface.core.dao;

import com.lefu.commons.utils.Page;
import com.yl.payinterface.core.model.AliPayReconAcc;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 支付宝对账 账户数据访问接口
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/8/18
 */
public interface AliPayReconAccDao {

	/**
	 * 新增对账账户
	 *
	 * @author AnLin
	 * @version V1.0.0
	 * @since 2018/8/18
	 */
	void save(AliPayReconAcc aliPayReconAcc);

	/**
	 * 修改对账账户
	 * @param aliPayReconAcc
	 */
	void update(AliPayReconAcc aliPayReconAcc);

	/**
	 * 定时任务组
	 *
	 * @author AnLin
	 * @version V1.0.0
	 * @since 2018/8/18
	 */
	List<AliPayReconAcc> findByTask();

    /**
     * 修改状态
     * @param acc
     * @param status
     */
	void updateStatus(@Param("acc") String acc, @Param("status") String status);

    /**
     * 分页查询所有对账账户
     * @param page
     * @param map
     * @return
     */
    Page queryAll(@Param("page") Page page, @Param("params") Map<String, Object> map);
}
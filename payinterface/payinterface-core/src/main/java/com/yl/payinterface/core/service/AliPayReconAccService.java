package com.yl.payinterface.core.service;

import com.lefu.commons.utils.Page;
import com.yl.payinterface.core.model.AliPayReconAcc;
import java.util.List;
import java.util.Map;

/**
 * 支付宝对账 账户服务接口
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/8/18
 */
public interface AliPayReconAccService {

    /**
     * 新增对账账户
     * @param aliPayReconAcc
     */
	void save(AliPayReconAcc aliPayReconAcc);

    /**
     * 修改对账账户
     * @param aliPayReconAcc
     */
	void update(AliPayReconAcc aliPayReconAcc);

    /**
     * 定时任务组
     * @return
     */
	List<AliPayReconAcc> findByTask();

    /**
     * 修改状态
     * @param acc
     * @param status
     */
	void updateStatus(String acc, String status);

	/**
	 * 分页查询所有对账账户
	 * @param page
	 * @param map
	 * @return
	 */
	Page queryAll(Page page, Map<String, Object> map);
}
package com.yl.payinterface.core.service.impl;

import com.lefu.commons.utils.Page;
import com.yl.payinterface.core.dao.AliPayReconAccDao;
import com.yl.payinterface.core.model.AliPayReconAcc;
import com.yl.payinterface.core.service.AliPayReconAccService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 支付宝对账 账户服务接口实现
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/8/18
 */
@Service("aliPayReconAccService")
public class AliPayReconAccServiceImpl implements AliPayReconAccService {

	@Resource
	private AliPayReconAccDao aliPayReconAccDao;

	@Override
	public void save(AliPayReconAcc aliPayReconAcc) {
        aliPayReconAcc.setOptimistic(1L);
		aliPayReconAccDao.save(aliPayReconAcc);
	}

	@Override
	public List<AliPayReconAcc> findByTask() {
		return aliPayReconAccDao.findByTask();
	}

	@Override
	public void updateStatus(String acc, String status) {
        aliPayReconAccDao.updateStatus(acc, status);
	}

	@Override
	public void update(AliPayReconAcc aliPayReconAcc) {
        aliPayReconAccDao.update(aliPayReconAcc);
	}

	@Override
	public Page queryAll(Page page, Map<String, Object> map) {
		return aliPayReconAccDao.queryAll(page, map);
	}
}
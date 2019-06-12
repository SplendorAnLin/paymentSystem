package com.yl.recon.core.service.impl;

import com.yl.recon.core.Constant;
import com.yl.recon.core.entity.ReconOrder;
import com.yl.recon.core.enums.ReconStatus;
import com.yl.recon.core.enums.ReconType;
import com.yl.recon.core.mybatis.mapper.ReconOrderMapper;
import com.yl.recon.core.service.ReconOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 对账订单访问接口实现
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年9月11日
 */
@Service("reconOrderService")
public class ReconOrderServiceImpl implements ReconOrderService {


	@Resource
	ReconOrderMapper reconOrderMapper;


	/**
	 * 初始化对账结果(对账订单)
	 *
	 * @param params
	 * @return
	 */
	@Override
	public ReconOrder initReconOrder(Map <String, Object> params) {
		ReconOrder reconOrder = new ReconOrder();
		reconOrder.setRepeatNumA(0);
		reconOrder.setNumsA(0);
		reconOrder.setAmountA(0.0D);
		reconOrder.setFailNumsA(0);
		reconOrder.setFailAmountA(0.0D);
		reconOrder.setRepeatNumB(0);
		reconOrder.setNumsB(0);
		reconOrder.setAmountB(0.0D);
		reconOrder.setFailNumsB(0);
		reconOrder.setFailAmountB(0.0D);
		reconOrder.setAmountErrNum(0);
		reconOrder.setVersion(System.currentTimeMillis() / 1000);
		reconOrder.setReconStatus(ReconStatus.ERR_HANDLING);
		reconOrder.setCreateTime(new Date());
		try {
			reconOrder.setReconDate((Date) params.get(Constant.RECON_DATE));
			reconOrder.setReconType((ReconType) params.get(Constant.RECON_TYPE));
			reconOrderMapper.insertSelective(reconOrder);
			return reconOrder;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
package com.yl.online.trade.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.lefu.commons.utils.Page;
import com.yl.online.model.model.PartnerRouter;
import com.yl.online.trade.dao.PartnerRouterDao;
import com.yl.online.trade.dao.mapper.PartnerRouterMapper;
import com.yl.online.trade.utils.CodeBuilder;

/**
 * 商户路由服务数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
@Repository("partnerRouterDao")
public class PartnerRouterDaoImpl implements PartnerRouterDao {

	@Resource
	private PartnerRouterMapper partnerRouterMapper;

	@Override
	public void createPartnerRouter(PartnerRouter partnerRouter) {
		partnerRouter.setCode(CodeBuilder.build("PR", "yyyyMMdd", 6));
		partnerRouter.setVersion(System.currentTimeMillis());
		partnerRouter.setCreateTime(new Date());
		partnerRouterMapper.createPartnerRouter(partnerRouter);
	}

	@Override
	public Page queryPartnerRouterByPage(Page page) {
		return partnerRouterMapper.queryPartnerRouterByPage(page);
	}

	@Override
	public PartnerRouter queryPartnerRouterByCode(String code) {
		return partnerRouterMapper.queryPartnerRouterByCode(code);
	}

	@Override
	public void updatePartnerRouter(PartnerRouter partnerRouter) {
		partnerRouter.setVersion(System.currentTimeMillis());
		partnerRouterMapper.updatePartnerRouter(partnerRouter);
	}

	@Override
	public PartnerRouter queryPartnerRouterBy(String partnerRole, String partnerCode) {
		return partnerRouterMapper.queryPartnerRouterBy(partnerRole, partnerCode);
	}

	@Override
	public List<PartnerRouter> findAllPartnerRouter(Page page,Map<String, Object> params) {
		return partnerRouterMapper.findAllPartnerRouter(page, params);
	}

	@Override
	public List<Map<String, Object>> queryCustRouterByCustomerNo(String customerNo) {
		return partnerRouterMapper.queryCustRouterByCustomerNo(customerNo);
	}
	

}

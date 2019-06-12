package com.yl.recon.core.service.impl;

import com.lefu.commons.utils.lang.StringUtils;
import com.yl.recon.api.core.bean.MyInterfaceInfoBean;
import com.yl.recon.core.Constant;
import com.yl.recon.core.context.DataBaseSourceType;
import com.yl.recon.core.context.DataSourceContextHolder;
import com.yl.recon.core.entity.ReconOrder;
import com.yl.recon.core.entity.order.TotalOrder;
import com.yl.recon.core.entity.order.TotalOrderExample;
import com.yl.recon.core.enums.ReconExceptionType;
import com.yl.recon.core.enums.ReconFileType;
import com.yl.recon.core.mybatis.mapper.CommonReconMapper;
import com.yl.recon.core.mybatis.mapper.TotalOrderMapper;
import com.yl.recon.core.service.CommonReconService;
import com.yl.utils.math.MyMathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 聚合支付有限公司
 *
 * @author 邱健
 * @version V1.0.0
 * @create 2018年01月08
 * @desc 对账服务公共处理类
 **/
@Service("commonReconService")
public class CommonReconServiceImpl implements CommonReconService {

	private static Logger logger = LoggerFactory.getLogger(CommonReconServiceImpl.class);

	@Resource
	private TotalOrderMapper totalOrderMapper;

	@Resource
	private CommonReconMapper commonReconMapper;

	/**
	 * 组装单边金额和笔数
	 *
	 * @param reconOrder
	 * @param amount
	 * @param isAorB
	 */
	@Override
	public void installFailAmount(ReconOrder reconOrder, Double amount, String isAorB) {
		if (Constant.A.equals(isAorB)) {
			reconOrder.setFailAmountA(MyMathUtils.add(reconOrder.getFailAmountA(), amount));
			reconOrder.setFailNumsA(reconOrder.getFailNumsA() + 1);
		} else if (Constant.B.equals(isAorB)) {
			reconOrder.setFailAmountB(MyMathUtils.add(reconOrder.getFailAmountB(), amount));
			reconOrder.setFailNumsB(reconOrder.getFailNumsB() + 1);
		}
	}


	/**
	 * 组装失败原因
	 *
	 * @param reconOrder
	 * @param reconExceptionType
	 */
	@Override
	public void installFailReason(ReconOrder reconOrder, ReconExceptionType reconExceptionType) {
		if (StringUtils.isNotBlank(reconOrder.getFailureReason())) {
			if (!reconOrder.getFailureReason().contains(reconExceptionType.getRemark())) {
				reconOrder.setFailureReason(reconOrder.getFailureReason().concat("|").concat(reconExceptionType.getRemark()));
			}
		} else {
			reconOrder.setFailureReason(reconExceptionType.getRemark());
		}
	}


	/**
	 * 获取对账文件里面的总金额和总笔数
	 *
	 * @param params
	 * @param reconFileType
	 * @return
	 */
	@Override
	public TotalOrder getTotalOrdersByType(Map <String, Object> params, ReconFileType reconFileType) {
		TotalOrder totalOrder = new TotalOrder();
		double totalAmount = 0D;
		double totalFee = 0D;
		int totalNum = 0;
		TotalOrderExample TotalOrderExample = new TotalOrderExample();
		com.yl.recon.core.entity.order.TotalOrderExample.Criteria criteria = TotalOrderExample.createCriteria();
		criteria.andReconFileTypeEqualTo(reconFileType.name());
		criteria.andReconDateEqualTo((Date) params.get(Constant.RECON_DATE));
		TotalOrderExample.setOrderByClause(" CREATE_TIME DESC ");
		List <TotalOrder> list = totalOrderMapper.selectByExample(TotalOrderExample);

		for (TotalOrder order : list) {
			if (null != order) {
				totalAmount = MyMathUtils.add(totalAmount, Double.parseDouble(order.getTotalAmount()));
				totalFee = MyMathUtils.add(totalFee, Double.parseDouble(order.getTotalFee()));
				totalNum += Integer.parseInt(order.getTotalNum());
			}
		}

		totalOrder.setTotalAmount(String.valueOf(totalAmount));
		totalOrder.setTotalFee(String.valueOf(totalAmount));
		totalOrder.setTotalNum(String.valueOf(totalNum));

		return totalOrder;
	}

	/**
	 * 查询有效的接口编号和名称
	 *
	 * @return
	 */
	@Override
	public List <MyInterfaceInfoBean> queryInterfaceInfo() {
		List <MyInterfaceInfoBean> myInterfaceInfoBeans = new ArrayList <>();
		try {
			//切为payinterface数据源
			DataSourceContextHolder.setDataSourceType(DataBaseSourceType.PAYINTERFACE);
			myInterfaceInfoBeans = commonReconMapper.queryInterfaceInfo();
		} catch (Exception e) {
			logger.error("查询接口编号和接口名称错误", e);
		} finally {
			//切回默认数据源
			DataSourceContextHolder.clearDataSourceTypeType();
		}
		return myInterfaceInfoBeans;
	}

	/**
	 * 判断对应的通道对账文件是否存在
	 *
	 * @param code
	 * @return
	 */
	@Override
	public boolean checkInterfaceCode(String code) {
		try {
			int num = commonReconMapper.checkInterfaceCode(code);
			if (num > 0) {
				//存在，不可以添加
				return true;
			}
		} catch (Exception e) {
			logger.error("判断对应的通道对账文件是否存在错误", e);
		}
		return false;
	}


}

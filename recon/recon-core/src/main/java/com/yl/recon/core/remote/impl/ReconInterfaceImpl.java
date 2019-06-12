package com.yl.recon.core.remote.impl;

import com.yl.recon.api.core.bean.*;
import com.yl.recon.api.core.bean.order.*;
import com.yl.recon.core.Constant;
import com.yl.recon.core.enums.ReconFileType;
import com.yl.recon.core.enums.SystemCode;

import java.util.Date;

import com.lefu.commons.utils.Page;
import com.yl.recon.api.core.remote.ReconInterface;
import com.yl.recon.core.mybatis.mapper.ReconExceptionMapper;
import com.yl.recon.core.mybatis.mapper.ReconFileInfoMapper;
import com.yl.recon.core.mybatis.mapper.ReconOrderMapper;
import com.yl.recon.core.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2018年01月09
 */
@Service("reconInterface")
public class ReconInterfaceImpl implements ReconInterface {


	@Value("${recon.filePath}")
	private String filePath;

	@Resource
	private ReconOrderMapper reconOrderMapper;
	@Resource
	private ReconFileInfoMapper reconFileInfoMapper;
	@Resource
	private ReconFileInfoExtendService reconFileInfoExtendService;
	@Resource
	private ReconFileInfoService reconFileInfoService;
	@Resource
	private ReconExceptionMapper reconExceptionMapper;
	@Resource
	private CommonReconService commonReconService;
	@Resource
	private TradeOrderService tradeOrderService;
	@Resource
	private RemitOrderService remitOrderService;
	@Resource
	private RealAuthOrderService realAuthOrderService;
	@Resource
	private PayinterfaceOrderService payinterfaceOrderService;
	@Resource
	private AccountOrderService accountOrderService;
	@Resource
	private BaseBankChannelOrderService baseBankChannelOrderService;


	/**
	 * 分页查询对账单
	 *
	 * @param params
	 * @param page
	 * @return
	 */
	@Override

	public Page findBy(Map <String, Object> params, Page page) {
		List <com.yl.recon.api.core.bean.ReconOrder> reconOrders = reconOrderMapper.findAll(page, params);
		page.setObject(reconOrders);
		return page;
	}

	/**
	 * 导出对账单
	 *
	 * @param params
	 * @return
	 */
	@Override
	public List <ReconOrder> reconOrderExport(Map <String, Object> params) {
		List <com.yl.recon.api.core.bean.ReconOrder> reconOrders = reconOrderMapper.queryAll(params);
		return reconOrders;
	}

	/**
	 * 查询接口信息
	 *
	 * @return
	 */
	@Override
	public List <MyInterfaceInfoBean> queryInterfaceInfo() {
		return this.commonReconService.queryInterfaceInfo();
	}

	/**
	 * 判断对应的通道对账文件是否存在
	 *
	 * @param code
	 * @return
	 */
	@Override
	public boolean checkInterfaceCode(String code) {
		return this.commonReconService.checkInterfaceCode(code);
	}

	/**
	 * 新增通道对账文件
	 */
	@Override
	public void reconFileInfoAdd(ReconFileInfo reconFileInfoBean) {
		Date createDate = new Date();
		//创建文件信息
		com.yl.recon.core.entity.fileinfo.ReconFileInfoExtend reconFileInfoExtend = createReconFileInfoExtend(reconFileInfoBean, createDate);
		//创建文件扩展信息
		createReconFileInfo(reconFileInfoBean, createDate, reconFileInfoExtend);
	}

	/**
	 * 创建文件信息
	 *
	 * @param reconFileInfoBean
	 * @param createDate
	 * @param reconFileInfoExtend
	 */
	private void createReconFileInfo(ReconFileInfo reconFileInfoBean, Date createDate, com.yl.recon.core.entity.fileinfo.ReconFileInfoExtend reconFileInfoExtend) {
		com.yl.recon.core.entity.fileinfo.ReconFileInfo reconFileInfo = new com.yl.recon.core.entity.fileinfo.ReconFileInfo();
		reconFileInfo.setExtendCode(0L);
		reconFileInfo.setFileName(reconFileInfoBean.getFileName());
		reconFileInfo.setSystemCode(SystemCode.OTHER);
		reconFileInfo.setReconFileType(ReconFileType.BANK);
		reconFileInfo.setValid(Constant.IS_VALID_1);
		reconFileInfo.setIsBankChannel(Constant.IS_VALID_1);
		reconFileInfo.setFilePath(filePath);
		reconFileInfo.setRemark(reconFileInfoBean.getRemark());
		reconFileInfo.setCode(0L);
		reconFileInfo.setVersion(0L);
		reconFileInfo.setCreateTime(createDate);
		reconFileInfo.setExtendCode(reconFileInfoExtend.getCode());
		this.reconFileInfoService.insertSelective(reconFileInfo);
	}

	/**
	 * 创建文件扩展信息
	 *
	 * @param reconFileInfoBean
	 * @param createDate
	 * @return
	 */
	private com.yl.recon.core.entity.fileinfo.ReconFileInfoExtend createReconFileInfoExtend(ReconFileInfo reconFileInfoBean, Date createDate) {
		ReconFileInfoExtend extendBean = reconFileInfoBean.getReconFileInfoExtend();
		com.yl.recon.core.entity.fileinfo.ReconFileInfoExtend reconFileInfoExtend = new com.yl.recon.core.entity.fileinfo.ReconFileInfoExtend();
		reconFileInfoExtend.setHeader(extendBean.getHeader());
		reconFileInfoExtend.setFooter(extendBean.getFooter());
		reconFileInfoExtend.setTotalFlag(extendBean.getFooter().split(extendBean.getDelimiter())[0]);
		reconFileInfoExtend.setDelimiter(extendBean.getDelimiter());
		reconFileInfoExtend.setInterfaceInfoCode(extendBean.getInterfaceInfoCode());
		reconFileInfoExtend.setBankName(extendBean.getBankName());
		reconFileInfoExtend.setBankOrderCode(extendBean.getBankOrderCode());
		reconFileInfoExtend.setInterfaceOrderCode(extendBean.getInterfaceOrderCode());
		reconFileInfoExtend.setAmount(extendBean.getAmount());
		reconFileInfoExtend.setFee(extendBean.getFee());
		reconFileInfoExtend.setTransTime(extendBean.getTransTime());
		reconFileInfoExtend.setVersion(0L);
		reconFileInfoExtend.setCreateTime(createDate);
		this.reconFileInfoExtendService.insertSelective(reconFileInfoExtend);
		return reconFileInfoExtend;
	}

	/**
	 * 修改通道对账文件
	 *
	 * @param reconFileInfoBean
	 */
	@Override
	public void reconFileInfoModify(ReconFileInfo reconFileInfoBean) {

		try {
			// 赋值更新：更新文件扩展信息
			updateReconFileInfoExtendByPage(reconFileInfoBean);
			// 赋值更新：更新文件信息
			updateReconFileInfoByPage(reconFileInfoBean);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	/**
	 * 对账数据查询
	 *
	 * @param page
	 * @return
	 */
	@Override
	public Page reconOrderDataQuery(ReconOrderDataQueryBean reconOrderDataQueryBean, Page page) {
		switch (reconOrderDataQueryBean.getReconFileType()) {
			case PAYINTERFACE:
				//查询接口
				List <PayinterfaceOrder> payinterfaceOrders = payinterfaceOrderService.findAllPayInterfaceOrder(reconOrderDataQueryBean, page);
				page.setObject(payinterfaceOrders);
				break;
			case ACCOUNT:
				//查询账户
				List <AccountOrder> accountOrders = accountOrderService.findAllAccountOrder(reconOrderDataQueryBean, page);
				page.setObject(accountOrders);
				break;
			case BANK:
				//查询通道
				List <BaseBankChannelOrder> baseBankChannelOrders = baseBankChannelOrderService.findAllBaseBankChannelOrder(reconOrderDataQueryBean, page);
				page.setObject(baseBankChannelOrders);
				break;
			case ONLINE:
				//查询线上交易
				List <TradeOrder> tradeOrders = tradeOrderService.findAllTradeOrders(reconOrderDataQueryBean, page);
				page.setObject(tradeOrders);
				break;
			case REAL_AUTH:
				//查询实名认证
				List <RealAuthOrder> realAuthOrders = realAuthOrderService.findAllRealAuthOrders(reconOrderDataQueryBean, page);
				page.setObject(realAuthOrders);
				break;
			case DPAY:
				//查询代付
				List <RemitOrder> remitOrders = remitOrderService.findAllRemitOrders(reconOrderDataQueryBean, page);
				page.setObject(remitOrders);
				break;
			default:
				break;
		}
		return page;
	}

	/**
	 * 查询:实名认证对账数据
	 *
	 * @return
	 */
	@Override
	public List <RealAuthOrder> realAuthOrderDataExport(ReconOrderDataQueryBean reconOrderDataQueryBean) {
		List <RealAuthOrder> realAuthOrders = realAuthOrderService.findRealAuthOrders(reconOrderDataQueryBean);
		return realAuthOrders;
	}

	/**
	 * 查询:代付对账数据
	 *
	 * @param reconOrderDataQueryBean
	 * @return
	 */
	@Override
	public List <RemitOrder> remitOrderDataExport(ReconOrderDataQueryBean reconOrderDataQueryBean) {
		List <RemitOrder> remitOrders = remitOrderService.findRemitOrders(reconOrderDataQueryBean);
		return remitOrders;
	}

	/**
	 * 查询:交易订单对账数据
	 *
	 * @param reconOrderDataQueryBean
	 * @return
	 */
	@Override
	public List <TradeOrder> tradeOrderDataExport(ReconOrderDataQueryBean reconOrderDataQueryBean) {
		List <TradeOrder> tradeOrders = tradeOrderService.findTradeOrders(reconOrderDataQueryBean);
		return tradeOrders;
	}

	/**
	 * 查询:账户对账数据
	 *
	 * @param reconOrderDataQueryBean
	 * @return
	 */
	@Override
	public List <AccountOrder> accountOrderDataExport(ReconOrderDataQueryBean reconOrderDataQueryBean) {
		List <AccountOrder> accountOrders = accountOrderService.finAccountOrder(reconOrderDataQueryBean);
		return accountOrders;
	}

	/**
	 * 查询:接口对账数据
	 *
	 * @param reconOrderDataQueryBean
	 * @return
	 */
	@Override
	public List <PayinterfaceOrder> payinterfaceOrderDataExport(ReconOrderDataQueryBean reconOrderDataQueryBean) {
		List <PayinterfaceOrder> payinterfaceOrders = payinterfaceOrderService.findPayInterfaceOrder(reconOrderDataQueryBean);
		return payinterfaceOrders;
	}

	/**
	 * 查询:银行通道对账数据
	 *
	 * @param reconOrderDataQueryBean
	 * @return
	 */
	@Override
	public List <BaseBankChannelOrder> baseBankChannelOrderDataExport(ReconOrderDataQueryBean reconOrderDataQueryBean) {
		List <BaseBankChannelOrder> baseBankChannelOrders = baseBankChannelOrderService.findBaseBankChannelOrder(reconOrderDataQueryBean);
		return baseBankChannelOrders;
	}

	/**
	 * 赋值更新：更新文件信息
	 *
	 * @param reconFileInfoBean
	 */
	private void updateReconFileInfoByPage(ReconFileInfo reconFileInfoBean) {
		//从数据库查询出原数据
		com.yl.recon.core.entity.fileinfo.ReconFileInfo oldInfo = this.reconFileInfoService.selectByPrimaryKey(Long.parseLong(reconFileInfoBean.getCode()));
		if (StringUtils.isNotBlank(reconFileInfoBean.getFileName())) {
			oldInfo.setFileName(reconFileInfoBean.getFileName());
		}
		if (null != reconFileInfoBean.getValid()) {
			oldInfo.setValid(reconFileInfoBean.getValid());
		}
		if (StringUtils.isNotBlank(reconFileInfoBean.getRemark())) {
			oldInfo.setRemark(reconFileInfoBean.getRemark());
		}

		this.reconFileInfoService.update(oldInfo);
	}

	/**
	 * 赋值更新：更新文件扩展信息
	 *
	 * @param reconFileInfoBean
	 */
	private void updateReconFileInfoExtendByPage(ReconFileInfo reconFileInfoBean) {
		//从数据库查询出原数据
		com.yl.recon.core.entity.fileinfo.ReconFileInfoExtend oldEx = this.reconFileInfoExtendService.selectByPrimaryKey(reconFileInfoBean.getExtendCode());
		ReconFileInfoExtend extendBean = reconFileInfoBean.getReconFileInfoExtend();
		if (StringUtils.isNotBlank(extendBean.getHeader())) {
			oldEx.setHeader(extendBean.getHeader());
		}
		if (StringUtils.isNotBlank(extendBean.getFooter())) {
			oldEx.setFooter(extendBean.getFooter());
		}
		if (StringUtils.isNotBlank(extendBean.getTotalFlag())) {
			oldEx.setTotalFlag(extendBean.getTotalFlag());
		}
		if (StringUtils.isNotBlank(extendBean.getDelimiter())) {
			oldEx.setDelimiter(extendBean.getDelimiter());
		}
		if (StringUtils.isNotBlank(extendBean.getInterfaceInfoCode())) {
			oldEx.setInterfaceInfoCode(extendBean.getInterfaceInfoCode());
		}
		if (StringUtils.isNotBlank(extendBean.getBankName())) {
			oldEx.setBankName(extendBean.getBankName());
		}
		if (StringUtils.isNotBlank(extendBean.getBankOrderCode())) {
			oldEx.setBankOrderCode(extendBean.getBankOrderCode());
		}
		if (StringUtils.isNotBlank(extendBean.getInterfaceOrderCode())) {
			oldEx.setInterfaceOrderCode(extendBean.getInterfaceOrderCode());
		}
		if (StringUtils.isNotBlank(extendBean.getAmount())) {
			oldEx.setAmount(extendBean.getAmount());
		}
		if (StringUtils.isNotBlank(extendBean.getFee())) {
			oldEx.setFee(extendBean.getFee());
		}
		if (StringUtils.isNotBlank(extendBean.getTransTime())) {
			oldEx.setTransTime(extendBean.getTransTime());
		}
		this.reconFileInfoExtendService.update(oldEx);
	}


	@Override
	public Page findReconException(Map <String, Object> params, Page page) {
		List <com.yl.recon.api.core.bean.ReconException> reconOrders = reconExceptionMapper.findAll(page, params);
		page.setObject(reconOrders);
		return page;
	}

	@Override
	public Page findAllReconFileInfo(Map <String, Object> params, Page page) {
		List <com.yl.recon.api.core.bean.ReconFileInfo> reconOrders = reconFileInfoMapper.findAllReconFileInfo(page, params);
		page.setObject(reconOrders);
		return page;
	}

	/**
	 * 通过主键查询对账文件信息
	 *
	 * @param params
	 * @return
	 */
	@Override
	public ReconFileInfo findReconFileInfoByCode(Map <String, Object> params) {
		return reconFileInfoMapper.findReconFileInfoByCode(params);
	}


	@Override
	public Map <String, Object> exceptionExport(Map <String, Object> params) {
		List <com.yl.recon.api.core.bean.ReconException> reconExceptions;
		reconExceptions = reconExceptionMapper.queryAll(params);
		if (reconExceptions != null && reconExceptions.size() > 0) {
			params = new HashMap <>(1);
			params.put("reconExceptions", reconExceptions);
			return params;
		}
		return null;
	}


}
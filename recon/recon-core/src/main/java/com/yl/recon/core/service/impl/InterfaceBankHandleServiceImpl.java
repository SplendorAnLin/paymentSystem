package com.yl.recon.core.service.impl;

import com.lefu.commons.utils.lang.StringUtils;
import com.yl.recon.api.core.bean.ReconFileInfoExtend;
import com.yl.recon.core.Constant;
import com.yl.recon.core.entity.ReconException;
import com.yl.recon.core.entity.ReconOrder;
import com.yl.recon.core.entity.order.BaseBankChannelOrder;
import com.yl.recon.core.entity.order.PayinterfaceOrder;
import com.yl.recon.core.entity.order.TotalOrder;
import com.yl.recon.core.enums.*;
import com.yl.recon.core.mybatis.mapper.CommonReconMapper;
import com.yl.recon.core.mybatis.mapper.ReconExceptionMapper;
import com.yl.recon.core.mybatis.mapper.ReconFileInfoMapper;
import com.yl.recon.core.mybatis.mapper.ReconOrderMapper;
import com.yl.recon.core.service.CommonReconService;
import com.yl.recon.core.service.InterfaceBankHandleService;
import com.yl.recon.core.service.ReconOrderService;
import com.yl.utils.MyListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2018年01月05
 */
@Service("interfaceBankHandleService")
public class InterfaceBankHandleServiceImpl implements InterfaceBankHandleService {

	private static final Logger logger = LoggerFactory.getLogger(InterfaceBankHandleServiceImpl.class);

	@Resource
	private CommonReconMapper commonReconMapper;
	@Resource
	private ReconExceptionMapper reconExceptionMapper;
	@Resource
	private ReconFileInfoMapper reconFileInfoMapper;
	@Resource
	private ReconOrderMapper reconOrderMapper;
	@Autowired
	private CommonReconService commonReconService;
	@Autowired
	private ReconOrderService reconOrderService;

	/**
	 * 接口与银行通道：数据库对账
	 *
	 * @param reconDate
	 */
	@Override
	public void executeByDb(Date reconDate) {
		try {
			Map <String, Object> params = new HashMap <String, Object>(2);
			params.put(Constant.RECON_TYPE, ReconType.INTERFACE_BANK);
			params.put(Constant.RECON_DATE, reconDate);
			params.put("valid", Constant.IS_VALID_1);
			//查询需要对账的通道编号(接口编号)
			List <com.yl.recon.api.core.bean.ReconFileInfo> reconFileInfos = reconFileInfoMapper.findReconFileInfo(params);
			for (com.yl.recon.api.core.bean.ReconFileInfo reconFileInfo : reconFileInfos) {
				if (null != reconFileInfo && null != reconFileInfo.getReconFileInfoExtend() && StringUtils.isNotBlank(reconFileInfo.getReconFileInfoExtend().getBankOrderCode()) && StringUtils.isNotBlank(reconFileInfo.getReconFileInfoExtend().getBankName())) {
					//接口和通道对账
					interfaceAndBankRecon(params, reconFileInfo.getReconFileInfoExtend());
				}
			}

		} catch (Exception e) {
			logger.info("对账出错:[{}]", e);
		}
	}


	/**
	 * 对账:A接口与B通道
	 */
	private void interfaceAndBankRecon(Map <String, Object> params, ReconFileInfoExtend reconFileInfoExtend) throws Exception {
		List <ReconException> reconExceptions = new ArrayList <>();
		try {
			if (null == params.get(Constant.RECON_TYPE)) {
				throw new RuntimeException("对账类型不能空");
			}
			ReconOrder reconOrder = reconOrderService.initReconOrder(params);

			//接口和通道
			if (params.get(Constant.RECON_TYPE).equals(ReconType.INTERFACE_BANK)) {
				reconOrder.setReconType(ReconType.INTERFACE_BANK);
				reconOrder.setMsg(ReconType.INTERFACE_BANK.getRemark());
				reconOrder.setMatchA(ReconFileType.PAYINTERFACE.name());
				reconOrder.setMatchB(reconFileInfoExtend.getBankName() + "(" + reconFileInfoExtend.getInterfaceInfoCode() + ")");
			}

			//接口单边
			List <PayinterfaceOrder> payinterfaceOrders = commonReconMapper.findInterfaceAndBankInterfaceSingleErr(params);
			//通道单边
			List <BaseBankChannelOrder> baseBankChannelOrders = commonReconMapper.findInterfaceAndBankBankSingleErr(params);
			//金额错误
			List <PayinterfaceOrder> amountErrs = commonReconMapper.findInterfaceAndBankAmountErr(params);
			TotalOrder a = commonReconService.getTotalOrdersByType(params, ReconFileType.PAYINTERFACE);
			reconOrder.setAmountA(Double.parseDouble(a.getTotalAmount()));
			reconOrder.setNumsA(Integer.parseInt(a.getTotalNum()));
			TotalOrder b = commonReconService.getTotalOrdersByType(params, ReconFileType.ONLINE);
			reconOrder.setAmountB(Double.parseDouble(b.getTotalAmount()));
			reconOrder.setNumsB(Integer.parseInt(b.getTotalNum()));
			//默认系统平账
			reconOrder.setReconStatus(ReconStatus.SYS_FLAT_ACC);
			reconOrder.setReconDate((Date) params.get(Constant.RECON_DATE));


			//接口单边
			if (null != payinterfaceOrders && payinterfaceOrders.size() > 0) {
				reconOrder.setReconStatus(ReconStatus.ERR_HANDLING);
				commonReconService.installFailReason(reconOrder, ReconExceptionType.PAYINTERFACE);
				//接口单边
				for (PayinterfaceOrder payinterfaceOrder : payinterfaceOrders) {
					if (null != payinterfaceOrder) {
						ReconException reconException = new ReconException();
						reconException.setVersion(payinterfaceOrder.getVersion());
						reconException.setReconOrderId(reconOrder.getCode());
						reconException.setCreateTime(new Date());
						reconException.setReconType(ReconType.INTERFACE_BANK);
						reconException.setAmount(payinterfaceOrder.getAmount());
						commonReconService.installFailAmount(reconOrder, payinterfaceOrder.getAmount(), Constant.A);
						reconException.setTradeOrderCode(payinterfaceOrder.getTradeOrderCode());
						reconException.setInterfaceOrderCode(payinterfaceOrder.getInterfaceOrderCode());
						reconException.setReconExceptionType(ReconExceptionType.PAYINTERFACE);
						reconException.setHandleRemark(ReconExceptionType.PAYINTERFACE.getRemark());
						reconException.setHandleStatus(HandleStatus.HANDLING);
						reconException.setOper(Constant.SYSTEM_CODE);
						reconException.setReconDate((Date) params.get(Constant.RECON_DATE));
						//保存明细
						reconExceptions.add(reconException);
					}
				}
			}
			//通道单边
			if (null != baseBankChannelOrders && baseBankChannelOrders.size() > 0) {
				reconOrder.setReconStatus(ReconStatus.ERR_HANDLING);
				commonReconService.installFailReason(reconOrder, ReconExceptionType.BANK);
				//通道单边
				for (BaseBankChannelOrder baseBankChannelOrder : baseBankChannelOrders) {
					if (null != baseBankChannelOrder) {
						ReconException reconException = new ReconException();
						reconException.setVersion(baseBankChannelOrder.getVersion());
						reconException.setReconOrderId(reconOrder.getCode());
						reconException.setCreateTime(new Date());
						reconException.setReconType(ReconType.INTERFACE_BANK);
						reconException.setAmount(baseBankChannelOrder.getAmount());
						commonReconService.installFailAmount(reconOrder, baseBankChannelOrder.getAmount(), Constant.B);
						reconException.setInterfaceOrderCode(baseBankChannelOrder.getInterfaceOrderCode());
						reconException.setReconExceptionType(ReconExceptionType.BANK);
						reconException.setHandleRemark(ReconExceptionType.BANK.getRemark());
						reconException.setHandleStatus(HandleStatus.HANDLING);
						reconException.setOper(Constant.SYSTEM_CODE);
						reconException.setReconDate((Date) params.get(Constant.RECON_DATE));
						//保存明细
						reconExceptions.add(reconException);
					}
				}
			}
			//金额错误
			if (null != amountErrs && amountErrs.size() > 0) {
				reconOrder.setReconStatus(ReconStatus.ERR_HANDLING);
				commonReconService.installFailReason(reconOrder, ReconExceptionType.AMOUNT_ERR);
				reconOrder.setAmountErrNum(amountErrs.size());
				//金额错误
				for (PayinterfaceOrder payinterfaceOrder : amountErrs) {
					if (null != payinterfaceOrder) {
						ReconException reconException = new ReconException();
						reconException.setVersion(payinterfaceOrder.getVersion());
						reconException.setReconOrderId(reconOrder.getCode());
						reconException.setCreateTime(new Date());
						reconException.setReconType(ReconType.INTERFACE_BANK);
						reconException.setAmount(payinterfaceOrder.getAmount());
						reconException.setTradeOrderCode(payinterfaceOrder.getTradeOrderCode());
						reconException.setInterfaceOrderCode(payinterfaceOrder.getInterfaceOrderCode());
						reconException.setReconExceptionType(ReconExceptionType.AMOUNT_ERR);
						reconException.setHandleRemark(payinterfaceOrder.getRemark());
						reconException.setHandleStatus(HandleStatus.HANDLING);
						reconException.setOper(Constant.SYSTEM_CODE);
						reconException.setReconDate((Date) params.get(Constant.RECON_DATE));
						//保存明细
						reconExceptions.add(reconException);
					}
				}
			}
			if (null != reconExceptions && reconExceptions.size() > 0) {
				List <List <ReconException>> lists = MyListUtils.split(reconExceptions, Constant.BATCH_INSERT_MAX);
				for (List <ReconException> list : lists) {
					reconExceptionMapper.insertList(list);
				}
			}
			reconOrderMapper.update(reconOrder);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}


}
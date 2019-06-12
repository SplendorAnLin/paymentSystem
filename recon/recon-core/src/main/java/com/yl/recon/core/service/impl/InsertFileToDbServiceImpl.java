package com.yl.recon.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.recon.core.Constant;
import com.yl.recon.core.entity.fileinfo.ReconFileInfo;
import com.yl.recon.core.entity.fileinfo.ReconFileInfoExample;
import com.yl.recon.core.entity.fileinfo.ReconFileInfoExtend;
import com.yl.recon.core.entity.fileinfo.ReconFileInfoExtendExample;
import com.yl.recon.core.entity.order.*;
import com.yl.recon.core.enums.ReconFileType;
import com.yl.recon.core.enums.SystemCode;
import com.yl.recon.core.enums.TransType;
import com.yl.recon.core.handler.AbstractFileHandler;
import com.yl.recon.core.handler.impl.FileHandlerImpl;
import com.yl.recon.core.mybatis.mapper.*;
import com.yl.recon.core.service.InsertFileToDbService;
import com.yl.utils.MyListUtils;
import com.yl.utils.date.MyDateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2018年01月05
 */
@Service("insertFileToDbService")
public class InsertFileToDbServiceImpl implements InsertFileToDbService {

	private static final Logger logger = LoggerFactory.getLogger(InsertFileToDbServiceImpl.class);



	@Resource
	private TradeOrderMapper tradeOrderMapper;
	@Resource
	private RemitOrderMapper remitOrderMapper;
	@Resource
	private RealAuthOrderMapper realAuthOrderMapper;
	@Resource
	private ReconFileInfoMapper reconFileInfoMapper;
	@Resource
	private ReconFileInfoExtendMapper reconFileInfoExtendMapper;
	@Resource
	private TotalOrderMapper totalOrderMapper;
	@Resource
	private AccountOrderMapper accountOrderMapper;
	@Resource
	private PayinterfaceOrderMapper payinterfaceOrderMapper;
	@Resource
	private BaseBankChannelOrderMapper baseBankChannelOrderMapper;


	@Override
	public void executeByDb(Date reconDate) {
		try {
			String fileTime = MyDateUtils.DateToStr(reconDate, "yyyy-MM-dd");
			//获取对账文件信息
			//1.获取对账信息
			ReconFileInfoExample example = new ReconFileInfoExample();
			ReconFileInfoExample.Criteria criteria = example.createCriteria();
			//查询有效的
			criteria.andValidEqualTo(Constant.IS_VALID_1);
			List <ReconFileInfo> reconFileInfos = this.reconFileInfoMapper.selectByExample(example);
			if (null == reconFileInfos || reconFileInfos.size() == 0) {
				logger.info("InsertFileToDbServiceImpl:对账文件信息表未配置正确!!!");
				return;
			}
			Long version = System.currentTimeMillis() / 1000;

			for (ReconFileInfo reconFileInfo : reconFileInfos) {
				if (null != reconFileInfo) {
					logger.info("导入对账文件:[{}]", reconFileInfo.getFileName());
					String filePath = reconFileInfo.getFilePath() + fileTime + "/";
					String fileName = reconFileInfo.getFileName();
					String file = filePath + fileName;
					AbstractFileHandler fileHandler = new FileHandlerImpl().loadFile(file);
					if (fileHandler.getFile() == null || fileHandler.getFilePath() == null) {
						logger.error("对账文件未获取到[{}]:", file);
						//TODO 待处理结果
						continue;
					}
					List <String> data;
					if (null != reconFileInfo.getIsBankChannel() && Constant.IS_VALID_1.equals(reconFileInfo.getIsBankChannel())) {
						//通道方对账文件
						//查询通道方对账文件信息扩展类
						ReconFileInfoExtendExample exExample = new ReconFileInfoExtendExample();
						ReconFileInfoExtendExample.Criteria exCriteria = exExample.createCriteria();
						exCriteria.andCodeEqualTo(reconFileInfo.getExtendCode());
						List <ReconFileInfoExtend> reconFileInfoExtends = this.reconFileInfoExtendMapper.selectByExample(exExample);
						for (ReconFileInfoExtend reconFileInfoExtend : reconFileInfoExtends) {
							data = (List <String>) fileHandler.fileHandle(reconFileInfoExtend.getDelimiter());
							//插入对账所需数据
							insertReconDate(reconDate, data, reconFileInfo.getReconFileType(), reconFileInfo.getSystemCode(), version, file, reconFileInfoExtend);
						}
					} else {
						data = (List <String>) fileHandler.fileHandle("\\|");
						//插入对账所需数据
						insertReconDate(reconDate, data, reconFileInfo.getReconFileType(), reconFileInfo.getSystemCode(), version, file, null);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}


	}


	/**
	 * 插入对账所需数据
	 *
	 * @param reconDate
	 * @param datas
	 * @param reconFileType
	 */
	private void insertReconDate(Date reconDate, List <String> datas, ReconFileType reconFileType, SystemCode systemCode, Long version, String file, ReconFileInfoExtend extend) {

		Date createTime = new Date();
		try {
			if (reconFileType.equals(ReconFileType.ACCOUNT)) {
				//插入账户数据
				installAcc(reconDate, datas, version, createTime, file);
			} else if (reconFileType.equals(ReconFileType.ONLINE)) {
				//插入ONLINE
				installOnline(reconDate, datas, systemCode, version, createTime, file);
			} else if (reconFileType.equals(ReconFileType.DPAY)) {
				//插入Remit
				installRemit(reconDate, datas, systemCode, version, createTime, file);
			} else if (reconFileType.equals(ReconFileType.REAL_AUTH)) {
				//插入REAL_AUTH
				installRealAuth(reconDate, datas, systemCode, version, createTime, file);
			} else if (reconFileType.equals(ReconFileType.PAYINTERFACE)) {
				//插入PAYINTERFACE
				installPayInterface(reconDate, datas, version, createTime, file);
			} else if (reconFileType.equals(ReconFileType.BANK)) {
				//插入BANK
				installBank(reconDate, datas, version, createTime, file, extend);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}


	}

	/**
	 * 插入BANK
	 *
	 * @param reconDate
	 * @param datas
	 * @param version
	 * @param createTime
	 * @param file
	 * @param extend
	 */
	private void installBank(Date reconDate, List <String> datas, Long version, Date createTime, String file, ReconFileInfoExtend extend) throws Exception {
		List <BaseBankChannelOrder> baseBankChannelOrders = new ArrayList <>();
		try {
			for (int i = 0; i < datas.size(); i++) {
				JSONObject order = JSONObject.parseObject(datas.get(i));
				if (StringUtils.isNotBlank(extend.getTotalFlag())) {
					if (i != datas.size() - 1) {
						//非合计的行
						setBaseBankChannelOrder(reconDate, version, createTime, extend, baseBankChannelOrders, order);
					} else {
						//保存合计信息
						insertTotalOrder(reconDate, version, createTime, file, order, ReconFileType.BANK);
					}
				} else {
					//不含合计的对账文件
					setBaseBankChannelOrder(reconDate, version, createTime, extend, baseBankChannelOrders, order);
				}
			}
			if (null != baseBankChannelOrders && baseBankChannelOrders.size() > 0) {
				List <List <BaseBankChannelOrder>> lists = MyListUtils.split(baseBankChannelOrders, Constant.BATCH_INSERT_MAX);
				for (List <BaseBankChannelOrder> list : lists) {
					this.insertListBaseBankChannelOrder(list);
				}
			}
		} catch (Exception e) {
			logger.error("插入BANK对账数据异常", e);
		}
	}

	/**
	 * 组装BaseBankChannelOrder集合
	 *
	 * @param reconDate
	 * @param version
	 * @param createTime
	 * @param extend
	 * @param baseBankChannelOrders
	 * @param order
	 */
	private void setBaseBankChannelOrder(Date reconDate, Long version, Date createTime, ReconFileInfoExtend extend, List <BaseBankChannelOrder> baseBankChannelOrders, JSONObject order) {
		BaseBankChannelOrder baseBankChannelOrder = new BaseBankChannelOrder();
		baseBankChannelOrder.setInterfaceInfoCode(extend.getInterfaceInfoCode());
		baseBankChannelOrder.setBankOrderCode(String.valueOf(order.get(extend.getBankOrderCode())));
		baseBankChannelOrder.setInterfaceOrderCode(String.valueOf(order.get(extend.getInterfaceOrderCode())));
		baseBankChannelOrder.setAmount(Double.parseDouble(String.valueOf(order.get(extend.getAmount()))));
		baseBankChannelOrder.setFee(Double.parseDouble(String.valueOf(order.get(extend.getFee()))));
		baseBankChannelOrder.setTransTime(MyDateUtils.StrToDate(String.valueOf(order.get(extend.getTransTime()))));
		baseBankChannelOrder.setReconDate(reconDate);
		baseBankChannelOrder.setVersion(version);
		baseBankChannelOrder.setCreateTime(createTime);
		baseBankChannelOrders.add(baseBankChannelOrder);
	}

	/**
	 * 插入PAYINTERFACE
	 *
	 * @param reconDate
	 * @param datas
	 * @param version
	 * @param createTime
	 */
	private void installPayInterface(Date reconDate, List <String> datas, Long version, Date createTime, String file) {
		List <PayinterfaceOrder> payinterfaceOrders = new ArrayList <>();
		//interfaceInfoCode|payType|interfaceOrderId|interfaceRequestId|tradeOrderCode|amount|fee|completeTime|systemCode
		try {
			for (int i = 0; i < datas.size(); i++) {
				JSONObject order = JSONObject.parseObject(datas.get(i));
				if (i != datas.size() - 1) {
					//非合计的行
					PayinterfaceOrder payinterfaceOrder = new PayinterfaceOrder();
					payinterfaceOrder.setVersion(version);
					payinterfaceOrder.setCreateTime(createTime);
					payinterfaceOrder.setInterfaceCode(String.valueOf(order.get("interfaceInfoCode")));
					payinterfaceOrder.setInterfaceType(String.valueOf(order.get("payType")));
					payinterfaceOrder.setInterfaceOrderCode(String.valueOf(order.get("interfaceOrderId")));
					payinterfaceOrder.setBankChannelCode(String.valueOf(order.get("interfaceRequestId")));
					payinterfaceOrder.setTradeOrderCode(String.valueOf(order.get("tradeOrderCode")));
					payinterfaceOrder.setAmount(Double.parseDouble(String.valueOf(order.get("amount"))));
					payinterfaceOrder.setCreateTime(new Date());
					payinterfaceOrder.setReconDate(reconDate);
					payinterfaceOrder.setTransTime(MyDateUtils.StrToDate(String.valueOf(order.get("completeTime"))));
					payinterfaceOrders.add(payinterfaceOrder);
				} else {
					//保存合计信息
					insertTotalOrder(reconDate, version, createTime, file, order, ReconFileType.PAYINTERFACE);
				}
			}
			if (null != payinterfaceOrders && payinterfaceOrders.size() > 0) {
				List <List <PayinterfaceOrder>> lists = MyListUtils.split(payinterfaceOrders, Constant.BATCH_INSERT_MAX);
				for (List <PayinterfaceOrder> list : lists) {
					this.insertListPayinterfaceOrder(list);
				}
			}
		} catch (Exception e) {
			logger.error("插入PAYINTERFACE对账数据异常", e);
			//throw new RuntimeException(e.getMessage(),e);
		}
	}

	/**
	 * 插入ORDER
	 *
	 * @param reconDate
	 * @param datas
	 * @param systemCode
	 * @param version
	 * @param createTime
	 */
	private void installOrder(Date reconDate, List <String> datas, SystemCode systemCode, Long version, Date createTime, String file) {
		List <TradeOrder> tradeOrders = new ArrayList <>();
		//orderCode|type|payType|amount|fee|cost|finishTime|interfaceCode|interfaceRequestId|systemCode
		try {
			for (int i = 0; i < datas.size(); i++) {
				JSONObject order = JSONObject.parseObject(datas.get(i));
				if (i != datas.size() - 1) {
					//非合计的行
					TradeOrder tradeOrder = new TradeOrder();
					tradeOrder.setVersion(version);
					tradeOrder.setCreateTime(createTime);
					tradeOrder.setTradeOrderCode(String.valueOf(order.get("orderCode")));
					tradeOrder.setAmount(Double.parseDouble(String.valueOf(order.get("amount"))));
					tradeOrder.setFee(Double.parseDouble(String.valueOf(order.get("fee"))));
					tradeOrder.setPayType(String.valueOf(order.get("payType")));
					tradeOrder.setReconDate(reconDate);
					tradeOrder.setTransTime(MyDateUtils.StrToDate((String.valueOf(order.get("finishTime")))));
					if (systemCode.equals(SystemCode.ONLINE)) {
						tradeOrder.setTransType(TransType.OLPAY);
					}
					if (systemCode.equals(SystemCode.DPAY)) {
						tradeOrder.setTransType(TransType.DPAY);
					}
					if (systemCode.equals(SystemCode.RECEIVE)) {
						tradeOrder.setTransType(TransType.RECEIVE);
					}
					if (systemCode.equals(SystemCode.REALAUTH)) {
						tradeOrder.setTransType(TransType.REALAUTH);
					}
					tradeOrders.add(tradeOrder);
				} else {
					//保存合计信息
					insertTotalOrder(reconDate, version, createTime, file, order, ReconFileType.ONLINE);
				}
			}
			if (null != tradeOrders && tradeOrders.size() > 0) {
				this.insertListTradeOrder(tradeOrders);
			}
		} catch (Exception e) {
			logger.error("插入ORDER对账数据异常", e);
			//throw new RuntimeException(e.getMessage(),e);
		}
	}

	/**
	 * 插入Online
	 *
	 * @param reconDate
	 * @param datas
	 * @param systemCode
	 * @param version
	 * @param createTime
	 * @param file
	 */
	private void installOnline(Date reconDate, List <String> datas, SystemCode systemCode, Long version, Date createTime, String file) {
		List <TradeOrder> tradeOrders = new ArrayList <>();
		//orderCode|type|payType|amount|fee|cost|finishTime|interfaceCode|interfaceRequestId|systemCode
		try {
			for (int i = 0; i < datas.size(); i++) {
				JSONObject order = JSONObject.parseObject(datas.get(i));
				if (i != datas.size() - 1) {
					//非合计的行
					TradeOrder tradeOrder = new TradeOrder();
					tradeOrder.setVersion(version);
					tradeOrder.setCreateTime(createTime);
					tradeOrder.setTradeOrderCode(String.valueOf(order.get("orderCode")));
					tradeOrder.setAmount(Double.parseDouble(String.valueOf(order.get("amount"))));
					tradeOrder.setFee(Double.parseDouble(String.valueOf(order.get("fee"))));
					tradeOrder.setPayType(String.valueOf(order.get("payType")));
					tradeOrder.setReconDate(reconDate);
					tradeOrder.setTransTime(MyDateUtils.StrToDate((String.valueOf(order.get("finishTime")))));
					tradeOrder.setTransType(TransType.OLPAY);
					tradeOrder.setInterfaceCode(String.valueOf(order.get("interfaceCode")));
					tradeOrder.setInterfaceOrderCode(String.valueOf(order.get("interfaceRequestId")));
					tradeOrders.add(tradeOrder);
				} else {
					//保存合计信息
					insertTotalOrder(reconDate, version, createTime, file, order, ReconFileType.ONLINE);
				}
			}
			if (null != tradeOrders && tradeOrders.size() > 0) {
				List <List <TradeOrder>> lists = MyListUtils.split(tradeOrders, Constant.BATCH_INSERT_MAX);
				for (List <TradeOrder> list : lists) {
					this.insertListTradeOrder(list);
				}
			}
		} catch (Exception e) {
			logger.error("插入ORDER对账数据异常", e);
			//throw new RuntimeException(e.getMessage(),e);
		}
	}

	/**
	 * 插入 DPAY
	 *
	 * @param reconDate
	 * @param datas
	 * @param systemCode
	 * @param version
	 * @param createTime
	 */
	private void installRemit(Date reconDate, List <String> datas, SystemCode systemCode, Long version, Date createTime, String file) {
		List <RemitOrder> remitOrders = new ArrayList <>();
		//orderCode|type|payType|amount|fee|cost|finishTime|interfaceCode|interfaceRequestId|systemCode
		try {
			for (int i = 0; i < datas.size(); i++) {
				JSONObject order = JSONObject.parseObject(datas.get(i));
				if (i != datas.size() - 1) {
					//非合计的行
					RemitOrder remitOrder = new RemitOrder();
					remitOrder.setVersion(version);
					remitOrder.setCreateTime(createTime);
					remitOrder.setTradeOrderCode(String.valueOf(order.get("orderCode")));
					remitOrder.setAmount(Double.parseDouble(String.valueOf(order.get("amount"))));
					remitOrder.setFee(Double.parseDouble(String.valueOf(order.get("fee"))));
					remitOrder.setPayType(String.valueOf(order.get("payType")));
					remitOrder.setReconDate(reconDate);
					remitOrder.setTransTime(MyDateUtils.StrToDate((String.valueOf(order.get("finishTime")))));
					remitOrder.setTransType(TransType.DPAY);
					remitOrder.setInterfaceCode(String.valueOf(order.get("interfaceCode")));
					remitOrder.setInterfaceOrderCode(String.valueOf(order.get("interfaceRequestId")));
					remitOrders.add(remitOrder);
				} else {
					//保存合计信息
					insertTotalOrder(reconDate, version, createTime, file, order, ReconFileType.DPAY);
				}
			}
			if (null != remitOrders && remitOrders.size() > 0) {
				List <List <RemitOrder>> lists = MyListUtils.split(remitOrders, Constant.BATCH_INSERT_MAX);
				for (List <RemitOrder> list : lists) {
					this.insertListRemitOrder(list);
				}
			}
		} catch (Exception e) {
			logger.error("插入DPAY对账数据异常", e);
			//throw new RuntimeException(e.getMessage(),e);
		}
	}

	/**
	 * 插入实名认证数据
	 *
	 * @param reconDate
	 * @param datas
	 * @param systemCode
	 * @param version
	 * @param createTime
	 */
	private void installRealAuth(Date reconDate, List <String> datas, SystemCode systemCode, Long version, Date createTime, String file) {
		List <RealAuthOrder> realAuthOrders = new ArrayList <>();
		//orderCode|type|payType|amount|fee|cost|finishTime|interfaceCode|interfaceRequestId|systemCode
		try {
			for (int i = 0; i < datas.size(); i++) {
				JSONObject order = JSONObject.parseObject(datas.get(i));
				if (i != datas.size() - 1) {
					//非合计的行
					RealAuthOrder realAuthOrder = new RealAuthOrder();
					realAuthOrder.setVersion(version);
					realAuthOrder.setCreateTime(createTime);
					realAuthOrder.setTradeOrderCode(String.valueOf(order.get("orderCode")));
					realAuthOrder.setAmount(Double.parseDouble(String.valueOf(order.get("amount"))));
					realAuthOrder.setFee(Double.parseDouble(String.valueOf(order.get("fee"))));
					realAuthOrder.setPayType(String.valueOf(order.get("payType")));
					realAuthOrder.setReconDate(reconDate);
					realAuthOrder.setTransTime(MyDateUtils.StrToDate((String.valueOf(order.get("finishTime")))));
					realAuthOrder.setTransType(TransType.REALAUTH);
					realAuthOrder.setInterfaceCode(String.valueOf(order.get("interfaceCode")));
					realAuthOrder.setInterfaceOrderCode(String.valueOf(order.get("interfaceRequestId")));
					realAuthOrders.add(realAuthOrder);
				} else {
					//保存合计信息
					insertTotalOrder(reconDate, version, createTime, file, order, ReconFileType.REAL_AUTH);
				}
			}
			if (null != realAuthOrders && realAuthOrders.size() > 0) {
				List <List <RealAuthOrder>> lists = MyListUtils.split(realAuthOrders, Constant.BATCH_INSERT_MAX);
				for (List <RealAuthOrder> list : lists) {
					this.insertListRealAuthOrder(list);
				}
			}
		} catch (Exception e) {
			logger.error("插入实名认证数据异常", e);
			//throw new RuntimeException(e.getMessage(),e);
		}
	}

	/**
	 * 保存合计信息
	 *
	 * @param reconDate
	 * @param version
	 * @param createTime
	 * @param file
	 * @param order
	 */
	private void insertTotalOrder(Date reconDate, Long version, Date createTime, String file, JSONObject order, ReconFileType reconFileType) {
		TotalOrder totalOrder = new TotalOrder();
		try {
			totalOrder.setFile(file);
			totalOrder.setReconFileType(reconFileType.name());
			totalOrder.setTotalAmount(order.get("totalAmount") == null ? "0.0" : String.valueOf(order.get("totalAmount")));
			totalOrder.setTotalFee(order.get("totalFee") == null ? "0.0" : String.valueOf(order.get("totalFee")));
			totalOrder.setTotalNum(order.get("totalNum") == null ? "0" : String.valueOf(order.get("totalNum")));
			totalOrder.setReconDate(reconDate);
			totalOrder.setVersion(version);
			totalOrder.setCreateTime(createTime);
			totalOrderMapper.insertSelective(totalOrder);
		} catch (Exception e) {
			logger.error("保存合计信息失败:[{}]", e);
		}
	}

	/**
	 * 插入账户数据
	 *
	 * @param reconDate
	 * @param datas
	 * @param version
	 * @param createTime
	 */
	private void installAcc(Date reconDate, List <String> datas, Long version, Date createTime, String file) {
		// 插入账户数据
		List <AccountOrder> accountOrders = new ArrayList <>();
		try {
			for (int i = 0; i < datas.size(); i++) {
				JSONObject order = JSONObject.parseObject(datas.get(i));
				if (i != datas.size() - 1) {
					//非合计的行
					//accountNo|tradeOrderCode|amount|symbol|transTime|systemCode|bussinessCode
					AccountOrder accountOrder = new AccountOrder();
					accountOrder.setVersion(version);
					accountOrder.setCreateTime(createTime);
					accountOrder.setAccountNo(String.valueOf(order.get("accountNo")));
					accountOrder.setAmount(Double.parseDouble(String.valueOf(order.get("amount"))));
					accountOrder.setFundSymbol(String.valueOf(order.get("symbol")));
					accountOrder.setReconDate(reconDate);
					accountOrder.setSystemCode(String.valueOf(order.get("systemCode")));
					accountOrder.setBussinessCode(String.valueOf(order.get("bussinessCode")));
					if (StringUtils.isNotBlank(accountOrder.getBussinessCode()) && accountOrder.getBussinessCode().contains(Constant.FEE)) {
						//手续费
						accountOrder.setIsFee(Constant.IS_FEE_TRUE);
					} else {
						accountOrder.setIsFee(Constant.IS_FEE_FALSE);
					}
					accountOrder.setTradeOrderCode(String.valueOf(order.get("orderCode")));
					accountOrder.setTransTime(MyDateUtils.StrToDate(String.valueOf(order.get("transTime"))));
					accountOrders.add(accountOrder);
				} else {
					//保存合计信息
					insertTotalOrder(reconDate, version, createTime, file, order, ReconFileType.ACCOUNT);
				}
			}
			if (null != accountOrders && accountOrders.size() > 0) {
				List <List <AccountOrder>> lists = MyListUtils.split(accountOrders, Constant.BATCH_INSERT_MAX);
				for (List <AccountOrder> list : lists) {
					this.insertListAccountOrder(list);
				}
			}
		} catch (Exception e) {
			logger.error("插入账户数据据异常", e);
			//throw new RuntimeException(e.getMessage(),e);
		}

	}


	/**
	 * 批量插入账户数据
	 *
	 * @param accountOrder
	 * @return
	 */
	@Override
	public void insertListAccountOrder(List <AccountOrder> accountOrder) {
		try {
			accountOrderMapper.insertList(accountOrder);
		} catch (Exception e) {
			logger.error("插入账户数据出错:[{}]", e);
		}

	}


	/**
	 * 批量插入订单数据
	 *
	 * @param tradeOrders
	 * @return
	 */
	@Override
	public void insertListTradeOrder(List <TradeOrder> tradeOrders) {
		try {
			tradeOrderMapper.insertList(tradeOrders);
		} catch (Exception e) {
			logger.error("插入订单数据出错:[{}]", e);
		}
	}

	/**
	 * 批量插入代付数据
	 *
	 * @return
	 */
	@Override
	public void insertListRemitOrder(List <RemitOrder> remitOrders) {
		try {
			remitOrderMapper.insertList(remitOrders);
		} catch (Exception e) {
			logger.error("插入代付数据出错:[{}]", e);
		}
	}

	/**
	 * 批量插入实名认证订单数据
	 *
	 * @param realAuthOrders
	 */
	@Override
	public void insertListRealAuthOrder(List <RealAuthOrder> realAuthOrders) {
		try {
			realAuthOrderMapper.insertList(realAuthOrders);
		} catch (Exception e) {
			logger.error("批量插入实名认证订单数据出错:[{}]", e);
		}
	}

	/**
	 * 批量插入接口数据
	 *
	 * @param PayinterfaceOrder
	 * @return
	 */
	@Override
	public void insertListPayinterfaceOrder(List <PayinterfaceOrder> PayinterfaceOrder) {
		try {
			payinterfaceOrderMapper.insertList(PayinterfaceOrder);
		} catch (Exception e) {
			logger.error("插入订单数据出错:[{}]", e);
		}
	}


	/**
	 * 批量插入银行通道数据
	 *
	 * @return
	 */
	@Override
	public void insertListBaseBankChannelOrder(List <BaseBankChannelOrder> baseBankChannelOrders) {
		try {
			baseBankChannelOrderMapper.insertList(baseBankChannelOrders);
		} catch (Exception e) {
			logger.error("插入订单数据出错:[{}]", e);
		}
	}


}
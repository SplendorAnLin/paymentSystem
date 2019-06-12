package com.yl.recon.core.service.impl;

import com.lefu.commons.utils.lang.DateUtils;
import com.yl.recon.core.context.DataBaseSourceType;
import com.yl.recon.core.context.DataSourceContextHolder;
import com.yl.recon.core.entity.other.account.BaseAccount;
import com.yl.recon.core.entity.other.order.BaseOrder;
import com.yl.recon.core.entity.other.ReconCom;
import com.yl.recon.core.entity.other.payinterface.BasePayInterface;
import com.yl.recon.core.mybatis.mapper.CreateReconFileMapper;
import com.yl.recon.core.service.CreateReconFileService;
import com.yl.recon.core.util.TxtUtil;
import com.yl.utils.date.MyDateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 聚合支付有限公司
 *
 * @author 邱健
 * @version V1.0.0
 * @create 2018年01月16
 * @desc 生成对账文件的服务
 **/
@Service("createReconFileService")
@PropertySource("classpath:/serverHost.properties")
public class CreateReconFileServiceImpl implements CreateReconFileService {
	private static Logger logger = LoggerFactory.getLogger(CreateReconFileServiceImpl.class);

	@Value("${recon.filePath}")
	private String filePath;
	@Resource
	private CreateReconFileMapper createReconFileMapper;

	@Override
	public void createRemitOrderReconFileTask(Date date) {
		Map <String, Object> params = new HashMap <>(2);
		try {
			//切为DPAY数据源
			DataSourceContextHolder.setDataSourceType(DataBaseSourceType.DPAY);
			params.put("createStartTime", DateUtils.getMinTime(date));
			params.put("createEndTime", DateUtils.getMaxTime(date));
			List <BaseOrder> baseOrders = this.createReconFileMapper.queryCompleteRemitOrder(params);
			List <ReconCom> reconComs = this.createReconFileMapper.queryRemitSumCountAndFee(params);
			String filenameTemp = filePath + MyDateUtils.DateToStr(date, "yyyy-MM-dd") + "/" + "dpay.txt";
			String filePathTemp = filePath + MyDateUtils.DateToStr(date, "yyyy-MM-dd");
			logger.info("生成对账文件：[{}]", filenameTemp);
			TxtUtil.createOrderTxtFile(filenameTemp, filePathTemp, baseOrders, reconComs.get(0));
		} catch (Exception e) {
			logger.error("生成dpay.txt失败", e);
		} finally {
			//切回默认数据源
			DataSourceContextHolder.clearDataSourceTypeType();
		}
	}

	/**
	 * 生成online订单对账文件
	 *
	 * @param date
	 */
	@Override
	public void createOnlineOrderReconFileTask(Date date) {
		Map <String, Object> params = new HashMap <>(2);
		try {
			//切为ONLINE_TRADE数据源
			DataSourceContextHolder.setDataSourceType(DataBaseSourceType.ONLINE_TRADE);
			params.put("createStartTime", DateUtils.getMinTime(date));
			params.put("createEndTime", DateUtils.getMaxTime(date));
			List <BaseOrder> baseOrders = this.createReconFileMapper.queryCompleteOnlineOrder(params);
			List <ReconCom> reconComs = this.createReconFileMapper.queryOnlineSumCountAndFee(params);
			String filenameTemp = filePath + MyDateUtils.DateToStr(date, "yyyy-MM-dd") + "/" + "online.txt";
			String filePathTemp = filePath + MyDateUtils.DateToStr(date, "yyyy-MM-dd");
			logger.info("生成对账文件：[{}]", filenameTemp);
			TxtUtil.createOrderTxtFile(filenameTemp, filePathTemp, baseOrders, reconComs.get(0));
		} catch (Exception e) {
			logger.error("生成online.txt失败", e);
		} finally {
			//切回默认数据源
			DataSourceContextHolder.clearDataSourceTypeType();
		}

	}

	/**
	 * 生成real_auth对账文件
	 *
	 * @param date
	 */
	@Override
	public void createRealAuthOrderReconFileTask(Date date) {
		Map <String, Object> params = new HashMap <>(2);
		try {
			//切为REAL_AUTH数据源
			DataSourceContextHolder.setDataSourceType(DataBaseSourceType.REAL_AUTH);
			params.put("createStartTime", DateUtils.getMinTime(date));
			params.put("createEndTime", DateUtils.getMaxTime(date));
			List <BaseOrder> baseOrders = this.createReconFileMapper.queryCompleteRealAuthOrder(params);
			List <ReconCom> reconComs = this.createReconFileMapper.queryRealAuthSumCountAndFee(params);
			String filenameTemp = filePath + MyDateUtils.DateToStr(date, "yyyy-MM-dd") + "/" + "realauth.txt";
			String filePathTemp = filePath + MyDateUtils.DateToStr(date, "yyyy-MM-dd");
			logger.info("生成对账文件：[{}]", filenameTemp);
			TxtUtil.createOrderTxtFile(filenameTemp, filePathTemp, baseOrders, reconComs.get(0));
		} catch (Exception e) {
			logger.error("生成realauth.txt失败", e);
		} finally {
			//切回默认数据源
			DataSourceContextHolder.clearDataSourceTypeType();
		}
	}

	/**
	 * 生成payinterface对账文件
	 *
	 * @param date
	 */
	@Override
	public void createPayInterfaceReconFileTask(Date date) {
		Map <String, Object> params = new HashMap <>(2);
		try {
			//切为payinterface数据源
			DataSourceContextHolder.setDataSourceType(DataBaseSourceType.PAYINTERFACE);
			params.put("createStartTime", DateUtils.getMinTime(date));
			params.put("createEndTime", DateUtils.getMaxTime(date));
			List <BasePayInterface> baseOrders = this.createReconFileMapper.queryCompletePayInterface(params);
			List <ReconCom> reconComs = this.createReconFileMapper.queryPayInterfaceSumCountAndFee(params);
			String filenameTemp = filePath + MyDateUtils.DateToStr(date, "yyyy-MM-dd") + "/" + "payinterface.txt";
			String filePathTemp = filePath + MyDateUtils.DateToStr(date, "yyyy-MM-dd");
			logger.info("生成对账文件：[{}]", filenameTemp);
			TxtUtil.createPayInterfaceTxtFile(filenameTemp, filePathTemp, baseOrders, reconComs.get(0));
		} catch (Exception e) {
			logger.error("生成dpay.txt失败", e);
		} finally {
			//切回默认数据源
			DataSourceContextHolder.clearDataSourceTypeType();
		}
	}

	/**
	 * 生成account对账文件
	 *
	 * @param date
	 */
	@Override
	public void createAccountReconFileTask(Date date) {
		Map <String, Object> params = new HashMap <>(2);
		try {
			//切为ACCOUNT数据源
			DataSourceContextHolder.setDataSourceType(DataBaseSourceType.ACCOUNT);
			params.put("createStartTime", DateUtils.getMinTime(date));
			params.put("createEndTime", DateUtils.getMaxTime(date));
			List <BaseAccount> baseOrders = this.createReconFileMapper.queryCompleteAccountOrder(params);
			List <ReconCom> reconComs = this.createReconFileMapper.queryAccountSumCountAndFee(params);
			String filenameTemp = filePath + MyDateUtils.DateToStr(date, "yyyy-MM-dd") + "/" + "account.txt";
			String filePathTemp = filePath + MyDateUtils.DateToStr(date, "yyyy-MM-dd");
			logger.info("生成对账文件：[{}]", filenameTemp);
			TxtUtil.createAccountTxtFile(filenameTemp, filePathTemp, baseOrders, reconComs.get(0));
		} catch (Exception e) {
			logger.error("生成account.txt失败", e);
		} finally {
			//切回默认数据源
			DataSourceContextHolder.clearDataSourceTypeType();
		}
	}

}

package com.yl.recon.api.core.remote;

import com.lefu.commons.utils.Page;
import com.yl.recon.api.core.bean.MyInterfaceInfoBean;
import com.yl.recon.api.core.bean.ReconFileInfo;
import com.yl.recon.api.core.bean.ReconOrder;
import com.yl.recon.api.core.bean.ReconOrderDataQueryBean;
import com.yl.recon.api.core.bean.order.*;

import java.util.List;
import java.util.Map;

/**
 * 对账订单远程服务接口
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年2月23日
 */
public interface ReconInterface {
	/**
	 * 根据日期查询对账结果
	 *
	 * @param params
	 * @param page
	 * @return
	 */
	Page findBy(Map <String, Object> params, Page page) throws Exception;

	/**
	 * 根据条件查询对账异常数据
	 *
	 * @param params
	 * @param page
	 * @return
	 */
	Page findReconException(Map <String, Object> params, Page page) throws Exception;

	/**
	 * 导出异常数据
	 *
	 * @param params
	 * @return
	 */
	Map <String, Object> exceptionExport(Map <String, Object> params) throws Exception;

	/**
	 * 查询对账文件信息
	 *
	 * @param params
	 * @param page
	 * @return
	 */
	Page findAllReconFileInfo(Map <String, Object> params, Page page) throws Exception;

	/**
	 * 通过主键查询对账文件信息
	 *
	 * @param params
	 * @return
	 */
	ReconFileInfo findReconFileInfoByCode(Map <String, Object> params) throws Exception;

	/**
	 * 导出对账订单
	 *
	 * @param params
	 * @return
	 */
	List <ReconOrder> reconOrderExport(Map <String, Object> params) throws Exception;

	/**
	 * 查询接口信息
	 *
	 * @return
	 */
	List <MyInterfaceInfoBean> queryInterfaceInfo() throws Exception;

	/**
	 * 判断对应的通道对账文件是否存在
	 *
	 * @param code
	 * @return
	 */
	boolean checkInterfaceCode(String code) throws Exception;

	/**
	 * 新增通道对账文件
	 *
	 * @param reconFileInfoBean
	 */
	void reconFileInfoAdd(ReconFileInfo reconFileInfoBean) throws Exception;

	/**
	 * 修改通道对账文件
	 *
	 * @param reconFileInfo
	 */
	void reconFileInfoModify(ReconFileInfo reconFileInfo) throws Exception;

	/**
	 * 对账数据查询
	 * @param params
	 * @param page
	 * @return
	 */
	Page reconOrderDataQuery(ReconOrderDataQueryBean params, Page page);

	/**
	 * 查询:实名认证对账数据
	 * @param reconOrderDataQueryBean
	 * @return
	 */
	List<RealAuthOrder> realAuthOrderDataExport(ReconOrderDataQueryBean reconOrderDataQueryBean);
	/**
	 * 查询:代付对账数据
	 * @param reconOrderDataQueryBean
	 * @return
	 */
	List<RemitOrder> remitOrderDataExport(ReconOrderDataQueryBean reconOrderDataQueryBean);
	/**
	 * 查询:交易订单对账数据
	 * @param reconOrderDataQueryBean
	 * @return
	 */
	List<TradeOrder> tradeOrderDataExport(ReconOrderDataQueryBean reconOrderDataQueryBean);
	/**
	 * 查询:账户对账数据
	 * @param reconOrderDataQueryBean
	 * @return
	 */
	List<AccountOrder> accountOrderDataExport(ReconOrderDataQueryBean reconOrderDataQueryBean);
	/**
	 * 查询:接口对账数据
	 * @param reconOrderDataQueryBean
	 * @return
	 */
	List<PayinterfaceOrder> payinterfaceOrderDataExport(ReconOrderDataQueryBean reconOrderDataQueryBean);
	/**
	 * 查询:银行通道对账数据
	 * @param reconOrderDataQueryBean
	 * @return
	 */
	List<BaseBankChannelOrder> baseBankChannelOrderDataExport(ReconOrderDataQueryBean reconOrderDataQueryBean);
}
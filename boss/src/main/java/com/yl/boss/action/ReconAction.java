package com.yl.boss.action;

import com.lefu.commons.utils.Page;
import com.yl.recon.api.core.bean.*;
import com.yl.recon.api.core.bean.order.*;
import com.yl.recon.api.core.enums.ReconFileType;
import com.yl.recon.api.core.enums.ReconType;
import com.yl.recon.api.core.remote.ReconInterface;
import com.yl.utils.date.MyDateUtils;
import com.yl.utils.excel.ExcelUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对账控制器
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年9月8日
 */
public class ReconAction extends Struts2ActionSupport {

	private static final long serialVersionUID = -2383054165942628730L;

	private ReconInterface reconInterface;
	private Page page;
	private String startTime;
	private String endTime;
	private ReconOrder reconOrder;
	private List<ReconOrder> reconOrders;
	private ReconException reconException;
	private ReconFileInfo reconFileInfo;
	private ReconOrderDataQueryBean reconOrderDataQueryBean;
	private List<ReconException> reconExceptions;
	private List<MyInterfaceInfoBean> myInterfaceInfoBeans;
	private String msg;
	private String code;
	private String returnPage;
	private final static String xls = "xls";
	private final static String xlsx = "xlsx";
	private final static String RECON_EXCEPTION_RESULT = "reconExceptionResult";
	private File file;
	private InputStream inputStream;

	/**
	 * 组装分页对象
	 */
	private void installActionPage() {
		int currentPage = this.getHttpRequest().getParameter("currentPage") == null ? 1 : Integer.parseInt(this.getHttpRequest().getParameter("currentPage"));
		if (page == null) {
			page = new Page();
		}
		page.setCurrentPage(currentPage);
	}


	/**
	 * 查询对账信息
	 */
	public String findReconByDate() throws Exception {
		try {
			//组装分页对象
			installActionPage();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("startTime", startTime);
			params.put("endTime", endTime);
			params.put("reconOrder", reconOrder);
			page = reconInterface.findBy(params, page);
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
		if (reconOrder.getReconType() == ReconType.INTERFACE_BANK) {
			//外部对账
			return "out";
		} else {
			//内部对账
			return "in";
		}
	}


	/**
	 * 跳转对账数据查询
	 */
	public String toReconOrderDataQuery() throws Exception {
		try {
			//默认查询接口数据
			reconOrderDataQueryBean = new ReconOrderDataQueryBean();
			reconOrderDataQueryBean.setReconFileType(ReconFileType.PAYINTERFACE);
			return SUCCESS;
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}


	/**
	 * 查询：对账数据
	 *
	 * @return
	 * @throws Exception
	 */
	public String reconOrderDataQuery() throws Exception {
		try {
			//组装分页对象
			installActionPage();
			page = reconInterface.reconOrderDataQuery(reconOrderDataQueryBean, page);
			returnPage = reconOrderDataQueryBean.getReconFileType().name();
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
		return returnPage;
	}

	/**
	 * 导出：对账数据
	 *
	 * @return
	 * @throws Exception
	 */
	public void reconOrderDataExport() throws Exception {
		try {
			String fileName;
			switch (reconOrderDataQueryBean.getReconFileType()) {
				case PAYINTERFACE:
					fileName = "接口数据" + MyDateUtils.DateToStr(new Date(), "yyyyMMddHHmm") + ".xls";
					List<PayinterfaceOrder> payinterfaceOrders = reconInterface.payinterfaceOrderDataExport(reconOrderDataQueryBean);
					ExcelUtil <PayinterfaceOrder> utilPayinterfaceOrder = new ExcelUtil <PayinterfaceOrder>(PayinterfaceOrder.class);
					utilPayinterfaceOrder.exportExcel(payinterfaceOrders, "接口数据", fileName, getHttpRequest(), getHttpResponse());
					break;
				case ACCOUNT:
					List<AccountOrder> accountOrders = reconInterface.accountOrderDataExport(reconOrderDataQueryBean);
					ExcelUtil <AccountOrder> utilAccountOrders = new ExcelUtil <AccountOrder>(AccountOrder.class);
					fileName = "账户数据" + MyDateUtils.DateToStr(new Date(), "yyyyMMddHHmm") + ".xls";
					utilAccountOrders.exportExcel(accountOrders, "账户数据", fileName, getHttpRequest(), getHttpResponse());
					break;
				case BANK:
					List<BaseBankChannelOrder> baseBankChannelOrders = reconInterface.baseBankChannelOrderDataExport(reconOrderDataQueryBean);
					ExcelUtil <BaseBankChannelOrder> utilBaseBankChannelOrder = new ExcelUtil <BaseBankChannelOrder>(BaseBankChannelOrder.class);
					fileName = "通道数据" + MyDateUtils.DateToStr(new Date(), "yyyyMMddHHmm") + ".xls";
					utilBaseBankChannelOrder.exportExcel(baseBankChannelOrders, "通道数据", fileName, getHttpRequest(), getHttpResponse());
					break;
				case ONLINE:
					List<TradeOrder> tradeOrders = reconInterface.tradeOrderDataExport(reconOrderDataQueryBean);
					ExcelUtil <TradeOrder> utilTradeOrder = new ExcelUtil <TradeOrder>(TradeOrder.class);
					fileName = "交易数据" + MyDateUtils.DateToStr(new Date(), "yyyyMMddHHmm") + ".xls";
					utilTradeOrder.exportExcel(tradeOrders, "交易数据", fileName, getHttpRequest(), getHttpResponse());
					break;
				case REAL_AUTH:
					List<RealAuthOrder> realAuthOrders = reconInterface.realAuthOrderDataExport(reconOrderDataQueryBean);
					ExcelUtil <RealAuthOrder> utilRealAuthOrder = new ExcelUtil <RealAuthOrder>(RealAuthOrder.class);
					fileName = "实名认证数据" + MyDateUtils.DateToStr(new Date(), "yyyyMMddHHmm") + ".xls";
					utilRealAuthOrder.exportExcel(realAuthOrders, "实名认证数据", fileName, getHttpRequest(), getHttpResponse());
					break;
				case DPAY:
					List<RemitOrder> remitOrders = reconInterface.remitOrderDataExport(reconOrderDataQueryBean);
					ExcelUtil <RemitOrder> utilRemitOrder = new ExcelUtil <RemitOrder>(RemitOrder.class);
					fileName = "代付数据" + MyDateUtils.DateToStr(new Date(), "yyyyMMddHHmm") + ".xls";
					utilRemitOrder.exportExcel(remitOrders, "代付数据", fileName, getHttpRequest(), getHttpResponse());
					break;
				default:
					break;
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}


	/**
	 * 查询对账详情
	 */
	public String findReconInfo() throws Exception {
		try {
			//组装分页对象
			installActionPage();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("startTime", startTime);
			params.put("endTime", endTime);
			params.put("reconException", reconException);
			page = reconInterface.findReconException(params, page);
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
		if (RECON_EXCEPTION_RESULT.equals(msg)) {
			//对账异常查询页返回
			return RECON_EXCEPTION_RESULT;
		}
		return SUCCESS;
	}


	/**
	 * 查询对账文件信息
	 *
	 * @return
	 */
	public String findReconFileInfo() throws Exception {
		try {
			//组装分页对象
			installActionPage();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("startTime", startTime);
			params.put("endTime", endTime);
			page = reconInterface.findAllReconFileInfo(params, page);
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
		return SUCCESS;
	}

	/**
	 * 新增通道对账文件
	 *
	 * @return
	 */
	public String reconFileInfoAdd() throws Exception {
		try {
			reconInterface.reconFileInfoAdd(reconFileInfo);
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
		return SUCCESS;
	}

	/**
	 * 修改：查询原数据
	 *
	 * @return
	 */
	public String toReconFileInfoModify() throws Exception {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("code", reconFileInfo.getCode());
			reconFileInfo = reconInterface.findReconFileInfoByCode(params);
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
		return SUCCESS;
	}


	/**
	 * 修改:通道对账文件
	 *
	 * @return
	 */
	public String reconFileInfoModify() throws Exception {
		try {
			reconInterface.reconFileInfoModify(reconFileInfo);
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
		return SUCCESS;
	}

	/**
	 * 新增通道方对账文件：自动获取接口编号
	 */
	public String toReconFileInfoAdd() throws Exception {
		try {
			myInterfaceInfoBeans = reconInterface.queryInterfaceInfo();
			return SUCCESS;
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}

	/**
	 * 验证接口编号是否重复
	 */
	public String checkInterfaceCode() throws Exception {
		try {
			boolean isExist = reconInterface.checkInterfaceCode(code);
			msg = isExist ? "FALSE" : "TRUE";
			return SUCCESS;
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}

	/**
	 * 导出合计数据
	 *
	 * @throws IOException
	 */
	public void reconOrderExport() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			params.put("startTime", startTime);
			params.put("endTime", endTime);
			params.put("reconOrder", reconOrder);
			List<ReconOrder> reconOrders = reconInterface.reconOrderExport(params);
			ExcelUtil <ReconOrder> util = new ExcelUtil <ReconOrder>(ReconOrder.class);
			String fileName = "对账合计数据" + MyDateUtils.DateToStr(new Date(), "yyyyMMddHHmm") + ".xls";
			util.exportExcel(reconOrders, "对账合计数据", fileName, getHttpRequest(), getHttpResponse());
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}

	}

	/**
	 * 导出异常数据
	 */
	public void exceptionExport() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			params.put("startTime", startTime);
			params.put("endTime", endTime);
			params.put("reconException", reconException);
			Map<String, Object> map = reconInterface.exceptionExport(params);
			reconExceptions = (List<ReconException>) map.get("reconExceptions");
			ExcelUtil <ReconException> util = new ExcelUtil <ReconException>(ReconException.class);
			String fileName = "异常数据" + MyDateUtils.DateToStr(new Date(), "yyyyMMddHHmm") + ".xls";
			util.exportExcel(reconExceptions, "异常数据", fileName, getHttpRequest(), getHttpResponse());
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}


	public ReconInterface getReconInterface() {
		return reconInterface;
	}

	public void setReconInterface(ReconInterface reconInterface) {
		this.reconInterface = reconInterface;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public ReconOrder getReconOrder() {
		return reconOrder;
	}

	public void setReconOrder(ReconOrder reconOrder) {
		this.reconOrder = reconOrder;
	}

	public List<ReconOrder> getReconOrders() {
		return reconOrders;
	}

	public void setReconOrders(List<ReconOrder> reconOrders) {
		this.reconOrders = reconOrders;
	}

	public ReconException getReconException() {
		return reconException;
	}

	public void setReconException(ReconException reconException) {
		this.reconException = reconException;
	}

	public ReconFileInfo getReconFileInfo() {
		return reconFileInfo;
	}

	public void setReconFileInfo(ReconFileInfo reconFileInfo) {
		this.reconFileInfo = reconFileInfo;
	}

	public List<ReconException> getReconExceptions() {
		return reconExceptions;
	}

	public void setReconExceptions(List<ReconException> reconExceptions) {
		this.reconExceptions = reconExceptions;
	}

	public List<MyInterfaceInfoBean> getMyInterfaceInfoBeans() {
		return myInterfaceInfoBeans;
	}

	public void setMyInterfaceInfoBeans(List<MyInterfaceInfoBean> myInterfaceInfoBeans) {
		this.myInterfaceInfoBeans = myInterfaceInfoBeans;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public static String getXls() {
		return xls;
	}

	public static String getXlsx() {
		return xlsx;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public ReconOrderDataQueryBean getReconOrderDataQueryBean() {
		return reconOrderDataQueryBean;
	}

	public void setReconOrderDataQueryBean(ReconOrderDataQueryBean reconOrderDataQueryBean) {
		this.reconOrderDataQueryBean = reconOrderDataQueryBean;
	}

	public String getReturnPage() {
		return returnPage;
	}

	public void setReturnPage(String returnPage) {
		this.returnPage = returnPage;
	}
}
package com.yl.customer.action;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.mlw.vlh.DefaultListBackedValueList;
import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListInfo;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.codehaus.jackson.JsonNode;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.lefu.commons.utils.web.HttpClientUtils.Method;
import com.lefu.commons.utils.web.InetUtils;
import com.yl.account.api.bean.request.AccountBalanceQuery;
import com.yl.account.api.bean.response.AccountBalanceQueryResponse;
import com.yl.account.api.dubbo.AccountRealQueryInterface;
import com.yl.account.api.enums.AccountType;
import com.yl.account.api.enums.UserRole;
import com.yl.customer.entity.Authorization;
import com.yl.dpay.hessian.beans.RequestBean;
import com.yl.dpay.hessian.beans.ResponseBean;
import com.yl.dpay.hessian.beans.ServiceConfigBean;
import com.yl.dpay.hessian.service.DpayFacade;
import com.yl.dpay.hessian.service.QueryFacade;
import com.yl.dpay.hessian.service.ServiceConfigFacade;

/**
 * 代付控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月4日
 * @version V1.0.0
 */
public class NewDfAction extends Struts2ActionSupport {
	private static final long serialVersionUID = 1L;
	private QueryFacade queryFacade;
	private DpayFacade dpayFacade;
	private List<RequestBean> dfBatch;
	private RequestBean dfSingle;
	private ServiceConfigFacade serviceConfigFacade;
	private AccountRealQueryInterface accountRealQueryInterface;
	private File excel;
	private String excelFileName;
	private String errorsMsg;
	private List<String[]> errorList;
	private List<RequestBean> fullRequestList;
	private static DecimalFormat df = new DecimalFormat("0000");
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	private static SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
	private static Properties prop = new Properties();
	private static String LockPrefix = "LOCK";

	static {
		try {
			prop.load(DfAction.class.getClassLoader().getResourceAsStream("system.properties"));
		} catch (IOException e) {
			logger.error("系统异常:", e);
		}
	}

	/**
	 * 代付查询
	 */
	@SuppressWarnings("rawtypes")
	public String dfQuery() {
		try {
			Authorization auth = (Authorization) getSession().getAttribute("auth");
			String queryId = "dpayRequest";
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			String status = (String) params.get("status");
			Map<String, Object> statusMap = convertDfStatus(status);
			params.putAll(statusMap);
			params.put("ownerId", auth.getCustomerno());
			params.put("ownerRole", "CUSTOMER");
			Map<String, Object> returnMap = queryFacade.query(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST), (ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		} catch (Exception e) {
			logger.error("系统异常:",e);
		}
		return SUCCESS;
	}

	/**
	 * 代付查询
	 */
	@SuppressWarnings("rawtypes")
	public String dfAuditQuery() {
		try {
			Authorization auth = (Authorization) getSession().getAttribute("auth");
			String queryId = "dpayAuditRequest";
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			String status = (String) params.get("status");
			Map<String, Object> statusMap = convertDfStatus(status);
			params.putAll(statusMap);
			params.put("ownerId", auth.getCustomerno());
			params.put("ownerRole", "CUSTOMER");

			Map<String, Object> returnMap = queryFacade.query(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST), (ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		} catch (Exception e) {
			logger.error("系统异常:",e);
		}
		return SUCCESS;
	}

	/**
	 * 代付导出
	 */
	@SuppressWarnings("rawtypes")
	public String dfExport() {

		try {
			Authorization auth = (Authorization) getSession().getAttribute("auth");
			CacheUtils.get(auth.getCustomerno(), String.class);

			String queryId = "dpayRequestExport";
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			String status = (String) params.get("status");
			Map<String, Object> statusMap = convertDfStatus(status);
			params.putAll(statusMap);
			params.put("ownerId", auth.getCustomerno());
			params.put("ownerRole", "CUSTOMER");
			Map<String, Object> returnMap = queryFacade.query(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST), (ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		} catch (Exception e) {
			logger.error("系统异常:",e);
		}
		return SUCCESS;
	}

	/**
	 * 代付复核
	 * 代付请求CODE
	 */

	public void dfBatchAudit() {
		String msg = "";
		try {
			Authorization auth = (Authorization) getSession().getAttribute("auth");
			String code = getHttpRequest().getParameter("code");
			String flag = getHttpRequest().getParameter("flag");
			String[] codes = code.split(",");
			int failNum = 0;
			String failFlows = "";
			int successNum = 0;
			String successFlows = "";
			msg = "";
			String lock = CacheUtils.get(LockPrefix + auth.getCustomerno(), String.class);

			if (StringUtils.isBlank(lock)) {
				logger.info("商户编号：" + auth.getCustomerno() + " 加锁 ");
				CacheUtils.setEx(LockPrefix + auth.getCustomerno(), "lock", 120);
				try {
					for (String co : codes) {
						try {
							logger.info("步骤2循环流水号 code:" + co + "flag:" + flag);
							dpayFacade.audit(co, flag, auth.getUsername());
							successNum++;
							successFlows += ",\"" + co + "\"";
						} catch (Exception e) {
							failNum++;
							failFlows += ",\"" + co + "\"";
							logger.error(e.getMessage());
						}
					}
				} catch (Exception e) {

					logger.error("系统异常",e);
				}
				if (failNum != 0) {
					msg = "{\"result\":0,\"success\":" + successNum + ",\"fail\":" + failNum;
				} else {
					msg = "{\"result\":1,\"success\":" + successNum + ",\"fail\":" + 0;
				}
				if (failFlows.length() > 0) {
					failFlows = failFlows.substring(1);
					msg += ",\"failFlows\":[" + failFlows + "]";
				} else {
					msg += ",\"failFlows\":[]";
				}
				if (successFlows.length() > 0) {
					successFlows = successFlows.substring(1);
					msg += ",\"successFlows\":[" + successFlows + "]";
				} else {
					msg += ",\"successFlows\":[]";
				}
				msg += "}";

				logger.info("商户编号：" + auth.getCustomerno() + " 解锁 ");
				CacheUtils.del(LockPrefix + auth.getCustomerno());
			} else {
				logger.info("商户编号：" + auth.getCustomerno() + "重复提交");
				msg = "{\"result\":2}";
			}
		} catch (Exception e) {
			logger.error("系统异常:",e);
		}

		this.write(msg);
	}

	/**
	 * 代付批次查询
	 */
	@SuppressWarnings("rawtypes")
	public String dfBatchQuery() {
		try {
			Authorization auth = (Authorization) getSession().getAttribute("auth");
			logger.info("dfBatchQuery:[" + auth.getCustomerno() + "]");
			String queryId = "dpayBatch";
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			params.put("ownerId", auth.getCustomerno());
			params.put("ownerRole", "CUSTOMER");
			Map<String, Object> returnMap = queryFacade.query(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST), (ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		} catch (Exception e) {
			logger.error("系统异常:",e);
		}
		return SUCCESS;
	}

	/**
	 * 代付批次明细查询
	 */
	@SuppressWarnings("rawtypes")
	public String dfBatchDetailQuery() {
		try {
			Authorization auth = (Authorization) getSession().getAttribute("auth");
			String code = getHttpRequest().getParameter("code");
			String queryId = "dpayRequest";
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			params.put("batchId", code);
			params.put("ownerId", auth.getCustomerno());
			params.put("ownerRole", "CUSTOMER");
			Map<String, Object> returnMap = queryFacade.query(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST), (ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		} catch (Exception e) {
			logger.error("系统异常:",e);
		}
		return SUCCESS;
	}

	/**
	 * 代付单笔申请
	 */
	public void dfSingleApply() {
		String msg ="";
		try {
			Authorization auth = (Authorization) getSession().getAttribute("auth");

			if (new BigDecimal(dfSingle.getAmount()).compareTo(new BigDecimal(0)) < 0) {
				throw new RuntimeException("dfSingleApply amount is error");
			}
			String ip = InetUtils.getRealIpAddr(getHttpRequest());
			msg = "成功";
			dfSingle.setRequestType("PAGE");
			dfSingle.setRequestNo(auth.getCustomerno() + sdf.format(new Date()) + df.format(1));
			// 发起申请不用判断账户余额-王华-2015-05-21
			dpayFacade.singleRequest(dfSingle);
		} catch (Exception e) {
			logger.error("系统异常:",e);
			msg = "失败";
		}

		this.write("{\"msg\":\"" + msg + "\"}");
	}

	/**
	 * 代付单上传批次申请
	 */
	public void dfUploadBatchApply() {
		Map<String ,Object> result = null;
		String msg;
		int succNums = 0;
		double succAmount = 0d;
		double fee = 0d;
		int faliNums = 0;
		double failAmount = 0d;
		try {
			Authorization auth = (Authorization) getSession().getAttribute("auth");
			msg = "成功";
			List<RequestBean> db = new ArrayList<RequestBean>();

			int i = 0;
			for (RequestBean rp : fullRequestList) {
				if (rp != null) {
					i++;
					rp.setRequestType("PAGE");
					rp.setRequestNo(auth.getCustomerno() + sdf.format(new Date()) + df.format(i));
					db.add(rp);
				}
				try {
					ResponseBean responseBean = dpayFacade.singleRequest(rp);
					if("S3100".equals(responseBean.getResponseCode()) || "S3000".equals(responseBean.getResponseCode())){
						succNums++;
						succAmount+=rp.getAmount();
						fee+=responseBean.getFee();
					} else {
						faliNums++;
						failAmount+=rp.getAmount();
					}
				} catch (Exception e) {
					logger.error("系统异常:",e);
					faliNums++;
					failAmount+=rp.getAmount();
				}
			}
			result = new HashMap<>();
			result.put("succNums", succNums);
			result.put("succAmount", succAmount);
			result.put("fee", fee);
			result.put("faliNums", faliNums);
			result.put("failAmount", failAmount);
			msg = JsonUtils.toJsonString(result);
		} catch (Exception e) {
			msg = "失败，账户可用余额不足或系统处理异常";
			logger.error("系统异常:",e);
		}
		
		this.write("{\"msg\":\"" + msg + "\"}");
	}

	/**
	 * 跳转到代付批次申请
	 * 根据传递的收款人的CODE进行查询
	 */
//	public String toDfBatchApply() {
//		setSingleAmount();
//		String code = getHttpRequest().getParameter("code");
//		String[] codes = code.split(",");
//		List<String> listCode = new ArrayList<String>();
//		for (String co : codes) {
//			listCode.add(co);
//		}
//		try {
//			List<Map<String, Object>> listReturn = queryFacade.queryTargetAccount(listCode);
//
//			getHttpRequest().setAttribute("dpayTargetAccount", new DefaultListBackedValueList(listReturn, new ValueListInfo()));
//		} catch (Exception e) {
//			logger.error("系统异常:",e);
//		}
//		return SUCCESS;
//	}

	/**
	 * 代付批次申请
	 */
	public void dfBatchApply() {
		Map<String ,Object> result = null;
		String msg;
		int succNums = 0;
		double succAmount = 0d;
		double fee = 0d;
		int faliNums = 0;
		double failAmount = 0d;
		Authorization auth = null;
		try {
			auth = (Authorization) getSession().getAttribute("auth");
		} catch (Exception e) {
			msg = "系统异常";
			logger.error("系统异常:",e);
			this.write("{\"msg\":\"" + msg + "\"}");
		}
		List<RequestBean> db = new ArrayList<RequestBean>();
		int i = 0;
		for (RequestBean rp : dfBatch) {
			if (rp != null) {
				i++;
				rp.setRequestType("PAGE");
				rp.setRequestNo(auth.getCustomerno() + sdf.format(new Date()) + df.format(i));
				db.add(rp);
			}
			try {
				ResponseBean responseBean = dpayFacade.singleRequest(rp);
				if("S3100".equals(responseBean.getResponseCode()) || "S3000".equals(responseBean.getResponseCode())){
					succNums++;
					succAmount+=rp.getAmount();
					fee+=responseBean.getFee();
				} else {
					faliNums++;
					failAmount+=rp.getAmount();
				}
			} catch (Exception e) {
				logger.error("系统异常:",e);
				faliNums++;
				failAmount+=rp.getAmount();
			}
		}
		result = new HashMap<>();
		result.put("succNums", succNums);
		result.put("succAmount", succAmount);
		result.put("fee", fee);
		result.put("faliNums", faliNums);
		result.put("failAmount", failAmount);
		msg = JsonUtils.toJsonString(result);
		this.write("{\"msg\":\"" + msg + "\"}");
	}

	// 是否是字母和汉字组合
	private boolean isCharacter(String str, String accountType) {
		if (StringUtils.isBlank(str)) {
			return false;
		}
		Pattern pattern = null;

		Matcher m = null;
		if ("对公".equals(accountType)) {
			pattern = Pattern.compile("([\u4e00-\u9fa5]|\u00b7|\uff08|\uff09|\u0028|\u0029|\\(|\\)|[0-9]|[a-zA-Z])+");
			Matcher matcher = pattern.matcher(str);
			return matcher.matches();
		} else if ("对私".equals(accountType)) {
			pattern = Pattern.compile("([\u4e00-\u9fa5]|\u00b7)+");
			Matcher matcher = pattern.matcher(str);
			return matcher.matches();
		}
		return true;

	}

	/**
	 * 代付收款人查询
	 */
	@SuppressWarnings("rawtypes")
	public String dfPayeeQuery() {
		try {
			Authorization auth = (Authorization) getSession().getAttribute("auth");
			String queryId = "dpayTargetAccount";
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			params.put("ownerId", auth.getCustomerno());
			params.put("ownerRole", "CUSTOMER");
			logger.info("dfQuery start...");
			Map<String, Object> returnMap = queryFacade.query(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST), (ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
			logger.info("dfQuery end...");
		} catch (Exception e) {
			logger.error("系统异常:",e);
		}
		return SUCCESS;
	}

	/**
	 * 代付收款文件上传
	 */
	@SuppressWarnings({ "resource" })
	public String dfPayeeUpload() {
		String suffix = excelFileName.substring(excelFileName.lastIndexOf(".") + 1);
		errorList = new ArrayList<>();
		Workbook wb = null;
		try {
			FileInputStream fis = new FileInputStream(excel);
			if ("xls".equals(suffix.toLowerCase())) {// 2003
				wb = new HSSFWorkbook(fis);
			} else if ("xlsx".equals(suffix.toLowerCase())) {// 2007
				wb = new XSSFWorkbook(excel.getAbsolutePath());
			} else {
				getHttpRequest().setAttribute("errorMsg", "请上传模板格式文件。");
				return "toDfSingleApply";
				// errorsMsg += "文件格式不正确,"; // 不识别
			}
		} catch (Throwable e) {
			logger.error("系统异常:", e);
			getHttpRequest().setAttribute("errorMsg", "文件上传错误。");
			// errorsMsg += "文件上传错误,"; // 上传错误
			return "toDfSingleApply";
		}

		Sheet sheet = wb.getSheetAt(0);

		if (sheet == null || sheet.getLastRowNum() < 1) {
			// errorsMsg += "无数据,"; // 无数据
			getHttpRequest().setAttribute("errorMsg", "请上传模板格式文件。");
			return "toDfSingleApply";
		}
		// 判断是否是模板格式文件开始
		try {
			Row template = sheet.getRow(14);

			if ("账户名称".equals(template.getCell(0).getStringCellValue().replaceAll("　", "").replaceAll(" ", "").trim())
					&& "银行账号".equals(template.getCell(1).getStringCellValue().replaceAll("　", "").replaceAll(" ", "").trim())
					&& "账户类型".equals(template.getCell(2).getStringCellValue().replaceAll("　", "").replaceAll(" ", "").trim())
					&& "开户银行".equals(template.getCell(3).getStringCellValue().replaceAll("　", "").replaceAll(" ", "").trim())
					&& "金额（元）".equals(template.getCell(4).getStringCellValue().replaceAll("　", "").replaceAll(" ", "").trim())
					&& "打款原因".equals(template.getCell(5).getStringCellValue().replaceAll("　", "").replaceAll(" ", "").trim())) {
				// do nothing
			} else {
				getHttpRequest().setAttribute("errorMsg", "请上传模板格式文件。");
				return "toDfSingleApply";
			}

		} catch (Exception e) {
			logger.error("系统异常:",e);
			getHttpRequest().setAttribute("errorMsg", "请上传模板格式文件。");
			return "toDfSingleApply";
		}
		// 判断是否是模板格式文件结束
		try {
			List<String[]> strList = new ArrayList<>();
			String errorAmount = "";
			for (int i = 15; i < sheet.getLastRowNum() + 1; i++) {
				errorAmount = "";
				String[] strs = new String[8];
				Row row = sheet.getRow(i);
				String msg = "";
				strs[6] = i + 1 + "";
				try {
					// 检查其中一行如果为空，则忽略此行 开始
					String temp = "";
					for (int j = 0; j <= 5; j++) {
						try {
							// try catch 的目的是单元格未输入抛出异常，做为空处理
							temp += row.getCell(j).getStringCellValue();
						} catch (Exception e) {

						}
					}

					if (StringUtils.isBlank(temp)) {
						continue;
					}

					for (int j = 0; j <= 5; j++) {
						try {
							// 防止用户在该输入汉字的地方输入数字导致未拦截到异常
							row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
						} catch (Exception e) {

						}
					}
					String amount = "";
					try {
						row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
						amount = String.valueOf(row.getCell(4).getStringCellValue()).replaceAll("　", "").replaceAll(" ", "").trim();
						if (StringUtils.isBlank(amount)) {
							throw new Exception("请输入代付金额。");
						}
						try {
							double d = Double.parseDouble(amount);
						} catch (Exception foramt) {
							throw new Exception("金额格式有误。");
						}
						if (BigDecimal.valueOf(Double.valueOf(amount)).compareTo(new BigDecimal(0)) <= 0 || amount == null || "".equals(amount)) {
							throw new Exception("金额请大于0元。");
						} else {
							// 如果有科学计数问题，转换成非可以计数
							BigDecimal bd = new BigDecimal(amount);
							bd = bd.setScale(2, RoundingMode.HALF_UP);
							amount = bd.toPlainString();
							errorAmount = amount;
						}

					} catch (Exception e) {
						// do nothing
					}

					msg = "账户类型";
					strs[2] = row.getCell(2).getStringCellValue().replaceAll("　", "").replaceAll(" ", "").trim();
					if (StringUtils.isBlank(strs[2])) {
						throw new Exception("请输入账户类型。");
					}
					if (!strs[2].equals("对公") && !strs[2].equals("对私")) {
						throw new Exception("账户类型不正确。");
					}

					// 检查其中一行如果为空，则忽略此行 结束
					msg = "账户名称";
					strs[0] = row.getCell(0).getStringCellValue().replaceAll("　", "").replaceAll(" ", "").trim();
					if (StringUtils.isBlank(strs[0])) {
						throw new Exception("请输入收款账户名称。");
					} else if (!isCharacter(strs[0], strs[2])) {
						throw new Exception("信息格式不正确。");
					}

					msg = "银行账号";
					strs[1] = row.getCell(1).getStringCellValue().replaceAll("　", "").replaceAll(" ", "").trim();
					if (StringUtils.isBlank(strs[1])) {
						throw new Exception("请输入收款账号。");
					} else if (!isAccountNoAllDigital(strs[1])) {
						throw new Exception("收款账号格式不正确。");
					}

					msg = "开户银行";
					strs[3] = row.getCell(3).getStringCellValue().replaceAll("　", "").replaceAll(" ", "").trim();
					if (StringUtils.isBlank(strs[3])) {
						throw new Exception("请输入开户行信息。");
					}
					// 优先格式化金额
					msg = "金额";
					row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
					if (StringUtils.isBlank(amount)) {
						amount = String.valueOf(row.getCell(4).getStringCellValue()).replaceAll("　", "").replaceAll(" ", "").trim();
					}
					if (StringUtils.isBlank(amount)) {
						throw new Exception("请输入代付金额。");
					}
					try {
						double d = Double.parseDouble(amount);
					} catch (Exception foramt) {
						throw new Exception("金额格式有误。");
					}
					if (BigDecimal.valueOf(Double.valueOf(amount)).compareTo(new BigDecimal(0)) <= 0 || amount == null || "".equals(amount)) {
						throw new Exception("金额请大于0元。");
					} else {
						String singleAmount = (String) getSession().getAttribute("singleAmount");
						if (BigDecimal.valueOf(Double.valueOf(amount)).compareTo(new BigDecimal(singleAmount)) > 0) {
							throw new Exception("单笔支付金额超限。");
						}
						// 如果有科学计数问题，转换成非可以计数
						BigDecimal bd = new BigDecimal(amount);
						bd = bd.setScale(2, RoundingMode.HALF_UP);
						amount = bd.toPlainString();
					}
					strs[4] = amount;

					msg = "打款原因";
					try {
						strs[5] = row.getCell(5).getStringCellValue().trim();
					} catch (Exception e) {}
				} catch (Exception e) {
					String[] errorsmsg = new String[8];
					for (int idx = 0; idx < 6; idx++) {
						try {
							row.getCell(idx).setCellType(Cell.CELL_TYPE_STRING);
							if (idx != 4) {
								errorsmsg[idx] = row.getCell(idx).getStringCellValue();
							} else {
								errorsmsg[idx] = errorAmount;
							}
						} catch (Exception e1) {}
					}
					errorsmsg[6] = (i + 1) + "";
					if (e.getMessage() == null) {
						if (msg.equals("账户名称")) {
							errorsmsg[7] = "请输入收款账户名称。";
						} else if (msg.equals("银行账号")) {
							errorsmsg[7] = "请输入收款账号。";
						} else if (msg.equals("账户类型")) {
							errorsmsg[7] = "请输入账户类型。";
						} else if (msg.equals("开户银行")) {
							errorsmsg[7] = "请输入开户行信息。";
						} else if (msg.equals("金额")) {
							errorsmsg[7] = "请输入代付金额。";
						}
					} else {
						errorsmsg[7] = e.getMessage();
					}
					errorList.add(errorsmsg);
					continue;
				}
				strList.add(strs);
			}

			// 验证文件中是否有记录开始
			if (errorList.size() + strList.size() == 0) {
				getHttpRequest().setAttribute("errorMsg", "文件中未查询到数据。");
				return "toDfSingleApply";
			}
			// 验证文件中是否有记录 结束

			// 验证记录是否少于200条开始
			if (errorList.size() + strList.size() > 200) {
				getHttpRequest().setAttribute("errorMsg", "上传信息大于200条，请分批上传。");
				return "toDfSingleApply";
			}
			// 验证记录是否少于200条开始结束

			String bankNames = "";
			for (int i = 0; i < strList.size(); i++) {
				if (i == 0) {
					bankNames = strList.get(i)[3];
					continue;
				}
				bankNames = bankNames + "," + strList.get(i)[3];
			}
			bankNames.replaceAll(" ", "");
			List<Map<String, String>> lists = getCnaps(bankNames);
			fullRequestList = new ArrayList<>();
			for (int i = 0; i < strList.size(); i++) {
				RequestBean fullRequestParam = new RequestBean();
				fullRequestParam.setAccountName(strList.get(i)[0]);
				fullRequestParam.setAccountNo(strList.get(i)[1]);
				if (strList.get(i)[2].equals("对公")) {
					fullRequestParam.setAccountType("OPEN");
				}
				if (strList.get(i)[2].equals("对私")) {
					fullRequestParam.setAccountType("INDIVIDUAL");
				}
				fullRequestParam.setAmount(Double.valueOf(strList.get(i)[4]));
				fullRequestParam.setDescription(strList.get(i)[5]);
				if (lists.get(i) != null
						&& lists.get(i).size() != 0
						&& (strList.get(i)[2].equals("对公") || checkCard(strList.get(i)[1]))
						&& (new BigDecimal(Double.valueOf(strList.get(i)[4])).compareTo(new BigDecimal(50000)) > 0
								)) {
					Map<String, String> map = getSabkCode(lists.get(i).get("providerCode"), "1");
					if (map == null) {
						throw new RuntimeException(" dpay upload getSabkCode error!");
					}
					fullRequestList.add(fullRequestParam);
				} else {
					fullRequestList.add(fullRequestParam);
//					if (checkCard(strList.get(i)[1]) && strList.get(i)[2].equals("对私")
//							&& new BigDecimal(Double.valueOf(strList.get(i)[4])).compareTo(new BigDecimal(50000)) > 0) {
//						List<Map<String, String>> sabkList = getSabkByProCode(getCardInfo(strList.get(i)[1]));
//						if (sabkList.get(0).size() != 0) {
//							openBank.setCnapsName(strList.get(i)[3]);
//							openBank.setBankCode(sabkList.get(0).get("providerCode"));
//							openBank.setOpenBankName(lists.get(i).get("name"));
//							fullRequestList.add(fullRequestParam);
//						} else {
//							if (strList.get(i)[2].equals("对公") || checkCard(strList.get(i)[1])) {
//								strList.get(i)[7] = "未匹配到正确的开户行信息!";
//								errorList.add(strList.get(i));
//							} else {
//								strList.get(i)[7] = "无效的银行卡号码!";
//								errorList.add(strList.get(i));
//							}
//						}
//					} else {
//						if (strList.get(i)[2].equals("对公") || checkCard(strList.get(i)[1])) {
//							strList.get(i)[7] = "未匹配到正确的开户行信息!";
//							errorList.add(strList.get(i));
//						} else {
//							strList.get(i)[7] = "无效的银行卡号码!";
//							errorList.add(strList.get(i));
//						}
//					}
				}
			}
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}

	public boolean isAccountNoAllDigital(String accountNo) {
		if (org.apache.commons.lang3.StringUtils.isBlank(accountNo)) {
			return false;
		}
		for (int i = 0; i < accountNo.length(); i++) {
			if (!"01234567890".contains("" + accountNo.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 代付收款人信息（增）
	 */
//	public void dfPayeeManage() {
//		String msg = "成功";
//		try {
//			Authorization auth = (Authorization) getSession().getAttribute("auth");
//
//			OwnerParam owner = new OwnerParam(auth.getCustomerno(), "CUSTOMER");
//			targetAccountParam.setOpenBank(openBank);
//			logger.info("dfPayeeManage start...");
//
//			dpayFacade.createTargetAccount(owner, targetAccountParam);
//		} catch (Exception e) {
//			msg = "失败";
//			logger.error("系统异常:",e);
//		}
//		logger.info("dfPayeeManage start...");
//		this.write("{\"msg\":\"" + msg + "\"}");
//	}

	/**
	 * 代付收款人信息（删）
	 */
//	public void dfPayeeDelete() {
//		String msg = "成功";
//		String code = getHttpRequest().getParameter("code");
//		try {
//			dpayFacade.invalid(code);
//		} catch (Exception e) {
//			msg = "失败";
//			logger.error("系统异常:",e);
//		}
//		this.write("{\"msg\":\"" + msg + "\"}");
//	}

	/**
	 * 代付收款人信息（跳转修改）
	 */
//	public String toDfPayeeUpdate() {
//		Authorization auth = (Authorization) getSession().getAttribute("auth");
//		String code = getHttpRequest().getParameter("code");
//		try {
//			targetAccountParam = dpayFacade.findTargetAccountByCode(new OwnerParam(auth.getCustomerno(), "CUSTOMER"), code);
//			targetAccountParam.setCode(code);
//		} catch (Exception e) {
//			logger.error("系统异常:",e);
//		}
//		return SUCCESS;
//	}

	/**
	 * 代付收款人信息（修改）
	 */
//	public void dfPayeeUpdate() {
//		String msg = "成功";
//		try {
//			Authorization auth = (Authorization) getSession().getAttribute("auth");
//			OwnerParam owner = new OwnerParam(auth.getCustomerno(), "CUSTOMER");
//			targetAccountParam.setOpenBank(openBank);
//			logger.info("dfPayeeUpdate start...");
//
//			dpayFacade.updateTargetAccount(owner, targetAccountParam);
//		} catch (Exception e) {
//			msg = "失败";
//			logger.error("系统异常:",e);
//		}
//		logger.info("dfPayeeUpdate end...");
//		this.write("{\"msg\":\"" + msg + "\"}");
//	}

	/**
	 * 获取充值账户余额
	 */
	public void getRechargeAccountBalance() {
		String msg = "null";
		Authorization auth = (Authorization) getSession().getAttribute("auth");
		try {
			// 查询账务余额
			AccountBalanceQuery accountBalanceQuery = new AccountBalanceQuery();
			accountBalanceQuery.setUserNo(auth.getCustomerno());
			accountBalanceQuery.setUserRole(UserRole.CUSTOMER);
			accountBalanceQuery.setAccountType(AccountType.CASH);
			AccountBalanceQueryResponse accountBalanceQueryResponse = accountRealQueryInterface._findAccountBalance(accountBalanceQuery);
			if (accountBalanceQueryResponse != null) {
				msg = String.valueOf(new BigDecimal(accountBalanceQueryResponse.getAvailavleBalance()).subtract(new BigDecimal(0)));
				logger.info("customerno:" + auth.getCustomerno() + " 剩余金额:" + msg);
			} else {
				msg = "0";
			}
		} catch (Exception e) {
			msg = "0";
			logger.error("商户编号【" + auth.getCustomerno() + "】查询余额异常: {}", e);
		}

		this.write("{\"msg\":\"" + msg + "\"}");
	}

	/**
	 * 获取手续费
	 */
	public void getFee() {
		String msg = null;
		String amount = getHttpRequest().getParameter("amount");
		try {
			
		} catch (Exception e) {
			logger.error("系统异常:",e);
		}
		this.write("{\"msg\":\"" + msg + "\"}");
	}

	/**
	 * 获取未审核代付订单金额
	 */
	@SuppressWarnings("unchecked")
	public Double getUnauditedOrderAmount() {
		Double msg = 0D;
		try {
			Authorization auth = (Authorization) getSession().getAttribute("auth");
			ServiceConfigBean serviceConfigBean = serviceConfigFacade.query(auth.getCustomerno());
			if (serviceConfigBean.getManualAudit().equals("TRUE")) {
				return msg;
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ownerId", auth.getCustomerno());
			params.put("ownerRole", "CUSTOMER");
			params.put("df.status", "WAIT");
			params.put("df.auditStatus", "WAIT");
			Map<String, Object> map = queryFacade.query("countUnauditedOrderAmount", params);
			if (map != null) {
				List<HashMap<String, String>> list = (List<HashMap<String, String>>) map.get("valueList");
				msg = list != null ? Double.valueOf(list.get(0).get("amount")) : msg;
			}
		} catch (Exception e) {
			logger.error("系统异常:",e);
		}
		return msg;
	}

	/**
	 * 获取代付订单数量
	 */
	@SuppressWarnings("unchecked")
	public void getOrderCount() {
		int msg = 0;
		try {
			Authorization auth = (Authorization) getSession().getAttribute("auth");
			ServiceConfigBean serviceConfigBean = serviceConfigFacade.query(auth.getCustomerno());
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ownerId", auth.getCustomerno());
			params.put("ownerRole", "CUSTOMER");
			params.put("createDate1", sf.format(new Date()) + "000000");
			params.put("createDate2", sf.format(new Date()) + "235959");
			params.put("handle_status", "'SUCCEED','SENDED'");
			Map<String, Object> map = queryFacade.query("countOrder", params);
			if (map != null) {
				List<HashMap<String, Long>> list = (List<HashMap<String, Long>>) map.get("valueList");

				int count = 0;
				if (list != null && list.size() > 0 && !String.valueOf(list.get(0).get("count")).equals("null")) {
					count = list.get(0).get("count").intValue();
				}
				int limitCountDay = 0;
//				if (serviceConfigBean.getLimitCountDay() != null) {
//					limitCountDay = serviceConfigBean.getLimitCountDay();
//				}
				if (count < limitCountDay) {
					msg = limitCountDay - count;
				}
				if (limitCountDay == 0) {
					msg = 9999999;
				}
			}
		} catch (Exception e) {
			logger.error("系统异常:", e);
			msg = 0;
		}
		this.write("{\"msg\":\"" + msg + "\"}");
	}

	/**
	 * 获取代付订单日总限额
	 */
	@SuppressWarnings("unchecked")
	public void getOrderSumAmount() {
		String msg = "0";
		try {
			Authorization auth = (Authorization) getSession().getAttribute("auth");
			ServiceConfigBean serviceConfigBean = serviceConfigFacade.query(auth.getCustomerno());
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ownerId", auth.getCustomerno());
			params.put("ownerRole", "CUSTOMER");
			params.put("createDate1", sf.format(new Date()) + "000000");
			params.put("createDate2", sf.format(new Date()) + "235959");
			params.put("handle_status", "'SUCCEED','SENDED'");
			Map<String, Object> map = queryFacade.query("countOrderAmount", params);
			if (map != null) {
				List<HashMap<String, String>> list = (List<HashMap<String, String>>) map.get("valueList");
				String count = "0";
				if (list != null && list.size() > 0 && !String.valueOf(list.get(0).get("amount")).equals("null")) {
					count = String.valueOf(list.get(0).get("amount"));
				}
//				msg = String.valueOf(new BigDecimal(serviceConfigBean.getLimitAmountDay()).subtract(new BigDecimal(count)));
			}
		} catch (Exception e) {
			logger.error("系统异常:",e);
			msg = "0";
		}
		this.write("{\"msg\":\"" + msg + "\"}");
	}

	/**
	 * 获取代付订单单笔限额
	 */
	public void getOrderSingleAmount() {
		String msg = "0";
		try {
			Authorization auth = (Authorization) getSession().getAttribute("auth");
			ServiceConfigBean serviceConfigBean = serviceConfigFacade.query(auth.getCustomerno());
			if (serviceConfigBean != null) {
				//XXX 最大
				msg = String.valueOf(serviceConfigBean.getMaxAmount());
			}
		} catch (Exception e) {
			logger.error("系统异常:",e);
			msg = "0";
		}
		this.write("{\"msg\":\"" + msg + "\"}");
	}

	/**
	 * 代付统计
	 */
	@SuppressWarnings({ "unchecked" })
	public void getDPayCount() {
		Authorization auth = (Authorization) getSession().getAttribute("auth");
		String msg = null;
		try {
			String accountNo = getHttpRequest().getParameter("accountNo");
			String accountName = getHttpRequest().getParameter("accountName");
			String cnapsName = getHttpRequest().getParameter("cnapsName");
			String requestNo = getHttpRequest().getParameter("requestNo");
			String status = getHttpRequest().getParameter("status");
			String createDate1 = getHttpRequest().getParameter("applyDate1");
			String createDate2 = getHttpRequest().getParameter("applyDate2");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ownerId", auth.getCustomerno());
			params.put("ownerRole", "CUSTOMER");
			params.put("accountNo", accountNo);
			params.put("accountName", accountName);
			params.put("cnapsName", cnapsName);
			params.put("requestNo", requestNo);
			params.putAll(convertDfStatus(status));
			if (createDate1 != null && !"".equals(createDate1)) {
				params.put("applyDate1", createDate1.replaceAll("-", "") + "000000");
			}
			if (createDate2 != null && !"".equals(createDate2)) {
				params.put("applyDate2", createDate2.replaceAll("-", "") + "235959");
			}
			Map<String, Object> map = queryFacade.query("dpayRequestCount", params);
			if (map != null) {
				List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) map.get("valueList");
				if (list != null && list.size() > 0) {
					if (list.get(0).get("amount") != null && list.get(0).get("pee") != null) {
						msg = list.get(0).get("flow_no") + "," + list.get(0).get("amount") + "," + list.get(0).get("pee");
					} else {
						msg = null;
					}
				}
			}
		} catch (Exception e) {
			logger.error("系统异常:",e);
			msg = "null";
		}
		this.write("{\"msg\":\"" + msg + "\"}");
	}
	/**
	 * 导出联系人信息
	 */
	public void dfPayeeExport() {
		Authorization auth = (Authorization) getSession().getAttribute("auth");
		String codes = getHttpRequest().getParameter("codes");
		if (codes != null && !"".equals(codes.trim())) {
			String queryId = "dpayTargetAccountExport";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("codes", codes);
			params.put("ownerId", auth.getCustomerno());
			params.put("ownerRole", "CUSTOMER");
			logger.info("dfQuery start...");
			Map<String, Object> returnMap = queryFacade.query(queryId, params);
			ValueList valueList = new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
					(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO));
			List<Map<String, String>> list = valueList.getList();

			try {
				InputStream in = new FileInputStream(getSession().getServletContext().getRealPath("/jsp/template/收款人模版.xlsx"));
				Workbook work = new XSSFWorkbook(in);
				// 得到excel的第0张表
				Sheet sheet = work.getSheetAt(0);
				// 得到第1行的第一个单元格的样式
				Row rowCellStyle = sheet.getRow(0);
				CellStyle columnOne = rowCellStyle.getCell(0).getCellStyle();
				// 这里面的行和列的数法与计算机里的一样，从0开始是第一
				// 填充title数据
				Row row = sheet.getRow(0);
				Cell cell = row.getCell(0);
				// 得到行，并填充数据和表格样式
				for (int i = 0; i < list.size(); i++) {
					row = sheet.createRow(i + 15);// 得到行
					cell = row.createCell(0);
					cell.setCellValue(list.get(i).get("account_name"));
					cell.setCellStyle(columnOne);
					cell = row.createCell(1);
					cell.setCellValue(list.get(i).get("account_no"));
					cell.setCellStyle(columnOne);
					cell = row.createCell(2);
					if (list.get(i).get("account_type").equals("OPEN")) {
						cell.setCellValue("对公");
					}
					if (list.get(i).get("account_type").equals("INDIVIDUAL")) {
						cell.setCellValue("对私");
					}
					cell.setCellStyle(columnOne);
					cell = row.createCell(3);
					cell.setCellValue(list.get(i).get("cnaps_name") != null ? list.get(i).get("cnaps_name") : list.get(i).get("sabk_name"));
					cell.setCellStyle(columnOne);
				}
				String sheetTitle = "收款联系人";
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setHeader("Pragma ", "public");
				response.setHeader("Cache-Control ", "must-revalidate, post-check=0, pre-check=0 ");
				response.addHeader("Cache-Control ", "public ");

				if (ServletActionContext.getRequest().getHeader("user-agent").indexOf("MSIE") != -1) {
					sheetTitle = java.net.URLEncoder.encode(sheetTitle, "utf-8") + "-" + com.pay.common.util.DateUtil.formatDate(new Date(), "yyyy-MM-dd hh:mm:ss")
							+ ".xlsx";
				} else {
					sheetTitle = new String(sheetTitle.getBytes("utf-8"), "iso-8859-1") + "-"
							+ com.pay.common.util.DateUtil.formatDate(new Date(), "yyyy-MM-dd hh:mm:ss") + ".xlsx";
				}
				response.setHeader("Content-disposition", "attachment;filename=" + sheetTitle);
				response.setContentType("application/vnd.ms-excel.numberformat:@;charset=UTF-8");
				work.write(response.getOutputStream());
				in.close();
			} catch (FileNotFoundException e) {
				logger.error("文件路径错误:", e);
			} catch (IOException e) {
				logger.error("文件输入流错误:", e);
			}

		}
		logger.info("dfQuery end...");
		// return SUCCESS;
	}

	/**
	 * 跳转到代付收款人管理
	 * 根据ADD、UPDATE来确定是否进行查询
	 */
	@SuppressWarnings({ "rawtypes" })
	public String toDfPayeeManage() {
		try {
			Authorization auth = (Authorization) getSession().getAttribute("auth");
			String code = getHttpRequest().getParameter("code");
			if (code != null && !"".equals(code.trim())) {
				String queryId = "dpayTargetAccount";
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("code", code);
				params.put("ownerId", auth.getCustomerno());
				params.put("ownerRole", "CUSTOMER");
				logger.info("dfQuery start...");
				Map<String, Object> returnMap = queryFacade.query(queryId, params);
				List resultList = (List) returnMap.get(QueryFacade.VALUELIST);
				if (resultList != null && resultList.size() > 0) {
					getHttpRequest().setAttribute("dpayTargetAccount", (Map) resultList.get(0));
				}
				logger.info("dfQuery end...");
			} else {
				getHttpRequest().setAttribute("dpayTargetAccount", new HashMap());
			}
		} catch (Exception e) {
			logger.error("系统异常:",e);
		}
		return SUCCESS;
	}

	/**
	 * 代付收款人信息（增）
	 */
//	public void dfPayeeManage() {
//		String msg = "成功";
//		try {
//			Authorization auth = (Authorization) getSession().getAttribute("auth");
//
//			OwnerParam owner = new OwnerParam(auth.getCustomerno(), "CUSTOMER");
//			targetAccountParam.setOpenBank(openBank);
//			logger.info("dfPayeeManage start...");
//
//			dpayFacade.createTargetAccount(owner, targetAccountParam);
//		} catch (Exception e) {
//			msg = "失败";
//			logger.error("系统异常:",e);
//		}
//		logger.info("dfPayeeManage start...");
//		this.write("{\"msg\":\"" + msg + "\"}");
//	}

	/**
	 * 代付收款人信息（删）
	 */
//	public void dfPayeeDelete() {
//		String msg = "成功";
//		String code = getHttpRequest().getParameter("code");
//		try {
//			dpayFacade.invalid(code);
//		} catch (Exception e) {
//			msg = "失败";
//			logger.error("系统异常:",e);
//		}
//		this.write("{\"msg\":\"" + msg + "\"}");
//	}

	/**
	 * 代付收款人信息（跳转修改）
	 */
//	public String toDfPayeeUpdate() {
//		Authorization auth = (Authorization) getSession().getAttribute("auth");
//		String code = getHttpRequest().getParameter("code");
//		try {
//			targetAccountParam = dpayFacade.findTargetAccountByCode(new OwnerParam(auth.getCustomerno(), "CUSTOMER"), code);
//			targetAccountParam.setCode(code);
//		} catch (Exception e) {
//			logger.error("系统异常:",e);
//		}
//		return SUCCESS;
//	}

	/**
	 * 代付收款人信息（修改）
	 */
//	public void dfPayeeUpdate() {
//		String msg = "成功";
//		try {
//			Authorization auth = (Authorization) getSession().getAttribute("auth");
//			OwnerParam owner = new OwnerParam(auth.getCustomerno(), "CUSTOMER");
//			targetAccountParam.setOpenBank(openBank);
//			logger.info("dfPayeeUpdate start...");
//
//			dpayFacade.updateTargetAccount(owner, targetAccountParam);
//		} catch (Exception e) {
//			msg = "失败";
//			logger.error("系统异常:",e);
//		}
//		logger.info("dfPayeeUpdate end...");
//		this.write("{\"msg\":\"" + msg + "\"}");
//	}

	/**
	 * 参数转换
	 */
	private Map<String, Object> retrieveParams(Map<String, String[]> requestMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		for (String key : requestMap.keySet()) {
			String[] value = requestMap.get(key);
			if (value != null) {
				resultMap.put(key, Array.get(value, 0).toString().trim());
			}
		}
		return resultMap;
	}

	/**
	 * 显示状态-数据库状态的转换
	 * AUDIT_WAIT 待审核
	 * AUDIT_REFUSE 审核拒绝
	 * HANDLING 处理中
	 * SUCCESS 付款成功
	 * FAILED 付款失败
	 * REFUND_WAIT 待退款
	 * REFUNDED 已退款（通过与拒绝）
	 * @param status 显示状态
	 * @return
	 */
	private Map<String, Object> convertDfStatus(String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		if ("AUDIT_WAIT".equals(status)) {
			map.put("df.auditStatus", "WAIT");
		} else if ("AUDIT_REFUSE".equals(status)) {
			map.put("df.auditStatus", "REFUSE");
		} else if ("HANDLING".equals(status)) {
			map.put("df.handling", "HANDLING");
		} else if ("SUCCEED".equals(status)) {
			map.put("dfRecord.handleStatus", "SUCCEED");
		} else if ("FAILED".equals(status)) {
			map.put("dfRecord.handleStatus", "FAILED");
		} else if ("REFUND_WAIT".equals(status)) {
			map.put("tk.auditStatus", "WAIT");
		} else if ("REFUNDED".equals(status)) {
			map.put("tk.refunded", "REFUNDED");
		}
		return map;
	}

	public List<Map<String, String>> getCnaps(String bankNames) throws Exception {
		if (bankNames == null || "".equals(bankNames)) {
			return new ArrayList<Map<String, String>>();
		}
		String words = "";
		if (bankNames.indexOf(",") > -1) {
			String[] bankName = bankNames.split(",");
			for (int i = 0; i < bankName.length; i++) {
				if (i == 0) {
					words += "words=" + URLEncoder.encode(bankName[i], "UTF-8");
					continue;
				}
				words += "&words=" + URLEncoder.encode(bankName[i], "UTF-8");
			}
		} else {
			words = "words=" + URLEncoder.encode(bankNames, "UTF-8");
			words += "&1=1";
		}
		List<Map<String, String>> list = null;
		try {
			String url = (String) prop.get("cachecenter.service.url")
					+ "/cnaps/batchSearch.htm?fields=clearingBankCode&fields=code&fields=providerCode&fields=clearingBankLevel&fields=name";
			String resData = HttpClientUtils.send(Method.POST, url, words, true, Charset.forName("UTF-8").name(), 40000);
			JsonNode cnapsNodes = JsonUtils.getInstance().readTree(resData);
			list = new ArrayList<>();
			for (JsonNode cnapsNode : cnapsNodes) {
				if (cnapsNode.size() == 0) {
					Map<String, String> map = new HashMap<String, String>();
					list.add(map);
					continue;
				}
				Map<String, String> map = new HashMap<String, String>();
				map.put("code", cnapsNode.get("code").toString());
				map.put("clearingBankCode", cnapsNode.get("clearingBankCode").toString());
				map.put("providerCode", cnapsNode.get("providerCode").toString());
				map.put("clearingBankLevel", cnapsNode.get("clearingBankLevel").toString());
				map.put("name", cnapsNode.get("name").toString());
				if ("null".equals(map.get("code"))) {
					map.put("code", "");
				}
				list.add(map);
			}
		} catch (Exception e) {
			logger.error("系统异常:",e);
		}
		return list;
	}

	public List<Map<String, Object>> getCardInfo(String panNos) throws Exception {
		if (panNos == null || "".equals(panNos)) {
			return new ArrayList<Map<String, Object>>();
		}
		String cardsNo = "";
		if (panNos.indexOf(",") > -1) {
			String[] panNoArr = panNos.split(",");
			for (int i = 0; i < panNoArr.length; i++) {
				if (i == 0) {
					cardsNo = "cardNos=" + panNoArr[i];
					continue;
				}
				cardsNo += "&" + "cardNos=" + panNoArr[i];
			}
		} else {
			cardsNo = "cardNos=" + panNos;
		}
		List<Map<String, Object>> list = null;
		try {
			String url = (String) prop.get("cachecenter.service.url") + "/iin/batchRecognition.htm?" + cardsNo;
			String resData = HttpClientUtils.send(Method.POST, url, "", false, "");
			JsonNode iinNode = JsonUtils.getInstance().readTree(resData);

			list = new ArrayList<>();
			for (JsonNode iins : iinNode) {
				if (iins.size() == 0) {
					Map<String, Object> map = new HashMap<>();
					map.put("providerCode", "");
					list.add(map);
					continue;
				}
				Map<String, Object> map = new HashMap<>();
				map.put("providerCode", iins.get("providerCode"));
				list.add(map);
			}
		} catch (Exception e) {
			logger.error("系统异常:",e);
		}
		return list;
	}

	public List<Map<String, String>> getSabkByProCode(List<Map<String, Object>> proCodes) throws Exception {
		if (proCodes == null || proCodes.size() == 0) {
			return new ArrayList<Map<String, String>>();
		}
		String providerCode = "";
		for (int i = 0; i < proCodes.size(); i++) {
			if (proCodes.get(i) == null || "".equals(proCodes.get(i))) {
				continue;
			}
			providerCode = "&providerCode=" + proCodes.get(i).get("providerCode") + "&clearBankLevel=1";
		}
		List<Map<String, String>> list = null;
		try {
			String url = (String) prop.get("cachecenter.service.url") + "/cnaps/batchSearch.htm?limit=1&fields=providerCode&fields=clearingBankCode" + providerCode;
			String resData = HttpClientUtils.send(Method.POST, url, "", false, "");
			JsonNode sabkCodes = JsonUtils.getInstance().readTree(resData);
			list = new ArrayList<>();
			for (JsonNode sabkCode : sabkCodes) {
				if (sabkCode.size() == 0) {
					Map<String, String> map = new HashMap<String, String>();
					list.add(map);
					continue;
				}
				Map<String, String> map = new HashMap<String, String>();
				map.put("clearingBankCode", sabkCode.get("clearingBankCode").toString());
				list.add(map);
			}
		} catch (Exception e) {
			logger.error("系统异常:",e);
		}
		return list;
	}

	public boolean checkCard(String cardNo) {
		if (!NumberUtils.isNumber(cardNo) || cardNo.length() < 14) return false;

		String[] nums = cardNo.split("");
		int sum = 0;
		int index = 1;
		for (int i = 0; i < nums.length; i++) {
			if ((i + 1) % 2 == 0) {
				if ("".equals(nums[nums.length - index])) {
					continue;
				}
				int tmp = Integer.parseInt(nums[nums.length - index]) * 2;
				if (tmp >= 10) {
					String[] t = String.valueOf(tmp).split("");
					tmp = Integer.parseInt(t[1]) + Integer.parseInt(t[2]);
				}
				sum += tmp;
			} else {
				if ("".equals(nums[nums.length - index])) continue;
				sum += Integer.parseInt(nums[nums.length - index]);
			}
			index++;
		}
		if (sum % 10 != 0) {
			return false;
		}
		return true;
	}

	public Map<String, String> getSabkCode(String proCode, String clearBankLevel) {
		String res = "";
		try {
			res = "word=&";
			res = res + "providerCode=" + proCode + "&";
			res = res + "clearBankLevel=" + clearBankLevel;
			String url = (String) prop.get("cachecenter.service.url")
					+ "/cnaps/suggestSearch.htm?fields=providerCode&fields=name&fields=clearingBankCode&fields=providerCode";
			String resData = HttpClientUtils.send(Method.POST, url, res, true, Charset.forName("UTF-8").name(), 6000);
			JsonNode sabkCodes = JsonUtils.getInstance().readTree(resData);

			Map<String, String> map = null;

			for (JsonNode sabkCode : sabkCodes) {
				if (sabkCode.size() != 0) {
					map = new HashMap<String, String>();
					map.put("clearingBankCode", sabkCode.get("clearingBankCode").toString());
				}
			}
			return map;
		} catch (IOException e) {
			logger.error("系统异常:", e);
		}
		return null;
	}

	public void toCachecenterCnaps() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String res = "";
		try {
			res = res + "word=" + request.getParameterMap().get("word")[0] + "&";
			res = res + "providerCode=" + request.getParameterMap().get("providerCode")[0] + "&";
			res = res + "clearBankLevel=" + request.getParameterMap().get("clearBankLevel")[0];
			String url = (String) prop.get("cachecenter.service.url")
					+ "/cnaps/suggestSearch.htm?fields=providerCode&fields=name&fields=clearingBankCode&fields=providerCode";
			String resData = HttpClientUtils.send(Method.POST, url, res, true, Charset.forName("UTF-8").name(), 6000);
			write(resData);
		} catch (IOException e) {
			logger.error("系统异常:", e);
		}
	}

	public void toCachecenterIin() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String res = "";
		try {
			res = res + "cardNo=" + request.getParameterMap().get("cardNo")[0];
			String url = (String) prop.get("cachecenter.service.url") + "/iin/recognition.htm?" + res;
			String resData = HttpClientUtils.send(Method.POST, url, "", true, Charset.forName("UTF-8").name(), 6000);
			write(resData);
		} catch (IOException e) {
			logger.error("系统异常:", e);
		}
	}

	public QueryFacade getQueryFacade() {
		return queryFacade;
	}

	public void setQueryFacade(QueryFacade queryFacade) {
		this.queryFacade = queryFacade;
	}

	public DpayFacade getDpayFacade() {
		return dpayFacade;
	}

	public void setDpayFacade(DpayFacade dpayFacade) {
		this.dpayFacade = dpayFacade;
	}


	public ServiceConfigFacade getServiceConfigFacade() {
		return serviceConfigFacade;
	}

	public void setServiceConfigFacade(ServiceConfigFacade serviceConfigFacade) {
		this.serviceConfigFacade = serviceConfigFacade;
	}

	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}

	public File getExcel() {
		return excel;
	}

	public void setExcel(File excel) {
		this.excel = excel;
	}

	public String getErrorsMsg() {
		return errorsMsg;
	}

	public void setErrorsMsg(String errorsMsg) {
		this.errorsMsg = errorsMsg;
	}

	public List<String[]> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<String[]> errorList) {
		this.errorList = errorList;
	}

	public AccountRealQueryInterface getAccountRealQueryInterface() {
		return accountRealQueryInterface;
	}

	public void setAccountRealQueryInterface(AccountRealQueryInterface accountRealQueryInterface) {
		this.accountRealQueryInterface = accountRealQueryInterface;
	}

	public List<RequestBean> getDfBatch() {
		return dfBatch;
	}

	public void setDfBatch(List<RequestBean> dfBatch) {
		this.dfBatch = dfBatch;
	}

	public RequestBean getDfSingle() {
		return dfSingle;
	}

	public void setDfSingle(RequestBean dfSingle) {
		this.dfSingle = dfSingle;
	}

	public List<RequestBean> getFullRequestList() {
		return fullRequestList;
	}

	public void setFullRequestList(List<RequestBean> fullRequestList) {
		this.fullRequestList = fullRequestList;
	}

}

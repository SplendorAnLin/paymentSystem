package com.yl.customer.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
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
import com.yl.customer.Constant;
import com.yl.customer.entity.Authorization;
import com.yl.dpay.hessian.beans.PayeeBean;
import com.yl.dpay.hessian.beans.RequestBean;
import com.yl.dpay.hessian.beans.ResponseBean;
import com.yl.dpay.hessian.beans.ServiceConfigBean;
import com.yl.dpay.hessian.enums.AccountType;
import com.yl.dpay.hessian.service.DpayFacade;
import com.yl.dpay.hessian.service.QueryFacade;
import com.yl.dpay.hessian.service.ServiceConfigFacade;
import com.yl.dpay.hessian.utils.RSAUtils;
import net.mlw.vlh.DefaultListBackedValueList;
import net.mlw.vlh.ValueListInfo;

/**
 * 代付控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月4日
 * @version V1.0.0
 */
public class DfAction extends Struts2ActionSupport {
	private static final long serialVersionUID = 1L;
	private QueryFacade queryFacade;
	private DpayFacade dpayFacade;
	private RequestBean dfSingle;
	private ServiceConfigFacade serviceConfigFacade;
	private String customerNo;
	private File excel;
	private String excelFileName;
	private String errorsMsg;
	private List<String[]> errorList;
	private String newPublicKey;
	private String privateKey;
	private ServiceConfigBean serviceConfigBean;
	private Double amount;
	private PayeeBean payee;
	private List<RequestBean> requestBeanList;
	private String msg;
	private String idCard;

	private static Properties prop = new Properties();

	static {
		try {
			prop.load(DfAction.class.getClassLoader().getResourceAsStream("system.properties"));
		} catch (IOException e) {
			logger.error("系统异常:", e);
		}
	}

	/**
	 * 提现
	 */
	public void drawCash() {
		Authorization auth = (Authorization) getSession().getAttribute("auth");
		try {
			if (amount != null && amount > 0) {
				ResponseBean bean = dpayFacade.drawCash(auth.getCustomerno(), amount);
				if (bean != null) {
					if (bean.getRequestStatus() != null) {
						write(bean.getRequestStatus() + "_" + bean.getResponseMsg());
					} else {
						write(bean.getResponseMsg());
					}
				} else {
					write("FAILED");
				}
			}
		} catch (Exception e) {
			logger.error("系统异常:", e);
			write("FAILED" + "_" + e.getMessage());
		}
	}

	/**
	 * 跳转修改密码
	 */
	public String toDfComplexPWDModify() {
		Authorization auth = (Authorization) getSession().getAttribute("auth");
		serviceConfigBean = serviceConfigFacade.query(auth.getCustomerno());
		return SUCCESS;
	}

	/**
	 * 跳转修改失败代付单
	 */
	public String toFaildRequest() {
		dfSingle = queryFacade.findByFlowNo(dfSingle.getFlowNo());
		return SUCCESS;
	}

	/**
	 * 查询失败订单
	 */
	public String dfFailedQuery() {
		try {
			Authorization auth = (Authorization) getSession().getAttribute("auth");
			String queryId = "dpayRequest";
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			params.put("ownerId", auth.getCustomerno());
			Map<String, Object> returnMap = queryFacade.query(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
							(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}

	/**
	 * 查询失败订单
	 */
	public String dfHistoryQuery() {
		try {
			Authorization auth = (Authorization) getSession().getAttribute("auth");
			String queryId = "dpayRequestHistory";
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			params.put("ownerId", auth.getCustomerno());
			Map<String, Object> returnMap = queryFacade.query(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
							(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}

	/**
	 * 代付统计
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getDPayCount() {
		try {
			Authorization auth = (Authorization) getSession().getAttribute("auth");
			String queryId = "dpayRequestCount";
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			params.put("ownerId", auth.getCustomerno());
			Map<String, Object> returnMap = queryFacade.query(queryId, params);
			List valueList = (List) returnMap.get(QueryFacade.VALUELIST);
			Map<String, Object> map = (Map<String, Object>) valueList.get(0);
			if (map != null && map.get("flow_no") != null && map.get("amount") != null) {
				Map<String, String> jMap = new HashMap<String, String>();
				jMap.put("no", map.get("flow_no").toString());
				jMap.put("amount", map.get("amount").toString());
				jMap.put("fee", map.get("fee").toString());
				getHttpResponse().getWriter().write(JsonUtils.toJsonString(jMap));
			} else {
				getHttpResponse().getWriter().write("null");
			}
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
	}

	/**
	 * 更换密钥
	 */
	public void dfChangeKeys() {
		try {
			Map<String, Object> keysMap = RSAUtils.genKeyPair();
			String resStr = null;
			String puk = RSAUtils.getPrivateKey(keysMap);
			if (puk.indexOf("\r\n") > -1) {
				resStr = RSAUtils.getPrivateKey(keysMap).replaceAll("\r\n", "&#92;r&#92;n\r\n") + "_"
						+ RSAUtils.getPublicKey(keysMap).replaceAll("\r\n", "&#92;r&#92;n\r\n");
			} else {
				resStr = RSAUtils.getPrivateKey(keysMap).replaceAll("\n", "&#92;r&#92;n\r\n") + "_"
						+ RSAUtils.getPublicKey(keysMap).replaceAll("\n", "&#92;r&#92;n\r\n");
			}
			getHttpResponse().getWriter().write(resStr);
		} catch (Exception e) {
			logger.error("商户:" + customerNo + "生成密钥异常: {}", e);
		}

	}

	/**
	 * 获取原密钥,并且新生成一对密钥
	 */
	public void getDfChangeKeys() {
		try {
			serviceConfigBean = serviceConfigFacade.query(customerNo);
			if (serviceConfigBean != null) {
				Map<String, Object> keysMap = RSAUtils.genKeyPair();
				String resStr = null;
				if (serviceConfigBean.getPublicKey().indexOf("\r\n") > -1) {
					resStr = serviceConfigBean.getPublicKey().replaceAll("\r\n", "&#92;r&#92;n\r\n") + "_"
							+ RSAUtils.getPublicKey(keysMap).replaceAll("\r\n", "&#92;r&#92;n\r\n") + "_"
							+ RSAUtils.getPrivateKey(keysMap).replaceAll("\r\n", "&#92;r&#92;n\r\n");
				} else {
					resStr = serviceConfigBean.getPublicKey().replaceAll("\n", "&#92;r&#92;n\r\n") + "_"
							+ RSAUtils.getPublicKey(keysMap).replaceAll("\n", "&#92;r&#92;n\r\n") + "_"
							+ RSAUtils.getPrivateKey(keysMap).replaceAll("\n", "&#92;r&#92;n\r\n");
				}

				getHttpResponse().getWriter().write(resStr);
			} else {
				getHttpResponse().getWriter().write("");
			}
		} catch (Exception e) {
			logger.error("商户:" + customerNo + "生成密钥异常: {}", e);
		}
	}

	/**
	 * 更新商户密钥
	 */
	public String dPayKeyUpdate() {
		try {
			serviceConfigBean.setPublicKey(serviceConfigBean.getPublicKey().replaceAll("&#92;r&#92;n", ""));
			serviceConfigBean.setPrivateKey(serviceConfigBean.getPrivateKey().replaceAll("&#92;r&#92;n", ""));
			serviceConfigFacade.updateKeys(serviceConfigBean);
			errorsMsg = "success";
		} catch (Exception e) {
			logger.error("商户:" + customerNo + "更新密钥异常: {}", e);
			errorsMsg = "failed";
		}
		return SUCCESS;
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
			params.put("ownerId", auth.getCustomerno());
			Map<String, Object> returnMap = queryFacade.query(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
							(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		} catch (Exception e) {
			logger.error("系统异常:", e);
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
			Map<String, Object> returnMap = queryFacade.query(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
							(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		} catch (Exception e) {
			logger.error("系统异常:", e);
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
			String queryId = "dpayRequestExport";
			Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
			Map<String, Object> params = retrieveParams(requestMap);
			String status = (String) params.get("status");
			Map<String, Object> statusMap = convertDfStatus(status);
			params.putAll(statusMap);
			params.put("ownerId", auth.getCustomerno());
			Map<String, Object> returnMap = queryFacade.query(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
							(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}

	/**
	 * 代付复核 代付请求CODE
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
			flag = flag.equals("TRUE") ? "PASS" : "REFUSE";

			for (String co : codes) {
				try {
					String lock = CacheUtils.get(Constant.LockPrefix + auth.getCustomerno() + "_" + co, String.class);
					if (StringUtils.isBlank(lock)) {
						logger.info("商户编号：" + auth.getCustomerno() + "，订单号：" + co + " 加锁 ");
						CacheUtils.setEx(Constant.LockPrefix + auth.getCustomerno() + "_" + co, "lock", 120);
						try {
							logger.info("代付审核流水号 code:" + co + "flag:" + flag);
							dpayFacade.audit(co, flag, auth.getUsername());
							successNum++;
							successFlows += ",\"" + co + "\"";
						} catch (Exception e) {
							failNum++;
							failFlows += ",\"" + co + "\"";
							logger.error(e.getMessage());
						}
					} else {
						failNum++;
						failFlows += ",\"" + co + "_重复提交\"";
					}
				} catch (Exception e) {
					logger.error("系统异常", e);
				}finally {
					logger.info("商户编号：" + auth.getCustomerno() + "，订单号：" + co + " 解锁 ");
					CacheUtils.del(Constant.LockPrefix + auth.getCustomerno() + "_" + co);
				}
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
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}

		this.write(msg);
	}

	/**
	 * 批量代付申请
	 */
	public void dfOnetoMany() {
		String msg = "成功";
		try {
			Authorization auth = (Authorization) getSession().getAttribute("auth");
			dfSingle.setOwnerId(auth.getCustomerno());
			ResponseBean bean = dpayFacade.singleRequest(dfSingle);
			if (!"S3100".equals(bean.getResponseCode()) && !"S3000".equals(bean.getResponseCode())
					&& !"S3101".equals(bean.getResponseCode())) {
				msg = "失败";
			}
		} catch (Exception e) {
			logger.error("系统异常:", e);
			msg = "失败";
		}
		this.write("{\"msg\":\"" + msg + "\"}");
	}

	/**
	 * 代付单笔申请
	 */
	public void dfSingleApply() {
		String msg = "成功";
		try {
			Authorization auth = (Authorization) getSession().getAttribute("auth");
			dfSingle.setAccountName(java.net.URLDecoder.decode(dfSingle.getAccountName(), "utf-8"));
			dfSingle.setOwnerId(auth.getCustomerno());
			ResponseBean bean = dpayFacade.singleRequest(dfSingle);
			// 保存收款人信息
			if (dfSingle.isSaveInfo()) {
				payee.setCreateDate(new Date());
				payee.setAccountName(dfSingle.getAccountName());
				payee.setAccountNo(dfSingle.getAccountNo());
				payee.setAccountType(AccountType.valueOf(dfSingle.getAccountType()));
				payee.setBankCode(dfSingle.getBankCode());
				payee.setOpenBankName(dfSingle.getBankName());
				payee.setBankCode(dfSingle.getBankCode());
                payee.setIdCardNo(dfSingle.getCerNo());
				payee.setOwnerId(auth.getCustomerno());
				serviceConfigFacade.create(payee);

			}
			if (!"S3100".equals(bean.getResponseCode()) && !"S3000".equals(bean.getResponseCode())
					&& !"S3101".equals(bean.getResponseCode())) {
				msg = "失败";
			}
		} catch (Exception e) {
			logger.error("系统异常:", e);
			msg = "失败";
		}
		this.write("{\"msg\":\"" + msg + "\"}");
	}

	/**
	 * 批量代付申请
	 */
	public void dfUploadBatchApply() {
		String msg = "成功";
		try {
			Authorization auth = (Authorization) getSession().getAttribute("auth");
			for (RequestBean s : requestBeanList) {
				if (s != null) {
					dfSingle = new RequestBean();
					dfSingle = s;
					dfSingle.setAccountName(java.net.URLDecoder.decode(dfSingle.getAccountName(), "utf-8"));
					dfSingle.setOwnerId(auth.getCustomerno());
					ResponseBean bean = dpayFacade.singleRequest(dfSingle);
					// 保存收款人信息
					if (Boolean.valueOf(getHttpRequest().getParameter("isBatchSaveInfo")) == true) {
						payee = new PayeeBean();
						payee.setCreateDate(new Date());
						payee.setAccountName(dfSingle.getAccountName());
						payee.setAccountNo(dfSingle.getAccountNo());
						payee.setAccountType(AccountType.valueOf(dfSingle.getAccountType()));
						payee.setBankCode(dfSingle.getBankCode());
						payee.setOpenBankName(dfSingle.getBankName());
						payee.setBankCode(dfSingle.getBankCode());
						payee.setOwnerId(auth.getCustomerno());
                        payee.setIdCardNo(dfSingle.getCerNo());
						serviceConfigFacade.create(payee);
					}
					if (!"S3100".equals(bean.getResponseCode()) && !"S3000".equals(bean.getResponseCode())
							&& !"S3101".equals(bean.getResponseCode())) {
						msg = "失败";
					}
				}
			}
		} catch (Exception e) {
			logger.error("系统异常:", e);
			msg = "失败";
		}
		this.write("{\"msg\":\"" + msg + "\"}");
	}

	@SuppressWarnings("unused")
	private boolean isCnorEn(char c) {
		if ((c >= 0x0391 && c <= 0xFFE5) // 中文字符
				|| (c >= 65 && c <= 90) // A-Z
				|| (c >= 97 && c <= 122)) // a-z
			// 英文字符
			return true;
		return false;
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
	 * 代付收款文件上传
	 */
	@SuppressWarnings({ "resource" })
	public String dfPayeeUpload() {
		Authorization auth = (Authorization) getSession().getAttribute("auth");
		ServiceConfigBean serviceConfigBean = serviceConfigFacade.query(auth.getCustomerno());
		if (serviceConfigBean == null) {
			getHttpRequest().setAttribute("errorMsg", "对不起，由于您尚未开通代付，此功能暂未开放，如有需要，请联系管理员。");
			return "toDfSingleApply";
		}

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
				//errorsMsg += "文件格式不正确,"; // 不识别
				return "toDfSingleApply";
			}
		} catch (Throwable e) {
			logger.error("系统异常:", e);
			getHttpRequest().setAttribute("errorMsg", "文件上传错误。");
			//errorsMsg += "文件上传错误,"; // 上传错误
			return "toDfSingleApply";
		}

		Sheet sheet = wb.getSheetAt(0);

		if (sheet == null || sheet.getLastRowNum() < 1) {
			//errorsMsg += "无数据,"; // 无数据
			getHttpRequest().setAttribute("errorMsg", "请上传模板格式文件。");
			return "toDfSingleApply";
		}
		// 判断是否是模板格式文件开始
		try {
			Row template = sheet.getRow(14);

			if ("账户名称".equals(template.getCell(0).getStringCellValue().replaceAll("　", "").replaceAll(" ", "").trim())
					&& "银行账号".equals(
							template.getCell(1).getStringCellValue().replaceAll("　", "").replaceAll(" ", "").trim())
					&& "账户类型".equals(
							template.getCell(2).getStringCellValue().replaceAll("　", "").replaceAll(" ", "").trim())
					&& "开户银行".equals(
							template.getCell(3).getStringCellValue().replaceAll("　", "").replaceAll(" ", "").trim())
					&& "金额（元）".equals(
							template.getCell(4).getStringCellValue().replaceAll("　", "").replaceAll(" ", "").trim())
					&& "打款原因".equals(
							template.getCell(5).getStringCellValue().replaceAll("　", "").replaceAll(" ", "").trim())
                    && "身份证号".equals(
                    template.getCell(6).getStringCellValue().replaceAll("　", "").replaceAll(" ", "").trim())) {
				// do nothing
			} else {
				getHttpRequest().setAttribute("errorMsg", "请上传模板格式文件。");
				return "toDfSingleApply";
			}

		} catch (Exception e) {
			logger.error("系统异常:", e);
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
							// try catch 的目的是单元格未输入跑出异常，做为空处理
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
						amount = String.valueOf(row.getCell(4).getStringCellValue()).replaceAll("　", "")
								.replaceAll(" ", "").trim();
						if (StringUtils.isBlank(amount)) {
							throw new Exception("请输入代付金额。");
						}
						try {
							double d = Double.parseDouble(amount);
						} catch (Exception foramt) {
							throw new Exception("金额格式有误。");
						}
						if (BigDecimal.valueOf(Double.valueOf(amount)).compareTo(new BigDecimal(0)) <= 0
								|| amount == null || "".equals(amount)) {
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
						throw new Exception("账户名称格式不正确。");
					}

					msg = "银行账号";
					strs[1] = row.getCell(1).getStringCellValue().replaceAll("　", "").replaceAll(" ", "").trim();
					if (StringUtils.isBlank(strs[1])) {
						throw new Exception("请输入收款账号。");
					} else {
						
						if (!strs[2].equals("对公")) {
							
							if(!isAccountNoAllDigital(strs[1])){
								throw new Exception("收款账号格式不正确。");
							}else {
								// 卡识别
								try {
									boolean Card = chckCard(strs[1]);
									if (Card == false) {
										throw new Exception("收款账号不正确。");
									}
								} catch (Exception e) {
									throw new Exception("收款账号不正确。");
								}
							}
							
						}
						
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
						amount = String.valueOf(row.getCell(4).getStringCellValue()).replaceAll("　", "")
								.replaceAll(" ", "").trim();
					}
					if (StringUtils.isBlank(amount)) {
						throw new Exception("请输入代付金额。");
					}
					try {
						double d = Double.parseDouble(amount);
					} catch (Exception foramt) {
						throw new Exception("金额格式有误。");
					}
					if (BigDecimal.valueOf(Double.valueOf(amount)).compareTo(new BigDecimal(0)) <= 0 || amount == null
							|| "".equals(amount)) {
						throw new Exception("金额请大于0元。");
					} else {
						String singleAmount = String.valueOf(serviceConfigBean.getMaxAmount());
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
                        DecimalFormat nf = new DecimalFormat("0");
                        strs[5] = row.getCell(5).getStringCellValue().trim();
                        strs[6] = row.getCell(6).getStringCellValue().trim();
                    } catch (Exception e) {

                    }
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
						} catch (Exception e1) {
						}
					}
					errorsmsg[6] = (i + 1) + "";
					if (e.getMessage() == null || e.getMessage() == "") {
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

			// 传值
			requestBeanList = new ArrayList<RequestBean>();
			for (String[] s : strList) {
				dfSingle = new RequestBean();
				dfSingle.setAccountName(s[0]);
				dfSingle.setAccountNo(s[1]);
				dfSingle.setAccountType(s[2]);
				dfSingle.setBankName(s[3]);
				dfSingle.setAmount(Double.valueOf(s[4]));
				dfSingle.setDescription(s[5]);
                dfSingle.setCerNo(s[6]);
				requestBeanList.add(dfSingle);
			}

			// 根据银行账号为cerType，bankCode赋值
//			String res = "";
//			for (RequestBean s : requestBeanList) {
//				res = "";
//				res = res + "cardNo=" + s.getAccountNo();
//				String url = (String) prop.get("cachecenter.service.url") + "/iin/recognition.htm?" + res;
//				Map<String, String> resMap=new HashMap<>();
//				boolean b = false;
//				
////				resMap =JsonUtils.toObject(getData(url), Map.class);
//				
//				resMap =JsonUtils.toObject(getData(url), Map.class);
//				
//				
//				Iterator keys = resMap.keySet().iterator();
//				while(keys.hasNext()){
//					String key = (String)keys.next();
//					if("message".equals(key)){
//						b = true;
//					}
//				}
//				if(b == true){
//					String[] errorsmsg = new String[8];
//					errorsmsg[6] = "";
//					errorsmsg[0] = s.getAccountName();
//					errorsmsg[1] = s.getAccountNo();
//					errorsmsg[2] = s.getAccountType();
//					errorsmsg[3] = s.getBankName();
//					errorsmsg[4] = s.getAmount().toString();
//					errorsmsg[7] = "暂不支持此银行。";
//					errorList.add(errorsmsg);
//					requestBeanList.remove(s);
//					continue;
//				} else {
//					s.setCardType(resMap.get("cardType"));
//					s.setBankCode(resMap.get("providerCode"));
//				}
//			}
			
			// 根据银行账号为cerType，bankCode赋值
			String res = "";
			
//			Iterator<RequestBean> it = requestBeanList.iterator();
//			
//			while (it.hasNext()) {
//				RequestBean requestBean = (RequestBean) it.next();
//				res = "";
//				res = res + "cardNo=" + requestBean.getAccountNo();
//				String url = (String) prop.get("cachecenter.service.url") + "/iin/recognition.htm?" + res;
//				boolean b = false;
//
//				String resData = null;
//				try {
//					resData = HttpClientUtils.send(Method.POST, url, "", true, Charset.forName("UTF-8").name(),
//							6000);
//					Map<String, String> resMap = JsonUtils.toObject(resData, Map.class);
//
//					Iterator keys = resMap.keySet().iterator();
//					while(keys.hasNext()){
//						String key = (String)keys.next();
//						if("message".equals(key)){
//							b = true;
//						}
//					}
//					if(b == true){
//						String[] errorsmsg = new String[8];
//						errorsmsg[6] = "";
//						errorsmsg[0] = requestBean.getAccountName();
//						errorsmsg[1] = requestBean.getAccountNo();
//						errorsmsg[2] = requestBean.getAccountType();
//						errorsmsg[3] = requestBean.getBankName();
//						errorsmsg[4] = requestBean.getAmount().toString();
//						errorsmsg[7] = "暂不支持此银行，请在Excel中自行更改，并重新上传";
//						errorList.add(errorsmsg);
//						requestBeanList.remove(requestBean);
//					} else {
//						requestBean.setCardType(resMap.get("cardType"));
//						requestBean.setBankCode(resMap.get("providerCode"));
//					}
//				} catch (Exception e) {
//					logger.error("系统异常:", e);
//				}
//			}
			
			
			
			
			
			List<RequestBean> st = new ArrayList<RequestBean>();
			
			
			for (RequestBean s : requestBeanList) {
				
				if(s.getAccountType().equals("对公")){
					continue;
				}
				
				res = "";
				res = res + "cardNo=" + s.getAccountNo();
				String url = (String) prop.get("cachecenter.service.url") + "/iin/recognition.htm?" + res;
				boolean b = false;

				String resData = null;
				try {
					resData = HttpClientUtils.send(Method.POST, url, "", true, Charset.forName("UTF-8").name(),
							6000);
					Map<String, String> resMap = JsonUtils.toObject(resData, Map.class);

					Iterator keys = resMap.keySet().iterator();
					while(keys.hasNext()){
						String key = (String)keys.next();
						if("message".equals(key)){
							b = true;
						}
					}
					if(b == true){
						String[] errorsmsg = new String[8];
						errorsmsg[6] = "";
						errorsmsg[0] = s.getAccountName();
						errorsmsg[1] = s.getAccountNo();
						errorsmsg[2] = s.getAccountType();
						errorsmsg[3] = s.getBankName();
						errorsmsg[4] = s.getAmount().toString();
						errorsmsg[7] = "暂不支持此银行。";
						errorList.add(errorsmsg);
						st.add(s);
					} else {
						if(!resMap.get("cardType").equals("CREDIT")){
							s.setCardType("DEBIT");
						}else{
							s.setCardType(resMap.get("cardType"));
						}
						s.setBankCode(resMap.get("providerCode"));
					}
				} catch (Exception e) {
					logger.error("系统异常:", e);
				}
			}
			
			if(st != null){
				for (RequestBean requestBean : st) {
					requestBeanList.remove(requestBean);
				}
			}
			
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}
	
	// 卡识别
	public boolean chckCard(String cardNo) {
		if (cardNo == "" || cardNo == null) {
			return false;
		}
		if (cardNo.length() < 12) {
			return false;
		}
		String[] n = cardNo.split("");
		String[] nums = new String[cardNo.length()];
		for (int j = 1; j < n.length; j++) {
			nums[j-1] = n[j];
		}
		
		int sum = 0;
		int index = 1;
		for (int i = 0; i < nums.length; i++) {
			if ((i + 1) % 2 == 0) {
				int tmp = Integer.parseInt(nums[nums.length - index]) * 2;
				if (tmp >= 10) {
					String t = String.valueOf(tmp);
 					tmp = Integer.parseInt(String.valueOf(t.charAt(0))) + Integer.parseInt(String.valueOf(t.charAt(1)));
				}
 				sum += tmp;
  			} else {
				sum += Integer.parseInt(nums[nums.length - index]);
			}
  			index++;
		}

		if (sum % 10 != 0) {
			return false;
		}
		return true;
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
	 * 显示状态-数据库状态的转换 AUDIT_WAIT 待审核 AUDIT_REFUSE 审核拒绝 HANDLING 处理中 SUCCESS 付款成功
	 * FAILED 付款失败 REFUND_WAIT 待退款 REFUNDED 已退款（通过与拒绝）
	 * 
	 * @param status
	 *            显示状态
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

	public boolean checkCard(String cardNo) {
		if (!NumberUtils.isNumber(cardNo) || cardNo.length() < 14)
			return false;

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
				if ("".equals(nums[nums.length - index]))
					continue;
				sum += Integer.parseInt(nums[nums.length - index]);
			}
			index++;
		}
		if (sum % 10 != 0) {
			return false;
		}
		return true;
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
			String resData = HttpClientUtils.send(Method.POST, url, words, true, Charset.forName("UTF-8").name(),
					40000);
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
			logger.error("系统异常:", e);
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
			logger.error("系统异常:", e);
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
			String url = (String) prop.get("cachecenter.service.url")
					+ "/cnaps/batchSearch.htm?limit=1&fields=providerCode&fields=clearingBankCode" + providerCode;
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
			logger.error("系统异常:", e);
		}
		return list;
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
			Map<String, String> resMap = JsonUtils.toObject(resData, Map.class);
			Map<String, String> r = new HashMap<>();
			if(!resMap.get("cardType").equals("CREDIT")){
				r.put("cardType", "DEBIT");
			}else{
				r.put("cardType", resMap.get("cardType"));
			}
			r.put("providerCode", resMap.get("providerCode"));
			write(JsonUtils.toJsonString(r));
		} catch (IOException e) {
			logger.error("系统异常:", e);
		}
	}
	
	/**
	 * 根据账户查询用户代付配置
	 * @return
	 */
	public String findDfConfigById(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		ServiceConfigBean serviceConfigBean = serviceConfigFacade.findServiceConfigById(auth.getCustomerno());
		Map<String,Object> sm = new HashMap<String,Object>();
		sm.put("maxAmount", serviceConfigBean.getMaxAmount());
		sm.put("minAmount", serviceConfigBean.getMinAmount());
		msg = JsonUtils.toJsonString(sm);
		return SUCCESS;
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

	public RequestBean getDfSingle() {
		return dfSingle;
	}

	public void setDfSingle(RequestBean dfSingle) {
		this.dfSingle = dfSingle;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getNewPublicKey() {
		return newPublicKey;
	}

	public void setNewPublicKey(String newPublicKey) {
		this.newPublicKey = newPublicKey;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public ServiceConfigBean getServiceConfigBean() {
		return serviceConfigBean;
	}

	public void setServiceConfigBean(ServiceConfigBean serviceConfigBean) {
		this.serviceConfigBean = serviceConfigBean;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public PayeeBean getPayee() {
		return payee;
	}

	public void setPayee(PayeeBean payee) {
		this.payee = payee;
	}

	public List<RequestBean> getRequestBeanList() {
		return requestBeanList;
	}

	public void setRequestBeanList(List<RequestBean> requestBeanList) {
		this.requestBeanList = requestBeanList;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}
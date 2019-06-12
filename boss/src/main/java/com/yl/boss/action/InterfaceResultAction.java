package com.yl.boss.action;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.type.TypeReference;
import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.bean.InterfaceRequestQueryBean;
import com.yl.payinterface.core.hessian.InterfaceRequestHessianService;

/**
 * 接口结果控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月28日
 * @version V1.0.0
 */
public class InterfaceResultAction extends Struts2ActionSupport {

	private static final long serialVersionUID = -2754974349991513765L;

	private InterfaceRequestHessianService interfaceRequestHessianService;
	private InterfaceRequestQueryBean requestBean;
	private String sumInfo;
	private List<InterfaceRequestQueryBean> InterfaceRequestBeanList;
	private Page page;
	private String interfaceReqId;
	private InterfaceRequestQueryBean interfaceRequest;
	private String msg;
	private InputStream inputStream;

	private static Properties prop = new Properties();
	static {
		try {
			prop.load(new InputStreamReader(CustomerAction.class.getClassLoader().getResourceAsStream("system.properties")));
		} catch (IOException e) {
			logger.error("InterfaceResultAction load Properties error:", e);
		}
	}

	public String findAll() {
		try {
			HttpServletRequest request = this.getHttpRequest();
			page = new Page();
			int currentPage = request.getParameter("currentPage") == null ? 1 : Integer.parseInt(request.getParameter("currentPage"));
			if (currentPage > 1) {
				page.setCurrentPage(currentPage);
			}
			page.setCurrentResult((currentPage - 1) * page.getShowCount());
			Map<String, Object> map = ObjectToMap(requestBean);
			page = interfaceRequestHessianService.queryAll(page, map);
			InterfaceRequestBeanList = JsonUtils.toObject((String) page.getObject(), new TypeReference<List<InterfaceRequestQueryBean>>() {
			});

		} catch (Exception e) {
			logger.error("", e);
		}
		return "query";
	}

	/**
	 * 接口请求Export
	 * @return
	 * @throws IOException 
	 */
	public String findInterfaceRequestExport() throws IOException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> map = ObjectToMap(requestBean);
		List<Map<String, Object>> interfaceRequestQueryBeans = interfaceRequestHessianService.queryInterfaceRequestQuery(map);

		if (interfaceRequestQueryBeans != null && interfaceRequestQueryBeans.size() > 0) {

			int size = interfaceRequestQueryBeans.size();

			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet("账户历史");
			XSSFRow row = sheet.createRow(0);
			row.createCell(0).setCellValue("业务请求号");
			row.createCell(1).setCellValue("接口请求号");
			row.createCell(2).setCellValue("通道订单号");
			row.createCell(3).setCellValue("商户编号");
			row.createCell(4).setCellValue("订单金额");
			row.createCell(5).setCellValue("接口请求状态");
			row.createCell(6).setCellValue("通道返回码");
			row.createCell(7).setCellValue("返回码描述");
			row.createCell(8).setCellValue("支付接口");
			row.createCell(9).setCellValue("接口成本");
			row.createCell(10).setCellValue("创建时间");
			row.createCell(11).setCellValue("完成时间");
			
			for (int i = 0; i < size; i++) {

				row = sheet.createRow(i + 1);
				
				Map<String,Object> s= interfaceRequestQueryBeans.get(i);

				if(s.get("bussinessOrderID") != null){
					row.createCell(0).setCellValue(String.valueOf(s.get("bussinessOrderID")));
				}
				
				if(s.get("interfaceRequestID") != null){
					row.createCell(1).setCellValue(String.valueOf(s.get("interfaceRequestID")));
				}
				
				if(s.get("interfaceOrderID") != null){
					row.createCell(2).setCellValue(String.valueOf(s.get("interfaceOrderID")));
				}
				
				if(s.get("ownerID") != null){
					row.createCell(3).setCellValue(String.valueOf(s.get("ownerID")));
				}
				
				if(s.get("amount") != null){
					row.createCell(4).setCellValue(String.valueOf(AmountUtils.round(Double.valueOf(String.valueOf(s.get("amount"))), 2, RoundingMode.HALF_UP)));
				}
				
				if(s.get("status") != null){
					try {
						switch (String.valueOf(s.get("status"))) {
						case "UNKNOWN":
							row.createCell(5).setCellValue("未知");
							break;

						case "SUCCESS":
							row.createCell(5).setCellValue("成功");
							break;

						default:
							row.createCell(5).setCellValue("失败");
							break;
						}
					} catch (Exception e) {
						row.createCell(5).setCellValue("");
					}
				}
				
				if(s.get("responseCode") != null){
					row.createCell(6).setCellValue(String.valueOf(s.get("responseCode")));
				}
				
				if(s.get("responseMessage") != null){
					row.createCell(7).setCellValue(String.valueOf(s.get("responseMessage")));
				}
				
				if(s.get("interfaceInfoCode") != null){
					row.createCell(8).setCellValue(String.valueOf(s.get("interfaceInfoCode")));
				}
				
				
				
				if(s.get("fee") != null){
					row.createCell(9).setCellValue(String.valueOf(AmountUtils.round(Double.valueOf(String.valueOf(s.get("fee"))), 2, RoundingMode.HALF_UP)));
				}
				
				if(s.get("createTime") != null){
					row.createCell(10).setCellValue(sdf.format(s.get("createTime")));
				}
				
				if(s.get("completeTime") != null){
					row.createCell(11).setCellValue(sdf.format(s.get("completeTime")));
				}
			}
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			wb.write(os);
			byte[] fileContent = os.toByteArray();
			ByteArrayInputStream is = new ByteArrayInputStream(fileContent);
			inputStream = is;
			msg = "_InterfaceResultExecl.xlsx";
		}
		return SUCCESS;
	}

	public String toDetail() {
		try {
			interfaceRequest = interfaceRequestHessianService.findRequestByInterfaceReqId(interfaceReqId);
		} catch (Exception e) {
			logger.error("", e);
		}
		return "toRequestDetail";// "interfaceRequest/interfaceRequestDetail";
	}

	public String interfaceFeeSum() {
		sumInfo = interfaceRequestHessianService.queryAllSum(ObjectToMap(requestBean));
		Map<String, Object> map = JsonUtils.toObject(sumInfo, HashMap.class);
		DecimalFormat df = new DecimalFormat("#,##0.00");
		String am = "0";
		String rf = "0";
		String al = "0";
		if (map != null) {
			if (map.get("rf") != null) {
				rf = df.format(Double.parseDouble(map.get("rf").toString()));
			}
			if (map.get("am") != null) {
				am = df.format(Double.parseDouble(map.get("am").toString()));
			}
			if (map.get("al") != null) {
				al = map.get("al").toString();
			}
		}
		Map<String, String> m = new HashMap<String, String>();
		m.put("am", am);
		m.put("rf", rf);
		m.put("al", al);
		sumInfo = JsonUtils.toJsonString(m);
		return "interfaceFeeSum";
	}

	private Map<String, Object> ObjectToMap(Object object) {
		Map<String, Object> map = new HashMap<>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(object);

					map.put(key, value);
				}

			}
		} catch (Exception e) {
		}
		return map;
	}

	public InterfaceRequestQueryBean getRequestBean() {
		return requestBean;
	}

	public void setRequestBean(InterfaceRequestQueryBean requestBean) {
		this.requestBean = requestBean;
	}

	public List<InterfaceRequestQueryBean> getInterfaceRequestBeanList() {
		return InterfaceRequestBeanList;
	}

	public void setInterfaceRequestBeanList(List<InterfaceRequestQueryBean> interfaceRequestBeanList) {
		InterfaceRequestBeanList = interfaceRequestBeanList;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getInterfaceReqId() {
		return interfaceReqId;
	}

	public void setInterfaceReqId(String interfaceReqId) {
		this.interfaceReqId = interfaceReqId;
	}

	public InterfaceRequestQueryBean getInterfaceRequest() {
		return interfaceRequest;
	}

	public void setInterfaceRequest(InterfaceRequestQueryBean interfaceRequest) {
		this.interfaceRequest = interfaceRequest;
	}

	public void setInterfaceRequestHessianService(InterfaceRequestHessianService interfaceRequestHessianService) {
		this.interfaceRequestHessianService = interfaceRequestHessianService;
	}

	public String getSumInfo() {
		return sumInfo;
	}

	public void setSumInfo(String sumInfo) {
		this.sumInfo = sumInfo;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public InterfaceRequestHessianService getInterfaceRequestHessianService() {
		return interfaceRequestHessianService;
	}
}
package com.yl.boss.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.yl.boss.api.enums.ProductType;
import com.yl.boss.service.ShareProfitService;

/**
 * 分润信息控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
public class ShareProfitAction extends Struts2ActionSupport {

	private static final long serialVersionUID = -306494001843880568L;
	private ShareProfitService shareProfitService;
	private String msg;
	private InputStream inputStream;
	
	/**
	 * 查看合计
	 * @return
	 */
	public String shareProfitCount(){
		Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
		Map<String, Object> params = retrieveParams(requestMap);
		msg = shareProfitService.findByMapShareProfit(params);
		return SUCCESS;
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
	 * 导出
	 * @return
	 * @throws IOException 
	 */
	public String spExport() throws IOException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Object[]> shareProfits = shareProfitService.spExport(retrieveParams(getHttpRequest().getParameterMap()));
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("经营分析");
		XSSFRow row = sheet.createRow(0);
		row.createCell(0).setCellValue("订单号");
		row.createCell(1).setCellValue("接口编号");
		row.createCell(2).setCellValue("订单来源");
		row.createCell(3).setCellValue("产品类型");
		row.createCell(4).setCellValue("商户编号");
		row.createCell(5).setCellValue("订单金额");
		row.createCell(6).setCellValue("商户费率");
		row.createCell(7).setCellValue("订单手续费");
		row.createCell(8).setCellValue("上级服务商编号");
		row.createCell(9).setCellValue("上级服务商费率");
		row.createCell(10).setCellValue("分润类型");
		row.createCell(11).setCellValue("上级分润比例");
		row.createCell(12).setCellValue("上级服务商利润");
		row.createCell(13).setCellValue("直属服务商编号");
		row.createCell(14).setCellValue("直属服务商费率");
		row.createCell(15).setCellValue("分润类型");
		row.createCell(16).setCellValue("直属分润比例");
		row.createCell(17).setCellValue("直属服务商利润");
		row.createCell(18).setCellValue("间属服务商编号");
		row.createCell(19).setCellValue("间属服务商费率");
		row.createCell(20).setCellValue("分润类型");
		row.createCell(21).setCellValue("间属分润比例");
		row.createCell(22).setCellValue("间属服务商利润");
		row.createCell(23).setCellValue("通道成本");
		row.createCell(24).setCellValue("平台利润");
		row.createCell(25).setCellValue("订单完成时间");
		row.createCell(26).setCellValue("分润时间");
		
		if (shareProfits != null && shareProfits.size() > 0) {
			int size = shareProfits.size();
			for (int i = 0; i < size; i++) {
				row = sheet.createRow(i + 1);
				Object[] s= shareProfits.get(i);
				if(s[0] != null){
					row.createCell(0).setCellValue(String.valueOf(s[0]));
				}else {
					row.createCell(0).setCellValue("");
				}
				
				if(s[1] != null){
					row.createCell(1).setCellValue(String.valueOf(s[1]));
				}else {
					row.createCell(1).setCellValue("");
				}
				
				if(s[2] != null){
					switch (String.valueOf(s[2])) {
					case "ONLINE_TRADE":
						row.createCell(2).setCellValue("交易");
						break;
						
					case "RECEIVE":
						row.createCell(2).setCellValue("代收");
						break;

					default:
						row.createCell(2).setCellValue("代付");
						break;
					}
				}else {
					row.createCell(2).setCellValue("");
				}
				
				if(String.valueOf(String.valueOf(s[3])) != null && String.valueOf(s[3]) != ""){
					try {
						row.createCell(3).setCellValue(ProductType.valueOf(String.valueOf(s[3])).getMessage());
					} catch (Exception e) {
						row.createCell(3).setCellValue(String.valueOf(s[3]));
					}
				}
				
				if(s[4] != null){
					row.createCell(4).setCellValue(String.valueOf(s[4]));
				}else {
					row.createCell(4).setCellValue("");
				}
				
				if(s[5] != null){
					row.createCell(5).setCellValue(String.valueOf(s[5]));
				}else {
					row.createCell(5).setCellValue("");
				}
				
				if(s[6] != null){
					row.createCell(6).setCellValue(String.valueOf(s[6]));
				}else {
					row.createCell(6).setCellValue("");
				}
				
				if(s[7] != null){
					row.createCell(7).setCellValue(String.valueOf(s[7]));
				}else {
					row.createCell(7).setCellValue("");
				}
				
				if(s[8] != null){
					row.createCell(8).setCellValue(String.valueOf(s[8]));
				}else {
					row.createCell(8).setCellValue("");
				}
				
				if(s[9] != null){
					row.createCell(9).setCellValue(String.valueOf(s[9]));
				}else {
					row.createCell(9).setCellValue("");
				}
				
				if(s[10] != null && !"".equals(s[10])){
					switch (String.valueOf(s[10])) {
					case "FIXED_PROFIT":
						row.createCell(10).setCellValue("固定");
						break;
					case "RATIO_PROFIT":
						row.createCell(10).setCellValue("比例");
						break;
					default:
						row.createCell(10).setCellValue("未知");
						break;
					}
				}
				
				if(s[11] != null){
					row.createCell(11).setCellValue(String.valueOf(s[11]));
				}else {
					row.createCell(11).setCellValue("");
				}
				
				if(s[12] != null){
					row.createCell(12).setCellValue(String.valueOf(s[12]));
				}else {
					row.createCell(12).setCellValue("");
				}
				
				if(s[13] != null){
					row.createCell(13).setCellValue(String.valueOf(s[13]));
				}else {
					row.createCell(13).setCellValue("");
				}
				
				if(s[14] != null){
					row.createCell(14).setCellValue(String.valueOf(s[14]));
				}else {
					row.createCell(14).setCellValue("");
				}
				
				if(s[15] != null && !"".equals(s[15])){
					switch (String.valueOf(s[15])) {
					case "FIXED_PROFIT":
						row.createCell(15).setCellValue("固定");
						break;
					case "RATIO_PROFIT":
						row.createCell(15).setCellValue("比例");
						break;
					default:
						row.createCell(15).setCellValue("未知");
						break;
					}
				}
				
				if(s[16] != null){
					row.createCell(16).setCellValue(String.valueOf(s[16]));
				}else {
					row.createCell(16).setCellValue("");
				}
				
				if(s[17] != null){
					row.createCell(17).setCellValue(String.valueOf(s[17]));
				}else {
					row.createCell(17).setCellValue("");
				}
				
				if(s[18] != null){
					row.createCell(18).setCellValue(String.valueOf(s[18]));
				}else {
					row.createCell(18).setCellValue("");
				}
				
				if(s[19] != null){
					row.createCell(19).setCellValue(String.valueOf(s[19]));
				}else {
					row.createCell(19).setCellValue("");
				}
				
				if(s[20] != null && !"".equals(s[20])){
					switch (String.valueOf(s[20])) {
					case "FIXED_PROFIT":
						row.createCell(20).setCellValue("固定");
						break;
					case "RATIO_PROFIT":
						row.createCell(20).setCellValue("比例");
						break;
					default:
						row.createCell(20).setCellValue("未知");
						break;
					}
				}
				
				if(s[21] != null){
					row.createCell(21).setCellValue(String.valueOf(s[21]));
				}else {
					row.createCell(21).setCellValue("");
				}
				
				if(s[22] != null){
					row.createCell(22).setCellValue(String.valueOf(s[22]));
				}else {
					row.createCell(22).setCellValue("");
				}
				
				if(s[23] != null){
					row.createCell(23).setCellValue(String.valueOf(s[23]));
				}else {
					row.createCell(23).setCellValue("");
				}
				
				if(s[24] != null){
					row.createCell(24).setCellValue(String.valueOf(s[24]));
				}else {
					row.createCell(24).setCellValue("");
				}
				
				if(s[25] != null){
					row.createCell(25).setCellValue(sdf.format(s[25]));
				}else {
					row.createCell(25).setCellValue("");
				}
				
				if(s[26] != null){
					row.createCell(26).setCellValue(sdf.format(s[26]));
				}else {
					row.createCell(26).setCellValue("");
				}
				
			}
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			wb.write(os);
			byte[] fileContent = os.toByteArray();
            ByteArrayInputStream is = new ByteArrayInputStream(fileContent);
            inputStream = is;
            msg = "_ShareProfitExecl.xlsx";
		}
		return SUCCESS;
	}

	public ShareProfitService getShareProfitService() {
		return shareProfitService;
	}

	public void setShareProfitService(ShareProfitService shareProfitService) {
		this.shareProfitService = shareProfitService;
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
}
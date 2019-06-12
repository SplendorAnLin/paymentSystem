package com.yl.customer.action;

import java.awt.image.BufferedImage;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.imageio.ImageIO;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.HSSFColor.RED;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.codehaus.jackson.type.TypeReference;
import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.api.bean.Customer;
import com.yl.customer.Constant;
import com.yl.customer.bean.TradeOrderBean;
import com.yl.customer.entity.Authorization;
import com.yl.customer.enums.OperateType;
import com.yl.customer.service.CustomerService;
import com.yl.customer.utils.DateUtils;
import com.yl.dpay.hessian.beans.RequestBean;
import com.yl.dpay.hessian.service.RequestFacade;
import com.yl.online.model.bean.Payment;
import com.yl.online.model.model.Order;
import com.yl.online.trade.hessian.OnlineInterfacePolicyHessianService;
import com.yl.online.trade.hessian.OnlinePartnerRouterHessianService;
import com.yl.online.trade.hessian.OnlineTradeQueryHessianService;
import com.yl.online.trade.hessian.bean.InterfacePolicyBean;
import com.yl.online.trade.hessian.bean.InterfacePolicyProfileBean;
import com.yl.online.trade.hessian.bean.PartnerRouterBean;
import com.yl.online.trade.hessian.bean.PartnerRouterProfileBean;
import com.yl.payinterface.core.bean.InterfaceInfoBean;
import com.yl.payinterface.core.bean.InterfaceProviderQueryBean;
import com.yl.payinterface.core.hessian.InterfaceProviderHessianService;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;
import com.yl.receive.hessian.ReceiveQueryFacade;
import com.yl.receive.hessian.bean.ReceiveRequestBean;
import net.sf.json.JSONObject;

/**
 * 交易系统控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月8日
 * @version V1.0.0
 */
public class OnlineAction extends Struts2ActionSupport {

	private static final long serialVersionUID = 2129120623360256242L;
	private InterfaceProviderHessianService interfaceProviderHessianService;
	private OnlineInterfacePolicyHessianService onlineInterfacePolicyHessianService;
	private PayInterfaceHessianService payInterfaceHessianService;
	private OnlinePartnerRouterHessianService onlinePartnerRouterHessianService;
	private OnlineTradeQueryHessianService onlineTradeQueryHessianService;
	private List<InterfaceProviderQueryBean> interfaceProviderQueryBeans;
	private List<InterfaceInfoBean> interfaceInfoBeans;
	private InterfacePolicyBean interfacePolicyBean;
	private List<InterfacePolicyProfileBean> profiles;
	private String interfaceType;
	private List<InterfacePolicyBean> interfacePolicyBeans;
	private List<PartnerRouterProfileBean> partnerProfiles;
	private PartnerRouterBean partnerRouterBean;
	private Object partnerRouters;
	private Object interfacePolicys;
	private Page page;
	private Object tradeOrders;
	private String orderCode;
	private Object tradeOrder;
	private List<Payment> payments;
	private TradeOrderBean tradeOrderBean;
	private String sumInfo;
	private List<Order> order;
	private OperateType operateType;
	private String orderTimeStart;
	private String orderTimeEnd;
	private InputStream inputStream;
	private CustomerService customerService;
	private RequestFacade requestFacade;
	private List<RequestBean> requestBeans;
	private List<ReceiveRequestBean> receiveRequestBeans;
	private ReceiveQueryFacade receiveQueryFacade;
	private static Properties prop = new Properties();
	static {
		try {
			prop.load(
					new InputStreamReader(OnlineAction.class.getClassLoader().getResourceAsStream("system.properties")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 对账文件导出
	 * @throws ParseException 
	 */
	
	public String customerRecon() throws ParseException{
		try {
			boolean flag = false;
			String imgPath = prop.getProperty("execl.imgPath");
			String phone = prop.getProperty("execl.phone");
			Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
			BufferedImage bufferImg = null; // 图片
			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			bufferImg = ImageIO.read(new File(imgPath));
			ImageIO.write(bufferImg, "png", byteArrayOut);
			Map<String, Object> params = new HashMap<>();
			SimpleDateFormat myFmt2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			HSSFWorkbook wb = new HSSFWorkbook();
			
			if (sumInfo.equals("ONLINE")) {
				HSSFSheet sheet = wb.createSheet("交易信息");
				sheet.setColumnWidth(0, 20 * 256); 
				HSSFFont font = wb.createFont(); 
				
				font.setFontName("微软雅黑"); 
				font.setFontHeightInPoints((short) 15);//设置字体大小
				//font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
				HSSFCellStyle cellStyle = wb.createCellStyle();  
				cellStyle.setFont(font);  
				cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直  
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
				
				HSSFFont font2 = wb.createFont(); 
				font2.setFontName("微软雅黑"); 
				font2.setFontHeightInPoints((short)15);//设置字体大小
				//font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
				HSSFCellStyle cellStyle2 = wb.createCellStyle();
				
				
				HSSFFont font3 = wb.createFont(); 
				font3.setFontName("微软雅黑"); 
				font3.setFontHeightInPoints((short)15);//设置字体大小
				//font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
				HSSFCellStyle cellStyle3 = wb.createCellStyle(); 
				cellStyle3.setFont(font3);  
				
				// 设置边框样式
				cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);
				cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				cellStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);
				// 设置边框颜色
				cellStyle2.setTopBorderColor(HSSFColor.BLACK.index);
				cellStyle2.setBottomBorderColor(HSSFColor.BLACK.index);
				cellStyle2.setLeftBorderColor(HSSFColor.BLACK.index);
				cellStyle2.setRightBorderColor(HSSFColor.BLACK.index);
				// 设置背景颜色
				cellStyle2.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
				cellStyle2.setFillPattern(CellStyle.SOLID_FOREGROUND);
				cellStyle2.setFont(font2);
				cellStyle2.setAlignment(HSSFCellStyle.ALIGN_LEFT);  
				
				HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
				HSSFClientAnchor anchor = new HSSFClientAnchor(450, 0, 750, 0, 
						(short) 0, 0, (short) 1, 1);
				HSSFRow row1 = sheet.createRow(0);
				row1.setHeightInPoints(80);
				row1.setHeight((short) (30 * 40));
				HSSFCell cell = row1.createCell(2);
				sheet.setDefaultColumnWidth(35);
				sheet.setDefaultRowHeightInPoints(30);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(cellStyle);
				cell.setCellValue("交易对账单 " + orderTimeStart);
				
				HSSFRow row0 = sheet.createRow(3);
				HSSFCell cell0 = row0.createCell(0);
				cell0.setCellStyle(cellStyle);
				cell0.setCellValue("");
				
				HSSFCell cell2 = row1.createCell(5);
				cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell2.setCellStyle(cellStyle);
				cell2.setCellValue(phone);
				
				// 插入图片  
				patriarch.createPicture(anchor, wb.addPicture(byteArrayOut  
						.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
				sheet.addMergedRegion(new CellRangeAddress(0,0,0,1));
				sheet.addMergedRegion(new CellRangeAddress(0,0,2,4));
				sheet.addMergedRegion(new CellRangeAddress(0,0,5,6));
				// 对账单头
				Customer customer = customerService.findCustByRemote(auth.getCustomerno());
				params = new HashMap<>();
				params.put("receiver", auth.getCustomerno());
				params.put("orderTimeStart", DateUtils.getMinTime(sdf.parse(orderTimeStart)));
				params.put("orderTimeEnd", DateUtils.getMaxTime(sdf.parse(orderTimeStart)));
				params.put("status", "SUCCESS");
				params.put("sys", "CUSTOMER");
				String info = onlineTradeQueryHessianService.orderFeeSum(params);
				JSONObject jsonObject = JSONObject.fromObject(info);
				HSSFRow row3 = sheet.createRow(1);
				
				HSSFCell cell3 = row3.createCell(0);
				cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell3.setCellStyle(cellStyle2);
				cell3.setCellValue("商户号");
				
				HSSFCell cell4 = row3.createCell(1);
				cell4.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell4.setCellStyle(cellStyle2);
				cell4.setCellValue("商户名");
				
				HSSFCell cell5 = row3.createCell(2);
				cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell5.setCellStyle(cellStyle2);
				cell5.setCellValue("支付笔数");
				
				HSSFCell cell6 = row3.createCell(3);
				cell6.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell6.setCellStyle(cellStyle2);
				cell6.setCellValue("支付总额");
				
				HSSFCell cell7 = row3.createCell(4);
				cell7.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell7.setCellStyle(cellStyle2);
				cell7.setCellValue("手续费总额");
				
				HSSFCell cell8 = row3.createCell(5);
				cell8.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell8.setCellStyle(cellStyle2);
				cell8.setCellValue("应结金额");
				
				HSSFCell cell9 = row3.createCell(6);
				cell9.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell9.setCellStyle(cellStyle2);
				cell9.setCellValue("交易日期");
				
				HSSFRow row4 = sheet.createRow(2);
				
				HSSFCell no = row4.createCell(0);
				no.setCellType(HSSFCell.CELL_TYPE_STRING);
				no.setCellStyle(cellStyle3);
				no.setCellValue(auth.getCustomerno());
				
				HSSFCell name = row4.createCell(1);
				name.setCellType(HSSFCell.CELL_TYPE_STRING);
				name.setCellStyle(cellStyle3);
				name.setCellValue(customer.getFullName());
				
				if (jsonObject.containsKey("al")) {
					HSSFCell al = row4.createCell(2);
					al.setCellType(HSSFCell.CELL_TYPE_STRING);
					al.setCellStyle(cellStyle3);
					al.setCellValue(jsonObject.getInt("al"));
				} else {
					HSSFCell al = row4.createCell(2);
					al.setCellType(HSSFCell.CELL_TYPE_STRING);
					al.setCellStyle(cellStyle3);
					al.setCellValue(0.00);
				}
				if (jsonObject.containsKey("am")) {
					HSSFCell am = row4.createCell(3);
					am.setCellType(HSSFCell.CELL_TYPE_STRING);
					am.setCellStyle(cellStyle3);
					am.setCellValue(jsonObject.getDouble("am"));
				} else {
					HSSFCell am = row4.createCell(3);
					am.setCellType(HSSFCell.CELL_TYPE_STRING);
					am.setCellStyle(cellStyle3);
					am.setCellValue(0.00);
				}
				if (jsonObject.containsKey("pf")) {
					HSSFCell am = row4.createCell(4);
					am.setCellType(HSSFCell.CELL_TYPE_STRING);
					am.setCellStyle(cellStyle3);
					am.setCellValue(jsonObject.getDouble("pf"));
				} else {
					HSSFCell am = row4.createCell(4);
					am.setCellType(HSSFCell.CELL_TYPE_STRING);
					am.setCellStyle(cellStyle3);
					am.setCellValue(0.00);
				}
				if (jsonObject.containsKey("am")) {
					if (jsonObject.containsKey("pf")) {
						HSSFCell am = row4.createCell(5);
						am.setCellType(HSSFCell.CELL_TYPE_STRING);
						am.setCellStyle(cellStyle3);
						am.setCellValue(jsonObject.getDouble("am") - jsonObject.getDouble("pf"));
					} else {
						HSSFCell am = row4.createCell(5);
						am.setCellType(HSSFCell.CELL_TYPE_STRING);
						am.setCellStyle(cellStyle3);
						am.setCellValue(jsonObject.getDouble("am"));
					}
				} else {
					HSSFCell am = row4.createCell(5);
					am.setCellType(HSSFCell.CELL_TYPE_STRING);
					am.setCellStyle(cellStyle3);
					am.setCellValue(0.00);
				}
				HSSFCell t = row4.createCell(6);
				t.setCellType(HSSFCell.CELL_TYPE_STRING);
				t.setCellStyle(cellStyle3);
				t.setCellValue(orderTimeStart);
				
				
				// 订单详细
				order = onlineTradeQueryHessianService.customerRecon(params);
				HSSFRow row2 = sheet.createRow(4);
				
				HSSFCell cell10 = row2.createCell(0);
				cell10.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell10.setCellStyle(cellStyle2);
				cell10.setCellValue("交易方式名称");
				
				HSSFCell cell11 = row2.createCell(1);
				cell11.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell11.setCellStyle(cellStyle2);
				cell11.setCellValue("支付方式");
				
				HSSFCell cell12 = row2.createCell(2);
				cell12.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell12.setCellStyle(cellStyle2);
				cell12.setCellValue("商户订单日期");
				
				HSSFCell cell13 = row2.createCell(3);
				cell13.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell13.setCellStyle(cellStyle2);
				cell13.setCellValue("商户订单号");
				
				HSSFCell cell14 = row2.createCell(4);
				cell14.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell14.setCellStyle(cellStyle2);
				cell14.setCellValue("支付流水号");
				
				HSSFCell cell15 = row2.createCell(5);
				cell15.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell15.setCellStyle(cellStyle2);
				cell15.setCellValue("订单金额");
				
				HSSFCell cell16 = row2.createCell(6);
				cell16.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell16.setCellStyle(cellStyle2);
				cell16.setCellValue("手续费");
				
				HSSFCell cell17 = row2.createCell(7);
				cell17.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell17.setCellStyle(cellStyle2);
				cell17.setCellValue("交易完成时间");
				
				HSSFRow row = null;
				for (int i = 0; i < order.size(); i++) {
					Order o = order.get(i);
					row = sheet.createRow(i + 5);
					
					HSSFCell a1 = row.createCell(0);
					a1.setCellType(HSSFCell.CELL_TYPE_STRING);
					a1.setCellStyle(cellStyle3);
					a1.setCellValue("在线支付");
					
					switch (o.getPayType().name()) {
					case "B2C":
						HSSFCell a2 = row.createCell(1);
						a2.setCellType(HSSFCell.CELL_TYPE_STRING);
						a2.setCellStyle(cellStyle3);
						a2.setCellValue("个人网银");
						break;
					case "B2B":
						HSSFCell a21 = row.createCell(1);
						a21.setCellType(HSSFCell.CELL_TYPE_STRING);
						a21.setCellStyle(cellStyle3);
						a21.setCellValue("企业网银");
						break;
					case "AUTHPAY":
						HSSFCell a211 = row.createCell(1);
						a211.setCellType(HSSFCell.CELL_TYPE_STRING);
						a211.setCellStyle(cellStyle3);
						a211.setCellValue("认证支付");
						break;
					case "REMIT":
						HSSFCell a2111 = row.createCell(1);
						a2111.setCellType(HSSFCell.CELL_TYPE_STRING);
						a2111.setCellStyle(cellStyle3);
						a2111.setCellValue("代付");
						break;
					case "WXJSAPI":
						HSSFCell a21111 = row.createCell(1);
						a21111.setCellType(HSSFCell.CELL_TYPE_STRING);
						a21111.setCellStyle(cellStyle3);
						a21111.setCellValue("微信公众号");
						break;
					case "WXNATIVE":
						HSSFCell a211111 = row.createCell(1);
						a211111.setCellType(HSSFCell.CELL_TYPE_STRING);
						a211111.setCellStyle(cellStyle3);
						a211111.setCellValue("微信扫码");
						break;
					case "ALIPAY":
						HSSFCell a2111111 = row.createCell(1);
						a2111111.setCellType(HSSFCell.CELL_TYPE_STRING);
						a2111111.setCellStyle(cellStyle3);
						a2111111.setCellValue("支付宝");
						break;
					case "UNKNOWN":
						HSSFCell a21111111 = row.createCell(1);
						a21111111.setCellType(HSSFCell.CELL_TYPE_STRING);
						a21111111.setCellStyle(cellStyle3);
						a21111111.setCellValue("未知");
						break;
					case "WXMICROPAY":
						HSSFCell a211111111 = row.createCell(1);
						a211111111.setCellType(HSSFCell.CELL_TYPE_STRING);
						a211111111.setCellStyle(cellStyle3);
						a211111111.setCellValue("微信条码");
						break;
					case "ALIPAYMICROPAY":
						HSSFCell a2111111111 = row.createCell(1);
						a2111111111.setCellType(HSSFCell.CELL_TYPE_STRING);
						a2111111111.setCellStyle(cellStyle3);
						a2111111111.setCellValue("支付宝条码");
						break;
					}
					HSSFCell b1 = row.createCell(2);
					b1.setCellType(HSSFCell.CELL_TYPE_STRING);
					b1.setCellStyle(cellStyle3);
					b1.setCellValue(sdf.format(o.getCreateTime()));
					
					HSSFCell b3 = row.createCell(3);
					b3.setCellType(HSSFCell.CELL_TYPE_STRING);
					b3.setCellStyle(cellStyle3);
					b3.setCellValue(o.getCode());
					
					HSSFCell b4 = row.createCell(4);
					b4.setCellType(HSSFCell.CELL_TYPE_STRING);
					b4.setCellStyle(cellStyle3);
					b4.setCellValue(o.getRequestCode());
					
					HSSFCell b5 = row.createCell(5);
					b5.setCellType(HSSFCell.CELL_TYPE_STRING);
					b5.setCellStyle(cellStyle3);
					b5.setCellValue(o.getAmount());
					
					HSSFCell b6 = row.createCell(6);
					b6.setCellType(HSSFCell.CELL_TYPE_STRING);
					b6.setCellStyle(cellStyle3);
					b6.setCellValue(o.getReceiverFee());
					
					HSSFCell b7 = row.createCell(7);
					b7.setCellType(HSSFCell.CELL_TYPE_STRING);
					b7.setCellStyle(cellStyle3);
					b7.setCellValue(myFmt2.format(o.getSuccessPayTime()));
				}
				sumInfo = auth.getCustomerno() + "_CustomerRecon_ONLINE_"+ orderTimeStart +".xls";
			} else if (sumInfo.equals("DPAY")) {
				HSSFSheet sheet = wb.createSheet("代付信息");
				sheet.setColumnWidth(0, 20 * 256); 
				HSSFFont font = wb.createFont(); 
				
				font.setFontName("微软雅黑"); 
				font.setFontHeightInPoints((short) 15);//设置字体大小
				// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
				HSSFCellStyle cellStyle = wb.createCellStyle();  
				cellStyle.setFont(font);  
				cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直  
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
				
				HSSFFont font2 = wb.createFont(); 
				font2.setFontName("微软雅黑"); 
				font2.setFontHeightInPoints((short)15);//设置字体大小
				//font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
				HSSFCellStyle cellStyle2 = wb.createCellStyle();  
				
				// 设置边框样式
				cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);
				cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				cellStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);
				// 设置边框颜色
				cellStyle2.setTopBorderColor(HSSFColor.BLACK.index);
				cellStyle2.setBottomBorderColor(HSSFColor.BLACK.index);
				cellStyle2.setLeftBorderColor(HSSFColor.BLACK.index);
				cellStyle2.setRightBorderColor(HSSFColor.BLACK.index);
				// 设置背景颜色
				cellStyle2.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
				cellStyle2.setFillPattern(CellStyle.SOLID_FOREGROUND);
				cellStyle2.setFont(font2);
				cellStyle2.setAlignment(HSSFCellStyle.ALIGN_LEFT);  
				
				HSSFFont font3 = wb.createFont(); 
				font3.setFontName("微软雅黑"); 
				font3.setFontHeightInPoints((short)15);//设置字体大小
				//font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
				HSSFCellStyle cellStyle3 = wb.createCellStyle(); 
				cellStyle3.setFont(font3);  
				
				HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
				HSSFClientAnchor anchor = new HSSFClientAnchor(450, 0, 750, 0, 
						(short) 0, 0, (short) 1, 1);
				HSSFRow row1 = sheet.createRow(0);
				row1.setHeightInPoints(80);
				row1.setHeight((short) (30 * 40));
				HSSFCell cell = row1.createCell(2);
				sheet.setDefaultColumnWidth(35);
				sheet.setDefaultRowHeightInPoints(30);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(cellStyle);
				cell.setCellValue("代付对账单 " + orderTimeStart);
				
				HSSFCell cell2 = row1.createCell(5);
				cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell2.setCellStyle(cellStyle);
				cell2.setCellValue(phone);
				
				// 插入图片  
				patriarch.createPicture(anchor, wb.addPicture(byteArrayOut  
						.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
				sheet.addMergedRegion(new CellRangeAddress(0,0,0,1));
				sheet.addMergedRegion(new CellRangeAddress(0,0,2,4));
				sheet.addMergedRegion(new CellRangeAddress(0,0,5,6));
				// 对账单头
				Customer customer = customerService.findCustByRemote(auth.getCustomerno());
				params = new HashMap<>();
				params.put("ownerId", auth.getCustomerno());
				params.put("starTime", DateUtils.getMinTime(sdf.parse(orderTimeStart)));
				params.put("starEnd", DateUtils.getMaxTime(sdf.parse(orderTimeStart)));
				String info = requestFacade.customerReconHead(params);
				JSONObject jsonObject = JSONObject.fromObject(info);
				HSSFRow row3 = sheet.createRow(1);
				
				HSSFCell cell3 = row3.createCell(0);
				cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell3.setCellStyle(cellStyle2);
				cell3.setCellValue("商户号");
				
				HSSFCell cell4 = row3.createCell(1);
				cell4.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell4.setCellStyle(cellStyle2);
				cell4.setCellValue("商户名");
				
				HSSFCell cell5 = row3.createCell(2);
				cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell5.setCellStyle(cellStyle2);
				cell5.setCellValue("支付笔数");
				
				HSSFCell cell6 = row3.createCell(3);
				cell6.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell6.setCellStyle(cellStyle2);
				cell6.setCellValue("支付总额");
				
				HSSFCell cell7 = row3.createCell(4);
				cell7.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell7.setCellStyle(cellStyle2);
				cell7.setCellValue("手续费总额");
				
				HSSFCell cell8 = row3.createCell(5);
				cell8.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell8.setCellStyle(cellStyle2);
				cell8.setCellValue("应结金额");
				
				HSSFCell cell9 = row3.createCell(6);
				cell9.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell9.setCellStyle(cellStyle2);
				cell9.setCellValue("交易日期");
				
				HSSFRow row4 = sheet.createRow(2);
				HSSFCell no = row4.createCell(0);
				no.setCellType(HSSFCell.CELL_TYPE_STRING);
				no.setCellStyle(cellStyle3);
				no.setCellValue(auth.getCustomerno());
				
				HSSFCell name = row4.createCell(1);
				name.setCellType(HSSFCell.CELL_TYPE_STRING);
				name.setCellStyle(cellStyle3);
				name.setCellValue(customer.getFullName());
				
				if (jsonObject.containsKey("count")) {
					HSSFCell count = row4.createCell(2);
					count.setCellType(HSSFCell.CELL_TYPE_STRING);
					count.setCellStyle(cellStyle3);
					count.setCellValue(jsonObject.getInt("count"));
				} else {
					HSSFCell count = row4.createCell(2);
					count.setCellType(HSSFCell.CELL_TYPE_STRING);
					count.setCellStyle(cellStyle3);
					count.setCellValue(0.00);
				}
				if (jsonObject.containsKey("sum")) {
					HSSFCell count = row4.createCell(3);
					count.setCellType(HSSFCell.CELL_TYPE_STRING);
					count.setCellStyle(cellStyle3);
					count.setCellValue(jsonObject.getDouble("sum"));
				} else {
					HSSFCell count = row4.createCell(3);
					count.setCellType(HSSFCell.CELL_TYPE_STRING);
					count.setCellStyle(cellStyle3);
					count.setCellValue(0.00);
				}
				if (jsonObject.containsKey("fee")) {
					HSSFCell count = row4.createCell(4);
					count.setCellType(HSSFCell.CELL_TYPE_STRING);
					count.setCellStyle(cellStyle3);
					count.setCellValue(jsonObject.getDouble("fee"));
				} else {
					HSSFCell count = row4.createCell(4);
					count.setCellType(HSSFCell.CELL_TYPE_STRING);
					count.setCellStyle(cellStyle3);
					count.setCellValue(0.00);
				}
				if (jsonObject.containsKey("sum")) {
					if (jsonObject.containsKey("fee")) {
						HSSFCell count = row4.createCell(5);
						count.setCellType(HSSFCell.CELL_TYPE_STRING);
						count.setCellStyle(cellStyle3);
						count.setCellValue(jsonObject.getDouble("sum") - jsonObject.getDouble("fee"));
					} else {
						HSSFCell count = row4.createCell(5);
						count.setCellType(HSSFCell.CELL_TYPE_STRING);
						count.setCellStyle(cellStyle3);
						count.setCellValue(jsonObject.getDouble("sum"));
					}
				} else {
					HSSFCell count = row4.createCell(5);
					count.setCellType(HSSFCell.CELL_TYPE_STRING);
					count.setCellStyle(cellStyle3);
					count.setCellValue(0.00);
				}
				HSSFCell count = row4.createCell(6);
				count.setCellType(HSSFCell.CELL_TYPE_STRING);
				count.setCellStyle(cellStyle3);
				count.setCellValue(orderTimeStart);
				
				HSSFRow row5 = sheet.createRow(3);
				HSSFCell t = row5.createCell(6);
				t.setCellType(HSSFCell.CELL_TYPE_STRING);
				t.setCellStyle(cellStyle3);
				t.setCellValue("");
				
				// 订单详细
				requestBeans = requestFacade.customerRecon(params);
				HSSFRow row2 = sheet.createRow(4);
				
				HSSFCell cell10 = row2.createCell(0);
				cell10.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell10.setCellStyle(cellStyle2);
				cell10.setCellValue("交易方式名称");
				
				HSSFCell cell11 = row2.createCell(1);
				cell11.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell11.setCellStyle(cellStyle2);
				cell11.setCellValue("支付方式");
				
				HSSFCell cell12 = row2.createCell(2);
				cell12.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell12.setCellStyle(cellStyle2);
				cell12.setCellValue("商户订单日期");
				
				HSSFCell cell13 = row2.createCell(3);
				cell13.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell13.setCellStyle(cellStyle2);
				cell13.setCellValue("商户订单号");
				
				HSSFCell cell14 = row2.createCell(4);
				cell14.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell14.setCellStyle(cellStyle2);
				cell14.setCellValue("支付流水号");
				
				HSSFCell cell15 = row2.createCell(5);
				cell15.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell15.setCellStyle(cellStyle2);
				cell15.setCellValue("订单金额");
				
				HSSFCell cell16 = row2.createCell(6);
				cell16.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell16.setCellStyle(cellStyle2);
				cell16.setCellValue("手续费");
				
				HSSFCell cell17 = row2.createCell(7);
				cell17.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell17.setCellStyle(cellStyle2);
				cell17.setCellValue("交易完成时间");
				
				HSSFRow row = null;
				for (int i = 0; i < requestBeans.size(); i++) {
					RequestBean requestBean = requestBeans.get(i);
					row = sheet.createRow(i + 5);
					
					HSSFCell a = row.createCell(0);
					a.setCellType(HSSFCell.CELL_TYPE_STRING);
					a.setCellStyle(cellStyle3);
					a.setCellValue("代付");
					
					HSSFCell a1 = row.createCell(1);
					a1.setCellType(HSSFCell.CELL_TYPE_STRING);
					a1.setCellStyle(cellStyle3);
					a1.setCellValue("代付");
					
					HSSFCell a2 = row.createCell(2);
					a2.setCellType(HSSFCell.CELL_TYPE_STRING);
					a2.setCellStyle(cellStyle3);
					a2.setCellValue(sdf.format(requestBean.getCreateDate()));
					
					HSSFCell a3 = row.createCell(3);
					a3.setCellType(HSSFCell.CELL_TYPE_STRING);
					a3.setCellStyle(cellStyle3);
					a3.setCellValue(requestBean.getRequestNo());
					
					HSSFCell a4 = row.createCell(4);
					a4.setCellType(HSSFCell.CELL_TYPE_STRING);
					a4.setCellStyle(cellStyle3);
					a4.setCellValue(requestBean.getFlowNo());
					
					HSSFCell a5 = row.createCell(5);
					a5.setCellType(HSSFCell.CELL_TYPE_STRING);
					a5.setCellStyle(cellStyle3);
					a5.setCellValue(requestBean.getAmount());
					
					HSSFCell a6 = row.createCell(6);
					a6.setCellType(HSSFCell.CELL_TYPE_STRING);
					a6.setCellStyle(cellStyle3);
					a6.setCellValue(requestBean.getFee());
					
					HSSFCell a7 = row.createCell(7);
					a7.setCellType(HSSFCell.CELL_TYPE_STRING);
					a7.setCellStyle(cellStyle3);
					a7.setCellValue(sdf.format(requestBean.getCompleteDate()));
				}
				sumInfo = auth.getCustomerno() + "_CustomerRecon_DPAY_"+ orderTimeStart +".xls";
			} else if (sumInfo.equals("RECEIVE")) {
				HSSFSheet sheet = wb.createSheet("代收信息");
				sheet.setColumnWidth(0, 20 * 256); 
				HSSFFont font = wb.createFont(); 
				
				font.setFontName("微软雅黑"); 
				font.setFontHeightInPoints((short) 15);//设置字体大小
				//font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
				HSSFCellStyle cellStyle = wb.createCellStyle();  
				cellStyle.setFont(font);  
				cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直  
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
				
				HSSFFont font2 = wb.createFont(); 
				font2.setFontName("微软雅黑"); 
				font2.setFontHeightInPoints((short)15);//设置字体大小
				//font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
				HSSFCellStyle cellStyle2 = wb.createCellStyle();  
				
				HSSFFont font3 = wb.createFont(); 
				font3.setFontName("微软雅黑"); 
				font3.setFontHeightInPoints((short)15);//设置字体大小
				//font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
				HSSFCellStyle cellStyle3 = wb.createCellStyle(); 
				cellStyle3.setFont(font3);
				
				// 设置边框样式
				cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);
				cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				cellStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);
				// 设置边框颜色
				cellStyle2.setTopBorderColor(HSSFColor.BLACK.index);
				cellStyle2.setBottomBorderColor(HSSFColor.BLACK.index);
				cellStyle2.setLeftBorderColor(HSSFColor.BLACK.index);
				cellStyle2.setRightBorderColor(HSSFColor.BLACK.index);
				// 设置背景颜色
				cellStyle2.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
				cellStyle2.setFillPattern(CellStyle.SOLID_FOREGROUND);
				cellStyle2.setFont(font2);
				cellStyle2.setAlignment(HSSFCellStyle.ALIGN_LEFT);  
				
				HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
				HSSFClientAnchor anchor = new HSSFClientAnchor(450, 0, 750, 0, 
						(short) 0, 0, (short) 1, 1);
				HSSFRow row1 = sheet.createRow(0);
				row1.setHeightInPoints(44);
				row1.setHeight((short) (30 * 40));
				HSSFCell cell = row1.createCell(2);
				sheet.setDefaultColumnWidth(35);
				sheet.setDefaultRowHeightInPoints(30);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(cellStyle);
				cell.setCellValue("代收对账单 " + orderTimeStart);
				
				HSSFCell cell2 = row1.createCell(5);
				cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell2.setCellStyle(cellStyle);
				cell2.setCellValue(phone);
				
				// 插入图片  
				patriarch.createPicture(anchor, wb.addPicture(byteArrayOut  
						.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
				sheet.addMergedRegion(new CellRangeAddress(0,0,0,1));
				sheet.addMergedRegion(new CellRangeAddress(0,0,2,4));
				sheet.addMergedRegion(new CellRangeAddress(0,0,5,6));
				// 对账单头
				Customer customer = customerService.findCustByRemote(auth.getCustomerno());
				params = new HashMap<>();
				params.put("ownerId", auth.getCustomerno());
				params.put("starTime", DateUtils.getMinTime(sdf.parse(orderTimeStart)));
				params.put("starEnd", DateUtils.getMaxTime(sdf.parse(orderTimeStart)));
				String info = receiveQueryFacade.customerReconHead(params);
				JSONObject jsonObject = JSONObject.fromObject(info);
				HSSFRow row3 = sheet.createRow(1);
				
				HSSFCell cell3 = row3.createCell(0);
				cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell3.setCellStyle(cellStyle2);
				cell3.setCellValue("商户号");
				
				HSSFCell cell4 = row3.createCell(1);
				cell4.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell4.setCellStyle(cellStyle2);
				cell4.setCellValue("商户名");
				
				HSSFCell cell5 = row3.createCell(2);
				cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell5.setCellStyle(cellStyle2);
				cell5.setCellValue("支付笔数");
				
				HSSFCell cell6 = row3.createCell(3);
				cell6.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell6.setCellStyle(cellStyle2);
				cell6.setCellValue("支付总额");
				
				HSSFCell cell7 = row3.createCell(4);
				cell7.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell7.setCellStyle(cellStyle2);
				cell7.setCellValue("手续费总额");
				
				HSSFCell cell8 = row3.createCell(5);
				cell8.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell8.setCellStyle(cellStyle2);
				cell8.setCellValue("应结金额");
				
				HSSFCell cell9 = row3.createCell(6);
				cell9.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell9.setCellStyle(cellStyle2);
				cell9.setCellValue("交易日期");
				
				HSSFRow row4 = sheet.createRow(2);
				
				HSSFCell no = row4.createCell(0);
				no.setCellType(HSSFCell.CELL_TYPE_STRING);
				no.setCellStyle(cellStyle3);
				no.setCellValue(auth.getCustomerno());
				
				HSSFCell name = row4.createCell(1);
				name.setCellType(HSSFCell.CELL_TYPE_STRING);
				name.setCellStyle(cellStyle3);
				name.setCellValue(customer.getFullName());
				
				if (jsonObject.containsKey("count")) {
					HSSFCell c2 = row4.createCell(2);
					c2.setCellType(HSSFCell.CELL_TYPE_STRING);
					c2.setCellStyle(cellStyle3);
					c2.setCellValue(jsonObject.getInt("count"));
				} else {
					HSSFCell c2 = row4.createCell(2);
					c2.setCellType(HSSFCell.CELL_TYPE_STRING);
					c2.setCellStyle(cellStyle3);
					c2.setCellValue(0.00);
				}
				if (jsonObject.containsKey("sum")) {
					HSSFCell c2 = row4.createCell(3);
					c2.setCellType(HSSFCell.CELL_TYPE_STRING);
					c2.setCellStyle(cellStyle3);
					c2.setCellValue(jsonObject.getDouble("sum"));
				} else {
					HSSFCell c2 = row4.createCell(3);
					c2.setCellType(HSSFCell.CELL_TYPE_STRING);
					c2.setCellStyle(cellStyle3);
					c2.setCellValue(0.00);
				}
				if (jsonObject.containsKey("fee")) {
					HSSFCell c2 = row4.createCell(4);
					c2.setCellType(HSSFCell.CELL_TYPE_STRING);
					c2.setCellStyle(cellStyle3);
					c2.setCellValue(jsonObject.getDouble("fee"));
				} else {
					HSSFCell c2 = row4.createCell(4);
					c2.setCellType(HSSFCell.CELL_TYPE_STRING);
					c2.setCellStyle(cellStyle3);
					c2.setCellValue(0.00);
				}
				if (jsonObject.containsKey("sum")) {
					if (jsonObject.containsKey("fee")) {
						HSSFCell c2 = row4.createCell(5);
						c2.setCellType(HSSFCell.CELL_TYPE_STRING);
						c2.setCellStyle(cellStyle3);
						c2.setCellValue(jsonObject.getDouble("sum") - jsonObject.getDouble("fee"));
					} else {
						HSSFCell c2 = row4.createCell(5);
						c2.setCellType(HSSFCell.CELL_TYPE_STRING);
						c2.setCellStyle(cellStyle3);
						c2.setCellValue(jsonObject.getDouble("sum"));
					}
				} else {
					HSSFCell c2 = row4.createCell(5);
					c2.setCellType(HSSFCell.CELL_TYPE_STRING);
					c2.setCellStyle(cellStyle3);
					c2.setCellValue(0.00);
				}
				HSSFCell c6 = row4.createCell(6);
				c6.setCellType(HSSFCell.CELL_TYPE_STRING);
				c6.setCellStyle(cellStyle3);
				c6.setCellValue(orderTimeStart);
				HSSFRow row5 = sheet.createRow(3);
				HSSFCell c7 = row5.createCell(0);
				c7.setCellType(HSSFCell.CELL_TYPE_STRING);
				c7.setCellStyle(cellStyle3);
				c7.setCellValue("");
				
				// 订单详细
				receiveRequestBeans = receiveQueryFacade.customerRecon(params);
				HSSFRow row2 = sheet.createRow(4);
				
				HSSFCell cell10 = row2.createCell(0);
				cell10.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell10.setCellStyle(cellStyle2);
				cell10.setCellValue("交易方式名称");
				
				HSSFCell cell11 = row2.createCell(1);
				cell11.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell11.setCellStyle(cellStyle2);
				cell11.setCellValue("支付方式");
				
				HSSFCell cell12 = row2.createCell(2);
				cell12.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell12.setCellStyle(cellStyle2);
				cell12.setCellValue("商户订单日期");
				
				HSSFCell cell13 = row2.createCell(3);
				cell13.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell13.setCellStyle(cellStyle2);
				cell13.setCellValue("商户订单号");
				
				HSSFCell cell14 = row2.createCell(4);
				cell14.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell14.setCellStyle(cellStyle2);
				cell14.setCellValue("支付流水号");
				
				HSSFCell cell15 = row2.createCell(5);
				cell15.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell15.setCellStyle(cellStyle2);
				cell15.setCellValue("订单金额");
				
				HSSFCell cell16 = row2.createCell(6);
				cell16.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell16.setCellStyle(cellStyle2);
				cell16.setCellValue("手续费");
				
				HSSFCell cell17 = row2.createCell(7);
				cell17.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell17.setCellStyle(cellStyle2);
				cell17.setCellValue("交易完成时间");
				
				HSSFRow row = null;
				for (int i = 0; i < receiveRequestBeans.size(); i++) {
					ReceiveRequestBean receiveRequestBean = receiveRequestBeans.get(i);
					row = sheet.createRow(i + 5);
					
					HSSFCell a = row.createCell(0);
					a.setCellType(HSSFCell.CELL_TYPE_STRING);
					a.setCellStyle(cellStyle3);
					a.setCellValue("代收");
					
					HSSFCell a1 = row.createCell(1);
					a1.setCellType(HSSFCell.CELL_TYPE_STRING);
					a1.setCellStyle(cellStyle3);
					a1.setCellValue("代收");
					
					HSSFCell a2 = row.createCell(2);
					a2.setCellType(HSSFCell.CELL_TYPE_STRING);
					a2.setCellStyle(cellStyle3);
					a2.setCellValue(sdf.format(receiveRequestBean.getCreateTime()));
					
					HSSFCell a3 = row.createCell(3);
					a3.setCellType(HSSFCell.CELL_TYPE_STRING);
					a3.setCellStyle(cellStyle3);
					a3.setCellValue(receiveRequestBean.getCustomerOrderCode());
					
					HSSFCell a4 = row.createCell(4);
					a4.setCellType(HSSFCell.CELL_TYPE_STRING);
					a4.setCellStyle(cellStyle3);
					a4.setCellValue(receiveRequestBean.getReceiveId());
					
					HSSFCell a5 = row.createCell(5);
					a5.setCellType(HSSFCell.CELL_TYPE_STRING);
					a5.setCellStyle(cellStyle3);
					a5.setCellValue(receiveRequestBean.getAmount());
					
					HSSFCell a6 = row.createCell(6);
					a6.setCellType(HSSFCell.CELL_TYPE_STRING);
					a6.setCellStyle(cellStyle3);
					a6.setCellValue(receiveRequestBean.getFee());
					
					HSSFCell a7 = row.createCell(7);
					a7.setCellType(HSSFCell.CELL_TYPE_STRING);
					a7.setCellStyle(cellStyle3);
					a7.setCellValue(sdf.format(receiveRequestBean.getLastUpdateTime()));
				}
				sumInfo = auth.getCustomerno() + "_CustomerRecon_RECEIVE_"+ orderTimeStart +".xls";
			} else if (sumInfo.equals("ALL")) {
				flag = true;
				HSSFSheet sheet = wb.createSheet("汇总信息");
				sheet.setColumnWidth(0, 20 * 256); 
				HSSFFont font = wb.createFont(); 
				
				font.setFontName("微软雅黑"); 
				font.setFontHeightInPoints((short) 15);//设置字体大小
				//font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
				HSSFCellStyle cellStyle = wb.createCellStyle();  
				cellStyle.setFont(font);  
				cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直  
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
				
				HSSFFont font2 = wb.createFont(); 
				font2.setFontName("微软雅黑"); 
				font2.setFontHeightInPoints((short)15);//设置字体大小
				//font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
				HSSFCellStyle cellStyle2 = wb.createCellStyle();  
				
				HSSFFont font3 = wb.createFont(); 
				font3.setFontName("微软雅黑"); 
				font3.setFontHeightInPoints((short)15);//设置字体大小
				//font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
				HSSFCellStyle cellStyle3 = wb.createCellStyle(); 
				cellStyle3.setFont(font3);  
				
				// 设置边框样式
				cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);
				cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				cellStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);
				// 设置边框颜色
				cellStyle2.setTopBorderColor(HSSFColor.BLACK.index);
				cellStyle2.setBottomBorderColor(HSSFColor.BLACK.index);
				cellStyle2.setLeftBorderColor(HSSFColor.BLACK.index);
				cellStyle2.setRightBorderColor(HSSFColor.BLACK.index);
				// 设置背景颜色
				cellStyle2.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
				cellStyle2.setFillPattern(CellStyle.SOLID_FOREGROUND);
				cellStyle2.setFont(font2);
				cellStyle2.setAlignment(HSSFCellStyle.ALIGN_LEFT);  
				
				HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
				HSSFClientAnchor anchor = new HSSFClientAnchor(450, 0, 750, 0, 
						(short) 0, 0, (short) 1, 1);
				HSSFRow row1 = sheet.createRow(0);
				row1.setHeightInPoints(80);
				row1.setHeight((short) (30 * 40));
				HSSFCell cell = row1.createCell(2);
				sheet.setDefaultColumnWidth(35);
				sheet.setDefaultRowHeightInPoints(30);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(cellStyle);
				cell.setCellValue("对账单 " + orderTimeStart);
				
				HSSFCell cell2 = row1.createCell(5);
				cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell2.setCellStyle(cellStyle);
				cell2.setCellValue(phone);
				
				// 插入图片  
				patriarch.createPicture(anchor, wb.addPicture(byteArrayOut  
						.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
				sheet.addMergedRegion(new CellRangeAddress(0,0,0,1));
				sheet.addMergedRegion(new CellRangeAddress(0,0,2,4));
				sheet.addMergedRegion(new CellRangeAddress(0,0,5,6));
				sheet.addMergedRegion(new CellRangeAddress(1,1,0,6));
				sheet.addMergedRegion(new CellRangeAddress(4,4,0,6));
				// 对账单头
				Customer customer = customerService.findCustByRemote(auth.getCustomerno());
				params = new HashMap<>();
				params.put("receiver", auth.getCustomerno());
				params.put("orderTimeStart", DateUtils.getMinTime(sdf.parse(orderTimeStart)));
				params.put("orderTimeEnd", DateUtils.getMaxTime(sdf.parse(orderTimeStart)));
				params.put("status", "SUCCESS");
				params.put("sys", "CUSTOMER");
				params.put("ownerId", auth.getCustomerno());
				params.put("starTime", DateUtils.getMinTime(sdf.parse(orderTimeStart)));
				params.put("starEnd", DateUtils.getMaxTime(sdf.parse(orderTimeStart)));
				 
				
				// 宋体  12 加粗   40%灰
				HSSFFont font4 = wb.createFont(); 
				font4.setFontName("微软雅黑"); 
				font4.setFontHeightInPoints((short)15);//设置字体大小
				HSSFCellStyle cellStyle4 = wb.createCellStyle();  
				cellStyle4.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
				cellStyle4.setFont(font4);
				cellStyle4.setFillPattern(CellStyle.SOLID_FOREGROUND);
				cellStyle4.setAlignment(HSSFCellStyle.ALIGN_LEFT);  
				
				// Calibri  11  加粗  
				HSSFFont font6 = wb.createFont(); 
				font6.setFontName("Calibri"); 
				font6.setFontHeightInPoints((short)11);//设置字体大小
				font6.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
				HSSFCellStyle cellStyle6 = wb.createCellStyle();  
				cellStyle6.setFont(font6);
				
				// 宋体  11 
				HSSFFont font5 = wb.createFont(); 
				font5.setFontName("宋体"); 
				font5.setFontHeightInPoints((short)11);//设置字体大小
				HSSFCellStyle cellStyle5 = wb.createCellStyle();  
				cellStyle5.setFont(font5);
				
				// 垂直居中 红色
				HSSFFont font9 = wb.createFont(); 
				font9.setFontName("微软雅黑");
				font9.setColor(RED.index);
				font9.setFontHeightInPoints((short)15);//设置字体大小
				HSSFCellStyle cellStyle9 = wb.createCellStyle();  
				cellStyle9.setFont(font9);
				cellStyle9.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直  
				cellStyle9.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
				
				HSSFRow row99 = sheet.createRow(1);
				HSSFCell cell99 = row99.createCell(0);
				cell99.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell99.setCellStyle(cellStyle4);
				cell99.setCellValue("商户基本信息");
				
				HSSFRow row3 = sheet.createRow(2);
				HSSFCell cell3 = row3.createCell(0);
				cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell3.setCellStyle(cellStyle3);
				cell3.setCellValue("商户全称");
				
				HSSFCell cell4 = row3.createCell(1);
				cell4.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell4.setCellStyle(cellStyle3);
				cell4.setCellValue(customer.getFullName());
				
				HSSFRow row31 = sheet.createRow(3);
				HSSFCell cell41 = row31.createCell(0);
				cell41.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell41.setCellStyle(cellStyle3);
				cell41.setCellValue("");
				
				HSSFRow row4 = sheet.createRow(4);
				HSSFCell cell04 = row4.createCell(0);
				cell04.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell04.setCellStyle(cellStyle4);
				cell04.setCellValue("交易汇总数据");
				
				HSSFRow row5 = sheet.createRow(5);
				HSSFCell cell5 = row5.createCell(0);
				cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell5.setCellStyle(cellStyle3);
				cell5.setCellValue("交易类型");
				
				HSSFCell cell6 = row5.createCell(1);
				cell6.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell6.setCellStyle(cellStyle3);
				cell6.setCellValue("订单金额(元)");
				
				HSSFCell cell7 = row5.createCell(2);
				cell7.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell7.setCellStyle(cellStyle3);
				cell7.setCellValue("订单数(笔)");
				
				HSSFCell cell8 = row5.createCell(3);
				cell8.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell8.setCellStyle(cellStyle3);
				cell8.setCellValue("订单退款(元)");
				
				HSSFCell cell9 = row5.createCell(4);
				cell9.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell9.setCellStyle(cellStyle3);
				cell9.setCellValue("退款数(笔)");
				
				HSSFCell cell10 = row5.createCell(5);
				cell10.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell10.setCellStyle(cellStyle3);
				cell10.setCellValue("手续费(元)");
				
				HSSFCell cell11 = row5.createCell(6);
				cell11.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell11.setCellStyle(cellStyle3);
				cell11.setCellValue("应结金额(元)");
				
				// 交易总数据
				String info = onlineTradeQueryHessianService.orderFeeSum(params);
				JSONObject jsonObject = JSONObject.fromObject(info);
				HSSFRow row05 = sheet.createRow(6);
				HSSFCell a = row05.createCell(0);
				a.setCellType(HSSFCell.CELL_TYPE_STRING);
				a.setCellStyle(cellStyle3);
				a.setCellValue("交易");
				if (jsonObject.containsKey("am")) {
					HSSFCell a1 = row05.createCell(1);
					a1.setCellType(HSSFCell.CELL_TYPE_STRING);
					a1.setCellStyle(cellStyle3);
					a1.setCellValue(jsonObject.getDouble("am"));
				} else {
					HSSFCell a1 = row05.createCell(1);
					a1.setCellType(HSSFCell.CELL_TYPE_STRING);
					a1.setCellStyle(cellStyle3);
					a1.setCellValue(0.00);
				}
				if (jsonObject.containsKey("al")) {
					HSSFCell a1 = row05.createCell(2);
					a1.setCellType(HSSFCell.CELL_TYPE_STRING);
					a1.setCellStyle(cellStyle3);
					a1.setCellValue(jsonObject.getDouble("al"));
				} else {
					HSSFCell a1 = row05.createCell(2);
					a1.setCellType(HSSFCell.CELL_TYPE_STRING);
					a1.setCellStyle(cellStyle3);
					a1.setCellValue(0.00);
				}
				HSSFCell a3 = row05.createCell(3);
				a3.setCellType(HSSFCell.CELL_TYPE_STRING);
				a3.setCellStyle(cellStyle3);
				a3.setCellValue(0.00);
				HSSFCell a4 = row05.createCell(4);
				a4.setCellType(HSSFCell.CELL_TYPE_STRING);
				a4.setCellStyle(cellStyle3);
				a4.setCellValue(0.00);
				if (jsonObject.containsKey("pf")) {
					HSSFCell a5 = row05.createCell(5);
					a5.setCellType(HSSFCell.CELL_TYPE_STRING);
					a5.setCellStyle(cellStyle3);
					a5.setCellValue(jsonObject.getDouble("pf"));
				} else {
					HSSFCell a5 = row05.createCell(5);
					a5.setCellType(HSSFCell.CELL_TYPE_STRING);
					a5.setCellStyle(cellStyle3);
					a5.setCellValue(0.00);
				}
				if (jsonObject.containsKey("am")) {
					if (jsonObject.containsKey("pf")) {
						HSSFCell a5 = row05.createCell(6);
						a5.setCellType(HSSFCell.CELL_TYPE_STRING);
						a5.setCellStyle(cellStyle3);
						a5.setCellValue(jsonObject.getDouble("am") - jsonObject.getDouble("pf"));
					} else {
						HSSFCell a5 = row05.createCell(6);
						a5.setCellType(HSSFCell.CELL_TYPE_STRING);
						a5.setCellStyle(cellStyle3);
						a5.setCellValue(jsonObject.getDouble("am"));
					}
				} else {
					HSSFCell a5 = row05.createCell(6);
					a5.setCellType(HSSFCell.CELL_TYPE_STRING);
					a5.setCellStyle(cellStyle3);
					a5.setCellValue(0.00);
				}
				
				// 代付总数据
				JSONObject json = JSONObject.fromObject(requestFacade.customerReconHead(params));
				HSSFRow row6 = sheet.createRow(7);
				
				HSSFCell a31 = row6.createCell(0);
				a31.setCellType(HSSFCell.CELL_TYPE_STRING);
				a31.setCellStyle(cellStyle3);
				a31.setCellValue("代付");
				
				if (json.containsKey("sum")) {
					HSSFCell a33 = row6.createCell(1);
					a33.setCellType(HSSFCell.CELL_TYPE_STRING);
					a33.setCellStyle(cellStyle3);
					a33.setCellValue(json.getDouble("sum"));
				} else {
					HSSFCell a33 = row6.createCell(1);
					a33.setCellType(HSSFCell.CELL_TYPE_STRING);
					a33.setCellStyle(cellStyle3);
					a33.setCellValue(0.00);
				}
				if (json.containsKey("count")) {
					HSSFCell a33 = row6.createCell(2);
					a33.setCellType(HSSFCell.CELL_TYPE_STRING);
					a33.setCellStyle(cellStyle3);
					a33.setCellValue(json.getInt("count"));
				} else {
					HSSFCell a33 = row6.createCell(2);
					a33.setCellType(HSSFCell.CELL_TYPE_STRING);
					a33.setCellStyle(cellStyle3);
					a33.setCellValue(0.00);
				}
				
				HSSFCell a33 = row6.createCell(3);
				a33.setCellType(HSSFCell.CELL_TYPE_STRING);
				a33.setCellStyle(cellStyle3);
				a33.setCellValue(0.00);
				
				HSSFCell a44 = row6.createCell(4);
				a44.setCellType(HSSFCell.CELL_TYPE_STRING);
				a44.setCellStyle(cellStyle3);
				a44.setCellValue(0.00);
				
				if (json.containsKey("fee")) {
					HSSFCell a5 = row6.createCell(5);
					a5.setCellType(HSSFCell.CELL_TYPE_STRING);
					a5.setCellStyle(cellStyle3);
					a5.setCellValue(json.getDouble("fee"));
				} else {
					HSSFCell a5 = row6.createCell(5);
					a5.setCellType(HSSFCell.CELL_TYPE_STRING);
					a5.setCellStyle(cellStyle3);
					a5.setCellValue(0.00);
				}
				if (json.containsKey("sum")) {
					if (json.containsKey("fee")) {
						HSSFCell a5 = row6.createCell(6);
						a5.setCellType(HSSFCell.CELL_TYPE_STRING);
						a5.setCellStyle(cellStyle3);
						a5.setCellValue(json.getDouble("sum") - json.getDouble("fee"));
					} else {
						HSSFCell a5 = row6.createCell(6);
						a5.setCellType(HSSFCell.CELL_TYPE_STRING);
						a5.setCellStyle(cellStyle3);
						a5.setCellValue(json.getDouble("sum"));
					}
				} else {
					HSSFCell a5 = row6.createCell(6);
					a5.setCellType(HSSFCell.CELL_TYPE_STRING);
					a5.setCellStyle(cellStyle3);
					a5.setCellValue(0.00);
				}
				
				// 代收总数据
				JSONObject Object = JSONObject.fromObject(receiveQueryFacade.customerReconHead(params));
				HSSFRow row7 = sheet.createRow(8);
				
				HSSFRow row8 = sheet.createRow(9);
				HSSFCell c8 = row8.createCell(0);
				c8.setCellType(HSSFCell.CELL_TYPE_STRING);
				c8.setCellStyle(cellStyle3);
				c8.setCellValue("");
				
				HSSFCell c1 = row7.createCell(0);
				c1.setCellType(HSSFCell.CELL_TYPE_STRING);
				c1.setCellStyle(cellStyle3);
				c1.setCellValue("代收");
				
				if (Object.containsKey("sum")) {
					HSSFCell c2 = row7.createCell(1);
					c2.setCellType(HSSFCell.CELL_TYPE_STRING);
					c2.setCellStyle(cellStyle3);
					c2.setCellValue(Object.getDouble("sum"));
				} else {
					HSSFCell c2 = row7.createCell(1);
					c2.setCellType(HSSFCell.CELL_TYPE_STRING);
					c2.setCellStyle(cellStyle3);
					c2.setCellValue(0.00);
				}
				if (Object.containsKey("count")) {
					HSSFCell c2 = row7.createCell(2);
					c2.setCellType(HSSFCell.CELL_TYPE_STRING);
					c2.setCellStyle(cellStyle3);
					c2.setCellValue(Object.getInt("count"));
				} else {
					HSSFCell c2 = row7.createCell(2);
					c2.setCellType(HSSFCell.CELL_TYPE_STRING);
					c2.setCellStyle(cellStyle3);
					c2.setCellValue(0.00);
				}
				
				HSSFCell c3 = row7.createCell(3);
				c3.setCellType(HSSFCell.CELL_TYPE_STRING);
				c3.setCellStyle(cellStyle3);
				c3.setCellValue(0.00);
				
				HSSFCell c4 = row7.createCell(4);
				c4.setCellType(HSSFCell.CELL_TYPE_STRING);
				c4.setCellStyle(cellStyle3);
				c4.setCellValue(0.00);
				
				if (Object.containsKey("fee")) {
					HSSFCell c5 = row7.createCell(5);
					c5.setCellType(HSSFCell.CELL_TYPE_STRING);
					c5.setCellStyle(cellStyle3);
					c5.setCellValue(Object.getDouble("fee"));
				} else {
					HSSFCell c5 = row7.createCell(5);
					c5.setCellType(HSSFCell.CELL_TYPE_STRING);
					c5.setCellStyle(cellStyle3);
					c5.setCellValue(0.00);
				}
				if (Object.containsKey("sum")) {
					if (Object.containsKey("fee")) {
						HSSFCell c5 = row7.createCell(6);
						c5.setCellType(HSSFCell.CELL_TYPE_STRING);
						c5.setCellStyle(cellStyle3);
						c5.setCellValue(Object.getDouble("sum") - Object.getDouble("fee"));
					} else {
						HSSFCell c5 = row7.createCell(6);
						c5.setCellType(HSSFCell.CELL_TYPE_STRING);
						c5.setCellStyle(cellStyle3);
						c5.setCellValue(Object.getDouble("sum"));
					}
				} else {
					HSSFCell c5 = row7.createCell(6);
					c5.setCellType(HSSFCell.CELL_TYPE_STRING);
					c5.setCellStyle(cellStyle3);
					c5.setCellValue(0.00);
				}
				
				// 总计
				HSSFRow row9 = sheet.createRow(10);
				
				HSSFCell cell11121 = row9.createCell(0);
				cell11121.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell11121.setCellStyle(cellStyle9);
				cell11121.setCellValue("总计");
				
				Double am = 0.0;
				int count = 0;
				Double fee = 0.0;
				Double cost = 0.0;
				if (jsonObject.containsKey("am")) {
					am = am + jsonObject.getDouble("am");
				}
				if (Object.containsKey("sum")) {
					am = am + Object.getDouble("sum");
				}
				if (json.containsKey("sum")) {
					am = am + json.getDouble("sum");
				}
				
				HSSFCell cell111211 = row9.createCell(1);
				cell111211.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell111211.setCellStyle(cellStyle9);
				cell111211.setCellValue(am);
				
				if (jsonObject.containsKey("al")) {
					count = count + jsonObject.getInt("al");
				}
				if (Object.containsKey("count")) {
					count = count + Object.getInt("count");
				}
				if (json.containsKey("count")) {
					count = count + json.getInt("count");
				}
				
				HSSFCell cell1112112 = row9.createCell(2);
				cell1112112.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell1112112.setCellStyle(cellStyle9);
				cell1112112.setCellValue(count);
				
				HSSFCell cell11121121 = row9.createCell(3);
				cell11121121.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell11121121.setCellStyle(cellStyle9);
				cell11121121.setCellValue(0.00);
				
				HSSFCell cell111211211 = row9.createCell(4);
				cell111211211.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell111211211.setCellStyle(cellStyle9);
				cell111211211.setCellValue(0.00);
				
				if (jsonObject.containsKey("pf")) {
					fee = fee + jsonObject.getDouble("pf");
				}
				if (Object.containsKey("fee")) {
					fee = fee + Object.getDouble("fee");
				}
				if (json.containsKey("fee")) {
					fee = fee + json.getDouble("fee");
				}
				HSSFCell cell1112112111 = row9.createCell(5);
				cell1112112111.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell1112112111.setCellStyle(cellStyle9);
				cell1112112111.setCellValue(fee);
				
				if (jsonObject.containsKey("am")) {
					if (jsonObject.containsKey("pf")) {
						cost = cost + jsonObject.getDouble("am") - jsonObject.getDouble("pf");
					} else {
						cost = cost + jsonObject.getDouble("am");
					}
				}
				
				if (Object.containsKey("sum")) {
					if (Object.containsKey("fee")) {
						cost = cost + Object.getDouble("sum") - Object.getDouble("fee");
					} else {
						cost = cost + Object.getDouble("sum");
					}
				}
				
				if (json.containsKey("sum")) {
					if (json.containsKey("fee")) {
						cost = cost + json.getDouble("sum") - json.getDouble("fee");
					} else {
						cost = cost + json.getDouble("sum");
					}
				}
				
				HSSFCell cell11121121111 = row9.createCell(6);
				cell11121121111.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell11121121111.setCellStyle(cellStyle9);
				cell11121121111.setCellValue(cost);
				
				sumInfo = auth.getCustomerno() + "_CustomerRecon_ALL_"+ orderTimeStart +".xls";
			}
			
			if (flag) {
				HSSFSheet sheet1 = wb.createSheet("交易信息");
				sheet1.setColumnWidth(0, 20 * 256); 
				HSSFFont font = wb.createFont(); 
				
				font.setFontName("微软雅黑"); 
				font.setFontHeightInPoints((short) 15);//设置字体大小
				//font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
				HSSFCellStyle cellStyle = wb.createCellStyle();  
				cellStyle.setFont(font);  
				cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直  
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
				
				HSSFFont font2 = wb.createFont(); 
				font2.setFontName("微软雅黑"); 
				font2.setFontHeightInPoints((short)15);//设置字体大小
				//font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
				HSSFCellStyle cellStyle2 = wb.createCellStyle();
				
				
				HSSFFont font3 = wb.createFont(); 
				font3.setFontName("微软雅黑"); 
				font3.setFontHeightInPoints((short)15);//设置字体大小
				//font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
				HSSFCellStyle cellStyle3 = wb.createCellStyle(); 
				cellStyle3.setFont(font3);  
				
				// 设置边框样式
				cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);
				cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				cellStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);
				// 设置边框颜色
				cellStyle2.setTopBorderColor(HSSFColor.BLACK.index);
				cellStyle2.setBottomBorderColor(HSSFColor.BLACK.index);
				cellStyle2.setLeftBorderColor(HSSFColor.BLACK.index);
				cellStyle2.setRightBorderColor(HSSFColor.BLACK.index);
				// 设置背景颜色
				cellStyle2.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
				cellStyle2.setFillPattern(CellStyle.SOLID_FOREGROUND);
				cellStyle2.setFont(font2);
				cellStyle2.setAlignment(HSSFCellStyle.ALIGN_LEFT);  
				
				HSSFPatriarch patriarch = sheet1.createDrawingPatriarch();
				HSSFClientAnchor anchor = new HSSFClientAnchor(450, 0, 750, 0, 
						(short) 0, 0, (short) 1, 1);
				HSSFRow row1 = sheet1.createRow(0);
				row1.setHeightInPoints(80);
				row1.setHeight((short) (30 * 40));
				HSSFCell cell = row1.createCell(2);
				sheet1.setDefaultColumnWidth(35);
				sheet1.setDefaultRowHeightInPoints(30);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(cellStyle);
				cell.setCellValue("交易对账单 " + orderTimeStart);
				
				HSSFRow row0 = sheet1.createRow(3);
				HSSFCell cell0 = row0.createCell(0);
				cell0.setCellStyle(cellStyle);
				cell0.setCellValue("");
				
				HSSFCell cell2 = row1.createCell(5);
				cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell2.setCellStyle(cellStyle);
				cell2.setCellValue(phone);
				
				// 插入图片  
				patriarch.createPicture(anchor, wb.addPicture(byteArrayOut  
						.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
				sheet1.addMergedRegion(new CellRangeAddress(0,0,0,1));
				sheet1.addMergedRegion(new CellRangeAddress(0,0,2,4));
				sheet1.addMergedRegion(new CellRangeAddress(0,0,5,6));
				// 对账单头
				Customer customer = customerService.findCustByRemote(auth.getCustomerno());
				params = new HashMap<>();
				params.put("receiver", auth.getCustomerno());
				params.put("orderTimeStart", DateUtils.getMinTime(sdf.parse(orderTimeStart)));
				params.put("orderTimeEnd", DateUtils.getMaxTime(sdf.parse(orderTimeStart)));
				params.put("status", "SUCCESS");
				params.put("sys", "CUSTOMER");
				String info = onlineTradeQueryHessianService.orderFeeSum(params);
				JSONObject jsonObject = JSONObject.fromObject(info);
				HSSFRow row3 = sheet1.createRow(1);
				
				HSSFCell cell3 = row3.createCell(0);
				cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell3.setCellStyle(cellStyle2);
				cell3.setCellValue("商户号");
				
				HSSFCell cell4 = row3.createCell(1);
				cell4.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell4.setCellStyle(cellStyle2);
				cell4.setCellValue("商户名");
				
				HSSFCell cell5 = row3.createCell(2);
				cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell5.setCellStyle(cellStyle2);
				cell5.setCellValue("支付笔数");
				
				HSSFCell cell6 = row3.createCell(3);
				cell6.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell6.setCellStyle(cellStyle2);
				cell6.setCellValue("支付总额");
				
				HSSFCell cell7 = row3.createCell(4);
				cell7.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell7.setCellStyle(cellStyle2);
				cell7.setCellValue("手续费总额");
				
				HSSFCell cell8 = row3.createCell(5);
				cell8.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell8.setCellStyle(cellStyle2);
				cell8.setCellValue("应结金额");
				
				HSSFCell cell9 = row3.createCell(6);
				cell9.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell9.setCellStyle(cellStyle2);
				cell9.setCellValue("交易日期");
				
				HSSFRow row4 = sheet1.createRow(2);
				
				HSSFCell no = row4.createCell(0);
				no.setCellType(HSSFCell.CELL_TYPE_STRING);
				no.setCellStyle(cellStyle3);
				no.setCellValue(auth.getCustomerno());
				
				HSSFCell name = row4.createCell(1);
				name.setCellType(HSSFCell.CELL_TYPE_STRING);
				name.setCellStyle(cellStyle3);
				name.setCellValue(customer.getFullName());
				
				if (jsonObject.containsKey("al")) {
					HSSFCell al = row4.createCell(2);
					al.setCellType(HSSFCell.CELL_TYPE_STRING);
					al.setCellStyle(cellStyle3);
					al.setCellValue(jsonObject.getInt("al"));
				} else {
					HSSFCell al = row4.createCell(2);
					al.setCellType(HSSFCell.CELL_TYPE_STRING);
					al.setCellStyle(cellStyle3);
					al.setCellValue(0.00);
				}
				if (jsonObject.containsKey("am")) {
					HSSFCell am = row4.createCell(3);
					am.setCellType(HSSFCell.CELL_TYPE_STRING);
					am.setCellStyle(cellStyle3);
					am.setCellValue(jsonObject.getDouble("am"));
				} else {
					HSSFCell am = row4.createCell(3);
					am.setCellType(HSSFCell.CELL_TYPE_STRING);
					am.setCellStyle(cellStyle3);
					am.setCellValue(0.00);
				}
				if (jsonObject.containsKey("pf")) {
					HSSFCell am = row4.createCell(4);
					am.setCellType(HSSFCell.CELL_TYPE_STRING);
					am.setCellStyle(cellStyle3);
					am.setCellValue(jsonObject.getDouble("pf"));
				} else {
					HSSFCell am = row4.createCell(4);
					am.setCellType(HSSFCell.CELL_TYPE_STRING);
					am.setCellStyle(cellStyle3);
					am.setCellValue(0.00);
				}
				if (jsonObject.containsKey("am")) {
					if (jsonObject.containsKey("pf")) {
						HSSFCell am = row4.createCell(5);
						am.setCellType(HSSFCell.CELL_TYPE_STRING);
						am.setCellStyle(cellStyle3);
						am.setCellValue(jsonObject.getDouble("am") - jsonObject.getDouble("pf"));
					} else {
						HSSFCell am = row4.createCell(5);
						am.setCellType(HSSFCell.CELL_TYPE_STRING);
						am.setCellStyle(cellStyle3);
						am.setCellValue(jsonObject.getDouble("am"));
					}
				} else {
					HSSFCell am = row4.createCell(5);
					am.setCellType(HSSFCell.CELL_TYPE_STRING);
					am.setCellStyle(cellStyle3);
					am.setCellValue(0.00);
				}
				HSSFCell t = row4.createCell(6);
				t.setCellType(HSSFCell.CELL_TYPE_STRING);
				t.setCellStyle(cellStyle3);
				t.setCellValue(orderTimeStart);
				
				
				// 订单详细
				order = onlineTradeQueryHessianService.customerRecon(params);
				HSSFRow row2 = sheet1.createRow(4);
				
				HSSFCell cell10 = row2.createCell(0);
				cell10.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell10.setCellStyle(cellStyle2);
				cell10.setCellValue("交易方式名称");
				
				HSSFCell cell11 = row2.createCell(1);
				cell11.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell11.setCellStyle(cellStyle2);
				cell11.setCellValue("支付方式");
				
				HSSFCell cell12 = row2.createCell(2);
				cell12.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell12.setCellStyle(cellStyle2);
				cell12.setCellValue("商户订单日期");
				
				HSSFCell cell13 = row2.createCell(3);
				cell13.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell13.setCellStyle(cellStyle2);
				cell13.setCellValue("商户订单号");
				
				HSSFCell cell14 = row2.createCell(4);
				cell14.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell14.setCellStyle(cellStyle2);
				cell14.setCellValue("支付流水号");
				
				HSSFCell cell15 = row2.createCell(5);
				cell15.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell15.setCellStyle(cellStyle2);
				cell15.setCellValue("订单金额");
				
				HSSFCell cell16 = row2.createCell(6);
				cell16.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell16.setCellStyle(cellStyle2);
				cell16.setCellValue("手续费");
				
				HSSFCell cell17 = row2.createCell(7);
				cell17.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell17.setCellStyle(cellStyle2);
				cell17.setCellValue("交易完成时间");
				
				HSSFRow row = null;
				for (int i = 0; i < order.size(); i++) {
					Order o = order.get(i);
					row = sheet1.createRow(i + 5);
					
					HSSFCell a1 = row.createCell(0);
					a1.setCellType(HSSFCell.CELL_TYPE_STRING);
					a1.setCellStyle(cellStyle3);
					a1.setCellValue("在线支付");
					
					switch (o.getPayType().name()) {
					case "B2C":
						HSSFCell a2 = row.createCell(1);
						a2.setCellType(HSSFCell.CELL_TYPE_STRING);
						a2.setCellStyle(cellStyle3);
						a2.setCellValue("个人网银");
						break;
					case "B2B":
						HSSFCell a21 = row.createCell(1);
						a21.setCellType(HSSFCell.CELL_TYPE_STRING);
						a21.setCellStyle(cellStyle3);
						a21.setCellValue("企业网银");
						break;
					case "AUTHPAY":
						HSSFCell a211 = row.createCell(1);
						a211.setCellType(HSSFCell.CELL_TYPE_STRING);
						a211.setCellStyle(cellStyle3);
						a211.setCellValue("认证支付");
						break;
					case "REMIT":
						HSSFCell a2111 = row.createCell(1);
						a2111.setCellType(HSSFCell.CELL_TYPE_STRING);
						a2111.setCellStyle(cellStyle3);
						a2111.setCellValue("代付");
						break;
					case "WXJSAPI":
						HSSFCell a21111 = row.createCell(1);
						a21111.setCellType(HSSFCell.CELL_TYPE_STRING);
						a21111.setCellStyle(cellStyle3);
						a21111.setCellValue("微信公众号");
						break;
					case "WXNATIVE":
						HSSFCell a211111 = row.createCell(1);
						a211111.setCellType(HSSFCell.CELL_TYPE_STRING);
						a211111.setCellStyle(cellStyle3);
						a211111.setCellValue("微信扫码");
						break;
					case "ALIPAY":
						HSSFCell a2111111 = row.createCell(1);
						a2111111.setCellType(HSSFCell.CELL_TYPE_STRING);
						a2111111.setCellStyle(cellStyle3);
						a2111111.setCellValue("支付宝");
						break;
					case "UNKNOWN":
						HSSFCell a21111111 = row.createCell(1);
						a21111111.setCellType(HSSFCell.CELL_TYPE_STRING);
						a21111111.setCellStyle(cellStyle3);
						a21111111.setCellValue("未知");
						break;
					case "WXMICROPAY":
						HSSFCell a211111111 = row.createCell(1);
						a211111111.setCellType(HSSFCell.CELL_TYPE_STRING);
						a211111111.setCellStyle(cellStyle3);
						a211111111.setCellValue("微信条码");
						break;
					case "ALIPAYMICROPAY":
						HSSFCell a2111111111 = row.createCell(1);
						a2111111111.setCellType(HSSFCell.CELL_TYPE_STRING);
						a2111111111.setCellStyle(cellStyle3);
						a2111111111.setCellValue("支付宝条码");
						break;
					}
					HSSFCell b1 = row.createCell(2);
					b1.setCellType(HSSFCell.CELL_TYPE_STRING);
					b1.setCellStyle(cellStyle3);
					b1.setCellValue(sdf.format(o.getCreateTime()));
					
					HSSFCell b3 = row.createCell(3);
					b3.setCellType(HSSFCell.CELL_TYPE_STRING);
					b3.setCellStyle(cellStyle3);
					b3.setCellValue(o.getCode());
					
					HSSFCell b4 = row.createCell(4);
					b4.setCellType(HSSFCell.CELL_TYPE_STRING);
					b4.setCellStyle(cellStyle3);
					b4.setCellValue(o.getRequestCode());
					
					HSSFCell b5 = row.createCell(5);
					b5.setCellType(HSSFCell.CELL_TYPE_STRING);
					b5.setCellStyle(cellStyle3);
					b5.setCellValue(o.getAmount());
					
					HSSFCell b6 = row.createCell(6);
					b6.setCellType(HSSFCell.CELL_TYPE_STRING);
					b6.setCellStyle(cellStyle3);
					b6.setCellValue(o.getReceiverFee());
					
					HSSFCell b7 = row.createCell(7);
					b7.setCellType(HSSFCell.CELL_TYPE_STRING);
					b7.setCellStyle(cellStyle3);
					b7.setCellValue(myFmt2.format(o.getSuccessPayTime()));
				}
			}
			
			if (flag) {
				HSSFSheet sheet2 = wb.createSheet("代付信息");
				sheet2.setColumnWidth(0, 20 * 256); 
				HSSFFont font = wb.createFont(); 
				
				font.setFontName("微软雅黑"); 
				font.setFontHeightInPoints((short) 15);//设置字体大小
				// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
				HSSFCellStyle cellStyle = wb.createCellStyle();  
				cellStyle.setFont(font);  
				cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直  
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
				
				HSSFFont font2 = wb.createFont(); 
				font2.setFontName("微软雅黑"); 
				font2.setFontHeightInPoints((short)15);//设置字体大小
				//font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
				HSSFCellStyle cellStyle2 = wb.createCellStyle();  
				
				// 设置边框样式
				cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);
				cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				cellStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);
				// 设置边框颜色
				cellStyle2.setTopBorderColor(HSSFColor.BLACK.index);
				cellStyle2.setBottomBorderColor(HSSFColor.BLACK.index);
				cellStyle2.setLeftBorderColor(HSSFColor.BLACK.index);
				cellStyle2.setRightBorderColor(HSSFColor.BLACK.index);
				// 设置背景颜色
				cellStyle2.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
				cellStyle2.setFillPattern(CellStyle.SOLID_FOREGROUND);
				cellStyle2.setFont(font2);
				cellStyle2.setAlignment(HSSFCellStyle.ALIGN_LEFT);  
				
				HSSFFont font3 = wb.createFont(); 
				font3.setFontName("微软雅黑"); 
				font3.setFontHeightInPoints((short)15);//设置字体大小
				//font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
				HSSFCellStyle cellStyle3 = wb.createCellStyle(); 
				cellStyle3.setFont(font3);  
				
				HSSFPatriarch patriarch = sheet2.createDrawingPatriarch();
				HSSFClientAnchor anchor = new HSSFClientAnchor(450, 0, 750, 0, 
						(short) 0, 0, (short) 1, 1);
				HSSFRow row1 = sheet2.createRow(0);
				row1.setHeightInPoints(80);
				row1.setHeight((short) (30 * 40));
				HSSFCell cell = row1.createCell(2);
				sheet2.setDefaultColumnWidth(35);
				sheet2.setDefaultRowHeightInPoints(30);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(cellStyle);
				cell.setCellValue("代付对账单 " + orderTimeStart);
				
				HSSFCell cell2 = row1.createCell(5);
				cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell2.setCellStyle(cellStyle);
				cell2.setCellValue(phone);
				
				// 插入图片  
				patriarch.createPicture(anchor, wb.addPicture(byteArrayOut  
						.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
				sheet2.addMergedRegion(new CellRangeAddress(0,0,0,1));
				sheet2.addMergedRegion(new CellRangeAddress(0,0,2,4));
				sheet2.addMergedRegion(new CellRangeAddress(0,0,5,6));
				// 对账单头
				Customer customer = customerService.findCustByRemote(auth.getCustomerno());
				params = new HashMap<>();
				params.put("ownerId", auth.getCustomerno());
				params.put("starTime", DateUtils.getMinTime(sdf.parse(orderTimeStart)));
				params.put("starEnd", DateUtils.getMaxTime(sdf.parse(orderTimeStart)));
				String info = requestFacade.customerReconHead(params);
				JSONObject jsonObject = JSONObject.fromObject(info);
				HSSFRow row3 = sheet2.createRow(1);
				
				HSSFCell cell3 = row3.createCell(0);
				cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell3.setCellStyle(cellStyle2);
				cell3.setCellValue("商户号");
				
				HSSFCell cell4 = row3.createCell(1);
				cell4.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell4.setCellStyle(cellStyle2);
				cell4.setCellValue("商户名");
				
				HSSFCell cell5 = row3.createCell(2);
				cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell5.setCellStyle(cellStyle2);
				cell5.setCellValue("支付笔数");
				
				HSSFCell cell6 = row3.createCell(3);
				cell6.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell6.setCellStyle(cellStyle2);
				cell6.setCellValue("支付总额");
				
				HSSFCell cell7 = row3.createCell(4);
				cell7.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell7.setCellStyle(cellStyle2);
				cell7.setCellValue("手续费总额");
				
				HSSFCell cell8 = row3.createCell(5);
				cell8.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell8.setCellStyle(cellStyle2);
				cell8.setCellValue("应结金额");
				
				HSSFCell cell9 = row3.createCell(6);
				cell9.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell9.setCellStyle(cellStyle2);
				cell9.setCellValue("交易日期");
				
				HSSFRow row4 = sheet2.createRow(2);
				HSSFCell no = row4.createCell(0);
				no.setCellType(HSSFCell.CELL_TYPE_STRING);
				no.setCellStyle(cellStyle3);
				no.setCellValue(auth.getCustomerno());
				
				HSSFCell name = row4.createCell(1);
				name.setCellType(HSSFCell.CELL_TYPE_STRING);
				name.setCellStyle(cellStyle3);
				name.setCellValue(customer.getFullName());
				
				if (jsonObject.containsKey("count")) {
					HSSFCell count = row4.createCell(2);
					count.setCellType(HSSFCell.CELL_TYPE_STRING);
					count.setCellStyle(cellStyle3);
					count.setCellValue(jsonObject.getInt("count"));
				} else {
					HSSFCell count = row4.createCell(2);
					count.setCellType(HSSFCell.CELL_TYPE_STRING);
					count.setCellStyle(cellStyle3);
					count.setCellValue(0.00);
				}
				if (jsonObject.containsKey("sum")) {
					HSSFCell count = row4.createCell(3);
					count.setCellType(HSSFCell.CELL_TYPE_STRING);
					count.setCellStyle(cellStyle3);
					count.setCellValue(jsonObject.getDouble("sum"));
				} else {
					HSSFCell count = row4.createCell(3);
					count.setCellType(HSSFCell.CELL_TYPE_STRING);
					count.setCellStyle(cellStyle3);
					count.setCellValue(0.00);
				}
				if (jsonObject.containsKey("fee")) {
					HSSFCell count = row4.createCell(4);
					count.setCellType(HSSFCell.CELL_TYPE_STRING);
					count.setCellStyle(cellStyle3);
					count.setCellValue(jsonObject.getDouble("fee"));
				} else {
					HSSFCell count = row4.createCell(4);
					count.setCellType(HSSFCell.CELL_TYPE_STRING);
					count.setCellStyle(cellStyle3);
					count.setCellValue(0.00);
				}
				if (jsonObject.containsKey("sum")) {
					if (jsonObject.containsKey("fee")) {
						HSSFCell count = row4.createCell(5);
						count.setCellType(HSSFCell.CELL_TYPE_STRING);
						count.setCellStyle(cellStyle3);
						count.setCellValue(jsonObject.getDouble("sum") - jsonObject.getDouble("fee"));
					} else {
						HSSFCell count = row4.createCell(5);
						count.setCellType(HSSFCell.CELL_TYPE_STRING);
						count.setCellStyle(cellStyle3);
						count.setCellValue(jsonObject.getDouble("sum"));
					}
				} else {
					HSSFCell count = row4.createCell(5);
					count.setCellType(HSSFCell.CELL_TYPE_STRING);
					count.setCellStyle(cellStyle3);
					count.setCellValue(0.00);
				}
				HSSFCell count = row4.createCell(6);
				count.setCellType(HSSFCell.CELL_TYPE_STRING);
				count.setCellStyle(cellStyle3);
				count.setCellValue(orderTimeStart);
				
				HSSFRow row5 = sheet2.createRow(3);
				HSSFCell t = row5.createCell(6);
				t.setCellType(HSSFCell.CELL_TYPE_STRING);
				t.setCellStyle(cellStyle3);
				t.setCellValue("");
				
				// 订单详细
				requestBeans = requestFacade.customerRecon(params);
				HSSFRow row2 = sheet2.createRow(4);
				
				HSSFCell cell10 = row2.createCell(0);
				cell10.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell10.setCellStyle(cellStyle2);
				cell10.setCellValue("交易方式名称");
				
				HSSFCell cell11 = row2.createCell(1);
				cell11.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell11.setCellStyle(cellStyle2);
				cell11.setCellValue("支付方式");
				
				HSSFCell cell12 = row2.createCell(2);
				cell12.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell12.setCellStyle(cellStyle2);
				cell12.setCellValue("商户订单日期");
				
				HSSFCell cell13 = row2.createCell(3);
				cell13.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell13.setCellStyle(cellStyle2);
				cell13.setCellValue("商户订单号");
				
				HSSFCell cell14 = row2.createCell(4);
				cell14.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell14.setCellStyle(cellStyle2);
				cell14.setCellValue("支付流水号");
				
				HSSFCell cell15 = row2.createCell(5);
				cell15.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell15.setCellStyle(cellStyle2);
				cell15.setCellValue("订单金额");
				
				HSSFCell cell16 = row2.createCell(6);
				cell16.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell16.setCellStyle(cellStyle2);
				cell16.setCellValue("手续费");
				
				HSSFCell cell17 = row2.createCell(7);
				cell17.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell17.setCellStyle(cellStyle2);
				cell17.setCellValue("交易完成时间");
				
				HSSFRow row = null;
				for (int i = 0; i < requestBeans.size(); i++) {
					RequestBean requestBean = requestBeans.get(i);
					row = sheet2.createRow(i + 5);
					
					HSSFCell a = row.createCell(0);
					a.setCellType(HSSFCell.CELL_TYPE_STRING);
					a.setCellStyle(cellStyle3);
					a.setCellValue("代付");
					
					HSSFCell a1 = row.createCell(1);
					a1.setCellType(HSSFCell.CELL_TYPE_STRING);
					a1.setCellStyle(cellStyle3);
					a1.setCellValue("代付");
					
					HSSFCell a2 = row.createCell(2);
					a2.setCellType(HSSFCell.CELL_TYPE_STRING);
					a2.setCellStyle(cellStyle3);
					a2.setCellValue(sdf.format(requestBean.getCreateDate()));
					
					HSSFCell a3 = row.createCell(3);
					a3.setCellType(HSSFCell.CELL_TYPE_STRING);
					a3.setCellStyle(cellStyle3);
					a3.setCellValue(requestBean.getRequestNo());
					
					HSSFCell a4 = row.createCell(4);
					a4.setCellType(HSSFCell.CELL_TYPE_STRING);
					a4.setCellStyle(cellStyle3);
					a4.setCellValue(requestBean.getFlowNo());
					
					HSSFCell a5 = row.createCell(5);
					a5.setCellType(HSSFCell.CELL_TYPE_STRING);
					a5.setCellStyle(cellStyle3);
					a5.setCellValue(requestBean.getAmount());
					
					HSSFCell a6 = row.createCell(6);
					a6.setCellType(HSSFCell.CELL_TYPE_STRING);
					a6.setCellStyle(cellStyle3);
					a6.setCellValue(requestBean.getFee());
					
					HSSFCell a7 = row.createCell(7);
					a7.setCellType(HSSFCell.CELL_TYPE_STRING);
					a7.setCellStyle(cellStyle3);
					a7.setCellValue(sdf.format(requestBean.getCompleteDate()));
				}
			}
			
			if (flag) {
				HSSFSheet sheet3 = wb.createSheet("代收信息");
				sheet3.setColumnWidth(0, 20 * 256); 
				HSSFFont font = wb.createFont(); 
				
				font.setFontName("微软雅黑"); 
				font.setFontHeightInPoints((short) 15);//设置字体大小
				//font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
				HSSFCellStyle cellStyle = wb.createCellStyle();  
				cellStyle.setFont(font);  
				cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直  
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
				
				HSSFFont font2 = wb.createFont(); 
				font2.setFontName("微软雅黑"); 
				font2.setFontHeightInPoints((short)15);//设置字体大小
				//font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
				HSSFCellStyle cellStyle2 = wb.createCellStyle();  
				
				HSSFFont font3 = wb.createFont(); 
				font3.setFontName("微软雅黑"); 
				font3.setFontHeightInPoints((short)15);//设置字体大小
				//font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
				HSSFCellStyle cellStyle3 = wb.createCellStyle(); 
				cellStyle3.setFont(font3);
				
				// 设置边框样式
				cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);
				cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				cellStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);
				// 设置边框颜色
				cellStyle2.setTopBorderColor(HSSFColor.BLACK.index);
				cellStyle2.setBottomBorderColor(HSSFColor.BLACK.index);
				cellStyle2.setLeftBorderColor(HSSFColor.BLACK.index);
				cellStyle2.setRightBorderColor(HSSFColor.BLACK.index);
				// 设置背景颜色
				cellStyle2.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
				cellStyle2.setFillPattern(CellStyle.SOLID_FOREGROUND);
				cellStyle2.setFont(font2);
				cellStyle2.setAlignment(HSSFCellStyle.ALIGN_LEFT);  
				
				HSSFPatriarch patriarch = sheet3.createDrawingPatriarch();
				HSSFClientAnchor anchor = new HSSFClientAnchor(450, 0, 750, 0, 
						(short) 0, 0, (short) 1, 1);
				HSSFRow row1 = sheet3.createRow(0);
				row1.setHeightInPoints(44);
				row1.setHeight((short) (30 * 40));
				HSSFCell cell = row1.createCell(2);
				sheet3.setDefaultColumnWidth(35);
				sheet3.setDefaultRowHeightInPoints(30);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(cellStyle);
				cell.setCellValue("代收对账单 " + orderTimeStart);
				
				HSSFCell cell2 = row1.createCell(5);
				cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell2.setCellStyle(cellStyle);
				cell2.setCellValue(phone);
				
				// 插入图片  
				patriarch.createPicture(anchor, wb.addPicture(byteArrayOut  
						.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
				sheet3.addMergedRegion(new CellRangeAddress(0,0,0,1));
				sheet3.addMergedRegion(new CellRangeAddress(0,0,2,4));
				sheet3.addMergedRegion(new CellRangeAddress(0,0,5,6));
				// 对账单头
				Customer customer = customerService.findCustByRemote(auth.getCustomerno());
				params = new HashMap<>();
				params.put("ownerId", auth.getCustomerno());
				params.put("starTime", DateUtils.getMinTime(sdf.parse(orderTimeStart)));
				params.put("starEnd", DateUtils.getMaxTime(sdf.parse(orderTimeStart)));
				String info = receiveQueryFacade.customerReconHead(params);
				JSONObject jsonObject = JSONObject.fromObject(info);
				HSSFRow row3 = sheet3.createRow(1);
				
				HSSFCell cell3 = row3.createCell(0);
				cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell3.setCellStyle(cellStyle2);
				cell3.setCellValue("商户号");
				
				HSSFCell cell4 = row3.createCell(1);
				cell4.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell4.setCellStyle(cellStyle2);
				cell4.setCellValue("商户名");
				
				HSSFCell cell5 = row3.createCell(2);
				cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell5.setCellStyle(cellStyle2);
				cell5.setCellValue("支付笔数");
				
				HSSFCell cell6 = row3.createCell(3);
				cell6.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell6.setCellStyle(cellStyle2);
				cell6.setCellValue("支付总额");
				
				HSSFCell cell7 = row3.createCell(4);
				cell7.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell7.setCellStyle(cellStyle2);
				cell7.setCellValue("手续费总额");
				
				HSSFCell cell8 = row3.createCell(5);
				cell8.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell8.setCellStyle(cellStyle2);
				cell8.setCellValue("应结金额");
				
				HSSFCell cell9 = row3.createCell(6);
				cell9.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell9.setCellStyle(cellStyle2);
				cell9.setCellValue("交易日期");
				
				HSSFRow row4 = sheet3.createRow(2);
				
				HSSFCell no = row4.createCell(0);
				no.setCellType(HSSFCell.CELL_TYPE_STRING);
				no.setCellStyle(cellStyle3);
				no.setCellValue(auth.getCustomerno());
				
				HSSFCell name = row4.createCell(1);
				name.setCellType(HSSFCell.CELL_TYPE_STRING);
				name.setCellStyle(cellStyle3);
				name.setCellValue(customer.getFullName());
				
				if (jsonObject.containsKey("count")) {
					HSSFCell c2 = row4.createCell(2);
					c2.setCellType(HSSFCell.CELL_TYPE_STRING);
					c2.setCellStyle(cellStyle3);
					c2.setCellValue(jsonObject.getInt("count"));
				} else {
					HSSFCell c2 = row4.createCell(2);
					c2.setCellType(HSSFCell.CELL_TYPE_STRING);
					c2.setCellStyle(cellStyle3);
					c2.setCellValue(0.00);
				}
				if (jsonObject.containsKey("sum")) {
					HSSFCell c2 = row4.createCell(3);
					c2.setCellType(HSSFCell.CELL_TYPE_STRING);
					c2.setCellStyle(cellStyle3);
					c2.setCellValue(jsonObject.getDouble("sum"));
				} else {
					HSSFCell c2 = row4.createCell(3);
					c2.setCellType(HSSFCell.CELL_TYPE_STRING);
					c2.setCellStyle(cellStyle3);
					c2.setCellValue(0.00);
				}
				if (jsonObject.containsKey("fee")) {
					HSSFCell c2 = row4.createCell(4);
					c2.setCellType(HSSFCell.CELL_TYPE_STRING);
					c2.setCellStyle(cellStyle3);
					c2.setCellValue(jsonObject.getDouble("fee"));
				} else {
					HSSFCell c2 = row4.createCell(4);
					c2.setCellType(HSSFCell.CELL_TYPE_STRING);
					c2.setCellStyle(cellStyle3);
					c2.setCellValue(0.00);
				}
				if (jsonObject.containsKey("sum")) {
					if (jsonObject.containsKey("fee")) {
						HSSFCell c2 = row4.createCell(5);
						c2.setCellType(HSSFCell.CELL_TYPE_STRING);
						c2.setCellStyle(cellStyle3);
						c2.setCellValue(jsonObject.getDouble("sum") - jsonObject.getDouble("fee"));
					} else {
						HSSFCell c2 = row4.createCell(5);
						c2.setCellType(HSSFCell.CELL_TYPE_STRING);
						c2.setCellStyle(cellStyle3);
						c2.setCellValue(jsonObject.getDouble("sum"));
					}
				} else {
					HSSFCell c2 = row4.createCell(5);
					c2.setCellType(HSSFCell.CELL_TYPE_STRING);
					c2.setCellStyle(cellStyle3);
					c2.setCellValue(0.00);
				}
				HSSFCell c6 = row4.createCell(6);
				c6.setCellType(HSSFCell.CELL_TYPE_STRING);
				c6.setCellStyle(cellStyle3);
				c6.setCellValue(orderTimeStart);
				HSSFRow row5 = sheet3.createRow(3);
				HSSFCell c7 = row5.createCell(0);
				c7.setCellType(HSSFCell.CELL_TYPE_STRING);
				c7.setCellStyle(cellStyle3);
				c7.setCellValue("");
				
				// 订单详细
				receiveRequestBeans = receiveQueryFacade.customerRecon(params);
				HSSFRow row2 = sheet3.createRow(4);
				
				HSSFCell cell10 = row2.createCell(0);
				cell10.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell10.setCellStyle(cellStyle2);
				cell10.setCellValue("交易方式名称");
				
				HSSFCell cell11 = row2.createCell(1);
				cell11.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell11.setCellStyle(cellStyle2);
				cell11.setCellValue("支付方式");
				
				HSSFCell cell12 = row2.createCell(2);
				cell12.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell12.setCellStyle(cellStyle2);
				cell12.setCellValue("商户订单日期");
				
				HSSFCell cell13 = row2.createCell(3);
				cell13.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell13.setCellStyle(cellStyle2);
				cell13.setCellValue("商户订单号");
				
				HSSFCell cell14 = row2.createCell(4);
				cell14.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell14.setCellStyle(cellStyle2);
				cell14.setCellValue("支付流水号");
				
				HSSFCell cell15 = row2.createCell(5);
				cell15.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell15.setCellStyle(cellStyle2);
				cell15.setCellValue("订单金额");
				
				HSSFCell cell16 = row2.createCell(6);
				cell16.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell16.setCellStyle(cellStyle2);
				cell16.setCellValue("手续费");
				
				HSSFCell cell17 = row2.createCell(7);
				cell17.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell17.setCellStyle(cellStyle2);
				cell17.setCellValue("交易完成时间");
				
				HSSFRow row = null;
				for (int i = 0; i < receiveRequestBeans.size(); i++) {
					ReceiveRequestBean receiveRequestBean = receiveRequestBeans.get(i);
					row = sheet3.createRow(i + 5);
					
					HSSFCell a = row.createCell(0);
					a.setCellType(HSSFCell.CELL_TYPE_STRING);
					a.setCellStyle(cellStyle3);
					a.setCellValue("代收");
					
					HSSFCell a1 = row.createCell(1);
					a1.setCellType(HSSFCell.CELL_TYPE_STRING);
					a1.setCellStyle(cellStyle3);
					a1.setCellValue("代收");
					
					HSSFCell a2 = row.createCell(2);
					a2.setCellType(HSSFCell.CELL_TYPE_STRING);
					a2.setCellStyle(cellStyle3);
					a2.setCellValue(sdf.format(receiveRequestBean.getCreateTime()));
					
					HSSFCell a3 = row.createCell(3);
					a3.setCellType(HSSFCell.CELL_TYPE_STRING);
					a3.setCellStyle(cellStyle3);
					a3.setCellValue(receiveRequestBean.getCustomerOrderCode());
					
					HSSFCell a4 = row.createCell(4);
					a4.setCellType(HSSFCell.CELL_TYPE_STRING);
					a4.setCellStyle(cellStyle3);
					a4.setCellValue(receiveRequestBean.getReceiveId());
					
					HSSFCell a5 = row.createCell(5);
					a5.setCellType(HSSFCell.CELL_TYPE_STRING);
					a5.setCellStyle(cellStyle3);
					a5.setCellValue(receiveRequestBean.getAmount());
					
					HSSFCell a6 = row.createCell(6);
					a6.setCellType(HSSFCell.CELL_TYPE_STRING);
					a6.setCellStyle(cellStyle3);
					a6.setCellValue(receiveRequestBean.getFee());
					
					HSSFCell a7 = row.createCell(7);
					a7.setCellType(HSSFCell.CELL_TYPE_STRING);
					a7.setCellStyle(cellStyle3);
					a7.setCellValue(sdf.format(receiveRequestBean.getLastUpdateTime()));
				}
			}
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			wb.write(os);
			byte[] fileContent = os.toByteArray();
            ByteArrayInputStream is = new ByteArrayInputStream(fileContent);
            inputStream = is;
		} catch (IOException e) {
			logger.error("对账文件生成失败！", e);
		}
		return SUCCESS;
	}

	/**
	 * 查询所有提供方信息
	 * 
	 * @return
	 */
	public String findAllProvider() {
		interfaceProviderQueryBeans = interfaceProviderHessianService.findAllProvider();
		return "findAllProvider";
	}

	/**
	 * 查询所有接口信息
	 * 
	 * @return
	 */
	public String findAllInterfaceInfo() {
		interfaceInfoBeans = payInterfaceHessianService.queryInterfaceInfo();
		return "findAllInterfaceInfo";
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 */
	public String findAllTradeOrderAndFee() {
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		tradeOrderBean.setReceiver(auth.getCustomerno());
		if (tradeOrderBean.getSuccessPayTimeStart() != null) {
			tradeOrderBean.setSuccessPayTimeStart(DateUtils.getMinTime(tradeOrderBean.getSuccessPayTimeStart()));
		}
		if (tradeOrderBean.getSuccessPayTimeEnd() != null) {
			tradeOrderBean.setSuccessPayTimeEnd(DateUtils.getMaxTime(tradeOrderBean.getSuccessPayTimeEnd()));
		}
		if (tradeOrderBean.getOrderTimeStart() != null) {
			tradeOrderBean.setOrderTimeStart(DateUtils.getMinTime(tradeOrderBean.getOrderTimeStart()));
		}
		if (tradeOrderBean.getOrderTimeEnd() != null) {
			tradeOrderBean.setOrderTimeEnd(DateUtils.getMaxTime(tradeOrderBean.getOrderTimeEnd()));
		}
		Map<String, Object> params = ObjectToMap(tradeOrderBean);
		params.put("sys", "CUSTOMER");
		int currentPage = this.getHttpRequest().getParameter("currentPage") == null ? 1 : Integer.parseInt(this.getHttpRequest().getParameter("currentPage"));
		if (page == null) {
			page = new Page();
		}
		page.setCurrentPage(currentPage);
		page = (Page) onlineTradeQueryHessianService.findAllTradeOrderAndFee(page, params);
		tradeOrders = page.getObject();
		return "findAllTradeOrderAndFee";
	}

	/**
	 * 查找一个订单
	 * 
	 * @return
	 */
	public String findOrderByCode() {
		tradeOrder = onlineTradeQueryHessianService.findOrderByCode(orderCode);
		payments = (List<Payment>) onlineTradeQueryHessianService.findPaymentByOrderCode(orderCode);
		return "findOrderByCode";
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
			System.out.println("transBean2Map Error " + e);
		}
		return map;
	}

	/**
	 * 查询统计
	 * 
	 * @return
	 */
	public String payOrderSum() {
		Authorization auth = (Authorization)getSession().getAttribute(Constant.SESSION_AUTH);
		String customerNo = auth.getCustomerno();
		tradeOrderBean.setReceiver(customerNo);
		if (tradeOrderBean.getSuccessPayTimeStart() != null) {
			tradeOrderBean.setSuccessPayTimeStart(DateUtils.getMinTime(tradeOrderBean.getSuccessPayTimeStart()));
		}
		if (tradeOrderBean.getSuccessPayTimeEnd() != null) {
			tradeOrderBean.setSuccessPayTimeEnd(DateUtils.getMaxTime(tradeOrderBean.getSuccessPayTimeEnd()));
		}
		if (tradeOrderBean.getOrderTimeStart() != null) {
			tradeOrderBean.setOrderTimeStart(DateUtils.getMinTime(tradeOrderBean.getOrderTimeStart()));
		}
		if (tradeOrderBean.getOrderTimeEnd() != null) {
			tradeOrderBean.setOrderTimeEnd(DateUtils.getMaxTime(tradeOrderBean.getOrderTimeEnd()));
		}
		Map<String, Object> params = ObjectToMap(tradeOrderBean);
		params.put("sys", "CUSTOMER");
		sumInfo = onlineTradeQueryHessianService.orderFeeSum(params);
		Map<String, Object> map = JsonUtils.toObject(sumInfo, HashMap.class);
		DecimalFormat df = new DecimalFormat("#,##0.00");
		String am = "0";
		String pf = "0";
		String al = "0";
		if (map != null) {
			if (map.get("pf") != null) {
				pf = df.format(Double.parseDouble(map.get("pf").toString()));
			}
			if (map.get("am") != null) {
				am = df.format(Double.parseDouble(map.get("am").toString()));
			}
			if (map.get("al") != null) {
				al = map.get("al").toString();
			}
		}
		Map<String,String> jMap = new HashMap<String,String>();
		jMap.put("am", am);
		jMap.put("pf", pf);
		jMap.put("al", al);
		sumInfo = JsonUtils.toJsonString(jMap);
		return "payOrderSum";
	}

	/**
	 * daochu
	 * 
	 * @return
	 */
	public String payOrderExport() {
		Authorization auth = (Authorization)getSession().getAttribute(Constant.SESSION_AUTH);
		String customerNo = auth.getCustomerno();
		if (tradeOrderBean.getSuccessPayTimeStart() != null) {
			tradeOrderBean.setSuccessPayTimeStart(DateUtils.getMinTime(tradeOrderBean.getSuccessPayTimeStart()));
		}
		if (tradeOrderBean.getSuccessPayTimeEnd() != null) {
			tradeOrderBean.setSuccessPayTimeEnd(DateUtils.getMaxTime(tradeOrderBean.getSuccessPayTimeEnd()));
		}
		if (tradeOrderBean.getOrderTimeStart() != null) {
			tradeOrderBean.setOrderTimeStart(DateUtils.getMinTime(tradeOrderBean.getOrderTimeStart()));
		}
		if (tradeOrderBean.getOrderTimeEnd() != null) {
			tradeOrderBean.setOrderTimeEnd(DateUtils.getMaxTime(tradeOrderBean.getOrderTimeEnd()));
		}
		tradeOrderBean.setReceiver(customerNo);
		Map<String, Object> params = ObjectToMap(tradeOrderBean);
		params.put("sys", "CUSTOMER");
		String info = onlineTradeQueryHessianService.orderInfoExport(params);
		order = JsonUtils.toObject(info, new TypeReference<List<Order>>() {
		});
		return "payOrderExport";
	}
	

	public void setInterfaceProviderHessianService(InterfaceProviderHessianService interfaceProviderHessianService) {
		this.interfaceProviderHessianService = interfaceProviderHessianService;
	}

	public List<InterfaceProviderQueryBean> getInterfaceProviderQueryBeans() {
		return interfaceProviderQueryBeans;
	}

	public void setInterfaceProviderQueryBeans(List<InterfaceProviderQueryBean> interfaceProviderQueryBeans) {
		this.interfaceProviderQueryBeans = interfaceProviderQueryBeans;
	}

	public void setPayInterfaceHessianService(PayInterfaceHessianService payInterfaceHessianService) {
		this.payInterfaceHessianService = payInterfaceHessianService;
	}

	public List<InterfaceInfoBean> getInterfaceInfoBeans() {
		return interfaceInfoBeans;
	}

	public void setInterfaceInfoBeans(List<InterfaceInfoBean> interfaceInfoBeans) {
		this.interfaceInfoBeans = interfaceInfoBeans;
	}

	public InterfacePolicyBean getInterfacePolicyBean() {
		return interfacePolicyBean;
	}

	public void setInterfacePolicyBean(InterfacePolicyBean interfacePolicyBean) {
		this.interfacePolicyBean = interfacePolicyBean;
	}

	public List<InterfacePolicyProfileBean> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<InterfacePolicyProfileBean> profiles) {
		this.profiles = profiles;
	}

	public void setOnlineInterfacePolicyHessianService(OnlineInterfacePolicyHessianService onlineInterfacePolicyHessianService) {
		this.onlineInterfacePolicyHessianService = onlineInterfacePolicyHessianService;
	}

	public String getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	public List<InterfacePolicyBean> getInterfacePolicyBeans() {
		return interfacePolicyBeans;
	}

	public void setInterfacePolicyBeans(List<InterfacePolicyBean> interfacePolicyBeans) {
		this.interfacePolicyBeans = interfacePolicyBeans;
	}

	public List<PartnerRouterProfileBean> getPartnerProfiles() {
		return partnerProfiles;
	}

	public void setPartnerProfiles(List<PartnerRouterProfileBean> partnerProfiles) {
		this.partnerProfiles = partnerProfiles;
	}

	public PartnerRouterBean getPartnerRouterBean() {
		return partnerRouterBean;
	}

	public void setPartnerRouterBean(PartnerRouterBean partnerRouterBean) {
		this.partnerRouterBean = partnerRouterBean;
	}

	public void setOnlinePartnerRouterHessianService(OnlinePartnerRouterHessianService onlinePartnerRouterHessianService) {
		this.onlinePartnerRouterHessianService = onlinePartnerRouterHessianService;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public void setOnlineTradeQueryHessianService(OnlineTradeQueryHessianService onlineTradeQueryHessianService) {
		this.onlineTradeQueryHessianService = onlineTradeQueryHessianService;
	}

	public Object getTradeOrders() {
		return tradeOrders;
	}

	public void setTradeOrders(Object tradeOrders) {
		this.tradeOrders = tradeOrders;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Object getTradeOrder() {
		return tradeOrder;
	}

	public void setTradeOrder(Object tradeOrder) {
		this.tradeOrder = tradeOrder;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	public TradeOrderBean getTradeOrderBean() {
		return tradeOrderBean;
	}

	public void setTradeOrderBean(TradeOrderBean tradeOrderBean) {
		this.tradeOrderBean = tradeOrderBean;
	}

	public String getSumInfo() {
		return sumInfo;
	}

	public void setSumInfo(String sumInfo) {
		this.sumInfo = sumInfo;
	}

	public List<Order> getOrder() {
		return order;
	}

	public void setOrder(List<Order> order) {
		this.order = order;
	}

	public Object getPartnerRouters() {
		return partnerRouters;
	}

	public void setPartnerRouters(Object partnerRouters) {
		this.partnerRouters = partnerRouters;
	}

	public OperateType getOperateType() {
		return operateType;
	}

	public void setOperateType(OperateType operateType) {
		this.operateType = operateType;
	}

	public Object getInterfacePolicys() {
		return interfacePolicys;
	}

	public void setInterfacePolicys(Object interfacePolicys) {
		this.interfacePolicys = interfacePolicys;
	}

	public String getOrderTimeStart() {
		return orderTimeStart;
	}

	public void setOrderTimeStart(String orderTimeStart) {
		this.orderTimeStart = orderTimeStart;
	}

	public String getOrderTimeEnd() {
		return orderTimeEnd;
	}

	public void setOrderTimeEnd(String orderTimeEnd) {
		this.orderTimeEnd = orderTimeEnd;
	}

	public InterfaceProviderHessianService getInterfaceProviderHessianService() {
		return interfaceProviderHessianService;
	}

	public OnlineInterfacePolicyHessianService getOnlineInterfacePolicyHessianService() {
		return onlineInterfacePolicyHessianService;
	}

	public PayInterfaceHessianService getPayInterfaceHessianService() {
		return payInterfaceHessianService;
	}

	public OnlinePartnerRouterHessianService getOnlinePartnerRouterHessianService() {
		return onlinePartnerRouterHessianService;
	}

	public OnlineTradeQueryHessianService getOnlineTradeQueryHessianService() {
		return onlineTradeQueryHessianService;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public RequestFacade getRequestFacade() {
		return requestFacade;
	}

	public void setRequestFacade(RequestFacade requestFacade) {
		this.requestFacade = requestFacade;
	}

	public List<RequestBean> getRequestBeans() {
		return requestBeans;
	}

	public void setRequestBeans(List<RequestBean> requestBeans) {
		this.requestBeans = requestBeans;
	}

	public List<ReceiveRequestBean> getReceiveRequestBeans() {
		return receiveRequestBeans;
	}

	public void setReceiveRequestBeans(List<ReceiveRequestBean> receiveRequestBeans) {
		this.receiveRequestBeans = receiveRequestBeans;
	}

	public ReceiveQueryFacade getReceiveQueryFacade() {
		return receiveQueryFacade;
	}

	public void setReceiveQueryFacade(ReceiveQueryFacade receiveQueryFacade) {
		this.receiveQueryFacade = receiveQueryFacade;
	}
}
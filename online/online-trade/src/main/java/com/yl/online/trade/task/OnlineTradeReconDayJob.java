package com.yl.online.trade.task;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.DateUtils;
import com.yl.online.model.model.Order;
import com.yl.online.trade.service.TradeOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.*;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 交易订单对账文件定时任务
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月12日
 * @version V1.0.0
 */
public class OnlineTradeReconDayJob {

	private static Logger logger = LoggerFactory.getLogger(OnlineTradeReconDayJob.class);

	@Resource
	TradeOrderService tradeOrderService;

	private String filenameTemp;

	private Date dailyDate = null;
	
	private static Properties prop = new Properties();
	static {
		try {
			prop.load(
					new InputStreamReader(OnlineTradeReconDayJob.class.getClassLoader().getResourceAsStream("serverHost.properties")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public void execute() {
		logger.info("*************** OnlineTradeReconDayJob Start ***************");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		dailyDate = DateUtils.addDays(new Date(), -1);
		Map<String, Object> params = new HashMap<>();
		boolean titleFlag = true;
		try {
			params.put("createStartTime", DateUtils.getMinTime(dailyDate));
			params.put("createEndTime", DateUtils.getMaxTime(dailyDate));
			List<Order> list = tradeOrderService.findOrderByJob(params);
			creatTxtFile("online");
			int size = list.size();
			for (int i = 0; i < size; i++) {
				createTxt(list.get(i), titleFlag);
				titleFlag = false;
			}
			Map<String, Object> map = tradeOrderService.findTotalByJob(params);
			createSum(map.get("COUNT"), map.get("AMOUNT"));
		} catch (Exception e) {
			logger.error("交易订单对账文件定时任务出错            -    OnlineTradeReconDayJob is error", e);
		}
		logger.info("*************** OnlineTradeReconDayJob End ***************");
	}

	/**
	 * 创建TXT文件
	 * 
	 * @param order
	 *            文件路径
	 * @param titleFlag
	 *            对象实体
	 * @throws IOException
	 */
	public void createTxt(Order order, boolean titleFlag) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String title = "tradeOrderCode|transType|payType|amount|fee|clearingFinishTime";
		// String title = "交易订单号|交易类型|接口类型|接口订单号|订单金额|订单手续费|交易完成时间";
		String newStr = "" + order.getCode() + "|OLPAY|" + order.getPayType() + "|"
				+ AmountUtils.round(order.getAmount(), 2, RoundingMode.HALF_UP) + "|" + AmountUtils.round(order.getReceiverFee(), 2, RoundingMode.HALF_UP) + "|"
				+ sdf.format(order.getClearingFinishTime());
		if (titleFlag) {
			writeTxtFile(title);
		}
		writeTxtFile(newStr);
	}

	public void createSum(Object count, Object transSumAmount) throws IOException {
		String title = "counts|amounts";
		String newStr = null;
		if (transSumAmount != null) {
			newStr = count + "|" + AmountUtils.round(Double.parseDouble(transSumAmount.toString()), 2, RoundingMode.HALF_UP);
		} else {
			newStr = count + "|" + transSumAmount;
		}
		writeTxtFile(title);
		writeTxtFile(newStr);
	}

	public boolean creatTxtFile(String name) throws IOException {
		boolean flag = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		filenameTemp = prop.getProperty("reconPath") + sdf.format(dailyDate) + "/" + name + ".txt";
		File filePath = new File(prop.getProperty("reconPath") + sdf.format(dailyDate));
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		File filename = new File(filenameTemp);
		if (!filename.exists()) {
			filename.createNewFile();
			flag = true;
		}
		return flag;
	}

	@SuppressWarnings("unused")
	public boolean writeTxtFile(String newStr) throws IOException {
		// 先读取原有文件内容，然后进行写入操作
		boolean flag = false;
		String filein = newStr + "\r\n";
		String temp = "";
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		FileOutputStream fos = null;
		PrintWriter pw = null;
		try {
			// 文件路径
			File file = new File(filenameTemp);
			// 将文件读入输入流
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			StringBuffer buf = new StringBuffer();
			// 保存该文件原有的内容
			for (int j = 1; (temp = br.readLine()) != null; j++) {
				buf = buf.append(temp);
				// 行与行之间的分隔符 相当于“\n”
				buf = buf.append(System.getProperty("line.separator"));
			}
			buf.append(filein);
			fos = new FileOutputStream(file);
			pw = new PrintWriter(fos);
			pw.write(buf.toString().toCharArray());
			pw.flush();
			flag = true;
		} catch (IOException e1) {
			throw e1;
		} finally {
			if (pw != null) {
				pw.close();
			}
			if (fos != null) {
				fos.close();
			}
			if (br != null) {
				br.close();
			}
			if (isr != null) {
				isr.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
		return flag;
	}

	public static Properties getProp() {
		return prop;
	}

	public static void setProp(Properties prop) {
		OnlineTradeReconDayJob.prop = prop;
	}
}
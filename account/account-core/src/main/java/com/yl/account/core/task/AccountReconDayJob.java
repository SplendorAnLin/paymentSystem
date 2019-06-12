package com.yl.account.core.task;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.DateUtils;
import com.yl.account.core.service.AccountRecordedDetailService;
import com.yl.account.model.AccountRecordedDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.*;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 当日流水对账文件定时任务
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月12日
 * @version V1.0.0
 */
public class AccountReconDayJob {

	private static Logger logger = LoggerFactory.getLogger(AccountReconDayJob.class);

	@Resource
	private AccountRecordedDetailService accountRecordedDetailService;

	private String filenameTemp;

	private Date dailyDate = null;
	
	private static Properties prop = new Properties();
	static {
		try {
			prop.load(
					new InputStreamReader(AccountReconDayJob.class.getClassLoader().getResourceAsStream("system.properties")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void execute() {
		logger.info("*************** AccountReconDayJob Start ***************");
		
		dailyDate = DateUtils.addDays(new Date(), -1);
		Map<String, Object> accountHistoryQueryParams = new HashMap<>();
		boolean titleFlag = true;
		try {
			accountHistoryQueryParams.put("createStartTime", DateUtils.getMinTime(dailyDate));
			accountHistoryQueryParams.put("createEndTime", DateUtils.getMaxTime(dailyDate));
			List<AccountRecordedDetail> list = accountRecordedDetailService
					.findAccountHistory(accountHistoryQueryParams);
			creatTxtFile("account");
			int size = list.size();
			for (int i = 0; i < size; i++) {
				createTxt(list.get(i), titleFlag);
				titleFlag = false;
			}
			Map<String, Object> map = accountRecordedDetailService.findAccountHistorySum(accountHistoryQueryParams);
			createSum(map.get("count"), map.get("transSumAmount"));
		} catch (Exception e) {
			logger.error("当日流水对账文件定时任务出错            -    AccountReconDayJob is error", e);
		}
		// 上传生成后的对账文件
		/*Sftp sf = new Sftp();
		String host = "192.168.0.5";
		int port = 22;
		String username = "root";
		String password = "zk159256";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String directory = "/home/attachment/" + sdf.format(dailyDate);
		String uploadFile = filenameTemp;
		ChannelSftp sftp = sf.connect(host, port, username, password);
		try {
			Vector content = sftp.ls(directory);
			if (content == null) {
				sftp.mkdir(directory);
			}
		} catch (Exception e) {
			try {
				sftp.mkdir(directory);
			} catch (SftpException e1) {
				logger.error("SFTP出错!", e1);
			}
		}
		sf.upload(directory, uploadFile, sftp);
		sftp.quit();*/
		logger.info("*************** AccountReconDayJob End ***************");
	}

	/**
	 * 创建TXT文件
	 * 
	 * @param titleFlag
	 *            文件路径
	 * @param accountRecordedDetail
	 *            对象实体
	 * @throws IOException
	 */
	public void createTxt(AccountRecordedDetail accountRecordedDetail, boolean titleFlag) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String title = "accountNo|tradeOrderCode|amount|symbol|transTime|systemCode";
		String newStr = "" + accountRecordedDetail.getAccountNo() + "|" + accountRecordedDetail.getTransFlow() + "|"
				+ AmountUtils.round(accountRecordedDetail.getTransAmount(), 2, RoundingMode.HALF_UP) + "|"
				+ accountRecordedDetail.getSymbol().toString() + "|" + sdf.format(accountRecordedDetail.getTransTime())
				+ "|" + accountRecordedDetail.getSystemCode();
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
		/*filenameTemp = "E:\\" + sdf.format(dailyDate) + "\\" + name + ".txt";*/
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
		String filein = newStr + System.lineSeparator();
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
				buf = buf.append(System.lineSeparator());
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
		AccountReconDayJob.prop = prop;
	}
}
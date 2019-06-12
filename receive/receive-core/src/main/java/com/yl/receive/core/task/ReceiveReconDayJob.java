package com.yl.receive.core.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.DateUtils;
import com.yl.receive.core.entity.ReceiveRequest;
import com.yl.receive.core.service.ReceiveRequestService;
import com.yl.receive.core.utils.Sftp;

/**
 * 代收对账文件定时任务
 * @author 聚合支付有限公司
 * @since 2016年4月12日
 * @version V1.0.0
 */
public class ReceiveReconDayJob {
	
	private static Logger logger = LoggerFactory.getLogger(ReceiveReconDayJob.class);
	
	@Resource
	ReceiveRequestService receiveRequestService;
	
	private String filenameTemp;
	
	private Date dailyDate = null;
	
	private static Properties prop = new Properties();
	static {
		try {
			prop.load(
					new InputStreamReader(ReceiveReconDayJob.class.getClassLoader().getResourceAsStream("system.properties")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void execute() {
		logger.info("*************** ReceiveReconDayJob Start ***************");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		dailyDate = DateUtils.addDays(new Date(), -1);
		Map<String, Object> params = new HashMap<>();
		boolean titleFlag = true;
		try {
			params.put("createStartTime", DateUtils.getMinTime(dailyDate));
			params.put("createEndTime", DateUtils.getMaxTime(dailyDate));
			List<ReceiveRequest> list = receiveRequestService.findByReconJob(params);
			creatTxtFile("receive");
			int size = list.size();
			for (int i = 0; i < size; i++) {
				createTxt(list.get(i), titleFlag);
				titleFlag = false;
			}
			Map<String, Object>  map = receiveRequestService.findTotalByJob(params);
			createSum(map.get("COUNT"), map.get("AMOUNT"), map.get("FEE"));
		} catch (Exception e) {
			logger.error("代收对账文件定时任务            -    ReceiveReconDayJob is error", e);
		}
		// 上传生成后的对账文件
		/*Sftp sf = new Sftp();
		String host = "192.168.0.5";
		int port = 22;
		String username = "root";
		String password = "zk159256";
		String directory = "/home/attachment/"+sdf.format(dailyDate);
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
		logger.info("*************** ReceiveReconDayJob End ***************");
	}
	
	/**
	 * 创建TXT文件
	 * @param path 文件路径
	 * @param accountRecordedDetail 对象实体
	 * @throws IOException
	 */
	public void createTxt(ReceiveRequest receiveRequest, boolean titleFlag) throws IOException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String title = "tradeOrderCode|transType|payType|interfaceRequestId|amount|fee|lastUpdateTime";
		String newStr = ""+receiveRequest.getReceiveId()+
				"|RECEIVE|RECEIVE|"+receiveRequest.getInterfaceRequestId()+"|"+AmountUtils.round(receiveRequest.getAmount(), 2, RoundingMode.HALF_UP)+"|"+receiveRequest.getFee()+"|"+sdf.format(receiveRequest.getLastUpdateTime());
		if (titleFlag) {
			writeTxtFile(title);
		}
		writeTxtFile(newStr);
	}
	
	
	public void createSum(Object count, Object transSumAmount, Object fee) throws IOException{
		String title = "counts|amounts|sumFee";
		String newStr = null;
		if (transSumAmount != null) {
			newStr = count + "|" + AmountUtils.round(Double.parseDouble(transSumAmount.toString()), 2, RoundingMode.HALF_UP) + "|" + 
					AmountUtils.round(Double.parseDouble(fee.toString()), 2, RoundingMode.HALF_UP);
		} else {
			newStr = count + "|" + transSumAmount + "|" + fee;
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
		ReceiveReconDayJob.prop = prop;
	}
}
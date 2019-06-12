package com.yl.recon.core.util;

import com.yl.recon.core.entity.other.ReconCom;
import com.yl.recon.core.entity.other.account.BaseAccount;
import com.yl.recon.core.entity.other.order.BaseOrder;
import com.yl.recon.core.entity.other.payinterface.BasePayInterface;
import com.yl.utils.date.MyDateUtils;
import com.yl.utils.json.JsonUtils;
import com.yl.utils.math.MyMathUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2018年01月09
 */
public class TxtUtil {
	private static Logger logger = LoggerFactory.getLogger(TxtUtil.class);

	/**
	 * 生成对账文件
	 */
	public static void createOrderTxtFile(String filenameTemp, String filePathTemp, List <BaseOrder> list, ReconCom reconCom) throws IOException {
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		FileOutputStream fos = null;
		PrintWriter pw = null;
		try {
			// 创建文件
			File filePath = new File(filePathTemp);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			File filename = new File(filenameTemp);
			if (filename.exists()) {
				// 存在删除,重新生成
				filename.delete();
			}


			filename.createNewFile();

			// 写文件
			// 文件路径
			File file = new File(filenameTemp);
			// 将文件读入输入流
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			StringBuffer buf = new StringBuffer();
			if (null != list && list.size() > 0) {
				String title = "orderCode|type|payType|amount|fee|cost|finishTime|interfaceCode|interfaceRequestId|systemCode";
				buf.append(title).append(System.lineSeparator());
				for (BaseOrder baseOrder : list) {
					if (null != baseOrder) {
						try {
							String newStr = baseOrder.getOrderCode() + "|" + baseOrder.getType() + "|" + baseOrder.getPayType() + "|" + MyMathUtils.round(baseOrder.getAmount()) + "|" + MyMathUtils.round(baseOrder.getFee()) + "|" + baseOrder.getCost() + "|" + MyDateUtils.DateToStr(baseOrder.getFinishTime()) + "|" + baseOrder.getInterfaceCode() + "|" + baseOrder.getInterfaceRequestId() + "|" + baseOrder.getSystemCode();
							buf.append(newStr).append(System.lineSeparator());
						} catch (Exception e) {
							logger.error("生成对账文件时错误数据[{}]:", JsonUtils.toJSONString(baseOrder));
							continue;
						}
					}
				}
			}
			String foot = "totalNum|totalAmount|totalFee";
			buf.append(foot).append(System.lineSeparator());
			String footStr = reconCom.getTotalNum() + "|" + (null == reconCom.getTotalAmount() ? "0.0" : reconCom.getTotalAmount()) + "|" + (null == reconCom.getTotalFee() ? "0.0" : reconCom.getTotalFee());
			buf.append(footStr).append(System.lineSeparator());
			fos = new FileOutputStream(file);
			pw = new PrintWriter(fos);
			pw.write(buf.toString().toCharArray());
			pw.flush();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
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
	}

	/**
	 * 生成对账文件
	 */
	public static void createPayInterfaceTxtFile(String filenameTemp, String filePathTemp, List <BasePayInterface> list, ReconCom reconCom) throws IOException {
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		FileOutputStream fos = null;
		PrintWriter pw = null;
		try {
			// 创建文件
			File filePath = new File(filePathTemp);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			File filename = new File(filenameTemp);
			if (filename.exists()) {
				// 存在删除,重新生成
				filename.delete();
			}
			filename.createNewFile();

			// 写文件
			// 文件路径
			File file = new File(filenameTemp);
			// 将文件读入输入流
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			StringBuffer buf = new StringBuffer();
			if (null != list && list.size() > 0) {
				String title = "interfaceInfoCode|payType|interfaceOrderId|interfaceRequestId|tradeOrderCode|amount|fee|completeTime|systemCode";
				buf.append(title).append(System.lineSeparator());
				for (BasePayInterface baseOrder : list) {
					if (null != baseOrder) {
						try {
							String newStr = baseOrder.getInterfaceInfoCode() + "|" + baseOrder.getPayType() + "|" + baseOrder.getInterfaceOrderId() + "|" + baseOrder.getInterfaceRequestId() + "|" + baseOrder.getTradeOrderCode() + "|" + MyMathUtils.round(baseOrder.getAmount()) + "|" + MyMathUtils.round(baseOrder.getFee()) + "|" + MyDateUtils.DateToStr(baseOrder.getCompleteTime()) + "|" + baseOrder.getSystemCode();
							buf.append(newStr).append(System.lineSeparator());
						} catch (Exception e) {
							logger.error("生成对账文件时错误数据[{}]:", JsonUtils.toJSONString(baseOrder));
							continue;
						}
					}
				}
			}
			String foot = "totalNum|totalAmount|totalFee";
			buf.append(foot).append(System.lineSeparator());
			String footStr = reconCom.getTotalNum() + "|" + (null == reconCom.getTotalAmount() ? "0.0" : reconCom.getTotalAmount()) + "|" + (null == reconCom.getTotalFee() ? "0.0" : reconCom.getTotalFee());
			buf.append(footStr).append(System.lineSeparator());
			fos = new FileOutputStream(file);
			pw = new PrintWriter(fos);
			pw.write(buf.toString().toCharArray());
			pw.flush();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
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
	}


	/**
	 * 生成对账文件
	 */
	public static void createAccountTxtFile(String filenameTemp, String filePathTemp, List <BaseAccount> list, ReconCom reconCom) throws IOException {
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		FileOutputStream fos = null;
		PrintWriter pw = null;
		try {
			// 创建文件
			File filePath = new File(filePathTemp);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			File filename = new File(filenameTemp);
			if (filename.exists()) {
				// 存在删除,重新生成
				filename.delete();
			}
			filename.createNewFile();

			// 写文件
			// 文件路径
			File file = new File(filenameTemp);
			// 将文件读入输入流
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			StringBuffer buf = new StringBuffer();
			if (null != list && list.size() > 0) {
				String title = "accountNo|orderCode|amount|symbol|transTime|bussinessCode|systemCode";
				buf.append(title).append(System.lineSeparator());
				for (BaseAccount baseOrder : list) {
					if (null != baseOrder) {
						try {
							String newStr = baseOrder.getAccountNo() + "|" + baseOrder.getOrderCode() + "|" + MyMathUtils.round(baseOrder.getAmount()) + "|" + baseOrder.getSymbol() + "|" + MyDateUtils.DateToStr(baseOrder.getTransTime()) + "|" + baseOrder.getBussinessCode() + "|" + baseOrder.getSystemCode();
							buf.append(newStr).append(System.lineSeparator());
						} catch (Exception e) {
							logger.error("生成对账文件时错误数据[{}]:", JsonUtils.toJSONString(baseOrder));
							continue;
						}
					}
				}
			}
			String foot = "totalNum|totalAmount|totalFee";
			buf.append(foot).append(System.lineSeparator());
			String footStr = reconCom.getTotalNum() + "|" + (null == reconCom.getTotalAmount() ? "0.0" : reconCom.getTotalAmount()) + "|" + (null == reconCom.getTotalFee() ? "0.0" : reconCom.getTotalFee());
			buf.append(footStr).append(System.lineSeparator());
			fos = new FileOutputStream(file);
			pw = new PrintWriter(fos);
			pw.write(buf.toString().toCharArray());
			pw.flush();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
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
	}


}

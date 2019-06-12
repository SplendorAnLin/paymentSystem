package com.yl.dpay.core.task;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.csvreader.CsvWriter;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.lefu.commons.utils.lang.DateUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.dpay.core.Utils.RSAUtil;
import com.yl.dpay.core.Utils.Sftp;
import com.yl.dpay.core.entity.Request;
import com.yl.dpay.core.service.RequestService;

/**
 * 代付订单数据上送定时任务
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月16日
 * @version V1.0.0
 */
public class MCFileImportNoticeTrsTask {
	
	@Resource
	RequestService requestService;
	
	private static Logger logger = LoggerFactory.getLogger(MCFileImportNoticeTrsTask.class);
	
	private Date importTime = DateUtils.addDays(new Date(), -1);
	
	private static final String ORDER_NOTIFY = "payecm.MCFileImportNoticeTrs";
	
	private static final String CUST_MANAGE_URL = "http://221.239.93.142:9085/front/Transfer.do";
	
	//RSA加密公钥 银行方提供
    private static final String RSA_PUBLICKEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCJDM3YWjCKKBEFY4ZgOJ5aant3JacgeyxfHTWBKCFkZqK36F3N/gJ21ee1YkiKD/TMBZLZ0XjphPSkQ4kwO47r6TbKqWqYAAqpJ0QCa8cbfjnNb4gkQK20xtVMChfjTtNdnaEEov1lx79OyOitFdUm7hCEH67BJJArdiPoOdC+xwIDAQAB";
    //签名私钥 商户方自己保存
    private static final String SIGN_PRIVATEKEY="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJC/yqNqyvMrwHgp/E5HMrNnAmFq\n" +
            "5VXHui2QA7CI29ihtO31nOfNCTC857fwZztuzmUTUVZke8M40NSLtUv9YjIb1O60EKGlwQzglxY8\n" +
            "ggvCqMOQx+G897Rlg1D/kcudrcbL/iLMdVpOibvLi29/H4CMZU5qJ4mL2xUx8n5gKStjAgMBAAEC\n" +
            "gYEAh7T4g5uE6NT1HvOmE7GWrDIAPlsc5f5Z44uomeLF0uVQnuRuFbjaS/JfgMkHz+XD5WlmEYwl\n" +
            "qFLlHtBYygSmAQxTdcKdsB74bqekeQlEsDelEVuD6kJRYBUMLEGIt+b/j6LnM1jFmYrGNqVCpWpB\n" +
            "SI06F6mGivEJ+GBKDJOFWakCQQDkybOzKLmeb4VHUVJRI+ZIHfgNzcI6ON4fHksykQrYTXhgzAUA\n" +
            "YzSXIFYxl4rGkeq435qdClV98pjA+jH1jhDVAkEAofc39jI6lISCByEOUi44wnqogsrmvd+N+PsN\n" +
            "M59/Y0u0ZjnRs3OUKeYX3I1qnWKDXvEjpKgoOBovpNpld40nVwJAJi8r2MkBQdonCmIeNQCi3IJz\n" +
            "9gnTUthO6i6qKkRe5P75Cl7Cru/fxSFWgWxjcwTDght/uJoS7rRgnkSjtfICCQJBAJm7zHR1TME3\n" +
            "3SvjJnK+yMVgI56x9L54+YtA0FEVrZaUfxEhBHiu1g3HBxMjb/UfUs7FWC2sJzDJOjvhyLnnU1UC\n" +
            "QDWq8mIqNYW6KpRdl0f2CEMXLsSV9blOsZlxne+lzprV5ZoxCo1Y9kUiqul35xrqZo3DSo+fjf1A\n" +
            "3NCN8DZUaAI=";
	
	@SuppressWarnings("rawtypes")
	public void execute() throws Exception {
		Map<String, Object> params = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		params.put("starTime", DateUtils.getMinTime(importTime));
		params.put("starEnd", DateUtils.getMaxTime(importTime));
		List<Request> requests = requestService.findByCreateTime(params);
		String fileName = "RQ_PT00001_batchpay_" + sdf.format(importTime) + "_0001.csv";
		String outPutPath = "E:\\";
		CsvWriter csvWriter = new CsvWriter(outPutPath + fileName,',', Charset.forName("GBK"));
		String[] headers = {"文件名","平台号","商户号","对方账号","联行号","对方账号行名","对方账号户名","金额"};
		csvWriter.writeRecord(headers);
		if (requests != null && requests.size() > 0) {
			int size = requests.size();
			for (int i = 0; i < size; i++) {
				Request request = requests.get(0);
				String[] content = {fileName,"PT00001",request.getOwnerId(),request.getAccountNo(),request.getBankCode(),request.getBankName(),"未知",String.valueOf(request.getAmount())};
				csvWriter.writeRecord(content);
			}
			csvWriter.close();
		}
		Sftp sf = new Sftp();
		String host = "192.168.0.5";
		int port = 22;
		String username = "root";
		String password = "zk159256";
		String directory = "/home/attachment/MCF/";
		String uploadFile = outPutPath + fileName;
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
		sftp.quit();
		
		Map<String, String> param = new LinkedHashMap<>();
		param.put("_TransactionId", ORDER_NOTIFY);
		param.put("_MCHTimestamp", String.valueOf(System.currentTimeMillis()));
		param.put("_MCHJnlNo", "CI" + System.currentTimeMillis());
		param.put("NoticeType", "2");
		param.put("FileName", fileName);
		
		String dataStr = JsonUtils.toJsonString(param);
		
		System.out.println("请求报文明文");
		System.out.println(dataStr);
		String sign = RSAUtil.sign(dataStr, SIGN_PRIVATEKEY);
		String plainData = RSAUtil.RSAencryptByPublicKey(dataStr.getBytes(), RSA_PUBLICKEY);
		String data = "PlainData=" + URLEncoder.encode(plainData, "utf-8") + "&Signatures=" + URLEncoder.encode(sign, "utf-8");
		System.out.println("请求报文");
		System.out.println(data);
		System.out.println("响应报文");
		System.out.println(sendReq("", CUST_MANAGE_URL+"?"+data));
	}
	
	public static String sendReq(String data, String url) throws Exception {

        java.net.HttpURLConnection urlConnection = null;
        BufferedOutputStream out;
        StringBuffer respContent = new StringBuffer();

        java.net.URL aURL = new java.net.URL(url);
        urlConnection = (java.net.HttpURLConnection) aURL.openConnection();

        urlConnection.setRequestMethod("POST");

        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        urlConnection.setUseCaches(false);

        urlConnection.setRequestProperty("Connection", "close");
        urlConnection.setRequestProperty("Content-Length", String.valueOf(data.getBytes("UTF-8").length));
        urlConnection.setRequestProperty("Content-Type", "text/html");

        urlConnection.setConnectTimeout(60000);
        urlConnection.setReadTimeout(60000);

        out = new BufferedOutputStream(urlConnection.getOutputStream());

        out.write(data.getBytes("UTF-8"));
        out.flush();
        out.close();
        int responseCode = urlConnection.getResponseCode();

        if (responseCode != 200) {
            throw new Exception("请求失败");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
        String line;

        while ((line = reader.readLine()) != null) {
            respContent.append(line);
        }

        urlConnection.disconnect();
        return respContent.toString();
    }
}
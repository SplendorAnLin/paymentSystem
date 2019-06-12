package com.yl.pay.pos.http;

import com.alibaba.fastjson.JSONObject;
import com.pay.common.util.SequenceUtils;
import com.yl.pay.pos.Constant;
import com.yl.pay.pos.bean.UnionPayBean;
import com.yl.pay.pos.entity.SignPic;
import com.yl.pay.pos.service.PosService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


/**
 * Servlet implementation class HandleHttpRequest
 */
public class HandleHttpRequest extends HttpServlet {
	
	private static final Log log = LogFactory.getLog(HandleHttpRequest.class);
	
	private static final long serialVersionUID = 1L;
	
	private String contentType = "application/x-msdownload";
	private String enc = "utf-8";
	private String fileRoot = "";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleHttpRequest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String paraType = request.getParameter("paraType");
		Map<String,String> result = new HashMap<String,String>();
		if("P001".equals(paraType)){
			try {
				String posSn = request.getParameter("posSn");
				String posType=request.getParameter("posType");
				ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());  
				PosService posService = (PosService)context.getBean("posService");  
				Map<String,String> res = posService.handleP001(posSn,posType);
				if("00".equals(res.get("code"))){
					result.put("posCati", res.get("posCati"));
				}
				result.put("code", res.get("code"));
				result.put("message", Constant.resCodes.get(res.get("code")));
			} catch (Exception e) {
				log.info(e.getMessage());
				result.put("message", "POS入库操作失败");
			}
		}else if("T001".equals(paraType)){
			try {
				ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());  
				PosService posService = (PosService)context.getBean("posService");  
				String posSn = request.getParameter("posSn");
				UnionPayBean unionPayBean = posService.handleT001(posSn);
				result.put("code", unionPayBean.getResponseCode());
				result.put("message", Constant.resCodes.get(unionPayBean.getResponseCode()));
				result.put("mkey", unionPayBean.getSwitchingData());
			} catch (Exception e) {
				log.info(e.getMessage());
				result.put("message", "签到操作失败");
			}
		}else if("T002".equals(paraType)){
			try {
				ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());  
				PosService posService = (PosService)context.getBean("posService");  
				String track2 = request.getParameter("track2").replace("D", "=");
				String amount = request.getParameter("amount");
				String posEntryModeCode = request.getParameter("posEntryModeCode");
				String posSn = request.getParameter("posSn");
				String pin = request.getParameter("pin");
				String dateExpiration=request.getParameter("dateExpiration");
				String icSystemRelated=request.getParameter("icSystemRelated");
				String cardSequenceNumber=request.getParameter("cardSequenceNumber");
				UnionPayBean unionPayBean = new UnionPayBean();
				unionPayBean.setTrack2(track2);
				unionPayBean.setAmount(amount);
				unionPayBean.setPosEntryModeCode(posEntryModeCode);
				unionPayBean.setDateExpiration(dateExpiration);
				if("021".equals(posEntryModeCode)||"051".equals(posEntryModeCode)){
					unionPayBean.setPosPinCaptureCode("12");
					unionPayBean.setPin(pin);
					unionPayBean.setSecurityControlInfo("2000000000000000");
				}else{
					unionPayBean.setSecurityControlInfo("0000000000000000");	
				}
				if("051".equals(posEntryModeCode)||"052".equals(posEntryModeCode)){
					unionPayBean.setCardSequenceNumber(cardSequenceNumber);
					unionPayBean.setICSystemRelated(icSystemRelated);
				}
				unionPayBean = posService.handleT002(unionPayBean,posSn);
				result.put("code", unionPayBean.getResponseCode());
				result.put("message", Constant.resCodes.get(unionPayBean.getResponseCode()));
			} catch (Exception e) {
				log.info(e.getMessage());
				result.put("message", "交易操作失败");
			}
		}else if("T003".equals(paraType)){
			try {
				ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());  
				PosService posService = (PosService)context.getBean("posService");  
				String track2 = request.getParameter("track2").replace("D", "=");
				String amount = request.getParameter("amount");
				String posEntryModeCode = request.getParameter("posEntryModeCode");
				String posSn = request.getParameter("posSn");
				String pin = request.getParameter("pin");
				String dateExpiration=request.getParameter("dateExpiration");
				String icSystemRelated=request.getParameter("icSystemRelated");
				String cardSequenceNumber=request.getParameter("cardSequenceNumber");
				UnionPayBean unionPayBean = new UnionPayBean();
				unionPayBean.setTrack2(track2);
				unionPayBean.setAmount(amount);
				unionPayBean.setPosEntryModeCode(posEntryModeCode);
				unionPayBean.setDateExpiration(dateExpiration);
				if("021".equals(posEntryModeCode)||"051".equals(posEntryModeCode)){
					unionPayBean.setPosPinCaptureCode("12");
					unionPayBean.setPin(pin);
					unionPayBean.setSecurityControlInfo("2000000000000000");
				}else{
					unionPayBean.setSecurityControlInfo("0000000000000000");	
				}
				if("051".equals(posEntryModeCode)||"052".equals(posEntryModeCode)){
					unionPayBean.setCardSequenceNumber(cardSequenceNumber);
					unionPayBean.setICSystemRelated(icSystemRelated);
				}
				unionPayBean = posService.handleT003(unionPayBean,posSn);
				result.put("code", unionPayBean.getResponseCode());
				result.put("amount", unionPayBean.getAdditionalAmount());
				result.put("message", Constant.resCodes.get(unionPayBean.getResponseCode()));
			} catch (Exception e) {
				log.info(e.getMessage());
				result.put("message", "查询余额操作失败");
			}
		}else if("T004".equals(paraType)){
			String externalId = request.getParameter("externalId");
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());  
			PosService posService = (PosService)context.getBean("posService");  
			SignPic signPic = posService.handleT004(externalId);
			File file = new File(signPic.getSignFilePath());// 读入文件  
			if (file.exists()) {
	            String filename = URLEncoder.encode(file.getName(), enc);
	            response.reset();
	            response.setContentType(contentType);
	            response.addHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
	            int fileLength = (int) file.length();
	            response.setContentLength(fileLength);
	            /*如果文件长度大于0*/
	            if (fileLength != 0) {
	                /*创建输入流*/
	                InputStream inStream = new FileInputStream(file);
	                byte[] buf = new byte[4096];
	                /*创建输出流*/
	                ServletOutputStream servletOS = response.getOutputStream();
	                try {
						int readLength;
						while (((readLength = inStream.read(buf)) != -1)) {
						    servletOS.write(buf, 0, readLength);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						 inStream.close();
			             servletOS.flush();
			             servletOS.close();
					}
	               
	            }
			}else{
				log.info("externalId:"+externalId+"文件不存在");
			}
			return;
			
		}
		
		response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(JSONObject.toJSONString(result));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html;charset=UTF-8");
		out.print("hello world!你好啊");
	}

	/**
     * 生成pos终端号
     * @return
     * @author 张钊
     */
	public String createPosCati(long poscatiSeq){
		return  SequenceUtils.createSequence(poscatiSeq , new int[] {8,0,8,5,9,3,8,0}, new int[] {4,5}, new int[] {6,8}, new int[] {7,8});
	}
	
}

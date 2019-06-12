package com.yl.agent.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.lefu.commons.utils.lang.StringUtils;
import com.pay.common.util.DateUtil;
import com.pay.common.util.StringUtil;
import com.yl.agent.Constant;
import com.yl.agent.entity.Authorization;
import com.yl.agent.utils.FileUtil;
import com.yl.agent.utils.ImgUtil;
import com.yl.boss.api.bean.Ad;
import com.yl.boss.api.bean.Agent;
import com.yl.boss.api.interfaces.AdInterface;
import com.yl.boss.api.interfaces.AgentInterface;
import com.yl.boss.api.interfaces.ShareProfitInterface;
import com.yl.dpay.hessian.service.QueryFacade;

import net.mlw.vlh.DefaultListBackedValueList;
import net.mlw.vlh.ValueListInfo;


/**
 * 广告控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月24日
 * @version V1.0.0
 */
public class AdAction extends Struts2ActionSupport {
	private static final long serialVersionUID = -3401548741065092718L;
	
	private Ad ad;
	private String id;
	private String agentNo;
	private String oem;
	private String msg;
	private File imageContent;
	private AdInterface adInterface;
	private AgentInterface agentInterface;
	private InputStream fileOutput = null;// 图片
	private ShareProfitInterface shareProfitInterface;
	
	private static Properties prop = new Properties();
	
	static {
		try {
			prop.load(
					new InputStreamReader(AdAction.class.getClassLoader().getResourceAsStream("system.properties")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String adQuery(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		Map<String, String[]> requestMap = getHttpRequest().getParameterMap();
		Map<String, Object> params = retrieveParams(requestMap);
		params.put("agent_no", auth.getAgentNo());
		try {
			String queryId = "adQuery";
			Map<String, Object> returnMap = shareProfitInterface.query(queryId, params);
			getHttpRequest().setAttribute(queryId,
					new DefaultListBackedValueList((List) returnMap.get(QueryFacade.VALUELIST),
							(ValueListInfo) returnMap.get(QueryFacade.VALUELIST_INFO)));
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
		return SUCCESS;
	}
	
	public String adQueryById(){
		ad=adInterface.fingAdById(Integer.parseInt(id));
		return SUCCESS;
	}
	/**
	 * 根据oem查找匹配信息
	 * @return
	 */
	public String findAgentByOem(){
		msg=adInterface.findAgentByOem(oem);
		return SUCCESS;
	}
	/**
	 * 根据代理商编号查询匹配信息
	 * @return
	 */
	public String findAgentByAgentNo(){
		msg=adInterface.findAgentByAgentNo(oem);
		return SUCCESS;
	}
	
	/**
	 * 广告新增
	 * @return
	 */
	public String adAdd(){
		try {
			Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
			Agent agent=agentInterface.getAgent(auth.getAgentNo());
			//处理广告图片
			String dateString = DateUtil.formatDate(new Date(), "yyyyMMddHHMMss");
			String proPath = prop.getProperty("ad.imgPath");
			String filePath = proPath + dateString + "/";
			
			msg = ImgUtil.checkImg(imageContent);
			if(StringUtil.isNull(msg)){
				String imgPath = "bc-"+ Long.toHexString(System.nanoTime()) + ".jpg";
				FileUtil.copy(imageContent, "/home"+filePath, imgPath);
				ad.setImageUrl(filePath + imgPath);
			} else {
				throw new Exception(msg);
			}
			
			//得到long类型当前时间
			long l = System.currentTimeMillis();
			//new日期对象
			Date date = new Date(l);
			//转换提日期输出格式
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			ad.setCreateTime(Timestamp.valueOf(dateFormat.format(date)));
			ad.setOem(agent.getShortName());
			//新增操作
			adInterface.create(ad);
			return SUCCESS;
		} catch (Exception e) {
			logger.error("广告异常{}",e);
			return "fail";
		}
	}
	/**
	 * 广告修改
	 * @return
	 */
	public String adUpdate(){
		try {
			Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
			Agent agent=agentInterface.getAgent(auth.getAgentNo());
			//处理广告图片
			if(imageContent != null){
				String msg = null;
				String dateString = DateUtil.formatDate(new Date(), "yyyyMMddHHMMss");
				String proPath = prop.getProperty("ad.imgPath");
				String filePath = proPath + dateString + "/";
				
				msg = ImgUtil.checkImg(imageContent);
				if(StringUtil.isNull(msg)){
					String imgPath = "bc-" + Long.toHexString(System.nanoTime()) + ".jpg";
					FileUtil.copy(imageContent, "/home"+filePath, imgPath);
					ad.setImageUrl(filePath + imgPath);
				} else {
					throw new Exception(msg);
				}
			}
			ad.setOem(agent.getShortName());
			//修改操作
			Date dates=new Date();
			Timestamp time=new Timestamp(dates.getTime());
			ad.setUpdateTime(time);
			adInterface.updateAd(ad);
			return SUCCESS;
		} catch (Exception e) {
			logger.error("广告异常{}",e);
			return "fail";
		}
	}
	
	/**
	 * 获取图片流
	 * @return
	 */
	public String findAdDocumentImg(){
		if(ad != null && ad.getId() != 0){
			if(ad.getImageUrl() != null && !ad.getImageUrl().equals("")){
				fileOutput = ImgUtil.findDocumentImgByName("/home"+ad.getImageUrl());
			}
		}
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
	public Ad getAd() {
		return ad;
	}
	public String getAgentNo() {
		return agentNo;
	}
	public String getOem() {
		return oem;
	}
	public String getMsg() {
		return msg;
	}
	public void setAd(Ad ad) {
		this.ad = ad;
	}
	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}
	public void setOem(String oem) {
		this.oem = oem;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public AdInterface getAdInterface() {
		return adInterface;
	}
	public void setAdInterface(AdInterface adInterface) {
		this.adInterface = adInterface;
	}
	public InputStream getFileOutput() {
		return fileOutput;
	}
	public void setFileOutput(InputStream fileOutput) {
		this.fileOutput = fileOutput;
	}
	public File getImageContent() {
		return imageContent;
	}
	public AgentInterface getAgentInterface() {
		return agentInterface;
	}
	public void setImageContent(File imageContent) {
		this.imageContent = imageContent;
	}
	public void setAgentInterface(AgentInterface agentInterface) {
		this.agentInterface = agentInterface;
	}

	public ShareProfitInterface getShareProfitInterface() {
		return shareProfitInterface;
	}

	public void setShareProfitInterface(ShareProfitInterface shareProfitInterface) {
		this.shareProfitInterface = shareProfitInterface;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}

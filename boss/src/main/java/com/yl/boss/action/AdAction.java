package com.yl.boss.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.pay.common.util.DateUtil;
import com.pay.common.util.StringUtil;
import com.yl.boss.entity.Ad;
import com.yl.boss.entity.Agent;
import com.yl.boss.service.AdService;
import com.yl.boss.service.AgentService;
import com.yl.boss.utils.FileUtil;
import com.yl.boss.utils.ImgUtil;

import net.mlw.vlh.web.mvc.ValueListHandlerHelper;


/**
 * 广告控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月24日
 * @version V1.0.0
 */
public class AdAction extends Struts2ActionSupport {
	private static final long serialVersionUID = -3401548741065092718L;
	@Resource
	private AdService adService;
	private ValueListHandlerHelper valueListHelper;	
	private Ad ad;
	private List<Ad> adList;
	private File imageContent;
	private InputStream fileOutput = null;// 图片
	private AgentService agentService;
	private String agentNo;
	private String oem;
	private String msg;
	
	private static Properties prop = new Properties();
	
	static {
		try {
			prop.load(
					new InputStreamReader(AdAction.class.getClassLoader().getResourceAsStream("system.properties")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String openAdd(){
		return SUCCESS;
	}
	/**
	 * 根据oem查找匹配信息
	 * @return
	 */
	public String findAgentByOem(){
		Agent agent=agentService.findByShortName(oem);
		if (agent!=null&&agent.getAgentLevel()==1) {
			Map<String, String> info=new HashMap<>();
			info.put("oem", agent.getShortName());
			info.put("agentNo", agent.getAgentNo());
			info.put("agentName", agent.getFullName());
			msg=JsonUtils.toJsonString(info);
		}else {
			msg="false";
		}
		return SUCCESS;
	}
	/**
	 * 根据代理商编号查询匹配信息
	 * @return
	 */
	public String findAgentByAgentNo(){
		Agent agent=agentService.findByNo(agentNo);
		if (agent!=null&&agent.getAgentLevel()==1) {
			Map<String, String> info=new HashMap<>();
			info.put("oem", agent.getShortName());
			info.put("agentNo", agent.getAgentNo());
			info.put("agentName", agent.getFullName());
			msg=JsonUtils.toJsonString(info);
		}else {
			msg="false";
		}
		return SUCCESS;
	}
	
	/**
	 * 广告新增
	 * @return
	 */
	public String adAdd(){
		try {
			//处理广告图片
			String msg = null;
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
			if (!StringUtils.notBlank(ad.getOem())) {
				ad.setOem("ylzf");
			}
			//新增操作
			adService.save(ad);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}
	
	//Valuelist查询
//	public String adQueryMap(){
//		try {
//			HttpServletRequest request = ServletActionContext. getRequest();
//			Map<String, Object> params = getParameterMap(request.getParameterMap());
//			//获取当前登陆用户信息值
//			Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
//			if(auth.getParenId() != null && !auth.getParenId().equals("")){
//				params.put("admin_id", auth.getParenId());
//			} else {
//				params.put("admin_id", auth.getAdminId());
//			}
//			
//			ValueListInfo info = new ValueListInfo(params);
//			ValueList valueList = valueListHelper.getValueList("adQuery", info);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return SUCCESS;
//	}
	
	/**
	 * 广告修改
	 * @return
	 */
	public String adUpdate(){
		try {
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
			if (!StringUtils.notBlank(ad.getOem())) {
				ad.setOem("ylzf");
			}
			//修改操作
			Date dates=new Date();
			Timestamp time=new Timestamp(dates.getTime());
			ad.setUpdateTime(time);
			adService.update(ad);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}

	
	/**
	 * 根据编号查询
	 * @return
	 */
	public String adQueryById(){
		try {
			ad = adService.queryById(ad.getId(),null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
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
	 * 从request中获得参数Map，并返回可读的Map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map getParameterMap(Map m) {
		// 参数Map
		Map properties = m;
		// 返回值Map
		Map returnMap = new HashMap();
		Iterator entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if(null == valueObj){
				value = "";
			}else if(valueObj instanceof String[]){
				String[] values = (String[])valueObj;
				for(int i=0;i<values.length;i++){
					value = values[i] + ",";
				}
				value = value.substring(0, value.length()-1);
			}else{
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}
	public AdService getAdService() {
		return adService;
	}
	public void setAdService(AdService adService) {
		this.adService = adService;
	}
	public ValueListHandlerHelper getValueListHelper() {
		return valueListHelper;
	}
	public void setValueListHelper(ValueListHandlerHelper valueListHelper) {
		this.valueListHelper = valueListHelper;
	}
	public Ad getAd() {
		return ad;
	}
	public void setAd(Ad ad) {
		this.ad = ad;
	}
	public List<Ad> getAdList() {
		return adList;
	}
	public void setAdList(List<Ad> adList) {
		this.adList = adList;
	}
	public File getImageContent() {
		return imageContent;
	}
	public void setImageContent(File imageContent) {
		this.imageContent = imageContent;
	}
	public InputStream getFileOutput() {
		return fileOutput;
	}
	public void setFileOutput(InputStream fileOutput) {
		this.fileOutput = fileOutput;
	}
	public static Properties getProp() {
		return prop;
	}
	public static void setProp(Properties prop) {
		AdAction.prop = prop;
	}
	public AgentService getAgentService() {
		return agentService;
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
	public void setAgentService(AgentService agentService) {
		this.agentService = agentService;
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
}

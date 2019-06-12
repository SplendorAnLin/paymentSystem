package com.yl.boss.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.commons.cache.util.CacheUtils;
import com.pay.common.util.DateUtil;
import com.yl.boss.Constant;
import com.yl.boss.entity.AppVersion;
import com.yl.boss.entity.Authorization;
import com.yl.boss.enums.Status;
import com.yl.boss.service.AppVersionService;
import com.yl.boss.utils.AppUtil;
import com.yl.boss.utils.FileUtil;
import com.zk.apkUtil.entity.ApkInfo;

public class ClientAction extends Struts2ActionSupport{
	
	private static final Logger logger = LoggerFactory.getLogger(ClientAction.class);
	
	private static final long serialVersionUID = -2748577728201312782L;	
	private static Properties prop = new Properties();
	static {
		try {
			prop.load(new InputStreamReader(ClientAction.class.getClassLoader().getResourceAsStream("system.properties")));
		} catch (IOException e) {
			logger.error("ClientAction load Properties error:", e);
		}
	}
	private AppVersionService appVersionService;
	private Map<String, String> info;//主版本单个信息
	private Map<String, Object> all;//主版本全部信息
	private AppVersion appVersion;//oem版本信息
	private File file;
	private String fileName;
	private String msg;
	private Long id;
	
	public String exist() {
		boolean isExist=appVersionService.exist(appVersion.getType(), appVersion.getAgentNo());
		if (isExist) {
			msg="true";
		}else {
			msg="false";
		}
		return SUCCESS;
	}
	
	public String findById(){
		if (id!=null) {
			 appVersion=appVersionService.findById(id);
		}
		return SUCCESS;
	}
	
	public String updateOemAndroid(){
		if (null != file&&!fileName.isEmpty()) {
			String filePath = prop.getProperty("app.filePath");
			AppVersion appVersionOld=null;
			if (appVersion.getId()!=null) {
				 appVersionOld=appVersionService.findById(appVersion.getId());
			}
			try {
				if (appVersionOld!=null) {
					//上个版本文件备份
					File file = new File(filePath+appVersionOld.getShortApp()+".apk");
					if (file.exists()) {
						FileUtil.copy(file, filePath, appVersionOld.getShortApp()+appVersionOld.getVersion()+"-"+DateUtil.formatDate(new Date(), "yyyyMMddHH")+".apk");
						FileUtil.delete(filePath+appVersionOld.getShortApp()+".apk");
					}else {
						logger.info("apk文件不存在");
					}
				}
				FileUtil.copy(file, filePath, appVersion.getShortApp()+".apk");
				ApkInfo apkInfo=AppUtil.getAppInfo(prop.getProperty("app.aapt.path"), filePath+appVersion.getShortApp()+".apk");
				appVersion.setVersion(apkInfo.getVersionName());
				appVersion.setRemark(apkInfo.getVersionCode());
				
				msg = "true";
			} catch (IOException e) {
				msg = "false";
				throw new RuntimeException("apk文件上传失败!", e);
			}catch(Exception e){
				msg = "false";
				throw new RuntimeException("apk信息错误!", e);
			} 
			appVersion.setType("android");
			Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
			if (appVersion.getId()!=null) {
				appVersion.setUrl( prop.getProperty("app.downPath")+appVersionOld.getShortApp()+".apk");
				appVersionService.update(appVersion, auth.getUsername());
			}else {
				appVersion.setUrl( prop.getProperty("app.downPath")+appVersion.getShortApp()+".apk");
				appVersion.setStatus(Status.TRUE);
				appVersionService.create(appVersion, auth.getUsername());
			}
		}
		return SUCCESS;
	}
	
	public String updateOemIos(){
		try {
			
			Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
			appVersion.setType("ios");
			if (appVersion.getId()!=null) {
				appVersionService.update(appVersion, auth.getUsername());
			}else {
				appVersion.setStatus(Status.TRUE);
				appVersionService.create(appVersion, auth.getUsername());
			}
			msg = "true";
		} catch (Exception e) {
			logger.error("updateOemIos failed!", e);
			msg = "false";
		}
		return SUCCESS;
	}
	
	public String updateOemIosStore(){
		try {
			
			Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
			appVersion.setType("iosStore");
			appVersion.setShortApp(appVersion.getShortApp() + "Store");
			if (appVersion.getId()!=null) {
				appVersionService.update(appVersion, auth.getUsername());
			}else {
				appVersion.setStatus(Status.TRUE);
				appVersionService.create(appVersion, auth.getUsername());
			}
			
			msg = "true";
		} catch (Exception e) {
			logger.error("updateOemIos failed!", e);
			msg = "false";
		}
		return SUCCESS;
	}
	
	public String upIosMain(){
		info.put("version", "1.0.0");
		CacheUtils.set(Constant.DICTS_RESOURCE_DB,Constant.IOS_VERSION, info,true);
		return SUCCESS;
	}
	
	public String upInsStoreMain(){
		info.put("version", "1.0.0");
		CacheUtils.set(Constant.DICTS_RESOURCE_DB,Constant.IOS_STORE_VERSION, info,true);
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String upAndroidMain(){
		if (null != file&&!fileName.isEmpty()) {
			Map<String, String> android=null;
			String filePath = prop.getProperty("app.filePath");
			try {
				android=CacheUtils.get(Constant.DICTS_RESOURCE_DB,Constant.ANDROID_VERSION,Map.class, true);
				if (android!=null) {
					//上个版本文件备份
					File file = new File(filePath+"ylzf.apk");
					if (file.exists()) {
						FileUtil.copy(file, filePath, "ylzf-"+android.get("version").toString()+"-"+DateUtil.formatDate(new Date(), "yyyyMMddHH")+".apk");
						FileUtil.delete(filePath+"ylzf.apk");
					}else {
						logger.info("apk文件不存在");
					}
				}
			} catch (Exception e) {
				logger.info("获取上个版本错误");
			}
			try {
				FileUtil.copy(file, filePath, "ylzf.apk");
				ApkInfo apkInfo=AppUtil.getAppInfo(prop.getProperty("app.aapt.path"), filePath+"ylzf.apk");
				info.put("versionCode", apkInfo.getVersionCode());
				info.put("version", apkInfo.getVersionName());
			}catch (IOException e) {
				logger.error("apk文件上传失败!异常:{}",e);
				return ERROR;
			}catch(Exception e){
				logger.error("apk信息错误!");
				return ERROR;
			} 
			info.put("url", prop.getProperty("app.downPath")+"ylzf.apk");
			//当前版本处理
			CacheUtils.set(Constant.DICTS_RESOURCE_DB,Constant.ANDROID_VERSION, info,true);
		}
		return SUCCESS;
	}
	
	
	public String findAppVersionHist(){
		
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String findAllVersion() {//获取主版本信息
		Map<String, String> ios=CacheUtils.get(Constant.DICTS_RESOURCE_DB,Constant.IOS_VERSION,Map.class, true);
		Map<String, String> android=CacheUtils.get(Constant.DICTS_RESOURCE_DB,Constant.ANDROID_VERSION,Map.class, true);
		all=new HashMap<>();
		all.put("ios", ios);
		all.put("android", android);
		return SUCCESS;
	}

	public Map<String, String> getInfo() {
		return info;
	}

	public Map<String, Object> getAll() {
		return all;
	}

	public void setInfo(Map<String, String> info) {
		this.info = info;
	}

	public void setAll(Map<String, Object> all) {
		this.all = all;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public AppVersionService getAppVersionService() {
		return appVersionService;
	}

	public void setAppVersionService(AppVersionService appVersionService) {
		this.appVersionService = appVersionService;
	}

	public AppVersion getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(AppVersion appVersion) {
		this.appVersion = appVersion;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}

package com.yl.boss.utils;

import com.zk.apkUtil.entity.ApkInfo;
import com.zk.apkUtil.utils.ApkUtil;

public class AppUtil {
	public static ApkInfo getAppInfo(String aaptPath,String apkPath) throws Exception{
		ApkUtil apkUtil=new ApkUtil(aaptPath);
		return apkUtil.getApkInfo(apkPath);
	}
}

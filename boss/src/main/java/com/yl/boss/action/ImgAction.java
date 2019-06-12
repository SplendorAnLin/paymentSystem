package com.yl.boss.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.yl.boss.utils.ImgUtil;

/**
 * 图片控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月28日
 * @version V1.0.0
 */
public class ImgAction extends Struts2ActionSupport{
	private static final long serialVersionUID = 2503779095503924863L;
	private InputStream fileOutput;
	private String imgUrl;
	public String openImg(){
		try {
			byte[] bytes=ImgUtil.getPathBytes(imgUrl);
			if (bytes.length>0) {
				fileOutput=new ByteArrayInputStream(bytes);
			}else {
				getHttpResponse().getWriter().println("图片不存在!");
			}
		} catch (Exception e) {
			return null;
		}
		return SUCCESS;
	}
	public InputStream getFileOutput() {
		return fileOutput;
	}
	public void setFileOutput(InputStream fileOutput) {
		this.fileOutput = fileOutput;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
}

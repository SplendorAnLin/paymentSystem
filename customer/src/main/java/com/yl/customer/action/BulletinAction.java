package com.yl.customer.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.api.bean.BulletinBean;
import com.yl.boss.api.enums.BulletinSysType;
import com.yl.boss.api.interfaces.BulletinInterface;
import com.yl.boss.api.utils.Page;
import com.yl.customer.Constant;
import com.yl.customer.entity.Authorization;
import com.yl.customer.entity.Bulletin;
import com.yl.customer.enums.BulletinType;
import com.yl.customer.service.BulletinService;

/**
 * 公告控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月2日
 * @version V1.0.0
 */
public class BulletinAction extends Struts2ActionSupport{
	
	private static final long serialVersionUID = -3201238991638228922L;
	private BulletinService bulletinService;
	//创建一个BOSS的bulletinInterface接口对象
	private BulletinInterface bulletinInterface;
	private List<BulletinBean> bulletinBeanList;
	private BulletinBean bulletin;
	private Page page;
	
	
	//根据条件查询所有公告信息，并返回集合
	public String bulletinShow(){
		if (page == null) {
			page=new Page(0);
		}
		HttpServletRequest request = this.getHttpRequest();
		int currentPage = request.getParameter("currentPage") == null ? 1 : Integer.parseInt(request.getParameter("currentPage"));
		if (currentPage > 1) {
			page.setCurrentPage(currentPage);
		}
		page.setTotalResult(bulletinInterface.findBulletinCount(JsonUtils.toJsonToObject(BulletinType.TRUE, com.yl.boss.api.enums.BulletinType.class), BulletinSysType.CUSTOMER, new Date()));
		bulletinBeanList=bulletinInterface.bulletinBySysCode(JsonUtils.toJsonToObject(BulletinType.TRUE, com.yl.boss.api.enums.BulletinType.class), BulletinSysType.CUSTOMER, new Date(), page);
		return SUCCESS;
	}
	
	public String bulletinDetail(){
		bulletin = bulletinService.findById(bulletin.getId());
		return SUCCESS;
	}
	

	public List<BulletinBean> getBulletinBeanList() {
		return bulletinBeanList;
	}

	public void setBulletinBeanList(List<BulletinBean> bulletinBeanList) {
		this.bulletinBeanList = bulletinBeanList;
	}

	public BulletinService getBulletinService() {
		return bulletinService;
	}

	public void setBulletinService(BulletinService bulletinService) {
		this.bulletinService = bulletinService;
	}

	public BulletinBean getBulletin() {
		return bulletin;
	}

	public void setBulletin(BulletinBean bulletin) {
		this.bulletin = bulletin;
	}
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public BulletinInterface getBulletinInterface() {
		return bulletinInterface;
	}

	public void setBulletinInterface(BulletinInterface bulletinInterface) {
		this.bulletinInterface = bulletinInterface;
	}
}

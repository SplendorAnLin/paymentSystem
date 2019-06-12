package com.yl.agent.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.agent.service.BulletinService;
import com.yl.boss.api.utils.Page;
import com.yl.boss.api.bean.BulletinBean;
import com.yl.boss.api.enums.BulletinSysType;
import com.yl.boss.api.enums.BulletinType;

/**
 * 广告控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月26日
 * @version V1.0.0
 */
public class BulletinAction extends Struts2ActionSupport{
	
	private static final long serialVersionUID = -3201238991638228922L;
	private BulletinService bulletinService;
	private List<BulletinBean> bulletinAgent;
	private BulletinBean bulletin;
	private Page page;

//	/**
//	 * 首页公告展示
//	 * @return
//	 */
//	public String bulletinShow() {
//		bulletinAgent=bulletinService.bulletinBySysCode(BulletinType.TRUE, BulletinSysType.AGENT, new Date());
//		return SUCCESS;
//	}
//	
	
	/**
	 * 首页信息
	 * @return
	 */
	public String bulletinList() {
		if (page == null) {
			page=new Page(0);
		}
		HttpServletRequest request = this.getHttpRequest();
		int currentPage = request.getParameter("currentPage") == null ? 1 : Integer.parseInt(request.getParameter("currentPage"));
		if (currentPage > 1) {
			page.setCurrentPage(currentPage);
		}
		page.setTotalResult(bulletinService.findBulletinCount(BulletinType.TRUE, BulletinSysType.AGENT, new Date()));
		bulletinAgent=bulletinService.bulletinBySysCode(BulletinType.TRUE, BulletinSysType.AGENT, new Date(),page);
		return SUCCESS;
	}
	
	
	
	public String bulletinDetail() {
		bulletin = bulletinService.findById(bulletin.getId());
		return SUCCESS;
	}
	
	
	public List<BulletinBean> getBulletinAgent() {
		return bulletinAgent;
	}
	public void setBulletinAgent(List<BulletinBean> bulletinAgent) {
		this.bulletinAgent = bulletinAgent;
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

	
}

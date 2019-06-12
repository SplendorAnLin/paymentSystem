package com.yl.boss.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lefu.commons.utils.Page;
import com.yl.boss.Constant;
import com.yl.boss.entity.AgentSettle;
import com.yl.boss.entity.AgentSettleHistory;
import com.yl.boss.entity.Authorization;
import com.yl.boss.service.AgentSettleService;

/**
 * 服务商结算卡控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月28日
 * @version V1.0.0
 */
public class AgentSettleAction extends Struts2ActionSupport {

	private static final long serialVersionUID = -7848417952171976554L;
	private AgentSettleService agentSettleService;
	private AgentSettle agentSettle;
	private Page page;
	private String agentNo;
	private List<AgentSettleHistory> listAgentSettleHistory;
	
	public String update(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		agentSettleService.update(agentSettle, auth.getRealname());
		return SUCCESS;
	}
	
	public String findAgentSettleByAgentNo(){
		agentSettle = agentSettleService.findByAgentNo(agentNo);
		return SUCCESS;
	}
	
	public String agentSettleHistoryQueryResult(){
		if (page == null) {
			page = new Page();
		}
		HttpServletRequest request = this.getHttpRequest();
		int currentPage = request.getParameter("currentPage") == null ? 1 : Integer.parseInt(request.getParameter("currentPage"));
		if (currentPage > 1) {
			page.setCurrentPage(currentPage);
		}
		listAgentSettleHistory = agentSettleService.findHistByAgentNo(agentNo,page);
		return SUCCESS;
	}

	public AgentSettleService getAgentSettleService() {
		return agentSettleService;
	}

	public void setAgentSettleService(AgentSettleService agentSettleService) {
		this.agentSettleService = agentSettleService;
	}

	public AgentSettle getAgentSettle() {
		return agentSettle;
	}

	public void setAgentSettle(AgentSettle agentSettle) {
		this.agentSettle = agentSettle;
	}

	public String getAgentNo() {
		return agentNo;
	}

	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}

	public List<AgentSettleHistory> getListAgentSettleHistory() {
		return listAgentSettleHistory;
	}

	public void setListAgentSettleHistory(List<AgentSettleHistory> listAgentSettleHistory) {
		this.listAgentSettleHistory = listAgentSettleHistory;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

}

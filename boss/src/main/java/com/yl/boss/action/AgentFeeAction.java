package com.yl.boss.action;

import java.util.List;

import com.yl.boss.Constant;
import com.yl.boss.entity.AgentFee;
import com.yl.boss.entity.Authorization;
import com.yl.boss.entity.CustomerFee;
import com.yl.boss.service.AgentFeeService;
import com.yl.boss.service.AgentService;
import com.yl.boss.utils.JsonUtils;

/**
 * 服务商费率控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月28日
 * @version V1.0.0
 */
public class AgentFeeAction extends Struts2ActionSupport {

	private static final long serialVersionUID = -5947743723640739489L;
	private AgentFeeService agentFeeService;
	private AgentFee agentFee;
	private String msg;
	private AgentService agentService;
	private List<AgentFee> agentFeeList;
	
	public String create(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		agentFeeService.create(agentFee, auth.getRealname());
		return SUCCESS;
	}
	
	public String update(){
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		agentFeeService.update(agentFee, auth.getRealname());
		return SUCCESS;
	}
	
	public String findById(){
		agentFee = agentFeeService.findById(agentFee.getId());
		return SUCCESS;
	}
	
	public String checkAgentFee(){
		String agentNo = agentFee.getAgentNo();
		String productType = agentFee.getProductType().toString();
		agentFee = agentFeeService.findBy(agentFee.getAgentNo(), agentFee.getProductType());
		if(agentFee == null){
			msg = "TRUE";
			if(productType.equals("HOLIDAY_REMIT")){
				List<AgentFee> listAgentFee = agentFeeService.findByAgentNo(agentNo);
				for (AgentFee c : listAgentFee) {
					if(c.getProductType().toString().equals("REMIT")){
						msg = "REMIT";
					}
				}
				if(msg != "REMIT"){
					msg = "EXISTS_REMIT";
				}else{
					msg = "TRUE";
				}
			}
		}else{
			msg = "FALSE";
		}
		return SUCCESS;
	}

	/**
     * 根据服务商编号查询所有业务费率信息
     * @return
     */
    public String queryAgentFeeAllByAgentNo(){
        try {
            String agentNo = getHttpRequest().getParameter("agentNo");
            if(agentNo != null){
                
                //判断当前代理商等级是否小于3
                if(agentService.queryAgentLevelByAgentNo(agentNo) < 3){
                    agentFeeList = agentFeeService.findByAgentNo(getHttpRequest().getParameter("agentNo"));
                    msg = JsonUtils.toJson(agentFeeList);
                }else {
                    msg = "false";
                }
                
            }
        } catch (Exception e) {
            msg = null;
            logger.info("Query AgentFee is Null, AgentNo:" + getHttpRequest().getParameter("agentNo"));
        }
        return SUCCESS;
    }
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public AgentFeeService getAgentFeeService() {
		return agentFeeService;
	}

	public AgentFee getAgentFee() {
		return agentFee;
	}

	public void setAgentFee(AgentFee agentFee) {
		this.agentFee = agentFee;
	}

	public void setAgentFeeService(AgentFeeService agentFeeService) {
		this.agentFeeService = agentFeeService;
	}

	public AgentService getAgentService() {
		return agentService;
	}

	public void setAgentService(AgentService agentService) {
		this.agentService = agentService;
	}

	public List<AgentFee> getAgentFeeList() {
		return agentFeeList;
	}

	public void setAgentFeeList(List<AgentFee> agentFeeList) {
		this.agentFeeList = agentFeeList;
	}
}

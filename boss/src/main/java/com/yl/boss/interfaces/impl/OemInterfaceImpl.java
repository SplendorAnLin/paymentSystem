package com.yl.boss.interfaces.impl;

import com.yl.boss.api.interfaces.OemInterface;
import com.yl.boss.entity.Agent;
import com.yl.boss.entity.Customer;
import com.yl.boss.service.AgentService;
import com.yl.boss.service.CustomerService;

import javax.annotation.Resource;

public class OemInterfaceImpl implements OemInterface {

    @Resource
    CustomerService customerService;
    @Resource
    AgentService agentService;

    @Override
    public String findTopAgentNo(String customerNo) {
        Customer customer=customerService.findByCustNo(customerNo);
        if (customer.getAgentNo()!=null){
            Agent agent=agentService.findByNo(customer.getAgentNo());
            if(agent!=null&&agent.getAgentLevel()!=null){
                if(agent.getAgentLevel()==1){
                    return agent.getAgentNo();
                }else if (agent.getAgentLevel()==2){
                    return agent.getParenId();
                }else if(agent.getAgentLevel()==3){
                    return agentService.findByNo(agent.getParenId()).getParenId();
                }
            }

        }
        return null;
    }



    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }
}

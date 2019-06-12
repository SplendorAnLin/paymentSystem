package com.yl.boss.action;

import com.yl.boss.entity.ReportCustomer;
import com.yl.boss.service.ReportCustomerService;

public class ReportCustomerAction extends Struts2ActionSupport{
	private static final long serialVersionUID = 1L;
	
	private ReportCustomer reportCustomer;
	private ReportCustomerService reportCustomerService;
	private long id;

	public String create(){
		reportCustomerService.create(reportCustomer);
		return SUCCESS;
	}
	
	public String update(){
		reportCustomerService.update(reportCustomer);
		return SUCCESS;
	}
	public String findById(){
		reportCustomer=reportCustomerService.findById(id);
		return SUCCESS;
	}
	
	public ReportCustomerService getReportCustomerService() {
		return reportCustomerService;
	}

	public long getId() {
		return id;
	}

	public void setReportCustomerService(ReportCustomerService reportCustomerService) {
		this.reportCustomerService = reportCustomerService;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ReportCustomer getReportCustomer() {
		return reportCustomer;
	}

	public void setReportCustomer(ReportCustomer reportCustomer) {
		this.reportCustomer = reportCustomer;
	}
}

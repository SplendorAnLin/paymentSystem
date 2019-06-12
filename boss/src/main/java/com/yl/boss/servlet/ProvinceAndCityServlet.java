package com.yl.boss.servlet;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yl.boss.bean.ProvinceAndCityBean;
import com.yl.boss.entity.Organization;
import com.yl.boss.service.OrganizationService;
import com.yl.boss.utils.ProvinceAndCityUtil;

public class ProvinceAndCityServlet extends HttpServlet{

	
	private OrganizationService organizationService;

	public OrganizationService getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}
	
	public void init() {
		ServletContext sc = getServletContext();  
        WebApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);  
  
        OrganizationService organizationService = (OrganizationService) ac.getBean("organizationService"); 
		List<Organization> provinces = organizationService.findAllProvince();
		for(Organization province : provinces){
			List<Organization> citys = organizationService.findByParentCode(province.getCode());
			ProvinceAndCityBean provinceAndCityBean = null;
			for(Organization city : citys){
				provinceAndCityBean = new ProvinceAndCityBean();
				provinceAndCityBean.setCity(city);
				provinceAndCityBean.setProvince(province);
				ProvinceAndCityUtil.map.put(city.getCode(), provinceAndCityBean);
			}
		}
	}
	

}

package com.yl.boss.action;

import java.util.List;

import javax.annotation.Resource;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.entity.Customer;
import com.yl.boss.entity.Shop;
import com.yl.boss.service.CustomerService;
import com.yl.boss.service.ShopService;
import com.yl.boss.utils.CodeBuilder;

/**
 * 网点控制器
 * 
 * @author 聚合支付有限公司
 * @since 2017年6月15日
 * @version V1.0.0
 */
public class ShopAction extends Struts2ActionSupport {

	private static final long serialVersionUID = 1L;

	@Resource
	ShopService shopService;
	
	@Resource
	CustomerService customerService;
	
	Shop shop;
	
	Customer customer;
	
	private String msg;

	/**
	 * 网点信息新增
	 * @return
	 */
	public String shopAdd(){
		try {
			customer = customerService.findByCustNo(getHttpRequest().getParameter("customerNo"));
			if(customer != null){
				
				//获取该商户最高网点编号
				//String shopNo = shopService.queryMaxShopNo(customer.getCustomerNo());
				
				//若当前获取到的编号不为空，则新增时，累加1；反之，则当第一条处理
//				if(shopNo != null){
//					shop.setShopNo(customer.getCustomerNo() + "-" + (Integer.parseInt(shopNo.split("-")[1]) + 1));
//				}else {
//					shop.setShopNo(customer.getCustomerNo() + "-" + "1001");
//				}
				shop.setShopNo(CodeBuilder.buildNumber(15));
				shop.setCustomer(customer);
				shopService.shopAdd(shop);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 根据ID查询单条网点信息
	 * @return
	 */
	public String shopById(){
		try {
			shop = shopService.shopById(Long.parseLong(getHttpRequest().getParameter("id")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 修改单条网点信息
	 * @return
	 */
	public String shopUpdate(){
		try {
			customer = customerService.findByCustNo(getHttpRequest().getParameter("customerNo"));
			if(customer != null){
				shop.setCustomer(customer);
				shopService.ShopUpdate(shop);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * Ajax根据网点编号查询是否存在
	 * @return
	 */
	public String ajaxQueryShopByShopNo(){
		try {
			shop = shopService.queryByShopNo(getHttpRequest().getParameter("shopNo"));
			if(shop != null && shop.getShopNo().split("-")[0].equals(getHttpRequest().getParameter("customerNo"))){
				msg = "true";
			}else {
				msg = "false";
			}
		} catch (Exception e) {
			msg = "true";
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 根据网点编号查询网点名称
	 * @return
	 */
	public String queryShopNameByShopNo(){
		msg = shopService.queryShopNameByShopNo(shop.getShopNo());
		return SUCCESS;
	}
	
	/**
	 * 根据商编查询旗下所有网点信息
	 * @return
	 */
	public String queryShopListByCustomerNo(){
		try {
			List<Shop> shopList = shopService.queryShopList(getHttpRequest().getParameter("customerNo"));
			if(shopList != null){
				msg = JsonUtils.toJsonString(shopList);
			}else {
				msg = null;
			}
		} catch (Exception e) {
			logger.info("query shopList fail");
		}
		return SUCCESS;
	}
	
	
	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}

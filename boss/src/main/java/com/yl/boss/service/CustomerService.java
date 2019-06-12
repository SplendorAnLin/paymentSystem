package com.yl.boss.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.yl.receive.hessian.bean.ReceiveConfigInfoBean;
import com.yl.boss.entity.Customer;
import com.yl.boss.entity.CustomerCert;
import com.yl.boss.entity.CustomerFee;
import com.yl.boss.entity.CustomerHistory;
import com.yl.boss.entity.CustomerSettle;
import com.yl.boss.entity.License;
import com.yl.boss.entity.Shop;
import com.yl.dpay.hessian.beans.ServiceConfigBean;
import com.yl.online.trade.hessian.bean.PartnerRouterBean;

/**
 * 商户业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public interface CustomerService {
	/**
	 * 收支明细 年
	 * @param owner
	 * @return
	 */
	public String initYear(String owner);
	
	/**
	 * 收支明细
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param status
	 * @param receiver
	 * @return
	 * @throws ParseException 
	 */
	public String initRD(String owner,String initSumFlag) throws ParseException;
	
	/**
	 * 订单转换率
	 * @param owner
	 * @return
	 * @throws ParseException 
	 */
	public String initOrder(String owner,String initSumFlag) throws ParseException;
	
	/**
	 * 经营分析  日 - 周 - 月 - 所有
	 * @param owner
	 * @return
	 * @throws ParseException 
	 */
	public String initDayDate(String owner,String initSumFlag) throws ParseException;
	
	/**
	 * 今日数据
	 * @param owner
	 * @return
	 */
	public String initDate(String owner,String initSumFlag);
	
	/**
	 * 商户密码重置
	 */
	public void resetPassWord(String customerNo);
	public boolean resetPassWordByPhone(String customerNo,String phone);
	/**
	 * 创建商户
	 * @param customer
	 * @param customerCert
	 * @param custFees
	 * @param custKeys
	 * @param customerSettle
	 * @param routeCodes
	 * @param oper
	 */
	public void create(Customer customer, CustomerCert customerCert,List<CustomerFee> custFees,
			ServiceConfigBean serviceConfigBean, CustomerSettle customerSettle, String oper, PartnerRouterBean partnerRouterBean, int cycle,ReceiveConfigInfoBean receiveConfigInfoBean,List<Shop> shopList, String configStatus);
	
	/**
	 * @Description 外部系统商户入网
	 * @param customer
	 * @param customerCert
	 * @param custFees
	 * @param serviceConfigBean
	 * @param customerSettle
	 * @param oper
	 * @param systemCode
	 * @date 2016年10月23日 下午8:25:41
	 */
	public void create(Customer customer, CustomerCert customerCert, List<CustomerFee> custFees, ServiceConfigBean serviceConfigBean, ReceiveConfigInfoBean receiveConfigInfoBean,CustomerSettle customerSettle, String oper, String systemCode, int cycle,List<Shop> shopList);
	/**
	 * 更新商户基本信息
	 * @param customer
	 * @param oper
	 */
	public void update(Customer customer, CustomerCert customerCert, String oper,int cycle);
	public void update(Customer customer);
	
	/**
	 * 
	 * @Description 商户审核修改商户
	 * @param customer
	 * @param customerFees
	 * @param serviceConfigBean
	 * @param oper
	 * @date 2016年10月26日
	 */
	public void updateForAudit(Customer customer, List<CustomerFee> customerFees,ServiceConfigBean serviceConfigBean,ReceiveConfigInfoBean receiveConfigInfoBean,PartnerRouterBean partnerRouterBean,String oper,String msg,List<Shop> shopList);
	/**
	 * 根据商编查询
	 * @param custNo
	 * @return
	 */
	public Customer findByCustNo(String custNo);
	
	/**
	 * 根据手机号查询
	 * @param phone
	 * @return
	 */
	public Customer findByPhone(String phone);
	
	/**
	 * 根据商编查询历史
	 * @param custNo
	 * @return List<CustomerHistory>
	 */
	public List<CustomerHistory> findHistByCustNo(String custNo);
	
	/**
	 * 根据商户全称查询
	 * @param fullName
	 * @return
	 */
	public Customer findByFullName(String fullName);
	
	/**
	 * 根据商户简称查询
	 * @param shortName
	 * @return
	 */
	public Customer findByShortName(String shortName);
	
	/**
	 * 根据服务商编号查询
	 * @param AgentNo
	 * @return
	 */
	public List<Customer> findByAgentNo(String AgentNo);

	/**
	 * 商户修改：只agent系统提供的修改 
	 * @param customer
	 * @param customerCert
	 * @param customerSettle
	 * @param customerFees
	 * @param oper
	 * @param cycle
	 * @author qiujian
	 * 2016年11月6日
	 * @param serviceConfigBean 
	 * @param systemCode 
	 */
	public void updateCustomerOnlyForAgentSystem(com.yl.boss.entity.Customer customer,
			ServiceConfigBean serviceConfigBean, CustomerCert customerCert, com.yl.boss.entity.CustomerSettle customerSettle,
			List<com.yl.boss.entity.CustomerFee> customerFees, String oper,ReceiveConfigInfoBean receiveConfigInfoBean, String systemCode, int cycle,List<Shop> shopList);

	public ServiceConfigBean findServiceConfigBeanByCustNo(String string);

	/**
	 * 扫码入网
	 */
	public String QrCreate(License license, Customer customer, CustomerCert customerCert, List<CustomerFee> custFees, ServiceConfigBean serviceConfigBean, ReceiveConfigInfoBean receiveConfigInfoBean,CustomerSettle customerSettle, String oper, String systemCode, int cycle);
	
	/**
	 * 扫码入网商户修改
	 */
	public void qrUpdateCustomer(com.yl.boss.entity.Customer customer,
			ServiceConfigBean serviceConfigBean, CustomerCert customerCert, com.yl.boss.entity.CustomerSettle customerSettle,
			List<com.yl.boss.entity.CustomerFee> customerFees, String oper,ReceiveConfigInfoBean receiveConfigInfoBean, String systemCode, int cycle);
	
	/**
	 * 根据商户全称和简称，检查是否有重复
	 * @param fullName
	 * @param shortName
	 * @return
	 */
	public Map<String,String> checkCustomerName(String fullName,String shortName);
	
	/**
	 * 商户审核(服务商审核)
	 */
	public void auditCustomerAction(com.yl.boss.entity.Customer customer,
			ServiceConfigBean serviceConfigBean, CustomerCert customerCert, com.yl.boss.entity.CustomerSettle customerSettle,
			List<com.yl.boss.entity.CustomerFee> customerFees, String oper,ReceiveConfigInfoBean receiveConfigInfoBean, String systemCode, int cycle,String msg,List<Shop> shopList);
 
	/**
	 * 根据商户编号查询简称
	 * @param customerNo
	 * @return
	 */
	public String findShortNameByCustomerNo(String customerNo);
	
    /**
     * 根据商户编号查询所属上级服务商编号
     * @param customerNo
     * @return
     */
    public String queryAgentNoByCustomerNo(String customerNo);
    /**
     *根据商户号查身份证号码
     * @param customerNo
     * @return
     */
    public String findCustomerCardByNo(String customerNo);
    
    /**
     * 根据商户编号判断当前商户是否存在
     * @param customerNo
     * @return
     */
    public boolean customerNoBool(String customerNo);
    
    /**
     * 根据商户编号获取全称
     * @param customerNo
     * @return
     */
    public String fullNameByCustNo(String customerNo);
    
    /**
     * 
     * @Description 快速入网
     * @param customer
     * @param password
     * @date 2017年9月15日
     */
    public void createQuick(Customer customer, String password);
    /**
     * 
     * @Description 快速入网提交结算卡信息
     * @param customerNo
     * @param customerCert
     * @param customerSettle
     * @param oper
     * @param systemCode
     * @date 2017年9月16日
     */
    public void createSettleInfo(String customerNo, CustomerCert customerCert, CustomerSettle customerSettle, String idCard, String oper, String systemCode);
    
    /**
     * 快速入网结算卡信息修改
     */
    public void updateCreateSettle(String customerNo, CustomerCert customerCert, CustomerSettle customerSettle, String oper);
    
    /**
     * 
     * @Description 更新商户基本信息
     * @param customer
     * @date 2017年9月23日
     */
    public String updateCustomerBaseInfo(Customer customer);
    
    /**
     * 
     * @Description 查询商户修改基本信息操作历史记录
     * @param customerNo
     * @return
     * @date 2017年9月24日
     */
    public List<CustomerHistory> findUpdateBaseInfoByCustNo(String customerNo);
}
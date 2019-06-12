package com.yl.boss.api.interfaces;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.boss.api.bean.Customer;
import com.yl.boss.api.bean.CustomerCertBean;
import com.yl.boss.api.bean.CustomerFee;
import com.yl.boss.api.bean.CustomerKey;
import com.yl.boss.api.bean.CustomerSettle;
import com.yl.boss.api.bean.Shop;
import com.yl.boss.api.bean.TransCardBean;
import com.yl.boss.api.bean.TransCardHistoryBean;
import com.yl.boss.api.enums.CardAttr;
import com.yl.boss.api.enums.KeyType;
import com.yl.boss.api.enums.ProductType;

/**
 * 商户信息远程接口
 *
 * @author 聚合支付有限公司
 * @since 2016年9月19日
 * @version V1.0.0
 */
public interface CustomerInterface {

	/**
	 * 收支明细 年
	 * 
	 * @param owner
	 * @return
	 */
	public String initYear(String owner);

	/**
	 * 收支明细
	 * 
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param status
	 * @param receiver
	 * @return
	 * @throws ParseException
	 */
	public String initRD(String owner, String initSumFlag) throws ParseException;

	/**
	 * 订单转换率
	 * 
	 * @param owner
	 * @return
	 * @throws ParseException
	 */
	public String initOrder(String owner, String initSumFlag) throws ParseException;

	/**
	 * 经营分析 日 - 周 - 月 - 所有
	 * 
	 * @param owner
	 * @return
	 * @throws ParseException
	 */
	public String initDayDate(String owner, String initSumFlag) throws ParseException;

	/**
	 * 今日数据
	 * 
	 * @param owner
	 * @return
	 */
	public String initDate(String owner, String initSumFlag);

	/**
	 * 根据商编获取商户信息
	 * 
	 * @param customerNo
	 * @return
	 */
	public Customer getCustomer(String customerNo);

	/**
	 * 根据手机号获取商户信息
	 * 
	 * @param phone
	 * @return
	 */
	public Customer getCustomerByPhone(String phone);

	/**
	 * 根据商编产品类型获取商户密钥
	 * 
	 * @param customerNo
	 * @param productType
	 * @return
	 */
	public CustomerKey getCustomerKeyBy(String customerNo, ProductType productType);

	/**
	 * 根据商编密钥类型获取商户密钥
	 * 
	 * @param customerNo
	 * @param keyType
	 * @return
	 */
	public CustomerKey getCustomerKey(String customerNo, KeyType keyType);

	/**
	 * 根据商编查询秘钥信息
	 * 
	 * @param customerNo
	 * @return
	 */
	public List<CustomerKey> getCustomerKeyByCustomerNo(String customerNo);

	/**
	 * 根据商编获取商户结算卡信息
	 * 
	 * @param customerNo
	 * @return
	 */
	public CustomerSettle getCustomerSettle(String customerNo);

	/**
	 * 根据商编获取商户费率
	 * 
	 * @param customerNo
	 * @param productType
	 * @return
	 */
	public CustomerFee getCustomerFee(String customerNo, String productType);

	/**
	 * 根据商编查询商户费率
	 * 
	 * @param customerNo
	 * @return
	 */
	public List<CustomerFee> getCustomerFeeList(String customerNo);

	/**
	 * @Description 商户入网功能
	 * @param customer
	 * @param customerCertBean
	 * @param custFees
	 * @param serviceConfigBean
	 * @param customerSettle
	 * @param oper
	 * @param systemCode
	 * @param cycle
	 * @date 2016年10月23日 下午8:51:52
	 */
	public void openCustomer(Customer customer, CustomerCertBean customerCertBean, List<CustomerFee> custFees, String serviceConfigBean, String receiveConfigInfoBean, CustomerSettle customerSettle, String oper, String systemCode, int cycle, List<Shop> shopList);

	/**
	 * 商户查询
	 * 
	 * @param queryId
	 * @param params
	 * @return
	 * @author qiujian 2016年10月29日
	 */
	Page queryCustomerByAgentNo(String queryId, Map<String, Object> params);

	/**
	 * 通过商户编号从boss系统获取商户信息
	 * 
	 * @param params
	 * @return
	 * @author qiujian 2016年10月30日
	 */
	public Map<String, Object> toUpdateCustomer(Map<String, Object> params);

	/**
	 * 服务商系统 修改 商户信息
	 * 
	 * @param params
	 * @return
	 * @author qiujian 2016年10月31日
	 * @param configBean
	 */
	public void updateCustomer(Customer customer, String configBean, CustomerCertBean customerCert, CustomerSettle customerSettle, List<CustomerFee> customerFees, String oper, String receiveConfigInfoBean, String string, int cycle, List<Shop> shopList);

	public String checkShortName(String shortName);

	/**
	 *
	 * @Description 单个支付类型手续费查询
	 * @param customerNo
	 * @param productType
	 * @return
	 * @date 2016年12月20日
	 */
	public String getCustomerDfFee(String customerNo, ProductType productType);

	/**
	 * 获取商户状态
	 * 
	 * @param customerNo
	 * @return
	 */
	public String getCustomerStatus(String customerNo);

	/**
	 * 手机号重复验证 - 扫码入网
	 * 
	 * @param phone
	 * @return
	 */
	public String checkPhone(String phone);

	/**
	 * 商户全称重复验证 - 扫码入网
	 * 
	 * @param fullName
	 * @return
	 */
	public String checkFullName(String fullName);

	/**
	 * 商户全称获取
	 */
	public String getCustomerFullName(String customerNo);

	/**
	 * 获取商户MD5密钥
	 */
	public String getCustomerMD5Key(String customerNo);

	/**
	 * 根据商户号获取第一个设备码
	 * 
	 * @param custNo
	 * @return
	 */
	public String getDevice(String custNo);

	/**
	 * 获取商户号所有的设备
	 * 
	 * @param custNo
	 * @return
	 */
	public Map<String, Object> getDevices(String custNo);

	/**
	 * 根据商户全称和简称，检查是否有重复
	 * 
	 * @param fullName
	 * @param shortName
	 * @return
	 */
	public Map<String, String> checkCustomerName(String fullName, String shortName);

	/**
	 * 获取最近一次拒绝信息
	 */
	String getLastInfo(String customerNo);

	/**
	 * 商户审核(服务商审核)
	 */
	public void auditCustomerAction(Customer customer, String configBean, CustomerCertBean customerCert, CustomerSettle customerSettle, List<CustomerFee> customerFees, String oper, String receiveConfigInfoBean, String SystemCode, int cycle, String msg, List<Shop> shopList);

	/**
	 * 通过密钥获取商户号
	 */
	public String findByKey(String key);

	/**
	 * 服务商获取下级商户编号集合
	 */
	public String findCustNoByAgentNo(String agentNo);

	/**
	 * 新增用户意见反馈
	 */
	public String AddUserFeedBack(Map<String, Object> params);

	/**
	 * 提现申请反馈
	 */
	public Map<String, Object> goDrawCash(Map<String, Object> param);

	/**
	 * 查询文档信息
	 * 
	 * @param param
	 * @return
	 */
	public Page queryProtocolManagements(Map<String, Object> params);

	/**
	 * 根据商户编号查询简称
	 * 
	 * @param customerNo
	 * @return
	 */
	public String findShortNameByCustomerNo(String customerNo);

	/**
	 * APP 绑定银行卡
	 */
	public Map<String, Object> addTransCard(Map<String, Object> param);

	/**
	 * APP 根据商户号查询绑定银行卡信息
	 */
	public List<Map<String, Object>> findByCustNo(String custNo);

	/**
	 * APP 根据商户号 银行卡号解绑
	 */
	public Map<String, Object> unlockTansCard(String custNo, String accountNo, String cardAttr);

	/**
	 * 根据商户号查询商户身份证号
	 * 
	 * @param customerNo
	 * @return 身份证
	 */
	public String findCustomerCardByNo(String customerNo);

	/**
	 * 实名认证接口
	 */
	public Map<String, Object> verified(Map<String, Object> params);

	/**
	 * APP验证交易卡是否存在
	 */
	public Map<String, Object> checkTransCard(Map<String, Object> params);

	/**
	 * 条件查询交易卡
	 */
	public TransCardBean findTransCardByAccNo(String custNo, String accountNo, CardAttr cardAttr);

	/**
	 * 新增交易卡历史
	 */
	public void addTransCardHistory(TransCardHistoryBean transCardHistoryBean);

	/**
	 * 修改交易卡历史
	 */
	public void updateTransCardHistory(TransCardHistoryBean transCardHistoryBean);

	/**
	 * 根据商户号和银行卡号查询
	 * 
	 * @param customerNo
	 * @param accountNo
	 * @return
	 */
	public List<TransCardHistoryBean> findTransCardHistoryByCustAndAcc(String customerNo, String accountNo);

	/**
	 * 接口绑卡处理
	 */
	public Map<String, Object> addTransCardForApi(Map<String, Object> param);

	/**
	 * 根据商户号和登陆号,重置密码
	 * 
	 * @param custNo
	 * @param phone
	 * @return
	 */
	public boolean resetPwd(String custNo, String phone);

	/**
	 * 获取商户路由模板配置
	 */
	public Map<String, Object> partnerRouter(String customerNo);

	/**
	 * POS数据接收
	 */
	public Map<String, Object> getPosApplicationInfo(Map<String, Object> params);

	/**
	 * APP 水牌申请
	 */
	public Map<String, Object> appDevice(Map<String, Object> params);

	/**
	 * 获取水牌类型
	 */
	public List<Map<String, Object>> getDevicesInfo(Map<String, Object> params);

	/**
	 * 根据字典类型编号Ajax查询旗下所有字典
	 */
	public String ajaxQueryDictionaryByTypeCode(String code);

	/**
	 * 快速绑卡
	 */
	public Map<String, Object> fastAddTransCard(String customerNo, String accountNo, String cardAttr);

	/**
	 * 新增卡历史记录返回结算卡卡号
	 */
	public void addTransCardHisForTrade(Map<String, Object> params);

	/**
	 * 根据接口请求号查询结算卡信息
	 */
	public TransCardBean findByInterfaceRequestId(String interfaceRequestId);
	
	/**
	 * 
	 * @Description 根据接口请求号查询交易卡信息
	 * @param interfaceRequestId
	 * @return
	 * @date 2017年11月4日
	 */
	public TransCardBean findTransCardByInterfaceRequestId(String interfaceRequestId);

	/**
	 * 根据商户编号，交易卡号获取结算卡信息
	 */
	public TransCardBean findByCustAndAccNo(String custNo, String accNo);

	/**
	 * 
	 * @Description 快速入网
	 * @param customer
	 * @param password
	 * @date 2017年9月15日
	 */
	public void createQuick(Customer customer, String password);
	 /**
	  * 根据商户号查询产品信息
	  * @param customerNo
	  * @return
	  */
	 public String customerProductInfo(String customerNo);
	 
	 /**
	  * App 绑定结算卡信息
	  */
	 public String customerSettle(Map<String, Object> params);
	 
	 /**
	  * App 修改结算卡信息
	  */
	 public String updateCreateSettle(Map<String, Object> params);
	 
	 /**
	  * App 查询结算卡信息
	  */
	 public Map<String, Object> querySettle(String customerNo);
	 
	 /**
	  * 
	  * @Description 更新商户基本信息
	  * @param params
	  * @return
	  * @date 2017年9月23日
	  */
	 public String updateCustomerBaseInfo(Map<String, Object> params);
	 /**
	  * 
	  * @Description 判断商户是否修改过商户基本信息
	  * @param customerNo
	  * @return
	  * @date 2017年9月24日
	  */
	 public Boolean findUpdateBaseInfoByCustNo(String customerNo);
	 
	 /**
	  * 
	  * @Description 根据商户编号查询相关信息
	  * @param customerNo
	  * @return
	  * @date 2017年10月13日
	  */
	 public CustomerCertBean findCustomerCertByCustNo(String customerNo);
}
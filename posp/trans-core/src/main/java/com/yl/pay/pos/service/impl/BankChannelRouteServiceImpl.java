package com.yl.pay.pos.service.impl;

import com.pay.common.util.AmountUtil;
import com.pay.common.util.DateUtil;
import com.pay.common.util.Day;
import com.yl.pay.pos.TransTypeMapping;
import com.yl.pay.pos.bean.*;
import com.yl.pay.pos.entity.*;
import com.yl.pay.pos.enums.*;
import com.yl.pay.pos.exception.TransExceptionConstant;
import com.yl.pay.pos.exception.TransRunTimeException;
import com.yl.pay.pos.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * Title: 银行路由处理服务（新版）
 * Description: 
 * Copyright: Copyright (c)2012
 * Company: com.yl.pay
 * @author jun
 */

public class BankChannelRouteServiceImpl extends BaseService implements IBankChannelRouteService {
	private static final Log log = LogFactory.getLog(BankChannelRouteServiceImpl.class);
	private IBankChannelService bankChannelService;
	private IBankChannelFeeService bankChannelFeeService;
	private IBankCustomerConfigService bankCustomerConfigService;
	private ITransRouteConfigService transRouteConfigService;
	
	public List<BankChannelRouteReturnBean> fetchAvailableBankChannel(BankChannelRouteBean routeParam) {

		log.info("+#+#+#+#+#+#+# getAvailableBankChannel start\n" +
				" customerNo=" + routeParam.getCustomerNo()+
				" customerMcc=" + routeParam.getCustomerMccCode()+
				" issuerBankCode=" + routeParam.getIssuer()+
				" cardType=" + routeParam.getCardType()+
				" cardStyle=" + routeParam.getCardStyle()+
				" amount=" + routeParam.getPosRequest().getAmount()+
				" cardVerifyCode=" + routeParam.getCardVerifyCode()+
				" routeType=" + routeParam.getRouteType().name());
		
		PosRequest posRequest=routeParam.getPosRequest();
		Order order=routeParam.getOrder();
		String customerMccCode=routeParam.getCustomerMccCode();
		String customerNo=routeParam.getCustomerNo();
		RouteType routeType = routeParam.getRouteType();
		
		List<BankChannelRouteReturnBean> returnBankChannelParamList=new ArrayList<BankChannelRouteReturnBean>();
		
		// 外卡,暂拒绝交易   
		if (!CurrencyType.CNY.equals(posRequest.getCurrencyType())) {
			log.info("CurrencyType invalid ="	+ posRequest.getCurrencyType());
			return null;
		}
		
		// 除了余额查询，可用余额查询，消费，预授权之外的所有交易都必须走原来的银行接口
		if (!TransType.BALANCE_INQUIRY.equals(posRequest.getTransType())
				&& !TransType.AVAILABLE_FUNDSINQUIRY.equals(posRequest.getTransType())
				&& !TransType.PURCHASE.equals(posRequest.getTransType())
				&& !TransType.PRE_AUTH.equals(posRequest.getTransType())&&!TransType.SPECIFY_QUANCUN.equals(posRequest.getTransType())
				&&!TransType.NOT_SPECIFY_QUANCUN.equals(posRequest.getTransType())
				&&!TransType.OFFLINE_PURCHASE.equals(posRequest.getTransType())&&!TransType.CASH_RECHARGE_QUNCUN.equals(posRequest.getTransType())) {
			if (order == null) {
				throw new TransRunTimeException(TransExceptionConstant.ORDER_IS_NULL);
			}

			TransType orgTransType = TransTypeMapping.getOrgTransType(posRequest.getTransType());
			if (orgTransType == null) {
				return null;
			}

			// 根据原订单查找原银行通道，并校验是否可用
			Payment payment = paymentDao.findSourcePayment(order,TransStatus.SUCCESS, orgTransType);
			if (payment == null) {
				throw new TransRunTimeException(TransExceptionConstant.PAYMENT_IS_NULL);
			}

			String orgBankChannelCode = payment.getBankChannel().getCode();
			String orgBankCustomerNo = payment.getBankCustomerNo();
			String orgBankInterfaceCode = payment.getBankInterface().getCode();

			BankChannelRouteReturnBean returnBankChannelParam = checkBankChannelAndBankCustomer(posRequest, routeParam.getIssuer(), 
					customerMccCode, routeParam.getCardType(),	orgBankChannelCode, customerNo, orgBankCustomerNo,orgBankInterfaceCode,routeParam.getIsIC());

			if (returnBankChannelParam == null) {
				log.info("returnBankChannelParam  is null sourceTrxType=" + orgTransType
						+ " sourceBankInterfaceCode="	+ payment.getBankInterface().getCode()
						+ " orgBankChannelCode=" + orgBankChannelCode);
				return null;
			}

			returnBankChannelParamList.add(returnBankChannelParam);
			
			log.info("usableBankChannel=\n"
					+ "[bankChannelCode=" + returnBankChannelParam.getBankChannel().getCode() + 
					", bankCustomerNumber="	+ returnBankChannelParam.getBankCustomerNo()+ 
					", bankChannelCost=" + returnBankChannelParam.getBankChannelCost()+
					"]");
			
			return returnBankChannelParamList;
		}
		
		returnBankChannelParamList = getChannelBySystemRule(posRequest, routeParam.getIssuer(), customerMccCode, routeParam.getCardType(),routeParam.getCardStyle(),customerNo,routeParam.getIsIC(),routeType);		
		
		if(returnBankChannelParamList!=null&&!returnBankChannelParamList.isEmpty()){
			for(BankChannelRouteReturnBean returnParam:returnBankChannelParamList){
				log.info("++++++++ useable bankChannel code="+returnParam.getBankChannel().getCode()+"--BankCustomerNumber="+returnParam.getBankCustomerNo()+"--bankChannelCost="+returnParam.getBankChannelCost());
			}
		}
		
		//调整通道优先顺序,将非第一优先级且与第一优先级通道不同银行接口的通道信息移到第二优先级
		List<BankChannelRouteReturnBean> bankChannelParamResult=new ArrayList<BankChannelRouteReturnBean>();
		int changeFlag=0;
		if(returnBankChannelParamList!=null&&!returnBankChannelParamList.isEmpty()){
			for(int i=0;i<returnBankChannelParamList.size();i++){
				if(i!=0&&changeFlag==0){
					if(!returnBankChannelParamList.get(i).getBankChannel().getBankInterface().getCode().equals(returnBankChannelParamList.get(0).getBankChannel().getBankInterface().getCode())){
						changeFlag=i;
					}
				}
			}
			if(returnBankChannelParamList.size()>1){
				bankChannelParamResult.add(returnBankChannelParamList.get(0));
				if(changeFlag!=0){
					bankChannelParamResult.add(returnBankChannelParamList.get(changeFlag));
				}
				for(int i=0;i<returnBankChannelParamList.size();i++){
					if(i==0||i==changeFlag){
						continue;
					}
					bankChannelParamResult.add(returnBankChannelParamList.get(i));
				}
			}else{
				bankChannelParamResult=returnBankChannelParamList;
			}
			
			for(BankChannelRouteReturnBean returnParam:bankChannelParamResult){
				log.info("+++22222+++++ useable bankChannel code="+returnParam.getBankChannel().getCode()+"--BankCustomerNumber="+returnParam.getBankCustomerNo()+"--bankChannelCost="+returnParam.getBankChannelCost());
			}
		}
		
		return bankChannelParamResult;	
	}
	
	/**
	 * 获取可用银行通道
	 */
	private List<BankChannelRouteReturnBean> getChannelBySystemRule(PosRequest posRequest,String issuer,String customerMccCode,
			CardType cardType,String cardStyle,String customerNo,YesNo isIC,RouteType routeType){
		TransType transType=posRequest.getTransType();
		TransRouteProfile routeProfile=getTransRouteProfile(posRequest, issuer, cardType, cardStyle, customerNo,routeType);
		if(routeProfile==null){
			return null;
		}
		log.info("-------useable routeProfile ="+routeProfile.getCode());
		
		List<BankChannelRouteReturnBean> allRouteReturnBeans=new LinkedList<BankChannelRouteReturnBean>();
		
		if(BankChannelChooseType.INCOST.equals(routeProfile.getChooseType())){
			//1、获取可用通道，并计算银行成本
			List<BankChannel> channels=bankChannelService.findAvailableBankChannel(issuer, cardType, posRequest.getTransType(),isIC);
			if(channels==null||channels.isEmpty()){
				return null;
			}
			//按成本走，默认排除非实名通道
			List<BankChannel> availableChannels=new LinkedList<BankChannel>();
			for(BankChannel channel:channels){
				if(!BankBillType.UN_REALNAME.equals(channel.getBankBillType())){
					availableChannels.add(channel);
				}
			}
			List<BankChannelRouteReturnBean> routeReturnBeans=getBankChannelCost(transType, availableChannels, posRequest.getAmount());
			//2、根据商户号、通道获取银行商户配置
			routeReturnBeans=getBankCustomerNew(routeReturnBeans, customerNo,posRequest.getPosCati());
			//3、按成本排序
			sortChannelBean(routeReturnBeans);
			allRouteReturnBeans=routeReturnBeans;
		}else if(BankChannelChooseType.INORDER.equals(routeProfile.getChooseType())){
			List<BankChannelAssignABean> aBeans=routeProfile.getOneBeans();	
			if(aBeans==null||aBeans.isEmpty()){
				throw new TransRunTimeException(TransExceptionConstant.ROUTE_RULE_ERROR);
			}
			
			//按aBean参数顺序
			for(BankChannelAssignABean aBean:aBeans){
				if(StringUtils.isBlank(aBean.getBankBillType())&&StringUtils.isBlank(aBean.getBankConnectType())){
					throw new TransRunTimeException(TransExceptionConstant.ROUTE_RULE_ERROR);
				}
				List<BankChannel> channels=bankChannelService.findAvailableBankChannel(issuer, cardType, transType, aBean.getBankConnectType(), aBean.getBankBillType(),isIC);
				if(channels==null||channels.isEmpty()){
					continue;
				}
				List<BankChannelRouteReturnBean> routeReturnBeans=getBankChannelCost(transType, channels, posRequest.getAmount());
				routeReturnBeans=getBankCustomerNew(routeReturnBeans, customerNo,posRequest.getPosCati());
				
				List<BankChannelRouteReturnBean> routeReturnBeanTemp=new LinkedList<BankChannelRouteReturnBean>();
				if(BankChannelChooseType.INCOST.name().equals(aBean.getChooseType())){
					sortChannelBean(routeReturnBeans);
					routeReturnBeanTemp=routeReturnBeans;
				}else if(BankChannelChooseType.INORDER.name().equals(aBean.getChooseType())){
					//按 bBean参数顺序
					for(BankChannelAssignBBean bBean:aBean.getTwoBeans()){
						List<BankChannelRouteReturnBean> routeReturnBeanTemp2=new LinkedList<BankChannelRouteReturnBean>();	
						if(StringUtils.isBlank(bBean.getBankInterface())&&StringUtils.isBlank(bBean.getMccCategory())){
							throw new TransRunTimeException(TransExceptionConstant.ROUTE_RULE_ERROR);
						}
						if(!StringUtils.isBlank(bBean.getBankInterface())&&!StringUtils.isBlank(bBean.getMccCategory())){
							for(BankChannelRouteReturnBean routeBean:routeReturnBeans){
								if(routeBean.getBankChannel().getBankInterface().getCode().equals(bBean.getBankInterface())
										&&routeBean.getBankChannel().getMccCategory().equals(bBean.getMccCategory())){
									routeReturnBeanTemp2.add(routeBean);
								}
							}
						}else if(!StringUtils.isBlank(bBean.getBankInterface())){
							for(BankChannelRouteReturnBean routeBean:routeReturnBeans){
								if(routeBean.getBankChannel().getBankInterface().getCode().equals(bBean.getBankInterface())){
									routeReturnBeanTemp2.add(routeBean);
								}
							}
						}else {
							for(BankChannelRouteReturnBean routeBean:routeReturnBeans){
								if(routeBean.getBankChannel().getMccCategory().equals(bBean.getMccCategory())){
									routeReturnBeanTemp2.add(routeBean);
								}
							}
						}
						sortChannelBean(routeReturnBeanTemp2);
						routeReturnBeanTemp.addAll(routeReturnBeanTemp2);
					}
					
				}else{
					//先按 bBean参数顺序
					for(BankChannelAssignBBean bBean:aBean.getTwoBeans()){
						List<BankChannelRouteReturnBean> routeReturnBeanTemp2=new LinkedList<BankChannelRouteReturnBean>();	
						if(StringUtils.isBlank(bBean.getBankInterface())&&StringUtils.isBlank(bBean.getMccCategory())){
							throw new TransRunTimeException(TransExceptionConstant.ROUTE_RULE_ERROR);
						}
						if(!StringUtils.isBlank(bBean.getBankInterface())&&!StringUtils.isBlank(bBean.getMccCategory())){
							for(BankChannelRouteReturnBean routeBean:routeReturnBeans){
								if(routeBean.getBankChannel().getBankInterface().getCode().equals(bBean.getBankInterface())
										&&routeBean.getBankChannel().getMccCategory().equals(bBean.getMccCategory())){
									routeReturnBeanTemp2.add(routeBean);
								}
							}
						}else if(!StringUtils.isBlank(bBean.getBankInterface())){
							for(BankChannelRouteReturnBean routeBean:routeReturnBeans){
								if(routeBean.getBankChannel().getBankInterface().getCode().equals(bBean.getBankInterface())){
									routeReturnBeanTemp2.add(routeBean);
								}
							}
						}else {
							for(BankChannelRouteReturnBean routeBean:routeReturnBeans){
								if(routeBean.getBankChannel().getMccCategory().equals(bBean.getMccCategory())){
									routeReturnBeanTemp2.add(routeBean);
								}
							}
						}
						sortChannelBean(routeReturnBeanTemp2);
						routeReturnBeanTemp.addAll(routeReturnBeanTemp2);
					}
					//剩余通道按成本
					List<BankChannelRouteReturnBean> routeReturnBeanTemp3=new LinkedList<BankChannelRouteReturnBean>();
					for(BankChannelRouteReturnBean routeBean:routeReturnBeans){
						boolean flag=true;
						for(BankChannelRouteReturnBean routeBean1:routeReturnBeanTemp){
							if(routeBean.getBankCustomerConfig().getCustomerNo().equals(routeBean1.getBankCustomerConfig().getCustomerNo())&&
									routeBean.getBankCustomerConfig().getBankChannelCode().equals(routeBean1.getBankCustomerConfig().getBankChannelCode())){
								flag=false;
								break;
							}
						}
						if(flag){
							routeReturnBeanTemp3.add(routeBean);
						}
					}
					sortChannelBean(routeReturnBeanTemp3);
					routeReturnBeanTemp.addAll(routeReturnBeanTemp3);
				}
				
				allRouteReturnBeans.addAll(routeReturnBeanTemp);
			}
			
		}else{
			List<BankChannelAssignABean> aBeans=routeProfile.getOneBeans();	
			if(aBeans==null||aBeans.isEmpty()){
				throw new TransRunTimeException(TransExceptionConstant.ROUTE_RULE_ERROR);
			}
			
			//按aBean参数顺序
			for(BankChannelAssignABean aBean:aBeans){
				if(StringUtils.isBlank(aBean.getBankBillType())&&StringUtils.isBlank(aBean.getBankConnectType())){
					throw new TransRunTimeException(TransExceptionConstant.ROUTE_RULE_ERROR);
				}
				List<BankChannel> channels=bankChannelService.findAvailableBankChannel(issuer, cardType, transType, aBean.getBankConnectType(), aBean.getBankBillType(),isIC);
				if(channels==null||channels.isEmpty()){
					continue;
				}
				List<BankChannelRouteReturnBean> routeReturnBeans=getBankChannelCost(transType, channels, posRequest.getAmount());
				routeReturnBeans=getBankCustomerNew(routeReturnBeans, customerNo,posRequest.getPosCati());
				
				List<BankChannelRouteReturnBean> routeReturnBeanTemp=new LinkedList<BankChannelRouteReturnBean>();
				if(BankChannelChooseType.INCOST.name().equals(aBean.getChooseType())){
					sortChannelBean(routeReturnBeans);
					routeReturnBeanTemp=routeReturnBeans;
				}else if(BankChannelChooseType.INORDER.name().equals(aBean.getChooseType())){
					//按 bBean参数顺序
					for(BankChannelAssignBBean bBean:aBean.getTwoBeans()){
						List<BankChannelRouteReturnBean> routeReturnBeanTemp2=new LinkedList<BankChannelRouteReturnBean>();	
						if(StringUtils.isBlank(bBean.getBankInterface())&&StringUtils.isBlank(bBean.getMccCategory())){
							throw new TransRunTimeException(TransExceptionConstant.ROUTE_RULE_ERROR);
						}
						if(!StringUtils.isBlank(bBean.getBankInterface())&&!StringUtils.isBlank(bBean.getMccCategory())){
							for(BankChannelRouteReturnBean routeBean:routeReturnBeans){
								if(routeBean.getBankChannel().getBankInterface().getCode().equals(bBean.getBankInterface())
										&&routeBean.getBankChannel().getMccCategory().equals(bBean.getMccCategory())){
									routeReturnBeanTemp2.add(routeBean);
								}
							}
						}else if(!StringUtils.isBlank(bBean.getBankInterface())){
							for(BankChannelRouteReturnBean routeBean:routeReturnBeans){
								if(routeBean.getBankChannel().getBankInterface().getCode().equals(bBean.getBankInterface())){
									routeReturnBeanTemp2.add(routeBean);
								}
							}
						}else {
							for(BankChannelRouteReturnBean routeBean:routeReturnBeans){
								if(routeBean.getBankChannel().getMccCategory().equals(bBean.getMccCategory())){
									routeReturnBeanTemp2.add(routeBean);
								}
							}
						}
						sortChannelBean(routeReturnBeanTemp2);
						routeReturnBeanTemp.addAll(routeReturnBeanTemp2);
					}
					
				}else{
					//先按 bBean参数顺序
					for(BankChannelAssignBBean bBean:aBean.getTwoBeans()){
						List<BankChannelRouteReturnBean> routeReturnBeanTemp2=new LinkedList<BankChannelRouteReturnBean>();	
						if(StringUtils.isBlank(bBean.getBankInterface())&&StringUtils.isBlank(bBean.getMccCategory())){
							throw new TransRunTimeException(TransExceptionConstant.ROUTE_RULE_ERROR);
						}
						if(!StringUtils.isBlank(bBean.getBankInterface())&&!StringUtils.isBlank(bBean.getMccCategory())){
							for(BankChannelRouteReturnBean routeBean:routeReturnBeans){
								if(routeBean.getBankChannel().getBankInterface().getCode().equals(bBean.getBankInterface())
										&&routeBean.getBankChannel().getMccCategory().equals(bBean.getMccCategory())){
									routeReturnBeanTemp2.add(routeBean);
								}
							}
						}else if(!StringUtils.isBlank(bBean.getBankInterface())){
							for(BankChannelRouteReturnBean routeBean:routeReturnBeans){
								if(routeBean.getBankChannel().getBankInterface().getCode().equals(bBean.getBankInterface())){
									routeReturnBeanTemp2.add(routeBean);
								}
							}
						}else {
							for(BankChannelRouteReturnBean routeBean:routeReturnBeans){
								if(routeBean.getBankChannel().getMccCategory().equals(bBean.getMccCategory())){
									routeReturnBeanTemp2.add(routeBean);
								}
							}
						}
						sortChannelBean(routeReturnBeanTemp2);
						routeReturnBeanTemp.addAll(routeReturnBeanTemp2);
					}
					//剩余通道按成本
					List<BankChannelRouteReturnBean> routeReturnBeanTemp3=new LinkedList<BankChannelRouteReturnBean>();
					for(BankChannelRouteReturnBean routeBean:routeReturnBeans){
						boolean flag=true;
						for(BankChannelRouteReturnBean routeBean1:routeReturnBeanTemp){
							if(routeBean.getBankCustomerConfig().getCustomerNo().equals(routeBean1.getBankCustomerConfig().getCustomerNo())&&
									routeBean.getBankCustomerConfig().getBankChannelCode().equals(routeBean1.getBankCustomerConfig().getBankChannelCode())){
								flag=false;
								break;
							}
						}
						if(flag){
							routeReturnBeanTemp3.add(routeBean);
						}
					}
					sortChannelBean(routeReturnBeanTemp3);
					routeReturnBeanTemp.addAll(routeReturnBeanTemp3);
				}
				
				allRouteReturnBeans.addAll(routeReturnBeanTemp);
			}
			
			//获取所有可用通道信息
			List<BankChannel> channels=bankChannelService.findAvailableBankChannel(issuer, cardType, posRequest.getTransType(),isIC);
			if(channels==null||channels.isEmpty()){
				return null;
			}
			//剩余通道排除非实名
			List<BankChannel> availableChannels=new LinkedList<BankChannel>();
			for(BankChannel channel:channels){
				if(!BankBillType.UN_REALNAME.equals(channel.getBankBillType())){
					availableChannels.add(channel);
				}
			}
			List<BankChannelRouteReturnBean> routeReturnBeans1=getBankChannelCost(transType, availableChannels, posRequest.getAmount());
			routeReturnBeans1=getBankCustomerNew(routeReturnBeans1, customerNo,posRequest.getPosCati());
			
			List<BankChannelRouteReturnBean> routeReturnBeanTemp4=new LinkedList<BankChannelRouteReturnBean>();
			for(BankChannelRouteReturnBean routeBean:routeReturnBeans1){
				boolean flag=true;
				for(BankChannelRouteReturnBean routeBean1:allRouteReturnBeans){
					if(routeBean.getBankCustomerConfig().getCustomerNo().equals(routeBean1.getBankCustomerConfig().getCustomerNo())&&
							routeBean.getBankCustomerConfig().getBankChannelCode().equals(routeBean1.getBankCustomerConfig().getBankChannelCode())){
						flag=false;
						break;
					}
				}
				if(flag){
					routeReturnBeanTemp4.add(routeBean);
				}
			}
		
			sortChannelBean(routeReturnBeanTemp4);
			allRouteReturnBeans.addAll(routeReturnBeanTemp4);
			
		}
		
		return allRouteReturnBeans;
	}
	
	private void sortChannelBean(List<BankChannelRouteReturnBean> routeReturnBeans){
		Collections.sort(routeReturnBeans, new Comparator() {
			public int compare(Object o1, Object o2) {
				BankChannelRouteReturnBean bankChannelParam0=(BankChannelRouteReturnBean)o1;
				BankChannelRouteReturnBean bankChannelParam1=(BankChannelRouteReturnBean)o2;
				if (bankChannelParam0 != null && bankChannelParam1 != null) {
					return new Double(bankChannelParam0.getBankChannelCost()).compareTo(bankChannelParam1.getBankChannelCost());
				   }
				return 0;
			}
		});
	}
	
	private List<BankChannelRouteReturnBean> getBankCustomer(List<BankChannelRouteReturnBean> routeReturnBeans,String customerNo){
		List<BankChannelRouteReturnBean> routeReturnBeans2=new LinkedList<BankChannelRouteReturnBean>();
		
		for(BankChannelRouteReturnBean routeBean:routeReturnBeans){
			BankChannel bankChannel=routeBean.getBankChannel();
			List<BankCustomerConfig> bankCustomerConfigs=bankCustomerConfigService.findByBankInterfaceAndBankChannelAndCustomerNo(bankChannel.getBankInterface().getCode(), bankChannel, customerNo);
			if(bankCustomerConfigs==null||bankCustomerConfigs.isEmpty()){
				continue;
			}
			routeBean.setBankCustomerConfig(bankCustomerConfigs.get(0));
			routeBean.setBankCustomerNo(bankCustomerConfigs.get(0).getBankCustomerNo());
			routeReturnBeans2.add(routeBean);
		}
		
		return routeReturnBeans2;
	}
	
	private List<BankChannelRouteReturnBean> getBankCustomerNew(List<BankChannelRouteReturnBean> routeReturnBeans,String customerNo,String posCati){
		List<BankChannelRouteReturnBean> routeReturnBeans2=new LinkedList<BankChannelRouteReturnBean>();
		
		for(BankChannelRouteReturnBean routeBean:routeReturnBeans){
			BankChannel bankChannel=routeBean.getBankChannel();
			List<BankCustomerConfig> bankCustomerConfigs=bankCustomerConfigService.findByBankInterfaceAndBankChannelAndCustomerNoAndPosCati(bankChannel.getBankInterface().getCode(), bankChannel, customerNo,posCati);
			if(bankCustomerConfigs==null||bankCustomerConfigs.isEmpty()){
				continue;
			}
			routeBean.setBankCustomerConfig(bankCustomerConfigs.get(0));
			routeBean.setBankCustomerNo(bankCustomerConfigs.get(0).getBankCustomerNo());
			routeReturnBeans2.add(routeBean);
		}
		
		return routeReturnBeans2;
	}
	
	private  List<BankChannelRouteReturnBean> getBankChannelCost(TransType transType,List<BankChannel> bankChannels,double trxAmount){
		List<BankChannelRouteReturnBean> bankChannelParamList=new ArrayList<BankChannelRouteReturnBean>();
	    //消费与预授权需要计算银行成本
	    if(!TransType.AVAILABLE_FUNDSINQUIRY.equals(transType)&&!TransType.BALANCE_INQUIRY.equals(transType)){
	    	for(int i=0;i<bankChannels.size();i++){
	    		BankChannel bankChannel = bankChannels.get(i);
	    		BankChannelFeeReturnBean channelCost=bankChannelFeeService.getBankChannelCost(bankChannel, trxAmount);
	    		if(channelCost==null){//银行成本计算异常
	    			continue;
	    		}
	    		BankChannelRouteReturnBean bankChannelParam=new BankChannelRouteReturnBean();
	    		bankChannelParam.setBankChannel(bankChannel);
	    		bankChannelParam.setBankChannelCost(channelCost.getBankChannelCost());
	    		bankChannelParam.setBankChannelFeeCode(channelCost.getBankChannelFeeCode());
	    		bankChannelParam.setBankChannelRate(channelCost.getBankChannelRate());
	    	    bankChannelParamList.add(bankChannelParam);
	    	}
	    	
	    }else{//查询不需要计算银行成本
    		for(int i=0;i<bankChannels.size();i++){
	    		BankChannel bankChannel=bankChannels.get(i);
	    		BankChannelRouteReturnBean bankChannelParam=new BankChannelRouteReturnBean();
	    		bankChannelParam.setBankChannel(bankChannel);
	    		bankChannelParam.setBankChannelCost(0.00);
	    	    bankChannelParamList.add(bankChannelParam);
	    	  }
	    }
	    
	    return bankChannelParamList;
	}
	
	private TransRouteProfile getTransRouteProfile(PosRequest posRequest,String issuer,CardType cardType,String cardStyle,String customerNo,RouteType routeType){
		double trxAmount=posRequest.getAmount();
		
		//TransRouteConfig routeConfig=transRouteConfigService.findByCustomerNo(customerNo);
		TransRouteConfig routeConfig=transRouteConfigService.findByCustomerNoAndRouteType(customerNo,routeType);
		if(routeConfig!=null){
			List<TransRouteProfile> routeProfiles=transRouteProfileDao.findByTransRouteCode(routeConfig.getTransRouteCode());
			if(routeProfiles==null||routeProfiles.isEmpty()){
				return null;
			}
			
			List<TransRouteProfile> matchProfiles=new LinkedList<TransRouteProfile>();
			for(TransRouteProfile routeProfile:routeProfiles){
				TransAttributeBean transAttribute=routeProfile.getTransAttributeBean();
				boolean matchFlag=true;
				if(transAttribute==null){
					matchProfiles.add(routeProfile);
					continue;
				}
				
				String assignIssuer=transAttribute.getIssuer();
				if(matchFlag&&!StringUtils.isBlank(assignIssuer)){
					if(assignIssuer.contains("!")){
						String[] assignIssuers=assignIssuer.substring(1).split("\\,");
						for(String assIssuer:assignIssuers){
							if(issuer.equals(assIssuer)){
								matchFlag=false; break;
							}
						}
					}else{
						String[] assignIssuers=assignIssuer.split("\\,");
						boolean flag=false;
						for(String assIssuer:assignIssuers){
							if(issuer.equals(assIssuer)){
								flag=true;
							}
						}
						if(!flag){
							matchFlag=false;
						}
					}
				}
				
				String assignCardType=transAttribute.getCardType();
				if(matchFlag&&!StringUtils.isBlank(assignCardType)){
					if(assignCardType.contains("!")){
						String[] assignCardTypes=assignCardType.substring(1).split("\\,");
						for(String assCardType:assignCardTypes){
							if(cardType.name().equals(assCardType)){
								matchFlag=false; break;
							}
						}
					}else{
						String[] assignCardTypes=assignCardType.split("\\,");
						boolean flag=false;
						for(String assCardType:assignCardTypes){
							if(cardType.name().equals(assCardType)){
								flag=true;
							}
						}
						if(!flag){
							matchFlag=false;
						}
					}
				}
				
				String assignCardStyle=transAttribute.getCardStyle();
				if(matchFlag&&!StringUtils.isBlank(assignCardStyle)){
					if(assignCardStyle.contains("!")){
						String[] assignCardStyles=assignCardStyle.substring(1).split("\\,");
						for(String assCardStyle:assignCardStyles){
							if(assCardStyle.equals(cardStyle)){
								matchFlag=false; break;
							}
						}
					}else{
						String[] assignCardStyles=assignCardStyle.split("\\,");
						boolean flag=false;
						for(String assCardStyle:assignCardStyles){
							if(assCardStyle.equals(cardStyle)){
								flag=true; break;
							}
						}
						if(!flag){
							matchFlag=false;
						}
					}
				}
				
				String amountScope=transAttribute.getAmountScope();		//A~B,C~D
				if(matchFlag&&!StringUtils.isBlank(amountScope)){
					boolean flag=false;
					String[] amountScopeFix=amountScope.split("\\,");
					for(String amountScopeSub:amountScopeFix){
						String[] amountFix=amountScopeSub.split("\\~");
						double amountSmall=Double.parseDouble(amountFix[0]);
						double amountBig=Double.parseDouble(amountFix[1]);
						if(AmountUtil.compare(trxAmount, amountSmall)&&AmountUtil.bigger(amountBig, trxAmount)){
							flag=true;break;
						}
					}
					if(!flag){
						matchFlag=false;
					}
				}
				
				String dateScope=transAttribute.getDateScope();		//A~B,C~D 	
				if(matchFlag&&!StringUtils.isBlank(dateScope)){
					boolean flag=false;
					String[] dateScopeFix=dateScope.split("\\,");
					for(String dateScopeSub:dateScopeFix){
						String[] dateFix=dateScopeSub.split("\\~");
						int dateSmall=Integer.parseInt(dateFix[0]);
						int dateBig=Integer.parseInt(dateFix[1]);
						int today=new Day(new Date()).getDayOfMonth();
						if(today>=dateSmall&&today<=dateBig){
							flag=true;break;
						}
					}
					if(!flag){
						matchFlag=false;
					}
				}
				
				String timeScope=transAttribute.getTimeScope();		//A~B
				if(matchFlag&&!StringUtils.isBlank(timeScope)){
					boolean flag=false;
					String[] timeScopeFix=timeScope.split("\\,");
					for(String timeScopeSub:timeScopeFix){
						String[] timeFix=timeScopeSub.split("\\~");
						int timeSmall=Integer.parseInt(timeFix[0]);
						int timeBig=Integer.parseInt(timeFix[1]);
						int now=Integer.parseInt(DateUtil.formatDate(new Date(), "HH"));
						if(now>=timeSmall&&now<=timeBig&&timeBig!=0){
							flag=true;break;
						}else if (timeBig==0&&now>=timeSmall){
							flag=true;break;
						}
					}
					if(!flag){
						matchFlag=false;
					}
				}
				
				if(matchFlag){
					matchProfiles.add(routeProfile);
				}
			}
			
			if(matchProfiles.size()>1){
				throw new TransRunTimeException(TransExceptionConstant.ROUTE_PROFILE_OVER_SIZE);
			}else if (matchProfiles.isEmpty()){
				return null;
			}
			
			return matchProfiles.get(0); 
		}
		return null;
	}
	
	/**
	 * 检查银行通道及银行商户号是否可用 
	 */
	private BankChannelRouteReturnBean checkBankChannelAndBankCustomer(
			PosRequest posRequest, String issuer,String customerMccCode,CardType cardType, String bankChannelCode,
			String customerNo, String bankCustomerNo,String bankInterfaceCode,YesNo isIC) {
		BankChannelRouteReturnBean returnParam=new BankChannelRouteReturnBean();
		
		BankChannel bankChannelTemp=bankChannelDao.findByCode(bankChannelCode);
		BankChannel bankChannel=null;
		if(bankChannelTemp==null){
			bankChannel=getNewChannel( customerNo, bankCustomerNo, bankInterfaceCode, issuer, cardType);
		}else{
			//判断此银行通道是否可用
			bankChannel=bankChannelService.isBankChannelUseable(issuer, bankChannelCode, cardType, posRequest.getTransType(),isIC);
		}
		
		if(bankChannel==null){
			log.info("+++++++++++ bankChannelCode="+bankChannelCode+" bankChannel is null!");
			return null;
		}
		
		BankChannelFeeReturnBean channelCost=null;
		if(TransType.PRE_AUTH_COMP.equals(posRequest.getTransType())){//预授权完成需要计算银行成本
			channelCost=bankChannelFeeService.getBankChannelCost(bankChannel, posRequest.getAmount());
			if(channelCost==null){
				return null;
			}
			returnParam.setBankChannelCost(channelCost.getBankChannelCost());
			returnParam.setBankChannelRate(channelCost.getBankChannelRate());
			returnParam.setBankChannelFeeCode(channelCost.getBankChannelFeeCode());
	  	}
		
		//是否有可用终端
		if(!isAvailableBankChannelDetails(bankInterfaceCode,bankCustomerNo)){
			return null;
		}
		
		returnParam.setBankChannel(bankChannel);
		returnParam.setBankCustomerNo(bankCustomerNo);
		
		return returnParam;
	}
	
	private BankChannel getNewChannel(String customerNo, String bankCustomerNo,String bankInterfaceCode
			,String issuer,CardType cardType){
		List<BankCustomerConfig> bankCustomerConfigs=bankCustomerConfigDao.findByBankInterfaceAndBankCustomerNoAndCustomerNo(bankInterfaceCode, bankCustomerNo, customerNo);
		if(bankCustomerConfigs!=null){
			for(BankCustomerConfig bankCustomerConfig:bankCustomerConfigs){
				BankChannel bankChannelTemp1=bankChannelDao.findByCode(bankCustomerConfig.getBankChannelCode());
				if(checkBankChannelTemp(bankChannelTemp1,issuer,cardType)){
					return bankChannelTemp1;
				}
			}
		}
		return null;
	}
	private boolean checkBankChannelTemp(BankChannel bankChannel,String issuer,CardType cardType){
		//校验支持的发卡行与卡种	如：!,BOC,ICBC
		if(bankChannel.getSupportIssuer().contains("!")){
			String[] supportIssuers=bankChannel.getSupportIssuer().substring(1).split("\\,");
			for(String supportIssuer:supportIssuers){
				if(issuer.equals(supportIssuer)){
					return false;
				}
			}
		}else{
			String[] supportIssuers=bankChannel.getSupportIssuer().split("\\,");
			boolean flag=false;
			for(String supportIssuer:supportIssuers){
				if(issuer.equals(supportIssuer)){
					flag=true;
				}
			}
			if(!flag){
				return flag;
			}
		}
		
		String[] supportCardTypes=bankChannel.getSupportCardType().split("\\,");
		boolean flag1=false;
		for(String supportCardType:supportCardTypes){
			if(cardType.name().equals(supportCardType)){
				flag1=true;
			}
		}
		if(!flag1){
			return flag1;
		}
		
		return true;
	}

	private boolean isAvailableBankChannelDetails(String bankInterfaceCode,String bankCustomerNo){
		List<BankInterfaceTerminal> details=bankInterfaceTerminalDao.findByBankInterfaceAndBankCustomerNoAndStatusAndBankPosRunStatus(bankInterfaceCode, bankCustomerNo, Status.TRUE, BankPosRunStatus.SIGNIN);
		if(details!=null&&details.size()>0){
			for(int j=0;j<details.size();j++){
				 if(!BankPosUseStatus.SIGNING.equals(details.get(j).getBankPosUseStatus())){
					 return true;
				 }
			}
		}
		return false;//没有可用银行终端
	}
	
	
	public IBankChannelService getBankChannelService() {
		return bankChannelService;
	}

	public void setBankChannelService(IBankChannelService bankChannelService) {
		this.bankChannelService = bankChannelService;
	}

	public IBankChannelFeeService getBankChannelFeeService() {
		return bankChannelFeeService;
	}

	public void setBankChannelFeeService(
			IBankChannelFeeService bankChannelFeeService) {
		this.bankChannelFeeService = bankChannelFeeService;
	}

	public IBankCustomerConfigService getBankCustomerConfigService() {
		return bankCustomerConfigService;
	}

	public void setBankCustomerConfigService(
			IBankCustomerConfigService bankCustomerConfigService) {
		this.bankCustomerConfigService = bankCustomerConfigService;
	}

	public ITransRouteConfigService getTransRouteConfigService() {
		return transRouteConfigService;
	}

	public void setTransRouteConfigService(
			ITransRouteConfigService transRouteConfigService) {
		this.transRouteConfigService = transRouteConfigService;
	}

	
}

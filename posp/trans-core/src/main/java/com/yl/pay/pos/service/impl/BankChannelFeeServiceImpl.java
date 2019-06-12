package com.yl.pay.pos.service.impl;

import com.pay.common.util.AmountUtil;
import com.yl.pay.pos.bean.BankChannelFeeReturnBean;
import com.yl.pay.pos.entity.BankChannel;
import com.yl.pay.pos.entity.BankChannelFee;
import com.yl.pay.pos.enums.ComputeFeeMode;
import com.yl.pay.pos.exception.TransExceptionConstant;
import com.yl.pay.pos.exception.TransRunTimeException;
import com.yl.pay.pos.service.IBankChannelFeeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Title: 银行通道费率规则服务
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

public class BankChannelFeeServiceImpl extends BaseService implements IBankChannelFeeService {
	private static final Log log=LogFactory.getLog(BankChannelFeeServiceImpl.class);
//	public static final String bankChannelCodes = PropertyUtil.getInstance("transConfig").getProperty("limitChannel");
	@Override
	public BankChannelFeeReturnBean getBankChannelCost(BankChannel bankChannel,double trxAmount) {
		double cost=0.00;
		BankChannelFee finalFeeRule=bankChannelFeeDao.findByBankChannelCode(bankChannel.getCode());
		if(finalFeeRule==null){
			return null;
		}
		
		//根据计费方式进行手续费计算
//		Double lowLimit=finalFeeRule.getLowerLimitFee();    		//暂不使用下限功能
		Double upLimit=finalFeeRule.getUpperLimitFee();
		ComputeFeeMode computeMode=finalFeeRule.getComputeFeeMode();
		Double fixedFee=finalFeeRule.getFixedFee();
		/*
		 * eg:
			0.016
			0.0035|4
			0.0035+0.0005
			0.014|16+0.002|10+0.0002|2
		 */
		if(ComputeFeeMode.RATIO.equals(computeMode)){
			String[] mainRateStrs=finalFeeRule.getRate().split("\\+");
			for(String mainRateStr:mainRateStrs){
				double tempCost=0.00;
				if(upLimit!=null&&AmountUtil.equal(upLimit, 1.00)){
					String[] rateStrs=mainRateStr.split("\\|");
					double rate=Double.parseDouble(rateStrs[0]);
					double upLimitFee=Double.parseDouble(rateStrs[1]);
					tempCost=AmountUtil.round(AmountUtil.mul(trxAmount, rate), 2);
					if(AmountUtil.bigger(tempCost, upLimitFee))
					{
						tempCost=upLimitFee;
					}
				}else{
					double rate=Double.parseDouble(mainRateStr);
					tempCost=AmountUtil.round(AmountUtil.mul(trxAmount, rate), 2);
				}
				cost=AmountUtil.add(cost, tempCost);
			}
		}else if(ComputeFeeMode.FIXED.equals(computeMode)){
			cost=fixedFee;
		}else if(ComputeFeeMode.COMPLEX.equals(computeMode)){
			String[] mainRateStrs=finalFeeRule.getRate().split("\\+");
			for(String mainRateStr:mainRateStrs){
				double tempCost=0.00;
				if(upLimit!=null&&AmountUtil.equal(upLimit, 1.00)){
					String[] rateStrs=mainRateStr.split("\\|");
					double rate=Double.parseDouble(rateStrs[0]);
					double upLimitFee=Double.parseDouble(rateStrs[1]);
					tempCost=AmountUtil.round(AmountUtil.mul(trxAmount, rate), 2);
					if(AmountUtil.bigger(tempCost, upLimitFee)){
						tempCost=upLimitFee;
					}
				}else{
					double rate=Double.parseDouble(mainRateStr);
					tempCost=AmountUtil.round(AmountUtil.mul(trxAmount, rate), 2);
				}
				cost=AmountUtil.add(cost, tempCost);
			}
			cost=AmountUtil.add(cost, fixedFee);
		}
		
//		if(lowLimit!=null&&AmountUtil.bigger(lowLimit, -1)&&AmountUtil.bigger(lowLimit, cost)){
//			cost=lowLimit;
//		}
//		if(upLimit!=null&&AmountUtil.bigger(upLimit, -1)&&AmountUtil.bigger(cost, upLimit)){
//			cost=upLimit;
//		}
		
		if(AmountUtil.bigger(0.00, cost)){
			throw new TransRunTimeException(TransExceptionConstant.AMOUNT_INVALID);
		}
		
		//手续费小数点后2位进行四舍五入
		cost=AmountUtil.round(cost, 2);
		
		return new BankChannelFeeReturnBean(finalFeeRule.getCode(),finalFeeRule.getRate(),cost);
	}

}

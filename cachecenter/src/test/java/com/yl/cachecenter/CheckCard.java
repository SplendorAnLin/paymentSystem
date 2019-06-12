package com.yl.cachecenter;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * CheckCard
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月24日
 * @version V1.0.0
 */
public class CheckCard {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(checkCard("6225768313509539"));
	}
	
	public static boolean checkCard(String cardNo){
		if(!NumberUtils.isNumber(cardNo))
			return false;
		
		String [] nums = cardNo.split("");
		int sum = 0;
		int index = 1;
		for(int i = 0 ; i < nums.length; i++){
			if((i+1)%2==0){
				if("".equals(nums[nums.length-index])){
					continue;
				}
				int tmp = Integer.parseInt(nums[nums.length-index])*2;
				if(tmp >= 10){
					String[] t = String.valueOf(tmp).split("");
					tmp = Integer.parseInt(t[1]) + Integer.parseInt(t[2]);
				}
				sum+=tmp;
			}else{
				if("".equals(nums[nums.length-index]))
					continue;
				sum+=Integer.parseInt(nums[nums.length-index]);
			}
			index ++;
		}
		if(sum%10 != 0){
			return false;
		}
		return true;
	}
}

package com.yl.dpay.core.Utils;

import com.pay.common.util.SequenceUtils;

/**
 * Code工具
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月15日
 * @version V1.0.0
 */
public class CodeUtil {
	
	public static String getBatchNo(long key){
		String code = SequenceUtils.createSequence(key, new int[] {8,3,5,7,0,6,4,9}, new int[] {2,12}, new int[] {5,9}, new int[] {3,6}, new int[] {7,9});
		return "TD"+code;
	}
	
	public static String getHandleRecordCode(long key){
		String code = SequenceUtils.createSequence(key, new int[] {8,9,1,2,3,0,7,5}, new int[] {2,12}, new int[] {5,9}, new int[] {3,6}, new int[] {7,9});
		return code;
	}
	
	public static String getRefundCode(long key){
		String code = SequenceUtils.createSequence(key, new int[] {8,0,6,3,4,7,5,9}, new int[] {2,12}, new int[] {5,9}, new int[] {3,6}, new int[] {7,9});
		return code;
	}
	
	public static String getRequestFlowNo(long key){
		String code = SequenceUtils.createSequence(key, new int[] {8,9,5,1,8,0,7,4}, new int[] {2,12}, new int[] {5,9}, new int[] {3,6}, new int[] {7,9});
		return "DF"+code;
	}
	
	public static String getTargetAccountCode(long key){
		String code = SequenceUtils.createSequence(key, new int[] {8,3,1,5,0,9}, new int[] {2,12}, new int[] {5,9}, new int[] {3,6}, new int[] {7,9});
		return code;
	}
	
	public static void main(String[] args){
		System.out.println(CodeUtil.getTargetAccountCode(System.nanoTime()%100000));
	}
	
}

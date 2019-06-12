package com.yl.pay.pos.util;

import com.lefu.commons.utils.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author jayzhangxiao
 *
 */
public class CodeBuilder {

	private static int seq = 0;
	
	public static String build(String start,String dateFormat,int nums){
		if(StringUtils.isBlank(dateFormat)){
			return start + "-" + getOrderIdByUUId();
		}
		return start + "-" + (new SimpleDateFormat(dateFormat).format(new Date())) + "-" + getOrderIdByUUId();
	}
	
	public static String build(String start){
		return start + "-" + new StringBuffer().append(start).append("-").append(getOrderIdByUUId()).toString();
	}
	
	public static String build(String start,String dateFormat){
		if(StringUtils.isBlank(dateFormat)){
			return start + "-" + getOrderIdByUUId();
		}
		return start + "-" + (new SimpleDateFormat(dateFormat).format(new Date())) + "-" + getOrderIdByUUId();
	}
	
	public static String build(String dateFormat,int nums){
		if(StringUtils.isBlank(dateFormat)){
			return (getOrderIdByUUId());
		}
		return new SimpleDateFormat(dateFormat).format(new Date()) + "-" + getOrderIdByUUId();
	}
	
	public static String getOrderIdByUUId() {
        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
        // 0 代表前面补充0     
        // 4 代表长度为4     
        // d 代表参数为正数型
        return machineId + String.format("%011d", hashCodeV);
    }

    public static String getPosSequnce(){
		return getOrderIdByUUId();
	}

	public static long getSequnce(){
		seq ++;
    	if(seq > 999999){
			seq = 0;
			return seq;
		}
		return seq;
	}
	
	public static void main(String[] args) {
		for(int i=0;i<1000001;i++){
			System.out.println(getSequnce());
		}
	}

}

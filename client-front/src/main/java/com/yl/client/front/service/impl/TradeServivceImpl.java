package com.yl.client.front.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.client.front.service.TradeServivce;
import com.yl.online.trade.hessian.OnlineTradeQueryHessianService;

@Service("tradeServivce")
public class TradeServivceImpl implements TradeServivce{
	
	@Resource
	OnlineTradeQueryHessianService onlineTradeQueryHessianService;
	
	public List<Map<String, Object>> findWeekOrder(String customerNo){
		Map<String, Object> params=new HashMap<>();
		params.put("receiver", customerNo);
		List<Map<String, Object>> info=onlineTradeQueryHessianService.orderWeekSumByDay(params);
		params.clear();
		ArrayList<String> time=getWeekLast(7);
		if (info.size()>0) {
			for (int i = 0; i < time.size(); i++) {
				boolean exists=false;
				for (Map<String, Object> map : info) {
					if (map.get("day").equals(time.get(i))) {
						exists=true;
			            break;
					}
				}
				if(!exists){
					Map<String, Object> sum=new HashMap<>();
					sum.put("day",time.get(i));
					sum.put("AMOUNT", 0);
					sum.put("count", 0);
			        info.add(sum);
			    }
			}
		}else {
			for (int i = 0; i < time.size(); i++) {
					Map<String, Object> sum=new HashMap<>();
					sum.put("day",time.get(i));
					sum.put("AMOUNT", 0);
					sum.put("count", 0);
			        info.add(sum);
			    }
		}
		return sumSort(info);
	}
	
	public List<Map<String, Object>> sumSort(List<Map<String, Object>> info){
		for(int i=0;i<info.size()-1;i++){
            for(int j=0;j<info.size()-i-1;j++){//比较两个整数
                if(Integer.valueOf(info.get(j).get("day").toString())<Integer.valueOf(info.get(j+1).get("day").toString())){
                    /*交换*/
                	Map<String, Object> temp=info.get(j);
                    info.set(j, info.get(j+1));
                    info.set(j+1, temp);
                }
            }
        }
		return info;
	}
	
	@Override
	public List<Map<String, Object>> findOrderApp(String customerNo) {
		Map<String, Object> params=new HashMap<>();
		params.put("receiver", customerNo);
		return onlineTradeQueryHessianService.findOrderApp(params);
	}

	/**
     * 获取过去或者未来 任意天内的日期数组
     * @param intervals      intervals天内
     * @return              日期数组
     */
    public static ArrayList<String> getWeekLast(int intervals ) {
        ArrayList<String> pastDaysList = new ArrayList<>();
        for (int i = 0; i <intervals; i++) {
            pastDaysList.add(getPastDate(i));
        }
        return pastDaysList;
    }
    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String result = format.format(today);
        return result;
    }

    /**
     * 获取未来 第 past 天的日期
     * @param past
     * @return
     */
    public static String getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String result = format.format(today);
        return result;
    }
}

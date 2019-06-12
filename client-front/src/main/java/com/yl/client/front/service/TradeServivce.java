package com.yl.client.front.service;

import java.util.List;
import java.util.Map;

public interface TradeServivce {
	public List<Map<String, Object>> findWeekOrder(String customerNo);
	public List<Map<String, Object>> findOrderApp(String customerNo);
}

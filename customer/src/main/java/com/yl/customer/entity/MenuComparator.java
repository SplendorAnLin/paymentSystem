package com.yl.customer.entity;

import java.util.Comparator;

/**
 * 菜单排序Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月15日
 * @version V1.0.0
 */
public class MenuComparator implements Comparator {

	public int compare(Object o1, Object o2) {

		Menu m1 = (Menu) o1;
		Menu m2 = (Menu) o2;
		if (m1.getDisplayOrder() > m2.getDisplayOrder()) {
			return 1;
		}
		if (m1.getDisplayOrder() == m2.getDisplayOrder()) {
			return 0;
		} 
		return -1;
	}
}

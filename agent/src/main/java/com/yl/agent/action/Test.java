package com.yl.agent.action;

import java.util.ArrayList;
import java.util.List;

import com.yl.agent.entity.Role;

import net.sf.json.JSONArray;

/**
 * 测试
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月28日
 * @version V1.0.0
 */
public class Test {

	public static void main(String[] args) {
		List<Role> list = new ArrayList<>();
		Role r = new Role();
		r.setName("Test");
		list.add(r);
		System.out.println(JSONArray.fromObject(r).toString());
	}

}

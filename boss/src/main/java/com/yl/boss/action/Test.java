package com.yl.boss.action;

import java.util.ArrayList;
import java.util.List;

import com.yl.boss.entity.Role;

import net.sf.json.JSONArray;

public class Test {

	public static void main(String[] args) {
		List<Role> list = new ArrayList<>();
		Role r = new Role();
		r.setName("Test");
		list.add(r);
		System.out.println(JSONArray.fromObject(r).toString());
	}

}

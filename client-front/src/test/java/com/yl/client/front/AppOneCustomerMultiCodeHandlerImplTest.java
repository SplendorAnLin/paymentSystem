package com.yl.client.front;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.google.gson.Gson;
import com.yl.client.front.context.SpringRootConfig;
import com.yl.client.front.handler.AppOneCustomerMultiCodeHandler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringRootConfig.class })
@ActiveProfiles("development")
@TransactionConfiguration(defaultRollback = false)
public class AppOneCustomerMultiCodeHandlerImplTest {

	@Resource
	AppOneCustomerMultiCodeHandler appOneCustomerMultiCodeHandler;
	
	@Test
	public void findBankCustomerByPage(){
		Map<String,Object> map = appOneCustomerMultiCodeHandler.findBankCustomerByPage(1, 5, "1101", "5012");
		
		Gson gson = new Gson();
		System.out.println(gson.toJson(map));
	}
	
	@Test
	public void findProvince(){
		Map<String,Object> map = appOneCustomerMultiCodeHandler.findProvince();
		
		Gson gson = new Gson();
		System.out.println(gson.toJson(map));
	}
	

	@Test
	public void findCityByProvince(){
		Map<String,Object> map = appOneCustomerMultiCodeHandler.findCityByProvince("11");
		
		Gson gson = new Gson();
		System.out.println(gson.toJson(map));
	}
	
	@Test
	public void findMcc(){
		Map<String,Object> map = appOneCustomerMultiCodeHandler.findMcc();
		
		Gson gson = new Gson();
		System.out.println(gson.toJson(map));
	}
	
	@Test
	public void followBankCustomer(){
		Map<String,Object> map = appOneCustomerMultiCodeHandler.followBankCustomer("C100255", "305220050650002");
		
		Gson gson = new Gson();
		System.out.println(gson.toJson(map));
	}
}

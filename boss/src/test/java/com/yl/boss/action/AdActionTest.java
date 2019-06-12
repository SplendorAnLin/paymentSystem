package com.yl.boss.action;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.lefu.commons.cache.util.CacheUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import sun.misc.BASE64Encoder;

import com.yl.boss.BaseTest;
import com.yl.boss.api.interfaces.CustomerInterface;

public class AdActionTest extends BaseTest{
	
	@Autowired
	private CustomerInterface customerInterface;
	
	@Test
	public void testOpenAdd() {
		
		Map<String, Object> params = new HashMap<>();
		String aaa = getImageStr("D:/close.png");
		params.put("handheldBank", aaa);
		params.put("handheldId", aaa);
		params.put("accountName", "比呢哦");
		params.put("accountNo", "6226226700369628");
		params.put("openBankName", "北京难打");
		params.put("idCard", "230107199102282477");
		params.put("customerNo", "C100251");
		String aa = customerInterface.customerSettle(params);
		System.out.println(aa);
	}

	/**
	 * @Description: 根据图片地址转换为base64编码字符串
	 * @Author: 
	 * @CreateTime: 
	 * @return
	 */
	public static String getImageStr(String imgFile) {
	    InputStream inputStream = null;
	    byte[] data = null;
	    try {
	        inputStream = new FileInputStream(imgFile);
	        data = new byte[inputStream.available()];
	        inputStream.read(data);
	        inputStream.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    // 加密
	    BASE64Encoder encoder = new BASE64Encoder();
	    return encoder.encode(data);
	}

	@Test
	public void redis(){
		System.out.println(CacheUtils.get("1.180.209.233", String.class));
	}

}

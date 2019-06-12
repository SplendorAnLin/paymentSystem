package com.yl.cachecenter.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.cachecenter.model.IIN;
import com.yl.cachecenter.service.IINService;

/**
 * 发行者识别号码访问控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月24日
 * @version V1.0.0
 */
@Controller
@RequestMapping("/iin")
public class IINController {
	private static final Logger logger = LoggerFactory.getLogger(IINController.class);
	@Resource
	private IINService iinService;
	
	/**
	 * 卡识别
	 * @param cardNo 卡号
	 * @param fields 返回的字段名列表
	 */
	@RequestMapping("recognition")
	public void recognition(HttpServletResponse response, @RequestParam("cardNo") String cardNo,
			@RequestParam(value = "fields", required = false) String[] fields){
		
		String result = null;
		Map<String, Object> map = new LinkedHashMap<>();
		try {
			if(fields ==null || fields.length==0){
				fields  = new String[]{"providerCode","cardType"};
			}else{
				boolean flag = true;
				String [] tmp = new String[fields.length+1];
				for(int i=0;i<fields.length;i++){
					if("providerCode".equals(fields[i])){
						flag = false;
						break;
					}
					tmp [i] = fields[i];
				}
				if(flag){
					tmp [fields.length] = "providerCode";
					fields = tmp;
				}
			}
			IIN iin = iinService.recognition(cardNo, fields);
			if (fields != null) {
				for (String field : fields) {
					map.put(field, PropertyUtils.getSimpleProperty(iin, field));
				}
			}
			result = JsonUtils.toJsonString(map);
		} catch (Exception e) {
			logger.error("", e);
			map.put("code", 10001);
			map.put("message", "ERROR");
			result = JsonUtils.toJsonString(map);
		}
		response.setContentType("text/plain;charset=UTF-8");
		try {
			response.getWriter().write(result);
		} catch (IOException e) {
			logger.error("", e);
		}
	}
	
	/**
	 * 卡批量识别
	 * @param cardNos 卡号集合
	 * @param fields 返回的字段名列表
	 */
	@RequestMapping("batchRecognition")
	public void batchRecognition(HttpServletResponse response, @RequestParam("cardNos") List<String> cardNos,
			@RequestParam(value = "fields", required = false) String[] fields){
		String result = null;
		try {
			if(fields ==null || fields.length==0){
				fields  = new String[]{"providerCode"};
			}
			List<Map<String,Object>> list = new ArrayList<>();
			for(String cardNo : cardNos){
				IIN iin = iinService.recognition(cardNo, fields);
				Map<String, Object> map = new LinkedHashMap<>();
				if (fields != null) {
					for (String field : fields) {
						map.put(field, PropertyUtils.getSimpleProperty(iin, field));
					}
				}
				list.add(map);
			}
			result = JsonUtils.toJsonString(list);
		} catch (Exception e) {
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("code", 10001);
			map.put("message", "ERROR");
			result = JsonUtils.toJsonString(map);
			logger.error("", e);
		}
		response.setContentType("text/plain;charset=UTF-8");
		try {
			response.getWriter().write(result);
		} catch (IOException e) {
			logger.error("", e);
		}
	}

	/**
	 * 发行者识别号码维护
	 * @param separator 各字段之间的分隔符
	 * @param txt 文本文件
	 * @param text 文本框
	 * @return
	 */
	@RequestMapping("add")
	public String add(@RequestParam(value = "separator", defaultValue = ",") String separator,
			@RequestParam(value = "iinTxt", required = false) MultipartFile txt, @RequestParam(value = "iinText", required = false) String text) {
		List<String> errorMsg = new ArrayList<>();
		try {
			List<String> rows = new ArrayList<>();
			text = StringUtils.safeValue(text);
			rows.addAll(Arrays.asList(text.split("\r\n")));
			if (txt != null) {
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(txt.getInputStream()), 1024 * 2048)) {
					for (String row = reader.readLine(); row != null; row = reader.readLine()) {
						rows.add(row);
					}
				}
			}
			List<IIN> iins = textToCnaps(separator, rows, errorMsg);
			iinService.add(iins);
		} catch (Exception e) {
			logger.error("", e);
			errorMsg.add(e.getMessage());
		}
		return "/iin/add";
	}
	
	/**
	 * 银行卡号校验
	 * @param cardNo 银行卡号
	 * @return boolean 正确卡号:true;错误卡号:false
	 */
	@RequestMapping("checkCard")
	public boolean checkCard(@RequestParam("cardNo") String cardNo){
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

	/**
	 * 从文本形式转为IIN集合
	 * @param separator 分隔符
	 * @param text 文本内容
	 * @param errorMsg 解析错误的信息
	 * @return IIN集合
	 */
	private List<IIN> textToCnaps(String separator, List<String> rows, List<String> errorMsg) {
		List<IIN> iins = new ArrayList<>();
		try {
			for (String row : rows) {
				String[] cnapsStrArray = row.split(separator);
				if (cnapsStrArray.length != 8) {
					errorMsg.add(row);
					continue;
				}
				IIN iin = new IIN();
				iin.setAgencyCode(cnapsStrArray[1]);
				iin.setAgencyName(cnapsStrArray[0]);
				iin.setCardName(cnapsStrArray[2]);
				iin.setCardType(cnapsStrArray[6]);
				iin.setCode(cnapsStrArray[5]);
				iin.setLength(Integer.parseInt(cnapsStrArray[4]));
				iin.setPanLength(Integer.parseInt(cnapsStrArray[3]));
				iin.setProviderCode(cnapsStrArray[7]);
				iins.add(iin);
			}
		} catch (Exception e) {
			logger.error("", e.getMessage());
		}
		return iins;
	}

}

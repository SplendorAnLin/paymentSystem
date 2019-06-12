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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.cachecenter.model.AdministrativeDivision;
import com.yl.cachecenter.model.LefuCodeToUnionPayCode;
import com.yl.cachecenter.service.AdministrativeDivisionService;

/**
 * 行政区划码访问控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月24日
 * @version V1.0.0
 */
@Controller
@RequestMapping("/ad")
public class AdministrativeDivisionController {
	private static final Logger logger = LoggerFactory.getLogger(AdministrativeDivisionController.class);
	@Resource
	private AdministrativeDivisionService administrativeDivisionService;
	
	/**
	 * 行政地区码查询
	 * @param adCode 行政地区码
	 */
	@RequestMapping("search")
	public void search(HttpServletResponse response, @RequestParam("adCode") String adCode) throws IOException {
		String result = null;
		try {
			AdministrativeDivision ad = administrativeDivisionService.search(adCode);
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("adCode", ad.getAdCode());
			map.put("adName", ad.getAdName());
			result = JsonUtils.toJsonString(map);
		} catch (Exception e) {
			logger.error("", e);
		}
		response.setContentType("text/plain;charset=UTF-8");
		response.getWriter().write(result);
	}
	
	/**
	 * 行政地区码维护
	 * @param separator 各字段之间的分隔符
	 * @param txt 文本文件
	 * @param text 文本框
	 * @return
	 */
	@RequestMapping("add")
	public String add(@RequestParam(value = "separator", defaultValue = ",") String separator,
			@RequestParam(value = "adTxt", required = false) MultipartFile txt, @RequestParam(value = "adText", required = false) String text) {
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
			List<AdministrativeDivision> ads = textToCnaps(separator, rows, errorMsg);
			administrativeDivisionService.add(ads);
		} catch (Exception e) {
			logger.error("", e);
			errorMsg.add(e.getMessage());
		}
		return "/ad/add";
	}
	
	/**
	 * 从文本形式转为AdministrativeDivision集合
	 * @param separator 分隔符
	 * @param text 文本内容
	 * @param errorMsg 解析错误的信息
	 * @return IIN集合
	 */
	private List<AdministrativeDivision> textToCnaps(String separator, List<String> rows, List<String> errorMsg) {
		List<AdministrativeDivision> ads = new ArrayList<>();
		for (String row : rows) {
			String[] cnapsStrArray = row.split(separator);
			if (cnapsStrArray.length != 2) {
				errorMsg.add(row);
				continue;
			}
			AdministrativeDivision ad = new AdministrativeDivision();
			ad.setAdCode(cnapsStrArray[1]);
			ad.setAdName(cnapsStrArray[0]);
			ads.add(ad);
		}
		return ads;
	}
	
	/**
	 * 乐富地区码匹配银联地区码维护
	 * @param separator 各字段之间的分隔符
	 * @param txt 文本文件
	 * @param text 文本框
	 * @return
	 */
	@RequestMapping("addLefuCodeToUnionPay")
	public String addLefuCodeToUnionPay(@RequestParam(value = "separator", defaultValue = ",") String separator,
			@RequestParam(value = "adTxt", required = false) MultipartFile txt, @RequestParam(value = "adText", required = false) String text) {
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
			List<LefuCodeToUnionPayCode> lps = textToLefuCodeToUnion(separator, rows, errorMsg);
			administrativeDivisionService.addLefuCodeToUnionPayCode(lps);
		} catch (Exception e) {
			logger.error("", e);
			errorMsg.add(e.getMessage());
		}
		return "/ad/addLefuCodeToUnionPay";
	}
	
	/**
	 * 从文本形式转为LefuCodeToUnionPayCode集合
	 * @param separator 分隔符
	 * @param text 文本内容
	 * @param errorMsg 解析错误的信息
	 * @return IIN集合
	 */
	private List<LefuCodeToUnionPayCode> textToLefuCodeToUnion(String separator, List<String> rows, List<String> errorMsg) {
		List<LefuCodeToUnionPayCode> lps = new ArrayList<>();
		for (String row : rows) {
			String[] cnapsStrArray = row.split(separator);
			if (cnapsStrArray.length != 2) {
				errorMsg.add(row);
				continue;
			}
			LefuCodeToUnionPayCode lp = new LefuCodeToUnionPayCode();
			lp.setLefuCode(cnapsStrArray[0]);
			lp.setUnionPayCode(cnapsStrArray[1]);
			lps.add(lp);
		}
		return lps;
	}

}

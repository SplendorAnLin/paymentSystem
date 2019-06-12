package com.yl.cachecenter.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.cachecenter.model.Station;
import com.yl.cachecenter.service.StationService;

/**
 * 站点信息访问控制器
 * @author AnLin
 * @since 2015年9月11日
 */

/**
 * 站点信息访问控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月24日
 * @version V1.0.0
 */
@Controller
@RequestMapping("/station")
public class StationController {
	private static final Logger logger = LoggerFactory.getLogger(StationController.class);
	@Resource
	private StationService stationService;

	/**
	 * 使用分词匹配银行名称
	 * @param response
	 * @param word 搜索关键词
	 * @param limit 返回条数
	 * @param fields 返回的字段名列表
	 * @param routeNo 路线编号
	 * @param flag 上下行编号
	 */
	@RequestMapping("suggestSearch")
	public void suggestSearch(HttpServletResponse response,
			@RequestParam(value = "word", required=false) String word,
			@RequestParam(value = "limit", defaultValue = "10") int limit,
			@RequestParam(value = "fields", required = false) String[] fields,
			@RequestParam(value = "routeNo", required=false) String routeNo,
			@RequestParam(value = "flag", required=false) String flag){
		String json = "";
		try {
			List<Station> stations = stationService.suggestSearch(URLDecoder.decode(word==null?"":word, "utf-8"), limit, fields, routeNo, flag);
			// 为减少传输字节数
			List<Map<String, Object>> list = new ArrayList<>();
			for (Station station : stations) {
				Map<String, Object> map = new LinkedHashMap<>();
				map.put("code", station.getCode());
				map.put("name", station.getName());
				map.put("routeNo", station.getRouteNo());
				if (fields != null) {
					for (String field : fields) {
						map.put(field, PropertyUtils.getSimpleProperty(station, field));
					}
				}
				list.add(map);
			}
			json = JsonUtils.toJsonString(list);
		} catch (Exception e) {
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("code", 10001);
			map.put("message", "ERROR");
			json = JsonUtils.toJsonString(map);
			logger.error("", e);
		}
		response.setContentType("text/plain;charset=UTF-8");
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			logger.error("", e);
		}
	}
	
	/**
	 * 联行号维护
	 * @param separator 各字段之间的分隔符
	 * @param txt 文本文件
	 * @param text 文本框
	 * @return
	 */
	@RequestMapping("add")
	public String add(@RequestParam(value = "separator", defaultValue = ",") String separator,
			@RequestParam(value = "stationTxt", required = false) MultipartFile txt, @RequestParam(value = "stationTxt", required = false) String text) {
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
			List<Station> stations = textToStation(separator, rows, errorMsg);
			stationService.add(stations);
		} catch (Exception e) {
			logger.error("", e);
			errorMsg.add(e.getMessage());
		}
		ModelAndView result = new ModelAndView();
		result.addObject("errorMsg", errorMsg);
		return "/station/add";
	}

	/**
	 * 从文本形式转为Station集合
	 * @param separator 分隔符
	 * @param text 文本内容
	 * @param errorMsg 解析错误的信息
	 * @return Station集合
	 */
	private List<Station> textToStation(String separator, List<String> rows, List<String> errorMsg) {
		List<Station> stations = new ArrayList<>();
		for (String row : rows) {
			String[] stationStrArray = row.split(separator);
			if (stationStrArray.length != 8) {
				errorMsg.add(row);
				continue;
			}
			Station station = new Station();
			station.setRouteNo(stationStrArray[0]);
			station.setFlag(stationStrArray[1]);
			station.setStartStation(stationStrArray[2]);
			station.setEndStation(stationStrArray[3]);
			station.setStartTime(stationStrArray[4]);
			station.setEndTime(stationStrArray[5]);
			station.setCode(stationStrArray[0]+"."+stationStrArray[6]);
			station.setStationNo(stationStrArray[6]);
			station.setName(stationStrArray[7]);
			
			stations.add(station);
		}
		return stations;
	}

}

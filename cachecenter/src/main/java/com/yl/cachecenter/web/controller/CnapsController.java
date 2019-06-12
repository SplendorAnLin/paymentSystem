package com.yl.cachecenter.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.cachecenter.model.Cnaps;
import com.yl.cachecenter.model.LefuCodeToUnionPayCode;
import com.yl.cachecenter.service.AdministrativeDivisionService;
import com.yl.cachecenter.service.CnapsService;

/**
 * 联行号访问控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月24日
 * @version V1.0.0
 */
@Controller
@RequestMapping("/cnaps")
public class CnapsController {
	private static final Logger logger = LoggerFactory.getLogger(CnapsController.class);
	@Resource
	private CnapsService cnapsService;
	@Resource
	private AdministrativeDivisionService administrativeDivisionService;

	/**
	 * 使用分词匹配银行名称
	 * @param response
	 * @param word 搜索关键词
	 * @param limit 返回条数
	 * @param fields 返回的字段名列表
	 * @param providerCode 银行编码
	 * @param areaCode 地区编码
     * @param clearBankLevel 清分行级别
	 */
	@RequestMapping("suggestSearch")
	public void suggestSearch(HttpServletResponse response, @RequestParam(value="word", required=false) String word, @RequestParam(value = "limit", defaultValue = "10") int limit,
			@RequestParam(value = "fields", required = false) String[] fields, @RequestParam(value="providerCode", required=false) String providerCode, 
			@RequestParam(value="areaCode", required=false) String areaCode,
			@RequestParam(value="clearBankLevel", defaultValue = "0", required=false) int clearBankLevel){
		String json = "";
		try {
			List<Cnaps> cnapses = cnapsService.suggestSearch(URLDecoder.decode(word==null?"":word, "utf-8"), limit, fields,providerCode,areaCode,clearBankLevel);
			// 为减少传输字节数
			List<Map<String, Object>> list = new ArrayList<>();
			for (Cnaps cnaps : cnapses) {
				Map<String, Object> map = new LinkedHashMap<>();
				map.put("code", cnaps.getCode());
				if (fields != null) {
					for (String field : fields) {
						map.put(field, PropertyUtils.getSimpleProperty(cnaps, field));
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
	 * 使用分词匹配银行名称
	 * @param response
	 * @param word 搜索关键词
	 * @param limit 返回条数
	 * @param fields 返回的字段名列表
	 * @param providerCode 银行编码
	 * @param areaCode 乐富地区编码
	 */
	@RequestMapping("search")
	public void search(HttpServletResponse response, @RequestParam(value="word", required=false) String word, @RequestParam(value = "limit", defaultValue = "10") int limit,
			@RequestParam(value = "fields", required = false) String[] fields, @RequestParam(value="providerCode", required=false) String providerCode, 
			@RequestParam(value="areaCode", required=false) String areaCode,
			@RequestParam(value="clearBankLevel", defaultValue = "0", required=false) int clearBankLevel){
		String json = "";
		try {
			if(areaCode != null && !areaCode.equals("")){
				LefuCodeToUnionPayCode lefuCodeToUnionPayCode = administrativeDivisionService.searchLefuCodeToUnionPayCode(areaCode);
				if(lefuCodeToUnionPayCode == null || lefuCodeToUnionPayCode.getUnionPayCode() == null || lefuCodeToUnionPayCode.getUnionPayCode().equals("")){
					areaCode = "999999";
				}else{
					areaCode = lefuCodeToUnionPayCode.getUnionPayCode();
				}
			}
			List<Cnaps> cnapses = cnapsService.suggestSearch(URLDecoder.decode(word==null?"":word, "utf-8"), limit, fields,providerCode,areaCode,clearBankLevel);
			// 为减少传输字节数
			List<Map<String, Object>> list = new ArrayList<>();
			for (Cnaps cnaps : cnapses) {
				Map<String, Object> map = new LinkedHashMap<>();
				map.put("code", cnaps.getCode());
				if (fields != null) {
					for (String field : fields) {
						map.put(field, PropertyUtils.getSimpleProperty(cnaps, field));
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
	 * 批量使用分词匹配银行名称
	 * @param response
	 * @param words 搜索关键词
	 * @param fields 返回的字段名列表
	 * @param providerCode 银行编码
	 * @param areaCode 地区编码
	 */
	@RequestMapping("batchSearch")
	public void batchSearch(HttpServletResponse response, @RequestParam(value="words", required=false) List<String> words, @RequestParam(value = "limit", defaultValue = "10") int limit,
			@RequestParam(value = "fields", required = false) String[] fields, @RequestParam(value="providerCode", required=false) List<String> providerCode, 
			@RequestParam(value="areaCode", required=false) List<String> areaCode,
			@RequestParam(value="clearBankLevel", defaultValue = "0", required=false) int clearBankLevel){
		String json = "";
		try {
			List<List<Cnaps>> lists = new ArrayList<>();
			int index = 0;
			try {
				index = words.size();
			} catch (Exception e) {
				try {
					index = providerCode.size();
				} catch (Exception e1) {
					try{
						index = areaCode.size();
					}catch(Exception e2){
						throw new RuntimeException("batchSearch : Params is null");
					}
				}
			}
			for(int i =0 ; i< index;i++){
				String keyWord = URLDecoder.decode(words.get(i), "UTF-8");
				List<Cnaps> cnapses = cnapsService.suggestSearch(words==null?null:keyWord, limit, fields, providerCode==null?null:providerCode.get(i),areaCode==null?null:areaCode.get(i),clearBankLevel);
				
				if(keyWord == null){
					keyWord = "";
				}else{
					if(keyWord.indexOf("信用社") > -1){
						keyWord = keyWord.substring(0, keyWord.indexOf("信用社")+3);
					}
					if(keyWord.indexOf("联合社") > -1){
						keyWord = keyWord.substring(0, keyWord.indexOf("联合社")+3);
					}
					if(keyWord.indexOf("银行") > -1){
						keyWord = keyWord.substring(0, keyWord.indexOf("银行")+2);
					}
				}
				
				if(cnapses.size()==1 && cnapses.get(0).getName().indexOf(keyWord)>-1){
					lists.add(cnapses);
				}else{
					List<Cnaps> cnapsList = new ArrayList<>();
					if(cnapses.size() > 1){
						boolean flag = true;
						for(Cnaps c : cnapses){
							if(c.getName().equals(keyWord)){
								cnapsList.add(c);
								flag = false;
								break;
							}
						}
						if(flag){
							cnapses = new ArrayList<>();
						}
					}
					
					if(cnapses.size() == 0 && words != null){
						String smpKeyWord = keyWord.replaceAll("省", "").replaceAll("市", "").replaceAll("县", "").replaceAll("区", "").replaceAll("股份有限公司", "");
						cnapses = cnapsService.suggestSearch(words==null?null:smpKeyWord, limit, fields, providerCode==null?null:providerCode.get(i),areaCode==null?null:areaCode.get(i),clearBankLevel);
						if(cnapses.size()==1){
							lists.add(cnapses);
							continue;
						}else{
							cnapsList = new ArrayList<>();
							if(cnapses.size() > 1){
								boolean flag = true;
								for(Cnaps c : cnapses){
									if(c.getName().equals(keyWord)){
										cnapsList.add(c);
										flag = false;
										break;
									}
									if(c.getName().equals(smpKeyWord)){
										cnapsList.add(c);
										flag = false;
										break;
									}
									String tmpStr = c.getName().replaceAll("省", "").replaceAll("市", "").replaceAll("县", "").replaceAll("区", "").replaceAll("股份有限公司", "");
									if(tmpStr.equals(smpKeyWord)){
										cnapsList.add(c);
										flag = false;
										break;
									}
								}
								if(flag){
									cnapses = new ArrayList<>();
								}
							}
						}
						if(cnapses.size() == 0 && words != null){
							String tmpSabkName = "";
							boolean flag = true;
							if(keyWord.indexOf("支行")>-1){
								flag = false;
								tmpSabkName = keyWord.substring(0, keyWord.indexOf("支行"))+"支行";
							}
							if(keyWord.indexOf("算行")>-1){
								tmpSabkName = keyWord.substring(0, keyWord.indexOf("算行"))+"算行";
							}
							if(keyWord.indexOf("合社")>-1){
								tmpSabkName = keyWord.substring(0, keyWord.indexOf("合社"))+"合社";
							}
							if(keyWord.indexOf("联社")>-1){
								tmpSabkName = keyWord.substring(0, keyWord.indexOf("联社"))+"联社";
							}
							if(keyWord.indexOf("用社")>-1){
								tmpSabkName = keyWord.substring(0, keyWord.indexOf("用社"))+"用社";
							}
							if(keyWord.indexOf("中心")>-1){
								tmpSabkName = keyWord.substring(0, keyWord.indexOf("中心"))+"中心";
							}
							if(keyWord.indexOf("作室")>-1){
								tmpSabkName = keyWord.substring(0, keyWord.indexOf("作室"))+"作室";
							}
							if(keyWord.indexOf("金融")>-1){
								tmpSabkName = keyWord.substring(0, keyWord.indexOf("金融"))+"金融";
							}
							if(keyWord.indexOf("算所")>-1){
								tmpSabkName = keyWord.substring(0, keyWord.indexOf("算所"))+"算所";
							}
							if(keyWord.indexOf("银行")>-1 && flag){
								tmpSabkName = keyWord.substring(0, keyWord.indexOf("银行"))+"银行";
							}
							
							Cnaps cnaps = null;
							if(!tmpSabkName.equals("")){
								List<Cnaps> sabk = cnapsService.suggestSearch(tmpSabkName, limit, fields, providerCode==null?null:providerCode.get(i),areaCode==null?null:areaCode.get(i),1);
								if(sabk != null && sabk.size() > 0){
									cnaps = new Cnaps();
									cnaps.setClearingBankCode(sabk.get(0).getClearingBankCode());
									cnaps.setCode(sabk.get(0).getCode());
									cnaps.setProviderCode(sabk.get(0).getProviderCode());
									cnaps.setClearingBankLevel(sabk.get(0).getClearingBankLevel());
									cnaps.setName(sabk.get(0).getName());
									cnapsList.add(cnaps);
								}else{
									sabk = cnapsService.suggestSearch(tmpSabkName, limit, fields, providerCode==null?null:providerCode.get(i),areaCode==null?null:areaCode.get(i),2);
									if(sabk != null && sabk.size() > 0){
										cnaps = new Cnaps();
										cnaps.setClearingBankCode(sabk.get(0).getClearingBankCode());
										cnaps.setCode(sabk.get(0).getCode());
										cnaps.setProviderCode(sabk.get(0).getProviderCode());
										cnaps.setClearingBankLevel(sabk.get(0).getClearingBankLevel());
										cnaps.setName(sabk.get(0).getName());
										cnapsList.add(cnaps);
									}else{
										sabk = cnapsService.suggestSearch(tmpSabkName, limit, fields, providerCode==null?null:providerCode.get(i),areaCode==null?null:areaCode.get(i),-1);
										if(sabk != null && sabk.size() > 0){
											if(sabk.size() == 1){
												cnaps = new Cnaps();
												cnaps.setClearingBankCode(sabk.get(0).getClearingBankCode());
												cnaps.setCode(sabk.get(0).getCode());
												cnaps.setProviderCode(sabk.get(0).getProviderCode());
												cnaps.setClearingBankLevel(sabk.get(0).getClearingBankLevel());
												cnaps.setName(sabk.get(0).getName());
												cnapsList.add(cnaps);
											}else{
												for(Cnaps c : sabk){
													if(c.getName().equals(tmpSabkName)){
														cnapsList.add(c);
														break;
													}
												}
											}
										}
									}
								}
								if(cnaps == null){
									if(keyWord.indexOf("银行")>-1){
										tmpSabkName = keyWord.substring(0, keyWord.indexOf("银行"))+"银行";
										sabk = cnapsService.suggestSearch(tmpSabkName, limit, fields, providerCode==null?null:providerCode.get(i),areaCode==null?null:areaCode.get(i),1);
										if(sabk != null && sabk.size() > 0){
											cnaps = new Cnaps();
											cnaps.setClearingBankCode(sabk.get(0).getClearingBankCode());
											cnaps.setCode(sabk.get(0).getCode());
											cnaps.setProviderCode(sabk.get(0).getProviderCode());
											cnaps.setClearingBankLevel(sabk.get(0).getClearingBankLevel());
											cnaps.setName(sabk.get(0).getName());
											cnapsList.add(cnaps);
										}else{
											sabk = cnapsService.suggestSearch(tmpSabkName, limit, fields, providerCode==null?null:providerCode.get(i),areaCode==null?null:areaCode.get(i),2);
											if(sabk != null && sabk.size() > 0){
												cnaps = new Cnaps();
												cnaps.setClearingBankCode(sabk.get(0).getClearingBankCode());
												cnaps.setCode(sabk.get(0).getCode());
												cnaps.setProviderCode(sabk.get(0).getProviderCode());
												cnaps.setClearingBankLevel(sabk.get(0).getClearingBankLevel());
												cnaps.setName(sabk.get(0).getName());
												cnapsList.add(cnaps);
											}else{
												sabk = cnapsService.suggestSearch(tmpSabkName, limit, fields, providerCode==null?null:providerCode.get(i),areaCode==null?null:areaCode.get(i),-1);
												if(sabk != null && sabk.size() > 0){
													if(sabk.size() == 1){
														cnaps = new Cnaps();
														cnaps.setClearingBankCode(sabk.get(0).getClearingBankCode());
														cnaps.setCode(sabk.get(0).getCode());
														cnaps.setProviderCode(sabk.get(0).getProviderCode());
														cnaps.setClearingBankLevel(sabk.get(0).getClearingBankLevel());
														cnaps.setName(sabk.get(0).getName());
														cnapsList.add(cnaps);
													}else{
														for(Cnaps c : sabk){
															if(c.getName().equals(tmpSabkName)){
																cnapsList.add(c);
																break;
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
					lists.add(cnapsList);
				}
			}
			// 为减少传输字节数
			List<Map<String, Object>> list = new ArrayList<>();
			for(List<Cnaps> cnapses : lists){
				if(cnapses.size() == 0){
					Map<String, Object> map = new LinkedHashMap<>();
					list.add(map);
					continue;
				}
				Map<String, Object> map = new LinkedHashMap<>();
				map.put("code", cnapses.get(0).getCode());
				if (fields != null) {
					for (String field : fields) {
						map.put(field, PropertyUtils.getSimpleProperty(cnapses.get(0), field));
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
			@RequestParam(value = "cnapsTxt", required = false) MultipartFile txt, @RequestParam(value = "cnapsText", required = false) String text) {
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
			List<Cnaps> cnapses = textToCnaps(separator, rows, errorMsg);
			cnapsService.add(cnapses);
		} catch (Exception e) {
			logger.error("", e);
			errorMsg.add(e.getMessage());
		}
		ModelAndView result = new ModelAndView();
		result.addObject("errorMsg", errorMsg);
		return "/cnaps/add";
	}

	/**
	 * 从文本形式转为Cnaps集合
	 * @param separator 分隔符
	 * @param text 文本内容
	 * @param errorMsg 解析错误的信息
	 * @return Cnaps集合
	 */
	private List<Cnaps> textToCnaps(String separator, List<String> rows, List<String> errorMsg) {
		List<Cnaps> cnapses = new ArrayList<>();
		for (String row : rows) {
			String[] cnapsStrArray = row.split(separator);
			if (cnapsStrArray.length != 6) {
				errorMsg.add(row);
				continue;
			}
			Cnaps cnaps = new Cnaps();
			cnaps.setCode(cnapsStrArray[0]);
			cnaps.setName(cnapsStrArray[1]);
			cnaps.setClearingBankCode(cnapsStrArray[2]);
			cnaps.setClearingBankLevel(Integer.parseInt(cnapsStrArray[3]));
			cnaps.setProviderCode(cnapsStrArray[4]);
			cnaps.setAdCode(cnapsStrArray[5]);
			cnapses.add(cnaps);
		}
		return cnapses;
	}

	/**
	 * 展示现有银行名称简称匹配信息
	 * @param separator 各字段之间的分隔符
	 * @param text 文本框
	 */
	@RequestMapping("toNickname")
	public String toNickname(Model model) {
		Map<String, String> nicknameMap = cnapsService.queryNickname();
		StringBuilder sb = new StringBuilder();
		for (String nicknameStrKey : nicknameMap.keySet()) {
			sb.append(nicknameStrKey).append(",").append(nicknameMap.get(nicknameStrKey)).append("\r\n");
		}
		model.addAttribute("nicknameText", sb.toString());
		return "/cnaps/nickname";
	}

	/**
	 * 银行名称简称匹配信息维护
	 * @param text 文本框
	 */
	@RequestMapping("nickname")
	public String nickname(@RequestParam(value = "nicknameText", required = false) String text) {
		List<String> rows = new ArrayList<>();
		text = StringUtils.safeValue(text);
		rows.addAll(Arrays.asList(text.split("\r\n")));
		Map<String, String> map = new HashMap<>();
		for (String row : rows) {
			String[] nickname = row.split(",");
			map.put(nickname[0], nickname[1]);
		}
		cnapsService.modifyNickname(map);
		return "redirect:/cnaps/toNickname.htm";
	}
	
	public Cnaps getCnapsInfo(){
		return null;
	}
	
	public Cnaps getASabkInfo(){
		return null;
	}
	
	public Cnaps getBSabkInfo(){
		return null;
	}

}

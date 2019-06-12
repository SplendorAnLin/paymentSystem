package com.yl.boss.action;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.Constant;
import com.yl.boss.entity.Authorization;
import com.yl.boss.entity.InterfaceReconBillConfig;
import com.yl.boss.service.ReconBillService;
import com.yl.boss.utils.DownloadUtil;

/**
 * 接口对账配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
public class ReconBillAction extends Struts2ActionSupport{
	
	private static final long serialVersionUID = 2258949589002129158L;
	private InterfaceReconBillConfig interfaceReconBillConfig;
	private List<InterfaceReconBillConfig> reconBillList;
	private ReconBillService reconBillService;
	private long id;
	private String jsonResult;
	
	/**
	 * 新增
	 */
	public String create() {
		interfaceReconBillConfig.setCreateTime(new Date());
		reconBillService.create(interfaceReconBillConfig);
		return SUCCESS;
	}
	
	/**
	 * 根据编号查询接口对账配置信息
	 * @return
	 */
	public String reconBillConfigById(){
		try {
			interfaceReconBillConfig = reconBillService.reconBillById(id);
		} catch (Exception e) {
			throw e;
		}
		return SUCCESS;
	}
	
	/**
	 * 修改接口对账单配置信息
	 * @return
	 */
	public String reconBillConfigUpdate(){
		try {
			reconBillService.reconBillUpdate(interfaceReconBillConfig);
		} catch (Exception e) {
			throw e;
		}
		return SUCCESS;
	}
	
	/**
	 * 查询对账单下载信息
	 * @return
	 */
	public String reconBillConfigInfo(){
		reconBillList=reconBillService.reconBill();
		return SUCCESS;
	}
	/**
	 * 下载对账单
	 * @return
	 */
	public void reconBillDown(){
		Map<String, String[]> requestMap=getHttpRequest().getParameterMap();
		Map<String, Object> params = retrieveParams(requestMap);
		params.put("fileName", params.get("filePrefix").toString()+"_"+params.get("generateTime").toString().replace("-","")+".txt");//设置文件名
		try {
			DownloadUtil.download(getHttpRequest(), getHttpResponse(), params);
		} catch (Exception e) {
			Authorization auth = (Authorization)getSession().getAttribute(Constant.SESSION_AUTH);
			logger.error("操作人："+auth.getUsername()+",读取文件【"+params.get("fileUrl").toString()+"/"+params.get("fileName").toString()+"】异常:", e);
		}
	}
	/**
	 * 参数转换
	 */
	private Map<String, Object> retrieveParams(Map<String, String[]> requestMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		for (String key : requestMap.keySet()) {
			String[] value = requestMap.get(key);
			if (value != null) {
				resultMap.put(key, Array.get(value, 0).toString().trim());
			}
		}
		return resultMap;
	}

	public InterfaceReconBillConfig getInterfaceReconBillConfig() {
		return interfaceReconBillConfig;
	}

	public void setInterfaceReconBillConfig(InterfaceReconBillConfig interfaceReconBillConfig) {
		this.interfaceReconBillConfig = interfaceReconBillConfig;
	}

	public List<InterfaceReconBillConfig> getReconBillList() {
		return reconBillList;
	}

	public void setReconBillList(List<InterfaceReconBillConfig> reconBillList) {
		this.reconBillList = reconBillList;
	}

	public ReconBillService getReconBillService() {
		return reconBillService;
	}

	public void setReconBillService(ReconBillService reconBillService) {
		this.reconBillService = reconBillService;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}

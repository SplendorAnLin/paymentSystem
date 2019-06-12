package com.yl.boss.interfaces.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.yl.boss.api.bean.Pos;
import com.yl.boss.api.interfaces.PosInterface;
import com.yl.boss.service.PosService;
import com.yl.boss.valuelist.ValueListRemoteAction;

import net.mlw.vlh.ValueList;

/**
 * POS终端信息信息远程接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2017年7月14日
 * @version V1.0.0
 */   
public class PosInterfaceImpl implements PosInterface {
	
	private ValueListRemoteAction valueListRemoteAction;
	
	@Resource
	PosService posService;

	public List<Pos> findPosByCust(String customerNo){
		List<com.yl.boss.entity.Pos> posList=posService.findPos(customerNo);
		if (posList!=null){
			List<Pos> resList=new ArrayList<>();
			for (com.yl.boss.entity.Pos pos:posList) {
				Pos temp=new Pos();
				temp.setAgentNo(pos.getAgentNo());
				temp.setBatchNo(pos.getBatchNo());
				temp.setCreateTime(pos.getCreateTime());
				temp.setCustomerNo(pos.getCustomerNo());
				temp.setLastSigninTime(pos.getLastSigninTime());
				temp.setMKey(pos.getMKey());
				temp.setOperator(pos.getOperator());
				temp.setParamVersion(pos.getParamVersion());
				temp.setPosBrand(pos.getPosBrand());
				temp.setPosCati(pos.getPosCati());
				temp.setPosSn(pos.getPosSn());
				temp.setPosType(pos.getPosType());
				temp.setRunStatus(pos.getRunStatus().toString());
				temp.setShopNo(pos.getShopNo());
				temp.setSoftVersion(pos.getSoftVersion());
				temp.setStatus(pos.getStatus().toString());
				temp.setType(pos.getType());
				resList.add(temp);
			}
			return  resList;
		}
		return null;
	}

	public Pos findPosByPosCati(String posCati){
		com.yl.boss.entity.Pos pos=posService.findPosByPosCati(posCati);
		Pos resPos=new Pos();
		resPos.setAgentNo(pos.getAgentNo());
		resPos.setBatchNo(pos.getBatchNo());
		resPos.setCreateTime(pos.getCreateTime());
		resPos.setCustomerNo(pos.getCustomerNo());
		resPos.setLastSigninTime(pos.getLastSigninTime());
		resPos.setMKey(pos.getMKey());
		resPos.setOperator(pos.getOperator());
		resPos.setParamVersion(pos.getParamVersion());
		resPos.setPosBrand(pos.getPosBrand());
		resPos.setPosCati(pos.getPosCati());
		resPos.setPosSn(pos.getPosSn());
		resPos.setPosType(pos.getPosType());
		resPos.setRunStatus(pos.getRunStatus().toString());
		resPos.setShopNo(pos.getShopNo());
		resPos.setSoftVersion(pos.getSoftVersion());
		resPos.setStatus(pos.getStatus().toString());
		resPos.setType(pos.getType());
		return  resPos;
	}

	public Pos findPosByPosSn(String posSn){
		com.yl.boss.entity.Pos pos=posService.findPosByPosSn(posSn);
		Pos resPos=new Pos();
		resPos.setAgentNo(pos.getAgentNo());
		resPos.setBatchNo(pos.getBatchNo());
		resPos.setCreateTime(pos.getCreateTime());
		resPos.setCustomerNo(pos.getCustomerNo());
		resPos.setLastSigninTime(pos.getLastSigninTime());
		resPos.setMKey(pos.getMKey());
		resPos.setOperator(pos.getOperator());
		resPos.setParamVersion(pos.getParamVersion());
		resPos.setPosBrand(pos.getPosBrand());
		resPos.setPosCati(pos.getPosCati());
		resPos.setPosSn(pos.getPosSn());
		resPos.setPosType(pos.getPosType());
		resPos.setRunStatus(pos.getRunStatus().toString());
		resPos.setShopNo(pos.getShopNo());
		resPos.setSoftVersion(pos.getSoftVersion());
		resPos.setStatus(pos.getStatus().toString());
		resPos.setType(pos.getType());
		return  resPos;
	}

	@Override
	public Map<String,Object> posQuery(Map<String, Object> params) {
		ValueList vl = valueListRemoteAction.execute("posInfo", params).get("posInfo");
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("valueListInfo", vl.getValueListInfo());
		map.put("valueList", vl.getList());
		return map;
	}
	
	@Override
	public Map<String, Object> posAssignOuter(Map<String, Object> params) {
		ValueList vl = valueListRemoteAction.execute("posAssignOuter", params).get("posAssignOuter");
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("valueListInfo", vl.getValueListInfo());
		map.put("valueList", vl.getList());
		return map;
	}
	
	@Override
	public void posBatchDeliveryOuter(String[] posCatiArrays, String agentNo, String agentChildNo, String operator) {
		posService.posBatchDeliveryOuter(posCatiArrays, agentNo, agentChildNo, operator);
	}

	@Override
	public void posBind(String[] posCatiArrays, String customerNo, String operator) {
		posService.posBind(posCatiArrays, customerNo, operator);
	}

	public ValueListRemoteAction getValueListRemoteAction() {
		return valueListRemoteAction;
	}

	public void setValueListRemoteAction(ValueListRemoteAction valueListRemoteAction) {
		this.valueListRemoteAction = valueListRemoteAction;
	}

}

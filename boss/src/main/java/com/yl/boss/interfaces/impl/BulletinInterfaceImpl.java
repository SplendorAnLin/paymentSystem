package com.yl.boss.interfaces.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.api.bean.BulletinBean;
import com.yl.boss.api.enums.BulletinSysType;
import com.yl.boss.api.enums.BulletinType;
import com.yl.boss.api.interfaces.BulletinInterface;
import com.yl.boss.api.utils.Page;
import com.yl.boss.entity.Bulletin;
import com.yl.boss.service.BulletinService;

import net.sf.json.JSONArray;

/**
 * 公告信息远程接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月19日
 * @version V1.0.0
 */
public class BulletinInterfaceImpl implements BulletinInterface{

	private BulletinService bulletinService;
	@Override
	public List<BulletinBean> bulletinBySysCode(BulletinType bulletinType, BulletinSysType bulletinSysType, Date date,Page page) {
		List<com.yl.boss.entity.Bulletin> list=bulletinService.findBulletinBy(bulletinType, bulletinSysType, date, page);
		if(list != null){
			return JsonUtils.toObject(JsonUtils.toJsonString(list), new TypeReference<List<BulletinBean>>() {});
		}
		return null;
	}
	@Override
	public BulletinBean findById(Long id) {
		Bulletin bulletin=bulletinService.findById(id);
		if (bulletin!=null) {
			return JsonUtils.toJsonToObject(bulletin, BulletinBean.class);
		}
		return null;
	}
	public int findBulletinCount(BulletinType bulletinType, BulletinSysType bulletinSysType, Date date) {
		return bulletinService.findBulletinCount(bulletinType, bulletinSysType, date);
	}
	
	public BulletinService getBulletinService() {
		return bulletinService;
	}

	public void setBulletinService(BulletinService bulletinService) {
		this.bulletinService = bulletinService;
	}
	@Override
	public String bulletinByMap(Map<String, Object> params) {
		BulletinType bulletinType=null;
		if(params.get("bulletinType")!=null){
			if(params.get("bulletinType").toString().equals("TRUE")){
				bulletinType=BulletinType.TRUE;
			}
			if(params.get("bulletinType").toString().equals("FALSE")){
				bulletinType=BulletinType.FALSE;
			}
		}
		BulletinSysType bulletinSysType=null;
		if(params.get("bulletinSysType")!=null){
			if(params.get("bulletinSysType").toString()!= null){
				if(params.get("bulletinSysType").toString().equals("BOSS")){
					bulletinSysType=BulletinSysType.BOSS;
				}
				if(params.get("bulletinSysType").toString().equals("CUSTOMER")){
					bulletinSysType=BulletinSysType.CUSTOMER;
				}
				if(params.get("bulletinSysType").toString().equals("AGENT")){
					bulletinSysType=BulletinSysType.AGENT;
				}
			}
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=null;
		if(params.get("date")!=null){
			try {
				date = sdf.parse( params.get("date").toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Integer currentPage=null;
		Integer showCount=null;
		if(params.get("currentPage")!= null ){
			currentPage=Integer.parseInt(params.get("currentPage").toString());
		}
		if(params.get("showCount")!=null){
			showCount=Integer.parseInt(params.get("showCount").toString());
		}
		Page page=new Page(0);
		if(currentPage !=null && currentPage != 0 && showCount != null && showCount != 0) {
			page.setCurrentPage(currentPage);
			page.setShowCount(showCount);
		}
		List<com.yl.boss.entity.Bulletin> list=bulletinService.findBulletinBy(bulletinType, bulletinSysType, date, page);
		if(list != null && list.size() != 0){
			JSONArray jsons=JSONArray.fromObject(list);
			return jsons.toString();
		}else{
			return null;
		}
	}



}

package com.yl.pay.pos.service.impl;

import com.pay.common.util.AmountUtil;
import com.yl.pay.pos.Constant;
import com.yl.pay.pos.dao.IcParamsDao;
import com.yl.pay.pos.dao.IcUpdateMarkDao;
import com.yl.pay.pos.entity.IcParams;
import com.yl.pay.pos.entity.IcUpdateMark;
import com.yl.pay.pos.enums.YesNo;
import com.yl.pay.pos.service.IIcParamsService;

import java.util.Date;
import java.util.List;

public class IcParamsServiceImpl implements IIcParamsService{

	IcParamsDao icParamsDao;
	IcUpdateMarkDao icUpdateMarkDao;
	@Override
	public String findParamsByPosCati(String posCati) {
		String returnMsg = Constant.NONE_IC_DATA;
		List<IcParams> icParams = icParamsDao.findAll();
		if (icParams == null || icParams.size() == 0) {
			return returnMsg;
		}
		IcUpdateMark icUpdateMark = icUpdateMarkDao.findByPosCati(posCati,"UPDATEPARAMS");
		StringBuffer icBuffer = new StringBuffer();
		StringBuffer tempBuffer=new StringBuffer();
		int markIndex = 0;
		
		if (icUpdateMark == null) {
			for (IcParams icParam : icParams) {
				tempBuffer.append("9F06").append(getLenth(icParam.getAid().length() / 2)).append(icParam.getAid());
				if (tempBuffer.toString().length() > 1022) {
					break;
				}
				icBuffer.append("9F06").append(getLenth(icParam.getAid().length() / 2)).append(icParam.getAid());
				markIndex++;
			}
			icUpdateMark = new IcUpdateMark();
			if (markIndex == icParams.size()) {
				returnMsg =Constant.HAVE_IC_DATA;
				icUpdateMark.setUpdateIndex(0);
				icUpdateMark.setIsRepeat(YesNo.N);
			} else {
				returnMsg = Constant.CYCLE_IC_DATA;
				icUpdateMark.setUpdateIndex(markIndex);
				icUpdateMark.setIsRepeat(YesNo.Y);
			}
			icUpdateMark.setPosCati(posCati);
			icUpdateMark.setTotalNumber(icParams.size());
			icUpdateMark.setUpdateType("UPDATEPARAMS");
			icUpdateMark.setLastUpdateParamsTime(new Date());
			icUpdateMarkDao.createIcMark(icUpdateMark);
		} else {
			for (IcParams icParam : icParams) {
				if(icUpdateMark.getUpdateIndex()>=icParam.getSeqIndex()){
					continue;
				}
				tempBuffer.append("9F06").append(getLenth(icParam.getAid().length() / 2)).append(icParam.getAid());
				if (icBuffer.toString().length() >1022) {
					break;
				}
				icBuffer.append("9F06").append(getLenth(icParam.getAid().length() / 2)).append(icParam.getAid());
				markIndex++;
			}
			if(AmountUtil.compare(AmountUtil.add(markIndex, icUpdateMark.getUpdateIndex()),icParams.size())){
				if(YesNo.Y.equals(icUpdateMark.getIsRepeat())){
					returnMsg = Constant.END_IC_DATA;
				}else{
					returnMsg = Constant.HAVE_IC_DATA;
				}
				icUpdateMark.setUpdateIndex(0);
				icUpdateMark.setIsRepeat(YesNo.N);
			} else {
				returnMsg = Constant.CYCLE_IC_DATA;
				icUpdateMark.setUpdateIndex(markIndex+icUpdateMark.getUpdateIndex());
				icUpdateMark.setIsRepeat(YesNo.Y);
			}
			icUpdateMark.setLastUpdateParamsTime(new Date());
			icUpdateMark.setTotalNumber(icParams.size());
			icUpdateMarkDao.updateIcMark(icUpdateMark);
		}
		return returnMsg+icBuffer.toString();
	}

	@Override
	public String findByAid(String aid) {
		String returnMsg=Constant.NONE_IC_DATA;
		IcParams icParams=icParamsDao.findByRidAndIndex(aid);
		if(icParams==null){
			return returnMsg;
		}
		returnMsg=Constant.HAVE_IC_DATA;
		StringBuffer icBuffer=new StringBuffer();
		icBuffer.append("9F06").append(getLenth(aid.length() / 2)).append(aid);
		icBuffer.append("DF01").append(getLenth(icParams.getAsi().length() / 2)).append(icParams.getAsi());
		icBuffer.append("9F09").append(getLenth(icParams.getAppVersion().length() / 2)).append(icParams.getAppVersion());
		icBuffer.append("DF11").append(getLenth(icParams.getTacDefault().length() / 2)).append(icParams.getTacDefault());
		icBuffer.append("DF12").append(getLenth(icParams.getTacOnline().length() / 2)).append(icParams.getTacOnline());
		icBuffer.append("DF13").append(getLenth(icParams.getTacRefuse().length() / 2)).append(icParams.getTacRefuse());
		icBuffer.append("9F1B").append(getLenth(icParams.getMinAmount().length()/ 2)).append(icParams.getMinAmount());
		icBuffer.append("DF15").append(getLenth(icParams.getRandomThreshold().length() / 2)).append(icParams.getRandomThreshold());
		icBuffer.append("DF16").append(getLenth(icParams.getMaxRandomPercentage().length() / 2)).append(icParams.getMaxRandomPercentage());
		icBuffer.append("DF17").append(getLenth(icParams.getRandomPercentage().length() / 2)).append(icParams.getRandomPercentage());
		icBuffer.append("DF14").append(getLenth(icParams.getDdol().length() / 2)).append(icParams.getDdol());
		icBuffer.append("DF18").append(getLenth(icParams.getPinOnlineAbility().length() / 2)).append(icParams.getPinOnlineAbility());
		icBuffer.append("9F7B").append(getLenth(icParams.getElectronicCashMinAmount().length() / 2)).append(icParams.getElectronicCashMinAmount());
		icBuffer.append("DF19").append(getLenth(icParams.getNoncontactOfflineMinAmount().length() / 2)).append(icParams.getNoncontactOfflineMinAmount());
		icBuffer.append("DF20").append(getLenth(icParams.getNoncontactMinAmount().length() / 2)).append(icParams.getNoncontactMinAmount());
		icBuffer.append("DF21").append(getLenth(icParams.getCvmLimit().length() / 2)).append(icParams.getCvmLimit());
		return returnMsg+icBuffer.toString().toUpperCase();
	}
	

	@Override
	public boolean queryUpdateFlag(String posCati,String updateType) {
		IcUpdateMark icUpdateMark=icUpdateMarkDao.findByType("UPDATEFLAG");
		IcUpdateMark icUpdateMark2=icUpdateMarkDao.findByPosCati(posCati, updateType);
		if("UPDATEPARAMS".equals(updateType)){
		if(icUpdateMark2==null||icUpdateMark2.getLastUpdateParamsTime()==null||(icUpdateMark.getLastUpdateParamsTime().getTime()-icUpdateMark2.getLastUpdateParamsTime().getTime()>0)){
			return true;
		}
		}else{
			if(icUpdateMark2==null||icUpdateMark2.getLastUpdateKeyTime()==null||(icUpdateMark.getLastUpdateKeyTime().getTime()-icUpdateMark2.getLastUpdateKeyTime().getTime()>0)){
				return true;
			}
		}
		return false;
	}
	public static String getLenth(int lenth){
		String hexStr=Integer.toHexString(lenth);
		if(hexStr.length()%2!=0){
			hexStr="0"+hexStr;
		}
		return hexStr;
	}
	public IcParamsDao getIcParamsDao() {
		return icParamsDao;
	}

	public void setIcParamsDao(IcParamsDao icParamsDao) {
		this.icParamsDao = icParamsDao;
	}

	public IcUpdateMarkDao getIcUpdateMarkDao() {
		return icUpdateMarkDao;
	}

	public void setIcUpdateMarkDao(IcUpdateMarkDao icUpdateMarkDao) {
		this.icUpdateMarkDao = icUpdateMarkDao;
	}


	
}

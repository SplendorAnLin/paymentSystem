package com.yl.pay.pos.service.impl;

import com.pay.common.util.AmountUtil;
import com.yl.pay.pos.Constant;
import com.yl.pay.pos.dao.IcKeyDao;
import com.yl.pay.pos.dao.IcUpdateMarkDao;
import com.yl.pay.pos.entity.IcKey;
import com.yl.pay.pos.entity.IcUpdateMark;
import com.yl.pay.pos.enums.YesNo;
import com.yl.pay.pos.service.IIcKeyService;

import java.util.Date;
import java.util.List;

public class IcKeyServiceImpl implements IIcKeyService {

	private IcKeyDao icKeyDao;
	private IcUpdateMarkDao icUpdateMarkDao;

	@Override
	public String findKeysByPosCati(String posCati) {
		String returnMsg = Constant.NONE_IC_DATA;
		List<IcKey> icKeys = icKeyDao.findAll();
		if (icKeys == null || icKeys.size() == 0) {
			return returnMsg ;
		}
		IcUpdateMark icUpdateMark = icUpdateMarkDao.findByPosCati(posCati, "UPDATEKEY");
		StringBuffer icBuffer = new StringBuffer();
		StringBuffer tempBuffer = new StringBuffer();
		int markIndex = 0;

		if (icUpdateMark == null) {
			for (IcKey icKey : icKeys) {
				String rid = icKey.getRid();
				String keyIndex = icKey.getKeyIndex();
				String expTime = icKey.getExpTime();
				tempBuffer.append("9F06").append(getLenth(rid.length() / 2)).append(rid);
				tempBuffer.append("9F22").append(getLenth(keyIndex.length() / 2)).append(keyIndex);
				tempBuffer.append("DF05").append(getLenth(expTime.length() / 2)).append(expTime);
				if (tempBuffer.toString().length() > 1022) {
					break;
				}
				icBuffer.append("9F06").append(getLenth(rid.length() / 2)).append(rid);
				icBuffer.append("9F22").append(getLenth(keyIndex.length() / 2)).append(keyIndex);
				icBuffer.append("DF05").append(getLenth(expTime.length() / 2)).append(expTime);
				markIndex++;
			}
			icUpdateMark = new IcUpdateMark();
			if (markIndex == icKeys.size()) {
				returnMsg = Constant.HAVE_IC_DATA;
				icUpdateMark.setUpdateIndex(0);
				icUpdateMark.setIsRepeat(YesNo.N);
			} else {
				returnMsg = Constant.CYCLE_IC_DATA;
				icUpdateMark.setIsRepeat(YesNo.Y);
				icUpdateMark.setUpdateIndex(markIndex);
			}

			icUpdateMark.setPosCati(posCati);
			icUpdateMark.setTotalNumber(icKeys.size());
			icUpdateMark.setUpdateType("UPDATEKEY");
			icUpdateMark.setLastUpdateKeyTime(new Date());
			icUpdateMarkDao.createIcMark(icUpdateMark);
		} else {
			for (IcKey icKey : icKeys) {
				if (icUpdateMark.getUpdateIndex() >= icKey.getSeqIndex()) {
					continue;
				}
				String rid = icKey.getRid();
				String keyIndex = icKey.getKeyIndex();
				String expTime = icKey.getExpTime();
				tempBuffer.append("9F06").append(getLenth(rid.length() / 2)).append(rid);
				tempBuffer.append("9F22").append(getLenth(keyIndex.length() / 2)).append(keyIndex);
				tempBuffer.append("DF05").append(getLenth(expTime.length() / 2)).append(expTime);

				if (icBuffer.toString().length() > 1022) {
					break;
				}
				icBuffer.append("9F06").append(getLenth(rid.length() / 2)).append(rid);
				icBuffer.append("9F22").append(getLenth(keyIndex.length() / 2)).append(keyIndex);
				icBuffer.append("DF05").append(getLenth(expTime.length() / 2)).append(expTime);
				markIndex++;
			}
			if (AmountUtil.compare(AmountUtil.add(markIndex, icUpdateMark.getUpdateIndex()), icKeys.size())) {
				if (YesNo.Y.equals(icUpdateMark.getIsRepeat())) {
					returnMsg = Constant.END_IC_DATA;
				} else {
					returnMsg = "31";
				}
				icUpdateMark.setUpdateIndex(0);
				icUpdateMark.setIsRepeat(YesNo.N);
			} else {
				returnMsg = Constant.CYCLE_IC_DATA;
				icUpdateMark.setUpdateIndex(markIndex + icUpdateMark.getUpdateIndex());
				icUpdateMark.setIsRepeat(YesNo.Y);
			}
			icUpdateMark.setTotalNumber(icKeys.size());
			icUpdateMark.setLastUpdateKeyTime(new Date());
			icUpdateMarkDao.updateIcMark(icUpdateMark);
		}
		return returnMsg + icBuffer.toString();
	}

	@Override
	public String findByRid(String rid, String keyIndex) {
		String returnMsg = Constant.NONE_IC_DATA;
		IcKey icKey = icKeyDao.findByRidAndIndex(rid, keyIndex);
		if (icKey == null) {
			return returnMsg;
		}
		returnMsg = Constant.HAVE_IC_DATA;
		String expTime = icKey.getExpTime();
		StringBuffer icBuffer = new StringBuffer();
		icBuffer.append("9F06").append(getLenth(rid.length() / 2)).append(rid);
		icBuffer.append("9F22").append(getLenth(keyIndex.length() / 2)).append(keyIndex);
		icBuffer.append("DF05").append(getLenth(expTime.length() / 2)).append(expTime);
		icBuffer.append("DF06").append(getLenth(icKey.getHashAlgorithmId().length() / 2)).append(icKey.getHashAlgorithmId());
		icBuffer.append("DF07").append(getLenth(icKey.getAlgorithmId().length() / 2)).append(icKey.getAlgorithmId());
		if(127<icKey.getModulus().length() / 2&&icKey.getModulus().length() / 2<255&&getLenth(icKey.getModulus().length() / 2).length()==2){
			icBuffer.append("DF02").append("81").append(getLenth(icKey.getModulus().length() / 2)).append(icKey.getModulus());
		}else{
			icBuffer.append("DF02").append(getLenth(icKey.getModulus().length() / 2)).append(icKey.getModulus());
		}
		icBuffer.append("DF04").append(getLenth(icKey.getExponent().length() / 2)).append(icKey.getExponent());
		icBuffer.append("DF03").append(getLenth(icKey.getCheckvalue().length() / 2)).append(icKey.getCheckvalue());
		return returnMsg + icBuffer.toString().toUpperCase();
	}

	public static String getLenth(int lenth) {
		String hexStr = Integer.toHexString(lenth);
		if (hexStr.length() % 2 != 0) {
			hexStr = "0" + hexStr;
		}
		return hexStr;
	}

	public IcKeyDao getIcKeyDao() {
		return icKeyDao;
	}

	public void setIcKeyDao(IcKeyDao icKeyDao) {
		this.icKeyDao = icKeyDao;
	}

	public IcUpdateMarkDao getIcUpdateMarkDao() {
		return icUpdateMarkDao;
	}

	public void setIcUpdateMarkDao(IcUpdateMarkDao icUpdateMarkDao) {
		this.icUpdateMarkDao = icUpdateMarkDao;
	}

}

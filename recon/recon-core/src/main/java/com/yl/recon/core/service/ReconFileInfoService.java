package com.yl.recon.core.service;

import java.util.List;

import com.yl.recon.core.entity.fileinfo.ReconFileInfo;

/**
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2018年01月13
 */
public interface ReconFileInfoService {

	int insert(ReconFileInfo reconFileInfo);

	int insertSelective(ReconFileInfo reconFileInfo);

	int insertList(List <ReconFileInfo> reconFileInfos);

	int update(ReconFileInfo reconFileInfo);

	/**
	 * 通过id获取
	 *
	 * @param id
	 * @return
	 */
	ReconFileInfo selectByPrimaryKey(Long id);
}

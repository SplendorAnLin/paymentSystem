package com.yl.recon.core.service.impl;

import com.yl.recon.core.entity.fileinfo.ReconFileInfo;
import com.yl.recon.core.mybatis.mapper.ReconFileInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.yl.recon.core.service.ReconFileInfoService;

/**
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2018年01月13
 */
@Service("reconFileInfoService")
public class ReconFileInfoServiceImpl implements ReconFileInfoService {

	@Resource
	private ReconFileInfoMapper reconFileInfoMapper;

	@Override
	public int insert(ReconFileInfo reconFileInfo) {
		return reconFileInfoMapper.insert(reconFileInfo);
	}

	@Override
	public int insertSelective(ReconFileInfo reconFileInfo) {
		return reconFileInfoMapper.insertSelective(reconFileInfo);
	}

	@Override
	public int insertList(List <ReconFileInfo> reconFileInfos) {
		return reconFileInfoMapper.insertList(reconFileInfos);
	}

	@Override
	public int update(ReconFileInfo reconFileInfo) {
		return reconFileInfoMapper.update(reconFileInfo);
	}

	/**
	 * 通过id获取
	 *
	 * @param id
	 * @return
	 */
	@Override
	public ReconFileInfo selectByPrimaryKey(Long id) {
		return reconFileInfoMapper.selectByPrimaryKey(id);
	}
}

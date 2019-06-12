package com.yl.recon.core.service.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.yl.recon.core.entity.fileinfo.ReconFileInfoExtend;
import com.yl.recon.core.mybatis.mapper.ReconFileInfoExtendMapper;
import com.yl.recon.core.service.ReconFileInfoExtendService;

@Service
public class ReconFileInfoExtendServiceImpl implements ReconFileInfoExtendService {

	@Resource
	private ReconFileInfoExtendMapper reconFileInfoExtendMapper;

	@Override
	public int insert(ReconFileInfoExtend reconFileInfoExtend) {
		return reconFileInfoExtendMapper.insert(reconFileInfoExtend);
	}

	@Override
	public int insertSelective(ReconFileInfoExtend reconFileInfoExtend) {
		return reconFileInfoExtendMapper.insertSelective(reconFileInfoExtend);
	}

	@Override
	public int insertList(List <ReconFileInfoExtend> reconFileInfoExtends) {
		return reconFileInfoExtendMapper.insertList(reconFileInfoExtends);
	}

	@Override
	public int update(ReconFileInfoExtend reconFileInfoExtend) {
		return reconFileInfoExtendMapper.update(reconFileInfoExtend);
	}

	/**
	 * 通过id获取
	 *
	 * @param id
	 * @return
	 */
	@Override
	public ReconFileInfoExtend selectByPrimaryKey(Long id) {
		return reconFileInfoExtendMapper.selectByPrimaryKey(id);
	}


}

package com.yl.recon.core.service;

import java.util.List;
import com.yl.recon.core.entity.fileinfo.ReconFileInfoExtend;
import com.yl.recon.core.entity.fileinfo.ReconFileInfoExtendExample;
import org.apache.ibatis.annotations.Param;

/**
 * @author 聚合支付有限公司
 * @since 2018年01月18
 * @version V1.0.0
 */
public interface ReconFileInfoExtendService{

	/**
	 * 插入：不允许为空的属性
	 * @param reconFileInfoExtend
	 * @return
	 */
    int insert(ReconFileInfoExtend reconFileInfoExtend);

	/**
	 * 插入：允许为空的属性
	 * @param reconFileInfoExtend
	 * @return
	 */
	int insertSelective(ReconFileInfoExtend reconFileInfoExtend);

	/**
	 * 批量插入
	 * @param reconFileInfoExtends
	 * @return
	 */
    int insertList(List <ReconFileInfoExtend> reconFileInfoExtends);

	/**
	 * 更新
	 * @param reconFileInfoExtend
	 * @return
	 */
	int update(ReconFileInfoExtend reconFileInfoExtend);


	/**
	 * 通过id获取
	 * @param id
	 * @return
	 */
	ReconFileInfoExtend selectByPrimaryKey(Long id);



}

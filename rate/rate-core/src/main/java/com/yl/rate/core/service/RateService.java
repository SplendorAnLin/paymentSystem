package com.yl.rate.core.service;

import com.yl.rate.core.entity.Rate;
import java.util.List;

public interface RateService{

    int insert(Rate rate, String oper);

    int insertSelective(Rate rate);

    int insertList(List<Rate> rates);

    int update(Rate rate, String oper);

    /**
     * 根据费率编号查询费率
     * @param code
     * @return
     */
    Rate queryByCode(String code);

    /**
     * 添加费率信息
     * @param rate
     * @return 费率编号
     */
    void rateAdd(Rate rate);

    /**
     * 修改状态 内部调用不新增历史
     * @param rate
     * @return
     */
    int updateStatus(Rate rate);

    /**
     * 历史ID关联新增
     * @param rate
     * @param oper
     * @param ownerRateId
     * @return
     */
    int addRate(Rate rate, String oper, Long ownerRateId);

    /**
     * 历史ID关联修改
     * @param rate
     * @param oper
     * @param ownerRateId
     * @return
     */
    int updateRate(Rate rate, String oper, Long ownerRateId);

    /**
     * 删除
     * @param id
     * @return
     */
    int delete(Long id);
}

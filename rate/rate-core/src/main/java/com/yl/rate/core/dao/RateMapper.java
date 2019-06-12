package com.yl.rate.core.dao;


import com.yl.rate.core.entity.Rate;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface RateMapper {
    int insert(@Param("rate") Rate rate);

    int insertSelective(@Param("rate") Rate rate);

    int insertList(@Param("rates") List<Rate> rates);

    int update(@Param("rate") Rate rate);

    /**
     * 根据编号查询费率信息
     *
     * @param code
     * @return
     */
    Rate queryByCode(@Param("code") String code);

    /**
     * 添加费率信息
     * @param rate
     * @return 费率编号
     */
    void rateAdd(Rate rate);

    /**
     * 根据费率编号校验当前是否存在
     * @param code
     * @return
     */
    int checkRateByCode(@Param("code") String code);

    /**
     * 根据编号查询费率信息
     *
     * @param code
     * @return
     */
    Rate queryByCodeDetails(@Param("code") String code);

    void rateUpdate(Rate rate);

    int delete(@Param("id")Long id);
}

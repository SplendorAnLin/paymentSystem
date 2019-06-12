package com.yl.rate.core.service.impl;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.rate.core.dao.*;
import com.yl.rate.core.entity.*;
import com.yl.rate.core.enums.Status;
import com.yl.rate.core.service.RateRuleService;
import com.yl.rate.core.service.RateTemplateService;
import com.yl.rate.core.utils.CodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service("rateTemplateService")
public class RateTemplateServiceImpl implements RateTemplateService{

    private static final Logger logger = LoggerFactory.getLogger(RateTemplateServiceImpl.class);

    @Resource
    private RateTemplateMapper rateTemplateMapper;

    @Resource
    private RateTemplateHistoryMapper rateTemplateHistoryMapper;

    @Resource
    private RateMapper rateMapper;

    @Resource
    private RateHistoryMapper rateHistoryMapper;

    @Resource
    private RateRuleService rateRuleService;

    @Resource
    private RateRuleMapper rateRuleMapper;

    @Resource
    private RateRuleHistoryMapper rateRuleHistoryMapper;

    @Resource
    private LadderRateMapper ladderRateMapper;

    @Resource
    private LadderRateHistoryMapper ladderRateHistoryMapper;

    @Resource
    private NormalRateMapper normalRateMapper;

    @Resource NormalRateHistoryMapper normalRateHistoryMapper;

    @Resource
    private PosRateMapper posRateMapper;

    @Resource
    private PosRateHistoryMapper posRateHistoryMapper;



    @Override
    public List<Map<String, Object>> getTemplateInfo(String ownerRole, String productType) {
        return rateTemplateMapper.getTemplateInfo(ownerRole, productType);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void rateTemplateUpdate(Map<String, Object> params) throws Exception {

        try {
            RateTemplate rateTemplate = JsonUtils.toJsonToObject(params.get("rateTemplate"), RateTemplate.class);

            RateTemplate rateTemplateDetails = rateTemplateMapper.queryRateTemplateDetails(rateTemplate.getCode());
            if(rateTemplateDetails != null) {

                //修改费率模板，并记录历史
                rateTemplateDetails.setUpdateTime(new Date());
                rateTemplateDetails.setStatus(rateTemplate.getStatus());
                rateTemplateDetails.setName(rateTemplate.getName());
                rateTemplateDetails.setOwnerRole(rateTemplate.getOwnerRole());
                rateTemplateDetails.setProductType(rateTemplate.getProductType());
                rateTemplateDetails.setDefaultRate(rateTemplate.getDefaultRate());
                rateTemplateDetails.setRemarks(rateTemplate.getRemarks());


                //判断当前是否要跳转默认状态
                if(!rateTemplate.getDefaultRate().equals(Status.FALSE)) {
                    if(checkDefaultRate(rateTemplate.getProductType().toString(), rateTemplate.getOwnerRole().toString()) != false) {
                        rateTemplateMapper.defaultRateUpdate(rateTemplate.getProductType().toString(), rateTemplate.getOwnerRole().toString(), Status.FALSE.toString());
                    }
                }


                rateTemplateMapper.update(rateTemplateDetails);
                rateTemplateHistoryMapper.insert(new RateTemplateHistory(rateTemplateDetails, params.get("oper").toString()));


                //根据当前费率模板费率编号查询数据库中存在的费率类型
                Rate rate = rateMapper.queryByCodeDetails(rateTemplateDetails.getRateCode());

                //查询规则
                List<RateRule> rateRule = rateRuleMapper.queryRateRule(rate.getCode());

                //删除原来所有的配置信息
                switch (rate.getCompFeeType()) {
                    case STANDARD:
                        normalRateMapper.deleteByRateRuleCode(rateRule.get(0).getCode());
                        break;
                    case LADDER:
                        for (int i = 0; i < rateRule.size(); i++) {
                            ladderRateMapper.deleteByRateRuleCode(rateRule.get(i).getCode());
                        }
                        break;
                    case POS:
                        for (int i = 0; i < rateRule.size(); i++) {
                            posRateMapper.deleteByRateRuleCode(rateRule.get(i).getCode());
                        }
                        break;
                }

                //删除所有原费率规则
                rateRuleMapper.deleteByRateCode(rate.getCode());


                //修改新的费率类型
                rate.setCompFeeType(JsonUtils.toJsonToObject(params.get("rate"), Rate.class).getCompFeeType());
                rateMapper.rateUpdate(rate);
                rateHistoryMapper.rateAdd(new RateHistory(rate, params.get("oper").toString()));


                //添加新的费率配置信息
                switch (rate.getCompFeeType()) {

                    case STANDARD:
                        NormalRate normalRate = JsonUtils.toJsonToObject(params.get("standard"), NormalRate.class);
                        do {
                            normalRate.setCode(CodeBuilder.buildNumber(8));
                        }while (normalRateMapper.checkRateByCode(normalRate.getCode()) != 0);
                        normalRate.setRateRuleCode(rateRuleService.rateRuleAdd(rate.getCode(), params.get("oper").toString()));
                        normalRate.setCreateTime(new Date());
                        normalRateMapper.insert(normalRate);
                        normalRateHistoryMapper.insert(new NormalRateHistory(normalRate, params.get("oper").toString()));
                        break;
                    case LADDER:
                        List<LadderRate> ladderRates = JsonUtils.toJsonToObject(params.get("ladder"), List.class);
                        LadderRate ladderRate = null;
                        for (int i = 0; i < ladderRates.size(); i++) {
                            ladderRate = JsonUtils.toJsonToObject(ladderRates.get(i), LadderRate.class);
                            do {
                                ladderRate.setCode(CodeBuilder.buildNumber(8));
                            }while (ladderRateMapper.checkRateByCode(ladderRate.getCode()) != 0);
                            ladderRate.setRateRuleCode(rateRuleService.rateRuleAdd(rate.getCode(), params.get("oper").toString()));
                            ladderRate.setCreateTime(new Date());
                            ladderRateMapper.insert(ladderRate);
                            ladderRateHistoryMapper.insert(new LadderRateHistory(ladderRate, params.get("oper").toString()));
                        }
                        break;
                    case POS:
                        List<PosRate> posRates = JsonUtils.toJsonToObject(params.get("pos"), List.class);
                        PosRate posRate = null;
                        for (int i = 0; i < posRates.size(); i++) {
                            posRate = JsonUtils.toJsonToObject(posRates.get(i), PosRate.class);
                            do {
                                posRate.setCode(CodeBuilder.buildNumber(8));
                            }while (posRateMapper.checkRateByCode(posRate.getCode()) != 0);
                            posRate.setRateRuleCode(rateRuleService.rateRuleAdd(rate.getCode(), params.get("oper").toString()));
                            posRate.setCreateTime(new Date());
                            posRateMapper.insert(posRate);
                            posRateHistoryMapper.insert(new PosRateHistory(posRate, params.get("oper").toString()));
                        }
                        break;
                }

            }else {
                throw new Exception("未找到模板信息，模板编号：" + rateTemplate.getCode());
            }
        } catch (Exception e) {
            logger.error("费率模板修改失败！{}", e);
            throw new Exception(e);
        }

    }

    @Override
    public boolean checkDefaultRate(String productType, String ownerRole) {
        if(rateTemplateMapper.checkDefaultRate(productType, ownerRole) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkRateByCode(String code) {
        if(rateTemplateMapper.checkRateByCode(code) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public int insert(RateTemplate rateTemplate){
        return rateTemplateMapper.insert(rateTemplate);
    }

    @Override
    public RateTemplate queryRateTemplate(String code, String productType) {
        return rateTemplateMapper.queryRateTemplate(code, productType);
    }

    @Override
    public RateTemplate queryDefaultRateTemplate(String productType, String ownerRole) {
        return rateTemplateMapper.queryDefaultRateTemplate(productType, ownerRole);
    }

    @Override
    public Page findAllRateTemplate(Map<String, Object> params, Page page) {
        page.setObject(rateTemplateMapper.findAllRateTemplate(params,page));
        return page;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void rateTemplateAdd(Map<String, Object> params) throws Exception {

        try {

            //检测当前待添加的费率模板是否存在重复
            RateTemplate rateTemplate = JsonUtils.toJsonToObject(params.get("rateTemplateBean"), RateTemplate.class);

            if(rateTemplateMapper.checkRateByCode(rateTemplate.getCode()) != 0) {
                throw new Exception("费率模板编号重复！");
            }

            //检测是否重复，条件满足则新增
            Rate rate = JsonUtils.toJsonToObject(params.get("rateBean"), Rate.class);
            do {
                rate.setCode(CodeBuilder.buildNumber(8));
            }while (rateMapper.checkRateByCode(rate.getCode()) != 0);
            rate.setCreateTime(new Date());
            rate.setStatus(Status.TRUE);
            rateMapper.rateAdd(rate);
            rateHistoryMapper.rateAdd(new RateHistory(rate, params.get("oper").toString()));

            //判断当前费率配置类型，并进行相应处理
            switch (rate.getCompFeeType()) {

                case STANDARD:
                    NormalRate normalRate = JsonUtils.toJsonToObject(params.get("standard"), NormalRate.class);
                    do {
                        normalRate.setCode(CodeBuilder.buildNumber(8));
                    }while (normalRateMapper.checkRateByCode(normalRate.getCode()) != 0);
                    normalRate.setRateRuleCode(rateRuleService.rateRuleAdd(rate.getCode(), params.get("oper").toString()));
                    normalRate.setCreateTime(new Date());
                    normalRateMapper.insert(normalRate);
                    normalRateHistoryMapper.insert(new NormalRateHistory(normalRate, params.get("oper").toString()));
                    break;
                case LADDER:
                    List<LadderRate> ladderRates = JsonUtils.toJsonToObject(params.get("ladder"), List.class);
                    LadderRate ladderRate = null;
                    for (int i = 0; i < ladderRates.size(); i++) {
                        ladderRate = JsonUtils.toJsonToObject(ladderRates.get(i), LadderRate.class);
                        do {
                            ladderRate.setCode(CodeBuilder.buildNumber(8));
                        }while (ladderRateMapper.checkRateByCode(ladderRate.getCode()) != 0);
                        ladderRate.setRateRuleCode(rateRuleService.rateRuleAdd(rate.getCode(), params.get("oper").toString()));
                        ladderRate.setCreateTime(new Date());
                        ladderRateMapper.insert(ladderRate);
                        ladderRateHistoryMapper.insert(new LadderRateHistory(ladderRate, params.get("oper").toString()));
                    }
                    break;
                case POS:
                    List<PosRate> posRates = JsonUtils.toJsonToObject(params.get("pos"), List.class);
                    PosRate posRate = null;
                    for (int i = 0; i < posRates.size(); i++) {
                        posRate = JsonUtils.toJsonToObject(posRates.get(i), PosRate.class);
                        do {
                            posRate.setCode(CodeBuilder.buildNumber(8));
                        }while (posRateMapper.checkRateByCode(posRate.getCode()) != 0);
                        posRate.setRateRuleCode(rateRuleService.rateRuleAdd(rate.getCode(), params.get("oper").toString()));
                        posRate.setCreateTime(new Date());
                        posRateMapper.insert(posRate);
                        posRateHistoryMapper.insert(new PosRateHistory(posRate, params.get("oper").toString()));
                    }
                    break;
            }

            //添加费率模板
            rateTemplate.setRateCode(rate.getCode());
            rateTemplate.setCreateTime(new Date());
            rateTemplate.setUpdateTime(new Date());


            //判断当前是否要跳转默认状态
            if(!rateTemplate.getDefaultRate().equals(Status.FALSE)) {
                if(checkDefaultRate(rateTemplate.getProductType().toString(), rateTemplate.getOwnerRole().toString()) != false) {
                    rateTemplateMapper.defaultRateUpdate(rateTemplate.getProductType().toString(), rateTemplate.getOwnerRole().toString(), Status.FALSE.toString());
                }
            }


            rateTemplateMapper.insert(rateTemplate);
            rateTemplateHistoryMapper.insert(new RateTemplateHistory(rateTemplate, params.get("oper").toString()));
        } catch (Exception e) {
            logger.error("费率模板新增失败！{}", e);
            throw new Exception(e);
        }

    }


    @Override
    public Map<String, Object> queryRateTemplateDetails(String code) {

        Map<String, Object> params = new HashMap<String, Object>();

        try {
            RateTemplate rateTemplate = rateTemplateMapper.queryRateTemplateDetails(code);
            params.put("rateTemplate", rateTemplate);

            Rate rate = rateMapper.queryByCodeDetails(rateTemplate.getRateCode());
            params.put("rate", rate);

            List<RateRule> rateRules = rateRuleMapper.queryRateRule(rate.getCode());
            params.put("rateRules", rateRules);

            switch (rate.getCompFeeType()) {

                case STANDARD:
                    NormalRate normalRate = normalRateMapper.queryByRateRuleCode(rateRules.get(0).getCode());
                    params.put("normalRate", normalRate);
                    break;
                case LADDER:
                    List<LadderRate> ladderRates = new ArrayList<LadderRate>();
                    for (RateRule rateRule : rateRules) {
                        ladderRates.add(ladderRateMapper.queryByRateRuleCode(rateRule.getCode()));
                    }
                    params.put("ladderRates", ladderRates);
                    break;
                case POS:
                    List<PosRate> posRates = new ArrayList<PosRate>();
                    for (RateRule rateRule : rateRules) {
                        posRates.add(posRateMapper.queryPosRateDetails(rateRule.getCode()));
                    }
                    params.put("posRates", posRates);
                    break;
            }

        } catch (Exception e) {
            logger.error("查询费率模板详情失败！");
        }

        return params;
    }

}

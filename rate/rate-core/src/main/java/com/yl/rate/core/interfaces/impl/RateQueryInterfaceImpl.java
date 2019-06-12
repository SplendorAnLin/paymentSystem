package com.yl.rate.core.interfaces.impl;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.DateUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.rate.core.entity.*;
import com.yl.rate.core.enums.*;
import com.yl.rate.core.service.*;
import com.yl.rate.core.utils.CodeBuilder;
import com.yl.rate.interfaces.beans.*;
import com.yl.rate.interfaces.interfaces.RateQueryInterface;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 费率查询远程服务接口实现
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/12/12
 */
@Service("rateQueryInterface")
public class RateQueryInterfaceImpl implements RateQueryInterface {

    @Resource
    private OwnerRateService ownerRateService;

    @Resource
    private OwnerRateHistoryService ownerRateHistoryService;

    @Resource
    private LadderRateService ladderRateService;

    @Resource
    private LadderRateHistoryService ladderRateHistoryService;

    @Resource
    private NormalRateService normalRateService;

    @Resource
    private NormalRateHistoryService normalRateHistoryService;

    @Resource
    private PosRateService posRateService;

    @Resource
    private PosRateHistoryService posRateHistoryService;

    @Resource
    private RateService rateService;

    @Resource
    private RateHistoryService rateHistoryService;

    @Resource
    private RateRuleService rateRuleService;

    @Resource
    private RateRuleHistoryService rateRuleHistoryService;

    @Resource
    private RateTemplateService rateTemplateService;

    @Resource
    private RateTemplateHistoryService rateTemplateHistoryService;

    @Override
    public String getTemplateInfo(String ownerRole, String productType) {
        return JsonUtils.toJsonString(rateTemplateService.getTemplateInfo(ownerRole, productType));
    }

    @Override
    public Page findAllFeeConfig(Page page, Map<String, Object> params) {
        return ownerRateService.findAllFeeConfig(page, params);
    }

    @Override
    public Page findAllFeeConfigHistory(Page page, String code) {
        return ownerRateHistoryService.findAllFeeConfigHistory(page, code);
    }

    @Override
    public OwnerRateBean findFeeConfigByCode(String code) {
        OwnerRate ownerRate = ownerRateService.queryByCode(code);
        if (ownerRate != null) {
            return JsonUtils.toObject(JsonUtils.toJsonString(ownerRate), new TypeReference<OwnerRateBean>() {
            });
        }
        return null;
    }

    @Override
    public int update(OwnerRateBean ownerRateBean, String oper) {
        OwnerRate ownerRate = JsonUtils.toObject(JsonUtils.toJsonString(ownerRateBean), new TypeReference<OwnerRate>() {
        });
        return ownerRateService.update(ownerRate, oper);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int createCustomize(AddOwnerRateBean addOwnerRateBean, String oper) {
        if (OwnerRateType.TEMPLATE.name().equals(addOwnerRateBean.getOwnerRateType().name())) {
            // 模板处理流程
            return template(addOwnerRateBean, oper);
        } else {
            // 自定义配置处理流程
            return customize(addOwnerRateBean, oper);
        }
    }

    @Override
    public AddOwnerRateBean queryByCode(String code) {
        AddOwnerRateBean addOwnerRateBean = new AddOwnerRateBean();
        OwnerRate ownerRate = ownerRateService.queryByCode(code);
        addOwnerRateBean.setFeeType(com.yl.rate.interfaces.enums.FeeType.valueOf(ownerRate.getFeeType().name()));
        addOwnerRateBean.setOwnerId(ownerRate.getOwnerId());
        addOwnerRateBean.setTemplateName(ownerRate.getRateCode());
        addOwnerRateBean.setRemarks(ownerRate.getRemarks());
        addOwnerRateBean.setProductType(com.yl.rate.interfaces.enums.ProductType.valueOf(ownerRate.getProductType().name()));
        addOwnerRateBean.setProductCode(ownerRate.getProductCode());
        addOwnerRateBean.setOwnerRateType(com.yl.rate.interfaces.enums.OwnerRateType.valueOf(ownerRate.getOwnerRateType().name()));
        addOwnerRateBean.setOwnerRole(com.yl.rate.interfaces.enums.OwnerRole.valueOf(ownerRate.getOwnerRole().name()));
        addOwnerRateBean.setId(ownerRate.getId());
        addOwnerRateBean.setCreateTime(ownerRate.getCreateTime());
        addOwnerRateBean.setCode(ownerRate.getCode());
        // 根据费率编号或模板编号查找费率
        Rate rate = null;
        if (ownerRate != null) {
            if (ownerRate.getOwnerRateType() == OwnerRateType.SPECIFIED) {
                rate = rateService.queryByCode(ownerRate.getRateCode());
            } else if (ownerRate.getOwnerRateType() == OwnerRateType.TEMPLATE) {
                RateTemplate rateTemplate = rateTemplateService.queryRateTemplate(ownerRate.getRateCode(), ownerRate.getProductType().name());
                rate = rateService.queryByCode(rateTemplate.getRateCode());
            }
        }
        addOwnerRateBean.setCompFeeType(com.yl.rate.interfaces.enums.CompFeeType.valueOf(rate.getCompFeeType().name()));
        if (CompFeeType.STANDARD == rate.getCompFeeType()) {
            List<RateRule> list = rateRuleService.queryRateRule(rate.getCode());
            if (list == null || list.size() == 0) {
                return null;
            }
            RateRule rateRule = list.get(0);
            NormalRate normalRate = normalRateService.queryByRateRuleCode(rateRule.getCode());
            if (normalRate != null){
                addOwnerRateBean.setNormalRateBean(JsonUtils.toObject(JsonUtils.toJsonString(normalRate), new TypeReference<NormalRateBean>() {
                }));
            }
        } else if (CompFeeType.LADDER == rate.getCompFeeType()) {
            ladderDetail(addOwnerRateBean, rate);
        } else if (CompFeeType.POS == rate.getCompFeeType()) {
            posDetail(addOwnerRateBean, rate);
        }
        return addOwnerRateBean;
    }

    @Override
    public HistoryOwnerRateBean queryByIdHistory(Long id) {
        HistoryOwnerRateBean addOwnerRateBean = new HistoryOwnerRateBean();
        OwnerRateHistory ownerRate = ownerRateHistoryService.queryById(id);
        addOwnerRateBean.setFeeType(com.yl.rate.interfaces.enums.FeeType.valueOf(ownerRate.getFeeType().name()));
        addOwnerRateBean.setOwnerId(ownerRate.getOwnerId());
        addOwnerRateBean.setTemplateName(ownerRate.getRateCode());
        addOwnerRateBean.setRemarks(ownerRate.getRemarks());
        addOwnerRateBean.setProductType(com.yl.rate.interfaces.enums.ProductType.valueOf(ownerRate.getProductType().name()));
        addOwnerRateBean.setProductCode(ownerRate.getProductCode());
        addOwnerRateBean.setOwnerRateType(com.yl.rate.interfaces.enums.OwnerRateType.valueOf(ownerRate.getOwnerRateType().name()));
        addOwnerRateBean.setOwnerRole(com.yl.rate.interfaces.enums.OwnerRole.valueOf(ownerRate.getOwnerRole().name()));
        addOwnerRateBean.setId(ownerRate.getId());
        addOwnerRateBean.setCreateTime(ownerRate.getCreateTime());
        addOwnerRateBean.setCode(ownerRate.getCode());
        // 根据费率编号或模板编号查找费率
        RateHistory rate = null;
        if (ownerRate != null) {
            if (ownerRate.getOwnerRateType() == OwnerRateType.SPECIFIED) {
                rate = rateHistoryService.queryByCode(ownerRate.getRateCode(), ownerRate.getId());
            } else if (ownerRate.getOwnerRateType() == OwnerRateType.TEMPLATE) {
                RateTemplateHistory rateTemplate = rateTemplateHistoryService.queryRateTemplate(ownerRate.getRateCode(), ownerRate.getProductType().name());
                addOwnerRateBean.setTemplateName(rateTemplate.getName());
                rate = rateHistoryService.queryByCode(rateTemplate.getRateCode(), ownerRate.getId());
            }
        }
        addOwnerRateBean.setCompFeeType(com.yl.rate.interfaces.enums.CompFeeType.valueOf(rate.getCompFeeType().name()));
        if (CompFeeType.STANDARD == rate.getCompFeeType()) {
            List<RateRuleHistory> list = rateRuleHistoryService.queryRateRule(rate.getCode(), addOwnerRateBean.getId());
            if (list == null || list.size() == 0) {
                return addOwnerRateBean;
            }
            RateRuleHistory rateRule = list.get(0);
            NormalRateHistory normalRate = normalRateHistoryService.queryByRateRuleCode(rateRule.getCode());
            if (normalRate != null){
                addOwnerRateBean.setNormalRateBean(JsonUtils.toObject(JsonUtils.toJsonString(normalRate), new TypeReference<NormalRateHistoryBean>() {
                }));
            }
        } else if (CompFeeType.LADDER == rate.getCompFeeType()) {
            ladderHistoryDetail(addOwnerRateBean, rate);
        } else if (CompFeeType.POS == rate.getCompFeeType()) {
            posHistoryDetail(addOwnerRateBean, rate);
        }
        return addOwnerRateBean;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateCustomize(AddOwnerRateBean addOwnerRateBean, String oper) {
        OwnerRate ownerRate = ownerRateService.queryByCode(addOwnerRateBean.getCode());
        if (ownerRate == null){
            return -1;
        }
        if (OwnerRateType.TEMPLATE.name().equals(addOwnerRateBean.getOwnerRateType().name())) {
            if (ownerRate.getOwnerRateType().equals(OwnerRateType.SPECIFIED)){
                Rate rate = rateService.queryByCode(ownerRate.getRateCode());
                deleteInfo(rate);
            }
            // 模板处理流程
            return updateTemplateInfo(addOwnerRateBean, ownerRate, oper);
        } else {
            // 自定义配置处理流程
            return updateCustomizeInfo(addOwnerRateBean, ownerRate, oper);
        }
    }

    /**
     * 修改自定义配置信息
     * @param addOwnerRateBean
     * @param oper
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateCustomizeInfo(AddOwnerRateBean addOwnerRateBean, OwnerRate ownerRate, String oper) {
        Rate rate = new Rate();
        rate.setCode(CodeBuilder.build("RATE", "yyyyMMdd"));
        rate.setStatus(Status.TRUE);
        rate.setCreateTime(new Date());
        String code = CodeBuilder.build("RR", "yyyyMMdd");
        boolean flag = false;
        ownerRate.setFeeType(FeeType.valueOf(addOwnerRateBean.getFeeType().name()));
        ownerRate.setLastUpdatedTime(new Date());
        ownerRate.setOwnerId(addOwnerRateBean.getOwnerId());
        ownerRate.setOwnerRateType(OwnerRateType.valueOf(addOwnerRateBean.getOwnerRateType().name()));
        ownerRate.setProductCode(addOwnerRateBean.getProductCode());
        ownerRate.setProductType(ProductType.valueOf(addOwnerRateBean.getProductType().name()));
        ownerRate.setOwnerRole(OwnerRole.valueOf(addOwnerRateBean.getOwnerRole().name()));
        ownerRate.setRemarks(addOwnerRateBean.getRemarks());
        ownerRate.setStatus(Status.TRUE);
        ownerRate.setRateCode(rate.getCode());
        Long historyId = ownerRateService.updateOwnerRate(ownerRate, oper);
        addOwnerRateBean.setId(historyId);
        // 针对原有数据进行判断删除
        if (!deleteInfo(rate)){
            return -1;
        }
        rate.setCompFeeType(CompFeeType.valueOf(addOwnerRateBean.getCompFeeType().name()));
        RateRule rateRule = new RateRule();
        // 自定义流程 ----  阶梯计费处理
        if (CompFeeType.LADDER.name().equals(addOwnerRateBean.getCompFeeType().name())) {
            if (addOwnerRateBean.getLadderRateBeans() == null || addOwnerRateBean.getLadderRateBeans().size() <= 0) {
                return -1;
            }
            updateLadder(addOwnerRateBean, oper, rate);
            flag = true;
        }
        // 自定义流程 ----  标准计费处理
        if (CompFeeType.STANDARD.name().equals(addOwnerRateBean.getCompFeeType().name())) {
            if (addOwnerRateBean.getNormalRateBean() == null) {
                return -1;
            }
            updateStandard(addOwnerRateBean, oper, rate, code);
            flag = true;
        }
        // 自定义流程 ----  POS线下计费处理
        if (CompFeeType.POS.name().equals(addOwnerRateBean.getCompFeeType().name())) {
            if (addOwnerRateBean.getPosRateBeans() == null || addOwnerRateBean.getPosRateBeans().size() <= 0) {
                return -1;
            }
            updatePOS(addOwnerRateBean, oper, rate);
            flag = true;
        }
        if (flag){
            rateService.addRate(rate, oper, historyId);
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * 修改模板配置信息
     * @param addOwnerRateBean
     * @param oper
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateTemplateInfo(AddOwnerRateBean addOwnerRateBean, OwnerRate ownerRate, String oper) {
        RateTemplate rateTemplate = rateTemplateService.queryRateTemplate(addOwnerRateBean.getTemplateName(), addOwnerRateBean.getProductType().name());
        if (rateTemplate == null){
            return -1;
        }
        ownerRate.setFeeType(FeeType.valueOf(addOwnerRateBean.getFeeType().name()));
        ownerRate.setLastUpdatedTime(new Date());
        ownerRate.setOwnerId(addOwnerRateBean.getOwnerId());
        ownerRate.setOwnerRateType(OwnerRateType.valueOf(addOwnerRateBean.getOwnerRateType().name()));
        ownerRate.setProductCode(addOwnerRateBean.getProductCode());
        ownerRate.setProductType(ProductType.valueOf(addOwnerRateBean.getProductType().name()));
        ownerRate.setOwnerRole(OwnerRole.valueOf(addOwnerRateBean.getOwnerRole().name()));
        ownerRate.setRateCode(rateTemplate.getCode());
        ownerRate.setRemarks(addOwnerRateBean.getRemarks());
        ownerRate.setStatus(Status.TRUE);
        Long id = ownerRateService.updateOwnerRate(ownerRate, oper);
        Rate rate = rateService.queryByCode(rateTemplate.getRateCode());
        rateService.delete(rate.getId());
        rate.setCreateTime(new Date());
        rate.setId(null);
        rateService.addRate(rate, oper, id);
        return 0;
    }

    /**
     * 新增模板配置
     * @param addOwnerRateBean
     * @param oper
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int template(AddOwnerRateBean addOwnerRateBean, String oper) {
        RateTemplate rateTemplate = rateTemplateService.queryRateTemplate(addOwnerRateBean.getTemplateName(), addOwnerRateBean.getProductType().name());
        if (rateTemplate == null){
            return -1;
        }
        OwnerRate ownerRate = new OwnerRate();
        ownerRate.setFeeType(FeeType.valueOf(addOwnerRateBean.getFeeType().name()));
        ownerRate.setLastUpdatedTime(new Date());
        ownerRate.setOwnerId(addOwnerRateBean.getOwnerId());
        ownerRate.setOwnerRateType(OwnerRateType.valueOf(addOwnerRateBean.getOwnerRateType().name()));
        ownerRate.setProductCode(addOwnerRateBean.getProductCode());
        ownerRate.setProductType(ProductType.valueOf(addOwnerRateBean.getProductType().name()));
        ownerRate.setOwnerRole(OwnerRole.valueOf(addOwnerRateBean.getOwnerRole().name()));
        ownerRate.setRateCode(rateTemplate.getCode());
        ownerRate.setRemarks(addOwnerRateBean.getRemarks());
        ownerRate.setStatus(Status.TRUE);
        ownerRate.setCode(CodeBuilder.build("OW", "yyyyMMdd"));
        ownerRate.setCreateTime(new Date());
        ownerRateService.insert(ownerRate, oper);
        return 0;
    }

    /**
     * 新增自定义配置
     * @param addOwnerRateBean
     * @param code
     * @param oper
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int customize(AddOwnerRateBean addOwnerRateBean, String oper) {
        String code = CodeBuilder.build("RR", "yyyyMMdd");
        boolean flag = false;
        Rate rate = new Rate();
        rate.setStatus(Status.FALSE);
        rate.setCreateTime(new Date());
        rate.setCompFeeType(CompFeeType.valueOf(addOwnerRateBean.getCompFeeType().name()));
        rate.setCode(CodeBuilder.build("RATE", "yyyyMMdd"));
        OwnerRate ownerRate = new OwnerRate();
        ownerRate.setFeeType(FeeType.valueOf(addOwnerRateBean.getFeeType().name()));
        ownerRate.setLastUpdatedTime(new Date());
        ownerRate.setOwnerId(addOwnerRateBean.getOwnerId());
        ownerRate.setOwnerRateType(OwnerRateType.valueOf(addOwnerRateBean.getOwnerRateType().name()));
        ownerRate.setProductCode(addOwnerRateBean.getProductCode());
        ownerRate.setProductType(ProductType.valueOf(addOwnerRateBean.getProductType().name()));
        ownerRate.setOwnerRole(OwnerRole.valueOf(addOwnerRateBean.getOwnerRole().name()));
        ownerRate.setRateCode(rate.getCode());
        ownerRate.setRemarks(addOwnerRateBean.getRemarks());
        ownerRate.setCode(CodeBuilder.build("OW", "yyyyMMdd"));
        ownerRate.setCreateTime(new Date());
        ownerRate.setStatus(Status.FALSE);
        Long historyId = ownerRateService.addOwnerRate(ownerRate, oper);
        rateService.addRate(rate, oper, historyId);
        addOwnerRateBean.setId(historyId);
        // 自定义流程 ----  阶梯计费处理
        if (CompFeeType.LADDER.name().equals(addOwnerRateBean.getCompFeeType().name())) {
            if (addOwnerRateBean.getLadderRateBeans() == null || addOwnerRateBean.getLadderRateBeans().size() <= 0) {
                return -1;
            }
            addLadderRate(addOwnerRateBean, rate, oper);
            flag = true;
        }
        // 自定义流程 ----  标准计费处理
        if (CompFeeType.STANDARD.name().equals(addOwnerRateBean.getCompFeeType().name())) {
            if (addOwnerRateBean.getNormalRateBean() == null) {
                return -1;
            }
            addNormalRate(addOwnerRateBean, rate, oper, code);
            flag = true;
        }
        // 自定义流程 ----  POS线下计费处理
        if (CompFeeType.POS.name().equals(addOwnerRateBean.getCompFeeType().name())) {
            if (addOwnerRateBean.getPosRateBeans() == null || addOwnerRateBean.getPosRateBeans().size() <= 0) {
                return -1;
            }
            addPosRate(addOwnerRateBean, rate, oper);
            flag = true;
        }
        if (flag){
            ownerRate.setStatus(Status.TRUE);
            rate.setStatus(Status.TRUE);
            OwnerRateHistory ownerRateHistory = new OwnerRateHistory();
            ownerRateHistory.setId(historyId);
            ownerRateHistory.setStatus(Status.TRUE);
            ownerRateHistoryService.updateStatus(ownerRateHistory);
            ownerRateService.updateStatus(ownerRate);
            rateService.updateStatus(rate);
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * 自定义流程 ----  POS线下计费处理 新增
     * @param addOwnerRateBean
     * @param rate
     * @param oper
     */
    public void addPosRate(AddOwnerRateBean addOwnerRateBean, Rate rate, String oper){
        List<PosRate> posRates = new ArrayList<>();
        List<RateRule> rateRules = new ArrayList<>();
        PosRate posRate = new PosRate();
        RateRule rateRule = null;
        for (int i = 0; i < addOwnerRateBean.getPosRateBeans().size(); i++) {
            rateRule = new RateRule();
            posRate = JsonUtils.toObject(JsonUtils.toJsonString(addOwnerRateBean.getPosRateBeans().get(i)), new TypeReference<PosRate>() {
            });
            posRate.setCreateTime(new Date());
            posRate.setRateRuleCode(CodeBuilder.build("RR", "yyyyMMdd"));
            posRate.setCode(CodeBuilder.build("LR", "yyyyMMdd"));
            posRates.add(posRate);
            rateRule.setCreateTime(new Date());
            rateRule.setCode(posRate.getRateRuleCode());
            rateRule.setRateCode(rate.getCode());
            rateRules.add(rateRule);
        }
        posRateService.insertList(posRates, oper);
        rateRuleService.addRateRules(rateRules, oper, addOwnerRateBean.getId());
    }

    /**
     * 自定义流程 ----  阶梯计费处理 新增
     * @param addOwnerRateBean
     * @param rate
     * @param oper
     */
    public void addLadderRate(AddOwnerRateBean addOwnerRateBean, Rate rate, String oper){
        List<LadderRate> ladderRates = new ArrayList<>();
        List<RateRule> rateRules = new ArrayList<>();
        LadderRate ladderRate = new LadderRate();
        RateRule rateRule = null;
        for (int i = 0; i < addOwnerRateBean.getLadderRateBeans().size(); i++) {
            rateRule = new RateRule();
            ladderRate = JsonUtils.toObject(JsonUtils.toJsonString(addOwnerRateBean.getLadderRateBeans().get(i)), new TypeReference<LadderRate>() {
            });
            ladderRate.setCreateTime(new Date());
            ladderRate.setRateRuleCode(CodeBuilder.build("RR", "yyyyMMdd"));
            ladderRate.setCode(CodeBuilder.build("LR", "yyyyMMdd"));
            ladderRates.add(ladderRate);
            rateRule.setCreateTime(new Date());
            rateRule.setCode(ladderRate.getRateRuleCode());
            rateRule.setRateCode(rate.getCode());
            rateRules.add(rateRule);
        }
        ladderRateService.insertList(ladderRates, oper);
        rateRuleService.addRateRules(rateRules, oper, addOwnerRateBean.getId());
    }

    /**
     * 自定义流程 ----  标准计费处理 新增
     * @param addOwnerRateBean
     * @param rate
     * @param oper
     * @param code
     */
    public void addNormalRate(AddOwnerRateBean addOwnerRateBean, Rate rate, String oper, String code){
        RateRule rateRule = new RateRule();
        NormalRate normalRate = JsonUtils.toObject(JsonUtils.toJsonString(addOwnerRateBean.getNormalRateBean()), new TypeReference<NormalRate>() {
        });
        normalRate.setRateRuleCode(code);
        normalRate.setCode(CodeBuilder.build("NR", "yyyyMMdd"));
        normalRate.setCreateTime(new Date());
        rateRule.setCreateTime(new Date());
        rateRule.setCode(code);
        rateRule.setRateCode(rate.getCode());
        rateRuleService.addRateRule(rateRule, oper, addOwnerRateBean.getId());
        normalRateService.insert(normalRate, oper);
    }

    /**
     * POS 详情统计
     * @param addOwnerRateBean
     * @param rate
     */
    public void posDetail(AddOwnerRateBean addOwnerRateBean, Rate rate){
        List<PosRateBean> posRates = new ArrayList<>();
        List<RateRule> list = rateRuleService.queryRateRule(rate.getCode());
        PosRate posRate = new PosRate();
        for (RateRule rateRule : list) {
            posRate = posRateService.queryByRateRuleCode(rateRule.getCode());
            if (posRate != null){
                posRates.add(JsonUtils.toObject(JsonUtils.toJsonString(posRate), new TypeReference<PosRateBean>() {
                }));
            }
        }
        addOwnerRateBean.setPosRateBeans(posRates);
    }

    /**
     * 阶梯详情统计
     * @param addOwnerRateBean
     * @param rate
     */
    public void ladderDetail(AddOwnerRateBean addOwnerRateBean, Rate rate){
        List<LadderRateBean> ladderRateBeans = new ArrayList<>();
        List<RateRule> list = rateRuleService.queryRateRule(rate.getCode());
        LadderRate ladderRate = new LadderRate();
        for (RateRule rateRule : list) {
            ladderRate = ladderRateService.queryByRateRuleCode(rateRule.getCode());
            if (ladderRate != null){
                ladderRateBeans.add(JsonUtils.toObject(JsonUtils.toJsonString(ladderRate), new TypeReference<LadderRateBean>() {
                }));
            }
        }
        addOwnerRateBean.setLadderRateBeans(ladderRateBeans);
    }

    /**
     * POS历史详情统计
     * @param addOwnerRateBean
     * @param rate
     */
    public void posHistoryDetail(HistoryOwnerRateBean addOwnerRateBean, RateHistory rate){
        List<PosRateHistoryBean> posRates = new ArrayList<>();
        List<RateRuleHistory> list = rateRuleHistoryService.queryRateRule(rate.getCode(), addOwnerRateBean.getId());
        PosRateHistory posRate = new PosRateHistory();
        for (RateRuleHistory rateRule : list) {
            posRate = posRateHistoryService.queryByRateRuleCode(rateRule.getCode());
            if (posRate != null){
                posRates.add(JsonUtils.toObject(JsonUtils.toJsonString(posRate), new TypeReference<PosRateHistoryBean>(){
                }));
            }
        }
        addOwnerRateBean.setPosRateBeans(posRates);
    }

    /**
     * 阶梯历史详情统计
     * @param addOwnerRateBean
     * @param rate
     */
    public void ladderHistoryDetail(HistoryOwnerRateBean addOwnerRateBean, RateHistory rate){
        List<LadderRateHistoryBean> ladderRateBeans = new ArrayList<>();
        List<RateRuleHistory> list = rateRuleHistoryService.queryRateRule(rate.getCode(), addOwnerRateBean.getId());
        LadderRateHistory ladderRate = new LadderRateHistory();
        for (RateRuleHistory rateRule : list) {
            ladderRate = ladderRateHistoryService.queryByRateRuleCode(rateRule.getCode());
            if (ladderRate != null){
                ladderRateBeans.add(JsonUtils.toObject(JsonUtils.toJsonString(ladderRate), new TypeReference<LadderRateHistoryBean>() {
                }));
            }
        }
        addOwnerRateBean.setLadderRateBeans(ladderRateBeans);
    }

    /**
     * 针对原有数据进行判断删除
     * @param rate
     * @return
     */
    public boolean deleteInfo(Rate rate){
        if (CompFeeType.STANDARD == rate.getCompFeeType()) {
            List<RateRule> list = rateRuleService.queryRateRule(rate.getCode());
            if (list == null || list.size() == 0) {
                return false;
            }
            RateRule rateRule = list.get(0);
            NormalRate normalRate = normalRateService.queryByRateRuleCode(rateRule.getCode());
            // 删除原有数据
            normalRateService.delete(normalRate.getId());
            rateRuleService.delete(rateRule.getId());
        } else if (CompFeeType.LADDER == rate.getCompFeeType()) {
            List<LadderRateBean> ladderRateBeans = new ArrayList<>();
            List<RateRule> list = rateRuleService.queryRateRule(rate.getCode());
            LadderRate ladderRate = new LadderRate();
            // 删除原有数据
            for (RateRule rateRule : list) {
                ladderRate = ladderRateService.queryByRateRuleCode(rateRule.getCode());
                ladderRateService.delete(ladderRate.getId());
                rateRuleService.delete(rateRule.getId());
            }
        } else if (CompFeeType.POS == rate.getCompFeeType()) {
            List<PosRateBean> posRates = new ArrayList<>();
            List<RateRule> list = rateRuleService.queryRateRule(rate.getCode());
            PosRate posRate = new PosRate();
            // 删除原有数据
            for (RateRule rateRule : list) {
                posRate = posRateService.queryByRateRuleCode(rateRule.getCode());
                posRateService.delete(posRate.getId());
                rateRuleService.delete(rateRule.getId());
            }
        }
        return true;
    }

    /**
     * 自定义流程 ----  POS线下计费处理 修改
     * @param addOwnerRateBean
     * @param oper
     * @param rate
     */
    public void updatePOS(AddOwnerRateBean addOwnerRateBean, String oper, Rate rate){
        List<PosRate> posRates = new ArrayList<>();
        List<RateRule> rateRules = new ArrayList<>();
        PosRate posRate = new PosRate();
        RateRule rateRule = null;
        for (int i = 0; i < addOwnerRateBean.getPosRateBeans().size(); i++) {
            posRate = JsonUtils.toObject(JsonUtils.toJsonString(addOwnerRateBean.getPosRateBeans().get(i)), new TypeReference<PosRate>() {
            });
            rateRule = new RateRule();
            posRate.setCreateTime(new Date());
            posRate.setRateRuleCode(CodeBuilder.build("RR", "yyyyMMdd"));
            posRate.setCode(CodeBuilder.build("LR", "yyyyMMdd"));
            posRates.add(posRate);
            rateRule.setCreateTime(new Date());
            rateRule.setCode(posRate.getRateRuleCode());
            rateRule.setRateCode(rate.getCode());
            rateRules.add(rateRule);
        }
        posRateService.insertList(posRates, oper);
        rateRuleService.addRateRules(rateRules, oper, addOwnerRateBean.getId());
    }

    /**
     * 自定义流程 ----  标准计费处理 修改
     * @param addOwnerRateBean
     * @param oper
     * @param rate
     * @param code
     */
    public void updateStandard(AddOwnerRateBean addOwnerRateBean, String oper, Rate rate, String code){
        RateRule rateRule = new RateRule();
        NormalRate normalRate = JsonUtils.toObject(JsonUtils.toJsonString(addOwnerRateBean.getNormalRateBean()), new TypeReference<NormalRate>() {
        });
        normalRate.setRateRuleCode(code);
        normalRate.setCode(CodeBuilder.build("NR", "yyyyMMdd"));
        normalRate.setCreateTime(new Date());
        rateRule.setCreateTime(new Date());
        rateRule.setCode(code);
        rateRule.setRateCode(rate.getCode());
        rateRuleService.addRateRule(rateRule, oper, addOwnerRateBean.getId());
        normalRateService.insert(normalRate, oper);
    }

    /**
     * 自定义流程 ----  阶梯计费处理 修改
     * @param addOwnerRateBean
     * @param oper
     * @param rate
     */
    public void updateLadder(AddOwnerRateBean addOwnerRateBean, String oper, Rate rate){
        List<LadderRate> ladderRates = new ArrayList<>();
        List<RateRule> rateRules = new ArrayList<>();
        LadderRate ladderRate = new LadderRate();
        RateRule rateRule = new RateRule();
        for (int i = 0; i < addOwnerRateBean.getLadderRateBeans().size(); i++) {
            rateRule = new RateRule();
            ladderRate = JsonUtils.toObject(JsonUtils.toJsonString(addOwnerRateBean.getLadderRateBeans().get(i)), new TypeReference<LadderRate>() {
            });
            ladderRate.setCreateTime(new Date());
            ladderRate.setRateRuleCode(CodeBuilder.build("RR", "yyyyMMdd"));
            ladderRate.setCode(CodeBuilder.build("LR", "yyyyMMdd"));
            ladderRates.add(ladderRate);
            rateRule.setCreateTime(new Date());
            rateRule.setCode(ladderRate.getRateRuleCode());
            rateRule.setRateCode(rate.getCode());
            rateRules.add(rateRule);
        }
        ladderRateService.insertList(ladderRates, oper);
        rateRuleService.addRateRules(rateRules, oper, addOwnerRateBean.getId());
    }
}
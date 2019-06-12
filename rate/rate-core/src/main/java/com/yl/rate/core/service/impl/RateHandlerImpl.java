package com.yl.rate.core.service.impl;

import com.yl.rate.core.ExceptionMessages;
import com.yl.rate.core.bean.PosRateBean;
import com.yl.rate.core.entity.*;
import com.yl.rate.core.enums.CompFeeType;
import com.yl.rate.core.enums.Condition;
import com.yl.rate.core.enums.OwnerRateType;
import com.yl.rate.core.service.*;
import com.yl.rate.core.utils.FeeUtils;
import com.yl.rate.interfaces.beans.RateRequestBean;
import com.yl.rate.interfaces.beans.RateResponseBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author Shark
 * @Description 费率处理实现类
 * @Date 2017/12/7 11:08
 */
@Service("rateHandler")
public class RateHandlerImpl implements RateHandler {

    @Resource
    private OwnerRateService ownerRateService;
    @Resource
    private RateService rateService;
    @Resource
    private RateRuleService rateRuleService;
    @Resource
    private RateTemplateService rateTemplateService;
    @Resource
    private NormalRateService normalRateService;
    @Resource
    private LadderRateService ladderRateService;
    @Resource
    private PosRateService posRateService;

    @Override
    public RateResponseBean getFee(RateRequestBean rateRequestBean) {
        // 初始化返回参数
        RateResponseBean rateResponseBean = new RateResponseBean();

        if (rateRequestBean == null) {
            rateResponseBean.setResponseCode(ExceptionMessages.PARAMETER_ERROR.getCode());
            rateResponseBean.setResponseMessage(ExceptionMessages.PARAMETER_ERROR.getMessage());
            return rateResponseBean;
        }

        // 根据所有者id和角色名称还有产品类型查询费率
        OwnerRate ownerRate = ownerRateService.queryByOwnerId(rateRequestBean.getOwnerId(), rateRequestBean.getOwnerRole().name(),
                rateRequestBean.getProductType().name(), rateRequestBean.getProductCode());
        // 根据费率编号或模板编号查找费率
        Rate rate = null;
        if (ownerRate != null) {
            if (ownerRate.getOwnerRateType() == OwnerRateType.SPECIFIED) {
                rate = rateService.queryByCode(ownerRate.getRateCode());
            } else if (ownerRate.getOwnerRateType() == OwnerRateType.TEMPLATE) {
                RateTemplate rateTemplate = rateTemplateService.queryRateTemplate(ownerRate.getRateCode(), rateRequestBean.getProductType().name());
                rate = rateService.queryByCode(rateTemplate.getRateCode());
            }
        } else {
            RateTemplate rateTemplate = rateTemplateService.queryDefaultRateTemplate(rateRequestBean.getProductType().name(), rateRequestBean.getOwnerRole().name());
            if (rateTemplate != null) {
                rate = rateService.queryByCode(rateTemplate.getRateCode());
            }
        }
        if (rate != null) {
            /** 标准计费 */
            if (CompFeeType.STANDARD == rate.getCompFeeType()) {
                List<RateRule> list = rateRuleService.queryRateRule(rate.getCode());
                if (list == null || list.size() == 0) {
                    return rateResponseBean;
                }
                RateRule rateRule = list.get(0);
                NormalRate normalRate = normalRateService.queryByRateRuleCode(rateRule.getCode());
                double fee = FeeUtils.computeFee(rateRequestBean.getAmount(), normalRate.getComputeMode(), normalRate.getFee(), normalRate.getMinFee(), normalRate.getMaxFee());
                rateResponseBean.setFee(fee);
                rateResponseBean.setResponseCode(ExceptionMessages.SUCCESS.getCode());
                rateResponseBean.setResponseMessage(ExceptionMessages.SUCCESS.getMessage());
                rateResponseBean.setStatus("SUCCESS");
            } else if (CompFeeType.COMBINATION == rate.getCompFeeType()) {
                // 组合费率（暂未实现）
            } else if (CompFeeType.LADDER == rate.getCompFeeType()) {
                // 阶梯费率
                rateResponseBean = getLadderFee(rateRequestBean, rate);
            } else if (CompFeeType.POS == rate.getCompFeeType()) {
                // pos费率
                rateResponseBean = getPosFee(rateRequestBean, rate);
            }
        }
        return rateResponseBean;
    }

    /**
     * 计算阶梯费率
     *
     * @param rateRequestBean
     * @param rate
     * @return
     */
    private RateResponseBean getLadderFee(RateRequestBean rateRequestBean, Rate rate) {
        RateResponseBean rateResponseBean = new RateResponseBean();
        List<RateRule> list = rateRuleService.queryRateRule(rate.getCode());
        for (RateRule rateRule : list) {
            LadderRate ladderRate = ladderRateService.queryByRateRuleCode(rateRule.getCode());
            if (ladderRate != null && ladderRate.getRateCondition() == Condition.AMOUNT) {
                if (NumberUtils.compare(rateRequestBean.getAmount(), Double.valueOf(ladderRate.getBeginCondition())) >= 0
                        && NumberUtils.compare(rateRequestBean.getAmount(), Double.valueOf(ladderRate.getEndCondition())) < 0) {
                    double fee = FeeUtils.computeFee(rateRequestBean.getAmount(), ladderRate.getComputeMode(), ladderRate.getFee(), ladderRate.getMinFee(), ladderRate.getMaxFee());
                    rateResponseBean.setFee(fee);
                    rateResponseBean.setResponseCode(ExceptionMessages.SUCCESS.getCode());
                    rateResponseBean.setResponseMessage(ExceptionMessages.SUCCESS.getMessage());
                    rateResponseBean.setStatus("SUCCESS");
                }
            }
        }
        return rateResponseBean;
    }

    /**
     * 获取pos费率
     *
     * @param rateRequestBean
     * @param rate
     * @return
     */
    private RateResponseBean getPosFee(RateRequestBean rateRequestBean, Rate rate) {
        RateResponseBean rateResponseBean = new RateResponseBean();
        if (rateRequestBean.getStandard() == null || rateRequestBean.getCardType() == null) {
            rateResponseBean.setResponseCode(ExceptionMessages.PARAMETER_ERROR.getCode());
            rateResponseBean.setResponseMessage(ExceptionMessages.PARAMETER_ERROR.getMessage());
            return rateResponseBean;
        }
        // 根据卡类型，是否标准查找费率信息
        PosRateBean posRateBean = posRateService.queryByRateCode(rate.getCode(), rateRequestBean.getCardType().name(),
                rateRequestBean.getStandard().name());

        // pos费率
        if (posRateBean != null) {
            double fee = FeeUtils.computeFee(rateRequestBean.getAmount(), posRateBean.getComputeMode(), posRateBean.getFee(),
                    posRateBean.getMinFee(), posRateBean.getMaxFee());
            rateResponseBean.setFee(fee);
            rateResponseBean.setResponseCode(ExceptionMessages.SUCCESS.getCode());
            rateResponseBean.setResponseMessage(ExceptionMessages.SUCCESS.getMessage());
            rateResponseBean.setStatus("SUCCESS");
            return rateResponseBean;
        }

        return rateResponseBean;
    }
}

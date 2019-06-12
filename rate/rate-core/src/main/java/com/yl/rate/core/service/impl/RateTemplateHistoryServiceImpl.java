package com.yl.rate.core.service.impl;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.DateUtils;
import com.yl.rate.core.dao.*;
import com.yl.rate.core.entity.*;
import com.yl.rate.core.service.RateTemplateHistoryService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("rateTemplateHistoryService")
public class RateTemplateHistoryServiceImpl implements RateTemplateHistoryService {

    @Resource
    private RateTemplateHistoryMapper rateTemplateHistoryMapper;

    @Resource
    private RateHistoryMapper rateHistoryMapper;

    @Resource
    private RateRuleHistoryMapper rateRuleHistoryMapper;

    @Resource
    private LadderRateHistoryMapper ladderRateHistoryMapper;

    @Resource
    private NormalRateHistoryMapper normalRateHistoryMapper;

    @Resource
    private PosRateHistoryMapper posRateHistoryMapper;



    @Override
    public void insert(RateTemplateHistory rateTemplateHistory) {
        rateTemplateHistoryMapper.insert(rateTemplateHistory);
    }

    @Override
    public RateTemplateHistory queryRateTemplate(String code, String productType) {
        return rateTemplateHistoryMapper.queryRateTemplate(code, productType);
    }

    @Override
    public RateTemplateHistory queryDefaultRateTemplate(String productType, String ownerRole) {
        return null;
    }

    @Override
    public Page findAllRateTemplateHistoryByCode(String code, Page page) {
        page.setObject(rateTemplateHistoryMapper.findAllRateTemplateHistoryByCode(code, page));
        return page;
    }

    @Override
    public Map<String, Object> queryRateTemplateHistoryDetails(Long id) {

        Map<String, Object> params = new HashMap<>();

        RateTemplateHistory rateTemplateHistory = rateTemplateHistoryMapper.queryRateTemplateById(id);

        params.put("rateTemplateHistory", rateTemplateHistory);

        //查询费率信息
        RateHistory rateHistory = rateHistoryMapper.queryByCode(rateTemplateHistory.getRateCode(), Long.valueOf(1));
        params.put("rateHistory", rateHistory);

        //根据费率编号和历史创建时间查询出当前所属的规则信息
        List<RateRuleHistory> rateRuleHistories = rateRuleHistoryMapper.queryRateRuleByCodeAndDate(rateHistory.getCode(), DateUtils.addSeconds(rateHistory.getCreateTime(), -2), DateUtils.addSeconds(rateHistory.getCreateTime(), +2));

        switch (rateHistory.getCompFeeType()) {
            case STANDARD:
                NormalRateHistory normalRateHistory = normalRateHistoryMapper.queryNormalRate(rateRuleHistories.get(0).getCode(), DateUtils.addSeconds(rateRuleHistories.get(0).getCreateTime(), -2), DateUtils.addSeconds(rateRuleHistories.get(0).getCreateTime(), +2));
                params.put("normalRateHistory", normalRateHistory);
                break;
            case LADDER:
                List<LadderRateHistory> ladderRateHistories = new ArrayList<>();
                for (int i = 0; i < rateRuleHistories.size(); i++) {
                    ladderRateHistories.add(ladderRateHistoryMapper.queryLadderRate(rateRuleHistories.get(i).getCode(), DateUtils.addSeconds(rateRuleHistories.get(i).getCreateTime(), -2), DateUtils.addSeconds(rateRuleHistories.get(i).getCreateTime(), +2)));
                }
                params.put("ladderRateHistories", ladderRateHistories);
                break;
            case POS:
                List<PosRateHistory> posRateHistories = new ArrayList<>();
                for (int i = 0; i < rateRuleHistories.size(); i++) {
                    posRateHistories.add(posRateHistoryMapper.queryPosRate(rateRuleHistories.get(i).getCode(), DateUtils.addSeconds(rateRuleHistories.get(i).getCreateTime(), -2), DateUtils.addSeconds(rateRuleHistories.get(i).getCreateTime(), +2)));
                }
                params.put("posRateHistories", posRateHistories);
                break;
        }

        return params;
    }
}
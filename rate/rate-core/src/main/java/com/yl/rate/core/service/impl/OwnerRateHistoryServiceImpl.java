package com.yl.rate.core.service.impl;

import com.lefu.commons.utils.Page;
import com.yl.rate.core.dao.OwnerRateHistoryMapper;
import com.yl.rate.core.entity.OwnerRateHistory;
import com.yl.rate.core.service.OwnerRateHistoryService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 费率信息历史记录访问接口实现
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/12/12
 */
@Service("ownerRateHistoryService")
public class OwnerRateHistoryServiceImpl implements OwnerRateHistoryService {

    @Resource
    private OwnerRateHistoryMapper ownerRateHistoryMapper;

    @Override
    public Page findAllFeeConfigHistory(Page page, String code) {
        page.setObject(ownerRateHistoryMapper.findAllFeeConfigHistory(page, code));
        return page;
    }

    @Override
    public int insert(OwnerRateHistory ownerRateHistory) {
        return ownerRateHistoryMapper.insert(ownerRateHistory);
    }

    @Override
    public OwnerRateHistory queryById(Long id) {
        return ownerRateHistoryMapper.queryById(id);
    }

    @Override
    public int updateStatus(OwnerRateHistory ownerRateHistory) {
        return ownerRateHistoryMapper.updateStatus(ownerRateHistory);
    }
}
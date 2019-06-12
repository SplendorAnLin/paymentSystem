package com.yl.rate.core.utils;

import com.lefu.commons.utils.lang.AmountUtils;
import com.yl.rate.core.ExceptionMessages;
import com.yl.rate.core.enums.ComputeMode;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Fee工具类
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年10月22日
 */
public class FeeUtils {


    /**
     * 计算商户交易费率
     *
     * @param amount
     * @param feeType
     * @param fee
     * @param minFee
     * @param maxFee
     * @return
     */
    public static double computeFee(Double amount, ComputeMode feeType, Double fee, Double minFee, Double maxFee) {
        double computeFee;
        switch (feeType) {
            case FIXED:
                computeFee = fee;
                break;
            case ROUND:
                computeFee = AmountUtils.multiply(amount, fee);
                computeFee = new BigDecimal(computeFee).setScale(2, RoundingMode.HALF_UP).doubleValue();
                break;
            case ROUND_UP:
                computeFee = AmountUtils.multiply(amount, fee);
                computeFee = new BigDecimal(computeFee).setScale(2, RoundingMode.UP).doubleValue();
                break;
            case ROUND_DOWN:
                computeFee = AmountUtils.multiply(amount, fee);
                computeFee = new BigDecimal(computeFee).setScale(2, RoundingMode.DOWN).doubleValue();
                break;
            default:
                throw new RuntimeException(ExceptionMessages.FEE_TYPE_ERROR.getCode());
        }
        if (computeFee < 0) {
            throw new RuntimeException(ExceptionMessages.AMOUNT_FEE_ERROR.getCode());
        }

        if (minFee != null && computeFee < minFee) {
            return minFee;
        }
        if (maxFee != null && computeFee > maxFee) {
            return maxFee;
        }
        return computeFee;
    }

    public static void main(String[] args) {
    }
}

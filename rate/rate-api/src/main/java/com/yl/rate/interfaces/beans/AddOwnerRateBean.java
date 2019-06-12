package com.yl.rate.interfaces.beans;

import com.yl.rate.interfaces.enums.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 新增自定义费率
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/12/14
 */
public class AddOwnerRateBean extends BaseBean {

    /** 所有者角色 */
    private OwnerRole ownerRole;

    /** 所有者编号 */
    private String ownerId;

    /** 产品类型 */
    private ProductType productType;

    /** 应用模式 */
    private FeeType feeType;

    /** 通道编号 */
    private String productCode;

    /** 是否使用模板 */
    private OwnerRateType ownerRateType;

    /** 模板名称 */
    private String templateName;

    /** 备注 */
    private String remarks;

    /** 计费方式 */
    private CompFeeType compFeeType;

    /** 标准费率 */
    private NormalRateBean normalRateBean = new NormalRateBean();

    /** 银行卡收单费率 */
    private List<PosRateBean> posRateBeans = new ArrayList<>();

    /** 阶梯费率 */
    private List<LadderRateBean> ladderRateBeans = new ArrayList<>();


    public OwnerRole getOwnerRole() {
        return ownerRole;
    }

    public void setOwnerRole(OwnerRole ownerRole) {
        this.ownerRole = ownerRole;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public FeeType getFeeType() {
        return feeType;
    }

    public void setFeeType(FeeType feeType) {
        this.feeType = feeType;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public OwnerRateType getOwnerRateType() {
        return ownerRateType;
    }

    public void setOwnerRateType(OwnerRateType ownerRateType) {
        this.ownerRateType = ownerRateType;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public NormalRateBean getNormalRateBean() {
        return normalRateBean;
    }

    public void setNormalRateBean(NormalRateBean normalRateBean) {
        this.normalRateBean = normalRateBean;
    }

    public List<PosRateBean> getPosRateBeans() {
        return posRateBeans;
    }

    public void setPosRateBeans(List<PosRateBean> posRateBeans) {
        this.posRateBeans = posRateBeans;
    }

    public List<LadderRateBean> getLadderRateBeans() {
        return ladderRateBeans;
    }

    public void setLadderRateBeans(List<LadderRateBean> ladderRateBeans) {
        this.ladderRateBeans = ladderRateBeans;
    }

    public CompFeeType getCompFeeType() {
        return compFeeType;
    }

    public void setCompFeeType(CompFeeType compFeeType) {
        this.compFeeType = compFeeType;
    }

    @Override
    public String toString() {
        return "AddOwnerRateBean{" +
                "ownerRole=" + ownerRole +
                ", ownerId='" + ownerId + '\'' +
                ", productType=" + productType +
                ", feeType=" + feeType +
                ", productCode='" + productCode + '\'' +
                ", ownerRateType=" + ownerRateType +
                ", templateName='" + templateName + '\'' +
                ", remarks='" + remarks + '\'' +
                ", compFeeType=" + compFeeType +
                ", normalRateBean=" + normalRateBean +
                ", posRateBeans=" + posRateBeans +
                ", ladderRateBeans=" + ladderRateBeans +
                '}';
    }
}
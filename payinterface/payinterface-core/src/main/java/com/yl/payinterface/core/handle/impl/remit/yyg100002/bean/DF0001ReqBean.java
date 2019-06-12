package com.yl.payinterface.core.handle.impl.remit.yyg100002.bean;

import java.util.List;

/**
 * 批量代付
 *
 * @author wanglei
 * @create 2017-07-19 上午11:16
 **/
public class DF0001ReqBean {

    private BatchPayHeadReq head;

    private String totleAmount;

    private String totleSize;

    private String remark;

    private String batchPaySN;

    private BatchPays batchPays;



    public String getTotleAmount() {
        return totleAmount;
    }

    public void setTotleAmount(String totleAmount) {
        this.totleAmount = totleAmount;
    }

    public String getTotleSize() {
        return totleSize;
    }

    public void setTotleSize(String totleSize) {
        this.totleSize = totleSize;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBatchPaySN() {
        return batchPaySN;
    }

    public void setBatchPaySN(String batchPaySN) {
        this.batchPaySN = batchPaySN;
    }

    public BatchPayHeadReq getHead() {
        return head;
    }

    public void setHead(BatchPayHeadReq head) {
        this.head = head;
    }

    public BatchPays getBatchPays() {
        return batchPays;
    }

    public void setBatchPays(BatchPays batchPays) {
        this.batchPays = batchPays;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DF0001ReqBean{");
        sb.append("head=").append(head);
        sb.append(", totleAmount='").append(totleAmount).append('\'');
        sb.append(", totleSize='").append(totleSize).append('\'');
        sb.append(", remark='").append(remark).append('\'');
        sb.append(", batchPaySN='").append(batchPaySN).append('\'');
        sb.append(", batchPays=").append(batchPays);
        sb.append('}');
        return sb.toString();
    }

    public static class BatchPayHeadReq {
        private String tranCode;
        private String channelVersion;
        private String apiVersion;
        private String channelDate;
        private String channelTime;
        private String channelSerial;
        private String proxyID;
        private String sign;

        public String getTranCode() {
            return tranCode;
        }

        public void setTranCode(String tranCode) {
            this.tranCode = tranCode;
        }

        public String getChannelVersion() {
            return channelVersion;
        }

        public void setChannelVersion(String channelVersion) {
            this.channelVersion = channelVersion;
        }

        public String getApiVersion() {
            return apiVersion;
        }

        public void setApiVersion(String apiVersion) {
            this.apiVersion = apiVersion;
        }

        public String getChannelDate() {
            return channelDate;
        }

        public void setChannelDate(String channelDate) {
            this.channelDate = channelDate;
        }

        public String getChannelTime() {
            return channelTime;
        }

        public void setChannelTime(String channelTime) {
            this.channelTime = channelTime;
        }

        public String getChannelSerial() {
            return channelSerial;
        }

        public void setChannelSerial(String channelSerial) {
            this.channelSerial = channelSerial;
        }

        public String getProxyID() {
            return proxyID;
        }

        public void setProxyID(String proxyID) {
            this.proxyID = proxyID;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("BatchPayHeadReq{");
            sb.append("tranCode='").append(tranCode).append('\'');
            sb.append(", channelVersion='").append(channelVersion).append('\'');
            sb.append(", apiVersion='").append(apiVersion).append('\'');
            sb.append(", channelDate='").append(channelDate).append('\'');
            sb.append(", channelTime='").append(channelTime).append('\'');
            sb.append(", channelSerial='").append(channelSerial).append('\'');
            sb.append(", proxyID='").append(proxyID).append('\'');
            sb.append(", sign='").append(sign).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

//    public String getBatchPays() {
//        return batchPays;
//    }
//
//    public void setBatchPays(String batchPays) {
//        this.batchPays = batchPays;
//    }
//
//    public String getHead() {
//        return head;
//    }
//
//    public void setHead(String head) {
//        this.head = head;
//    }

    public static class BatchPays {

        private List<BatchPay> batchPay;

        public List<BatchPay> getBatchPay() {
            return batchPay;
        }

        public void setBatchPay(List<BatchPay> batchPay) {
            this.batchPay = batchPay;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("batchPays{");
            sb.append("batchPay=").append(batchPay);
            sb.append('}');
            return sb.toString();
        }

        public static class BatchPay {
            private String batchPaySN;
            private String bankCode;
            private String accountType;
            private String accountNo;
            private String accountName;
            private String province;
            private String city;
            private String bankName;
            private String amount;
            private String curency;
            private String idType;
            private String idNo;
            private String tel;
            private String remark;
            private String creValDate;
            private String creValCV2;

            public String getBatchPaySN() {
                return batchPaySN;
            }

            public void setBatchPaySN(String batchPaySN) {
                this.batchPaySN = batchPaySN;
            }

            public String getBankCode() {
                return bankCode;
            }

            public void setBankCode(String bankCode) {
                this.bankCode = bankCode;
            }

            public String getAccountType() {
                return accountType;
            }

            public void setAccountType(String accountType) {
                this.accountType = accountType;
            }

            public String getAccountNo() {
                return accountNo;
            }

            public void setAccountNo(String accountNo) {
                this.accountNo = accountNo;
            }

            public String getAccountName() {
                return accountName;
            }

            public void setAccountName(String accountName) {
                this.accountName = accountName;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getBankName() {
                return bankName;
            }

            public void setBankName(String bankName) {
                this.bankName = bankName;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getCurency() {
                return curency;
            }

            public void setCurency(String curency) {
                this.curency = curency;
            }

            public String getIdType() {
                return idType;
            }

            public void setIdType(String idType) {
                this.idType = idType;
            }

            public String getIdNo() {
                return idNo;
            }

            public void setIdNo(String idNo) {
                this.idNo = idNo;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getCreValDate() {
                return creValDate;
            }

            public void setCreValDate(String creValDate) {
                this.creValDate = creValDate;
            }

            public String getCreValCV2() {
                return creValCV2;
            }

            public void setCreValCV2(String creValCV2) {
                this.creValCV2 = creValCV2;
            }

            @Override
            public String toString() {
                final StringBuffer sb = new StringBuffer("batchPay{");
                sb.append("batchPaySN='").append(batchPaySN).append('\'');
                sb.append(", bankCode='").append(bankCode).append('\'');
                sb.append(", accountType='").append(accountType).append('\'');
                sb.append(", accountNo='").append(accountNo).append('\'');
                sb.append(", accountName='").append(accountName).append('\'');
                sb.append(", province='").append(province).append('\'');
                sb.append(", city='").append(city).append('\'');
                sb.append(", bankName='").append(bankName).append('\'');
                sb.append(", amount='").append(amount).append('\'');
                sb.append(", curency='").append(curency).append('\'');
                sb.append(", idType='").append(idType).append('\'');
                sb.append(", idNo='").append(idNo).append('\'');
                sb.append(", tel='").append(tel).append('\'');
                sb.append(", remark='").append(remark).append('\'');
                sb.append(", creValDate='").append(creValDate).append('\'');
                sb.append(", creValCV2='").append(creValCV2).append('\'');
                sb.append('}');
                return sb.toString();
            }
        }

    }
}

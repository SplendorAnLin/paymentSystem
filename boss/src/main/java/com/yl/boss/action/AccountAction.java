package com.yl.boss.action;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.account.api.bean.AccountBean;
import com.yl.account.api.bean.AccountChangeDetailBean;
import com.yl.account.api.bean.AccountFreezeBean;
import com.yl.account.api.bean.AccountRecordedDetailBean;
import com.yl.account.api.bean.request.AccountModify;
import com.yl.account.api.bean.response.AccountHistoryQueryResponse;
import com.yl.account.api.bean.response.AccountModifyResponse;
import com.yl.account.api.dubbo.AccountQueryInterface;
import com.yl.boss.Constant;
import com.yl.boss.bean.AccountHistoryQueryBean;
import com.yl.boss.bean.AccountQueryBean;
import com.yl.boss.entity.Authorization;
import com.yl.boss.service.AccountService;
import com.yl.boss.utils.AddDate;
import com.yl.boss.utils.DateUtils;
import com.yl.boss.utils.ToolUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 账户信息
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年8月24日
 */
public class AccountAction extends Struts2ActionSupport {
    private static final long serialVersionUID = 5633409001921833258L;
    private AccountQueryInterface accountQueryInterface;
    private Page page;
    private AccountService accountService;
    private AccountQueryBean accountQueryBean;
    private List<AccountBean> accounts;
    private AccountBean account;
    private String accountNo;
    private List<AccountRecordedDetailBean> accountRecordedDetails;
    private List<AccountFreezeBean> accountFreezeDetailResponse;
    private AccountHistoryQueryBean accountHistoryQueryBean;
    private AccountFreezeBean accountFZTWQueryBean;
    private List<AccountChangeDetailBean> accountChangeDetails;
    private AccountModify accountModify;
    private String msg;
    private String adjustReason;
    private InputStream inputStream;
    private String freezeNo;
    private List<Map<String, Object>> findResult;

    private static Properties prop = new Properties();

    static {
        try {
            prop.load(new InputStreamReader(CustomerAction.class.getClassLoader().getResourceAsStream("system.properties")));
        } catch (IOException e) {
            logger.error("AccountAction load Properties error:", e);
        }
    }

    /**
     * 导出
     *
     * @throws IOException
     */
    public String accExport() throws IOException {
        if (accountHistoryQueryBean == null) {
            accountHistoryQueryBean = new AccountHistoryQueryBean();
        }
        if (accountHistoryQueryBean.getTransStartTime() != null) {
            accountHistoryQueryBean.setTransStartTime(DateUtils.getMinTime(accountHistoryQueryBean.getTransStartTime()));
        }
        if (accountHistoryQueryBean.getTransEndTime() != null) {
            accountHistoryQueryBean.setTransEndTime(DateUtils.getMaxTime(accountHistoryQueryBean.getTransEndTime()));
        }
        Map<String, Object> queryParams = ToolUtils.ObjectToMap(accountHistoryQueryBean);
        AccountHistoryQueryResponse accountHistoryQueryResponse = accountQueryInterface.findAccountHistoryExportBy(queryParams);
        accountRecordedDetails = accountHistoryQueryResponse.getAccountRecordedDetailBeans();

        if (accountRecordedDetails != null && accountRecordedDetails.size() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("账户历史");
            XSSFRow row = sheet.createRow(0);
            row.createCell(0).setCellValue("用户号");
            row.createCell(1).setCellValue("角色");
            row.createCell(2).setCellValue("业务类型");
            row.createCell(3).setCellValue("账户编号");
            row.createCell(4).setCellValue("系统编码");
            row.createCell(5).setCellValue("交易系统流水号");
            row.createCell(6).setCellValue("资金标识");
            row.createCell(7).setCellValue("原金额");
            row.createCell(8).setCellValue("交易金额");
            row.createCell(9).setCellValue("余额");
            row.createCell(10).setCellValue("创建时间");
            row.createCell(11).setCellValue("交易日期");
            row.createCell(10).setCellValue("待入账日期");
            int size = accountRecordedDetails.size();
            AccountRecordedDetailBean accountRecordedDetailBean = new AccountRecordedDetailBean();
            for (int i = 0; i < size; i++) {
                accountRecordedDetailBean = new AccountRecordedDetailBean();
                accountRecordedDetailBean = accountRecordedDetails.get(i);
                row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(accountRecordedDetailBean.getUserNo());

                try {
                    switch (accountRecordedDetailBean.getUserRole().name()) {
                        case "CUSTOMER":
                            row.createCell(1).setCellValue("商户");
                            break;

                        case "AGENT":
                            row.createCell(1).setCellValue("服务商");
                            break;

                        default:
                            row.createCell(1).setCellValue("会员");
                            break;
                    }
                } catch (Exception e) {
                    row.createCell(1).setCellValue("");
                }

                try {
                    switch (accountRecordedDetailBean.getBussinessCode()) {
                        case "DPAY_DEBIT":
                            row.createCell(2).setCellValue("代付出款");
                            break;

                        case "DPAY_DEBIT_FEE":
                            row.createCell(2).setCellValue("代付手续费出款");
                            break;

                        case "DPAY_CREDIT":
                            row.createCell(2).setCellValue("代付退款");
                            break;

                        case "DPAY_CREDIT_FEE":
                            row.createCell(2).setCellValue("代付手续费退款");
                            break;

                        case "ONLINE_CREDIT":
                            row.createCell(2).setCellValue("支付入账");
                            break;

                        case "ONLINE_CREDIT_FEE":
                            row.createCell(2).setCellValue("支付手续费扣款");
                            break;

                        case "SHATE_CREDIT":
                            row.createCell(2).setCellValue("分润入账");
                            break;

                        case "ADJUST":
                            row.createCell(2).setCellValue("系统调账");
                            break;

                        case "DIRECT_SHATE_CREDIT":
                            row.createCell(2).setCellValue("直属服务商分润");
                            break;

                        default:
                            row.createCell(2).setCellValue("间属服务商分润");
                            break;
                    }
                } catch (Exception e) {
                    row.createCell(2).setCellValue("");
                }

                row.createCell(3).setCellValue(accountRecordedDetailBean.getAccountNo());

                try {
                    switch (accountRecordedDetailBean.getSystemCode()) {
                        case "DPAY":
                            row.createCell(4).setCellValue("代付");
                            break;

                        case "BOSS":
                            row.createCell(4).setCellValue("运营");
                            break;
                        default:
                            row.createCell(4).setCellValue("在线支付");
                            break;
                    }
                } catch (Exception e) {
                    row.createCell(4).setCellValue("");
                }

                row.createCell(5).setCellValue(accountRecordedDetailBean.getTransFlow());

                try {
                    switch (accountRecordedDetailBean.getSymbol().name()) {
                        case "SUBTRACT":
                            row.createCell(6).setCellValue("减");
                            break;

                        default:
                            row.createCell(6).setCellValue("加");
                            break;
                    }
                } catch (Exception e) {
                    row.createCell(6).setCellValue("");
                }

                if (accountRecordedDetailBean.getSymbol().name().equals("PLUS")) {
                    row.createCell(7).setCellValue(accountRecordedDetailBean.getRemainBalance() - accountRecordedDetailBean.getTransAmount());
                } else {
                    row.createCell(7).setCellValue(accountRecordedDetailBean.getRemainBalance() + accountRecordedDetailBean.getTransAmount());
                }
                if (accountRecordedDetailBean.getSymbol().name().equals("PLUS")) {
                    row.createCell(8).setCellValue("+" + accountRecordedDetailBean.getTransAmount());
                } else {
                    row.createCell(8).setCellValue("-" + accountRecordedDetailBean.getTransAmount());
                }
                row.createCell(9).setCellValue(accountRecordedDetailBean.getRemainBalance());
                row.createCell(10).setCellValue(sdf.format(accountRecordedDetailBean.getCreateTime()));
                row.createCell(11).setCellValue(sdf.format(accountRecordedDetailBean.getTransTime()));
                if (accountRecordedDetailBean.getWaitAccountDate() == null) {
                    row.createCell(10).setCellValue("");
                } else {
                    row.createCell(10).setCellValue(sdf.format(accountRecordedDetailBean.getWaitAccountDate()));
                }
            }
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            wb.write(os);
            byte[] fileContent = os.toByteArray();
            ByteArrayInputStream is = new ByteArrayInputStream(fileContent);
            inputStream = is;
            msg = "_accountHistoryExecl.xlsx";
        }
        return SUCCESS;
    }

    /**
     * 查询账户历史
     *
     * @return
     */
    public String queryAccountHistory() {
        if (page == null) {
            page = new Page<>();
        }
        HttpServletRequest request = this.getHttpRequest();
        int currentPage = request.getParameter("currentPage") == null ? 1 : Integer.parseInt(request.getParameter("currentPage"));
        if (currentPage > 1) {
            page.setCurrentPage(currentPage);
        }
        if (accountHistoryQueryBean.getTransStartTime() != null) {
            accountHistoryQueryBean.setTransStartTime(DateUtils.getMinTime(accountHistoryQueryBean.getTransStartTime()));
        }
        if (accountHistoryQueryBean.getTransEndTime() != null) {
            accountHistoryQueryBean.setTransEndTime(DateUtils.getMaxTime(accountHistoryQueryBean.getTransEndTime()));
        }
        if (accountHistoryQueryBean.getCreateStartTime() != null) {
            accountHistoryQueryBean.setCreateStartTime(DateUtils.getMinTime(accountHistoryQueryBean.getCreateStartTime()));
        }
        if (accountHistoryQueryBean.getCreateEndTime() != null) {
            accountHistoryQueryBean.setCreateEndTime(DateUtils.getMaxTime(accountHistoryQueryBean.getCreateEndTime()));
        }
        page = accountService.queryAccountHistory(accountHistoryQueryBean, page);
        accountRecordedDetails = (List<AccountRecordedDetailBean>) page.getObject();
        return "queryAccountHistory";
    }
    
    /**
     * 账户历史合计
     * @return
     */
    public String queryAccountHistorySum(){
    	HttpServletRequest request = this.getHttpRequest();
        if (accountHistoryQueryBean.getTransStartTime() != null) {
            accountHistoryQueryBean.setTransStartTime(DateUtils.getMinTime(accountHistoryQueryBean.getTransStartTime()));
        }
        if (accountHistoryQueryBean.getTransEndTime() != null) {
            accountHistoryQueryBean.setTransEndTime(DateUtils.getMaxTime(accountHistoryQueryBean.getTransEndTime()));
        }
        if (accountHistoryQueryBean.getCreateStartTime() != null) {
            accountHistoryQueryBean.setCreateStartTime(DateUtils.getMinTime(accountHistoryQueryBean.getCreateStartTime()));
        }
        if (accountHistoryQueryBean.getCreateEndTime() != null) {
            accountHistoryQueryBean.setCreateEndTime(DateUtils.getMaxTime(accountHistoryQueryBean.getCreateEndTime()));
        }
        Map<String, Object> respData = accountService.queryAccountHistorySum(accountHistoryQueryBean);
        msg = JsonUtils.toJsonString(respData);
    	return SUCCESS;
    }

    /**
     * 查询账户信息
     *
     * @return
     */
    public String queryAccount() {
        if (page == null) {
            page = new Page<>();
        }
        HttpServletRequest request = this.getHttpRequest();
        int currentPage = request.getParameter("currentPage") == null ? 1 : Integer.parseInt(request.getParameter("currentPage"));
        if (currentPage > 1) {
            page.setCurrentPage(currentPage);
        }
        accountQueryBean.setOpenEndDate(AddDate.addOneDay(accountQueryBean.getOpenEndDate()));
        page = accountService.queryAccount(page, accountQueryBean);
        accounts = (List<AccountBean>) page.getObject();
        if ("1".equals(request.getParameter("accountAdjustInfo"))) {//调账目录的查询
            return "queryAccountAdjust";
        }
        if ("2".equals(request.getParameter("accountAdjustInfo"))) {//冻结的查询
            return "queryAccountFrozen";
        }
        return "queryAccount";
    }

    public String queryAccountDetail() {
        account = accountService.queryAccountDetail(accountNo);
        return "queryAccountDetail";
    }

    /**
     * 根据ID查询调账历史
     */
    @SuppressWarnings("unchecked")
    public String accountHistoryById() {
        if (page == null) {
            page = new Page<>();
        }
        HttpServletRequest request = this.getHttpRequest();
        int currentPage = request.getParameter("currentPage") == null ? 1 : Integer.parseInt(request.getParameter("currentPage"));
        if (currentPage > 1) {
            page.setCurrentPage(currentPage);
        }
        accountNo = request.getParameter("accountNo");
        Map info = accountQueryInterface.findAllAdjHistory(accountNo, page);
        page = (Page) info.get("page");
        findResult = (List<Map<String, Object>>) info.get("findResult");
        return "queryAccountHistory";
    }

    /**
     * 冻结历史查询
     */
    public String accountFZHistoryById() {
        if (page == null) {
            page = new Page<>();
        }
        HttpServletRequest request = this.getHttpRequest();
        int currentPage = request.getParameter("currentPage") == null ? 1 : Integer.parseInt(request.getParameter("currentPage"));
        if (currentPage > 1) {
            page.setCurrentPage(currentPage);
        }
        accountNo = request.getParameter("accountNo");
        if (accountNo != null) {
            accountFZTWQueryBean = new AccountFreezeBean();
            accountFZTWQueryBean.setAccountNo(accountNo);
        }
        page = accountService.queryAccountFz(accountFZTWQueryBean, page);
        accountFreezeDetailResponse = (List<AccountFreezeBean>) page.getObject();
        return "queryAccountFZHistory";
    }

    /**
     * 查询操作历史
     *
     * @return
     */
    public String findAccountChangeRecords() {
        if (page == null) {
            page = new Page<>();
        }
        HttpServletRequest request = this.getHttpRequest();
        int currentPage = request.getParameter("currentPage") == null ? 1 : Integer.parseInt(request.getParameter("currentPage"));
        if (currentPage > 1) {
            page.setCurrentPage(currentPage);
        }
        page = accountService.queryAccountChangeRecordsBy(accountQueryBean, page);
        accountChangeDetails = (List<AccountChangeDetailBean>) page.getObject();
        return "findAccountChangeRecords";
    }

    /**
     * 调账
     *
     * @return
     */
    public String adjustmentAccount() {
        try {
            Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
            HttpServletRequest request = this.getHttpRequest();
            String symbol = request.getParameter("symbol");
            String amount = request.getParameter("amount");
            accountService.adjustmentAccount(accountNo, symbol, amount, adjustReason, auth.getRealname());
            msg = "true";
        } catch (Exception e) {
            msg = "false";
        }
        return "adjustmentAccount";
    }

    /**
     * 账户资金冻结
     *
     * @return
     */
    public String accountFrozen() {
        try {
            Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
            HttpServletRequest request = this.getHttpRequest();
            String freezeLimit = request.getParameter("freezeLimit");
            String amount = request.getParameter("amount");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(freezeLimit);
            accountService.accountFrozen(accountNo, date, amount, adjustReason, auth.getRealname());
            msg = "true";
        } catch (Exception e) {
            msg = "false";
        }
        return "accountFrozen";
    }

    /**
     * 根据冻结编号查询账户编号
     */
    public String queryFreezeAccount() {
        msg = accountService.queryFreezeAccount(freezeNo);
        return SUCCESS;
    }


    /**
     * 账户资金解冻
     *
     * @return
     */

    public String accountJCFrozen() {
        try {
            Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
            HttpServletRequest request = this.getHttpRequest();
            String freezeNo = request.getParameter("freezeNo");
            accountService.thaw(accountNo, freezeNo, adjustReason, auth.getRealname());
            msg = "true";
        } catch (Exception e) {
            msg = "false";
        }
        return "queryAccountFZHistory";
    }

    // HttpServletRequest request, HttpServletResponse response, @Param("code")
    // String code, @Param("status") String status,
    // @Param("reason") String reason, @Param("userNo") String userNo
    public String updateAcountStatus() {
        Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
        try {
            accountModify.setRemark(java.net.URLDecoder.decode(accountModify.getRemark(), "UTF-8"));
            // accountModify.setAccountNo(code);
            // accountModify.setUserNo(userNo);
            accountModify.setBussinessCode("MODIFY_ACCOUNT");
            accountModify.setOperator(auth.getRealname());
            accountModify.setSystemCode("BOSS");
            accountModify.setSystemFlowId(UUID.randomUUID().toString().substring(16));
            accountModify.setRequestTime(new Date());
            // accountModify.setRemark(reason);
            // accountModify.setAccountStatus(AccountStatus.valueOf(status));
            AccountModifyResponse accountModifyResponse = accountService.modifyAccount(accountModify);
            if ("SUCCESS".equals(accountModifyResponse.getResult().toString())) {
                msg = "更改成功！";
            } else {
                msg = "更改失败!";
            }

        } catch (Exception e) {
            logger.error("", e);
        }
        return SUCCESS;
    }

    public String showAccountHistoryDetail() {

        return "showAccountHistoryDetail";
    }

    /**
     * 查询冻结编号是否存在
     *
     * @return
     */
    public String queryFreezeNoExists() {
        msg = String.valueOf(accountService.queryFreezeNoExists(getHttpRequest().getParameter("freezeNo")));
        return SUCCESS;
    }

    /* Get And Set */
    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public AccountQueryBean getAccountQueryBean() {
        return accountQueryBean;
    }

    public void setAccountQueryBean(AccountQueryBean accountQueryBean) {
        this.accountQueryBean = accountQueryBean;
    }

    public List<AccountBean> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountBean> accounts) {
        this.accounts = accounts;
    }

    public AccountBean getAccount() {
        return account;
    }

    public void setAccount(AccountBean account) {
        this.account = account;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public List<AccountRecordedDetailBean> getAccountRecordedDetails() {
        return accountRecordedDetails;
    }

    public void setAccountRecordedDetails(List<AccountRecordedDetailBean> accountRecordedDetails) {
        this.accountRecordedDetails = accountRecordedDetails;
    }

    public List<AccountChangeDetailBean> getAccountChangeDetails() {
        return accountChangeDetails;
    }

    public void setAccountChangeDetails(List<AccountChangeDetailBean> accountChangeDetails) {
        this.accountChangeDetails = accountChangeDetails;
    }


    public AccountHistoryQueryBean getAccountHistoryQueryBean() {
        return accountHistoryQueryBean;
    }

    public void setAccountHistoryQueryBean(AccountHistoryQueryBean accountHistoryQueryBean) {
        this.accountHistoryQueryBean = accountHistoryQueryBean;
    }

    public AccountModify getAccountModify() {
        return accountModify;
    }

    public void setAccountModify(AccountModify accountModify) {
        this.accountModify = accountModify;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAdjustReason() {
        return adjustReason;
    }

    public void setAdjustReason(String adjustReason) {
        this.adjustReason = adjustReason;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public AccountQueryInterface getAccountQueryInterface() {
        return accountQueryInterface;
    }

    public void setAccountQueryInterface(AccountQueryInterface accountQueryInterface) {
        this.accountQueryInterface = accountQueryInterface;
    }

    public AccountFreezeBean getAccountFZTWQueryBean() {
        return accountFZTWQueryBean;
    }

    public void setAccountFZTWQueryBean(AccountFreezeBean accountFZTWQueryBean) {
        this.accountFZTWQueryBean = accountFZTWQueryBean;
    }

    public List<AccountFreezeBean> getAccountFreezeDetailResponse() {
        return accountFreezeDetailResponse;
    }

    public void setAccountFreezeDetailResponse(List<AccountFreezeBean> accountFreezeDetailResponse) {
        this.accountFreezeDetailResponse = accountFreezeDetailResponse;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getFreezeNo() {
        return freezeNo;
    }

    public void setFreezeNo(String freezeNo) {
        this.freezeNo = freezeNo;
    }

    public List<Map<String, Object>> getFindResult() {
        return findResult;
    }

    public void setFindResult(List<Map<String, Object>> findResult) {
        this.findResult = findResult;
    }

}
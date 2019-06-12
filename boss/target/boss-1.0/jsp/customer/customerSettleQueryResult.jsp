<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>商户结算信息-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  
  <s:if test="#attr['customerSettle'].list.size()>0">
    <vlh:root value="customerSettle" url="?" includeParameters="*" configName="defaultlook">
    
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>商户编号</th>
                <th>账户名称</th>
                <th>账户编号</th>
                <th>商户类型</th>
                <th>银行编号</th>
                <th>开户行名称</th>
                <th>创建时间</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
                <vlh:row bean="customerSettle">
                  <s:set name="entity" value="#attr['customerSettle']" />
                  <vlh:column property="customer_no" />
                  <vlh:column property="account_name" />
                  <vlh:column property="account_no" />
                  <vlh:column><dict:write dictId="${entity.customer_type }" dictTypeId="ACCOUNT_TYPE"></dict:write></vlh:column>
                  <vlh:column property="bank_code"><dict:write dictId="${entity.bank_code }" dictTypeId="BANK_CODE"></dict:write></vlh:column>
                  <vlh:column property="open_bank_name" />
                  <vlh:column><s:date name="#entity.create_time" format="yyyy-MM-dd HH:mm:ss" /></vlh:column>
                  <vlh:column attributes="width='10%'">
                    <button data-myiframe='{
                      "target": "${pageContext.request.contextPath}/toUpdateCustomerSettle.action?customerSettle.customerNo=${entity.customer_no}",
                      "btns": [
                        {"lable": "取消"},
                        {"lable": "修改", "trigger": ".btn-submit"}
                      ]
                    }' class="btn-small">修改</button>
                    <button data-dialog="${pageContext.request.contextPath}/customerSettleHistByCustNo.action?customer_no=${entity.customer_no}" class="btn-small">历史信息</button>
                  </vlh:column>
                </vlh:row>
            </tbody>
          </table>
        </div>
      </div>
    
    
      <%@ include file="../include/vlhPage.jsp"%>
    </vlh:root>
  </s:if>
  <s:if test="#attr['customerSettle'].list.size()==0">
    无符合条件的查询结果
  </s:if>
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>

<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>商户报备-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">

  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>

  <s:if test="#attr['reportCustomerQuery'].list.size()>0">
    <vlh:root value="reportCustomerQuery" url="?" includeParameters="*" configName="defaultlook">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>商户编号</th>
                <th>通道ID</th>
                <th>通道类型</th>
                <th>APP_ID</th>
                <th>行业编码</th>
                <th>状态</th>
                <th>备注</th>
                <th>创建时间</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>

              <vlh:row bean="reportCustomerQuery">
                <s:set name="reportCustomer" value="#attr['reportCustomerQuery']" />
                <vlh:column property="customer_no" />
                <vlh:column property="interface_no" />
                <vlh:column property="interface_type" />
                <vlh:column property="app_id" />
                <vlh:column property="industry" />
                <vlh:column>
                  <dict:write dictId="${reportCustomer.status }" dictTypeId="ACCOUNT_STATUS_COLOR"></dict:write>
                </vlh:column>
                <vlh:column property="remark" />
                <vlh:column><s:date name="#reportCustomer.create_time" format="yyyy-MM-dd" /></vlh:column>
                <vlh:column attributes="width='10%'">
                  <button data-myiframe='{
                    "target": "toUpdateReportCustomer.action?id=${reportCustomer.id }",
                    "btns": [
                      {"lable": "取消"},
                      {"lable": "修改", "trigger": ".btn-submit"}
                    ]
                  }' class="btn-small">修改</button>
                </vlh:column>
              </vlh:row>
            </tbody>
          </table>
        </div>
      </div>
    
      <%@ include file="../include/vlhPage.jsp"%>
    </vlh:root>
  </s:if>
  <s:if test="#attr['reportCustomerQuery'].list.size()==0">
    无符合条件的查询结果
  </s:if>


  
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>

<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>商户审核-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  
  <s:if test="#attr['customerAuditQuery'].list.size()>0">
    <vlh:root value="customerAuditQuery" url="?" includeParameters="*" configName="defaultlook">
    
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>商户编号</th>
                <th>商户名称</th>
                <th>商户简称</th>
                <th>服务商</th>
                <th>商户地址</th>
                <th>状态</th>
                <th>商户类型</th>
                <th>开通时间</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
            
              <vlh:row bean="customerAuditQuery">
                <s:set name="customer" value="#attr['customerAuditQuery']" />
                <vlh:column property="customer_no" />
                <vlh:column>${customer.full_name}</vlh:column>
                <vlh:column>${customer.short_name}</vlh:column>
                <vlh:column property="agent_no" />
                <vlh:column>${customer.province }${customer.city }${customer.addr}</vlh:column>
                <vlh:column><dict:write dictId="${customer.status }" dictTypeId="CUSTOMER_STATUS_COLOR"></dict:write></vlh:column>
                <vlh:column>
                <dict:write dictId="${customer.customer_type }" dictTypeId="AGENT_TYPE"></dict:write></vlh:column>
                <vlh:column><s:date name="#customer.create_time" format="yyyy-MM-dd" /></vlh:column>
                <vlh:column attributes="width='10%'">
                  <c:if test="${customer.status eq 'AUDIT' }">
                    <button data-myiframe='{
                      "target": "coustomerToAudit.action?customer.customerNo=${customer.customer_no}",
                      "btns": [
                        {"lable": "取消"},
                        {"lable": "通过审核", "trigger": ".btn-submit-accept"},
                        {"lable": "拒绝审核", "trigger": ".btn-submit-reject"}
                      ]
                    }' class="btn-small">审核</button>
                  </c:if>
                  <c:if test="${customer.status eq 'AGENT_AUDIT' }">
                    <button onclick="reminders('${customer.agent_no}');" class="btn-small">催审</button>
                  </c:if>
                </vlh:column>
              </vlh:row>
              
            </tbody>
          </table>
        </div>
      </div>
  
      
      <%@ include file="../include/vlhPage.jsp"%>
    </vlh:root>
  </s:if>
  <s:if test="#attr['customerAuditQuery'].list.size()==0">
    无符合条件的查询结果
  </s:if>
  
  
  
  
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    function reminders(id) {
      AjaxConfirm('确认发送一条短信催促服务商审核吗?', '请确认', {
        url: '${pageContext.request.contextPath}/auditUrging.action?msg=' + id,
        type: 'post',
        error: function() {
          Messages.error('网络异常, 催审失败, 请稍后重试!');
        },
        success: function() {
          Messages.success('催审成功!');
        }
      }, false);
    }
  </script>
</body>
</html>

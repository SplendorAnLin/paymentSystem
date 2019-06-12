<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>交易卡-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  <s:if test="#attr['transQuery'].list.size()>0">
    <vlh:root value="transQuery" url="?" includeParameters="*" configName="defaultlook">
    
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp w-p-100 tow-color">
            <thead>
              <tr>
                <th>商户号</th>
				<th>编号</th>
				<th>结算卡编号</th>
				<th>银行卡姓名</th>
				<th>银行卡号</th>
				<th>手机号</th>
				<th>身份证号</th>
				<th>卡类型</th>
				<th>卡属性</th>
				<th>卡状态</th>
				<th>绑定时间</th>
				<th>解绑时间</th>
				<th>操作</th>
              </tr>
            </thead>
            <tbody>
              <vlh:row bean="transQuery"><s:set name="trans" value="#attr['transQuery']" />
              	<vlh:column property="customer_no" />
              	<vlh:column property="code"/>
              	<vlh:column property="settle_code"/>
              	<vlh:column property="account_name"/>
              	<vlh:column property="account_no"/>
              	<vlh:column property="phone"/>
              	<vlh:column property="id_number"/>
              	<vlh:column>
              		<dict:write dictId="${trans.card_type }" dictTypeId="TRANS_TYPE"></dict:write>
              	</vlh:column>
              	<vlh:column>
              		<dict:write dictId="${trans.card_attr }" dictTypeId="CARD_ATTR"></dict:write>
              	</vlh:column>
              	<vlh:column>
              		<dict:write dictId="${trans.card_status }" dictTypeId="TRANS_STATUS"></dict:write>
              	</vlh:column>
                <vlh:column><s:date name="#trans.tied_time" format="yyyy-MM-dd HH:mm:ss" /></vlh:column>
                <vlh:column><s:date name="#trans.unlock_time" format="yyyy-MM-dd HH:mm:ss" /></vlh:column>
                <vlh:column attributes="width='10%'">
                 <button data-myiframe='{
                  "target": "transCardHistoryQuery.action?transCard.customerNo=${trans.customer_no }&transCard.accountNo=${trans.account_no }",
                  "btnType": 0
                }' class="btn-small">历史记录</button>
                <c:if test="${trans.card_status == 'NORAML'}">
                	<button class="btn-small" onclick="changeStatus('${trans.code}', '禁用');">禁用</button>
                </c:if>
                <c:if test="${trans.card_status == 'DISABLED'}">
                	<button class="btn-small" onclick="changeStatus('${trans.code}', '启用');">启用</button>
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
  <s:if test="#attr['transQuery'].list.size()==0">
    	<p class="pd-10">无符合条件的查询结果</p>
  </s:if>
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
  	function changeStatus(code, msg) {
        AjaxConfirm('时候' + msg + '此交易卡?', '请确认', {
            url: '${pageContext.request.contextPath}/updateTransStatus.action?code=' + code,
            dataType: 'json',
            type: 'post',
            success: function(msg) {
            	if (msg == 'ERROR') {
            		Messages.error('更改交易卡状态失败!');
            	} else {
            		Messages.success('更改交易卡状态成功!');
            	}
              
            },
            error: function() {
              Messages.error('网络异常, 更改交易卡状态失败, 请稍后重试!');
            }
          }, true);
  	}
  
  
  </script>
</body>
</html>
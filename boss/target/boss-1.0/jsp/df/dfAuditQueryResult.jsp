<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>付款审核-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  
  <s:if test="#attr['dpayAuditRequest'].list.size()>0">
    <vlh:root value="dpayAuditRequest" url="?" includeParameters="*" configName="defaultlook">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th><input type="checkbox" class="checkbox"></th>
                <th>商户编号</th>
                <th>订单号</th>
                <th>商户订单号</th>
                <th>收款人姓名</th>
                <th>收款账号</th>
                <th>证件号</th>
                <th>金额</th>
                <th>手续费</th>
                <th>开户行</th>
                <th>账户类型</th>
                <td>打款原因</td>
                <td>审核原因</td>
                <td>创建时间</td>
                <td>审核时间</td>
              </tr>
            </thead>
            <tbody>
              <vlh:row bean="dpayAuditRequest">
                <s:set name="dpayAuditRequest" value="#attr['dpayAuditRequest']" />
                <vlh:column attributes="width='45px'" >
                  <s:if test="${dpayAuditRequest.audit_status=='REMIT_WAIT' }">
                    <input type="checkbox" class="checkbox" name="select-order" value="${dpayAuditRequest.flow_no }" applyamount="<fmt:formatNumber value="${dpayAuditRequest.amount + dpayAuditRequest.fee}" pattern="#0.00#" />">
                  </s:if>
                </vlh:column>
                <vlh:column property="owner_id" attributes="width='80px'" />
                <vlh:column property="flow_no"  attributes="width='220px'" />
                <vlh:column property="request_no"   attributes="width='180px'" />
                <vlh:column property="account_name" attributes="width='80px'" />
                <vlh:column property="account_no" attributes="width='180px' class='accoutNoStar'" />
                <vlh:column property="cert_no" attributes="width='180px'"/>
                <vlh:column property="amount" attributes="width='80px'" >
                <fmt:formatNumber value="${dpayAuditRequest.amount }" pattern="#0.00#" /></vlh:column>
                <vlh:column property="fee" attributes="width='40px'" >
                <fmt:formatNumber value="${dpayAuditRequest.fee }" pattern="#0.00#" /></vlh:column>
                <vlh:column property="bank_name" attributes="width='280px'" />
                <vlh:column attributes="width='80px'">
                  <s:if test="${dpayAuditRequest.account_type=='INDIVIDUAL' }">对私</s:if>
                  <s:if test="${dpayAuditRequest.account_type=='OPEN' }">对公</s:if>
                </vlh:column>
                <vlh:column attributes="width='200px'" property="description"/>
                <vlh:column attributes="width='200px'" property="remit_aduti_reason"/>
                <vlh:column property="create_date" attributes="width='180px'" format="yyyy-MM-dd HH:mm:ss"/>
                <vlh:column property="audit_date" attributes="width='180px'" format="yyyy-MM-dd HH:mm:ss"/>
              </vlh:row>
            </tbody>
          </table>
        </div>
      </div>
      <%@ include file="../include/vlhPage.jsp"%>
      <div class="text-center mt-10">
        <a class="btn" onclick="dfAudit.accept(this)" href="javascript:void(0);">通过</a>
        <a class="btn"onclick="dfAudit.reject(this)" href="javascript:void(0);">拒绝</a>
      </div>
    </vlh:root>
  </s:if>
  <s:else>
    无符合条件的查询结果
  </s:else>
  
  <!-- 付款审核验证对话框-密码验证 width: 7rem;-->
  <div id="accept-password" style="width: 378px; padding: 0.2em;display:none;">
    <div class="text-left" style="padding: 2em 1.2em;">
      <form class="validator stop-formeNnter" prompt="DropdownMessage">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">审核笔数:</span>
              <div class="input-wrap">
                <span class="text-secondary"><span class="mr-5 count">1</span>笔</span>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">审核金额:</span>
              <div class="input-wrap">
                <span class="text-secondary"><span class="mr-5 amount">505.80</span>元</span>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">复核密码:</span>
              <div class="input-wrap">
                <input type="password" name="password" required rangelength="[6,16]" checkAuditPassword trigger='{"checkAuditPassword": 0}'>
              </div>
            </div>
          </div>
        </div>
      </form>
      <div class="tips bg-gray-middle text-secondary" style="padding: 1em;">
        如需更高安全级别，可在【代付密码】中开启手机验证码复核功能。
      </div>
    </div>
  </div>

  <!-- 付款审核验证对话框-短信验证 width: 7rem; -->
  <div id="accept-password-sms" style="width: 400px; padding: 0.2em;display:none;">
    <div class="text-left" style="padding: 2em 1.2em;">
      <form class="validator stop-formeNnter" prompt="DropdownMessage">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">审核笔数:</span>
              <div class="input-wrap">
                <span class="text-secondary"><span class="mr-5 count">1</span>笔</span>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">审核金额:</span>
              <div class="input-wrap">
                <span class="text-secondary"><span class="mr-5 amount">505.80</span>元</span>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">手机验证码:</span>
              <div class="input-wrap mr-10">
                <input type="password" style="width: 120px;" name="verify-code" required rangelength="[6,6]" placeholder="验证码6位数" checkVerifyCode trigger='{"checkVerifyCode": 0}'>
              </div>
              <a id="sendVerifyCode" href="javascript:void(0);">获取验证码</a>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">复核密码:</span>
              <div class="input-wrap">
                <input type="password" name="password" required rangelength="[6,16]" checkAuditPassword trigger='{"checkAuditPassword": 0}'>
              </div>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>

  <!-- 审核拒绝对话框 -->
  <div id="reject-password" style="padding: 0.2em;display:none;">
    <div class="text-left">
      <form class="stop-formeNnter" prompt="DropdownMessage">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span style="text-align: left;" class="ib mr-10">审核笔数:</span>
              <div class="input-wrap">
                <span class="text-secondary"><span class="mr-5 count">1</span>笔</span>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span style="text-align: left;" class="ib mr-10">审核金额:</span>
              <div class="input-wrap">
                <span class="text-secondary"><span class="mr-5 amount">505.80</span>元</span>
              </div>
            </div>
          </div>
        </div>
      </form>
      <div class="tips mt-10">
        您确认复核拒绝代付订单吗？
      </div>
    </div>
  </div>

  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>

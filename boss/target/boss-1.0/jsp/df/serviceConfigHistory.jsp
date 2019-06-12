<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>修改历史</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 1200px;padding: 10px;">
  
  <div class="title-h1 fix noBottom">
    <span class="primary fl">用户代付配置修改历史</span>
  </div>
  
  <s:if test="#attr['historyQuery'].list.size()>0">
    <vlh:root value="historyQuery" url="?" includeParameters="*" configName="defaultlook">
    
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                  <th>用户</th>
                  <th>角色</th>
                  <th>状态</th>
                  <th>手机号</th>
                  <th>审核</th>
                  <th>运营审核</th>
                  <th>验证码审核状态</th>
                  <th>自动结算</th>
                  <th>操作员</th>
                  <th>最大金额</th>
                  <th>最小金额</th>
                  <th>单日最大金额</th>
                  <th>出款开始时间</th>
                  <th>出款结束时间</th>
                  <th>IP</th>
                  <th>域名</th>
                  <th>创建时间</th>
              </tr>
            </thead>
            <tbody>
              <vlh:row bean="historyQuery">
              <s:set name="entity" value="#attr['historyQuery']" />
                <vlh:column property="owner_id" attributes="width='5%'" />
                <vlh:column property="owner_role" attributes="width='7%'">
                  <c:if test="${entity.owner_role eq 'BOSS'}">运营</c:if>
                  <c:if test="${entity.owner_role eq 'CUSTOMER'}">商户</c:if>
                  <c:if test="${entity.owner_role eq 'AGENT'}">服务商</c:if>
                </vlh:column>
                <vlh:column property="valid" attributes="width='8%'">
                  <c:if test="${entity.valid eq 'TRUE'}"><span style="color: blue;">启用</span></c:if>
                  <c:if test="${entity.valid eq 'FALSE'}"><span style="color: red">停用</span></c:if>
                </vlh:column>
                <vlh:column property="phone" attributes="width='10%'" />
                <vlh:column property="manual_audit" attributes="width='8%'">
                  <c:if test="${entity.manual_audit eq 'TRUE'}"><span style="color: blue;">自动</span></c:if>
                  <c:if test="${entity.manual_audit eq 'FALSE'}"><span style="color: red">人工</span></c:if>
                </vlh:column>
                <vlh:column property="boss_audit" attributes="width='8%'">
                  <c:if test="${entity.boss_audit eq 'TRUE'}"><span style="color: blue;">自动</span></c:if>
                  <c:if test="${entity.boss_audit eq 'FALSE'}"><span style="color: red">人工</span></c:if>
                </vlh:column>
                <vlh:column property="use_phone_check" attributes="width='9%'">
                  <c:if test="${entity.use_phone_check eq 'TRUE'}"><span style="color: blue;">启用</span></c:if>
                  <c:if test="${entity.use_phone_check eq 'FALSE'}"><span style="color: red">停用</span></c:if>
                </vlh:column>
                <vlh:column property="fire_type" attributes="width='9%'">
                  <c:if test="${entity.fire_type eq 'AUTO'}"><span style="color: blue;">自动</span></c:if>
                  <c:if test="${entity.fire_type eq 'MAN'}"><span style="color: red">手动</span></c:if>
                </vlh:column>
                <vlh:column property="operator" attributes="width='8%'" />
                <vlh:column property="max_amount" attributes="width='8%'" />
                <vlh:column property="min_amount" attributes="width='8%'" />
                <vlh:column property="day_max_amount" attributes="width='8%'" />
                <vlh:column property="start_time" attributes="width='8%'"></vlh:column>
                <vlh:column property="end_time" attributes="width='8%'"></vlh:column>
                <vlh:column property="cust_ip" attributes="width='10%'" />
                <vlh:column property="domain" attributes="width='10%'" />
                <vlh:column property="create_time" attributes="width='10%'"><s:date name="#entity.create_time" format="yyyy-MM-dd HH:mm:ss" /></vlh:column>
              </vlh:row>
            </tbody>
          </table>
        </div>
      </div>

      <%@ include file="../include/vlhPage.jsp"%>
    </vlh:root>
  </s:if>
  <s:if test="#attr['historyQuery'].list.size()==0">
    <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;暂无修改历史
  </s:if>
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>

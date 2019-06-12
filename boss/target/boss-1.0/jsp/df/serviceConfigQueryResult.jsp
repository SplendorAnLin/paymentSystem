<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>用户代付配置-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  
  <s:if test="#attr['dpayConfigQuery'].list.size()>0">
    <vlh:root value="dpayConfigQuery" url="?" includeParameters="*" configName="defaultlook">
    
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>用户ID</th>
                <th>角色</th>
                <th>状态</th>
                <th>假日付</th>
                <th>手机号</th>
                <th>运营审核</th>
                <th>自动结算</th>
                <th>最大金额</th>
                <th>最小金额</th>
                <th>单日最大金额</th>
                <td>出款开始时间</td>
                <td>出款结束时间</td>
                <td>IP</td>
                <td>域名</td>
                <td>创建时间</td>
                <td>操作</td>
              </tr>
            </thead>
            <tbody>
              <vlh:row bean="dpayConfig">
                <s:set name="entity" value="#attr['dpayConfig']" />
                <vlh:column property="owner_id" attributes="width='10%'" />
                <vlh:column property="owner_role" attributes="width='7%'">
                  <c:if test="${entity.owner_role eq 'BOSS'}">运营</c:if>
                  <c:if test="${entity.owner_role eq 'CUSTOMER'}">商户</c:if>
                  <c:if test="${entity.owner_role eq 'AGENT'}">服务商</c:if>
                </vlh:column>
                <vlh:column property="valid" attributes="width='8%'">
                  <c:if test="${entity.valid eq 'TRUE'}"><span style="color: blue;">启用</span></c:if>
                  <c:if test="${entity.valid eq 'FALSE'}"><span style="color: red">停用</span></c:if>
                </vlh:column>
                <vlh:column property="holiday_status" attributes="width='8%'">
                  <c:if test="${entity.holiday_status eq 'TRUE'}"><span style="color: blue;">启用</span></c:if>
                  <c:if test="${entity.holiday_status eq 'FALSE'}"><span style="color: red">停用</span></c:if>
                  <c:if test="${entity.holiday_status ==null}"><span style="color: red">未开通</span></c:if>
                </vlh:column>
                <vlh:column property="phone" attributes="width='13%'" />
                <vlh:column property="boss_audit" attributes="width='8%'">
                  <c:if test="${entity.boss_audit eq 'TRUE'}"><span style="color: blue;">自动</span></c:if>
                  <c:if test="${entity.boss_audit eq 'FALSE'}"><span style="color: red">人工</span></c:if>
                </vlh:column>
                <vlh:column property="fire_type" attributes="width='9%'">
                  <c:if test="${entity.fire_type eq 'AUTO'}"><span style="color: blue;">自动</span></c:if>
                  <c:if test="${entity.fire_type eq 'MAN'}"><span style="color: red">手动</span></c:if>
                </vlh:column>
                <vlh:column property="max_amount" attributes="width='10%'">
                  <fmt:formatNumber value="${entity.max_amount }" pattern="#0.00#" />
                </vlh:column>
                <vlh:column property="min_amount" attributes="width='8%'" >
                  <fmt:formatNumber value="${entity.min_amount }" pattern="#0.00#" />
                </vlh:column>
                <vlh:column property="day_max_amount" attributes="width='8%'" >
                  <fmt:formatNumber value="${entity.day_max_amount }" pattern="#0.00#" />
                </vlh:column>
                <vlh:column property="start_time" attributes="width='8%'"></vlh:column>
                <vlh:column property="end_time" attributes="width='8%'"></vlh:column>
                <vlh:column property="cust_ip" attributes="width='15%'" />
                <vlh:column property="domain" attributes="width='15%'" />
                <vlh:column property="create_date" attributes="width='15%'">
                  <s:date name="#entity.create_date" format="yyyy-MM-dd HH:mm:ss" />
                </vlh:column>
                <vlh:column attributes="width='8%'">
                  <button data-myiframe='{
                    "target": "${pageContext.request.contextPath}/serviceConfigById.action?owner_id=${entity.owner_id}",
                    "btns": [
                      {"lable": "取消"},
                      {"lable": "修改", "trigger": ".btn-submit"}
                    ]
                  }' class="btn-small">修改</button>
                  <button data-dialog="${pageContext.request.contextPath}/goHistoryById.action?owner_id=${entity.owner_id}" class="btn-small">历史查询</button>
                  <c:if test="${entity.valid eq 'TRUE'}">
                    <button onclick="restPassWord('${entity.owner_id}')" class="btn-small">重置密码</button>
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
  <s:if test="#attr['dpayConfigQuery'].list.size()==0">
    <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;没有查找到用户信息
  </s:if>
    
    
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    function restPassWord(id) {
      AjaxConfirm('确认重置密码吗?', '请确认', {
        url: '${pageContext.request.contextPath}/resetDfPwd.action?id=' + id,
        dataType: 'json',
        type: 'post',
        success: function(msg) {
          Messages.success('重置密码成功!');
        },
        error: function() {
          Messages.error('网络异常, 重置密码失败, 请稍后重试!');
        }
      }, true);
    }
  </script>
</body>
</html>

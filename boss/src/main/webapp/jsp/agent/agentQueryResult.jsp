<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>服务商信息-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  <c:if test="${page.object != null && page.object.size() > 0 }">
    <div class="table-warp">
      <div class="table-sroll">
        <table class="data-table shadow--2dp  w-p-100 tow-color">
          <thead>
            <tr>
              <th>服务商编号</th>
              <th>服务商上级编号</th>
              <th>服务商等级</th>
              <th>服务商全称</th>
              <th>服务商简称</th>
              <th>服务商类型</th>
              <th>业务联系手机号</th>
              <th>服务商联系人</th>
              <th>服务商状态</th>
              <th>创建时间</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${page.object}" var="agent">
              <tr>
                <td>${agent.agent_no }</td>
                <td>${agent.paren_id }</td>
                <td><dict:write dictId="${agent.agent_level }" dictTypeId="SERVICE_PROVIDER_LEVEL"></dict:write></td>
                <td>${agent.full_name }</td>
                <td>${agent.short_name }</td>
                <td><dict:write dictId="${agent.agent_type }" dictTypeId="AGENT_TYPE"></dict:write></td>
                <td>${agent.phone_no }</td>
                <td>${agent.link_name }</td>
                <td><dict:write dictId="${agent.status }" dictTypeId="SERVICE_PROVIDER_STATUS_COLOR"></dict:write></td>
                <td><fmt:formatDate value="${agent.create_time }" pattern="yyyy-MM-dd" /></td>
                <td>
                  <button data-dialog="${pageContext.request.contextPath}/agentDetail.action?agent.agentNo=${agent.agent_no}" class="btn-small">详细</button>
                  <button data-myiframe='{
                    "target": "${pageContext.request.contextPath}/toUpdateAgentAction.action?agent.agentNo=${agent.agent_no}",
                    "btns": [
                      {"lable": "取消"},
                      {"lable": "修改", "trigger": ".btn-submit"}
                    ]
                  }' class="btn-small">修改</button>
                  <button onclick="restPassWord('${agent.agent_no}')" class="btn-small">重置密码</button>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
    <div class="jump-rarp mt-20 text-center" id="PageWrap" total_count="${page.totalResult}" total="${page.totalPage}"  current="${page.currentPage}"></div>
  </c:if>
  <c:if test="${page.object eq null or page.object.size() == 0 }">
    <p class="pd-10">无符合条件的查询结果</p>
  </c:if>
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    function restPassWord(agentNo) {
      AjaxConfirm('确认重置此服务商密码?', '请确认', {
        url: '${pageContext.request.contextPath}/resetAgentPasswordAction.action?agent.agentNo=' + agentNo,
        dataType: 'json',
        type: 'post',
        success: function(msg) {
          Messages.success('重置服务商密码成功!');
        },
        error: function() {
          Messages.error('网络异常, 重置服务商密码失败, 请稍后重试!');
        }
      }, true);
    }
  </script>
</body>
</html>

<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>冻结/解冻-结</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  <c:if test="${accounts != null && accounts.size() > 0}">
    <div class="table-warp">
      <div class="table-sroll">
        <table class="data-table shadow--2dp  w-p-100 tow-color">
          <thead>
            <tr>
            <th>账户编号</th>
            <th>账户类型</th>
            <th>账户状态</th>
            <th>商户编号</th>
            <th>商户等级</th>
            <th>币种</th>
            <th>总余额</th>
            <th>在途金额</th>
            <th>冻结余额</th>
            <th>创建时间</th>
            <th>操作</th>
            </tr>
          </thead>
        <tbody>
          <c:forEach items="${accounts }" var="acc">
            <tr>
              <td>${acc.code }</td>
              <td><dict:write dictId="${acc.type.name() }" dictTypeId="ACCOUNT_BASE_TYPE"></dict:write></td>
              <td><dict:write dictId="${acc.status.name() }" dictTypeId="ACCOUNT_STATUS_INFO_COLOR"></dict:write> </td>              
              <td>${acc.userNo }</td>
              <td><dict:write dictId="${acc.userRole.name() }" dictTypeId="USER"></dict:write></td>
              <td>${acc.currency }</td>
              <td><fmt:formatNumber value="${acc.balance }" pattern="#.##" /></td>
              <td><fmt:formatNumber value="${acc.transitBalance }"  pattern="#.##" /></td>
              <td><fmt:formatNumber value="${acc.freezeBalance }" pattern="#.##" /></td>
              <td><fmt:formatDate value="${acc.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
              <td>
                <c:if test="${acc.status.name() == 'FREEZE'}">
                  <button class="btn-small disabled">禁用</button>
                </c:if>
                <button data-myiframe='{
                  "target": "toAccountFrozenA.action?id=${acc.code }&&balance=${acc.balance }",
                  "btns": [
                    {"lable": "取消"},
                    {"lable": "冻结", "trigger": ".btn-submit"}
                  ]
                }' class="btn-small mr-5">冻结</button>
                <button data-myiframe='{
                  "target": "toAccountFrozenB.action?id=${acc.code }&&balance=${acc.balance }",
                  "btns": [
                    {"lable": "取消"},
                    {"lable": "解冻", "trigger": ".btn-submit"}
                  ]
                }' class="btn-small mr-5">解冻</button>
                <button data-dialog="accountFZHistoryAction.action?accountNo=${acc.code }" class="btn-small">操作历史</button>
              </td>
            </tr>
          </c:forEach>
        </tbody>
       </table>
      </div>
    </div>
    <div class="jump-rarp mt-20 text-center" id="PageWrap" total_count="${page.totalResult}" total="${page.totalPage}"  current="${page.currentPage}"></div>
  </c:if>
  <c:if test="${accounts eq null or accounts.size() == 0}">
      <p class="pd-10">无符合条件的记录</p>
  </c:if>
  
  <%@ include file="../../include/bodyLink.jsp"%>
</body>
</html>

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<html>
<head>
</head>
<script>
  // 阻止组件自动渲染
  window.stopRender = true;
</script>
<body>

		<div class="in-pop" style="width: 1200px; padding: 10px;">
    
      <!-- 错误信息列表 -->
			<s:if test="errorList.size()>0">
        <div class="row" id="showMatchedDiv" style="margin-bottom: 30px;">
          <div class="title-h1 fix tabSwitch2">
            <span class="primary fl">核对错误的信息（提示：为保证代付申请成功提交，请对错误的信息进行手动修改！）</span>
          </div>
          <div class="content">
            <div class="table-warp">
              <div class="table-sroll">
                <table class="data-table shadow--2dp  w-p-100 tow-color">
                  <thead>
                    <tr>
                      <th>错误行数</th>
                      <th>账户名称</th>
                      <th>银行账号</th>
                      <th>账户类型</th>
                      <th>开户银行</th>
                      <th>代付金额</th>
                      <th>错误原因</th>
                      <th>操作</th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:forEach items="${requestScope.errorList}" var="error" varStatus="idx">
                      <tr>
                        <td>${error[6]}</td>
                        <td>${error[0]}</td>
                        <td>${error[1]}</td>
                        <td>${error[2]}</td>
                        <td>${error[3]}</td>
                        <td>${error[4]}</td>
                        <td class="c_red">${error[7]}</td>
                        <td>
                          <a href="javascript:void(0)" class="btn-small edit_payeeInfo change_apply">修改</a>
                        </td>
                      </tr>
                    </c:forEach>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
			</s:if>
			
      <!-- 批量表单 -->
      <div class="row">
        <div class="title-h1 fix tabSwitch2">
          <span class="primary fl">核对正确的信息</span>
        </div>
        <div class="content">
          <form class="stop-formeNnter" id="dfBatchForm" method="post">
            <div class="table-warp">
              <div class="table-sroll">
                <table class="data-table shadow--2dp  w-p-100 tow-color">
                  <thead>
                    <tr>
                      <th>账户名称</th>
                      <th>银行账号</th>
                      <th>身份证号</th>
                      <th>账户类型</th>
                      <th>开户银行</th>
                      <th>代付金额</th>
                      <th>打款原因</th>
                      <th>操作</th>
                    </tr>
                  </thead>
                  <tbody class="successInfo">
                    <c:forEach items="${requestScope.requestBeanList}" var="pl" varStatus="idx">
                      <tr>
                        <td>${pl.accountName}<input type="hidden" name="requestBeanList[${idx.index }].accountName" class="accountName" value="${pl.accountName}"/></td>
                        <td>${pl.accountNo}<input type="hidden" name="requestBeanList[${idx.index }].accountNo" class="accountNo" value="${pl.accountNo}"/></td>
                        <td>${pl.cerNo}<input type="hidden" name="requestBeanList[${idx.index }].cerNo" class="cerNo" value="${pl.cerNo}"/></td>
                          <td>
                          ${pl.accountType }
                          <input type="hidden" name="requestBeanList[${idx.index }].accountType" class="accountType" value="<c:if test="${pl.accountType == '对私' }">INDIVIDUAL</c:if><c:if test="${pl.accountType == '对公' }">OPEN</c:if>" />
                        </td>
                        <td>
                          ${pl.bankName }
                          <input type="hidden" name="requestBeanList[${idx.index }].bankName" class="openBank" value="${pl.bankName }" />
                        </td>
                        <td>
                          ${pl.amount}<input type="hidden" name="requestBeanList[${idx.index }].amount" class="amount" value="${pl.amount}"/>
                        </td>
                        <td>
                          ${pl.description}<input type="hidden" name="requestBeanList[${idx.index }].description" class="description" value="${pl.description}"/>
                        </td>
                        <td>
                          <input type="hidden" name="requestBeanList[${idx.index }].bankCode" value="${pl.bankCode }" />
                          <input type="hidden" name="requestBeanList[${idx.index }].cardType"  value="${pl.cardType }" />
                          <input type="hidden" name="requestBeanList[${idx.index }].cerType" value="ID" />
                          <input type="hidden" name="requestBeanList[${idx.index }].requestType" value="PAGE" />
                          <a class="btn-small deletePayeeInfo" href="javascript:void 0;">删除</a>
                        </td>
                      </tr>
                    </c:forEach>
                  </tbody>
                </table>
              </div>
            </div>
           <div class="text-center pd-10">
              <input class="checkbox" type="checkbox" name="isBatchSaveInfo"  id="sabkBankFlag" value="true">
              <label>是否保存收款人信息</label>
           </div>
          </form>
        </div>
      </div>

		</div>
    <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
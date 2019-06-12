<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>账户信息-结果</title>
  <%@ include file="../include/header.jsp"%>
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
            <th>用户编号</th>
            <th>用户角色</th>
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
              <td><dict:write dictId="${acc.status.name() }" dictTypeId="ACCOUNT_STATUS_INFO_COLOR"></dict:write></td>
              <td>${acc.userNo }</td>
              <td><dict:write dictId="${acc.userRole.name() }" dictTypeId="USER"></dict:write></td>
              <td>${acc.currency }</td>
              <td><fmt:formatNumber value="${acc.balance }" pattern="#.##" /></td>
              <td><fmt:formatNumber value="${acc.transitBalance }" pattern="#.##" /></td>
              <td><fmt:formatNumber value="${acc.freezeBalance }" pattern="#.##" /></td>
              <td><fmt:formatDate value="${acc.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
              <td>
                <button data-dialog="findAccountChangeRecordsAction.action?accountQueryBean.accountNo=${acc.code }" class="btn-small">操作历史</button>
                <button onclick="modifyStatus('${acc.code}', '${acc.userNo }', '${acc.status.name()}')" class="btn-small">更改账户状态</button>
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
    无符合条件的记录
  </c:if>
  
  
    <!--更改用户状态对话框内容-->
    <div class="not-ready-render" id="accModifyStatus" style="display: none;min-width: 430px;padding: 10px 38px 10px 0;">
      <form class="validator">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">账户状态:</span>
              <div class="input-wrap">
                <select class="input-select status" name="accountQueryBean.status" required>
                  <option value="NORMAL">正常</option>
                  <option value="END_IN">止收</option>
                  <option value="EN_OUT">止支</option>
                  <option value="FREEZE">冻结</option>
                </select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90" style="vertical-align: top;">更改原因:</span>
              <div class="input-wrap">
                <textarea class="failRemark" style="width: 285px;height: 90px;" name="failRemark" required></textarea>
              </div>
            </div>
          </div>
        </div>
      </form>
    </div>

  
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    // 更改账户状态
    function modaifyAcc(accountNo, userStatus, remark, userNo, dialog) {
      Api.boss.accountModify(accountNo, userStatus, remark, userNo, function(successd, msg) {
        if (successd) {
          window.Messages.success(msg);
        } else {
          window.Messages.error('更改失败');
        }
        dialog.close(null, true);
        window.RefresQueryResult();
      });
    }

    // 更改账户状态弹窗
    var accModifyStatus = $('#accModifyStatus');
    function modifyStatus(accountNo, userNo, currentStatus) {
      var dialog = new window.top.MyDialog({
        "title": "更改账户状态",
        "target": accModifyStatus.clone(),
        "btns": [
          {
            "lable": "取消"
          },
          {
            "lable": "更改",
            "click": function(data, content) {
              var form = $('form', content);
              // 获取验证实例
              var validator = window.getValidator(form);
              dialog.loading(true);
              validator.form(function(status) {
                if (status.status == Compared.EnumStatus.fail) {
                  dialog.loading(false);
                  Messages.error('请填写更改原因!');
                } else if (status.status == Compared.EnumStatus.ok) {
                  // 获取状态
                  var userStatus = $('.status', content).val();
                  // 获取原因
                  var remark = $('.failRemark', content).val();
                  modaifyAcc(accountNo, userStatus, remark, userNo, dialog);
                }
              });
            }
          }
        ],
        "onOpen": function(dialog, content, win) {
          // 初始化当前状态
          $('.input-select', content[0]).selectForValue(currentStatus);
        }
      });
    };
  </script>
</body>
</html>

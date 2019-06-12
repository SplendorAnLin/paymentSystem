<%@ page language="java" pageEncoding="UTF-8" %>
<html>
<head>
  <title>修改银行通道对账文件</title>
  <%@ include file="../include/header.jsp" %>
</head>
<body style="width: 450px; padding:0.2em;">

<div class="query-box" style="padding: 0.8em 0.1em 0 4.5em;">
  <!--查询表单-->
  <div class="query-form">
    <form class="validator notification" action="reconFileInfoModify.action" method="post" prompt="DropdownMessage"
          data-success="修改成功" data-fail="修改失败">
      <input type="hidden" name="reconFileInfo.code" value="${reconFileInfo.code}"/>
      <input type="hidden" name="reconFileInfo.extendCode" value="${reconFileInfo.extendCode}"/>
      <div class="fix">
        <div class="item">
          <div class="input-area">
            <span class="label w-100">接口编号：</span>
            <div class="input-wrap">
              <input type="text" class="input-text" readonly
                     value="${reconFileInfo.reconFileInfoExtend.interfaceInfoCode}" required>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-100">接口名称：</span>
            <div class="input-wrap">
              <input type="text" class="input-text" readonly name="reconFileInfo.reconFileInfoExtend.bankName"
                     value="${reconFileInfo.reconFileInfoExtend.bankName}" required>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-100">是否开启：</span>
            <div class="input-wrap">
              <div class="input-wrap">
                <select class="input-select" name="reconFileInfo.valid" data-value="${reconFileInfo.valid}" required>
                  <option value="0">关闭</option>
                  <option value="1">开启</option>
                </select>
              </div>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-100">文件名：</span>
            <div class="input-wrap">
              <input type="text" class="input-text" name="reconFileInfo.fileName" value="${reconFileInfo.fileName}"
                     required>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-100">表头行：</span>
            <div class="input-wrap">
              <input type="text" class="input-text" title="${reconFileInfo.reconFileInfoExtend.header}"
                     name="reconFileInfo.reconFileInfoExtend.header" value="${reconFileInfo.reconFileInfoExtend.header}"
                     required>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-100">合计行：</span>
            <div class="input-wrap">
              <input type="text" class="input-text" name="reconFileInfo.reconFileInfoExtend.footer"
                     value="${reconFileInfo.reconFileInfoExtend.footer}">
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-100">分隔符：</span>
            <div class="input-wrap">
              <input type="text" class="input-text" name="reconFileInfo.reconFileInfoExtend.delimiter"
                     value="${reconFileInfo.reconFileInfoExtend.delimiter}" maxlength="1"
                     required>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-100">通道订单号列名：</span>
            <div class="input-wrap">
              <input type="text" class="input-text" name="reconFileInfo.reconFileInfoExtend.bankOrderCode"
                     value="${reconFileInfo.reconFileInfoExtend.bankOrderCode}" required>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-100">接口订单号列名：</span>
            <div class="input-wrap">
              <input type="text" class="input-text" name="reconFileInfo.reconFileInfoExtend.interfaceOrderCode"
                     value="${reconFileInfo.reconFileInfoExtend.interfaceOrderCode}"
                     required>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-100">订单金额列名：</span>
            <div class="input-wrap">
              <input type="text" class="input-text" name="reconFileInfo.reconFileInfoExtend.amount"
                     value="${reconFileInfo.reconFileInfoExtend.amount}" required>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-100">手续费列名：</span>
            <div class="input-wrap">
              <input type="text" class="input-text" name="reconFileInfo.reconFileInfoExtend.fee"
                     value="${reconFileInfo.reconFileInfoExtend.fee}" required>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-100">交易时间列名：</span>
            <div class="input-wrap">
              <input type="text" class="input-text" name="reconFileInfo.reconFileInfoExtend.transTime"
                     value="${reconFileInfo.reconFileInfoExtend.transTime}" required>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-100">备注：</span>
            <div class="input-wrap">
              <textarea cols="22" rows="4" name="reconFileInfo.remark">${reconFileInfo.remark}</textarea>
            </div>
          </div>
        </div>
      </div>
      <div class="text-center mt-10 hidden">
        <button class="btn btn-submit">修改</button>
      </div>
    </form>
  </div>
</div>

<%@ include file="../include/bodyLink.jsp" %>
</body>
</html>

<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>修改字典</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 380px; padding:0.2em;">
  
  <div class="query-box" style="padding: 0.8em 2.5em;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator notification" action="${pageContext.request.contextPath}/dictionaryRangedByKeyCrud.action" method="post" prompt="DropdownMessage"  data-success="修改成功" data-fail="修改失败">
        <input type="hidden" name="dictionaryType" value="updateDictionary">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">字典类型编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="dictionaryTypeRanged.code" value="${dictionaryTypeCode }" readonly>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">字典KEY:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="dictionaryRanged.key" value="${dictionaryRanged.key }"  readonly>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">字典VALUE:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="dictionaryRanged.value" value="${dictionaryRanged.value }"  required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">字典状态:</span>
              <div class="input-wrap">
                <dict:select styleClass="input-select" name="dictionaryRanged.status" value="${dictionaryRanged.status }" dictTypeId="STATUS"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">字典顺序:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="dictionaryRanged.order" value="${dictionaryRanged.order }" required digits>
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-10 hidden">
          <button class="btn btn-submit">新 增</button>
        </div>
      </form>
    </div>
  </div>
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    var form = $('form');
    // 挂接表单结果提示钩子
    form.data('receiver-hook', function(status, closeDialog, iframe) {
      window.lastWin.reloadPage();
      closeDialog(status);
    });
  </script>
</body>
</html>

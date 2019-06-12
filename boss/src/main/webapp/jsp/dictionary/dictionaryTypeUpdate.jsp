<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>修改字典类型</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 430px; padding:0.2em;">
  
  <div class="query-box" style="padding: 0.8em 0;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator notification" action="${pageContext.request.contextPath}/dictionaryTypeSaveOrUpdate.action" method="post" prompt="DropdownMessage"  data-success="修改成功" data-fail="修改失败">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">字典类型编码:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="dictionaryTypeRanged.code" value="${dictionaryTypeRanged.code }" readonly placeholder="只能输入英文/数字/下划线">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">字典类型名称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="dictionaryTypeRanged.name" value="${dictionaryTypeRanged.name }">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90" style="vertical-align: top;">备注信息:</span>
              <div class="input-wrap">
                <textarea class="failRemark" style="width: 285px;height: 90px;" name="dictionaryTypeRanged.remark" required>${dictionaryTypeRanged.remark }</textarea>
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
</body>
</html>

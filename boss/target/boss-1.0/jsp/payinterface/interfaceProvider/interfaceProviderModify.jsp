<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>提供方信息修改</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body style="width: 400px; padding:0.2em;">
  
  <div class="query-box" style="padding: 0.8em 4em;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator notification" action="updateInterfaceProvider.action" method="post" id="form" prompt="DropdownMessage"  data-success="修改成功" data-fail="修改失败">
        <div class="fix">

          <div class="item">
            <div class="input-area">
              <span class="label w-90">提供方编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="interfaceProvider.code" value="${interfaceProviderBean.code}" readonly>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">提供方全称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="interfaceProvider.fullName" value="${interfaceProviderBean.fullName}" required rangelength="[2, 10]">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">提供方简称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="interfaceProvider.shortName" value="${interfaceProviderBean.shortName}" required rangelength="[2, 10]">
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-10 hidden">
          <button class="btn btn-submit">修 改</button>
        </div>
      </form>
    </div>
  </div>

  
  <%@ include file="../../include/bodyLink.jsp"%>
</body>
</html>

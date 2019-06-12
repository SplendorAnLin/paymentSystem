<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>新增设备类型</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 400px; padding:0.2em;">
  
  <div class="query-box" style="padding: 0.8em 4em;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator ajaxFormNotification" action="upDeviceType.action" method="post" prompt="DropdownMessage"  data-success="修改成功" data-fail="修改失败">
        <input type="hidden" name="deviceTypeInfo.id" value="${deviceTypeInfo.id}"/>
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">父类设备:</span>
              <div class="input-wrap">
                <select class="input-select" name="deviceTypeInfo.parentId" data-value="${deviceTypeInfo.parentId}" required>
                  <c:forEach items="${ deviceTypeList}" var="device">
                    <option value="${device.id }">${device.deviceName }</option>
                  </c:forEach>
                </select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">类型名称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="deviceTypeInfo.deviceName" value="${deviceTypeInfo.deviceName}" required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">类型单价:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="deviceTypeInfo.unitPrice" value="${deviceTypeInfo.unitPrice}" required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">类型备注:</span>
              <div class="input-wrap">
                <input type="text" class="input-text fee" name="deviceTypeInfo.remark" value="${deviceTypeInfo.remark}" required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">状态:</span>
              <div class="input-wrap">
                <dict:select name="deviceTypeInfo.status" styleClass="input-select" value="${deviceTypeInfo.status }" dictTypeId="ACCOUNT_STATUS"></dict:select>
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

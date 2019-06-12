<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>修改费率</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 400px; padding:0.2em;">
  
  <div class="query-box" style="padding: 0.8em 4em;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator notification" action="updateReportCustomer.action" method="post" id="form" prompt="DropdownMessage"  data-success="修改成功" data-fail="修改失败">
        <input type="hidden" name="reportCustomer.id" value="${reportCustomer.id}">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">商户号:</span>
              <div class="input-wrap">
              	<input type="text" class="input-text" name="reportCustomer.customerNo"  value="${reportCustomer.customerNo}" >
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">通道方编号:</span>
              <div class="input-wrap">
               	<input type="text" class="input-text" name="reportCustomer.interfaceNo"  value="${reportCustomer.interfaceNo}">
              </div>
            </div>
          </div>

          <div class="item">
            <div class="input-area">
              <span class="label w-90">APPID:</span>
              <div class="input-wrap">
                    <input type="text" class="input-text" name="reportCustomer.appId"  value="${reportCustomer.appId}">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">通道类型:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="reportCustomer.interfaceType"  value="${reportCustomer.interfaceType}">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">行业编码:</span>
              <div class="input-wrap">
                <input type="text" class="input-text fee" name="reportCustomer.industry"  value="${reportCustomer.industry}">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">备注:</span>
              <div class="input-wrap">
                <input type="text" class="input-text minfee" name="reportCustomer.remark"  value="${reportCustomer.remark}">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">状态:</span>
              <div class="input-wrap switch-wrap">
                <dict:radio name="reportCustomer.status"  value="${reportCustomer.status}" dictTypeId="ALL_STATUS"></dict:radio>
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
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
  </script>
</body>
</html>

<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>对账单配置信息修改</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 400px; padding:0.2em;">
  
  <div class="query-box" style="padding: 1.8em 4em;">
    <!--查询表单-->
    <div class="query-form">

      <form class="validator notification" action="reconBillConfigUpdate.action" method="post" id="form" prompt="DropdownMessage"  data-success="修改成功" data-fail="修改失败">
        <input type="hidden" name="interfaceReconBillConfig.id" value="${interfaceReconBillConfig.id }">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">接口名称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" readonly value="${interfaceReconBillConfig.interfaceName }">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">接口前缀:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="interfaceReconBillConfig.filePrefix" required rangelength="[2, 20]" value="${interfaceReconBillConfig.filePrefix }">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">对账单路径:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="interfaceReconBillConfig.reconBillUrl" required rangelength="[2, 30]" value="${interfaceReconBillConfig.reconBillUrl }">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">生成账单时间:</span>
              <div class="input-wrap">
                <input type="text" class="input-text date-hms" name="interfaceReconBillConfig.generateTime" date-fmt="H:mm"  required  value="${interfaceReconBillConfig.generateTime }">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">配置状态:</span>
              <div class="input-wrap switch-wrap">
                <dict:radio value="${interfaceReconBillConfig.status }" name="interfaceReconBillConfig.status" dictTypeId="ALL_STATUS"></dict:radio>
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
</body>
</html>

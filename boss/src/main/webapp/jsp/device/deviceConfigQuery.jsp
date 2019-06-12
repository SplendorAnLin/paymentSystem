<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>设备配置</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body>

  <div class="query-box">

    <!--查询表单-->
    <div class="query-form mt-10">
      <form class="hidden" id="historyForm" action="deviceConfigHistory.action" target="query-result">
        <button class="query-btn">查询</button>
      </form>
    </div>

    <form id="updateForm" class="validator notification" action="deviceConfigUpdate.action" method="post"  data-success="配置成功" data-fail="配置失败">
      <input type="hidden" name="deviceConfig.id" value="${deviceConfig.id}">
      <div class="adaptive fix">
        <div class="item">
          <div class="input-area">
            <span class="label w-90">配置信息:</span>
            <div class="input-wrap">
              <input type="text" class="input-text" name="deviceConfig.configKey" value="${deviceConfig.configKey}" required>
            </div>
          </div>
        </div>
      </div>
      <div class="text-center mt-10">
        <button class="btn">修 改</button>
        <a href="javascript:void(0);" onclick="history();" class="btn query-btn">查 询</a>
        <input type="reset" class="btn clear-form" value="重 置">
      </div>
    </form>


    

    <!--查询结果框架-->
    <div class="query-result mt-20">
      <iframe name="query-result" frameborder="0"></iframe>
    </div>

  </div>
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    // 查询更改历史
    function history() {
      $('#historyForm').submit();
    }
  </script>
</body>
</html>

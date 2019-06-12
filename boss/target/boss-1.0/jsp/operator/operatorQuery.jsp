<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>用户查询</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body>
  
  <div class="query-box">
    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="operatorQuery.action" method="post" target="query-result">
        <div class="adaptive fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">登录名:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="userName">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">用户名:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="realName">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">所属系统:</span>
              <div class="input-wrap">
                <dict:select dictTypeId="SYS_TYPE" styleClass="input-select" name="sysType" id="sysType" onchange="switchSysType(this.value);"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90" style="width: 120px;">编号(商户/代理商):</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="no">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">状态:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" styleClass="input-select" dictTypeId="ACCOUNT_STATUS" name="status"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">创建时间:</span>
              <div class="input-wrap">
                <input type="text" name="create_time_start" class="input-text default-time double-input date-start ignoreEmpy">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="create_time_end" class="input-text default-time-end double-input date-end ignoreEmpy">
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-10">
          <button class="btn query-btn">查 询</button>
          <input type="reset" class="btn clear-form" value="重 置">
        </div>
      </form>
    </div>


    <!--查询结果框架-->
    <div class="query-result mt-20">
      <iframe name="query-result" frameborder="0"></iframe>
    </div>

  </div>

  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    // 编号输入框块
    var accNumberBlock = $('[name="no"]').parents('.item');
    // 切换所属系统
    function switchSysType(value) {
      if (value === 'BOSS') {
        accNumberBlock.hide();
      } else {
        accNumberBlock.show();
      }
    }
    $('[name="sysType"]').trigger('onchange');
  </script>
</body>
</html>

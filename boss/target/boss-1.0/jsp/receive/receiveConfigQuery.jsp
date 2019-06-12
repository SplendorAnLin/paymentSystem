<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>用户代收配置</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body>
  
  <div class="query-box">

    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="receiveConfigQuery.action" method="post" target="query-result">
        <div class="adaptive fix">

          <div class="item">
            <div class="input-area">
              <span class="label w-90">用户编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="owner_id">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">用户名称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="owner_name">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">状态:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" styleClass="input-select" name="status" dictTypeId="ACCOUNT_STATUS"></dict:select>
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-10">
          <button class="btn query-btn">查 询</button>
          <a data-myiframe='{
                "target": "toReceiveConfigAdd.action",
                "btns": [
                  {"lable": "取消"},
                  {"lable": "新增", "trigger": ".btn-submit"}
                ]
              }' class="btn" href="javascript:void(0);">新增代收配置</a>
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
</body>
</html>

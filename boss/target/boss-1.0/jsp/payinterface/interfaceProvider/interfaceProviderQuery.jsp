<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>接口提供方查询</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body>
  
  <div class="query-box">
    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="findAllInterfaceProvider.action" method="post" target="query-result">
        <div class="adaptive fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">提供方编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="code">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">提供方全称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="fullName">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">提供方简称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="shortName">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">创建时间:</span>
              <div class="input-wrap">
                <input type="text" name="createTimeStart" class="input-text double-input default-time date-start ignoreEmpy">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="createTimeEnd" class="input-text double-input default-time-end date-end ignoreEmpy">
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-10">
          <button class="btn query-btn">查 询</button>
          <a data-myiframe='{
                "target": "toAddInterfaceProvider.action",
                "btns": [
                  {"lable": "取消"},
                  {"lable": "新增", "trigger": ".btn-submit"}
                ]
              }' class="btn" href="javascript:void(0);">新增</a>
          <input type="reset" class="btn clear-form" value="重 置">
        </div>
      </form>
    </div>


    <!--查询结果框架-->
    <div class="query-result mt-20">
      <iframe name="query-result" frameborder="0"></iframe>
    </div>

  </div>

  
  <%@ include file="../../include/bodyLink.jsp"%>
</body>
</html>

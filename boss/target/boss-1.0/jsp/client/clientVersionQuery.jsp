<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>APP版本管理</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body>
  <input type="hidden" id="msg" value="${msg }">
  <div class="query-box">

    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="clientVersionQuery.action" method="post" target="query-result">
        <div class="adaptive fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">服务商编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="agent_no">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">app简称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="short_app">
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-10">
          <button class="btn query-btn mb-10">查 询</button>
          <a data-myiframe='{
                "target": "toAndroidOemUpdate.action",
                "btns": [
                  {"lable": "取消"},
                  {"lable": "新增", "trigger": ".btn-submit"}
                ]
              }' class="btn mb-10" href="javascript:void(0);">安卓添加OEM版</a>
          <a data-myiframe='{
                "target": "toIosOemUpdate.action",
                "btns": [
                  {"lable": "取消"},
                  {"lable": "新增", "trigger": ".btn-submit"}
                ]
              }' class="btn mb-10" href="javascript:void(0);">IOS添加OEM版</a>
          <a data-myiframe='{
                "target": "toIosStoreOemUpdate.action",
                "btns": [
                  {"lable": "取消"},
                  {"lable": "新增", "trigger": ".btn-submit"}
                ]
              }' class="btn mb-10" href="javascript:void(0);">IOS_Store添加OEM版</a>
          <input type="reset" class="btn clear-form mb-10" value="重 置">
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

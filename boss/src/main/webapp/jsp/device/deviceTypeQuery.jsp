<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>设备类型管理</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body>
  
  <div class="query-box">

    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="deviceTypeQuery.action" method="post" target="query-result">
        <div class="adaptive fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">设备类型:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="parent_name">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">设备型号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="device_name">
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-10">
          <button class="btn query-btn">查 询</button>
          <a data-myiframe='{
                "target": "toDeviceTypeAdd.action",
                "btns": [
                  {"lable": "取消"},
                  {"lable": "添加", "trigger": ".btn-submit"}
                ]
              }' class="btn" href="javascript:void(0);">新增设备类型</a>
          <a data-myiframe='{
                "target": "deviceTypeParentAdd.action",
                "btns": [
                  {"lable": "取消"},
                  {"lable": "添加", "trigger": ".btn-submit"}
                ]
              }' class="btn" href="javascript:void(0);">新增设备型号</a>
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

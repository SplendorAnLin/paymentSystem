<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>接口信息</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body>
  
  <div class="query-box">
    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="queryAllInterfaceInfoAction.action" method="post" target="query-result">
        <div class="adaptive fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">接口编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="interfaceInfoParam.code">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">接口名称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="interfaceInfoParam.name">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">接口状态:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" styleClass="input-select" dictTypeId="ACCOUNT_STATUS" name="interfaceInfoParam.status"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">接口类型:</span>
              <div class="input-wrap">
                <dict:select name="interfaceInfoParam.type" styleClass="input-select" nullOption="true" dictTypeId="BF_INTERFACE_TYPE_NAME"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">创建时间:</span>
              <div class="input-wrap">
                <input type="text" class="input-text default-time double-input date-start ignoreEmpy" name="interfaceInfoParam.createTimeStart">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" class="input-text default-time-end double-input date-end ignoreEmpy" name="interfaceInfoParam.createTimeEnd">
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-10">
          <button class="btn query-btn">查 询</button>
          <a data-myiframe='{
                "target": "toInterfaceInfo.action",
                "btns": [
                  {"lable": "取消"},
                  {"lable": "新增", "trigger": ".btn-submit"}
                ]
              }' class="btn" href="javascript:void(0);">新增接口</a>
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

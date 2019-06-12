<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>文档信息</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body>
  
  <div class="query-box">
    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="protocolManagements.action" method="post" target="query-result">
        <div class="adaptive fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">标题:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="title">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">文档类型:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" styleClass="input-select" diy="" name="type" onchange="changeDocType(this.value);" dictTypeId="PROTOCOL_MANAGEMENT"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">所属分类:</span>
              <div class="input-wrap">
                <!--隐藏下拉列表-->
                <dict:select nullOption="true" diy=" dataType=\"PACT\"" style="display:none;" dictTypeId="PROTOCOL_MANAGEMENT_PACT"></dict:select>
                <dict:select nullOption="true" diy=" dataType=\"HELP\""  style="display:none;" dictTypeId="PROTOCOL_MANAGEMENT_HELP"></dict:select>
                <!--实际下拉列表-->
                <select class="input-select" name="sort" id="target">
                  <option value="">全部</option>
                </select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">状态:</span>
              <div class="input-wrap">
                <dict:select styleClass="input-select" name="status" nullOption="true" dictTypeId="ACCOUNT_STATUS"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">  
              <span class="label w-90">创建日期:</span>
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
          <a data-myiframe='{
                "target": "toProtocolManagementAdd.action",
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
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    // 下拉列表联动
    var target = $('#target');
    function changeDocType(type) {
      if (type == '') {
        target.html('<option value="">全部</option>').renderSelectBox();
        return;
      }
      
      // 寻找对应类型隐藏select
      var dataSelect = $('[dataType="' + type + '"]');
      target.html(dataSelect.html()).renderSelectBox();
    }
  
  </script>
</body>
</html>

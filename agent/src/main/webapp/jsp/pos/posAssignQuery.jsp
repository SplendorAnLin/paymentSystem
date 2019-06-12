<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>POS分配信息</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body>
  
  <div class="query-box">

    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="${pageContext.request.contextPath}/posAssignOuter.action" method="post" target="query-result">
        <div class="adaptive fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">POS终端号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="pos_cati">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">POS机型号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="type">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">POS机具序列号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="pos_sn">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">批次号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="batch_no">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">状态:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" styleClass="input-select" name="status" dictTypeId="STATUS"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">运行状态:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" styleClass="input-select" name="run_status" dictTypeId="RUN_STATUS"></dict:select>
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
</body>
</html>

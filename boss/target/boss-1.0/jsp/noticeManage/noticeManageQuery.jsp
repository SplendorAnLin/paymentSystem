<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>客户端配置管理</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body>
  
  <div class="query-box">
    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="findAllNoticeConfigByInfo.action" method="post" target="query-result">
        <div class="adaptive fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">服务商编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="ownerId" />
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">名称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="name" />
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">状态:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" styleClass="input-select" dictTypeId="STATUS" name="status"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">类型:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="type" />
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">创建时间:</span>
              <div class="input-wrap">
                <input type="text" class="input-text default-time double-input date-start ignoreEmpy" name="createDateStart">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" class="input-text default-time-end double-input date-end ignoreEmpy" name="createDateEnd">
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-10">
          <button class="btn query-btn">查 询</button>
          <a data-myiframe='{
                "target": "${pageContext.request.contextPath}/jsp/noticeManage/noticeManageAdd.jsp",
                "btns": [
                  {"lable": "取消"},
                  {"lable": "新增", "trigger": ".btn-submit"}
                ]
              }' class="btn" href="javascript:void(0);">新增配置</a>
          <a data-myiframe='{
                "target": "${pageContext.request.contextPath}/jsp/noticeManage/sendAllNotice.jsp",
                "btns": [
                  {"lable": "取消"},
                  {"lable": "发送", "trigger": ".btn-submit"}
                ]
              }' class="btn" href="javascript:void(0);">群发消息</a>
          
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

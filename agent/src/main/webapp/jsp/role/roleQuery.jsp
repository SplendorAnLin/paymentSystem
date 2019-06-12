<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>角色管理</title>
<%@ include file="../include/header.jsp"%>
</head>
<body>
  <input type="hidden" id="msg" value="<%=request.getParameter("success") %>">
  <div class="query-box">
    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="roleQuery.action" method="post" target="query-result">
        <div class="adaptive fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">角色名:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="name">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">状态:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" name="status" styleClass="input-select" dictTypeId="STATUS"></dict:select>
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-10">
          <button class="btn query-btn">查 询</button>
          <a data-myiframe='{
                "target": "toRoleAdd.action",
                "btns": [
                  {"lable": "取消"},
                  {"lable": "新增", "trigger": ".btn-submit"}
                ]
              }' class="btn" href="javascript:void(0);">创建角色</a>
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
    if (<%=request.getParameter("success") %> == true) {
      Messages.success('操作成功!');
    } else if (<%=request.getParameter("success") %> == false) {
      Messages.error('操作失败!');
    }
  </script>
</body>
</html>
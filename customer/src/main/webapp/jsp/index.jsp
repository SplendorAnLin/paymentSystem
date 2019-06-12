<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-cmn">
<head>
  <%@ include file="include/header.jsp"%>
  <title>商户管理后台</title>
</head>
<body class="full-size over-hide">
  <!-- 页眉 -->
  <div class="sys-header theme-bg absolute">
    <div class="logo fl">
      <img class="toggle-drawerNav" src="./images/logo-head.png" alt="">
    </div>
    <div class="fr">
      <ul class="links fl">
        <li><a target="_blank" href="javascript:void(0);">聚合商户管理系统</a></li>
        <li><a target="_blank" href="javascript:void(0);">API文档</a></li>
        <li><a target="_blank" href="javascript:void(0);">帮助</a></li>
      </ul>
      <div class="control-panel fl">
        <div class="user-name f-13">
          <span>Hi, </span>
          <span>
            <c:forEach items="${sessionScope.auth.roles}" var="role">${role.name }</c:forEach>
            ：${sessionScope.auth.realname}
           </span>
          <i class="faArrow-down faArrow fa fa-angle-down"></i>
          <i class="faArrow-up faArrow fa fa-angle-up"></i>
        </div>
        <div class="menus theme-bg">
          <span class="arrow-up"></span>

            <c:choose>
                <c:when test="${sessionScope.auth.customerno eq 'C100048' or sessionScope.auth.customerno eq 'C100058' or sessionScope.auth.customerno eq 'C100059'}">

                </c:when>
                <c:otherwise>
                    <a class="theme-bg-hover" href="javascript:void(0);" data-title="账户信息" data-create="${pageContext.request.contextPath}/queryCustomerBasicInfo.action" >账户信息</a>
                </c:otherwise>
            </c:choose>


            <%--<c:if test="${!sessionScope.auth.customerno eq 'C100048'}">--%>
                <%--<a class="theme-bg-hover" href="javascript:void(0);" data-title="账户信息" data-create="${pageContext.request.contextPath}/queryCustomerBasicInfo.action" >账户信息</a>--%>
            <%--</c:if>--%>
          <a class="theme-bg-hover" href="javascript:void(0);" data-myiframe='{
            "target": "${pageContext.request.contextPath}/toOperatorPasswordUpdate.action",
            "btns": [
              {
                "lable": "取消"
              },
              {
                "lable": "确定",
                "trigger": ".btn-submit"
              }
            ],
            "title": "修改账户密码",
            "mask": true,
            "drag": true,
            "isModal": true
            }' >修改账户密码</a>
          <a class="theme-bg-hover" data-dialog="loginLogQuery.action" href="javascript:void(0);">登陆历史</a>
          <a class="theme-bg-hover" href="${pageContext.request.contextPath}/jsp/logout.jsp">退出</a>
        </div>
      </div>
    </div>
  </div>
  
  <!--主体-->
  <div class="sys-body outer h-p-100">

    <!--菜单栏-->
    <div class="menu-bar fl h-p-100">
      <div class="navigation" id="navigation">
        <ul id="menu-list">
          <li><a class="no-folder" href="${pageContext.request.contextPath}/welcomeQuery.action">首页</a></li>
          
          
          
          <s:iterator value="#session.auth.menu.children" status="menu">
              <li>
                <div class="folder"><s:property value="name" /></div>
                <ul>
                  <li><s:if test="isLeaf='N'">
                      <s:iterator
                        value="children"
                        status="childMenu">
                        <a href="${pageContext.request.contextPath}/<s:property value="url"/>"><s:property value="name" /></a>
                      </s:iterator>
                    </s:if></li>
                </ul>
              </li>
            </s:iterator>
   
        </ul>
      </div>
    </div>

    <!--内容区-->
    <div class="content-window h-p-100">

      <!--选项卡-->
      <div class="tabs-out">
        <span class="tabs-arrow left"><i class="fa fa-angle-double-left"></i></span>
        <div class="tabs-in">
          <div class="tabs-pill">
            <ul class="tabs fix" id="tabs-container">
            </ul>
          </div>
        </div>
         <span class="tabs-arrow right"><i class="fa fa-angle-double-right"></i></span>
      </div>


      <!--iframe窗口内容-->
      <div class="windows-out">
        <div class="windows-in">
          <div class="loading-img" id="loading-img">
            <div class="img"></div>
          </div>
          <div class="iframes" id="iframes">
          </div>
        </div>
      </div>


    </div>


  </div>



  <!--抽屉菜单栏-->
  <div class="drawer-wrap" id="drawer-nav">
    <div class="drawer-nav">
        <ul id="drawer-menu-list">
        </ul>
    </div>
  </div>


  <%@ include file="include/bodyLink.jsp"%>
  
  <script>
    windowManage.create('${pageContext.request.contextPath}/welcomeQuery.action', '首页');
  </script>
</body>
</html>
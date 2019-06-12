<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="./include/header.jsp"%>
<html>
<head>
</head>
<body>

  <%@ include file="./include/bodyLink.jsp"%>
  <script>
    $(function() {
     <%String type = request.getParameter("type");
        if ("A".equals(type)) {
          out.println("noPermit();");
        } else if ("B".equals(type)) {
          out.println("sessionExpired();");
        } else if ("C".equals(type)) {
          out.println("compelLogout();");
        }%>
    });
  
    function noPermit() {
      new window.top.Alert('无权限操作此功能！', '警告');
    }
  
    function sessionExpired() {
      new window.top.Alert('Session信息过期或未登录，请重新登录系统！', '超时响应', function() {
        window.top.location.href = "${pageContext.request.contextPath}/jsp/login.jsp";
      });
    }
  
    function compelLogout() {
      new window.top.Alert('用户名在其它客户端重新登录，您被强制登出！', '帐号重复登陆', function() {
        window.top.location.href = "${pageContext.request.contextPath}/jsp/login.jsp";
      });
    }
  </script>
</body>
</html>
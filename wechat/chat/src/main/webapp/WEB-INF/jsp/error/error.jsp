<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出错啦</title>
</head>
<body>
<div class="ui-widget">
    <div class="ui-state-error ui-corner-all" style="padding: 5px;">
        <p>
            <span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
            <strong>Exception:</strong>
            ${ex.message }

        </p>
    </div>
</div>

<div style="display: none;">
    <c:forEach items="${ex.stackTrace }" var="e">
        ${e }<br />
    </c:forEach>
</div>
</body>
</html>
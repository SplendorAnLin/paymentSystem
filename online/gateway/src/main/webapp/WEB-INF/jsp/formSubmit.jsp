<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>

<body onload="sub()">
<div style="display: none;">
    ${params.html}
</div>
</body>
<script type="text/javascript">
    function sub() {
        document.forms[0].submit();
    }
</script>
</html>
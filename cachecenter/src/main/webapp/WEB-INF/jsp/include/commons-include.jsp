<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link type="text/css" rel="stylesheet" href="http://static.lefu8.com/js/jquery/themes/blitzer/jquery-ui.min.css">
<link type="text/css" rel="stylesheet" href="http://static.lefu8.com/js/jquery/themes/blitzer/jquery.ui.theme.css">

<script type="text/javascript" src="http://static.lefu8.com/js/common.js"></script>
<script type="text/javascript" src="http://static.lefu8.com/js/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="http://static.lefu8.com/js/jquery/ui/jquery-ui.min.js"></script>
<script type="text/javascript" src="http://static.lefu8.com/js/jquery/ui/i18n/jquery.ui.datepicker-zh-CN.min.js"></script>

<script type="text/javascript">
	$(function() {
		$("form").submit(function() {
			$(":input").each(function() {
				if ($(this).attr("type") != "file") {
					$(this).val($.trim($(this).val()));
				}
			});
		});
	});
	
	// 全局变量contextPath，存放着当前应用名
    var contextPath = "${pageContext.request.contextPath }"; 
</script>
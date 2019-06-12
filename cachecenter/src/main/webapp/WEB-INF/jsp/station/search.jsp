<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../include/header.jsp"%>
<%@include file="../include/commons-include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3CUTF-8//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
  #word { width: 25em; }
  </style>
<script type="text/javascript">
	$(function() {
		$("#word").autocomplete(
				{
					source : function(request, response) {
						$.ajax({
							url : "${pageContext.request.contextPath}/station/suggestSearch.htm",
							type : "POST",
							dataType : "json",
							data : {word:$("#word").val(),fields:"name"},
							success : function(data) {
								response($.map(data, function(item){
									return{
										label: item.name,
										value: item.name,
										code: item.code
									};
								}));
							}
						});
					},
					minLength : 2,
					select : function(event, ui) {
						$("#cnaps").val(ui.item.code);
					},
					open : function() {
						$(this).removeClass("ui-corner-all").addClass(
								"ui-corner-top");
					},
					close : function() {
						$(this).removeClass("ui-corner-top").addClass(
								"ui-corner-all");
					}
				});
	});
</script>
</head>
<body>
	<div class="detail_tit">
		<h2>联行号匹配</h2>
	</div>
	<br>
	路线：<input type="text" id="routeNo">
	上下行标识：<input type="text" id="flag">
	站点名称<input type="text" id="word">
	<input type="text" id="cnaps" name="cnaps" readonly="readonly">
</body>
</html>

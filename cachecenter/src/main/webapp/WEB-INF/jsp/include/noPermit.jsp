<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="commons-include.jsp"%>
<html>
	<head>		
		<script>
			$(function() {
				<%  String type = request.getParameter("type"); 
			   		if("A".equals(type)){
			   			out.println("noPermit();");
			   		}
				%>					
			});
											
			function noPermit(){												
				showMsg("无操作此功能的权限！");
			}
			
			function showMsg(msg){
				$( "#dialog-message-info").html(msg);
				$( "#dialog-message" ).dialog({
					resizable: false,
					height:260,
					modal: true,				
					buttons: {"关闭": function() { $( this ).dialog( "close" );}}
				});
			}	
		</script>
	</head>
	<body>
		<div id="dialog-message" title="操作提示" style="display:none">
		<p><span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span><p id="dialog-message-info"></p></p>
	</div>	
	</body>	
</html>
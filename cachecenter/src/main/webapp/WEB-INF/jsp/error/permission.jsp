<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/commons-include.jsp"%>
<%@ include file="../include/header.jsp"%>
<%@ include file="../include/dialog.jsp"%>

<html>
	<head>		
		<script>
			
			$(function() {
							
				<%  String type = request.getParameter("type"); 
			   		if("A".equals(type)){
			   			out.println("noPermit();");
			   		}else if("B".equals(type)){
			   			out.println("sessionExpired();");
			   		}else if("C".equals(type)){
			   			out.println("compelLogout();");
			   		}
				%>					
			});
											
			function noPermit(){												
				//window.top.document.frames['main'].document.dialogMessage("无操作此功能的权限！");	
				dialogMessage("无操作此功能的权限！");						
			}	
			
			function sessionExpired(){						
				$( "#dialog:ui-dialog" ).dialog( "destroy" );	
				$( "#dialog-message-info").html("Session信息过期或未登录，请重新登录系统！");
				$( "#dialog-message" ).dialog({
					modal: true,
					buttons: {
						确定: function() {
							$( this ).dialog( "close" );
							window.top.location.href="${pageContext.request.contextPath}/jsp/login.jsp";	
						}
					}
				});		
			}	
						
			function compelLogout(){
				$( "#dialog:ui-dialog" ).dialog( "destroy" );	
				$( "#dialog-message-info").html("用户名在其它客户端重新登录，您被强制登出！");
				$( "#dialog-message" ).dialog({
					modal: true,
					buttons: {
						确定: function() {
							$( this ).dialog( "close" );
							window.top.location.href="${pageContext.request.contextPath}/jsp/login.jsp";	
						}
					}
				});	
			}		
				
		</script>
	</head>
	<body>	
	</body>	
</html>
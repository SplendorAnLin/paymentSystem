<%@ page language="java" contentType= "text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<script>	
	
	<% if(request.getParameter("success")!=null && request.getParameter("success").equals("true")){
		out.println("$(function(){");
		out.println("dialogMessage('操作成功！');");
		out.println("});");
	 	}
	%> 
	
	<% if(request.getParameter("success")!=null && request.getParameter("success").equals("false")){
		out.println("$(function(){");
		out.println("dialogMessage('操作失败！');");
		out.println("});");
	 	}
	%> 
	
	function dialogModel(info){
		$( "#dialog:ui-dialog" ).dialog( "destroy" );
		$( "#dialog-modal-info").html(info);
		$( "#dialog-modal" ).dialog({
			height: 140,
			modal: true
		});
	}
	function dialogMessage(info){
		$( "#dialog:ui-dialog" ).dialog( "destroy" );
		$( "#dialog-message-info").html(info);
		$( "#dialog-message" ).dialog({
			modal: true,
			buttons: {
				Ok: function() {
					$( this ).dialog( "close" );
				}
			}
		});
	}
</script>

<div id="dialog-modal" title="操作提示">
	<p id="dialog-modal-info"></p>
</div>

<div id="dialog-message" title="操作提示" style="display:none">
	<p><span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span><p id="dialog-message-info"></p></p>
</div>

<div id="dialog-confirm" title="操作提示" style="display:none">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span><p id="dialog-confirm-info"></p></p>
</div>

<div id="df-dialog-msg" title="操作提示" style="display:none">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span><p id="df-dialog-msg-info"></p></p>
</div>

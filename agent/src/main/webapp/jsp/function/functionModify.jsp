<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head> 
  </head>
  <body>
		
		<div class="pop_tit" ><h2>功能修改</h2></div>
		
		<form action="functionModify.action" method="post" >	
			<s:if test="#attr['function'].list.size() > 0">
				<s:set name="entity" value="#attr['function'].list[0]" />
				<table class="global_table" align="center" border="1" cellpadding="5" cellspacing="0">
					<tr>
						<th width="30%">功能名称：</th>
						<td width="70%">
							<input type="hidden" name="function.id" value="${entity.id}"/>
							<input class="inp width240" type="text" name="function.name" value="${entity.name }"/>
						</td>
					</tr>	
					<tr>	
						<th>ACTION名称：</th>
						<td>
							<input class="inp width240" type="text" name="function.action" value="${entity.action }"/>							
						</td>
					</tr>
					<tr>	
						<th>状态：</th>
						<td>
							<dict:select dictTypeId="Status" styleClass="width150" name="function.status" value="${entity.status}"></dict:select>
						</td>			
					</tr>						
					<tr>
						<th>备注：</th>
						<td>
							<textarea rows="5" cols="60" name="function.remark">${entity.remark}</textarea>
						</td>			
					</tr>										
				</table>
				
				<br/>
				<center>
					<input type="submit" value="确认修改" class="global_btn" />&nbsp;					
					<input  class="global_btn" type="reset" value="重置" />
				</center>
			</s:if>
		</form>
  </body>
</html>

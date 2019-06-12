<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<html>

<script type="text/javascript">
	function detail(id){
		window.location.href = "${pageContext.request.contextPath}/bfDetail.action?id="+id;
	}
	
	function edit(id){
		$.ajax({
			type:"post",
			url:"checkBf.action",
			data:{id:id},
			success:function(msg){
				if(msg!=''){
					alert(msg);
					return;
				}else{
					window.parent.location.href = "${pageContext.request.contextPath}/toBfModify.action?id="+id;
				}
			}
		});
	}
	
</script>

<body>	
	<s:if test="#attr['bfQuery'].list.size()>0">	
		<vlh:root value="bfQuery" url="?" includeParameters="*" configName="defaultlook">
			<table cellpadding="0" cellspacing="1" class="global_tableresult">
				<vlh:row bean="bfQuery">
					<s:set name="bfQuery" value="#attr['bfQuery']" />
					<vlh:column title="BF_CODE" property="code" attributes="width='15%'" />
					<vlh:column title="功能名称" property="name" attributes="width='15%'" />
					<vlh:column title="所属子系统" attributes="width='15%'">
						<dict:write dictId="${bfQuery.syscode}" dictTypeId="bfSys"></dict:write>
					</vlh:column>
					<vlh:column title="所属类别" property="menuname" attributes="width='15%'">
					</vlh:column>
					<vlh:column title="状态" attributes="width='15%'" >
						<s:if test="${bfQuery.status=='TRUE'}">可用</s:if>
						<s:if test="${bfQuery.status=='FALSE'}">不可用</s:if>
					</vlh:column>
					<vlh:column title="操作" attributes="width='10%'" >
						<a href="javascript:detail(${bfQuery.id});">详细</a>&nbsp;&nbsp;
						<a href="javascript:edit(${bfQuery.id});">修改</a>&nbsp;&nbsp;
					</vlh:column>
				</vlh:row>
			</table>
			<br/>										
			<div class="gpage">
				<vlh:paging showSummary="true" pages="8" attributes="width='500'" />
			</div>				
			</vlh:root>		
	</s:if>	
	<s:if test="#attr['bfQuery'].list.size()==0">
		<div style="padding-left:30px;width:200px;">
			<div class="erropop-auto" >没有找到符合条件的记录．</div>
		</div>
	</s:if>
</body>
</html>

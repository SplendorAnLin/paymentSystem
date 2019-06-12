<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<html>
<head>
	<style>
		td{border:0px solid #f00}
	</style>
	<script type="text/javascript">	
		function detail(id){
			var url="bulletinDetail.action?bulletin.id="+id;
			window.parent.parent.parent.refresh("popframe-1", url);
			window.parent.parent.parent.showDialog("popdiv-1");		
		}
	</script>
</head>
<body>
	<div class="detail_tit" ><h2>公告</h2></div><br>	
	<s:if test="#attr['bulletin'].list.size()>0">	
	
		<vlh:root value="bulletin" url="?" includeParameters="*" configName="defaultlook">
			
			<table cellpadding="0" cellspacing="1" class="global_tableresult">
				<vlh:row bean="bulletin">					
					<s:set name="entity" value="#attr['bulletin']" />
					<vlh:column title="标题" property="title" attributes="width='30%'"/>	
					<vlh:column title="创建时间" property="create_time" attributes="width='6%'">
						<s:date name="#entity.create_time" format="yy-MM-dd HH:mm"/>
					</vlh:column>
					<vlh:column title="操作" attributes="width='4%'" >
						<a href="javascript:detail(${entity.id});">详细</a>
					</vlh:column>
				</vlh:row>
			</table>
			
			<div class="gpage">
				<vlh:paging showSummary="true" pages="5" attributes="width='500'" />
			</div>				
		</vlh:root>
	</s:if>	

	<s:if test="#attr['bulletin'].list.size()==0"> 
		 <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;暂无公告
	</s:if>
</body>
</html>

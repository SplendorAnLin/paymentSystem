<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="vlh" uri="/WEB-INF/tld/valuelist.tld" %>
<%@ taglib prefix="s" uri="/WEB-INF/tld/struts-tags.tld"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<%@ include file="../include/header.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="MobileOptimized" content="240">
<meta name="viewport" content="width=device-width; initial-scale=1.0; minimum-scale=1.0; maximum-scale=1.0">
<link href="${pageContext.request.contextPath}/jsp/moble/css/common.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/jsp/moble/css/163css.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/moble/js/163css.js"></script>
<script type="text/javascript">
	function queryDetail(id){
		var url = "${pageContext.request.contextPath}/serviceInfoDetail.action?convenientServiceInfo.id=" + id;
		window.location.href = url;
	}
</script>
<style>.over2 a{background-color:#fadda5 !important; color:#000 !important; border-radius:15px !important; padding:5px 20px !important;}
.over2 a:visited{background-color:#fadda5 !important; color:#000 !important; border-radius:15px !important; padding:5px 20px !important;}</style>
</head>
<body>

<s:set name="title" value="#attr['serviceInfoQuery'].list[0].name"/>
<link href="${pageContext.request.contextPath}/jsp/moble/css/css.css" rel="stylesheet" type="text/css">
<div class="ylhead"><center><div style="margin-top:7px;"><font size="4" color="white">${title}</font></div></center></div>
<ul class="tjlist">
        <div class="pagelist">
        <ul>
        <s:if test="#attr['serviceInfoQuery'].list.size()>0">
		<vlh:root value="serviceInfoQuery" url="?" includeParameters="*" configName="defaultlook">
			<table cellpadding="0" cellspacing="1" class="global_tableresult">
				<vlh:row bean="serviceInfoQuery">
					<s:set name="entity" value="#attr['serviceInfoQuery']" />
						<vlh:column title="标题" attributes="width='150px',heigth='30px'">
							<li>
								<a style="width:150px;heigth:30px;" href="javascript:queryDetail(${entity.id})">${entity.title}</a>
							</li>
						</vlh:column>
						<vlh:column title="联系人" attributes="width='80px'" >
							<li>
								<a style="width:80px" href="javascript:queryDetail(${entity.id})">${entity.link_man}</a>
							</li>
						</vlh:column>
					
				</vlh:row>
			</table>
			
			<div class="gpage">
				<vlh:paging showSummary="true" pages="5" attributes="width='300px'" />
			</div>				
		</vlh:root>
	</s:if>	

	<s:if test="#attr['serviceInfoQuery'].list.size()==0">
		无符合条件的查询结果
	</s:if>
      </div>
</body>
</html>
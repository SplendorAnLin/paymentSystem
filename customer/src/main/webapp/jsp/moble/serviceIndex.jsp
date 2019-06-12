<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<html>
<head>
<meta name="viewport" id="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1.5">
<title>便民工具</title>
<link href="${pageContext.request.contextPath}/jsp/moble/resource/mainv5.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jsp/moble/resource/weixin2.css">
<link href="${pageContext.request.contextPath}/jsp/moble/resource/weixin.css" rel="stylesheet" type="text/css">
<script type="text/javascript" async="" src="${pageContext.request.contextPath}/jsp/moble/resource/ga.js"></script>
<script src="${pageContext.request.contextPath}/jsp/moble/resource/hm.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/moble/resource/zepto.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/moble/resource/common2.js"></script>
</head>

<body>
    <c:forEach items="${oneLevelList}" varStatus="i" var="list">
      	<c:if test="${i.first}">
      		<div class="wid mtop10 clearfix" id="topclass">  
					<ul>
      				<li><a href="#tools1" class="on" data-toolid="1">${list.name}</a></li>	
      	</c:if>
      	<c:if test="${!i.first}">
      		<li><a href="#tools${i.index + 1}" data-toolid="${i.index + 1}">${list.name}</a></li>
      	</c:if>
     	<c:if test="${i.last}">
     			</ul>
			</div>
    	</c:if>
      </c:forEach>
      
      <c:forEach items="${oneLevelList}" varStatus="i" var="list">
	<div class="toollist wid clearfix" id="tools${i.index + 1}">
		<h2><span>${list.name}</span></h2> 
		  <ul class="clearfix">
		  	  <c:forEach items="${twoLevelList}" var="tList">
		  	  	  <c:if test="${tList.parentLevel eq list.id}">
			  		  <li><a href="${tList.url}" style="background-image: url(${pageContext.request.contextPath}/${tList.icon});"><span>${tList.name}</span></a></li>
		  	  	  </c:if>
		  	  </c:forEach>
		  </ul>
	</div>
  </c:forEach>
<div class="clear"></div>
<script type="text/javascript">
$("#topclass a").on('click', function(){
    $("#topclass a").removeClass('on');
    $(this).addClass('on');    
})
</script>
</body></html>

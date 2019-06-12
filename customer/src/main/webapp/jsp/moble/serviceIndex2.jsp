<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<html>
<head>
<meta name="viewport" id="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1.5">
<title>便民工具</title>
<link href="${pageContext.request.contextPath}/jsp/moble/resource/mainv6.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jsp/moble/resource/weixin3.css">
<link href="${pageContext.request.contextPath}/jsp/moble/resource/weixin3.css" rel="stylesheet" type="text/css">
<script type="text/javascript" async="" src="${pageContext.request.contextPath}/jsp/moble/resource/ga.js"></script>
<script src="${pageContext.request.contextPath}/jsp/moble/resource/hm.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/moble/resource/zepto.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/moble/resource/common2.js"></script>
</head>

<body>
	<div style="background: url('${pageContext.request.contextPath}/jsp/moble/img/34.png') no-repeat;height: 125px;width: 100%;">
	</div>
    <c:forEach items="${oneLevelList}" varStatus="i" var="list">
      	<c:if test="${i.first}">
      		<div class="wid mtop10 clearfix" id="topclass">  
					<ul>
      				<li><a href="#tools1" class="on" data-toolid="1" style="color: white;">${list.name}</a></li>	
      	</c:if>
      	<c:if test="${!i.first}">
      		<li><a href="#tools${i.index + 1}" data-toolid="${i.index + 1}" style="color: white;">${list.name}</a></li>
      	</c:if>
     	<c:if test="${i.last}">
     			</ul>
			</div>
    	</c:if>
      </c:forEach>
      
      <c:forEach items="${oneLevelList}" varStatus="i" var="list">
	<div class="toollist wid clearfix" id="tools${i.index + 1}">
		<h2><span><lable style="margin-left: -10px;">${list.name}</lable></span></h2> 
		  <ul class="clearfix">
		  	  <c:forEach items="${twoLevelList}" var="tList">
		  	  	  <c:if test="${tList.parentLevel eq list.id}">
			  		  <li>
				  		  	<a href="${tList.url}" style="background-image: url(${pageContext.request.contextPath}/${tList.icon});"><span style="margin-top: 5px;"><font color="black;">${tList.name}</font></span></a>
			  		  </li>
		  	  	  </c:if>
		  	  </c:forEach>
		  </ul>
	</div>
  </c:forEach>
<div class="clear"></div></br></br>
<center style="color:grey;">
	千阳出品
</center></br>
<script type="text/javascript">
$("#topclass a").on('click', function(){
    $("#topclass a").removeClass('on');
    $(this).addClass('on');    
})
</script>
</body></html>

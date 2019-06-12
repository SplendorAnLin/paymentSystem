<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../include/header.jsp"%>

<head>
<script type="text/javascript">
	$(function(){
		$(".nav a").click(function(){						
			// 显示一级菜单
			var topmenu = $(this);
			var topindex = topmenu.parent().index();
			$(".nav a").removeClass("hover") ;
			topmenu.addClass("hover") ;	
						
			// 显示二级菜单						
			$(".sub").hide() ;
			var submenu = $(".sub").eq(topindex);
			submenu.show() ;		
		});
				
		$(".sub a").click(function(){
			$(".sub a").removeClass("hover") ;
			$(this).addClass("hover");
		});  	
		
		$(".sub").hide() ;	
		$(".sub").eq(0).show();	
	});			
</script>
</head>

<body>
	<div id="topmain">
		<div id="top">
			
			<img src="../../image/logo.gif" alt="ecode" width="163" height="45" border="0" class="logo" title="ecode" />
			
			<p class="topTxt">
				<span class="yellow">欢迎登录POS运营管理系统</span> 				
				<span class="red">登录时间：<s:date name="#session['auth'].loginTime" format="MM/dd HH:mm" /></span>
			</p>
									
			<ul class="nav">
				<s:iterator value="#session.auth.menu.children" status="menu">
				<s:if test="#menu.isFirst()">
					<li><a href="#" class="hover"><s:property value="name"/></a></li>
				</s:if>
				<s:else>
					<li><a href="#"><s:property value="name"/></a></li>
				</s:else>
				</s:iterator>
				<li><a href="#">自服务</a></li>
			</ul>			
			
			<s:iterator value="#session.auth.menu.children" status="menu">
				<ul class="sub">
				<s:if test="isLeaf='N'">					
					<s:iterator value="children" status="childMenu">
						<li><a href='${pageContext.request.contextPath}/<s:property value="url"/>' target="main"><s:property value="name"/></a></li>					
					</s:iterator>
				</s:if>
				</ul>				
			</s:iterator>	
			
			<ul class="sub">
				<li><a href='${pageContext.request.contextPath}/toOperatorPasswordModify.action' target="main">密码修改</a></li>
				<li><a href='${pageContext.request.contextPath}/operatorLogout.action' target="_top">退出</a></li>
			</ul>
					
		</div>
	</div>
</body>


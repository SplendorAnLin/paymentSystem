<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/tld/struts-tags.tld"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<html style="overflow-y:hidden;">
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="js/html5.js"></script>
<script type="text/javascript" src="js/respond.min.js"></script>
<script type="text/javascript" src="js/PIE_IE678.js"></script>
<![endif]-->
<link href="css/H-ui.css" rel="stylesheet" type="text/css" />
<link href="css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="./font/font-awesome.min.css"/>
<!--[if IE 7]>
<link href="font/font-awesome-ie7.min.css" rel="stylesheet" type="text/css" />
<![endif]-->
<!--[if IE 6]>
<script type="text/javascript" src="./js/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>运营管理后台</title>
</head>
<body style="overflow:hidden">
<header class="Hui-header cl">
	<a class="Hui-logo l" style="margin-left: 45px;" title="主页" href="${pageContext.request.contextPath}/operatorLogin.action">主页</a>
		<span class="Hui-userbox">
			<span class="c-white">
				<s:if test="${sessionScope.auth.operatorType eq 'ADMIN'}">管理员</s:if>
				<s:if test="${sessionScope.auth.operatorType eq 'SUPERADMIN'}">超级管理员</s:if>
				<s:if test="${sessionScope.auth.operatorType eq 'OPERATOR'}">运营人员</s:if>
				：${sessionScope.auth.realname}
			</span>
	<a class="btn btn-danger radius ml-10" href="${pageContext.request.contextPath}/jsp/logout.jsp" title="退出"><i class="icon-off"></i> 退出</a></span>
	<a aria-hidden="false" class="Hui-nav-toggle" id="nav-toggle" href="#"></a>
</header>
<div class="cl Hui-main">
  <aside class="Hui-aside" style="">
    <input runat="server" id="divScrollValue" type="hidden" value="" />
    <div class="menu_dropdown bk_2">
		<s:iterator value="#session.auth.menu.children" status="menu">
		  <dl>
	        <dt><i></i><s:property value="name"/><b></b></dt>
		        <dd>
		          <ul>
			        <s:if test="isLeaf='N'">
						<s:iterator value="children" status="childMenu">
							<li><a _href='${pageContext.request.contextPath}/<s:property value="url"/>' href="javascript:;"><s:property value="name"/></a></li>
						</s:iterator>
					</s:if>
				</ul>
	        </dd>
	      </dl>
		</s:iterator>
    </div>
  </aside>
  <div class="dislpayArrow"><a class="pngfix" href="javascript:void(0);"></a></div>
  <section class="Hui-article">
    <div id="Hui-tabNav" class="Hui-tabNav">
      <div class="Hui-tabNav-wp">
        <ul id="min_title_list" class="acrossTab cl">
          <li class="active"><span title="欢迎登陆" data-href="jsp/welcome.jsp">欢迎登陆</span><em></em></li>
        </ul>
      </div>
      <div class="Hui-tabNav-more btn-group"><a id="js-tabNav-prev" class="btn radius btn-default btn-small" href="javascript:;"><i class="icon-step-backward"></i></a><a id="js-tabNav-next" class="btn radius btn-default btn-small" href="javascript:;"><i class="icon-step-forward"></i></a></div>
    </div>
    <div id="iframe_box" class="Hui-articlebox">
      <div class="show_iframe">
        <div style="display:none" class="loading"></div>
        <iframe scrolling="yes" frameborder="0" src="jsp/welcome.jsp"></iframe>
      </div>
    </div>
  </section>
</div>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/Validform_v5.3.2_min.js"></script> 
<script type="text/javascript" src="js/layer/layer.min.js"></script>
<script type="text/javascript" src="js/H-ui.js"></script>
<script type="text/javascript" src="js/H-ui.admin.js"></script>

</body>
</html>
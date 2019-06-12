<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URL"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="viewport" content="width=device-width">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<title>支付结果</title>
<link rel="stylesheet" type="text/css" href="mobleGateway/public2.css">
</head>

<body>


	<form>
		<section>
			<header class="headerScc">
				<img src="mobleGateway/logo789.png">
				<p>
					<span>会</span><span>支</span><span>付</span><span>会</span><span>生</span><span>活</span>
				</p>
			</header>
			<div class="content">

				<div class="pay-panelScc">
					<div class="order-line">
						<label>商户名称</label>
						<div class="value" id="productName"><%=URLDecoder.decode(request.getParameter("customerName")) %></div>
					</div>
					<div class="order-line">
						<label>金&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;额</label>
						<div class="value" id="amount">
							<span>￥</span><%=request.getParameter("payAmount") %></div>
					</div>
					<div class="order-line">
						<label></label>
						<div class="value"></div>
					</div>
					<div class="order-line">
						<label>支付结果</label>
						<div class="value"><%=URLDecoder.decode(request.getParameter("payStatus")) %></div>
					</div>
				</div>
				
			</div>

		</section>
	</form>
</body>
</html>
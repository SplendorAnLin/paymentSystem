<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../include/header.jsp"%>
<html class="w-p-100">
<head>
<meta charset="UTF-8">
	<style>
.eays {
	margin-left: 10px;
	font-size: 15px;
}

.info-table {
	white-space: nowrap;
	width: 55%;
}

.info-table td {
	padding: 10px;
}

.title {
	font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
	font-weight: bold;
	color: #757575;
}

.value {
	font-size: 12px;
	color: #757575;
}

.key-value {
	width: 100px;
}
</style>
</head>
<body class="w-p-100 min-width">
	<div class="ctx-iframe">

		<div class="title-h1 fix">
			<span class="primary fl">商户信息</span>
		</div>

		<div class="table-warp  pd-r-10" style="margin-bottom: 50px;">
			<div class="table-scroll">
				<table class="info-table">
					<tbody>
						<tr>
							<td class="title"><span>商户名称</span></td>
							<td class="value"><span>浙江金蚨资产管理有限公司</span></td>
							<td class="title"><span>商户简称</span></td>
							<td class="value"><span>金蚨资产</span></td>
						</tr>
						<tr>
							<td class="title"><span>商户类型</span></td>
							<td class="value"><span>普通商户</span></td>
							<td class="title"><span>商户编号</span></td>
							<td class="value"><span>101550002435</span></td>
						</tr>
						<tr>
							<td class="title"><span>商户经营类型</span></td>
							<td class="value"><span>实体</span></td>
							<td class="title"><span>行业类别</span></td>
							<td class="value"><span>其他行业</span></td>
						</tr>
						<tr>
							<td class="title"><span>所属渠道</span></td>
							<td class="value"><span>舟山港综合保税区明德数据服务有限公司</span></td>
							<td class="title"><span>客服电话</span></td>
							<td class="value"><span>057128922189</span></td>
						</tr>
						<tr>
							<td class="title"><span>负责人手机</span></td>
							<td class="value"><span>13989890099</span></td>
							<td class="title"><span>邮箱</span></td>
							<td class="value"><span>41963264@qq.com</span></td>
						</tr>
						<tr>
							<td class="title"><span>秘钥类型(MD5)</span></td>
							<td class="value"><span class="key-value  ellipsis ib v-m">fb95f6416cf223d0e7a2933ac47c7bff</span>
								<a data-url="html/client/pop-window/key.html" data-title="查看密匙"
								data-close="true" class="eays pop-modal v-m"
								href="javascript:void(0);"><i class="fa fa-eye"></i></a></td>
							<td class="title"><span>秘钥类型(RSA)</span></td>
							<td class="value"><span class="key-value ellipsis ib v-m">MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDWXJ/t7zFQV1t4PBb1J1UNDPZmelsSmKN88v+b7MBDHpU5PU3b93f5UVwn+A6sEFV95jb32tO6+hbZI5LIUMQZXrg9lj1kES1iwVVlAykZ6pgz/
									+omQEw7o2krDL7HSR4cip8RhovQdPg/gmGc9jMfOUKBTeS9RqocuX2W6jGLxwIDAQAB</span>
								<a data-url="html/client/pop-window/key.html" data-title="查看密匙"
								data-close="true" class="eays pop-modal v-m"
								href="javascript:void(0);"><i class="fa fa-eye"></i></a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<iframe onload="InitWidth(this);" class="table-result"
			src="html/client/JiBenXinXi-table.html" frameborder="0"></iframe>
	</div>
	<%@ include file="../include/bodyLink.jsp"%>
	<script src="${pageContext.request.contextPath}/index.js"></script>
	<script>
		function InitWidth(ele) {
			$(ele).css({
				'width' : '100%',
				'height' : $(ele).contents().find('body').outerHeight() + 100,
			});
		}
	</script>
</body>
</html>
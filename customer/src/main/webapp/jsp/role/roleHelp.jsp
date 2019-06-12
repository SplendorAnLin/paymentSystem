<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<html>
<head>
	<script type="text/javascript">
		$(function() {
	        $( "#task_info" ).accordion();
		});	
	</script>
	<style>
		body{padding:0px 15px;}
		.question{width:99%;padding:3px 0px;}
		.question2{width:93.1%;padding:3px 0px;}
		.question .title{font-weight:bold;padding:0px 0px 0px 24px;background-color:#fff}
		.question .content{line-height:20px;}	
		.question2 .title{font-weight:bold;padding:0px 0px 0px 24px;background-color:#fff}
		.question2 .content{line-height:20px;}	
	</style>
</head>
	<div class="detail_tit" ><h2>角色使用帮助</h2></div><br><br>
	<br>
	<div id="task_info">
		<div class="question">
			<p class="title">Q：提报商户</p>
		</div>
		<div class="question2">
			<p class="content">
				A：对应“商户管理->新增商户”菜单
			<p>		
		</div>
		<div class="question">
			<p class="title">Q：商户查询</p>
		</div>
		<div class="question2">
			<p class="content">
				A：对应“商户管理->商户查询”菜单
			<p>		
		</div>
		<div class="question">
			<p class="title">Q：终端管理</p>
		</div>
		<div class="question2">
			<p class="content">
				A：对应“终端管理->POS绑定申请”菜单
			<p>		
		</div>
		<div class="question">
			<p class="title">Q：终端查询</p>
		</div>
		<div class="question2">
			<p class="content">
				A：对应“终端管理->终端查询、终端统计、POS绑定单查询”菜单
			<p>		
		</div>
		<div class="question">
			<p class="title">Q：报表查询</p>
		</div>
		<div class="question2">
			<p class="content">
				A：对应“统计报表->商户交易查询”菜单
			<p>		
		</div>
		<div class="question">
			<p class="title">Q：商户变更</p>
		</div>
		<div class="question2">
			<p class="content">
				A：对应“商户管理->费率变更申请、基本信息变更申请、商户网点变更申请、商户结算信息变更申请、商户增机申请、商户换机机申请”菜单
			<p>		
		</div>
		<div class="question">
			<p class="title">Q：商户变更查询</p>
		</div>
		<div class="question2">
			<p class="content">
				A：对应“单据查询->费率变更查询、基本信息变更查询、商户网点变更查询、商户结算信息变更查询、商户增机查询、商户换机机查询”菜单
			<p>		
		</div>
		<div class="question">
			<p class="title">Q：分润查询</p>
		</div>
		<div class="question2">
			<p class="content">
				A：对应“分润管理->分润查询”菜单
			<p>		
		</div>
		<div class="question">
			<p class="title">Q：账户管理</p>
		</div>
		<div class="question2">
			<p class="content">
				A：对应“账户管理->我的账户、账户明细查询、我的结算卡、结算记录查询”菜单
			<p>		
		</div>
		<div class="question">
			<p class="title">Q：交易查询</p>
		</div>
		<div class="question2">
			<p class="content">
				A：对应“交易管理->交易查询”菜单
			<p>		
		</div>
		<div class="question">
			<p class="title">Q：交易导出</p>
		</div>
		<div class="question2">
			<p class="content">
				A：对应“交易管理->交易查询”菜单中导出结果功能
			<p>		
		</div>
		<div class="question">
			<p class="title">Q：我的信息管理</p>
		</div>
		<div class="question2">
			<p class="content">
				A：对应“我的信息->我的信息、基本信息变更申请、基本信息变更查询、调单请求查询”菜单中导出结果功能
			<p>		
		</div>
	</div>
	<center style="margin-top: 20px;">
		<input type="button" value="返回"  class="global_btn" onclick="javascript:history.go(-1);"/>
	</center>
	
</html>	
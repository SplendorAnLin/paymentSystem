<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" class="w-p-100">
<head>
  <meta charset="UTF-8">
  <title>设备管理</title>
</head>
<body class="w-p-100 min-width">
<div class="ctx-iframe">
  <div class="form-box mb-40 mt-20 ml-20">
    <form class="rest-from" action="./html/sysStting/device-query.html" method="get" target="table-result">
      <div class="coll-1 ib mr-20">
					<div class="block ">
						<span class="lable w-75">手机号:</span>
						<div class="b-input w-170">
							<div class="input-wrap w-p-100">
								<input type="text" name="userName" class="input-text rest-text"
									placeholder="">
							</div>
						</div>
					</div>
					<div class="block">
						<span class="lable w-75">设备状态:</span>
						<div class="b-input w-170">
							<div class="input-wrap w-p-100">
								<select class="input-select" name="sysType" id="sysType" onchange="selectSys(this.value);">
									<option value="">全部</option>
									<option value="AGENT">正常</option>
									<option value="CUST">冻结</option>
									<option value="CUST">未使用</option>
								</select>
							</div>
						</div>
					</div>
				</div>

				<div class="coll-2 ib">
					<div class="block ">
						<span class="lable w-120">设备号:</span>
						<div class="b-input w-170">
							<div class="input-wrap w-p-100">
								<input type="text" name="no" class="input-text rest-text" id="AccNun"
									placeholder="">
							</div>
						</div>
					</div>
					<div class="rowr-4 text-right">
						 <button class="btn  btn-loading query-btn" href="javascript:void(0);">查询</button>
						<input type="reset" class="btn" value="重 置"/>
					</div>
				</div>
				
				</form>
  <iframe name="table-result" class="redirect table-result" src="" frameborder="0"></iframe>
</div>
	<%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
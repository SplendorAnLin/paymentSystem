<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<html class="w-p-100">
<head>
<script>
function listExport() {
	$("#form").attr("action", "nameListExport.action");
	$("#submit").trigger("click");
	$("#dfQuery").attr("action", "nameListQuery.action");
	var btn = $("#submit");
	if (btn[0].End) {
		btn[0].End();
	}
}
</script>
</head>
<body class="ctx-iframe" onload="query();">
	<div class="ctx-iframe">
		<div class="form-box mb-40 mt-20 ml-20">
			<form class="rest-from verif-form" action="nameListQuery.action" method="post" id="form" target="queryResult">
				<div class="coll-1 ib">
					<div class="block">
						<span class="lable w-75">姓名:</span>
							<div class="b-input w-170">
              					<div class="input-wrap w-p-100">
                					<input name="userName" type="text" class="input-text rest-text">
              					</div>
							</div>
					</div>
					<div class="block">
						<span class="lable w-75">证件号:</span>
							<div class="b-input w-170">
              					<div class="input-wrap w-p-100">
                					<input name="licenseNumber" type="text" class="input-text rest-text">
              					</div>
							</div>
					</div>
					<div class="block">
						<span class="lable w-75">帐号:</span>
							<div class="b-input w-170">
              					<div class="input-wrap w-p-100">
                					<input name="account" type="text" class="input-text rest-text">
              					</div>
							</div>
					</div>
					<div class="block">
						<span class="lable w-75">手机号码:</span>
							<div class="b-input w-170">
              					<div class="input-wrap w-p-100">
                					<input name="phoneNumber" type="text" class="input-text rest-text">
              					</div>
							</div>
					</div>
				</div>
				<div class="coll-2 ib mr-20">
					<div class="block">
						<span class="lable w-75">商户编号:</span>
							<div class="b-input w-170">
              					<div class="input-wrap w-p-100">
                					<input name="ownerId" type="text" class="input-text rest-text">
              					</div>
							</div>
					</div>
					<div class="block ">
						<span class="lable w-75">证件类型:</span>
						<div class="b-input w-170">
							<dict:select nullOption="true" name="typeOfCertificate" styleClass="input-select" dictTypeId="TYPE_OF_CERTIFICATE"></dict:select>
						</div>
					</div>
					<div class="block">
						<span class="lable w-75">卡类型:</span>
						<div class="b-input w-170">
							<dict:select nullOption="true" styleClass="input-select" name="cardType" dictTypeId="WHITE_CARD_TYPE"></dict:select>
						</div>
					</div>
					<div class="block">
						<span class="lable w-75">完成时间:</span>
						<div class="ib v-m">
							<div class="input-wrap w-170">
								<input type="text" value="" name="createTimeStart"
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-text input-date rest-text" />
							</div>
							<span class="cut"> - </span>
							<div class="input-wrap w-170">
								<input type="text" value="" name="createTimeEnd"
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="input-text input-date rest-text" />
							</div>
						</div>
					</div>
					<div class="text-right">
						<button id="submit" href="javascript:void(0);"  class="btn btn-loading query-btn" >查询</button>
						<input class="btn"  type="reset" value="重置" />
					</div>
				</div>
			</form>
		</div>
		<iframe name="queryResult" class="redirect table-result" src="" frameborder="0"></iframe>
	</div>
	</div>
	<%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../include/header.jsp"%>
<html class="over-x-h">
<head>
<style>
.lable {
	margin-right: 10px;
}
</style>
<script type="text/javascript">
  			function showMsg(msg, type){
  				if(!type) {
  					type = 3;
  				}
  				if (window.hotModal && window.hotModal.hotButton) {
  					window.hotModal.hotButton[0].End();
  				}
  				new window.top.Message({
					content: '<p>'+msg+'</p>',
					type: type
				});
			} 
  
			function valYan() {
				var accountNo = $("#accountNo").val();
				var symbol = $("#symbol").val();
				var amount = $("#amount").val();
				var capital = true;
				var prompt = "";
				if(symbol == "PLUS"){
					symbol = "<b class='c_green'>增加</b>";
					prompt = "小心他跑路哦！！！";
				}else{
					symbol = "<b class='c_red'>减少</b>";
					prompt = "赶紧扣光他！！！";
					if ($("#balance").val() < parseFloat(amount)){
						capital = false;
					}
				} 
				if (capital == true){
					var dd = $("<p style='padding: 20px;''>您真的要给账户编号为" + accountNo+symbol+amount + "元么？" + prompt +"</p>");
	  				if (window.hotModal && window.hotModal.hotButton) {
	  					window.hotModal.hotButton[0].End();
	  				}
					var prevModal = window.hotModal;
					var mm = new window.top.Modal({
						header : '<p>账户调账</p>',
						body : dd,
						footer : "<button class='btn ml-10 reverse close-btn'>取消</button><button class='btn cgbtn ml-10'>确定</button>",
						onClick: function(btn) {
							if (!$(btn).hasClass('cgbtn')) {
								return;
							}
							var params = $("#form").serialize();
							$.ajax({
								url:"adjustmentAccountAction.action",
								data:params,
								type:"post",
								async:false,	//同步
								success:function(data){
									var msg = data;
									if(msg == "true"){
										if(symbol == "增加"){
											showMsg("调账成功，亲！您真是位仁慈的老板！", 1);
											$("#form",window.parent.document).submit();
										}else{
											showMsg("调账成功，亲！干得漂亮！",1);
											$("#form",window.parent.document).submit();
										}
						  				if (window.hotModal && window.hotModal.hotButton) {
						  					window.hotModal.hotButton[0].End();
						  				}
						  			// todo 刷新页面
						  			$('iframe', window.top.tabsManage.tabs[window.top.tabsManage.tab_index].iframe).contents().find('.table-result')[0].contentWindow.location.reload();	
									}else{
										showMsg("调账失败");
						  				if (window.hotModal && window.hotModal.hotButton) {
						  					window.hotModal.hotButton[0].End();
						  				}
									}
									mm.Destroy();
									prevModal.Destroy();
								},
								error: function(error) {
									showMsg("未知错误, 请联系管理员: " + error);
					  				if (window.hotModal && window.hotModal.hotButton) {
					  					window.hotModal.hotButton[0].End();
					  				}
									mm.Destroy();
									prevModal.Destroy();
								}
							});
						}
					})
				}else{
	  				if (window.hotModal && window.hotModal.hotButton) {
	  					window.hotModal.hotButton[0].End();
	  				}
					showMsg("亲！当前账号资金不足，无法扣账嘞！");
				}
			}
		</script>
</head>
<body>
	<div class="in-pop" style="width: 400px; padding: 20px 0;">
		<form id="form" class="verif-form">
			<input type="hidden" id="balance"
				value="<%=request.getParameter("balance")%>" />
			<div class="block">
				<span class="lable w-120">账户编号:</span> <span
					class="static-lable c_gray v-m"><input type="text"
					name="accountNo" value="<%=request.getParameter("id")%>"
					readonly="readonly" id="accountNo" /></span>
			</div>
			<div class="block">
				<span class="lable w-120">资金标识:</span>
				<div class="b-input w-170">
					<dict:select styleClass="input-select" name="symbol" id="symbol" dictTypeId="CAPITAL_IDENTIFICATION"></dict:select>
				</div>
			</div>
			<div class="block">
				<span class="lable w-120">调账金额:</span>
				<div class="b-input w-170">
					<div class="input-wrap w-p-100">
						<input type="text" name="amount" id="amount" data-verifi="^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$" class="input-text verif-behoove"
							placeholder="" />
					</div>
				</div>
			</div>
			<div class="block">
				<span class="lable w-120">调账原因:</span>
				<div class="b-input w-170">
					<div class="input-wrap w-p-100">
						<input type="text" name="adjustReason" id="adjustReason"
							class="input-text verif-behoove" placeholder="输入您的调帐原因" />
					</div>
				</div>
			</div>
			<button class="hidden btn-submit verif-btn btn-loading" type="button" ></button>
		</form>
	</div>
	<%@ include file="../../include/bodyLink.jsp"%>
	<script type="text/javascript">
	$(".btn-submit").click(function(event) {
		  // 如果表单验证失败, 则返回
		  if (event.isDefaultPrevented()) {
		    return;
		  }
		  valYan();
	})
	</script>
</body>
</html>
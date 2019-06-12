<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<head>
<script type="text/javascript">
	//页面加载 时触发，根据页面默认条件进行查询
	function QueryResultLoad(){
		$("#submit").trigger("click");
	}

	/**表单提交前执行该方法**/
	function beforeSubmit() {
		$("#cbs").val("");
	}
	function spExport() {
		$("#form1").attr("action", "spExport.action");
		$("#form1").attr('target', '_self');
		var btn = $("#submit");
		btn.trigger("click");
		if (btn[0].End) {
			btn[0].End();
		}
		$("#form1").attr("action", "shareProfitQuery.action");
		$("#form1").attr('target', 'queryResult');
	}
</script>
<style>
.block {
	width: 450px;
}
</style>
</head>
<body class="w-p-100 min-width" onload="QueryResultLoad();">
	<div class="ctx-iframe">
		<div class="form-box mb-40 mt-20 ml-20">
			<form class="rest-from" method="post"
				action="toShareProfitQueryList.action" target="queryResult" id="form1">
				<div class="block ib">
					<span class="lable w-100">产品类型:</span>
					<div class="b-input w-170">
						<dict:select nullOption="true" name="product_type" styleClass="input-select" dictTypeId="BF_SHARE_PAYTYPE"></dict:select>
					</div>
				</div>
				<div class="block ib">
					<span class="lable w-100">订单金额:</span>
					<div class="b-input">
						<div class="input-wrap  w-79">
							<input name="amount_start" type="text"
								class="input-text rest-text" placeholder="">
						</div>
						<span class="cut"> - </span>
						<div class="input-wrap  w-79">
							<input name="amount_end" type="text" class="input-text rest-text"
								placeholder="">
						</div>
					</div>
				</div>
				<div class="block ib">
					<span class="lable w-100">利润区间:</span>
					<div class="b-input">
						<div class="input-wrap  w-79">
							<input name="agent_profit_start" type="text"
								class="input-text rest-text" placeholder="">
						</div>
						<span class="cut"> - </span>
						<div class="input-wrap  w-79">
							<input name="agent_profit_end" type="text"
								class="input-text rest-text" placeholder="">
						</div>
					</div>
				</div>
				<div class="block ib">
					<span class="lable w-100">会员级别:</span>
					<div class="b-input w-170">
						<dict:select nullOption="true" name="profit_type" styleClass="input-select" dictTypeId="CUSTOMER_MEMBER_LEVEL"></dict:select>
					</div>
				</div>
				<div class="block ib">
					<span class="lable w-100">上级编号:</span>
					<div class="b-input">
						<div class="input-wrap w-140">
							<input name="profit_ratio_start" type="text"
								class="input-text rest-text" placeholder="">
						</div>
					</div>
				</div>
				<div class="block ib">
					<span class="lable w-100">会员编号:</span>
					<div class="b-input">
						<div class="input-wrap  w-140">
							<input name="profit_ratio_start" type="text"
								class="input-text rest-text" placeholder="">
						</div>
					</div>
				</div>
				<div class="block ib">
					<span class="lable w-100">分润类型:</span>
					<div class="b-input w-170">
						<dict:select nullOption="true" name="profit_type" styleClass="input-select" dictTypeId="CUSTOMER_SUB_TYPE"></dict:select>
					</div>
				</div>
					<div class="block ib">
						<span class="lable w-100">分润时间:</span>
						<div class="b-input">
							<div class="input-wrap w-145">
								<input type="text" class="input-text input-date date-hms rest-text"
									placeholder="" name="create_time_start"
									value="<%=DateUtil.today()+" 00:00:00" %>" >
							</div>
							<span class="cut"> - </span>
							<div class="input-wrap w-145">
								<input type="text" class="input-text input-date date-hms rest-text"
									placeholder="" name="create_time_end"
									value="<%=DateUtil.today()+" 23:59:59" %>" >
							</div>
						</div>
					</div>
					<div class="block ib">
						<span class="lable w-100">订单完成时间:</span>
						<div class="b-input">
							<div class="input-wrap w-145">
								<input type="text" class="input-text input-date date-hms rest-text"
									placeholder="" name="order_time_start"
									value="<%=DateUtil.today()+" 00:00:00" %>">
							</div>
							<span class="cut"> - </span>
							<div class="input-wrap w-145">
								<input type="text" class="input-text input-date date-hms rest-text"
									placeholder="" name="order_time_end"
									value="<%=DateUtil.today()+" 23:59:59" %>">
							</div>
						</div>
					</div>
				<div class="text-center">
					<button type="submit" id="submit"
						class="btn  btn-loading query-btn" href="javascript:void(0);">查询</button>
					<input type="reset" class="btn" value="重置" /> <a id=""
						onclick="getCount();" class="btn btn-loading"
						href="javascript:void(0);">查看合计</a>
					<div style="display: none;" id="countSpan">
						<div class="in-pop" style="width: 400px; padding: 10px;">
							<div class="forms">
								<div class="coll-1 ib mr-20">
									<div class="block">
										<span class="lable w-100">总金额:</span> <span id="totalAmount"
											class="static-lable v-m c_red">0.00</span>
									</div>
									<div class="block">
										<span class="lable w-100">总笔数:</span> <span id="totalNum"
											class="static-lable v-m c_red">0</span>
									</div>
								</div>

								<div class="coll-2 ib">
									<div class="block">
										<span class="lable w-100">总手续费:</span> <span
											id=totalFee class="static-lable v-m c_red"></span>
									</div>
									<div class="block">
										<span class="lable w-100">总利润:</span> <span id="totalProfit"
											class="static-lable v-m c_red"></span>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
		<iframe name="queryResult" class="redirect table-result" src=""
			frameborder="0"></iframe>
	</div>
	<%@ include file="../include/bodyLink.jsp"%>
	<script type="text/javascript">
		var BB = $('#countSpan');
		var totalNum = $('#totalNum');
		var totalAmount = $('#totalAmount');
		var totalFee = $('#totalFee');
		var totalProfit = $('#totalProfit');

		function countShareProfit() {
			var MM = window.top.Modal || window.Modal;
			var test = new MM({
				header : '<p>查看合计</p>',
				body : BB.html(),
				footer : '<p></p>'
			});
		}
		function getCount() {
			params = $("#form1").serialize();
			$.ajax({
				url : "shareProfitCount.action",
				data : params,
				type : "post",
				dataType : "json",
				async : false, //同步
				success : function(data) {
					var re = eval(data);
					if (re == 'null') {
						totalNum.text(0);
						totalAmount.text(0.00);
						totalFee.text(0.00);
						totalProfit.text(0.00);
					} else {
						var ms = re.toString().split(",");
						totalNum.text(ms[0]);
						totalAmount.text((Number(ms[1]).toFixed(2)));
						totalFee.text((Number(ms[2]).toFixed(2)));
						totalProfit.text((Number(ms[3]).toFixed(2)));
					}
					countShareProfit();
				}
			});
		}
	</script>
</body>
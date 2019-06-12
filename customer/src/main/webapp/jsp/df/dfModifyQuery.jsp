<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>

<head>		
<link href="${pageContext.request.contextPath}/css/df.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">	
	
	$(document).ready(function (){
		if($("#triggerSubmit").val()=='true'||$("#triggerSubmit").val()==''||$("#triggerSubmit").val()==null||$("#triggerSubmit").val()=='null'){
			$("#submit").trigger("click");
		}
	})
	function myrefresh(){
		$("#submit").trigger("click");
	}
		/**表单提交前执行该方法**/
		function beforeSubmit(){
			 if($("#dfQuery").attr("action")=="dfExport.action"){
				//导出文件不刷新
			}else{
				$("#cbs").val("");
			} 
		}
		
		function getCount(){
			params = $("#dfQuery").serialize();
			$.ajax({
				url:"getDPayCount.action",
				data:params,
				type:"post",
				async:false,	//同步
				success:function(data){
					var msg = data;
					if(msg == 'null'){
						$("#totalNum").html(0);
						$("#totalAmount").html(0.00);
						$("#countSpan").show();
					}else{
						var ms = msg.split(",");
						$("#totalNum").html(ms[0]);
						$("#totalAmount").html(ms[1]);
						$("#countSpan").show();
					}
				}
			});
		}
		function dfExport(){
			$("#dfQuery").attr("action","dfExport.action");
			$("#submit").trigger("click");
			$("#dfQuery").attr("action","dfFailedQuery.action");
		}
	</script>
</head>

<body>
	<input type="hidden" id="triggerSubmit" value='<%=request.getParameter("refresh") %>'>
	<input type="hidden" id="cbs"/>		
	<div class="search_tit"><h2>代付单修改查询</h2></div>
	<form id="dfQuery"  method="post" action="dfFailedQuery.action" target="queryResult" onsubmit="beforeSubmit();">
		<input type="hidden" name="requestStatus" value="FAILED">
		<table class="global_table" cellpadding="0" cellspacing="0">
			<tr>		
				<th width="10%">创建时间：</th>
				<td width="20%">
					<input type="text" value="<%=DateUtil.today() %>" name="applyDate1"
						class="Wdate width80" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
					 - 	 					
			    	<input type="text" value="<%=DateUtil.today() %>" name="applyDate2"
			    	    class="Wdate width80" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
			    </td>
				<th width="10%">收款账号：</th>
				<td width="20%">
					<input class="inp" type="text" name="accountNo"/>
				</td>
				<th width="10%">收款人姓名：</th>
				<td width="20%">
					<input class="inp" type="text" name="accountName"/>
				</td>			    
			</tr>	
			<tr>
					
				<th>订单号：</th>
				<td>
					<input class="inp" type="text" name="flowId"/>
				</td>
				<th>商户编号：</th>
				<td>
					<input name="ownerId" class="inp"/>
				</td>
				<th>审核员：</th>
				<td><input class="inp" type="text" name="operator"/></td>
			</tr>
			<tr>
				<th>账户类型：</th>
				<td>
					<select name="accountType">
						<option></option>
						<option value="OPEN">对公</option>
						<option value="INDIVIDUAL">对私</option>
					</select>
				</td>
				<th>卡类型：</th>
				<td>
					<select name="cardType">
						<option></option>
						<option value="DEBIT">借记卡</option>
						<option value="CREDIT">贷记卡</option>
					</select>
				</td>
				<th>认证类型：</th>
				<td>
					<select name="certType">
						<option></option>
						<option value="NAME">账户名称</option>
						<option value="ID">身份证</option>
						<option value="PROTOCOL">协议</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>商户订单号：</th>
				<td>
					<input name="requestNo" class="inp"/>
				</td>
				<th></th>
				<td>
				</td>
				<th></th>
				<td>
				</td>
			</tr>
		</table>	
		<br>
		<center>
			<input id="submit" type="submit" class="global_btn" value="查 询" />
			<input type="reset" class="global_btn" value="重 置" />
		</center>
	</form>	
	
 	<div class="total_panel"  >
	 	<table style="width:100%">
		 	<tr>
			 	<td style="100%"><div class="total"  >
						<a href="javascript:getCount();" style="text-decoration: none">查看合计</a>
						<span id="countSpan" style="display: none;">&nbsp;&nbsp;&nbsp;
							总笔数：<span id="totalNum" style="color: red;">0</span>&nbsp;&nbsp;&nbsp;
							总金额：<span id="totalAmount" style="color: red;">0.00</span>&nbsp;&nbsp;&nbsp;
						</span>
					</div></td>
			 	<td align="right">
			 	<div class="export"  >
						<a href="javascript:dfExport();" style="text-decoration: none">导出</a>
					</div>
			 	</td>
		 	</tr>
	 	</table>
 	</div>
	
	<iframe onload="closeLoading()" name="queryResult" src="" width="100%" height="360px" scrolling="no" frameborder="0" noresize ></iframe>	
	<div id="background" class="background" style="display: none; "></div> 
 	<div id="progressBar" class="progressBar" style="display: none; ">数据加载中，请稍等...</div>
 	
 	
</body>
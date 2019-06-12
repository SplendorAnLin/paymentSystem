<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<head>		
	<script type="text/javascript">	
		function accountHisQuery(){
			$("#form1").attr("action", "accountHistoryQuery.action");
			$("#form1").attr("target", "queryResult");
			
			var isTrue = true;
			$("input[title='createTime']").each(function(){
				if($(this).val() == ''){
					dialogMessage("请选择所要查询的时间范围");
					isTrue = false;
					return false;
				}
			});
			if(isTrue){
				var end = new Date($("#createTimeEnd").val().replace(/-/g,"/"));
				var start = new Date($("#createTimeStart").val().replace(/-/g,"/"));
				var times = end.getTime() - start.getTime();
				var days = parseInt(times / (1000 * 60 * 60 * 24));
				if(days > 30){
					dialogMessage("您选择的时间不能大于30天");
					return false;
				}else{
					$("#form1").submit();
				}
			}
		}
	
	
	</script>
</head>

<body>		
	<div class="search_tit"><h2>账户明细查询</h2></div>
	<form id="form1" method="post">
		
		<table class="global_table" cellpadding="0" cellspacing="0">				
			<tr>
				<th>业务发生时间：</th>
				<td>
					<input id="createTimeStart" title="createTime" type="text" name="lowTransTime" value="<%=DateUtil.today() %>"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate width80" /> - 
					<input id="createTimeEnd" title="createTime" type="text" name="highTransTime" value="<%=DateUtil.today() %>"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate width80" />
				</td>
				<th>资金方向：</th>
				<td>
					<dict:select dictTypeId="AccountForward2" name="accountForward" nullOption="true" nullLabel="" styleClass="width150" />
				</td>
			</tr>				
		</table>
		<br>
		<center>
			<input type="button" class="global_btn" value="查 询" onclick="accountHisQuery();" />
			<input type="reset" class="global_btn" value="重 置" />
		</center>
	</form>	
	<br>
	<iframe name="queryResult" src="" width="100%" height="520px" scrolling="no" frameborder="0" noresize ></iframe>
</body>

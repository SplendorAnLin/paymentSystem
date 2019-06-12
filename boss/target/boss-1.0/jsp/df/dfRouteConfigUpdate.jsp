<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<html>
	<head>
	</head>
	<body>	
    <div class="title-h1 fix tabSwitch2">
      <span class="primary fl">付款路由配置</span>
    </div>
    
		<form id="updateForm" action="updateCustomerFeeAction.action" method="post">
			<input type="hidden" name="routeConfigBean.code" value="${routeConfigBean.code}"/>
			<table cellpadding="0" cellspacing="1" class="global_tabledetail">
				<tr>
					<th width="20%">路由状态：</th>
					<td width="30%">
						<dict:select styleClass="inp width125" name="routeConfigBean.status" dictTypeId="ACCOUNT_STATUS"></dict:select>
					</td>
					<th width="20%"></th>
					<td width="30%">
				    </td>
				</tr>
			</table>	
			<br />
		<center>
			<input type="button" class="global_btn" value="修 改" onclick="modify();"/>
			<input type="reset" class="global_btn" value="重 置" />
			<input type="button" class="global_btn" value="历 史" onclick="history();"/>
		</center>
		</form>
		<form id="routeConfigHistoryForm" method="post" action="routeConfigHistory.action" target="queryResult"></form>
		<br/>
		
		<iframe name="queryResult" src="" width="100%" height="360px" scrolling="no" frameborder="0" noresize ></iframe>	
		<div id="background" class="background" style="display: none; "></div> 
	 	<div id="progressBar" class="progressBar" style="display: none; ">数据加载中，请稍等...</div>
    
    
    <%@ include file="../include/bodyLink.jsp"%>
    <script type="text/javascript">   
      function history() {
        $("#routeConfigHistoryForm").submit();
      }
      function modify(){
        var params=$("#updateForm").serialize();
        $.ajax({
          url : "routeConfig.action",
          type : "post",
          async : false,
          data:params,
          success : function(msg) {
            if(msg=='SUCCESS'){
              alert("配置成功");
              var url = "${pageContext.request.contextPath}/toRouteConfig.action";
              window.location.href=url;
            }else{
              alert("配置失败");
            }

          }
        });
      }
    </script> 
	</body>
</html>

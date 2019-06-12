<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<html>
	<head>
		<script type="text/javascript">		
		function showImages(path){
			var url = "${pageContext.request.contextPath}/productShowImg.action?path=" + path ;
			window.parent.parent.showImages("image-3", url);
			window.parent.parent.showDialogFreedom(setDialog("imagediv-3"));
		}
		function setDialog(id){
			var settings= { 
			            message: $("#"+id,parent.parent.document), 
			            css: {
			            top:'1%',
			            left:'1%',
			            center:'yes',
			            border:	'0px solid #f00',
			            marginLeft:'0px',
			            marginTop: '0px', 
			            width:'1320px',
			            height:'580px',
			            background:'transparent',
			            scroll:'on',
			            cursor:'wait'
			        } 
			    }
			return settings;
		}
		
		function showDiv(div){
			if(div == 'apply2'){
				$("#apply2").show();
			}
			if(div == 'apply'){
				$("#apply").show();
			}
			
		}
		
		//添加图片时验证
		function fileCheck(){
			var fileName = $("#fileId").val();
			if(fileName == ''){
				dialogMessage("请上传资质文件");
			}else if(fileName.substr(fileName.lastIndexOf(".")) != '.jpg'){
				dialogMessage("请上传 .jpg 类型文件");
			}else{
				mySubmitConfirm('form1');
			}
		}
		//修改图片时验证
		function fileCheckMod(){
			var fileName = $("#fileIdModify").val();
			if(fileName != ''){
				if(fileName.substr(fileName.lastIndexOf(".")) != '.jpg'){
					dialogMessage("请上传.jpg类型文件");
				}else{
					mySubmitConfirm('form2');
				}
			}else{
				mySubmitConfirm('form2');
			}
			
		}
		
		function mySubmitConfirm(fromId){
			$( "#dialog").dialog( "destroy" );	
			$( "#dialog-confirm-info").html("确认提交该信息吗?");
			$( "#dialog-confirm" ).dialog({	resizable: false,height:140,modal: true,buttons:				
				 { "确定": function() { 
							$( this ).dialog( "close" );
							if(fromId == 'form1'){
								$("#form1").submit();
							}else if (fromId == 'form2')
								$("#form2").submit();
				   		   },
				   "取消": function() {$( this ).dialog( "close" );}
				 }
			});
		} 
		</script>	
	</head>
	<body>	
		
		<div class="pop_tit" ><h2>产品详细信息</h2></div>
		<table cellpadding="0" cellspacing="1" class="global_tabledetail">		
			<tr>
				<th width="18%">产品名称：</th>
				<td width="32%">
					${productList.name }
				</td>					
			</tr>
			<tr>
				<th>产品描述：</th>
				<td >	
					${productList.description }		
				</td>				
			</tr>
		</table>	
		<br>
		
		<center>
			<s:if test="${productCustomerInfo.status == 'AUDITING'}">
				<input type="button" class="global_btn" value="查看" onclick="showDiv('apply2');"/>
			</s:if>	
			<s:else>
				<input type="button" class="global_btn" value="开通" onclick="showDiv('apply');"/>
			</s:else>
		</center>	
		
		<!-- ----------------------------------------开通----------------------------------------------- -->
		<div id="apply" style="display:none">
 		<div class="detail_tit" ><h2>申请开通产品</h2></div> 		
 		<form id="form1" action="productOpen.action" method="post" enctype="multipart/form-data">		
 			<input type="hidden" name="productId" id="productId" value="${productList.id }">
 			<table class="global_table" cellpadding="0" cellspacing="0">
 			<tr>
				<th width="20%">资质上传&nbsp;<font color="red">*</font>：</th>
				<td width="80%">
					<input type="file"  name="file" id="fileId" class="width370">
				</td>
			</tr>
 			<tr>
				<th width="20%">模板下载&nbsp;：</th>
				<td width="80%">
					<ul>
						<li>
							<a href="${pageContext.request.contextPath}/jsp/download/lefuPaymentAccredit.docx" target="_blank">乐富代付审核员授权书.docx</a>
						</li>
						<li>
							<a href="${pageContext.request.contextPath}/jsp/download/lefuPaymentService.docx" target="_blank">乐富支付代收付服务协议.docx</a>
						</li>
						<li>
							<a href="${pageContext.request.contextPath}/jsp/download/lefuPaymentService_register.docx" target="_blank">乐富支付代收付服务协议(附件一：注册信息表) - 代付.docx</a>
						</li>
					</ul>
				</td>
			</tr>
 			
 		</table><br/>
 		<center>
 			<input type="button" value="提交" class="global_btn" onclick="fileCheck();"/>	
 			<input type="reset" class="global_btn" value="重置"/>
 		</center>
 		</form>
 		</div>	
 		
 		<!-- ----------------------------------------查看----------------------------------------------- -->
 		<div id="apply2" style="display:none" >
 		<div class="detail_tit" ><h2>申请信息</h2></div> 		
 		<form id="form2" action="productModify.action" method="post" enctype="multipart/form-data">		
 			<input type="hidden" name="productId" id="productId" value="${productList.id }">
 			<input type="hidden" name="productCusId" id="productId" value="${ProductCustomerInfo.id }">
 			<table class="global_table" cellpadding="0" cellspacing="0">
			<tr>
			<th width="20%">
					资质信息：
			</th>
			<td>
				<span id="imgspan1"><a href="javascript:showImages('${productCustomerInfo.imagePath}');">预览</a></span>
			</td>
			</tr>
			<tr>
				<th width="20%">修改&nbsp;：</th>
				<td width="80%">
					<input type="file"  name="file" id="fileIdModify" class="width370">
				</td>
			</tr>
 			
 		</table><br/>
 		<center>
 			<input type="button" value="提交" class="global_btn" onclick="fileCheckMod();"/>	
 			<input type="reset" class="global_btn" value="重置"/>
 		</center>
 		</form>
 	</div>		
	</body>
</html>

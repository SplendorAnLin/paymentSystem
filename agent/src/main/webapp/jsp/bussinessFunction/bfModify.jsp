<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.*"%>

<html>
<head>	
	<title>修改功能编码</title>
	<script type="text/javascript">
	$(function(){

		//树的基本信息设置
		var setting = {
			showLine : false,
			nodesCol : "children",
			checkable: true
		};
		var sampleNodes = ${purMenuTreeJSONString} ;
		zTree1 = $("#treeDemo").zTree(setting, sampleNodes);
		zTree1.expandAll(true) ;
		
		function menuTree(){
			$("#menuTree").show() ;		
		}
		$("#addRoleMenu").click(menuTree) ;
		
		//整理菜单字符串
		$("#form1").submit(function(){
			var selectNodes = zTree1.getCheckedNodes();
			var temp="" ;
			for(var i=0;i<selectNodes.length;i++){
				if( i==0 ){
					temp += selectNodes[i].id ;
				}else{
					temp += ("_"+selectNodes[i].id) ;
				}
			}
			$("#menusInString").val( temp ) ;
			return true ;
		});
		
		$(".menu").click(function(){ $("#menuTree").hide() ; }) ;	

		
		//显示功能选项
		function allFunction(){
			$("#function").show() ;
		}
		$("#addRoleFunction").click(allFunction) ;
		
		$(".function").click(function(){ $("#function").hide() ; }) ;
		
		//显示岗位选项
		function allPost(){
			$("#group").show() ;
		}
		$("#addPost").click(allPost) ;
		
		$(".group").click(function(){ $("#group").hide() ; }) ;
		
		//表单验证
		$("#name").addClass("validate[required,length[0,100]] text-input") ;
		$("#code").addClass("validate[required]") ;
		$("#type2").addClass("validate[required") ;
		$("#form1").validationEngine() ;
		
		//iframe进度条停止
		//window.parent.finish() ;
		
		var type1 = '${menu2.id}';
		var type = '${bussinessFunction.type}';
		$("#abc"+type1).attr("selected","selected");
		$("#abc"+type).attr("selected","selected");
		
	});
	
	function myReset(){
		window.location.href = window.location.href;
	}
	
	function loadChMenus(){
		var pid = $("#type1").val();
		$.ajax({
			type:"post",
			url:"loadChMenus.action",
			data:{pid:pid},
			success:function(msg){
				var html='';
				if(msg==''){
					dialogMessage("没有符合条件的功能");
					return;
				}else{
					$.each(msg,function(i,entity){
						html += '<option id="abc'+entity.id+'" value="'+entity.id+'">'+entity.name+'</option>';
						$("#type2").html(html);
					});
				}
			}
		});
	}
	</script>
</head>
<body>
	<div class="detail_tit"><h2>修改功能编码</h2></div>
	<form action="bfModify.action" method="post" id="form1">
		<input id="menusInString" type="hidden" name="menusInString" vlaue="" />
		<input id="bfId" type="hidden" name="bussinessFunction.id" value="${bussinessFunction.id}" />
		<table class="global_table" cellpadding="0" cellspacing="0">
			<tr>
				<th width="30%">BF_CODE：</th>
				<td width="70%">
					${bussinessFunction.code}
				</td>
			</tr>	
			<tr>	
				<th>BF名称：</th>
				<td>
					<input class="inp width220" type="text" id="name" name="bussinessFunction.name" value="${bussinessFunction.name}"/>							
				</td>
			</tr>
			<tr>	
				<th>BF描述：</th>
				<td>
					<input class="inp width450" type="text" id="description" name="bussinessFunction.description" value="${bussinessFunction.description}"/>							
				</td>
			</tr>	
			<tr>
				<th>所属子系统：</th>
				<td>
					<dict:select dictTypeId="bfSys" value="${bussinessFunction.sysCode}" name="bussinessFunction.sysCode" styleClass="width180" ></dict:select>
				</td>
			</tr>	
			<tr>
				<th>所属类别：</th>
				<td>
					<select id="type1" onchange="loadChMenus()" style="width:120px">
						<c:forEach items="${menus1}" var="m1">
							<option value="${m1.id}" id="abc${m1.id}">${m1.name}</option>
						</c:forEach>
					</select> 
					<select id="type2" name="bussinessFunction.type" style="width:120px">
						<c:forEach items="${menus2}" var="m2">
							<option value="${m2.id}" id="abc${m2.id}">${m2.name}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>	
				<th>状态：</th>
				<td><input class="inp width10" type="radio" name="bussinessFunction.status" checked="checked" id="TRUE" value="TRUE"/>可用
 				&nbsp;&nbsp;&nbsp;&nbsp;
 				<input class="inp width10" type="radio" name="bussinessFunction.status" id="FALSE" value="FALSE"/>不可用</td>			
			</tr>
			<tr>
				<th>关联菜单：</th>
				<td><a id="addRoleMenu" href="#"><span>选择菜单</span></a></td>
			</tr>
			<tr>
				<th>关联功能：</th>
				<td><a id="addRoleFunction" href="#">选择功能</a></td>
			</tr>
			<tr>
				<th>关联岗位：</th>
				<td><a id="addPost" href="#">选择岗位</a></td>
			</tr>	
		</table>
		
		<br>
		<center>
			<input type="submit"  value="提 交" class="global_btn"/> 
		  	<input type="botton"  value="重 置" class="global_btn" onclick="myReset()"/>
		  	<input type="botton"  value="返回" class="global_btn" onclick="javascript:history.go(-1);"/>
		</center>
	
	<!-- 功能 -->
	<div class="container_X" id="function" style="position:absolute;left:570px;top:120px;width:310px;display:none;">
		<div class="search_tit_X" style="padding-left:135px;cursor:move;" >
			<span><a class="function" id="functionBeSure" href="#">[确定]</a></span>
			<span style="margin-left:3px;"><a class="function" id="functionBeCancel" href="#" >[取消]</a></span>
		</div>
		<div class="search_c_X" style="height: 470px;overflow: auto;background-color: white;">
			<table>
			<c:forEach items="${allFunctions}" var="function1" varStatus="i">
				<tr>
					<td style="width:10%">
					<c:if test="${marks1[i.index]==0}">
						<input class="functionCheckbox" type="checkbox" name="functions" value="${function1.id}"/>
					</c:if>
					<c:if test="${marks1[i.index]==1}">
						<input class="functionCheckbox" type="checkbox" checked="checked" name="functions" value="${function1.id}"/>
					</c:if>
					</td>
					<td style="width:85%">${function1.name}</td>
				</tr>
			</c:forEach>
			</table>
		</div>
	</div>
	
	<!-- 岗位 -->
	<div class="container_X" id="group" style="position:absolute;left:570px;top:120px;width:310px;display:none;">
		<div class="search_tit_X" style="padding-left:135px;cursor:move;" >
			<span><a class="group" id="groupBeSure" href="#">[确定]</a></span>
			<span style="margin-left:3px;"><a class="group" id="groupBeCancel" href="#" >[取消]</a></span>
		</div>
		<div class="search_c_X" style="height: 470px;overflow: auto;background-color: white;">
			<table>
			<c:forEach items="${allPosts}" var="post" varStatus="j">
				<tr>
					<td style="width:10%">
					<c:if test="${marks2[j.index]==0}">
						<input class="groupCheckbox" type="checkbox" name="groups" value="${post.id}"/>
					</c:if>	
					<c:if test="${marks2[j.index]==1}">
						<input class="groupCheckbox" type="checkbox" checked="checked" name="groups" value="${post.id}"/>
					</c:if>	
					</td>
					<td style="width:85%">${post.postName}</td>
				</tr>
			</c:forEach>
			</table>
		</div>
	</div>
	</form>
	
	<div class="container_X" id="menuTree" style="position:absolute;left:570px;top:100px;width:310px;display:none;" >
		<div class="search_tit_X" style="padding-left:135px;cursor:move;" >
			<span><a class="menu" id="beSure" href="#">[确定]</a></span>
			<span style="margin-left:3px;"><a class="menu" id="beCancel" href="#" >[取消]</a></span>
		</div>
		<div class="search_c_X" style="height: 470px;overflow: auto;background-color: white;">
			<ul id="treeDemo" class="tree"></ul>
		</div>
	</div>
</body>
</html>

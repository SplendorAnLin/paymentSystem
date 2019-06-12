<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<head>
<script type="text/javascript" src="${applicationScope.staticFilesHost}${pageContext.request.contextPath}/js/interfaceProvider/interfaceProvider.js"></script>
</head>
<body onload="query();"  class="w-p-100">
<div class="w-p-100 min-width">
	<div class="form-box mb-40 mt-20 ml-20">
		<form class="rest-from" action="toRiskLevelQueryList.action" method="post" target="queryResult" id="form">
			<div class="coll-1 ib mr-20">
				<div class="block">
                    <span class="lable w-75">风险编号:</span>
                    <div class="b-input w-170">
                        <div class="input-wrap w-p-100">
                            <input name="fullName" type="text" class="input-text rest-text" placeholder="">
                        </div>
                    </div>
                </div>
                <div class="block">
                    <span class="lable w-75">风险名称:</span>
                    <div class="b-input w-170">
                        <div class="input-wrap w-p-100">
                            <input name="shortName" type="text" class="input-text rest-text" placeholder="">
                        </div>
                    </div>
                </div>
			</div>
			<div class="coll-2 ib">
                <div class="block">
                   <span class="lable w-75">创建日期:</span>
                   <div class="ib v-m">
                       <div class="input-wrap w-170">
                           <input name="createTimeStart" id="createTimeStart" type="text" class="input-text input-date date-hms rest-text" placeholder="" value="<%=DateUtil.today()+" 00:00:00"  %>">
                       </div>
                       <span class="cut"> - </span>
                       <div class="input-wrap w-170">
                           <input name="createTimeEnd" id="createTimeEnd" type="text" class="input-text input-date date-hms rest-text" placeholder="" value="<%=DateUtil.today()+" 23:59:59"  %>">
                       </div>
                   </div>
               </div>
			</div>
			<div style="margin-left: 372px;">
				<input type="submit" class="btn btn-loading query-btn" value="查 询" />
				<a class="btn rest-btn" href="javascript:void(0);">重置</a>
				<button class="btn pop-modal btn-loading" data-title="新增级别" data-url="toRiskLevelAdd.action" >新增级别</button>
			</div>
		</form>
	</div>
	<iframe name="queryResult" class="redirect table-result" src="" frameborder="0"></iframe>
</div>
<%@ include file="../include/bodyLink.jsp"%>
</body>
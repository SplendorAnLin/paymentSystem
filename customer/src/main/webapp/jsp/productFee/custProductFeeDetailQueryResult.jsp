<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<html>
<head>
	<style>
		td{border:0px solid #f00}
	</style>
</head>
<body>
	<br>	
	<s:if test="#attr['custProductFeeDetailQuery'].list.size()>0">
	<%int count = 1; %>
		<vlh:root value="custProductFeeDetailQuery" url="?" includeParameters="*" configName="defaultlook">
			<table cellpadding="0" cellspacing="1" class="global_tableresult">
				<vlh:row bean="custProductFeeDetailQuery">					
					<s:set name="entity" value="#attr['custProductFeeDetailQuery']" />		
					<vlh:column title="产品" attributes="width='5%'">
						<font title="${entity.productno}">${entity.productname}</font>
					</vlh:column>
					<vlh:column title="计费来源" attributes="width='5%'">
						<s:if test="#entity.billingsource == 'TRANSACTION'">
							<font title="${entity.billingsource}">交易</font>
						</s:if>
						<s:elseif test="#entity.billingsource == 'SETTLEMENT'">							
							<font title="${entity.billingsource}">结算</font>
						</s:elseif>
						<s:else>
							${entity.billingsource}
						</s:else>
					</vlh:column>
					<vlh:column title="服务商" attributes="width='8%'" >
						<font title="${entity.agentno}">${entity.agentname}</font>
					</vlh:column>
					<vlh:column title="商户" attributes="width='8%'" >
						<font title="${entity.customerno}">${entity.customername}</font>
					</vlh:column>
					<vlh:column title="商户编号" property="customerno" attributes="width='8%'" />
					<vlh:column title="阶梯上限" property="staircap" attributes="width='5%'" />
					<vlh:column title="阶梯下限" property="stairfloor" attributes="width='5%'" />
					<vlh:column title="计费方式" attributes="width='10%'">
						<s:if test="#entity.computemode == 'RATE'">
							<font title="${entity.computemode}">费率</font>
						</s:if>
						<s:elseif test="#entity.computemode == 'FIXATION'">							
							<font title="${entity.computemode}">固定</font>
						</s:elseif>
						<s:elseif test="#entity.computemode == 'FIXATIONRATE'">							
							<font title="${entity.computemode}">固定加费率</font>
						</s:elseif>
						<s:elseif test="#entity.computemode == 'RATEFLOORS'">							
							<font title="${entity.computemode}">费率保底</font>
						</s:elseif>
						<s:elseif test="#entity.computemode == 'FIXATIONRATEFLOORS'">							
							<font title="${entity.computemode}">固定加费率保底</font>
						</s:elseif>
						<s:elseif test="#entity.computemode == 'RATECAPS'">							
							<font title="${entity.computemode}">费率封顶</font>
						</s:elseif>
						<s:elseif test="#entity.computemode == 'FIXATIONRATEFCAPS'">							
							<font title="${entity.computemode}">固定加费率封顶</font>
						</s:elseif>
						<s:elseif test="#entity.computemode == 'RATEFLOORSCAPS'">							
							<font title="${entity.computemode}">费率保底加封顶</font>
						</s:elseif>
						<s:elseif test="#entity.computemode == 'RATEFIXATIONFLOORSCAPS'">							
							<font title="${entity.computemode}">费率加固定保底并封顶</font>
						</s:elseif>
						<s:else>
							${entity.computemode}
						</s:else>
					</vlh:column>
					<vlh:column title="费率" property="rate" attributes="width='4%'" />
					<vlh:column title="固定" property="fixationcost" attributes="width='5%'" />
					<vlh:column title="封顶" property="upperlimitcost" attributes="width='5%'" />
					<vlh:column title="保底" property="lowerlimitcost" attributes="width='5%'" />
					<vlh:column title="生效时间" attributes="width='8%'">
						<s:date name="#entity.effectTime" format="yy-MM-dd" />
					</vlh:column>
					<vlh:column title="失效时间" attributes="width='8%'">
						<s:date name="#entity.expireTime" format="yy-MM-dd" />
					</vlh:column>
					<vlh:column title="统计范围" property="ladderstatiscope" attributes="width='12%'">
						<s:if test="#entity.ladderstatiscope == 'SINGLE'">
							<font title="${entity.ladderstatiscope}">单笔</font>
						</s:if>
						<s:elseif test="#entity.ladderstatiscope == 'DAILY'">							
							<font title="${entity.ladderstatiscope}">每日</font>
						</s:elseif>
						<s:elseif test="#entity.ladderstatiscope == 'MONTH'">							
							<font title="${entity.ladderstatiscope}">按月</font>
						</s:elseif>
						<s:elseif test="#entity.ladderstatiscope == 'SETTLEMENTDATE'">							
							<font title="${entity.ladderstatiscope}">结算日每日非结算日</font>
						</s:elseif>
						<s:else>
							${entity.ladderstatiscope}
						</s:else>
					</vlh:column>					
					<vlh:column title="计费金额" property="transamount" attributes="width='8%'" />
					<vlh:column title="交易时间" attributes="width='10%'">
						<s:date name="#entity.transtime" format="yy-MM-dd HH:mm" />
					</vlh:column>
					<vlh:column title="产品费用" property="productfee" attributes="width='8%'" />
					<vlh:column title="计费时间" attributes="width='10%'">
						<s:date name="#entity.createtime" format="yy-MM-dd HH:mm" />
					</vlh:column>
				</vlh:row>
			</table>
			
			<div class="gpage">
				<vlh:paging showSummary="true" pages="5" attributes="width='500'" />
			</div>				
		</vlh:root>
	</s:if>	

	<s:if test="#attr['custProductFeeDetailQuery'].list.size()==0">
		无符合条件的查询结果
	</s:if>
	<div align="center">
		<input type="button" class="global_btn" value="返回" onclick="javascript:history.go(-1);"/>
	</div>
</body>
</html>

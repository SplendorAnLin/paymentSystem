<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<html>
	<head>
		<script type="text/javascript">					
		</script>	
	</head>
	<body>	
		
		<div class="detail_tit" ><h2>终端显示</h2></div>
		
			<s:if test="#attr['posShopLegal'].size()>0">
			<vlh:root value="posShopLegal" url="?" includeParameters="*" configName="defaultlook">
				
				<table cellpadding="0" cellspacing="1" class="global_tableresult">
					<vlh:row bean="posShopLegal">					
						<s:set name="psl" value="#attr['posShopLegal']" />
						<vlh:column title="终端序列号" attributes="width='10%'">
							${psl.posSn}					 										
						</vlh:column>
						<vlh:column title="类型" attributes="width='10%'">
							${psl.type}					 										
						</vlh:column>	
						<vlh:column title="拨号POS绑定号码" attributes="width='10%'" >
							${psl.bindPhoneNo}					 										
						</vlh:column>	
						<vlh:column title="法人" attributes="width='10%'">
							${psl.legalPerson}					 										
						</vlh:column>																		
						<vlh:column title="身份证号" attributes="width='20%'" >
							${psl.identityNo}					 										
						</vlh:column>	
						<vlh:column title="身份证地址"  >
							${psl.identityAddress}					 										
						</vlh:column>
						
					</vlh:row>
				</table>
				
				<div class="gpage">
					<vlh:paging showSummary="true" pages="5" attributes="width='500'" />
				</div>				
			</vlh:root>
		</s:if>	
	
			<s:if test="#attr['posShopLegal'].size()==0">
				<div style="clear:both;">商户不存在交易终端</div>
			</s:if>	
	
	</body>
</html>

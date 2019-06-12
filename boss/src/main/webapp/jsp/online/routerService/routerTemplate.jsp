<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
	<div id="showRouterStrategyDiv" style="display: none;">
		<div align="right" style="margin-top: 2%"><a href="javascript:routerStrategyAdd(0, 'isRouterAdd');"><img class="iconadd" src="/boss/image/icon_+.jpg"><span class="spanAdd"></span>路由策略新增</a></div>
		<table cellpadding="0" cellspacing="1" class="global_tabledetail">
			<table cellpadding="0" cellspacing="0" class="global_tabledetail routerStrategyList" id="ruletab_idx0" tableIdx="0">
				<tr>
					<th rowspan="6">
						规则 * | <img class='icondelete' src='${pageContext.request.contextPath}/image/arrow.gif' />示例规则</div>
					</th>
				</tr>
				<tr>
					<th style="background-color: #EEEEEE;*+width: 15%;">卡类型</th>
					<td>
						<select dictTypeCode="CardType" nullOption="true" name="profiles[0].cardType" id="profiles[0].cardType" onchange="deletInterfaceCodes(0);" >
							<option value=''></option>
							<option value='DEBIT_CARD'>借记卡</option>
							<option value='CREDIT_CARD'>贷记卡</option>
							<option value='PREPAID_CARD'>预付卡</option>
							<option value='SEMI_CREDIT_CARD'>准贷记卡</option>
							<option value='WALLET'>钱包</option>
						</select>
						<input type="hidden" id="profiles[0].cardType_ipt" name="profiles[0].cardType" value="" />
					</td>
					<th style="background-color: #EEEEEE;*+width: 15%;">接口提供方编码</th>
					<td colspan="2">
						<select name="profiles[0].interfaceProviderCode" id="profiles[0].interfaceProviderCode" class="inp width150">
							<option value=""></option>
						</select>
						<input type="hidden" id="profiles[0].interfaceProviderCode_ipt" name="profiles[0].interfaceProviderCode" value="" />
					</td>
				</tr>
				<tr>
					<th style="background-color: #EEEEEE;*+width: 15%;">接口策略类型</th>
					<td>
						<select dictTypeCode="InterfacePolicyType" nullOption="true" name="profiles[0].policyType" id="profiles[0].policyType" >
							<option value=''></option>
							<option value='PRIORITY'>优先级</option>
							<option value='SCALE'>比列</option>
							<option value='COST'>成本</option>
						</select>
					</td>
					<th style="background-color: #EEEEEE;*+width: 15%;">有效期</th>
					<td colspan="2">
						<input name="profiles[0].effectTime" id="profiles[0].effectTime" class="effectTime Wdate width80" /> - <input name="profiles[0].expireTime" id="profiles[0].expireTime" class="expireTime Wdate width80" />
					</td>
				</tr>
				<tr>
					<th id="selectInterfaceAttr0" class="selectInterfaceAttr" style="background-color: #EEEEEE;*+width: 15%;" onclick="javascript:showInterfaceInfo(0);">
						<img class="iconadd" src="${pageContext.request.contextPath}/image/icon_+.jpg"/>接口信息<br/>
					</th>
					<td colspan="4" width="700px">
						<table style="display: none; width: 700px;" id="showInterfaceInfoTable0">
							<tr>
								<th>接口名称</th>
								<th>权重\比列</th>
								<th>操作</th>
							</tr>
							<tbody id="showInterfaceInfoBody0">
							</tbody>
						</table>
					</td>
				</tr>
			</table>
			<div style="margin-top: 2%"></div>
			<div style="display: none;">
				<span id="select-result0"></span>
			</div>
			<div id="interfaceAttrSelect0" style="display: none;" class="interfaceAttrSelect">
				<ol id="interfaceAttrSelectItems0" class="interfaceAttrSelectItems ui-selectable"></ol>
			</div>
			<div id="addStrategyRouter">
				
			</div>
		</table>
	</div>
	<br>
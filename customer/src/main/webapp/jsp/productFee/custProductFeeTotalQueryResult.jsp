<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<html>
	<body>
		<s:if test="#attr['custProductFeeTotalQuery'].list.size()>0">	
			<vlh:root value="custProductFeeTotalQuery" url="?" includeParameters="*" configName="defaultLook">
				<vlh:row bean="custProductFeeTotalQuery">
					<s:set name="entity" value="#attr['custProductFeeTotalQuery']" />
					<font color='#00f'>总计费金额：</font>
					<s:if test="#entity.totaltransamount == null">
						<font color='#00f'>0.0</font>
					</s:if>
					<s:else>
						<vlh:column property="totaltransamount"/>
					</s:else>
					元，
					<font color='#00f'>总笔数：</font>
					<s:if test="#entity.totalnum == null">
						<font color='#00f'>0.0</font>
					</s:if>
					<s:else>
						<vlh:column property="totalnum"/>
					</s:else>
					笔，
					<font color='#00f'>总产品费用：</font>
					<s:if test="#entity.totalproductfee == null">
						<font color='#00f'>0.0</font>
					</s:if>
					<s:else>
						<vlh:column property="totalproductfee"/>
					</s:else>
					元
				</vlh:row>
			</vlh:root>
		</s:if>
	</body>
</html>

<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>修改路由模板</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body style="width: 1200px;min-height: 6rem;padding:10px;">
  
  <div class="query-box" style="padding: 0;">
    
    
    <!-- 卡类型缓存下拉列表 -->
    <dict:select nullOption="true" id="CardTypeTmp" styleClass="hidden" dictTypeId="CARD_TYPE"></dict:select>
    <!-- 策略类型缓存下拉列表 -->
    <dict:select nullOption="true" id="PolicyTmp" styleClass="hidden" dictTypeId="INTERFACE_POLICY"></dict:select>
    <!-- 提供方编码缓存下拉列表 -->
    <select id="ProviderCodeTmp"  class="hidden input-select" data-hide="true">
	  <option value="">全部</option>
	</select>
    <input type="hidden" id="msg" value="${msg}">

    <!--查询表单-->
    <div class="query-form">
      <form class="validator ajaxFormNotification" action="${pageContext.request.contextPath}/routerModify.action"" method="post" id="form" prompt="DropdownMessage"  data-success="修改成功" data-fail="修改失败">

        <div class="row">
          <div class="title-h1 fix tabSwitch2">
            <span class="primary fl">基本信息</span>
          </div>
          <div class="content">
            <div class="item-row fix">
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">路由编码:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text w-136" name="interfacePolicyBean.code" value="${interfacePolicyBean.code }" required readonly>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">路由名称:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text w-136" name="interfacePolicyBean.name"  value="${interfacePolicyBean.name }" required>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">接口类型:</span>
                  <div class="input-wrap">
                    <dict:select styleClass="input-select w-136 interfaceType" id="interfaceType" name="interfacePolicyBean.interfaceType" value="${interfacePolicyBean.interfaceType}" dictTypeId="BF_INTERFACE_TYPE_NAME"></dict:select>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">路由状态:</span>
                  <div class="input-wrap">
                    <dict:select styleClass="input-select w-136" name="interfacePolicyBean.status" value="${interfacePolicyBean.status }" dictTypeId="ALL_STATUS"></dict:select>
                  </div>
                </div>
              </div>
            </div>

          </div>
        </div>


        <div class="row">
          <div class="title-h1 fix">
            <span class="primary fl">路由策略</span>
            <a class="addRule fr" href="javascript:void(0);">新增路由策略</a>
          </div>
          <div class="content">
            <div id="ruleWrap">

            </div>
          </div>
        </div>



        <div class="text-center mt-10 hidden">
          <button class="btn btn-submit">新 增</button>
        </div>
      </form>
    </div>
  </div>

  
  
  <%@ include file="../../include/bodyLink.jsp"%>
  <script src="${pageContext.request.contextPath}/js/profile/routerService/router.js"></script>
  <script>
    // 初始化路由模板管理器
    routerMannage.init(function() {
    	<c:forEach items="${interfacePolicyBean.profiles }" varStatus="pfs" var="pfiles">
        routerMannage.addRule({
          'cardType': '${pfiles.cardType}',
          'policyType': '${pfiles.policyType}',
          'providerCode': '${pfiles.interfaceProviderCode}',
          'effectTime': '<fmt:formatDate value="${pfiles.effectTime }" pattern="yyyy-MM-dd"/>',
          'expireTime': '<fmt:formatDate value="${pfiles.expireTime }" pattern="yyyy-MM-dd"/>'
        });
        <c:forEach items="${pfiles.interfaceInfos }" varStatus="iifs" var="interInfos">
          // 循环h接口信息
          routerMannage.addInteface('${pfs.index}', {code: 'test-3', name: '个人网银测试'}, {
            'interfaceName': '<c:forEach items="${MaplistInterfaceInfoBean }" var="MaplistInterfaceInfoBean"><c:if test="${interInfos.interfaceCode eq MaplistInterfaceInfoBean.key}">${MaplistInterfaceInfoBean.value }</c:if></c:forEach>',
            'interfaceCode': '${interInfos.interfaceCode }',
            'scale': '${interInfos.scale }'
          });
      </c:forEach>
    	</c:forEach>
    });
   
    
    $(document).ready(function() {
      var form = $('form');
      // 获取表单验证对象实例 
      var formValidator = window.getValidator(form);

      // 验证钩子
      formValidator.options.onCustomize = function() {
        if (routerMannage.rules.length === 0) {
          Messages.error('必须添加至少一个路由策略!');
          return false;
        } else {
          // 路由策略必须添加至少一个接口信息
          if (routerMannage.rules.length !== routerMannage.interfaces.length) {
            Messages.error('必须添加至少一个接口信息!');
            return false;
          }
          return true;
        }
      };
    })
  </script>
</body>
</html>

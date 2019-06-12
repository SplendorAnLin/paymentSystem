<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>修改商户路由</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body style="width: 1200px;min-height: 6rem;padding:10px;">
  
  <div class="query-box" style="padding: 0;">
  
    <!-- 接口类型缓存 -->
    <dict:select id="InterfaceType" styleClass="hidden"  dictTypeId="BF_INTERFACE_TYPE_NAME"></dict:select>
    <input type="hidden" id="msg" value="${msg}">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator notification" action="${pageContext.request.contextPath}/partnerRouterModify.action" method="post" id="form" prompt="DropdownMessage"  data-success="修改成功" data-fail="修改失败">
        <input name="partnerRouterBean.Code" type="hidden"  value="${partnerRouterBean.code }" />
        <div class="row">
          <div class="title-h1 fix tabSwitch2">
            <span class="primary fl">基本信息</span>
          </div>
          <div class="content">
            <div class="item-row fix">
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">合作方编码:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text w-136" name="partnerRouterBean.partnerCode" value="${partnerRouterBean.partnerCode }">
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">创建时间:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text w-136"  value="<fmt:formatDate value="${partnerRouterBean.createTime }" pattern="yyyy-MM-dd" />" readonly required>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">合作方角色:</span>
                  <div class="input-wrap">
                    <dict:select value="${partnerRouterBean.partnerRole }" styleClass="input-select w-136" name="partnerRouterBean.partnerRole" id="partnerRole" dictTypeId="PARTNER_ROLE"></dict:select>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">商户路由状态:</span>
                  <div class="input-wrap">
                    <dict:select value="${partnerRouterBean.status }" styleClass="input-select w-136" name="partnerRouterBean.status"  dictTypeId="ALL_STATUS"></dict:select>
                  </div>
                </div>
              </div>
            </div>

          </div>
        </div>


        <div class="row">
          <div class="title-h1 fix">
            <span class="primary fl">合作方路由配置</span>
            <a class="addRouter fr" href="javascript:void(0);">新增路由配置</a>
          </div>
          <div class="content">
            <div id="routerWrap">
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
  <script src="${pageContext.request.contextPath}/js/profile/routerService/parentRouter.js"></script>
  <script>
  // 由于使用字典所以无法使用自定义属性, 通过js写属性
  $('#partnerRole').attr('required', 'required');
  $('#partnerRole').attr('checkPartnerCode', 'true');
  $('#partnerRole').attr('trigger', '{"checkPartnerCode": 2}');
  
  // 检测合作方编码是否已存在商户路由
  // 初始角色
  var defaultRole = $('#partnerRole').val();
  Compared.add('checkPartnerCode', function(role, params, ele, ansyFn) {
  	if (role === defaultRole) {
  		return Compared.toStatus(true);
  	}
  	// 当前合作方编码
  	var code = $('[name="partnerRouterBean.partnerCode"]').val();
    Api.boss.custRouteByCustomerNo(code, function(chunk) {
      var result =  eval(chunk);
      // 商户是否已存在商户路由
      var exist = false;
      for (var i = 0; i < result.length; ++i) {
        var info = result[i];
        if (info.PARTNER_ROLE === role) {
          exist = true;
        }
      }
      ansyFn(Compared.toStatus(exist === false, '当前合作方编码与合作方角色的组合已存在商户路由配置!'));
    });

  });
  
  // 操作结果判断
  $('form').data('receiver-hook', function(status, closeDialog, iframe) {
    var msg = utils.iframeFind(iframe, '#msg').val();
    closeDialog(msg == true || msg == 'true');
  });
  
  
    $(document).ready(function() {
      var form = $('form');
      // 获取表单验证对象实例 
      var formValidator = window.getValidator(form);
      // 验证钩子
      formValidator.options.onCustomize = function() {
        if (parentRouter.availableCount() === 0) {
          Messages.error('必须添加一条路由配置!');
          return false;
        }
        if ($('.addPolicyInfo').length !== 0) {
          Messages.error('请新增路由模板!');
          return false;
        }
        return true;
        
      };
    })
    parentRouter.init();
  </script>
  
  <c:forEach items="${partnerRouterBean.profiles }" var="profiles" varStatus="pfIdx">
    <c:forEach items="${profiles.templateInterfacePolicy }" var="templatePolicys" varStatus="tempIdx">
      <script>
        var names = '<c:if test="${MapInterfacePolicyBean !=null }"><c:forEach items="${MapInterfacePolicyBean }" var="MapInterfacePolicyBean"><c:if test="${profiles.templateInterfacePolicy eq MapInterfacePolicyBean.key}">${MapInterfacePolicyBean.value }</c:if></c:forEach></c:if>';
        var codes = '${templatePolicys }';
        var index = parentRouter.add('${profiles.interfaceType}', {
          'policySelectType': '${profiles.policySelectType }'
        });
        if (names && codes) {
        	parentRouter.setPolicyInfo(index, names, codes);
        }
      </script>
    </c:forEach>
  </c:forEach>
  
</body>
</html>

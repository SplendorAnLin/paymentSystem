<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>新增商户路由</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body style="width: 1200px;min-height: 6rem;padding:10px;">
  
  <div class="query-box" style="padding: 0;">
  
    <!-- 接口类型缓存 -->
    <dict:select id="InterfaceType" styleClass="hidden"  dictTypeId="BF_INTERFACE_TYPE_NAME"></dict:select>
    <input type="hidden" id="msg" value="${msg}">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator notification" action="${pageContext.request.contextPath}/partnerRouterAddAction.action" method="post" id="form" prompt="DropdownMessage"  data-success="新增成功" data-fail="新增失败">

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
                    <input type="text" class="input-text w-136" name="partnerRouterBean.partnerCode" required checkPartnerCode trigger='{"checkPartnerCode": 2}'>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">合作方名称:</span>
                  <div class="input-wrap">
                    <input type="text" class="input-text w-136" name="userName" placeholder="请填写合作方编码" readonly required>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">合作方角色:</span>
                  <div class="input-wrap">
                    <dict:select styleClass="input-select w-136" name="partnerRouterBean.partnerRole" id="partnerRole" dictTypeId="PARTNER_ROLE"></dict:select>
                  </div>
                </div>
              </div>
              <div class="item">
                <div class="input-area">
                  <span class="label w-90">商户路由状态:</span>
                  <div class="input-wrap">
                    <dict:select styleClass="input-select w-136" name="partnerRouterBean.status"  dictTypeId="ALL_STATUS"></dict:select>
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
  
  // 检测合作方编码是否已存在商户路由
  var userName = $('[name="userName"]');
  Compared.add('checkPartnerCode', function(val, params, ele, ansyFn) {
  	// 当前选择角色
  	var role = $('#partnerRole').val();
    // 获取商户编号全称
	Api.boss.findCustomerFullName(val, function(fullName) {
		if (fullName != 'FALSE') {
		  	Api.boss.custRouteByCustomerNo(val, function(chunk) {
		  		var result =  eval(chunk);
/* 		  		if (result.length === 0) {
		  			ansyFn(Compared.toStatus(false, '合作方编码不存在!'));
		  			return;
		  		} */
		  		
		  		// 商户是否已存在商户路由
		  		var exist = false;
		  		for (var i = 0; i < result.length; ++i) {
		  			var info = result[i];
		  			if (info.PARTNER_ROLE === role) {
		  				exist = true;
		  			}
		  		}
		  		
		  		userName.val(fullName);
		  		ansyFn(Compared.toStatus(exist === false, '当前合作方编码与合作方角色的组合已存在商户路由配置!'));
		  	});
		} else {
			userName.val('');
		  ansyFn(Compared.toStatus(false, '合作方编码不存在!'));
		}
		
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
      // 合作方角色改变主动触发合作方编码验证
      $('#partnerRole').change(function() {
      	formValidator.element($('[name="partnerRouterBean.partnerCode"]'));
      });
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
</body>
</html>

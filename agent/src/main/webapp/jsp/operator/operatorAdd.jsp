<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>新增操作员</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 420px; padding: 10px;">
  
  <div class="query-box" style="padding: 2em;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator notification" action="operatorAdd.action" method="post"  prompt="DropdownMessage"  data-success="新增成功" data-fail="新增失败">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">登录名(手机号):</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="operator.username" id="username" required phone checkOperatorPhone trigger='{"checkOperatorPhone": 2}'>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">名字:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="operator.realname" required rangelength="[2, 10]">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">密码:</span>
              <div class="input-wrap">
                <input type="password" class="input-text" name="operator.password" required rangelength="[6, 12]">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">密码确认:</span>
              <div class="input-wrap">
                <input type="password" class="input-text" name="passwordConfirm" required rangelength="[6, 12]" equalTo="[name='operator.password']">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">操作员类型:</span>
              <div class="input-wrap">
                <select class="input-select" name="operator.roleId" id="operatorType" required>
                  <option value="">加载中...</option>
                </select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">状态:</span>
              <div class="input-wrap switch-wrap">
                <dict:radio name="operator.status" dictTypeId="ALL_STATUS"></dict:radio>
              </div>
            </div>
          </div>
        </div>
        <div class="text-right hidden mt-10" style="width: 280px;">
          <button class="btn btn-submit">新 增</button>
          <input type="reset" class="btn clear-form" value="重 置">
        </div>
      </form>
    </div>
  </div>
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    // 检测操作员手机号是否重复
    Compared.add('checkOperatorPhone', function(val, params, ele, ansyFn) {
      Api.agent.checkOperatorPhone(val, function(status) { 
        ansyFn(Compared.toStatus(status, '操作员账号已存在!'));
      });
    });
    var select = $('#operatorType');
    // todo 增加 获取类型下拉列表api
    Api.agent.operatorType(function(data) {
      // 获取SelectBox组件实例
      var selectBox = select.get(0).selectBox;
      selectBox.options = window.optionArray(data, 'name', 'id');
    });
    
  </script>
</body>
</html>

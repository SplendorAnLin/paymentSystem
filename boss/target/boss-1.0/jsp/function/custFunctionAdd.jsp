<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>商户功能新增</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 430px; padding:0.2em;">
  
  <div class="query-box" style="padding: 0.8em 0;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator notification" action="custFunctionAdd.action" method="post" prompt="DropdownMessage"  data-success="新增成功" data-fail="新增失败">
        <input type="hidden" id="MenuId" readonly name="function.menuId"  />
        
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">功能名称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="function.name" required >
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">关联菜单:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="menuName" id="menuName" required checkMenu trigger='{"checkMenu": 0}'>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">ACTION:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="function.action" required placeholder=".action">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">是否验证:</span>
              <div class="input-wrap">
                <dict:select styleClass="input-select" name="function.isCheck" dictTypeId="YNY_COLOR"></dict:select>
<!--                 <input type="radio" name="function.isCheck" id="u-on" value="Y" checked >
                <lable for="u-on">开启</lable>
                <input type="radio" name="function.isCheck" id="u-off" value="N" >
                <lable for="u-off">关闭</lable> -->
                <!-- <select class="input-select" name="function.isCheck">
                  <option value="Y">开启</option>
                  <option value="N">关闭</option>
                </select> -->
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">状态:</span>
              <div class="input-wrap">
                <dict:select styleClass="input-select" name="function.status" dictTypeId="ALL_STATUS"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90" style="vertical-align: top;">备注:</span>
              <div class="input-wrap">
                <textarea class="failRemark" style="width: 285px;height: 90px;" name="function.remark" required></textarea>
              </div>
            </div>
          </div>

        </div>
        <div class="text-center mt-10 hidden">
          <button class="btn btn-submit">新 增</button>
        </div>
      </form>
    </div>
  </div>
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>

    // 检测关联菜单
    Compared.add('checkMenu', function(val, params, ele, ansyFn) {
      // 检测autocomplete组件选中数据, 用户手动输入则无效
      var menu = $(ele).attr('data-select');
      if (menu == '' || menu == undefined) {
        $('#MenuId').val('');
      }
      return Compared.toStatus(menu != '' && menu != undefined, '关联菜单无效, 请从菜单列表中选择');
    });

    // todo 选中后给 #MenuId 隐藏的赋值


    // 关联菜单联想列表
    var menuName = $('#menuName');
    var autocomplete = new AutoComplete(menuName, {
        // 数据源
        dataSource: function(val, ac, callback) {
        	menuName.inputLoading();
          Api.boss.findCustomerMenuByName(val, function(menus) {
          	menuName.inputEnding();
            callback(menus, val);
          });
        },
        // 插入位置
        appendTo: null,
        // 敲击延迟, 对于本地数据推荐0延迟
        delay: 30,
        // 是否禁用
        disabled: false,
        // 最小长度, 比如匹配银行卡号, 输入11位数时才进行请求
        minLength: 0,
        // 是否清除前后空格
        istrim: true,
        // 是否空白匹配所有
        allowEmpy: true,
        // 是否忽略大小写
        ignoreCase: true,
        // item选项模板
        tmp: '<li index="{{index}}" title="{{label}}" value="{{value}}">{{label}}</li>' 
      });
    menuName.on(AutoComplete.Event.select, function(event, data) {
      $('#MenuId').val(data.id);
    });
  </script>
</body>
</html>

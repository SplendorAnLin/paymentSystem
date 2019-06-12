// 商户路由
(function() {

  // 生成option
  function conver_option(info) {
    var option = utils.tpl('<option value="{{value}}">{{label}}</option>', {
      value: info.value,
      label: info.label
    });
    return option;
  }




  // 路由配置容器
  var routerWrap = $('#routerWrap');

  // 商户路由管理器
  var parentRouter = {
    // 接口类型对象 { B2C: {available: true, label: '个人网银'} }
    interface: {},
    // 路由配置列表
    routers: [],
    // 初始化
    init: function() {
      parentRouter.selectForInterface();
      parentRouter.bind();
    },
    // 绑定事件
    bind: function() {
      // 接口类型改变
      routerWrap.on('change', '.interfaceType', function() {
        var index = $(this).closest('.routerItem').data('index');
        var router = parentRouter.routers[index];
        parentRouter.interface[router.interfaceType].available = true;
        parentRouter.interface[this.value].available = false;
        router.interfaceType = this.value;
        parentRouter.refInterfaceType();
      });
      // 策略选择方式改变
      routerWrap.on('change', '.policySelectType', function() {
        var index = $(this).closest('.routerItem').data('index');
        parentRouter.switchPolicyType(index);
      });
      routerWrap.on('click', '.addPolicyInfo', function() {
        var btn = $(this);
        var index = btn.closest('.routerItem').data('index');
        btn.loading();
        // 显示新增路由模板对话框
        parentRouter.infoDialog(index, function() {
          btn.ending();
        });
      });

      $('.addRouter').click(function() {
        parentRouter.add(parentRouter.findAvailable());
      });
      routerWrap.on('click', '.delRouter', function() {
        var index = $(this).closest('.routerItem').data('index');
        parentRouter.remove(index);
      });
      routerWrap.on('click', '.delRouterPolicyInfo', function() {
        var index = $(this).closest('.routerItem').data('index');
        parentRouter.clearPolicyInfo(index);
      });
    },
    // 寻找最后一个可用接口类型
    findAvailable: function() {
      var interfaceType = null;
      var interface = parentRouter.interface;
      for (var type in interface) {
        if (interface[type].available) {
          interfaceType = type;
          break;
        }
      }
      return interfaceType;
    },
    // 隐射接口类型到对象
    selectForInterface: function() {
      // 接口i类型下拉列表缓存
      var interfaceTypeSelect = $('#InterfaceType');
      interfaceTypeSelect.find('option').each(function() {
        var option = $(this);
        var val = option.val();
        var label = option.text();
        parentRouter.interface[val] = { available: true, label: label };
      });
    },
    // 可用接口类型隐射到下拉列表
    interfaceForSelect: function() {
      var interface = parentRouter.interface;
      var options = '';
      for (var type in interface) {
        var info = interface[type];
        if (!info.available)
          continue;
        var option = conver_option({
          value: type,
          label: info.label
        });
        options = options + option;
      }
      return options;
    },
    // 新增路由配置
    add: function(interfaceType, values) {
      var interface = parentRouter.interface;
      // 接口类型不可用，则不增加
      if (!interfaceType || !interface[interfaceType] || !interface[interfaceType].available) {
        return;
      }
      var index = parentRouter.routers.length;
      // 创建元素
      var routerItem = view.createRouterItem(index, interfaceType, values);

      // 设置此接口类型为不可用
      interface[interfaceType].available = false;
      routerItem.data('index', index);
      // todo 下拉列表改变时, 此数据也要同步更改
      parentRouter.routers.push({
        routerItem: routerItem,
        interfaceType: interfaceType
      });
      routerWrap.append(routerItem);
      // 重新渲染组件
      window.compoment.render(routerItem);
      // 重新计算下拉列表
      parentRouter.refInterfaceType();
      return index;
    },
    // 删除路由配置
    remove: function(index) {
      var routers = parentRouter.routers;
      var interface = parentRouter.interface;
      if (index < 0 || index >= routers.length)
        return;

      var router = routers[index];
      router.routerItem.remove();
      interface[router.interfaceType].available = true;
      routers.splice(index, 1);
      // 重新计算下拉列表
      parentRouter.refInterfaceType();
      parentRouter.flushIndex();
    },
    // 设置策略路由模板
    setPolicyInfo: function(index, label, value) {
      var routers = parentRouter.routers;
      var interface = parentRouter.interface;
      if (index < 0 || index >= routers.length || !label || !value)
        return;

      var routerItem = routers[index].routerItem;
      // 禁用接口类型和策略方式下拉列表
      view.disableRouterInfo(routerItem, true);
      // 创建策略信息元素
      var policyInfo = view.createPolicyInfo(index, label, value);
      // 插入
      $('.policyWrap', routerItem).html(policyInfo);
    },
    // 移除策略路由模板
    clearPolicyInfo: function(index) {
      var routers = parentRouter.routers;
      var interface = parentRouter.interface;
      if (index < 0 || index >= routers.length)
        return;

      var routerItem = routers[index].routerItem;
      // 禁用接口类型和策略方式下拉列表
      view.disableRouterInfo(routerItem, false);
      $('.policyWrap', routerItem).empty();
      // 还原当前策略选择方式
      parentRouter.switchPolicyType(index);
    },
    // 切换策略方式
    switchPolicyType: function(index) {
      var routers = parentRouter.routers;
      var interface = parentRouter.interface;
      if (index < 0 || index >= routers.length)
        return;

      var routerItem = routers[index].routerItem;
      var policyType = $('.policySelectType', routerItem).val();
      var policyWrap = $('.policyWrap', routerItem);

      switch(policyType) {
        // 模板方式
        case 'TEMPLATE':
          // 显示路由模板新增按钮
          policyWrap.html('<div class="item ml-20"><a href="javascript:void(0);" class="btn addPolicyInfo">新增路由模板</a></div>');
          break;
        default:
          policyWrap.empty();
          break;
      }
    },
    // 显示模板信息对话框
    infoDialog: function(index, callback) {
      var routers = parentRouter.routers;
      var interface = parentRouter.interface;
      if (index < 0 || index >= routers.length) {
        if ($.isFunction(callback))
          callback();
        return;
      }
      var router = routers[index];
      var routerItem = router.routerItem;
      var interfaceType = router.interfaceType;

      // 获取路由模板
      Api.boss.routerTemplate(interfaceType, function(interfacePolicyBeans) {
        if ($.isFunction(callback))
          callback();
        if (interfacePolicyBeans.length === 0) {
          Messages.error('此接口类型没有路由模板, 请更换接口类型!');
          return;
        }
        var dialog = new window.top.MyDialog({
          target: view.createPlicyInfoDialog(interfacePolicyBeans),
          btns: [
            {
              // 按钮文本 (必须, 没有其他选项情况下, 默认为关闭按钮)
              lable: '取消'
            },
            {
              // 按钮文本 (必须)
              lable: '确定',
              // 按钮单击事件
              click: function(data, content) {
                // 获取选中的接口信息
                var option = $('[selected]', content);
                if (option.length != 0) {
                  var i = parseInt(option.data('index'));
                  parentRouter.setPolicyInfo(index, interfacePolicyBeans[i].name, interfacePolicyBeans[i].code);
                }
                dialog.close();
              }
            }
          ],
          // 页脚按钮类型(2外观更加好看)
          btnType: 2,
          title: '选择一个路由模板'
        });



      });
    },
    // 刷新索引
    flushIndex: function() {
      var routers = parentRouter.routers;
      var interface = parentRouter.interface;

      utils.each(routers, function(router, index) {
        var routerItem = router.routerItem;
        var interfaceType = router.interfaceType;
        routerItem.data('index', index);
        $('[data-placeholder-text]', routerItem).each(function() {
          var ele = $(this);
          var text = ele.attr('data-placeholder-text').replace(/index/g, index);
          ele.text(text);
        });
        $('[data-placeholder-name]', routerItem).each(function() {
          var ele = $(this);
          var name = ele.attr('data-placeholder-name').replace(/index/g, index);
          ele.attr('name', name);
        });

      });

    },
    // 判断已使用接口类型数量
    availableCount: function() {
      var count = 0;

      var interface = parentRouter.interface;
      for (var type in interface) {
        if (!interface[type].available) {
          ++count;
        }
      }

      return count;
    },
    // 刷新接口类型下拉列表
    refInterfaceType: function() {
      /**
       * 遍历已经创建的规则, 寻找其中的下拉列表,
       * 将每个下拉列表选中的类型对应设置为不可用, 然后更新下拉列表为 当前+剩余可用
       */
      
      // 可用下拉列表
      var options = parentRouter.interfaceForSelect();
      utils.each(parentRouter.routers, function(routerInfo, i) {
        var interfaceType = routerInfo.interfaceType;
        var current = conver_option({
          value: interfaceType,
          label: parentRouter.interface[interfaceType].label
        });
        // routerItem
        $('.interfaceType', routerInfo.routerItem).html(current + options);
      });
    }
  };


  // 路由配置模板
  var routerTemplate = '\
    <div class="routerItem">\
      <div class="title-h2 fix">\
        <span class="primary fl" data-placeholder-text="商户规则: index" >商户规则: {{index}}</span>\
        <a class="delRouter fr" href="javascript:void(0);">删除</a>\
      </div>\
      <div class="item-row fix">\
        <div class="item">\
          <div class="input-area">\
            <span class="label w-90">接口类型:</span>\
            <div class="input-wrap">\
              <select class="input-select w-100 interfaceType" data-placeholder-name="partnerProfiles[index].interfaceType" name="partnerProfiles[{{index}}].interfaceType" required>\
              </select>\
            </div>\
          </div>\
        </div>\
        <div class="item">\
          <div class="input-area">\
            <span class="label w-90">策略选择方式:</span>\
            <div class="input-wrap">\
              <select class="input-select w-100 policySelectType" data-placeholder-name="partnerProfiles[index].policySelectType" name="partnerProfiles[{{index}}].policySelectType" required>\
                <option value="">请选择模版</option>\
                <option value="TEMPLATE">模板</option>\
              </select>\
            </div>\
          </div>\
        </div>\
        <div class="policyWrap item fix"></div>\
      </div>\
    </div>\
  ';

  // 路由策略模板模板
  var routerPolicyInfo = '\
    <div class="item">\
      <div class="input-area">\
        <span class="label w-90">路由模板名称:</span>\
        <div class="input-wrap">\
          <input type="text" class="input-text w-100"  value="{{label}}" readonly>\
        </div>\
      </div>\
    </div>\
    <div class="item">\
      <div class="input-area">\
        <span class="label w-90">路由模板编码:</span>\
        <div class="input-wrap">\
          <input type="text" class="input-text w-100" data-placeholder-name="partnerProfiles[index].templateInterfacePolicy" name="partnerProfiles[{{index}}].templateInterfacePolicy" value="{{value}}" readonly>\
        </div>\
      </div>\
    </div>\
    <div class="item ml-20">\
      <a class="btn delRouterPolicyInfo" href="javascript:void(0);">删除</a>\
    </div>\
  ';




  // 视图
  var view = {
    // 创建路由配置元素
    createRouterItem: function(index, interfaceType, values) {
      var routerItem = $(utils.tpl(routerTemplate, {
        index: index
      }));

      $('.interfaceType', routerItem).html(conver_option({value: interfaceType, label:  parentRouter.interface[interfaceType].label}));
      if (values) {
        $('.interfaceType', routerItem).selectForValue(interfaceType);
        $('.policySelectType', routerItem).selectForValue(values['policySelectType']);
      }
      return routerItem;
    },
    // 创建策略路由模板
    createPolicyInfo: function(index, label, value) {
      var policyInfo = $(utils.tpl(routerPolicyInfo, {
        index: index,
        label: label,
        value: value
      }));
      return policyInfo;
    },
    // 创建路由模板选择对话框
    createPlicyInfoDialog: function(interfacePolicyBeans) {
      var dropdown = $('<div class="SelectBox-dropdown open"></div>');
      var dl = $('<dl class="SelectBox-options"></dl>');
      dropdown.css({
        'min-width': '300px',
        'min-height': '200px'
      });

      utils.each(interfacePolicyBeans, function(info, i) {
        var option = $(utils.tpl('<dd class="option" value="{{code}}" title="{{name}}">{{name}}</dd>', {
          code: info.code,
          name: info.name
        }));
        option.click(function() {
          $(this).attr('selected', 'selected').siblings().removeAttr('selected');
        });
        option.data('index', i);
        dl.append(option);
      });

      $('dd', dl).not(':last-child').css('border-bottom', '1px solid #ddd');
      dropdown.append(dl);
      return dropdown;
    },
    // 禁用接口类型和策略方式下拉列表
    disableRouterInfo: function(routerItem, disabled) {
      if (disabled) {
        $('.interfaceType,.policySelectType', routerItem).readonlyInput(true);
      } else {
        $('.interfaceType,.policySelectType', routerItem).readInput(true);
      }
    }
  };

  window.parentRouter = parentRouter;
})();
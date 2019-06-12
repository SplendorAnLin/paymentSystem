// 路由模板管理器
(function() {


  // 获取可用接口信息
  var interfaceInfos = [];
  // 获取提供方编码
  var providerOptions = '';



  // 规则容器
  var ruleWrap = null;
  // 路由管理器
  var routerMannage = {
    // 当前路由规则
    rules: [],
    // 当前接口信息
    interfaces: [],
    // 初始化
    init: function(callback) {
      ruleWrap = $('#ruleWrap');

      Api.boss.interFaceInfo(function(_interfaceInfos) {
        if (_interfaceInfos === 'error') {
          Messages.error('获取接口信息失败, 请稍后重试...');
          return;
        }
        interfaceInfos = _interfaceInfos;

        Api.boss.findAllProvider(function(providerList) {
          var options = '<option value="">请选择</option>';
          for (var i = 0; i < providerList.length; ++i) {
        	var prov = providerList[i];
        	options += '<option value="' + prov.code + '">' + prov.fullName + '</option>';
          }
          providerOptions = options;

          // 准备完毕
          routerMannage.bind();
          if ($.isFunction(callback))
            callback();

        });

      });

    },
    // 绑定事件
    bind: function() {
      $('.addRule').click(function() { routerMannage.addRule(); });


      ruleWrap.on('click', '.delRule', function() {
        var ruleItem = $(this).closest('.ruleItem');
        var ruleIndex = ruleItem.data('index');
        routerMannage.removeRule(ruleIndex);
      });

      ruleWrap.on('click', '.addInterface', function() {
        var ruleItem = $(this).closest('.ruleItem');
        var ruleIndex = ruleItem.data('index');
        // 选中卡类型和提供方才能下一步
        if (routerMannage.judgRuleValue(ruleItem)) {
          routerMannage.interfaceDialog(ruleIndex);
        } else {
          Messages.warn('请先选择卡类型和接口提供方!');
        }
      });


      ruleWrap.on('click', '.delInterface', function() {
        var ruleItem = $(this).closest('.ruleItem');
        var ruleIndex = ruleItem.data('index');
        var interfaceIndex = $(this).closest('.interfaceItem').data('index');
        routerMannage.removeInterface(ruleIndex, interfaceIndex);
      });




    },
    // 新增路由
    addRule: function(values) {
      var index = routerMannage.rules.length;
      var ruleItem = view.createRuleItem(index, values);
      ruleItem.data('index', index);
      ruleWrap.append(ruleItem);
      // 重新渲染组件
      window.compoment.render(ruleItem);
      routerMannage.rules.push(ruleItem);
      routerMannage.judgRuleCount();
      return index;
    },
    // 删除路由
    removeRule: function(index) {

      var ruleItem = routerMannage.rules[index];
      ruleItem.remove();
      routerMannage.rules.splice(index, 1);
      var interfaces = routerMannage.interfaces[index];
      if (interfaces)
        routerMannage.interfaces.splice(index, 1);
      routerMannage.flushIndex();
      routerMannage.judgRuleCount();
    },
    // 新增接口信息
    addInteface: function(ruleIndex, info, values) {
      var ruleItem = routerMannage.rules[ruleIndex];
      var interfaces = routerMannage.interfaces[ruleIndex];
      if (!interfaces) {
        interfaces = routerMannage.interfaces[ruleIndex] = [];
      }
      var interfaceIndex = interfaces.length;
      var interfaceItem = view.createInterfaceItem(ruleIndex, interfaceIndex, info, values);

      interfaceItem.data('index', interfaceIndex);
      $('.interfaceWrap', ruleItem).append(interfaceItem);
      routerMannage.interfaces[ruleIndex].push(interfaceItem);
      routerMannage.judgInterfaceCount(ruleIndex);
    },
    // 删除接口信息
    removeInterface: function(ruleIndex, interfaceIndex) {

      var interfaceItem = routerMannage.interfaces[ruleIndex][interfaceIndex];
      interfaceItem.remove();
      routerMannage.interfaces[ruleIndex].splice(interfaceIndex, 1);

      routerMannage.flushInterfaceIndex(ruleIndex);
      routerMannage.judgInterfaceCount(ruleIndex);
    },
    // 添加接口信息对话框
    interfaceDialog: function(ruleIndex) {
      var infos = routerMannage.filterInterface();
      if (infos.length === 0) {
        Messages.error('此接口类型暂没有接口信息, 请更换接口类型!');
        return;
      }
      // 去重复
      infos = routerMannage.uniqueInterface(infos, ruleIndex);

      if (infos.length === 0) {
        Messages.warn('无更多接口信息!');
        return;
      }

      var dialog = new window.top.MyDialog({
        target: view.createInterfaceDialog(infos),
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
                routerMannage.addInteface(ruleIndex, infos[i]);
              }
              dialog.close();
            }
          }
        ],
        // 页脚按钮类型(2外观更加好看)
        btnType: 2,
        title: '选择一个接口信息'
      });

    },
    // 根据接口类型过滤接口信息
    filterInterface: function() {
      // 当前接口类型
      var interfaceType = ifaceTypeSelect.val();
      var infos = [];
      // 根据接口类型过滤接口信息
      utils.each(interfaceInfos, function(info) {
        if (info.type === interfaceType)
          infos.push(info);
      });
      return infos;
    },
    // 刷新路由规则索引
    flushIndex: function() {
      var rules = routerMannage.rules;
      utils.each(rules, function(ruleItem, index) {
        ruleItem.data('index', index);
        $('[data-placeholder-text]', ruleItem).each(function() {
          var ele = $(this);
          var text = ele.attr('data-placeholder-text').replace(/ruleIndex/g, index);
          ele.text(text);
        });
        $('[data-placeholder-name]', ruleItem).each(function() {
          var ele = $(this);
          var name = ele.attr('data-placeholder-name').replace(/ruleIndex/g, index);
          ele.attr('name', name);
        });
        routerMannage.flushInterfaceIndex(index);
      });

    },
    // 排除以存在的接口信息
    uniqueInterface: function(infos, ruleIndex) {
      var interfaces = routerMannage.interfaces[ruleIndex];
      if (!interfaces || infos.length == 0) {
        return infos;
      }

      var availableInterface = [];

      for (var i = 0; i < infos.length; ++i) {
        var exist = false;
        for (var j = 0; j < interfaces.length; ++j) {
          if ($(interfaces[j]).find('.interfaceCode').val() == infos[i].code)
            exist = true;
        }
        if (exist == false)
          availableInterface.push(infos[i]);
      }
      return availableInterface;
    },
    // 刷新接口信息索引
    flushInterfaceIndex: function(ruleIndex) {
      var interfaces = routerMannage.interfaces[ruleIndex];
      utils.each(interfaces, function(interfaceItem, interfaceIndex) {
        interfaceItem.data('index', interfaceIndex);
        $('[data-placeholder-name]', interfaceItem).each(function() {
          var ele = $(this);
          var name = ele.attr('data-placeholder-name').replace(/ruleIndex/g, ruleIndex).replace(/interfaceIndex/g, interfaceIndex);;
          ele.attr('name', name);
        });
      });
    },
    // 判断是否禁用接口类型
    judgRuleCount: function() {
      var rules = routerMannage.rules;
      view.disableInterfaceType(rules.length >= 1);
    },
    // 判断是否停用卡类型和提供方
    judgInterfaceCount: function(ruleIndex) {
      var ruleItem = routerMannage.rules[ruleIndex];
      var interfaces = routerMannage.interfaces[ruleIndex];
      view.disableRuleInfo(ruleItem, interfaces.length >= 1);
    },
    // 判断是否可以添加接口信息
    judgRuleValue: function(ruleItem) {
      return ($('.cardType', ruleItem).val() !== '' && $('.providerCode', ruleItem).val() !== '');
    }
  };


  // 路由规则模板
  var ruleTemplate = '\
      <div class="ruleItem">\
        <div class="title-h2 fix">\
          <span class="primary fl" data-placeholder-text="路由规则#ruleIndex: 基本信息" >路由规则#{{ruleIndex}}: 基本信息</span>\
          <a class="delRule fr" href="javascript:void(0);">删除</a>\
        </div>\
        <div class="item-row fix">\
          <div class="item">\
            <div class="input-area">\
              <span class="label w-90">卡类型:</span>\
              <div class="input-wrap">\
                <select class="input-select cardType w-100" data-placeholder-name="interfacePolicyBean.profiles[ruleIndex].cardType" name="interfacePolicyBean.profiles[{{ruleIndex}}].cardType" required>\
                  <option value="">请选择</option>\
                </select>\
              </div>\
            </div>\
          </div>\
          <div class="item">\
            <div class="input-area">\
              <span class="label w-90">接口策略类型:</span>\
              <div class="input-wrap">\
                <select class="input-select policyType w-100" data-placeholder-name="interfacePolicyBean.profiles[ruleIndex].policyType" name="interfacePolicyBean.profiles[{{ruleIndex}}].policyType" required>\
                  <option value="">请选择</option>\
                  <option value="PRIORITY">优先级</option>\
                  <option value="SCALE">比列</option>\
                  <option value="COST">成本</option>\
                </select>\
              </div>\
            </div>\
          </div>\
          <div class="item">\
            <div class="input-area">\
              <span class="label w-90">接口提供方:</span>\
              <div class="input-wrap">\
                <select class="input-select providerCode w-100" data-placeholder-name="interfacePolicyBean.profiles[ruleIndex].interfaceProviderCode" name="interfacePolicyBean.profiles[{{ruleIndex}}].interfaceProviderCode" required>\
                  <option value="">请选择</option>\
                </select>\
              </div>\
            </div>\
          </div>\
          <div class="item">\
            <div class="input-area">\
              <span class="label w-90">有效日期:</span>\
              <div class="input-wrap">\
                <input type="text" data-placeholder-name="interfacePolicyBean.profiles[ruleIndex].effectTime"  name="interfacePolicyBean.profiles[{{ruleIndex}}].effectTime" class="date-start w-136 effectTime" date-fmt="yyyy-MM-dd" required>\
              </div>\
              <span class="cut"> - </span>\
              <div class="input-wrap">\
                <input type="text" data-placeholder-name="interfacePolicyBean.profiles[ruleIndex].expireTime"  name="interfacePolicyBean.profiles[{{ruleIndex}}].expireTime" class="date-end w-136 expireTime" date-fmt="yyyy-MM-dd" required>\
              </div>\
            </div>\
          </div>\
        </div>\
        <div class="interFace">\
          <div class="title-h2 fix">\
            <span class="primary fl" data-placeholder-text="路由规则#ruleIndex: 接口信息" >路由规则#{{ruleIndex}}: 接口信息</span>\
            <a class="addInterface fr" href="javascript:void(0);">添加接口信息</a>\
          </div>\
          <div class="interfaceWrap">\
          </div>\
        </div>\
      </div>\
  ';

  // 接口信息模板
  var interfaceTemplate = '\
    <div class="interfaceItem item-row fix">\
      <div class="item">\
        <div class="input-area">\
          <span class="label w-90">接口名称:</span>\
          <div class="input-wrap">\
            <input type="text" class="input-text interfaceName w-136"readonly value="{{interfaceName}}" >\
            <input type="text" class="input-text hidden interfaceCode w-136" data-placeholder-name="interfacePolicyBean.profiles[ruleIndex].interfaceInfos[interfaceIndex].interfaceCode" name="interfacePolicyBean.profiles[{{ruleIndex}}].interfaceInfos[{{interfaceIndex}}].interfaceCode" readonly required value="{{interfaceCode}}">\
          </div>\
        </div>\
      </div>\
      <div class="item">\
        <div class="input-area">\
          <span class="label w-90">权重比例:</span>\
          <div class="input-wrap">\
            <input type="text" class="input-text scale w-136" data-placeholder-name="interfacePolicyBean.profiles[ruleIndex].interfaceInfos[interfaceIndex].scale" name="interfacePolicyBean.profiles[{{ruleIndex}}].interfaceInfos[{{interfaceIndex}}].scale" digits required>\
          </div>\
        </div>\
      </div>\
      <div class="item ml-20">\
        <a class="delInterface btn" href="javascript:void(0);">移除</a>\
      </div>\
    </div>\
  ';

  // 卡类型缓存下拉列表
  var cardTypeTmp = $('#CardTypeTmp').hide();
  // 策略类型缓存下拉列表
  var policyTmp = $('#PolicyTmp').hide();
  // 提供方编码缓存下拉列表
  var providerCodeTmp = $('#ProviderCodeTmp').hide();
  // 接口类型下拉列表
  var ifaceTypeSelect = $('.interfaceType');

  // 视图管理器
  var view = {
    // 创建路由规则元素
    createRuleItem: function(index, values) {
      // 创建规则元素
      var ruleItem = $(utils.tpl(ruleTemplate, {
        ruleIndex: index
      }));

      // 卡类型
      var cardType = $('.cardType', ruleItem);
      // 接口策略类型
      var policyType = $('.policyType', ruleItem);
      // 接口提供方编码
      var providerCode = $('.providerCode', ruleItem);
      // 生效日期
      var effectTime = $('.effectTime', ruleItem);
      // 失效日期
      var expireTime = $('.expireTime', ruleItem);

      // 卡类型下拉列表从 字典:select 标签生成的select获取
      cardType.html(cardTypeTmp.html());
      // 提供方编码下拉列表从 字典:select 标签生成的select获取
      policyType.html(policyTmp.html());
      // 提供方编码下拉列表从 字典:select 标签生成的select获取
      providerCode.html(providerOptions);

      if (values) {
        cardType.selectForValue(values['cardType']);
        policyType.selectForValue(values['policyType']);
        providerCode.selectForValue(values['providerCode']);
        effectTime.val(values['effectTime']);
        expireTime.val(values['expireTime']);
      }

      return ruleItem;
    },
    // 创建接口信息元素
    createInterfaceItem: function(ruleIndex, interfaceIndex, info, values) {
      // 创建接口信息元素
      var interfaceItem = $(utils.tpl(interfaceTemplate, {
        ruleIndex: ruleIndex,
        interfaceIndex: interfaceIndex,
        interfaceCode: info.code,
        interfaceName: info.name
      }));
      // 接口名称
      var interfaceName = $('.interfaceName', interfaceItem);
      // 接口编码
      var interfaceCode = $('.interfaceCode', interfaceItem);
      // 权重比例
      var scale = $('.scale', interfaceItem);

      if (values) {
        interfaceName.val(values['interfaceName']);
        interfaceCode.val(values['interfaceCode']);
        scale.val(values['scale']);
      }

      return interfaceItem;
    },
    // 创建接口类型选择对话框元素
    createInterfaceDialog: function(infos) {
      // 创建接口信息选择对话框
      var dropdown = $('<div class="SelectBox-dropdown open"></div>');
      var dl = $('<dl class="SelectBox-options"></dl>');

      dropdown.css({
        'min-width': '300px',
        'min-height': '200px'
      });

      utils.each(infos, function(info, i) {
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
    // 启用/禁用接口类型
    disableInterfaceType: function(disabled) {
      if (disabled) {
        ifaceTypeSelect.readonlyInput(true);
      } else {
        ifaceTypeSelect.readInput(true);
      }
    },
    // 启用/禁用 卡类型和提供方
    disableRuleInfo: function(ruleItem, disabled) {
      if (disabled) {
        $('.cardType,.policyType', ruleItem).readonlyInput(true);
      } else {
        $('.cardType,.policyType', ruleItem).readInput(true);
      }
    },
  };


  window.routerMannage = routerMannage;


})();

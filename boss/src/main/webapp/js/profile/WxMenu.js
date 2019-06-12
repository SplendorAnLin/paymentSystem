(function() {


  // todo: 菜单类型选择后, 菜单编辑面板也应该根据类型刷新

  // 没有任何主菜单时占位符
  var nothing = 
  '<li class="menu selected" style="width: 100%;" menu-type="placeholder">\
      <a href="javascript:void(0);">\
        <i class="fa fa-plus"></i>\
        <span>添加菜单</span>\
      </a>\
    </li>'

  // 主菜单占位符
  var plaholder = 
    '<li class="menu" menu-type="placeholder">\
      <a href="javascript:void(0);">\
        <i class="fa fa-plus"></i>\
      </a>\
    </li>';

  // 子菜单占位符
  var sub_plaholder = 
    '<li class="submenu sub—placeholder">\
      <a href="javascript:void(0);">\
        <span class="inner"><i class="fa fa-plus"></i></span>\
      </a>\
    </li>';

  // 工具函数
  var utils = {
    // 字符串模板替换
    tpl: function(tpls, data) {
      return tpls.replace(/{{(.*?)}}/g, function($1, $2) {
        return data[$2] === undefined ? $1 : data[$2];
      });
    },
    // 字符串模板替换, 属性替换, 比如disabled属性, true就添加,false就移除
    tplForAttr: function(tpls, data) {
      return tpls.replace(/{{(.*?)}}/g, function($1, $2) {
        return data[$2] ? $1.replace(/({{)|(}})/g, '') : '';
      });
    },
    // 是否为数组
    isArray: function(obj) {
      return Object.prototype.toString.call(obj) === '[object Array]';
    },
    // 是否为函数
    isFunction: function(fn) {
      return Object.prototype.toString.call(fn) === '[object Function]';
    },
    // 去除首尾空格
    trim: function(str) {
      return str.replace(/(^\s*)|(\s*$)/g, '') || '';
    },
    /**
     * 根据索引获取option
     * @param  {Object} select select元素
     * @param  {Number} index  索引
     * @return {Object}        索引元素
     */
     getOptionForIndex: function(select, index) {
      index = index === undefined ? select[0].selectedIndex : parseInt(index);
      return select.find('option').eq(index);
     },
  };

  // 菜单类型验证器 验证通过=true, 失败=false
  var MenuTypeTest = {
    // 点击类型
    click: function(menu) {
      if (menu.type !== 'click')
        return false;
      // 验证key字段, [必填][maxLen 60]
      if (!menu.key || menu.key.length > 60)
        return false;
      return true;
    },
    // 跳转url类型
    view: function(menu) {
      if (menu.type !== 'view')
        return false;
      // 验证url字段, [必填][maxLen 150]
      if (!menu.url || menu.url.length > 150)
        return false;
      return true;
    },
  };

  // 菜单格式验证器
  var spec = function(menu, issub) {
    // 参数检测
    if (!menu) {
      console.warn('spec 菜单格式验证失败!, 菜单数据不能为空');
      return false;
    }

    // 检测是否有name字段
    if (issub) {
      if (menu.name.length > model.MaxSubMenuNameLen) {
        console.warn('spec 菜单格式验证失败!,子菜单name字段过长!', menu.name);
        return false;
      }
    } else {
      if (menu.name.length > model.MaxMenuNameLen) {
        console.warn('spec 菜单格式验证失败!,主菜单name字段过长!', menu.name);
        return false;
      }
    }

    // 类型验证函数
    var checkType = MenuTypeTest[menu.type];
    // 检测目录菜单
    if (utils.isArray(menu.sub_button)) {
      if (menu.sub_button.length > model.MaxSubMenuTotal) {
        console.warn('spec 菜单格式验证失败!, 子菜单数量过多, 最多:%s个子菜单', model.MaxSubMenuTotal);
        return false;
      }
      var flag = true;
      // 循环检测子菜单格式
      menu.sub_button.forEach(function(submenu) {
        if (spec(submenu, true) === false)
          flag = false;
      });
      if (flag === false) {
        console.warn('spec 菜单格式验证失败!, 子菜单数据不合法!', menu.sub_button);
        return false;
      } else {
        return true;
      }
    }
    if (checkType === undefined) {
      console.warn('spec 菜单格式验证失败!, 菜单数据不是一个目录菜单也没有合法type字段!');
      return false;
    }

    // 按菜单类型检测
    var testResult = checkType(menu);
    if (testResult === false || testResult === undefined) {
      console.warn('spec 菜单格式验证失败!, 菜单类型检测失败!', menu.type);
      return false;
    }

    return true;
  };
  // 生成默认主菜单
  var defaultPrimary = function(menu) {
    return $.extend({}, {
      'name': '菜单名称',
      'sub_button': []
    }, menu);
  };
  // 生成默认子菜单
  var defaultSecondary = function(menu) {
    return $.extend({}, {
      'name': '子菜单名称',
      'type': 'view',
      'url': '请输入网页地址'
    }, menu);
  };

  // 验证对象
  var verification = {
    // 检测索引是否越界
    checkIndexRound: function(data, index) {
      if (index < 0 || index >= data.length) {
        console.warn('verification.checkIndexRound 索引无效或越界, 无法修改菜单!');
        return false;
      }
      return true;
    },
    // 检测菜单数量是否越界
    checkTotal: function(data, issub) {
      if (issub) {
        // 检测子菜单数量
        if (data.length + 1 > model.MaxSubMenuTotal) {
          console.warn('verification.checkTotal 子菜单数量以满, 无法添加菜单!');
          return false;
        }
      } else {
        // 检测主菜单数量
        if (data.length + 1 > model.MaxMenuTotal) {
          console.warn('verification.checkTotal 主菜单数量以满, 无法添加菜单!');
          return false;
        }
      }
      return true;
    },
    // 检测菜单是否为目录类型
    checkDirMenu: function(menu, nowarn) {
    	// || menu.type !== undefined ||
      if (!menu) {
        return false;
      }
      if (!utils.isArray(menu.sub_button) || menu.type !== undefined) {
        if (!nowarn)
          console.warn('verification.checkDirMenu 菜单不是一个目录类型!', menu);
        
        return false;
      }
      return true;
    },
  };


  // 模型
  var model = {
    // 菜单数据
    _button: [],
    // 获取菜单数据
    get data() {
      return model._button;
    },
    // 设置菜单数据
    set data(data) {
      // 参数检测
      if (!data || !utils.isArray(data.button)) {
        console.warn('model.data 设置菜单数据失败: 数据格式非法!');
        return false;
      }
      // 检测主菜单数量是否越界
      if (data.button.length > model.MaxMenuTotal) {
        console.warn('model.data 设置菜单数据失败: 主菜单数量越界, 最大菜单数量%s!', model.MaxMenuTotal);
        return false;
      }
      // 数据格式检测
      var flag = true;
      data.button.forEach(function(menu) {
        if (spec(menu) === false)
          flag = false;
      });
      if (flag === false) {
        console.warn('model.data 设置菜单数据失败, 某些菜单格式不正确!');
        return false;
      }

      // 设置数据
      model._button = data.button;
      // 刷新
      model.flush();
    },
    // 主菜单最大数量
    MaxMenuTotal: 3,
    // 子菜单最大数量
    MaxSubMenuTotal: 5,
    // 主菜单最大名称长度(4个汉字, 8个字母)
    MaxMenuNameLen: 5,
    // 子菜单最大名称长度(7个汉字, 12个字母)
    MaxSubMenuNameLen: 7,
    // 获取完整菜单数据
    toAll: function() {
      return {'button': model._button};
    },
    // 当前选中菜单
    current: null,
    // 获取当前选中菜单
    getCurrent: function() {
      var current = model.current
      if (current == null || current === undefined)
        return null;

      if (utils.isArray(current)) {
        return model.queryToSecondary(current[0], current[1]);
      } else {
        return model.queryToPrimary(current);
      }
    },
    // 根据sub_button数组获取主菜单
    getParent: function(list) {
      if (model.data === list) {
        return model.data
      } else {
        var parent = null;
        model.data.forEach(function(menu) {
          if (menu.sub_button === list)
            parent = menu;
        });
        return parent;
      }
    },
    // 刷新数据
    flush: function() {
      var current = model.current
      if (current === null || current === undefined) {
        view.render(model.data);
      }

      if (utils.isArray(model.current)) {
        view.select(current[0], current[1]);
      } else {
        view.select(current);
      }

    },
    // 添加主菜单
    addToPrimary: function(menu) {
      var data = model.data;
      // 菜单数据
      menu = defaultPrimary(menu);
      // 检测主菜单数量
      if (!verification.checkTotal(data))
        return false;
      // 检测菜单数据格式
      if (!spec(menu))
        return false;

      // 添加菜单
      data.push(menu);
      // 刚添加的菜单作为当前选中菜单
      view.select(data.length - 1);
      //model.flush();
    },
    // 修改主菜单
    modifyToPrimary: function(index, menu) {
      var data = model.data;
      // 检测索引是否越界
      if (!verification.checkIndexRound(data, index))
        return false;
      // 菜单数据
      menu = $.extend({}, data[index], menu);
      // 如果type == ''则认为是目录类型, 删除目录
      if (menu.type == '') {
        delete menu.type;
      }
      // 检测菜单数据格式
      if (!spec(menu))
        return false;
      
      // 修改菜单
      data[index] = menu;
      model.flush();
    },
    // 删除主菜单
    delToPrimary: function(index) {
      var data = model.data;
      // 检测索引是否越界
      if (!verification.checkIndexRound(data, index))
        return false;
      
      // 删除菜单
      data.splice(index, 1);
      model.current = null;
      model.flush();
    },
    // 交换主菜单
    swapToPrimary: function(prev, next) {
      var data = model.data;
      // 检测索引是否越界
      if (!verification.checkIndexRound(data, prev) || !verification.checkIndexRound(data, next))
        return false;

      // 交换菜单
      var cache = data[prev]
      data[prev] = data[next];
      data[next] = cache;
      // 重新渲染
      //model.flush();
    },
    // 查询主菜单
    queryToPrimary: function(index) {
      var data = model.data;
      // 检测索引是否越界
      if (!verification.checkIndexRound(data, index))
        return null;
      
      return data[index];
    },
    // 选中主菜单
    selectToPrimary: function(index) {
      var data = model.data;
      // 检测索引是否越界
      if (!verification.checkIndexRound(data, index))
        return null;
      model.current = index;
    },

    // 添加子菜单
    addToSecondary: function(parentIndex, menu) {
      menu = defaultSecondary(menu);
      var data = model.data;
      var parent = model.queryToPrimary(parentIndex);
      // 获取父菜单, 如果父菜单不是目录类型,则失败
      if (!verification.checkDirMenu(parent))
        return;
      // 子菜单列表
      var button = parent.sub_button;
      // 检测子菜单数量
      if (!verification.checkTotal(button, true))
        return false;
      // 检测菜单数据格式
      if (!spec(menu, true))
        return false;

      // 添加菜单
      button.push(menu);
      // 刚添加的菜单作为当前选中菜单
      view.select(parentIndex, button.length - 1);
      //model.flush();
    },
    // 修改子菜单
    modifyToSecondary: function(parentIndex, index, menu) {
      var data = model.data;
      var parent = model.queryToPrimary(parentIndex);
      // 获取父菜单, 如果父菜单不是目录类型,则失败
      if (!verification.checkDirMenu(parent))
        return;
      // 子菜单列表
      var button = parent.sub_button;
      // 检测索引是否越界
      if (!verification.checkIndexRound(button, index))
        return false;
      menu = $.extend({}, button[index], menu);
      // 检测菜单数据格式
      if (!spec(menu, true))
        return false;
      
      // 修改菜单
      button[index] = menu;
      model.flush();
    },
    // 删除子菜单
    delToSecondary: function(parentIndex, index) {
      var data = model.data;
      var parent = model.queryToPrimary(parentIndex);
      // 获取父菜单, 如果父菜单不是目录类型,则失败
      if (!verification.checkDirMenu(parent))
        return;
      // 子菜单列表
      var button = parent.sub_button;
      // 检测索引是否越界
      if (!verification.checkIndexRound(button, index))
        return false;


      // 子元素都删完索引指向父菜单
      if (index === 0 && button.length === 1) {
        model.current = parentIndex;
      } else if (index === button.length - 1) {
        // 最末尾索引
        model.current[1] = button.length - 2;
      }

      // 修改菜单
      button.splice(index, 1);
      model.flush();
    },
    // 交换子菜单
    swapToSecondary: function(parentIndex, prev, next) {
      var data = model.data;
      var parent = model.queryToPrimary(parentIndex);
      // 获取父菜单, 如果父菜单不是目录类型,则失败
      if (!verification.checkDirMenu(parent))
        return;
      // 子菜单列表
      var button = parent.sub_button;
      // 检测索引是否越界
      if (!verification.checkIndexRound(button, prev) || !verification.checkIndexRound(button, next))
        return false;

      // 修改菜单
      var cache = button[prev];
      button[prev] = button[next];
      button[next] = cache;
      //model.flush();
    },
    // 查询子菜单
    queryToSecondary: function(parentIndex, index) {
      var data = model.data;
      var parent = model.queryToPrimary(parentIndex);
      // 获取父菜单, 如果父菜单不是目录类型,则失败
      if (!verification.checkDirMenu(parent))
        return null;
      // 子菜单列表
      var button = parent.sub_button;
      // 检测索引是否越界
      if (!verification.checkIndexRound(button, index))
        return null;
      return button[index];
    },
    // 选中子菜单
    selectToSecondary: function(parentIndex, index) {
      var data = model.data;
      var parent = model.queryToPrimary(parentIndex);
      // 获取父菜单, 如果父菜单不是目录类型,则失败
      if (!verification.checkDirMenu(parent))
        return false;
      var button = parent.sub_button;
      // 检测索引是否越界
      if (!verification.checkIndexRound(button, index))
        return false;
      model.current = [parentIndex, index];
    },

  };

  // 视图
  var view = {
    // 视图容器
    menubox: null,
    // 绑定容器
    bind: function(container) {
      view.menubox = container;
      // 菜单顺序改变
      $('#menu-index').change(view.hander_swap);
      // 菜单类型改变
      $('#menu-type').change(view.hander_type);
      // 删除菜单按钮
      $('#btn-delMenu').click(uditorPlan.remove);
      // 修改按钮
      $('#btn-modify').click(uditorPlan.modify);
      // 保存按钮
      $('#btn-save').click(uditorPlan.save);

      // 菜单错误提示关闭按钮
      $('.frm-error .close').click(function() {
        $(this).parents('.frm-error').hide();
      });

      view.render([]);
    },
    // 渲染视图
    render: function(data) {
      var menubox = view.menubox;
      // 参数检测
      if (!menubox || !utils.isArray(data))
        return;
      // 没有数组则使用占位符
      if (data.length === 0) {
        menubox.html($(nothing).bind('click', {issub: false}, view.hander_new));
        return;
      }

      var menus = $();
      // 循环创建主菜单
      data.forEach(function(menu, i) {
        var menuLi = view.create(menu, i);
        // 循环创建子菜单
        if (verification.checkDirMenu(menu, true)) {
          menuLi.append($('<div class="sub_menus"><ul class="sub_menus_list"></ul><i class="arrow arrow_out"></i><i class="arrow arrow_in"></i></div>'));
          var ul = $('.sub_menus_list', menuLi);
          var submenus = $();
          menu.sub_button.forEach(function(submenu, j) {
            submenus = submenus.add(view.createSubMenu(submenu, i, j));
          });
          if (utils.isArray(model.current)) {
            // 添加子元素被选中样式
            if (model.current[1] === menu.sub_button.length - 1) {
              $('.arrow_out', menuLi).css('border-top-color', '#44b549');
            }
          }



          // 创建补位子菜单
          if (menu.sub_button.length < model.MaxSubMenuTotal) {
            submenus = submenus.add($(sub_plaholder).bind('click', {issub: true, parentIndex: i}, view.hander_new));
          }
          ul.append(submenus);
        }
        
        menus = menus.add(menuLi);
      });
      // 创建占位符菜单
      if (data.length < model.MaxMenuTotal) {
        var plaMenu = $(plaholder).bind('click', {issub: false}, view.hander_new);
        menus = menus.add(plaMenu);
      }
      // 平均分配菜单宽度
      menus.css('width', ((100 / menus.length) + '%') );
      menubox.html(menus);
    },
    // 生成主菜单
    create: function(menu, index) {
      // todo 绑定事件
      // 获取菜单类型
      var type = menu.type || 'dir';
      var current = model.current;
      var html = 
      '<li class="menu" menu-type="{{type}}">\
        <a href="javascript:void(0);"><i class="fa"></i><span>{{name}}</span></a>\
      </li>';
      
      var li = $(utils.tpl(html, {
        type: type,
        name: menu.name
      }));

      if (current !== null) {
        if (utils.isArray(current)) {
          // 子元素被选中, 则菜单添加 sub_selected 类展开子元素列表
          if (menu === model.queryToPrimary(current[0])) {
            li.addClass('sub_selected');
          }
        } else {
          // 当前菜单被选中, 则添加选中样式
          if (menu === model.queryToPrimary(current)) {
            li.addClass('selected');
          }
        }
      }

      li.data('menu', menu);
      li.bind('click', {'menu': menu, 'issub': false, 'index': index}, view.hander_select);

      return li;
    },
    // 生成子菜单
    createSubMenu: function(submenu, parentIndex, index) {
      // 获取菜单类型
      var type = submenu.type;
      var current = model.current;
      var html = 
      '<li class="submenu" menu-type="{{type}}">\
        <a href="javascript:void(0);">\
          <span class="inner"><span>{{name}}</span></span>\
        </a>\
      </li>';
      
      var li = $(utils.tpl(html, {
        type: type,
        name: submenu.name
      }));


      if (utils.isArray(current)) {
        // 添加子元素被选中样式
        if (submenu == model.queryToSecondary(current[0], current[1])) {
          li.addClass('selected');
        }
      }
      
      li.data('menu', submenu);
      li.bind('click', {'menu': submenu, 'issub': true, 'index': index, 'parentIndex': parentIndex}, view.hander_select);
      return li;
    },
    // 选中菜单
    select: function(parentIndex, subIndex) {
      var parent = model.queryToPrimary(parentIndex);
      // 检测主菜单索引是否越界
      if (!parent) {
        //console.warn('选中菜单失败, 主菜单索引越界:', parentIndex);
        uditorPlan.disable(true);
        return false;
      }
      if (subIndex !== undefined) {
        var submenu = model.queryToSecondary(parentIndex, subIndex);
        // 检测子菜单是否越界
        if (!submenu) {
          console.warn('选中菜单失败, 子菜单索引越界:', subIndex);
          uditorPlan.disable(true);
          return false;
        }
        model.current = [parentIndex, subIndex];
        uditorPlan.sync(submenu, parent.sub_button);
      } else {
        model.current = parentIndex;
        uditorPlan.sync(parent, model.data);
      }

      // todo: 控制右侧编辑菜单面板
      //console.warn('选中菜单', model.current, model.data);
      view.render(model.data);
    },
    // 事件-菜单选中
    hander_select: function(event) {
      var menu = event.data['menu'];
      var issub = event.data['issub'];
      var index = event.data['index'];
      var parentIndex = event.data['parentIndex'];


      if (issub) {
        view.select(parentIndex, index);
      } else {
        view.select(index);
      }

      // console.log('选中', menu, issub);
      event.stopPropagation();
    },
    // 事件-菜单顺序改变
    hander_swap: function(event) {
      var option = utils.getOptionForIndex($(this));
      var index = parseInt(option.val());
      var current = model.current;
      var menu = model.getCurrent();
      var list = null;

      // 判断主菜单还是子菜单
      if (!utils.isArray(current)) {
        // 交换主菜单
        list = model.data;
        model.swapToPrimary(current, index);
        model.current = index;
      } else {
        // 交换子菜单
        list = model.queryToPrimary(current[0]).sub_button;
        model.swapToSecondary(current[0], current[1], index);
        model.current[1] = index;
      }
      // 重新渲染视图
      view.render(model.data);
      // 重新渲染菜单顺序select
      uditorPlan.updateSelect(menu, list);

    },
    // 事件-菜单类型改变
    hander_type: function(event) {

      var type = this.value;

      // 改变类型默认填值
      var toMenu = {
        type: type
      }

      if (type == 'click') {
        toMenu['key'] = '填写key';
      } else if (type == 'view') {
        toMenu['url'] = '填写url';
      }

      var currentMenu = null;
      var parent = null;
      if (!utils.isArray(model.current)) {
        // 主菜单类型改变
        parent = model.data;
        // 更改类型
        model.modifyToPrimary(model.current, toMenu);
        currentMenu = model.queryToPrimary(model.current);
      } else {
        // 子菜单类型改变
        parent = (model.queryToPrimary(model.current[0])).sub_button;
        model.modifyToSecondary(model.current[0], model.current[1], toMenu);
        currentMenu = model.queryToSecondary(model.current[0], model.current[1]);
      }

      // 重新渲染视图
      view.render(model.data);
      // 同步右侧面板
      uditorPlan.sync(currentMenu, parent);
    },
    // 事件-新建菜单
    hander_new: function(event) {
      var issub = event.data['issub'];
      var index = event.data['index'];
      var parentIndex = event.data['parentIndex'];

      if (issub) {
        // 创建子菜单
        model.addToSecondary(parentIndex);
      } else {
        // 创建主菜单
        model.addToPrimary();
      }

    },
  };

  // 类型对应输入框, 可以 '#menu-key, #menu-id' 多个选中
  var typeMappin = {
    // click类型菜单显示 #menu-key输入框
    click: {
      // input 选择器
      selector: '#menu-key',
      // click类型填值器
      fillVal: function(menu, list) {
        $(typeMappin.click.selector).val(menu.key);
      }
    },
    // view类型菜单显示 #menu-url输入框
    view: {
      selector: '#menu-url',
      fillVal: function(menu, list) {
        $(typeMappin.view.selector).val(menu.url);
      }
    }
  };

  // 菜单编辑器面板
  var uditorPlan = {
    // 编辑面板容器
    container: null,
    // 绑定编辑面板
    bind: function(container) {
      uditorPlan.container = container;
    },
    // 禁用编辑面板
    disable: function(disabled) {
      var container = uditorPlan.container;
      if (disabled) {
        $('.disabled-bg', container).addClass('active');
      } else {
        $('.disabled-bg', container).removeClass('active');
      }
      return this;
    },
    // 菜单数据同步到编辑面板
    sync: function(menu, list) {
      var container = uditorPlan.container;
      uditorPlan.disable(false);

      // todo 如果menu是二级菜单, 则禁用菜单类型下拉列表中的目录
      if (utils.isArray(model.current)) {
        // 显示菜单类型中的目录option
        $('#menu-type option').eq(0).attr('disabled', 'disabled');
      } else {
        $('#menu-type option').eq(0).removeAttr('disabled');
      }


      // 根据菜单类型显示对应输入框
      if (verification.checkDirMenu(menu, true)) {
        // 隐藏url,key字段对应的输入框
        uditorPlan.hold();
        // 填type值
        $('#menu-type', container).val('');
        // 显示菜单类型中的目录option
        //$('#menu-type option').eq(0).removeAttr('disabled');
      } else {
        // 将类型映射到输入框
        var inputMappin = typeMappin[menu.type];
        if (inputMappin === undefined) {
          console.warn('uditorPlan.sync 同步数据失败, 菜单数据类型不支持!', menu, menu.type);
          return false;
        }
        // 显示对应输入框
        uditorPlan.hold($(inputMappin.selector));
        // 进行填值
        inputMappin.fillVal(menu, list);
        // 填type值
        $('#menu-type', container).val(menu.type);
        // 去掉菜单类型中的目录option
        //$('#menu-type option').eq(0).attr('disabled', 'disabled');
      }

      // 填写name,index字段的值
      $('#menu-name', container).val(menu.name);
      uditorPlan.updateSelect(menu, list);
    },
    // 更新菜单顺序
    updateSelect: function(menu, list) {
      var container = uditorPlan.container;
      var select = $('#menu-index', container);
      select.html(uditorPlan.options(list));
      // 设置当前菜单为select选中
      var currentIndex = select.val();
      parent = model.getParent(list);
      
      if (verification.checkDirMenu(parent, true)) {
        parent = parent.sub_button;
      }
      parent.forEach(function(_menu, i) {
        if (_menu === menu) {
          currentIndex = i;
        }
      });
      // .selectbox()
      select.val(currentIndex);
    },
    // 根据输入框获取所在组
    get: function(input) {
      return input.parents('.frm-group');
    },
    // 根据元素组显示仅保留的输入框组
    hold: function(inputs) {
      inputs = inputs || $();
      var container = uditorPlan.container;
      // 默认保留 name和菜单顺序2个必填组
      inputs = inputs.add($('#menu-name, #menu-index, #menu-type'));
      $('.frm-group', container).hide();
      uditorPlan.get(inputs).show();
    },
    // 根据菜单列表返回options
    options: function(list) {
      var options = $();
      list.forEach(function(menu, i) {
        var html = '<option value="{{index}}">{{name}}</option>';
        var option = $(utils.tpl(html, {name: menu.name, index: i}));
        options = options.add(option);
      });
      return options;
    },
    // 显示错误提示
    showError: function(msg) {
      $('.frm-error').show();
      $('.fai-reasonl').text(msg);
    },
    // 控制-删除当前菜单
    remove: function() {
      var current = model.current;
      if (current === null || current === undefined)
        return;
      
      if (utils.isArray(current)) {
        // 删除子菜单
        model.delToSecondary(current[0], current[1]);
      } else {
        // 删除主菜单
        model.delToPrimary(current);
      }



    },
    // 控制-修改当前菜单
    modify: function() {
      var current = model.current;
      if (current === null || current === undefined)
        return;

      // 当前菜单是否为子菜单
      var issub = utils.isArray(current);
      // 获取当前菜单
      var currentMenu = model.getCurrent();

      // 将当前编辑面板中数据导出为menu菜单数据
      var menu = {};
      var name = $('#menu-name').val();
      var maxLen = issub ? model.MaxSubMenuNameLen : model.MaxMenuNameLen;
      if (name.length <= 0 || name.length > maxLen) {
        uditorPlan.showError('菜单名称长度必须为1~' + maxLen + '个汉字');
        return;
      }
      menu.name = name;

      // view类型检测
      var type = currentMenu.type;
      if (type === 'view') {
        menu.type = type;
        var url = $('#menu-url').val();
        if (url.length <= 0 || url.length > 128) {
          uditorPlan.showError('菜单链接长度必须为1~' + 128 + '个字母');
          return;
        }
        menu.url = url;
      }
      if (type === 'click') {
        menu.type = type;
        var key = $('#menu-key').val();
        if (key.length <= 0 || key.length > 128) {
          uditorPlan.showError('菜单key长度必须为1~' + 128 + '个字母');
          return;
        }
        menu.key = key;
      }


      if (issub) {
        // 修改子菜单
        model.modifyToSecondary(current[0], current[1], menu);
      } else {
        // 修改主菜单
        model.modifyToPrimary(current, menu);
      }
      $('.frm-error').hide();
      alert('修改成功');

    },
    // 控制-保存菜单
    save: function() {
      var data = model.toAll();
      if (utils.isFunction(wxMenu.saveFn)) {
        wxMenu.saveFn(data);
      }
    },
  };


  var wxMenu = {
    saveFn: null,
    init: function(menuBox, editorPlan) {
      uditorPlan.bind(editorPlan);
      view.bind(menuBox);
    },
    data: function(data) {
      model.data = data;
    },
    bindSave: function(fn) {
      wxMenu.saveFn = fn;
    }
  };


  window.wxMenu = wxMenu;





})();
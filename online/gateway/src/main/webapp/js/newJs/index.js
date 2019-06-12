'use strict';

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

/// <reference path="E:\DefinitelyTyped\jquery\jquery.d.ts" />


/**
 * 左侧导航栏管理
 */
var navigationManage = function navigationManage() {
    var nav = $('#nav-bar');
    var activeClass = 'active';
    var onClass = 'on';
    var item = $('.on', nav);
    var active = $('.active', nav);

    $('a', nav).click(function () {

        var node = $(this);

        // 折叠文件夹
        if (node.next().hasClass('sub-list')) {
            if (active) {
                active.removeClass(activeClass);
                if (active[0] == node.parent()[0]) {
                    active = null;
                    return;
                }
            }

            active = node.parent().addClass(activeClass);
            return;
        }

        // 选择菜单项
        if (item) {
            item.removeClass(onClass);
        }
        item = node.addClass(onClass);

        // 创建对应标签
        var TabManage = window.TabManage;
        if (TabManage) {
            TabManage.Create(item.attr('data-url'), item.text());
        }
    });
};

/**
 * 选项卡管理
 */
var tabsManage = function tabsManage() {

    //tab顶级容器
    var tabsWrap = $('#tabs-wrap');
    // tab容器
    var tab_box = $('#tabs-size');
    // tab选择类
    var select_class = 'selected';
    // tab项模版
    var tab_template = '<li><a class="tab-inner ellipsis" href="javascript:void(0)"><span class="title">_Title_</span><span class="close"></span></a></li>';
    // tab的index属性
    var tab_attr = 'data-Index';
    // iframe容器
    var iframe_box = $('#iframe-box');
    // iframe选择类
    var iframe_class = 'on';
    // iframe模版
    var iframe_template = ' <iframe src="_Url_" frameborder="0"></iframe>';
    // 加载图片
    var loadIMG = $('#load-img');

    // tab数组
    var tabs = [];
    // 当前tab索引
    var tab_index;

    /**
     * 创建tab选项卡
     */
    function Create(url, title) {

        if (!url || url === '' || !title || title === '') {
            return;
        }

        // 对应url存在, 则切换tab
        for (var i = 0; i < tabs.length; ++i) {
            if (tabs[i].url === url) {
                Select(i);
                return;
            }
        }

        // 创建tab标签元素
        var tab = $(tab_template.replace('_Title_', title));
        tab.attr(tab_attr, tabs.length);
        // 创建对应 iframe
        var iframe = $(iframe_template.replace('_Url_', url));

        // 加入dom
        tab_box.append(tab);
        iframe_box.append(iframe);

        // 加入数组
        tabs.push({
            'tab': tab,
            'url': url,
            'title': title,
            'iframe': iframe,
            'isload': false
        });

        // 缓存当前索引
        var cache_index = tabs.length - 1;

        // 防止快速创建标签, 导致过度动画跟不上
        loadIMG.stop(true, true);

        // 处理第一个标签的特殊样式
        if (tabs.length === 1) {
            tab.addClass('tab-first');
        }
        // 激活当前创建标签
        Select(tabs.length - 1);
        // 监听载入事件
        iframe.bind('load', function () {
            tabs[cache_index].isload = true;
            loadIMG.fadeOut();
        });
        // 处理标签溢出
        tab_overflow_Handler(true);
    }

    /**
     * 选择tab选项卡
     */
    function Select(index) {

        index = parseInt(index);
        if (isNaN(index) || index === null) {
            return;
        }
        if (index < 0 || index >= tabs.length) {
            return;
        }

        // 移除上一个激活样式
        if (tab_index >= 0 && tab_index < tabs.length) {
            tabs[tab_index].tab.removeClass(select_class);
            tabs[tab_index].iframe.removeClass(iframe_class).fadeOut();
        }

        // 显示加载等待图片
        if (!tabs[index].isload) {
            loadIMG.fadeIn();
        }

        // 添加激活样式
        tabs[index].tab.addClass(select_class);
        tabs[index].iframe.addClass(iframe_class).fadeIn();
        // 设置当前索引
        tab_index = index;
    }

    /**
     * 销毁tab选项卡
     */
    function Destroy(index) {

        index = parseInt(index);
        if (isNaN(index) || index === null) {
            return;
        }
        if (index <= 0 || index >= tabs.length) {
            return;
        }

        var tab = tabs[index].tab;
        var iframe = tabs[index].iframe;

        // 计算删除后当前索引
        var tab_index_result = tab_index;

        // 要删除标签页在激活标签页前面
        if (index < tab_index) {
            tab_index_result = tab_index - 1;
        }
        // 要删除标签页等于激活标签页
        if (index === tab_index && index === tabs.length - 1) {
            tab_index_result = index - 1;
        }

        // 从数组中删除
        tabs.splice(index, 1);
        // 从dom中删除
        tab.remove();
        iframe.remove();

        // 刷新tab列表的索引
        Set_IndexAttr();

        tab_index = undefined;
        // 重新激活选项卡
        Select(tab_index_result);
        // 处理选项卡溢出
        tab_overflow_Handler();
    }

    /**
     * 刷新选项卡
     */
    function Flush(index) {
        index = parseInt(index);
        if (isNaN(index) || index === null) {
            return;
        }
        if (index < 0 || index >= tabs.length) {
            return;
        }

        tabs[index].iframe.attr('src', tabs[index].url);
        //tabs[index].iframe[0].contentWindow.location.reload(true);
    }

    /**
     * 关闭其他标签
     */
    function Destroy_Other(index) {
        // 0 和 index 不会删除,
        // 之后重新激活 index 样式

        index = parseInt(index);
        if (isNaN(index) || index === null) {
            return;
        }
        if (index < 0 || index >= tabs.length) {
            return;
        }

        // 从dom中删除
        for (var i = 1; i < tabs.length; ++i) {
            if (i === index) {
                continue;
            }
            tabs[i].tab.remove();
            tabs[i].iframe.remove();
        }
        // 从数组中删除
        if (index === 0) {
            tabs = [tabs[0]];
        } else {
            tabs = [tabs[0], tabs[index]];
        }

        // 刷新tab列表的索引
        Set_IndexAttr();
        // 重新激活选项卡
        Select(tabs.length === 1 ? 0 : 1);
        // 处理选项卡溢出
        tab_overflow_Handler();
    }

    /**
     * 设置tab列表中的索引
     */
    function Set_IndexAttr() {
        for (var i = 0; i < tabs.length; ++i) {
            tabs[i].tab.attr(tab_attr, i);
        }
    }

    // 左箭头-默认隐藏
    var arrow_Left = $('#arrow-left');
    // 又箭头-默认隐藏
    var arrow_Right = $('#arrow-right');
    // 箭头宽度
    var arrow_width = arrow_Left.outerWidth() + 4;
    // 箭头容器
    var arrow_box = $('#arrow-box').hide();
    // 是否显示箭头
    var isShow = false;
    // 溢出长度
    var over_len;
    // 单次滚动距离
    var scroll_distance = 200;

    /**
     * 检测tab选项卡是否溢出
     */
    function tab_overflow_Handler(toEnd) {

        // 溢出长度
        over_len = tab_box.outerWidth() - tabsWrap.outerWidth();

        if (over_len > 0) {
            // 显示tab箭头
            if (!isShow) {
                arrow_box.fadeIn();
                tab_box.css({ 'margin': '0 ' + arrow_width + 'px' });
                isShow = true;
            }

            // 新建选项卡时, 自动滚动到尾部
            if (toEnd) {
                tab_box.animate({ 'left': '-' + (over_len + arrow_width * 2) });
            }
        } else {
            // 隐藏tab箭头
            if (isShow) {
                arrow_box.fadeOut();
                tab_box.css({ 'margin': '0', 'left': 0 });
                isShow = false;
            }
        }
    }

    // 监听窗口尺寸变化事件
    $(window).resize(throttle(function () {
        tab_overflow_Handler();
    }, 30));

    arrow_Left.click(function () {

        var next_left = tab_box.position().left + scroll_distance;

        if (next_left > 0) {
            tab_box.animate({ 'left': '0' });
        } else {
            tab_box.animate({ 'left': next_left });
        }
    });

    arrow_Right.click(function () {

        var next_left = tab_box.position().left - scroll_distance;

        if (next_left < -(over_len + arrow_width * 2)) {
            console.log('切换到头', next_left);
            tab_box.animate({ 'left': -(over_len + arrow_width * 2) });
        } else {
            console.log('切换一个步长', next_left);
            tab_box.animate({ 'left': next_left });
        }
    });

    var TabManage = {
        /**
         * 创建一个选项卡
         */
        'Create': Create,
        /**
         * 销毁一个选项卡
         */
        'Destroy': Destroy,
        /**
         * 选择一个选项卡
         */
        'Select': Select,
        /**
         * 刷新选项卡
         */
        'Flush': Flush,
        /**
         * 关闭其他选项卡
         */
        'Destroy_Other': Destroy_Other,
        /**
         * 检测选项卡溢出
         */
        'tab_overflow_Handler': tab_overflow_Handler
    };

    window.TabManage = TabManage;

    // tab选项卡事件
    tab_box.click(function (event) {

        var select = event.target.nodeName === 'LI' ? $(event.target) : $(event.target).parents('li');
        var index = select.attr(tab_attr);

        if ($(event.target).hasClass('close')) {
            Destroy(index);
        } else {
            Select(index);
        }
    });

    // 创建默认标签页
    Create('html/account/index.html', '首页');
};

/**
 * 弹出窗口
 */
var popWindow = function popWindow() {

    // 结果弹出框模板
    var rp_template = '\n    <div class="global-full">\n      <div class="mask"></div>\n      <div class="container">\n        <span class="middle"></span>\n        <div class="box-out ib">\n          <div class="close close-btn"></div>\n          <div class="box-in">\n            <iframe src="" frameborder="0"></iframe>\n          </div>\n        </div>\n      </div>\n    </div>\n  ';

    // 内容弹出框模板
    var wd_template = '\n    <div class="global-wd">\n      <div class="mask"></div>\n      <div class="container">\n        <span class="middle"></span>\n        <div class="box-out ib">\n          <div class="box-in"">\n            <div class="head">\n              <p class="t1 title"></p>\n              <div class="close-2 close-btn"></div>\n            </div>\n            <div class="body">\n              <iframe src="" frameborder="0"></iframe>\n            </div>\n            <div class="foot">\n              <button class="mr-10 btn-2 btn-2-size-30 reverse close-btn ">\u53D6\u6D88</button>\n              <button class="mr-10 btn-2 btn-2-size-30">\u786E\u5B9A</button>\n            </div>\n          </div>\n        </div>\n      </div>\n    </div>\n  ';

    // z-index基数
    var zindex = 90;
    // 弹出框计数
    var count = 0;

    /**
     * 结果弹出框
     */

    var ResultPop = function () {

        /**
         * 构造函数
         * @param id  创建窗口的id
         * @param url 框架地址
         * @param mask 是否增加遮罩
         */
        function ResultPop(url, mask) {
            _classCallCheck(this, ResultPop);

            // 通过模板创建dom
            this.element = $(rp_template).css({ 'z-index': zindex + count }).hide();
            // 关闭按钮
            this.DOM_close = $('.close-btn', this.element);
            // 框架
            this.DOM_iframe = $('iframe', this.element);
            // 遮罩
            this.DOM_mask = $('.mask', this.element);
            // 处理不显示遮罩
            if (!mask) {
                this.DOM_mask.remove();
            }
            // 加入dom
            $(document.body).append(this.element);
            this.DOM_iframe.attr('src', url);
            this.element.fadeIn();
            // 增加计数
            ++count;
            // 绑定事件
            this.bind();
        }

        /**
         * bind
         */


        _createClass(ResultPop, [{
            key: 'bind',
            value: function bind() {
                var DOM_close = this.DOM_close,
                    DOM_mask = this.DOM_mask,
                    element = this.element;

                DOM_close.click(function () {
                    element.fadeOut('normal', function () {
                        element.remove();
                    });
                });
            }
        }]);

        return ResultPop;
    }();

    /**
     * 弹出框窗口
     */


    var WindowPop = function () {
        function WindowPop(title, url, mask) {
            _classCallCheck(this, WindowPop);

            // 通过模板创建dom
            this.element = $(wd_template).css({ 'z-index': zindex + count }).hide();
            // 关闭按钮
            this.DOM_close = $('.close-btn', this.element);
            // head
            this.DOM_head = $('.head', this.element);
            // 框架
            this.DOM_iframe = $('iframe', this.element);
            var DOM_iframe = this.DOM_iframe;
            // 遮罩
            this.DOM_mask = $('.mask', this.element);
            // 标题
            this.DOM_title = $('.title', this.element).text(title);
            // 页脚
            this.DOM_foot = $('.foot', this.element);
            // 处理不显示遮罩
            if (!mask) {
                this.DOM_mask.remove();
            }

            DOM_iframe.bind('load', function () {
                // 获取框架内宽度
                DOM_iframe.css({ height: DOM_iframe.contents().find('.in-pop').outerHeight(), width: DOM_iframe.contents().find('.in-pop').outerWidth() });
            });

            // 加入dom
            $(document.body).append(this.element);
            this.DOM_iframe.attr('src', url);
            this.element.fadeIn();
            // 增加计数
            ++count;
            // 绑定事件
            this.bind();
        }

        /**
         * 设置页脚按钮
         */


        _createClass(WindowPop, [{
            key: 'setFoot',
            value: function setFoot(data) {

                /**
                 * data = [
                 *  {title: '标题', reverse: false, close: false}
                 * ];
                 */

                var DOM_foot = this.DOM_foot;


                data.forEach(function (value) {
                    DOM_foot.html($('<button class="mr-10 btn-2 btn-2-size-30 ' + (value.close ? 'close-btn' : '') + '  ' + (value.reverse ? 'reverse' : '') + ' ">' + value.title + '</button>'));
                });
            }

            /**
             * bind
             */

        }, {
            key: 'bind',
            value: function bind() {
                var DOM_close = this.DOM_close,
                    DOM_mask = this.DOM_mask,
                    element = this.element,
                    DOM_head = this.DOM_head;

                DOM_close.click(function () {
                    element.fadeOut('normal', function () {
                        element.remove();
                    });
                });

                var box = $('.box-out', element);

                var pos = box.position();
                var mouseX = void 0;
                var mouseY = void 0;

                var moveWindow = function moveWindow(_event) {
                    var offsetX = _event.pageX - mouseX;
                    var offsetY = _event.pageY - mouseY;

                    box.css({
                        'position': 'absolute',
                        'left': offsetX + pos.left,
                        'top': offsetY + pos.top
                    });
                };

                //const _tm = throttle(moveWindow, 300);


                // 拖拽
                DOM_head.mousedown(function (event) {

                    pos = box.position();
                    mouseX = event.pageX;
                    mouseY = event.pageY;
                    DOM_head.mousemove(moveWindow);
                });

                element.mouseup(function () {
                    DOM_head.unbind('mousemove', moveWindow);
                });
            }
        }]);

        return WindowPop;
    }();

    window.WindowPop = WindowPop;
    window.ResultPop = ResultPop;
};

navigationManage();
tabsManage();
popWindow();

{
    (function () {
        var payway = $('#payway-tab');
        var bankc = $('#bankc');
        var acclass = 'active';
        var prev = $('.' + acclass, payway);

        $('.tab', payway).click(function () {
            if (prev) {
                prev.removeClass(acclass);
            }
            prev = $(this).addClass(acclass);

            var index = prev.attr('data-index');
            $(index, bankc).addClass('on').siblings().removeClass('on');
        });
    })();
}
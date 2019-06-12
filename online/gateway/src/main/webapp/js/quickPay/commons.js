var zhupanCommon = (function ($) {
    return {
    	/*检测dom是否存在*/
        nonDom: function (obj) {
            return obj.length == 0;
        },
        /*移除dom操作*/
        removeDom: function (obj) {
            if (!zhupanCommon.nonDom(obj)) {
                obj.remove();
            }
        },
        iHoverFun:function(elem, className) {
            elem.focus(function() {
                $(this).parent().addClass(className);
                $(this).parent().parent().addClass(className);
                $(this).prev().addClass(className);
            })
            elem.blur(function() {
                $(this).parent().removeClass(className);
                $(this).parent().parent().removeClass(className);
                $(this).prev().removeClass(className);
            });
        },
        /**
         * 错误提示框
         * */
        createErrorTip: function (target, msg) {
            var offset = target.offset();// 获取目标的定位坐标，这是个对象
            var className = 'formTip';
            var domElem = $('.' + className);
            var dom = $('<div class="' + className + '"></div>');
            domInner = '<i class="formTipArrowTriangle"></i>'; // 创建提示层内部结构，小三角
            domInner += '<i class="formTipArrow formTipArrowLeft"></i>'; // 提示层左侧
            domInner += '<em class="formTipArrow">' + msg + '</em>'; // 提示信息在这
            domInner += '<i class="formTipArrow formTipArrowRight"></i>'; // 提示层右侧
            dom.append(domInner); // 将提示层内部对象添加到提示层中
            dom.css({ // 设置提示层的css为绝对定位，它相对它的目标target参数进行定位
                "position": "absolute",
                "top": offset.top + 30,
                "left": offset.left + 5,
                "z-index": 100000 // 底层css原因，所以它的层叠顺序为4
            });
            if (zhupanCommon.nonDom(domElem)) {
                $("body").append(dom);
            }
            return dom;
        },
        /**
         * beforeSend提示框
         * */
        createBeforeTip: function (target, cssClass, msg) {
            var domElem = $('.' + cssClass),
                modal = $('<span class="cf modal ' + cssClass + '"></span>'),
                modalChild = $('<i></i><span>' + msg + '</span>');
            modal.append(modalChild);
            if (zhupanCommon.nonDom(domElem)) {
                target.append(modal);
            }
            return modal;
        },
        /**
         * confirm提示框
         * */
        createConfirmTip: function(target,cssClass,  msg, okFun){
            var domElem = $('.' + cssClass),
                modal = $('<div class="'+cssClass+'"><i></i><i class="y"></i></div>'),
                modalChild = $('<div class="deleteModalInfo cf">' + msg + '</div>'),
                btn = $('<button id="ok">确定</button><button id="cancle">取消</button>');
            rootPosition = target.position();
            modal.append(modalChild);
            modal.append(btn);
            zhupanCommon.removeDom(domElem);
            target.append(modal);
            /*监听*/
            modal.delegate('#ok', 'click', function (e) {
                okFun();
                target.find('.' + cssClass).remove();
                return false;
            });
            modal.delegate('#cancle', 'click', function () {
                target.find('.' + cssClass).remove();
                return false;
            });
        }
    }
})(jQuery)
        
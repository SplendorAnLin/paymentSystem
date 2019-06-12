(function (doc, win) {
    var docEl = doc.documentElement, // 文档根节点
        evt = 'orientationchange' in window ? 'orientationchange' : 'resize', // 如果你当前的设备支持方向改变事件就给他这个事件，否则就用resize事件
        recalc = function () {
            var clientWidth = docEl.clientWidth;
            if (!clientWidth) return;
            if(clientWidth>=640){
                docEl.style.fontSize = '100px';
            }else{
                docEl.style.fontSize = 100 * (clientWidth / 640) + 'px';
            }
        };
    if (!doc.addEventListener) return;
    win.addEventListener(evt, recalc, false);
    doc.addEventListener('DOMContentLoaded', recalc, false);
})(document, window);
(function () {

  // 是否为函数
  function isFunction(array) {
    return Object.prototype.toString.call(array) === '[object Function]';
  }

  // 数组中书否存在此值
  function includes(array, value) {
    var exist = false;
    for (var i = 0; i < array.length; ++i) {
      if (array[i] === value)
        exist = true;
        break;
    }
    return exist;
  }

  // 拖拽元素集合
  var drags = [];
  // 当前拖拽元素
  var current = null;
  var originX = 0;
  var originY = 0;
  var left = 0;
  var top = 0;

  var Drag = {
    add: function (element, xfilter) {
      if (includes(drags, element))
        return;
      drags.push(element);
      // $(element).bind('mousedown', mousedown);
      element.addEventListener('mousedown', mousedown);
      element.xfilter = xfilter;
    },
    remove: function (element) {
      var index = drags.indexOf(element);
      if (index !== -1) {
        drags.splice(index, 1);
        // $(element).unbind('mousedown', mousedown);
        element.removeEventListener('mousedown', mousedown);
      }
    },
    current: function() {
      return current;
    }
  };

  function mousedown(event) {
    if (event.button !== 0)
      return;

    var element = event.currentTarget;
    if (isFunction(element.xfilter)) {
      // 过滤器不通过
      if (!element.xfilter(event.currentTarget, event.target)) {
        return;
      }
    }
    current = event.currentTarget;

    originX = event.clientX;
    originY = event.clientY;
    left = isNaN(parseFloat(current.style.left)) ? 0 : parseFloat(current.style.left);
    top = isNaN(parseFloat(current.style.top)) ? 0 : parseFloat(current.style.top);
  }

  function mouseup() {
    if (current == null)
      return;
    current = null;
  }

  function mousemove(event) {
    if (current == null)
      return;

    var offsetX = event.clientX - originX;
    var offsetY = event.clientY - originY;
    current.style.left = (left + offsetX) + 'px';
    current.style.top = (top + offsetY) + 'px';
  }

  window.addEventListener('load', function (event) {
    document.addEventListener('mouseup', mouseup);
    document.addEventListener('mousemove', mousemove);
  });
  window.Drag = Drag;
})();

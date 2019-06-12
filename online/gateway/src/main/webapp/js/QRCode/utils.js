/**
 * @description 判断一个对象是否为函数
 * @param fn {function}
 * @returns {boolean}
 */
function isFunction(fn) {
  return Object.prototype.toString.call(fn) === '[object Function]';
}

/**
 * @description 判断一个对象是否为jQuery对象
 * @param jq {function}
 * @returns {boolean}
 */
function isJquery(jq) {
  // jq instanceof jQuery
  if (jq == null) {
    return false;
  }
  return jq['jquery'] !== undefined;
}

/**
 * 模板替换
 * @param tpls {string}  模板
 * @param data {Object} 数据
 * @return {string}     拼接后的模板
 */
function tpl(tpls, data) {
  return tpls.replace(/{{(.*?)}}/g, function($1, $2) {
    return data[$2];
  });
}

/**
 * 寻找框架内元素
 * @param iframe {jQuery}  框架对象
 * @param selector {string}  选择器
 * @returns {jQuery}
 */
function iframeFind(iframe, selector) {
  return iframe.contents().find(selector);
}


/**
 * 范围随机数
 * @param {Number} minnum 最小值
 * @param {Number} maxnum 最大值
 */
function rand(minnum , maxnum){
  return Math.floor(minnum + Math.random() * (maxnum - minnum));
}







/**
 * 加载图片
 * @param url {string}  图片地址
 * @param callback {function}  回调函数(含一个status参数)
 */
function loadImage(url, callback) {
  var img = new Image();
  var count = 0;
  img.src = url;
  if (img.complete) {
    callback(img, true);
    return;
  }
  img.onload = function() {
    if (count !== 0) {
      return;
    }
    callback(img, true);
    ++count;
  };
  img.onerror = function() {
    callback(img, false);
  };
}

/**
 * 去除前后空格
 * @param {string} str 
 */
function trim(str) {
  return str.replace(/(^\s*)|(\s*$)/g, '');
}

/**
 * 判断是否iphone设备
 */
function isIphone() {
  var UA = navigator.userAgent;
  return (UA.match(/iPad/) || UA.match(/iPhone/) || UA.match(/iPod/));
}

/**
 * 获取dpr
 */
 function getDPR() {
   var dpr;
      var u = (window.navigator.appVersion.match(/android/gi),  window.navigator.appVersion.match(/iphone/gi)),
          _dpr = window.devicePixelRatio;
 
        // 所以这里似乎是将所有 Android 设备都设置为 1 了
        dpr = u ? ( (_dpr >= 3 && (!dpr || dpr >= 3))
                        ? 3
                        : (_dpr >= 2 && (!dpr || dpr >= 2))
                            ? 2
                            : 1
                  )
                : 1;
      return dpr;
 }


/**
 * 数组转options
 */
function arrayToSelect(array, labelName, valueName) {
  var options = $();
  $.each(array, function() {
    var option = $('<option value="' + this[valueName] + '">' + this[labelName] + '</option>');
    options = options.add(option);
  });
  return options;
};


var Ajax = {
  // monopoliize函数是否空闲
  monopoliIdle: {},
  // 贪婪计数
  greedyCount: {},
  /**
   * @description 参数验证
   * @param args {object}
   * @returns {boolean} 验证成功=true
   */
  premise: function(args) {
    if (!args || !args.url) {
      return false;
    }
    return true;
  },
  /**
   * @description 独占型(第一个ajax没有返回, 后面的ajax无法成功提交)
   * @param args {object}
   * @returns {boolean} 参数不合法或没有空闲状态则返回false
   */
  monopoliize: function(args) {
    // 这里缓存地址, 但是不要缓存参数, 否则基本就没起到作用
    var result = args.url.match(/^.*\\?/);
    var realurl = result.length > 0 ? result[0] : args.url;
    if (!Ajax.premise(args) || Ajax.monopoliIdle[realurl] === false) {
      return false;
    }
    // 设置非空闲状态
    Ajax.monopoliIdle[realurl] = false;
    $.ajax({
      url: args.url,
      async: args.async || true,
      cache: args.cache || true,
      data: args.data || {},
      type: args.type || 'get',
      dataType: args.dataType,
      error: function() {
        // 设置空闲状态
        Ajax.monopoliIdle[realurl] = true;
        if (isFunction(args.error)) {
          args.error();
        }
      },
      success: function(data) {
        // 设置空闲状态
        Ajax.monopoliIdle[realurl] = true;
        if (isFunction(args.success)) {
          args.success(data);
        }
      },
    });
    return true;
  },
  /**
   * @description 贪婪型(无限提交, 以最后一个为准)
   * @param args {object}
   * @returns {boolean}
   */
  greedy: function(args) {
    if (!Ajax.premise(args)) {
      return false;
    }
    // 这里缓存地址, 但是不要缓存参数, 否则基本就没起到作用
    var result = args.url.match(/^.*\\?/);
    var realurl = result.length > 0 ? result[0] : args.url;
    if (!Ajax.greedyCount[realurl]) {
      // 初始化计数
      Ajax.greedyCount[realurl] = 1;
    } else {
      // 累加一次提交计数
      Ajax.greedyCount[realurl] += 1;
    }
    // 缓存上一次计数
    var count = Ajax.greedyCount[realurl];
    // 是否最后一次提交
    function isLast() {
      if (count === Ajax.greedyCount[realurl]) {
        // 成功则清空计数
        Ajax.greedyCount[realurl] = undefined;
        return true;
      }
      return false;
    }
    
    
    $.ajax({
      url: args.url,
      async: args.async || true,
      cache: false,
      data: args.data || {},
      type: args.type || 'get',
      dataType: args.dataType,
      error: function() {
        if (isLast() && isFunction(args.error)) {
          args.error();
        }
      },
      success: function(data) {
        if (isLast() && isFunction(args.success)) {
          args.success(data);
        }
      },
    });
  },
};




// 取url获参数值
function getQueStr(url, ref) {
  var str = url.substr(url.indexOf('?') + 1);
  if (str.indexOf('&') !== -1) {
    var arr = str.split('&');
    for (var i in arr) {
      if (arr[i].split('=')[0] === ref) {
        return arr[i].split('=')[1];
      }
    }
  } else {
    return url.substr(url.indexOf('=') + 1);
  }
}

// 设置url参数值
function setQueStr(url, ref, value) {
  var str = '';
  if (url.indexOf('?') !== -1)
    str = url.substr(url.indexOf('?') + 1);
  else
    return url + '?' + ref + '=' + value;
  var returnurl = '';
  var setparam = '';
  var arr;
  var modify = '0';

  if (str.indexOf('&') !== -1) {
    arr = str.split('&');
    for (var i in arr) {
      if (arr[i].split('=')[0] === ref) {
        setparam = value;
        modify = '1';
      } else {
        setparam = arr[i].split('=')[1];
      }
      returnurl = returnurl + arr[i].split('=')[0] + '=' + setparam + '&';
    }
    returnurl = returnurl.substr(0, returnurl.length - 1);
    if (modify === '0')
      if (returnurl === str)
        returnurl = returnurl + '&' + ref + '=' + value;
  } else {
    if (str.indexOf('=') !== -1) {
      arr = str.split('=');

      if (arr[0] === ref) {
        setparam = value;
        modify = '1';
      } else {
        setparam = arr[1];
      }
      returnurl = arr[0] + '=' + setparam;
      if (modify === '0')
        if (returnurl === str)
          returnurl = returnurl + '&' + ref + '=' + value;
    } else {
      returnurl = ref + '=' + value;
    }
  }
  return url.substr(0, url.indexOf('?')) + '?' + returnurl;
}

// 删除参数值
function delQueStr(url, ref) {
  var str = '';
  if (url.indexOf('?') !== -1)
    str = url.substr(url.indexOf('?') + 1);
  else
    return url;
  var arr = '';
  var returnurl = '';
  if (str.indexOf('&') !== -1) {
    arr = str.split('&');
    for (var i in arr) {
      if (arr[i].split('=')[0] !== ref) {
        returnurl = returnurl + arr[i].split('=')[0] + '=' + arr[i].split('=')[1] + '&';
      }
    }
    return url.substr(0, url.indexOf('?')) + '?' + returnurl.substr(0, returnurl.length - 1);
  } else {
    arr = str.split('=');
    if (arr[0] === ref)
      return url.substr(0, url.indexOf('?'));
    else
      return url;
  }
}


window.isFunction = isFunction;
window.isJquery = isJquery;
window.tpl = tpl;
window.iframeFind = iframeFind;
window.isIphone = isIphone;
window.getQueStr = getQueStr;
window.setQueStr = setQueStr;
window.delQueStr = delQueStr;


window.utils = {
  isFunction: isFunction,
  isJquery: isJquery,
  tpl: tpl,
  trim: trim,
  rand: rand,
  getDPR: getDPR,
  iframeFind: iframeFind,
  isIphone: isIphone,
  loadImage: loadImage,
  arrayToSelect: arrayToSelect,
  getQueStr: getQueStr,
  setQueStr: setQueStr,
  delQueStr: delQueStr
};
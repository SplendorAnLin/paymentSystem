(function() {
  var doc = window.document;
  var docEl = doc.documentElement;
  var metaEl = doc.querySelector('meta[name="viewport"]');

  if (!metaEl) {
    var scale = 1;
    var metaEl = doc.createElement('meta');
    metaEl.setAttribute('name', 'viewport');
    metaEl.setAttribute('content', 'initial-scale=' + 1 + ', maximum-scale=' + 1 + ', minimum-scale=' + 1 + ', user-scalable=no');
    var wrap = doc.createElement('div');
    wrap.appendChild(metaEl);
    doc.write(wrap.innerHTML);
  }

})();
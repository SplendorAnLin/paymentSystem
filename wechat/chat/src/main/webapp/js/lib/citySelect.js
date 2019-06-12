(function() {

  $.fn.citySelect = function(option) {
    var options = {
      // 城市数据json
      url: '/js/plugin/city.min.json',
      // 默认省份 主动设为null则所有下拉列表默认都会是请选择
      prov: '北京',
      // 默认市
      city: '东城区',
      // 默认区
      area: null,
      // 没有数据是否只读
      nodata: 'readpnly'
    };
    // 扩展参数
    var opt = $.extend({}, options, option);

    // 获取下拉列表
    var provSelect = $('#prov', this);
    var citySelect = $('#city', this);
    var areaSelect = $('#area', this);


    provSelect.change(function() {
      provChange(this);
    });

    citySelect.change(function() {
      cityChange(this);
    });


    function provChange(selectRef) {
      var index = selectRef.selectedIndex;
      var select = $(selectRef);
      var option = select.find('option').eq(index);

      // 如果option存在
      var citys = option.data('citys');
      if (citys) {
        fillCity(citys);
      } else {
        fillCity(null, null, true);
      }
      cityChange(citySelect.get(0));
    }

    function cityChange(selectRef) {
      var index = selectRef.selectedIndex;
      var select = $(selectRef);
      var option = select.find('option').eq(index);

      // 如果option存在
      var areas = option.data('areas');
      if (areas) {
        fillArea(areas);
      } else {
        fillArea(null, null, true);
      }
    }

    // 填充省份下拉列表
    function fillProv(data, prov) {
      prov = prov || opt.prov;
      var options = prov == null ? $('<option value="">请选择</option>').data('citys', '请选择') : $();
      for (var i = 0; i < data.length; ++i) {
        // data
        var item = data[i];
        var name = item.p;
        var option = $('<option></option>').text(name).attr('value', name);
        option.data('citys', item.c);
        if (name === prov)
          option.get(0).selected = true;
        options = options.add(option);
      }


      provSelect.html(options);
      if (window.SelectBox)
        provSelect.SelectBox()
    }

    // 填充市下拉列表
    function fillCity(data, city, empty) {
      city = city || opt.city;

      // 处理外国没有市的情况
      if (empty) {
        citySelect.html($('<option value="">无</option>'));
        if (window.SelectBox)
          citySelect.SelectBox()
        return;
      }

      if (data == '请选择') {
        citySelect.html($('<option value="">请选择</option>').data('areas', '请选择'));
        if (window.SelectBox)
          citySelect.SelectBox()
        return;
      }

      var options = $();
      for (var i = 0; i < data.length; ++i) {
        // data
        var item = data[i];
        var name = item.n;
        var option = $('<option></option>').text(name).attr('value', name);
        option.data('areas', item.a);
        if (name === prov)
          option.get(0).selected = true;
        options = options.add(option);
      }

      citySelect.html(options);
      if (window.SelectBox)
        citySelect.SelectBox()

    }

    // 填充区下拉列表
    function fillArea(data, area, empty) {
      area = area || opt.area;

      // 处理直辖市没有区的情况
      if (empty) {
        areaSelect.html($('<option value="">无</option>'));
        if (window.SelectBox)
          areaSelect.SelectBox()
        return;
      }

      if (data == '请选择') {
        areaSelect.html($('<option value="">请选择</option>'));
        if (window.SelectBox)
          areaSelect.SelectBox()
        return;
      }

      var options = $();
      for (var i = 0; i < data.length; ++i) {
        var item = data[i];
        var name = item.s;
        var option = $('<option></option>').text(name).attr('value', name);
        if (name === prov)
          option.get(0).selected = true;
        options = options.add(option);
      }

      areaSelect.html(options);
      if (window.SelectBox)
        areaSelect.SelectBox()

    }


    // 初始化
    function init(data) {
      fillProv(data);
      provChange(this);
    }

    // 获取数据
    $.getJSON(opt.url, function(json) { init(json.citylist); });
  };

})();
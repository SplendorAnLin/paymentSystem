/* 字典城市下拉联动 */
(function() {

  $.fn.dictCitySelect = function(opt) {
    var defaultOption = {
      // 默认省
      prov: null,
      // 省份下拉列表是有请选择
      provEmpy: true,
      // 省份下拉列表选择器
      provSelect: '.prov',
      // 默认城
      city: null,
      // 城市下拉列表是有请选择
      cityEmpy: true,
      // 城市下拉列表选择器
      citySelect: '.city',
      // 地区编码输入框选择器
      organization: '.organization'
    };
    var config = $.extend({}, defaultOption, opt);

    // 获取下拉列表
    var provSelect = $(config.provSelect);
    var citySelect = $(config.citySelect);
    var organization = $(config.organization);

    if (provSelect.length == 0 || citySelect.length == 0) {
      console.warn('初始化城市联动组件失败, 未找到省份/城市下拉列表!');
      return;
    } else {
      provSelect.html('<option value="">请选择</option>');
      citySelect.html('<option value="">请选择</option>');
      provSelect.change(changeProv);
      citySelect.change(changeCity);
    }

    // 初始化获取所有省
    api.queryProvList(function(provList) {
      if (provList == null) {
        // Messages.error('获取省份数据失败!, 请检查网络或稍后重试...');
    	  alert('获取省份数据失败!, 请检查网络或稍后重试...');
        return;
      }
      // 过滤状态false的元素
      // provList = utils.filterDisableItem(provList);
      var options = $();
      if (config.provEmpy) {
        options = options.add($('<option value="">请选择</option>'));
      }
      api.each(provList, function(provInfo) {
        var provCode =  provInfo.key.split(".")[1];
        var option = $('<option value="' + provInfo.value + '" data-provcode="' + provCode + '">' + provInfo.value + '</option>');
        if (provInfo.value == config.prov) {
          option[0].selected = true;
          provSelect.val(provInfo.value);
          changeProv(provCode);
        }
        options = options.add(option);
      });
      
      provSelect.html(options);
    });

    // 更改省份
    function changeProv(provcode) {
      var cuttentOption = provSelect.find('option').eq(provSelect[0].selectedIndex);
      var provCode =  cuttentOption.attr('data-provcode') || provcode;
      api.queryCityList(provCode, function(cityList) {
        if (cityList == null) {
          //Messages.error('获取城市数据失败!, 请检查网络或稍后重试...');
          alert('获取城市数据失败!, 请检查网络或稍后重试...');
          return;
        }
        // 过滤状态false的元素
        // cityList = utils.filterDisableItem(cityList);
        var options = $();
        if (config.cityEmpy) {
          options = options.add($('<option value="">请选择</option>'));
        }
        api.each(cityList, function(cityInfo) {
          var cityCode = cityInfo.key.split(".")[1];
          var option = $('<option value="' + cityInfo.value + '" data-citycode="' + cityCode + '">' + cityInfo.value + '</option>');
          if (cityInfo.value == config.city) {
            option[0].selected = true;
            citySelect.val(cityInfo.value);
            changeCity(cityCode);
          }
          options = options.add(option);
        });
        citySelect.html(options);
      });

    }

    // 更改城市
    function changeCity(citycode) {
      var cuttentOption = citySelect.find('option').eq(citySelect[0].selectedIndex);
      var cityCode =  cuttentOption.attr('data-citycode') || citycode;
      organization.val(cityCode.replace('CITY_', ''));
    }

  };

})();
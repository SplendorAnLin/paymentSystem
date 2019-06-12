<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<title>扫码入网</title>
<link rel="stylesheet"
	href="${applicationScope.staticFilesHost}/gateway/css/QRCode/register.css">
<link rel="stylesheet"
	href="${applicationScope.staticFilesHost}/gateway/css/QRCode/Modal.css">
<link rel="stylesheet"
	href="${applicationScope.staticFilesHost}/gateway/css/QRCode/autocomplete.css">
<link rel="stylesheet"
	href="${applicationScope.staticFilesHost}/gateway/css/QRCode/font-awesome.css">
<script
	src="${applicationScope.staticFilesHost}/gateway/js/QRCode/flexible_wap.js"></script>
<style>
.uploadFile {
	position: relative;
	border-radius: 5px;
}

.selected-box {
	display: none;
	text-align: center;
	position: absolute;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	border-radius: 17px;
}

.selected-box i {
	flex: 1;
	align-self: center;
	color: #fa4e4e;
	font-size: 1.5rem;
}
.autocomplete-wrap {
	font-size: 12px;
    line-height: 7px;
}
.uploadImage {
	box-shadow: 0px 0px 5px rgba(0,0,0,0.7);
	border-radius: 10px;
}
.input-row .in.last {
    border-bottom: none;
}
.maskTop {
	display: flex;
	position: absolute;
	left: 0;
	top: 0;
	width: 0;
	height: 0;
	background-color: rgba(0, 0, 0, 0.7);
    text-align: center;
    align-items: center;
    transition: opacity 0.3s;
    opacity: 0;
}
.maskTop.active {
	width: 100%;
	height: 100%;
	opacity: 1;
	z-index: 5;
}
.maskTop .i {
	align-self: center;
}
.load-block {
    width: 50px;
    height: 50px;
    font-size: 1.5em;
    flex: 1;
}


</style>
<script>
 var context = "${applicationScope.staticFilesHost}";
</script>
</head>
<body class="bc-lightGray">
	<div class="mobileFrame">
	
		<div class="maskTop">
			<div class="load-block c-red">
				<i class="fa fa-spinner fa-spin fa-3x fa-fw margin-bottom"></i>
			</div>
		</div>
	
		<div class="nav-warp">
			<ul class="row">
				<li class="col-4"><a href="javascript:void(0);">登陆信息</a></li>
				<li class="col-4"><a href="javascript:void(0);">基本信息</a></li>
				<li class="col-4 active"><a href="javascript:void(0);">结算信息</a></li>
			</ul>
		</div>
		<form id="customerAdd" enctype="multipart/form-data"
			action="${applicationScope.staticFilesHost}/gateway/three"
			method="POST">
			<input type="hidden" name="id" value="${id }"> <input
				type="hidden" id="bankCode" readonly="readonly" class="input-text">
			<select style="display: none;" class="input-select" id="customerType">
				<option value="INDIVIDUAL">对私</option>
				<option value="OPEN">对公</option>
			</select>
			<div class="input-row">
				<div class="in row">
					<label class="col-5">结算卡账户名:</label> <input class="col-7"
						name="accName" id="cardName" type="text" required chinese>
				</div>
			</div>
			<div class="input-row">
				<div class="in row">
					<label class="col-5">结算卡卡号:</label> <input class="col-7"
						name="accNo" id="accountNo" type="number" required>
				</div>
			</div>
			
			<div class="input-row">
				<div class="in row">
					<label class="col-5">分支行信息:</label> 
					<input class="col-7"
						name="bankName" id="openBankName" disabled="disabled"
						value="请输入地区、分行支行名称等关键词进行匹配" type="text" required justWhenSubmit>
				</div>
			</div>
			<div class="input-row">
				<div class="in row last">
					<label class="col-5">上传照片:</label> 
					<span class="col-7" ></span>
				</div>
				<div class="upload-photo row">
				
					<span class="col-5 uploadFile" data-prefix="hid">
						<img class="uploadImage" src="${applicationScope.staticFilesHost}/gateway/image/upload_IdCrad.png">
						<input type="file" required justWhenSubmit acceptIamge>
						<div class="chunks"></div>
						<div class="selected-box">
							<i class="fa fa-spinner fa-spin fa-3x fa-fw margin-bottom"></i>
						</div>
					</span>
					
					<span class="col-2 line"></span> 
					<span class="col-5 uploadFile" data-prefix="hbank">
						<img class="uploadImage" src="${applicationScope.staticFilesHost}/gateway/image/upload_BankCard.png">
						<input type="file" required justWhenSubmit acceptIamge>
						<div class="chunks"></div>
						<div class="selected-box">
							<i class="fa fa-spinner fa-spin fa-3x fa-fw margin-bottom"></i>
						</div>
					</span>
					
				</div>
			</div>
			<div class="btnWrap">
				<button class="btn-full">提交信息</button>
			</div>
			<%--<div class="btnWrap">--%>
				<%--<span class="contact">客服电话: <span>400-860-7199</span></span>--%>
			<%--</div>--%>
		</form>
	</div>
	<script
		src="${applicationScope.staticFilesHost}/gateway/js/QRCode/jquery-1.8.3.js"></script>
	<script
		src="${applicationScope.staticFilesHost}/gateway/js/QRCode/Autocomplete.js"></script>
	<script
		src="${applicationScope.staticFilesHost}/gateway/js/QRCode/utils.js"></script>
	<script
		src="${applicationScope.staticFilesHost}/gateway/js/QRCode/underscore.js"></script>
	<script
		src="${applicationScope.staticFilesHost}/gateway/js/QRCode/verification.js"></script>
	<script
		src="${applicationScope.staticFilesHost}/gateway/js/QRCode/Modal.js"></script>
	<script
		src="${applicationScope.staticFilesHost}/gateway/js/QRCode/register.js"></script>
	<script
		src="${applicationScope.staticFilesHost}/gateway/js/QRCode/API.js"></script>
	<script>
	$(document).ready(function() {
		$('.uploadImage').css('height', $('.uploadFile').height());
	});
	window.onload = function() {
		$('.uploadImage').css('height', $('.uploadFile').height());
	};
	
	var img = new Image();
    img.onload = function() {
    	$('.uploadImage').css('height', $('.uploadFile').height());
    };
    img.src = '${applicationScope.staticFilesHost}/gateway/image/upload_IdCrad.png';
	
	
	
	$('.uploadImage').css('height', $('.uploadFile').height());
	$('[type="file"]').change(function() {
		
		var load = $(this).parent().find('.selected-box');
		var img = $(this).parent().find('.uploadImage');
		var uploadFile = $(this).parent();
		var chunks = $(this).parent().find('.chunks');
		var input = $(this).parent().find('.src');
		var self = this;
		if (this.files && this.files[0]) {
			var size = this.files[0].size  / 1024;
			if (size > 3080) {
	        	load.css('display', 'none');
	        	new Alert("图片尺寸过大", '图片无效');
	        	this.value = '';
				return;
			}
			load.css('display', 'flex');
			img.data('backup', img.attr('src'));
			// img.attr('src', window.URL.createObjectURL(this.files[0]));
			readFile(this.files[0], 80, 1000, function(data) {
				//console.log(data.src);
				img.attr('src', data.src);
				var baseData = data.src.replace('data:image/jpeg;base64,', '');
				chunks.empty();
				var prefix = uploadFile.attr('data-prefix') + '_';
				var interval = 1000;
				// 分段传输, 保证每段1000长
				var count = Math.round(baseData.length / interval) + 1;
				// 已使用长度
				var len = 0;
				var countInput = $('<input>').hide().attr('name', uploadFile.attr('data-prefix') + 'count').val(count);
				chunks.append(countInput);
				
				for (var i = 0; i < count; ++i) {
					// 获取数据
					var chunk = baseData.slice(len, len + interval);
					// 创建分段textarea
					var _chunk = $('<textarea></textarea>').hide().attr('name', prefix + i).text(chunk);
					// 插入
					chunks.append(_chunk);
					len = len + interval;
				}
				
				
				
				// 平均分4段上传
/* 				var interval = baseData.length / 10;
				var start = 0;
				for (var i = 1; i < 11; ++i) {
					var inputInterval = $(self).parent().find('.src_' + i);
					//inputInterval.val('abcd');
					inputInterval.text(baseData.slice(start, start + interval));
					//inputInterval.val( baseData.slice(start, start + interval));
					start = start + interval;
				} */
				
				
				load.css('display', 'none');
			}, function() {
				load.css('display', 'none');
				new Alert("请选择 jpg 或者 png 图片", '图片无效');
			});
		} else if (img.data('backup')){
			img.attr('src', img.data('backup'));
			input.val('');
		}
	});
    /**
     * 读取inputFile图片文件并压缩
     * @param {Image} source_img_obj 源图像对象
     * @param {Integer} quality 质量图像对象的输出质量
     * @param {Number} width 图像压缩后的宽度
     * @return {Image} result_image_obj 压缩的图像对象
     */
    function readFile(file, quality, width, success, fail) {
    	 
   	  // console.log('图片尺寸', file.size / 1024);
    	 
      if (!/image\/\w+/.test(file.type)) {
        if ($.isFunction(fail)) {
          fail(file.type, '只能为图片文件');
        }
        console.warn(file.name, file.type, '只能为图片');
        return false;
      }
      // console.log('读取文件: 名称: %s\t类型: %s', file.name, file.type);
      var reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = function () {
        var img = new Image();
        img.src = this.result
        img.onload = function() {
          if ($.isFunction(success)) {
            success(compress(img, quality, 'image/jpeg', width));
          }
        }
      }
    }
    /**
     * 接收图像对象（可以是JPG或PNG）并返回压缩的新图像对象
     * @param {Image} source_img_obj 源图像对象
     * @param {Integer} quality 质量图像对象的输出质量
     * @param {Number} width 图像压缩后的宽度
     * @return {Image} result_image_obj 压缩的图像对象
     */
    function compress(source_img_obj, quality, output_format, width) {
      var mime_type = "image/jpeg";
      if (output_format != undefined && output_format == "png") {
        mime_type = "image/png";
      }
      var cvs = document.createElement('canvas');
      // 原始宽高比
      var scale = source_img_obj.naturalWidth / source_img_obj.naturalHeight;
      // 设置画布尺寸为真实图片的宽度
      if (width) {
        cvs.width = width;
        cvs.height = source_img_obj.naturalHeight * (width / source_img_obj.naturalWidth);
      } else {
        cvs.width = source_img_obj.naturalWidth;
        cvs.height = source_img_obj.naturalHeight;
      }
      var ctx = cvs.getContext("2d").drawImage(source_img_obj, 0, 0, cvs.width, cvs.height);
      var newImageData = cvs.toDataURL(mime_type, quality / 100);
      var result_image_obj = new Image();
      result_image_obj.src = newImageData;
      return result_image_obj;
    }
	// 卡识别验证
	window.SettleCardValidator = function(customValidator, defaultInit) {
	  /**
	   * 对公验证
	   * 账户类型: OPEN(对公)
	   *  - 账户名称: 必填
	   *  - 银行卡号: 必填
	   *  - 开户行名称: 必填
	   */
	  /**
	   * 对私验证
	   * 账户类型: INDIVIDUAL(对私)
	   *  - 账户名称: 必填,中文
	   *  - 银行卡号: 必填,正常银行卡号, checkBankCard(ajax银行卡验证)
	   *  - 开户行名称: 必填, checkBankName(ajax银行名称验证)
	   */
	  // 银行卡号name
	  var cardNo_Name = 'accNo';
	  var bank_Name = 'bankName';
		// 开户行编号(银行卡号)
		var accountNo = $('#accountNo');
	  // 账户名称
	  var cardName = $('#cardName');
		// 账户类型(对公对私下拉列表)
		var customerType = $('#customerType');
		// 开户行名称(银行名称)
		var openBankName = $('#openBankName');
		// 银行码 [隐藏输入框, 通过获取银行卡号信息设置]
		var bankCode = $('#bankCode');
		// 开启总行复选框
		var sabkBankFlag = $('#sabkBankFlag');
		// 上一次银行卡号
		var lastAccountNo = accountNo.val();
	  // 获取账户类型 INDIVIDUAL=对私 OPEN=对公
	  var getCustomerType = function() {
	    return customerType.val();
	  };
	  // 获取银行等级
	  var getBankFlag = function() {
	    // 页面没有总行复选框则默认0级
	    return sabkBankFlag.length !== 1 ? 0 : sabkBankFlag.val();
	  };
	  // 切黄总行 element=总行复选框
	  window.changeFlag = function(element) {
	    if (element.checked == true) {
	      element.value = 1;
	    } else {
	      element.value = 0;
	    }
	    openBankName.val('');
	  };
	  
	  
	  
	  $('#openBankName').focus(function() {
	        // 自动定位
	        $('.mobileFrame').animate({scrollTop: ($('#openBankName').offset().top - 10)}, 300);
	  });
	  
	  
	  
	  // 银行列表自动完成
 	  var autocomplete = openBankName.Autocomplete({
		  onShow: function() {
			  autocomplete.warp.css({
				  'top': '70px'
			  });
			  
		  },
	    // 下拉列表插入位置
	    appendTo: openBankName.parent(),
	    // 选择下拉列表事件
	    onSelect: function (txt, attData) {
 	      Api.vip.bankCoreInfo(attData.bankCode, function (data) {
	        if (data.length > 0) {
	          bankCode.val(data.clearingBankCode);
	        }
	      });
	    },
	    // 下拉列表资源获取
	    source: function (value, fn) {
	      // 对公账户类型则不提示银行列表
	      if (getCustomerType() === 'OPEN') {
	        return;
	      }
	      Api.vip.banks(value, bankCode.val(), getBankFlag(), function (banks) {
	    	fn(banks, value);
	      });
	    }
	  });
	  // 验证银行卡号
	  $.addMethod('checkBankCard', function(value, element, customerType, fn) {
	    // 替换空格
	    var card = value.replace(/\s+/g, '');
	    if (card === '') {
	      return false;
	    }
	    // 对公账户类型则不验证
	    var type = $.isFunction(customerType) ? customerType(element, card) : customerType;
	    if (type === 'OPEN') {
	      return true;
	    }
	    Api.vip.bankCardInfo(card, function(iin) {
	      if (iin !== null) {
	        bankCode.val(iin.providerCode);
	        // 银行卡号改变, 清空银行名称
	        if (lastAccountNo != value) {
	          openBankName.val('');
	        }
	      }
	      fn(iin !== null && iin.providerCode !== null);
	    });
	  }, '银行卡号不正确', '验证中...');
	  // 检测银行名
	  $.addMethod('checkBankName', function (value, element, customerType, fn) {
	    // 对公账户类型则不验证
	    var type = $.isFunction(customerType) ? customerType(element, value) : customerType;
	    if (type === 'OPEN' || autocomplete.isShow) {
	      return true;
	    }
	    Api.vip.banks(value, bankCode.val(), getBankFlag(), function (banks) {
        	var status = false;
        	$.each(banks, function() {
        		if (this.name == value || this.value === value) {
        			status = true;
        		}
        	});

          fn(status);
	    });
	  }, '无效的银行名称', '验证中...');
	  // 添加规则到验证对象
	  var rules = customValidator.options.rules;
	  // 银行卡号
	  rules[cardNo_Name] = {
	    required: true,
	    bankCard: true,
	    checkBankCard: getCustomerType
	  };
	  // 银行名称
	  rules[bank_Name] = {
	    required: true,
	    checkBankName: getCustomerType
	  };
	  // 添加触发时机
	  var trigger = customValidator.options.trigger;
	  trigger[cardNo_Name] = { checkBankCard: 'blur', bankCard:'blur', required:'blur',};
	  trigger[bank_Name] = { checkBankName: 'blur', required:'blur'};
	  trigger['accName'] = { chinese: 'blur', required:'blur'};
	  // 添加验证回调
	  var onchange = customValidator.options.onchange;
	  onchange[cardNo_Name] = function(element, status) {
	    // 如果还在焦点说明还没异步验证
	    if (accountNo.is(":focus")) {
	      return;
	    }
	    // 开户银行名称输入框
	    var tips = '请输入地区、分行支行名称等关键词进行匹配';
	    switch (status) {
	      case true:
	        lastAccountNo = element.val();
	        if (openBankName.val() === tips) {
	          openBankName.val('');
	        }
	        openBankName.removeAttr('disabled');
	        openBankName.focus();
	        // 自动定位, 延迟一下, 等待手机输入法弹出改变布局后
	        setTimeout(function() {
	        	$('.mobileFrame').animate({scrollTop: ($('#openBankName').offset().top - 10)}, 300);
	        }, 200);
	        break;
	      case false:
	        openBankName.val(tips);
	        openBankName.attr('disabled', 'disabled');
	        // 将银行名称输入框复位
	        var bindElement = openBankName.data(Validator.FLAG_MSG_ELEMENT);
	        if (bindElement) {
	          bindElement.hide();
	        }
	        if (openBankName.data('vd-lastcolor')) {
	          openBankName.css('border', openBankName.data('vd-lastcolor'));
	        }
	        break;
	      case 'wait':
	        // ..
	        break;
	    }
	  };
	  // 账户类型更改后, 触发验证
	  customerType.change(function () {
	    var rule = customValidator.rules(cardNo_Name);
	    if (getCustomerType() === 'OPEN') {
	      cardName.removeAttr('chinese');
	      delete rule.bankCard;
	      //清空银行编码
	      bankCode.attr('value', '');
	    } else {
	      cardName.attr('chinese', 'true');
	      rule['bankCard'] = true;
	    }
	    customValidator.element(accountNo);
	  });
	  // 重新初始化验证
	  customValidator.init();
	  // 带初始值, 则主动验证一次
	  if (defaultInit) {
	    // 模拟切换账户类型, 切换对应验证
	    customerType.trigger('change');
	  	// 手动验证一次银行卡号, 获取bankCode
	  	customValidator.element(accountNo);
	  }
	  return autocomplete;
	};
	 // 创建验证实例
	 var customValidator  = $('#customerAdd').Validator({
	 });
  	customValidator.options.submitHandler = function(from, status) {
  		if (status === true) {
  			$('.maskTop').addClass('active');
  		}
  	};
	 // 卡识别验证
 	window.SettleCardValidator(customValidator);
	</script>
</body>
</html>
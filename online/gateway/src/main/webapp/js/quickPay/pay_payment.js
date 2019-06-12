$(function() {
			var timer,
				windowInnerHeight = window.innerHeight;
			var windouw = window.innerHeight;
         //倒计时start
			var curTime = 0,
				timer = null,
				btn = $('#yanzheng');

			function handleTimer() {
				if(curTime == 0) {
					clearInterval(timer);
					timer = null;
					$("#yanzheng").css("background", "rgba(243,159,27,1)");
					btn.text('重新发送');
				} else {
				   $("#yanzheng").css("background", "#ccc");
					btn.text(curTime + 's后发送');
					curTime--;
				}
			}
//			
			
//			var submitFun= function() {
//				var param = $("#form").serialize();
//				console.log(param);
//				$.post("saveOpenCardInfo", param, function(result) {
//					if (result == "0000") {
//						window.location.href = 'toPay?key=' + $("#accountNo").val();
//					}
//				});
//			};
//			
//			$("#subBtn").on('click', submitFun);
//			$("#phoneSubBtn").on('click', submitFun);

	
			/**
			 * S zhupan类库
			 * $submit 提交按钮
			 * $form form表单
			 * alertDom
			 */
         
			var S = zhupan,
				SC = zhupanCommon,
				phone_no=$("#phone"),
				username=$("#username"),
				id_card=$("#id_card"),
				pay_card=$("#pay_card"),
				yanzheng = $("#parttom"),
				$submit = $(":submit"),
				$form = $('#form'),
				cvv=$("#cvv"),
				validityMonth=$("input[name=validityMonth]");
			    validityYear=$("input[name=validityYear]");
				$loadingModalParent = $('.loginContent'),
				action = $form.attr('action');
				//判斷是否需要短信驗證
				if($("#smsCodeType").val()!="NO_YLZF_CODE"){
					$("#NO_YLZF_CODE").css("display","none");
				}

			valiFunc = function() {
					var verificationClass = S.verification();
					var validator = new verificationClass;
					validator.add(phone_no, [{
						strategy: 'isNonEmpty',
						errorMsg: '电话号码不能为空'
					}, {
						strategy: 'phone',
						errorMsg: '电话号码格式不正确'
					}]);
					if($("#smsCodeType").val()=="NO_YLZF_CODE"){
						validator.add(yanzheng, [{
							strategy: 'isNonEmpty',
							errorMsg: '验证码不能为空'
						}, {
							strategy: 'isNumber',
							errorMsg: '验证码输入格式不正确'
						}]);
					}
					

					return validator.start();
				},
				valiFuncs = function() {
					var verificationClass = S.verification();
					var validator = new verificationClass;
					validator.add(phone_no, [{
						strategy: 'isNonEmpty',
						errorMsg: '电话号码不能为空'
					}, {
						strategy: 'phone',
						errorMsg: '电话号码格式不正确'
					}]);
					

					return validator.start();
				},
				validebat = function() {
					var verificationClass = S.verification();
					var validator = new verificationClass;
					validator.add(phone_no, [{
						strategy: 'isNonEmpty',
						errorMsg: '电话号码不能为空'
					}, {
						strategy: 'phone',
						errorMsg: '电话号码格式不正确'
					}]);
					

					return validator.start();
				},
				removeDom = function(elem) {
					SC.removeDom($(elem));
				},
				createErrorTip = function(filed, errorMsg) {
					filed.focus();
					var createErrorTip = S.getSingle(SC.createErrorTip);
					var layer = createErrorTip(filed, errorMsg);
				 
					layer.fadeIn("fast").delay(2000).fadeOut("fast", function() {
						this.remove();
					if(bankCard(pay_card.val())==false){
						
				   var banckerror=SC.createErrorTip(pay_card,'无效银行卡');
					banckerror.fadeIn("fast").delay(2000).fadeOut("fast", function() {
						this.remove();
					});
					}
					});
				},
		
		
				createBeforeErrorTip = function(target, cssClass, msg) {
					var createTip = S.getSingle(SC.createBeforeTip);
					var layer = createTip(target, cssClass, msg);
				};
          
			SC.iHoverFun(yanzheng, 'iHover');
			SC.iHoverFun(phone_no, 'iHover');
			SC.iHoverFun(username, 'iHover');
			SC.iHoverFun(id_card, 'iHover');
			SC.iHoverFun(pay_card, 'iHover');
			
			//发送验证码提交
			btn.click(function(){
				if(timer===null){
					var param = "orderCode=" + $("#orderCode").val() + "&phone=" + $("#phone").val() + "&accountNo=" + $("#accountNo").val(); 
					 if($("#cardType").val()=="DEBIT"){
						 onclicks(validebat);
			          }
					 else{
						  onclicks(valiFuncs);
						 
					 }
				}
			})
			 if($("#cardType").val()=="DEBIT"){
	        	  $(".debat").css("display","none");
	        	  submits(validebat);
	          }
			 else{
				  $(".debat").css("display","block");
				 submits(valiFunc);
				 
			 }
			//yanzhen
			function onclicks(vali){
				S.domclick(vali, {
					form: $form,
					action: "sendOpenCardVerifyCode",
					dataType: '',
					createErrorTip: createErrorTip,
				    successCallback: function(result) {
				    	console.log(result)
						if (result == '0000') {
							curTime = 60;
							timer = setInterval(handleTimer, 1000);
						} else {
							var info = '';
							if (result == '0001') {
								info = '请录入完整信息。';
							} else if (result == '0002') {
								info = '订单不存在！';
							} else if (result == '0003') {
								info = '卡片信息错误！';
							} else if (result == '0004') {
								info = '发送验证码失败！';
							}
							$("#errormdgs").text(info);
							$("#errormdg").fadeIn("slow").delay(1000).fadeOut("slow");
						}
					},
					completeFunc: function() {
						$submit.prop("disabled", false);
					}
				});
			}
			//调用表单提交方法
			function submits(vali){
				S.formSubmit(vali, {
					form: $form,
					action: action,
					dataType: '',
					createErrorTip: createErrorTip,
					beforeSendFunc: function() {
						$('#loading').fadeIn(500);
						removeDom('.error');
						$submit.prop("disabled", true);

					},
					successCallback: function(result) {
						if (result == "0000") {
							window.location.href = 'toPay?key=' + $("#accountNo").val();
						} else if (result == '0001') {
							$("#errormdgs").text("验证码错误！");
							$("#errormdg").fadeIn("slow").delay(1000).fadeOut("slow");
						}

					},
					completeFunc: function() {
						$('#loading').fadeOut(500);
						$submit.prop("disabled", false);
					}
				});
			}
			
			
			
			
	// 银行卡号
     function bankCard(val) {
      var status = true;
      // value为NAN(非法值)或者长度小于12, 则false
      if (isNaN(val))
        status = false;
      if (val.length < 12) {
        status = false;
      }
      // 将 123456字符串卡号,分割成数组 ['1', '2', '3', ...]
      var nums = val.split('');
      // 合计
      var sum = 0;
      // 索引
      var index = 1;
      for (var i = 0; i < nums.length; i++) {
        // 当前索引是否为偶数
        if ((i + 1) % 2 === 0) {
          // 当前数组倒index的数字 * 2, 转数值
          var tmp = Number(nums[nums.length - index]) * 2;
          if (tmp >= 10) {
            // 将大于等于10的值转字符串
            var t = tmp + ''.split('');
            // tmp 值等于 字符串[0] + 字符串[1],  '16' 就是 1+6=7
            tmp = Number(t[0]) + Number(t[1]);
          }
          // 累加值
          sum += tmp;
        } else {
          // sum 累加当前数组倒index的数字
          sum += Number(nums[nums.length - index]);
        }
        // 累加索引
        index++;
      }
      // 如果值不是10的倍数, 则不是一个合法的银行卡号
      if (sum % 10 !== 0) {
        status = false;
      }
    
      return status;
    };
	
		}(jQuery))
	
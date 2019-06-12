var zhupan = (function($) {
	return {
		/**
		 * 单例模式集成
		 * */
		getSingle: function(fn) {
			var result;
			return function() {
				return result || (result = fn.apply(this, arguments));
			}
		},

		/**
		 * Ajax
		 * */
		ajax: function(type, url, dataType, formData, successFunc, beforeSendFunc,errorFunc,completeFunc) {
			var urlArg = url.indexOf('?') >= 0 ? '&' : '?'
			$.ajax({
				type: type,
				url: url + urlArg + 'r=' + Math.random(),
				data: formData,
				dataType: dataType,
				beforeSend: function() {
					if(typeof beforeSendFunc !== 'undefined' && typeof beforeSendFunc == 'function') {
						beforeSendFunc();
					}
				},
				success: function(data) {
					successFunc(data);
				},
				complete: function() {
					if(typeof completeFunc !== 'undefined' && typeof completeFunc == 'function') {
						completeFunc();
					}
				}
			});
		},
		/*
		 * 表单验证，策略+命令模式
		 * 验证出错返回一个对象，包括错误信息以及给出的要在那个元素上提示错误信息
		 * fill 只有填写才验证
		 * */
		verification: function() {
			var algorithm = {
					__commonAlgorithm: function(value, fill, callback) {
						if(fill !== '') {
							if(value !== '') {
								return callback();
							}
						} else {
							return callback();
						}
					},

					__isFalse: function(callback, filed, errorMsg) {
						if(callback()) {
							return {
								errorMsg: errorMsg,
								filed: filed
							};
						}
					},
					isNonEmpty: function(value, filed, errorMsg) {
						value = $.trim(value);
						return algorithm.__isFalse(function() {
							return value === '';
						}, filed, errorMsg);
					},
					
					isNumber: function(value, filed, fill, errorMsg) {
						value = $.trim(value);
						var myAlgorithm = function() {
							return algorithm.__isFalse(function() {
								return !/\d+/.test(value);
							}, filed, errorMsg);
						};
						return algorithm.__commonAlgorithm(value, fill, myAlgorithm);
					},
					betweenLength: function(value, minLength, maxLength, filed, fill, errorMsg) {
						value = $.trim(value);
						var myAlgorithm = function() {
							return algorithm.__isFalse(function() {
								return(value.length < minLength || value.length > maxLength);
							}, filed, errorMsg);
						};
						return algorithm.__commonAlgorithm(value, fill, myAlgorithm);
					},

					legitimateUrl: function(value, filed, fill, errorMsg) {
						value = $.trim(value);
						var myAlgorithm = function() {
							return algorithm.__isFalse(function() {
								return !/^http[s]?:\/\/([\w-]+\.)+[\w-]+([\w-./?%#&:=]*)?$/.test(value);
							}, filed, errorMsg);
						};
						return algorithm.__commonAlgorithm(value, fill, myAlgorithm);
					},
				},
				strategies = {
					isNonEmpty: function(value, filed, fill, errorMsg) {
						return algorithm.isNonEmpty(value, filed, errorMsg);
					},

					betweenLength: function(value, minLength, maxLength, filed, fill, errorMsg) {
						return algorithm.betweenLength(value, minLength, maxLength, filed, fill, errorMsg);
					},

					legitimateUrl: function(value, filed, fill, errorMsg) {
						return algorithm.legitimateUrl(value, filed, fill, errorMsg);
					},

					isNumber: function(value, filed, fill, errorMsg) {
						return algorithm.isNumber(value, filed, fill, errorMsg);
					}

				},
				Validator = function() {
					this.cache = [];
				};
			Validator.prototype.add = function(dom, rules) {
				var self = this; // 代表验证类
				for(var i = 0, rule; rule = rules[i++];) {
					(function(rule) {
						var strategyAry = rule.strategy.split(':'),
							errorMsg = rule.errorMsg,
							field = dom,
							fill = rule.fill ? rule.fill : '';
						self.cache.push(function() {
							var strategy = strategyAry.shift();
							strategyAry.unshift(dom.val());
							strategyAry.push(field);
							strategyAry.push(fill);
							strategyAry.push(errorMsg);
							return strategies[strategy].apply(dom, strategyAry);
						});
					})(rule);
				}
			};
			Validator.prototype.start = function() {
				for(var i = 0, validatorFunc; validatorFunc = this.cache[i++];) {
					var fail = validatorFunc();
					if(typeof fail === 'object') {
						return fail;
					}
				}
			};
			return Validator;
		},
		/*
		 * 表单提交
		 * */
		formSubmit: function(verificationFunc, formObj) {
			var form = formObj.form;
			form.submit(function() {
				   if(typeof formObj.submitBeforeFn !== 'undefined' && typeof formObj.submitBeforeFn == 'function') {
                    formObj.submitBeforeFn();
                }
                var fail = verificationFunc && verificationFunc();
                if (typeof fail === 'object') {
                    var errorMsg = fail.errorMsg,
                        filed = fail.filed;
                    if(typeof formObj.createErrorTip !== 'undefined' && typeof formObj.createErrorTip == 'function') {
                        formObj.createErrorTip(filed, errorMsg); //如果样式中需要字段就加字段
                        //formObj.createErrorTip(errorMsg);
                    }
                    filed.focus();
                    return false;
                }
				// 提交表单
				var action = formObj.action,
					dataType = formObj.dataType,
					formData = form.serialize();
				zhupan.ajax('POST', action, dataType, formData, formObj.successCallback, formObj.beforeSendFunc,formObj.errorFunc, formObj.completeFunc);
				return false;
			});
		}
	}
})(jQuery);
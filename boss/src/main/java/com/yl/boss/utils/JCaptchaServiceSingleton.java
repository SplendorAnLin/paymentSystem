package com.yl.boss.utils;

import com.octo.captcha.service.captchastore.FastHashMapCaptchaStore;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * 验证码服务单例
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月28日
 * @version V1.0.0
 */
public class JCaptchaServiceSingleton {
	
	private static ImageCaptchaService imageCaptchaService = new DefaultManageableImageCaptchaService(
			new FastHashMapCaptchaStore(), new GMailEngine(), 180, 100000, 75000);

	public static ImageCaptchaService getInstance() {
		return imageCaptchaService;
	}
}

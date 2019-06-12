package com.yl.customer.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.log4j.Logger;

import com.octo.captcha.service.CaptchaServiceException;

/**
 * Servlet implementation class JcaptchaServlet
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月20日
 * @version V1.0.0
 */
@WebServlet("/JcaptchaServlet")
public class JcaptchaServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1535669084913540108L;

	public static final String CAPTCHA_IMAGE_FORMAT = "jpeg";  
    
    private static final Logger log = Logger.getLogger(JcaptchaServlet.class);  
  
    @Override  
    public void init() throws ServletException {  
    }  
  
    /** 
     * @step1 本方法使用Jcaptcha工具生成img图片，并输出到客户端 
     * @step2 将来在用户提交的action中插入下面语句进行验证码的校验：Boolean isResponseCorrect = 
     *        captchaService.validateResponseForID( captchaId, ""); 
     **/  
    @Override  
    protected void doGet(HttpServletRequest request,  
            HttpServletResponse response) throws ServletException, IOException {  
        byte[] captchaChallengeAsJpeg = null;  
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();  
        try {  
            // 借助于HttpSession ID存储Captcha ID，开发者也可以借助于其他惟一值  
            String captchaId = request.getSession().getId();  
            // 获得GMailEngine使用的图片内容  
            BufferedImage challenge = JCaptchaServiceSingleton.getInstance().getImageChallengeForID(captchaId, request.getLocale());  
            // 输出JPEG格式  
            ImageIO.write(challenge , "JPEG", jpegOutputStream); 
        } catch (IllegalArgumentException e) {  
            log.error(e);  
            response.sendError(HttpServletResponse.SC_NOT_FOUND);  
            return;  
        } catch (CaptchaServiceException e) {  
            log.error(e);  
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);  
            return;  
        }  
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();  
        response.setHeader("Cache-Control", "no-store");  
        response.setHeader("Pragma", "no-cache");  
        response.setDateHeader("Expires", 0);  
        // 输出JPEG图片  
        response.setContentType("image/jpeg");  
        ServletOutputStream responseOutputStream = response.getOutputStream();  
        responseOutputStream.write(captchaChallengeAsJpeg);  
        responseOutputStream.flush();  
        responseOutputStream.close();  
    }

}

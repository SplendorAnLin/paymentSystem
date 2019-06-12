package com.yl.agent.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.yl.agent.Constant;

/**
 * 验证码
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月28日
 * @version V1.0.0
 */
public class RandomCodeAction extends ActionSupport {
	
	private static int WIDTH = 80;
	private static int HEIGHT = 25;

	private String randomCode;
	
	public String execute() throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("image/jpeg");
		ServletOutputStream sos = response.getOutputStream();
		
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();		
		char[] rands = generateCheckCode();

		drawBackground(g);
		drawRands(g, rands);		
		g.dispose();
	
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(image, "JPEG", bos);
		byte[] buf = bos.toByteArray();
		response.setContentLength(buf.length);
	
		sos.write(buf);
		bos.close();
		sos.close();
        String verifyCode = new String(rands);        
        session.setAttribute(Constant.SESSION_CODE, verifyCode);
		return null;
	}
	private char[] generateCheckCode() {
		
		String chars = "0123456789qwertyuiopasdfghjklzxcvbnm";
		char[] rands = new char[4];
		for (int i = 0; i < 4; i++) {
			int rand = (int) (Math.random() * 36);
			rands[i] = chars.charAt(rand);
		}
		return rands;
	}

	private void drawRands(Graphics g, char[] rands) {
		
		g.setColor(Color.BLACK);
		g.setFont(new Font(null, Font.ITALIC | Font.BOLD, 20));
	
		g.drawString("" + rands[0], 3, 17);
		g.drawString("" + rands[1], 20, 15);
		g.drawString("" + rands[2], 40, 20);
		g.drawString("" + rands[3], 60, 16);
	}

	private void drawBackground(Graphics g) {
	
		g.setColor(new Color(0xDCDCDC));
		g.fillRect(0, 0, WIDTH, HEIGHT);

		for (int i = 0; i < 100; i++) {
			int x = (int) (Math.random() * WIDTH);
			int y = (int) (Math.random() * HEIGHT);
			int red = (int) (Math.random() * 255);
			int green = (int) (Math.random() * 255);
			int blue = (int) (Math.random() * 255);
			g.setColor(new Color(red, green, blue));
			g.drawOval(x, y, 1, 0);
		}
	}
	public String getRandomCode() {
		return randomCode;
	}
	public void setRandomCode(String randomCode) {
		this.randomCode = randomCode;
	}
}

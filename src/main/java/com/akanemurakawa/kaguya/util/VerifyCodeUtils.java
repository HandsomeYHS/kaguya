package com.akanemurakawa.kaguya.util;

import com.akanemurakawa.kaguya.constant.BaseConstant;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 创建用于登录认证的验证码
 */
@WebServlet("/VerifyCode")
public class VerifyCodeUtils extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 随机生成一个颜色
	 */
	public Color getColor() {
		Random random = new Random();
		int r = random.nextInt(256);
		int g = random.nextInt(256);
		int b = random.nextInt(256);
		return new Color(r,g,b);
	}
	
	/**
	 * 随机产生四位的验证字符
	 */
	public String getCode(){	
		// length : 26+26 = 52 52+9 = 61
		char[] str = {'A','B','C','D','E','F','G',
					  'H','I','J','K','L','M','N',
					  'O','P','Q','R','S','T','U',
					  'V','W','X','Y','Z','a','b',
					  'c','d','e','f','g','h','i',
					  'j','k','l','m','n','o','p',
					  'q','r','s','t','u','v','w',
					  'x','y','z',
					  '1','2','3','4','5','6','7',
					  '8','9'
					};
		Random rand = new Random();
		String code = "";
		for(int i = 0; i<4; i++){
			int num = rand.nextInt(61);
			code += str[num];
		}
		return code;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 禁止浏览器缓存
		response.setDateHeader("Expires", -1);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		// 设置浏览器支持接收的图片格式
		response.setHeader("Content-Type", "image/jpeg");
		
		// 设置验证码图片的格式
		BufferedImage image = new BufferedImage(80, 30, BufferedImage.TYPE_INT_BGR);
		Graphics g = image.getGraphics();
		// 填充验证码的背景颜色
		g.setColor(Color.decode("#ebf2f9"));
		g.fillRect(0, 0, 80, 30);
		/*
		 * beginning location(xBegin,yBegin), ending location(xEnd,yEnd)
		 * 随机生产20条干扰线
		 */
		for (int i = 0; i < 20; i++) {
			Random random = new Random();
			int xBegin = random.nextInt(80);
			int yBegin = random.nextInt(30);
			int xEnd = random.nextInt(xBegin+10);
			int yEnd = random.nextInt(yBegin+10);
			g.setColor(getColor());
			g.drawLine(xBegin, yBegin, xBegin+xEnd, yBegin+yEnd);
		}
		
		/*
		 * Black, font: serif, bold, 20 pixels
		 * 设置验证码的颜色和字体
		 */
		g.setColor(Color.decode("#1a9724"));
		g.setFont(new Font("serif", Font.BOLD, 20));
		String verifyCode = getCode();
		/*
		 * comment:使用空格在验证码的字符之间
		 */
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < verifyCode.length(); i++) {
			stringBuffer.append(verifyCode.charAt(i)+"");
		}
		// 画验证码
		g.drawString(stringBuffer.toString(), 15, 20);
		// 把该值放进session中
		request.getSession().setAttribute(BaseConstant.SESSION_VERIFY_CODE, verifyCode);
		// Put the verification code into jpg format(把验证码放进图片格式中)
		ImageIO.write(image, "jpg", response.getOutputStream());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

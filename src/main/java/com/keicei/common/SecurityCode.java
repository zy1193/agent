package com.keicei.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;

/**
 * 以BMP格式输出验证码图片,验证码保存在session的SecurityCode属性
 * 
 * @author zhyan
 * 
 */
public class SecurityCode extends HttpServlet {

	private static final long serialVersionUID = 8118671479736768770L;
	private static final int WIDTH = 80;
	private static final int HEIGTH = 28;
	public static final String SESSION_ATTR_NAME = "SecurityCode";

	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		fc = fc > 255 ? 255 : fc;
		bc = bc > 255 ? 255 : bc;
		return new Color(fc + random.nextInt(bc - fc), fc
				+ random.nextInt(bc - fc), fc + random.nextInt(bc - fc));
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int i, x, y, xl, yl;
		StringBuilder buff = new StringBuilder(4);
		String number;

		response.setContentType("image/bmp");
		JspFactory
				.getDefaultFactory()
				.getPageContext(this, request, response, null, true, 8192, true)
				.getOut().clear();
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		BufferedImage image = new BufferedImage(WIDTH, HEIGTH,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		Random random = new Random();

		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, WIDTH, HEIGTH);

		g.setFont(new Font("verdana", Font.BOLD, 24));

		for (i = 0; i < 255; i++) {
			g.setColor(getRandColor(160, 255));
			x = random.nextInt(WIDTH);
			y = random.nextInt(HEIGTH);
			xl = random.nextInt(12);
			yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		g.setColor(Color.LIGHT_GRAY);
		g.drawRect(0, 0, WIDTH - 1, HEIGTH - 1);

		for (i = 0; i < 4; i++) {
			number = Integer.toString(random.nextInt(10));
			buff.append(number);
			g.setColor(new Color(33 + random.nextInt(110), 66 + random
					.nextInt(110), 99 + random.nextInt(110)));
			g.drawString(number, 18 * i + 4, 24);
		}

		request.getSession().setAttribute(SESSION_ATTR_NAME, buff.toString());

		g.dispose();
		ImageIO.write(image, "BMP", response.getOutputStream());
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}

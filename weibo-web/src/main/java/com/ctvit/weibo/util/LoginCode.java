package com.ctvit.weibo.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * 产生随机码
 * @author tqc
 *
 */
public class LoginCode extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8074068294861693410L;
	public static final String RANDOM_CODE_SESSION = "randomCode";

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		 // 验证码图片的宽度。
		 int width = 104;
		 // 验证码图片的高度。
		 int height = 48;
		 BufferedImage buffImg = new BufferedImage(width, height,
		 BufferedImage.TYPE_INT_RGB);
		 Graphics2D g = buffImg.createGraphics();
		 // 创建一个随机数生成器类。
		 Random random = new Random();
		 // 设定图像背景色(因为是做背景，所以偏淡)
		 g.setColor(getRandColor(200, 250));
		 g.fillRect(0, 0, width, height);
		 // 创建字体，字体的大小应该根据图片的高度来定。
		 Font font = new Font("Times New Roman", Font.HANGING_BASELINE, 40);
		 // 设置字体。
		 g.setFont(font);
		 
		 // 画边框。
		 g.setColor(Color.BLACK);
		 g.drawRect(0, 0, width - 1, height - 1);
		 // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到。
		 //g.setColor(Color.GRAY);
		 g.setColor(getRandColor(160,200));
		 for (int i = 0; i < 155; i++) {
			 int x = random.nextInt(width);
			 int y = random.nextInt(height);
			 int xl = random.nextInt(12);
			 int yl = random.nextInt(12);
			 g.drawLine(x, y, x + xl, y + yl);
		 }
		 
		 // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
		 StringBuffer randomCode = new StringBuffer();
		 // 设置默认生成4个验证码
		 int length = 4;
		 // 设置备选验证码:包括数字"0-9"
		 String base = "0123456789";
		 
		 int size = base.length();
		 
		 // 随机产生6位数字的验证码。
		 for (int i = 0; i < length; i++) {
		 // 得到随机产生的验证码数字。
			 int start = random.nextInt(size);
			 String strRand = base.substring(start, start + 1);
		 
		 // 用随机产生的颜色将验证码绘制到图像中。
		 // 生成随机颜色(因为是做前景，所以偏深)
		 //g.setColor(getRandColor(1, 100));
		 
		 //调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
		 g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
		 g.drawString(strRand, 15 * i + 6, 35);

		 // 将产生的四个随机数组合在一起。
		 
		 randomCode.append(strRand);
		 }
		 // 将四位数字的验证码保存到Session中。
		 HttpSession session = req.getSession();
		 session.setAttribute(RANDOM_CODE_SESSION, randomCode.toString().toLowerCase());
		 
		 //图象生效
		 g.dispose();

		 // 禁止图像缓存。
		 resp.setHeader("Pragma", "no-cache");
		 resp.setHeader("Cache-Control", "no-cache");
		 resp.setDateHeader("Expires", 0);

		 resp.setContentType("image/jpeg");

		// 将图像输出到Servlet输出流中。
		ServletOutputStream sos = resp.getOutputStream();
		ImageIO.write(buffImg, "jpeg", sos);
		sos.flush();
		sos.close();
	}
	
	public void createImage (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.service(req, resp);
	}
	
	Color getRandColor(int fc, int bc) {
		// 给定范围获得随机颜色
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

}

package com.akanemurakawa.kaguya.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hanaeyuuma.freeblogs.model.Admin;
import com.hanaeyuuma.freeblogs.model.Msg;
import com.hanaeyuuma.freeblogs.model.User;
import com.hanaeyuuma.freeblogs.service.AdminService;
import com.hanaeyuuma.freeblogs.service.UserService;
import com.hanaeyuuma.freeblogs.util.DateFormatUtils;
import com.hanaeyuuma.freeblogs.util.MD5Utils;
import com.hanaeyuuma.freeblogs.util.SendEmailUtils;

/**
 * 用于用户登录和登出的控制器， Login/logout
 * @author HanaeYuuma
 * @date 2018-7-12
 */
@Controller
public class LoginController {

	/**
	 *  用户保存用户登录记录的cookie
	 */
	private String cookieFreeBlogsName = "cookieFreeBlogs"; 
	
	/**
	 *  用于保存用户登录时间的cookie
	 */
	private String cookieLoginLastTimeName ="cookieLoginLastTime"; 
	/**
	 *  用户登录时间
	 */
	private String loginLastTime;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AdminService adminService;
	
	/**
	 * 这一部分是用于查看用户是否使用记住了密码，如果有则查询相应的cookie的值，然后传到数据库验证
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/tologin.do")
	public String checkLoginCookie(HttpServletRequest request) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				/*
				 * 如果有cookieSuzuki则表示使用 记住密码，这个时候取出该cookie的值，然后到数据库验证密码
				 * 如果验证成功则直接跳转到用户界面
				 * Name:"cookieSuzuki", Value:password
				 */
				String cookieSuzuki = cookie.getName();
				if (cookieFreeBlogsName.equals(cookieSuzuki)) {
					String cookieSuzukiValue = cookie.getValue(); 
					User userGetFromCookie = new User(cookieSuzukiValue);
					User checkUserGetFromCookie = userService.checkUserCookie(userGetFromCookie);
					if (checkUserGetFromCookie != null) {
						HttpSession session = request.getSession();
						session.setAttribute("loginUser", checkUserGetFromCookie);
						// 解决url乱码问题
						return "redirect:/p/"+ URLEncoder.encode(checkUserGetFromCookie.getUserUsername(), "UTF-8")+"/home";
					}
				}
			}
		}
		return "login";
	}
	
	@RequestMapping("/toRegiste.do")
	public String toRegiste() {
		return "registe";
	}
	
	/**
	 *  用于接受用户登录的请求
	 *  1.记录用户的登录时间
	 *  2.判断用户是否记住密码
	 *  3.把用户放到session中登录
	 */
	@RequestMapping(value="/login.do", method=RequestMethod.POST)
	public String userLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 这一部分是用于记录用户的登录时间
		 */
		boolean isLastTimeCookie = false;// 判断是否是第一次登录的标志
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			// 遍历cookie
			for (Cookie cookie : cookies){
				String name = cookie.getName();
				if (cookieLoginLastTimeName.equals(name)) {
					// 将此次的时间作为新的lasttime存放
					// 中间不能有空格，否则会报错:An invalid character [32] was present in the Cookie value
					loginLastTime = DateFormatUtils.getNowTime("YYYY年MM月dd日HH:mm:ss");
					cookie.setValue(loginLastTime);
					cookie.setMaxAge(3600*24*7);// 单位秒，保存七天
					response.addCookie(cookie);
					isLastTimeCookie = true;
					break;
				}
			}
		}
		if (!isLastTimeCookie) {
			// 把第一次的时间作为cookie记录
			loginLastTime = DateFormatUtils.getNowTime("YYYY年MM月dd日HH:mm:ss");
			Cookie cookieTime = new Cookie(cookieLoginLastTimeName, loginLastTime);
			cookieTime.setMaxAge(3600*24*7);// 单位秒，保存七天
			response.addCookie(cookieTime);
		}
		
		/*
		 * 判断用户是否需要记住密码，如果需要则存入cookie中。
		 */
		// 是否需要记住密码
		String rememberMe = request.getParameter("remember-me");
		// 获得用户输入的邮箱和密码
		String email = request.getParameter("email");
		String password= request.getParameter("password");
		// MD5加密后进行验证
		password = MD5Utils.getMD5(password);
		// 如果勾选了记住密码，则把用户的账号密码存入到cookie中 ，Name:"cookieSuzuki", Value:password
		if (rememberMe != null && "remember-me".equals(rememberMe)) {
			Cookie cookieRole = new Cookie(cookieFreeBlogsName, password);
			cookieRole.setMaxAge(7*3600*24); // 单位秒，保存七天
			response.addCookie(cookieRole);
		}
		
		User checkUser = null;
		User user = new User(email, password);
		checkUser = userService.checkUserLogin(user);
		// 如果成功则到用户的首页，否则重新登录
		if (checkUser != null){
			// 保存用户信息和最后的登录
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", checkUser);
			session.setAttribute("loginLastTime", loginLastTime);
			// 解决url乱码问题
			return "redirect:/p/"+ URLEncoder.encode(checkUser.getUserUsername(), "UTF-8")+"/home";
		}else {
			request.setAttribute("code", "账号或密码错误！");
			return "result";
		}
	}

	/**
	 * 检验验证码
	 */
	@RequestMapping(value="/checkVerifyCode", method=RequestMethod.POST)
	@ResponseBody
	public Msg checkVerifyCode(HttpServletRequest request) throws ServletException, IOException {
		// 获得生成的验证码
		String verifyCode = (String) request.getSession().getAttribute("verifyCode");
		// 获得用户输入的验证码
		String inputVerifyCode = request.getParameter("inputVerifyCode");
		// 不区分大小写
		if (verifyCode.toLowerCase().equals(inputVerifyCode.toLowerCase())) {
			return Msg.success("验证成功");
		}
		return Msg.fail("验证失败");
	}
	
	/**
	 * 验证管理员的登录
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/adminlogin.do")
	public String adminLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 获得管理员输入的账号和密码
		Integer id = Integer.parseInt(request.getParameter("id"));
		String password= request.getParameter("password");
		// MD5加密后进行验证
		password = MD5Utils.getMD5(password);
		
		Admin checkAdmin = null;
		Admin admin = new Admin(id, password);
		checkAdmin = adminService.checkAdminLogin(admin);
		// 如果成功则到管理员后台的首页，否则重新登录
		if (checkAdmin != null){
			// 保存用户信息
			HttpSession session = request.getSession();
			session.setAttribute("loginAdmin", checkAdmin);
			return "/WEB-INF/admin/noticeManager";
		}else {
			return "adminlogin";
		}
	}
	
	
	/**
	 * 用户退出登录，清空网站保存的登录的cookie的值，并使得session失效
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/logout.do")
	public String userLogout(HttpServletRequest request, HttpServletResponse response) {
		// 清空cookie的值，当cookie有相同的Name时候，就会覆盖原来的value
		Cookie cookie = new Cookie(cookieFreeBlogsName, null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		// 使session失效
		HttpSession session =request.getSession();
		session.invalidate();
		return "login";
	}
	
	/**
	 * 管理员退出登录，清空网站保存的登录的cookie的值，并使得session失效
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/adminlogout.do")
	public String adminLogout(HttpServletRequest request, HttpServletResponse response) {
		// 使session失效
		HttpSession session =request.getSession();
		session.invalidate();
		return "adminlogin";
	}
	
	/**
	 * 跳转到忘记密码界面
	 * @return
	 */
	@RequestMapping("/toForgetPassword.do")
	public String toForgetPassword(){
		return "forgetPassword";
	}
	
	/**
	 * 判断邮箱是否存储，只有存在了才会给该 邮箱发送验证码
	 * @param email
	 * @return
	 */
	@RequestMapping(value="/findPassword.do", method=RequestMethod.GET)
	@ResponseBody
	public Msg findPassword(@RequestParam(value="email")String email){
		User user = new User();
		user.setUserEmail(email);
		Integer result = userService.selectExistUserByUserEmail(user);
		// 不为null，说明存在该邮箱
		if (result != null) {
			return Msg.success();
		}
		return Msg.fail();
	}
	
	/**
	 * 找回密码的时候，发送验证码到用户邮箱
	 * @description 
	 * @param email
	 * @return
	 */
	@RequestMapping(value="/sendCodeForfindPassword.do", method=RequestMethod.GET)
	@ResponseBody
	public Msg sendCodeForfindPassword(@RequestParam(value="email")String email){
		Random rand = new Random();
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i<6; i++){
			sb.append(rand.nextInt(9));
		}
		// 随机生成六位验证码
		String code = sb.toString();
		// 邮件配置
		String from = "hanaeyuuma@163.com";
		String password = "chen9201314";
		String to = email;
		String subject = "FreeBlogs用户中心邮件验证码(系统自动邮件，请勿回复)";
		String content = "您此次的邮箱验证码是"+code+"<br/>"
				+ "如果您并未发过此请求，则可能是因为其他用户误输入了您的电子邮件地址而使您收到这封邮件，那么您可以放心的忽略此邮件，无需进一步采取任何操作。";
		SendEmailUtils sendEmail = new SendEmailUtils();
		try {
			sendEmail.send(from, password, to, subject, content);
		} catch (Exception e) {
			return Msg.fail("验证码发送失败！");
		}
		return Msg.success().add("code", code);
	}
	
	/**
	 * 验证用户邮箱成功，随机生成八位新密码返回给用户
	 * @description 未实现
	 * @param email
	 * @return
	 */
	@RequestMapping(value="/newPassword.do", method=RequestMethod.GET)
	@ResponseBody
	public Msg newPassword(@RequestParam(value="email")String email){
		Random rand = new Random();
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i<8; i++){
			sb.append(rand.nextInt(9));
		}
		// 随机生成八位新密码返回给用户
		String code = sb.toString();
		
		User user = new User();
		// 加密存储
		user.setUserPassword(MD5Utils.getMD5(code)); 
		user.setUserEmail(email);
		userService.updatePasswordByEmail(user);

		return Msg.success().add("code", "您好的新密码是："+code+"，为了你的账号安全，请登录后立即修改！");
	}
}

package com.akanemurakawa.kaguya.controller;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanaeyuuma.freeblogs.model.Article;
import com.hanaeyuuma.freeblogs.model.Category;
import com.hanaeyuuma.freeblogs.model.Friend;
import com.hanaeyuuma.freeblogs.model.Msg;
import com.hanaeyuuma.freeblogs.model.Tag;
import com.hanaeyuuma.freeblogs.model.User;
import com.hanaeyuuma.freeblogs.service.ArticleService;
import com.hanaeyuuma.freeblogs.service.CategoryService;
import com.hanaeyuuma.freeblogs.service.FriendService;
import com.hanaeyuuma.freeblogs.service.TagService;
import com.hanaeyuuma.freeblogs.service.UserService;
import com.hanaeyuuma.freeblogs.util.MD5Utils;
import com.hanaeyuuma.freeblogs.util.SendEmailUtils;

/**
 * 用于用户注册的控制器
 * 
 * @author HanaeYuuma
 * @date 2018-8-15
 */
@Controller
public class RegisteController {

	@Autowired
	private UserService userService;

	@Autowired
	private ArticleService articleService;

	@Autowired
	private FriendService friendService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private TagService tagService;

	/**
	 * 验证用户名是否被占用
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping("/user/registe/checkName")
	@ResponseBody
	public Msg checkName(@RequestParam("username") String username) {
		if (null != userService.checkExistUserName(username)) {
			return Msg.fail();
		}
		return Msg.success();
	}

	/**
	 * 验证邮箱是否被注册
	 * 
	 * @param email
	 * @return
	 */
	@RequestMapping("/user/registe/checkEmail")
	@ResponseBody
	public Msg checkEmail(@RequestParam("email") String email) {
		User user = new User();
		user.setUserEmail(email);
		if (null != userService.selectExistUserByUserEmail(user)) {
			return Msg.fail();
		}
		return Msg.success();
	}

	/**
	 * 发送邮箱注册码
	 * 
	 * @param email
	 * @return
	 */
	@RequestMapping("/user/registe/sendEmailForVerifyCode")
	@ResponseBody
	public Msg sendEmailForVerifyCode(@RequestParam("email") String email, HttpServletRequest request) {
		Random rand = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			sb.append(rand.nextInt(9));
		}
		// 随机生成四位验证码返回给用户
		String code = sb.toString();

		String from = "hanaeyuuma@163.com";
		String password = "chen9201314";
		String subject = "FreeBlogs用户中心邮件验证码(系统自动邮件，请勿回复)";
		String content = "欢迎来到FreeBlogs，您此次的注册账号的邮箱验证码是<b>" + code + "<b/>，五分钟内有效。<br/>"
				+ "如果您并未发过此请求，则可能是因为其他用户误输入了您的电子邮件地址而使您收到这封邮件，那么您可以放心的忽略此邮件，无需进一步采取任何操作。";
		SendEmailUtils sendEmail = new SendEmailUtils();
		try {
			sendEmail.send(from, password, email, subject, content);
			request.getSession().setAttribute("registeVerifyCode", code);
			request.getSession().setMaxInactiveInterval(5*60); // 单位秒
			return Msg.success();
		} catch (Exception e) {
			return Msg.fail();
		}
	}

	/**
	 * 验证邮箱注册码
	 * 
	 * @param email
	 * @return
	 */
	@RequestMapping("/user/registe/checkVerifyCode")
	@ResponseBody
	public Msg checkVerifyCode(@RequestParam("inputVerifyCode") String inputVerifyCode, HttpServletRequest request) {
		// 生成的验证码
		String code = (String) request.getSession().getAttribute("registeVerifyCode");
		if (!inputVerifyCode.equals(code)) {
			return Msg.fail("验证失败");
		}
		return Msg.success("验证成功");
	}

	/**
	 * 验证邮箱注册码
	 * 
	 * @param email
	 * @return
	 */
	@RequestMapping("/user/registe.do")
	public String registe(HttpServletRequest request) {
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		password = MD5Utils.getMD5(password);
		String sex = request.getParameter("sex");
		User user = new User(username, password, email, sex);
		userService.insertSelective(user);

		/**
		 * 以下为注册用户时候的默认
		 */
		// 使用mybatis的配置，插入成功自动获得自增的主键
		Integer userId = user.getId();
		Friend friend = new Friend(userId, "FreeBlogs", "http://www.google.com");
		friendService.insert(friend);
		friendService.insert(friend);
		friendService.insert(friend);

		Article newArticle = new Article(userId, "Welcome to FreeBlogs.", null, "HelloWorld", "HelloWorld");
		articleService.insert(newArticle);
		// 使用mybatis的配置，插入成功自动获得自增的主键
		Integer articleId = newArticle.getId();

		Tag newTag = new Tag(null, articleId, userId, "HelloWorld");
		Category newCategory = new Category(null, articleId, userId, "HelloWorld");
		tagService.insertSelective(newTag);
		categoryService.insertSelective(newCategory);
		request.setAttribute("code", "恭喜您，注册成功！");
		return "result";
	}

}

package com.akanemurakawa.kaguya.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hanaeyuuma.freeblogs.model.Article;
import com.hanaeyuuma.freeblogs.model.Comment;
import com.hanaeyuuma.freeblogs.model.Follower;
import com.hanaeyuuma.freeblogs.model.Msg;
import com.hanaeyuuma.freeblogs.model.User;
import com.hanaeyuuma.freeblogs.service.ArticleService;
import com.hanaeyuuma.freeblogs.service.UserService;

/**
 * 
 * @author HanaeYuuma
 * @date 2018-7-15
 */
@Controller
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private UserService userService;

	/**
	 * 点击阅读更多时候，查看文章的详细内容，通过id查询 并返回上一篇和下一篇的Article的对象，如果不存在则返回""给前台判断显示
	 * 
	 * @param title
	 * @return
	 */
	@RequestMapping("/p/{uid}/article")
	public ModelAndView detail(@RequestParam(value = "articleId") Integer articleId, @PathVariable("uid") Integer uid,
			HttpServletRequest request, @RequestParam(value = "pn", defaultValue = "1") Integer pn) {
		ModelAndView mav = new ModelAndView();
		// 当前需要阅读的文章
		Article article = articleService.selectByPrimaryKey(articleId);
		mav.addObject("Article", article);

		Integer minId = articleService.selectMinIdByUserId(uid);
		Integer maxInd = articleService.selectMaxIdByUserId(uid);
		// 如果还有上一篇文章则查询标题，否则返回其他提示字符串
		Article tempArticle = new Article();
		tempArticle.setId(articleId);
		tempArticle.setUserId(uid);
		if (articleId <= minId) {
			String previousArticle = ""; // 没有上一篇了，传入一个""过去进行判断
			mav.addObject("previousArticle", previousArticle);
		} else {
			Article previousArticle = articleService.selectPreTitleByPrimaryKey(tempArticle);
			mav.addObject("previousArticle", previousArticle);
		}
		// 如果还有下一篇则查询
		if (articleId >= maxInd) {
			String nextArticle = ""; // 没有下一篇了，传入一个""过去进行判断
			mav.addObject("nextArticle", nextArticle);
		} else {
			Article nextArticle = articleService.selectNextTitleByPrimaryKey(tempArticle);
			mav.addObject("nextArticle", nextArticle);
		}
		// 更新阅读点击次数，做一个标识放进session中，不同文章的标识通过id区别
		String isHit = (String) request.getSession().getAttribute("isHit" + articleId);
		if (null == isHit) {
			request.getSession().setAttribute("isHit" + articleId, "true");
			articleService.updateHitByPrimaryKey(articleId);
		}

		User user = userService.selectByPrimaryKey(uid);
		mav.addObject("user", user);

		// 判断是否是粉丝和是否关注
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("loginUser"); // 当前登录用户
		if (sessionUser != null) {
			Follower follow = new Follower(uid, sessionUser.getId());
			Integer isFollower = userService.isFollow(follow); // 是否是粉丝
			follow.setUserId(sessionUser.getId());
			follow.setFollowingId(uid);
			Integer isFolowing = userService.isFollow(follow); // 是否关注
			mav.addObject("isFollower", isFollower);
			mav.addObject("isFolowing", isFolowing);
		}

		// 查看评论
		PageHelper.startPage(pn, 5);
		List<Comment> comments = userService.selectComment(articleId);
		// 封装详细的分页信息，传入连续显示的页数
		PageInfo page = new PageInfo(comments, 5);
		mav.addObject("pageInfo", page);
		mav.setViewName("article_detail");
		return mav;
	}

	/**
	 * 写文章的时候验证标题是否存在
	 * 
	 * @param title
	 * @return
	 */
	@RequestMapping("/checkTitleExists")
	@ResponseBody
	public Msg checkTitleExists(@RequestParam(value = "title") String title) {
		String article = articleService.selectContentByTitle(title);
		if (article != null) {
			return Msg.fail();
		}
		return Msg.success();
	}

}

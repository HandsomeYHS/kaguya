package com.akanemurakawa.kaguya.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hanaeyuuma.freeblogs.model.Article;
import com.hanaeyuuma.freeblogs.model.Follower;
import com.hanaeyuuma.freeblogs.model.Msg;
import com.hanaeyuuma.freeblogs.model.Notice;
import com.hanaeyuuma.freeblogs.model.Tag;
import com.hanaeyuuma.freeblogs.model.User;
import com.hanaeyuuma.freeblogs.service.ArticleService;
import com.hanaeyuuma.freeblogs.service.NoticeService;
import com.hanaeyuuma.freeblogs.service.TagService;
import com.hanaeyuuma.freeblogs.service.UserService;

/**
 * 
 * @author HanaeYuuma
 * @date 2018-7-8
 */
@Controller
public class IndexController {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private UserService userService;

	@Autowired
	private TagService tagService;

	@Autowired
	private NoticeService noticeService;

	/**
	 * 首页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView toIndex() {
		ModelAndView mav = new ModelAndView();
		// 推荐关注，那当然就是我自己了。id为1
		User user = userService.selectByPrimaryKey(1);
		mav.addObject("siteUser", user);
		mav.setViewName("freeblogs");
		return mav;
	}
	
	/**
	 * 通过ajax来获取更多的文章
	 * @param pn
	 * @return
	 */
	@RequestMapping(value="/indexArticle", method=RequestMethod.GET)
	@ResponseBody
	public Msg getArticleWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
		ModelAndView mav = new ModelAndView();
		// Mybatis的分页插件， param:(pageNow, pageSize)
		PageHelper.startPage(pn, 10);
		List<Article> articles = articleService.getAllArticle();
		// 封装详细的分页信息，传入连续显示的页数
		PageInfo page = new PageInfo(articles, 5);
		return Msg.success().add("pageInfo", page);
	}

	/**
	 * 访问用户主页，传入相应的数据，使用Mybatis的分页插件进行分页查询文章
	 * 
	 * @param pn
	 * @return
	 */
	@RequestMapping("/p/{username}/home")
	public ModelAndView toHome(@RequestParam(value = "pn", defaultValue = "1") Integer pn,
			@PathVariable("username") String username, HttpServletRequest request) {
		Integer uid = userService.checkExistUserName(username);

		ModelAndView mav = new ModelAndView();
		// Mybatis的分页插件， param:(pageNow, pageSize)
		PageHelper.startPage(pn, 5);
		List<Article> articles = articleService.getUserAllArticle(uid); // 查看用户的所有文章
		// 封装详细的分页信息，传入连续显示的页数
		PageInfo page = new PageInfo(articles, 5);

		User user = userService.selectByPrimaryKeyWithFriend(uid);
		Notice notice = noticeService.selectRecentOneOrderByDate(); // 查看最新的一条公告，每个用户都看到的
		List<Tag> tags = tagService.selectTagDistinct(uid); // 查询用户的所有标签，不重复
		List<Article> recentArticles = articleService.selectRecentArticles(uid); // 近期文章
		List<Article> hotArticles = articleService.selectHotArticles(uid); // 热门文章
		List<Integer> follower = userService.getFollowerIds(uid); // 粉丝
		List<Integer> following = userService.getFollowingIds(uid); // 关注

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

		mav.addObject("user", user);
		mav.addObject("recentArticles", recentArticles);
		mav.addObject("hotArticles", hotArticles);
		mav.addObject("pageInfo", page);
		mav.addObject("notice", notice);
		mav.addObject("tags", tags);
		mav.addObject("follower", follower.size());
		mav.addObject("following", following.size());
		mav.setViewName("home");
		return mav;
	}

	/**
	 * 跳转到日志界面
	 * 
	 * @return
	 */
	@RequestMapping("/p/{username}/archives")
	public String toArchives(@PathVariable(value = "username") String username, Model model,
			HttpServletRequest request) {
		Integer uid = userService.checkExistUserName(username);
		List<Article> archivesArticles = articleService.selectAllBaseInfo(uid);
		User user = userService.selectByPrimaryKey(uid);
		model.addAttribute("archivesArticles", archivesArticles);
		model.addAttribute("total", archivesArticles.size()); // 共有多少篇
		model.addAttribute("user", user);

		// 判断是否是粉丝和是否关注
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("loginUser"); // 当前登录用户
		if (sessionUser != null) {
			Follower follow = new Follower(uid, sessionUser.getId());
			Integer isFollower = userService.isFollow(follow); // 是否是粉丝
			follow.setUserId(sessionUser.getId());
			follow.setFollowingId(uid);
			Integer isFolowing = userService.isFollow(follow); // 是否关注
			model.addAttribute("isFollower", isFollower);
			model.addAttribute("isFolowing", isFolowing);
		}
		return "archives";
	}

	/**
	 * 用户跳转到好友动态界面(只限用户关注的人)，这里需要登录后才能操作。
	 * 
	 * @return
	 */
	@RequestMapping("/p/{username}/dynamic")
	public String toDynamic(@RequestParam(value = "pn", defaultValue = "1") Integer pn,
			@PathVariable(value = "username") String username, Model model) {
		Integer uid = userService.checkExistUserName(username);
		User user = userService.selectByPrimaryKey(uid);
		// 获得所有关注的人id
		List<Integer> uids = userService.getFollowingIds(uid);
		if (0 == uids.size()) {
			System.out.println("null");
			model.addAttribute("pageInfo", null);
		}else {
			// 一次显示十条
			PageHelper.startPage(pn, 10);
			List<Article> articles = articleService.getAllFollowingArticle(uids);
			// 封装详细的分页信息，传入连续显示的页数
			PageInfo page = new PageInfo(articles, 5);
			model.addAttribute("pageInfo", page);
		}
		model.addAttribute("user", user);
		return "dynamic";
	}

	/**
	 * 跳转到关于界面
	 * 
	 * @return
	 */
	@RequestMapping("/about")
	public String toAbout() {
		return "about";
	}

	/**
	 * 模糊搜索GET
	 * @param pn
	 * @param content
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String search(@RequestParam(value = "pn", defaultValue = "1") Integer pn,
			@RequestParam(value="keyWord",required = false) String keyWord, Model model) {
		PageHelper.startPage(pn, 10);
		keyWord = keyWord.trim();
		List<Article> articles = articleService.search(keyWord);
		PageInfo page = new PageInfo(articles, 5);
		model.addAttribute("pageInfo", page);
		model.addAttribute("keyWord", keyWord);
		return "search_result";
	}
	
}

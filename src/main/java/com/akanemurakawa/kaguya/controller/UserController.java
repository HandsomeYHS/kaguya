package com.akanemurakawa.kaguya.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hanaeyuuma.freeblogs.model.Article;
import com.hanaeyuuma.freeblogs.model.Category;
import com.hanaeyuuma.freeblogs.model.Comment;
import com.hanaeyuuma.freeblogs.model.Friend;
import com.hanaeyuuma.freeblogs.model.Msg;
import com.hanaeyuuma.freeblogs.model.Notice;
import com.hanaeyuuma.freeblogs.model.Tag;
import com.hanaeyuuma.freeblogs.model.User;
import com.hanaeyuuma.freeblogs.service.ArticleService;
import com.hanaeyuuma.freeblogs.service.CategoryService;
import com.hanaeyuuma.freeblogs.service.FriendService;
import com.hanaeyuuma.freeblogs.service.NoticeService;
import com.hanaeyuuma.freeblogs.service.TagService;
import com.hanaeyuuma.freeblogs.service.UserService;
import com.hanaeyuuma.freeblogs.util.DateFormatUtils;
import com.hanaeyuuma.freeblogs.util.HTMLToTextUtils;
import com.hanaeyuuma.freeblogs.util.MD5Utils;
import com.hanaeyuuma.freeblogs.util.MarkdownUtils;

/**
 * 处理用户请求
 * 
 * @author HanaeYuuma
 * @date 2018-7-18
 */
@Controller
@RequestMapping("/user")
public class UserController {

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

	@Autowired
	private NoticeService noticeService;

	/**
	 * 用户后台 跳转到通知公告界面，传最新的一条公告过去，其他的分页在界面加载完毕再发送ajax请求
	 * 
	 * @return
	 */
	@RequestMapping("/toNotice.do")
	public String toNotice(Model model) {
		Notice notice = noticeService.selectRecentOneOrderByDate();
		model.addAttribute("notice", notice);
		return "WEB-INF/user/notice";
	}

	/**
	 * 用户后台 跳转到用户基本资料界面
	 * 
	 * @return
	 */
	@RequestMapping("/toInfo.do")
	public String toInfo(HttpServletRequest request) {
		return "WEB-INF/user/info";
	}

	/**
	 * 用户后台 跳转到系统设置界面
	 * 
	 * @return
	 */
	@RequestMapping("/toSetting.do")
	public String toSetting(Model model) {
		return "WEB-INF/user/setting";
	}

	/**
	 * 用户后台 阅读公告，点击公告标题的时候切换
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getNotice", method = RequestMethod.GET)
	@ResponseBody
	public Msg getNotice(@RequestParam(value = "id") Integer id) {
		Notice notice = noticeService.selectByPrimaryKey(id);
		return Msg.success().add("notice", notice);
	}

	/**
	 * 用户后台 跳转到用户修改资料界面
	 * 
	 * @return
	 */
	@RequestMapping("/toModifyInfo.do")
	public String toModifyInfo(Model model) {
		return "WEB-INF/user/modifyInfo";
	}

	/**
	 * 用户后台 头像上传 
	 * 0. 删除之前的头像 
	 * 1. 首先获得上传文件的目录位置 
	 * 2. 图片过滤器
	 * 3. 生成新的文件名称 
	 * 4. 将内存中的数据写入到磁盘中
	 * 5. 更新到数据库中 
	 * 6. 修改成功，重新从数据库查询该用户的信息，更新到session中
	 * 
	 * @param id
	 * @param avatarFile
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upLoadAvatar", method = RequestMethod.POST)
	public String uploadAvatarImg(@RequestParam(value = "id") Integer id,
			@RequestParam(value = "upload-avatar") MultipartFile avatarFile, HttpServletRequest request) {
		/*
		 * 0.删除之前的头像
		 */
		userService.deleteAvatar(id);

		/*
		 * 1.上传文件夹的位置
		 */
		String savePath = "/upload/img/avatar/";
		String rootPath = request.getServletContext().getRealPath(savePath);
		// 上传的原始文件的名称， 打印输出originalFilename为：27891114_p0_中野梓.jpg
		String originalFilename = avatarFile.getOriginalFilename();
		// 上传文件的时间 ，打印输出upLoadTime为：20180721192712664
		String upLoadTime = DateFormatUtils.getNowTime("yyyyMMddHHmmssSS");

		/*
		 * 2.图片过滤器
		 */
		// 图片文件格式过滤器
		// 文件扩展名 ，取出图片的格式， 打印输出fileFormat为：.jpg
		String fileFormat = originalFilename.substring(originalFilename.lastIndexOf("."));
		if (!".jpg".equals(fileFormat) && !".png".equals(fileFormat)) {
			request.setAttribute("code", "上传失败 ，无效图片格式 ，请选择jpg或者png格式！");
			return "WEB-INF/user/result";
		}
		// 图片文件大小过滤器
		long fileSize = avatarFile.getSize();
		if (fileSize > (10 * 1024 * 1024)) {
			request.setAttribute("code", "上传失败 ，文件大小不能超过10MB！");
			return "WEB-INF/user/result";
		}

		/*
		 * 3. 新的文件名称， 防止文件名异常，通过id和时间标识唯一性.打印输出newFilename为：1_20180721192712664.jpg
		 */
		// 创建年月文件夹
		Calendar date = Calendar.getInstance();
		int year = date.get(Calendar.YEAR);
		int month = (date.get(Calendar.MONTH) + 1);
		File dateDirs = new File(year + File.separator + month);
		// 新的文件名称
		String newFilename = "avatar_" + id + "_" + upLoadTime + fileFormat;
		File tempFile = new File(rootPath + File.separator + dateDirs, newFilename);
		// 如果目标文件所在的目录不存在则进行创建
		if (!tempFile.exists()) {
			tempFile.mkdirs();
		}

		/*
		 * 4.将内存中的数据写入到磁盘中
		 */
		try {
			avatarFile.transferTo(tempFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		 * 5.更新到数据库中 关于路径问题：用户上传的图片保存在相应的服务器中，因此这里根据服务器的url地址来存储访问， 并且需要配置tomcat的虚拟目录
		 */
		String scheme = request.getScheme(); // 请求协议
		String serverName = request.getServerName(); // 主机名
		int serverPort = request.getServerPort(); // 端口
		// http:/localhost:8080/项目名/upload/img/avatar/2018/7/1_20180721192712664.jpg
		String sqlPath = scheme + "://" + serverName + ":" + serverPort + "/FreeBlogs" + savePath + year + "/" + month
				+ "/" + newFilename;
		User user = new User();
		user.setId(id);
		user.setUserAvatar(sqlPath);
		int result = userService.updateAvatar(user);
		if (1 == result) {
			request.setAttribute("code", "头像修改成功！");
		}

		/*
		 * 6.修改成功，重新从数据库查询该用户的信息，更新到session中
		 */
		HttpSession session = request.getSession();
		User newUser = userService.selectByPrimaryKey(id);
		session.setAttribute("loginUser", newUser);
		return "WEB-INF/user/result";
	}

	/**
	 * 用户后台 修改资料：只有输入了才进行更新，否则跳过。用户名不能重复
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/modifyInfo", method = RequestMethod.POST)
	public String modifyInfo(@RequestParam(value = "id") Integer id, HttpServletRequest request) {
		String username = request.getParameter("username");
		String avatar = request.getParameter("avatar");
		String github = request.getParameter("github");
		String twitter = request.getParameter("twitter");
		String weibo = request.getParameter("weibo");
		String description = request.getParameter("description");

		String[] friendId = request.getParameterValues("friendId");
		String[] friendName = request.getParameterValues("friendName");
		String[] friendLink = request.getParameterValues("friendLink");

		Boolean isModifyFriendSuccess = true;
		for (int i = 0; i < friendId.length; i++) {
			// 如果没有输入就跳过，否则进行修改
			if (friendName[i] == "" && friendLink[i] == "") {
				continue;
			} else {
				Friend friend = new Friend(Integer.parseInt(friendId[i]), id, friendName[i], friendLink[i]);
				isModifyFriendSuccess = friendService.modifyFriendInfo(friend);
			}
		}

		Boolean isModifyUserSuccess = true;
		User user = new User(id, username, avatar, description, github, twitter, weibo);
		// 如果用户填写的用户信息不为空才进行修改
		if (!"".equals(username) || !"".equals(avatar) || !"".equals(github) || !"".equals(twitter) || !"".equals(weibo)
				|| !"".equals(description)) {
			isModifyUserSuccess = userService.modifyUserInfo(user);
		}

		if (isModifyUserSuccess == true && isModifyFriendSuccess == true) {
			// 如果修改成功，重新从数据库查询该用户的信息，更新到session中
			HttpSession session = request.getSession();
			User newUser = userService.selectByPrimaryKey(id);
			session.setAttribute("loginUser", newUser);
			request.setAttribute("code", "资料修改成功！");
		} else if (isModifyUserSuccess == false) {
			request.setAttribute("code", "该用户名已经存在！");
		} else if (isModifyFriendSuccess == false) {
			request.setAttribute("code", "友情链接修改失败！");
		}
		return "WEB-INF/user/result";
	}

	/**
	 * 用户后台 修改密码 ResponseBody: 自动把的返回对象转为JSON字符串(需要导入jackson包)
	 * 
	 * @param id
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	@RequestMapping(value = "/modifyPassword", method = RequestMethod.PUT)
	@ResponseBody
	public Msg modifyPassword(@RequestParam(value = "id") Integer id,
			@RequestParam(value = "oldPassword") String oldPassword,
			@RequestParam(value = "newPassword") String newPassword) {
		// 先进行MD5加密后再认证
		oldPassword = MD5Utils.getMD5(oldPassword);
		newPassword = MD5Utils.getMD5(newPassword);
		return userService.modifyUserPassword(id, oldPassword, newPassword);
	}

	/**
	 * 用户后台 跳转到用户文章管理界面
	 * 
	 * @return
	 */
	@RequestMapping("/toArticleManager.do")
	public String toArticleManager(@RequestParam(value = "pageNow", defaultValue = "1") Integer pageNow,
			@RequestParam(value = "username") String username, Model model) {
		Integer uid = userService.checkExistUserName(username);
		PageHelper.startPage(pageNow, 5);
		List<Article> articles = articleService.getUserAllArticle(uid);
		// 封装详细的分页信息，传入连续显示的页数
		PageInfo page = new PageInfo(articles, 5);
		model.addAttribute("pageInfo", page);
		return "WEB-INF/user/articleManager";
	}

	/**
	 * 用户后台 分页查询文章 ResponseBody: 自动把的返回对象转为JSON字符串(需要导入jackson包)
	 * 
	 * @return
	 */
	@RequestMapping("/article")
	@ResponseBody
	public Msg getArticleWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn,
			@RequestParam(value = "uid") Integer uid) {
		// Mybatis的分页插， param:(pageNow, pageSize)
		PageHelper.startPage(pn, 5);
		List<Article> articles = articleService.getUserAllArticle(uid);
		// 封装详细的分页信息，传入连续显示的页数
		PageInfo page = new PageInfo(articles, 5);
		return Msg.success().add("pageInfo", page);
	}

	/**
	 * 用户后台 跳转到编写文章界面
	 * 
	 * @return
	 */
	@RequestMapping("/toWriteArticle.do")
	public String toWriteArticle() {
		return "WEB-INF/user/writeArticle";
	}

	/**
	 * 用户后台 写文章
	 * 说明：这里用户写文章的时候是通过markdown来编辑，因此需要保存用户填写的markdown文件内容，便于用户修改文章的时候得到的是markdown格式的文本内容
	 * markdown文件存储在磁盘中：用户写文章和修改文章都是用markdown html文件存储在数据中：将markdown转为html，用于文章界面的显示
	 * 1.获取数据 
	 * 2.存储文章内容的markdown文件
	 * 3.插入文章，通过mybatis配置获得自增的主键 
	 * 4.插入标签和分类
	 * 
	 * @return
	 */
	@RequestMapping(value = "/writeArticle", method = RequestMethod.POST)
	public String writeArticle(@RequestParam(value = "uid") Integer uid, HttpServletRequest request) {
		String articleTitle = request.getParameter("title");
		String tagName = request.getParameter("tag");
		String categoryName = request.getParameter("category");
		String articleContent = request.getParameter("contentHTML"); // HTML 格式， 在前台直接传入的是HTMl，而不是markdown
		/*
		 * 保存用户编写的markdown文件
		 */
		String markdownContent = request.getParameter("content"); // markdown 格式
		String savePath = request.getServletContext().getRealPath("/source/articles_md/");
		MarkdownUtils.saveMarkdown(markdownContent, articleTitle, savePath);

		/*
		 * 这里需要为文章摘要的存储转换为纯文本格式 文章摘要选取内容的前150，如果不足这么多字则选取全文
		 */
		String articleSumary = HTMLToTextUtils.getSimpleText(articleContent); // 纯文本 格式
		if (articleSumary.length() > 150) {
			articleSumary = articleSumary.substring(0, 150);
		}

		Article newArticle = new Article(uid, articleTitle, null, articleSumary, articleContent);
		articleService.insert(newArticle);
		// 使用mybatis的配置，插入成功自动获得自增的主键
		Integer articleId = newArticle.getId();

		Tag newTag = new Tag(null, articleId, uid, tagName);
		Category newCategory = new Category(null, articleId, uid, categoryName);
		tagService.insertSelective(newTag);
		categoryService.insertSelective(newCategory);
		// 添加成功，跳转到文章管理界面查看。
		return "WEB-INF/user/articleManager";
	}

	/**
	 * 用户后台 写文章的时候接收上传的图片，这里的逻辑和上传头像一样，但是因为Editormd 要求按照指定的格式返回，所以我这里就没有封装。
	 * 
	 * @param file    该value="editormd-image-file"是官方指定的。
	 * @param request
	 * @return
	 */
	@RequestMapping("/uploadImgEditormd")
	@ResponseBody
	public Map<String, Object> uploadImgEditormd(
			@RequestParam(value = "editormd-image-file", required = true) MultipartFile file,
			@RequestParam(value = "uid") Integer uid, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		/*
		 * 1.上传文件夹的位置
		 */
		String savePath = "/upload/img/article/";
		String rootPath = request.getServletContext().getRealPath(savePath);
		// 上传的原始文件的名称， 打印输出originalFilename为：123123
		String originalFilename = file.getOriginalFilename();
		// 上传文件的时间 ，打印输出upLoadTime为：20180721192712664
		String upLoadTime = DateFormatUtils.getNowTime("yyyyMMddHHmmssSS");

		/*
		 * 2.图片过滤器
		 */
		// 图片文件格式过滤器
		// 文件扩展名 ，取出图片的格式， 打印输出fileFormat为：.jpg
		String fileFormat = originalFilename.substring(originalFilename.lastIndexOf("."));
		if (!".jpg".equals(fileFormat) && !".png".equals(fileFormat)) {
			resultMap.put("success", 0);
			resultMap.put("message", "上传失败 ，无效图片格式 ，请选择jpg或者png格式！");
		}
		// 图片文件大小过滤器
		long fileSize = file.getSize();
		if (fileSize > (10 * 1024 * 1024)) {
			resultMap.put("success", 0);
			resultMap.put("message", "上传失败 ，文件大小不能超过10MB！");
		}

		/*
		 * 3. 新的文件名称， 防止文件名异常，通过时间标识唯一性.打印输出newFilename为：20180721192712664.jpg
		 */
		// 创建年月文件夹
		Calendar date = Calendar.getInstance();
		int year = date.get(Calendar.YEAR);
		int month = (date.get(Calendar.MONTH) + 1);
		File dateDirs = new File(year + File.separator + month);
		// 新的文件名称
		String newFilename = "article_" + uid + "_" + upLoadTime + fileFormat;
		File tempFile = new File(rootPath + File.separator + dateDirs, newFilename);
		// 如果目标文件所在的目录不存在则进行创建
		if (!tempFile.exists()) {
			tempFile.mkdirs();
		}

		/*
		 * 4.将内存中的数据写入到磁盘中
		 */
		try {
			file.transferTo(tempFile);
			resultMap.put("success", 1);
			resultMap.put("message", "上传成功！");
			resultMap.put("url",
					request.getContextPath() + savePath + year + File.separator + month + File.separator + newFilename);
		} catch (IllegalStateException e) {
			resultMap.put("success", 0);
			resultMap.put("message", "上传失败！");
			e.printStackTrace();
		} catch (IOException e) {
			resultMap.put("success", 0);
			resultMap.put("message", "上传失败！");
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * 用户后台 跳转到修改文章界面
	 * 
	 * @return
	 */
	@RequestMapping("/toModifyArticle.do")
	public String toModifyArticle(@RequestParam(value = "id") Integer id, Model model, HttpServletRequest request) {
		Article article = articleService.selectBaseByPrimaryKey(id);
		String tagName = tagService.selectByArticlePrimaryKey(id).getTagName();
		String categoryName = categoryService.selectByArticlePrimaryKey(id).getCategoryName();

		// 读取文章的md格式传到修改界面
		String path = request.getServletContext().getRealPath("/source/articles_md/");
		String content = MarkdownUtils.getMarkdown(article.getArticleTitle(), path);
		article.setArticleContent(content);
		model.addAttribute("article", article);
		model.addAttribute("tagName", tagName);
		model.addAttribute("categoryName", categoryName);
		return "WEB-INF/user/modifyArticle";
	}

	/**
	 * 用户后台 修改文章 
	 * 1.首先获取数据 
	 * 2.到数据库中更新 
	 * 3.删除之前的md文件 
	 * 4.生成新的md文件
	 * 
	 * @return
	 */
	@RequestMapping(value = "/modifyArticle", method = RequestMethod.POST)
	public String modifyArticle(@RequestParam(value = "id") Integer articleId, HttpServletRequest request) {
		String articleTitle = request.getParameter("title");
		String tagName = request.getParameter("tag");
		String categoryName = request.getParameter("category");
		String articleContent = request.getParameter("contentHTML"); // HTML 格式， 在前台直接传入的是HTMl，而不是markdown

		/*
		 * 这里需要为文章摘要的存储转换为纯文本格式 文章摘要选取内容的前150，如果不足这么多字则选取全文
		 */
		String articleSumary = HTMLToTextUtils.getSimpleText(articleContent); // 纯文本 格式
		if (articleSumary.length() > 150) {
			articleSumary = articleSumary.substring(0, 150);
		}
		// 到数据库中更新
		Tag newTag = new Tag(tagName);
		Category newCategory = new Category(categoryName);
		Article newArticle = new Article(articleId, articleTitle, articleSumary, articleContent, newCategory, newTag);
		articleService.updateArticleByPrimaryKey(newArticle);

		String savePath = request.getServletContext().getRealPath("/source/articles_md/");
		// 删除之前的md文件
		String oldTitle = request.getParameter("oldTitle"); // 旧标题通过隐藏的属性传入。
		MarkdownUtils.removeMarkdown(oldTitle, savePath);
		// 生成新的md文件
		String markdownContent = request.getParameter("content"); // markdown 格式
		MarkdownUtils.saveMarkdown(markdownContent, articleTitle, savePath);

		// 添加成功，跳转到文章管理界面查看。
		return "WEB-INF/user/articleManager";
	}

	/**
	 * 用户后台 单个删除文章 ResponseBody: 自动把的返回对象转为JSON字符串(需要导入jackson包) PathVariable:
	 * 用来获得请求url中的动态参数的
	 * 
	 * @return
	 */
	@RequestMapping(value = "/deleteArticle/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Msg deleteArticle(@PathVariable("id") Integer id, HttpServletRequest request) {

		String savePath = request.getServletContext().getRealPath("/source/articles_md/");
		// 删除文章之前需要把保存的md文件删除
		String oldTitle = articleService.selectTitleByPrimaryKey(id);
		MarkdownUtils.removeMarkdown(oldTitle, savePath);
		// 删除文章
		articleService.deleteArticleByPrimaryKey(id);
		return Msg.success("删除成功！");
	}

	/**
	 * 用户后台 批量删除文章 
	 * 1.前台传过来的数据格式：/user/deleteArticles/1,2,3
	 * 2.通过逗号","分割过来，实际上传过来的是字符串，之后进行转换为List<Integer>进行插入 ResponseBody:
	 * 自动把的返回对象转为JSON字符串(需要导入jackson包) PathVariable: 用来获得请求url中的动态参数的
	 * 
	 * @return
	 */
	@RequestMapping(value = "/deleteArticles/{str_ids}", method = RequestMethod.DELETE)
	@ResponseBody
	public Msg batchDeleteArticles(@PathVariable("str_ids") String str_ids, HttpServletRequest request) {
		// 解析id，传过来的数据/user/deleteArticles/id1,id2,id3...
		String[] str_id = str_ids.split(",");
		// 封装成Integer集合
		List<Integer> idList = new ArrayList<>();
		for (String id : str_id) {
			idList.add(Integer.parseInt(id));
		}
		// 批量删除文章之前需要把保存的md文件删除
		String savePath = request.getServletContext().getRealPath("/source/articles_md/");
		// 删除文章之前需要把保存的md文件删除
		for (String id : str_id) {
			String oldTitle = articleService.selectTitleByPrimaryKey(Integer.parseInt(id));
			MarkdownUtils.removeMarkdown(oldTitle, savePath);
		}
		// 批量删除文章
		articleService.deleteBatch(idList);
		return Msg.success("批量删除成功！");
	}

	/**
	 * 用户关注别人操作
	 * 
	 * @param uid         登录的用户id
	 * @param followingId 要关注的人id
	 * @return
	 */
	@RequestMapping(value = "/follow", method = RequestMethod.POST)
	@ResponseBody
	public Msg follow(@RequestParam(value = "uid") Integer uid,
			@RequestParam(value = "followingId") Integer followingId) {
		userService.Follow(uid, followingId);
		return Msg.success();
	}

	/**
	 * 查看所有关注的人
	 * 
	 * @return
	 */
	@RequestMapping("/getFollowing")
	public ModelAndView getFollowing(@RequestParam(value = "username") String username) {
		Integer uid = userService.checkExistUserName(username);
		ModelAndView mav = new ModelAndView();
		List<User> users = userService.getFollowing(uid);
		User user = userService.selectByPrimaryKey(uid);
		mav.addObject("users", users);
		mav.addObject("user", user);
		mav.addObject("title", "我的关注");
		mav.setViewName("follow");
		return mav;
	}

	/**
	 * 查看所有粉丝
	 * 
	 * @param uid
	 * @return
	 */
	@RequestMapping("/getFollower")
	public ModelAndView getfollower(@RequestParam(value = "username") String username) {
		Integer uid = userService.checkExistUserName(username);
		ModelAndView mav = new ModelAndView();
		List<User> users = userService.getFollower(uid);
		User user = userService.selectByPrimaryKey(uid);
		mav.addObject("users", users);
		mav.addObject("user", user);
		mav.addObject("title", "我的粉丝");
		mav.setViewName("follow");
		return mav;
	}

	/**
	 * 移除粉丝
	 * 
	 * @param uid
	 * @param followingId
	 * @return
	 */
	@RequestMapping(value = "/deleteFollower", method = RequestMethod.POST)
	@ResponseBody
	public Msg deleteFollower(@RequestParam(value = "uid") Integer uid,
			@RequestParam(value = "followingId") Integer followingId) {
		userService.deleteFollow(uid, followingId);
		return Msg.success();
	}

	/**
	 * 取消关注
	 * 
	 * @param uid
	 * @param followingId
	 * @return
	 */
	@RequestMapping(value = "/deleteFollowing", method = RequestMethod.POST)
	@ResponseBody
	public Msg deleteFollowing(@RequestParam(value = "uid") Integer uid,
			@RequestParam(value = "followingId") Integer followingId) {
		userService.deleteFollow(uid, followingId);
		return Msg.success();
	}

	/**
	 * 用户对文章进行评论
	 * 
	 * @param uid
	 * @param articleId
	 * @param content
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	@ResponseBody
	public Msg addComment(@RequestParam(value = "uid") Integer uid,
			@RequestParam(value = "articleId") Integer articleId, @RequestParam(value = "content") String content,
			HttpServletRequest request) {
		Comment comment = new Comment(articleId, uid, content);
		userService.addComment(comment);
		return Msg.success();
	}

	/**
	 * 用户删除评论
	 * 
	 * @param uid
	 * @param articleId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Msg deleteComment(@RequestParam(value = "id") Integer id, HttpServletRequest request) {
		userService.deleteComment(id);
		return Msg.success();
	}
}

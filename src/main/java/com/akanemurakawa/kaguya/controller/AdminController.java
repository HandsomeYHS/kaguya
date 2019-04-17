package com.akanemurakawa.kaguya.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.http.client.utils.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.IntrospectionException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.*;

/**
 * 
 * @author HanaeYuuma
 * @date 2018-8-29
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

	@Resource
	private AdminService adminService;

	@Resource
	private NoticeService noticeService;
	
	@Resource
	private UserService userService;

	/**
	 * 跳转到公告管理
	 * 
	 * @return
	 */
	@RequestMapping("/toNoticeManager.do")
	public String toNoticeManager() {
		return "/WEB-INF/admin/noticeManager";
	}

	/**
	 * 跳转到用户管理
	 * 
	 * @return
	 */
	@RequestMapping("/toUserManager.do")
	public String toUserManager() {
		return "/WEB-INF/admin/userManager";
	}

	/**
	 * 跳转到数据系统
	 * 
	 * @return
	 */
	@RequestMapping("/toDataSystem.do")
	public String toUserMatoDataSystem() {
		return "/WEB-INF/admin/dataSystem";
	}

	/**
	 * 跳转到系统设置
	 * 
	 * @return
	 */
	@RequestMapping("/toSetting.do")
	public String toSetting() {
		return "/WEB-INF/admin/setting";
	}

	/**
	 * 跳转到系统设置
	 * 
	 * @return
	 */
	@RequestMapping("/toWriteNotice.do")
	public String toWriteNotice() {
		return "/WEB-INF/admin/writeNotice";
	}

	/**
	 * 发布公告 说明：这里用户写公告的时候是通过markdown来编辑，因此需要保存用户填写的markdown文件内容，
	 *  便于用户修改公告的时候得到的是markdown格式的文本内容 markdown文件存储在磁盘中：用户写公告和修改公告都是用markdown
	 * html文件存储在数据中：将markdown转为html，用于公告界面的显示 1.获取数据 2.存储公告内容的markdown文件 3.插入公告
	 * 
	 * @return
	 */
	@RequestMapping(value = "/writeNotice", method = RequestMethod.POST)
	public String writeArticle(@RequestParam(value = "aid") Integer aid, HttpServletRequest request) {
		String noticeTitle = request.getParameter("title");
		String noticeContent = request.getParameter("contentHTML"); // HTML 格式， 在前台直接传入的是HTMl，而不是markdown
		/*
		 * 保存编写的markdown文件
		 */
		String markdownContent = request.getParameter("content"); // markdown 格式
		String savePath = request.getServletContext().getRealPath("/source/notices_md/");
		MarkdownUtils.saveMarkdown(markdownContent, noticeTitle, savePath);

		/*
		 * 这里需要为公告摘要的存储转换为纯文本格式 公告摘要选取内容的前150，如果不足这么多字则选取全文
		 */
		String noticeSumary = HTMLToTextUtils.getSimpleText(noticeContent); // 纯文本 格式
		if (noticeSumary.length() > 150) {
			noticeSumary = noticeSumary.substring(0, 150);
		}
		Notice record = new Notice(aid, noticeTitle, noticeSumary, noticeContent);
		noticeService.insert(record);

		// 添加成功，跳转到公告管理界面查看。
		return "WEB-INF/admin/noticeManager";
	}

	/**
	 * 写公告的时候接收上传的图片，这里的逻辑和上传头像一样，但是因为Editormd 要求按照指定的格式返回，所以我这里就没有封装。
	 * 
	 * @param file    该value="editormd-image-file"是官方指定的。
	 * @param request
	 * @return
	 */
	@RequestMapping("/uploadImgEditormd")
	@ResponseBody
	public Map<String, Object> uploadImgEditormd(
			@RequestParam(value = "editormd-image-file", required = true) MultipartFile file,
			@RequestParam(value = "aid") Integer aid, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		/*
		 * 1.上传文件夹的位置
		 */
		String savePath = "/upload/img/notice/";
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
		String newFilename = "notice_" + aid + "_" + upLoadTime + fileFormat;
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
	 * 单个删除公告
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteNotice/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Msg deleteNotice(@PathVariable("id") Integer id, HttpServletRequest request) {
		String savePath = request.getServletContext().getRealPath("/source/notices_md/");
		// 删除公告之前需要把保存的md文件删除
		String oldTitle = noticeService.selectTitleById(id);
		MarkdownUtils.removeMarkdown(oldTitle, savePath);
		noticeService.deleteByPrimaryKey(id);
		return Msg.success("删除成功！");
	}

	/**
	 * 批量删除公告 
	 * 1.前台传过来的数据格式：/admin/deleteNotices/1,2,3
	 * 2.通过逗号","分割过来，实际上传过来的是字符串，之后进行转换为List<Integer>进行插入 ResponseBody:
	 * 自动把的返回对象转为JSON字符串(需要导入jackson包) PathVariable: 用来获得请求url中的动态参数的
	 * 
	 * @return
	 */
	@RequestMapping(value = "/deleteNotices/{str_ids}", method = RequestMethod.DELETE)
	@ResponseBody
	public Msg batchDeleteNotices(@PathVariable("str_ids") String str_ids, HttpServletRequest request) {
		// 解析id，传过来的数据/user/deleteArticles/id1,id2,id3...
		String[] str_id = str_ids.split(",");
		// 封装成Integer集合
		List<Integer> idList = new ArrayList<>();
		for (String id : str_id) {
			idList.add(Integer.parseInt(id));
		}
		// 批量删除公告之前需要把保存的md文件删除
		String savePath = request.getServletContext().getRealPath("/source/notices_md/");
		// 删除公告之前需要把保存的md文件删除
		for (String id : str_id) {
			String oldTitle = noticeService.selectTitleById(Integer.parseInt(id));
			MarkdownUtils.removeMarkdown(oldTitle, savePath);
		}
		// 批量删除公告
		noticeService.deleteBatch(idList);
		return Msg.success("批量删除成功！");
	}

	/**
	 * 用户后台 跳转到修改文章界面
	 * 
	 * @return
	 */
	@RequestMapping("/toModifyNotice.do")
	public String toModifyNotice(@RequestParam(value = "nid") Integer nid, Model model, HttpServletRequest request) {
		Notice notice = noticeService.selectByPrimaryKey(nid);
		// 读取文章的md格式传到修改界面
		String path = request.getServletContext().getRealPath("/source/notices_md/");
		String content = MarkdownUtils.getMarkdown(notice.getNoticeTitle(), path);
		notice.setNoticeContent(content);
		model.addAttribute("notice", notice);
		return "WEB-INF/admin/modifyNotice";
	}

	/**
	 * 修改公告 
	 * 1.首先获取数据 
	 * 2.到数据库中更新 
	 * 3.删除之前的md文件 
	 * 4.生成新的md文件
	 * 
	 * @return
	 */
	@RequestMapping(value = "/modifyNotice", method = RequestMethod.POST)
	public String modifyNotice(@RequestParam(value = "nid") Integer nid, HttpServletRequest request) {
		String noticeTitle = request.getParameter("title");
		String noticeContent = request.getParameter("contentHTML"); // HTML 格式， 在前台直接传入的是HTMl，而不是markdown

		/*
		 * 这里需要为公告摘要的存储转换为纯文本格式 文章摘要选取内容的前150，如果不足这么多字则选取全文
		 */
		String noticeSumary = HTMLToTextUtils.getSimpleText(noticeContent); // 纯文本 格式
		if (noticeSumary.length() > 150) {
			noticeSumary = noticeSumary.substring(0, 150);
		}
		// 到数据库中更新
		Notice notice = new Notice();
		notice.setId(nid);
		notice.setNoticeTitle(noticeTitle);
		notice.setNoticeSumary(noticeSumary);
		notice.setNoticeContent(noticeContent);
		noticeService.updateByPrimaryKey(notice);

		String savePath = request.getServletContext().getRealPath("/source/notices_md/");
		// 删除之前的md文件
		String oldTitle = request.getParameter("oldTitle"); // 旧标题通过隐藏的属性传入。
		MarkdownUtils.removeMarkdown(oldTitle, savePath);
		// 生成新的md文件
		String markdownContent = request.getParameter("content"); // markdown 格式
		MarkdownUtils.saveMarkdown(markdownContent, noticeTitle, savePath);

		// 添加成功，跳转到文章管理界面查看。
		return "WEB-INF/admin/noticeManager";
	}

	/**
	 * 上传excel，导入用户信息。
	 * 格式： 用户名，密码，邮箱，性别
	 * @param request
	 * @param model
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/upLoadExcel")
	public String upLoadExcel(HttpServletRequest request, Model model,
			@RequestParam(value = "upload-file", required = true) MultipartFile file) throws Exception {
		InputStream in = file.getInputStream();
		// 数据导入
		boolean isSuccess = adminService.importExcelUserInfo(in, file);
		in.close();
		// 成功导入
		if (isSuccess) {
			return "redirect:/WEB-INF/admin/userManager";
		}else {
			//TODO
			return "redirect:/WEB-INF/admin/userManager";
		}
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws InvocationTargetException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping("/downloadExcel.do")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException, InvocationTargetException, ClassNotFoundException,
			IllegalAccessException, IntrospectionException, ParseException, IOException {
		Date date = new Date();
		response.reset(); // 清除buffer缓存
		// 指定下载的文件名，浏览器都会使用本地编码，即GBK，浏览器收到这个文件名后，用ISO-8859-1来解码，然后用GBK来显示
		// 所以我们用GBK解码，ISO-8859-1来编码，在浏览器那边会反过来执行。
		String filename = "用户信息"+DateUtils.formatDate(date, "yyyy-MM-dd")+".xlsx";
		response.setHeader("Content-Disposition", "attachment;filename=" + filename);
		// 设置文件ContentType类型，这样设置，会自动判断下载文件类型
		// response.setContentType("multipart/form-data");
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		
		// 导出Excel对象
		OutputStream outputStream = response.getOutputStream();
		adminService.exportExcelUserInfo(outputStream, "用户信息");
	}
	
	/**
	 * 分页查询用户
	 * @param pn
	 * @return
	 */
	@RequestMapping(value="/getUser", method=RequestMethod.GET)
	@ResponseBody
	public Msg getUserWithAjax(@RequestParam(value="pn", defaultValue = "1")Integer pn,
			@RequestParam(value="username", defaultValue = "")String username, 
			@RequestParam(value="email", defaultValue = "")String email, 
			@RequestParam(value="sex", defaultValue = "")String sex, 
			HttpServletRequest request) {
		
		PageHelper.startPage(pn, 10);
		List<User> users = null;
		if ("".equals(username) && "".equals(email) && "".equals(sex)) {
			users = userService.selectAllUserInfo();
		}else {
			User user = new User();
			user.setUserUsername(username);
			user.setUserEmail(email);
			user.setUserSex(sex);
			users = userService.selectUserInfo(user);
		}
		PageInfo page = new PageInfo(users, 5);
		return Msg.success().add("pageInfo", page);
	}
	
	/**
	 * 单个删除用户
	 * @param id
	 * @return
	 */
	@RequestMapping(value="deleteUser/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Msg deleteUser(@PathVariable("id") Integer id) {
		/*
		 * 需要先删除Tag，Category，Friend，Article，最后才删除User。
		 * 这里通过设置数据库的外键属性，删除时：CASCADE。
		 * 当删除父表的时候，会检查该记录是否有外键，如果有则删除对应的子表记录。
		 * 级联删除。
		 */
		userService.deleteByPrimaryKey(id);
		return Msg.success("删除成功！");
	}
	
	/**
	 * 批量删除用户
	 * @param str_ids
	 * @return
	 */
	@RequestMapping(value = "/deleteUsers/{str_ids}", method = RequestMethod.DELETE)
	@ResponseBody
	public Msg batchDeleteUsers(@PathVariable("str_ids") String str_ids) {
		// 解析id，传过来的数据/user/deleteUsers/id1,id2,id3...
		String[] str_id = str_ids.split(",");
		// 封装成Integer集合
		List<Integer> idList = new ArrayList<>();
		for (String id : str_id) {
			idList.add(Integer.parseInt(id));
		}
		// 批量删除
		userService.deleteBatch(idList);
		return Msg.success("批量删除成功！");
	}
}

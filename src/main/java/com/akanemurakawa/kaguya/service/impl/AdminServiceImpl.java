package com.akanemurakawa.kaguya.service.impl;

import java.beans.IntrospectionException;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanaeyuuma.freeblogs.dao.AdminMapper;
import com.hanaeyuuma.freeblogs.model.Admin;
import com.hanaeyuuma.freeblogs.model.Article;
import com.hanaeyuuma.freeblogs.model.Category;
import com.hanaeyuuma.freeblogs.model.Excel;
import com.hanaeyuuma.freeblogs.model.Friend;
import com.hanaeyuuma.freeblogs.model.Tag;
import com.hanaeyuuma.freeblogs.model.User;
import com.hanaeyuuma.freeblogs.service.AdminService;
import com.hanaeyuuma.freeblogs.service.ArticleService;
import com.hanaeyuuma.freeblogs.service.CategoryService;
import com.hanaeyuuma.freeblogs.service.FriendService;
import com.hanaeyuuma.freeblogs.service.TagService;
import com.hanaeyuuma.freeblogs.service.UserService;
import com.hanaeyuuma.freeblogs.util.ExcelUtils;
import com.hanaeyuuma.freeblogs.util.MD5Utils;

/**
 * 
 * @author HanaeYuuma
 * @date 2018-6-11
 */
@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private AdminMapper adminMapper;
	
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
	 * Admin:验证管理员登录，通过id和密码.如果存在则返回该管理员信息，否则返回null
	 */
	@Override
	public Admin checkAdminLogin(Admin admin) {
		Admin checkAdmin = adminMapper.selectByIdAndPassword(admin);
		if (checkAdmin != null){
			return checkAdmin;
		}else{
			return null;
		}
	}

	/**
	 * Admin:导入excel：用户信息
	 */
	@Override
	public boolean importExcelUserInfo(InputStream in, MultipartFile file) {
	    List<List<Object>> listob = ExcelUtils.importExcel(in,file.getOriginalFilename(),1);
	    List<User> userList = new ArrayList<User>();
	    if (null == listob) {
	    	return false;
	    }
	    // 遍历listob数据，把数据放到List中
	    for (int i = 0; i < listob.size(); i++) {
	        List<Object> ob = listob.get(i);
	        User user = new User();
	        // 通过遍历实现把每一列封装成一个model中，再把所有的model用List集合装载
	        user.setUserUsername(String.valueOf(ob.get(0)));
	        user.setUserPassword(MD5Utils.getMD5(String.valueOf(ob.get(1))));
	        user.setUserEmail(String.valueOf(ob.get(2)));
	        if ("男".equals(String.valueOf(ob.get(3))) || "M".equals(String.valueOf(ob.get(3)))) {
	        	user.setUserSex("M");
	        }else if("女".equals(String.valueOf(ob.get(3))) || "F".equals(String.valueOf(ob.get(3)))) {
	        	user.setUserSex("F");
	        }else {
	        	// 默认是男
	        	user.setUserSex("M");
	        }
	        userList.add(user);
	    }
	    // 批量插入
	    userService.batchInsert(userList);
	    
	    /**
		 * 以下为默认
		 */
	    for (User user : userList) {
	    	// 使用mybatis的配置，插入成功自动获得自增的主键
			Integer userId = user.getId();
			Friend friend = new Friend(userId, "FreeBlogs", "www.google.com");
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
		}
	    return true;
	}

	/**
	 * Admin:导出excel，用户信息
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
	@Override
	public void exportExcelUserInfo(OutputStream outputStream, String sheetName) throws InvocationTargetException, ClassNotFoundException, IntrospectionException, ParseException, IllegalAccessException, IllegalArgumentException, IOException {
	    // 根据条件查询数据，把数据装载到一个list中
	    List<User> dataList = userService.selectAllUserInfo();
	    XSSFWorkbook xssfWorkbook = null;
	    // 设置标题栏
	    List<Excel> excel = new ArrayList<>();
	    excel.add(new Excel("用户ID","id",0));
	    excel.add(new Excel("用户名","userUsername",0));
	    excel.add(new Excel("密码","userPassword",0));
	    excel.add(new Excel("邮箱","userEmail",0));
	    excel.add(new Excel("性别","userSex",0));
	    excel.add(new Excel("头像","userAvatar",0));
	    excel.add(new Excel("简介","userDescription",0));
	    excel.add(new Excel("github","userGithub",0));
	    excel.add(new Excel("twitter","userTwitter",0));
	    excel.add(new Excel("weibo","userWeibo",0));
	    Map<Integer,List<Excel>> map = new LinkedHashMap<>();
	    map.put(0, excel);
	    
		ExcelUtils.exportExcel(outputStream, User.class, dataList, map, sheetName);
		BufferedOutputStream bufferedOutPut = new BufferedOutputStream(outputStream);
		bufferedOutPut.flush();
		bufferedOutPut.close();
	}
}

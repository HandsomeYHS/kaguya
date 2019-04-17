package com.akanemurakawa.kaguya;

import java.util.List;
import com.akanemurakawa.kaguya.model.entity.Article;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;

/**
 * 使用spring测试模块测试mvc请求功能，测试crud请求是否成功。
 * RunWith: 表示使用spring提供的测试单元
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MVCTest {
	
	// 传入SpringMVC的IOC
	@Resource
	WebApplicationContext context;
	
	// 虚拟mvc请求，对于服务器端的Spring MVC测试支持主入口点。
	MockMvc mockMvc;
	
	/**
	 * aop
	 */
	@Before
	public void initMockMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void testPage() {
		try {
			// 模拟请求得到返回值
			MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/article").param("pageNow", "1")).andReturn();
			// 请求成功后，去除请求域中的pageInfo
			MockHttpServletRequest request = result.getRequest();
			PageInfo pi = (PageInfo)request.getAttribute("pageInfo");
			System.out.println("当前页面"+pi.getPageNum());
			System.out.println("总页码"+pi.getPages());
			System.out.println("总记录数"+pi.getTotal());
			System.out.println("再页面需要连续显示的页码");
			int[] nums = pi.getNavigatepageNums();
			for (int i : nums) {
				System.out.println(" " + i);
			}
			List<Article> list = pi.getList();
			for (Article article : list) {
				System.out.println(article.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

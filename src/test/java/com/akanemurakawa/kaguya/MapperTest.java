package com.akanemurakawa.kaguya;

import java.util.UUID;

import com.akanemurakawa.kaguya.dao.mapper.ArticleMapper;
import com.akanemurakawa.kaguya.dao.mapper.CategoryMapper;
import com.akanemurakawa.kaguya.dao.mapper.NoticeMapper;
import com.akanemurakawa.kaguya.model.entity.Article;
import com.akanemurakawa.kaguya.model.entity.Notice;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.annotation.Resource;

/**
 * 测试Mapper，使用的时候把@Test的注释去掉，然后RunAs--JUnit Test
 * RunWith: 表示使用spring的测试单元
 * ContextConfiguration: 指定加载的spring.xml文件位置
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MapperTest {
	
	@Resource
	private ArticleMapper articleMapper;
	
	@Resource
	private CategoryMapper categoryMapper;
	
	@Resource
	private SqlSession sqlSession;
	
	@Resource
	private SqlSessionTemplate sqlSessionTemplate;
	
	/**
	 * 测试Category的CRUD
	 */
	@Test
	public void testCategoryCRUD() {
		System.out.println(categoryMapper);
		/*
		 * 因为一直插入失败，一直没有找到原因，结果就想try-catch一下，没想到直接给报错错误信息了。这是一个很不错的方法，
		 * 以后在控制台没有输出错误信息而又执行失败的时候，把觉得有问题的代码块try-catch一下就好了。
		 */
		try {
			System.out.println("开始批量插入");
			// 获取一个模式为BATCH，自动提交为false的session。批量操作的sqlsseion,需要在spring.xml下配置。
			sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
			CategoryMapper mapper = sqlSession.getMapper(CategoryMapper.class);
			for (int i = 2; i <= 101; i++) {
				String uid = UUID.randomUUID().toString().substring(0,5);
				//mapper.insertSelective(new Category(null, i, "测试"+uid));
			}
			// 手动提交
			sqlSession.commit();
			// 清理缓存，防止溢出
			sqlSession.clearCache();
			System.out.println("批量完成！");
		} catch (Exception e) {
			System.out.println("发生错误！");
			// 回滚没有提交的数据
			sqlSession.rollback();
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
	}
	
	/**
	 *  test Article CRUD
	 */
	@Test
	public void testArticleCRUD() {
		System.out.println(articleMapper);
		try {
			System.out.println("开始批量插入");
			sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
			ArticleMapper mapper = sqlSession.getMapper(ArticleMapper.class);
			for (long i = 1; i <= 100; i++) {
				String uid = UUID.randomUUID().toString().substring(0,5);
				mapper.insertSelective(new Article(1, "这是第"+i+"条测试mapper",  null, "测试"+uid, null,"测试"));
			}
			sqlSession.commit();
			sqlSession.clearCache();
			System.out.println("批量完成！");
		} catch (Exception e) {
			System.out.println("发生错误！");
			sqlSession.rollback();
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
	}
	
	/**
	 *  test Notice CRUD
	 */
//	@Test
	public void testNoticeCRUD() {
		System.out.println(articleMapper);
		try {
			System.out.println("开始批量插入");
			sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
			NoticeMapper mapper = sqlSession.getMapper(NoticeMapper.class);
			for (int i = 1; i <= 100; i++) {
				String uid = UUID.randomUUID().toString().substring(0,5);
				mapper.insertSelective(new Notice(1002, "这是第"+i+"条测试mapper",  null, "测试"+uid,"测试"+uid));
			}
			sqlSession.commit();
			sqlSession.clearCache();
			System.out.println("批量完成！");
		} catch (Exception e) {
			System.out.println("发生错误！");
			sqlSession.rollback();
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
	}
}

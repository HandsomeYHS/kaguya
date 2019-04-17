/*
 Navicat Premium Data Transfer

 Source Server         : my-test
 Source Server Type    : MySQL
 Source Server Version : 50716
 Source Host           : localhost:3306
 Source Schema         : kaguya

 Target Server Type    : MySQL
 Target Server Version : 50716
 File Encoding         : 65001

 Date: 17/04/2019 20:13:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录账号',
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录名',
  `password` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'dd4b21e9ef71e1291183a46b913ae6f2' COMMENT '密码',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_acount`(`account`) USING BTREE COMMENT '管理员登录账号的唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1003 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '管理员信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1001, 'root', 'root', 'dd4b21e9ef71e1291183a46b913ae6f2');
INSERT INTO `admin` VALUES (1002, 'admin', 'admin', 'dd4b21e9ef71e1291183a46b913ae6f2');

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章标题',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '发布时间',
  `sumary` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章摘要',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发布内容',
  `hit` int(6) NULL DEFAULT 0 COMMENT '文章点击数',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE COMMENT '用户id的普通索引'
) ENGINE = InnoDB AUTO_INCREMENT = 82 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文章信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (49, 6, 'Rust语言', '2018-12-30 23:28:37', 'HelloWorld', 'HelloWorld', 11);
INSERT INTO `article` VALUES (50, 7, 'Welcome to FreeBlogs.', '2018-08-20 12:05:06', 'HelloWorld', 'HelloWorld', 5);
INSERT INTO `article` VALUES (56, 6, '在线文档', '2018-08-25 19:45:58', 'FreeBlogsWhat’s this?It’s a tiny personal blog website.Rename Suzuki to FreeBlogs in 2 August, 2018.\r\nFeatures\r\nSupport user login, register users, re', '<h1 id=\"h1-freeblogs\"><a name=\"FreeBlogs\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>FreeBlogs</h1><h3 id=\"h3-what-s-this-\"><a name=\"What’s this?\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>What’s this?</h3><p>It’s a tiny personal blog website.Rename Suzuki to FreeBlogs in 2 August, 2018.</p>\r\n<h3 id=\"h3-features\"><a name=\"Features\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>Features</h3><ol>\r\n<li><p>Support user login, register users, remember passwords, retrieve passwords, and support email authentication.</p>\r\n</li><li><p>The user’s home page interface can display articles in separate pages. View user information, recent articles, tags, friendship links, latest announcements, weather and date plugins.</p>\r\n</li><li><p>User background management function: display of basic data, modify basic data, modify avatar, change password, and modify friendship link. Add, modify articles, and delete articles in bulk or individually. The article supports markdown editing and supports image uploading.</p>\r\n</li><li><p>The article reading supports full-text reading, user reviews. Support for displaying the previous and next articles, supporting article reading statistics (heat statistics).</p>\r\n</li><li><p>User log function, displaying articles by year.</p>\r\n</li><li><p>The user pays attention to the function, the user can perform the function of paying attention to others, and has the dynamic function of the friend. You can follow and unfollow and remove fan features.</p>\r\n</li></ol>\r\n<h3 id=\"h3-development-technology\"><a name=\"Development Technology\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>Development Technology</h3><ul>\r\n<li>Front-end Framework: Bootstrap、LayUI</li><li>Back-end Framework: Spring、SpringMVC、MyBatis</li><li>Database: MySQL</li><li>Deploy: Tomcat</li><li>Project Management: Maven </li><li>Editor: Markdown</li></ul>\r\n<h3 id=\"h3-how-can-i-read-this-code-or-use-it-to-build-website-\"><a name=\"How can I read this code or use it to build website?\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>How can I read this code or use it to build website?</h3><p>Ok, you can read the “README.md” in the doc file.</p>\r\n<h3 id=\"h3-faq\"><a name=\"FAQ\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>FAQ</h3><p>Q:What is the meaning about suzuki?</p>\r\n<p>A:スズキ、铃木</p>\r\n<h3 id=\"h3-other-problems\"><a name=\"Other Problems\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>Other Problems</h3><p>pull Issues or mailto me <a href=\"mailto:chenshjing@gmail.com.\">chenshjing@gmail.com.</a></p>\r\n<h3 id=\"h3-logs\"><a name=\"Logs\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>Logs</h3><p>unknow</p>\r\n<h3 id=\"h3-license\"><a name=\"LICENSE\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>LICENSE</h3><p><a href=\"http://www.apache.org/licenses/\">Apache License</a></p>\r\n<h3 id=\"h3--\"><a name=\"这是什么?\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>这是什么?</h3><p>一个小型的个人博客网站，于2018年8月2日由<strong>Suzuki</strong>改名为<strong>FreeBlogs</strong>。</p>\r\n<h3 id=\"h3-u7279u8272\"><a name=\"特色\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>特色</h3><ol>\r\n<li><p>支持用户登录，注册用户，记住密码，找回密码，支持邮箱认证。</p>\r\n</li><li><p>用户主页的界面可以分页显示文章。查看用户信息，近期文章，标签，友情链接，最新公告，天气和日期插件。</p>\r\n</li><li><p>用户后台管理功能：基本资料的显示,，修改基本资料，修改头像，修改密码，修改友情链接。添加，修改文章，可以进行批量或者单个删除文章。文章支持markdown编辑，支持图片上传。</p>\r\n</li><li><p>文章阅读支持全文阅读，用户评论。支持显示上一篇和下一篇文章，支持文章阅读统计（热度统计）。</p>\r\n</li><li><p>用户日志功能，按照年份显示文章。</p>\r\n</li><li><p>用户关注功能，用户可以进行关注他人的功能，具有好友动态功能。可以进行关注和取消关注，移除粉丝功能。</p>\r\n</li></ol>\r\n<h3 id=\"h3-u5F00u53D1u6280u672F\"><a name=\"开发技术\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>开发技术</h3><ul>\r\n<li>前端框架: Bootstrap、LayUI</li><li>后端框架: Spring、SpringMVC、MyBatis</li><li>数据库: MySQL</li><li>部署: Tomcat</li><li>项目管理工具: Maven </li><li>编辑: Markdown</li></ul>\r\n<h3 id=\"h3--\"><a name=\"我应该怎么阅读这个项目或者使用其搭建我的个人网站?\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>我应该怎么阅读这个项目或者使用其搭建我的个人网站?</h3><p>好的， 你可以阅读我的doc/README.md的文件内容。</p>\r\n<h3 id=\"h3-u5E38u89C1u95EEu9898\"><a name=\"常见问题\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>常见问题</h3><p>Q:suzuki是什么意思?</p>\r\n<p>A:スズキ、铃木</p>\r\n<h3 id=\"h3-u5176u4ED6u95EEu9898\"><a name=\"其他问题\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>其他问题</h3><p>提交Issues或者邮箱给我<a href=\"mailto:chenshjing@gmail.com.\">chenshjing@gmail.com.</a></p>\r\n<h3 id=\"h3-u65E5u5FD7\"><a name=\"日志\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>日志</h3><p>なんでもないよ、并没有。</p>\r\n<h3 id=\"h3-u9075u5B88u534Fu8BAE\"><a name=\"遵守协议\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>遵守协议</h3><p><a href=\"http://www.apache.org/licenses/\">Apache License</a></p>\r\n', 5);
INSERT INTO `article` VALUES (61, 7, '校园博客系统', '2018-08-25 22:38:54', 'FreeBlogsWhat’s this?It’s a tiny personal blog website.Rename Suzuki to FreeBlogs in 2 August, 2018.\r\nFeatures\r\nSupport user login, register users, re', '<h1 id=\"h1-freeblogs\"><a name=\"FreeBlogs\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>FreeBlogs</h1><h3 id=\"h3-what-s-this-\"><a name=\"What’s this?\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>What’s this?</h3><p>It’s a tiny personal blog website.Rename Suzuki to FreeBlogs in 2 August, 2018.</p>\r\n<h3 id=\"h3-features\"><a name=\"Features\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>Features</h3><ol>\r\n<li><p>Support user login, register users, remember passwords, retrieve passwords, and support email authentication.</p>\r\n</li><li><p>The user’s home page interface can display articles in separate pages. View user information, recent articles, tags, friendship links, latest announcements, weather and date plugins.</p>\r\n</li><li><p>User background management function: display of basic data, modify basic data, modify avatar, change password, and modify friendship link. Add, modify articles, and delete articles in bulk or individually. The article supports markdown editing and supports image uploading.</p>\r\n</li><li><p>The article reading supports full-text reading, user reviews. Support for displaying the previous and next articles, supporting article reading statistics (heat statistics).</p>\r\n</li><li><p>User log function, displaying articles by year.</p>\r\n</li><li><p>The user pays attention to the function, the user can perform the function of paying attention to others, and has the dynamic function of the friend. You can follow and unfollow and remove fan features.</p>\r\n</li></ol>\r\n<h3 id=\"h3-development-technology\"><a name=\"Development Technology\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>Development Technology</h3><ul>\r\n<li>Front-end Framework: Bootstrap、LayUI</li><li>Back-end Framework: Spring、SpringMVC、MyBatis</li><li>Database: MySQL</li><li>Deploy: Tomcat</li><li>Project Management: Maven </li><li>Editor: Markdown</li></ul>\r\n<h3 id=\"h3-how-can-i-read-this-code-or-use-it-to-build-website-\"><a name=\"How can I read this code or use it to build website?\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>How can I read this code or use it to build website?</h3><p>Ok, you can read the “README.md” in the doc file.</p>\r\n<h3 id=\"h3-faq\"><a name=\"FAQ\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>FAQ</h3><p>Q:What is the meaning about suzuki?</p>\r\n<p>A:スズキ、铃木</p>\r\n<h3 id=\"h3-other-problems\"><a name=\"Other Problems\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>Other Problems</h3><p>pull Issues or mailto me <a href=\"mailto:chenshjing@gmail.com.\">chenshjing@gmail.com.</a></p>\r\n<h3 id=\"h3-logs\"><a name=\"Logs\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>Logs</h3><p>unknow</p>\r\n<h3 id=\"h3-license\"><a name=\"LICENSE\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>LICENSE</h3><p><a href=\"http://www.apache.org/licenses/\">Apache License</a></p>\r\n<h3 id=\"h3--\"><a name=\"这是什么?\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>这是什么?</h3><p>一个小型的个人博客网站，于2018年8月2日由<strong>Suzuki</strong>改名为<strong>FreeBlogs</strong>。</p>\r\n<h3 id=\"h3-u7279u8272\"><a name=\"特色\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>特色</h3><ol>\r\n<li><p>支持用户登录，注册用户，记住密码，找回密码，支持邮箱认证。</p>\r\n</li><li><p>用户主页的界面可以分页显示文章。查看用户信息，近期文章，标签，友情链接，最新公告，天气和日期插件。</p>\r\n</li><li><p>用户后台管理功能：基本资料的显示,，修改基本资料，修改头像，修改密码，修改友情链接。添加，修改文章，可以进行批量或者单个删除文章。文章支持markdown编辑，支持图片上传。</p>\r\n</li><li><p>文章阅读支持全文阅读，用户评论。支持显示上一篇和下一篇文章，支持文章阅读统计（热度统计）。</p>\r\n</li><li><p>用户日志功能，按照年份显示文章。</p>\r\n</li><li><p>用户关注功能，用户可以进行关注他人的功能，具有好友动态功能。可以进行关注和取消关注，移除粉丝功能。</p>\r\n</li></ol>\r\n<h3 id=\"h3-u5F00u53D1u6280u672F\"><a name=\"开发技术\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>开发技术</h3><ul>\r\n<li>前端框架: Bootstrap、LayUI</li><li>后端框架: Spring、SpringMVC、MyBatis</li><li>数据库: MySQL</li><li>部署: Tomcat</li><li>项目管理工具: Maven </li><li>编辑: Markdown</li></ul>\r\n<h3 id=\"h3--\"><a name=\"我应该怎么阅读这个项目或者使用其搭建我的个人网站?\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>我应该怎么阅读这个项目或者使用其搭建我的个人网站?</h3><p>好的， 你可以阅读我的doc/README.md的文件内容。</p>\r\n<h3 id=\"h3-u5E38u89C1u95EEu9898\"><a name=\"常见问题\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>常见问题</h3><p>Q:suzuki是什么意思?</p>\r\n<p>A:スズキ、铃木</p>\r\n<h3 id=\"h3-u5176u4ED6u95EEu9898\"><a name=\"其他问题\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>其他问题</h3><p>提交Issues或者邮箱给我<a href=\"mailto:chenshjing@gmail.com.\">chenshjing@gmail.com.</a></p>\r\n<h3 id=\"h3-u65E5u5FD7\"><a name=\"日志\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>日志</h3><p>なんでもないよ、并没有。</p>\r\n<h3 id=\"h3-u9075u5B88u534Fu8BAE\"><a name=\"遵守协议\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>遵守协议</h3><p><a href=\"http://www.apache.org/licenses/\">Apache License</a></p>\r\n', 9);
INSERT INTO `article` VALUES (80, 6, 'Vue学习第一天-第一个实例', '2018-12-30 21:49:42', 'Vue.js（读音 /vjuː/, 类似于 view） 是一套构建用户界面的渐进式框架。\r\nVue 只关注视图层， 采用自底向上增量开发的设计。\r\nVue 的目标是通过尽可能简单的 API 实现响应的数据绑定和组合的视图组件。\r\nVue 学习起来非常简单，本教程基于 Vue 2.1.8 版本测试。\r', '<p>Vue.js（读音 /vjuː/, 类似于 view） 是一套构建用户界面的渐进式框架。</p>\r\n<p>Vue 只关注视图层， 采用自底向上增量开发的设计。</p>\r\n<p>Vue 的目标是通过尽可能简单的 API 实现响应的数据绑定和组合的视图组件。</p>\r\n<p>Vue 学习起来非常简单，本教程基于 Vue 2.1.8 版本测试。<br><img src=\"http://www.runoob.com/wp-content/uploads/2017/01/vue.png\" alt=\"\"></p>\r\n<h3 id=\"h3-u7B2Cu4E00u4E2Au5B9Eu4F8B\"><a name=\"第一个实例\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>第一个实例</h3><pre><code>&lt;div id=&quot;app&quot;&gt;\r\n  &lt;p&gt;{{ message }}&lt;/p&gt;\r\n&lt;/div&gt;\r\n</code></pre>', 3);
INSERT INTO `article` VALUES (81, 6, 'Python解释器', '2017-12-30 21:55:04', 'python3Python的3.0版本，常被称为Python 3000，或简称Py3k。相对于Python的早期版本，这是一个较大的升级。为了不带入过多的累赘，Python 3.0在设计的时候没有考虑向下兼容。\r\nPython 介绍及安装教程我们在Python 2.X版本的教程中已有介绍，这里就不再', '<p>python3<br>Python的3.0版本，常被称为Python 3000，或简称Py3k。相对于Python的早期版本，这是一个较大的升级。为了不带入过多的累赘，Python 3.0在设计的时候没有考虑向下兼容。</p>\r\n<p>Python 介绍及安装教程我们在Python 2.X版本的教程中已有介绍，这里就不再赘述。</p>\r\n<p>你也可以点击 Python2.x与3​​.x版本区别 来查看两者的不同。</p>\r\n<p>本教程主要针对Python 3.x版本的学习，如果你使用的是Python 2.x版本请移步至Python 2.X版本的教程。</p>\r\n', 0);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `article_id` int(11) NOT NULL COMMENT '文章id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章分类名字',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idxarticle_id`(`article_id`) USING BTREE COMMENT '文章id的普通索引',
  INDEX `idx_user_id`(`user_id`) USING BTREE COMMENT '用户id的普通索引'
) ENGINE = InnoDB AUTO_INCREMENT = 78 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文章分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (46, 49, 6, 'HelloWorld');
INSERT INTO `category` VALUES (47, 50, 7, 'HelloWorld');
INSERT INTO `category` VALUES (76, 80, 6, 'vue');
INSERT INTO `category` VALUES (77, 81, 6, 'Python学习');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `article_id` int(11) NOT NULL COMMENT '文章id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '评论内容',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '评论日期',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_article_id`(`article_id`) USING BTREE COMMENT '文章id的普通索引',
  INDEX `idx_user_id`(`user_id`) USING BTREE COMMENT '用户id的普通索引'
) ENGINE = InnoDB AUTO_INCREMENT = 91 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci COMMENT = '评论信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (47, 50, 7, '<img src=\"/FreeBlogs/img/smilies/24.png\"><img src=\"/FreeBlogs/img/smilies/24.png\">', '2018-08-20 12:07:28');
INSERT INTO `comment` VALUES (49, 49, 7, '<img src=\"/FreeBlogs/img/smilies/19.png\"><img src=\"/FreeBlogs/img/smilies/19.png\"><img src=\"/FreeBlogs/img/smilies/19.png\">', '2018-08-20 12:08:36');
INSERT INTO `comment` VALUES (50, 50, 7, '<img src=\"/FreeBlogs/img/smilies/21.png\"><img src=\"/FreeBlogs/img/smilies/21.png\"><img src=\"/FreeBlogs/img/smilies/21.png\"><img src=\"/FreeBlogs/img/smilies/21.png\">', '2018-08-21 15:30:37');
INSERT INTO `comment` VALUES (57, 56, 6, '<img src=\"/FreeBlogs/img/smilies/12.png\">', '2018-08-25 19:47:45');
INSERT INTO `comment` VALUES (82, 61, 7, '<img src=\"/FreeBlogs/img/smilies/21.png\"><img src=\"/FreeBlogs/img/smilies/21.png\">', '2018-09-02 12:14:59');
INSERT INTO `comment` VALUES (83, 61, 7, '<img src=\"/FreeBlogs/img/smilies/22.png\"><img src=\"/FreeBlogs/img/smilies/22.png\">', '2018-09-02 12:15:03');
INSERT INTO `comment` VALUES (84, 61, 7, '<img src=\"/FreeBlogs/img/smilies/24.png\"><img src=\"/FreeBlogs/img/smilies/24.png\"><img src=\"/FreeBlogs/img/smilies/24.png\">', '2018-09-02 12:15:05');
INSERT INTO `comment` VALUES (85, 61, 7, '<img src=\"/FreeBlogs/img/smilies/24.png\">', '2018-09-02 12:15:06');
INSERT INTO `comment` VALUES (86, 61, 7, '<img src=\"/FreeBlogs/img/smilies/20.png\">', '2018-09-02 12:15:09');
INSERT INTO `comment` VALUES (87, 61, 7, '<img src=\"/FreeBlogs/img/smilies/5.png\">', '2018-09-02 12:15:11');
INSERT INTO `comment` VALUES (88, 61, 7, '<img src=\"/FreeBlogs/img/smilies/6.png\">', '2018-09-02 12:15:13');
INSERT INTO `comment` VALUES (89, 61, 7, '<img src=\"/FreeBlogs/img/smilies/14.png\">', '2018-09-02 12:15:22');
INSERT INTO `comment` VALUES (90, 80, 6, '<img src=\"/HanaeYuuma/img/smilies/6.png\"><img src=\"/HanaeYuuma/img/smilies/6.png\">加油!加油！', '2018-12-30 21:51:36');

-- ----------------------------
-- Table structure for follower
-- ----------------------------
DROP TABLE IF EXISTS `follower`;
CREATE TABLE `follower`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `following_id` int(11) NOT NULL COMMENT '被关注的用户id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx__user_id`(`user_id`) USING BTREE COMMENT '关注别人的用户id的普通索引',
  INDEX `idx_following_id`(`following_id`) USING BTREE COMMENT '被关注人id的普通索引'
) ENGINE = InnoDB AUTO_INCREMENT = 70 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci COMMENT = '用户关注信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of follower
-- ----------------------------
INSERT INTO `follower` VALUES (18, 2, 3);
INSERT INTO `follower` VALUES (20, 3, 2);
INSERT INTO `follower` VALUES (38, 1, 3);
INSERT INTO `follower` VALUES (39, 1, 2);
INSERT INTO `follower` VALUES (43, 2, 1);
INSERT INTO `follower` VALUES (60, 7, 2);
INSERT INTO `follower` VALUES (63, 7, 1);
INSERT INTO `follower` VALUES (66, 6, 7);
INSERT INTO `follower` VALUES (67, 6, 2);
INSERT INTO `follower` VALUES (68, 6, 1);
INSERT INTO `follower` VALUES (69, 1, 6);

-- ----------------------------
-- Table structure for friend
-- ----------------------------
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '友链名称',
  `link` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '友链地址',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE COMMENT '用户id的普通索引'
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of friend
-- ----------------------------
INSERT INTO `friend` VALUES (1, 1, '软件那些事儿', 'https://liuyandong.com');
INSERT INTO `friend` VALUES (2, 1, '阮一峰', 'http://www.ruanyifeng.com/home.html');
INSERT INTO `friend` VALUES (3, 1, '张馆长', 'https://zhangguanzhang.github.io/');
INSERT INTO `friend` VALUES (7, 6, '软件那些事儿', 'https://liuyandong.com');
INSERT INTO `friend` VALUES (8, 6, '刘延栋', 'https://liuyandong.com');
INSERT INTO `friend` VALUES (9, 6, '测试', 'https://liuyandong.com');

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `admin_id` int(11) NOT NULL COMMENT '用户id',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '公告标题',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '发布时间',
  `sumary` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '公告摘要',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发布内容',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_admin_id`(`admin_id`) USING BTREE COMMENT '管理员id的普通索引'
) ENGINE = InnoDB AUTO_INCREMENT = 106 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '公告信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (7, 1002, '这是第1条测试mapper', '2018-07-30 12:51:16', '测试08943', '测试08943');
INSERT INTO `notice` VALUES (8, 1002, '这是第2条测试mapper', '2018-07-30 12:51:16', '测试ffdc3', '测试ffdc3');
INSERT INTO `notice` VALUES (9, 1002, '这是第3条测试mapper', '2018-07-30 12:51:16', '测试23c17', '测试23c17');
INSERT INTO `notice` VALUES (10, 1002, '这是第4条测试mapper', '2018-07-30 12:51:16', '测试c9d5e', '测试c9d5e');
INSERT INTO `notice` VALUES (11, 1002, '这是第5条测试mapper', '2018-07-30 12:51:16', '测试6bd07', '测试6bd07');
INSERT INTO `notice` VALUES (12, 1002, '这是第6条测试mapper', '2018-07-30 12:51:16', '测试82600', '测试82600');
INSERT INTO `notice` VALUES (13, 1002, '这是第7条测试mapper', '2018-07-30 12:51:16', '测试6bba0', '测试6bba0');
INSERT INTO `notice` VALUES (14, 1002, '这是第8条测试mapper', '2018-07-30 12:51:16', '测试b730a', '测试b730a');
INSERT INTO `notice` VALUES (15, 1002, '这是第9条测试mapper', '2018-07-30 12:51:16', '测试211ac', '测试211ac');
INSERT INTO `notice` VALUES (16, 1002, '这是第10条测试mapper', '2018-07-30 12:51:16', '测试7853f', '测试7853f');
INSERT INTO `notice` VALUES (17, 1002, '这是第11条测试mapper', '2018-07-30 12:51:16', '测试a82f1', '测试a82f1');
INSERT INTO `notice` VALUES (18, 1002, '这是第12条测试mapper', '2018-07-30 12:51:16', '测试8431e', '测试8431e');
INSERT INTO `notice` VALUES (19, 1002, '这是第13条测试mapper', '2018-07-30 12:51:16', '测试6ed85', '测试6ed85');
INSERT INTO `notice` VALUES (20, 1002, '这是第14条测试mapper', '2018-07-30 12:51:16', '测试f6015', '测试f6015');
INSERT INTO `notice` VALUES (21, 1002, '这是第15条测试mapper', '2018-07-30 12:51:16', '测试58f74', '测试58f74');
INSERT INTO `notice` VALUES (22, 1002, '这是第16条测试mapper', '2018-07-30 12:51:16', '测试bea0c', '测试bea0c');
INSERT INTO `notice` VALUES (23, 1002, '这是第17条测试mapper', '2018-07-30 12:51:16', '测试db66f', '测试db66f');
INSERT INTO `notice` VALUES (24, 1002, '这是第18条测试mapper', '2018-07-30 12:51:16', '测试758d4', '测试758d4');
INSERT INTO `notice` VALUES (25, 1002, '这是第19条测试mapper', '2018-07-30 12:51:16', '测试71e1a', '测试71e1a');
INSERT INTO `notice` VALUES (26, 1002, '这是第20条测试mapper', '2018-07-30 12:51:16', '测试3d339', '测试3d339');
INSERT INTO `notice` VALUES (27, 1002, '这是第21条测试mapper', '2018-07-30 12:51:16', '测试4c556', '测试4c556');
INSERT INTO `notice` VALUES (28, 1002, '这是第22条测试mapper', '2018-07-30 12:51:16', '测试fcb0c', '测试fcb0c');
INSERT INTO `notice` VALUES (29, 1002, '这是第23条测试mapper', '2018-07-30 12:51:16', '测试fa558', '测试fa558');
INSERT INTO `notice` VALUES (30, 1002, '这是第24条测试mapper', '2018-07-30 12:51:16', '测试51326', '测试51326');
INSERT INTO `notice` VALUES (31, 1002, '这是第25条测试mapper', '2018-07-30 12:51:16', '测试c0edc', '测试c0edc');
INSERT INTO `notice` VALUES (32, 1002, '这是第26条测试mapper', '2018-07-30 12:51:16', '测试40425', '测试40425');
INSERT INTO `notice` VALUES (33, 1002, '这是第27条测试mapper', '2018-07-30 12:51:16', '测试11672', '测试11672');
INSERT INTO `notice` VALUES (34, 1002, '这是第28条测试mapper', '2018-07-30 12:51:16', '测试2936a', '测试2936a');
INSERT INTO `notice` VALUES (35, 1002, '这是第29条测试mapper', '2018-07-30 12:51:16', '测试02a87', '测试02a87');
INSERT INTO `notice` VALUES (36, 1002, '这是第30条测试mapper', '2018-07-30 12:51:16', '测试40c8d', '测试40c8d');
INSERT INTO `notice` VALUES (37, 1002, '这是第31条测试mapper', '2018-07-30 12:51:16', '测试06ca4', '测试06ca4');
INSERT INTO `notice` VALUES (38, 1002, '这是第32条测试mapper', '2018-07-30 12:51:16', '测试712c3', '测试712c3');
INSERT INTO `notice` VALUES (39, 1002, '这是第33条测试mapper', '2018-07-30 12:51:16', '测试de98c', '测试de98c');
INSERT INTO `notice` VALUES (40, 1002, '这是第34条测试mapper', '2018-07-30 12:51:16', '测试ba82b', '测试ba82b');
INSERT INTO `notice` VALUES (41, 1002, '这是第35条测试mapper', '2018-07-30 12:51:16', '测试de02a', '测试de02a');
INSERT INTO `notice` VALUES (42, 1002, '这是第36条测试mapper', '2018-07-30 12:51:16', '测试a4140', '测试a4140');
INSERT INTO `notice` VALUES (43, 1002, '这是第37条测试mapper', '2018-07-30 12:51:16', '测试cc1cb', '测试cc1cb');
INSERT INTO `notice` VALUES (44, 1002, '这是第38条测试mapper', '2018-07-30 12:51:16', '测试5bbbd', '测试5bbbd');
INSERT INTO `notice` VALUES (45, 1002, '这是第39条测试mapper', '2018-07-30 12:51:16', '测试7cd57', '测试7cd57');
INSERT INTO `notice` VALUES (46, 1002, '这是第40条测试mapper', '2018-07-30 12:51:16', '测试67b85', '测试67b85');
INSERT INTO `notice` VALUES (47, 1002, '这是第41条测试mapper', '2018-07-30 12:51:16', '测试036ce', '测试036ce');
INSERT INTO `notice` VALUES (48, 1002, '这是第42条测试mapper', '2018-07-30 12:51:16', '测试4ba98', '测试4ba98');
INSERT INTO `notice` VALUES (49, 1002, '这是第43条测试mapper', '2018-07-30 12:51:16', '测试672c1', '测试672c1');
INSERT INTO `notice` VALUES (50, 1002, '这是第44条测试mapper', '2018-07-30 12:51:16', '测试976a4', '测试976a4');
INSERT INTO `notice` VALUES (77, 1002, '这是第71条测试mapper', '2018-07-30 12:51:17', '测试5fbe2', '测试5fbe2');
INSERT INTO `notice` VALUES (78, 1002, '这是第72条测试mapper', '2018-07-30 12:51:17', '测试c6965', '测试c6965');
INSERT INTO `notice` VALUES (79, 1002, '这是第73条测试mapper', '2018-07-30 12:51:17', '测试e7e36', '测试e7e36');
INSERT INTO `notice` VALUES (80, 1002, '这是第74条测试mapper', '2018-07-30 12:51:17', '测试01a96', '测试01a96');
INSERT INTO `notice` VALUES (81, 1002, '这是第75条测试mapper', '2018-07-30 12:51:17', '测试570c4', '测试570c4');
INSERT INTO `notice` VALUES (82, 1002, '这是第76条测试mapper', '2018-07-30 12:51:17', '测试708ae', '测试708ae');
INSERT INTO `notice` VALUES (83, 1002, '这是第77条测试mapper', '2018-07-30 12:51:17', '测试46b4b', '测试46b4b');
INSERT INTO `notice` VALUES (84, 1002, '这是第78条测试mapper', '2018-07-30 12:51:17', '测试90443', '测试90443');
INSERT INTO `notice` VALUES (85, 1002, '这是第79条测试mapper', '2018-07-30 12:51:17', '测试8c8a0', '测试8c8a0');
INSERT INTO `notice` VALUES (86, 1002, '这是第80条测试mapper', '2018-07-30 12:51:17', '测试56ebb', '测试56ebb');
INSERT INTO `notice` VALUES (87, 1002, '这是第81条测试mapper', '2018-07-30 12:51:17', '测试033b1', '测试033b1');
INSERT INTO `notice` VALUES (88, 1002, '这是第82条测试mapper', '2018-07-30 12:51:17', '测试775fd', '测试775fd');
INSERT INTO `notice` VALUES (89, 1002, '这是第83条测试mapper', '2018-07-30 12:51:17', '测试0847c', '测试0847c');
INSERT INTO `notice` VALUES (90, 1002, '这是第84条测试mapper', '2018-07-30 12:51:17', '测试0116e', '测试0116e');
INSERT INTO `notice` VALUES (91, 1002, '这是第85条测试mapper', '2018-07-30 12:51:17', '测试b5ea2', '测试b5ea2');
INSERT INTO `notice` VALUES (92, 1002, '这是第86条测试mapper', '2018-07-30 12:51:17', '测试84792', '测试84792');
INSERT INTO `notice` VALUES (93, 1002, '这是第87条测试mapper', '2018-07-30 12:51:17', '测试dee77', '测试dee77');
INSERT INTO `notice` VALUES (94, 1002, '这是第88条测试mapper', '2018-07-30 12:51:17', '测试ac97c', '测试ac97c');
INSERT INTO `notice` VALUES (95, 1002, '这是第89条测试mapper', '2018-07-30 12:51:17', '测试3df34', '测试3df34');
INSERT INTO `notice` VALUES (96, 1002, '这是第90条测试mapper', '2018-07-30 12:51:17', '测试fd4b3', '测试fd4b3');
INSERT INTO `notice` VALUES (97, 1002, '这是第91条测试mapper', '2018-07-30 12:51:17', '测试f319c', '测试f319c');
INSERT INTO `notice` VALUES (98, 1002, '这是第92条测试mapper', '2018-07-30 12:51:17', '测试08507', '测试08507');
INSERT INTO `notice` VALUES (99, 1002, '这是第93条测试mapper', '2018-07-30 12:51:17', '测试af7a5', '测试af7a5');
INSERT INTO `notice` VALUES (100, 1002, '这是第94条测试mapper', '2018-07-30 12:51:17', '测试97733', '测试97733');
INSERT INTO `notice` VALUES (101, 1002, '这是第95条测试mapper', '2018-07-30 12:51:17', '测试77075', '测试77075');
INSERT INTO `notice` VALUES (102, 1002, '这是第96条测试mapper', '2018-07-30 12:51:17', '测试8f5dc', '测试8f5dc');
INSERT INTO `notice` VALUES (103, 1002, '这是第97条测试mapper', '2018-07-30 12:51:17', '测试2f5c7', '测试2f5c7');
INSERT INTO `notice` VALUES (104, 1002, '这是第98条测试mapper', '2018-07-30 12:51:17', '测试df97a', '测试df97a');
INSERT INTO `notice` VALUES (105, 1002, '这是第99条测试mapper', '2018-07-30 12:51:17', '测试3af37', '测试3af37');

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `article_id` int(11) NOT NULL COMMENT '文章id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章标签名字',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_article_id`(`article_id`) USING BTREE COMMENT '文章id的普通索引',
  INDEX `idx_user_id`(`user_id`) USING BTREE COMMENT '用户id的普通索引'
) ENGINE = InnoDB AUTO_INCREMENT = 78 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '标签信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES (46, 49, 6, 'HelloWorld');
INSERT INTO `tag` VALUES (47, 50, 7, 'HelloWorld');
INSERT INTO `tag` VALUES (53, 56, 6, 'fdsaf');
INSERT INTO `tag` VALUES (57, 61, 7, 'f\'d\'sa\'f');
INSERT INTO `tag` VALUES (76, 80, 6, 'vue');
INSERT INTO `tag` VALUES (77, 81, 6, 'Python');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_username` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '昵称',
  `user_password` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `user_email` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱(登录名)',
  `user_sex` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'M' COMMENT '用户性别',
  `user_avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '/FreeBlogs/img/comm/default-avatar.jpg' COMMENT '头像',
  `user_description` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '简单一句话，介绍你自己' COMMENT '简介',
  `user_github` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '${APP_PATH}/index.html' COMMENT 'github',
  `user_twitter` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '${APP_PATH}/index.html' COMMENT 'twitter',
  `user_weibo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '${APP_PATH}/index.html' COMMENT 'weibo',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '咕咕咕', 'dd4b21e9ef71e1291183a46b913ae6f2', '614784818@qq.com', 'M', 'https://tvax2.sinaimg.cn/crop.0.0.480.480.180/005K7HjSly8ftn30yq0krj30dc0dcwgc.jpg', '爱学习，爱生活。', 'https://github.com/HanaeYuuma/Suzuki', 'https://twitter.com/sakayuki0', 'https://weibo.com/u/5262210436');
INSERT INTO `user` VALUES (2, '苏苏苏', 'dd4b21e9ef71e1291183a46b913ae6f2', 'chenshaojing@gmail.com', 'F', 'https://tse4.mm.bing.net/th?id=OIP.OzEGru3Gy7t1TgR69PKP5gHaEK&pid=Api', '是苏苏苏呀，与君初相识，犹如故人归。', 'https://github.com/HanaeYuuma/Suzuki', 'https://twitter.com/sakayuki0', 'https://weibo.com/u/5262210436');
INSERT INTO `user` VALUES (3, '不说再见', 'dd4b21e9ef71e1291183a46b913ae6f2', 'hanaeyuuma@163.com', 'M', 'http://i.ce.cn/ce/culture/gd/201503/10/W020150310564712595750.jpg', 'Welcome to Suzuki', NULL, NULL, NULL);
INSERT INTO `user` VALUES (6, '阿轴', 'dd4b21e9ef71e1291183a46b913ae6f2', '1174355343@qq.com', 'F', 'http://imgtu.5011.net/uploads/content/20170401/2052071491035431.jpg', '離れる道行く、未来とも知らず。', 'https://github.com/HanaeYuuma/Suzuki', 'https://twitter.com/sakayuki0', 'https://weibo.com/u/5262210436');
INSERT INTO `user` VALUES (7, 'NAN', 'dd4b21e9ef71e1291183a46b913ae6f2', '1499342956@qq.com', 'M', 'https://pic.qqtn.com/up/2017-10/15082099205103498.jpg', '努力创造未来', '${APP_PATH}/index.html', '${APP_PATH}/index.html', '${APP_PATH}/index.html');

SET FOREIGN_KEY_CHECKS = 1;

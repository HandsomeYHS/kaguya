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

 Date: 25/04/2019 11:10:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gallery
-- ----------------------------
DROP TABLE IF EXISTS `gallery`;
CREATE TABLE `gallery`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_code` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'use unique code',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'gallery unique code',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `picture_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `authority_type` char(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'PUB' COMMENT '\'PUB\' or \'PRI\',  default: \'PUB\'',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `IDX_USER_CODE`(`user_code`) USING BTREE,
  UNIQUE INDEX `IDX_TITLE`(`title`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'gallery repository table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for repository
-- ----------------------------
DROP TABLE IF EXISTS `repository`;
CREATE TABLE `repository`  (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `user_code` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'repository unique key',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `description` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `authority_type` char(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'PUB' COMMENT '\'PUB\' or \'PRI\',  default: \'PUB\'',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `IDX_TITLE`(`title`) USING BTREE,
  INDEX `IDX_USER_CODE`(`user_code`) USING BTREE,
  FULLTEXT INDEX `IDX_CONTENT`(`content`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'repository table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_code` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'user unique code',
  `username` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bio` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `gravatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `github` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `company` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `organizations` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `regitster_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `IDX_USER_CODE`(`user_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'user info table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '614784818', 'AkaneMurakawa', 'working', 'chenshjing@gmail.com', '123456', '一个想写框架和架构不想写业务的苦逼后端\r\n', 'https://avatars2.githubusercontent.com/u/23401691?s=400&u=9113c7e12ff94f184206c1d30a33e58dd6c960bd&v=4', 'https://github.com/AkaneMurakawa', NULL, NULL, 'Sukushiki-mono', '2019-04-25 09:27:49');

SET FOREIGN_KEY_CHECKS = 1;

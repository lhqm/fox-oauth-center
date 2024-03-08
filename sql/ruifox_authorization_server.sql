/*
 Navicat Premium Data Transfer

 Source Server         : 本地机器
 Source Server Type    : MySQL
 Source Server Version : 80200 (8.2.0)
 Source Host           : localhost:3306
 Source Schema         : ruifox_authorization_server

 Target Server Type    : MySQL
 Target Server Version : 80200 (8.2.0)
 File Encoding         : 65001

 Date: 08/03/2024 17:59:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for authorization_user
-- ----------------------------
DROP TABLE IF EXISTS `authorization_user`;
CREATE TABLE `authorization_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'RAM表用户统一序列ID',
  `local_id` bigint NULL DEFAULT NULL COMMENT '创建该用户的系统赋给该用户的ID',
  `client_id` int NULL DEFAULT NULL COMMENT '该用户所属的系统编号',
  `platform` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建该用户的三方平台（类似微信、QQ、飞书）等',
  `binding` bigint NULL DEFAULT NULL COMMENT '绑定到的账号唯一序列号（指向id字段）',
  `binding_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '绑定账户识别码。对于一般系统，是账号，对于三方平台，比如微信，是序列号',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '账户昵称',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '账户个人账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '账户密码',
  `avatar` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '用户头像（可以是base64,已做兼容）',
  `phone` bigint NULL DEFAULT NULL COMMENT '用户手机号',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱，可以用于邮箱登录，不再使用额外绑定账号',
  `gender` tinyint NULL DEFAULT NULL COMMENT '性别（0女1男其他均为未知）',
  `depart_id` bigint NULL DEFAULT NULL COMMENT '部门ID',
  `org_id` bigint NULL DEFAULT NULL COMMENT '组织ID',
  `active` tinyint NULL DEFAULT NULL COMMENT '账户状态（0封禁1正常）',
  `del` tinyint NULL DEFAULT 0 COMMENT '账户删除状态（0正常1删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of authorization_user
-- ----------------------------
INSERT INTO `authorization_user` VALUES (1, 1, 0, NULL, NULL, NULL, '锐狐超级管理员', 'ruifox', 'wgxdvQkHRtSfdk7D1LzB8FvyfXPbIqXZKHC7ZB8UK/U=', 'https://img0.baidu.com/it/u=273222448,225449124&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', NULL, 'ruifox@qq.com', 1, NULL, NULL, 1, 0);
INSERT INTO `authorization_user` VALUES (2, 2, 0, NULL, NULL, NULL, '自动注册测试账号', 'autotest', 'wgxdvQkHRtSfdk7D1LzB8FvyfXPbIqXZKHC7ZB8UK/U=', 'https://img0.baidu.com/it/u=273222448,225449124&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', NULL, 'ruifox@qq.com', 1, NULL, NULL, 1, 0);
INSERT INTO `authorization_user` VALUES (3, NULL, 0, '企业微信', NULL, NULL, '何瑞康', '17780309196', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 0);
INSERT INTO `authorization_user` VALUES (4, NULL, 0, '企业微信', NULL, NULL, '谭文杰', '18048443616', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 0);

-- ----------------------------
-- Table structure for oauth_ask_pair
-- ----------------------------
DROP TABLE IF EXISTS `oauth_ask_pair`;
CREATE TABLE `oauth_ask_pair`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '唯一ID',
  `client_id` int NULL DEFAULT NULL COMMENT '客户端关联ID',
  `corp_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '客户端企业或组织的账号',
  `corp_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '客户端企业或组织的密码',
  `channel` int NULL DEFAULT NULL COMMENT '消息渠道（1企微）',
  `app_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'APP编号',
  `redirect_url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '授权回调地址',
  `del` tinyint NULL DEFAULT 0 COMMENT '是否删除（0正常1删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_ask_pair
-- ----------------------------
INSERT INTO `oauth_ask_pair` VALUES (1, 2, 'ww6a7bc28e96f07af5', 'r0r4uHQTGiFS9-m5aM1E2I4OpH5e4J2qUpqjF5PTDT4', 1, '1000031', 'http://ram.mynetworks.net/login/callback?type=qywx', 0);
INSERT INTO `oauth_ask_pair` VALUES (2, 3, 'ww6a7bc28e96f07af5', 'cYdzQPocHFptspHCdU--FclzfGj-Wisq_DW4hfwTgys', 1, '1000025', NULL, 0);

-- ----------------------------
-- Table structure for oauth_clients_info
-- ----------------------------
DROP TABLE IF EXISTS `oauth_clients_info`;
CREATE TABLE `oauth_clients_info`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '客户端自增ID',
  `client_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '客户端开放平台接入ID',
  `client_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '客户端接入秘钥',
  `client_icon` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '客户端图标',
  `client_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '客户端名称',
  `contract_scope` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '允许签约的权限，多个用逗号隔开',
  `allow_url` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '允许授权的URL，多个用逗号隔开',
  `is_code` tinyint NULL DEFAULT NULL COMMENT '是否授权码模式（0否1是，下同）',
  `is_implicit` tinyint NULL DEFAULT NULL COMMENT '是否简单模式',
  `is_password` tinyint NULL DEFAULT NULL COMMENT '是否密码模式',
  `is_client` tinyint NULL DEFAULT NULL COMMENT '是否凭证模式',
  `is_auto_mode` tinyint NULL DEFAULT NULL COMMENT '是否启用全局设置（为1系统自动判断上述四种模式并匹配生效）',
  `is_new_refresh` tinyint NULL DEFAULT NULL COMMENT '使用refresh_token刷新token以后是否同步刷新refresh_token',
  `access_token_timeout` bigint NULL DEFAULT NULL COMMENT '认证token过期时间（单位为秒，下同）',
  `refresh_token_timeout` bigint NULL DEFAULT NULL COMMENT '刷新token过期时间',
  `client_token_timeout` bigint NULL DEFAULT NULL COMMENT '客户端会话凭证过期时间',
  `past_client_token_timeout` bigint NULL DEFAULT NULL COMMENT '客户端会话凭证续期时间',
  `del` tinyint NULL DEFAULT NULL COMMENT '逻辑删除（0正常1删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_clients_info
-- ----------------------------
INSERT INTO `oauth_clients_info` VALUES (1, '1001', 'aaaa-bbbb-cccc-dddd-eeee', 'http://xboot.exrick.cn/img/logo-min.bfdd4967.png', '测试客户端', 'userinfo,sysinfo', 'http://10.10.0.29:8002/', 1, 1, 0, 0, 0, 1, 36000, 128000, 360000, NULL, 0);
INSERT INTO `oauth_clients_info` VALUES (2, 'FOXCRM', 'ruifoxcrm', 'https://img2.baidu.com/it/u=3235855801,74793779&fm=253&fmt=auto&app=138&f=JPEG?w=473&h=283', '锐狐客户管理系统', 'all', 'http://www.baidu.com,http://127.0.0.1:8080/,http://127.0.0.1:8081/,http://oa.foxtest.net/,http://10.10.0.55:8080/', 1, 1, 0, 0, 0, 1, 36000, 128000, 360000, NULL, 0);
INSERT INTO `oauth_clients_info` VALUES (3, 'FOXSRE', 'ruifoxsre', NULL, '锐狐运维系统', 'all', 'http://www.baidu.com,http://127.0.0.1:8080/,http://127.0.0.1:8081/,http://oa.foxtest.net/,http://10.10.0.55:8080/', 1, 1, 0, 0, 0, 1, 36000, 128000, 360000, NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;

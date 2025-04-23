/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : easy_erp

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 23/04/2025 18:03:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `user_id` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `sex` tinyint(1) NULL DEFAULT NULL COMMENT '性别，0：女，1：男',
  `department` tinyint(1) NULL DEFAULT NULL COMMENT '部门，0：开发部，1：测试部，2：需求部',
  `position` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职位，0：部门经理，1：小组组长，2：普通成员，3：优秀员工',
  `join_time` date NULL DEFAULT NULL COMMENT '入职年月',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('10000', '张三', '18666666666', '47ec2dd791e31e2ef2076caf64ed9b3d', '2000-07-11', 1, 1, '0,1', NULL);
INSERT INTO `user_info` VALUES ('10008', '李四', '18666666662', '47ec2dd791e31e2ef2076caf64ed9b3d', '2000-07-11', 0, 1, '0,1,2', NULL);
INSERT INTO `user_info` VALUES ('10009', '王五', '18666666663', '47ec2dd791e31e2ef2076caf64ed9b3d', '2000-07-11', 1, 0, '0,1', NULL);
INSERT INTO `user_info` VALUES ('10010', '赵六', '18666666664', '47ec2dd791e31e2ef2076caf64ed9b3d', '2000-07-11', 0, 1, '0,1', NULL);
INSERT INTO `user_info` VALUES ('10011', '老刘', '18666666665', '47ec2dd791e31e2ef2076caf64ed9b3d', '2000-07-11', 1, 2, '0,1,2,3', NULL);
INSERT INTO `user_info` VALUES ('10012', '老罗', '18666668888', 'cd88e9ac92526ed9d0bd06848c5e64d0', '2023-08-15', 1, 1, '2,1', NULL);
INSERT INTO `user_info` VALUES ('6666666666', '蓝豪杰', '18944933260', '30a6d1178e078244a08cee3924bfe206', '2001-05-16', 1, 0, '0', NULL);

SET FOREIGN_KEY_CHECKS = 1;

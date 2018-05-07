/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 50709
Source Host           : localhost:3306
Source Database       : aloo

Target Server Type    : MYSQL
Target Server Version : 50709
File Encoding         : 65001

Date: 2016-11-30 12:17:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cb_dbname
-- ----------------------------
DROP TABLE IF EXISTS `cb_dbname`;
CREATE TABLE `cb_dbname` (
  `id` int(10) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `type` varchar(2) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `CBdb_id` varchar(20) DEFAULT NULL,
  `CBdb_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cb_dbname
-- ----------------------------
INSERT INTO `cb_dbname` VALUES ('0', '0.test', '0', '0', '0', '0', null);

-- ----------------------------
-- Table structure for cb_dbpic
-- ----------------------------
DROP TABLE IF EXISTS `cb_dbpic`;
CREATE TABLE `cb_dbpic` (
  `id` int(10) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `CBdb_id` varchar(10) DEFAULT NULL,
  `CBdb_name` varchar(20) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cb_dbpic
-- ----------------------------
INSERT INTO `cb_dbpic` VALUES ('0', '0', '0', '0', null);

-- ----------------------------
-- Table structure for cb_mission
-- ----------------------------
DROP TABLE IF EXISTS `cb_mission`;
CREATE TABLE `cb_mission` (
  `id` int(20) NOT NULL,
  `mission_id` int(20) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `number` int(20) DEFAULT NULL,
  `time` varchar(20) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cb_mission
-- ----------------------------
INSERT INTO `cb_mission` VALUES ('0', '0', '0', null, null, null);

-- ----------------------------
-- Table structure for cb_resultpic
-- ----------------------------
DROP TABLE IF EXISTS `cb_resultpic`;
CREATE TABLE `cb_resultpic` (
  `id` int(10) NOT NULL,
  `name` varchar(300) DEFAULT NULL,
  `point` varchar(20) DEFAULT NULL,
  `result_picname` varchar(200) DEFAULT NULL,
  `result_CBDBname` varchar(20) DEFAULT NULL,
  `mission_id` int(10) DEFAULT NULL,
  `address_web` varchar(500) DEFAULT NULL,
  `web` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cb_resultpic
-- ----------------------------
INSERT INTO `cb_resultpic` VALUES ('0', '', '', '', '', '0', '', '');

-- ----------------------------
-- Table structure for db_name
-- ----------------------------
DROP TABLE IF EXISTS `db_name`;
CREATE TABLE `db_name` (
  `id` int(10) NOT NULL,
  `user` varchar(200) DEFAULT NULL,
  `mission_name` varchar(200) DEFAULT NULL,
  `query` varchar(100) DEFAULT NULL,
  `fljc_msid` varchar(50) DEFAULT NULL,
  `cbjc_msid` varchar(50) DEFAULT NULL,
  `rljc_msid` varchar(50) DEFAULT NULL,
  `fljc_type` varchar(20) DEFAULT NULL,
  `cbjc_type` varchar(20) DEFAULT NULL,
  `rljc_type` varchar(20) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  `style` varchar(20) DEFAULT NULL,
  `search` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of db_name
-- ----------------------------
INSERT INTO `db_name` VALUES ('0', '0.test', '0', '0', '0', '0', null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for db_pic
-- ----------------------------
DROP TABLE IF EXISTS `db_pic`;
CREATE TABLE `db_pic` (
  `id` int(10) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `bdUrl` varchar(500) DEFAULT NULL,
  `ywUrl` varchar(500) DEFAULT NULL,
  `msid` varchar(200) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `web` varchar(200) DEFAULT NULL,
  `search` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of db_pic
-- ----------------------------
INSERT INTO `db_pic` VALUES ('0', '0', '0', '0', '0', null, '', null);

-- ----------------------------
-- Table structure for moxing
-- ----------------------------
DROP TABLE IF EXISTS `moxing`;
CREATE TABLE `moxing` (
  `id` int(20) NOT NULL,
  `name` varchar(150) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of moxing
-- ----------------------------
INSERT INTO `moxing` VALUES ('0', null, null);

-- ----------------------------
-- Table structure for pic
-- ----------------------------
DROP TABLE IF EXISTS `pic`;
CREATE TABLE `pic` (
  `id` int(11) NOT NULL,
  `name` varchar(150) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `mission_id` int(11) DEFAULT NULL,
  `rate` float(20,18) DEFAULT NULL COMMENT '得分',
  `label` int(5) DEFAULT NULL,
  `review` varchar(20) DEFAULT NULL,
  `address_web` varchar(500) DEFAULT NULL,
  `web` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pic
-- ----------------------------
INSERT INTO `pic` VALUES ('0', null, '0', '0', null, null, null, null, null);

-- ----------------------------
-- Table structure for pic2
-- ----------------------------
DROP TABLE IF EXISTS `pic2`;
CREATE TABLE `pic2` (
  `id` int(11) NOT NULL,
  `name` varchar(150) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `mission_id` int(11) DEFAULT NULL,
  `rate` float(20,18) DEFAULT NULL COMMENT '得分',
  `label` int(5) DEFAULT NULL,
  `review` varchar(20) DEFAULT NULL,
  `address_web` varchar(500) DEFAULT NULL,
  `web` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pic2
-- ----------------------------
INSERT INTO `pic2` VALUES ('0', null, null, '0', null, null, null, null, null);

-- ----------------------------
-- Table structure for rl_dbname
-- ----------------------------
DROP TABLE IF EXISTS `rl_dbname`;
CREATE TABLE `rl_dbname` (
  `id` int(10) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `type` varchar(2) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `CBdb_id` varchar(20) DEFAULT NULL,
  `CBdb_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rl_dbname
-- ----------------------------
INSERT INTO `rl_dbname` VALUES ('0', '0.test', '0', '0', '0', '0', null);

-- ----------------------------
-- Table structure for rl_dbpic
-- ----------------------------
DROP TABLE IF EXISTS `rl_dbpic`;
CREATE TABLE `rl_dbpic` (
  `id` int(10) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `CBdb_id` varchar(10) DEFAULT NULL,
  `CBdb_name` varchar(20) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rl_dbpic
-- ----------------------------
INSERT INTO `rl_dbpic` VALUES ('0', '0', '0', '0', null, null);

-- ----------------------------
-- Table structure for rl_mission
-- ----------------------------
DROP TABLE IF EXISTS `rl_mission`;
CREATE TABLE `rl_mission` (
  `id` int(20) NOT NULL,
  `mission_id` int(20) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `number` int(20) DEFAULT NULL,
  `time` varchar(20) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rl_mission
-- ----------------------------
INSERT INTO `rl_mission` VALUES ('0', '0', '0', null, '', '');

-- ----------------------------
-- Table structure for rl_resultpic
-- ----------------------------
DROP TABLE IF EXISTS `rl_resultpic`;
CREATE TABLE `rl_resultpic` (
  `id` int(10) NOT NULL,
  `name` varchar(300) DEFAULT NULL,
  `point` varchar(20) DEFAULT NULL,
  `result_picname` varchar(200) DEFAULT NULL,
  `result_CBDBname` varchar(20) DEFAULT NULL,
  `mission_id` int(10) DEFAULT NULL,
  `address_web` varchar(500) DEFAULT NULL,
  `web` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rl_resultpic
-- ----------------------------
INSERT INTO `rl_resultpic` VALUES ('0', '', '', '', '', '0', '', '');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `realName` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `tel` varchar(12) DEFAULT NULL,
  `role` int(10) unsigned zerofill DEFAULT '0000000000' COMMENT '是否是核心网评员',
  `openId` varchar(255) DEFAULT NULL COMMENT '微信用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '123456', '超级管理员', 'll@sohu.com', '15652240394', '0000000001', null);
INSERT INTO `user` VALUES ('2', 'bifeng001', '123456', '毕枫001', 'bifeng001@163.com', '12', '0000000000', null);
INSERT INTO `user` VALUES ('3', 'alooficy', 'fenglong1994', '超级管理员', '958622144@qq.com', 'fenglong1994', '0000000001', null);
INSERT INTO `user` VALUES ('4', '1', '1', '1', '1', '1', '0000000001', null);
INSERT INTO `user` VALUES ('5', '2', '2', '2', '2', '2', '0000000001', null);

-- ----------------------------
-- Table structure for weibomission
-- ----------------------------
DROP TABLE IF EXISTS `weibomission`;
CREATE TABLE `weibomission` (
  `id` int(10) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `query` varchar(200) DEFAULT NULL,
  `user` varchar(200) DEFAULT NULL,
  `idkey` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of weibomission
-- ----------------------------
INSERT INTO `weibomission` VALUES ('0', null, null, null, null);

-- ----------------------------
-- Table structure for weibopic
-- ----------------------------
DROP TABLE IF EXISTS `weibopic`;
CREATE TABLE `weibopic` (
  `id` int(20) NOT NULL,
  `msid` int(20) DEFAULT NULL,
  `url` varchar(600) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `address` varchar(600) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of weibopic
-- ----------------------------
INSERT INTO `weibopic` VALUES ('0', null, null, null, null);

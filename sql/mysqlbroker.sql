/*
Navicat MySQL Data Transfer

Source Server         : 10.106.1.52
Source Server Version : 50173
Source Host           : 10.106.1.52:3306
Source Database       : mysqlbroker

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2014-02-10 14:50:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `credentials`
-- ----------------------------
DROP TABLE IF EXISTS `credentials`;
CREATE TABLE `credentials` (
  `username` varchar(128) NOT NULL,
  `password` varchar(512) NOT NULL,
  `host` varchar(32) NOT NULL,
  `port` varchar(5) NOT NULL,
  `url` varchar(512) NOT NULL,
  `date` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `serviceinstance`;
CREATE TABLE `serviceinstance` (
  `id` varchar(128) NOT NULL,
  `planid` varchar(128) DEFAULT NULL,
  `orgid` varchar(128) DEFAULT NULL,
  `spaceid` varchar(128) DEFAULT NULL,
  `name` varchar(128) NOT NULL,
  `date` varchar(128) DEFAULT NULL,
  `servicenodeid` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `servicenodes`
-- ----------------------------
DROP TABLE IF EXISTS `servicenodes`;
CREATE TABLE `servicenodes` (
  `id` varchar(10) NOT NULL,
  `info` varchar(1024) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
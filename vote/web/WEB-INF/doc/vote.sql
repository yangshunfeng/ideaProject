/*
Navicat MySQL Data Transfer

Source Server         : 123.207.164.33
Source Server Version : 50635
Source Host           : 123.207.164.33:3306
Source Database       : vote

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2017-06-25 11:05:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for apply
-- ----------------------------
DROP TABLE IF EXISTS `apply`;
CREATE TABLE `apply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vote_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `introduction` varchar(255) NOT NULL,
  `is_accept` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `vote_id` (`vote_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `apply_ibfk_1` FOREIGN KEY (`vote_id`) REFERENCES `voteinfo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `apply_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of apply
-- ----------------------------
INSERT INTO `apply` VALUES ('1', '1', '1', '得唔得', '1');
INSERT INTO `apply` VALUES ('2', '1', '4', '无', '1');
INSERT INTO `apply` VALUES ('3', '1', '5', '无', '1');
INSERT INTO `apply` VALUES ('4', '1', '6', '无', '1');
INSERT INTO `apply` VALUES ('5', '1', '7', '无', '1');
INSERT INTO `apply` VALUES ('6', '1', '8', '无', '1');
INSERT INTO `apply` VALUES ('7', '1', '9', '无', '1');
INSERT INTO `apply` VALUES ('8', '1', '10', '无', '1');
INSERT INTO `apply` VALUES ('9', '1', '11', '无', '1');
INSERT INTO `apply` VALUES ('10', '1', '12', '无', '1');
INSERT INTO `apply` VALUES ('11', '1', '13', '无', '1');
INSERT INTO `apply` VALUES ('12', '1', '14', '无', '1');

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vote_id` int(11) NOT NULL,
  `send_id` int(11) NOT NULL,
  `aim_id` int(11) NOT NULL,
  `introduction` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `vote_id` (`vote_id`),
  KEY `send_id` (`send_id`),
  KEY `aim_id` (`aim_id`),
  CONSTRAINT `record_ibfk_1` FOREIGN KEY (`vote_id`) REFERENCES `voteinfo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `record_ibfk_2` FOREIGN KEY (`send_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `record_ibfk_3` FOREIGN KEY (`aim_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of record
-- ----------------------------
INSERT INTO `record` VALUES ('1', '1', '1', '1', '1');
INSERT INTO `record` VALUES ('2', '1', '3', '1', 'hehe');
INSERT INTO `record` VALUES ('3', '1', '15', '6', '无');
INSERT INTO `record` VALUES ('4', '1', '16', '9', '无');
INSERT INTO `record` VALUES ('5', '1', '17', '11', '无');
INSERT INTO `record` VALUES ('6', '1', '18', '10', '无');
INSERT INTO `record` VALUES ('7', '1', '19', '11', '无');
INSERT INTO `record` VALUES ('8', '1', '20', '8', '无');
INSERT INTO `record` VALUES ('9', '1', '21', '12', '无');
INSERT INTO `record` VALUES ('10', '1', '22', '8', '无');
INSERT INTO `record` VALUES ('11', '1', '23', '9', '无');
INSERT INTO `record` VALUES ('12', '1', '24', '12', '无');
INSERT INTO `record` VALUES ('13', '1', '25', '13', '无');
INSERT INTO `record` VALUES ('14', '1', '26', '11', '无');
INSERT INTO `record` VALUES ('15', '1', '27', '9', '无');
INSERT INTO `record` VALUES ('16', '1', '28', '13', '无');
INSERT INTO `record` VALUES ('17', '1', '29', '8', '无');
INSERT INTO `record` VALUES ('18', '1', '30', '8', '无');
INSERT INTO `record` VALUES ('19', '1', '31', '9', '无');
INSERT INTO `record` VALUES ('20', '1', '32', '4', '无');
INSERT INTO `record` VALUES ('21', '1', '33', '10', '无');
INSERT INTO `record` VALUES ('22', '1', '34', '8', '无');
INSERT INTO `record` VALUES ('23', '1', '35', '4', '无');
INSERT INTO `record` VALUES ('24', '1', '36', '6', '无');
INSERT INTO `record` VALUES ('25', '1', '37', '13', '无');
INSERT INTO `record` VALUES ('26', '1', '38', '13', '无');
INSERT INTO `record` VALUES ('27', '1', '39', '13', '无');
INSERT INTO `record` VALUES ('28', '1', '40', '8', '无');
INSERT INTO `record` VALUES ('29', '1', '41', '4', '无');
INSERT INTO `record` VALUES ('30', '1', '42', '7', '无');
INSERT INTO `record` VALUES ('31', '1', '43', '7', '无');
INSERT INTO `record` VALUES ('32', '1', '44', '11', '无');
INSERT INTO `record` VALUES ('33', '1', '45', '14', '无');
INSERT INTO `record` VALUES ('34', '1', '46', '4', '无');
INSERT INTO `record` VALUES ('35', '1', '47', '7', '无');
INSERT INTO `record` VALUES ('36', '1', '48', '11', '无');
INSERT INTO `record` VALUES ('37', '1', '49', '8', '无');
INSERT INTO `record` VALUES ('38', '1', '50', '13', '无');
INSERT INTO `record` VALUES ('39', '1', '51', '14', '无');
INSERT INTO `record` VALUES ('40', '1', '52', '6', '无');
INSERT INTO `record` VALUES ('41', '1', '53', '11', '无');
INSERT INTO `record` VALUES ('42', '1', '54', '7', '无');
INSERT INTO `record` VALUES ('43', '1', '55', '5', '无');
INSERT INTO `record` VALUES ('44', '1', '56', '8', '无');
INSERT INTO `record` VALUES ('45', '1', '57', '9', '无');
INSERT INTO `record` VALUES ('46', '1', '58', '14', '无');
INSERT INTO `record` VALUES ('47', '1', '59', '11', '无');
INSERT INTO `record` VALUES ('48', '1', '60', '11', '无');
INSERT INTO `record` VALUES ('49', '1', '61', '7', '无');
INSERT INTO `record` VALUES ('50', '1', '62', '12', '无');
INSERT INTO `record` VALUES ('51', '1', '63', '13', '无');
INSERT INTO `record` VALUES ('52', '1', '64', '7', '无');
INSERT INTO `record` VALUES ('53', '1', '65', '9', '无');
INSERT INTO `record` VALUES ('54', '1', '66', '6', '无');
INSERT INTO `record` VALUES ('55', '1', '67', '7', '无');
INSERT INTO `record` VALUES ('56', '1', '68', '9', '无');
INSERT INTO `record` VALUES ('57', '1', '69', '8', '无');
INSERT INTO `record` VALUES ('58', '1', '70', '13', '无');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `realname` varchar(255) NOT NULL,
  `number` varchar(255) NOT NULL,
  `identity` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '81dc9bdb52d04dc20036dbd8313ed055', '杨春明', '1234', '2');
INSERT INTO `user` VALUES ('2', 'mryang', '81dc9bdb52d04dc20036dbd8313ed055', '杨春明', '1234', '1');
INSERT INTO `user` VALUES ('3', 'wcf', 'e10adc3949ba59abbe56e057f20f883e', 'wcf', '5120141048', '1');
INSERT INTO `user` VALUES ('4', 'team02', '9996535e07258a7bbfd8b132435c5962', '刘禅', '5120141015', '1');
INSERT INTO `user` VALUES ('5', 'team03', '7bccfde7714a1ebadf06c5f4cea752c1', '诸葛亮', '5120141016', '2');
INSERT INTO `user` VALUES ('6', 'team04', 'a9eb812238f753132652ae09963a05e9', '关羽', '5120141017', '2');
INSERT INTO `user` VALUES ('7', 'team05', 'd38901788c533e8286cb6400b40b386d', '张飞', '5120141018', '2');
INSERT INTO `user` VALUES ('8', 'team06', 'b3ba8f1bee1238a2f37603d90b58898d', '赵云', '5120141019', '1');
INSERT INTO `user` VALUES ('9', 'team07', 'a9078e8653368c9c291ae2f8b74012e7', '孙尚香', '5120141020', '1');
INSERT INTO `user` VALUES ('10', 'team08', '1c65cef3dfd1e00c0b03923a1c591db4', '魏延', '5120141021', '1');
INSERT INTO `user` VALUES ('11', 'team09', '2de5d16682c3c35007e4e92982f1a2ba', '庞统', '5120141022', '1');
INSERT INTO `user` VALUES ('12', 'team10', 'e1d5be1c7f2f456670de3d53c7b54f4a', '吕布', '5120141023', '2');
INSERT INTO `user` VALUES ('13', 'team11', '2c89109d42178de8a367c0228f169bf8', '张耕博', '5120165917', '1');
INSERT INTO `user` VALUES ('14', 'team12', '5eac43aceba42c8757b54003a58277b5', '李鑫', '5120166166', '1');
INSERT INTO `user` VALUES ('15', 'team13', '905056c1ac1dad141560467e0a99e1cf', '熊博', '5120166168', '1');
INSERT INTO `user` VALUES ('16', 'team14', 'e6d8545daa42d5ced125a4bf747b3688', '谢林松', '5120166171', '1');
INSERT INTO `user` VALUES ('17', 'team15', '39e4973ba3321b80f37d9b55f63ed8b8', '张治', '5120167406', '1');
INSERT INTO `user` VALUES ('18', 'team16', '2ba8698b79439589fdd2b0f7218d8b07', '孙书乐', '5120160116', '1');
INSERT INTO `user` VALUES ('19', 'team17', '81e5f81db77c596492e6f1a5a792ed53', '张家兴', '5120160119', '1');
INSERT INTO `user` VALUES ('20', 'team18', '838e8afb1ca34354ac209f53d90c3a43', '邓鑫', '5120162145', '1');
INSERT INTO `user` VALUES ('21', 'team19', 'a284df1155ec3e67286080500df36a9a', '丁国锋', '5120167174', '1');
INSERT INTO `user` VALUES ('22', 'team20', 'b495ce63ede0f4efc9eec62cb947c162', '李渝波', '5120162160', '1');
INSERT INTO `user` VALUES ('23', 'team21', '884ce4bb65d328ecb03c598409e2b168', '杨龙', '5120162118', '2');
INSERT INTO `user` VALUES ('24', 'team22', '3bf55bbad370a8fcad1d09b005e278c2', '周荣成', '5120162106', '2');
INSERT INTO `user` VALUES ('25', 'team23', 'e3251075554389fe91d17a794861d47b', '陈耀', '5120162110', '2');
INSERT INTO `user` VALUES ('26', 'team24', 'cd758e8f59dfdf06a852adad277986ca', '刘奇拓', '5120165408', '2');
INSERT INTO `user` VALUES ('27', 'team25', '26588e932c7ccfa1df309280702fe1b5', '雷林', '5120162174', '2');
INSERT INTO `user` VALUES ('28', 'team26', '68a83eeb494a308fe5295da69428a507', '邓学霖', '5120162105', '2');
INSERT INTO `user` VALUES ('29', 'team27', 'f4573fc71c731d5c362f0d7860945b88', '程飞翔', '5120165222', '2');
INSERT INTO `user` VALUES ('30', 'team28', '17326d10d511828f6b34fa6d751739e2', '范泽毅', '5120165220', '2');
INSERT INTO `user` VALUES ('31', 'team29', 'dc4c44f624d600aa568390f1f1104aa0', '周宇康', '5120162175', '2');
INSERT INTO `user` VALUES ('32', 'team30', '7eb3c8be3d411e8ebfab08eba5f49632', '陈泳坤', '5120162176', '2');
INSERT INTO `user` VALUES ('33', 'team31', '6c8dba7d0df1c4a79dd07646be9a26c8', '刘佳宁', '5120165411', '2');
INSERT INTO `user` VALUES ('34', 'team32', 'ce2ffd21fc958d9ef0ee9ba5336e357', '刘子夕', '5120167177', '2');
INSERT INTO `user` VALUES ('35', 'team33', '8e2cfdc275761edc592f73a076197c33', '李静', '5120162208', '2');
INSERT INTO `user` VALUES ('36', 'team34', 'b51a15f382ac914391a58850ab343b00', '邓晓', '5120160133', '1');
INSERT INTO `user` VALUES ('37', 'team35', '193002e668758ea9762904da1a22337c', '陈妍', '5120160132', '1');
INSERT INTO `user` VALUES ('38', 'team36', 'e00406144c1e7e35240afed70f34166a', '黄佳美', '5120162196', '1');
INSERT INTO `user` VALUES ('39', 'team37', 'c850371fda6892fbfd1c5a5b457e5777', '杜小丽', '5120162195', '1');
INSERT INTO `user` VALUES ('40', 'team38', 'c1e39d912d21c91dce811d6da9929ae8', '张佳文', '5120162193', '1');
INSERT INTO `user` VALUES ('41', 'team39', '7810ccd41bf26faaa2c4e1f20db70a71', '杨文璐', '5120162189', '1');
INSERT INTO `user` VALUES ('42', 'team40', 'f91e24dfe80012e2a7984afa4480a6d6', '何沁伟', '5120162205', '1');
INSERT INTO `user` VALUES ('43', 'team41', 'e702e51da2c0f5be4dd354bb3e295d37', '肖嘉欣', '5120165959', '1');
INSERT INTO `user` VALUES ('44', 'team42', 'bb04af0f7ecaee4aae62035497da1387', '刘芳', '5120162201', '1');
INSERT INTO `user` VALUES ('45', 'team43', 'fb60d411a5c5b72b2e7d3527cfc84fd0', '罗媛媛', '5120160134', '1');
INSERT INTO `user` VALUES ('46', 'team44', 'd759175de8ea5b1d9a2660e45554894f', '朱芃璇', '5120166072', '1');
INSERT INTO `user` VALUES ('47', 'team45', 'ab1a4d0dd4d48a2ba1077c4494791306', '李俊良', '5202162139', '1');
INSERT INTO `user` VALUES ('48', 'team46', '995665640dc319973d3173a74a03860c', '胥朝阳', '5202162142', '1');
INSERT INTO `user` VALUES ('49', 'team47', 'da11e8cd1811acb79ccf0fd62cd58f86', '江涛', '5202166173', '1');
INSERT INTO `user` VALUES ('50', 'team48', 'd94e18a8adb4cc0f623f7a83b1ac75b4', '陈波', '5202160109', '1');
INSERT INTO `user` VALUES ('51', 'team49', '1cecc7a77928ca8133fa24680a88d2f9', '任成', '5202160105', '1');
INSERT INTO `user` VALUES ('52', 'team50', '18b59ce1fd616d874afad0f44ba338d', '何纲', '5202162116', '1');
INSERT INTO `user` VALUES ('53', 'team51', '242c100dc94f871b6d7215b868a875f8', '李辉辉', '5202160118', '1');
INSERT INTO `user` VALUES ('54', 'team52', '944bdd9636749a0801c39b6e449dbedc', '刘浩', '5202162173', '1');
INSERT INTO `user` VALUES ('55', 'team53', 'c0a271bc0ecb776a094786474322cb82', '廖家俊', '5202162182', '1');
INSERT INTO `user` VALUES ('56', 'team54', '4079016d940210b4ae9ae7d41c4a2065', '李航', '5202162147', '1');
INSERT INTO `user` VALUES ('57', 'team55', 'e9fa1f3e9e66792401a6972d477dcc3', '杨武', '5202160122', '1');
INSERT INTO `user` VALUES ('58', 'team56', '829424ffa0d3a2547b6c9622c77de03', '刘亮', '5202162153', '1');
INSERT INTO `user` VALUES ('59', 'team57', '70222949cc0db89ab32c9969754d4758', '廖昌圣', '5202159566', '1');
INSERT INTO `user` VALUES ('60', 'team58', '71f6278d140af599e06ad9bf1ba03cb0', '王思杰', '5202162128', '1');
INSERT INTO `user` VALUES ('61', 'team59', '459a4ddcb586f24efd9395aa7662bc7c', '陆硕', '5202165223', '1');
INSERT INTO `user` VALUES ('62', 'team60', '7c82fab8c8f89124e2ce92984e04fb40', '肖芳茂', '5202162133', '1');
INSERT INTO `user` VALUES ('63', 'team61', '84438b7aae55a0638073ef798e50b4ef', '邓皓瀚', '5202165137', '1');
INSERT INTO `user` VALUES ('64', 'team62', 'dc87c13749315c7217cdc4ac692e704c', '林世友', '5202162134', '1');
INSERT INTO `user` VALUES ('65', 'team63', '2812e5cf6d8f21d69c91dddeefb792a7', '邓盼', '5120167178', '1');
INSERT INTO `user` VALUES ('66', 'team64', '286674e3082feb7e5afb92777e48821f', '赵兰萍', '5120162207', '1');
INSERT INTO `user` VALUES ('67', 'team65', 'a51fb975227d6640e4fe47854476d133', '罗月', '5120162206', '1');
INSERT INTO `user` VALUES ('68', 'team66', 'a0833c8a1817526ac555f8d67727caf6', '张雪娇', '5120162191', '1');
INSERT INTO `user` VALUES ('69', 'team67', '6f3e29a35278d71c7f65495871231324', '夏德平', '5120162192', '1');
INSERT INTO `user` VALUES ('70', 'team68', '2df45244f09369e16ea3f9117ca45157', '胡丹丹', '5120160129', '1');

-- ----------------------------
-- Table structure for voteinfo
-- ----------------------------
DROP TABLE IF EXISTS `voteinfo`;
CREATE TABLE `voteinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `is_anonymous` int(11) NOT NULL,
  `state` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voteinfo
-- ----------------------------
INSERT INTO `voteinfo` VALUES ('1', '组长竞选', '啦啦啦~竞选组长', '0', '2');

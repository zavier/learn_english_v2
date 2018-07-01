
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `contact` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `telephone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES (1, 'customer1', 'Jack', '12512345678', 'jack@gmail.com', '11');
INSERT INTO `customer` VALUES (2, 'customer2', 'Rose', '12623456789', 'rose@gmail.com', '22');
INSERT INTO `customer` VALUES (3, 'zzz', 'qqq', '12345678', '123@123.com', '1111');
INSERT INTO `customer` VALUES (4, 'zzz', 'qqq', '12345678', '123@123.com', '1111');
INSERT INTO `customer` VALUES (8, 'zzz', 'qqq', '12345678', '123@123.com', '1111');
INSERT INTO `customer` VALUES (9, 'zzz', 'qqq', '12345678', '123@123.com', '1111');


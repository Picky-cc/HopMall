SET FOREIGN_KEY_CHECKS=0;

-- 2016.06.13

ALTER TABLE principal 
ADD UNIQUE KEY `name` (`name`),
DROP COLUMN `key_id`,
ADD COLUMN `t_user_id` bigint(20) DEFAULT NULL COMMENT '所属用户',
ADD COLUMN `created_time` datetime DEFAULT NULL COMMENT '创建时间',
ADD COLUMN `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人';

CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '用户名字',
  `email` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '联系邮箱',
  `phone` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '联系电话',
  `company_id` bigint(20) DEFAULT NULL COMMENT '所属公司',
  `dept_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '部门名称',
  `position_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '职位名称',
  `remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 2016.06.13

SET FOREIGN_KEY_CHECKS=1;

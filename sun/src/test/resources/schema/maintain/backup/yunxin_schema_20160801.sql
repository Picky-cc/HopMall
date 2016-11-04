SET FOREIGN_KEY_CHECKS=0;

UPDATE `asset_set` SET `carry_over_type` = 0 WHERE `carry_over_type` IS NULL;

-- 还款计划表，增加还款计划类型字段
ALTER TABLE `asset_set` CHANGE `carry_over_type` `repayment_plan_type` tinyint(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '还款计划类型 (0:正常，1:提前还款)';

ALTER TABLE `asset_set` DROP COLUMN `carry_over_no`;

-- 提前还款申请表，增加提前还款状态字段
ALTER TABLE `t_prepayment_application` ADD COLUMN `prepayment_status` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '提前还款状态 0:未还款，1：还款成功，2:还款失败';

-- 提前还款申请表，添加完成时间字段
ALTER TABLE `t_prepayment_application` ADD COLUMN `completed_time` datetime DEFAULT NULL COMMENT '完成时间';

-- 20160728 start
ALTER TABLE `app` ADD COLUMN `pub_key_path` TEXT DEFAULT NULL COLLATE utf8_unicode_ci COMMENT '商户公钥地址';

ALTER TABLE `app` ADD COLUMN `pub_key` TEXT DEFAULT NULL COLLATE utf8_unicode_ci COMMENT '商户公钥';

ALTER TABLE `app` DROP COLUMN `pub_key_path`;

ALTER TABLE `app` DROP COLUMN `pub_key`;

-- 20160728 end

-- 20160729 start
DROP TABLE IF EXISTS `t_merchant_config`;

CREATE TABLE `t_merchant_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mer_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商户编号',
  `secret` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商户唯一标识',
  `company_id` bigint(20) NOT NULL COMMENT '商户公司编号(company表id)',
  `pub_key_path` text COLLATE utf8_unicode_ci COMMENT '商户公钥地址',
  `pub_key` text COLLATE utf8_unicode_ci COMMENT '商户公钥',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='商户API请求配置表';

ALTER TABLE `dictionary` CHANGE `content` `content` text COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '字典内容';

-- 20160729 end

SET FOREIGN_KEY_CHECKS=1;

SET FOREIGN_KEY_CHECKS=0;

-- 2016.06.07
DROP TABLE IF EXISTS `system_operate_log`;

CREATE TABLE `system_operate_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `ip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `object_uuid`  varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL, 
  `log_function_type` int(11) DEFAULT '0',
  `log_operate_type` int(11)  DEFAULT '0',
  `key_content` text COLLATE utf8_unicode_ci,
  `update_content_detail` text COLLATE utf8_unicode_ci,
  `record_content_detail` text COLLATE utf8_unicode_ci,
  `record_content` text COLLATE utf8_unicode_ci ,
  `occur_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 2016.06.07

-- 2016.06.14
ALTER TABLE `loan_batch` add column `loan_batch_uuid`  varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL;

ALTER TABLE `financial_contract` add column `financial_contract_uuid`  varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL;
-- 2016.06.14

SET FOREIGN_KEY_CHECKS=1;

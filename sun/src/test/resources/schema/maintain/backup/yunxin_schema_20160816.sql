SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for payment_channel_information
-- ----------------------------
DROP TABLE IF EXISTS `payment_channel_information`;
CREATE TABLE `payment_channel_information` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `related_financial_contract_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `related_financial_contract_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `payment_channel_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `payment_channel_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `payment_institution_name` int(11) DEFAULT '0',
  `outlier_channel_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  `credit_channel_working_status` tinyint(1) DEFAULT NULL,
  `debit_channel_working_status` tinyint(1) DEFAULT NULL,
  `configure_progress` tinyint(1) DEFAULT NULL,
  `payment_configure_data` text COLLATE utf8_unicode_ci,
  `clearing_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `business_type` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for financial_contract_config
-- ----------------------------
DROP TABLE IF EXISTS `financial_contract_config`;
CREATE TABLE `financial_contract_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `financial_contract_uuid` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `business_type` tinyint(1) DEFAULT NULL,
  `payment_channel_uuids_for_credit` varchar(520) COLLATE utf8_unicode_ci DEFAULT NULL,
  `payment_channel_uuids_for_debit` varchar(520) COLLATE utf8_unicode_ci DEFAULT NULL,
  `credit_payment_channel_mode` tinyint(1) DEFAULT NULL,
  `debit_payment_channel_mode` tinyint(1) DEFAULT NULL,
  `payment_channel_router_for_credit` varchar(520) COLLATE utf8_unicode_ci DEFAULT NULL,
  `payment_channel_router_for_debit` varchar(520) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `transfer_application` ADD COLUMN `payment_channel_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL;

SET FOREIGN_KEY_CHECKS=1;
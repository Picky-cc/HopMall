SET FOREIGN_KEY_CHECKS=0;

-- 银联银行配置
DROP TABLE IF EXISTS `unionpay_bank_config`;

CREATE TABLE `unionpay_bank_config` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `bank_code` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `bank_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `use_batch_deduct` tinyint(1) DEFAULT '0' COMMENT '0:不使用，1:使用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `bank_code` (`bank_code`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `unionpay_bank_config` (`id`, `bank_code`, `bank_name`, `use_batch_deduct`)
VALUES
	(1, '403', '中国邮政储蓄银行', 1);
	
-- 银联手动交易（测试用表）

DROP TABLE IF EXISTS `unionpay_manual_transaction`;

CREATE TABLE `unionpay_manual_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `bank_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bank_card_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bank_card_owner` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_card_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `province` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `city` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `amount` decimal(19,2) DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `unionpay_transaction_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `transaction_type` int(9) NOT NULL DEFAULT '0' COMMENT '0:实时代收，1:批量代收',
  `payment_channel_id` int(11) NOT NULL DEFAULT '1',
  `request_packet` text COLLATE utf8_unicode_ci,
  `response_packet` text COLLATE utf8_unicode_ci,
  `query_response_packet` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`),
  UNIQUE KEY `transaction_no` (`unionpay_transaction_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

SET FOREIGN_KEY_CHECKS=1;

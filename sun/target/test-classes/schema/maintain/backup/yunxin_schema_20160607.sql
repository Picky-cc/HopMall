SET FOREIGN_KEY_CHECKS=0;

-- 2016-06-03 start

DROP TABLE IF EXISTS `usb_key`;
CREATE TABLE `usb_key` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bank_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `config` text COLLATE utf8_unicode_ci,
  `key_type` int(11) DEFAULT NULL,
  `uuid` varchar(255) COLLATE utf8_unicode_ci  DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


DROP TABLE IF EXISTS `app_arrive_record`;
CREATE TABLE `app_arrive_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` decimal(19,2) DEFAULT NULL,
  `arrive_record_status` int(11) DEFAULT NULL,
  `pay_ac_no` varchar(255) COLLATE utf8_unicode_ci  DEFAULT NULL,
  `receive_ac_no` varchar(255) COLLATE utf8_unicode_ci  DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8_unicode_ci  DEFAULT NULL,
  `serial_no` varchar(255) COLLATE utf8_unicode_ci  DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `app_id` bigint(20) NOT NULL,
  `drcrf` varchar(255) COLLATE utf8_unicode_ci  DEFAULT NULL,
  `pay_name` varchar(255) COLLATE utf8_unicode_ci  DEFAULT NULL,
  `summary` varchar(255) COLLATE utf8_unicode_ci  DEFAULT NULL,
  `vouh_no` varchar(255) COLLATE utf8_unicode_ci  DEFAULT NULL,
  `cash_flow_uid` varchar(255) COLLATE utf8_unicode_ci  DEFAULT NULL,
  `detail_data` text,
  `cash_flow_channel_type` int(11) DEFAULT '0',
  `transaction_type` int(11) DEFAULT '0',
  `partner_id` varchar(255) COLLATE utf8_unicode_ci  DEFAULT NULL,
  `audit_status` int(11) DEFAULT '0',
  `issued_amount` decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

alter table `account`
add column `scan_cash_flow_switch` bit(1) DEFAULT 0,
add column `usb_key_configured` bit(1) DEFAULT 0;

-- 2016-06-03 end
SET FOREIGN_KEY_CHECKS=1;
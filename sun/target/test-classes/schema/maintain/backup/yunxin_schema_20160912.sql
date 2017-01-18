SET FOREIGN_KEY_CHECKS=0;

ALTER TABLE financial_contract ADD COLUMN `unusual_modify_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '变更当日还款计划授权标示，0:未授权，1:已授权';

DROP TABLE IF EXISTS `asset_set_extra_charge`;
CREATE TABLE `asset_set_extra_charge` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `asset_set_extra_charge_uuid` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '还款计划费用明细uuid',
  `asset_set_uuid` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '还款计划uuid',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_modify_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  `first_account_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `first_account_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `second_account_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `second_account_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `third_account_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `third_account_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `account_amount` decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=309 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='还款计划额外费用明细表';

ALTER TABLE t_deduct_application_detail ADD COLUMN `asset_set_uuid` VARCHAR(36) COLLATE utf8_unicode_ci  DEFAULT NULL after `repayment_plan_code`;

ALTER TABLE `unionpay_bank_config` add COLUMN  `standard_bank_code` VARCHAR(255) COLLATE utf8_unicode_ci  DEFAULT NULL;

DROP TABLE IF EXISTS `t_interface_modfify_overdue_fee_log`;
CREATE TABLE `t_interface_modfify_overdue_fee_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `request_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `over_due_fee_data` text COLLATE utf8_unicode_ci,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `ip_address` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `request_no` (`request_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `t_api_config` (`id`, `api_url`, `fn_code`, `description`, `api_status`) VALUES (11, '/api/modify', '200005', '逾期费用变更接口', '1');

SET FOREIGN_KEY_CHECKS=1;

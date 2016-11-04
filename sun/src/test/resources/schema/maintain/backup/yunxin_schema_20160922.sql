SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `t_remittance_refund_bill`;
CREATE TABLE `t_remittance_refund_bill` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `remittance_refund_bill_uuid` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '撤销单号',
  `financial_contract_uuid` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '信托合同uuid',
  `financial_contract_id` bigint(20) DEFAULT NULL COMMENT '信托合同Id',
  `channel_cash_flow_no` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '通道流水号',
  `payment_channel_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '支付通道名称',
  `payment_gateway` tinyint(1) unsigned DEFAULT NULL COMMENT '支付网关0:广州银联，1:超级网银，2:银企直连',
  `payment_channel_uuid` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '支付通道uuid',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `transaction_time` datetime DEFAULT NULL COMMENT '交易时间',
  `host_account_uuid` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '发生账户uuid',
  `host_account_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '发生账号',
  `host_account_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '发生账户名',
  `counter_account_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '对方账号',
  `counter_account_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '对方账户名',
  `related_exec_req_no` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '代付单号',
  `transaction_type` tinyint(1) unsigned DEFAULT NULL COMMENT '交易类型0:贷，1:借',
  `amount` decimal(19,2) DEFAULT NULL COMMENT '金额',
  `last_modified_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  KEY `remittance_refund_bill_uuid` (`remittance_refund_bill_uuid`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='放款计划执行日志';


DROP TABLE IF EXISTS `virtual_account_flow`;

CREATE TABLE `virtual_account_flow` (
`id`  bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增长编号',
`account_side`  TINYINT(1) UNSIGNED DEFAULT NULL COMMENT '收支类型',
`balance`  decimal(19,2) DEFAULT NULL COMMENT '瞬时余额',
`create_time`  datetime DEFAULT NULL COMMENT '创建时间',
`transaction_amount`  decimal(19,2) DEFAULT NULL COMMENT '交易金额',
`uuid`  varchar(255) COLLATE utf8_unicode_ci DEFAULT  NULL DEFAULT NULL COMMENT 'uuid',
`virtual_account_alias`  varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '账户名称',
`virtual_account_flow_no`  varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '账户流水号',
`virtual_account_no`  varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '账户编号',
`virtual_account_uuid`  varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '虚拟账户uuid',
`transaction_type`  TINYINT(1) UNSIGNED DEFAULT NULL COMMENT '交易类型',
`business_document_no`  varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易号',
PRIMARY KEY (`id`)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE `virtual_account_flow` ADD INDEX business_document_no ( `business_document_no` ) USING BTREE;
ALTER TABLE `virtual_account_flow` ADD INDEX virtual_account_flow_no ( `virtual_account_flow_no` ) USING BTREE;
ALTER TABLE `virtual_account_flow` ADD INDEX virtual_account_no ( `virtual_account_no` ) USING BTREE;


ALTER TABLE `journal_voucher` ADD COLUMN `journal_voucher_no`  varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '业务单号';
update `journal_voucher` set `journal_voucher_no`=uuid() where journal_voucher_no is NULL;

ALTER TABLE `t_deduct_application` ADD COLUMN  `customer_name`  VARCHAR(255) COLLATE utf8_unicode_ci;

SET FOREIGN_KEY_CHECKS=1;

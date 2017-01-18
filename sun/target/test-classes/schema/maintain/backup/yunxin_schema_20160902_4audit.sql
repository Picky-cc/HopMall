SET FOREIGN_KEY_CHECKS=0;

-- 
DROP TABLE IF EXISTS `app_arrive_record`;
CREATE TABLE `app_arrive_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` decimal(19,2) DEFAULT NULL,
  `arrive_record_status` int(11) DEFAULT NULL,
  `pay_ac_no` varchar(255) DEFAULT NULL,
  `receive_ac_no` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `serial_no` varchar(255) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `app_id` bigint(20) NOT NULL,
  `drcrf` varchar(255) DEFAULT NULL,
  `pay_name` varchar(255) DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `vouh_no` varchar(255) DEFAULT NULL,
  `operate_remark` text,
  `cash_flow_uid` varchar(255) DEFAULT NULL,
  `detail_data` text,
  `cash_flow_channel_type` int(11) DEFAULT '0',
  `transaction_type` int(11) DEFAULT '0',
  `partner_id` varchar(255) DEFAULT NULL,
  `issued_amount` decimal(19,2) DEFAULT NULL,
  `audit_status` int(11) DEFAULT '0',
  `source_document_uuid` varchar(255) DEFAULT NULL,
  `first_account_name` varchar(255) DEFAULT NULL,
  `first_account_code` varchar(255) DEFAULT NULL,
  `first_account_alias` varchar(255) DEFAULT NULL,
  `second_account_name` varchar(255) DEFAULT NULL,
  `second_account_code` varchar(255) DEFAULT NULL,
  `second_account_alias` varchar(255) DEFAULT NULL,
  `third_account_name` varchar(255) DEFAULT NULL,
  `third_account_code` varchar(255) DEFAULT NULL,
  `third_account_alias` varchar(255) DEFAULT NULL,
  `fourth_account_name` varchar(255) DEFAULT NULL,
  `fourth_account_code` varchar(255) DEFAULT NULL,
  `fourth_account_alias` varchar(255) DEFAULT NULL,
  `auxiliary_accounting` varchar(255) DEFAULT NULL,
  `journal` varchar(255) DEFAULT NULL,
  `journal_time` datetime DEFAULT NULL,
  `receive_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_2alj2i3o77yog125lkliovhel` (`app_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- 20160801
ALTER TABLE `journal_voucher` CHANGE `virtual_account_uuid` `batch_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '凭证批次uuid';
ALTER TABLE `journal_voucher`
ADD COLUMN `journal_voucher_type` TINYINT(1) UNSIGNED DEFAULT NULL COMMENT '执行状态',
ADD COLUMN `counter_account_type` TINYINT(1) UNSIGNED DEFAULT NULL COMMENT '来源账户类型',
ADD COLUMN `related_bill_contract_info_lv_1` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '一级单据信息',
ADD COLUMN `related_bill_contract_info_lv_2` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '二级单据信息',
ADD COLUMN `related_bill_contract_info_lv_3` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '三级单据信息',
ADD COLUMN `cash_flow_account_info` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '流水账户信息';


-- 20160810
ALTER TABLE `customer` ADD COLUMN `customer_type` TINYINT(1) UNSIGNED DEFAULT '0' COMMENT '客户类型:0.个人, 1.公司';
-- TODO add company into customer;
-- TODO fill contractUuid;
ALTER TABLE `source_document`
ADD COLUMN `first_party_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '第一交易对手id',
ADD COLUMN `second_party_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '第二交易对手id',
ADD COLUMN `virtual_account_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '虚拟账户uuid',
ADD COLUMN `first_account_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '一级科目code',
ADD COLUMN `second_account_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '二级科目code',
ADD COLUMN `third_account_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '三级科目code',
ADD COLUMN `excute_status` TINYINT(1) UNSIGNED DEFAULT NULL COMMENT '执行状态',
ADD COLUMN `excute_result` TINYINT(1) UNSIGNED DEFAULT NULL COMMENT '执行结果',
ADD COLUMN `related_contract_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '相关的合同uuid',
ADD COLUMN `financial_contract_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '相关的信托合同uuid',
ADD COLUMN `source_document_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '凭证编号',

ADD COLUMN `first_party_type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '第一交易对手类型',
ADD COLUMN `first_party_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '第一交易对手名称',
ADD COLUMN `virtual_account_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '虚拟账户编号';

DROP TABLE IF EXISTS `virtual_account`;
CREATE TABLE `virtual_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `total_balance` decimal(19,2) DEFAULT NULL COMMENT '余额',
  `virtual_account_uuid` varchar(255) DEFAULT NULL COMMENT '虚拟账户uuid',
  `parent_account_uuid` varchar(255) DEFAULT NULL COMMENT '父账户uuid',
  `virtual_account_alias` varchar(255) DEFAULT NULL COMMENT '虚拟账户别名',
  `virtual_account_no` varchar(255) DEFAULT NULL COMMENT '虚拟账户编号',
  `version` varchar(255) DEFAULT NULL COMMENT '虚拟账户版本号',
  `owner_uuid` varchar(255) DEFAULT NULL COMMENT '账户拥有者uuid',
  `owner_name` varchar(255) DEFAULT NULL COMMENT '账户拥有者姓名',
  
  `customer_type` TINYINT(1) UNSIGNED DEFAULT NULL COMMENT '客户类型',
  
  `fst_level_contract_uuid` varchar(255) DEFAULT NULL COMMENT '一级合同uuid',
  `snd_level_contract_uuid` varchar(255) DEFAULT NULL COMMENT '二级合同uuid',
  `trd_level_contract_uuid` varchar(255) DEFAULT NULL COMMENT '三级合同uuid',
  
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_time` datetime DEFAULT NULL COMMENT '余额最后更新时间',
  `last_modified_time` datetime DEFAULT NULL COMMENT '数据最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE (virtual_account_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


DROP TABLE IF EXISTS `app_account`;
CREATE TABLE `app_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uuid` varchar(255) DEFAULT NULL COMMENT '商户账户uuid',
  `bank_name` varchar(255) DEFAULT NULL COMMENT '银行名称',
  `account_name` varchar(255) DEFAULT NULL COMMENT '账户开户名',
  `account_no` varchar(255) DEFAULT NULL COMMENT '账户号',
  `app_account_active_status` TINYINT(1) UNSIGNED NOT NULL COMMENT '商户表的id',
  `app_id` bigint(20) NOT NULL COMMENT '商户表的id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

alter TABLE `company`
add column `uuid` varchar(255) DEFAULT NULL COMMENT '公司uuid';
UPDATE `company` SET `uuid` = uuid() where `uuid` is null;

ALTER TABLE `contract`
ADD column `financial_contract_uuid`  varchar(255) DEFAULT NULL COMMENT '信托合同uuid';
-- TODO set financial_contract_uuid

-- JV、ledgerBook 相关索引
ALTER TABLE `ledger_book_shelf` ADD INDEX ledger_book_no ( `ledger_book_no` ) USING BTREE;
ALTER TABLE `ledger_book_shelf` ADD INDEX first_account_uuid ( `first_account_uuid` ) USING BTREE;
ALTER TABLE `ledger_book_shelf` ADD INDEX second_account_uuid ( `second_account_uuid` ) USING BTREE;
ALTER TABLE `ledger_book_shelf` ADD INDEX third_account_uuid ( `third_account_uuid` ) USING BTREE;
ALTER TABLE `ledger_book_shelf` ADD INDEX contract_uuid ( `contract_uuid` ) USING BTREE;
ALTER TABLE `ledger_book_shelf` ADD INDEX related_lv_1_asset_uuid ( `related_lv_1_asset_uuid` ) USING BTREE;
ALTER TABLE `ledger_book_shelf` ADD INDEX related_lv_2_asset_uuid ( `related_lv_2_asset_uuid` ) USING BTREE;
ALTER TABLE `ledger_book_shelf` ADD INDEX related_lv_3_asset_uuid ( `related_lv_3_asset_uuid` ) USING BTREE;
ALTER TABLE `ledger_book_shelf` ADD INDEX first_party_id ( `first_party_id` ) USING BTREE;
ALTER TABLE `ledger_book_shelf` ADD INDEX second_party_id ( `second_party_id` ) USING BTREE;

ALTER TABLE `journal_voucher` ADD INDEX billing_plan_uuid ( `billing_plan_uuid` ) USING HASH;
ALTER TABLE `journal_voucher` ADD INDEX cash_flow_serial_no ( `cash_flow_serial_no` ) USING HASH;
ALTER TABLE `journal_voucher` ADD INDEX cash_flow_uuid ( `cash_flow_uuid` ) USING HASH;
ALTER TABLE `journal_voucher` ADD INDEX source_document_uuid ( `source_document_uuid` ) USING HASH;
ALTER TABLE `journal_voucher` ADD INDEX cash_flow_account_info ( `cash_flow_account_info` ) USING HASH;

SET FOREIGN_KEY_CHECKS=1;

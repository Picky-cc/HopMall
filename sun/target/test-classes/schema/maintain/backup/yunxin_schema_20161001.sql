SET FOREIGN_KEY_CHECKS=0;

-- 20160919 start 主动付款凭证相关
INSERT INTO `t_api_config` ( `api_url`, `fn_code`, `description`, `api_status`)
VALUES
	( '/api/command', '300004', '主动付款凭证接口', 1);

DROP TABLE IF EXISTS `t_interface_active_voucher_log`;

CREATE TABLE `t_interface_active_voucher_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `request_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '请求唯一标识',
  `transaction_type` int(11) DEFAULT NULL COMMENT '交易类型(0:提交，1:撤销)',
  `voucher_type` int(11) DEFAULT NULL COMMENT '凭证类型(5:主动付款，6:他人代偿)',
  `unique_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '贷款合同唯一外部标识',
  `contract_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '贷款合同编号',
  `repayment_plan_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '还款计划编号',
  `voucher_amount` decimal(19,2) DEFAULT NULL COMMENT '凭证金额',
  `receivable_account_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '收款账户号',
  `payment_account_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '付款账户号',
  `payment_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '付款银行帐户名称',
  `payment_bank` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '付款银行名称',
  `bank_transaction_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '银行流水号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `ip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `request_no` (`request_no`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='主动付款凭证日志表';

DROP TABLE IF EXISTS `source_document_resource`;

CREATE TABLE `source_document_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'uuid',
  `source_document_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '原始凭证uuid',
  `batch_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '批次编号',
  `path` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '资源路径',
  `status` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否可用',
  PRIMARY KEY (`id`),
  KEY `uuid` (`uuid`) USING HASH,
  KEY `source_document_uuid` (`source_document_uuid`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='原始凭证相关资源文件表';
-- 20160919 end

-- 20160923 余额支付单
ALTER TABLE `journal_voucher`
ADD COLUMN `related_bill_contract_no_lv_1` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '一级单据编号（信托合同名称）',
ADD COLUMN `related_bill_contract_no_lv_2` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '二级单据编号（贷款合同编号）',
ADD COLUMN `related_bill_contract_no_lv_3` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '三级单据编号（还款计划编号）',
ADD COLUMN `related_bill_contract_no_lv_4` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '四级单据编号（订单编号）',
ADD COLUMN `source_document_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '凭证编号';
-- 20160923 余额支付单

-- 20160929 contract_uniqeId加索引
ALTER TABLE `contract` ADD INDEX contract_unique_id ( `unique_id` ) USING HASH;
-- 20160929 contract_uniqeId加索引

DROP TABLE IF EXISTS `t_interface_data_sync_log`;

CREATE TABLE `t_interface_data_sync_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_sync_log_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'uuid',
  `product_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '产品代码',
  `contract_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '合同编号',
  `contract_end_date` datetime DEFAULT NULL COMMENT '合同截止日期',
  `contract_unique_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '贷款唯一标示',
  `repayment_plan_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '还款计划编号',
  `asset_set_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '还款计划uuid',
  `contract_flag` tinyint(1) unsigned DEFAULT NULL,
  `repay_type` int(11) unsigned DEFAULT NULL COMMENT '回款方式',
  `plan_repay_date` datetime DEFAULT NULL COMMENT '计划还款日期',
  `actual_repay_date` datetime DEFAULT NULL COMMENT '实际还款日期',
  `data_sync_big_decimal_details` text COLLATE utf8_unicode_ci COMMENT '数据同步金额明细',
  `sync_frequency` int(11) unsigned DEFAULT NULL COMMENT '同步次数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_modify_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `is_success` tinyint(1) DEFAULT NULL COMMENT '同步状态',
  `return_message` text COLLATE utf8_unicode_ci COMMENT '返回信息',
  `request_message` text COLLATE utf8_unicode_ci COMMENT '请求信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='同步数据log表';

ALTER TABLE `contract` ADD INDEX uuid ( `uuid` );
ALTER TABLE `asset_set` ADD INDEX asset_uuid ( `asset_uuid` );
ALTER TABLE `source_document` ADD INDEX source_document_uuid ( `source_document_uuid` );
ALTER TABLE `source_document` ADD INDEX outlier_document_uuid ( `outlier_document_uuid` );
ALTER TABLE `source_document` ADD INDEX first_outlier_doc_type ( `first_outlier_doc_type` );


SET FOREIGN_KEY_CHECKS=1;
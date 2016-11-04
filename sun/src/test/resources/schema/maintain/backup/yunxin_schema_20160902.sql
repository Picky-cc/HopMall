SET FOREIGN_KEY_CHECKS=0;

INSERT INTO `t_api_config` (`id`, `api_url`, `fn_code`, `description`, `api_status`)
VALUES
	(9, '/api/command', '300002', '放款接口', 0);
	
-- contract_account 更新
ALTER TABLE `contract_account` ADD COLUMN `province_code` VARCHAR(255) DEFAULT NULL  after `province`;
ALTER TABLE `contract_account` ADD COLUMN `city_code` VARCHAR(255) DEFAULT NULL after `city`;

-- 20160822 商户付款凭证相关 start
DROP TABLE IF EXISTS `t_interface_voucher_log`;
CREATE TABLE `t_interface_voucher_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `request_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '请求唯一标识',
  `transaction_type` int(11) DEFAULT NULL COMMENT '交易类型(0:提交，1:撤销)',
  `business_voucher_no` varchar(255) COLLATE utf8_unicode_ci COMMENT '凭证编号',
  `voucher_type` int(11) DEFAULT NULL COMMENT '凭证类型(0:代偿，1:担保补足，2:回购，3:差额划拨)',
  `voucher_amount` decimal(19,2) DEFAULT NULL COMMENT '凭证金额',
  `financial_contract_no` varchar(255) COLLATE utf8_unicode_ci COMMENT '信托产品代码',
  `receivable_account_no` varchar(255) COLLATE utf8_unicode_ci COMMENT '付款账户号',
  `payment_account_no` varchar(255) COLLATE utf8_unicode_ci COMMENT '付款账户号',
  `payment_name` varchar(255) COLLATE utf8_unicode_ci COMMENT '付款银行帐户名称',
  `payment_bank` varchar(255) COLLATE utf8_unicode_ci COMMENT '付款银行名称',
  `bank_transaction_no` varchar(255) COLLATE utf8_unicode_ci COMMENT '银行流水号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `ip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='商户付款凭证日志表';

INSERT INTO `t_api_config` (`id`, `api_url`, `fn_code`, `description`, `api_status`)
VALUES
	(10, '/api/command', '300003', '商户付款凭证接口', 0);

-- 20160822 商户付款凭证相关 end

-- 20160824 扣款接口相关 start

DROP TABLE IF EXISTS `t_interface_deduct_bill`;

DROP TABLE IF EXISTS `t_deduct_application`;
CREATE TABLE `t_deduct_application` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `deduct_application_uuid` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '扣款申请uuid',
  `deduct_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `request_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '扣款请求编号',
  `financial_contract_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '信托合同id',
  `financial_product_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '信托产品代码',
  `contract_unique_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '贷款合同唯一编号',
  `contract_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '贷款合同编号',
  `planned_deduct_total_amount` decimal(19,2) DEFAULT NULL COMMENT '计划扣款总金额',
  `actual_deduct_total_amount` decimal(19,2) DEFAULT NULL COMMENT '实际扣款总金额',
  `notify_url` text COLLATE utf8_unicode_ci COMMENT '回调地址',
  `transcation_type` tinyint(1) DEFAULT NULL COMMENT '接口交易类型',
  `repayment_type` tinyint(1) DEFAULT NULL COMMENT '还款类型',
  `execution_status` tinyint(1) unsigned DEFAULT NULL COMMENT '执行状态',
  `execution_remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '执行备注',
  `create_time` datetime DEFAULT NULL COMMENT '受理日期',
  `creator_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `ip` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'ip地址',
  `last_modified_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  `record_status` tinyint(1) DEFAULT NULL,
  `is_available` tinyint(1) DEFAULT NULL,
  `api_called_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='扣款申请表';

DROP TABLE IF EXISTS `t_deduct_plan`;
CREATE TABLE `t_deduct_plan` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `deduct_plan_uuid` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '扣款计划uuid',
  `deduct_application_uuid` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '扣款申请uuid',
  `deduct_application_detail_uuid` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '扣款申请明细uuid',
  `financial_contract_uuid` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '信托合同唯一编号',
  `contract_unique_id` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '合同唯一编号',
  `contract_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '合同编号',
  `payment_gateway` tinyint(1) unsigned DEFAULT NULL COMMENT '支付网关0:广州银联，1:超级网银，2:银企直连',
  `payment_channel_uuid` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '支付通道uuid',
  `pg_account` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '支付网关账户（或商户号）',
  `pg_clearing_account` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '支付网关清算帐户',
  `transaction_type` tinyint(1) unsigned DEFAULT NULL COMMENT '交易类型0:贷，1:借',
  `transaction_remark` text COLLATE utf8_unicode_ci COMMENT '交易备注',
  `cp_bank_code` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '恒生银行编码',
  `cp_bank_card_no` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易对手方银行卡号',
  `cp_bank_account_holder` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易对手方银行卡持有人',
  `cp_id_type` tinyint(1) unsigned DEFAULT NULL COMMENT '交易对手方证件类型0：身份证,1: 户口簿，2：护照,3.军官证,4.士兵证，5. 港澳居民来往内地通行证,6. 台湾同胞来往内地通行证,7. 临时身份证,8. 外国人居留证,9. 警官证, 10.其他证件',
  `cp_id_number` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易对手方证件号',
  `cp_bank_province` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易对手方开户行所在省',
  `cp_bank_city` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易对手方开户行所在市',
  `cp_bank_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易对手方开户行名称',
  `planned_payment_date` datetime DEFAULT NULL COMMENT '计划支付时间',
  `complete_payment_date` datetime DEFAULT NULL COMMENT '实际支付完成时间',
  `planned_total_amount` decimal(19,2) DEFAULT NULL COMMENT '计划交易总金额',
  `actual_total_amount` decimal(19,2) DEFAULT NULL COMMENT '实际交易总金额',
  `execution_precond` text COLLATE utf8_unicode_ci COMMENT '执行前置条件JSON',
  `execution_status` tinyint(1) unsigned DEFAULT NULL COMMENT '执行状态',
  `execution_remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '执行备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `last_modified_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='扣款计划表';

DROP TABLE IF EXISTS `t_deduct_application_detail`;
CREATE TABLE `t_deduct_application_detail` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `deduct_application_detail_uuid` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '扣款申请明细uuid',
  `deduct_application_uuid` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '扣款申请uuid',
  `financial_contract_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '信托合约id',
  `contract_unique_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '贷款合同唯一编号',
  `repayment_plan_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '还款计划编号',
  `request_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `repayment_type` tinyint(1) DEFAULT NULL COMMENT '还款类型',
  `transaction_type` tinyint(1) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `execution_remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '执行备注',
  `creator_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `last_modify_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  `first_account_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `first_account_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `second_account_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `second_account_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `third_account_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `third_account_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `account_amount` decimal(19,2) DEFAULT NULL,
  `is_total` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='扣款申请明细表';

-- 放款接口相关表

# Dump of table t_remittance_application
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_remittance_application`;

CREATE TABLE `t_remittance_application` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `remittance_application_uuid` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '放款申请uuid',
  `request_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '放款请求编号',
  `financial_contract_uuid` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '信托合同uuid',
  `financial_contract_id` bigint(20) DEFAULT NULL COMMENT '信托合同Id',
  `financial_product_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '信托产品代码',
  `contract_unique_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '贷款合同唯一编号',
  `contract_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '贷款合同编号',
  `planned_total_amount` decimal(19,2) DEFAULT NULL COMMENT '计划放款总金额',
  `actual_total_amount` decimal(19,2) DEFAULT NULL COMMENT '实际放款总金额',
  `auditor_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '审核人',
  `audit_time` datetime DEFAULT NULL COMMENT '审核日期',
  `notify_url` text COLLATE utf8_unicode_ci COMMENT '回调地址',
  `plan_notify_number` int(11) DEFAULT NULL COMMENT '计划回调次数',
  `actual_notify_number` int(11) DEFAULT NULL COMMENT '实际回调次数',
  `remittance_strategy` tinyint(1) unsigned DEFAULT NULL COMMENT '放款策略',
  `remark` text COLLATE utf8_unicode_ci COMMENT '备注',
  `transaction_recipient` tinyint(1) unsigned DEFAULT NULL COMMENT '交易接收方, 0:本端，1:对端',
  `execution_status` tinyint(1) unsigned DEFAULT NULL COMMENT '执行状态, 0:已创建、1:处理中、2:成功、3:失败、4:异常、5:撤销',
  `execution_remark` text COLLATE utf8_unicode_ci COMMENT '执行备注',
  `create_time` datetime DEFAULT NULL COMMENT '受理日期',
  `creator_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `ip` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'ip地址',
  `last_modified_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_key_request_no` (`request_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='放款申请表';



# Dump of table t_remittance_application_detail
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_remittance_application_detail`;

CREATE TABLE `t_remittance_application_detail` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `remittance_application_detail_uuid` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '放款申请明细uuid',
  `remittance_application_uuid` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '放款申请uuid',
  `financial_contract_uuid` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '信托合约uuid',
  `financial_contract_id` bigint(20) DEFAULT NULL COMMENT '信托合同Id',
  `business_record_no` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '业务记录号',
  `priority_level` int(11) DEFAULT NULL COMMENT '优先级',
  `cp_bank_code` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '恒生银行编码',
  `cp_bank_card_no` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易对手方银行卡号',
  `cp_bank_account_holder` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易对手方银行卡持有人',
  `cp_id_type` tinyint(1) unsigned DEFAULT NULL COMMENT '交易对手方证件类型0：身份证,1: 户口簿，2：护照,3.军官证,4.士兵证，\n  5. 港澳居民来往内地通行证,6. 台湾同胞来往内地通行证,7. 临时身份证,8. 外国人居留证,9. 警官证, 10.其他证件',
  `cp_id_number` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易对手方证件号',
  `cp_bank_province` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易对手方开户行所在省',
  `cp_bank_city` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易对手方开户行所在市',
  `cp_bank_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易对手方开户行名称',
  `planned_payment_date` datetime DEFAULT NULL COMMENT '计划支付时间',
  `complete_payment_date` datetime DEFAULT NULL COMMENT '实际支付完成时间',
  `planned_total_amount` decimal(19,2) DEFAULT NULL COMMENT '计划交易总金额',
  `actual_total_amount` decimal(19,2) DEFAULT NULL COMMENT '实际交易总金额',
  `execution_status` tinyint(1) unsigned DEFAULT NULL COMMENT '执行状态, 0:已创建、1:处理中、2:成功、3:失败、4:异常、5:撤销',
  `execution_remark` text COLLATE utf8_unicode_ci COMMENT '执行备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `last_modified_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='放款申请明细表';



# Dump of table t_remittance_plan
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_remittance_plan`;

CREATE TABLE `t_remittance_plan` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `remittance_plan_uuid` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '放款计划uuid',
  `remittance_application_uuid` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '放款申请uuid',
  `remittance_application_detail_uuid` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '放款申请明细uuid',
  `business_record_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '业务记录号',
  `financial_contract_uuid` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '信托合约uuid',
  `financial_contract_id` bigint(20) DEFAULT NULL COMMENT '信托合同Id',
  `contract_unique_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '贷款合同唯一编号',
  `contract_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '贷款合同编号',
  `payment_gateway` tinyint(1) unsigned DEFAULT NULL COMMENT '支付网关0:广州银联，1:超级网银，2:银企直连',
  `payment_channel_uuid` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '支付通道uuid',
  `pg_account_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '支付网关账户名',
  `pg_account_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '支付网关账户号',
  `pg_clearing_account` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '支付网关清算帐户',
  `transaction_type` tinyint(1) unsigned DEFAULT NULL COMMENT '交易类型0:贷，1:借',
  `transaction_remark` text COLLATE utf8_unicode_ci COMMENT '交易备注',
  `priority_level` int(11) DEFAULT NULL COMMENT '优先级',
  `cp_bank_code` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '恒生银行编码',
  `cp_bank_card_no` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易对手方银行卡号',
  `cp_bank_account_holder` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易对手方银行卡持有人',
  `cp_id_type` tinyint(1) unsigned DEFAULT NULL COMMENT '交易对手方证件类型0：身份证,1: 户口簿，2：护照,3.军官证,4.士兵证，5. 港澳居民来往内地通行证,6. 台湾同胞来往内地通行证,7. 临时身份证,8. 外国人居留证,9. 警官证, 10.其他证件',
  `cp_id_number` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易对手方证件号',
  `cp_bank_province` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易对手方开户行所在省',
  `cp_bank_city` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易对手方开户行所在市',
  `cp_bank_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易对手方开户行名称',
  `planned_payment_date` datetime DEFAULT NULL COMMENT '计划支付时间',
  `complete_payment_date` datetime DEFAULT NULL COMMENT '实际支付完成时间',
  `planned_total_amount` decimal(19,2) DEFAULT NULL COMMENT '计划交易总金额',
  `actual_total_amount` decimal(19,2) DEFAULT NULL COMMENT '实际交易总金额',
  `execution_precond` text COLLATE utf8_unicode_ci COMMENT '执行前置条件JSON',
  `execution_status` tinyint(1) unsigned DEFAULT NULL COMMENT '执行状态',
  `execution_remark` text COLLATE utf8_unicode_ci COMMENT '执行备注',
  `transaction_serial_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易流水号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `last_modified_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='放款计划表';



# Dump of table t_remittance_plan_exec_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_remittance_plan_exec_log`;

CREATE TABLE `t_remittance_plan_exec_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `remittance_application_uuid` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '放款申请uuid',
  `remittance_plan_uuid` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '放款计划uuid',
  `financial_contract_uuid` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '信托合约uuid',
  `financial_contract_id` bigint(20) DEFAULT NULL COMMENT '信托合同Id',
  `payment_channel_uuid` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '支付通道uuid',
  `payment_channel_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '支付通道名称',
  `pg_account_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '支付网关账户名',
  `pg_account_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '支付网关账户号',
  `pg_clearing_account` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '支付网关清算账号',
  `planned_amount` decimal(19,2) DEFAULT NULL COMMENT '计划支付金额',
  `actual_total_amount` decimal(19,2) DEFAULT NULL COMMENT '实际交易总金额',
  `cp_bank_code` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '恒生银行编码',
  `cp_bank_card_no` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易对手方银行卡号',
  `cp_bank_account_holder` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易对手方银行卡持有人',
  `cp_id_type` tinyint(1) unsigned DEFAULT NULL COMMENT '交易对手方证件类型0：身份证,1: 户口簿，2：护照,3.军官证,4.士兵证，5. 港澳居民来往内地通行证,6. 台湾同胞来往内地通行证,7. 临时身份证,8. 外国人居留证,9. 警官证, 10.其他证件',
  `cp_id_number` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易对手方证件号',
  `cp_bank_province` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易对手方开户行所在省',
  `cp_bank_city` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易对手方开户行所在市',
  `cp_bank_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易对手方开户行名称',
  `transaction_type` tinyint(1) unsigned DEFAULT NULL COMMENT '交易类型0:贷，1:借',
  `transaction_remark` text COLLATE utf8_unicode_ci COMMENT '交易备注',
  `exec_req_no` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易上送请求号',
  `exec_rsp_no` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易下达响应号',
  `execution_status` tinyint(1) DEFAULT NULL COMMENT '执行状态',
  `execution_remark` text COLLATE utf8_unicode_ci COMMENT '执行备注',
  `transaction_serial_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易流水号',
  `complete_payment_date` datetime DEFAULT NULL COMMENT '交易完成时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_modified_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='放款计划执行日志';

ALTER TABLE `t_deduct_application` add COLUMN `transaction_recipient` tinyint(1) UNSIGNED DEFAULT NULL ;
ALTER TABLE `t_deduct_plan`  add COLUMN  `transaction_serial_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL;


DROP TABLE IF EXISTS `t_interface_deduct_application_log`;
CREATE TABLE `t_interface_deduct_application_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `deduct_id` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '扣款唯一编号',
  `request_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `contract_unique_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '贷款合同唯一ID',
  `contract_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `ip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 放款相关表 追加索引 2016-08-27
ALTER TABLE `t_remittance_application` ADD INDEX remittance_application_uuid ( `remittance_application_uuid` ) USING HASH;

ALTER TABLE `t_remittance_application_detail` ADD INDEX remittance_application_uuid ( `remittance_application_uuid` ) USING HASH;
ALTER TABLE `t_remittance_application_detail` ADD INDEX remittance_application_detail_uuid ( `remittance_application_detail_uuid` ) USING HASH;

ALTER TABLE `t_remittance_plan` ADD INDEX remittance_application_uuid ( `remittance_application_uuid` ) USING HASH;
ALTER TABLE `t_remittance_plan` ADD INDEX remittance_plan_uuid ( `remittance_plan_uuid` ) USING HASH;

ALTER TABLE `t_remittance_plan_exec_log` ADD INDEX remittance_application_uuid ( `remittance_application_uuid` ) USING HASH;

-- 20160829 start
ALTER TABLE `t_deduct_application`  add COLUMN `repayment_plan_code_list` text  COLLATE utf8_unicode_ci   DEFAULT NULL  after `contract_unique_id`;

ALTER TABLE `update_asset_log` modify COLUMN `request_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '请求编号';
ALTER TABLE `update_asset_log` ADD UNIQUE request_no (`request_no`);
ALTER TABLE `t_interface_voucher_log` ADD UNIQUE request_no (`request_no`);
ALTER TABLE `t_prepayment_application` ADD UNIQUE request_no (`request_no`);

-- 20160829 end
ALTER TABLE `t_interface_deduct_application_log` MODIFY `deduct_id` VARCHAR(255) DEFAULT  NULL;

ALTER TABLE `t_interface_deduct_application_log` ADD UNIQUE request_no (`request_no`);

ALTER TABLE `t_interface_import_asset_package`ADD UNIQUE request_no (`request_no`);

ALTER TABLE `t_interface_repayment_information_log` ADD UNIQUE request_no (`request_no`);

ALTER TABLE `t_deduct_application` ADD UNIQUE request_no (`request_no`);
-- 20160829 start

-- 20160830 zjm
DROP TABLE IF EXISTS `cash_flow`;
CREATE TABLE `cash_flow` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cash_flow_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cash_flow_channel_type` tinyint(1) DEFAULT NULL,
  `company_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `host_account_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `host_account_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `host_account_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `counter_account_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `counter_account_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `counter_account_appendix` text COLLATE utf8_unicode_ci,
  `counter_bank_info` text COLLATE utf8_unicode_ci,
  `account_side` tinyint(1) DEFAULT NULL,
  `transaction_time` datetime DEFAULT NULL,
  `transaction_amount` decimal(19,2) DEFAULT NULL,
  `balance` decimal(19,2) DEFAULT NULL,
  `transaction_voucher_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bank_sequence_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `other_remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `strike_balance_status` tinyint(1) DEFAULT NULL,
  `issued_amount` decimal(19,2) DEFAULT NULL,
  `audit_status` tinyint(1) DEFAULT NULL,
  `date_field_one` datetime DEFAULT NULL,
  `date_field_two` datetime DEFAULT NULL,
  `date_field_three` datetime DEFAULT NULL,
  `long_field_one` bigint(20) DEFAULT NULL,
  `long_field_two` bigint(20) DEFAULT NULL,
  `long_field_three` bigint(20) DEFAULT NULL,
  `string_field_one` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `string_field_two` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `string_field_three` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `decimal_field_one` decimal(19,2) DEFAULT NULL,
  `decimal_field_two` decimal(19,2) DEFAULT NULL,
  `decimal_field_three` decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `cash_flow_uuid` (`cash_flow_uuid`) USING HASH,
  KEY `bank_sequence_no` (`bank_sequence_no`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

alter table `account` add column `bank_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL;

-- 20160830 start louguanyang
DROP TABLE IF EXISTS `source_document_detail`;
CREATE TABLE `source_document_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'uuid',
  `source_document_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '原始凭证uuid',
  `contract_unique_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '贷款合同contractUniqueId',
  `repayment_plan_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '还款计划编号',
  `amount` decimal(19,2) DEFAULT NULL COMMENT '金额',
  `status` int(11) DEFAULT NULL COMMENT '明细核销状态(0:未核销，1:已核销)',
  `first_type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '一级明细类型',
  `first_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '一级明细类型编号',
  `second_type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '二级明细类型',
  `second_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '二级明细类型编号',
  `payer` tinyint(1) DEFAULT NULL COMMENT '付款人(0:贷款人，1:商户垫付)',
  `receivable_account_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '付款账户号',
  `payment_account_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '付款账户号',
  `payment_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '付款银行帐户名称',
  `payment_bank` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '付款银行名称',
  PRIMARY KEY (`id`),
  KEY `source_document_uuid` (`source_document_uuid`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='原始凭证明细表'; 
-- 20160830 end

ALTER TABLE `source_document_detail` ADD COLUMN `check_state` int(11) DEFAULT '0' COMMENT '明细校验状态(0:未校验,1:校验失败,2:校验成功)';
ALTER TABLE `source_document_detail` ADD COLUMN `comment` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '记录内容';

-- 20160831 信托约束字段

ALTER TABLE financial_contract ADD COLUMN `sys_normal_deduct_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '系统正常扣款标示，0:关闭，1:开启';
ALTER TABLE financial_contract ADD COLUMN `sys_overdue_deduct_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '系统逾期扣款标示，0:关闭，1:开启';
ALTER TABLE financial_contract ADD COLUMN `sys_create_penalty_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '是否由系统产生罚息，0:关闭，1:开启';
ALTER TABLE financial_contract ADD COLUMN `sys_create_guarantee_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '是否系统产生但保单，0:关闭，1:开启';

-- zjm 0831
alter table `cash_flow` add column `trade_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL after `strike_balance_status`;

alter table `t_remittance_plan_exec_log` add column `payment_gateway` tinyint(1) unsigned DEFAULT NULL COMMENT '支付网关0:广州银联，1:超级网银，2:银企直连' after `financial_contract_id`;
alter table `t_remittance_plan` add column `payment_channel_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '支付通道名称' after `payment_channel_uuid`;

SET FOREIGN_KEY_CHECKS=1;
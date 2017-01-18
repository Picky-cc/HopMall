SET FOREIGN_KEY_CHECKS=0;

-- 20160706 start
INSERT INTO `t_api_config` (`id`, `api_url`, `fn_code`, `description`, `api_status`) 
VALUES 
	(5, '/api/modify', '200002', '提前还款接口', 0),
	(6, '/api/query', '100003', '查询还款清单接口', 0),
	(7, '/api/modify', '200003', '变更还款信息', 0);
-- 20160706 end

-- 20160707 start
DROP TABLE IF EXISTS `t_prepayment_log`;
DROP TABLE IF EXISTS `t_interface_prepayment_log`;
CREATE TABLE `t_interface_prepayment_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `contract_id` bigint(20) DEFAULT NULL COMMENT '贷款合同id',
  `asset_set_id` bigint(20) DEFAULT NULL COMMENT '还款计划id',
  `unique_id` text DEFAULT NULL COLLATE utf8_unicode_ci COMMENT '贷款合同唯一标识',
  `contract_no` text DEFAULT NULL COLLATE utf8_unicode_ci COMMENT '贷款合同编号',
  `request_no` text DEFAULT NULL COLLATE utf8_unicode_ci COMMENT '请求编号',
  `asset_recycle_date` date DEFAULT NULL COMMENT '计划提前还款时间',
  `asset_initial_value` decimal(19,2) DEFAULT NULL COMMENT '计划提前还款金额',
  `type` int(11) DEFAULT NULL COMMENT '提前还款类型 0:全部提前还款',
  `origin_data` text DEFAULT NULL COLLATE utf8_unicode_ci COMMENT '变更数据备份',
  `status` bit(1) DEFAULT NULL COMMENT '处理状态',
  `result_msg` text DEFAULT NULL COLLATE utf8_unicode_ci COMMENT '返回信息',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `ip` text NOT NULL COLLATE utf8_unicode_ci COMMENT '请求ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
-- 20160707 end	
-- 20160711 start

ALTER TABLE `t_interface_deduct_bill` CHANGE `request_id` `request_no` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '';

ALTER TABLE `t_interface_deduct_bill` ADD COLUMN `deduct_id` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '商户提交的扣款id' AFTER `request_no`;
ALTER TABLE `t_interface_deduct_bill` ADD UNIQUE KEY `unique_deduct_id` (`deduct_id`);

-- 20160711 end

-- 2016.7.12
ALTER TABLE `contract_account`
ADD COLUMN  `from_date` datetime COLLATE utf8_unicode_ci default  NULL COMMENT '有效时间开始日',
ADD COLUMN  `thru_date` datetime COLLATE utf8_unicode_ci default  NULL COMMENT  '有效时间到期日';

DROP TABLE IF EXISTS `t_interface_repayment_information_log`;
CREATE TABLE `t_interface_repayment_information_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `contract_id` bigint(20) NOT NULL COMMENT '贷款合同id',
  `request_no` text COLLATE utf8_unicode_ci COMMENT '请求编号',
  `request_data` text COLLATE utf8_unicode_ci COMMENT '请求变更数据',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `ip` text COLLATE utf8_unicode_ci COMMENT '请求ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

update contract_account  ca , contract  c  set ca.from_date = c.begin_date  where ca.contract_id = c.id;
update contract_account  ca , contract  c  set ca.thru_date = '2900-01-01 00:00:00' where ca.contract_id = c.id;


DROP TABLE IF EXISTS `request_record`;

CREATE TABLE `request_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gateway` int(11) DEFAULT '0',
  `function_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `req_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `transaction_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `original_req_package` text COLLATE utf8_unicode_ci,
  `merchant_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `req_date` datetime DEFAULT NULL,
  `long_field_one` bigint(20) NOT NULL DEFAULT '0',
  `long_field_three` bigint(20) NOT NULL DEFAULT '0',
  `long_field_two` bigint(20) NOT NULL DEFAULT '0',
  `decimal_field_one` decimal(19,2) DEFAULT NULL,
  `decimal_field_three` decimal(19,2) DEFAULT NULL,
  `decimal_field_two` decimal(19,2) DEFAULT NULL,
  `string_field_one` varchar(255) DEFAULT NULL,
  `string_field_three` varchar(255) DEFAULT NULL,
  `string_field_two` varchar(255) DEFAULT NULL, 
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

SET FOREIGN_KEY_CHECKS=1;

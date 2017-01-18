# ************************************************************
# Sequel Pro SQL dump
# Version 4499
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 121.40.230.133 (MySQL 5.5.16)
# Database: yunxin
# Generation Time: 2016-09-02 14:40:51 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table account
# ------------------------------------------------------------

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '回款账户名称',
  `account_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '回款账户卡号',
  `bank_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '回款账户开户行名',
  `alias` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attr` text COLLATE utf8_unicode_ci,
  `scan_cash_flow_switch` bit(1) DEFAULT b'0' COMMENT '扫描流水开关，默认0关闭',
  `usb_key_configured` bit(1) DEFAULT b'0' COMMENT '是否配置usbKey，默认0未配置',
  `uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bank_code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='信托账户表';



# Dump of table app
# ------------------------------------------------------------

DROP TABLE IF EXISTS `app`;

CREATE TABLE `app` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '合作商户编号',
  `app_secret` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '合作商户唯一标识',
  `is_disabled` bit(1) DEFAULT NULL,
  `host` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '合作商户官网',
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '合作商户名称',
  `company_id` bigint(20) NOT NULL COMMENT '合作商户公司编号(company表id)',
  `addressee` varchar(2048) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_fo5tnbjkos6udvf57gdtr6ro2` (`app_id`),
  KEY `FK_sw03avhjpih3a9mre2dqtmljs` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='商户表';



# Dump of table app_account
# ------------------------------------------------------------

DROP TABLE IF EXISTS `app_account`;

CREATE TABLE `app_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商户账户uuid',
  `bank_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '银行名称',
  `account_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '账户开户名',
  `account_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '账户号',
  `app_account_active_status` tinyint(1) unsigned NOT NULL COMMENT '商户表的id',
  `app_id` bigint(20) NOT NULL COMMENT '商户表的id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



# Dump of table app_arrive_record
# ------------------------------------------------------------

DROP TABLE IF EXISTS `app_arrive_record`;

CREATE TABLE `app_arrive_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` decimal(19,2) DEFAULT NULL,
  `arrive_record_status` int(11) DEFAULT NULL,
  `pay_ac_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `receive_ac_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `serial_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `app_id` bigint(20) NOT NULL,
  `drcrf` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pay_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `summary` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vouh_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `operate_remark` text COLLATE utf8_unicode_ci,
  `cash_flow_uid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `detail_data` text COLLATE utf8_unicode_ci,
  `cash_flow_channel_type` int(11) DEFAULT '0',
  `transaction_type` int(11) DEFAULT '0',
  `partner_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `issued_amount` decimal(19,2) DEFAULT NULL,
  `audit_status` int(11) DEFAULT '0',
  `source_document_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `first_account_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `first_account_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `first_account_alias` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `second_account_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `second_account_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `second_account_alias` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `third_account_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `third_account_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `third_account_alias` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fourth_account_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fourth_account_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fourth_account_alias` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `auxiliary_accounting` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `journal` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `journal_time` datetime DEFAULT NULL,
  `receive_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_2alj2i3o77yog125lkliovhel` (`app_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



# Dump of table asset_package
# ------------------------------------------------------------

DROP TABLE IF EXISTS `asset_package`;

CREATE TABLE `asset_package` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `is_available` bit(1) DEFAULT NULL COMMENT '是否有效',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `contract_id` bigint(20) NOT NULL COMMENT '贷款合同id',
  `asset_package_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '资产包编号',
  `financial_contract_id` bigint(20) NOT NULL COMMENT '信托合同id',
  `loan_batch_id` bigint(20) DEFAULT NULL COMMENT '资产包导入批次编号',
  PRIMARY KEY (`id`),
  KEY `FK_phupyma05nkp3f3730j8akbxy` (`contract_id`),
  KEY `FK_s92pu5r6h6v25vqdxfyd8xnhp` (`financial_contract_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='资产包表';



# Dump of table asset_set
# ------------------------------------------------------------

DROP TABLE IF EXISTS `asset_set`;

CREATE TABLE `asset_set` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `guarantee_status` int(11) DEFAULT '0' COMMENT '担保补足状态: 0:未发生,1:待补足,2:已补足,3:担保作废',
  `settlement_status` int(11) DEFAULT '0' COMMENT '担保结清状态: 0:未发生,1:申请清算,2:清算处理中,3:已清算',
  `asset_fair_value` decimal(19,2) DEFAULT NULL COMMENT '资产公允值',
  `asset_principal_value` decimal(19,2) DEFAULT NULL COMMENT '本期资产本金',
  `asset_interest_value` decimal(19,2) DEFAULT NULL COMMENT '本期资产利息',
  `asset_initial_value` decimal(19,2) DEFAULT NULL COMMENT '资产初始价值',
  `asset_recycle_date` date DEFAULT NULL COMMENT '计划资产回收日期',
  `confirm_recycle_date` date DEFAULT NULL COMMENT '人工确认到账日期',
  `refund_amount` decimal(19,2) DEFAULT NULL COMMENT '退款金额',
  `asset_status` int(11) DEFAULT NULL COMMENT '资金状态: 0:未转结,1:已转结',
  `on_account_status` int(11) DEFAULT NULL COMMENT '挂账状态: 0:未挂账,1:已挂账,2:已核销',
  `repayment_plan_type` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '还款计划类型 (0:正常，1:提前还款)',
  `last_valuation_time` datetime DEFAULT NULL COMMENT '最后评估时间',
  `asset_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_modified_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `comment` text COLLATE utf8_unicode_ci COMMENT '备注',
  `single_loan_contract_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '还款编号',
  `contract_id` bigint(20) NOT NULL COMMENT '贷款合同编号',
  `actual_recycle_date` datetime DEFAULT NULL COMMENT '资产实际回收时间',
  `current_period` int(11) NOT NULL DEFAULT '0' COMMENT '当前期数',
  `overdue_status` int(11) NOT NULL DEFAULT '0' COMMENT '逾期状态（0:正常，1:待确认，2:已逾期）',
  `overdue_date` date DEFAULT NULL COMMENT '逾期日期',
  `version_no` int(11) DEFAULT NULL COMMENT '还款计划版本号',
  `active_status` int(11) NOT NULL DEFAULT '0' COMMENT '有效状态 0:开启,1:作废',
  `extra_charge` text COLLATE utf8_unicode_ci COMMENT '额外费用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='还款计划表';



# Dump of table asset_valuation_detail
# ------------------------------------------------------------

DROP TABLE IF EXISTS `asset_valuation_detail`;

CREATE TABLE `asset_valuation_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` decimal(19,8) DEFAULT NULL COMMENT '金额',
  `asset_value_date` date DEFAULT NULL COMMENT '资产价值日',
  `created_date` date DEFAULT NULL COMMENT '创建时间',
  `subject` int(11) DEFAULT NULL COMMENT '明细科目: 0:还款额,1:罚息,2:金额调整',
  `asset_set_id` bigint(20) NOT NULL COMMENT '还款计划id',
  `asset_valuation_detail_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_7llykse8t8wvdaehyoeit3yr1` (`asset_set_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='资产评估明细表';



# Dump of table bank
# ------------------------------------------------------------

DROP TABLE IF EXISTS `bank`;

CREATE TABLE `bank` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `bank_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bank_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



# Dump of table batch_pay_record
# ------------------------------------------------------------

DROP TABLE IF EXISTS `batch_pay_record`;

CREATE TABLE `batch_pay_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `batch_pay_record_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `amount` decimal(19,2) DEFAULT NULL COMMENT '批量总金额',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `request_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '上传第三方的请求编号',
  `serial_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易序列号',
  `request_data` text COLLATE utf8_unicode_ci COMMENT '请求报文',
  `response_data` text COLLATE utf8_unicode_ci COMMENT '响应报文',
  `query_response_data` text COLLATE utf8_unicode_ci COMMENT '查询响应报文',
  `trans_date_time` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易发起时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='批量交易记录表';



# Dump of table business_voucher
# ------------------------------------------------------------

DROP TABLE IF EXISTS `business_voucher`;

CREATE TABLE `business_voucher` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_id` bigint(20) DEFAULT NULL,
  `account_side` int(11) DEFAULT NULL COMMENT '0:贷方1:借方',
  `billing_plan_breif` text COLLATE utf8_unicode_ci,
  `billing_plan_type_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '代收单类型',
  `billing_plan_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '代收单uuid',
  `business_voucher_status` int(11) DEFAULT NULL COMMENT '制证状态: 0:待制证,1:制证中,2:已制证,3:已作废',
  `business_voucher_type_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '业务凭证类型uuid',
  `business_voucher_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL COMMENT '公司id',
  `receivable_amount` decimal(19,2) DEFAULT NULL COMMENT '应收金额',
  `settlement_amount` decimal(19,2) DEFAULT NULL COMMENT '结算金额',
  `tax_invoice_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '发票',
  PRIMARY KEY (`id`),
  UNIQUE KEY `business_voucher_uuid` (`business_voucher_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='业务凭证表';



# Dump of table cash_flow
# ------------------------------------------------------------

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
  `trade_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
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



# Dump of table city
# ------------------------------------------------------------

DROP TABLE IF EXISTS `city`;

CREATE TABLE `city` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `province_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table company
# ------------------------------------------------------------

DROP TABLE IF EXISTS `company`;

CREATE TABLE `company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '公司地址',
  `full_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '公司全名',
  `short_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '公司简称',
  `uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '公司uuid',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='公司表';



# Dump of table contract
# ------------------------------------------------------------

DROP TABLE IF EXISTS `contract`;

CREATE TABLE `contract` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'uuid',
  `unique_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'uuid',
  `begin_date` date DEFAULT NULL COMMENT '贷款开始日',
  `contract_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '贷款合同编号',
  `end_date` date DEFAULT NULL COMMENT '贷款结束日',
  `asset_type` int(11) DEFAULT NULL COMMENT '资产类型 0:二手车,1:种子贷',
  `month_fee` decimal(19,2) DEFAULT NULL COMMENT '月还款额(等额本息)',
  `app_id` bigint(20) NOT NULL COMMENT '商户id',
  `customer_id` bigint(20) NOT NULL COMMENT '客户id',
  `house_id` bigint(20) NOT NULL COMMENT '抵押物id',
  `actual_end_date` datetime DEFAULT NULL COMMENT '实际结束日',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `interest_rate` decimal(19,10) DEFAULT NULL COMMENT '贷款利率',
  `payment_day_in_month` int(11) NOT NULL COMMENT '还款日',
  `payment_frequency` int(11) NOT NULL COMMENT '还款频率(月)',
  `periods` int(11) NOT NULL COMMENT '贷款期数',
  `repayment_way` int(11) DEFAULT NULL COMMENT '回款方式: 0:等额本息,1:等额本金,2,锯齿型',
  `total_amount` decimal(19,2) DEFAULT NULL COMMENT '贷款总额',
  `penalty_interest` decimal(19,10) DEFAULT NULL COMMENT '罚息利率',
  `active_version_no` int(11) DEFAULT NULL COMMENT '有效的还款计划版本号',
  `repayment_plan_operate_logs` text COLLATE utf8_unicode_ci COMMENT '还款计划操作日志JsonArray',
  `state` int(11) DEFAULT '2' COMMENT '合同状态: 0:放款中,1:未生效,2:已生效，3:异常中止',
  `financial_contract_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '信托合同uuid',
  PRIMARY KEY (`id`),
  KEY `FK_bq5tla4nfg2ufwy9b8qhiqnra` (`app_id`),
  KEY `FK_bguj1cuekmap4ctbc2xkg705u` (`customer_id`),
  KEY `FK_j1e038cafnvj9yw9ub3i693e8` (`house_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='贷款合同表';



# Dump of table contract_account
# ------------------------------------------------------------

DROP TABLE IF EXISTS `contract_account`;

CREATE TABLE `contract_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pay_ac_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '还款账户卡号',
  `bankcard_type` int(11) DEFAULT NULL COMMENT '还款账户类型 0：借记卡 , 1：信用卡',
  `contract_id` bigint(20) NOT NULL COMMENT '贷款合同id',
  `payer_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '还款人姓名',
  `bank` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '开户行名称',
  `bind_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '绑定Id',
  `id_card_num` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '还款人身份证号',
  `bank_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '还款账户银行代码',
  `province` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '还款账户开户行所在省',
  `province_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `city` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '还款账户开户行所在市',
  `city_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `standard_bank_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '标准银行代码',
  `from_date` datetime DEFAULT NULL COMMENT '有效时间开始日',
  `thru_date` datetime DEFAULT NULL COMMENT '有效时间到期日',
  PRIMARY KEY (`id`),
  KEY `FK_la0nxf0f3965rlq6wua7hd29e` (`contract_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='贷款合同账户表';



# Dump of table customer
# ------------------------------------------------------------

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '客户身份证号',
  `mobile` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '客户手机号',
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '客户姓名',
  `source` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '客户编号',
  `app_id` bigint(20) NOT NULL COMMENT '商户id',
  `customer_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `customer_type` tinyint(1) unsigned DEFAULT '0' COMMENT '客户类型:0.个人, 1.公司',
  PRIMARY KEY (`id`),
  KEY `FK_j97j99u191mju0ts0j91m13ge` (`app_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='客户信息表';



# Dump of table dictionary
# ------------------------------------------------------------

DROP TABLE IF EXISTS `dictionary`;

CREATE TABLE `dictionary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '字典名称',
  `content` text COLLATE utf8_unicode_ci COMMENT '字典内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='字典表';



# Dump of table district
# ------------------------------------------------------------

DROP TABLE IF EXISTS `district`;

CREATE TABLE `district` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city_code` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table finance_company
# ------------------------------------------------------------

DROP TABLE IF EXISTS `finance_company`;

CREATE TABLE `finance_company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) NOT NULL COMMENT '公司id',
  PRIMARY KEY (`id`),
  KEY `FK_5jte56orrjtn6rw9aktjb82wo` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='金融公司表';



# Dump of table financial_contract
# ------------------------------------------------------------

DROP TABLE IF EXISTS `financial_contract`;

CREATE TABLE `financial_contract` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `asset_package_format` int(10) DEFAULT NULL COMMENT '资产包格式 0:等额本息，1:锯齿型',
  `adva_matuterm` int(11) NOT NULL COMMENT '商户打款宽限期（日）',
  `adva_start_date` datetime DEFAULT NULL COMMENT '信托合同起始日期',
  `contract_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '信托合同编号',
  `contract_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '信托合同名称',
  `app_id` bigint(20) NOT NULL COMMENT '商户id',
  `company_id` bigint(20) NOT NULL COMMENT '公司id',
  `adva_repo_term` int(11) NOT NULL COMMENT '坏账',
  `thru_date` datetime DEFAULT NULL COMMENT '信托合同截止日期',
  `capital_account_id` bigint(20) DEFAULT NULL COMMENT '资金账户',
  `financial_contract_type` int(10) DEFAULT '0' COMMENT '信托合约类型 0:消费贷,1:小微企业贷款',
  `loan_overdue_start_day` bigint(20) DEFAULT '0' COMMENT '贷款逾期开始日',
  `loan_overdue_end_day` bigint(20) DEFAULT '0' COMMENT '贷款逾期结束日',
  `payment_channel_id` bigint(10) DEFAULT '0' COMMENT '支付通道',
  `ledger_book_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '账本编号',
  `financial_contract_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sys_normal_deduct_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '系统正常扣款标示，0:关闭，1:开启',
  `sys_overdue_deduct_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '系统逾期扣款标示，0:关闭，1:开启',
  `sys_create_penalty_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '是否由系统产生罚息，0:关闭，1:开启',
  `sys_create_guarantee_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '是否系统产生但保单，0:关闭，1:开启',
  PRIMARY KEY (`id`),
  KEY `FK_3ekc0b1qljtw8dwqfeue4pim2` (`app_id`),
  KEY `FK_hqc1gtp50u281v286ityq8vn6` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='信托合同表';



# Dump of table financial_contract_config
# ------------------------------------------------------------

DROP TABLE IF EXISTS `financial_contract_config`;

CREATE TABLE `financial_contract_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `financial_contract_uuid` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '信托合同Uuid',
  `business_type` tinyint(1) DEFAULT NULL COMMENT '业务类型 0 自有 1 委托',
  `payment_channel_uuids_for_credit` varchar(520) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '付款通道列表',
  `payment_channel_uuids_for_debit` varchar(520) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '收款通道列表',
  `credit_payment_channel_mode` tinyint(1) DEFAULT NULL COMMENT '付款模式 0 单一通道模式',
  `debit_payment_channel_mode` tinyint(1) DEFAULT NULL COMMENT '收款模式 0 单一通道模式',
  `payment_channel_router_for_credit` varchar(520) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '付款路由信息',
  `payment_channel_router_for_debit` varchar(520) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '收款路由信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



# Dump of table house
# ------------------------------------------------------------

DROP TABLE IF EXISTS `house`;

CREATE TABLE `house` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `app_id` bigint(20) NOT NULL COMMENT '商户id',
  PRIMARY KEY (`id`),
  KEY `FK_c77xof7x7l7q26wxuw8y62o31` (`app_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='抵押物表';



# Dump of table journal_voucher
# ------------------------------------------------------------

DROP TABLE IF EXISTS `journal_voucher`;

CREATE TABLE `journal_voucher` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_side` int(11) DEFAULT NULL,
  `bank_identity` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `billing_plan_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `booking_amount` decimal(19,2) DEFAULT NULL,
  `business_voucher_type_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `business_voucher_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `cash_flow_amount` decimal(19,2) DEFAULT NULL,
  `cash_flow_breif` text COLLATE utf8_unicode_ci,
  `cash_flow_channel_type` int(11) DEFAULT NULL,
  `cash_flow_serial_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '支付平台流水号',
  `cash_flow_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `checking_level` int(11) DEFAULT NULL COMMENT '日记账凭证检查等级 0:自动制证,1:人工二次确认',
  `company_id` bigint(20) DEFAULT NULL COMMENT '公司id',
  `completeness` int(11) DEFAULT NULL COMMENT '日记帐凭证完整性 0:现金流条目流缺失,1:交易通知条目缺失,2:交易通知歧义,3:条目完整',
  `counter_party_account` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `counter_party_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `journal_voucher_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `notification_identity` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '上传给支付平台的通知标识',
  `notification_memo` text COLLATE utf8_unicode_ci COMMENT '上传给支付平台的自定义信息',
  `notification_record_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '通知变更时间',
  `notified_date` datetime DEFAULT NULL,
  `settlement_modes` int(11) DEFAULT NULL,
  `source_document_amount` decimal(19,2) DEFAULT NULL,
  `source_document_breif` text COLLATE utf8_unicode_ci,
  `source_document_cash_flow_serial_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '支付平台流水号',
  `source_document_counter_party_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `source_document_identity` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '支付平台的通知标识',
  `source_document_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '原始凭证uuid',
  `status` int(11) DEFAULT NULL COMMENT '日记账凭证状态 0:已建,1:已制证,2:凭证作废',
  `trade_time` datetime DEFAULT NULL COMMENT '交易时间',
  `batch_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '凭证批次uuid',
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `source_document_counter_party_account` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `source_document_counter_party_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `issued_time` datetime DEFAULT NULL,
  `journal_voucher_type` tinyint(1) unsigned DEFAULT NULL COMMENT '执行状态',
  `counter_account_type` tinyint(1) unsigned DEFAULT NULL COMMENT '来源账户类型',
  `related_bill_contract_info_lv_1` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '一级单据信息',
  `related_bill_contract_info_lv_2` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '二级单据信息',
  `related_bill_contract_info_lv_3` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '三级单据信息',
  `cash_flow_account_info` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '流水账户信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `journal_voucher_uuid` (`journal_voucher_uuid`),
  KEY `billing_plan_uuid` (`billing_plan_uuid`) USING HASH,
  KEY `cash_flow_serial_no` (`cash_flow_serial_no`) USING HASH,
  KEY `cash_flow_uuid` (`cash_flow_uuid`) USING HASH,
  KEY `source_document_uuid` (`source_document_uuid`) USING HASH,
  KEY `cash_flow_account_info` (`cash_flow_account_info`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='财务凭证表';



# Dump of table ledger_book
# ------------------------------------------------------------

DROP TABLE IF EXISTS `ledger_book`;

CREATE TABLE `ledger_book` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `ledger_book_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '账本编号',
  `ledger_book_orgnization_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '账本所属公司id',
  `book_master_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT '',
  `party_concerned_ids` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='分类账本表';



# Dump of table ledger_book_shelf
# ------------------------------------------------------------

DROP TABLE IF EXISTS `ledger_book_shelf`;

CREATE TABLE `ledger_book_shelf` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ledger_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `debit_balance` decimal(19,2) DEFAULT NULL,
  `credit_balance` decimal(19,2) DEFAULT NULL,
  `first_account_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `first_account_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `account_side` int(11) NOT NULL,
  `second_account_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `second_account_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `third_account_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `third_account_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `first_party_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `second_party_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `third_party_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `forward_ledger_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `backward_ledger_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `batch_serial_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `amortized_date` date DEFAULT NULL,
  `book_in_date` datetime DEFAULT NULL,
  `business_voucher_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `carried_over_date` datetime DEFAULT NULL,
  `contract_id` bigint(20) DEFAULT NULL,
  `contract_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `default_date` datetime DEFAULT NULL,
  `journal_voucher_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ledger_book_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ledger_book_owner_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `life_cycle` int(11) DEFAULT NULL,
  `related_lv_1_asset_outer_idenity` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `related_lv_1_asset_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `related_lv_2_asset_outer_idenity` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `related_lv_2_asset_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `related_lv_3_asset_outer_idenity` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `related_lv_3_asset_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `source_document_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ledger_book_no` (`ledger_book_no`) USING BTREE,
  KEY `first_account_uuid` (`first_account_uuid`) USING BTREE,
  KEY `second_account_uuid` (`second_account_uuid`) USING BTREE,
  KEY `third_account_uuid` (`third_account_uuid`) USING BTREE,
  KEY `contract_uuid` (`contract_uuid`) USING BTREE,
  KEY `related_lv_1_asset_uuid` (`related_lv_1_asset_uuid`) USING BTREE,
  KEY `related_lv_2_asset_uuid` (`related_lv_2_asset_uuid`) USING BTREE,
  KEY `related_lv_3_asset_uuid` (`related_lv_3_asset_uuid`) USING BTREE,
  KEY `first_party_id` (`first_party_id`) USING BTREE,
  KEY `second_party_id` (`second_party_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='分类账本架表';



# Dump of table loan_batch
# ------------------------------------------------------------

DROP TABLE IF EXISTS `loan_batch`;

CREATE TABLE `loan_batch` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `is_available` bit(1) DEFAULT NULL COMMENT '是否激活',
  `code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '批次编号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `financial_contract_id` bigint(20) DEFAULT NULL COMMENT '信托合同id',
  `last_modified_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `loan_date` datetime DEFAULT NULL,
  `loan_batch_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='资产包批次表';



# Dump of table offline_bill
# ------------------------------------------------------------

DROP TABLE IF EXISTS `offline_bill`;

CREATE TABLE `offline_bill` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` decimal(19,2) DEFAULT '0.00' COMMENT '金额',
  `bank_show_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '开户行名称',
  `comment` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `trade_time` datetime DEFAULT NULL COMMENT '交易时间',
  `is_delete` bit(1) NOT NULL COMMENT '是否删除',
  `status_modified_time` datetime DEFAULT NULL COMMENT '状态修改时间',
  `last_modified_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `offline_bill_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '线下支付单编号',
  `offline_bill_status` int(11) DEFAULT '0' COMMENT '线下支付单状态 0:失败,1:成功',
  `offline_bill_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `serial_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '交易流水号',
  `payer_account_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '付款人姓名',
  `payer_account_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '付款人账号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='线下支付单表';



# Dump of table payment_channel
# ------------------------------------------------------------

DROP TABLE IF EXISTS `payment_channel`;

CREATE TABLE `payment_channel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `channel_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '支付通道名称',
  `user_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户名',
  `user_password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户密码',
  `merchant_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商户号',
  `cer_file_path` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '公钥文件地址',
  `pfx_file_path` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '私钥文件地址',
  `pfx_file_key` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '私钥key',
  `payment_channel_type` int(10) DEFAULT NULL COMMENT '通道类型 0:广银联通道',
  `api_url` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '接口地址',
  `from_date` datetime DEFAULT NULL COMMENT '开始时间',
  `thru_date` datetime DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='支付通道表';



# Dump of table payment_channel_information
# ------------------------------------------------------------

DROP TABLE IF EXISTS `payment_channel_information`;

CREATE TABLE `payment_channel_information` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `related_financial_contract_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '关联的信托合同Uuid',
  `related_financial_contract_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '关联的信托合同名称',
  `payment_channel_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '通道Uuid',
  `payment_channel_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '通道名称',
  `payment_institution_name` int(11) DEFAULT '0' COMMENT '网关 0 银联广州 1 超级网银 2 银企直连',
  `outlier_channel_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商户号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_modify_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `credit_channel_working_status` tinyint(1) DEFAULT NULL COMMENT '付款通道状态 0 未对接 1 已开启 2 已关闭',
  `debit_channel_working_status` tinyint(1) DEFAULT NULL COMMENT '收款通道状态 0 未对接 1 已开启 2 已关闭',
  `credit_payment_channel_service_uuid` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '付款通道服务Uuid',
  `debit_payment_channel_service_uuid` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '收款通道服务Uuid',
  `configure_progress` tinyint(1) DEFAULT NULL COMMENT '配置进程 0 已配置 1 待配置',
  `payment_configure_data` text COLLATE utf8_unicode_ci COMMENT '配置数据',
  `clearing_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '清算号',
  `business_type` tinyint(1) DEFAULT NULL COMMENT '业务类型 0 自有 1 委托',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



# Dump of table principal
# ------------------------------------------------------------

DROP TABLE IF EXISTS `principal`;

CREATE TABLE `principal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `authority` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '权限',
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '账号名',
  `password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '密码',
  `start_date` datetime DEFAULT NULL COMMENT '有效开始日期',
  `thru_date` datetime DEFAULT NULL COMMENT '有效截止日期',
  `t_user_id` bigint(20) DEFAULT NULL COMMENT '所属用户',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='账号信息表';



# Dump of table province
# ------------------------------------------------------------

DROP TABLE IF EXISTS `province`;

CREATE TABLE `province` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table rent_order
# ------------------------------------------------------------

DROP TABLE IF EXISTS `rent_order`;

CREATE TABLE `rent_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_type` int(11) DEFAULT '0' COMMENT '收款单类型 0:结算单,1:商户担保单',
  `due_date` date DEFAULT NULL COMMENT '结算日',
  `order_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '收款编号',
  `payout_time` datetime DEFAULT NULL COMMENT '收款时间',
  `total_rent` decimal(19,2) DEFAULT NULL COMMENT '总金额',
  `customer_id` bigint(20) DEFAULT NULL COMMENT '贷款人id',
  `user_upload_param` text COLLATE utf8_unicode_ci,
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `financial_contract_id` bigint(20) DEFAULT '-1' COMMENT '信托计划id',
  `repayment_bill_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '回款唯一编号',
  `asset_set_id` bigint(20) NOT NULL COMMENT '应收资产id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `clearing_status` int(11) DEFAULT NULL COMMENT '结清状态 0:未结清,1:已结清',
  `executing_settling_status` int(11) DEFAULT NULL COMMENT '扣款执行状态 0:已创建,1:处理中,2:扣款成功,3:扣款失败',
  `comment` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_rv0v87rr6w4sjptb9ht364kf5` (`order_no`),
  KEY `FK_mydin4osn99npigxd7mtq5p1n` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='结算单担保单表';



# Dump of table request_record
# ------------------------------------------------------------

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
  `string_field_one` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `string_field_three` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `string_field_two` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



# Dump of table settlement_order
# ------------------------------------------------------------

DROP TABLE IF EXISTS `settlement_order`;

CREATE TABLE `settlement_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `settlement_order_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `asset_set_id` bigint(20) NOT NULL COMMENT '资产id',
  `settle_order_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '清算单号',
  `guarantee_order_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '担保单号',
  `due_date` date DEFAULT NULL COMMENT '清算截止日',
  `overdue_days` bigint(20) DEFAULT '0' COMMENT '逾期天数',
  `overdue_penalty` decimal(19,2) DEFAULT '0.00' COMMENT '逾期罚息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_modify_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `settlement_amount` decimal(19,2) DEFAULT '0.00' COMMENT '结清金额',
  `comment` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='清算单表';



# Dump of table sms_quene
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sms_quene`;

CREATE TABLE `sms_quene` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `client_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '客户编号',
  `template_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '模版编号',
  `params` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '请求参数',
  `content` text COLLATE utf8_unicode_ci COMMENT '短信内容',
  `platform_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '合作商户号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `request_time` datetime DEFAULT NULL COMMENT '请求时间',
  `response_time` datetime DEFAULT NULL COMMENT '响应时间',
  `response_txt` text COLLATE utf8_unicode_ci COMMENT '响应结果',
  `allowed_send_status` tinyint(1) DEFAULT '0' COMMENT '是否允许自动发送',
  `sms_send_status` int(11) DEFAULT NULL COMMENT '短信发送状态 0:待发送,1:成功,2:失败',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='短信队列表';



# Dump of table sms_template
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sms_template`;

CREATE TABLE `sms_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '模版编号',
  `title` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '模版名称',
  `template` text COLLATE utf8_unicode_ci COMMENT '模版内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='短信模版表';



# Dump of table source_document
# ------------------------------------------------------------

DROP TABLE IF EXISTS `source_document`;

CREATE TABLE `source_document` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company_id` bigint(20) DEFAULT NULL,
  `source_document_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `source_document_type` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `issued_time` datetime DEFAULT NULL,
  `source_document_status` int(11) DEFAULT '0',
  `source_account_side` int(11) DEFAULT NULL,
  `booking_amount` decimal(19,2) DEFAULT '0.00' COMMENT '已制证金额',
  `outlier_document_uuid` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '外部凭据号',
  `outlier_trade_time` datetime DEFAULT NULL COMMENT '外部凭据交易时间',
  `outlier_counter_party_account` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `outlier_counter_party_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `outlier_account` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '外部凭据发生账号',
  `outlie_account_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `outlier_account_id` bigint(20) DEFAULT NULL,
  `outlier_company_id` bigint(20) DEFAULT NULL COMMENT '外部凭据所属公司',
  `outlier_serial_global_identity` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '外部凭据全局标识',
  `outlier_memo` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `outlier_amount` decimal(19,2) DEFAULT NULL COMMENT '外部凭据金额',
  `outlier_settlement_modes` int(11) DEFAULT NULL COMMENT '外部凭据类型',
  `outlier_breif` text COLLATE utf8_unicode_ci,
  `outlier_account_side` int(11) DEFAULT NULL,
  `appendix` text COLLATE utf8_unicode_ci,
  `first_outlier_doc_type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '一级外部单据类型',
  `second_outlier_doc_type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `third_outlier_doc_type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `currency_type` int(11) DEFAULT NULL,
  `audit_status` int(11) DEFAULT '0',
  `first_party_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '第一交易对手id',
  `second_party_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '第二交易对手id',
  `virtual_account_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '虚拟账户uuid',
  `first_account_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '一级科目code',
  `second_account_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '二级科目code',
  `third_account_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '三级科目code',
  `excute_status` tinyint(1) unsigned DEFAULT NULL COMMENT '执行状态',
  `excute_result` tinyint(1) unsigned DEFAULT NULL COMMENT '执行结果',
  `related_contract_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '相关的合同uuid',
  `financial_contract_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '相关的信托合同uuid',
  `source_document_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '凭证编号',
  `first_party_type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '第一交易对手类型',
  `first_party_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '第一交易对手名称',
  `virtual_account_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '虚拟账户编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='原始凭证表';



# Dump of table source_document_detail
# ------------------------------------------------------------

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
  `check_state` int(11) DEFAULT '0' COMMENT '明细校验状态(0:未校验,1:校验失败,2:校验成功)',
  `comment` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '记录内容',
  PRIMARY KEY (`id`),
  KEY `source_document_uuid` (`source_document_uuid`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='原始凭证明细表';



# Dump of table system_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `system_log`;

CREATE TABLE `system_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `operate_time` datetime DEFAULT NULL COMMENT '操作时间',
  `log_type` int(11) DEFAULT NULL COMMENT '日志类型 0:登录日志,1:操作日志',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `ip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'ip',
  `event` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '事件',
  `content` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '日志内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='系统日志表';



# Dump of table system_operate_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `system_operate_log`;

CREATE TABLE `system_operate_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '账号id',
  `ip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `object_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '数据uuid',
  `log_function_type` int(11) DEFAULT '0' COMMENT '日志功能类型',
  `log_operate_type` int(11) DEFAULT '0' COMMENT '日志操作类型',
  `key_content` text COLLATE utf8_unicode_ci,
  `update_content_detail` text COLLATE utf8_unicode_ci COMMENT '更新内容明细',
  `record_content_detail` text COLLATE utf8_unicode_ci COMMENT '记录内容明细',
  `record_content` text COLLATE utf8_unicode_ci COMMENT '记录内容',
  `occur_time` datetime DEFAULT NULL COMMENT '发生时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='系统操作日志表';



# Dump of table t_api_config
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_api_config`;

CREATE TABLE `t_api_config` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `api_url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '接口地址',
  `fn_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '功能代码',
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '接口说明',
  `api_status` int(11) DEFAULT '0' COMMENT '接口状态0 : 关闭，1 : 启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='接口配置表';



# Dump of table t_deduct_application
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_deduct_application`;

CREATE TABLE `t_deduct_application` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `deduct_application_uuid` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '扣款申请uuid',
  `deduct_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `request_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '扣款请求编号',
  `financial_contract_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '信托合同id',
  `financial_product_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '信托产品代码',
  `contract_unique_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '贷款合同唯一编号',
  `repayment_plan_code_list` text COLLATE utf8_unicode_ci,
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
  `transaction_recipient` tinyint(1) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `request_no` (`request_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='扣款申请表';



# Dump of table t_deduct_application_detail
# ------------------------------------------------------------

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



# Dump of table t_deduct_plan
# ------------------------------------------------------------

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
  `transaction_serial_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='扣款计划表';



# Dump of table t_interface_deduct_application_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_interface_deduct_application_log`;

CREATE TABLE `t_interface_deduct_application_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `deduct_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `request_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `contract_unique_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '贷款合同唯一ID',
  `contract_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `ip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `request_no` (`request_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



# Dump of table t_interface_import_asset_package
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_interface_import_asset_package`;

CREATE TABLE `t_interface_import_asset_package` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `fn_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `request_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `request_data` text COLLATE utf8_unicode_ci COMMENT '请求导入资产包数据',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `ip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `request_no` (`request_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



# Dump of table t_interface_repayment_information_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_interface_repayment_information_log`;

CREATE TABLE `t_interface_repayment_information_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `contract_id` bigint(20) NOT NULL COMMENT '贷款合同id',
  `request_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `request_data` text COLLATE utf8_unicode_ci COMMENT '请求变更数据',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `ip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `request_no` (`request_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='还款信息变更日志表';



# Dump of table t_interface_voucher_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_interface_voucher_log`;

CREATE TABLE `t_interface_voucher_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `request_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '请求唯一标识',
  `transaction_type` int(11) DEFAULT NULL COMMENT '交易类型(0:提交，1:撤销)',
  `business_voucher_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '凭证编号',
  `voucher_type` int(11) DEFAULT NULL COMMENT '凭证类型(0:代偿，1:担保补足，2:回购，3:差额划拨)',
  `voucher_amount` decimal(19,2) DEFAULT NULL COMMENT '凭证金额',
  `financial_contract_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '信托产品代码',
  `receivable_account_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '付款账户号',
  `payment_account_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '付款账户号',
  `payment_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '付款银行帐户名称',
  `payment_bank` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '付款银行名称',
  `bank_transaction_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '银行流水号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `ip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `request_no` (`request_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='商户付款凭证日志表';



# Dump of table t_merchant_config
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_merchant_config`;

CREATE TABLE `t_merchant_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mer_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商户编号',
  `secret` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商户唯一标识',
  `company_id` bigint(20) NOT NULL COMMENT '商户公司编号(company表id)',
  `pub_key_path` text COLLATE utf8_unicode_ci COMMENT '商户公钥地址',
  `pub_key` text COLLATE utf8_unicode_ci COMMENT '商户公钥',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='商户API请求配置表';



# Dump of table t_prepayment_application
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_prepayment_application`;

CREATE TABLE `t_prepayment_application` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `contract_id` bigint(20) DEFAULT NULL COMMENT '贷款合同id',
  `asset_set_id` bigint(20) DEFAULT NULL COMMENT '还款计划id',
  `unique_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `contract_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `request_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `asset_recycle_date` date DEFAULT NULL COMMENT '计划提前还款时间',
  `asset_initial_value` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` int(11) DEFAULT NULL COMMENT '提前还款类型 0:全部提前还款',
  `asset_set_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `ip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `prepayment_status` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '提前还款状态 0:未还款，1：还款成功，2:还款失败',
  `completed_time` datetime DEFAULT NULL COMMENT '完成时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `request_no` (`request_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



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
  UNIQUE KEY `unique_key_request_no` (`request_no`),
  KEY `remittance_application_uuid` (`remittance_application_uuid`) USING HASH
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
  PRIMARY KEY (`id`),
  KEY `remittance_application_uuid` (`remittance_application_uuid`) USING HASH,
  KEY `remittance_application_detail_uuid` (`remittance_application_detail_uuid`) USING HASH
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
  `payment_channel_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '支付通道名称',
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
  PRIMARY KEY (`id`),
  KEY `remittance_application_uuid` (`remittance_application_uuid`) USING HASH,
  KEY `remittance_plan_uuid` (`remittance_plan_uuid`) USING HASH
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
  `payment_gateway` tinyint(1) unsigned DEFAULT NULL COMMENT '支付网关0:广州银联，1:超级网银，2:银企直连',
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
  PRIMARY KEY (`id`),
  KEY `remittance_application_uuid` (`remittance_application_uuid`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='放款计划执行日志';



# Dump of table t_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '用户名字',
  `email` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '联系邮箱',
  `phone` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '联系电话',
  `company_id` bigint(20) DEFAULT NULL COMMENT '所属公司',
  `dept_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '部门名称',
  `position_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '职位名称',
  `remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户信息表';



# Dump of table transfer_application
# ------------------------------------------------------------

DROP TABLE IF EXISTS `transfer_application`;

CREATE TABLE `transfer_application` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `transfer_application_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `amount` decimal(19,2) DEFAULT NULL COMMENT '扣款金额',
  `batch_pay_record_id` bigint(20) DEFAULT NULL COMMENT '批支付记录',
  `comment` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '转账申请备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '转账申请创建人',
  `deduct_status` int(11) DEFAULT NULL COMMENT '扣款结果状态 0:未扣款成功,1:已扣款成功',
  `payment_way` int(11) DEFAULT NULL COMMENT '支付方式 0:银联代扣,1:线下支付',
  `executing_deduct_status` int(11) DEFAULT NULL COMMENT '执行状态 0:已创建,1:扣款中:2,扣款成功,3扣款失败,4扣款超时',
  `transfer_application_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '转账申请单号',
  `order_id` int(11) NOT NULL COMMENT '结算单id',
  `deduct_time` datetime DEFAULT NULL COMMENT '扣款结果时间',
  `last_modified_time` datetime DEFAULT NULL COMMENT '最后状态更新时间',
  `contract_account_id` bigint(20) NOT NULL COMMENT '贷款人扣款账户',
  `union_pay_order_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '上传给银联的唯一的订单号',
  `payment_channel_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_m3y9rhpjoo97gam48ulhgg175` (`contract_account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='线上支付单表';



# Dump of table unionpay_bank_config
# ------------------------------------------------------------

DROP TABLE IF EXISTS `unionpay_bank_config`;

CREATE TABLE `unionpay_bank_config` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `bank_code` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '银行代码',
  `bank_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '银行名称',
  `use_batch_deduct` tinyint(1) DEFAULT '0' COMMENT '0:不使用，1:使用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `bank_code` (`bank_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='银联银行配置表';



# Dump of table unionpay_manual_transaction
# ------------------------------------------------------------

DROP TABLE IF EXISTS `unionpay_manual_transaction`;

CREATE TABLE `unionpay_manual_transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `bank_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '银行代码',
  `bank_card_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '银行卡号',
  `bank_card_owner` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '银行卡所有人姓名',
  `id_card_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '身份证号',
  `province` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '扣款卡开户行所在省',
  `city` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '扣款卡开户行所在市',
  `amount` decimal(19,2) DEFAULT NULL COMMENT '扣款金额',
  `remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `unionpay_transaction_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '请求银联交易编号',
  `transaction_type` int(9) NOT NULL DEFAULT '0' COMMENT '0:实时代收，1:批量代收',
  `payment_channel_id` int(11) NOT NULL DEFAULT '1' COMMENT '支付通道',
  `request_packet` text COLLATE utf8_unicode_ci COMMENT '请求报文',
  `response_packet` text COLLATE utf8_unicode_ci COMMENT '响应报文',
  `query_response_packet` text COLLATE utf8_unicode_ci COMMENT '查询响应报文',
  PRIMARY KEY (`id`),
  UNIQUE KEY `transaction_no` (`unionpay_transaction_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='银联手动交易记录表';



# Dump of table update_asset_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `update_asset_log`;

CREATE TABLE `update_asset_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `contract_id` bigint(20) NOT NULL COMMENT '贷款合同id',
  `request_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '请求编号',
  `request_reason` text COLLATE utf8_unicode_ci COMMENT '请求原因',
  `request_data` text COLLATE utf8_unicode_ci COMMENT '请求变更数据',
  `origin_data` text COLLATE utf8_unicode_ci COMMENT '变更数据备份',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `ip` text COLLATE utf8_unicode_ci COMMENT '请求ip',
  PRIMARY KEY (`id`),
  UNIQUE KEY `request_no` (`request_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='变更还款信息日志表';



# Dump of table usb_key
# ------------------------------------------------------------

DROP TABLE IF EXISTS `usb_key`;

CREATE TABLE `usb_key` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bank_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '银行代码',
  `config` text COLLATE utf8_unicode_ci COMMENT '配置',
  `key_type` int(11) DEFAULT NULL COMMENT 'key类型 0:实体 1:电子',
  `uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='usb_key表';



# Dump of table usb_key_account_relation
# ------------------------------------------------------------

DROP TABLE IF EXISTS `usb_key_account_relation`;

CREATE TABLE `usb_key_account_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_uuid` varchar(255) DEFAULT NULL COMMENT '账户uuid',
  `usb_key_uuid` varchar(255) DEFAULT NULL COMMENT 'usb_key uuid',
  `gate_way_type` int(11) DEFAULT NULL COMMENT '网关类型 0:银企直联 1:超级网银',
  `start_date` datetime DEFAULT NULL COMMENT '开始日期',
  `end_date` datetime DEFAULT NULL COMMENT '结束日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='usb_key&account关系表';



# Dump of table virtual_account
# ------------------------------------------------------------

DROP TABLE IF EXISTS `virtual_account`;

CREATE TABLE `virtual_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `total_balance` decimal(19,2) DEFAULT NULL COMMENT '余额',
  `virtual_account_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '虚拟账户uuid',
  `parent_account_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '父账户uuid',
  `virtual_account_alias` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '虚拟账户别名',
  `virtual_account_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '虚拟账户编号',
  `version` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '虚拟账户版本号',
  `owner_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '账户拥有者uuid',
  `owner_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '账户拥有者姓名',
  `fst_level_contract_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '一级合同uuid',
  `snd_level_contract_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '二级合同uuid',
  `trd_level_contract_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '三级合同uuid',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_time` datetime DEFAULT NULL COMMENT '余额最后更新时间',
  `last_modified_time` datetime DEFAULT NULL COMMENT '数据最后修改时间',
  `customer_type` tinyint(1) unsigned DEFAULT NULL COMMENT '客户类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

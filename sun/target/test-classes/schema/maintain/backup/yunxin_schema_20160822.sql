SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for payment_channel_information
-- ----------------------------
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

-- ----------------------------
-- Table structure for financial_contract_config
-- ----------------------------
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

SET FOREIGN_KEY_CHECKS=1;

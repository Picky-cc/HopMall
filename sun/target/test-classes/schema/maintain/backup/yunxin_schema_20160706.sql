SET FOREIGN_KEY_CHECKS=0;

-- 20160627 start

INSERT INTO `t_api_config` (`id`, `api_url`, `fn_code`, `description`, `api_status`)
VALUES
	(3, '/api/command', '300001', '逾期扣款接口', 0),
	(4, '/api/query', '100002', '逾期扣款结果查询接口', 0);

DROP TABLE IF EXISTS `t_interface_deduct_bill`;

CREATE TABLE `t_interface_deduct_bill` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `fn_code` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '功能代码',
  `request_id` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '请求唯一标识',
  `unique_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '贷款合同唯一编号',
  `contract_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '贷款合同编号',
  `contract_id` bigint(20) NOT NULL COMMENT '贷款合同id',
  `contract_account_id` bigint(20) NOT NULL COMMENT '贷款合同账户',
  `amount` decimal(19,2) NOT NULL COMMENT '扣款金额',
  `channel_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '通道编号',
  `deduct_detail` text COLLATE utf8_unicode_ci COMMENT '扣款明细JsonArray',
  `payment_channel_id` bigint(20) NOT NULL COMMENT '通道id',
  `req_no` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '扣款交易请求号',
  `rsp_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '扣款交易响应号',
  `request_packet` text COLLATE utf8_unicode_ci COMMENT '请求报文',
  `response_packet` text COLLATE utf8_unicode_ci COMMENT '响应报文',
  `query_result_packet` text COLLATE utf8_unicode_ci COMMENT '查询结果报文',
  `executing_deduct_status` int(11) DEFAULT NULL COMMENT '执行状态',
  `send_time` datetime DEFAULT NULL COMMENT '交易发送时间',
  `completed_time` datetime DEFAULT NULL COMMENT '完成时间',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `last_query_time` datetime DEFAULT NULL,
  `caller_ip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '调用者ip',
  `remark` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `original_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '原始响应代码',
  `original_msg` varchar(255) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '原始响应信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_id` (`request_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 20160627 end
	
-- 20160628 start
alter table `asset_set` add column `overdue_status` int(11) default 0 NOT NULL COMMENT '逾期状态';
alter table `asset_set` add column `overdue_date` date default null COMMENT '逾期日期' after `overdue_status`;
-- 20160628 end

SET FOREIGN_KEY_CHECKS=1;

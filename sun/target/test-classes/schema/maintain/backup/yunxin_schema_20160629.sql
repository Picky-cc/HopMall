SET FOREIGN_KEY_CHECKS=0;

-- 20160622 start
DROP TABLE IF EXISTS `update_asset_log`;

CREATE TABLE `update_asset_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `contract_id` bigint(20) NOT NULL COMMENT '贷款合同id',
  `request_no` text COLLATE utf8_unicode_ci COMMENT '请求编号',
  `request_reason` text COLLATE utf8_unicode_ci COMMENT '请求原因',
  `request_data` text COLLATE utf8_unicode_ci COMMENT '请求变更数据',
  `origin_data` text COLLATE utf8_unicode_ci COMMENT '变更数据备份',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `ip` text COLLATE utf8_unicode_ci COMMENT '请求ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
-- 20160622 end

-- 20160623	start

DROP TABLE IF EXISTS `t_api_config`;

CREATE TABLE `t_api_config` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `api_url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '接口地址',
  `fn_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '功能代码',
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '接口说明',
  `api_status` int(11) DEFAULT '0' COMMENT '接口状态0 : 关闭，1 : 启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `t_api_config` (`id`, `api_url`, `fn_code`, `description`, `api_status`)
VALUES
	(1, '/api/query', '100001', '查询还款计划接口', 0),
	(2, '/api/modify', '200001', '变更还款计划接口', 0);

-- 20160623 end

-- 20160624 start
ALTER TABLE `contract` ADD COLUMN `unique_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'uuid' after `id`;
-- 20160624 end

SET FOREIGN_KEY_CHECKS=1;

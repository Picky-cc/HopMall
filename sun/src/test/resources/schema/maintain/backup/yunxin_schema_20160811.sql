SET FOREIGN_KEY_CHECKS=0;

ALTER TABLE `contract_account` ADD COLUMN `standard_bank_code` varchar(255) COLLATE utf8_unicode_ci   DEFAULT NULL COMMENT '标准银行代码' after `city` ;

DROP TABLE IF EXISTS `t_interface_import_asset_package`;
CREATE TABLE `t_interface_import_asset_package` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `fn_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `request_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `request_data` text COLLATE utf8_unicode_ci COMMENT '请求导入资产包数据',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `ip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

DROP TABLE IF EXISTS `bank`;
CREATE TABLE `bank` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `bank_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bank_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 20160802 end 

INSERT INTO `t_api_config` ( `api_url`, `fn_code`, `description`, `api_status`) VALUES ( '/api/modify', '200004', '导入资产包', '1');

-- 20160809 start

ALTER TABLE `asset_set`  add COLUMN `extra_charge` text COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '额外费用';

-- 20160809 end


-- 20160809 start

ALTER TABLE `contract` ADD COLUMN `uuid` varchar(255) COLLATE utf8_unicode_ci  DEFAULT NULL COMMENT 'uuid' after `id`;
UPDATE `contract` SET `uuid` = uuid() where `uuid` is null;

ALTER TABLE `contract` ADD COLUMN `state` int(11) DEFAULT '2' COMMENT '合同状态: 0:放款中,1:未生效,2:已生效，3:异常中止';
-- 20160809 end

SET FOREIGN_KEY_CHECKS=1;
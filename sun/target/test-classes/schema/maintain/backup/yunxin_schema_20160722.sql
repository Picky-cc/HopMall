SET FOREIGN_KEY_CHECKS=0;

-- 20160713 start
ALTER TABLE `asset_set` ADD COLUMN `version_no` INT(11) COMMENT '还款计划版本号';

ALTER TABLE `asset_set` ADD COLUMN `active_status` INT(11) NOT NULL DEFAULT '0' COMMENT '有效状态 0:开启,1:作废';

ALTER TABLE `contract` ADD COLUMN `active_version_no` INT(11) COMMENT '有效的还款计划版本号';

-- 更新历史还款计划version_no
UPDATE `asset_set` SET `version_no` = 1 WHERE `version_no` IS NULL;

-- 更新历史贷款合同active_version_no
UPDATE `contract` SET `active_version_no` = 1 WHERE `active_version_no` IS NULL;

-- 20160713 end

-- 20160720 start

ALTER TABLE t_interface_prepayment_log RENAME t_prepayment_application;

ALTER TABLE `t_prepayment_application` CHANGE `origin_data` `asset_set_uuid` varchar(255) COLLATE utf8_unicode_ci;

-- 修改t_prepayment_application asset_initial_value、unique_id、contract_no、request_no、ip 数据类型
ALTER TABLE `t_prepayment_application` CHANGE `asset_initial_value` `asset_initial_value` varchar(255) COLLATE utf8_unicode_ci;

ALTER TABLE `t_prepayment_application` CHANGE `unique_id` `unique_id` varchar(255) COLLATE utf8_unicode_ci;

ALTER TABLE `t_prepayment_application` CHANGE `contract_no` `contract_no` varchar(255) COLLATE utf8_unicode_ci;

ALTER TABLE `t_prepayment_application` CHANGE `request_no` `request_no` varchar(255) COLLATE utf8_unicode_ci;

ALTER TABLE `t_prepayment_application` CHANGE `ip` `ip` varchar(255) COLLATE utf8_unicode_ci;

ALTER TABLE `t_prepayment_application` DROP COLUMN status, DROP COLUMN result_msg;

-- 修改 t_interface_repayment_information_log request_no、ip 数据类型
ALTER TABLE `t_interface_repayment_information_log` CHANGE `request_no` `request_no` varchar(255) COLLATE utf8_unicode_ci;

ALTER TABLE `t_interface_repayment_information_log` CHANGE `ip` `ip` varchar(255) COLLATE utf8_unicode_ci;

-- 修改 t_interface_deduct_bill unique索引
DROP INDEX unique_id on t_interface_deduct_bill;

CREATE UNIQUE INDEX unique_request_no on t_interface_deduct_bill (request_no);

-- 20160720 end

-- 20160722 start

ALTER TABLE `contract` ADD COLUMN `repayment_plan_operate_logs` TEXT COLLATE utf8_unicode_ci COMMENT '还款计划操作日志JsonArray';

-- 20160722 end

SET FOREIGN_KEY_CHECKS=1;

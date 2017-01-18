SET FOREIGN_KEY_CHECKS=0;

ALTER TABLE `t_remittance_plan_exec_log` ADD COLUMN `plan_credit_cash_flow_check_number` int(11) DEFAULT '0' COMMENT '计划贷记流水检查次数';
ALTER TABLE `t_remittance_plan_exec_log` ADD COLUMN `actual_credit_cash_flow_check_number` int(11) DEFAULT '0' COMMENT '实际贷记流水检查次数';
ALTER TABLE `t_remittance_plan_exec_log` ADD COLUMN `reverse_status` TINYINT(1) UNSIGNED DEFAULT '0' COMMENT '冲账状态:0:未发生,1:未冲账,2:已冲账';
ALTER TABLE `t_remittance_plan_exec_log` ADD COLUMN `credit_cash_flow_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '贷记流水号';
ALTER TABLE `t_remittance_plan_exec_log` ADD COLUMN `debit_cash_flow_uuid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '借记流水号';

SET FOREIGN_KEY_CHECKS=1;

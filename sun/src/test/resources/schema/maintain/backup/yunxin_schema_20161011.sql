SET FOREIGN_KEY_CHECKS=0;

ALTER TABLE `t_interface_data_sync_log` ADD INDEX asset_set_uuid ( `asset_set_uuid` ) USING HASH;

ALTER TABLE `t_deduct_application` ADD INDEX deduct_application_uuid ( `deduct_application_uuid` ) USING HASH;

ALTER TABLE `t_deduct_application_detail` ADD INDEX deduct_application_uuid ( `deduct_application_uuid` ) USING HASH;

ALTER TABLE `t_deduct_application_detail` ADD INDEX asset_set_uuid ( `asset_set_uuid` ) USING HASH;

ALTER TABLE `system_operate_log` ADD INDEX object_uuid ( `object_uuid` ) USING HASH;

ALTER TABLE `asset_set_extra_charge` ADD INDEX asset_set_uuid ( `asset_set_uuid` ) USING HASH;

ALTER TABLE `asset_set` add COLUMN `sync_status` tinyint(1)  default 0 comment '同步状态';

-- 20161009 信托约束字段

ALTER TABLE financial_contract ADD COLUMN `sys_create_statement_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '是否由系统产生结算单，0:关闭，1:开启';

-- 农分期，开启生成日结算单
UPDATE financial_contract SET sys_create_statement_flag = '1' WHERE contract_no = 'G08200';

ALTER TABLE rent_order add INDEX asset_set_id(asset_set_id);

ALTER TABLE transfer_application add INDEX order_id(order_id);

ALTER TABLE `t_remittance_plan_exec_log` ADD INDEX remittance_plan_uuid ( `remittance_plan_uuid` ) USING HASH;

ALTER TABLE `t_remittance_plan_exec_log` ADD INDEX exec_req_no ( `exec_req_no` ) USING HASH;

ALTER TABLE `t_remittance_refund_bill` ADD INDEX related_exec_req_no ( `related_exec_req_no` ) USING HASH;

SET FOREIGN_KEY_CHECKS=1;

 SET FOREIGN_KEY_CHECKS=0;

 delete from asset_set_extra_charge;
 
 INSERT INTO `asset_set_extra_charge` (`id`, `asset_set_extra_charge_uuid`, `asset_set_uuid`, `create_time`, `last_modify_time`, `first_account_name`, `first_account_uuid`, `second_account_name`, `second_account_uuid`, `third_account_name`, `third_account_uuid`, `account_amount`) 
 VALUES 
 ('310', 'caa20a1c-ff80-439f-9d4d-7a4d117a1113', '88450378-e6fd-4857-9562-22971b05b932', '2016-09-06 15:51:31', '2016-09-06 15:51:31', 'FST_RECIEVABLE_ASSET', '20000', 'SND_RECIEVABLE_LOAN_PENALTY', '20000.03', NULL, NULL, '90.00'),
 ('311', '4dc43d47-81da-4cfd-82fa-78a16a7a78d8', '88450378-e6fd-4857-9562-22971b05b932', '2016-09-06 15:51:31', '2016-09-06 15:51:31', 'FST_RECIEVABLE_ASSET', '20000', 'SND_RECIEVABLE_OVERDUE_FEE', '20000.06', 'TRD_RECIEVABLE_OVERDUE_FEE_OBLIGATION', '20000.06.01', '100.00');

 
 
 
 SET FOREIGN_KEY_CHECKS=1;
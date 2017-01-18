SET FOREIGN_KEY_CHECKS=0;

delete from `app_arrive_record`;
INSERT INTO `app_arrive_record` (`id`, `amount`, `arrive_record_status`, `pay_ac_no`, `receive_ac_no`, `remark`, `serial_no`, `time`, `app_id`, `drcrf`, `pay_name`, `summary`, `vouh_no`, `operate_remark`)
VALUES
	(52,33000.00,0,'','1001300219000027827','','2015-04-08-15.07.46.398390','2015-04-08 15:07:46',2,'2','','天山华庭3#803租金及','16135607',NULL),
	(116, 15000.00, 3, '117311096547400', '1001300219000027827', '货款', '2015-04-21-16.02.33.061089', '2015-04-21 16:02:33', 2, '2', '东亚银行(中国)有限公司', '货款', '0', '[{\"operateRemark\":\"\",\"operateTime\":\"2015-09-17 16:03:04\",\"operateType\":0,\"operatorId\":9}]'),
	(336, 11500.00, 3, '121915739910802', '1001300219000027827', '房屋租金', '2015-05-18-09.15.27.197880', '2015-05-18 09:15:27', 2, '2', '上海坤烛股权投资基金管理有限公司', '房屋租金', '0', ''),
	(4165, 15000.00, 0, '117311096547400', '1001300219000027827', '货款', '2015-09-22-17.34.24.134155', '2015-09-22 17:34:24', 2, '2', '东亚银行(中国)有限公司', '货款', '0', NULL);

delete from `rent_order`;
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`, `user_upload_param`, `unique_particle_id`, `transfer_status`, `modify_time`, `unique_bill_id`, `contract_payment_amount`, `contract_payment_type`, `doubt_record`, `virtual_account_unique_id`)
VALUES
	(481, '2015-10-20', '2015-11-19', NULL, 'SHIMAO6#37C-20150417-17', 0, 0.00, NULL, 00000000, '2015-10-20', 26000.00, 89, 150, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 1, NULL, NULL, NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a'),
	(559, '2015-09-26', '2015-10-25', NULL, 'SHIMAO6#40C-20150427-17', 0, 0.00, NULL, 00000000, '2015-09-26', 26000.00, 99, 160, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 1, NULL, NULL, NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a');

delete from `receive_order_map`;
INSERT INTO `receive_order_map` (`id`, `amount`, `association_type`, `create_time`, `is_deducted`, `pay_time`, `app_arrive_record_id`, `order_id`, `receive_order_map_status`)
VALUES
	(961, 0.00, 0, '2015-09-22 17:37:54', 00000000, NULL, 4165, 481, 0),
	(962, 0.00, 0, '2015-09-22 17:37:54', 00000000, NULL, 4165, 559, 0);

SET FOREIGN_KEY_CHECKS=1;

SET FOREIGN_KEY_CHECKS = 0;

delete from rent_order;
delete from contract;
delete from customer;
delete from house;
delete from app;
delete from import_result;

INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`, `user_upload_param`, `unique_particle_id`, `transfer_status`, `modify_time`, `unique_bill_id`, `contract_payment_amount`, `contract_payment_type`, `doubt_record`, `virtual_account_unique_id`) VALUES
(6960, '2015-04-01', NULL, NULL, 'KXHY1#1803-20150402-1', 4, '16500.00', '2015-04-03 16:45:24', b'0', NULL, '16500.00', 13, 106, 1, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 1, NULL, NULL, NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a'),
(6961, '2015-05-01', NULL, NULL, 'KXHY1#1803-20150402-2', 4, '16500.00', '2015-05-13 15:17:34', b'0', NULL, '16500.00', 13, 106, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 1, NULL, NULL, NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a'),
(6962, '2015-06-01', NULL, NULL, 'KXHY1#1803-20150402-3', 4, '16500.00', '2015-05-27 16:45:49', b'0', NULL, '16500.00', 13, 106, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 1, NULL, NULL, NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a'),
(6963, '2015-06-01', NULL, NULL, 'KXHY1#1803-20150402-4', 4, '16500.00', '2015-05-27 16:45:49', b'0', NULL, '16500.00', 13, 106, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 1, NULL, NULL, NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a');

INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`, `transfer_status`) VALUES
(1014, '2015-04-01', 'KXHY1#1803', 0, '33000.00', '2016-03-31', NULL, '', '16500.00', b'0', 1, NULL, NULL, 2, 106, 13, 0),
(1015, '2014-11-01', 'HNHY3#602', 0, '25200.00', '2015-10-31', NULL, '', '12600.00', b'0', 1, NULL, NULL, 2, 107, 14, 0),
(1016, '2014-06-01', 'HQHY1#33B', 0, '34000.00', '2015-11-30', NULL, '', '17000.00', b'0', 1, NULL, NULL, 2, 108, 15, 0);

INSERT INTO `house` (`id`, `address`, `area`, `bulid_area`, `city`, `community`, `community_detail`, `hall`, `house_status`, `house_type`, `province`, `room`, `app_id`) VALUES
(660, '汇川路88号1#1803', NULL, 0, NULL, '凯欣豪苑1#1803', NULL, 0, 1, 1, NULL, 0, 2),
(661, '徐汇区淮海中路183弄3号602室', NULL, 0, NULL, '汇宁花园3#602', NULL, 0, 1, 1, NULL, 0, 2),
(662, '紫云路118弄1号33B室', NULL, 0, NULL, '虹桥豪苑1#33B', NULL, 0, 1, 1, NULL, 0, 2);

INSERT INTO `customer` (`id`, `account`, `city`, `mobile`, `name`, `province`, `source`, `app_id`) VALUES
(1066, NULL, NULL, NULL, '小寓预付款承租方', NULL, '寓见', 1),
(1067, NULL, NULL, NULL, '上海化耀国际贸易有限公司', NULL, '优帕克', 2),
(1068, NULL, NULL, NULL, '吴林飞', NULL, '优帕克', 2),
(1069, NULL, NULL, NULL, 'R.B', NULL, '优帕克', 2);

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `payee_account_name`, `payee_account_no`, `payee_bank_name`) VALUES
(1, 'xiaoyu', '70991db75ebb24fa0993f4fa25775022', b'0', 'http://beta.demo2do.com/jupiter/', '寓见', 5, NULL, NULL, NULL),
(2, 'youpark', '123456', b'0', '', '优帕克', 4, NULL, NULL, NULL),
(3, 'test4Zufangbao', '2e85ae999845f25faf8ea7b514ee0aca', b'0', 'http://e.zufangbao.cn', '租房宝测试账号', 9, NULL, NULL, NULL);

INSERT INTO `import_result`(`id`,
    `app_id`,
    `batch_no`,
    `origin_resources`,
    `update_data_log`,
    `operator_user_id`,
    `create_time`,
    `modify_time`,
    `import_result_status`,
    `note`) VALUES (1, 1, '9c37ecd0-6524-4b38-a184-68527a80986b', '{\"savedOrderCsv\":[{\"contractId\":1014,\"contractNo\":\"ct-0001122\",\"count\":\"2\",\"customerId\":1067,\"dueDate\":\"2015/8/5\",\"endDate\":\"2015/11/10\",\"houseId\":655,\"oldHouseId\":655,\"orderId\":6960,\"startDate\":\"2015/8/11\",\"totalRent\":\"2100\"},{\"contractId\":1015,\"contractNo\":\"ct-0002122\",\"count\":\"2\",\"customerId\":1068,\"dueDate\":\"2015/8/25\",\"endDate\":\"2015/11/30\",\"houseId\":657,\"oldHouseId\":657,\"orderId\":6961,\"startDate\":\"2015/8/31\",\"totalRent\":\"3000\"},{\"contractId\":1016,\"contractNo\":\"ct-0029122\",\"count\":\"3\",\"customerId\":1069,\"dueDate\":\"2015/7/7\",\"endDate\":\"2015/10/12\",\"houseId\":662,\"oldHouseId\":662,\"orderId\":6962,\"startDate\":\"2015/7/13\",\"totalRent\":\"3300\"},{\"contractId\":1016,\"contractNo\":\"ct-0029122\",\"count\":\"4\",\"customerId\":1069,\"dueDate\":\"2015/10/7\",\"endDate\":\"2016/1/12\",\"houseId\":662,\"oldHouseId\":662,\"orderId\":6963,\"startDate\":\"2015/10/13\",\"totalRent\":\"3300\"}],\"savedcontractCsv\":[{\"address\":\"杭州大道388号城市春天1-802\",\"appId\":1,\"beginDate\":\"2015年5月11日\",\"community\":\"ct-108021\",\"contractId\":1014,\"contractNo\":\"ct-0001122\",\"customerId\":1067,\"customerName\":\"唐XX\",\"deposit\":\"1300\",\"endDate\":\"2015年11月10日\",\"houseId\":655,\"mobile\":\"18201010101\",\"monthFee\":\"700\",\"oldHouseId\":655,\"paymentInstrument\":\"1\",\"successfully_saved\":true},{\"address\":\"杭州大道388号城市春天1-803\",\"appId\":1,\"beginDate\":\"2015年5月31日\",\"community\":\"ct-108023\",\"contractId\":1015,\"contractNo\":\"ct-0002122\",\"customerId\":1068,\"customerName\":\"林X\",\"deposit\":\"1700\",\"endDate\":\"2015年11月30日\",\"houseId\":657,\"mobile\":\"18201010101\",\"monthFee\":\"1000\",\"oldHouseId\":657,\"paymentInstrument\":\"1\",\"successfully_saved\":true},{\"address\":\"杭州大道388号城市春天2-402\",\"appId\":1,\"beginDate\":\"2015年1月13日\",\"community\":\"ct-204022\",\"contractId\":1016,\"contractNo\":\"ct-0029122\",\"customerId\":1069,\"customerName\":\"杨X\",\"deposit\":\"1800\",\"endDate\":\"2016年1月12日\",\"houseId\":662,\"mobile\":\"18201010101\",\"monthFee\":\"1100\",\"oldHouseId\":662,\"paymentInstrument\":\"1\",\"successfully_saved\":true}]}',NULL, NULL, '2015-8-31 20:22:37', '2015-8-31 20:23:16', 2, 'a');

SET FOREIGN_KEY_CHECKS = 1;
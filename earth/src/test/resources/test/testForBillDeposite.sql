SET FOREIGN_KEY_CHECKS=0;
delete from partical_model;
delete from partical;
delete from principal;
delete from app_service_config;
delete from app_account;
delete from app_particles;
delete from app where id = 3;
delete from rent_order;

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`) VALUES
  (3,'zufangbao','123456',00000000,'http://www.zufangbao.com','租房宝测试',9);
 
INSERT INTO `app_service_config` (`channel`, `name`, `url`, `app_id`) VALUES
 (1,'query-order','query-order',3),
 (1,'payment-notify','sync-order',3),
  (1,'view-name','ali-payment-harf',3),
  (1,'app_bill_page_url','http://www.zufangbao.com',3),
  (1,'app_consult_page_url','http://www.zufangbao.com',3),
   (1,'return_url','http://www.zufangbao.com',3),
    (1,'notify_url','http://www.zufangbao.com',3);
  
 
INSERT INTO `app_account` ( `channel`, `header`, `logo`, `name`, `app_id`) VALUES
  ( 1, NULL, 'banner-logo.png', '小寓科技', 3);


INSERT INTO `partical` (`id`, `entry_url`, `partical_name`, `partical_status`, `partical_type`, `unique_partical_id`)
VALUES
	(1, 'http://127.0.0.1:17523/testquark', 'test_quark', 1, 0, '963eb1401db6fc3cfce2a7cd5e9f916d'),
	(2, 'http://127.0.0.1:17523/testquark', 'test2_quark', 1, 0, 'e13855b7f2037a9fed12432720d156b5');


INSERT INTO `partical_model` (`id`, `value`, `name`, `unique_partical_id`)
VALUES
	(1, '/cooling-bills', 'coolingHotBills', '963eb1401db6fc3cfce2a7cd5e9f916d'),
	(2, '/app-config/sync', 'syncAppConfig', '963eb1401db6fc3cfce2a7cd5e9f916d'),
	(3, '/hot-snapshot/all', 'snapShotHotBills', '963eb1401db6fc3cfce2a7cd5e9f916d'),
	(4, 'testvalue21', 'testname1', 'e13855b7f2037a9fed12432720d156b5'),
	(5, 'testvalue22', 'testname2', 'e13855b7f2037a9fed12432720d156b5'),
	(6, 'testvalue23', 'testname3', 'e13855b7f2037a9fed12432720d156b5');

INSERT INTO `app_particles` (`id`,`app_id`,`is_delete`,`modified_time`,`unique_partical_id`) VALUES (3,'xiaoyu',b'0','2015-04-27 15:00:58','963eb1401db6fc3cfce2a7cd5e9f916d'),
(1,'zufangbao',b'0','2015-04-27 15:00:58','483b89b9ac532bb271a7ba407304fd22'),(4,'xiaoyu',b'0','2015-04-27 15:00:58','963eb1401db6fc3cfce2a7cd5e9f916d');
	

INSERT INTO `principal` (`id`, `authority`, `name`, `password`) VALUES
	(1, 'ROLE_SUPER_USER', 'root', NULL, 'e10adc3949ba59abbe56e057f20f883e'),
	(2, 'ROLE_INSTITUTION', 'dingcheng', 2, 'e10adc3949ba59abbe56e057f20f883e'),
	(3, 'ROLE_APP', 'xiaoyu', 1, 'e10adc3949ba59abbe56e057f20f883e'),
	(4, 'ROLE_APP','youpark',2,'086246bffb2de7288946151fc900db59');

INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`, `user_upload_param`, `unique_particle_id`, `transfer_status`, `modify_time`, `unique_bill_id`, `contract_payment_amount`, `contract_payment_type`, `doubt_record`, `virtual_account_unique_id`, `bill_life_cycle`, `collection_bill_life_cycle`, `collection_bill_capital_status`, `collection_bill_service_notice_status`, `collection_bill_frozen_status`, `asset_package_id`, `factoring_contract_id`)
VALUES
	(107, '2015-04-01', NULL, NULL, 'KXHY1#1803-20150402-1', 2, 16500.00, '2015-04-03 16:45:24', 00000000, NULL, 16500.00, 13, 106, 1, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', NULL, NULL, NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a', 0, 1, 1, 3, 1, 0, NULL, -1);

SET FOREIGN_KEY_CHECKS=1;
SET FOREIGN_KEY_CHECKS=0;
delete from partical_model;
delete from partical;
delete from principal;
delete from app_service_config where app_id = 3;
delete from app_account where app_id = 3;
delete from app_particles;
delete from app;
delete from company;

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`) VALUES
  (3,'zufangbao','123456',00000000,'http://beta.demo2do.com/jupiter/','租房宝测试',9);
  
INSERT INTO `company` (`id`, `address`, `full_name`, `short_name`, `bank_name`, `card_no`, `name`)
VALUES
	(9, '杭州', '租房宝测试', '租房宝测试', NULL, NULL, NULL);


INSERT INTO `partical` (`id`, `entry_url`, `partical_name`, `partical_status`, `partical_type`, `unique_partical_id`)
VALUES
	(1, 'http://test.url', 'test_quark', 0, 0, '483b89b9ac532bb271a7ba407304fd22'),
	(2, 'http://test2.url', 'test2_quark', 1, 1, 'e13855b7f2037a9fed12432720d156b5');


INSERT INTO `partical_model` (`id`, `value`, `name`, `unique_partical_id`)
VALUES
	(1, 'testvalue11', 'testname1', '483b89b9ac532bb271a7ba407304fd22'),
	(2, 'testvalue12', 'testname2', '483b89b9ac532bb271a7ba407304fd22'),
	(3, 'testvalue13', 'testname3', '483b89b9ac532bb271a7ba407304fd22'),
	(4, 'testvalue21', 'testname1', 'e13855b7f2037a9fed12432720d156b5'),
	(5, 'testvalue22', 'testname2', 'e13855b7f2037a9fed12432720d156b5'),
	(6, 'testvalue23', 'testname3', 'e13855b7f2037a9fed12432720d156b5');

INSERT INTO `app_particles` (`id`,`app_id`,`is_delete`,`modified_time`,`unique_partical_id`) VALUES (3,'zufangbao',b'0','2015-04-27 15:00:58','483b89b9ac532bb271a7ba407304fd22');
	

INSERT INTO `principal` (`id`, `authority`, `name`, `password`) VALUES
	(1, 'ROLE_SUPER_USER', 'root', NULL, 'e10adc3949ba59abbe56e057f20f883e'),
	(2, 'ROLE_INSTITUTION', 'dingcheng', 2, 'e10adc3949ba59abbe56e057f20f883e'),
	(3, 'ROLE_APP', 'xiaoyu', 1, 'e10adc3949ba59abbe56e057f20f883e'),
	(4, 'ROLE_APP','yopark',2,'086246bffb2de7288946151fc900db59');

SET FOREIGN_KEY_CHECKS=1;
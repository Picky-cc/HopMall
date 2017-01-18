delete from ar_timeline_node;
INSERT INTO `ar_timeline_node` (`id`, `time_line_unique_id`, `project_id`, `create_time`, `serial_no`, `is_deleted`, `timeline_version_uuid`, `data_key`, `write_grant`, `source`, `data`)
VALUES
	(1, 'aaa', '1', '2015-10-21 14:13:36', 0, 00000000, '1', 'a', 'open', 'data1', ''),
	(10, 'aaa', '1', '2015-10-21 14:13:36', 1, 00000000, '1', 'a', 'open', 'data1', ''),
	(2, 'bbb', '1', '2015-10-21 14:13:41', 0, 00000000, '1', 'b', 'open', 'data2', ''),
	(3, 'ddd', '1', '2015-10-21 14:13:36', 0, 00000000, '1', 'd', '1', 'data3', ''),
	(4, 'eee', '1', '2015-10-21 14:13:36', 0, 00000000, '1', 'd', '1', 'data3', '');

delete from ar_working_space;
INSERT INTO `ar_working_space` (`id`, `unique_id`, `record_data_hash_code`, `last_modify_time`, `data`, `timeline_id`, `company_id`, `timeline_version_uuid`)
VALUES
	(1, '10001', 'a', '2015-10-21 11:16:02', 'data1', 'aaa', 2, '1'),
	(2, '10002', 'c', '2015-10-21 11:16:02', 'data1', 'aaa', 3, '1'),
	(3, '10003', 'd', '2015-10-21 11:16:02', 'data3', 'ddd', 3, '1'),
	(4, '10004', 'd', '2015-10-21 11:16:02', 'data3', 'eee', 2, '1');

delete from ar_project;
INSERT INTO `ar_project` (`id`, `owner_company_id`, `unique_id`, `working_schema`, `create_time`, `name`, `contract_no`, `timeline_data`, `partner_app_id`, `status`, `is_delete`)
VALUES
	(1, 2, 'unique_id_1', '', NULL, '', '', '', 15, 0, 00000000);
	
delete from company;
INSERT INTO `company` (`id`, `address`, `full_name`, `short_name`, `bank_name`, `card_no`, `name`) VALUES 
(2, '上海陆家嘴', '鼎程（上海）商业保理有限公司', '鼎程', NULL, NULL, NULL),
(15, 'huoshi', '', '', NULL, NULL, NULL);

delete from app;
INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`) VALUES
(15,'huoshi','',00000000,'','',15);

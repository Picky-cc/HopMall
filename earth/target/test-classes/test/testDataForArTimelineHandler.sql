delete from `ar_project`;
INSERT INTO `ar_project` (`id`, `owner_company_id`, `unique_id`, `working_schema`, `create_time`, `name`, `contract_no`, `timeline_data`, `partner_ship_list`, `status`, `is_delete`)
VALUES
	(1, 2, '10000001', '', '2015-10-27 11:23:29', '测试项目', 'BL00001', '[{\"timeLineUUID\":10001, \"batchNo\":\"10001\", \"timelineStatus\":0, \"createTime\":\"2015-08-10\"},{\"timeLineUUID\":10002,\"batchNo\":\"10002\",  \"timelineStatus\":1, \"createTime\":\"2015-09-10\"},{\"timeLineUUID\":10003, \"batchNo\":\"10003\", \"timelineStatus\":2, \"createTime\":\"2015-10-10\"}]', '3', 0, 00000000);

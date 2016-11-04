INSERT INTO `partical` ( `entry_url`, `partical_name`, `partical_status`, `partical_type`, `unique_partical_id`)
VALUES('http://localhost:9999/quark', 'test_quark', 1, 0, '483b89b9ac532bb271a7ba407304fd22');


INSERT INTO `partical_model` ( `value`, `name`, `unique_partical_id`)
VALUES('/bill-deposite/today-paid-bills', 'getTodayPaidBills', '483b89b9ac532bb271a7ba407304fd22');

INSERT INTO `app_particles` (`app_id`,`is_delete`,`modified_time`,`unique_partical_id`) 
VALUES ('xiaoyu',b'0','2015-04-27 15:00:58','483b89b9ac532bb271a7ba407304fd22');
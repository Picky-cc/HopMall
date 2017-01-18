SET FOREIGN_KEY_CHECKS = 0;

delete from system_log;

insert into `system_log` (`id`, `operate_time`, `user_id`, `ip`, `event`, `content`) values('2','2015-07-02 13:28:28','5','127.0.0.1','登陆','登陆');
insert into `system_log` (`id`, `operate_time`, `user_id`, `ip`, `event`, `content`) values('3','2015-07-02 13:28:59','5','127.0.0.1','退出','退出登陆');

SET FOREIGN_KEY_CHECKS = 1;
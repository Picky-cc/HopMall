SET FOREIGN_KEY_CHECKS=0;

delete from principal;

insert into `principal`(`id`,`authority`,`key_id`,`name`,`password`) values
('1','ROLE_SUPER_USER',null,'root','befd2450f81f88ecc5fbcc4c1f97f0b4'),
('2','ROLE_INSTITUTION','2','dingcheng','df5b59f050d316b72b17f73714473f8b'),
('3','ROLE_APP','1','xiaoyu','e10adc3949ba59abbe56e057f20f883e'),
('4','ROLE_BANK_APP','2','yopark','086246bffb2de7288946151fc900db59'),
('5','ROLE_INSTITUTION','2','DCF001','3cf5eaa7d33e0d023e811c90cd6f2f73'),
('6','ROLE_INSTITUTION','2','DCF002','adb29c63523254a343864e97c84e6360');

SET FOREIGN_KEY_CHECKS=1;
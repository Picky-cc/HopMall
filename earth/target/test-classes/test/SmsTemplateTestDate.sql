SET FOREIGN_KEY_CHECKS=0;
delete from sms_template;
delete from principal;

INSERT INTO `principal` (`id`, `authority`, `name`, `password`) VALUES ('1', 'ROLE_SUPER_USER', 'root', 'befd2450f81f88ecc5fbcc4c1f97f0b4'),
('2', 'ROLE_INSTITUTION', '2', 'dingcheng', 'df5b59f050d316b72b17f73714473f8b'),
('3', 'ROLE_APP', '1', 'xiaoyu', 'e10adc3949ba59abbe56e057f20f883e'),
('4', 'ROLE_BANK_APP', '2', 'yopark', '086246bffb2de7288946151fc900db59'),
('5', 'ROLE_INSTITUTION', '2', 'DCF001', '3cf5eaa7d33e0d023e811c90cd6f2f73'),
('6', 'ROLE_INSTITUTION', '2', 'DCF002', 'adb29c63523254a343864e97c84e6360'),
 ('7', 'ROLE_APP', '5', 'laoA', 'e10adc3949ba59abbe56e057f20f883e'),
('8', 'ROLE_SUPER_USER', NULL, 'guanzhishi', 'befd2450f81f88ecc5fbcc4c1f97f0b4'),
('9', 'ROLE_SUPER_USER', NULL, 'zhangjianming', '14ebdd77cd348da2ee411e118d125b53'),
('10', 'ROLE_SUPER_USER', NULL, 'dongjigong', 'cb8d07590edc38e54bb40e3719acc189'),
 ('11', 'ROLE_SUPER_USER', NULL, 'lishuzhen', 'a82a92061f9ad7a549a843658107141b'),
 ('12', 'ROLE_SUPER_USER', NULL, 'chenjie', '64b2f4c902086b2220afd5b05ad25fb9'),
('13', 'ROLE_APP', '3', 'test4Zufangbao', 'e10adc3949ba59abbe56e057f20f883e'),
 ('14', 'ROLE_SUPER_USER', 'zhushiyun', '2ba9a0c7b7bf6b07846c7468c32552d1'),
 ('15', 'ROLE_SUPER_USER', NULL, 'lixu', 'a82a92061f9ad7a549a843658107141b'),
 ('16', 'ROLE_SUPER_USER', NULL, 'jinyin', '9c74066927e18620a8bc89580f23e8ed'),
 ('17', 'ROLE_APP', '8', 'woju', 'e10adc3949ba59abbe56e057f20f883e'),
 ('18', 'ROLE_SUPER_USER', NULL, 'wukai', 'c4de644efae81ff323fa45b50c31296b'),
('19', 'ROLE_APP', '9', 'meijia', 'e10adc3949ba59abbe56e057f20f883e'),
('20', 'ROLE_BANK_APP', '2', 'YoparkTest', 'befd2450f81f88ecc5fbcc4c1f97f0b4'),
('21', 'ROLE_MERCURY_APP', '1', 'XY001', 'e10adc3949ba59abbe56e057f20f883e'),
('22', 'ROLE_SUPER_USER', NULL, 'meikehuan', 'e10adc3949ba59abbe56e057f20f883e')
,('23', 'ROLE_INSTITUTION', '2', 'fishmei', 'e10adc3949ba59abbe56e057f20f883e');

INSERT INTO `sms_template` (`id`, `content`, `is_deleted`, `modified_time`, `sms_template_type`)
VALUES ('1', '您当前的房租为590', '\0', '2015-08-01 21:24:50', '0'),
	   ('2', '您当前的房租为690', '\0', '2015-08-03 21:24:50', '0');
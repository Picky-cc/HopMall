SET FOREIGN_KEY_CHECKS=0;

delete from principal;

INSERT INTO `principal` (`id`, `authority`, `name`, `password`, `start_date`, `thru_date`)
VALUES
('1', 'ROLE_SUPER_USER', 'root', 'a82a92061f9ad7a549a843658107141b',NULL,NULL),
('2', 'ROLE_INSTITUTION', '2', 'dingcheng', 'df5b59f050d316b72b17f73714473f8b',NULL,NULL),
('3', 'ROLE_APP', '1', 'xiaoyu', 'e10adc3949ba59abbe56e057f20f883e',NULL,NULL),
('4', 'ROLE_APP', '2', 'yopark', '086246bffb2de7288946151fc900db59',NULL,NULL);

SET FOREIGN_KEY_CHECKS=1;
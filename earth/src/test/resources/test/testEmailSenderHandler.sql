SET FOREIGN_KEY_CHECKS=0;

delete  from app;
INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `addressee`, `payee_account_name`, `payee_account_no`, `payee_bank_name`) 
VALUES 
('1', 'xiaoyu', '70991db75ebb24fa0993f4fa25775022', '\0', 'http://beta.demo2do.com/jupiter/', '寓见', '5', 'zhangjianming@zufangbao.com;8520874@qq.com;myounique@sina.com;123@abc.com', NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS=1;
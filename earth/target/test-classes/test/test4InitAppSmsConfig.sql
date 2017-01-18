
-- 线上美家的app sms config配置
INSERT INTO `app_sms_config` (`app_id`, `is_deleted`, `is_need_link`, `sms_template`,`notify_date_array`)
VALUES
	(9,00000000,b'1','%s:%s,您好!您当期房租账单%s元已可支付,请点击链接[%s]【租房宝】','-10000');
delete from `sms_template`;
INSERT INTO `sms_template` (`id`, `code`, `title`, `template`)
VALUES
	(1, 'YX-NFQ-SUCC', '云信-农分期-扣款成功短信', '客户%s于%s经代扣成功还款%s元。');

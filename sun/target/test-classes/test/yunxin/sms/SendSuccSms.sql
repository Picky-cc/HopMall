delete from `sms_quene`;

INSERT INTO `sms_quene` (`id`, `client_id`, `template_code`, `params`, `content`, `platform_code`, `create_time`, `request_time`, `response_time`, `response_txt`, `allowed_send_status`, `sms_send_status`)
VALUES
	(1, 'C73664', 'LOAN_REPAY_FAIL', NULL, '尊敬的测试用户16，您的本月还款账单（还款编号：ZC2735BA683869E604）应于2016年05月18日还款3000.00元，实际于2016年06月07日从尾号3656的银行卡自动还款失败，请及时将足量款项转至该账户。若已逾期将会产生罚息，请以实际扣款金额为准。若已还款，请忽略本条短信。如有疑问，请拨打400-688-0909。', 'nongfenqi', '2016-06-07 12:41:28', NULL, NULL, NULL, 0, 0),
	(2, 'C75110', 'LOAN_REPAY_SUCC', NULL, '尊敬的测试用户18，您的本月还款账单（还款编号：ZC2735BA6838C23F8A）本息合计金额为2419.20元，于2016年06月07日从尾号0000的银行卡还款成功。如有疑问，请拨打400-688-0909。', 'nongfenqi', '2016-06-07 12:41:28', NULL, NULL, NULL, 0, 0);
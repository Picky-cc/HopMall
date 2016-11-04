SET FOREIGN_KEY_CHECKS=0;

delete from `app_arrive_record`;
delete from `app`;

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`)
VALUES
	(1,'xiaoyu','70991db75ebb24fa0993f4fa25775022',00000000,'http://beta.demo2do.com/jupiter/','寓见',5),
	(2,'youpark','123456',00000000,'','优帕克',4),
	(3,'alipayAppId','2e85ae999845f25faf8ea7b514ee0aca',00000000,'http://e.zufangbao.cn','租房宝测试账号',9),
	(4,'zhuke','cb742d55634a532060ac7387caa8d242',00000000,'http://zkroom.com/','杭州驻客网络技术有限公司',6),
	(5,'laoA','744a38b1672b728dc35a68f7a10df082',00000000,'http://www.13980.com','上海元轼信息咨询有限公司',7),
	(6,'keluoba','30f4d225438a7fd1541fe1a055420dfd',00000000,'http://keluoba.com','柯罗芭',8);

	INSERT INTO `app_arrive_record` (`id`, `amount`, `arrive_record_status`, `pay_ac_no`, `receive_ac_no`, `remark`, `serial_no`, `time`, `app_id`, `drcrf`, `pay_name`, `summary`, `vouh_no`, `operate_remark`, `cash_flow_uid`, `detail_data`, `cash_flow_channel_type`, `transaction_type`, `partner_id`)
VALUES
	(4660, 1650.00, 0, '20884023249019440156', '20889112143230040156', '', '20151012072517653350666284500992', '2015-10-12 07:25:30', 3, '2', '', '', '2015101200001000940062595431', NULL, '9d425d23-8f69-4c72-923e-e48f712bbef4', '\"<AccountQueryAccountLogVO><balance>67414.23</balance><buyer_account>20884023249019440156</buyer_account><currency>156</currency><deposit_bank_no>2015101283239589431</deposit_bank_no><goods_title>订单</goods_title><income>1650.00</income><iw_account_log_id>98172826097941</iw_account_log_id><memo> </memo><merchant_out_order_no>20151012072517653350666284500992</merchant_out_order_no><outcome>0.00</outcome><partner_id>2088911214323004</partner_id><rate>0.0125</rate><seller_account>20889112143230040156</seller_account><seller_fullname>杭州随地付网络技术有限公司</seller_fullname><service_fee>0.00</service_fee><service_fee_ratio> </service_fee_ratio><sign_product_name>快捷手机wap支付</sign_product_name><sub_trans_code_msg>快速支付,支付给个人，支付宝帐户全额</sub_trans_code_msg><total_fee>1650.00</total_fee><trade_no>2015101200001000940062595431</trade_no><trade_refund_amount>0.00</trade_refund_amount><trans_code_msg>在线支付</trans_code_msg><trans_date>2015-10-12 07:25:30</trans_date></AccountQueryAccountLogVO>\"', 1, 5, '2088911214323004');

INSERT INTO `app_arrive_record` (`id`, `amount`, `arrive_record_status`, `pay_ac_no`, `receive_ac_no`, `remark`, `serial_no`, `time`, `app_id`, `drcrf`, `pay_name`, `summary`, `vouh_no`, `operate_remark`, `cash_flow_uid`, `detail_data`, `cash_flow_channel_type`, `transaction_type`, `partner_id`)
VALUES
	(4663, 19.38, 0, '20882025628008650156', '20889112143230040156', '服务费[2015101200001000860059999879]', '20151012075715653358723781560320', '2015-10-12 07:58:01', 3, '2', '', '服务费[2015101200001000860059999879]', '2015101200001000860059999879', NULL, '7e5da8a3-29da-4a06-8552-a6f7a2e5c974', '\"<AccountQueryAccountLogVO><balance>68924.22</balance><buyer_account>20882025628008650156</buyer_account><currency>156</currency><deposit_bank_no>2015101268558128668</deposit_bank_no><goods_title>订单</goods_title><income>0.00</income><iw_account_log_id>98372092965000</iw_account_log_id><memo>服务费[2015101200001000860059999879]</memo><merchant_out_order_no>20151012075715653358723781560320</merchant_out_order_no><outcome>19.38</outcome><partner_id>2088911214323004</partner_id><rate>0.0125</rate><seller_account>20889112143230040156</seller_account><seller_fullname>杭州随地付网络技术有限公司</seller_fullname><service_fee>0.00</service_fee><service_fee_ratio> </service_fee_ratio><sign_product_name>快捷手机wap支付</sign_product_name><sub_trans_code_msg>收费</sub_trans_code_msg><total_fee>1550.00</total_fee><trade_no>2015101200001000860059999879</trade_no><trade_refund_amount>0.00</trade_refund_amount><trans_code_msg>收费</trans_code_msg><trans_date>2015-10-12 07:58:01</trans_date></AccountQueryAccountLogVO>\"', 0, 1, '2088911214323004');

	INSERT INTO `app_arrive_record` (`id`, `amount`, `arrive_record_status`, `pay_ac_no`, `receive_ac_no`, `remark`, `serial_no`, `time`, `app_id`, `drcrf`, `pay_name`, `summary`, `vouh_no`, `operate_remark`, `cash_flow_uid`, `detail_data`, `cash_flow_channel_type`, `transaction_type`, `partner_id`)
VALUES
	(4667, 25.00, 0, '', '', '服务费[20151012110070101500000000176283]', '20151012110070101500000000176283', '2015-10-12 09:39:29', 3, '1', '', '服务费[20151012110070101500000000176283]', '', NULL, '91da0d3f-8598-40f0-922f-e0adb8548373', '\"<AccountQueryAccountLogVO><balance>22726.72</balance><buyer_account> </buyer_account><currency>156</currency><deposit_bank_no> </deposit_bank_no><goods_title> </goods_title><income>0.00</income><iw_account_log_id>98372720192000</iw_account_log_id><memo>服务费[20151012110070101500000000176283]</memo><merchant_out_order_no>20151012110070101500000000176283</merchant_out_order_no><outcome>25.00</outcome><partner_id> </partner_id><rate>0.002</rate><seller_account> </seller_account><seller_fullname> </seller_fullname><service_fee>0.00</service_fee><service_fee_ratio> </service_fee_ratio><sign_product_name>企业版转账到银行账户</sign_product_name><sub_trans_code_msg>收费</sub_trans_code_msg><total_fee>0.00</total_fee><trade_no> </trade_no><trade_refund_amount>0.00</trade_refund_amount><trans_code_msg>收费</trans_code_msg><trans_date>2015-10-12 09:39:29</trans_date></AccountQueryAccountLogVO>\"', 1, 1, '');



SET FOREIGN_KEY_CHECKS=1;
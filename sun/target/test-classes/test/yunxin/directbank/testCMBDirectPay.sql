SET FOREIGN_KEY_CHECKS=0;
delete from `account`;
delete from `usb_key`;
delete from `app`;
delete from `company`;
delete from `app_arrive_record`;

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`)
VALUES
	(1, 'xiaoyu', '70991db75ebb24fa0993f4fa25775022', 00000000, 'http://beta.demo2do.com/jupiter/', '寓见', 5),
	(2, 'youpark', '123456', 00000000, '', '优帕克', 4),
	(3, 'test4Zufangbao', '2e85ae999845f25faf8ea7b514ee0aca', 00000000, 'http://e.zufangbao.cn', '租房宝测试账号', 3),
	(4, 'zhuke', 'cb742d55634a532060ac7387caa8d242', 00000000, 'http://zkroom.com/', '杭州驻客网络技术有限公司', 6),
	(5, 'laoA', '744a38b1672b728dc35a68f7a10df082', 00000000, 'http://www.13980.com', '上海元轼信息咨询有限公司', 7),
	(6, 'keluoba', '30f4d225438a7fd1541fe1a055420dfd', 00000000, 'http://keluoba.com', '柯罗芭', 8),
	(7, 'testForXiaoyu', '2138ed4b66cebbded5753f3c59a064ae', 00000000, 'http://xxx.com', '小寓测试帐号', 10),
	(8, 'woju', '495d49ae55fd794036576aa8f71dbb43', 00000000, 'http://www.woju.com', '杭州蜗居', 11),
	(9, 'meijia', '1bf40057e15fd462773c13e0a85e9676', 00000000, 'http://99196.hotel.cthy.com/', '美家公寓', 12),
	(10, 'anxin', 'd1cd2618432723c478fab102bdfa2e11', 00000000, 'http://anxin.com', '安心公寓', 13),
	(11, 'yuanlai', 'eaaf6bfe5c98e042822b60cae955a276', 00000000, 'http://yuanlai.com', '源涞国际', 14),
	(12, 'hanwei', 'caedfdce4d3e6b6b1fe0cc01599b0123', 00000000, 'http://www.hanwei.com', '汉维仓储', 15);


INSERT INTO `account` (`id`, `account_name`, `account_no`, `alias`, `attr`,`usb_key_configured`,`scan_cash_flow_switch`)
VALUES
	(1, '鼎程（上海）商业保理有限公司', '11014671112002', '鼎程回款户', NULL,1,1),
	(2, '鼎程（上海）商业保理有限公司', '1001262119204647489', '鼎程－优帕克风险准备金户', NULL,1,1),
	(3, '上海优帕克投资管理有限公司', '1001300219000027827','优帕克回款户', NULL,1,1),
	(4, '小寓科技', '', NULL, NULL,1,1),
	(5, '杭州随地付网络技术有限公司', '1001262119204646188','随地付', NULL,1,1),
	(6, '鼎程 (上海) 商业保理有限公司', '1001184219000050139','鼎程－寓见风险准备金户', NULL,1,1),
	(7, '杭州随地付网络技术有限公司', 'zfbdg@zufangbao.com', '随地付', NULL,1,1),
	(8, '罗仙林', '6222081202010524989', NULL, NULL,1,1),
	(10, '王晓娣', '6214830285912142', NULL, NULL,1,1),
	(11, '杭州随地付网络技术有限公司', '1202021519900680156', '随地付', NULL,1,1),
	(12, '杭州蜗居网络技术有限公司', '19025101040024439', NULL, NULL,1,1),
	(13, '寓见资产管理（上海）有限公司', 'slin@yuapt.com',NULL, NULL,1,1),
	(14, '上海锐诩酒店管理有限公司', '121912306510801','安心公寓回款户', NULL,1,1),
	(15, '杭州随地付网络技术有限公司', '571907757810703', '随地付', NULL,1,1),
	(16, '鼎程（上海）商业保理有限公司', '121916729010301', '鼎程保证金户', NULL,1,1),
	(17, '鼎程（上海）商业保理有限公司', '121916729010102',  '鼎程回款户', '{\"CRTSQN\":\"CMB0000005\",\"CRTBNK\":\"招商银行上海联洋支行\",\"CRTPVC\":\"上海市\",\"CRTCTY\":\"浦东新区\",\"bankBranchNo\":\"21\"}',1,1),
	(18, '上海源涞实业有限公司', '121913751710201', '源涞国际回款户', NULL,1,1),
	(19, '上海柯罗芭服饰有限公司', '1001215509300557671', '柯罗芭回款户', NULL,1,1),
	(20, '寓见资产管理（上海）有限公司', '31001613402050023693', NULL, NULL,1,1),
	(21, '北京汉维百利威仓储服务有限公司', '121917799510201', '北京汉维回款户', NULL,1,1),
	(22, '鼎程（上海）商业保理有限公司', '121916729010301000003','汉维保证金户', NULL,1,1);
	
	
INSERT INTO `usb_key` (`id`, `bank_code`, `config`, `key_type`, `uuid`)
VALUES
	(1, 'CMB', '{\"LGNNAM\":\"管知时suidifu\",\"URL\":\"http://cmb.zufangbao.cn:25662\",\"GetTransInfo_Code\":\"GetTransInfo\",\"GetAccInfo_Code\":\"GetAccInfo\",\"GetPaymentInfo_Code\":\"GetPaymentInfo\",\"DCPAYMNT_Code\":\"DCPAYMNT\",\"BUSCOD\":\"N02031\",\"BUSMOD\":\"00002\",\"PAYMENTLOCKED\":false}', 0, '44bcf65b-c138-48f4-afd5-929bbdd979ee'),
	(2, 'CMB', '{\"LGNNAM\":\"DCFC004\",\"URL\":\"http://cmb2.zufangbao.cn:25663\",\"GetTransInfo_Code\":\"GetTransInfo\",\"GetAccInfo_Code\":\"GetAccInfo\",\"GetPaymentInfo_Code\":\"GetPaymentInfo\",\"DCPAYMNT_Code\":\"DCPAYMNT\",\"BUSCOD\":\"N02031\",\"BUSMOD\":\"00001\",\"PAYMENTLOCKED\":true}', 0, '23988f31-f583-4afa-b866-a46abeafd8b0'),
	(3, 'ICBC', NULL, 1, '0baa43b3-6f77-46ac-81ab-2eb051cd7e76'),
	(4, 'PAB', NULL, 0, 'eb1f22e0-ba46-4719-9d4e-04d51e22c10e');


INSERT INTO `company` (`id`, `address`, `full_name`, `short_name`)
VALUES
	(2, '上海陆家嘴', '鼎程（上海）商业保理有限公司', '鼎程'),
	(3, '杭州万塘路8号', '杭州随地付网络技术有限公司', '租房宝'),
	(4, '上海', '上海优帕克投资管理有限公司', '优帕克'),
	(5, '上海', '小寓科技', '小寓'),
	(6, '上海', '杭州驻客网络技术有限公司', '驻客'),
	(7, '上海', '上海元轼信息咨询有限公司', '老A'),
	(8, '上海', '柯罗芭', '柯罗芭'),
	(9, '杭州', '租房宝测试', '租房宝测试'),
	(10, NULL, '小寓测试帐号', '小寓测试帐号'),
	(11, NULL, '杭州蜗居', '杭州蜗居'),
	(12, NULL, '美家公寓', '美家公寓'),
	(13, NULL, '安心公寓', '安心公寓'),
	(14, NULL, '源涞国际', '源涞国际'),
	(15, NULL, '汉维仓储', '汉维仓储');

	
INSERT INTO `app_arrive_record` (`id`, `amount`, `arrive_record_status`, `pay_ac_no`, `receive_ac_no`, `remark`, `serial_no`, `time`, `app_id`, `drcrf`, `pay_name`, `summary`, `vouh_no`, `cash_flow_uid`, `detail_data`, `cash_flow_channel_type`, `transaction_type`, `partner_id`)
VALUES
	(5058, 2000.00, 0, '6212261001039371007', '121913751710201', NULL, 'G5073900001144C', '2015-10-25 12:24:10', 11, '2', '卢亮', '提回贷记:00010375220,汇兑', NULL, NULL, NULL, 0, 0, NULL),
	(5059, 100000.00, 0, '121913751710201', '121913751710903', NULL, 'G1088700003653C', '2015-10-25 18:13:35', 11, '1', '上海源涞实业有限公司', '划款', '20151023193918', NULL, NULL, 0, 0, NULL),
	(5060, 21000.00, 0, '121913751710201', '1001103309000062348', NULL, 'G1088700003671C', '2015-10-25 18:20:35', 11, '1', '上海源涞实业有限公司', '退押金', '20151022142006', NULL, NULL, 0, 0, NULL);

SET FOREIGN_KEY_CHECKS=1;

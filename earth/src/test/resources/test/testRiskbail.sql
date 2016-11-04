delete from charge;

INSERT INTO `charge` (`id`, `factoring_contract_id`, `finance_payment_record_id`, `pay_money`, `payment_time`, `remark`, `batch_no`, `charge_status`, `modify_time`)
VALUES
	(308, 58, NULL, 1000.00, '2015-08-15 12:20:33', 'test2', '', 3, '2015-08-15 12:20:33'),
	(307, 58, NULL, 1000.00, '2015-08-13 15:26:37', 'test1', '', 3, '2015-08-13 15:26:37'),
	(306, 58, NULL, 1000.00, '2015-08-13 15:14:20', 'test', '', 3, '2015-08-13 15:14:20'),
	(269, 58, 536, -115918.39, '2015-08-10 15:17:00', '释放风险准备金作为JAFJ9#22A回购', NULL, 3, '2015-08-10 15:17:00'),
	(268, 58, 525, 15500.00, '2015-08-07 15:11:20', 'SHIMAO2#53I-20150806-3', 'SHIMAO2#53I-20150806-3', 2, '2015-08-07 15:11:20'),
	(267, 58, 524, 13900.00, '2015-08-07 15:10:52', 'DSHY7#502-20150806-1', 'DSHY7#502-20150806-1', 2, '2015-08-07 15:10:52'),
	(266, 58, 523, 14400.00, '2015-08-07 15:03:39', 'HHTX5#704-20150806-3', 'HHTX5#704-20150806-3', 2, '2015-08-07 15:03:39'),
	(265, 58, 522, 33500.00, '2015-08-07 15:02:39', 'WXL252#2-20150806-3', 'WXL252#2-20150806-3', 2, '2015-08-07 15:02:39'),
	(264, 58, 521, 12200.00, '2015-08-07 14:57:19', 'TSHT6#1002-20150806-1', 'TSHT6#1002-20150806-1', 2, '2015-08-07 14:57:19'),
	(263, 58, 520, 11500.00, '2015-08-07 14:56:14', 'TAHY18#901-20150806-3', 'TAHY18#901-20150806-3', 2, '2015-08-07 14:56:14'),
	(262, 58, 519, 32000.00, '2015-08-07 14:54:58', 'SHIMAO6#47E-20150806-2', 'SHIMAO6#47E-20150806-2', 2, '2015-08-07 14:54:58'),
	(261, 58, 518, 17500.00, '2015-08-07 14:54:12', 'SHIMAO3#46I-20150806-2', 'SHIMAO3#46I-20150806-2', 2, '2015-08-07 14:54:12'),
	(260, 58, 517, 20000.00, '2015-08-07 14:53:01', 'JLHT5#3808-20150806-2', 'JLHT5#3808-20150806-2', 2, '2015-08-07 14:53:01'),
	(259, 61, 496, 1200.00, '2015-08-05 16:38:54', 'CC-61646-3', 'CC-61646-3', 2, '2015-08-05 16:38:54'),
	(258, 61, 495, 1650.00, '2015-08-05 16:38:54', 'CC-61604-3', 'CC-61604-3', 2, '2015-08-05 16:38:54'),
	(257, 61, 494, 1600.00, '2015-08-05 16:38:54', 'CC-61597-3', 'CC-61597-3', 2, '2015-08-05 16:38:54'),
	(256, 61, 493, 1200.00, '2015-08-05 16:38:54', 'CC-61592-3', 'CC-61592-3', 2, '2015-08-05 16:38:54'),
	(255, 61, 492, 1000.00, '2015-08-05 16:38:54', 'CC-61582-3', 'CC-61582-3', 2, '2015-08-05 16:38:54'),
	(254, 61, 491, 1500.00, '2015-08-05 16:38:54', 'CC-61566-3', 'CC-61566-3', 2, '2015-08-05 16:38:54'),
	(253, 61, 490, 1900.00, '2015-08-05 16:38:54', 'CC-61565-3', 'CC-61565-3', 2, '2015-08-05 16:38:54');

SET FOREIGN_KEY_CHECKS=0;

delete from rent_order;

INSERT INTO `rent_order`(
  `id` ,
  `due_date` ,
  `end_date` ,
  `late_fee` ,
  `order_no` ,
  `order_status` ,
  `paid_rent` ,
  `payout_time` ,
  `is_settled` ,
  `start_date` ,
  `total_rent` ,
  `contract_id`,
  `customer_id`,
  `repayment_type`) VALUES 
('107', '2015-04-01', null, null, 'KXHY1#1803-20150402-1', '3', '16500.00', '2015-04-03 16:45:24', '\0', null, '16500.00', '13', '106', '1'),
('108', '2015-05-01', null, null, 'KXHY1#1803-20150402-2', '4', null, '2015-05-13 15:17:34', '\0', null, '16500.00', '13', '106', '0'),
('109', '2015-06-01', null, null, 'KXHY1#1803-20150402-3', '0', null, null, '\0', null, '16500.00', '13', '106', '0'),
('110', '2015-07-01', null, null, 'KXHY1#1803-20150402-4', '3', null, null, '\0', null, '16500.00', '13', '106', '0'),
('111', '2015-08-01', null, null, 'KXHY1#1803-20150402-5', '3', null, null, '\0', null, '16500.00', '13', '106', '0'),
('112', '2015-09-01', null, null, 'KXHY1#1803-20150402-6', '0', null, null, '\0', null, '16500.00', '13', '106', '0'),
('113', '2015-10-01', null, null, 'KXHY1#1803-20150402-7', '0', null, null, '\0', null, '16500.00', '13', '106', '0'),
('114', '2015-11-01', null, null, 'KXHY1#1803-20150402-8', '0', null, null, '\0', null, '16500.00', '13', '106', '0');

SET FOREIGN_KEY_CHECKS=1;
SET FOREIGN_KEY_CHECKS=0;

delete from customer;
delete from house;
delete from rent_order;
delete from contract;
delete from app;

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `payee_account_name`, `payee_account_no`, `payee_bank_name`) VALUES
(1, 'xiaoyu', '70991db75ebb24fa0993f4fa25775022', b'0', 'http://beta.demo2do.com/jupiter/', '寓见', 5, NULL, NULL, NULL);

INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`, `user_upload_param`, `unique_particle_id`, `transfer_status`, `modify_time`, `unique_bill_id`,`contract_payment_amount`,`contract_payment_type`) VALUES
(1, '2015-04-01', NULL, NULL, '111111-2', 4, '16500.00', '2015-04-03 16:45:24', b'0', NULL, '16500.00', 13, 106, 1, NULL, NULL, 0, NULL, NULL,NULL,NULL);

INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`, `transfer_status`) VALUES
(13, '2015-04-01', 'KXHY1#1803', 0, '33000.00', '2016-03-31', NULL, '', '16500.00', b'0', 0, NULL, NULL, 2, 106, 13, 0);

SET FOREIGN_KEY_CHECKS=1;
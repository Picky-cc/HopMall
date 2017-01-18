SET FOREIGN_KEY_CHECKS=0;

delete from transfer_application;
delete from rent_order;
delete from customer;
delete from journal_voucher;


INSERT INTO `transfer_application` (`id`, `transfer_usage_type`, `src_bill_id`, `factoring_contract_id`, `execute_type`, `funding_src_type`, `trigger_type`, `amount`, `payer_account_id`, `receive_account_id`, `create_time`, `creator_id`, `comment`, `status`, `batch_pay_record_id`, `last_modified_time`, `payment_institution_id`)
VALUES
	('6', '2', 'xiaoyu_repayment_bill_id_1', '61', '0', '0', '0', '1600.00', '7','1', NULL, NULL, NULL, '2','474', '2016-03-19 17:24:15', '2'),
	('7', '2', 'xiaoyu_repayment_bill_id_2', '61', '0', '0', '0', '0.02', '7', '1', NULL, NULL, NULL, '2', '474', '2016-03-19 17:24:15', '2');

	INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`, `user_upload_param`, `unique_particle_id`, `transfer_status`, `modify_time`, `unique_bill_id`, `contract_payment_amount`, `contract_payment_type`, `doubt_record`, `virtual_account_unique_id`, `bill_life_cycle`, `collection_bill_life_cycle`, `collection_bill_capital_status`, `asset_package_id`, `collection_bill_service_notice_status`, `collection_bill_frozen_status`, `factoring_contract_id`, `audit_bill_id`, `repayment_audit_status`, `repayment_status`, `repayment_bill_id`) 
	VALUES 
	('373', '2015-04-05', '2015-05-04', NULL, 'CC-60334-2', '0', '0.00', NULL, '\0', '2015-04-05', '1600.00', '79', '140', '0', NULL, '9dfca839-5d8e-419d-8e9c-50fad3683b9e', '1', NULL, 'xiaoyu_CC-60334-2_373', NULL, NULL, NULL, 'ed8cb46d-525b-47e4-abae-419bae28dc61', '1', '1', '4', '-1', '1', '0', '-1', 'xiaoyu_CC-61851-3_7236', '0', '0', 'xiaoyu_repayment_bill_id_1'),
    ('374', '2015-05-05', '2015-06-04', NULL, 'CC-60334-3', '0', '0.00', NULL, '\0', '2015-05-05', '1600.00', '79', '140', '0', NULL, '9dfca839-5d8e-419d-8e9c-50fad3683b9e', '1', NULL, 'xiaoyu_CC-60334-3_374', NULL, NULL, NULL, 'ed8cb46d-525b-47e4-abae-419bae28dc61', '1', '1', '4', '-1', '1', '0', '-1', 'xiaoyu_CC-61851-3_7236', '0', '0', 'xiaoyu_repayment_bill_id_2');

    

    INSERT INTO `customer` (`id`, `account`, `city`, `mobile`, `name`, `province`, `source`, `app_id`) 
    VALUES 
    ('140', NULL, NULL, NULL, '李佳欣', NULL, '寓见', '1');

SET FOREIGN_KEY_CHECKS=1;
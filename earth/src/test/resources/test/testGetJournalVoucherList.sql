SET FOREIGN_KEY_CHECKS=0;

delete from transfer_application;
delete from rent_order;
delete from customer;
delete from journal_voucher;


INSERT INTO `transfer_application` (`id`, `transfer_usage_type`, `src_bill_id`, `factoring_contract_id`, `execute_type`, `funding_src_type`, `trigger_type`, `amount`, `payer_account_id`, `receive_account_id`, `create_time`, `creator_id`, `comment`, `status`, `batch_pay_record_id`, `last_modified_time`, `payment_institution_id`)
VALUES
	('6', '2', 'xiaoyu_repayment_bill_id_1', '61', '0', '0', '0', '0.02', '7','1', NULL, NULL, NULL, '2','474', '2016-03-19 17:24:15', '2'),
	('7', '2', 'xiaoyu_repayment_bill_id_2', '61', '0', '0', '0', '0.02', '7', '1', NULL, NULL, NULL, '2', '474', '2016-03-19 17:24:15', '2');

	INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`, `user_upload_param`, `unique_particle_id`, `transfer_status`, `modify_time`, `unique_bill_id`, `contract_payment_amount`, `contract_payment_type`, `doubt_record`, `virtual_account_unique_id`, `bill_life_cycle`, `collection_bill_life_cycle`, `collection_bill_capital_status`, `asset_package_id`, `collection_bill_service_notice_status`, `collection_bill_frozen_status`, `factoring_contract_id`, `audit_bill_id`, `repayment_audit_status`, `repayment_status`, `repayment_bill_id`) 
	VALUES 
	('373', '2015-04-05', '2015-05-04', NULL, 'CC-60334-2', '0', '0.00', NULL, '\0', '2015-04-05', '1600.00', '79', '140', '0', NULL, '9dfca839-5d8e-419d-8e9c-50fad3683b9e', '1', NULL, 'xiaoyu_CC-60334-2_373', NULL, '1', NULL, 'ed8cb46d-525b-47e4-abae-419bae28dc61', '1', '1', '4', '-1', '1', '0', '-1', 'xiaoyu_CC-61851-3_7236', '0', '0', 'xiaoyu_repayment_bill_id_1'),
    ('374', '2015-05-05', '2015-06-04', NULL, 'CC-60334-3', '0', '0.00', NULL, '\0', '2015-05-05', '1600.00', '79', '140', '0', NULL, '9dfca839-5d8e-419d-8e9c-50fad3683b9e', '1', NULL, 'xiaoyu_CC-60334-3_374', NULL, '1', NULL, 'ed8cb46d-525b-47e4-abae-419bae28dc61', '1', '1', '4', '-1', '1', '0', '-1', 'xiaoyu_CC-61851-3_7236', '0', '0', 'xiaoyu_repayment_bill_id_2');

    
    INSERT INTO `journal_voucher` (`id`, `account_side`, `bank_identity`, `billing_plan_uuid`, `booking_amount`, `business_voucher_type_uuid`, `business_voucher_uuid`, `cash_flow_amount`, `cash_flow_breif`, `cash_flow_channel_type`, `cash_flow_serial_no`, `cash_flow_uuid`, `checking_level`, `company_id`, `completeness`, `counter_party_account`, `counter_party_name`, `journal_voucher_uuid`, `notification_identity`, `notification_memo`, `notification_record_uuid`, `notified_date`, `settlement_modes`, `source_document_amount`, `source_document_breif`, `source_document_cash_flow_serial_no`, `source_document_counter_party_uuid`, `source_document_identity`, `source_document_uuid`, `status`, `trade_time`, `virtual_account_uuid`, `created_date`)
    VALUES 
    ('2', '1', '2088911214323004', 'xiaoyu_CC-61851-3_7236', '1600.00', 'ef8d1fbd-3bcc-42a1-b021-912133d76744', '67251e65-b28a-4000-91ec-c545f569ccea', '4800.00', '', '1', '2015110200001000560062361524', '335c8379-35ac-4a9e-88e3-101110494bb0', '1', '5', '3', NULL, NULL, '6ebaaf8413984394bdd9f873a37f6097', '20151102101957661185981376301056', '', 'ddd80c1336e545599706d37b4ca2ce58', '2015-11-02 22:21:08', NULL, '4800.00', NULL, '2015110200001000560062361524', NULL, '20151102101957661185981376301056', NULL, '1', '2015-11-02 22:21:08', NULL, NULL);

    INSERT INTO `customer` (`id`, `account`, `city`, `mobile`, `name`, `province`, `source`, `app_id`) 
    VALUES 
    ('140', NULL, NULL, NULL, '李佳欣', NULL, '寓见', '1');

SET FOREIGN_KEY_CHECKS=1;

set @appId = 9;

/* ---  1 */

set @contractNo = 'ct-0052';

set @houseId := (select contract.`house_id` from contract where contract.`contract_no` = @contractNo and contract.app_id = @appId);

set @customerId := (select contract.`customer_id` from contract where contract.`contract_no` = @contractNo and contract.app_id = @appId);

set @contractId := (select contract.`id` from contract where contract.`contract_no` = @contractNo and contract.app_id = @appId);

set foreign_key_checks = 0;

delete from house  where house.`id` =  @houseId;

delete from `contract_partical` where contract_partical.`contract_id` = @contractId;

delete from customer where customer.`id` = @customerId;
 
delete from rent_order where rent_order.`contract_id` = @contractId;

delete from contract where contract.`id`  = @contractId;

set foreign_key_checks = 1;

/* --- 2 */

set @contractNo = 'ct-0058';

set @houseId := (select contract.`house_id` from contract where contract.`contract_no` = @contractNo and contract.app_id = @appId);

set @customerId := (select contract.`customer_id` from contract where contract.`contract_no` = @contractNo and contract.app_id = @appId);

set @contractId := (select contract.`id` from contract where contract.`contract_no` = @contractNo and contract.app_id = @appId);

set foreign_key_checks = 0;

delete from house  where house.`id` =  @houseId;

delete from `contract_partical` where contract_partical.`contract_id` = @contractId;

delete from customer where customer.`id` = @customerId;
 
delete from rent_order where rent_order.`contract_id` = @contractId;

delete from contract where contract.`id`  = @contractId;

set foreign_key_checks = 1;

/* --- 3 */

set @contractNo = 'ct-0307';

set @houseId := (select contract.`house_id` from contract where contract.`contract_no` = @contractNo and contract.app_id = @appId);

set @customerId := (select contract.`customer_id` from contract where contract.`contract_no` = @contractNo and contract.app_id = @appId);

set @contractId := (select contract.`id` from contract where contract.`contract_no` = @contractNo and contract.app_id = @appId);

set foreign_key_checks = 0;

delete from house  where house.`id` =  @houseId;

delete from `contract_partical` where contract_partical.`contract_id` = @contractId;

delete from customer where customer.`id` = @customerId;
 
delete from rent_order where rent_order.`contract_id` = @contractId;

delete from contract where contract.`id`  = @contractId;

set foreign_key_checks = 1;

/* ---4 */

set @contractNo = 'ct-0105';

set @houseId := (select contract.`house_id` from contract where contract.`contract_no` = @contractNo and contract.app_id = @appId);

set @customerId := (select contract.`customer_id` from contract where contract.`contract_no` = @contractNo and contract.app_id = @appId);

set @contractId := (select contract.`id` from contract where contract.`contract_no` = @contractNo and contract.app_id = @appId);

set foreign_key_checks = 0;

delete from house  where house.`id` =  @houseId;

delete from `contract_partical` where contract_partical.`contract_id` = @contractId;

delete from customer where customer.`id` = @customerId;
 
delete from rent_order where rent_order.`contract_id` = @contractId;

delete from contract where contract.`id`  = @contractId;

set foreign_key_checks = 1;

/* ---5 */

set @contractNo = 'ct-0323';

set @houseId := (select contract.`house_id` from contract where contract.`contract_no` = @contractNo and contract.app_id = @appId);

set @customerId := (select contract.`customer_id` from contract where contract.`contract_no` = @contractNo and contract.app_id = @appId);

set @contractId := (select contract.`id` from contract where contract.`contract_no` = @contractNo and contract.app_id = @appId);

set foreign_key_checks = 0;

delete from house  where house.`id` =  @houseId;

delete from `contract_partical` where contract_partical.`contract_id` = @contractId;

delete from customer where customer.`id` = @customerId;
 
delete from rent_order where rent_order.`contract_id` = @contractId;

delete from contract where contract.`id`  = @contractId;

set foreign_key_checks = 1;
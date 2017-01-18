-- 清理驻客数据

set @appId = 4;

/* ---  1 */

set @contractNo = 'ZKCGY012411A';

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

/* ---  2  ZKCGY012318A*/

set foreign_key_checks = 0;

delete from rent_order where rent_order.`contract_id` in (468,838,839,840,848);

delete from customer where customer.`id` in (528,898,899,900,908);

delete from house where house.`id` in (470,840,841,842,850);

delete from contract where contract.id in (468,838,839,840,848);

set foreign_key_checks = 1;

/* ---  3 */

set @appId = 4;

set @contractNo = 'ZKCGY012117A';

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


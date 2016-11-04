-- 33

set @appId = 'meijia';

set @dueDate = '2015-08-01';

select g_contract.`contract_no` as '租约号',g_order.`order_no` as '订单号',g_order.`total_rent` as '金额',g_customer.`name` as '租客名' from quark.bill q_bill inner join galaxy.rent_order g_order on q_bill.`out_lier_order_no` = g_order.`order_no` inner join `galaxy`.contract g_contract on g_contract.`id` = g_order.`contract_id` inner join galaxy.customer g_customer on g_contract.`customer_id` = g_customer.`id` where q_bill.bill_status = 2 and q_bill.`app_id` = @appId AND q_bill.paid_amount > 1 and is_deleted = false and g_order.`due_date`  < @dueDate;


-- 36
set @appId = 9;

set @dueDate = '2015-08-01';

select g_contract.`contract_no` as '租约号',g_order.`order_no` as '订单号',g_order.`total_rent` as '金额',g_customer.`name` as '租客名',g_order.`order_status` 

from galaxy.rent_order g_order inner join `galaxy`.contract g_contract on g_contract.`id` = g_order.`contract_id` inner join galaxy.customer g_customer on g_contract.`customer_id` = g_customer.`id` where  g_contract.`app_id` = @appId AND g_order.`total_rent`> 1  and g_order.`due_date`  < @dueDate and (g_order.`order_status` = 2 or  g_order.`order_status` = 5 or g_order.`order_status` = 4);
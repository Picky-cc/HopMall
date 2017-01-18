select sum(g_order.`total_rent`) as '金额' from rent_order as g_order inner join contract g_contract on g_contract.`id` = 

g_order.`contract_id` where g_contract.`app_id` = 8 and (g_order.`order_status` = 2 or  g_order.`order_status` = 5 or 

g_order.`order_status` = 4);
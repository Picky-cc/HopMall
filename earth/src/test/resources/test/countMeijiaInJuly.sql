select 

q_transaction.`out_lier_order_no` as '订单号',

q_transaction.`pay_money` as '支付金额',


q_transaction.`trade_no` AS '银联交易流水号'
-- sum(`pay_money`) 

from quark.transaction_record q_transaction inner join galaxy.rent_order g_order on q_transaction.`out_lier_order_no` = g_order.`order_no` where q_transaction.app_id = 'meijia' and 

q_transaction.last_modified_time > '2015-07-27' and 

q_transaction.last_modified_time  < '2015-07-28' and (q_transaction.transaction_record_status = 2 ) and q_transaction.pay_money > 1;
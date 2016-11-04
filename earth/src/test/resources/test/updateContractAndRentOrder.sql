-- 设置订单足额
update `rent_order` set `rent_order`.payment_status = 0 where `rent_order`.total_rent = `rent_order`.paid_rent;
-- 设置订单不足额
update `rent_order` set `rent_order`.payment_status = 1 where `rent_order`.total_rent > `rent_order`.paid_rent;
-- 设置订单超额
update `rent_order` set `rent_order`.payment_status = 2 where `rent_order`.total_rent < `rent_order`.paid_rent;
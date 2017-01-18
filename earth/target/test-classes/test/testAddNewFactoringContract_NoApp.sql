SET FOREIGN_KEY_CHECKS=0;

delete from app;
delete from payment_channel;
delete from company;





INSERT INTO
 `payment_channel` (`id`, `channel_name`, `user_name`, `user_password`, `merchant_id`, `cer_file_path`, `pfx_file_path`, `pfx_file_key`, `payment_channel_type`, `api_url`, `from_date`, `thru_date`) 
 VALUES 
 ('1', '广东银联', 'user1', '123456', '123', NULL, NULL, NULL, '1', NULL, NULL, NULL);


SET FOREIGN_KEY_CHECKS=1;
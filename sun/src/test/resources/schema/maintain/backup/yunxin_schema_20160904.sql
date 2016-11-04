SET FOREIGN_KEY_CHECKS=0;

ALTER TABLE transfer_application ADD COLUMN `deduct_send_time` datetime DEFAULT NULL COMMENT '扣款发起时间';

UPDATE transfer_application SET deduct_send_time = create_time WHERE deduct_send_time IS NULL;

SET FOREIGN_KEY_CHECKS=1;
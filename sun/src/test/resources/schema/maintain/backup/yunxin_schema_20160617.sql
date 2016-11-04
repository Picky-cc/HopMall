SET FOREIGN_KEY_CHECKS=0;

-- 2016.06.17
DROP TABLE IF EXISTS `usb_key_account_relation`;

CREATE TABLE `usb_key_account_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_uuid` varchar(255) DEFAULT NULL,
  `usb_key_uuid` varchar(255) DEFAULT NULL,
  `gate_way_type` int(11) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


alter table `account`
add column `uuid` varchar(255) COLLATE utf8_unicode_ci  DEFAULT NULL;

-- 2016.06.17

SET FOREIGN_KEY_CHECKS=1;

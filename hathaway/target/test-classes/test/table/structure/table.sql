CREATE TABLE `job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `excuting_result` int(11) DEFAULT NULL,
  `excuting_status` int(11) DEFAULT NULL,
  `fifth_stage_current_task_expired_time` datetime DEFAULT NULL,
  `fifth_stage_excuting_result` int(11) DEFAULT NULL,
  `fifth_stage_excuting_status` int(11) DEFAULT NULL,
  `fifth_stage_retry_times` int(11) NOT NULL,
  `fifth_stage_uuid` varchar(255) DEFAULT NULL,
  `fifth_stagecreate_time` datetime DEFAULT NULL,
  `fifth_stagelast_modified_time` datetime DEFAULT NULL,
  `fourth_stage_current_task_expired_time` datetime DEFAULT NULL,
  `fourth_stage_excuting_result` int(11) DEFAULT NULL,
  `fourth_stage_excuting_status` int(11) DEFAULT NULL,
  `fourth_stage_retry_times` int(11) NOT NULL,
  `fourth_stage_uuid` varchar(255) DEFAULT NULL,
  `fourth_stagecreate_time` datetime DEFAULT NULL,
  `fourth_stagelast_modified_time` datetime DEFAULT NULL,
  `fst_business_code` varchar(255) DEFAULT NULL,
  `fst_stage_current_task_expired_time` datetime DEFAULT NULL,
  `fst_stage_excuting_result` int(11) DEFAULT NULL,
  `fst_stage_excuting_status` int(11) DEFAULT NULL,
  `fst_stage_retry_times` int(11) NOT NULL,
  `fst_stage_uuid` varchar(255) DEFAULT NULL,
  `fst_stagecreate_time` datetime DEFAULT NULL,
  `fst_stagelast_modified_time` datetime DEFAULT NULL,
  `issuer_i_p` varchar(255) DEFAULT NULL,
  `issuer_identity` varchar(255) DEFAULT NULL,
  `job_name` varchar(255) DEFAULT NULL,
  `job_type` int(11) DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  `snd_business_code` varchar(255) DEFAULT NULL,
  `snd_stage_current_task_expired_time` datetime DEFAULT NULL,
  `snd_stage_excuting_result` int(11) DEFAULT NULL,
  `snd_stage_excuting_status` int(11) DEFAULT NULL,
  `snd_stage_retry_times` int(11) NOT NULL,
  `snd_stage_uuid` varchar(255) DEFAULT NULL,
  `snd_stagecreate_time` datetime DEFAULT NULL,
  `snd_stagelast_modified_time` datetime DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `trd_business_code` varchar(255) DEFAULT NULL,
  `trd_stage_current_task_expired_time` datetime DEFAULT NULL,
  `trd_stage_excuting_result` int(11) DEFAULT NULL,
  `trd_stage_excuting_status` int(11) DEFAULT NULL,
  `trd_stage_retry_times` int(11) NOT NULL,
  `trd_stage_uuid` varchar(255) DEFAULT NULL,
  `trd_stagecreate_time` datetime DEFAULT NULL,
  `trd_stagelast_modified_time` datetime DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
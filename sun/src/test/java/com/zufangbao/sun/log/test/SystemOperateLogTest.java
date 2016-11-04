package com.zufangbao.sun.log.test;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zufangbao.sun.entity.financial.TransferApplication;
import com.zufangbao.sun.service.TransferApplicationService;
import com.zufangbao.sun.yunxin.log.LogFunctionType;
import com.zufangbao.sun.yunxin.log.LogOperateType;
import com.zufangbao.sun.yunxin.log.RecordLogCore;
import com.zufangbao.sun.yunxin.log.SystemOperateLog;
import com.zufangbao.sun.yunxin.service.SystemOperateLogService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
@Transactional()
public class SystemOperateLogTest {

	@Autowired
	private RecordLogCore recordLog;
	@Autowired
	private SystemOperateLogService systemOperateLogService;
	@Autowired 
	private TransferApplicationService transferApplicationService;

	@Test
	@Sql("classpath:test/yunxin/testRecordLogTest.sql")
	public void testRecordLogTest() {

		TransferApplication transferApplication = null;
		SystemOperateLog log = new SystemOperateLog();
		try {
			transferApplication = transferApplicationService.load(
					TransferApplication.class, 1l);
			log = recordLog.insertNormalRecordLog(1l, "192.168.1.1",
					LogFunctionType.ASSETPACKAGEIMPORT,LogOperateType.ADD,transferApplication);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.setKeyContent(transferApplication.getTransferApplicationNo());
		log.setRecordContent("新增" + "线上支付单" + log.getKeyContent());
		systemOperateLogService.saveOrUpdate(log);
		System.out.println(log);
	}
	
	
	/*@Test
	@Sql("classpath:test/yunxin/testUpdateRecordLog.sql")
	public void testUpdateRecordLog() {

		TransferApplication transferApplication = null;
		SystemOperateLog log = new SystemOperateLog();
		try {
			transferApplication = transferApplicationService.list(
					TransferApplication.class, new Filter()).get(0);
			log = recordLog.insertNormalRecordLog(1l, "192.168.1.1",
					LogFunctionType.ASSETPACKAGEIMPORT,LogOperateType.ADD,transferApplication);
			
			recordLog.beforeUpdateRecordLog(transferApplication,TransferApplication.class);
			transferApplication.setAmount(BigDecimal.ONE);
		    log =  recordLog.generateUpdateRecordLog(1l, "192.168.1.1",
					LogFunctionType.ASSETPACKAGEIMPORT,LogOperateType.UPDATE,null,transferApplication, TransferApplication.class);
			System.out.println(recordLog);
			log.setKeyContent(transferApplication.getTransferApplicationNo());
			List<UpdateContentDetail> updateDetails= JsonUtils.parseArray(log.getUpdateContentDetail(), UpdateContentDetail.class);
			StringBuffer stringBuffer = new StringBuffer("新增 "+"线上支付单 "+log.getKeyContent());
			for(UpdateContentDetail updateDetail :updateDetails){
				stringBuffer.append(","+updateDetail.getFiledName()+"由"+updateDetail.getOldValue()+"改为"+updateDetail.getNewValue());
			}
			log.setRecordContent(stringBuffer.toString());
			System.out.println(log.getRecordContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}

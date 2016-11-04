package com.zufangbao.earth.yunxin.task;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo2do.core.persistence.support.Filter;
import com.zufangbao.sun.entity.financial.TransferApplication;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.service.BatchPayRecordService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.service.TransferApplicationService;
import com.zufangbao.sun.yunxin.entity.ExecutingSettlingStatus;
import com.zufangbao.wellsfargo.yunxin.handler.TransferApplicationHandler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml"})
public class TransferApplicationTaskTest {

	@Autowired
	private TransferApplicationTask transferApplicationTask;
	
	@Autowired
	private TransferApplicationHandler transferApplicationHandler;
	
	@Autowired
	private TransferApplicationService transferApplicationService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private BatchPayRecordService batchPayRecordService;
	
	@Test
	@Sql("classpath:test/yunxin/transferApplication/test_create_transfer_BatchPayRecord.sql")
	public void testTransferApplicationTask() {
		try {
			transferApplicationTask.todayRecycleAssetCreateTransferApplicationAndDeduct();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Order> orderList = orderService.list(Order.class, new Filter());
		assertEquals(2,orderList.size());
		assertEquals(ExecutingSettlingStatus.DOING,orderList.get(0).getExecutingSettlingStatus());
		assertEquals(ExecutingSettlingStatus.DOING,orderList.get(1).getExecutingSettlingStatus());
		
		List<TransferApplication> transferApplicationList = transferApplicationService.list(TransferApplication.class, new Filter());
		assertEquals(2,transferApplicationList.size());
		
	}

}

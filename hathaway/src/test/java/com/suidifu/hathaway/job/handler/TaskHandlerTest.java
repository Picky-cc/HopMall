package com.suidifu.hathaway.job.handler;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.suidifu.hathaway.job.Task;
import com.suidifu.hathaway.mq.MqResponse;
import com.suidifu.hathaway.redis.serializer.FastJsonRedisSerializer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" 
})
public class TaskHandlerTest {
	

	@Autowired
	private RedisTemplate<String, List<String>> stageTaskList;
	
	@Autowired
	private RedisTemplate<String, Set<String>> stageTaskSet;
	
	@Autowired
	private RedisTemplate<String, Task> taskList;
	
	@Autowired
	private TaskHandler taskHandler;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
		
	}
	@Test
	public void testSaveTask() throws Exception {
		
		Task  task = new Task();
		
		String uuid = UUID.randomUUID().toString();
		
		task.setUuid(uuid);
		
		taskHandler.saveTask(task);
		
		Task taskInRedis = taskHandler.getOneTaskBy(uuid);
		
		assertEquals(uuid,taskInRedis.getUuid());
	}
	@Test
	public void testupdateTask() throws Exception {
		
		Task  task = new Task();
		
		task.setJobUUid("xxxxx");
		
		String uuid = UUID.randomUUID().toString();
		
		task.setUuid(uuid);
		
		taskHandler.saveTask(task);
		
		MqResponse mqResponse = new MqResponse();
		
		mqResponse.setException_fired(true);
		
		String newJobUuid = "new";
		
		task.setJobUUid(newJobUuid);
		
		taskHandler.updateTask(task, mqResponse);
		
		Task taskInRedis = taskHandler.getOneTaskBy(uuid);
		
		assertEquals(newJobUuid,taskInRedis.getJobUUid());
		
	}
	@Test
	public void testgetOneTaskBy() throws Exception {
	}
	@Test
	public void testsetTaskSetToStage() throws Exception {
		
		String stageUuid = UUID.randomUUID().toString();
		
		Set<String> taskUuidSet = new HashSet<String>();
		
		String taskUuid = UUID.randomUUID().toString();
		String taskUuid2= UUID.randomUUID().toString();
		
		taskUuidSet.add(taskUuid);
		taskUuidSet.add(taskUuid2);
		
		taskHandler.setTaskSetToStage(stageUuid, taskUuidSet);
		
		Set<String> taskUuidSetInRedis = taskHandler.getAllTaskUuidListBy(stageUuid);
		
		assertEquals(2,taskUuidSetInRedis.size());
		
		
		
//		assertEquals(taskUuid,taskUuidSetInRedis.iterator().next());

	}
	
	@Test
	public void testName() throws Exception {
		
		stageTaskSet.setValueSerializer(new Jackson2JsonRedisSerializer(Set.class));
		
//		Set<String> keys = stageTaskSet.keys("\\*");
//		
//		for (String string : keys) {
//			System.out.println(string);
//		}
		
		String stageUuid = "50057e09-0fe8-44d5-997d-516fc144c28f";
		
		
		
		
		Set<String> taskUuidSet = stageTaskSet.boundSetOps(stageUuid).pop();
		
		for (String string : taskUuidSet) {
			System.out.println(string);
		}
	}

	@Test
	public void testStageTaskList() {
		
		String key = "A";
		
		String valueStr = "xxx";
	
		List<String> value = new ArrayList<String>();
		
		value.add(valueStr);
		
		stageTaskList.boundListOps(key).leftPush(value);
		
		assertEquals(value,stageTaskList.boundListOps(key).rightPop());
		
	}
	@Test
	public void testTaskList() throws Exception {
		
		String key = "B";
		
		Task task = new Task();
		
		String jobUuid = "jobUUid";
		
		task.setJobUUid(jobUuid);
		
		if(taskList.hasKey(key)){
			
			taskList.delete(key);
			
		}
		
		taskList.setValueSerializer(new FastJsonRedisSerializer<>(Task.class));
		
		taskList.boundSetOps(key).add(task);
		
		Task fetchTask = taskList.boundSetOps(key).pop();
		
		assertEquals(jobUuid,fetchTask.getJobUUid());
		
	}
	@Test
	public void testsearchTaskUuidListBy() throws Exception {
		
		String stageUuid = "xxx";
		
		taskHandler.getAllTaskUuidListBy(stageUuid);
	}

}

package com.suidifu.hathaway;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.suidifu.hathaway.handler.impl.MqStatusInspectorHandlerImplTest;
import com.suidifu.hathaway.mq.MqParameterTest;
import com.suidifu.hathaway.util.ReflectionUtilsTest;

@RunWith(Suite.class)
@SuiteClasses({
	MqParameterTest.class,
	ReflectionUtilsTest.class,
	MqStatusInspectorHandlerImplTest.class
})
public class AllTestOfWukai {

}

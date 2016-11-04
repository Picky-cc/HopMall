package com.zufangbao.sun;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.zufangbao.sun.service.RemittanceApplicationServiceTest;
import com.zufangbao.sun.service.RemittancePlanExecLogServiceTest;

@RunWith(Suite.class)
@SuiteClasses({
	RemittanceApplicationServiceTest.class,
	RemittancePlanExecLogServiceTest.class
	})
public class AllTestOfXieWenQianInSun {

}
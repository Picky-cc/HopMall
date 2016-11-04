package com.zufangbao.earth;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.zufangbao.earth.yunxin.unionpay.GZUnionPayPacketFactoryTest;
import com.zufangbao.earth.yunxin.unionpay.UnionpayBankConfigServiceTest;

@RunWith(Suite.class)
@SuiteClasses({
	GZUnionPayPacketFactoryTest.class,
	UnionpayBankConfigServiceTest.class,
	})
public class AllTestOfZhanghongbingInEarth {

}
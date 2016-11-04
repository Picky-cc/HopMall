package com.zufangbao.sun;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.zufangbao.sun.service.FinanceCompanyServiceTest;
import com.zufangbao.sun.service.USBKeyServiceTest;
import com.zufangbao.sun.utils.DirectBankHandlerFactoryTest;
import com.zufangbao.sun.yunxin.handler.DataStatisticsHandlerTest;
import com.zufangbao.sun.yunxin.ledgerBook.LedgerBookServiceTest;
import com.zufangbao.sun.yunxin.service.AssetSetServiceTest;
import com.zufangbao.sun.yunxin.service.AssetValuationDetailServiceTest;
import com.zufangbao.sun.yunxin.service.AssetsPackageServiceTest;
import com.zufangbao.sun.yunxin.service.ContractServiceTest;
import com.zufangbao.sun.yunxin.service.OrderServiceYunxinTest;
import com.zufangbao.sun.yunxin.service.RepaymentPlanServiceTest;
import com.zufangbao.sun.yunxin.service.SmsQueneServiceTest;
import com.zufangbao.sun.yunxin.service.SmsTemplateServiceTest;
import com.zufangbao.sun.yunxin.service.TransferApplicationServiceYunxinTest;

@RunWith(Suite.class)
@SuiteClasses({
	AssetValuationDetailServiceTest.class,
	TransferApplicationServiceYunxinTest.class,
	OrderServiceYunxinTest.class,
	AssetSetServiceTest.class,
	AssetsPackageServiceTest.class,
	FinanceCompanyServiceTest.class,
	SmsTemplateServiceTest.class,
	LedgerBookServiceTest.class,
	DirectBankHandlerFactoryTest.class,
	USBKeyServiceTest.class,
	SmsQueneServiceTest.class,
	RepaymentPlanServiceTest.class,
	ContractServiceTest.class,
	DataStatisticsHandlerTest.class,
	AllTestOfXieWenQianInSun.class
	})
public class AllTestsOfSunYunxinBranch {

}

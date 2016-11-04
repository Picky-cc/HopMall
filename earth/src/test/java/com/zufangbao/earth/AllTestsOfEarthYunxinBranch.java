package com.zufangbao.earth;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.zufangbao.earth.util.ValidatorUtilTest;
import com.zufangbao.earth.web.controller.api.ModifyApiControllerTest;
import com.zufangbao.earth.yunxin.api.GZUnionPayApiHandlerTest;
import com.zufangbao.earth.yunxin.api.RepaymentPlanAPIHandlerTest;
import com.zufangbao.earth.yunxin.handler.AssetSetHandlerTest;
import com.zufangbao.earth.yunxin.handler.AssetValuationDetailHandlerTest;
import com.zufangbao.earth.yunxin.handler.impl.ContractHandlerTest;
import com.zufangbao.earth.yunxin.handler.impl.DataStatisticsCacheHandlerTest;
import com.zufangbao.earth.yunxin.handler.impl.LoanBatchHandlerTest;
import com.zufangbao.earth.yunxin.handler.impl.OfflineBillHandlerTest;
import com.zufangbao.earth.yunxin.handler.impl.SettlementOrderHandlerTest;
import com.zufangbao.earth.yunxin.handler.impl.TransferApplicationHandlerYunxinTest;
import com.zufangbao.earth.yunxin.ledgerBook.LedgerBookEntryTest;
import com.zufangbao.earth.yunxin.service.DictionaryServiceTest;
import com.zufangbao.earth.yunxin.task.AssetTaskTest;
import com.zufangbao.earth.yunxin.task.TransferApplicationTaskTest;
import com.zufangbao.earth.yunxin.web.AssetSetControllerTest;
import com.zufangbao.earth.yunxin.web.ContractControllerTest;
import com.zufangbao.earth.yunxin.web.FinancialContractControllerTest;
import com.zufangbao.earth.yunxin.web.GuaranteeControllerTest;
import com.zufangbao.earth.yunxin.web.LoanBatchControllerTest;
import com.zufangbao.earth.yunxin.web.PaymentControllerTest;
import com.zufangbao.earth.yunxin.web.SettlementOrderControllerTest;
import com.zufangbao.earth.yunxin.web.YunxinOfflinePaymentControllerTest;
import com.zufangbao.sun.AllTestsOfSunYunxinBranch;
import com.zufangbao.wellsfargo.AllTestsOfWellsfargoYunxinBranch;

@RunWith(Suite.class)
@SuiteClasses({
	AllTestsOfSunYunxinBranch.class,
	AllTestsOfWellsfargoYunxinBranch.class,
	AllTestOfZhanghongbingInEarth.class,
	AssetValuationDetailHandlerTest.class,
	TransferApplicationHandlerYunxinTest.class,
	PaymentControllerTest.class,
	AssetTaskTest.class,
	AssetSetHandlerTest.class,
	SettlementOrderHandlerTest.class,
	OfflineBillHandlerTest.class,
	TransferApplicationTaskTest.class,
	GuaranteeControllerTest.class,
	ContractControllerTest.class,
	YunxinOfflinePaymentControllerTest.class,
	DictionaryServiceTest.class,
	ValidatorUtilTest.class,
	AssetSetControllerTest.class,
	FinancialContractControllerTest.class,
	LoanBatchControllerTest.class,
	SettlementOrderControllerTest.class,
	GZUnionPayApiHandlerTest.class,
	LoanBatchHandlerTest.class,
	ContractHandlerTest.class,
	RepaymentPlanAPIHandlerTest.class,
	DataStatisticsCacheHandlerTest.class,
	ModifyApiControllerTest.class,
	AllTestOfXieWenQianInEarth.class,
	LedgerBookEntryTest.class
	})
public class AllTestsOfEarthYunxinBranch {

}

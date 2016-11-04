package com.zufangbao.wellsfargo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.zufangbao.wellsfargo.yunxin.handler.OrderHandlerYunxinTest;
import com.zufangbao.wellsfargo.yunxin.handler.SourceDocumentHandlerTest;
import com.zufangbao.wellsfargo.yunxin.service.JournalVoucherServiceYunxinTest;
import com.zufangbao.wellsfargo.yunxin.service.SourceDocumentServiceTest;

@RunWith(Suite.class)
@SuiteClasses({
	OrderHandlerYunxinTest.class,
	JournalVoucherServiceYunxinTest.class,
	SourceDocumentHandlerTest.class,
	SourceDocumentServiceTest.class,
	JournalVoucherServiceYunxinTest.class
	})
public class AllTestsOfWellsfargoYunxinBranch {

}

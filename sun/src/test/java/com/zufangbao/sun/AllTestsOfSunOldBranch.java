/**
 * 
 */
package com.zufangbao.sun;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.zufangbao.sun.service.CustomerServiceTest;
import com.zufangbao.sun.service.FinancialContractServiceTest;
import com.zufangbao.sun.service.TransferApplicationServiceTest;
import com.zufangbao.sun.service.impl.ContractServiceImplTest;
import com.zufangbao.sun.service.impl.HouseServiceTest;
import com.zufangbao.sun.service.impl.UtilsTest;
import com.zufangbao.sun.utils.DateUtilsTest;
import com.zufangbao.sun.utils.FinanceUtilsTest;
import com.zufangbao.sun.utils.GeneratorUtilsTest;
import com.zufangbao.sun.utils.HSSFUtilsTest;
import com.zufangbao.sun.utils.JavaScriptEngineHelperUtilsTest;
import com.zufangbao.sun.utils.ObjectCastUtilsTest;
import com.zufangbao.sun.utils.PoiUtilTest;

/**
 * @author wukai
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ 
	ObjectCastUtilsTest.class,
	ContractServiceImplTest.class,
	CustomerServiceTest.class,
	FinancialContractServiceTest.class,
    JavaScriptEngineHelperUtilsTest.class,
    TransferApplicationServiceTest.class,
    HouseServiceTest.class,
    UtilsTest.class,
    DateUtilsTest.class,
    FinanceUtilsTest.class,
    GeneratorUtilsTest.class,
    HSSFUtilsTest.class,
    PoiUtilTest.class
})
public class AllTestsOfSunOldBranch {

}

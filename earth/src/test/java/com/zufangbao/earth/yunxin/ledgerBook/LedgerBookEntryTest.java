package com.zufangbao.earth.yunxin.ledgerBook;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.demo2do.core.persistence.support.Filter;
import com.zufangbao.earth.yunxin.handler.DataStatisticsCacheHandler;
import com.zufangbao.earth.yunxin.handler.impl.DataStatisticsCacheHandlerImpl;
import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.entity.account.DepositeAccountInfo;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.ledgerbook.BankAccountCache;
import com.zufangbao.sun.ledgerbook.ChartOfAccount;
import com.zufangbao.sun.ledgerbook.TAccountInfo;
import com.zufangbao.sun.service.AccountService;
import com.zufangbao.sun.service.FinancialContractService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class LedgerBookEntryTest {

	@Autowired
	@Qualifier("dataStatisticsCacheHandler")
	private BankAccountCache bankAccountCache;
	@Autowired
	private FinancialContractService financialContractService;
	
	
	
	@Before
	public void init(){
		
	}
	
	@Autowired
	private AccountService accountService;
	
	@Test
	@Sql("classpath:test/yunxin/ledgerBook/test_add_bank_account_into_book_entry.sql")
	public void testRefreshEntry(){
		FinancialContract financialContract = financialContractService.load(FinancialContract.class, 2L);
		
		bankAccountCache.getBankAccountCache(financialContract);
		
		String accountNo = "07895462";
		
		String merId = "mer_id_1";
		
		HashMap<String, TAccountInfo> entryBook=ChartOfAccount.EntryBook();
		TAccountInfo merIdAccountInfo = entryBook.get(merId);
		assertNotNull(merIdAccountInfo);
		assertEquals(merId,merIdAccountInfo.getSecondLevelAccount().getAccountName());
		assertEquals(ChartOfAccount.FST_BANK_SAVING_CODE+"."+merId,merIdAccountInfo.getSecondLevelAccount().getAccountCode());
		
		TAccountInfo account_accountInfo = entryBook.get(accountNo);
		assertNotNull(account_accountInfo);
		assertEquals(accountNo,account_accountInfo.getSecondLevelAccount().getAccountName());
		assertEquals(ChartOfAccount.FST_BANK_SAVING_CODE+"."+accountNo,account_accountInfo.getSecondLevelAccount().getAccountCode());
		
		//校验虚拟账户
		String account_Indecx = accountNo+ChartOfAccount.SUFFIX_OF_INDEPENDENT_ASSETS;
		TAccountInfo vir_account_accountInfo = entryBook.get(account_Indecx);
		assertNotNull(vir_account_accountInfo);
		assertEquals(accountNo,vir_account_accountInfo.getSecondLevelAccount().getAccountName());
		assertEquals(ChartOfAccount.FST_BANK_SAVING_CODE+"."+accountNo,vir_account_accountInfo.getSecondLevelAccount().getAccountCode());
		assertEquals(account_Indecx,vir_account_accountInfo.getThirdLevelAccount().getAccountName());
		assertEquals(account_Indecx,vir_account_accountInfo.getThirdLevelAccount().getAccountCode());
		
		
		//chartOfAccount中已有的
		assertNotNull(entryBook.get(ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH));
		assertNotNull(entryBook.get(ChartOfAccount.SND_BANK_SAVING_NFQ_YL));
	}
}

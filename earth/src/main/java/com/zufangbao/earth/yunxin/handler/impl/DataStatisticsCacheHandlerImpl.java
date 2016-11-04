package com.zufangbao.earth.yunxin.handler.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

import com.zufangbao.earth.cache.DataStatisticsCache;
import com.zufangbao.earth.cache.DataStatisticsCacheSpec;
import com.zufangbao.earth.cache.DepositeAccountInfoCache;
import com.zufangbao.earth.yunxin.handler.DataStatisticsCacheHandler;
import com.zufangbao.sun.entity.account.DepositeAccountInfo;
import com.zufangbao.sun.entity.account.DepositeAccountType;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.ledgerbook.AccountSide;
import com.zufangbao.sun.ledgerbook.BankAccountCache;
import com.zufangbao.sun.ledgerbook.ChartOfAccount;
import com.zufangbao.sun.ledgerbook.TAccountInfo;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.yunxin.entity.model.vo.welcom.RemittanceDataStatistic;
import com.zufangbao.sun.yunxin.entity.model.vo.welcom.RepaymentDataStatistic;
import com.zufangbao.sun.yunxin.handler.DataStatHandler;

@Component("dataStatisticsCacheHandler")
public class DataStatisticsCacheHandlerImpl implements DataStatisticsCacheHandler, BankAccountCache{

	private static Log log = LogFactory.getLog(DataStatisticsCacheHandlerImpl.class);
	
	@Autowired
	private DataStatHandler dataStatHandler;
	
	@Autowired
	private FinancialContractService financialContractService;
	
	@Autowired
	private CacheManager cacheManager;
	
	private static long TIMEOUT = 1000 * 60 * 1;
	
	private static long TIMEOUT_BANK_ACCOUNT = 1000 * 60 * 2;
	
	public static long getTIMEOUT() {
		return TIMEOUT;
	}

	public static void setTIMEOUT(long tIMEOUT) {
		TIMEOUT = tIMEOUT;
	}
	
	
	
	@Override
	public List<String>  getFinancialContractUuidBy(String hostAccountNo) {
		if(StringUtils.isEmpty(hostAccountNo)) return ( List<String> ) Collections.EMPTY_LIST;
		
		Cache cache = cacheManager.getCache(DataStatisticsCacheSpec.FINANCIAL_CONTRACT_VS_BANK_ACCOUNT_CACHE_KEY);
		Cache.ValueWrapper cacheWrapper = cache.get(hostAccountNo);
		if(cacheWrapper != null){
			List<String> data = (List<String>)cacheWrapper.get();
			if(data!=null ){
				log.info("get RepaymentData from cache.");
				return data;
			}
		}
		
		List<String> financialContractUuids = financialContractService.getFinancialContractUuidBy(hostAccountNo);
		if(CollectionUtils.isEmpty(financialContractUuids)==true) return Collections.EMPTY_LIST;
		cache.put(hostAccountNo, financialContractUuids);
		return financialContractUuids;
	}
	@Override
	public DepositeAccountInfo extractFirstBankAccountFrom(FinancialContract financialContract){
		HashMap<String,DepositeAccountInfo> bankAccountCache = getBankAccountCache(financialContract);
		return extractFirstBankAccount(bankAccountCache, DepositeAccountType.BANK);
	}
	
	@Override
	public DepositeAccountInfo extractUnionPayAccountFrom(FinancialContract financialContract, String ... unionPayKeys){
		HashMap<String,DepositeAccountInfo> bankAccountCache = getBankAccountCache(financialContract);
		if(unionPayKeys !=null && unionPayKeys.length!=0){
			for (String unionPayKey : unionPayKeys) {
				DepositeAccountInfo info = bankAccountCache.get(unionPayKey);
				if(info!=null){
					return info;
				}
			}
		}
		return extractFirstBankAccount(bankAccountCache, DepositeAccountType.UINON_PAY);
	}
	
	public DepositeAccountInfo extractFirstBankAccount(
			HashMap<String, DepositeAccountInfo> accountSet, DepositeAccountType depositAccountType) {
		
		for(DepositeAccountInfo info:accountSet.values())
		{
			if(info!=null&&info.getAccount_type()!=null&&info.getAccount_type().equals(depositAccountType))
				return info;
		}
		return null;
	}
	
	@Override
	public HashMap<String,DepositeAccountInfo>  getBankAccountCache(String financialContractUuid) {
		if(StringUtils.isEmpty(financialContractUuid)) return new HashMap<String, DepositeAccountInfo>();
		
		Cache cache = cacheManager.getCache(DataStatisticsCacheSpec.BANK_ACCOUNT_VS_FINANCIAL_CONTRACT_CACHE_KEY);
		Cache.ValueWrapper cacheWrapper = cache.get(financialContractUuid);
		if(cacheWrapper != null){
			HashMap<String,DepositeAccountInfo> data = (HashMap<String,DepositeAccountInfo>)cacheWrapper.get();
			if(data!=null ){
				log.info("get RepaymentData from cache.");
				return data;
			}
		}
		
		FinancialContract contract= financialContractService.getFinancialContractBy(financialContractUuid);
		return getBankAccountCache(contract);
	}
	
	@Override
	public HashMap<String,DepositeAccountInfo>  getBankAccountCache(FinancialContract contract) {
		if(contract==null||StringUtils.isEmpty(contract.getFinancialContractUuid())) return new HashMap<String, DepositeAccountInfo>();
		
		Cache cache = cacheManager.getCache(DataStatisticsCacheSpec.BANK_ACCOUNT_VS_FINANCIAL_CONTRACT_CACHE_KEY);
		Cache.ValueWrapper cacheWrapper = cache.get(contract.getFinancialContractUuid());
		if(cacheWrapper != null){
			DepositeAccountInfoCache data = (DepositeAccountInfoCache)cacheWrapper.get();
			if(data!=null && data.getAccountInfoMap()!=null && !data.isExpired(TIMEOUT_BANK_ACCOUNT)){
				log.info("get RepaymentData from cache.");
				return data.getAccountInfoMap();
			}
		}
		
		HashMap<String,DepositeAccountInfo> accountSet= financialContractService.extractDepositeAccount(contract);
		referesh_entry_book(accountSet.values());
		cache.put(contract.getFinancialContractUuid(), new DepositeAccountInfoCache(accountSet,System.currentTimeMillis()));
		return accountSet;
	}
	
	public synchronized void  referesh_entry_book(Collection<DepositeAccountInfo> accountSide)
	{
		HashMap<String, TAccountInfo> entryBook=ChartOfAccount.EntryBook();
		
		
		for(DepositeAccountInfo account:accountSide)
		{
			if(account.getAccount_type().equals(DepositeAccountType.VIRTUAL_ACCOUNT)) continue;
			if(!entryBook.containsKey(account.getDeposite_account_name())) {
				String accountCode=ChartOfAccount.FST_BANK_SAVING_CODE+"."+account.getDeposite_account_name();
				//银行银联账户
				ChartOfAccount.create2NDAccount(entryBook,ChartOfAccount.FST_BANK_SAVING,account.getDeposite_account_name(),AccountSide.DEBIT,accountCode);
				
			}
			if(account.getAccount_type()==DepositeAccountType.BANK &&
					!StringUtils.equals(ChartOfAccount.FST_BANK_SAVING, account.getDeposite_account_name())
					&& !entryBook.containsKey(account.getIndependentAccountName())) {
				//银行虚拟账户
				ChartOfAccount.create3rdAccount(entryBook,ChartOfAccount.FST_BANK_SAVING,account.getDeposite_account_name(),account.getIndependentAccountName(),AccountSide.DEBIT,account.getIndependentAccountName());
			}
		}
	}

	@Override
	public RepaymentDataStatistic getRepaymentDataFromOrRefreshCache(List<Long> financialContractIds) {
		DataStatisticsCache cache = getDataStatisticsFromOrRefreshCache(financialContractIds);
		return cache.getRepaymentData();
	}
	
	@Override
	public RemittanceDataStatistic getRemittanceDataFromOrRefreshCache(
			List<Long> financialContractIds) {
		DataStatisticsCache cache = getDataStatisticsFromOrRefreshCache(financialContractIds);
		return cache.getRemittanceDataStatistic();
	}
	
	private DataStatisticsCache getDataStatisticsFromOrRefreshCache(List<Long> financialContractIds) {
		log.info("get dataStatistics with financialContractIds:"+financialContractIds);
		
		Cache cache = cacheManager.getCache(DataStatisticsCacheSpec.CACHE_KEY);
		Cache.ValueWrapper cacheWrapper = cache.get(financialContractIds);
		if(cacheWrapper != null){
			DataStatisticsCache data = (DataStatisticsCache)cacheWrapper.get();
			if(data!=null && !data.isExpired(TIMEOUT)){
				log.info("get dataStatistics from cache.");
				return data;
			}
		}
		log.info("get dataStatistics from db.");
		RepaymentDataStatistic repaymentData = dataStatHandler.countRepaymentData(financialContractIds);
		RemittanceDataStatistic remittanceData = dataStatHandler.countRemittanceData(financialContractIds);
		DataStatisticsCache dataCache = new DataStatisticsCache(System.currentTimeMillis(),financialContractIds, repaymentData, remittanceData);
		cache.put(financialContractIds, dataCache);
		return dataCache;
	}

	@Override
	@CacheEvict(value=DataStatisticsCacheSpec.CACHE_KEY,key="#financialContractIds")
	public void cacheEvict(List<Long> financialContractIds) {
		log.info("cacheEvict "+financialContractIds);
	}

}

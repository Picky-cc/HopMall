package com.zufangbao.earth.cache.handler.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.BillMatchResult;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.CashFlowCacheHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.CashFlowHandler;
@Component("cashFlowCacheHandler")
public class CashFlowCacheHandlerImpl implements CashFlowCacheHandler{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3532205695113223768L;

	private static long TIMEOUT = 1000 * 60 * 2;
	
	private static Log logger = LogFactory.getLog(CashFlowCacheHandlerImpl.class);
	
	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private CashFlowHandler cashFlowHandler;

	@Override
	public void saveBillMatchResultAndReverseCache(String cashFlowUuid,
			List<BillMatchResult> billMatchResultList){
		MatchedResultSetAgainstCashFlow matchedResultSetAgainstCashFlow = new MatchedResultSetAgainstCashFlow(billMatchResultList);
		Cache cache = cacheManager.getCache(CashFlowCacheHandlerSpec.EHCACHE_CASH_FLOW_UUID_KEY);
		cache.put(cashFlowUuid, matchedResultSetAgainstCashFlow);
			buildResverseCache(cashFlowUuid, billMatchResultList);
	}

	private void buildResverseCache(String cashFlowUuid,
			List<BillMatchResult> billMatchResultList) {
		Cache billMatchResultReverseMappingCache = cacheManager.getCache(CashFlowCacheHandlerSpec.EHCACHE_MATCH_RESULT_REVERSE_MAPPING);

		for(BillMatchResult bill : billMatchResultList)
		{
			if(bill==null)
				continue;
			String billId=bill.getBillingPlanUuid();
			if(StringUtils.isEmpty(billId)) 
				continue;
			Cache.ValueWrapper cacheWrapper=billMatchResultReverseMappingCache.get(billId);
			HashSet<String> cashFlowIdSet=new HashSet<String>();;
			if(cacheWrapper!=null ) 
			{	Object cacheObject = cacheWrapper.get();
				if(cacheObject!=null) 
					cashFlowIdSet=(HashSet<String>)cacheObject;			
			}
			cashFlowIdSet.add(cashFlowUuid);
			billMatchResultReverseMappingCache.put(billId, cashFlowIdSet);
		}
	}
	
	@Override
	public void clearRelativeBillOf(Collection<String> billingPlanUuids )
	{
		Cache billMatchResultReverseMappingCache = cacheManager.getCache(CashFlowCacheHandlerSpec.EHCACHE_MATCH_RESULT_REVERSE_MAPPING);
		
	
		for(String billingPlanUuid : billingPlanUuids)
		{
			if(StringUtils.isEmpty(billingPlanUuid)) continue;
			Cache.ValueWrapper cacheWrapper=billMatchResultReverseMappingCache.get(billingPlanUuid);
			if(cacheWrapper==null ) continue;
			Object cacheObject = cacheWrapper.get();
			if(cacheObject==null) continue;
			
			HashSet<String> cashFlowIdSet=(HashSet<String>)cacheObject;
			clearBillMatchResult(cashFlowIdSet);
			cashFlowIdSet.clear();
			billMatchResultReverseMappingCache.put(billingPlanUuid, cashFlowIdSet);
			billMatchResultReverseMappingCache.evict(billingPlanUuid);
		}
	}

	@Override
	public List<BillMatchResult> getBillMatchResultList(String cashFlowUuid, AccountSide accountSide){
		Cache cache = cacheManager.getCache(CashFlowCacheHandlerSpec.EHCACHE_CASH_FLOW_UUID_KEY);
		Cache.ValueWrapper cacheWrapper = cache.get(cashFlowUuid);
		if(cacheWrapper != null){
			Object object = cacheWrapper.get();
			if (object == null){
				return new ArrayList<BillMatchResult>();
			}
			MatchedResultSetAgainstCashFlow matchedResultSetAgainstCashFlow = (MatchedResultSetAgainstCashFlow) object;
			if (matchedResultSetAgainstCashFlow != null ) {
				if( !matchedResultSetAgainstCashFlow.isExpired(TIMEOUT))
					return matchedResultSetAgainstCashFlow.result();
			}
			
		}
		List<BillMatchResult> results = refreshCacheOfMatchedBillBy(cashFlowUuid, accountSide);
		return results;
	}
	
	@Override
	public List<BillMatchResult> refreshCacheOfMatchedBillBy(String cashFlowUuid, AccountSide accountSide){
		
		List<BillMatchResult> billMatchResultList = cashFlowHandler.getMatchedBillInDbBy(cashFlowUuid, accountSide);
		saveBillMatchResultAndReverseCache(cashFlowUuid, billMatchResultList);;
		return billMatchResultList;
	}
	
	@Override
	public void clearBillMatchResult(String cashFlowId)
	{
		HashSet<String> cashFlowIdSet=new HashSet<String> ();
		cashFlowIdSet.add(cashFlowId);
		clearBillMatchResult(cashFlowIdSet);
		
	}
	@Override
	public void clearBillMatchResult(Set<String> cashFlowIdSet)
	{
		Cache MatchResultcache = cacheManager.getCache(CashFlowCacheHandlerSpec.EHCACHE_CASH_FLOW_UUID_KEY);
		for(String cashFlowId:cashFlowIdSet)
		{
			Object cachedObject=MatchResultcache.get(cashFlowId);
			if(cachedObject==null)
				return;
			MatchResultcache.put(cashFlowId,CollectionUtils.EMPTY_COLLECTION);
			MatchResultcache.evict(cashFlowId);
		}
		
	}
	private void clearCache(String cacheSpec){
		Cache cache = cacheManager.getCache(cacheSpec);
		cache.clear();	
	}

	public static long getTIMEOUT() {
		return TIMEOUT;
	}

	public static void setTIMEOUT(long tIMEOUT) {
		TIMEOUT = tIMEOUT;
	}
	
	
	@Override
	public void clearAllCache() {
		clearBillMatchResultCache();
	}

	@Override
	public void clearBillMatchResultCache() {
		clearCache(CashFlowCacheHandlerSpec.EHCACHE_CASH_FLOW_UUID_KEY);
		clearCache(CashFlowCacheHandlerSpec.EHCACHE_MATCH_RESULT_REVERSE_MAPPING);
	}

}

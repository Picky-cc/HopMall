package com.zufangbao.wellsfargo.silverpool.cashauditing.handler;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.BillMatchResult;

public interface CashFlowCacheHandler {
	public static class CashFlowCacheException extends Exception {
		
		private static final long serialVersionUID = 1955899265741510345L;
		public int code;
		public String msg;
		public CashFlowCacheException(int code, String msg) {
			super();
			this.code = code;
			this.msg = msg;
		}
		public CashFlowCacheException(int code) {
			super();
			this.code = code;
		}
		
	}
	public void saveBillMatchResultAndReverseCache(String cashFlowUuid, List<BillMatchResult> billMatchResultList);
	public void clearAllCache();
	public void clearBillMatchResultCache();
	public abstract void clearBillMatchResult(Set<String> cashFlowIdSet);
	public abstract void clearBillMatchResult(String cashFlowId);
	public abstract List<BillMatchResult> getBillMatchResultList(String cashFlowUuid, AccountSide accountSide);
	public List<BillMatchResult> refreshCacheOfMatchedBillBy(String cashFlowUuid, AccountSide accountSide);
	public abstract void clearRelativeBillOf(Collection<String> billingPlanUuids);
}

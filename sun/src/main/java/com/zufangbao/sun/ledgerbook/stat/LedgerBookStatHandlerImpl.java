package com.zufangbao.sun.ledgerbook.stat;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.sun.ledgerbook.ChartOfAccount;
import com.zufangbao.sun.ledgerbook.LedgerItemService;
import com.zufangbao.sun.yunxin.entity.model.api.ExtraChargeSpec;

@Component("ledgerBookStatHandler")
public class LedgerBookStatHandlerImpl implements LedgerBookStatHandler {
	@Autowired
	private LedgerItemService ledgerItemService;
	
	@Override
	public BigDecimal get_receivable_amount(String ledgerBookNo,String assetSetUuid, String customerUuid){
		BigDecimal amount = ledgerItemService.getBalancedAmount(ledgerBookNo, ChartOfAccount.FST_RECIEVABLE_ASSET, "", customerUuid, assetSetUuid, "");
		return amount;
	}
	@Override
	public BigDecimal get_gurantee_amount(String ledgerBookNo, String assetSetUuid) {
		BigDecimal amount = ledgerItemService.getBalancedAmount(ledgerBookNo, ChartOfAccount.SND_RECIEVABLE_LOAN_GUARANTEE, "", "", assetSetUuid, "");
		return amount;
	}
	@Override
	public BigDecimal get_unearned_amount(String ledgerBookNo, String assetSetUuid) {
		BigDecimal amount = ledgerItemService.getBalancedAmount(ledgerBookNo, ChartOfAccount.FST_UNEARNED_LOAN_ASSET, "", "", assetSetUuid, "");
		return amount;
	}
	
	
	@Override
	public Map<String, BigDecimal> unrecovered_asset_snapshot(
			String ledgerBookNo, String assetSetUuid, boolean isIncludeUnearned) {
		HashMap<String,BigDecimal> snapShot=new HashMap<String,BigDecimal>();
		for(String key:ExtraChargeSpec.keySet)
		{
			BigDecimal snapshot_amount=BigDecimal.ZERO;
			BigDecimal recievable_amount=BigDecimal.ZERO;
			BigDecimal recievable_overdue_amount =BigDecimal.ZERO;
			BigDecimal unearned_amount=BigDecimal.ZERO;
			String entryName=ExtraChargeSpec.fst_recievable_entry_mapping.get(key);
			
			if(!StringUtils.isEmpty(entryName)){
				recievable_amount= ledgerItemService.getBalancedAmount(ledgerBookNo,entryName,"", "", assetSetUuid, "");
			}
			entryName=ExtraChargeSpec.fst_recievable_overdue_entry_mapping.get(key);
			if(!StringUtils.isEmpty(entryName)){
				recievable_overdue_amount=ledgerItemService.getBalancedAmount(ledgerBookNo,entryName,"", "", assetSetUuid, "");
			}
			
			if(isIncludeUnearned){
				entryName=ExtraChargeSpec.fst_unearned_entry_mapping.get(key);
				if(!StringUtils.isEmpty(entryName)){
					unearned_amount = ledgerItemService.getBalancedAmount(ledgerBookNo,entryName,"", "", assetSetUuid, "");
				}
			}
			snapshot_amount = snapshot_amount.add(recievable_amount).add(recievable_overdue_amount).add(unearned_amount);
			snapShot.put(key, snapshot_amount);
		}
		return snapShot;
	}
}

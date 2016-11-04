package com.zufangbao.sun.ledgerbook.stat;

import java.math.BigDecimal;
import java.util.Map;

public interface LedgerBookStatHandler {

	public abstract BigDecimal get_receivable_amount(String ledgerBookNo,
			String assetSetUuid, String customerUuid);
	
	public abstract BigDecimal get_gurantee_amount(String ledgerBookNo,
			String assetSetUuid);
	
	public abstract BigDecimal get_unearned_amount(String ledgerBookNo,
			String assetSetUuid);

	public abstract Map<String,BigDecimal> unrecovered_asset_snapshot(String ledgerBookNo, String assetSetUuid, boolean isIncludeUnearned);
}

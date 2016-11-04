package com.zufangbao.wellsfargo.silverpool.cashauditing.handler;

import java.util.List;

public interface UpdateAssetStatusLedgerBookHandler {
	public void updateAssetsFromLedgerBook(String ledgerBookNo, List<String> assetSetUuids, boolean updateOrder);
}

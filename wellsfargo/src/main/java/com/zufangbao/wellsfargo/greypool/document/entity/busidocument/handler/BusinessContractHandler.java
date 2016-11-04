package com.zufangbao.wellsfargo.greypool.document.entity.busidocument.handler;

import java.util.List;

import com.zufangbao.wellsfargo.greypool.document.entity.busidocument.contract.SimplifiedContractInfo;

public interface BusinessContractHandler {
	public List<SimplifiedContractInfo> getSimpliedContractInfoByMatch(String keyword);
}

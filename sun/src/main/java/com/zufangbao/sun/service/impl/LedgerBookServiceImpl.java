package com.zufangbao.sun.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.demo2do.core.utils.StringUtils;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.ledgerbook.LedgerBook;
import com.zufangbao.sun.service.AssetPackageService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.LedgerBookService;
import com.zufangbao.sun.yunxin.entity.AssetSet;

@Service("ledgerBookService")
public class LedgerBookServiceImpl extends GenericServiceImpl<LedgerBook>
		implements LedgerBookService {

	@Autowired
	private FinancialContractService financialContractService;
	@Autowired
	private AssetPackageService assetPackageService;
	
	@Override
	public LedgerBook getBookByBookNo(String bookNo) {
		if(StringUtils.isEmpty(bookNo)){
			return null;
		}
		Filter filter = new Filter();
		filter.addEquals("ledgerBookNo", bookNo);
		List<LedgerBook> bookList = this.list(LedgerBook.class, filter);
		if(CollectionUtils.isEmpty(bookList)){
			return null;
		}
		return bookList.get(0);
	}

	@Override
	public LedgerBook getBookByAsset(AssetSet assetSet) {
		Contract contract = assetSet.getContract();
		FinancialContract financialContract = financialContractService.getFinancialContractBy(contract.getFinancialContractUuid());
		LedgerBook ledgerBook = getBookByBookNo(financialContract.getLedgerBookNo());
		return ledgerBook;
	}

	@Override
	public LedgerBook getBookByOrder(Order order) {
		AssetSet assetSet = order.getAssetSet();
		return getBookByAsset(assetSet);
	}
}

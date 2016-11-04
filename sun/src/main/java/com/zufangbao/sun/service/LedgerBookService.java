package com.zufangbao.sun.service;

import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.ledgerbook.LedgerBook;
import com.zufangbao.sun.yunxin.entity.AssetSet;

public interface LedgerBookService extends GenericService<LedgerBook> {
	public LedgerBook getBookByBookNo(String bookNo);
	public LedgerBook getBookByAsset(AssetSet assetSet);
	public LedgerBook getBookByOrder(Order order);
}

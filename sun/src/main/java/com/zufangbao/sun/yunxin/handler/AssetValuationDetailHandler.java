package com.zufangbao.sun.yunxin.handler;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.ledgerbook.AlreadayCarryOverException;
import com.zufangbao.sun.ledgerbook.InvalidLedgerException;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AssetValuationDetail;
import com.zufangbao.sun.yunxin.entity.AssetValuationSubject;

public interface AssetValuationDetailHandler {
	public void assetValuation(AssetSet assetSet, Date valuation_date_time) throws AlreadayCarryOverException, InvalidLedgerException;
	public BigDecimal get_asset_fair_value(List<AssetValuationDetail> assetValuationDetailList);
	public List<Long> extractIdsFrom(List<AssetValuationDetail> assetValuationDetailList);
	public String extractIdsJsonFrom(List<AssetValuationDetail> assetValuationDetailList);
	public Order add_asset_valuation_and_modify_amount_of_asset_order(Order order,BigDecimal deltaAmount, Date valuationTime, String comment);
	public BigDecimal getAmountBySubject(AssetValuationSubject assetValuationSubject,Date valuationDate, int loanOverdueStartDay, AssetSet assetSet);
}

package com.zufangbao.sun.ledgerbook;

import java.math.BigDecimal;
import java.util.HashMap;

public class AccountDispersor
{
	
	
	private HashMap<String,BigDecimal> AccountLedgers=new HashMap<String,BigDecimal>();
	private HashMap<String,LedgerTradeParty> TradeParties=new HashMap<String,LedgerTradeParty>();
	private HashMap<String,VoucherSet> vouchers=new HashMap<String,VoucherSet>();
	
	
	public void dispers(String accoutName,BigDecimal Amount,AccountSide side)
	{
		if(side==AccountSide.CREDIT) Amount=Amount.negate();
		AccountLedgers.put(accoutName,Amount);
	}
	public void dispers(String accoutName,BigDecimal Amount,AccountSide side,LedgerTradeParty party)
	{
		if(side==AccountSide.CREDIT) Amount=Amount.negate();
		AccountLedgers.put(accoutName,Amount);
		TradeParties.put(accoutName,party);
	}
	public void dispers(String accoutName,BigDecimal Amount,AccountSide side,LedgerTradeParty party,String journalVoucher,String BusinessVoucher,String SourceDocument)
	{
		if(side==AccountSide.CREDIT) Amount=Amount.negate();
		AccountLedgers.put(accoutName,Amount);
		TradeParties.put(accoutName,party);
		VoucherSet vset=new VoucherSet(journalVoucher,BusinessVoucher,SourceDocument);
		vouchers.put(accoutName, vset);
	}
	public HashMap<String,BigDecimal> dispersTable()
	{
		return AccountLedgers;
	}
	public HashMap<String,LedgerTradeParty> dispersParties()
	{
		return TradeParties;
	}
	public HashMap<String,VoucherSet> dispersVoucherSet()
	{
		return vouchers;
	}
}
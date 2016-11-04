package com.zufangbao.sun.entity.account;

import com.zufangbao.sun.ledgerbook.ChartOfAccount;
import com.zufangbao.sun.ledgerbook.LedgerTradeParty;
import com.zufangbao.sun.ledgerbook.TAccountInfo;

public class DepositeAccountInfo {
	
	
	
	
	private String deposite_account_name;
	
	
	
	private LedgerTradeParty deopsite_account_owner_party;
	
	
	private DepositeAccountType  account_type;
	
	public DepositeAccountInfo(LedgerTradeParty owner_party )
	{
		this.setAccount_type(DepositeAccountType.BANK);
		this.setDeopsite_account_owner_party(owner_party);
		this.setDeposite_account_name(ChartOfAccount.FST_BANK_SAVING);
	}
	
	public DepositeAccountInfo(String account_name,LedgerTradeParty owner_party,DepositeAccountType account_type)
	{
		this.setAccount_type(account_type);
		this.setDeopsite_account_owner_party(owner_party);
		this.setDeposite_account_name(account_name);
	}

	public String getDeposite_account_name() {
		return deposite_account_name;
	}
	public String getIndependentAccountName(){
		TAccountInfo account=ChartOfAccount.EntryBook().get(deposite_account_name);
		if(account==null){
			return ChartOfAccount.FST_BANK_SAVING;
		}
		if(!ChartOfAccount.FST_BANK_SAVING.equals(account.getFirstLevelAccount().getAccountName())
			||	ChartOfAccount.FST_BANK_SAVING.equals(deposite_account_name)){
			return deposite_account_name;
		}
		return deposite_account_name+ChartOfAccount.SUFFIX_OF_INDEPENDENT_ASSETS;
	}

	public void setDeposite_account_name(String deposite_account_name) {
		this.deposite_account_name = deposite_account_name;
	}

	public LedgerTradeParty getDeopsite_account_owner_party() {
		return deopsite_account_owner_party;
	}

	public void setDeopsite_account_owner_party(
			LedgerTradeParty deopsite_account_owner_party) {
		this.deopsite_account_owner_party = deopsite_account_owner_party;
	}

	public DepositeAccountType getAccount_type() {
		return account_type;
	}

	public void setAccount_type(DepositeAccountType account_type) {
		this.account_type = account_type;
	}
	
	

}

package com.zufangbao.sun.ledgerbook;


public class TAccountInfo {

	private String Alias;
	private Entry firstLevelAccount;
	private Entry secondLevelAccount;
	private Entry thirdLevelAccount;
	public String getAlias() {
		return Alias;
	}
	public void setAlias(String alias) {
		Alias = alias;
	}
	public Entry getFirstLevelAccount() {
		return firstLevelAccount;
	}
	public void setFirstLevelAccount(Entry firstLevelAccount) {
		this.firstLevelAccount = firstLevelAccount;
	}
	public Entry getSecondLevelAccount() {
		return secondLevelAccount;
	}
	public void setSecondLevelAccount(Entry secondLevelAccount) {
		this.secondLevelAccount = secondLevelAccount;
	}
	public Entry getThirdLevelAccount() {
		return thirdLevelAccount;
	}
	public void setThirdLevelAccount(Entry thirdLevelAccount) {
		this.thirdLevelAccount = thirdLevelAccount;
	}
	public TAccountInfo(String alias, Entry firstLevelAccount,
			Entry secondLevelAccount, Entry thirdLevelAccount) {
		super();
		Alias = alias;
		this.firstLevelAccount = firstLevelAccount;
		this.secondLevelAccount = secondLevelAccount;
		this.thirdLevelAccount = thirdLevelAccount;
	}
	public TAccountInfo(){}
	
	public static TAccountInfo making_first_class_account(Entry entry)
	{
		TAccountInfo account=new TAccountInfo(entry.getAccountName(),entry,null,null);
		
		return account;
	}
	
	public static TAccountInfo making_second_class_account(Entry fstentry,Entry sndentry)
	{
		TAccountInfo account=new TAccountInfo(fstentry.getAccountName(),fstentry,sndentry,null);
		
		return account;
	}
	public static TAccountInfo making_third_class_account(Entry fstEntry,Entry sndEntry,Entry trdEntry)
	{
		TAccountInfo account=new TAccountInfo(trdEntry.getAccountName(),fstEntry,sndEntry,trdEntry);
		return account;
	}
	
}

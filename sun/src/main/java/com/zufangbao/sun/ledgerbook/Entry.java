package com.zufangbao.sun.ledgerbook;


public class Entry {
	
	
	
	private String accountName;
	
	private String accountCode; 
	
	private AccountSide side;
	
	private EntryLevel level;
	
	
	

	public AccountSide getSide() {
		return side;
	}

	public void setSide(AccountSide side) {
		this.side = side;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	
	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountUuId) {
		this.accountCode = accountUuId;
	}

	public EntryLevel getLevel() {
		return level;
	}

	public void setLevel(EntryLevel level) {
		this.level = level;
	}
	
	
	public Entry()
	{
		
	}

	public Entry(String accountName, String accountNameUuId,
			EntryLevel level,AccountSide side) {
		super();
		this.accountName = accountName;
		this.accountCode = accountNameUuId;
		this.level = level;
		this.side=side;
	}

	public static Entry making_first_class_entry(String Name,String code,AccountSide side)
	{
		return new Entry(Name,code+"",EntryLevel.LVL1,side);
		
	}
	
	public static Entry making_second_class_entry(String Name,String code,AccountSide side)
	{
		return new Entry(Name,code+"",EntryLevel.LVL2,side);
		
	}
	public static Entry making_third_class_entry(String Name,String code,AccountSide side)
	{
		return new Entry(Name,code+"",EntryLevel.LVL3,side);
		
	}
	
	
	
	

}

package com.zufangbao.sun.entity.account;

import com.zufangbao.sun.ledgerbook.ChartOfAccount;


/**
 * 银行账号类型
 * @author zjm
 *
 */
public enum LedgerBookBankAccount {

	/** 农分期广银联 */
	NFQ_GZUNION("enum.ledger-book-bank-account.nfq-gzunion",ChartOfAccount.SND_BANK_SAVING_NFQ_YL),
	
	/** 农分期招商银行 */
	NFQ_CMB("enum.ledger-book-bank-account.nfq-cmb",ChartOfAccount.SND_BANK_SAVING_NFQ_ZSYH),
	
	/** 农分期平安银行独立账户 账内冲销 */
	NFQ_INDEPENDENT_INNER_TRANSFER("enum.ledger-book-bank-account.nfq-independent-inner-transfer",ChartOfAccount.SND_LIABILITIES_INDEPENDENT_INTER_ACCOUNT_REMITTANCE),
	
	/** 农分期平安银行独立账户代偿 */
	NFQ_INDEPENDENT_OUTER_TRANSFER("enum.ledger-book-bank-account.nfq-independent-outer-transfer",ChartOfAccount.SND_LIABILITIES_INDEPENDENT_INTER_ACCOUNT_BENEFICIARY);
	
	
	private String key;
	private String ledgerBookSndBankAccount;
	
	public String getLedgerBookSndBankAccount() {
		return ledgerBookSndBankAccount;
	}

	/**
	 * 
	 * @param key
	 */
	private LedgerBookBankAccount(String key, String ledgerBookSndBankAccount){
		this.key = key;
		this.ledgerBookSndBankAccount = ledgerBookSndBankAccount;
	}
	
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * Get alias of PaymentInstrument
	 * 
	 * @return
	 */
	public String getAlias() {
		return this.key.substring(this.key.lastIndexOf(".") + 1);
	}
	
	public static LedgerBookBankAccount fromValue(int value){
	    
	    for(LedgerBookBankAccount item : LedgerBookBankAccount.values()){
	      if(item.ordinal() == value){
	        return item;
	      }
	    }
	    return null;
	  }
	
}

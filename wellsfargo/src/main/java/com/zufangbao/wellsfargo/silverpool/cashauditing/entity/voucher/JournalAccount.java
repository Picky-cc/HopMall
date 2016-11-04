/**
 * 
 */
package com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher;

import com.zufangbao.sun.entity.account.AccountSide;

/**
 * @author wukai
 *
 */
public class JournalAccount {

	private AccountSide accountSide;

	public JournalAccount() {
		super();
	}
	
	public JournalAccount(AccountSide accountSide) {
		super();
		this.accountSide = accountSide;
	}

	public static JournalAccount generateDebitJournalAccount(){
		return new JournalAccount(AccountSide.DEBIT);
	}
	public static JournalAccount generateCreditJournalAccount(){
		return new JournalAccount(AccountSide.CREDIT);
	}

	public AccountSide getAccountSide() {
		return accountSide;
	}

	public void setAccountSide(AccountSide accountSide) {
		this.accountSide = accountSide;
	}
}

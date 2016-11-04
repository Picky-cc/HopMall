package com.zufangbao.sun.ledgerbook;


public class LedgerTradeParty {
	
	private String fstParty;
	private String sndParty;
	public String getFstParty() {
		return fstParty;
	}
	public void setFstParty(String fstParty) {
		this.fstParty = fstParty;
	}
	public String getSndParty() {
		return sndParty;
	}
	public void setSndParty(String sndParty) {
		this.sndParty = sndParty;
	}
	public LedgerTradeParty(String fstParty, String sndParty) {
		super();
		this.fstParty = fstParty;
		this.sndParty = sndParty;
	}
	
	public LedgerTradeParty(){
	}

}

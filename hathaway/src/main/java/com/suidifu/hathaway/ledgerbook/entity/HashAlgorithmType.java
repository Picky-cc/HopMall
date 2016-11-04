package com.suidifu.hathaway.ledgerbook.entity;

public enum HashAlgorithmType {
	MD5("MD5"), 
	SHA_256("SHA-256"),
	SHA_384("SHA-384"),
	SHA_512("SHA-512");
	
	private String key;
	
	private HashAlgorithmType(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}

/**
 * 
 */
package com.suidifu.hathaway.persistence.support;


/**
 * @author wukai
 *
 */
public class Order {

	private String sentence;
	
	public Order() {
		super();
	}

	public Order(String sentence) {
		super();
		this.sentence = sentence;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	
}

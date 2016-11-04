/**
 * 
 */
package com.suidifu.hathaway.amqp;

/**
 * @author wukai
 *
 */
public class AsycOccurReminderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8251027371706733772L;
	
	private String requestUuid;

	public AsycOccurReminderException(String requestUuid) {
		super();
		this.requestUuid = requestUuid;
	}

	public String getRequestUuid() {
		return requestUuid;
	}
}

/**
 * 
 */
package com.suidifu.hathaway.interfaces;

/**
 * @author wukai
 *
 */
public interface MqSerialzableAndDeserialzable extends java.io.Serializable {
	
	public Object deserializeFromString(String serializedString);
	
	public String serializeToStrig();

}

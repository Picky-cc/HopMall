/**
 * 
 */
package com.suidifu.hathaway.persistence.support;

/**
 * @author wukai
 *
 */
public class SupportConvertor {

	public static Filter convertFrom(com.demo2do.core.persistence.support.Filter rawFilter){
		
		Filter filter = new Filter(rawFilter.getParameters(),rawFilter.getWhereSentence(),rawFilter.getClauseSentence());
		
		return filter;
	}
	public static Order convertFrom(com.demo2do.core.persistence.support.Order rawOrder){
		
		Order order = new Order(rawOrder.getSentence());
		
		return order;
	}
}

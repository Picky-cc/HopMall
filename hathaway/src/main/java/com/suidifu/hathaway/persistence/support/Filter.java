/**
 * 
 */
package com.suidifu.hathaway.persistence.support;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wukai
 *
 */
public class Filter {

	private Map<String, Object> parameters = new LinkedHashMap<String, Object>();
	
	private String whereSentence;
	
	private String clauseSentence;
	
	public Filter() {
		super();
	}

	public Filter(Map<String, Object> parameters, String whereSentence,
			String clauseSentence) {
		super();
		this.parameters = parameters;
		this.whereSentence = whereSentence;
		this.clauseSentence = clauseSentence;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public String getWhereSentence() {
		return whereSentence;
	}

	public void setWhereSentence(String whereSentence) {
		this.whereSentence = whereSentence;
	}

	public String getClauseSentence() {
		return clauseSentence;
	}

	public void setClauseSentence(String clauseSentence) {
		this.clauseSentence = clauseSentence;
	}
}

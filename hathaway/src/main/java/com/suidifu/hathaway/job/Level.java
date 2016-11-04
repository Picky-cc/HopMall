/**
 * 
 */
package com.suidifu.hathaway.job;

/**
 * @author wukai
 *
 */
public enum Level{
	
	FST("enum.level.fst"),
	
	SND("enum.level.snd"),
	
	TRD("enum.level.trd"),
	
	FOURTH("enum.level.fourth"),
	
	FIFTH("enum.level.fifth"),
	
	;
	
	private Level(String key) {
		this.key = key;
	}

	private String key;

	public String getKey() {
		return key;
	}
}

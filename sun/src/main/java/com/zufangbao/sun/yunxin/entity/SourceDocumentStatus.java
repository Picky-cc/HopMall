package com.zufangbao.sun.yunxin.entity;

import com.zufangbao.sun.BasicEnum;

/**
 * 
 * @author zjm
 *
 */
public enum SourceDocumentStatus implements BasicEnum{

	/** 创建 */
	CREATE("enum.sourceDocument-status.create"),

	/** 成功 */
	SIGNED("enum.sourceDocument-status.signed"),

	/** 作废 */
	INVALID("enum.sourceDocument-status.invalid");

	private String key;

	private SourceDocumentStatus(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
	
	public int getOrdinal(){
		
		return this.ordinal();
	}
	
	public static SourceDocumentStatus fromOrdinal(int ordinal){
		
		for(SourceDocumentStatus item : SourceDocumentStatus.values()){
			
			if(ordinal == item.getOrdinal()){
				
				return item;
			}
		}
		return null;
	}

	public String getAlias() {
		return this.key.substring(this.key.lastIndexOf(".") + 1);
	}
}

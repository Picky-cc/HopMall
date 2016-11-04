package com.suidifu.coffer.entity;

import java.util.Map;

public abstract class DebitModel {
	protected String busiCode;

	public String getBusiCode() {
		return busiCode;
	}

	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}
	
	public abstract void process(Map<String, String> workParms) throws Exception;
	
	protected static String formatEscapeNull(String format, Object... args) {
		for (int i = 0; i < args.length; i++) {
			if(args[i] == null) {
				args[i] = "";
			}
		}
		return String.format(format, args);
	}
}

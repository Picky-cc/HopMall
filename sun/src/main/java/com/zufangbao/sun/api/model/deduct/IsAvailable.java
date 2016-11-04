package com.zufangbao.sun.api.model.deduct;

import com.zufangbao.sun.utils.ApplicationMessageUtils;

public enum IsAvailable {

	
	    //有效
		AVAILABLE("enum.is-available.recorded"),
		//废除
		DELETEED("enum.is-available.deleted");
		 
		
		
		
		
	   private String key;
		
		/**
		 * @param key
		 */
		private IsAvailable(String key) {
			this.key = key;
		}
		
		/**
		 * @return the key
		 */
		public String getKey() {
			return key;
		}
		
		/**
		 * Get alias of order status
		 * 
		 * @return
		 */
		public String getAlias() {
			return this.key.substring(this.key.lastIndexOf(".") + 1);
		}
		

		
		public int getOrdinal(){
			
			return this.ordinal();
		}
		public static IsAvailable fromOrdinal(int ordinal){
			
			for(IsAvailable item : IsAvailable.values()){
				
				if(ordinal == item.getOrdinal()){
					
					return item;
				}
			}
			return null;
		}
		
		
		public  String getChineseMessage() {
			return ApplicationMessageUtils.getChineseMessage(this.getKey());
		}

}

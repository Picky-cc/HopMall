package com.zufangbao.sun.yunxin.entity;

import com.zufangbao.sun.utils.ApplicationMessageUtils;

public enum IsSummary {
	    //合计
	    DETAILFEE("enum.is-summary.detailFee"),
		//明细
		TOTALFEE("enum.is-summary.totalFee"),
		//逾期费用合计
	    OVERDUEFEE("enum.is-summary.o");
		
		
	   private String key;
		
		/**
		 * @param key
		 */
		private IsSummary(String key) {
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
		public static IsSummary fromOrdinal(int ordinal){
			
			for(IsSummary item : IsSummary.values()){
				
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

package com.zufangbao.earth.cache.handler.impl;

import java.io.Serializable;
import java.util.List;

import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.BillMatchResult;

public class MatchedResultSetAgainstCashFlow implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3490621067466627252L;

	private List<BillMatchResult> resultList;
	
	private long timeStamp;
	
	public MatchedResultSetAgainstCashFlow(List<BillMatchResult> results)
	{
		resultList=results;
		
		this.timeStamp=System.currentTimeMillis();
	}
	
	public boolean isExpired(long timeout)
	{
		return (System.currentTimeMillis()-timeStamp)>timeout;
	}
	
	public List<BillMatchResult> result()
	{
		return resultList;
	}
	
	
}
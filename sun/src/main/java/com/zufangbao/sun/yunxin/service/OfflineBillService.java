/**
 * 
 */
package com.zufangbao.sun.yunxin.service;

import java.util.Collection;
import java.util.List;

import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.yunxin.entity.OfflineBill;

public interface OfflineBillService extends GenericService<OfflineBill> {

	OfflineBill getOfflineBillBy(String offlineBillUuid);
	
	public List<OfflineBill> getOfflineBillListBy(Collection<String> offlineBillUuids);
	
	public List<String> extractOfflineBillNo(List<OfflineBill> offlineBills);
	
	public List<String> extractSerialNo(List<OfflineBill> offlineBills);
	
	public List<OfflineBill> queryOfflineBillListBy(String queryBeginDate, String queryEndDate);
	
	public OfflineBill getOfflineBillById(Long offlineBillId);
	
}
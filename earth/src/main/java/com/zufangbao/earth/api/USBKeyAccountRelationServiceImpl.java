package com.zufangbao.earth.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.service.impl.GenericServiceImpl;

@Service("usbKeyAccountRelationService")
public class USBKeyAccountRelationServiceImpl extends
		GenericServiceImpl<USBKeyAccountRelation> implements
		USBKeyAccountRelationService {

	@Override
	public String getUSBKeyBy(String accountUuid, GatewayType gateWayType) {
		if(StringUtils.isEmpty(accountUuid) || null == gateWayType) {
			return StringUtils.EMPTY;
		}
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("accountUuid", accountUuid);
		parms.put("gateWayType", gateWayType);
		
		List<USBKeyAccountRelation> relationList = genericDaoSupport.searchForList("FROM USBKeyAccountRelation WHERE accountUuid =:accountUuid AND gateWayType =:gateWayType", parms);
		
		if(CollectionUtils.isEmpty(relationList)) {
			return StringUtils.EMPTY;
		}
		return relationList.get(0).getUsbKeyUuid();
	}

}

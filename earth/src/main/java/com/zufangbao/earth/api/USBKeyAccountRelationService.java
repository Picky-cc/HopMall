package com.zufangbao.earth.api;

import com.demo2do.core.service.GenericService;

/**
 * 
 * @author zjm
 *
 */
public interface USBKeyAccountRelationService extends GenericService<USBKeyAccountRelation> {

	String getUSBKeyBy(String accountUuid, GatewayType gateWayType);
}

package com.zufangbao.earth.api;

import com.zufangbao.earth.api.exception.UnknownUkeyException;
import com.zufangbao.sun.entity.directbank.USBKey;

/**
 * 
 * @author zjm
 *
 */
public interface USBKeyHandler {

	USBKey getUSBKeyByPayerAccount(String payerAccountNo, GatewayType gateWayType) throws UnknownUkeyException;
}

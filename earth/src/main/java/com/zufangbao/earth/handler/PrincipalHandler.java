package com.zufangbao.earth.handler;

import com.zufangbao.earth.model.PrincipalInfoModel;

public interface PrincipalHandler {

	public String createPrincipal(PrincipalInfoModel principalInfoModel, Long creatorId);
	
	public void updatePrincipal(PrincipalInfoModel principalInfoModel, Long operatorId);
	
}

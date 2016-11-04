package com.zufangbao.earth.yunxin.handler.remittance.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo2do.core.web.resolver.Page;
import com.zufangbao.earth.yunxin.entity.remittance.model.RemittanceApplicationShowModel;
import com.zufangbao.earth.yunxin.handler.remittance.RemittancetApplicationHandler;
import com.zufangbao.sun.yunxin.entity.remittance.RemittanceApplication;
import com.zufangbao.sun.yunxin.entity.remittance.RemittanceApplicationQueryModel;
import com.zufangbao.sun.yunxin.service.remittance.IRemittanceApplicationService;

@Component("remittancetTranscationHandler")
public class RemittanceApplicationHandlerImpl implements RemittancetApplicationHandler{
	
	@Autowired
	IRemittanceApplicationService iRemittanceApplicationService;
	
	@Override
	public List<RemittanceApplicationShowModel> queryShowModelList(RemittanceApplicationQueryModel queryModel, Page page) {
		 List<RemittanceApplication>  remittanceApplications =  iRemittanceApplicationService.queryRemittanceApplication(queryModel, page);
		 return castShowModel(remittanceApplications);
	}

	private List<RemittanceApplicationShowModel> castShowModel(List<RemittanceApplication> remittanceApplications) {
		List<RemittanceApplicationShowModel> showModels = new ArrayList<RemittanceApplicationShowModel>();
		 for(RemittanceApplication remittanceApplication :remittanceApplications){
			 RemittanceApplicationShowModel showModel = new RemittanceApplicationShowModel(remittanceApplication);
			 showModels.add(showModel);
		 }
		 return showModels;
	}

}

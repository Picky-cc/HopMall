package com.zufangbao.earth.yunxin.handler.remittance;

import java.util.List;

import com.demo2do.core.web.resolver.Page;
import com.zufangbao.earth.yunxin.entity.remittance.model.RemittanceApplicationShowModel;
import com.zufangbao.sun.yunxin.entity.remittance.RemittanceApplicationQueryModel;

public interface RemittancetApplicationHandler {
	
      public List<RemittanceApplicationShowModel>  queryShowModelList(RemittanceApplicationQueryModel queryModel, Page page);
      
}

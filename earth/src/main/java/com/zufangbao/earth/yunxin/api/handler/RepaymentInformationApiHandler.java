package com.zufangbao.earth.yunxin.api.handler;

import javax.servlet.http.HttpServletRequest;

import com.zufangbao.earth.yunxin.api.model.modify.RepaymentInformationModifyModel;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;

public interface RepaymentInformationApiHandler {

	public  void modifyRepaymentInformation(RepaymentInformationModifyModel modifyModel, HttpServletRequest request, Contract contract );

	public  void modifyRepaymentInfoByRule(String bankCode, String bankAccount, String bankName, String provinceCode,
			String cityCode, ContractAccount vaildContractAccount);
}

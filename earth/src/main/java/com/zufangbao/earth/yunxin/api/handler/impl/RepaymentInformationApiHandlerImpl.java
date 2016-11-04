package com.zufangbao.earth.yunxin.api.handler.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.suidifu.coffer.entity.cmb.UnionPayBankCodeMap;
import com.zufangbao.earth.yunxin.api.exception.ApiException;
import com.zufangbao.earth.yunxin.api.exception.ApiResponseCode;
import com.zufangbao.earth.yunxin.api.handler.RepaymentInformationApiHandler;
import com.zufangbao.earth.yunxin.api.model.modify.RepaymentInformationModifyModel;
import com.zufangbao.earth.yunxin.api.service.UpdateRepaymentInformationLogService;
import com.zufangbao.sun.entity.bank.Bank;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.service.ContractAccountService;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.service.BankService;
import com.zufangbao.wellsfargo.greypool.geography.entity.City;
import com.zufangbao.wellsfargo.greypool.geography.entity.Province;
import com.zufangbao.wellsfargo.greypool.geography.service.CityService;
import com.zufangbao.wellsfargo.greypool.geography.service.ProvinceService;

@Component("RepaymentInformationApiHandler")
public class RepaymentInformationApiHandlerImpl  implements RepaymentInformationApiHandler{

	@Autowired
	private   ContractAccountService contractAccountService;
	
	@Autowired 
	private UpdateRepaymentInformationLogService updateRepaymentInformationLogService;
	
	@Autowired
	private BankService  bankService;
	
	@Autowired
	private  ProvinceService provinceService;
	
	@Autowired
	private  CityService cityService;
	
	@Override
	public void modifyRepaymentInformation(RepaymentInformationModifyModel modifyModel, HttpServletRequest request, Contract contract) {
		
	   ContractAccount vaildContractAccount =contractAccountService.getTheEfficientContractAccountBy(contract);
	   modifyRepaymentInfoByRule(modifyModel.getBankCode(),modifyModel.getBankAccount(),modifyModel.getBankName(),modifyModel.getBankProvince(),
				modifyModel.getBankCity(), vaildContractAccount);
		
	}


	@Override
	public  void modifyRepaymentInfoByRule(String bankCode,String bankAccount,String bankName,String provinceCode,String cityCode, 
			ContractAccount vaildContractAccount) {
		contractAccountService.setContractAccountIsNotVaild(vaildContractAccount);
		   generateAndSaveEditContractAccount(bankCode,bankAccount,bankName,provinceCode,
				   cityCode, vaildContractAccount);
	}
	
	

	private void generateAndSaveEditContractAccount(String bankCode,
			String bankAccount, String bankName, String bankProvince,
			String bankCity, ContractAccount vaildContractAccount) {
		
		ContractAccount editContractAccount = new ContractAccount(vaildContractAccount);
		
		checkModifyContentAndSetProvinceAndCity(bankCode, bankProvince, bankCity, editContractAccount);
		
		String  unionBankCode = UnionPayBankCodeMap.BANK_CODE_MAP.get(bankCode);
		
		editContractAccount.setBankCode(unionBankCode);
		editContractAccount.setStandardBankCode(bankCode);
		editContractAccount.setPayAcNo(bankAccount);
		editContractAccount.setBank(bankName);
		
		editContractAccount.setFromDate(new Date());
		editContractAccount.setThruDate(DateUtils.MAX_DATE);
		contractAccountService.save(editContractAccount);
	}

	public void checkModifyContentAndSetProvinceAndCity(String bankCode, String bankProvinceCode,
			String bankCityCode, ContractAccount editContractAccount) {
		Bank bank =  bankService.getCachedBanks().get(bankCode);
	    if(bank == null){
	    	throw new ApiException(ApiResponseCode.NO_MATCH_BANK);
	    }
	    Province province = provinceService.getProvinceByCode(bankProvinceCode);
	    if(province == null){
	    	throw new ApiException(ApiResponseCode.NO_MATCH_PROVINCE);
	    }
	    City city = cityService.getCityByCityCode(bankCityCode);
	    if(city == null){
	    	throw new ApiException(ApiResponseCode.NO_MATCH_CITY);
	    }
	    editContractAccount.setProvinceCode(bankProvinceCode);
	    editContractAccount.setCityCode(bankCityCode);
	    editContractAccount.setProvince(province.getName());
	    editContractAccount.setCity(city.getName());
	}
	
	

}

package com.zufangbao.earth.web.controller.reapymentInfo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo2do.core.web.interceptor.MenuSetting;
import com.zufangbao.earth.web.controller.BaseController;
import com.zufangbao.earth.yunxin.api.handler.RepaymentInformationApiHandler;
import com.zufangbao.sun.entity.bank.Bank;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.service.ContractAccountService;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.yunxin.service.BankService;
import com.zufangbao.wellsfargo.greypool.geography.entity.City;
import com.zufangbao.wellsfargo.greypool.geography.entity.Province;
import com.zufangbao.wellsfargo.greypool.geography.service.CityService;
import com.zufangbao.wellsfargo.greypool.geography.service.ProvinceService;

/** 
* @author 作者 zhenghangbo
* @version 创建时间：Oct 1, 2016 8:47:28 PM 
* 类说明 
*/

@Controller
@RequestMapping("modifyContractAccount")
public class ModifyRepaymentInfoController extends BaseController{

	@Autowired
	private ContractAccountService contractAccountService;
	
	@Autowired
	private ProvinceService  provinceService;
	@Autowired
	private ContractService  contractService;
	
	@Autowired
	private BankService      bankService;
	
	@Autowired
	private CityService      cityService;
	
	@Autowired
	private RepaymentInformationApiHandler repaymentInformationApiHandler;
	

	
	@RequestMapping(value ="/repaymentInfo/modify")
	public @ResponseBody  String modifyReapymentInfo(ModifyRepaymentInfoRequestModel requestModel){
		
		try {
			if(requestModel.checkData() == false){
				return  jsonViewResolver.errorJsonResult("数据错误！！");
			}
			
			Map<String, Province> provincesMap = provinceService.getCacheProvinces();
			Map<String, City> citysMap = cityService.getCacheCitysMap();
			Province  province = provincesMap.get(requestModel.getProvinceCode());
			City  city =  citysMap.get(requestModel.getCityCode());
			if(city ==null || province ==null){
				return jsonViewResolver.errorJsonResult("系统错误，变更还款信息失败!!!");
			}
			Contract contract = contractService.load(Contract.class, requestModel.getContractId());
			ContractAccount vaildContractAccount = contractAccountService.getTheEfficientContractAccountBy(contract);
			
			repaymentInformationApiHandler.modifyRepaymentInfoByRule(requestModel.getBankCode(), requestModel.getBankAccount(), BankCoreCodeMapSpec.coreBankMap.get(requestModel.getBankCode()), province.getCode(), city.getCode(), vaildContractAccount);
			return jsonViewResolver.sucJsonResult();
			
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误，变更还款信息失败!!!"); 
		}
		
	}
	
	
	
}

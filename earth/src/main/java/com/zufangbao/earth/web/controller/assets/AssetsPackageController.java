/**
 * 
 */
package com.zufangbao.earth.web.controller.assets;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo2do.core.entity.Result;
import com.demo2do.core.utils.JsonUtils;
import com.demo2do.core.web.interceptor.MenuSetting;
import com.demo2do.core.web.resolver.Secure;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.util.ExceptionUtils;
import com.zufangbao.earth.util.IpUtil;
import com.zufangbao.earth.yunxin.handler.LoanBatchHandler;
import com.zufangbao.sun.service.AssetPackageService;
import com.zufangbao.sun.yunxin.log.LogFunctionType;
import com.zufangbao.sun.yunxin.log.LogOperateType;

/**
 * 资产包管理
 * 
 * @author wk
 *
 */
@RestController
@RequestMapping("/assets-package")
@MenuSetting("menu-data")
public class AssetsPackageController {
	@Autowired
	private AssetPackageService assetPackageService;
    @Autowired
    private LoanBatchHandler loanBatchHandler;
    
	@RequestMapping(value = "excel-create-assetData", method = RequestMethod.POST)
	public @ResponseBody String importAssetPackageExcel(
			@RequestParam(value = "financialContractNo", required = true) Long financialContractNo,
		MultipartFile file, String app_id,@Secure Principal principal, HttpServletRequest request) {
		
		if(file == null) {
			return returnErrorMessage("请选择要导入的资产包");
		}
		
		try {
			String ipAddress = IpUtil.getIpAddress(request);
			InputStream inputStream = file.getInputStream();
			Result result = assetPackageService.importAssetPackagesViaExcel(inputStream, financialContractNo, principal.getName(), ipAddress);
			if(result.isValid()) {
				Long loanBatchId = (Long) result.get("loanBatchId");
				loanBatchHandler.generateLoanBacthSystemLog(principal, ipAddress, LogFunctionType.ASSETPACKAGEIMPORT ,LogOperateType.IMPORT,loanBatchId);
			}
			return JsonUtils.toJsonString(result);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return returnErrorMessage("资产包中数据格式有误！！！");
		} catch (Exception e) {
			e.printStackTrace();
			return returnErrorMessage(ExceptionUtils.getCauseErrorMessage(e));
		}
	}

	private String returnErrorMessage(String message) {
		Result result = new Result();
		result.fail().setMessage(message);
		return JsonUtils.toJsonString(result);
	}
	
}
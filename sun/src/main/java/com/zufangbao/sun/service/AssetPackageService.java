/**
 * 
 */
package com.zufangbao.sun.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.demo2do.core.entity.Result;
import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.financial.AssetPackage;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.ledgerbook.DuplicateAssetsException;

/**
 * @author lute
 *
 */

public interface AssetPackageService extends GenericService<AssetPackage> {
	
	
	public AssetPackage getAssetPackagesByContract(Contract contract);
	
	public AssetPackage getAssetPackageByContractId(Long contractId);
	
	public Result importAssetPackagesViaExcel(InputStream input, Long financialContractId, String operatorName, String ipAddress) throws IOException,InvalidFormatException, DuplicateAssetsException;
	
	FinancialContract getFinancialContract(Contract contract);
	
	public List<AssetPackage> getAssetPackageListByLoanBatchId(Long loanBatchId);
	
}

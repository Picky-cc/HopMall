package com.zufangbao.sun.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.service.impl.GenericServiceImpl;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.financial.BankTransactionLimitSheet;
import com.zufangbao.sun.entity.financial.BankTransactionLimitSheetListModel;
import com.zufangbao.sun.entity.financial.BankTransactionLimitSheetUpdateModel;
import com.zufangbao.sun.entity.financial.PaymentInstitutionName;
import com.zufangbao.sun.service.BankTransactionLimitSheetService;
import com.zufangbao.sun.utils.EnumUtil;
import com.zufangbao.sun.yunxin.entity.model.TransactionLimitQueryModel;
import com.zufangbao.sun.yunxin.entity.remittance.AccountSide;

@Service("bankTransactionLimitSheetService")
public class BankTransactionLimitSheetServiceImpl extends GenericServiceImpl<BankTransactionLimitSheet> implements BankTransactionLimitSheetService {

	@Override
	public boolean modifyinvalidTime(PaymentInstitutionName paymentInstitutionName, String outlierChannelName, AccountSide accountSide) {
		if(paymentInstitutionName == null || StringUtils.isBlank(outlierChannelName) || accountSide == null){
			return false;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("paymentInstitutionName", paymentInstitutionName.ordinal());
		params.put("outlierChannelName", outlierChannelName);
		params.put("accountSide", accountSide.ordinal());
		genericDaoSupport.executeSQL(
				"UPDATE bank_transaction_limit_sheet "
					+ " SET invalid_time = now() "
					+ " WHERE payment_institution_name =:paymentInstitutionName "
					+ " AND outlier_channel_name =:outlierChannelName"
					+ " AND account_side = :accountSide"
					+ " AND invalid_time is NULL", params);
		return true;
	}
	
	@Override
	public boolean modifyTransactionLimit(BankTransactionLimitSheetUpdateModel updateModel) {
		
		if(StringUtils.isBlank(updateModel.getBankTransactionLimitSheetUuid())){
			return false;
		}
		
		if(!(isValid(updateModel.getTransactionLimitPerTranscation()) 
				&& isValid(updateModel.getTranscationLimitPerDay()) 
				&& isValid(updateModel.getTransactionLimitPerMonth()))){
			return false;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		StringBuffer updateSb = new StringBuffer("UPDATE bank_transaction_limit_sheet SET ");
		if(updateModel.getTransactionLimitPerTranscation() == null){
			updateSb.append("transaction_limit_per_transcation = NULL,");
		}else{
			updateSb.append("transaction_limit_per_transcation = :transactionLimitPerTranscation,");
			params.put("transactionLimitPerTranscation", updateModel.getTransactionLimitPerTranscation());
		}
		if(updateModel.getTranscationLimitPerDay() == null){
			updateSb.append("transcation_limit_per_day = NULL,");
		}else{
			updateSb.append("transcation_limit_per_day = :transcationLimitPerDay,");
			params.put("transcationLimitPerDay", updateModel.getTranscationLimitPerDay());
		}
		if(updateModel.getTransactionLimitPerMonth() == null){
			updateSb.append("transaction_limit_per_month = NULL");
		}else{
			updateSb.append("transaction_limit_per_month = :transactionLimitPerMonth");
			params.put("transactionLimitPerMonth", updateModel.getTransactionLimitPerMonth());
		}
		
		updateSb.append(" WHERE bank_transaction_limit_sheet_uuid =:bankTransactionLimitSheetUuid AND invalid_time IS NULL");
		params.put("bankTransactionLimitSheetUuid", updateModel.getBankTransactionLimitSheetUuid());
		
		genericDaoSupport.executeSQL(updateSb.toString(), params);
		return true;
	}
	
	private boolean isValid(BigDecimal a){
		if(a == null){
			return true;
		}
		return a.compareTo(BigDecimal.ZERO)>=0;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllBanks(List<String> paymentChannelUuids) {
		if(CollectionUtils.isEmpty(paymentChannelUuids)){
			return Collections.EMPTY_LIST;
		}
		Map<String, Object> params = new HashMap<>();
		String queryString = "SELECT DISTINCT bankCode FROM BankTransactionLimitSheet WHERE paymentChannelInformationUuid IN (:paymentChannelUuids) AND invalidTime IS NULL";
		params.put("paymentChannelUuids", paymentChannelUuids);
		return this.genericDaoSupport.searchForList(queryString, params);
		
		// http://blog.sina.com.cn/s/blog_6feaf58501014am4.html TODO
	}
	
	@Override
	public List<BankTransactionLimitSheet> query(TransactionLimitQueryModel queryModel, Page page) {
		StringBuffer querySb = new StringBuffer("SELECT btls FROM BankTransactionLimitSheet btls WHERE invalidTime IS NULL");
		Map<String, Object> parameters = new HashMap<>();
		genQuerySentence(queryModel, querySb, parameters);
		if(page == null){
			return this.genericDaoSupport.searchForList(querySb.toString(), parameters);
		}else{
			return this.genericDaoSupport.searchForList(querySb.toString(), parameters, page.getBeginIndex(), page.getEveryPage());
		}
	}
	
	@Override
	public int queryCount(TransactionLimitQueryModel queryModel) {
		StringBuffer querySb = new StringBuffer("FROM BankTransactionLimitSheet btls WHERE invalidTime IS NULL");
		Map<String, Object> parameters = new HashMap<>();
		genQuerySentence(queryModel, querySb, parameters);
		return this.genericDaoSupport.count(querySb.toString(), parameters);
	}
	
	private void genQuerySentence(TransactionLimitQueryModel queryModel, StringBuffer querySb, Map<String, Object> parameters){
		PaymentInstitutionName paymentInstitutionName = queryModel.getGatewayEnum();
		if(paymentInstitutionName != null){
			querySb.append( " AND btls.paymentInstitutionName=:paymentInstitutionName");
			parameters.put("paymentInstitutionName", paymentInstitutionName);
		}
		
		String outlierChannelName = queryModel.getOutlierChannelName();
		if(StringUtils.isNotBlank(outlierChannelName)){
			querySb.append( " AND btls.outlierChannelName=:outlierChannelName");
			parameters.put("outlierChannelName", outlierChannelName);
		}
		
		AccountSide accountSide = queryModel.getAccountSideEnum();
		if(accountSide != null){
			querySb.append( " AND btls.accountSide=:accountSide");
			parameters.put("accountSide", accountSide);
		}
		
		String keyWord = queryModel.getKeyWord();
		if(StringUtils.isNotBlank(keyWord)){
			querySb.append( " AND btls.bankName LIKE :keyWord");
			parameters.put("keyWord", "%"+keyWord+"%");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankTransactionLimitSheet> getBankTransactionLimitSheetBy(PaymentInstitutionName paymentInstitutionName, AccountSide accountSide, String outlierChannelName, Page page) {
		if(StringUtils.isBlank(outlierChannelName) || accountSide == null || paymentInstitutionName ==null){
			return Collections.emptyList();
		}
		String queryStr = "FROM BankTransactionLimitSheet WHERE paymentInstitutionName =:paymentInstitutionName AND outlierChannelName =:outlierChannelName AND accountSide =:accountSide AND invalidTime IS NULL";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("paymentInstitutionName", paymentInstitutionName);
		param.put("outlierChannelName", outlierChannelName);
		param.put("accountSide", accountSide);
		if(page == null){
			return this.genericDaoSupport.searchForList(queryStr, param);
		}
		return this.genericDaoSupport.searchForList(queryStr, param, page.getBeginIndex(), page.getEveryPage());
	}
	
	@Override
	public int getBankTransactionLimitSheetCountBy(String paymentChannelUuid, AccountSide accountSide) {
		if(StringUtils.isBlank(paymentChannelUuid) || accountSide == null){
			return 0;
		}
		String queryStr = "FROM BankTransactionLimitSheet WHERE paymentChannelInformationUuid =:paymentChannelUuid AND accountSide =:accountSide AND invalidTime IS NULL";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("paymentChannelUuid", paymentChannelUuid);
		param.put("accountSide", accountSide);
		return this.genericDaoSupport.count(queryStr, param);
	}
	
}

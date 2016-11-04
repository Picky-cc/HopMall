package com.zufangbao.wellsfargo.silverpool.cashauditing.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.demo2do.core.utils.DateUtils;
import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.icbc.business.AppArriveRecord;
import com.zufangbao.sun.entity.icbc.business.CashFlowChannelType;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalAccount;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucher;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherCheckingLevel;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherCompleteness;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherQueryModel;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherSearchModel;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherStatus;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherType;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocument;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.JournalVoucherService;

@Service("journalVoucherService")
public class JournalVoucherServiceImpl extends GenericServiceImpl<JournalVoucher> implements JournalVoucherService {

	@Autowired
	private OrderService orderService;

	private static final Log logger = LogFactory.getLog(JournalVoucherServiceImpl.class);
	
	@Override
	public JournalVoucher getJournalVoucherByVoucherUUID(String journalVoucherUUID) {
		if(StringUtils.isEmpty(journalVoucherUUID)){
			return null;
		}
		String querySentence = "FROM JournalVoucher WHERE journalVoucherUuid=:journalVoucherUuid";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("journalVoucherUuid", journalVoucherUUID);
		List<JournalVoucher> journalVoucherList = genericDaoSupport.searchForList(querySentence,params);
		if(CollectionUtils.isEmpty(journalVoucherList)){
			return null;
		}
		return journalVoucherList.get(0);
	}
	
	@Override
	public void save(JournalVoucher journalVoucher) {
		genericDaoSupport.save(journalVoucher);
	}
	

	@Override
	public int issueVourcher(String journalVoucherUUID) {
		String querySentence = "UPDATE JournalVoucher SET status=:status, bookingAmount=sourceDocumentAmount WHERE journalVoucherUuid=:journalVoucherUuid";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("journalVoucherUuid", journalVoucherUUID);
		params.put("status",JournalVoucherStatus.VOUCHER_ISSUED);
		return genericDaoSupport.executeHQL(querySentence, params);
	}

	@Override
	public void issueVourcher(JournalVoucher journalVoucher) {
		journalVoucher.setStatus(JournalVoucherStatus.VOUCHER_ISSUED);
		journalVoucher.setBookingAmount(journalVoucher.getSourceDocumentAmount());
		journalVoucher.setIssuedTime(new Date());
		genericDaoSupport.save(journalVoucher);
	}

	@Override
	public boolean exists(String notificationRecordUuid) {
		if (StringUtils.isEmpty(notificationRecordUuid)){
			return false;
		}
		String querySentence = "Select count(*) From JournalVoucher WHERE notificationRecordUuid=:notificationRecordUuid";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("notificationRecordUuid", notificationRecordUuid);
		if(genericDaoSupport.searchForInt(querySentence, params) <= 0){
			return false;
		}
		return true;
	}

	@Override
	public List<JournalVoucher> getJournalVoucherByCashFlowSerialNoAndJournalAccount(String cashFlowSerialNo, JournalAccount journalAccount) {
		return getJournalVoucherByCashFlowSerialNoAndAccountSide(cashFlowSerialNo, journalAccount.getAccountSide());
	}

	private List<JournalVoucher> getJournalVoucherByCashFlowSerialNoAndAccountSide(String cashFlowSerialNo, AccountSide accountSide) {
		if (StringUtils.isEmpty(cashFlowSerialNo)){
			return null;
		}
		String querySentence = "From JournalVoucher WHERE cashFlowSerialNo=:cashFlowSerialNo AND accountSide=:accountSide";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("cashFlowSerialNo", cashFlowSerialNo);
		params.put("accountSide", accountSide);
		return genericDaoSupport.searchForList(querySentence, params);
		
	}


	@Override
	public List<JournalVoucher> getJournalVoucherBySourceDocumentCashFlowSerialNoAndJournalAccount(
			String source_document_cash_flow_serial_no, JournalAccount journalAccount) {
		if (StringUtils.isEmpty(source_document_cash_flow_serial_no)){
			return null;
		}
		String querySentence = "From JournalVoucher WHERE source_document_cash_flow_serial_no=:source_document_cash_flow_serial_no AND accountSide =:accountSide ";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("source_document_cash_flow_serial_no", source_document_cash_flow_serial_no);
		params.put("accountSide", journalAccount.getAccountSide());
		return  genericDaoSupport.searchForList(querySentence, params);
	}
	
	
	@Override
	public int countJournalVoucherBySourceDocumentCashFlowSerialNoAndJournalAccount(
			String source_document_cash_flow_serial_no, JournalAccount journalAccount) {
		if (StringUtils.isEmpty(source_document_cash_flow_serial_no)){
			return 0;
		}
		String querySentence = "SELECT COUNT(*) From JournalVoucher WHERE source_document_cash_flow_serial_no=:source_document_cash_flow_serial_no AND accountSide =:accountSide ";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("source_document_cash_flow_serial_no", source_document_cash_flow_serial_no);
		params.put("accountSide", journalAccount.getAccountSide());
		return  genericDaoSupport.searchForInt(querySentence, params);
	}
	

	@Override
	public boolean existCashFlow(String cashFlowUid) {
		return getJournalVoucherByCashFlowUid(cashFlowUid) != null;
	}

	@Override
	public void saveCashFlow(AppArriveRecord appArriveRecord) {
		
		if(null == appArriveRecord){
			
			return;
		}
		if(existCashFlow(appArriveRecord.getCashFlowUid())){
			
			return;
		}
		
		List<JournalVoucher> notifications = getNotificationByCashFlow(appArriveRecord);
		
		JournalVoucherCompleteness journalVoucherCompleteness = getJournalVoucherCompletenessBy(notifications);
		
		resolveJournalVoucherAccordingBy(journalVoucherCompleteness, notifications, appArriveRecord);
	}
	private JournalVoucherCompleteness getJournalVoucherCompletenessBy(List<JournalVoucher> notifications){
		
		if(CollectionUtils.isEmpty(notifications)){
			
			return JournalVoucherCompleteness.NOTIFICATION_MISSING;
		}
		if(notifications.size() == 1){
			
			return JournalVoucherCompleteness.COMPLETE;
		}
		return JournalVoucherCompleteness.NOTIFICATION_AMBIGUOUS;
	}
	
	private void resolveJournalVoucherAccordingBy(JournalVoucherCompleteness journalVoucherCompleteness,List<JournalVoucher> notifications,AppArriveRecord appArriveRecord){
		
		if(journalVoucherCompleteness == JournalVoucherCompleteness.NOTIFICATION_MISSING){
			
			JournalVoucher journalVoucher = new JournalVoucher();
			journalVoucher.createFromCashFlow(appArriveRecord);
			
			journalVoucher.setCompleteness(journalVoucherCompleteness);
			
			this.save(journalVoucher);
		}
		if(journalVoucherCompleteness == JournalVoucherCompleteness.COMPLETE){
			
			JournalVoucher journalVoucherInDB = notifications.get(0);
			
			if(journalVoucherCompleteness != journalVoucherInDB.getCompleteness()){
				
				journalVoucherInDB.copyDataFromCashFlow(appArriveRecord);
				
				journalVoucherInDB.setCompleteness(journalVoucherCompleteness);
				
				this.saveOrUpdate(journalVoucherInDB);
			}
		}
		if(journalVoucherCompleteness == JournalVoucherCompleteness.NOTIFICATION_AMBIGUOUS){
			
			for (JournalVoucher journalVoucher : notifications) {
				
				if(journalVoucherCompleteness == journalVoucher.getCompleteness()){
					
					continue;
				}
				
				journalVoucher.copyDataFromCashFlow(appArriveRecord);
				
				journalVoucher.setCompleteness(journalVoucherCompleteness);
				
				this.saveOrUpdate(journalVoucher);
			}
		}
	}
	
	@Override
	public JournalVoucher getJournalVoucherByCashFlowUid(String cashFlowUid) {
		
		if(StringUtils.isEmpty(cashFlowUid)){
			
			return null;
			
		}
		Filter filter = new Filter();
		
		filter.addEquals("cashFlowUuid", cashFlowUid);
		
		List<JournalVoucher> journalVouchers = this.list(JournalVoucher.class, filter);
		
		if(CollectionUtils.isEmpty(journalVouchers)){
			
			return null;
		}
		
		return journalVouchers.get(0);
	}

	@Override
	public boolean isExistNotificationByCashFlow(AppArriveRecord cashFlow) {
		return CollectionUtils.isNotEmpty(getNotificationByCashFlow(cashFlow));
		
	}

	@Override
	public List<JournalVoucher> getNotificationByCashFlow(AppArriveRecord cashFlow) {
		
		if(null == cashFlow){
			
			return null;
		}
		Filter filter = new Filter();
		
		filter.addEquals("sourceDocumentIdentity", cashFlow.getSerialNo());
		filter.addEquals("sourceDocumentCashFlowSerialNo", cashFlow.getVouhNo());
		filter.addEquals("sourceDocumentAmount",cashFlow.getAmount());
		
		List<JournalVoucher> journalVouchers = this.list(JournalVoucher.class, filter);
		
		return journalVouchers;
	}

	@Override
	public boolean isJournalVoucherComplete(JournalVoucher notification,
			AppArriveRecord cashFlow) {
		
		if(null == notification || null == cashFlow){
			
			return false;
			
		}
		return !StringUtils.isEmpty(notification.getSourceDocumentIdentity()) &&
				!StringUtils.isEmpty(cashFlow.getSerialNo())				  &&//商户凭证号 			
				!StringUtils.isEmpty(notification.getSourceDocumentCashFlowSerialNo())  &&
				!StringUtils.isEmpty(cashFlow.getVouhNo())					 &&	//支付平台流水号
				(notification.getSourceDocumentAmount() != null)			&& 
				(cashFlow.getAmount() != null);									//交易金额
//		       &&
//		       (notification.getNotifiedDate() == cashFlow.getTime());//交易时间
	}

	@Override
	public List<JournalVoucher> getVouchersByBillingPlanUidAndIssued(
			String billingPlanUuid, Long companyId) {
		
		if(StringUtils.isEmpty(billingPlanUuid)){
			
			return Collections.emptyList();
		}
		
		Filter filter = new Filter();
		filter.addEquals("companyId", companyId);
		filter.addEquals("billingPlanUuid", billingPlanUuid);
		filter.addEquals("status", JournalVoucherStatus.VOUCHER_ISSUED);
		
		return this.list(JournalVoucher.class, filter);
	}

	@Override
	public JournalVoucher getJournalVoucherByNotificationUUIDAndJournalAccount(String notificationRecordUuid, JournalAccount journalAccount) {
		if (StringUtils.isEmpty(notificationRecordUuid)){
			return null;
		}
		String querySentence = "From JournalVoucher where notificationRecordUuid=:notificationRecordUuid And accountSide=:accountSide";
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("notificationRecordUuid", notificationRecordUuid);
		map.put("accountSide", journalAccount.getAccountSide());
		List<JournalVoucher> voucherList =  genericDaoSupport.searchForList(querySentence,map);
		if (CollectionUtils.isEmpty(voucherList)){
			return null;
		}
		return voucherList.get(0);
	}


	@Override
	public int fillBusinessVoucherUuid(String businessVoucherUuid,String businessVoucherTypeUuid, List<JournalVoucher> journalVouchers) {
		
		if(StringUtils.isEmpty(businessVoucherUuid) || StringUtils.isEmpty(businessVoucherTypeUuid) || CollectionUtils.isEmpty(journalVouchers)){
			
			return 0;
		}
		
		List<String> journalVoucherUuids = new ArrayList<String>();
		
		for(JournalVoucher journalVoucher : journalVouchers){
			
			journalVoucherUuids.add(journalVoucher.getJournalVoucherUuid());
		}
		String sentence = "UPDATE JournalVoucher SET businessVoucherUuid = :businessVoucherUuid ,businessVoucherTypeUuid = :businessVoucherTypeUuid WHERE journalVoucherUuid IN (:journalVoucherUuids)";
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		parameters.put("businessVoucherUuid", businessVoucherUuid);
		parameters.put("businessVoucherTypeUuid", businessVoucherTypeUuid);
		parameters.put("journalVoucherUuids", journalVoucherUuids);
		
		return genericDaoSupport.executeHQL(sentence, parameters);
	}

	@Override
	public JournalVoucher refreshCompletenes(JournalVoucher journalVoucher) {
		if (null == journalVoucher){
			return null;
		}
		String notifiedCashFlowSerialNo = journalVoucher.getSourceDocumentCashFlowSerialNo();
		BigDecimal notifiedAmount = journalVoucher.getSourceDocumentAmount();
		
		String notificationIdentity = journalVoucher.getNotificationIdentity();
		String cashFlowSerialNo = journalVoucher.getCashFlowSerialNo();
		BigDecimal cashFlowAmount = journalVoucher.getCashFlowAmount();
		
		if (StringUtils.isEmpty(notifiedCashFlowSerialNo) || null == notifiedAmount){
			journalVoucher.setCompleteness(JournalVoucherCompleteness.NOTIFICATION_MISSING);
			return journalVoucher;
		}
		if(StringUtils.isEmpty(notificationIdentity) ||	StringUtils.isEmpty(cashFlowSerialNo) || cashFlowAmount==null){
			journalVoucher.setCompleteness(JournalVoucherCompleteness.CASHFLOW_MISSING);
			return journalVoucher;
		}
		JournalAccount journalAccount = new JournalAccount(journalVoucher.getAccountSide());
		int existedVoucherSize =  countJournalVoucherBySourceDocumentCashFlowSerialNoAndJournalAccount(notifiedCashFlowSerialNo, journalAccount);
		if(existedVoucherSize>1){
			journalVoucher.setCompleteness(JournalVoucherCompleteness.NOTIFICATION_AMBIGUOUS);
			return journalVoucher;
		}
		
		journalVoucher.setCompleteness(JournalVoucherCompleteness.COMPLETE);
		return journalVoucher;
	}

	@Override
	public List<JournalVoucher> getJournalVoucherListByVoucherUUIDList(
			List<String> voucherUuidList) {
		List<JournalVoucher> voucherList = new ArrayList<JournalVoucher>();
		if (CollectionUtils.isEmpty(voucherUuidList)){
			return voucherList;
		}
		for (String voucherUuid : voucherUuidList){
			JournalVoucher voucher = getJournalVoucherByVoucherUUID(voucherUuid);
			if (voucher != null){
				voucherList.add(voucher);
			}
		}
		return voucherList;
	}
	
	@Override
	public Date getLastPaidTimeByIssuedJournalVoucherAndJournalAccount(String billingPlanUuid, Long companyId, JournalAccount journalAccount) {
		if(StringUtils.isEmpty(billingPlanUuid) || companyId==null){
			return null;
		}
		String querySentence = "From JournalVoucher where billingPlanUuid=:billingPlanUuid AND companyId=:companyId AND status=:status AND accountSide=:accountSide ORDER BY tradeTime DESC";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("status", JournalVoucherStatus.VOUCHER_ISSUED);
		params.put("billingPlanUuid", billingPlanUuid);
		params.put("accountSide", journalAccount.getAccountSide());
		params.put("companyId", companyId);
		List<JournalVoucher> journalVoucherList = genericDaoSupport.searchForList(querySentence, params);
		if(CollectionUtils.isEmpty(journalVoucherList) || journalVoucherList.get(0)==null){
			return null;
		}
		return journalVoucherList.get(0).getTradeTime();
	}
	
	@Override
	public List<JournalVoucher> getJournalVoucherBy(String cashFlowUuid, AccountSide accountSide,JournalVoucherCompleteness completeness, Long companyId, JournalVoucherStatus status) {
		Filter filter = new Filter();
		if (!StringUtils.isEmpty(cashFlowUuid)){
			filter.addEquals("cashFlowUuid", cashFlowUuid);
		}
		if (accountSide != null){
			filter.addEquals("accountSide", accountSide);
		}
		if (completeness != null){
			filter.addEquals("completeness", completeness);
		}
		if (companyId != null){
			filter.addEquals("companyId", companyId);
		}
		if (status != null){
			filter.addEquals("status", status);
		}
		return this.list(JournalVoucher.class, filter);
	}

	@Override
	public BigDecimal getBookingAmountSumOfIssueJournalVoucherBy(String billingPlanUuid, Long companyId, JournalAccount journalAccount) {
		if (StringUtils.isEmpty(billingPlanUuid) || companyId==null){
			return BigDecimal.ZERO;
		}

		String querySentence = "From JournalVoucher where billingPlanUuid=:billingPlanUuid AND companyId=:companyId AND status=:status AND accountSide=:accountSide";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("status", JournalVoucherStatus.VOUCHER_ISSUED);
		params.put("billingPlanUuid", billingPlanUuid);
		params.put("accountSide", journalAccount.getAccountSide());
		params.put("companyId", companyId);
		List<JournalVoucher> jvList =  genericDaoSupport.searchForList(querySentence, params);
		return getTotalAmountFrom(jvList);
	}

	@Override
	public List<JournalVoucher> getIssuedJVListBy(String cashFlowUuid, JournalAccount journalAccount) {
		if (StringUtils.isEmpty(cashFlowUuid)){
			return Collections.EMPTY_LIST;
		}
		
		Filter filter = new Filter();
		filter.addEquals("cashFlowUuid", cashFlowUuid);
		filter.addEquals("accountSide", journalAccount.getAccountSide());
		filter.addEquals("status", JournalVoucherStatus.VOUCHER_ISSUED);
		
		return this.list(JournalVoucher.class, filter);
	}

	@Override
	public void lapseJournalVoucher(JournalVoucher journalVoucher) {
		if (journalVoucher == null || journalVoucher.getId() == null){
			return;
		}
		journalVoucher.setStatus(JournalVoucherStatus.VOUCHER_LAPSE);
		this.save(journalVoucher);
	}
	
	@Override
	//TODO log when lapse
	public void lapseJournalVoucherList(List<JournalVoucher> journalVoucherList) {
		if(CollectionUtils.isEmpty(journalVoucherList)){
			return;
		}
		for (JournalVoucher journalVoucher:journalVoucherList){
			if (journalVoucher == null){
				continue;
			}
			lapseJournalVoucher(journalVoucher);
			logger.info("Lapse journalVoucher with journalVoucherUuid["+journalVoucher.getJournalVoucherUuid()+"].");
		}
		
	}

	@Override
	public Set<String> getJournalVoucherUuidsFrom(List<JournalVoucher> journalVoucherList) {
		Set<String> journalVoucherUuids = new HashSet<String>();
		if(CollectionUtils.isEmpty(journalVoucherList)){
			return journalVoucherUuids;
		}
		for(JournalVoucher journalVoucher: journalVoucherList){
			if(journalVoucher == null){
				continue;
			}
			if(StringUtils.isEmpty(journalVoucher.getJournalVoucherUuid())){
				continue;
			}
			journalVoucherUuids.add(journalVoucher.getJournalVoucherUuid());
		}
		return journalVoucherUuids;
		
	}

	@Override
	public List<String> getBillUuidsInDebitIssuedJVListBy(String cashFlowUuid,	Collection<String> billingPlanUuids) {
		List<String> journalVoucherList = new ArrayList<String>();
		if(StringUtils.isEmpty(cashFlowUuid) || CollectionUtils.isEmpty(billingPlanUuids)){
			return journalVoucherList;
		}
		String querySentence = "SELECT billing_plan_uuid From journal_voucher where cash_flow_uuid=:cashFlowUuid And status=:status And billing_plan_uuid IN (:billingPlanUuids)";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("status", JournalVoucherStatus.VOUCHER_ISSUED.ordinal());
		params.put("cashFlowUuid", cashFlowUuid);
		params.put("billingPlanUuids", billingPlanUuids);
		return genericDaoSupport.queryForSingleColumnList(querySentence, params, String.class);
	}

	@Override
	public JournalVoucher getIssuedJournalVoucherBy(String journalVoucherUuid, JournalAccount journalAccount) {
		if(StringUtils.isEmpty(journalVoucherUuid)){
			return null;
		}
		Filter filter = new Filter();
		filter.addEquals("journalVoucherUuid", journalVoucherUuid);
		filter.addEquals("status",JournalVoucherStatus.VOUCHER_ISSUED);
		filter.addEquals("accountSide",journalAccount.getAccountSide());
		
		List<JournalVoucher> journalVoucherList = this.list(JournalVoucher.class, filter);
		if (CollectionUtils.isEmpty(journalVoucherList)){
			return null;
		}
		return journalVoucherList.get(0);
	}

	@Override
	public Set<String> getBillingPlanUuidsFrom(List<JournalVoucher> journalVoucherList) {
		Set<String> billingPlanUuids = new HashSet<String>();
		if(CollectionUtils.isEmpty(journalVoucherList)){
			return billingPlanUuids;
		}
		for(JournalVoucher journalVoucher:journalVoucherList){
			if(journalVoucher == null){
				continue;
			}
			billingPlanUuids.add(journalVoucher.getBillingPlanUuid());
		}
		return billingPlanUuids;
	}

	@Override
	public BigDecimal getIssuedAmountByIssueJV(String cashFlowUuid, JournalAccount journalAccount) {
		if (StringUtils.isEmpty(cashFlowUuid)){
			return BigDecimal.ZERO;
		}
		List<JournalVoucher> jvList = getJournalVoucherBy(cashFlowUuid, journalAccount.getAccountSide(), null, null, JournalVoucherStatus.VOUCHER_ISSUED);
		if (CollectionUtils.isEmpty(jvList)){
			return BigDecimal.ZERO; 
		}
		return getTotalAmountFrom(jvList);
		
	}
	private BigDecimal getTotalAmountFrom(List<JournalVoucher> jvList){
		BigDecimal totalAmount = BigDecimal.ZERO;
		if(CollectionUtils.isEmpty(jvList)){
			return totalAmount;
		}
		for(JournalVoucher journalVoucher:jvList){
			if(journalVoucher==null){
				continue;
			}
			if(journalVoucher.getBookingAmount() == null){
				continue;
			}
			totalAmount = totalAmount.add(journalVoucher.getBookingAmount());
		}
		return totalAmount;
	}

	private final static Map<Integer,List<CashFlowChannelType>> voucherTypeToCashFlowTypeMapping =new HashMap<Integer, List<CashFlowChannelType>>()
			{{
				put(1,Arrays.asList(CashFlowChannelType.Alipay,CashFlowChannelType.Unionpay));
				put(2,Arrays.asList(CashFlowChannelType.DirectBank));
			}};
			
	
	private Date createFullDateByDay(Date day,String time) {
		String strDate = DateUtils.format(day, "yyyy-MM-dd ");
		strDate = strDate + time;
		Date date = DateUtils.parseDate(strDate, "yyyy-MM-dd HH:mm:ss");
		return date;
	}

	@Override
	public List<JournalVoucher> getInForceJournalVoucherListBy(String billingPlanUuid, String businessVoucherType) {
		if(StringUtils.isEmpty(billingPlanUuid) || StringUtils.isEmpty(businessVoucherType)) {
			return Collections.EMPTY_LIST;
		}
		
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("businessVoucherType", businessVoucherType);
		parms.put("billingPlanUuid", billingPlanUuid);
		parms.put("JournalVoucherStatus", JournalVoucherStatus.VOUCHER_ISSUED);
		
		return genericDaoSupport.searchForList("FROM JournalVoucher WHERE billingPlanUuid =:billingPlanUuid AND businessVoucherTypeUuid =:businessVoucherType AND status =:JournalVoucherStatus", parms);
	}
	public List<JournalVoucher> getJournalVoucherBy(String sourceDocumentUuid, AccountSide accountSide, JournalVoucherStatus journalVoucherStatus, Long companyId){
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("sourceDocumentUuid", sourceDocumentUuid);
		parms.put("accountSide", accountSide);
		parms.put("status", journalVoucherStatus);
		parms.put("companyId", companyId);
		
		return genericDaoSupport.searchForList("FROM JournalVoucher WHERE sourceDocumentUuid =:sourceDocumentUuid AND accountSide =:accountSide AND status =:status AND companyId=:companyId", parms);
	}
	
	@Override
	public JournalVoucher getInForceJournalVoucherBy(Long companyId, String billingPlanUuid, String sourceDocumentIdentity, String businessVoucherType) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("billingPlanUuid", billingPlanUuid);
		parms.put("companyId", companyId);
		parms.put("sourceDocumentIdentity", sourceDocumentIdentity);
		parms.put("businessVoucherType", businessVoucherType);
		parms.put("JournalVoucherStatus", JournalVoucherStatus.VOUCHER_LAPSE);
		
		List<JournalVoucher> journalVoucherList = genericDaoSupport.searchForList("FROM JournalVoucher WHERE billingPlanUuid =:billingPlanUuid AND companyId =:companyId AND sourceDocumentIdentity =:sourceDocumentIdentity AND businessVoucherTypeUuid =:businessVoucherType AND status <>:JournalVoucherStatus", parms);
		
		if(CollectionUtils.isEmpty(journalVoucherList)){
			return null;
		}
		return journalVoucherList.get(0);
	}
	
	@Override
	public List<JournalVoucher> getInForceJournalVoucherListBy(JournalVoucherQueryModel jvqModel, Date startIssuedDate, Date endIssuedDate) {

		if(null == jvqModel || null == startIssuedDate || null == endIssuedDate) {
			return Collections.emptyList();
		}
		
		Date newStartDate = createFullDateByDay(startIssuedDate,"00:00:00");
		Date newEndDate = createFullDateByDay(endIssuedDate,"23:59:59");
		
		Map<String,Object> parms = new HashMap<String,Object>();
		parms.put("startDate", newStartDate);
		parms.put("endDate", newEndDate);
		parms.put("status", JournalVoucherStatus.VOUCHER_ISSUED);
		
		StringBuffer querySentence = new StringBuffer("From JournalVoucher WHERE (issuedTime BETWEEN :startDate AND :endDate) AND status =:status");
		
		if(null != jvqModel) {
			int voucherType = jvqModel.getType();
			List<CashFlowChannelType> cashFlowChannelTypes = voucherTypeToCashFlowTypeMapping.get(voucherType);
			parms.put("cashFlowChannelTypes", cashFlowChannelTypes);
			querySentence.append(" AND cashFlowChannelType IN (:cashFlowChannelTypes)");
		}
		
		if(null != jvqModel.getCompanyId()) {
			parms.put("companyId", jvqModel.getCompanyId());
			querySentence.append(" AND companyId = :companyId");
		}

		return genericDaoSupport.searchForList(querySentence.toString(), parms);
	}

	/** new method begin */
	
	@Override
	public boolean existsJVWithSourceDocumentUuid(String sourceDocumentUuid, Long companyId, AccountSide accountSide, String billingPlanUuid) {
		Filter filter = new Filter();
		filter.addEquals("sourceDocumentUuid", sourceDocumentUuid);
		filter.addEquals("companyId", companyId);
		filter.addEquals("accountSide", accountSide);
		filter.addEquals("billingPlanUuid", billingPlanUuid);
		List<JournalVoucher> journalVoucherList = this.list(JournalVoucher.class, filter);
		if(CollectionUtils.isEmpty(journalVoucherList)){
			return false;
		}
		return true;
	}
	
	@Override
	public JournalVoucher createIssuedJournalVoucherBySourceDocument(
			String billingPlanUuid, SourceDocument sourceDocument,
			String businessVoucherTypeUuid, BigDecimal bookingAmount, String businessVoucherUuid, JournalVoucherType journalVoucherType) {
		
		JournalVoucher newjournalVoucher = new JournalVoucher();

		newjournalVoucher.copyFromSourceDocument(sourceDocument);
		newjournalVoucher.fill_voucher_and_booking_amount(billingPlanUuid, businessVoucherTypeUuid, businessVoucherUuid,
				bookingAmount, JournalVoucherStatus.VOUCHER_ISSUED, newjournalVoucher.getCheckingLevel(), journalVoucherType);
//		newjournalVoucher.setJournalVoucherType(journalVoucherType);
		this.save(newjournalVoucher);
		
		return newjournalVoucher;
	}
	
	@Override
	@Deprecated
	public JournalVoucher createIssuedJournalVoucherByCashFlow(String billingPlanUuid, AppArriveRecord appArriveRecord, String businessVoucherTypeUuid, BigDecimal bookingAmount,String businessVoucherUuid, JournalVoucherCheckingLevel journalVoucherCheckingLevel){
		JournalVoucher newjournalVoucher = new JournalVoucher();
		newjournalVoucher.createFromCashFlow(appArriveRecord);
		
		newjournalVoucher.fill_voucher_and_booking_amount(billingPlanUuid, businessVoucherTypeUuid, businessVoucherUuid,
				bookingAmount, JournalVoucherStatus.VOUCHER_ISSUED, journalVoucherCheckingLevel, JournalVoucherType.ONLINE_DEDUCT_BACK_ISSUE);
		refreshCompletenes(newjournalVoucher);
		save(newjournalVoucher);
		return newjournalVoucher;
	}

	@Override
	public Set<String> extractSourceDocumentUuid(List<JournalVoucher> journalVoucherList) {
		Set<String> sourceDocumentUuid = new HashSet<String>();
		if(CollectionUtils.isEmpty(journalVoucherList)){
			return sourceDocumentUuid;
		}
		for (JournalVoucher journalVoucher : journalVoucherList) {
			if(journalVoucher==null){
				continue;
			}
			sourceDocumentUuid.add(journalVoucher.getSourceDocumentUuid());
		}
		return sourceDocumentUuid;
	}
	
	@Override
	public List<JournalVoucher> getJournalVoucherListBy(JournalVoucherSearchModel searchModel, int begin, int max) {
		if(searchModel==null){
			return Collections.emptyList();
		}
		StringBuffer query = new StringBuffer("From JournalVoucher where 1=1 ");
		Map<String,Object> params = new HashMap<String,Object>();
		if(!StringUtils.isEmpty(searchModel.getPayAccountNo())){
			query.append(" AND sourceDocumentCounterPartyAccount LIKE :payAccount ");
			params.put("payAccount", "%"+searchModel.getPayAccountNo()+"%");
		}
		if(!StringUtils.isEmpty(searchModel.getPayAccountName())){
			query.append(" AND sourceDocumentCounterPartyName LIKE :payName ");
			params.put("payName", "%"+searchModel.getPayAccountName()+"%");
		}
		if(searchModel.getJournalVoucherStatusEnum()!=null){
			query.append(" AND status = :status ");
			params.put("status", searchModel.getJournalVoucherStatusEnum());
		}
		if(searchModel.getAccountSideEnum()!=null){
			query.append(" AND accountSide = :accountSide ");
			params.put("accountSide", searchModel.getAccountSideEnum());
		}
		if(searchModel.getCashFlowChannelTypeEnum()!=null){
			query.append(" AND cashFlowChannelType = :cashFlowChannelType ");
			params.put("cashFlowChannelType", searchModel.getCashFlowChannelTypeEnum());
		}
		if(searchModel.getStartDate()!=null){
			query.append(" AND Date(tradeTime) >=:startDate ");
			params.put("startDate", searchModel.getStartDate());
		}
		if(searchModel.getEndDate()!=null){
			query.append(" AND Date(tradeTime) <=:endDate ");
			params.put("endDate", searchModel.getEndDate());
		}
		if(begin==0 || max==0){
			return genericDaoSupport.searchForList(query.toString(), params);
		}else {
			return genericDaoSupport.searchForList(query.toString(), params,begin,max);
		}
	}

	@Override
	public List<JournalVoucher> getJournalVoucherBySourceDocumentUuid(
			String sourceDocumentUuid) {
		Filter filter = new Filter();
		filter.addEquals("sourceDocumentUuid", sourceDocumentUuid);
		return  this.list(JournalVoucher.class, filter);
	}

	@Override
	public List<JournalVoucher> lapse_issued_jvs_of_cash_flow(String cashFlowUuid, AccountSide accountSide) {
		List<JournalVoucher> vouchersToBeLapse = getIssuedJVListBy(cashFlowUuid, new JournalAccount(accountSide));
		lapseJournalVoucherList(vouchersToBeLapse);
		return vouchersToBeLapse;
	}
	
	@Override
	public JournalVoucher getInforceDepositJournalVoucher(Long companyId, String sourceDocumentUuid){
		List<JournalVoucher> depositJournalVoucherList = getInforceJournalVoucherList(companyId, sourceDocumentUuid, AccountSide.DEBIT, JournalVoucherType.BANK_CASHFLOW_DEPOSIT);
		if(CollectionUtils.isEmpty(depositJournalVoucherList)){
			return null;
		}
		return depositJournalVoucherList.get(0);
	}
	
	private List<JournalVoucher> getInforceJournalVoucherList(Long companyId, String billingPlanUuid, AccountSide accountSide, JournalVoucherType journalVoucherType){
		StringBuffer query = new StringBuffer("From JournalVoucher where companyId=:companyId AND accountSide=:accountSide AND billingPlanUuid=:billingPlanUuid "
				+ " AND journalVoucherType=:journalVoucherType");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("companyId", companyId);
		params.put("accountSide", accountSide);
		params.put("billingPlanUuid", billingPlanUuid);
		params.put("journalVoucherType", journalVoucherType);
		return genericDaoSupport.searchForList(query.toString(),params);
	}

	@Override
	public List<JournalVoucher> getInforceThirdPartyDeductJournalVoucher(Long companyId, String billingPlanUuid){
		return getInforceJournalVoucherList(companyId, billingPlanUuid, AccountSide.DEBIT, JournalVoucherType.THIRD_PARTY_DEDUCT_VOUCHER);
	}

}

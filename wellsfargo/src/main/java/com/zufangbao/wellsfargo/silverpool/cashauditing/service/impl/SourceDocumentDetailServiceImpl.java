package com.zufangbao.wellsfargo.silverpool.cashauditing.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.persistence.support.Order;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.utils.EnumUtil;
import com.zufangbao.sun.utils.StringUtils;
import com.zufangbao.sun.yunxin.entity.api.VoucherSource;
import com.zufangbao.sun.yunxin.entity.model.voucher.VoucherQueryModel;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentDetail;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentDetailCheckState;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentDetailStatus;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentDetailService;

@Service("sourceDocumentDetailService")
public class SourceDocumentDetailServiceImpl extends GenericServiceImpl<SourceDocumentDetail> implements SourceDocumentDetailService {

	@Override
	public void cancelDetails(String sourceDocumentUuid, String firstNo, String secondNo) {
		String hql = "UPDATE SourceDocumentDetail SET status = :status Where sourceDocumentUuid = :sourceDocumentUuid AND status <> :status"
				+ " AND secondNo =:secondNo";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sourceDocumentUuid", sourceDocumentUuid);
		params.put("secondNo", secondNo);
		params.put("status",SourceDocumentDetailStatus.INVALID);
		if(!StringUtils.isEmpty(firstNo)) {
			hql += " AND firstNo = :firstNo";
			params.put("firstNo", firstNo);
		}
		genericDaoSupport.executeHQL(hql, params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> get_voucher_ids_by_first_type(VoucherQueryModel voucherQueryModel, String firstType, Page page) {
		if(StringUtils.isEmpty(firstType)) {
			return Collections.emptyList();
		}
		StringBuffer buffer = new StringBuffer("SELECT id FROM SourceDocumentDetail WHERE firstType = :firstType");
		buffer.append(build_business_payment_voucher_query_sql(voucherQueryModel));
		buffer.append(" GROUP BY sourceDocumentUuid, firstNo ORDER BY id");
		Map<String, Object> params = build_business_payment_voucher_query_params(voucherQueryModel, firstType);
		if(page == null) {
			return this.genericDaoSupport.searchForList(buffer.toString(), params);
		}else {
			return this.genericDaoSupport.searchForList(buffer.toString(), params, page.getBeginIndex(), page.getEveryPage());
		}
	}

	private Map<String,Object> build_business_payment_voucher_query_params(VoucherQueryModel voucherQueryModel, String firstType) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("firstType", firstType);
		if(voucherQueryModel.queryType()) {
			params.put("secondType", voucherQueryModel.getVoucherTypeEnum().getKey());
		}
		if(voucherQueryModel.queryStatus()) {
			SourceDocumentDetailStatus status = EnumUtil.fromOrdinal(SourceDocumentDetailStatus.class, voucherQueryModel.getVoucherStatus());
			if(status != null) {
				params.put("status", status);
			}
		}
		if(voucherQueryModel.queryString(voucherQueryModel.getHostAccount())) {
			params.put("receivableAccountNo", "%" + voucherQueryModel.getHostAccount() + "%");
		}
		if(voucherQueryModel.queryString(voucherQueryModel.getCounterNo())) {
			params.put("paymentAccountNo", "%" + voucherQueryModel.getCounterNo() + "%");
		}
		if(voucherQueryModel.queryString(voucherQueryModel.getCounterName())) {
			params.put("paymentName", "%" + voucherQueryModel.getCounterName() + "%");
		}
		return params;
	}

	private String build_business_payment_voucher_query_sql(VoucherQueryModel voucherQueryModel) {
		StringBuffer buffer = new StringBuffer();
		if(voucherQueryModel.queryType()) {
			buffer.append(" AND secondType = :secondType");
		}
		if(voucherQueryModel.queryStatus()) {
			SourceDocumentDetailStatus status = EnumUtil.fromOrdinal(SourceDocumentDetailStatus.class, voucherQueryModel.getVoucherStatus());
			if(status != null) {
				buffer.append(" AND status = :status");
			}
		}
		if(voucherQueryModel.queryString(voucherQueryModel.getHostAccount())) {
			buffer.append(" AND receivableAccountNo LIKE :receivableAccountNo");
		}
		if(voucherQueryModel.queryString(voucherQueryModel.getCounterNo())) {
			buffer.append(" AND paymentAccountNo LIKE :paymentAccountNo");
		}
		if(voucherQueryModel.queryString(voucherQueryModel.getCounterName())) {
			buffer.append(" AND paymentName LIKE :paymentName");
		}
		return buffer.toString();
	}

	@Override
	public int countSameVersionDetails(SourceDocumentDetail detail) {
		
		String count_query = "select count(id) from SourceDocumentDetail where source_document_uuid = '"
				+ detail.getSourceDocumentUuid() + "' AND first_no='" + detail.getFirstNo() + "' AND second_no='"
				+ detail.getSecondNo() + "'";
		
		return this.genericDaoSupport.searchForInt(count_query);
	}
	@Override
	public List<SourceDocumentDetail> getSameVersionDetails(SourceDocumentDetail detail, Page page) {
		Filter filter = new Filter();
		filter.addEquals("sourceDocumentUuid", detail.getSourceDocumentUuid());
		filter.addEquals("firstNo", detail.getFirstNo());
		filter.addEquals("secondNo", detail.getSecondNo());
		Order order = new Order("comment", "desc");
		order.add("id", "asc");
		if(page == null) {
			return list(SourceDocumentDetail.class, filter, order);
		}else {
			return list(SourceDocumentDetail.class, filter, order, page);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public SourceDocumentDetail getSourceDocumentDetailBy(Long id) {
		String sql = "FROM SourceDocumentDetail WHERE id = :id";
		List<SourceDocumentDetail> result = this.genericDaoSupport.searchForList(sql, "id", id);
		if(CollectionUtils.isEmpty(result)) {
			return null;
		}
		return result.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SourceDocumentDetail> getDetailsBySourceDocumentUuid(String sourceDocumentUuid, String secondNo) {
		if(StringUtils.isEmpty(secondNo)){
			return Collections.emptyList();
		}
		Map<String, Object> params = new HashMap<>();
		String hql = "FROM SourceDocumentDetail WHERE sourceDocumentUuid = :sourceDocumentUuid AND secondNo = :secondNo AND status <> :status ";
		hql+=" ORDER BY id";
		params.put("sourceDocumentUuid", sourceDocumentUuid);
		params.put("secondNo", secondNo);
		params.put("status", SourceDocumentDetailStatus.INVALID);
	
		return this.genericDaoSupport.searchForList(hql, params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getDetailIdsBySourceDocumentUuid(String sourceDocumentUuid, String secondNo){
		if(StringUtils.isEmpty(secondNo)){
			return Collections.emptyList();
		}
		Map<String, Object> params = new HashMap<>();
		String hql = "SELECT id FROM SourceDocumentDetail WHERE sourceDocumentUuid = :sourceDocumentUuid AND secondNo = :secondNo AND status <> :status ORDER BY id";
		params.put("sourceDocumentUuid", sourceDocumentUuid);
		params.put("secondNo", secondNo);
		params.put("status", SourceDocumentDetailStatus.INVALID);
	
		return this.genericDaoSupport.searchForList(hql, params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean exist(String sourceDocumentUuid, String firstType, String secondNo){
		if(StringUtils.isEmpty(secondNo) || StringUtils.isEmpty(firstType)){
			return false;
		}
		String hql = "Select id FROM SourceDocumentDetail WHERE sourceDocumentUuid = :sourceDocumentUuid "
				+ " AND firstType=:firstType AND secondNo = :secondNo AND status <> :status ";
		Map<String, Object> params = new HashMap<>();
		params.put("sourceDocumentUuid", sourceDocumentUuid);
		params.put("firstType", firstType);
		params.put("secondNo", secondNo);
		params.put("status", SourceDocumentDetailStatus.INVALID);
		List<Long> detailIds = this.genericDaoSupport.searchForList(hql, params,0,1);
		if(CollectionUtils.isEmpty(detailIds)){
			return false;
		} else {
			return true;
		}
		
	}
	
	@Override
	public int countBusinessVouchers(VoucherQueryModel voucherQueryModel) {
		String firstType = VoucherSource.BUSINESS_PAYMENT_VOUCHER.getKey();
		return get_voucher_ids_by_first_type(voucherQueryModel, firstType, null).size();
	}

	@Override
	public boolean isSourceDocumentDetailsCheckFails(SourceDocumentDetail detail) {
		String count_query = "select count(id) from SourceDocumentDetail where source_document_uuid = '"
				+ detail.getSourceDocumentUuid() + "' AND first_no='" + detail.getFirstNo() + "' AND second_no='"
				+ detail.getSecondNo() + "' AND check_state='" + SourceDocumentDetailCheckState.CHECK_FAILS.ordinal() + "'";
		
		return this.genericDaoSupport.searchForInt(count_query) > 0;
	}

	@Override
	public SourceDocumentDetail getValidSourceDocumentDetail(String firstType,
			String firstNo, String secondType, String secondNo) {
		String querySentence = "From SourceDocumentDetail where firstType=:firstType and firstNo=:firstNo and status!=:invalid "
				+ " AND secondType=:secondType AND secondNo=:secondNo";
		Map<String, Object> params = new HashMap<>();
		params.put("firstType", firstType);
		params.put("firstNo", firstNo);
		params.put("secondType", secondType);
		params.put("secondNo", secondNo);
		params.put("invalid", SourceDocumentDetailStatus.INVALID);
		List<SourceDocumentDetail> details = genericDaoSupport.searchForList(querySentence, params);
		if(CollectionUtils.isEmpty(details)){
			return null;
		}
		return details.get(0);
	}

	@Override
	public int countActiveVouchers(VoucherQueryModel voucherQueryModel) {
		String firstType = VoucherSource.ACTIVE_PAYMENT_VOUCHER.getKey();
		return get_voucher_ids_by_first_type(voucherQueryModel, firstType, null).size();
	}

	@Override
	public BigDecimal getTotalAmountOfSourceDocumentDetail(String sourceDocumentUuid, String secondNo) {
		if(StringUtils.isEmpty(secondNo)){
			return BigDecimal.ZERO;
		}
		Map<String, Object> params = new HashMap<>();
		String hql = "Select sum(amount) FROM SourceDocumentDetail WHERE sourceDocumentUuid = :sourceDocumentUuid AND secondNo = :secondNo AND checkState = :checkState"
				+ " AND status =:status ";
		params.put("checkState", SourceDocumentDetailCheckState.CHECK_SUCCESS);
		params.put("sourceDocumentUuid", sourceDocumentUuid);
		params.put("secondNo", secondNo);
		params.put("status", SourceDocumentDetailStatus.SUCCESS);
	
		List<BigDecimal> amounts = genericDaoSupport.searchForList(hql, params);
		return processBigDecimalResult(amounts);
	}

	private BigDecimal processBigDecimalResult(List<BigDecimal> result) {
		if(CollectionUtils.isEmpty(result) || result.get(0) == null) {
		return BigDecimal.ZERO;
		}
		return (BigDecimal) result.get(0);
	}
	


	@Override
	public BigDecimal getVoucherAmountOfSourceDocument(String firstNo, String secondNo) {
		String hql = "Select sum(amount) FROM SourceDocumentDetail WHERE firstNo = :firstNo AND secondNo = :secondNo";
		Map<String, Object> params = new HashMap<>();
		params.put("firstNo", firstNo);
		params.put("secondNo", secondNo);
		List<BigDecimal> amounts = genericDaoSupport.searchForList(hql, params);
		return processBigDecimalResult(amounts);
	}

	@Override
	public void appendSourceDocumentUuid(String sourceDocumentUuid, String firstNo, String secondNo) {
		if(StringUtils.isEmpty(sourceDocumentUuid) || StringUtils.isEmpty(firstNo) || StringUtils.isEmpty(secondNo)) {
			return;
		}
		String hql = "update SourceDocumentDetail set sourceDocumentUuid =:sourceDocumentUuid where firstNo = :firstNo AND secondNo = :secondNo";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.putIfAbsent("sourceDocumentUuid", sourceDocumentUuid);
		parameters.putIfAbsent("firstNo", firstNo);
		parameters.putIfAbsent("secondNo", secondNo);
		genericDaoSupport.executeHQL(hql, parameters);
		
	}
}


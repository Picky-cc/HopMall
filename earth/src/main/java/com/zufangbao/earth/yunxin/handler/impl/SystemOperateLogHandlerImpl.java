package com.zufangbao.earth.yunxin.handler.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.utils.JsonUtils;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.service.PrincipalService;
import com.zufangbao.earth.yunxin.handler.SystemOperateLogHandler;
import com.zufangbao.sun.entity.financial.LoanBatch;
import com.zufangbao.sun.entity.icbc.business.AppArriveRecord;
import com.zufangbao.sun.service.AppArriveRecordService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.service.TransferApplicationService;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.OfflineBill;
import com.zufangbao.sun.yunxin.log.LogFunctionType;
import com.zufangbao.sun.yunxin.log.LogOperateType;
import com.zufangbao.sun.yunxin.log.RecordLogCore;
import com.zufangbao.sun.yunxin.log.SystemOperateLog;
import com.zufangbao.sun.yunxin.log.SystemOperateLogRequestParam;
import com.zufangbao.sun.yunxin.log.SystemOperateLogVO;
import com.zufangbao.sun.yunxin.log.UpdateContentDetail;
import com.zufangbao.sun.yunxin.service.LoanBatchService;
import com.zufangbao.sun.yunxin.service.OfflineBillService;
import com.zufangbao.sun.yunxin.service.SystemOperateLogService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.VoucherOperationInfo;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucher;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherStatus;

@Component("systemOperateLogHandler")
public class SystemOperateLogHandlerImpl implements SystemOperateLogHandler {

	@Autowired
	private RecordLogCore recordLogCore;
	@Autowired
	private PrincipalService principalService;
	@Autowired
	private SystemOperateLogService systemOperateLogService;
	@Autowired
	private TransferApplicationService transferApplicationService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private OfflineBillService offlineBillService;
	@Autowired
	private LoanBatchService loanBatchService;

	@Autowired
	private AppArriveRecordService appArriveRecordService;
	
	@Override
	public void generateSystemOperateLog(SystemOperateLogRequestParam param)
			throws Exception {

		switch (param.getLogOperateType()) {
		case UPDATE:
			getUpdateSystemLog(param);
			break;
		case EXPORT:
			getExportBatchSystemLog(param);
			break;
		default:
			getGeneralSystemLog(param);
			break;
		}

	}

	@Override
	public List<SystemOperateLogVO> getSystemOperateLogVOListBy(
			String objectUuid, Page page) {

		List<SystemOperateLog> systemOperateLogList = systemOperateLogService
				.getSystemOperateLogBy(objectUuid, page);

		List<SystemOperateLogVO> systemOperateLogVOList = new ArrayList<SystemOperateLogVO>();
		for (SystemOperateLog systemOperateLog : systemOperateLogList) {
			SystemOperateLogVO systemOperateLogVO = new SystemOperateLogVO(
					systemOperateLog, getOperateName(systemOperateLog));
			String recordContent = generateRecordContent(objectUuid,
					systemOperateLog);
			systemOperateLogVO.setRecordContent(recordContent);
			systemOperateLogVOList.add(systemOperateLogVO);
		}
		return systemOperateLogVOList;
	}

	@Override
	public void generateAssociateSystemLog(Principal principal, String ip,
			String offlineBillUuid, Map<String, Object> map) throws Exception {
		Filter filter = new Filter();
		filter.addEquals("offlineBillUuid", offlineBillUuid);
		OfflineBill offlineBill = offlineBillService.list(OfflineBill.class,
				filter).get(0);
		SystemOperateLog log = recordLogCore.insertNormalRecordLog(
				principal.getId(), ip, LogFunctionType.OFFLINEBILLASSOCIATE,
				LogOperateType.ASSOCIATE, offlineBill);
		log.setKeyContent(offlineBill.getOfflineBillNo());
		StringBuffer recordContent = new StringBuffer("支付单"
				+ log.getKeyContent() + "(单号)" + "关联单据");
		Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) { 
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator
					.next();
			String orderNo = entry.getKey();
			recordContent.append(orderNo + ",");
		}
		log.setRecordContent(recordContent.toString());
		systemOperateLogService.saveOrUpdate(log);

	}
	
	@Override
	public void generateCashFLowAuditSystemLog(VoucherOperationInfo voucherOperationInfo, AppArriveRecord appArriveRecord)throws Exception{
		SystemOperateLog log = recordLogCore.insertNormalRecordLog(
				voucherOperationInfo.getOperatorId(), voucherOperationInfo.getIp(), LogFunctionType.JOURNAL_VOUCHER_AUDIT,
				LogOperateType.AUDIT, appArriveRecord);
		log.setKeyContent(appArriveRecord.getSerialNo());
		StringBuffer recordContent = new StringBuffer("银行流水"
				+ log.getKeyContent() + "(流水号)" + "对账");
		for (JournalVoucher journalVoucher : voucherOperationInfo.getChangedJournalVoucherList()) {
			String opeartion = journalVoucher.getStatus()==JournalVoucherStatus.VOUCHER_ISSUED?"制证":"废证";
			String content = "凭证单号("+journalVoucher.getJournalVoucherUuid() + ")金额(" +journalVoucher.getBookingAmount()+")";
			recordContent.append(","+opeartion+content);
		}
		log.setRecordContent(recordContent.toString());
		systemOperateLogService.saveOrUpdate(log);
	}
	

	private void getExportBatchSystemLog(SystemOperateLogRequestParam param) {
		SystemOperateLog log = new SystemOperateLog(param.getPrincipalId(),
				param.getIpAddress(), param.getLogFunctionType(),
				LogOperateType.EXPORT);
		StringBuffer recordContenDetail = new StringBuffer();
		for (String uuid : param.getUuidList()) {
			recordContenDetail.append(uuid + ",");
		}
		log.setRecordContentDetail(recordContenDetail.toString());
		systemOperateLogService.saveOrUpdate(log);
	}

	private SystemOperateLog getUpdateSystemLog(
			SystemOperateLogRequestParam param) throws Exception {
		SystemOperateLog log = recordLogCore.generateUpdateRecordLog(
				param.getPrincipalId(), param.getIpAddress(),
				param.getLogFunctionType(), LogOperateType.UPDATE,
				param.getObject(), param.getEditObject(),
				param.getPersistentClass());
		log.setKeyContent(param.getKeyContent());
		log.setRecordContent(castUpdateRecordContent(
				param.getLogFunctionType(), log));
		systemOperateLogService.saveOrUpdate(log);
		return log;
	}

	private String generateRecordContent(String objectUuid,
			SystemOperateLog systemOperateLog) {
		String recordContent = new String();
		switch (systemOperateLog.getLogFunctionType()) {
		case ONLINEBILLEXPORTCHECKING:
			recordContent = "对账单导出" + "支付单"
					+ getTransferApplicationNo(objectUuid);
			break;
		case ONLINEBILLEXPORTDAILYRETURNLIST:
			recordContent = "每日还款清单导出" + "支付单"
					+ getTransferApplicationNo(objectUuid);
			break;
		case GUARANTEEEXPORT:
			recordContent = "导出" + "担保单" + getGuaranteeNo(objectUuid);
			break;
		case EXPORTREPAYEMNTPLAN:
			recordContent = "导出" + "还款计划" + getLoanBatchNo(objectUuid);
			break;
		default:
			recordContent = systemOperateLog.getRecordContent();
			break;
		}
		return recordContent;
	}

	private String getGuaranteeNo(String objectUuid) {
		if (orderService.getOrderByRepaymentBillId(objectUuid) != null) {
			return orderService.getOrderByRepaymentBillId(objectUuid)
					.getOrderNo();
		}
		;
		return null;

	}

	private String getLoanBatchNo(String objectUuid) {
		LoanBatch loanBatch = loanBatchService.getLoanBatchByUUid(objectUuid);
		if (loanBatch != null) {
			return loanBatch.getCode();
		}
		return null;
	}

	private String getTransferApplicationNo(String objectUuid) {
		if (transferApplicationService.getTransferApplicationListBy(objectUuid) != null) {
			return transferApplicationService.getTransferApplicationListBy(
					objectUuid).getTransferApplicationNo();
		}
		return null;
	}

	private String getOperateName(SystemOperateLog systemOperateLog) {
		return principalService.load(Principal.class,
				systemOperateLog.getUserId()).getName();
	}

	private String castUpdateRecordContent(LogFunctionType logfunctionType,
			SystemOperateLog log) {
		List<UpdateContentDetail> updateDetails = JsonUtils.parseArray(
				log.getUpdateContentDetail(), UpdateContentDetail.class);
		StringBuffer stringBuffer = new StringBuffer(
				LogMapRecordContentSpec.logFunctionTypeMatchRecordContentHeadName.get(logfunctionType)
						+ log.getKeyContent());
		for (UpdateContentDetail updateDetail : updateDetails) {
			stringBuffer.append(","
					+ LogMapRecordContentSpec.recordContentHeadNameMatchRecordContent.get(
							LogMapRecordContentSpec.logFunctionTypeMatchRecordContentHeadName
									.get(logfunctionType)).get(
							updateDetail.getFiledName()) + "由"
					+ updateDetail.getOldValue() + "改为"
					+ updateDetail.getNewValue());
		}
		return stringBuffer.toString();
	}

	private void getGeneralSystemLog(SystemOperateLogRequestParam param)
			throws Exception {
		SystemOperateLog log = recordLogCore.insertNormalRecordLog(
				param.getPrincipalId(), param.getIpAddress(),
				param.getLogFunctionType(), param.getLogOperateType(),
				param.getObject());
		log.setKeyContent(param.getKeyContent());
		log.setRecordContent(LogMapRecordContentSpec.logFunctionTypeMatchRecordContentHeadName
				.get(param.getLogFunctionType()) + log.getKeyContent());
		systemOperateLogService.saveOrUpdate(log);
	}

	@Override
	public List<SystemOperateLogVO> getLogVOListByUuid(String uuid) {
		List<SystemOperateLog> logList = systemOperateLogService.getLogsByUuid(uuid);
		List<SystemOperateLogVO> result = new ArrayList<>();
		for (SystemOperateLog log : logList) {
			String occurTime = DateUtils.format(log.getOccurTime(), DateUtils.LONG_DATE_FORMAT);
			String operateName = getOperateName(log);
			SystemOperateLogVO logVO = new SystemOperateLogVO(log.getRecordContent(), occurTime, operateName);
			result.add(logVO);
		}
		return result;
	}

}

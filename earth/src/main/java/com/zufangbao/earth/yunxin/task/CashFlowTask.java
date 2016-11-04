package com.zufangbao.earth.yunxin.task;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo2do.core.utils.DateUtils;
import com.suidifu.coffer.GlobalSpec;
import com.suidifu.coffer.entity.CashFlowResult;
import com.suidifu.coffer.entity.CashFlowResultModel;
import com.suidifu.coffer.entity.QueryCashFlowModel;
import com.suidifu.coffer.handler.pab.PABDirectBankHandler;
import com.zufangbao.sun.Constant.BankCorpEps;
import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.entity.directbank.USBKey;
import com.zufangbao.sun.entity.icbc.business.CashFlow;
import com.zufangbao.sun.entity.icbc.business.CashFlowChannelType;
import com.zufangbao.sun.entity.icbc.business.StrikeBalanceStatus;
import com.zufangbao.sun.ledgerbook.AccountSide;
import com.zufangbao.sun.service.AccountService;
import com.zufangbao.sun.service.CashFlowService;
import com.zufangbao.sun.service.USBKeyService;

@Component("cashFlowTask")
public class CashFlowTask {

	@Autowired
	private AccountService accountService;
	@Autowired
	private USBKeyService usbKeyService;
	@Autowired
	private PABDirectBankHandler pabDirectBankHandler;
	@Autowired
	private CashFlowService cashFlowService;

	private static Log logger = LogFactory.getLog(CashFlowTask.class);

	public void exeQueryCashFlow() {
		logger.info("start exeQueryCashFlow...");
		List<Account> accountList = accountService
				.listAccountWithScanCashFlowSwitchOn();
		for (Account account : accountList) {
			try {
				if (!BankCorpEps.PAB_CODE.equals(account.getBankCode())) {
					continue;
				}
				String usbUuid = account.getUsbUuid();
				if (StringUtils.isEmpty(usbUuid)) {
					logger.warn("could not find usbkey...");
					continue;
				}
				USBKey usbKey = usbKeyService.getUSBKeyByUUID(usbUuid);
				if (null == usbKey) {
					logger.warn("could not find usbkey...");
					continue;
				}
				Map<String, String> workParms = usbKey.getConfig();

				QueryCashFlowModel queryCashFlowModel = new QueryCashFlowModel(
						account.getAccountNo(), DateUtils.format(new Date(),
								"yyyyMMdd"), DateUtils.format(new Date(),
								"yyyyMMdd"), new Date());
				
				CashFlowResultModel cashFlowResultModel = pabDirectBankHandler.queryIntradayCashFlow(queryCashFlowModel,
						workParms);
				if(GlobalSpec.DEFAULT_FAIL_CODE.equals(cashFlowResultModel.getCommCode())) {
					logger.warn(cashFlowResultModel.getErrMsg());
					continue;
				}
				List<CashFlowResult> cashFlowResultList = cashFlowResultModel.getCashFlowResult();
				if(CollectionUtils.isEmpty(cashFlowResultList)) {
					logger.info(account.getAccountNo() + "intraday cash flow is empty...");
					continue;
				}
				
				for (CashFlowResult cashFlowResult : cashFlowResultList) {
					try {
						boolean iscashFlowExist = cashFlowService.isCashFlowAlreadyExist(cashFlowResult.getHostAccountNo(), cashFlowResult.getBankSequenceNo(), cashFlowResult.getTransactionAmount());
						if(iscashFlowExist) {
							continue;
						}
						
						AccountSide accountSide = com.suidifu.coffer.entity.AccountSide.CREDIT.equals(cashFlowResult.getAccountSide()) ? AccountSide.CREDIT : AccountSide.DEBIT;
						
						StrikeBalanceStatus strikeBalanceStatus = null == cashFlowResult.getStrikeBalanceStatus() ? null : com.suidifu.coffer.entity.StrikeBalanceStatus.NORMAL.equals(cashFlowResult.getStrikeBalanceStatus()) ? StrikeBalanceStatus.NORMAL : StrikeBalanceStatus.STRIKE;
						
						String tradeUuid = StringUtils.EMPTY;
						String remark = cashFlowResult.getRemark();
						if(StringUtils.isNotEmpty(remark)) {
							String regex = "\\((.*?)\\)";
							Pattern pattern = Pattern.compile(regex);
							Matcher matcher = pattern.matcher(remark);
							if(null != matcher && matcher.find(0)) {
								tradeUuid = matcher.group(1);
							}
						}
						
						CashFlow cashFlow = new CashFlow(UUID.randomUUID().toString(), CashFlowChannelType.DirectBank, null, null, cashFlowResult.getHostAccountNo(), cashFlowResult.getHostAccountName(), cashFlowResult.getCounterAccountNo(), cashFlowResult.getCounterAccountName(), cashFlowResult.getCounterAccountAppendix(), cashFlowResult.getCounterBankInfo(), accountSide, cashFlowResult.getTransactionTime(), cashFlowResult.getTransactionAmount(), cashFlowResult.getBalance(), cashFlowResult.getTransactionVoucherNo(), cashFlowResult.getBankSequenceNo(), cashFlowResult.getRemark(), cashFlowResult.getOtherRemark(), strikeBalanceStatus, tradeUuid);
						
						cashFlowService.save(cashFlow);
					} catch (Exception e) {
						e.printStackTrace();
						logger.warn("save cash flow failed, host account:" + cashFlowResult.getHostAccountNo() + "bankSequenceNo:" + cashFlowResult.getBankSequenceNo());
					}
					
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.warn("handle account:" + account.getAccountNo() + "failed...");
			}
		}

		logger.info("end exeQueryCashFlow...");
	}
}

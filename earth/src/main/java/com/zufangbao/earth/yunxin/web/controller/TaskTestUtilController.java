package com.zufangbao.earth.yunxin.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.demo2do.core.persistence.support.Order;
import com.zufangbao.earth.web.controller.BaseController;
import com.zufangbao.earth.yunxin.api.dataSync.task.DataSyncTask;
import com.zufangbao.earth.yunxin.task.AssetTask;
import com.zufangbao.earth.yunxin.task.OrderTask;
import com.zufangbao.earth.yunxin.task.PrepaymentTask;
import com.zufangbao.earth.yunxin.task.SettlementOrderTask;
import com.zufangbao.earth.yunxin.task.SmsTask;
import com.zufangbao.earth.yunxin.task.TransferApplicationTask;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.unionpay.UnionpayManualTransaction;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.yunxin.service.UnionpayBankConfigService;
import com.zufangbao.sun.yunxin.service.UnionpayManualTransactionService;

@Controller
@RequestMapping(value="manual-tasks")
public class TaskTestUtilController extends BaseController{
	
	@Autowired
	private AssetTask assetTask;
	
	@Autowired
	private TransferApplicationTask transferApplicationTask;
	
	@Autowired
	private OrderTask orderTask;
	
	@Autowired
	private SettlementOrderTask settlementOrderTask;
	
	@Autowired
	private SmsTask smsTask;
	
	@Autowired
	private UnionpayBankConfigService unionpayBankConfigService;
	
	@Autowired
	private UnionpayManualTransactionService unionpayManualTransactionService;
	
	@Autowired
	private FinancialContractService  financialContractService;
	
	@Autowired
	private PrepaymentTask prepaymentTask;
	
	@Autowired
	private DataSyncTask dataSyncTask;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView showTaskTestUtilView() {
		ModelAndView mav = new ModelAndView("manual-tasks");
		List<FinancialContract> financialContractList = financialContractService.loadAll(FinancialContract.class);
		mav.addObject("financialContractList" , financialContractList);
		return mav;
	}
	
	@RequestMapping(value = "evaluate-asset", method = RequestMethod.GET)
	public @ResponseBody String evaluateAsset() {
		try {
			assetTask.endYesterdayWorkAndStartTodayWork();
			return jsonViewResolver.jsonResult("［提前还款失败次日同步］&［评估资产、生成结算单］正常！");
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("［提前还款失败次日同步］&［评估资产、生成结算单］异常！");
		}
	}
	
	@RequestMapping(value = "trtask-today", method = RequestMethod.GET)
	public @ResponseBody String trtaskToday() {
		try {
			transferApplicationTask.todayRecycleAssetCreateTransferApplicationAndDeduct();
			return jsonViewResolver.jsonResult("正常扣款正常！");
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("正常扣款异常！");
		}
	}
	
	@RequestMapping(value = "trtask_overdue", method = RequestMethod.GET)
	public @ResponseBody String trtaskOverdue() {
		try {
			transferApplicationTask.overDueAssetCreateTransferApplicationAndDeduct();
			return jsonViewResolver.jsonResult("逾期扣款正常！");
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("逾期扣款异常！");
		}
	}
	
	@RequestMapping(value = "trtask_prepayment", method = RequestMethod.GET)
	public @ResponseBody String trtaskPrepayment() {
		try {
			prepaymentTask.execPrepaymentApplication();
			return jsonViewResolver.jsonResult("提前还款扣款正常！");
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("提前还款逾期扣款异常！");
		}
	}
	
	@RequestMapping(value = "sync-order-status", method = RequestMethod.GET)
	public @ResponseBody String syncOrderStatus() {
		try {
			orderTask.updateFailOrderStatus();
			return jsonViewResolver.jsonResult("日结算单状态同步正常！");
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("日结算单状态同步异常！");
		}
	}
	
	@RequestMapping(value = "guarantee-order", method = RequestMethod.GET)
	public @ResponseBody String guaranteeOrder() {
		try {
			orderTask.createGuaranteeOrder();
			return jsonViewResolver.jsonResult("生成担保单正常！");
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("生成担保单异常！");
		}
	}
	
	@RequestMapping(value = "settlement-order", method = RequestMethod.GET)
	public @ResponseBody String settlementOrder() {
		try {
			settlementOrderTask.createSettlementOrder();
			return jsonViewResolver.jsonResult("生成清算单正常！");
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("生成清算单异常！");
		}
	}
	
	@RequestMapping(value = "query-order-result", method = RequestMethod.GET)
	public @ResponseBody String queryOrderResult() {
		try {
			transferApplicationTask.singleQueryUnionpayDeductResult();
			return jsonViewResolver.jsonResult("查询订单结果正常！");
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("查询订单结果异常！");
		}
	}
	
	@RequestMapping(value = "create-sms", method = RequestMethod.GET)
	public @ResponseBody String createSms() {
		try {
			smsTask.createRemindAndOverDueSmsQuene();
			return jsonViewResolver.jsonResult("生成还款提醒短信正常！");
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("生成还款提醒短信异常！");
		}
	}
	
	@RequestMapping(value = "send-sms", method = RequestMethod.GET)
	public @ResponseBody String sendSms() {
		try {
			smsTask.sendSmsQuene();
			return jsonViewResolver.jsonResult("发送短信正常！");
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("发送短信异常！");
		}
	}
	
	@RequestMapping(value = "refresh-unionpay-bank-config", method = RequestMethod.GET)
	public @ResponseBody String refreshUnionpayBankConfig() {
		try {
			unionpayBankConfigService.cacheEvictUnionpayBankConfig();
			return jsonViewResolver.jsonResult("刷新银联银行配置正常！");
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("刷新银联银行配置异常！");
		}
	}
	
	@RequestMapping(value = "manual-transactions", method = RequestMethod.GET)
	public ModelAndView manualTransactions() {
		try {
			ModelAndView mav = new ModelAndView("manual-transactions");
			Order order = new Order("id", "DESC");
			List<UnionpayManualTransaction> transactions = unionpayManualTransactionService.list(UnionpayManualTransaction.class, order);
			mav.addObject("transactions", transactions);
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			return pageViewResolver.errorSpec();
		}
	}
	@RequestMapping(value = "data-sync", method = RequestMethod.GET)
	public @ResponseBody String syncDatas() {
		try {
			dataSyncTask.manualOperatecommandDataSync();			
			return jsonViewResolver.jsonResult("数据同步正常！");
			
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.jsonResult("数据同步异常！");
		}
	}
	
}

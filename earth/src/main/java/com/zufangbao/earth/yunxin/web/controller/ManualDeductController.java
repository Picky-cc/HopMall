package com.zufangbao.earth.yunxin.web.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.utils.JsonUtils;
import com.demo2do.core.web.interceptor.MenuSetting;
import com.demo2do.core.web.resolver.Secure;
import com.gnete.security.crypt.Crypt;
import com.gnete.security.crypt.CryptException;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.model.ManualDeductWebModel;
import com.zufangbao.earth.web.controller.BaseController;
import com.zufangbao.earth.yunxin.unionpay.component.IGZUnionPayApiComponent;
import com.zufangbao.earth.yunxin.unionpay.constant.GZUnionPayConstants.GZUnionPayBusinessCode;
import com.zufangbao.earth.yunxin.unionpay.model.AccountDetailQueryInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.BatchDeductInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.BatchQueryInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.RealTimeDeductInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.TransactionDetailQueryInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.TransactionDetailQueryResult;
import com.zufangbao.earth.yunxin.unionpay.model.basic.GZUnionPayResult;
import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.entity.company.Company;
import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.entity.directbank.USBKey;
import com.zufangbao.sun.entity.financial.PaymentChannel;
import com.zufangbao.sun.entity.unionpay.UnionpayManualTransaction;
import com.zufangbao.sun.handler.IDirectBankHandler;
import com.zufangbao.sun.service.AccountService;
import com.zufangbao.sun.service.AppService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.PaymentChannelService;
import com.zufangbao.sun.service.USBKeyService;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.utils.DirectBankHandlerFactory;
import com.zufangbao.sun.utils.GeneratorUtils;
import com.zufangbao.sun.yunxin.service.UnionpayManualTransactionService;

/**
 * 
 * @author zhushiyun
 *
 */
@Controller
@RequestMapping("manual-deduct")
public class ManualDeductController extends BaseController{

	@Autowired
	private PaymentChannelService paymentChannelService;
	
	@Autowired
	private IGZUnionPayApiComponent igzUnionPayApiComponent;
	
	@Autowired
	private USBKeyService usbKeyService;
	
	@Autowired
	private AppService appService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private FinancialContractService financialContractService;
	
	@Autowired
	private UnionpayManualTransactionService unionpayManualTransactionService;
	
	public static final int TransactionType_RealTimeDeduct = 0;
	public static final int TransactionType_BatchDeduct = 1;
	
	private static final Log logger = LogFactory.getLog(ManualDeductController.class);
	
	@RequestMapping(value="pre-deduct",method=RequestMethod.GET)
	public ModelAndView preDeduct(@Secure Principal principal) {
		try {
			List<PaymentChannel> paymentChannelList = paymentChannelService.list(PaymentChannel.class, new Filter());
			List<Account> accountList = accountService.listAccountWithScanCashFlowSwitchOn();
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("paymentChannelList", paymentChannelList);
			params.put("accountList", accountList);
			return pageViewResolver.pageSpec("manual-deduct",params);
		} catch (Exception e){
			e.printStackTrace();
		}
		return pageViewResolver.errorSpec();
	}
	
	@RequestMapping(value="deduct",method=RequestMethod.POST)
	@MenuSetting("submenu-payment-asset")
	public @ResponseBody String manualDeduct(@Secure Principal principal, @ModelAttribute ManualDeductWebModel manualDeductWebModel,
			@RequestParam(value="paymentChannelId",required=true) Long paymentChannelId,
			@RequestParam(value="transactionType",required=true) int transactionType) {
		try {
			PaymentChannel paymentChannel = paymentChannelService.load(PaymentChannel.class, paymentChannelId);
			if(paymentChannel==null){
				return jsonViewResolver.errorJsonResult("没有支付通道！");
			}
			if(!manualDeductWebModel.validMaxAmount()){
				return jsonViewResolver.errorJsonResult("金额必须小于等于1！");
			}
			String reqNo = GeneratorUtils.generateUnionPayNo();
			UnionpayManualTransaction unionpayManualTransaction = new UnionpayManualTransaction(manualDeductWebModel.getBankCode(), manualDeductWebModel.getAccountNo(),
					manualDeductWebModel.getAccountName(), manualDeductWebModel.getIdCardNum(), manualDeductWebModel.getProvince(),
					manualDeductWebModel.getCity(), manualDeductWebModel.getAmount(), manualDeductWebModel.getRemark(),
					reqNo, transactionType,
					paymentChannelId, "","","");
			Serializable manualTransactionId = unionpayManualTransactionService.save(unionpayManualTransaction);
			
			GZUnionPayResult gzUnionPayResult = deduct(paymentChannel, manualDeductWebModel, reqNo, transactionType);
			logger.info("==========人工扣款:transactionType["+transactionType+"],name["+principal.getName()+"],paymentChannelId["+paymentChannelId+"],reqNo["+reqNo+"],manualBatchDeductWebModel["+manualDeductWebModel+"].");
			logger.info("==========gzUnionPayResult:"+gzUnionPayResult);
			
			UnionpayManualTransaction unionpayManualTransactionInDB = unionpayManualTransactionService.load(UnionpayManualTransaction.class, manualTransactionId);
			unionpayManualTransactionInDB.setRequestPacket(gzUnionPayResult.getRequestPacket());
			unionpayManualTransactionInDB.setResponsePacket(gzUnionPayResult.getResponsePacket());
			unionpayManualTransactionService.update(unionpayManualTransactionInDB);
			
			return jsonViewResolver.sucJsonResult("gzUnionPayResult",gzUnionPayResult.toString());
		} catch (Exception e){
			logger.error("人工扣款失败.transactionType["+transactionType+"].");
			e.printStackTrace();
			
		}
		return jsonViewResolver.errorJsonResult("系统错误");
	}
	
	private GZUnionPayResult deduct(PaymentChannel paymentChannel, ManualDeductWebModel  manualDeductWebModel,String reqNo,int transactionType){
		GZUnionPayResult gzUnionPayResult = null;
		if(transactionType==TransactionType_BatchDeduct){
			BatchDeductInfoModel batchDeductInfoModel = getBatchDeductInfoModelBy(paymentChannel, manualDeductWebModel,reqNo);
			gzUnionPayResult = igzUnionPayApiComponent.execBatchDeduct(batchDeductInfoModel);
		} else {
			RealTimeDeductInfoModel realTimeDeductInfoModel = getRealTimeDeductInfoModelBy(reqNo,paymentChannel,  manualDeductWebModel);
			gzUnionPayResult = igzUnionPayApiComponent.execRealTimeDeductPacket(realTimeDeductInfoModel);
		}
		return gzUnionPayResult;
	}

	private BatchDeductInfoModel getBatchDeductInfoModelBy(PaymentChannel paymentChannel,
			ManualDeductWebModel manualBatchDeductWebModel,String reqNo) {
		BatchDeductInfoModel batchDeductInfoModel = new BatchDeductInfoModel(manualBatchDeductWebModel,paymentChannel,reqNo,GZUnionPayBusinessCode.REPAY_A_LOAN);
		return batchDeductInfoModel;
	}
	
	
	private RealTimeDeductInfoModel getRealTimeDeductInfoModelBy(String reqNo,PaymentChannel paymentChannel, ManualDeductWebModel manualDeductWebModel) {
		RealTimeDeductInfoModel realTimeDeductInfoModel;
		
		realTimeDeductInfoModel = new RealTimeDeductInfoModel(manualDeductWebModel, paymentChannel,reqNo, GZUnionPayBusinessCode.REPAY_A_LOAN);
		return realTimeDeductInfoModel;
	}
	@RequestMapping(value="query-deduct")
	@MenuSetting("submenu-payment-asset")
	public @ResponseBody String queryDeduct(@Secure Principal principal, 
			@RequestParam(value="queryReqNo",required=true) String queryReqNo,
			@RequestParam(value="paymentChannelId",required=true) Long paymentChannelId) {
		try {
			PaymentChannel paymentChannel = paymentChannelService.load(PaymentChannel.class, paymentChannelId);
			if(paymentChannel==null){
				return jsonViewResolver.errorJsonResult("没有支付通道！");
			}
			BatchQueryInfoModel batchQueryInfoModel = new BatchQueryInfoModel(paymentChannel, queryReqNo);
			GZUnionPayResult gzUnionPayResult = igzUnionPayApiComponent.execBatchQuery(batchQueryInfoModel);
			logger.info("==========人工查询扣款:  name["+principal.getName()+"],paymentChannelId["+paymentChannelId+"],queryReqNo["+queryReqNo+"].");
			logger.info("==========gzUnionPayResult:"+gzUnionPayResult);
			UnionpayManualTransaction unionpayManualTransaction = unionpayManualTransactionService.getUnionpayManualTransactionServiceByReqNo(queryReqNo);
			if(unionpayManualTransaction!=null){
				unionpayManualTransaction.setQueryResponsePacket(gzUnionPayResult.getResponsePacket());
				unionpayManualTransactionService.update(unionpayManualTransaction);
			}
			return jsonViewResolver.sucJsonResult("gzUnionPayResult",gzUnionPayResult.toString());
		} catch(Exception e){
			logger.error("人工查询扣款失败");
			e.printStackTrace();
		}
		return jsonViewResolver.errorJsonResult("系统错误");
	}
	@RequestMapping("query-account-detail")
	public @ResponseBody String queryAccountDetail(@Secure Principal principal, 
			@RequestParam(value="beginDate",required=true) String beginDate,
			@RequestParam(value="endDate",required=true) String endDate,
			@RequestParam(value="pageNum",required=false,defaultValue="") String pageNum,
			@RequestParam(value="accountType",required=false,defaultValue="0") String accountType,
			@RequestParam(value="paymentChannelId",required=true) Long paymentChannelId){
			try {
				PaymentChannel paymentChannel = paymentChannelService.load(PaymentChannel.class, paymentChannelId);
				if(paymentChannel==null){
					return jsonViewResolver.errorJsonResult("没有支付通道！");
				}
				if(StringUtils.isEmpty(beginDate)||StringUtils.isEmpty(endDate)){
					return jsonViewResolver.errorJsonResult("请输入日期！");
				}
				AccountDetailQueryInfoModel  model = new AccountDetailQueryInfoModel(paymentChannel,
						beginDate, endDate, pageNum, "",accountType);
				GZUnionPayResult gzUnionPayResult = igzUnionPayApiComponent.execAccountDetailQuery(model);
				return jsonViewResolver.sucJsonResult("gzUnionPayResult",JsonUtils.toJsonString(gzUnionPayResult));
			} catch(Exception e){
				logger.error("查询账户明细失败");
				e.printStackTrace();
				return jsonViewResolver.errorJsonResult("系统错误");
			}
	}
	
	@RequestMapping("query-transaction-detail")
	public @ResponseBody String queryTransactionDetail(@Secure Principal principal, 
			@RequestParam(value="beginDate",required=true) String beginDate,
			@RequestParam(value="endDate",required=true) String endDate,
			@RequestParam(value="pageNum",required=false,defaultValue="") String pageNum,
			@RequestParam(value="paymentChannelId",required=true) Long paymentChannelId){
			try {
				PaymentChannel paymentChannel = paymentChannelService.load(PaymentChannel.class, paymentChannelId);
				if(paymentChannel==null){
					return jsonViewResolver.errorJsonResult("没有支付通道！");
				}
				if(StringUtils.isEmpty(beginDate)||StringUtils.isEmpty(endDate)){
					return jsonViewResolver.errorJsonResult("请输入日期！");
				}
				TransactionDetailQueryInfoModel  model = new TransactionDetailQueryInfoModel(paymentChannel,
						beginDate, endDate, pageNum,"");
				TransactionDetailQueryResult queryResult = igzUnionPayApiComponent.execTransactionDetailQuery(model);
				return jsonViewResolver.sucJsonResult("transactionDetailQueryResult",JsonUtils.toJsonString(queryResult));
			} catch(Exception e){
				logger.error("查询交易明细失败");
				e.printStackTrace();
				return jsonViewResolver.errorJsonResult("系统错误");
			}
	}
	
	private String createSettFileUrl(PaymentChannel paymentChannel,
			String settDate, String settNo, String sfType, String reqTime)
			throws CryptException {
		String stringBefore = settDate+"|"+settNo+"|"+paymentChannel.getUserName()+"|"+paymentChannel.getMerchantId()+"|"+reqTime;
		Crypt  crypt = new Crypt("gbk");
		String signedMsg = crypt.sign(stringBefore, paymentChannel.getPfxFilePath(), paymentChannel.getPfxFileKey());
		String payUrl = paymentChannel.getApiUrl();
		String settFileUrlPre = payUrl.replace("ProcessServlet", "GetSettFile.do");
		//sign在最后
		String paramUrls = "?SETT_DATE="+settDate+"&SETT_NO="+settNo+"&SF_TYPE="+sfType+"&USER_NAME="+paymentChannel.getUserName()+"&MERCHANT_ID="+paymentChannel.getMerchantId()+"&REQ_TIME="+reqTime+"&SIGNED_MSG="+signedMsg;
		return settFileUrlPre+paramUrls;
	}
	
	@RequestMapping("get-sett-file-url")
	public @ResponseBody String getSettFileUrl(@Secure Principal principal, 
			@RequestParam(value="settDate",required=true) String settDate,
			@RequestParam(value="settNo",required=false,defaultValue="01") String settNo,
			@RequestParam(value="sfType",required=true) String sfType,
			@RequestParam(value="paymentChannelId",required=true) Long paymentChannelId){
			try {
				PaymentChannel paymentChannel = paymentChannelService.load(PaymentChannel.class, paymentChannelId);
				if(paymentChannel==null){
					return jsonViewResolver.errorJsonResult("没有支付通道！");
				}
				if(StringUtils.isEmpty(settDate)||StringUtils.isEmpty(sfType)){
					return jsonViewResolver.errorJsonResult("清算日期和收付类型不能为空");
				}
				
				String reqTime = DateUtils.getNowFullDateTime();
				
				String url = createSettFileUrl(paymentChannel, settDate,
						settNo, sfType, reqTime);
				
				return jsonViewResolver.sucJsonResult("url",url);
			} catch(Exception e){
				logger.error("获取清算文件URL失败");
				e.printStackTrace();
				return jsonViewResolver.errorJsonResult("系统错误");
			}
	}
	
	@RequestMapping("add-brush-flow")
	public @ResponseBody String addBrushFlow(
			@RequestParam("flowStartDate") String startDate,
			@RequestParam("flowEndDate") String endDate,
			@RequestParam("accountId") long accountId){
		try {
			Account account = accountService.load(Account.class, accountId);
			if(account==null){
				return jsonViewResolver.errorJsonResult("没有银行账号");
			}
			USBKey usbKey = usbKeyService.getUSBKeyByAccount(account);
			if(usbKey==null){
				return jsonViewResolver.errorJsonResult("未配置Ukey");
			}
			Company company = financialContractService.getCompanyByAccountNo(account.getAccountNo());
			if(company==null){
				return jsonViewResolver.errorJsonResult("不存在相应的信托合同或者公司");
			}
			App app = appService.getAppByCompanyId(company.getId());
			IDirectBankHandler directBankHandler = DirectBankHandlerFactory.newInstance(usbKey.getBankCode());
			directBankHandler.recordUnsavedCashFlow(startDate, endDate, account, app, usbKey);
			return jsonViewResolver.sucJsonResult();
		} catch(Exception e){
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}

}

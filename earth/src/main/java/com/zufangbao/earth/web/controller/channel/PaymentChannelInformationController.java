package com.zufangbao.earth.web.controller.channel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.demo2do.core.web.interceptor.MenuSetting;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.earth.web.controller.BaseController;
import com.zufangbao.earth.web.controller.reapymentInfo.BankCoreCodeMapSpec;
import com.zufangbao.earth.yunxin.handler.BankTransactionLimitSheetHandler;
import com.zufangbao.earth.yunxin.handler.FinancialContractConfigHandler;
import com.zufangbao.earth.yunxin.handler.PaymentChannelInformationHandler;
import com.zufangbao.gluon.spec.global.GlobalSpec;
import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.entity.financial.BankTransactionConfigure;
import com.zufangbao.sun.entity.financial.BankTransactionLimitSheet;
import com.zufangbao.sun.entity.financial.BankTransactionLimitSheetListModel;
import com.zufangbao.sun.entity.financial.BankTransactionLimitSheetUpdateModel;
import com.zufangbao.sun.entity.financial.BusinessType;
import com.zufangbao.sun.entity.financial.ChannelWorkingStatus;
import com.zufangbao.sun.entity.financial.ChargeExcutionMode;
import com.zufangbao.sun.entity.financial.ChargeRateMode;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.financial.FinancialContractConfig;
import com.zufangbao.sun.entity.financial.PaymentChannelConfigure;
import com.zufangbao.sun.entity.financial.PaymentChannelInformation;
import com.zufangbao.sun.entity.financial.PaymentInstitutionName;
import com.zufangbao.sun.entity.financial.PaymentStrategyMode;
import com.zufangbao.sun.service.BankTransactionLimitSheetService;
import com.zufangbao.sun.service.FinancialContractConfigService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.PaymentChannelInformationService;
import com.zufangbao.sun.service.TransferApplicationService;
import com.zufangbao.sun.utils.EnumUtil;
import com.zufangbao.sun.utils.excel.ExcelUtil;
import com.zufangbao.sun.yunxin.entity.model.PaymentChannelQueryModel;
import com.zufangbao.sun.yunxin.entity.model.StrategyResultSaveModel;
import com.zufangbao.sun.yunxin.entity.model.StrategySwitchResultSubmitModel;
import com.zufangbao.sun.yunxin.entity.model.TransactionLimitQueryModel;
import com.zufangbao.sun.yunxin.entity.model.TransferApplicationQueryModel4TransactionDetail;
import com.zufangbao.sun.yunxin.entity.model.TransferApplicationResultModel4TransactionDetail;
import com.zufangbao.sun.yunxin.entity.model.TransferApplicationStatisticsModel;
import com.zufangbao.sun.yunxin.entity.remittance.AccountSide;
import com.zufangbao.wellsfargo.yunxin.handler.TransferApplicationHandler;

@RestController
@RequestMapping("/paymentchannel")
@MenuSetting("menu-financial")
public class PaymentChannelInformationController extends BaseController{
	
	@Autowired
	private PaymentChannelInformationHandler paymentChannelInformationHandler;
	
	@Autowired
	private BankTransactionLimitSheetHandler bankTransactionLimitSheetHandler;

	@Autowired
	private TransferApplicationHandler transferApplicationHandler;
	
	@Autowired
	private FinancialContractConfigHandler financialContractConfigHandler;
	
	@Autowired
	private FinancialContractConfigService financialContractConfigService;
	
	@Autowired
	private PaymentChannelInformationService paymentChannelInformationService;
	
	@Autowired
	private TransferApplicationService transferApplicationService;
	
	@Autowired
	private FinancialContractService financialContractService;
	
	@Autowired
	private BankTransactionLimitSheetService bankTransactionLimitSheetService;
	
	private static final Log logger = LogFactory.getLog(PaymentChannelInformationController.class);
	
	private static String savePath;
	
	@Value("#{config['uploadPath']}")
	private void setSavePath(String uploadPath){
		if(StringUtils.isEmpty(uploadPath)){
			PaymentChannelInformationController.savePath = getClass().getResource(".").getFile().toString() + "paymentChannel/" ;
		}else if(uploadPath.endsWith(File.separator)){
			PaymentChannelInformationController.savePath = uploadPath+ "paymentChannel"+ File.separator;
		}else{
			PaymentChannelInformationController.savePath =  uploadPath+ File.separator+ "paymentChannel"+ File.separator;
		}
	}

	private static final int everyPage=8;
	
	private static final String bankLimitationXslKey = "16d753ab90114b00bb2a275f775f863f_银行限额模板.xls";
	
	// 通道配置-列表 页面
	@RequestMapping(value={"/config/list", ""})
	@MenuSetting("submenu-channel-config")
	public ModelAndView getConfigListPage() {
		try {
			ModelAndView modelAndView = new ModelAndView("paymentChannel/config-list");
			modelAndView.addObject("creditStatusList", Arrays.asList(ChannelWorkingStatus.values()));
			modelAndView.addObject("debitStatusList", Arrays.asList(ChannelWorkingStatus.values()));
			modelAndView.addObject("gatewayList", Arrays.asList(PaymentInstitutionName.values()));
			modelAndView.addObject("businessTypeList", Arrays.asList(BusinessType.values()));
			return modelAndView;
		} catch (Exception e) {
			logger.error("#getConfigListPage  occur error.");
			e.printStackTrace();
			return pageViewResolver.errorSpec();
		}
	}
	
	// 通道配置-列表 查询
	@RequestMapping(value="/config/search", method = RequestMethod.GET)
	@MenuSetting("submenu-channel-config")
	public @ResponseBody String searchChannel(
			@ModelAttribute PaymentChannelQueryModel queryModel, Page page){
		try{
			Map<String, Object> dataMap = paymentChannelInformationHandler.searchPaymentChannelBy(queryModel, page);
			return jsonViewResolver.sucJsonResult(dataMap,SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteDateUseDateFormat);
		}catch(Exception e){
			logger.error("#searchChannel#  occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}
	
	// 通道配置-列表 详情操作
	@RequestMapping(value = "/config/detail", method = RequestMethod.GET)
	@MenuSetting("submenu-channel-config")
	public @ResponseBody String viewDetails(
			@RequestParam(value = "paymentChannelUuid", required =false) String paymentChannelUuid){
		try{
			Map<String, Object> data = paymentChannelInformationHandler.getPaymentChannelDetails(paymentChannelUuid);
			return jsonViewResolver.sucJsonResult(data, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteNullStringAsEmpty);
		}catch (Exception e){
			logger.error("#viewDetails# occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误，请重试");   
		}
	}
	
	// 通道配置-配置 页面
	@RequestMapping(value = "/config/{paymentChannelUuid}", method =RequestMethod.GET)
	@MenuSetting("submenu-channel-config")
	public ModelAndView editPaymentChannel(
			@PathVariable(value = "paymentChannelUuid") String paymentChannelUuid){
		try{
			Map<String, Object> dataMap = new HashMap<String, Object>();
			genPaymentChannelRtnDataMap(dataMap, paymentChannelUuid);
			return pageViewResolver.pageSpec("paymentChannel/config-edit", dataMap);
		}catch(Exception e){
			logger.error("#editPaymentChannel#  occur error.");
			e.printStackTrace();
			return pageViewResolver.errorSpec();
		}
	}
	
	private void genPaymentChannelRtnDataMap(Map<String, Object> dataMap, String paymentChannelUuid){
		dataMap.put("chargeRateMode", Arrays.asList(ChargeRateMode.values()));
		dataMap.put("channelWorkingStatus", Arrays.asList(ChannelWorkingStatus.getValuesExceptNotLink()));
		dataMap.put("chargeExcutionMode", Arrays.asList(ChargeExcutionMode.values()));
		FinancialContract fc =  paymentChannelInformationHandler.getFinancialContractBy(paymentChannelUuid);
		if(fc != null && fc.getCapitalAccount() != null){
			Account captitalAccount = fc.getCapitalAccount();
			String unionStr = captitalAccount.getBankName() + "(" + captitalAccount.getMarkedAccountNo() + ")";
			dataMap.put("captitalAccountNameAndNo", unionStr);
		}
		PaymentChannelInformation pci = paymentChannelInformationService.getPaymentChannelInformationBy(paymentChannelUuid);
		if(pci != null){
			String configDataJsonStr = pci.getPaymentConfigureData();
			dataMap.put("paymentChannelConfigData", configDataJsonStr);
			dataMap.put("relatedFinancialContractName", pci.getRelatedFinancialContractName());
			dataMap.put("relatedFinancialContractUuid", pci.getRelatedFinancialContractUuid());
			dataMap.put("paymentChannelName", pci.getPaymentChannelName());
			dataMap.put("outlierChannelName", pci.getOutlierChannelName());
			dataMap.put("paymentInstitutionOrdinal", pci.getPaymentInstitutionName()==null?-1:pci.getPaymentInstitutionName().ordinal());
			dataMap.put("clearingNo", pci.getClearingNo());
			dataMap.put("creditChannelWorkingStatus", pci.getCreditChannelWorkingStatus());
			dataMap.put("debitChannelWorkingStatus", pci.getDebitChannelWorkingStatus());
		}
	}
	
	// 通道配置-配置页面 判断通道名是否唯一
	@RequestMapping(value = "/config/edit/check", method = RequestMethod.GET)
	@MenuSetting("submenu-channel-config")
	public @ResponseBody String checkParams(
			@RequestParam(value = "paymentChannelUuid") String paymentChannelUuid,
			@RequestParam(value = "paymentChannelName") String paymentChannelName){
		try{
			boolean isTrue = paymentChannelInformationService.isUnique(paymentChannelName, paymentChannelUuid);
			return jsonViewResolver.jsonResult(isTrue);
		}catch (Exception e){
			logger.error("#checkParams# occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误,请重试");
		}
	}
	
	// 通道配置-配置页面 下载模板
	@Deprecated
	@RequestMapping(value = "/config/edit/downloadBankLimitXls", method= RequestMethod.GET)
	@MenuSetting("submenu-channel-config")
	public @ResponseBody String downloadBankLimitXls(HttpServletRequest request, HttpServletResponse response){
		try{
			ExcelUtil<BankTransactionConfigure> excelUtil = new ExcelUtil<BankTransactionConfigure>(BankTransactionConfigure.class);
			List<BankTransactionConfigure> list = new ArrayList<BankTransactionConfigure>();
			list.add(new BankTransactionConfigure());
			HSSFWorkbook workBook = excelUtil.exportDataToHSSFWork(list, "银行限额表");
			exportExcelToClient(response, "银行限额模板.xls", GlobalSpec.UTF_8, workBook);
			return jsonViewResolver.sucJsonResult();
		}catch(Exception e){
			logger.error("#downloadBankLimitXls# occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误,请重试");
		}
	}
	
	// 通道配置 - 配置页面 银行限额预览
	@RequestMapping(value = "/config/edit/bankLimitPreview", method = RequestMethod.GET)
	@MenuSetting("submenu-channel-config")
	public @ResponseBody String bankLimitPreview(
			@RequestParam(value = "paymentInstitutionOrdinal") int paymentInstitutionOrdinal,
			@RequestParam(value = "outlierChannelName") String outlierChannelName,
//			@RequestParam(value = "paymentChannelUuid") String paymentChannelUuid,
			@RequestParam(value = "accountSide", defaultValue = "-1") int accountSide, Page page) {
		try{
			AccountSide accountSideEnum = EnumUtil.fromOrdinal(AccountSide.class, accountSide);
			PaymentInstitutionName paymentInstitutionName = EnumUtil.fromOrdinal(PaymentInstitutionName.class, paymentInstitutionOrdinal);
			if(StringUtils.isBlank(outlierChannelName) || accountSideEnum == null || paymentInstitutionName == null){
				return jsonViewResolver.errorJsonResult("请求参数错误，请重试");
			}
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("list", bankTransactionLimitSheetHandler.getBankLimitPreview(paymentInstitutionName, accountSideEnum, outlierChannelName, null));
//			resultMap.put("size", bankTransactionLimitSheetService.getBankTransactionLimitSheetCountBy(paymentChannelUuid, accountSideEnum));
			return jsonViewResolver.sucJsonResult(resultMap,SerializerFeature.DisableCircularReferenceDetect);
		}catch(Exception e){
			logger.error("#bankLimitPreview# occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误,请重试");
		}
	}

	// 通道配置-配置页面 保存配置操作
	@RequestMapping(value = "/config/{paymentChannelUuid}/save", method = RequestMethod.POST)
	@MenuSetting("submenu-channel-config")
	public @ResponseBody String saveConfigure(
			@PathVariable(value = "paymentChannelUuid") String paymentChannelUuid,
			@RequestParam(value = "paymentChannelName") String paymentChannelName,
			@RequestParam(value = "data") String jsonData) {
		try {
			PaymentChannelConfigure paymentChannelConfigure = JSON.parseObject(jsonData,  PaymentChannelConfigure.class);
			paymentChannelInformationHandler.savePaymentChannelConfigure(paymentChannelUuid, paymentChannelName, paymentChannelConfigure);
			return jsonViewResolver.sucJsonResult();
		} catch (Exception e) {
			logger.error("#saveConfigure# occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("保存失败");
		}
	}
	
	// 通道配置-交易明细 页面
	@RequestMapping(value = "/config/transactiondetail/{paymentChannelUuid}", method = RequestMethod.GET)
	@MenuSetting("submenu-channel-config")
	public ModelAndView getTransactionDetail(@PathVariable(value = "paymentChannelUuid") String paymentChannelUuid){
		try{
			Map<String, Object> dataMap = new HashMap<String, Object>();
			genTransactionDetailDataMap(dataMap, paymentChannelUuid);
			return pageViewResolver.pageSpec("paymentChannel/config-detail", dataMap);
		}catch(Exception e){
			logger.error("#getTransactionDetail# occur error.");
			e.printStackTrace();
			return pageViewResolver.errorSpec();
		}
	}
	
	private void genTransactionDetailDataMap(Map<String, Object> dataMap, String paymentChannelUuid){
		BigDecimal totalCreditAmount = transferApplicationHandler.getTotalCreditAmount(paymentChannelUuid);
		BigDecimal totalDebitAmount = transferApplicationHandler.getTotalDebitAmount(paymentChannelUuid);
		BigDecimal tradingSuccessRateIn24Hours = transferApplicationService.getTradingSuccessRateIn24Hours(paymentChannelUuid);
		PaymentChannelInformation paymentChannelInformation = paymentChannelInformationService.getPaymentChannelInformationBy(paymentChannelUuid);
		String paymentChannelName = paymentChannelInformation.getPaymentChannelName();
		Date createTime = paymentChannelInformation.getCreateTime();
		dataMap.put("totalCreditAmount", totalCreditAmount);
		dataMap.put("totalDebitAmount", totalDebitAmount);
		dataMap.put("tradingSuccessRateIn24Hours", tradingSuccessRateIn24Hours);
		dataMap.put("paymentChannelName", paymentChannelName);
		dataMap.put("createTime", createTime);
		dataMap.put("paymentChannelUuid", paymentChannelUuid);
		dataMap.put("everyPage", everyPage);
	}
	
	// 通道配置-交易明细 查询交易额趋势 7天 6月
	@RequestMapping(value = "/config/transactionDetail/searchTradingVolume", method = RequestMethod.GET)
	@MenuSetting("submenu-channel-config")
	public @ResponseBody String searchTradingVolume(
			@RequestParam(value = "time") String time,
			@RequestParam(value = "paymentChannelUuid") String paymentChannelUuid){
		try{
			Map<String, Object>  resultMap = transferApplicationHandler.getCreditAndDebitAmountListBy(time, paymentChannelUuid);
			return jsonViewResolver.sucJsonResult(resultMap,SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteDateUseDateFormat);
		}catch(Exception e){
			logger.error("#searchTradingVolume# occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("查询失败");
		}
	}
	
	// 通道配置-交易明细 交易记录查询
	@RequestMapping(value = "/config/transactionDetail/search")
	@MenuSetting("submenu-channel-config")
	public @ResponseBody String searchTransferApplication(@ModelAttribute TransferApplicationQueryModel4TransactionDetail financialContractQueryModel,
			Page page){
		try{
			page = new Page(page.getCurrentPage(),everyPage);
			Map<String, Object> resultData = transferApplicationHandler.queryTransferApplicationForTransactionDetail(financialContractQueryModel, page);
			return jsonViewResolver.sucJsonResult(resultData,SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteDateUseDateFormat);
		}catch (Exception e){
			logger.error("#searchTransferApplication# occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("查询失败");
		}
	}
	
	//通道配置-交易明细 导出交易记录查询结果
	@RequestMapping(value = "/config/transactionDetail/export")
	@MenuSetting("submenu-channel-config")
	public @ResponseBody String exportTransferApplicationResult(@ModelAttribute TransferApplicationQueryModel4TransactionDetail financialContractQueryModel,HttpServletResponse response){
		try{
			Map<String, Object> resultData = transferApplicationHandler.queryTransferApplicationForTransactionDetail(financialContractQueryModel, null);
			List<TransferApplicationResultModel4TransactionDetail> list = (List<TransferApplicationResultModel4TransactionDetail>) resultData.get("list");
			ExcelUtil<TransferApplicationResultModel4TransactionDetail> excelUtil = new ExcelUtil<TransferApplicationResultModel4TransactionDetail>(TransferApplicationResultModel4TransactionDetail.class);
			HSSFWorkbook workBook = excelUtil.exportDataToHSSFWork(list, "交易记录");
			exportExcelToClient(response, "交易记录表.xls", GlobalSpec.UTF_8, workBook);
			return jsonViewResolver.sucJsonResult();
		}catch(Exception e){
			logger.error("#exportTransferApplicationResult# occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("导出失败");
		}
	}
	
	// 通道切换-列表 页面
	@RequestMapping(value={"/switch/list", "/switch"}, method = RequestMethod.GET)
	@MenuSetting("submenu-channel-switch")
	public ModelAndView getSwitchListPage(){
		try {
		ModelAndView modelAndView = new ModelAndView("paymentChannel/switch-list");
		modelAndView.addObject("creditStrategyMode", Arrays.asList(PaymentStrategyMode.values()));
		modelAndView.addObject("debitStrategyMode", Arrays.asList(PaymentStrategyMode.values()));
		return modelAndView;
		}catch(Exception e){
			logger.error("#getSwitchListPage# occur error.");
			e.printStackTrace();
			return pageViewResolver.errorSpec();
		}
	}
	
	// 通道切换-列表页 查询操作
	@RequestMapping(value = "/switch/search", method = RequestMethod.GET)
	@MenuSetting("submenu-channel-switch")
	public @ResponseBody String switchSearch(
			@RequestParam(value = "creditStrategyMode", required = false, defaultValue = "-1") int creditStrategyMode,
			@RequestParam(value = "debitStrategyMode", required = false, defaultValue = "-1") int debitStrategyMode,
			@RequestParam(value = "keyWord", required = false) String keyWord, Page page){
		try{
			Map<String, Object> dataMap = financialContractConfigHandler.queryBy(debitStrategyMode, creditStrategyMode, keyWord, page);
			return jsonViewResolver.sucJsonResult(dataMap,SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteDateUseDateFormat);
		} catch(Exception e){
			logger.error("#switchSearch# occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("查询失败");
		}
	}
	
	// 通道切换-列表页 详情操作
	@Deprecated
	@RequestMapping(value = "/switch/detailView", method = RequestMethod.GET)
	@MenuSetting("submenu-channel-switch")
	public @ResponseBody String switchDetail(
			@RequestParam(value = "financialContractUuid") String financialContractUuid){
		try{
			Map<String, Object> dataMap = financialContractConfigHandler.getSwitchDetail(financialContractUuid);
			return jsonViewResolver.sucJsonResult(dataMap,SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteDateUseDateFormat);
		}catch(Exception e){
			logger.error("#switchDetail# occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("操作失败");
		}
	}
	
	// 通道切换-详情页
	@RequestMapping(value = "/switch/detail/{financialContractUuid}", method = RequestMethod.GET)
	@MenuSetting("submenu-channel-switch")
	public ModelAndView switchDetailPage(
			@PathVariable(value = "financialContractUuid") String financialContractUuid) {
		try {
			ModelAndView modelAndView = new ModelAndView("paymentChannel/channel-detail");
			FinancialContract fc = financialContractService.getFinancialContractBy(financialContractUuid);
			if(fc == null){
				return modelAndView;
			}
			modelAndView.addObject("contractName",fc.getContractName());
			modelAndView.addObject("contractNo", fc.getContractNo());
			Account account = fc.getCapitalAccount();
			modelAndView.addObject("bankNameUnionAccountNo", account.getBankName() + "(" + account.getMarkedAccountNo() + ")" );
			modelAndView.addAllObjects(financialContractConfigHandler.getSwitchDetailInfo(financialContractUuid));
			return modelAndView;
		} catch (Exception e) {
			logger.error("#switchDetailPage# occur error.");
			e.printStackTrace();
			return pageViewResolver.errorSpec();
		}
	}
	
	// 通道切换-各银行通道顺序预览
	@RequestMapping(value="/switch/paymentChannelOrder", method = RequestMethod.GET)
	@MenuSetting("submenu-channel-switch")
	public @ResponseBody String previewPaymentChannelOrderForBanks(
			@RequestParam(value="financialContractUuid") String financialContractUuid,
			@RequestParam(value="businessType", defaultValue="-1") int businessType,
			@RequestParam(value="accountSide", defaultValue="-1") int accountSide){
		BusinessType type = EnumUtil.fromOrdinal(BusinessType.class, businessType);
		AccountSide Side = EnumUtil.fromOrdinal(AccountSide.class, accountSide);
		List<Map<String, Object>> rtnMaps = financialContractConfigHandler.previewPaymentChannelOrderForBanks(financialContractUuid, type, Side);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("list", rtnMaps);
		return jsonViewResolver.sucJsonResult(dataMap,SerializerFeature.DisableCircularReferenceDetect);
	}
	
	// 通道切换-设置策略 第二步
	@RequestMapping(value = "/switch/strategy/step/2")
	@MenuSetting("submenu-channel-switch")
	public @ResponseBody String getPaymentChannelList(
			@RequestParam(value = "financialContractUuid") String financialContractUuid,
			@RequestParam(value = "businessType") int businessType,
			@RequestParam(value = "accountSide") int accountSide){
		try{
			BusinessType businessTypeEnum = EnumUtil.fromOrdinal(BusinessType.class, businessType);
			AccountSide accountSideEnum = EnumUtil.fromOrdinal(AccountSide.class, accountSide);
			if(StringUtils.isEmpty(financialContractUuid) || businessTypeEnum == null || accountSideEnum == null){
				return jsonViewResolver.errorJsonResult("参数错误，请重试");
			}
			FinancialContractConfig financialContractConfig = financialContractConfigService.getFinancialContractConfigBy(financialContractUuid, businessTypeEnum);
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("list", financialContractConfigHandler.extractAvailableBriefPaymentChannelInfo(accountSideEnum, financialContractConfig));
			return jsonViewResolver.sucJsonResult(resultMap,SerializerFeature.DisableCircularReferenceDetect);
		}catch (Exception e){
			logger.error("#getPaymentChannelList# occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("获取通道列表出错，请重试");
		}
	}
	
	// 通道切换-设置策略 第三步
	@RequestMapping(value = "/switch/strategy/step/3")
	@MenuSetting("submenu-channel-switch")
	public @ResponseBody String getAllBank(
			@RequestParam(value = "financialContractUuid") String financialContractUuid,
			@RequestParam(value = "businessType") int businessType,
			@RequestParam(value = "accountSide") int accountSide){
		try{
			BusinessType businessTypeEnum = EnumUtil.fromOrdinal(BusinessType.class, businessType);
			AccountSide accountSideEnum = EnumUtil.fromOrdinal(AccountSide.class, accountSide);
			if(StringUtils.isEmpty(financialContractUuid) || businessTypeEnum == null || accountSideEnum == null){
				return jsonViewResolver.errorJsonResult("参数错误，请重试");
			}
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("list", bankTransactionLimitSheetHandler.getAllBanks(financialContractUuid, businessTypeEnum, accountSideEnum));
			return jsonViewResolver.sucJsonResult(resultMap,SerializerFeature.DisableCircularReferenceDetect);
		}catch (Exception e){
			logger.error("#getPaymentChannelList# occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("获取通道列表出错，请重试");
		}
	}
	
	// 通道切换-切换策略 页面
	@Deprecated
	@RequestMapping(value = "/switch/strategy/{financialContractUuid}", method = RequestMethod.GET)
	@MenuSetting("submenu-channel-switch")
	public ModelAndView getSwitchStrategyPage(
			@PathVariable(value = "financialContractUuid") String financialContractUuid){
		try{
			ModelAndView modelAndView = new ModelAndView("paymentChannel/switch-strategy");
			modelAndView.addObject("financialContractUuid", financialContractUuid);
			
			FinancialContract financialContract = financialContractService.getFinancialContractBy(financialContractUuid);
			if(financialContract != null){
				modelAndView.addObject("financialContractName", financialContract.getContractName());
			}
			modelAndView.addObject("creditStrategyMode", Arrays.asList(PaymentStrategyMode.values()));
			modelAndView.addObject("debitStrategyMode", Arrays.asList(PaymentStrategyMode.values()));
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.putAll(financialContractConfigHandler.getActiveCreditAndDebitStrategy(financialContractUuid, BusinessType.SELF));
			dataMap.putAll(financialContractConfigHandler.getActiveCreditAndDebitStrategy(financialContractUuid, BusinessType.ENTRUST));
			modelAndView.addObject("dataMap", JSON.toJSONString(dataMap, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteDateUseDateFormat));
			return modelAndView;
		}catch(Exception e){
			logger.error("#getSwitchStrategyPage# occur error.");
			e.printStackTrace();
			return pageViewResolver.errorSpec();
		}
	}
	
	// 通道切换-切换策略 收款策略列表
	@Deprecated
	@RequestMapping(value = "/switch/strategy/debit", method = RequestMethod.GET)
	@MenuSetting("submenu-channel-switch")
	public @ResponseBody String getDebitStrategyList(
			@RequestParam(value = "financialContractUuid") String financialContractUuid,
			@RequestParam(value = "paymentStrategyMode", required=false, defaultValue="-1") int paymentStrategyMode,
			@RequestParam(value = "businessType", required=false, defaultValue="0") int businessType){
		try{
			Map<String, Object> resultMap = new HashMap<>();
			List<Map<String, Object>> dataList = financialContractConfigHandler.getDebitStrategyList(financialContractUuid, businessType);
			resultMap.put("list", dataList);
			return jsonViewResolver.sucJsonResult(resultMap,SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteDateUseDateFormat);
		}catch(Exception e){
			logger.error("#getDebitStrategyList# occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("查询收款策略失败");
		}
	}
	
	// 通道切换-切换策略 付款策略列表
	@Deprecated
	@RequestMapping(value = "/switch/strategy/credit", method = RequestMethod.GET)
	@MenuSetting("submenu-channel-switch")
	public @ResponseBody String getCreditStrategyList(
			@RequestParam(value = "financialContractUuid") String financialContractUuid,
			@RequestParam(value = "paymentStrategyMode", required=false, defaultValue="-1") int paymentStrategyMode,
			@RequestParam(value = "businessType", required=false, defaultValue="0") int businessType){
		try{
			Map<String, Object> resultMap = new HashMap<>();
			List<Map<String, Object>> dataList = financialContractConfigHandler.getCreditStrategyList(financialContractUuid, businessType);
			resultMap.put("list", dataList);
			return jsonViewResolver.sucJsonResult(resultMap,SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteDateUseDateFormat);
		}catch(Exception e){
			logger.error("#getCreditStrategyList# occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("查询付款策略失败");
		}
	}
	
	// 通道切换-切换策略 提交按钮
	@Deprecated
	@RequestMapping(value = "/switch/strategy/submit", method = RequestMethod.POST)
	@MenuSetting("submenu-channel-switch")
	public @ResponseBody String saveStrtegyResult(
			@ModelAttribute StrategyResultSaveModel strategyResultSaveModel){
		try{
			boolean isSelfSuc = financialContractConfigService.updateCreditAndDebitStrategy(strategyResultSaveModel, 0);
			boolean isAcSuc = financialContractConfigService.updateCreditAndDebitStrategy(strategyResultSaveModel, 1);
			return jsonViewResolver.jsonResult(isSelfSuc&&isAcSuc);
		}catch(Exception e){
			logger.error("#saveStrtegyResult# occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("提交失败");
		}
	}
	
	// 通道切换-切换策略 提交按钮
	@RequestMapping(value = "/switch/strategy/saveResult", method = RequestMethod.POST)
	@MenuSetting("submenu-channel-switch")
	public @ResponseBody String saveStrtegySwitchResult(@RequestBody String data){
			 // @@RequestBody StrategySwitchResultSubmitModel submitModel
		try {
			StrategySwitchResultSubmitModel submitModel = JSON.parseObject(data,  StrategySwitchResultSubmitModel.class);
			boolean isSuc = financialContractConfigHandler.updateConfig(submitModel);
			return jsonViewResolver.jsonResult(isSuc);
		} catch (Exception e) {
			logger.error("#saveStrtegySwitchResult# occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("保存失败");
		}
	}
	
	// 效率分析 页面
	@RequestMapping(value = "/efficentanalysis", method = RequestMethod.GET)
	@MenuSetting("submenu-channel-efficentanalysis")
	public ModelAndView getEfficentAnalysisPage(){
		try{
			Map<String, Object> dataMap = transferApplicationHandler.getDataForEfficentAnalysis();
			return pageViewResolver.pageSpec("paymentChannel/efficent-analysis", dataMap);
		}catch(Exception e){
			logger.error("#getEfficentAnalysisPage# occur error.");
			e.printStackTrace();
			return pageViewResolver.errorSpec();
		}
	}
	
	// 效率分析-交易额趋势查询
	@RequestMapping(value = "/efficentanalysis/tradingVolumeTrend")
	@MenuSetting("submenu-channel-efficentanalysis")
	public @ResponseBody String getTradingVolumeTrend(
			@RequestParam(value = "time") String time){
		try{
			Map<String, Object> dataMap = transferApplicationHandler.getTradingVolumeTrendIn(time);
			return jsonViewResolver.sucJsonResult(dataMap,SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteDateUseDateFormat);
		}catch(Exception e){
			logger.error("#getTradingVolumeTrend# occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("查询失败");
		}
	}
	
	// 效率分析-24小时通道交易查询
	@RequestMapping(value = "/efficentanalysis/statistics")
	@MenuSetting("submenu-channel-efficentanalysis")
	public @ResponseBody String getStatistics(@RequestParam(value = "type") int type){
		try{
			Map<String, Object> dataMap = new HashMap<String, Object>();
			List<TransferApplicationStatisticsModel> transferApplicationStatisticsModels = transferApplicationHandler.getStatisticsDataIn24Hours(type);
			dataMap.put("list", transferApplicationStatisticsModels);
			return jsonViewResolver.sucJsonResult(dataMap,SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteDateUseDateFormat);
		}catch(Exception e){
			logger.error("#getStatistics# occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("查询失败");
		}
	}
	
	// 第三方限额-列表 页面
	@RequestMapping(value="/limitSheet/list")
	@MenuSetting("submenu-channel-limit")
	public ModelAndView getThirdPartyTransactionLimitListPage() {
		try {
			ModelAndView modelAndView = new ModelAndView("paymentChannel/quota-list");
			modelAndView.addObject("outlierChannelNames", paymentChannelInformationService.getAllOutlierChannelNames());
			modelAndView.addObject("accountSide", AccountSide.values());
			modelAndView.addObject("gatewayList", PaymentInstitutionName.values());
			return modelAndView;
		} catch (Exception e) {
			logger.error("#getConfigListPage  occur error.");
			e.printStackTrace();
			return pageViewResolver.errorSpec();
		}
	}
	
	// 第三方限额-列表 查询
	@RequestMapping(value="/limitSheet/search", method = RequestMethod.GET)
	@MenuSetting("submenu-channel-limit")
	public @ResponseBody String searchBankTransactionLimit(
			@ModelAttribute TransactionLimitQueryModel queryModel, Page page){
		try{
			Map<String, Object> dataMap = bankTransactionLimitSheetHandler.searchBankTransactionLimitBy(queryModel, page);
			return jsonViewResolver.sucJsonResult(dataMap,SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteDateUseDateFormat);
		}catch(Exception e){
			logger.error("#searchBankTransactionLimit# occur error.");
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult("系统错误");
		}
	}

	// 第三方限额-编辑限额表
	@RequestMapping(value = "/limitSheet/update", method = RequestMethod.POST)
	@MenuSetting("submenu-channel-limit")
	public @ResponseBody String updateBankTransactionLimit(
			@ModelAttribute BankTransactionLimitSheetUpdateModel updateModel){
		try {
			boolean isSuc = bankTransactionLimitSheetService.modifyTransactionLimit(updateModel);
			return jsonViewResolver.jsonResult(isSuc);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("##updateBankTransactionLimit### occur error.");
			return jsonViewResolver.errorJsonResult("更新失败，请重试");
		}
	}
	
	// 文件上传 操作
	@RequestMapping(value = "/file/upload", method = RequestMethod.POST)
	@MenuSetting("submenu-channel-config")
	public @ResponseBody String uploadFile(
			@RequestParam(value = "paymentInstitutionName", defaultValue="-1") int paymentInstitutionName,
			@RequestParam(value = "outlierChannelName", required=false) String outlierChannelName,
			@RequestParam(value = "accountSide", defaultValue="-1") int accountSide,
			HttpServletRequest request){
		try {
			PaymentInstitutionName paymentInstitutionNameEnum = EnumUtil.fromOrdinal(PaymentInstitutionName.class, paymentInstitutionName);
			AccountSide accountSideEnum = EnumUtil.fromOrdinal(AccountSide.class, accountSide);
			PaymentChannelInformation information = paymentChannelInformationService.getPaymentChannelInformationBy(paymentInstitutionNameEnum, outlierChannelName);
			if(paymentInstitutionNameEnum == null || accountSideEnum == null || outlierChannelName == null || information == null){
				return jsonViewResolver.errorJsonResult("参数错误,请重试");
			}
			if(!ServletFileUpload.isMultipartContent(request)){// 按照传统方式获取数据
				return jsonViewResolver.errorJsonResult("请选择文件进行上传");
			}
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multipartRequest.getFile("file");
			if(file == null){
				return jsonViewResolver.errorJsonResult("请选择文件进行上传");
			}
			String originFilename = file.getOriginalFilename(); // 获得原始文件名  c:\a\b\1.txt OR 1.txt
			String filename = originFilename.substring(originFilename.lastIndexOf(File.separator)+1);// 获得文件名1.txt
			if(StringUtils.isEmpty(filename)){
				return jsonViewResolver.errorJsonResult("请选择文件");
			}
			if(!checkFilename(filename)){
				return jsonViewResolver.errorJsonResult("不支持该文件类型");
			}
			String result = checkAndSaveBanktranscationLimitSheet(file, information, accountSideEnum);
			if(StringUtils.isNotBlank(result)){
				return jsonViewResolver.errorJsonResult(result);
			}
			Map<String, Object> resultMap = storeFile(file, savePath, filename);
			if(MapUtils.isEmpty(resultMap)){
				return jsonViewResolver.errorJsonResult("保存文件出错");
			}
			return jsonViewResolver.sucJsonResult(resultMap);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return jsonViewResolver.errorJsonResult(e.getMessage());
		}
	}
	
	private String checkAndSaveBanktranscationLimitSheet(MultipartFile file,PaymentChannelInformation information, AccountSide side) {
		try {
			// 文件解析并检查
			InputStream inputStream = file.getInputStream();
			List<BankTransactionConfigure> configList = new ExcelUtil<>(BankTransactionConfigure.class).importExcel(0, inputStream);
			inputStream.close();
			if (CollectionUtils.isEmpty(configList)) {
				return "银行限额表格式错误，请检查";
			}
			List<String> bcList = new ArrayList<String>();
			for (BankTransactionConfigure configure : configList) {
				String bankCode = configure.getBankCode();
				String bankName = configure.getBankName();
				if(StringUtils.isBlank(bankCode) || StringUtils.isBlank(bankName)){
					return "数据缺失";
				}
				if(bcList.contains(bankCode)){
					return "银行编号["+bankCode+"]重复";
				}
				bcList.add(bankCode);
				if (!BankCoreCodeMapSpec.coreBankMap.containsKey(bankCode)) {
					return "银行编号["+bankCode+"]不存在，请检查";
				}
				if (!bankName.equals(BankCoreCodeMapSpec.coreBankMap.get(bankCode))) {
					return "银行编号["+bankCode+"]与银行名称["+bankName+"]不对应，请检查";
				}
				if(!isAmountNonnegative(configure.getTransactionLimitPerTranscation())
						|| !isAmountNonnegative(configure.getTransactionLimitPerMonth())
						|| !isAmountNonnegative(configure.getTranscationLimitPerDay())){
					return "限额数据不能小于零";
				}
			}

			if( ! bankTransactionLimitSheetService.modifyinvalidTime(information.getPaymentInstitutionName(),information.getOutlierChannelName(), side)){
				return "参数错误错误，请重试";
			}

			for (BankTransactionConfigure configure : configList) {
				BankTransactionLimitSheet limitSheet = new BankTransactionLimitSheet();
				limitSheet.initBy(configure);
				limitSheet.setPaymentInstitutionName(information.getPaymentInstitutionName());
				limitSheet.setOutlierChannelName(information.getOutlierChannelName());
				limitSheet.setAccountSide(side);
				limitSheet.setPaymentChannelInformationUuid(information.getPaymentChannelUuid());
				bankTransactionLimitSheetService.save(limitSheet);
			}
			return null;
		} catch (Exception e) {
			logger.error("###checkAndSaveBanktranscationLimitSheet### occur error.");
			e.printStackTrace();
			return "未知错误，请重试";
		}
	}
	
	private boolean isAmountNonnegative(String amountStr){
		return !( amountStr != null && amountStr.startsWith("-") );
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> storeFile(MultipartFile file, String path, String filename){
		try{
			
		String saveFilename = genSaveFilename(filename);
		File fileTmp = new File(savePath);
		if (!fileTmp.exists() && !fileTmp.isDirectory()) { // 目录不存在，需要创建
			fileTmp.mkdir();  //创建目录
		}
		File source = new File((path + saveFilename).toString());
		file.transferTo(source);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("fileName", filename);
		resultMap.put("fileKey", saveFilename);
		return resultMap;
		}catch (Exception e){
			logger.error("###storeFile### occur error.");
			e.printStackTrace();
			return MapUtils.EMPTY_MAP;
		}
	}
	
	private String genSaveFilename(String filename){
		return UUID.randomUUID().toString().replace("-", "") + "_" + filename;
	}
	
	private boolean checkFilename(String filename){
		List<String> valaidFileformat = Arrays.asList(new String[]{"xls", "xlsx"});
		//获得扩展名
		int dotFlag = filename.lastIndexOf(".");
		String fileExtName = dotFlag == -1? null: filename.substring(dotFlag+1).toLowerCase();
		return valaidFileformat.contains(fileExtName);
	}
	
	// 文件下载 操作
	@RequestMapping(value = "/file/download", method = RequestMethod.GET)
	@MenuSetting("submenu-channel-config")
	public void downloadFile(@RequestParam(value = "fileKey", defaultValue="0") String fileKey, HttpServletRequest request, HttpServletResponse response){
		try{
			if(fileKey.equals("1")){
				fileKey = "16d753ab90114b00bb2a275f775f863f_银行限额模板.xls";
			}else if(fileKey.equals("2")){
				fileKey = "银行编号列表.xls";
			}
			File file = new File(savePath + fileKey);
			if(!file.exists()){
				logger.error("Downloading file error. FilePath: "+ savePath + fileKey);
				return ;
			}
			//处理文件名
			String realname = fileKey.substring(fileKey.indexOf("_")+1);
			//设置响应头，控制浏览器下载该文件
			response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
			 //读取要下载的文件，保存到文件输入流
			FileInputStream in = new FileInputStream(savePath + fileKey);
			OutputStream out = response.getOutputStream();		//创建输出流
			byte buffer[] = new byte[1024];		//创建缓冲区
			int len = 0;
			while((len=in.read(buffer))>0){		//循环将输入流中的内容读取到缓冲区当中
				 out.write(buffer, 0, len);		//输出缓冲区的内容到浏览器，实现文件下载
			}
			in.close();
			out.close();
			return ;
		}catch(Exception e){
			logger.error("#downloadFile# occur error.");
			e.printStackTrace();
			return ;
		}
	}
	
	// 文件下载 操作
	@Deprecated
	@RequestMapping(value = "/file/download/bank", method = RequestMethod.GET)
	@MenuSetting("submenu-channel-config")
	public void downloadBankList(HttpServletRequest request, HttpServletResponse response) {
		try {
			String fileName = "银行编号列表.xls";
			File file = new File(savePath + fileName);
			if (!file.exists()) {
				logger.error("Downloading file error. FilePath: " + savePath+ fileName);
				return;
			}
			// 设置响应头，控制浏览器下载该文件
			response.setHeader("content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
			// 读取要下载的文件，保存到文件输入流
			FileInputStream in = new FileInputStream(savePath + fileName);
			OutputStream out = response.getOutputStream(); // 创建输出流
			byte buffer[] = new byte[1024]; // 创建缓冲区
			int len = 0;
			while ((len = in.read(buffer)) > 0) { // 循环将输入流中的内容读取到缓冲区当中
				out.write(buffer, 0, len); // 输出缓冲区的内容到浏览器，实现文件下载
			}
			in.close();
			out.close();
			return;
		} catch (Exception e) {
			logger.error("#downloadFile# occur error.");
			e.printStackTrace();
			return;
		}
	}
}
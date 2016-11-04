<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" import="java.util.*" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

  <fmt:setBundle basename="ApplicationMessage" />
  <fmt:setLocale value="zh_CN" />

  <%@ include file="/WEB-INF/include/meta.jsp"%>
  <%@ include file="/WEB-INF/include/css-newlayout.jsp"%>
  <title>api- 五维金融金融管理平台</title>
</head>
<body>

  <%@ include file="/WEB-INF/include/header.jsp"%>
  
  <div class="web-g-main">

    <%@ include file="/WEB-INF/include/aside.jsp"%>
    <div class="content">
    <div class="scroller">
    	<h4>人工测试接口</h4>
    		
		<div class="static-info-wrapper">
	        <br>
	        <div class="lookup-params">
				变更还款计划接口
				<br>
				<span class="item">
	            	uniqueId
	            	<input type="text" value="" class="form-control real-value" name="uniqueId1"/>
	            </span>
	            <span class="item">
	            	contractNo(与uniqueId二选一)
	            	<input type="text" value="" class="form-control real-value" name="contractNo1"/>
	            </span>
				<span class="item has-suffix">
	             	变更原因
	             	<select name="modifyRepaymentPlanReason" class="form-control real-value small" id="modifyRepaymentPlanReason">
	                	<option value="0" >0:项目结清</option>
	                	<option value="1" >1:提前部分还款</option>
	                	<option value="2" >2:错误更正</option>
	            	</select>
	            </span>
	            <span class="item has-suffix">
	            	变更明细内容	            	
	            	<textarea name="modifyRepaymentPlanData" class="real-value"  rows="10" cols="50"></textarea>
	            </span>
	            <button type="button" id="modifyRepaymentPlanBtn" class="btn btn-primary">变更还款计划</button>
			</div>
			<br>
			 <div class="lookup-params">
				商户付款凭证接口
				<br>
				<span class="item has-suffix">
	             	交易类型
	             	<select name="transactionType" class="form-control real-value small" id="transactionType">
	                	<option value="0" >0:提交</option>
	                	<option value="1" >1:撤销</option>
	            	</select>
	            </span>
	            <span class="item has-suffix">
	             	凭证类型
	             	<select name="voucherType" class="form-control real-value small" id="voucherType">
	                	<option value="0" >0:代偿</option>
	                	<option value="1" >1:担保补足</option>
	                	<option value="2" >2:回购</option>
	                	<option value="3" >3:差额划拨</option>
	            	</select>
	            </span>
	            <span class="item has-suffix">
	            	凭证金额
	              	<input type="text" value="0.01" class="form-control real-value" name="voucherAmount"/>
	            </span>
	            <span class="item has-suffix">
	            	信托产品代码 
	            	<input type="text" value="" class="form-control real-value" name="financialContractNo1">
	            </span>
	            <span class="item has-suffix">
	            	收款账户号 
	            	<input type="text" value="" class="form-control real-value" name="receivableAccountNo">
	            </span>
	            <span class="item has-suffix">
	            	付款账户号
	            	<input type="text" value="" class="form-control real-value" name="paymentAccountNo">
	            </span>
	            <span class="item has-suffix">
	            	付款银行帐户名称 
	            	<input type="text" value="" class="form-control real-value" name="paymentName">
	            </span>
	            <span class="item has-suffix">
	            	付款银行名称 
	            	<input type="text" value="" class="form-control real-value" name="paymentBank">
	            </span>
	            <span class="item has-suffix">
	            	银行流水号 
	            	<input type="text" value="" class="form-control real-value" name="bankTransactionNo">
	            </span>
	            <span class="item has-suffix">
	            	明细 
	            	<textarea name="detail" class="real-value"  rows="10" cols="50"></textarea>
	            </span>
	            <button type="button" id="businessPaymentVoucherBtn" class="btn btn-primary">商户付款凭证</button>
			</div>
			<br>
	        <div class="lookup-params">
				变更还款信息接口
				<br>
				<span class="item">
	            	uniqueId
	            	<input type="text" value="" class="form-control real-value" name="uniqueId2"/>
	            </span>
	            <span class="item">
	            	contractNo(与uniqueId二选一)
	            	<input type="text" value="" class="form-control real-value" name="contractNo2"/>
	            </span>
	            <span class="item has-suffix">
	            	bankCode
	            	<input type="text" value="" class="form-control real-value" name="bankCode">
	            </span>
	            <span class="item has-suffix">
	            	bankAccount
	            	<input type="text" value="" class="form-control real-value" name="bankAccount">
	            </span>
	            <span class="item has-suffix">
	            	bankName
	            	<input type="text" value="" class="form-control real-value" name="bankName">
	            </span>
	            <span class="item has-suffix">
	            	bankProvince
	            	<input type="text" value="" class="form-control real-value" name="bankProvince">
	            </span>
	            <span class="item has-suffix">
	            	bankCity
	            	<input type="text" value="" class="form-control real-value" name="bankCity">
	            </span>
	            <button type="button" id="modifyRepaymentInformationBtn" class="btn btn-primary">变更还款信息</button>
			</div>
			<br>
	        <div class="lookup-params">
				提前还款接口
				<br>
				<span class="item">
	            	uniqueId
	            	<input type="text" value="" class="form-control real-value" name="uniqueId3"/>
	            </span>
	            <span class="item">
	            	contractNo(与uniqueId二选一)
	            	<input type="text" value="" class="form-control real-value" name="contractNo3"/>
	            </span>
	            提前还款类型
	            <select name="voucherType" class="form-control real-value small" id="voucherType">
	            	<option value="0" >0:全部提前还款</option>
	            </select>
	            <span class="item has-suffix">
	            	金额
	              	<input type="text" value="0.01" class="form-control real-value" name="assetInitialValue"/>
	            </span>
	            <span class="item has-suffix">
	            	应还日期
	            	<input type="text" value="" class="form-control real-value" name="assetRecycleDate">
	            </span>
	            <button type="button" id="prepaymentBtn" class="btn btn-primary">提前还款</button>
			</div>
			<br>
	        <div class="lookup-params">
				导入资产包接口
				<br>
				<span class="item">
	            	资产包详情Json
	            	<textarea name="assetPackageContent" class="real-value"  rows="10" cols="50"></textarea>	            	
	            </span>
	            <button type="button" id="importAssetPackageBtn" class="btn btn-primary">导入资产包</button>
			</div>
			<br>
	        <div class="lookup-params">
				逾期费用修改接口
				<br>
				<span class="item">
	            	修改内容详情Json
	            	<textarea name="modifyOverDueFeeDetails" class="real-value"  rows="10" cols="50"></textarea>
	            </span>
	            <button type="button" id="modifyOverDueFeeBtn" class="btn btn-primary">逾期费用修改</button>
			</div>
			<br>
	        <div class="lookup-params">
				查询还款计划接口
				<br>
				<span class="item">
	            	uniqueId
	            	<input type="text" value="" class="form-control real-value" name="uniqueId4"/>
	            </span>
	            <span class="item">
	            	contractNo(与uniqueId二选一)
	            	<input type="text" value="" class="form-control real-value" name="contractNo4"/>
	            </span>
	            <button type="button" id="queryRepaymentPlanBtn" class="btn btn-primary">查询还款计划</button>
			</div>
			<br>
			<c:set var="today">
				<fmt:formatDate value="<%=new Date() %>" pattern="yyyy-MM-dd"/>
			</c:set>
			<div class="lookup-params">
				查询还款清单接口
				<br>
	            <span class="item has-suffix">
	            	信托产品代码 
	            	<input type="text" value="" class="form-control real-value" name="financialContractNo2">
	            </span>
	            起始日期
	            <span class="item has-suffix">
	             	<input type="text" placeholder="起始日期YYYYMMDD" value="${today }" class="form-control real-value" name="queryStartDate">
	            </span>
	            终止日期
	            <span class="item has-suffix">
	             	<input type="text" placeholder="终止日期YYYYMMDD" value="${today }" class="form-control real-value" name="queryEndDate">
	            </span>
	            <button type="button" id="queryRepaymentListBtn" class="btn btn-primary">查询还款清单</button>
			</div>
			<br>
			<div class="lookup-params">
				执行扣款指令接口
				<br>
				<span class="item">
	            	uniqueId
	            	<input type="text" value="" class="form-control real-value" name="uniqueId5"/>
	            </span>
	            <span class="item">
	            	contractNo(与uniqueId二选一)
	            	<input type="text" value="" class="form-control real-value" name="contractNo5"/>
	            </span>
	            <span class="item has-suffix">
	            	deductId 
	            	<input type="text" value="" class="form-control real-value" name="deductId">
	            </span>
	            <span class="item has-suffix">
	            	信托产品代码 
	            	<input type="text" value="" class="form-control real-value" name="financialProductCode">
	            </span>
	            <span class="item has-suffix">
	            	金额
	              	<input type="text" value="0.01" class="form-control real-value" name="deductAmount"/>
	            </span>
	            请求时间
	            <span class="item has-suffix">
	             	<input type="text" placeholder="起始日期YYYYMMDD" value="${today }" class="form-control real-value" name="apiCalledTime">
	            </span>
	            <span class="item has-suffix">
	             	扣款类型
	             	<select name="repaymentType" class="form-control real-value small" id="repaymentType">
	                	<option value="0" >0:提前</option>
	                	<option value="1" >1:提前</option>
	                	<option value="2" >2:逾期</option>
	            	</select>
	            </span>
	            <span class="item has-suffix">
	            	详情
	            	<textarea name="repaymentDetails" class="real-value"  rows="10" cols="50"></textarea>
	            </span>
	            
	            <button type="button" id="commandDeductBtn" class="btn btn-primary">执行扣款指令接口</button>
			</div>
			<div id="messageDiv"></div>
		</div>
    </div>
  </div>
</div>
<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>
<script src="${ctx.resource }/js/utils/underscore.js"></script>
<script type="text/javascript">
$(function(){
	
	var fun_deduct = function(url,params,operationName,$btn) {
		$.post(url,	params, function(data) {
			$btn.removeAttr("disabled");
			var $result = $.parseJSON(data);
			var $showMsg = new Date() + ", message["+$result.message+"]";
			for(var key in $result.data){
				$showMsg+=","+key+"["+_.escape($result.data[key])+"]";
			}
			$("#messageDiv").append("<p>"+operationName+":"+$showMsg+"</p>");
		});
	};
	
	$("#modifyRepaymentPlanBtn").click(function() {
		$btn = $(this);
		$btn.attr("disabled", "disabled");
		var $uniqueId = $("[name='uniqueId1']").val();
		var $contractNo = $("[name='contractNo1']").val();		
		var $reason = $("[name='modifyRepaymentPlanReason']").val();
		var $data = $("[name='modifyRepaymentPlanData']").val();
		var params = {uniqueId:$uniqueId,contractNo:$contractNo,reason:$reason,data:$data};
		fun_deduct('${ctx}/manual-apis/modify-repayment-plan',params,"变更还款计划接口", $btn);
	});

	$("#businessPaymentVoucherBtn").click(function() {
		$btn = $(this);
		$btn.attr("disabled", "disabled");
		var $transactionType = $("[name='transactionType']").val();
		var $voucherType = $("[name='voucherType']").val();
		var $voucherAmount = $("[name='voucherAmount']").val();
		var $financialContractNo = $("[name='financialContractNo1']").val();
		var $receivableAccountNo = $("[name='receivableAccountNo']").val();
		var $paymentAccountNo = $("[name='paymentAccountNo']").val();
		var $paymentName = $("[name='paymentName']").val();
		var $paymentBank = $("[name='paymentBank']").val();
		var $bankTransactionNo = $("[name='bankTransactionNo']").val();
		var $detail = $("[name='detail']").val();

		var params = {transactionType:$transactionType,voucherType:$voucherType,voucherAmount:$voucherAmount,financialContractNo:$financialContractNo,receivableAccountNo:$receivableAccountNo,paymentAccountNo:$paymentAccountNo,paymentName:$paymentName,paymentBank:$paymentBank,bankTransactionNo:$bankTransactionNo,detail:$detail};
		fun_deduct('${ctx}/manual-apis/business-payment-voucher',params,"商户付款凭证接口", $btn);
	});
	
	$("#modifyRepaymentInformationBtn").click(function() {
		$btn = $(this);
		$btn.attr("disabled", "disabled");
		var $uniqueId = $("[name='uniqueId2']").val();
		var $contractNo = $("[name='contractNo2']").val();		
		var $bankCode = $("[name='bankCode']").val();
		var $bankAccount = $("[name='bankAccount']").val();
		var $bankName = $("[name='bankName']").val();
		var $bankProvince = $("[name='bankProvince']").val();
		var $bankCity = $("[name='bankCity']").val();

		var params = {uniqueId:$uniqueId,contractNo:$contractNo,bankCode:$bankCode,bankAccount:$bankAccount,bankName:$bankName,bankProvince:$bankProvince,bankCity$bankCity};
		fun_deduct('${ctx}/manual-apis/modify-repayment-information',params,"变更还款信息接口", $btn);
	});

	$("#prepaymentBtn").click(function() {
		$btn = $(this);
		$btn.attr("disabled", "disabled");
		var $uniqueId = $("[name='uniqueId3']").val();
		var $contractNo = $("[name='contractNo3']").val();		
		var $type = $("[name='type']").val();
		var $assetInitialValue = $("[name='assetInitialValue']").val();
		var $assetRecycleDate = $("[name='assetRecycleDate']").val();

		var params = {uniqueId:$uniqueId,contractNo:$contractNo,type:$type,assetInitialValue:$assetInitialValue,assetRecycleDate:$assetRecycleDate};
		fun_deduct('${ctx}/manual-apis/prepayment',params,"提前还款接口", $btn);
	});

	$("#importAssetPackageBtn").click(function() {
		$btn = $(this);
		$btn.attr("disabled", "disabled");
		var $assetPackageContent = $("[name='assetPackageContent']").val();

		var params = {assetPackageContent:$assetPackageContent};
		fun_deduct('${ctx}/manual-apis/import-asset-package',params,"导入资产包接口", $btn);
	});

	$("#modifyOverDueFeeBtn").click(function() {
		$btn = $(this);
		$btn.attr("disabled", "disabled");
		var $modifyOverDueFeeDetails = $("[name='modifyOverDueFeeDetails']").val();

		var params = {modifyOverDueFeeDetails:$modifyOverDueFeeDetails};
		fun_deduct('${ctx}/manual-apis/modify-overdue-fee',params,"逾期费用修改接口", $btn);
	});

	$("#queryRepaymentPlanBtn").click(function() {
		$btn = $(this);
		$btn.attr("disabled", "disabled");
		var $uniqueId = $("[name='uniqueId4']").val();
		var $contractNo = $("[name='contractNo4']").val();		
		
		var params = {uniqueId:$uniqueId,contractNo:$contractNo};
		fun_deduct('${ctx}/manual-apis/query-repayment-plan',params,"查询还款计划接口", $btn);
	});

	$("#queryRepaymentListBtn").click(function() {
		$btn = $(this);
		$btn.attr("disabled", "disabled");
		var $financialContractNo = $("[name='financialContractNo2']").val();
		var $queryStartDate = $("[name='queryStartDate']").val();
		var $queryEndDate = $("[name='queryEndDate']").val();		
		
		var params = {financialContractNo:$financialContractNo,queryStartDate:$queryStartDate,queryEndDate:$queryEndDate};
		fun_deduct('${ctx}/manual-apis/query-repayment-list',params,"查询还款清单接口", $btn);
	});

	$("#commandDeductBtn").click(function() {
		$btn = $(this);
		$btn.attr("disabled", "disabled");
		var $uniqueId = $("[name='uniqueId5']").val();
		var $contractNo = $("[name='contractNo5']").val();	
		var $deductId = $("[name='deductId']").val();
		var $financialProductCode = $("[name='financialProductCode']").val();
		var $apiCalledTime = $("[name='apiCalledTime']").val();
		var $deductAmount = $("[name='deductAmount']").val();
		var $repaymentType = $("[name='repaymentType']").val();
		var $repaymentDetails = $("[name='repaymentDetails']").val();
				
		
		var params = {uniqueId:$uniqueId,contractNo:$contractNo,deductId:$deductId,financialProductCode:$financialProductCode,apiCalledTime:$apiCalledTime,deductAmount:$deductAmount,repaymentType:$repaymentType,repaymentDetails:$repaymentDetails};
		fun_deduct('${ctx}/manual-apis/command-deduct',params,"执行扣款指令接口", $btn);
	});
});
</script>
</body>
</html>
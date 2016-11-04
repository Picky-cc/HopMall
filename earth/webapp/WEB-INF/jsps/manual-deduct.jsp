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
  <title>task- 五维金融金融管理平台</title>
</head>
<body>

  <%@ include file="/WEB-INF/include/header.jsp"%>
  
  <div class="web-g-main">

    <%@ include file="/WEB-INF/include/aside.jsp"%>
    <div class="content">
    <div class="scroller">
    	<h4>人工扣款</h4>
    		
		<div class="static-info-wrapper">
			<div class="lookup-params">
	           	<span class="item">
	              <select name="paymentChannel" class="form-control real-value small" id="appIdSelect">
	                <c:forEach var="item" items="${paymentChannelList}">
	                	<option value="${item.id}" >${item.channelName}</option>
	                </c:forEach>
	              </select>
	            </span>
	        </div>
	        <div class="lookup-params">
				<span class="item has-suffix">
	              <input type="text" placeholder="金额" value="0.01" class="form-control real-value" name="amount"/>
	            </span>
	            <span class="item has-suffix">
	              <input type="text" placeholder="银行代码" value="" class="form-control real-value" name="bankCode"/>
	            </span>
	            <span class="item has-suffix">
	              <input type="text" placeholder="银行卡号" value="" class="form-control real-value" name="accountNo">
	            </span>
	            <span class="item has-suffix">
	              <input type="text" placeholder="持卡人姓名" value="" class="form-control real-value" name="accountName">
	            </span>
	            <span class="item has-suffix">
	              <input type="text" placeholder="备注" value="" class="form-control real-value" name="remark">
	            </span>
	            <span class="item has-suffix">
	              <input type="text" placeholder="身份证号" value="" class="form-control real-value" name="idCardNum">
	            </span>
	            <span class="item has-suffix">
	              <input type="text" placeholder="开户行所在省" value="" class="form-control real-value" name="province">
	            </span>
	            <span class="item has-suffix">
	              <input type="text" placeholder="开户行所在市" value="" class="form-control real-value" name="city">
	            </span>
	            <button type="button" id="batchDeductBtn"
								class="btn btn-primary">批量扣款</button>
				&nbsp;&nbsp;
				<button type="button" id="realTimeDeductBtn"
								class="btn btn-primary">实时扣款</button>
			</div>
			<div class="lookup-params">			
				<span class="item has-suffix">
	              <input type="text" placeholder="交易号" value="" class="form-control real-value" name="queryReqNo">
	            </span>
	            <button type="button" id="queryBatchDeductBtn"
								class="btn btn-primary">查询扣款</button>
				
			</div>
			<c:set var="today">
							<fmt:formatDate value="<%=new Date() %>" pattern="yyyyMMdd"/>
			</c:set>
			<div class="lookup-params">			
				<span class="item has-suffix">
	              <input type="text" placeholder="起始日期YYYYMMDD" value="${today }" class="form-control real-value" name="beginDate">
	            </span>
	            <span class="item has-suffix">
	              <input type="text" placeholder="终止日期YYYYMMDD" value="${today }" class="form-control real-value" name="endDate">
	            </span>
	            <span class="item has-suffix">
	              <input type="text" placeholder="页码" value="1" class="form-control real-value" name="pageNum">
	            </span>
	            <span class="item has-suffix">
	              <input type="text" placeholder="账户类型" value="0" class="form-control real-value" name="accountType">
	            </span>
	            <button type="button" id="queryTransactionDetailBtn"
								class="btn btn-primary">查询交易明细</button>
				<button type="button" id="queryAccountDetailBtn"
								class="btn btn-primary">查询账户明细</button>
				
			</div>
			<div class="lookup-params">			
				<span class="item has-suffix">
	              <input type="text" placeholder="清算日期YYYYMMDD" value="${today }" class="form-control real-value" name="settDate">
	            </span>
	            <span class="item has-suffix">
	              <input type="text" placeholder="收付类型.收:S.付:F" value="S" class="form-control real-value" name="sfType">
	            </span>
	            <span class="item has-suffix">
	              <input type="text" placeholder="清算场次.默认01,中行为02" value="01" class="form-control real-value" name="settNo">
	            </span>
	            <button type="button" id="getSettFileUrlBtn"
								class="btn btn-primary">获取清算文件URL</button>
				
			</div>
			<div class="lookup-params">			
				<span class="item has-suffix">
	              <input type="text" placeholder="流水起始日期YYYYMMDD" value="${today }" class="form-control real-value" name="flowStartDate">
	            </span>
	            <span class="item has-suffix">
	              <input type="text" placeholder="流水终止日期YYYYMMDD" value="${today }" class="form-control real-value" name="flowEndDate">
	            </span>
	            <span class="item">
	              <select name="accountId" class="form-control real-value small">
	                <c:forEach var="item" items="${accountList}">
	                	<option value="${item.id}" >${item.accountName}(${item.accountNo})</option>
	                </c:forEach>
	              </select>
	            </span>
	            <button type="button" id="addBrushFlow"
								class="btn btn-primary">补刷银企直连流水</button>
				
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
	function fun_getDeductParams (transactionType){
		var $bankCode = $("[name='bankCode']").val();
		var $accountNo = $("[name='accountNo']").val();
		var $accountName = $("[name='accountName']").val();
		var $amount = $("[name='amount']").val();
		var $remark = $("[name='remark']").val();
		var $idCardNum = $("[name='idCardNum']").val();
		var $province = $("[name='province']").val();
		var $city = $("[name='city']").val();
		var $paymentChannelId = $("[name='paymentChannel']").val();
		return {
			bankCode:$bankCode,
			accountNo:$accountNo,
			accountName:$accountName,
			amount:$amount,
			remark:$remark,
			idCardNum:$idCardNum,
			province:$province,
			city:$city,
			paymentChannelId:$paymentChannelId,
			transactionType:transactionType
		};
	};
	
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

	$("#realTimeDeductBtn").click(function() {
		$btn = $(this);
		$btn.attr("disabled", "disabled");
		fun_deduct('${ctx}/manual-deduct/deduct',fun_getDeductParams(0),"实时扣款", $btn);
	});
	
	$("#batchDeductBtn").click(function(){
		$btn = $(this);
		$btn.attr("disabled", "disabled");
		fun_deduct('${ctx}/manual-deduct/deduct',fun_getDeductParams(1),"批量扣款", $btn);
	});
	
	$("#queryBatchDeductBtn").click(function() {
		$btn = $(this);
		$btn.attr("disabled", "disabled");
		var $paymentChannelId = $("[name='paymentChannel']").val();
		var $queryReqNo = $("[name='queryReqNo']").val();
		var params = {paymentChannelId:$paymentChannelId,queryReqNo:$queryReqNo};
		fun_deduct('${ctx}/manual-deduct/query-deduct',params,"查询批量扣款", $btn);
	});
	
	$("#queryTransactionDetailBtn").click(function() {
		$btn = $(this);
		$btn.attr("disabled", "disabled");
		var $paymentChannelId = $("[name='paymentChannel']").val();
		var $beginDate = $("[name='beginDate']").val();
		var $endDate = $("[name='endDate']").val();
		var $pageNum = $("[name='pageNum']").val();
		var params = {paymentChannelId:$paymentChannelId,beginDate:$beginDate,endDate:$endDate,pageNum:$pageNum};
		fun_deduct('${ctx}/manual-deduct/query-transaction-detail',params,"查询交易明细", $btn);
	});
	$("#queryAccountDetailBtn").click(function() {
		$btn = $(this);
		$btn.attr("disabled", "disabled");
		var $paymentChannelId = $("[name='paymentChannel']").val();
		var $beginDate = $("[name='beginDate']").val();
		var $endDate = $("[name='endDate']").val();
		var $pageNum = $("[name='pageNum']").val();
		var $accountType = $("[name='accountType']").val();
		var params = {paymentChannelId:$paymentChannelId,beginDate:$beginDate,endDate:$endDate,pageNum:$pageNum,accountType:$accountType};
		fun_deduct('${ctx}/manual-deduct/query-account-detail',params,"查询账户明细", $btn);
	});
	$("#getSettFileUrlBtn").click(function() {
		$btn = $(this);
		$btn.attr("disabled", "disabled");
		var $paymentChannelId = $("[name='paymentChannel']").val();
		var $settDate = $("[name='settDate']").val();
		var $settNo = $("[name='settNo']").val();
		var $sfType = $("[name='sfType']").val();
		var params = {paymentChannelId:$paymentChannelId,settDate:$settDate,settNo:$settNo,sfType:$sfType};
		fun_deduct('${ctx}/manual-deduct/get-sett-file-url',params,"获取清算文件URL", $btn);
	});
	$("#addBrushFlow").click(function(){
		$btn = $(this);
		$btn.attr("disabled", "disabled");
		var $accountId = $("[name='accountId']").val();
		var $flowStartDate = $("[name='flowStartDate']").val();
		var $flowEndDate = $("[name='flowEndDate']").val();
		var params ={accountId:$accountId,flowStartDate:$flowStartDate,flowEndDate:$flowEndDate};
		fun_deduct('${ctx}/manual-deduct/add-brush-flow',params,"补刷银企直连流水", $btn);
	});
});
</script>
</body>
</html>
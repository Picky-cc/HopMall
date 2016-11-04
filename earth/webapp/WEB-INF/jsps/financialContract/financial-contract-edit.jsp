<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

<fmt:setBundle basename="ApplicationMessage" />
<fmt:setLocale value="zh_CN" />

<%@ include file="/WEB-INF/include/meta.jsp"%>
<%@ include file="/WEB-INF/include/css-newlayout.jsp"%>
<title>编辑信托合同 - 五维金融金融管理平台</title>
</head>
<body>

	<%@ include file="/WEB-INF/include/header.jsp"%>

	<div class="web-g-main">

		<%@ include file="/WEB-INF/include/aside.jsp"%>
		<div data-commoncontent='true' class="content">
			<div class="position-map">
				<div class="pull-left">
					当前位置: <span class="item current">编辑信托合同</span>
				</div>
				<div class="pull-right">
					<a href="javascript:window.history.go(-1);"
						class="back btn btn-default">&lt;&lt; 返回</a>
				</div>
			</div>

			<c:if test="${not empty infoMessage}">
				<div
					class="alert alert-success alert-dismissable alert-fade top-margin-10 text-align-center">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<i class="glyphicon glyphicon-info-sign"></i>&nbsp;&nbsp;
					<fmt:message key="${infoMessage}" />
				</div>
			</c:if>

			<form id="financialContractCreate" class="form-horizontal"
				data-validate="true">

				<div>
					<div class="panel panel-default feature-panel">
						<div class="panel-body">
							<div class="form-group demo2do-form-group">
								<input id="financialContractId" type="hidden" name="filter"
									value="true" value="${financialContract.id}"> <label
									class="col-xs-2 control-label">信托产品代码</label>
								<div class="col-xs-3">
									<input type="text" name="financialContractNo"
										id="financialContractNo" class="form-control"
										data-validate="required"
										value="${financialContract.contractNo}" />
								</div>


							</div>

							<div class="form-group demo2do-form-group row">
								<label class="col-xs-2 control-label pull-left">信托合同名称</label>
								<div class="col-xs-3 pull-left">
									<input type="text" name="financialContractName"
										id="financialContractName" class="form-control"
										data-validate="required"
										value="${financialContract.contractName}" />
								</div>
								<div class="col-xs-1"></div>
								<label class="col-xs-2 control-label ">信托合同类型</label>
								<div class="col-xs-2 pull-left">
									<select name="financialContractType"
										id="financialContractType" class="form-control">
										<c:forEach var="item" items="${financialContractTypeList}">
											<option
												<c:if test="${item.ordinal eq  financialContractType.ordinal}">selected</c:if>
												value="${item.ordinal}"><fmt:message
													key="${item.key}" /></option>
										</c:forEach>
									</select>
								</div>


							</div>
							<div class="form-group demo2do-form-group">
								<label class="col-xs-2 control-label">信托商户名称</label>
								<div class="col-xs-4">
									<div class="input-group">
										<select name="appId" id="appId" class="form-control">
											<c:forEach var="item" items="${appList}">
												<option value="${item.id}"
													<c:if test="${item.id eq app.id }">selected</c:if>>${item.name}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<label class="col-xs-2 control-label">信托公司</label>
								<div class="col-xs-4">
									<div class="input-group">
										<select name="companyId" id="companyId" class="form-control">
											<c:forEach var="item" items="${companyList}">
												<option value="${item.id}"
													<c:if test="${item.id eq company.id}">selected</c:if>>${item.shortName}</option>
											</c:forEach>
										</select>
									</div>
								</div>

							</div>


							<div class="form-group demo2do-form-group">


								<label class="col-xs-2 control-label">信托合同起始日</label>
								<div class="col-xs-4">
									<div class="input-group">
										<input type="date" class="form-control input-date"
											name="advaStartDate" id="advaStartDate"
											value="<fmt:formatDate value="${financialContract.advaStartDate}" pattern="yyyy-MM-dd"/>" />
									</div>
								</div>

								<label class="col-xs-2 control-label">信托合同截至日</label>
								<div class="col-xs-4">
									<div class="input-group">
										<input type="date" class="form-control input-date"
											name="thruDate" id="thruDate"
											value="<fmt:formatDate value="${financialContract.thruDate}" pattern="yyyy-MM-dd"/>" />
									</div>
								</div>
							</div>


							<div class="form-group demo2do-form-group">

								<label class="col-xs-2 control-label">信托专户户名</label>
								<div class="col-xs-3">
									<input type="text" name="trustsAccountName"
										class="form-control" id="trustsAccountName"
										data-validate="required"
										value="${financialContract.capitalAccount.accountName}" />
								</div>
								<div class="col-xs-1"></div>
								<label class="col-xs-2 control-label">信托专户开户行</label>
								<div class="col-xs-3">
									<input type="text" name="trustsBankName" class="form-control"
										id="trustsBankName" data-validate="required"
										value="${financialContract.capitalAccount.bankName}" />
								</div>


							</div>
							<div class="form-group demo2do-form-group">
								<label class="col-xs-2 control-label">信托专户账号</label>
								<div class="col-xs-3">
									<input type="text" name="trustsAccountNo" class="form-control"
										id="trustsAccountNo" data-validate="required"
										value="${financialContract.capitalAccount.accountNo}" />
								</div>
								<div class="col-xs-1"></div>

								<%-- <label class="col-xs-2 control-label">回款通道名称</label>
								<div class="col-xs-2">
									<select name="paymentChannel" id="paymentChannel"
										class="form-control">
										<c:forEach var="item" items="${paymentChannelTypeList}">
											<option value="${item.id}"
												<c:if test="${item.id eq paymentChannel.id}">selected</c:if>>${item.channelName}</option>
										</c:forEach>
									</select>
								</div> --%>
							</div>
							<div class="form-group demo2do-form-group">
								<label class="col-xs-2 control-label">贷款差异</label>
								<div class="col-xs-4">
									<div class="input-group">
										<input type="number" id="loanOverdueStartDay" step="1"
											min="0" class="form-control" data-validate="number"
											value="${financialContract.loanOverdueStartDay}" /> <span
											class="input-group-addon">至</span> <input type="number"
											step="1" min="0" class="form-control" id="loanOverdueEndDay"
											data-validate="number"
											value="${financialContract.loanOverdueEndDay}" /> <span
											class="input-group-addon">自然日</span>
									</div>
								</div>

								<label class="col-xs-2 control-label">贷款坏账</label>
								<div class="col-xs-4">
									<div class="input-group">
										<span class="input-group-addon">大于等于</span> <input
											type="number" step="1" min="0" class="form-control"
											name="advaRepoTerm" id="advaRepoTerm" data-validate="number"
											value="${financialContract.advaRepoTerm}" /> <span
											class="input-group-addon">自然日</span>
									</div>
								</div>
							</div>
							<div class="form-group demo2do-form-group">
								<label class="col-xs-2 control-label">资产包格式</label>
								<div class="col-xs-2">
									<select name="assetPackageFormat" id="assetPackageFormat"
										class="form-control">
										<c:forEach var="item" items="${assetPackageFormatList}">
											<option value="${item.ordinal}"
												<c:if test="${item.ordinal eq  assetPackageFormat.ordinal}">selected</c:if>><fmt:message
													key="${item.key}" /></option>
										</c:forEach>
									</select>
								</div>
								<div class="col-xs-2"></div>

								<label class="col-xs-2 control-label">商户打款宽限日</label>
								<div class="col-xs-2">
									<div class="input-group">
										<input type="number" step="1" min="0" class="form-control"
											name="advaMatuterm" id="advaMatuterm" data-validate="number"
											value="${financialContract.advaMatuterm}" /><span
											class="input-group-addon">工作日</span>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
			<div class="text-center vertical-margin-20">

				<button id="submit" type="submit"
					class="btn btn-success btn-shadow btn-shadow-success demo2do-btn">提交</button>
				&nbsp;&nbsp;&nbsp;&nbsp;


			</div>

		</div>

	</div>

	<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>

	<script type="text/javascript">
		$("#submit")
				.click(
						function() {
							var financialContractId = $("#financialContractId")
									.val();
							var financialContractNo = $("#financialContractNo")
									.val();
							var financialContractName = $(
									"#financialContractName").val();
							var companyId = $("#companyId").val();
							var appId = $("#appId").val();
							var financialContractType = $(
									"#financialContractType").val();
							var loanOverdueStartDay = $("#loanOverdueStartDay")
									.val();
							var loanOverdueEndDay = $("#loanOverdueEndDay")
									.val();
							var advaRepoTerm = $("#advaRepoTerm").val();
							var advaMatuterm = $("#advaMatuterm").val();
							var advaStartDate = $("#advaStartDate").val();
							var thruDate = $("#thruDate").val();
							var trustsAccountName = $("#trustsAccountName")
									.val();
							var trustsBankName = $("#trustsBankName").val(); //house
							var trustsAccountNo = $("#trustsAccountNo").val();
							var paymentChannel = $("#paymentChannel").val();
							var assetPackageFormat = $("#assetPackageFormat")
									.val();

							/*  var regu = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
							 if (!regu.test(otsdAmt) || !regu.test(otsdAmtAval) || !regu.test(recTransfeeTotalAmt)) {
							       alert('请输入正确的金额！');
							       return;
							 } */

							$
									.ajax({
										type : 'POST',
										url : '${ctx}/financialContract/edit-financialContract/${financialContractId}',
										data : {
											'financialContractNo' : financialContractNo,
											'financialContractName' : financialContractName,
											'companyId' : companyId,
											'appId' : appId,
											'financialContractType' : financialContractType,
											'loanOverdueStartDay' : loanOverdueStartDay,
											'loanOverdueEndDay' : loanOverdueEndDay,
											'advaRepoTerm' : advaRepoTerm,
											'advaMatuterm' : advaMatuterm,
											'advaStartDate' : advaStartDate,
											'thruDate' : thruDate,
											'trustsAccountName' : trustsAccountName,
											'trustsBankName' : trustsBankName,
											'trustsAccountNo' : trustsAccountNo,
											'paymentChannel' : paymentChannel,
											'assetPackageFormat' : assetPackageFormat
										},
										error : function() {
											alert('网络错误！稍后再试！');
										},
										success : function(data) {
											var result = JSON.parse(data)
											alert(result.message);
											window.location = "${ctx}/financialContract";
										}
									});

						});
	</script>
</body>
</html>
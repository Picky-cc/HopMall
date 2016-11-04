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
<title>信托合同详情 - 五维金融金融管理平台</title>
</head>
<body>

	<%@ include file="/WEB-INF/include/header.jsp"%>

	<div class="web-g-main">

		<%@ include file="/WEB-INF/include/aside.jsp"%>
		<div class="content">
			<div class="scroller">
				<div class="position-map">
					<div class="pull-left">
						当前位置: <span class="item current">信托合同详情</span>
					</div>
					<div class="pull-right">
						<sec:authorize ifNotGranted="ROLE_TRUST_OBSERVER">
							<a href="${ctx}/financialContract/edit-show-financialContract/${financialContract.id}" class="btn btn-primary">编辑</a>
						</sec:authorize>
						<button type="button" onclick="window.history.go(-1);" class="btn btn-default">&lt;&lt; 返回</button>
					</div>
				</div>

				<div class="col-layout-detail">
					<div class="top">
						<div class="block" >
							<h5 class="hd">信托合同信息</h5>
							<div class="bd">
								<div class="col">
									<p>信托合同名称 ：${financialContract.contractName}</p>
									<p>信托产品代码 ：${financialContract.contractNo}</p>
									<p>信托合同类型 ：<fmt:message key="${financialContract.financialContractType.key}"/></p>
									<p>信托合同起始日 ：<fmt:formatDate value="${financialContract.advaStartDate}" pattern="yyyy年MM月dd日 "/></p>
									<p>信托合同截止日 ：<fmt:formatDate value="${financialContract.thruDate}" pattern="yyyy年MM月dd日 "/></p>
								</div>
							</div>
						</div>
						<div class="block" >
							<h5 class="hd">信托专户信息</h5>
							<div class="bd">
								<div class="col">
									<p>信托专户户名 ：${financialContract.capitalAccount.accountName}</p>
									<p>信托专户开户行 ：${financialContract.capitalAccount.bankName}</p>
									<p>信托专户账号 ：${financialContract.capitalAccount.accountNo}</p>
									<p>回款通道名称 ：${financialContract.paymentChannel.channelName}</p>
								</div>
							</div>
						</div>
						<div class="block" >
							<h5 class="hd">信托商户信息</h5>
							<div class="bd">
								<div class="col">
									<p>信托商户名称 ：${financialContract.app.name}</p>
								</div>
							</div>
						</div>
						<div class="block" >
							<h5 class="hd">信托资产信息</h5>
							<div class="bd">
								<div class="col">
									<p>贷款差异 ：${financialContract.loanOverdueStartDay}至${financialContract.loanOverdueEndDay}自然日</p>
									<p>贷款坏账 ：大于等于${financialContract.advaRepoTerm}自然日</p>
									<p>资产包格式 ：<fmt:message key="${financialContract.assetPackageFormat.key }" /></p>
									<p>商户打款宽限日 ：${financialContract.advaMatuterm}工作日</p>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="table-layout-detail">
					<div class="block contracts">
						<h5 class="hd">贷款合同<button type="button" class="btn btn-default pull-right export" data-action="${ctx}/contracts/exprot-loan-contract">导出贷款合同</button></h5>
						<div class="bd">
							<table class="data-list" data-action="${ctx}/financialContract/${financialContract.id}/contracts" data-autoload="true">
								<thead>
									<tr>
										<th>序号</th>
										<th>贷款合同编号</th>
										<th>资产类型</th>
										<th>资产编号</th>
										<th>贷款利率</th>
										<th>生效日期</th>
										<th>贷款方式</th>
										<th>期数</th>
										<th>还款周期</th>
										<th>客户姓名</th>
										<th>贷款总额</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<script class="template" type="script/template">
				    					{% _.each(list, function(item, index) { %}
				        					<tr>
												<td>{%= index + 1 %}</td>
												<td><a href="${ctx}/contracts/detail?id={%= item.id %}">{%= item.contractNo %}</a></td>
												<td>{%= item.assetTypeMsg %}</td>
												<td>{%= item.house.address %}</td>
												<td>{%= (+item.interestRate) %}</td>
												<td>{%= new Date(item.beginDate).format('yyyy-MM-dd') %}</td>
												<td>{%= item.repaymentWayMsg %}</td>
												<td>{%= item.periods %}</td>
												<td>{%= item.paymentFrequency %}月付</td>
												<td>{%= item.customer.name %}</td>
												<td>{%= (+item.totalAmount).formatMoney(2, '') %}</td>
												<td><a href="${ctx}/contracts/detail?id={%= item.id %}">详情</a></td>
				        					</tr>
				   						{% }); %}
									</script>
								</tbody>
							</table>
						</div>
						<div class="ft">
							<jsp:include page="/WEB-INF/include/plugins/assembly.jsp">
					            <jsp:param value="page-control" name="type"/>
					            <jsp:param value="true" name="advanced"/>
					        </jsp:include>
						</div>
					</div>

					<div class="block">
						<jsp:include page="/WEB-INF/include/system-operate-log.jsp">
				            <jsp:param value="${financialContract.financialContractUuid}" name="objectUuid"/>
			            </jsp:include>
					</div>
				</div>
			</div>
		</div>

	</div>

	<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>

</body>
</html>

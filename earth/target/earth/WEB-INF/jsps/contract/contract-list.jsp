<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.zufangbao.gluon.spec.global.GlobalCodeSpec"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

<fmt:setBundle basename="ApplicationMessage" />
<fmt:setLocale value="zh_CN" />

<%@ include file="/WEB-INF/include/meta.jsp"%>
<%@ include file="/WEB-INF/include/css-newlayout.jsp"%>
<title>贷款合同 - 五维金融金融管理平台</title>
</head>
<body>

	<%@ include file="/WEB-INF/include/header.jsp"%>

	<div class="web-g-main">

		<%@ include file="/WEB-INF/include/aside.jsp"%>
		<div class="content">
			<div class="scroller">
				<div class="lookup-params">
					<div class="inner clearfix">
						<input type="hidden" name="filter" value="true" class="real-value">
						<span class="item">
							<select name="financialContractIds" class="form-control real-value selectpicker" multiple data-title="信托合同项目"  data-actions-box="true">
								<c:forEach var="item" items="${financialContracts}">
									<option value="${item.id}">${item.contractName}(${item.contractNo })</option>
								</c:forEach>
							</select>
						</span>
						<span class="item">
							<select name="contractStateOrdinals" class="form-control real-value selectpicker" multiple data-title="合同状态"  data-actions-box="true">
								<c:forEach var="item" items="${contractStates}">
									<option value="${item.ordinal()}"><fmt:message key="${item.key}"/></option>
								</c:forEach>
							</select>
						</span>
						<span class="item"> 
						 	<input type="text" class="form-control real-value" name="contractNo" value="" placeholder="请输入贷款合同编号">
						</span>
						<span class="item"> 
							<input type="text" class="form-control real-value" name="underlyingAsset" value="" placeholder="请输入资产编号">
						</span> 
						<span class="item"> 
							<input type="text" class="form-control real-value" name="customerName" value="" placeholder="请输入客户姓名">
						</span> 
						<span class="item beginend-datepicker"> 
							<jsp:include page="/WEB-INF/include/plugins/datetimepicker.jsp">
								<jsp:param value="group" name="type" />
								<jsp:param value="startDateString" name="paramName1" />
								<jsp:param value="endDateString" name="paramName2" />
								<jsp:param value="请输入生效起始日期" name="placeHolder1" />
								<jsp:param value="请输入生效终止日期" name="placeHolder2" />
							</jsp:include>
						</span> 
						<span class="item">
							<button id="lookup" class="btn btn-primary">
								查询
							</button>
						</span>
					</div>
				</div>
				<div class="table-area">
					<table class="data-list" data-action="./contracts/query" data-autoload="true">
						<thead>
							<tr>
								<th>贷款合同编号</th>
								<th>资产编号</th>
								<th>贷款利率</th>
								<th>生效日期</th>
								<th>合同状态</th>
								<th>贷款方式</th>
								<th>期数</th>
								<th>还款周期</th>
								<th>客户姓名</th>
								<th>贷款总额</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<script type="script/template" id="tableFieldTmpl">
							    {% _.each(list, function(contract, index) { %}
							        <tr data-id="{%= contract.id %}">
										<td><a href="${ctx}/contracts/detail?id={%= contract.id %}">{%= contract.contractNo %}</a></td>
										<td>{%= contract.house.address %}</td>
										<td>{%= (+contract.interestRate * 100).formatPercent(2) %}</td>
										<td>{%= new Date(contract.beginDate).format('yyyy-MM-dd') %}</td>
										<td>{%= contract.stateMsg %}</td>
										<td>{%= contract.repaymentWayMsg %}</td>
										<td>{%= contract.periods %}</td>
										<td>{%= contract.paymentFrequency %}月付</td>
										<td>{%= contract.customer.name %}</td>
										<td>{%= (+contract.totalAmount).formatMoney(2, '') %}</td>
										<td><a href="${ctx}/contracts/detail?id={%= contract.id %}">详情</a></td>
							        </tr>
							    {% }); %}
						    </script>
						</tbody>
					</table>
				</div>
			</div>
			<div class="operations">
					<button data-action="./contracts/exprot-loan-contract" type="button" class="btn export-excel">导出贷款合同</button>
					<jsp:include page="/WEB-INF/include/plugins/assembly.jsp">
			            <jsp:param value="page-control" name="type"/>
			            <jsp:param value="true" name="advanced"/>
		            </jsp:include>
			</div>
		</div>
	</div>

	<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>
</body>
</html>

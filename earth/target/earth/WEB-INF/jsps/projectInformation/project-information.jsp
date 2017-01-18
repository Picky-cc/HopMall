
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
<title>项目信息列表 - 五维金融金融管理平台</title>
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
							<select name="financialContractId" class="form-control real-value">
								<option value="-1">信托合同项目</option>
								<c:forEach var="item" items="${financialContracts}">
									<option value="${item.id }">${item.contractName}(${item.contractNo })</option>
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
								
								
								<jsp:param value="loanEffectStartDate" name="paramName1" />
								<jsp:param value="loanEffectEndDate" name="paramName2" />
								<jsp:param value="请输入生效起始日期" name="placeHolder1" />
								<jsp:param value="请输入生效终止日期" name="placeHolder2" />
							</jsp:include>
						</span> 
						<span class="item beginend-datepicker"> 
							<jsp:include page="/WEB-INF/include/plugins/datetimepicker.jsp">
								<jsp:param value="group" name="type" />
								
								
								<jsp:param value="loanExpectTerminateStartDate" name="paramName1" />
								<jsp:param value="loanExpectTerminateEndDate" name="paramName2" />
								<jsp:param value="请输入预计终止起始日期" name="placeHolder1" />
								<jsp:param value="请输入预计终止终止日期" name="placeHolder2" />
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
					<table class="data-list" data-action="./project-information/query" data-autoload="true">
						<thead>
							<tr>
								<th>贷款合同编号</th>
								<th>资产编号</th>
								<th>贷款利率</th>
								<th>生效日期</th>
								<th>预计终止日期</th>
								<th>实际终止日期</th>
								<th>还款进度</th>
								<th>贷款方式</th>
								<th>还款周期</th>
								<th>还款日期</th>
								<th>客户姓名</th>
								<th>贷款总额</th>
								<th>本期还款金额</th>
								<th>本期还款利息</th>
								<th>还款情况</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<script type="script/template" id="tableFieldTmpl">
							    {% _.each(list, function(projectInformationShowVO, index) { %}
							        <tr>
										<td>{%= projectInformationShowVO.contractNo %}</td>
										<td>{%= projectInformationShowVO.assetNo %}</td>
										<td>{%= (+projectInformationShowVO.loanRate * 100).formatPercent(2) %}</td>
										<td>{%= new Date(projectInformationShowVO.effectDate).format('yyyy-MM-dd') %}</td>
                                        <td>{%= new Date(projectInformationShowVO.expectTerminalDate).format('yyyy-MM-dd') %}</td>
                                        <td>{%= new Date(projectInformationShowVO.actualTermainalDate).format('yyyy-MM-dd') %}</td>
										<td>{%= projectInformationShowVO.repaymentSchedule %}</td>
										<td>{%= projectInformationShowVO.loanType %}</td>
										<td>{%= projectInformationShowVO.repaymentCycle %}月付</td>
										<td>{%= new Date(projectInformationShowVO.repaymentDate).format('yyyy-MM-dd') %}</td>
                                        <td>{%= projectInformationShowVO.customerName %}</td>
                                        <td>{%= (+projectInformationShowVO.loanAmount).formatMoney(2, '') %}</td>
                                        <td>{%= (+projectInformationShowVO.currentPeriodRepaymentAmount).formatMoney(2, '') %}</td>
                                        <td>{%= (+projectInformationShowVO.currentPeriodRepaymentInterest).formatMoney(2, '') %}</td>
                                        <td>{%= projectInformationShowVO.repaymentSituation%}</td>
										<td><a href="${ctx}/contracts/detail?id={%= projectInformationShowVO.contractId %}">详情</a></td>
							        </tr>
							    {% }); %}
						    </script>
						</tbody>
					</table>
				</div>
			</div>
			<div class="operations">
					<button data-action="./project-information/export-excel" type="button" class="btn export-excel">导出项目信息</button>
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


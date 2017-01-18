<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

<fmt:setBundle basename="ApplicationMessage" />
<fmt:setLocale value="zh_CN" />

<%@ include file="/WEB-INF/include/meta.jsp"%>
<%@ include file="/WEB-INF/include/css-newlayout.jsp"%>
<title>还款列表 - 五维金融金融管理平台</title>
</head>
<body>

	<%@ include file="/WEB-INF/include/header.jsp"%>
	<div class="web-g-main">
		<%@ include file="/WEB-INF/include/aside.jsp"%>
		<div class="content">
			<div class="scroller">
				<div class="lookup-params">
					<div class="inner clearfix">
						<span class="item" style="width: 127px;"> 
							<select name="financialContractIds" class="form-control real-value selectpicker" multiple data-title="信托合同项目" data-actions-box="true">
								<c:forEach var="item" items="${financialContracts}">
									<option value="${item.id }">${item.contractName}(${item.contractNo })</option>
								</c:forEach>
							</select>
						</span>
						<span class="item">
							<input type="text" class="form-control real-value" name="singleLoanContractNo" placeholder="还款编号" >
						</span>
						<span class="item">
							<input type="text" class="form-control real-value" name="contractNo" placeholder="合同编号">
						</span>
						<span class="item">
							<input type="text" class="form-control real-value" name="customerName" placeholder="客户姓名">
						</span>
						<span class="item beginend-datepicker">
			              <jsp:include page="/WEB-INF/include/plugins/datetimepicker.jsp">
			                <jsp:param value="group" name="type"/>
			                <jsp:param value="false" name="calendarbtn"/>
			                <jsp:param value="true" name="clearbtn"/>
			                <jsp:param value="startDate" name="paramName1"/>
			                <jsp:param value="endDate" name="paramName2"/>
			                <jsp:param value="请输入计划还款起始日期" name="placeHolder1"/>
			                <jsp:param value="请输入计划还款终止日期" name="placeHolder2"/>
			              </jsp:include>
			            </span>
			            <span class="item">
							<select class="form-control real-value selectpicker" name="paymentStatusOrdinals" multiple data-title="还款状态"  data-actions-box="true">
								<c:forEach var="payment_status" items="${paymentStatusList }">
									<option value="${payment_status.ordinal() }">
										<fmt:message key="${payment_status.key}"></fmt:message>
									</option>
								</c:forEach>
						</select>
						</span>
						<span class="item">
							<select
								class="form-control real-value" name="overDueStatus">
								<option value="">差异状态</option>
								<c:forEach var="overDueStatus" items="${overDueStatusList }">
									<option value="${overDueStatus.ordinal() }"><fmt:message key="${overDueStatus.key}" ></fmt:message></option>
								</c:forEach>
							</select>
						</span>
						<span class="item">
							<select class="form-control real-value selectpicker" name="auditOverDueStatusOrdinals" multiple data-title="逾期状态"  data-actions-box="true">
								<c:forEach var="auditOverdueStatus" items="${auditOverdueStatusList }">
									<option value="${auditOverdueStatus.ordinal() }"><fmt:message key="${auditOverdueStatus.key}" ></fmt:message></option>
								</c:forEach>
							</select>
						</span>
			            <span class="item">
			              <button id="lookup" class="btn btn-primary">查询</button>
			            </span>
					</div>
				</div>
	
				<div class="table-area">
					<table class="data-list" data-action="${ctx}/assets/query" data-autoload="true">
						<thead>
							<tr>
								<th>序号</th>
								<th>还款编号</th>
								<th>贷款合同编号</th>
								<th>信托产品代码</th>
								<th>信托项目名称</th>
								<th>资产编号</th>
								<th>客户姓名</th>
								<th>计划还款日期</th>
								<th>当前期数</th>
								<th>总期数</th>
								<th>计划还款本金</th>
								<th>计划还款利息</th>
								<th>计划还款金额</th>
								<th>差异天数</th>
								<th>差异罚息金额</th>
								<th>应还款金额</th>
								<th>实际还款金额</th>
								<th>实际还款日期</th>
								<th>退款金额</th>
								<th>还款状态</th>
								<th>担保状态</th>
								<th>逾期天数</th>
								<th>备注</th>
								<th width="60">操作</th>
							</tr>
						</thead>
						<tbody>
							<script type="script/template" id="tableFieldTmpl">
							    {% _.each(list, function(assetSetModel, index){ %}
							        <tr>
							            <td>{%= index + 1%}</td>
							            <td><a href="${ctx}/assets/{%= assetSetModel.assetSetId %}/detail">{%= assetSetModel.singleLoanContractNo %}</a></td>
							            <td>
							                <p style="width:80px; cursor: pointer;" class="text-overflow" data-title="{%= assetSetModel.contractNo %}">{%= assetSetModel.contractNo %}</p>
							            </td>
							            <td>{%= assetSetModel.financialContractNo %}</td>
										<td>{%= assetSetModel.financialProjectName %}</td>
										<td>{%= assetSetModel.assetNo %}</td>
										<td>{%= assetSetModel.customerName %}</td>
							            <td>{%= new Date(assetSetModel.assetRecycleDate).format('yyyy-MM-dd') %}</td>
										<td>{%= assetSetModel.currentPeriod %}</td>                                         
										<td>{%= assetSetModel.allPeriods %}</td>
                                        <td>{%= (+assetSetModel.assetPrincipalValue).formatMoney(2,'') %}</td>                                         
										<td>{%= (+assetSetModel.assetInterestValue).formatMoney(2,'') %}</td>
							            <td>{%= (+assetSetModel.assetInitialValue).formatMoney(2,'') %}</td>
							            <td>{%= assetSetModel.overDueDays %}</td>
							            <td>{%= (+assetSetModel.penaltyInterestAmount).formatMoney(2,'') %}</td>
							            <td>{%= (+assetSetModel.amount ).formatMoney(2,'')%}</td>
										<td>{%= (+assetSetModel.actualAmount).formatMoney(2,'') %}</td>
                                        <td>{%= new Date(assetSetModel.actualRecycleDate).format('yyyy-MM-dd') %}</td>
							            <td>{%= (+assetSetModel.refundAmount).formatMoney(2, '') %}</td>
							            <td>{%= assetSetModel.paymentStatus %}</td>
							            <td>{%= assetSetModel.guaranteeStatus %}</td>
							            <td><span class="{%= +assetSetModel.auditOverdueDays > 0 ? 'color-danger':'' %}">{%= assetSetModel.auditOverdueDays %}</span></td>
							            <td>{%= assetSetModel.comment %}</td>
							            <td>
							                <a href="${ctx}/assets/{%= assetSetModel.assetSetId %}/detail">详情</a>
							            </td>
							        </tr>
							    {% }); %}
							</script>
						</tbody>
					</table>
				</div>
			</div>
			<div class="operations">
				<button data-action="./assets/exprot-repayment-plan-detail" type="button" class="btn export-excel">导出还款计划汇总表</button>
				<button data-action="./assets/exprot-overDue-repayment-plan-detail" type="button" class="btn export-excel">导出逾期还款明细表</button>
				<button data-action="./assets/exprot-repayment-management" type="button" class="btn export-excel">导出还款管理表</button>
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

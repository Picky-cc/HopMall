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
									<option value="${item.uuid }">${item.contractName}(${item.contractNo })</option>
								</c:forEach>
							</select>
						</span>
						<span class="item">
							<select name="repaymentType" class="form-control real-value selectpicker" multiple data-title="扣款单还款类型" data-actions-box="true">
								<c:forEach var="item" items="${repaymentType}">
									<option value="${item.ordinal }"><fmt:message key="${item.key}"/></option>
								</c:forEach>
							</select>
						</span>
						<span class="item">
							<select name="executionStatus" class="form-control real-value selectpicker" multiple data-title="扣款状态" data-actions-box="true">
								<c:forEach var="item" items="${orderStatus}">
									<option value="${item.ordinal }"><fmt:message key="${item.key}"/></option>
								</c:forEach>
							</select>
						</span>
						<span class="item"> 
						 	<input type="text" class="form-control real-value" name="loanContractNo" value="" placeholder="请输入贷款合同编号">
						</span>
						<span class="item"> 
							<input type="text" class="form-control real-value" name="customerName" value="" placeholder="请输入客户姓名">
						</span> 
						<span class="item beginend-datepicker"> 
							<jsp:include page="/WEB-INF/include/plugins/datetimepicker.jsp">
								<jsp:param value="group" name="type" />
								<jsp:param value="false" name="calendarbtn" />
								<jsp:param value="true" name="clearbtn" />
								<jsp:param value="receiveStartDate" name="paramName1" />
								<jsp:param value="receiveEndDate" name="paramName2" />
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
					<table class="data-list" 
						data-action="./application/query" 
						data-autoload="true">
						<thead>
							<tr>
								<th>扣款编号</th>
								<th>扣款单还款类型</th>
								<th>贷款合同编号</th>
								<th>信托产品代码</th>
								<th>信托项目名称</th>
								<th>客户姓名</th>
								<th>扣款金额</th>
								<th>实际扣款金额</th>
								<th>扣款请求受理时间</th>
								<th>扣款状态</th>
								<th>备注</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<script type="script/template" id="tableFieldTmpl">
								{% _.each(list, function(item, index) { %}
									<tr>
										<td>{%= item.deudctNo %}</td>
										<td>{%= item.repaymentType %}</td>
										<td>{%= item.loanContractNo %}</td>
										<td>{%= item.financialContractCode %}</td>
										<td>{%= item.financialProjectName %}</td>
										<td>{%= item.customerName %}</td>
										<td>{%= item.planDeductAmount %}</td>
										<td>{%= item.actualDeductAmount %}</td>
										<td>{%= item.deductApplicationDate %}</td>
										<td>{%= item.deductStatus %}</td>
										<td>{%= item.remark %}</td>
										<td><a href="./application/detail/{%= item.deudctNo %}">详情</a></td>
									</tr>
								{% }); %}
							</script>
						</tbody>
					</table>
				</div>
			</div>
			<div class="operations">
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

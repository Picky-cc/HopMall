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
<title>充值单列表 - 五维金融金融管理平台</title>
</head>
<body>

	<%@ include file="/WEB-INF/include/header.jsp"%>

	<div class="web-g-main">
		<%@ include file="/WEB-INF/include/aside.jsp"%>
		<div class="content">
			<div class="scroller">
				<div class="lookup-params">
					<div class="inner clearfix">
						<div class="pull-left">
							<input type="hidden" name="filter" value="true">
							<span class="item">
								<select class="form-control real-value" name="financialContractUuid" >
									<option value="">信托项目名称</option>
									<c:forEach var="item" items="${financialContracts }">
										<option value="${item.financialContractUuid}">${item.contractName }</option>
									</c:forEach>
								</select>
							</span> 
							<span class="item"> 
								<select class="form-control real-value" name="sourceDocumentStatus" >
									<option value="-1">状态</option>
									<c:forEach var="item" items="${sourceDocumentStatusList }">
										<option value="${item.ordinal}"><fmt:message key="${item.key}" /></option>
									</c:forEach>
								</select>
							</span>
							<span class="item">
								<select class="form-control real-value" name="customerType" >
									<option value="-1">客户类型</option>
									<c:forEach var="item" items="${customerTypeList }">
										<option value="${item.ordinal}"><fmt:message key="${item.key}" /></option>
									</c:forEach>
								</select>
							</span>
							<span class="item">
								<input type="text" class="form-control large real-value " name="key" placeholder="客户名称、账户编号、充值金额">
							</span> 
							<span class="item">
								<button id="lookup" type="button" class="btn btn-primary">查询</button>
							</span>
						</div>
					</div>
				</div>
				<div class="table-area">
					<table class="data-list" data-action="${ctx}/capital/customer-account-manage/deposit-receipt-list/query" data-autoload="true">
						<thead>
							<tr>
								<th>充值单号</th>
								<th>账户名称</th>
								<th>账户编号</th>
								<th>客户名称</th>
								<th>客户类型</th>
								<th>贷款合同编号</th>
								<th>信托项目名称</th>
								<th>充值金额</th>
								<th>备注</th>
								<th>状态</th>
								<th>机构流水</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<script type="script/template" id="tableFieldTmpl">
							    {% _.each(list, function(item, index){ %}
							        <tr>
							            <td><a href="">{%= item.sourceDocumentNo %}</a></td>
							            <td><a href="">{%= item.virtualAccountName %}</a></td>
							            <td><a href="">{%= item.virtualAccountNo %}</a></td>
							            <td>{%= item.firstPartyName %}</td>
							            <td>{%= item.customerTypeName %}</td>
							            <td><a href="">{%= item.contractNo %}</a></td>
							            <td>{%= item.contractName %}</td>
							            <td>{%= item.bookingAmount %}</td>
							            <td>{%= item.summary %}</td>
							            <td>{%= item.sourceDocumentStatusName %}</td>
							            <td><a href="">{%= item.institutions %}</a></td>
							            <td>
							                <a href="">作废</a>
							            </td>
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
	<script src="${ctx.resource}/js/bootstrap-modal-trigger.js"></script>
</body>
</html>


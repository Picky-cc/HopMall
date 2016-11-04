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
<title>客户账户流水 - 五维金融金融管理平台</title>
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
								<select class="form-control real-value" name="accountSide" >
									<option value="-1">收支类型</option>
									<c:forEach var="item" items="${accountSideList }">
										<option value="${item.ordinal}"><fmt:message key="${item.incomeType}"/></option>
									</c:forEach>
								</select>
							</span> 
							<span class="item"> 
								<select class="form-control real-value" name="transactionType" >
									<option value="-1">交易类型</option>
									<c:forEach var="item" items="${virtualAccountTransactionTypeList }">
										<option value="${item.ordinal}"><fmt:message key="${item.key}"/></option>
									</c:forEach>
								</select>
							</span>
							<span class="item">
								<input type="text" class="form-control large real-value " name="key" placeholder="交易号、账户编号、账户名称">
							</span> 
							<span class="item">
								<button id="lookup" type="button" class="btn btn-primary">查询</button>
							</span>
						</div>
					</div>
				</div>
				<div class="table-area">
					<table class="data-list" data-action="${ctx}/capital/customer-account-manage/account-flow-list/query" data-autoload="true">
						<thead>
							<tr>
								<th>账户流水号</th>
								<th>交易号</th>
								<th>收支类型</th>
								<th>账户编号</th>
								<th>账户名称</th>
								<th>发生金额</th>
								<th>瞬时余额</th>
								<th>交易类型</th>
								<th>发生时间</th>
							</tr>
						</thead>
						<tbody>
							<script type="script/template" id="tableFieldTmpl">
							    {% _.each(list, function(item, index){ %}
							        <tr>
							            <td><a href="">{%= item.virtualAccountFlowNo %}</a></td>
							            <td><a href="">{%= item.businessDocumentNo %}</a></td>
							            <td>{%= item.accountSideName %}</td>
							            <td>{%= item.virtualAccountNo %}</td>
							            <td>{%= item.virtualAccountAlias %}</td>
							            <td>{%= item.transactionAmount %}</td>
							            <td>{%= item.balance %}</td>
							            <td>{%= item.transactionTypeName %}</td>
							            <td>{%= new Date(item.createTime).format('yyyy-MM-dd HH:mm:ss') %}</td>
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


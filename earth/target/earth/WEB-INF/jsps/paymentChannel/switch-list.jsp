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
<title>通道切换列表 - 五维金融金融管理平台</title>

</head>
<body>

	<%@ include file="/WEB-INF/include/header.jsp"%>

	<div class="web-g-main">

		<%@ include file="/WEB-INF/include/aside.jsp"%>
		<div class="content">
			<div class="scroller">
				<div class="lookup-params">
					<div class="inner">
						<span class="item">
							<select name="debitStrategyMode" class="form-control real-value">
								<option value="">选择收款策略模式</option>
								<c:forEach var="item" items="${debitStrategyMode}">
									<option value="${item.ordinal }">
										<fmt:message key="${item.key}"/>
									</option>
								</c:forEach>
							</select>
						</span> 
						<span class="item">
							<select name="creditStrategyMode" class="form-control real-value">
								<option value="">选择付款策略模式</option>
								<c:forEach var="item" items="${creditStrategyMode}">
									<option value="${item.ordinal }">
										<fmt:message key="${item.key}"/>
									</option>
								</c:forEach>
							</select>
						</span>
						<span class="item">
							<input type="text" name="keyWord" class="form-control real-value" placeholder="商户号，信托计划，通道等关键词..." style="width: 205px;">
						</span>
						<span class="item">
							<button id="lookup" class="btn btn-primary">
								查询
							</button>
						</span>
					</div>
				</div>

				<div class="table-area">
					<table class="data-list" data-action="${ctx}/paymentchannel/switch/search" data-autoload="true">
						<thead>
							<tr>
								<th>信托计划</th>
								<th>信托合同编号</th>
								<th>清算绑定行号</th>
								<th>收款通道策略</th>
								<th>付款通道策略</th>
								<th style="width: 60px">操作</th>
							</tr>
						</thead>
						<tbody>
							<script type="script/template" id="tableFieldTmpl">
								{% _.each(list, function(item, index) { %}
									<tr data-financialcontractuuid = '{%= item.financialContractUuid%}'>
										<td>{%= item.contractName %}</td>
										<td>{%= item.contractNo %}</td>
										<td>{%= item.bankNameUionAccountNo %} </td>
										<td>{%= item.debitPaymentChannelMode %}</td>
										<td>{%= item.creditPaymentChannelMode %}</td>
										<td><a href="./detail/{%= item.financialContractUuid %}" class="">详情</a></td>
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


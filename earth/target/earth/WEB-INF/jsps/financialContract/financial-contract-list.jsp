<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.zufangbao.gluon.spec.global.GlobalCodeSpec"%>
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
<title>信托合同 - 五维金融金融管理平台</title>
</head>
<body>

	<%@ include file="/WEB-INF/include/header.jsp"%>

	<div class="web-g-main">
		<%@ include file="/WEB-INF/include/aside.jsp"%>
		<div class="content">
			<div class="scroller">
				<div class="lookup-params">
					<div class="inner clearfix">
						<span class="item"> 
							<input type="text" class="form-control real-value" name="financialContractNo" value="" placeholder="输入信托合同编号">
						</span> 
						<span class="item"> 
							<input type="text" class="form-control real-value" name="financialContractName" value="" placeholder="输入信托合同名称">
						</span> 
						<span class="item"> 
							<select name="appId" class="form-control real-value">
								<option value="-1">信托商户名称</option>
								<c:forEach var="item" items="${appList}">
									<option value="${item.id }">${item.name}</option>
								</c:forEach>
							</select>
						</span> 
						<span class="item"> 
							<select name="financialContractType" class="form-control real-value">
								<option value="-1">信托合同类型</option>
								<c:forEach var="item" items="${financialContractTypeList}">
									<option value="${item.ordinal }">
										<fmt:message key="${item.key}" />
									</option>
								</c:forEach>
							</select>
						</span> 
						<span class="item"> 
							<input type="text" class="form-control real-value" name="financialAccountNo" value="" placeholder="输入信托专户账号">
						</span> 
						<span class="item">
							<button id="lookup" class="btn btn-primary">
								查询
							</button>
						</span>
						<sec:authorize ifNotGranted="ROLE_TRUST_OBSERVER">
							<span class="item">
					            <a href="./financialContract/new-financialContract" class="pull-right btn btn-success">
					                <i class="glyphicon glyphicon-plus"></i>新增合同
					            </a>
							</span>
						</sec:authorize>
					</div>
				</div>
				<div class="table-area">
					<table class="data-list" data-action="./financialContract/query" data-autoload="true">
						<thead>
							<tr>
								<th>信托产品代码</th>
								<th>信托合同名称</th>
								<th>信托商户名称</th>
								<th>信托合同类型</th>
								<th>信托合同起始日</th>
								<th>信托合同截止日</th>
								<th>信托专户账号</th>
								<th>放款通道名称</th>
								<th>回款通道名称</th>
								<th width="60">操作</th>
							</tr>
						</thead>
						<tbody>
							<script type="script/template" id="tableFieldTmpl">
								{% _.each(list, function(item, index){ %}
									<tr>
										<td>
											<a href="${ctx}/financialContract/{%= item.id %}/detail">{%= item.contractNo %}</a>
										</td>
										<td>{%= item.contractName %}</td>
										<td>{%= item.app.name %}</td>
										<td>{%= item.financialContractTypeMsg %}</td>
										<td>{%= new Date(item.advaStartDate).format('yyyy/MM/dd') %}</td>
										<td>{%= item.thruDate ? new Date(item.thruDate).format('yyyy/MM/dd') : '' %}</td>
										<td>{%= item.capitalAccount.accountNo %}</td>
										<td></td>
										<td>
											{%= item.paymentChannel ? item.paymentChannel.channelName : '' %}
										</td>
										<td>
											<a href="${ctx}/financialContract/{%= item.id %}/detail">详情</a>
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

</body>
</html>

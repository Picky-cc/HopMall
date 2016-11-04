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
<title>放款计划订单列表 - 五维金融金融管理平台</title>
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
							<select name="financialContractIds" class="form-control real-value selectpicker" multiple data-actions-box="true" data-title="信托合作项目">
								<c:forEach var="item" items="${financialContracts}">
									<option value="${item.id}" selected>${item.contractName}(${item.contractNo})</option>
								</c:forEach>
							</select>
						</span>
						<span class="item">
							<input type="text" class="form-control real-value" name="orderNo" value="" placeholder="订单编号">
						</span>
						<span class="item">
							<input type="text" class="form-control real-value" name="loanContractNo" value="" placeholder="贷款合同编号">
						</span>
						<span class="item beginend-datepicker">
							<jsp:include page="/WEB-INF/include/plugins/datetimepicker.jsp">
								<jsp:param value="group" name="type" />
								<jsp:param value="true" name="pickTime"/>
								<jsp:param value="receiveStartDate" name="paramName1" />
								<jsp:param value="receiveEndDate" name="paramName2" />
								<jsp:param value="受理日期起始" name="placeHolder1" />
								<jsp:param value="受理日期终止" name="placeHolder2" />
							</jsp:include>
						</span>
						<span class="item">
							<select name="orderStatus" class="form-control real-value selectpicker" multiple data-actions-box="true" data-title="订单状态">
								<c:forEach var="item" items="${orderStatus}">
									<option value="${item.ordinal()}">
										<fmt:message key="${item.key}"/>
									</option>
								</c:forEach>
							</select>
						</span>
						<span class="item">
							<button id="lookup" class="btn btn-primary">
								查询
							</button>
						</span>
					</div>
				</div>	
				<div class="table-area">
					<table class="data-list" data-action="${ctx}/remittance/application/query" data-autoload="true">
						<thead>
							<tr>
								<th>信托项目名称</th>
								<th>贷款合同编号</th>
								<th>订单编号</th>
								<th>计划放款金额</th>
								<th>实际放款金额</th>
								<th>放款策略类型</th>
								<th>
									<a data-paramname="isAsc" class="sort none">
										受理时间<i class="icon"></i>
									</a>
								</th>
								<th>订单状态</th>
							</tr>
						</thead>
						<tbody>
							<script type="script/template" id="tableFieldTmpl">
								{% _.each(list,function(item,index){ %}
									<tr>
										<td>{%= item.financialProjectName%}</td>
										<td><a href="${ctx}/contracts/detail?uid={%= item.contractUniqueId%}">{%= item.loanContractNo%}</a></td>
										<td><a href="${ctx}/remittance/application/details/{%= item.orderNo%}">{%= item.orderNo%}</a></td>
										<td>{%= (+item.planLoanAmount).formatMoney(2,'')%}</td>
										<td>{%= (+item.actualLoanAmount).formatMoney(2,'')%}</td>
										<td>{%= item.remittanceStrategy%}</td>
										<td>{%= new Date(item.receiveTime).format('yyyy-MM-dd HH:mm:ss')%}</td>
										<td>
											{% if(item.orderStatus =='异常'){ %}
												<span class="color-warning">异常</span>
											{%	}else if(item.orderStatus == '失败'){ %}
												<span class="color-danger">失败</span>
											{%  }else{  %}
												{%= item.orderStatus%}
											{%  }  %}
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
</html>>
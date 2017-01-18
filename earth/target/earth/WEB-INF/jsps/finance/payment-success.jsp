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
<title>线上支付单列表 - 五维金融金融管理平台</title>
</head>
<body>

	<%@ include file="/WEB-INF/include/header.jsp"%>

	<div class="web-g-main">
		<%@ include file="/WEB-INF/include/aside.jsp"%>

		<div data-commoncontent='true' class="content">
			<div class="scroller">
				<div class="lookup-params">
					<div class="inner clearfix">
						<input type="hidden" name="filter" value="true">
						<span class="item">
							<select name="paymentWay" class="form-control real-value">
								<option value="-1">支付方式</option>
								<c:forEach var="item" items="${paymentWayList}">
									<option value="${item.ordinal }">
										<fmt:message key="${item.key}" /></option>
								</c:forEach>
							</select>
						</span>
						<span class="item">
							<select name="executingDeductStatus" class="form-control real-value">
								<option value="-1">支付单状态</option>
								<c:forEach var="item" items="${executingDeductStatusList}">
									<option value="${item.ordinal }">
										<fmt:message key="${item.key}" /></option>
								</c:forEach>
							</select>
						</span>  
						<span class="item"> 
							<input type="text" class="form-control real-value" name="paymentNo" value="" placeholder="请输入支付单号">
						</span> 
						<span class="item"> 
							<input type="text" class="form-control real-value" name="repaymentNo" value="" placeholder="请输入还款编号">
						</span> 
						<span class="item"> 
							<input type="text" class="form-control real-value" name="billingNo" value="" placeholder="请输入结算单号">
						</span> 
						<span class="item"> 
							<input type="text" class="form-control real-value" name="accountName" value="" placeholder="请输入账户姓名">
						</span> 
						<span class="item"> 
							<input type="text" class="form-control real-value" name="payAcNo" value="" placeholder="请输入银行账户号">
						</span>
						<span class="item beginend-datepicker"> 
						 	<jsp:include page="/WEB-INF/include/plugins/datetimepicker.jsp">
								<jsp:param value="group" name="type" />
								<jsp:param value="startDateString" name="paramName1" />
								<jsp:param value="endDateString" name="paramName2" />
								<jsp:param value="请输入发生起始日期" name="placeHolder1" />
								<jsp:param value="请输入发生终止日期" name="placeHolder2" />
							</jsp:include>
						</span>

						<span class="item"> 
							<input type="text" class="form-control real-value" name="bank" value="" placeholder="请输入银行名称">
						</span> 

						<span class="item">
							<button type="submit" id="lookup" class="btn btn-primary">
								查询
							</button>
						</span>
					</div>
				</div>
				<div class="table-area">
					<table class="data-list" 
						data-action="./query" 
						data-autoload="true">
						<thead>
							<tr>
								<th>序号</th>
								<th>支付单号</th>
								<th>结算单号</th>
								<th>银行名称</th>
								<th>账户姓名</th>
								<th>银行账户号</th>
								<th>代扣金额</th>
								<th>发生时间</th>
								<th>支付方式</th>
								<th>状态</th>
								<th>备注</th>
								<th width="60">操作</th>
							</tr>
						</thead>
						<tbody>
							<script type="script/template" id="tableFieldTmpl">
							    {% _.each(list, function(item, index) { %}
							        <tr class="record-item">
							            <td>{%= index + 1 %}</td>
							            <td>
											<a href="${ctx}/payment-manage/payment/{%= item.id %}/detail">{%= item['transferApplicationNo'] %}</a>
										</td>
							            <td>
											<a href="${ctx}/payment-manage/order/{%= item['order.id'] %}/detail">{%= item['order.orderNo'] %}</a>
										</td>
							            <td>{%= item['contractAccount.bank'] %}</td>
							            <td>{%= item['contractAccount.payerName'] %}</td>
							            <td>{%= item['contractAccount.payAcNo'] %}</td>
							            <td>{%= item.amount %}</td>
							            <td style="max-width: 100px;">{%= item.lastModifiedTime %}</td>
							            <td>{%= item.paymentWayMsg %}</td>
							            <td>{%= item.executingDeductStatusMsg %}</td>
							            <td>{%= item.comment %}</td>
							            <td>
							            	<a href="${ctx}/payment-manage/payment/{%= item.id %}/detail">详情</a>
							            </td>
							        </tr>
							    {% }); %}
							</script>
						</tbody>
					</table>
				</div>
			</div>

			<div class="operations">
				<div class="lookup-params pull-left" style="background-image: none; border: none; padding: 0;">
					<form id="myForm" action="${ctx}/payment-manage/payment/exportexcel" method="POST">
						<span class="item"> 对账单导出： </span>
						<span class="item">
							<jsp:include page="/WEB-INF/include/plugins/datetimepicker.jsp">
								<jsp:param value="startDateString" name="paramName1" />
								<jsp:param value="请输入发生时间" name="placeholder1" />
								<jsp:param value="top-right" name="pickerPosition" />
							</jsp:include>
						</span>
						<span class="item">
							<label>信托合同：</label>
							<select name="financialContractId" class="form-control">
								<c:forEach var="item" items="${financialContractList}">
									<option value="${item.id }"
										${item.id eq financialContract.id ? 'selected' : ''}>${item.contractNo}</option>
								</c:forEach>
							</select>
						</span>
						<span class="item">
							<button id="exportData" class="btn btn-primary">导出对账单</button>
						</span>
						<span class="item">
							<button id="exportDailyRepaymentList" type="button" class="btn btn-primary">导出当日还款清单</button>
						</span>
					</form>
				</div>
				
				<jsp:include page="/WEB-INF/include/plugins/assembly.jsp">
		            <jsp:param value="page-control" name="type"/>
		            <jsp:param value="true" name="advanced"/>
	            </jsp:include>
			</div>
		</div>
	</div>

	<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>

	<script type="text/javascript">
		$(function() {
			$("#exportDailyRepaymentList").click(function() {
				var data = $("#myForm").serialize();
				window.location.href = "${ctx}/payment-manage/payment/export/daily-return-list?" + data;
			});
		});
	</script>
</body>
</html>

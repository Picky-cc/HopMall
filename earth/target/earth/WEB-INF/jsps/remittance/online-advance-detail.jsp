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
<title>线上代付单详情- 五维金融金融管理平台</title>
</head>
<body>
	<%@ include file="/WEB-INF/include/header.jsp"%>
	<div class="web-g-main">
		<%@ include file="/WEB-INF/include/aside.jsp"%>
		<div class="content">
			<div class="scroller">
				<div class="position-map">
					<div class="pull-left">
			            当前位置:
			            <span class="item current">线上代付单详情</span>
					</div>
					<div class="pull-right">
						<button type="button" onclick="window.history.go(-1);" class="btn btn-default">&lt;&lt; 返回</button>
					</div>
				</div>

				<div class="col-layout-detail">
					<div class="top">
						<div class="block">
							<h5 class="hd">贷款信息</h5>
							<div class="bd">
								<div class="col">
									<p>放款编号：
										<a href="${ctx}/remittance/plan/details/${remittancePlanExecLog.remittancePlanUuid}">${remittancePlanExecLog.remittancePlanUuid}
										</a>
									</p>
									<p>代付单号：${remittancePlanExecLog.execReqNo}</p>
									<p>发生金额：
										<fmt:formatNumber type="number" pattern="#,##0.00#" value="${remittancePlanExecLog.plannedAmount}"/>
									</p>
								</div>
							</div>
						</div>
						<div class="block">
							<h5 class="hd">付款方账户信息</h5>
							<div class="bd">
								<div class="col">
									<p>付款方账户名：${remittancePlanExecLog.pgAccountInfo.accountName}</p>
									<p>付款方账户号：${remittancePlanExecLog.pgAccountInfo.accountNo}</p>
									<p>账户开户行：${remittancePlanExecLog.pgAccountInfo.bankName}</p>
									<p>开户行所在地：${remittancePlanExecLog.pgAccountInfo.province}&nbsp;${remittancePlanExecLog.pgAccountInfo.city}</p>
									<p>银行编号：${remittancePlanExecLog.pgAccountInfo.bankCode}</p>
									<p>证件号：${remittancePlanExecLog.pgAccountInfo.idNumber}</p>
								</div>
							</div>
						</div>
						<div class="block">
							<h5 class="hd">收款方账户信息</h5>
							<div class="bd">
								<div class="col">
									<p>收款方账户名：${remittancePlanExecLog.cpAccountInfo.accountName}</p>
									<p>收款方账户号：${remittancePlanExecLog.cpAccountInfo.accountNo}</p>
									<p>账户开户行：${remittancePlanExecLog.cpAccountInfo.bankName}</p>
									<p>开户行所在地：${remittancePlanExecLog.cpAccountInfo.province}&nbsp;${remittancePlanExecLog.cpAccountInfo.city}</p>
									<p>银行编号：${remittancePlanExecLog.cpAccountInfo.bankCode}</p>
									<p>证件号：${remittancePlanExecLog.cpAccountInfo.idNumber}</p>
								</div>
							</div>
						</div>
						<div class="block">
							<h5 class="hd">执行信息</h5>
							<div class="bd">
								<div class="col">
									<p>创建时间：
										<fmt:formatDate value="${remittancePlanExecLog.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
									</p>
									<p>状态变更时间：
										<fmt:formatDate value="${remittancePlanExecLog.lastModifiedTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
									</p>
									<p>执行时差：${remittancePlanExecLog.executedTime}</p>
									<p>代付状态：<fmt:message key="${remittancePlanExecLog.executionStatus.key}"/></p>
									<p>冲账状态：<fmt:message key="${remittancePlanExecLog.reverseStatus.key}"/></p>
								</div>
							</div>
						</div>
						<div class="block">
							<h5 class="hd">通道信息</h5>
							<div class="bd">
								<div class="col">
									<p>执行通道：${remittancePlanExecLog.paymentChannelName}</p>
									<p>通道编号：${remittancePlanExecLog.paymentChannelUuid}</p>
									<p>通道流水号：${remittancePlanExecLog.transactionSerialNo}</p>
									<p>备注：${remittancePlanExecLog.executionRemark}</p>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="table-layout-detail">
					<div class="block">
						<h5 class="hd">代付撤销单</h5>
						<div class="bd">
							<table class="data-list">
								<thead>
									<tr>
										<th>通道流水号</th>
										<th>通道名称</th>
										<th>发生时间</th>
										<th>退回账户</th>
										<th>交易类型</th>
										<th>金额</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="remittanceRefundBill" items="${remittanceRefundBills}">
										<tr>
											<td>${remittanceRefundBill.channelCashFlowNo}</td>
											<td><fmt:message key="${remittanceRefundBill.paymentGateway.key}"></fmt:message></td>
											<td><fmt:formatDate value="${remittanceRefundBill.createTime}" pattern="yyyy-MM-dd"/></td>
											<td>${remittanceRefundBill.hostAccountNo}</td>
											<td>冲账</td>
											<td>
												<fmt:formatNumber type="number" pattern="#,##0.00#" value="${remittanceRefundBill.amount}"/>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				
				<div class="table-layout-detail">
					<div class="block">
						<jsp:include page="/WEB-INF/include/system-operate-log.jsp">
				            <jsp:param value="${remittancePlanExecLog.execReqNo}" name="objectUuid"/>
			            </jsp:include>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>
</body>
</html>>
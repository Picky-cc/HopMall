<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>

<fmt:setBundle basename="ApplicationMessage" />
<fmt:setLocale value="zh-CN" />

<%@ include file="/WEB-INF/include/meta.jsp"%>
<%@ include file="/WEB-INF/include/css-newlayout.jsp"%>
<title>贷款合同详情 - 五维金融金融管理平台</title>

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
			            <span class="item current">扣款单详情</span>
					</div>
				</div>
				
				<div class="col-layout-detail">
					<div class="top">
						<div class="block">
							<h5 class="hd">贷款信息</h5>
							<div class="bd">
								<div class="col">
									<p>合同编号 ：<a class="contractNo" href="${ctx}/contracts/detail?id=${showModel.loanInformation.loanContractId}">${showModel.loanInformation.loanContractNo}</a></p>
									<p>贷款客户编号 ：${showModel.loanInformation.loanCustoemrNo}</p>
									<p>资产编号 ：${showModel.loanInformation.assetSetNo}</p>
									<p>还款编号 ：${showModel.loanInformation.repaymentPlanNos}</p>
								</div>
							</div>
						</div>
						<div class="block">
							<h5 class="hd">扣款信息</h5>
							<div class="bd">
								<div class="col">
									<p>扣款单号 ：${showModel.deductInformation.deductApplicationCode}</p>
									<p>扣款创建日期 ：<fmt:formatDate value="${showModel.deductInformation.deductCreateDate}" pattern="yyyy-MM-dd" /></p>
									<p>扣款状态 ：${showModel.deductInformation.deductStatus}</p>
									<p>还款类型 ：${showModel.deductInformation.repaymentType}</p>
								</div>
								<div class="col">
									<p>扣款金额 ：${showModel.deductInformation.repaymentType}</p>
									<p>实际扣款金额 ：<fmt:formatNumber type='number' pattern='#,#00.00#' value='${showModel.deductInformation.actualDeductAmount}' /></p>
									<p>扣款受理时间 ：<fmt:formatDate value="${showModel.deductInformation.deductReceiveTime}" pattern="yyyy-MM-dd hh:mm" /></p>
									<p>扣款发生时间 ：<fmt:formatDate value="${showModel.deductInformation.deductHappenTime}" pattern="yyyy-MM-dd hh:mm" /></p>
									<p>备注 ：${showModel.deductInformation.remark}</p>
								</div>
							</div>
						</div>
						<div class="block">
							<h5 class="hd">账户信息</h5>
							<div class="bd">
								<div class="col">
									<p>客户名称 ：<span class="customer">${showModel.accountInformation.customerName}</span></p>
									<p>账户开户行 ：${showModel.accountInformation.bank}</p>
									<p>开户行所在地 ：${showModel.accountInformation.addressOfBank}</p>
									<p>绑定账号：${showModel.accountInformation.repaymentAccountNo}</p>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="table-layout-detail">
					<div class="block">
						<h5 class="hd">线上支付单</h5>
						<div class="bd">
							<table>
								<thead>
									<tr>
										<th>支付编号</th>
										<th>扣款单号</th>
										<th>银行名称</th>
										<th>银行账户号</th>
										<th>代扣金额</th>
										<th>发生时间</th>
										<th>状态</th>
										<th>备注</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="item" items="${showModel.paymentOrderInformations}">
										<tr>
											<td><a href="#">${item.paymentOrderNo}</a></td>
											<td>${item.deductOrderNo}</td>
											<td>${item.bankName}</td>
											<td>${item.bankAccount}</td>
											<td><fmt:formatNumber type='number' pattern='#,#00.00#' value='${item.deductAmount}' /></td>
											<td><fmt:formatDate value="${item.occurTime}" pattern="yyyy-MM-dd hh:mm" /></td>
											<td>
												<span class="color-danger">${item.repaymentPlanStatus}</span>
											</td>
											<td>${item.remark}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>

				<div class="table-layout-detail">
					<div class="block">
						<h5 class="hd">还款计划明细</h5>
						<div class="bd">
							<table>
								<thead>
									<tr>
										<th>还款编号</th>
										<th>还款日期</th>
										<th>当前期数</th>
										<th>计划还款本金</th>
										<th>计划还款利息</th>
										<th>贷款服务费</th>
										<th>技术维护费</th>
										<th>其他金额</th>
										<th>逾期费用合计</th>
										<th>应还款金额</th>
										<th>实际还款金额</th>
										<th>还款状态</th>
										<th>备注</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="item" items="${showModel.repaymentPlanDetailShowModels}">
										<tr>
											<td><a href="#">${item.repaymentPlanCode}</a></td>
											<td><fmt:formatDate value="${item.repaymentDate}"  pattern="yyyy-MM-dd"/></td>
											<td>${item.currentPeriods}</td>
											<td><fmt:formatNumber value="${item.principal}" type='number' pattern='#,#00.00#'/></td>
											<td><fmt:formatNumber value="${item.interest}" type='number' pattern='#,#00.00#'/></td>
											<td><fmt:formatNumber value="${item.loanFee}" type='number' pattern='#,#00.00#'/></td>
											<td><fmt:formatNumber value="${item.techFee}" type='number' pattern='#,#00.00#'/></td>
											<td><fmt:formatNumber value="${item.otherFee}" type='number' pattern='#,#00.00#'/></td>
											<td><fmt:formatNumber value="${item.totalOverDueFee}" type='number' pattern='#,#00.00#'/></td>
											<td><fmt:formatNumber value="${item.planRepaymentAmount}" type='number' pattern='#,#00.00#'/></td>
											<td><fmt:formatNumber value="${item.actualAmount}" type='number' pattern='#,#00.00#'/></td>
											<td>
												<span>${item.repaymentStatus}</span>
											</td>
											<td>
												<span>${item.remark}</span>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>

	<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>
	
</body>
</html>
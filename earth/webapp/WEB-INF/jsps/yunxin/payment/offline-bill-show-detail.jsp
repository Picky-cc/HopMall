
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

<fmt:setBundle basename="ApplicationMessage" />
<fmt:setLocale value="zh-CN" />

<%@ include file="/WEB-INF/include/meta.jsp"%>
<%@ include file="/WEB-INF/include/css-newlayout.jsp"%>
<title>线下支付单详情 - 五维金融金融管理平台</title>

</head>
<body>

	<%@ include file="/WEB-INF/include/header.jsp"%>
	<div class="web-g-main">
		<%@ include file="/WEB-INF/include/aside.jsp"%>

		<div class="content">
			<input type="hidden" name="overdueStatus"
				value="${assetSetModel.assetSet.overdueStatus.ordinal}">
			<div class="scroller">
				<div class="position-map">
					<div class="pull-left">
						当前位置: <span class="item current">线下支付单详情</span>
					</div>
					<div class="pull-right">
						<sec:authorize ifNotGranted="ROLE_TRUST_OBSERVER">
							<span class="item">
								<button type="button" id="btnMatch" class="btn btn-default"
									onclick="smartMatch('${offlineBill.offlineBillUuid}')"
									${offlineBill.offlineBillConnectionState.ordinal == 2 ? 'DISABLED':''}>关联</button>
							</span>
							<!-- <span class="item">
							<button type="submit" id="lookup" class="btn btn-primary">解除关联</button>
							</span> -->
						</sec:authorize>
						
						<span class="item"> <a
							href="javascript:window.history.go(-1);"
							class="back btn btn-default">&lt;&lt; 返回</a>
						</span>
					</div>
				</div>

				<div class="col-layout-detail">
					<div class="top">
						<div class="block">
							<h5 class="hd">线下支付单信息</h5>
							<div class="bd">
								<div class="col">
									<p>支付单号 ：${offlineBill.offlineBillNo}</p>
									<p>支付接口编号 ：</p>
									<p>付款方名称 ：${offlineBill.payerAccountName}</p>
									<p>发生时间 ：${offlineBill.statusModifiedTime}</p>
								</div>
								<div class="col">
									<p>付款方开户行 ：${offlineBill.bankShowName}</p>
									<p>发生金额 ：
										<fmt:formatNumber type='number' pattern='#,##0.00#' value='${offlineBill.amount}' />
									</p>
									<p>
										支付状态 ：
										<fmt:message key="${offlineBill.offlineBillStatus.key}" />
									</p>
									<p>绑定号 ：</p>
								</div>
								<div class="col">
									<p>支付机构流水号 ：${offlineBill.serialNo }</p>
									<p>支付机构 ：</p>
									<p>支付方式 ：线下打款</p>
									<p>备注 ：${offlineBill.comment}</p>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="table-layout-detail">
					<div class="block">
						<h5 class="hd">线下支付单关联记录</h5>
						<div class="bd">
							<table>
								<thead>
									<tr>
										<th>来源单号</th>
										<th>类型	</th>
										<th>还款编号</th>
										<th>计划还款日期	</th>
										<th>结算日期	</th>
										<th>客户姓名</th>
										<th>结算金额</th>
										<th>已付金额</th>
										<th>关联金额</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="orderMatchShowModel" items="${orderMatchShowModelList}">
										<tr>
											<c:if test="${orderMatchShowModel.order.orderType.ordinal eq 0}">
												<td>
													<a href="${ctx}/payment-manage/order/${orderMatchShowModel.order.id}/detail">${orderMatchShowModel.order.orderNo }</a>
											    </td>
											</c:if>
											<c:if test="${orderMatchShowModel.order.orderType.ordinal eq 1}">
												<td>
													<a href="${ctx}/guarantee/order/${orderMatchShowModel.order.id}/guarantee-detail">${orderMatchShowModel.order.orderNo }</a>
												</td>
											</c:if>
											<td><fmt:message key="${orderMatchShowModel.order.orderType.key }"></fmt:message></td>
											<td>
												<a href="${ctx}/assets/${orderMatchShowModel.order.assetSet.id }/detail">${orderMatchShowModel.order.assetSet.singleLoanContractNo}</a>
											</td>
											<td>${orderMatchShowModel.order.assetSet.assetRecycleDate }</td>
											<td>${orderMatchShowModel.order.dueDate }</td>
											<td>${orderMatchShowModel.order.customer.name }</td>
											<td>
												<fmt:formatNumber type='number' value='${orderMatchShowModel.order.totalRent }' pattern='#,##0.00#'/>
											</td>
											<td>
												<fmt:formatNumber type='number' value='${orderMatchShowModel.paidAmount }' pattern='#,##0.00#'/>
											</td>
											<td>
												<fmt:formatNumber type='number' value='${orderMatchShowModel.issuedAmount }' pattern='#,##0.00#'/>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				
				<div class="table-layout-detail">
					<jsp:include page="/WEB-INF/include/system-operate-log.jsp">
						<jsp:param value="${offlineBill.offlineBillUuid}"
							name="objectUuid" />
					</jsp:include>
				</div>

			</div>
		</div>
	</div>

	<div class="modal fade in bs-example-modal-sm" id="match-dialog">
		<div class="modal-dialog" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close close-dialog" aria-label="关闭">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="dialoglabel">关联线下支付单</h4>
				</div>
				<div class="modal-body">
					<p id="showMsg"></p>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>
	<script>
		function smartMatch(val) {
			$.post('${ctx}/offline-payment-manage/match', {
				'offlineBillUuid' : val
			}, function(data) {
				$('#match-dialog').find('#showMsg').html(data).end().modal();
				$('#match-dialog .close-dialog').click(function() {
					$('#match-dialog').modal('hide');
					window.location.reload();
				});
			})
		}
	</script>
</body>
</html>


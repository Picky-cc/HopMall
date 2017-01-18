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
<title>担保补足详情 - 五维金融金融管理平台</title>

</head>
<body>

	<%@ include file="/WEB-INF/include/header.jsp"%>
	<div class="web-g-main">
		<%@ include file="/WEB-INF/include/aside.jsp"%>
		
		<div class="content">
			<input type="hidden" name="orderId" value="">
			<div class="scroller">
				<div class="position-map">
					<div class="pull-left">
			            当前位置:
			            <span class="item current">担保补足详情</span>
					</div>
					<div class="pull-right">
						<sec:authorize ifNotGranted="ROLE_TRUST_OBSERVER">
							<c:choose>
								<c:when test="${order.assetSet.guaranteeStatusCanBeLapsed()}">
									<button type="button" id="cancel" class="btn btn-primary">作废</button>
								</c:when>
								<c:when test="${order.assetSet.guaranteeStatusLapsed}">
									<button type="button" id="activite" class="btn btn-primary">激活</button>
								</c:when>
							</c:choose>
						</sec:authorize>
						<button type="button" onclick="window.history.go(-1);" class="btn btn-default">&lt;&lt; 返回</button>
					</div>
				</div>
				
				<div class="col-layout-detail">
					<div class="top">
						<div class="block">
							<h5 class="hd">担保信息</h5>
							<div class="bd">
								<div class="col">
									<p>担保补足号 ：${order.orderNo}</p>
									<p>担保金额 ：
										<fmt:formatNumber type='number' value='${order.totalRent}' pattern='#,##0.00#'/>
									</p>
									<p>担保截止日 ：${order.dueDate }</p>
									<p>发生时间 ：${order.modifyTime }</p>
								</div>
							</div>
						</div>
						<div class="block">
							<h5 class="hd">还款信息</h5>
							<div class="bd">
								<div class="col">
									<p>还款编号 ：${order.assetSet.singleLoanContractNo }</p>
									<p>计划还款金额 ：
										<fmt:formatNumber type='number' value='${order.assetSet.assetInitialValue }' pattern='#,##0.00#'/>
									</p>
									<p>计划还款日期 ：${order.assetSet.assetRecycleDate }</p>
									<p>差异天数 ：${order.numbersOfGuranteeDueDays }</p>
								</div>
							</div>
						</div>
						<div class="block">
							<h5 class="hd">其他</h5>
							<div class="bd">
								<div class="col">
									<p>商户编号 ：${order.assetSet.contract.app.appId }</p>
									<p>状态 ：<fmt:message key="${order.assetSet.guaranteeStatus.key}"></fmt:message></p>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="table-layout-detail">
					
					<div class="block">
                        <h5 class="hd">线下支付记录</h5>
                        <div class="bd">
                            <table>
                                <thead>
                                    <tr>
                                        <th>序号</th>
                                        <th>支付单号</th>
                                        <th>银行名称</th>
                                        <th>账户姓名</th>
                                        <th>支付机构流水号</th>
                                        <th>支付金额</th>
                                        <th>关联状态</th>
                                        <th>入账时间</th>
                                        <th>发生时间</th>
                                        <th>状态</th>
                                        <th>备注</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="item" items="${payDetails}" varStatus="status">
                                        <tr>
                                            <td>${status.index + 1}</td>
											<td><a href="${ctx}/offline-payment-manage/payment/${item.offlineBill.id}/detail">${item.offlineBill.offlineBillNo}</a></td>
											<td>${item.offlineBill.bankShowName}</td>
                                            <td>${item.offlineBill.payerAccountName}</td>
                                            <td>${item.offlineBill.serialNo}</td>
                                            <td>${item.journalVoucher.bookingAmount}</td>
                                            <td><fmt:message key="${item.offlineBill.offlineBillConnectionState.key}" />
                                            <td>${item.offlineBill.tradeTime}</td>
                                            <td>${item.journalVoucher.createdDate}</td>
                                            <td><fmt:message key="${item.offlineBill.offlineBillStatus.key}" /></td>
                                            <td>${item.offlineBill.comment}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
					
					<div class="block">
						<jsp:include page="/WEB-INF/include/system-operate-log.jsp">
				            <jsp:param value="${order.repaymentBillId}" name="objectUuid"/>
			            </jsp:include>
					</div>
				</div>

			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>

</body>

</html>
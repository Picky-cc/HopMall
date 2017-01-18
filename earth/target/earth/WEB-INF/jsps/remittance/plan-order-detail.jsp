<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

<fmt:setBundle basename="ApplicationMessage" />
<fmt:setLocale value="zh_CN" />

<%@ include file="/WEB-INF/include/meta.jsp"%>
<%@ include file="/WEB-INF/include/css-newlayout.jsp"%>
<title>放款计划订单详情 - 五维金融金融管理平台</title>

<script type="template/script" id="againNotifyResultTmpl">
  	<div class="modal-dialog">
	    <div class="modal-content">
		    <div class="modal-header">
		        <button type="button" class="close close-dialog" aria-label="关闭" data-dismiss="modal">
		        	<span aria-hidden="true">×</span>
		        </button>
		        <h4 class="modal-title" id="dialoglabel">操作成功</h4>
		    </div>
	      	<div class="modal-body" >
	      		<div >计划回调次数已增加，稍后系统会自动回调结果。</div>
	      	</div>
	      	<div class="modal-footer">
		        <button type="button" class="btn btn-success submit" >确定</button>
	      	</div>
	    </div>
  	</div>
</script>
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
			            <span class="item current">计划订单详情</span>
					</div>
					<div class="pull-right">
						<button type="button" onclick="window.history.go(-1);" class="btn btn-default">&lt;&lt; 返回</button>
					</div>
				</div>

				<div class="col-layout-detail">
					<div class="top">
						<div class="block">
							<h5 class="hd">基本信息</h5>
							<div class="bd">
								<div class="col">
									<p>订单编号：${remittanceApplication.remittanceApplicationUuid}</p>
									<p>来源单号：${remittanceApplication.requestNo}</p>
									<p>贷款合同编号：
										<a href="${ctx}/contracts/detail?uid=${remittanceApplication.contractUniqueId}">${remittanceApplication.contractNo}</a>
									</p>
									<p>信托产品代码：${remittanceApplication.financialProductCode}</p>
								</div>
							</div>
						</div>
						<div class="block">
							<h5 class="hd">放款信息</h5>
							<div class="bd">
								<div class="col">
									<p>计划放款金额：
										<fmt:formatNumber type='number' pattern='#,##0.00#' value='${remittanceApplication.plannedTotalAmount}' />
									</p>
									<p>放款策略类型：
										<fmt:message key="${remittanceApplication.remittanceStrategy.key}"></fmt:message>
									</p>
									<p>放款起始日期：${remittanceApplication.createTime}</p>
									<p>订单状态：
										<fmt:message key="${remittanceApplication.executionStatus.key}"></fmt:message>
									</p>
								</div>
							</div>
						</div>
						<div class="block">
							<h5 class="hd">审核信息</h5>
							<div class="bd">
								<div class="col">
									<p>审核人：${remittanceApplication.auditorName}</p>
									<p>审核日期：
										<fmt:formatDate value="${remittanceApplication.auditTime}"  pattern="yyyy-MM-dd"/>
									</p>
									<p>受理时间：
										<fmt:formatDate value="${remittanceApplication.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
									</p>
									<p>
									 	计划回调次数：${remittanceApplication.planNotifyNumber}
									 	<c:if test="${remittanceApplication.actualNotifyNumber >= remittanceApplication.planNotifyNumber && remittanceApplication.planNotifyNumber != 0}">
									 		<button type="button" 
									 			class="btn btn-primary againNotifyResult" 
									 			data-remittanceApplicationUuid="${remittanceApplication.remittanceApplicationUuid}" 
									 			style="margin-left:10px">
									 			重新回调结果
									 		</button>
									 	</c:if>
									</p>
									<p>
										实际回调次数: ${remittanceApplication.actualNotifyNumber}
									</p>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="table-layout-detail" style="display: none">
					<div class="block">
						<h5 class="hd">订单账户信息</h5>
						<div class="bd">
							<table class="data-list" data-action="" data-autoload="true">
								<thead>
									<tr>
										<th>角色类型</th>
										<th>账户名称</th>
										<th>账户号</th>
										<th>开户行</th>
										<th>开户行所在省</th>
										<th>开户行所在市</th>
										<th>证件号</th>
									</tr>
								</thead>
								<tbody>
									<!-- <tr>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr> -->
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="table-layout-detail">
					<div class="block orders">
						<h5 class="hd">订单明细</h5>
						<div class="bd">
							<table class="data-list" data-action="" data-autoload="true">
								<thead>
									<tr>
										<th>放款优先级</th>
										<th>来源编号</th>
										<th>放款编号</th>
										<th>计划执行日期</th>
										<th>执行金额</th>
										<th>付款方账户名</th>
										<th>收款方账户名</th>
										<th>交易类型</th>
										<th>执行状态</th>
										<th>备注</th>
									</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${not empty remittancePlans}">
											<c:forEach var="item" items="${remittancePlans}">
												<tr>
													<td>${item.priorityLevel}</td>
													<td>${item.businessRecordNo}</td>
													<td>
														<a href="${ctx}/remittance/plan/details/${item.remittancePlanUuid}">
															${item.remittancePlanUuid}
														</a>
													</td>
													<td><fmt:formatDate value="${item.plannedPaymentDate}"  pattern="yyyy-MM-dd"/></td>
													<td>
														<fmt:formatNumber type='number' pattern='#,##0.00#' value='${item.plannedTotalAmount}' />
													</td>
													<td>
														<span 	class="showPopover"
																data-container="body"
																data-placement="top"
																data-html="true"
																data-trigger="focus" 
																data-toggle="popover">
																${item.pgAccountInfo.accountName}
														</span>
														<div class="hide account">
															<div>
																<span class='text-muted'>账户名:</span>${item.pgAccountInfo.accountName}
																<br/>
																<span class='text-muted'>账户号:</span>${item.pgAccountInfo.accountNo}
																<br/>
																<span class='text-muted'>开户行:</span>${item.pgAccountInfo.bankName}
																<br/>
																<span class='text-muted'>所在地:</span>${item.pgAccountInfo.province}&nbsp;${item.pgAccountInfo.city}
																<br/>
																<span class='text-muted'>证件号:</span>${item.pgAccountInfo.idNumber}
																<br/>
															</div>
														</div>
													</td>
													<td>
														<span 	class="showPopover"
																data-container="body"
																data-placement="top"
																data-html="true"
																data-trigger="focus" 
																data-toggle="popover">
															${item.cpAccountInfo.accountName}
														</span>
														<div class="hide account">
															<div>
																<span class='text-muted'>账户名:</span>${item.cpAccountInfo.accountName}
																<br/>
																<span class='text-muted'>账户号:</span>${item.cpAccountInfo.accountNo}
																<br/>
																<span class='text-muted'>开户行:</span>${item.cpAccountInfo.bankName}
																<br/>
																<span class='text-muted'>所在地:</span>${item.cpAccountInfo.province}&nbsp;${item.cpAccountInfo.city}
																<br/>
																<span class='text-muted'>证件号:</span>${item.cpAccountInfo.idNumber}
																<br/>
															</div>
														</div>
													</td>
													<td>${item.transactionType.ordinal == 0 ? '代付' : '代收'}</td>
													<c:choose>
														<c:when test="${item.executionStatus.ordinal() eq 4}">
															<td><span class="color-warning"><fmt:message key='${item.executionStatus.key}'/></span></td>
														</c:when>
														<c:when test="${item.executionStatus.ordinal() eq 3}">
															<td><span class="color-danger"><fmt:message key='${item.executionStatus.key}'/></span></td>
														</c:when>
														<c:otherwise>
															<td><span><fmt:message key='${item.executionStatus.key}'/></span></td>
														</c:otherwise>
													</c:choose>
													<td>${item.executionRemark}</td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr>
												<td colspan="8" align="center">
													没有更多数据
												</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="table-layout-detail">
					<div class="block">
						<jsp:include page="/WEB-INF/include/system-operate-log.jsp">
				            <jsp:param value="${remittanceApplication.remittanceApplicationUuid}" name="objectUuid"/>
			            </jsp:include>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>
</body>
</html>>
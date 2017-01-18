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
	<title>凭证详情- 五维金融金融管理平台</title>

	<style>
		@media (min-width: 768px) {
			.table-modal .modal-dialog, .form-modal .modal-dialog {
			    width: 860px;
			}
		}
	</style>

	<script id="cashFlowSelectView" type="script/template">
		<div class="modal-dialog">
		    <div class="modal-content">
		        <div class="modal-header">
		            <button type="button" class="close close-dialog" aria-label="关闭" data-dismiss="modal"><span aria-hidden="true">×</span></button>
		            <h4 class="modal-title" id="dialoglabel">导入资产包</h4>
		        </div>
		        <div class="modal-body form-wrapper">
		            <table class="data-list">
		                <thead>
		                    <tr>
			                    <th></th>
		                        <th>支付编号</th>
		                        <th>付款方开户行</th>
		                        <th>支付机构流水号</th>
		                        <th>支付接口编号</th>
		                        <th>支付机构</th>
		                        <th>支付金额</th>
		                        <th>发生时间</th>
		                        <th>支付方式</th>
		                        <th>状态</th>
		                    </tr>
		                </thead>
		                <tbody>
	                		{% if(_.isEmpty(data.cashFlows)){ %}
		                			<tr>
										<td colspan="10" align="center">
											没有更多数据
										</td>
									</tr>
	                		{% }else{ %}
		                			{% _.each(data.cashFlows, function(item) { %}
			                			<tr>
						                    <td style="width: 40px; text-align: center; padding: 10px 0;">
							                    <input type="checkbox" data-cashflowuuid ="{%= item.cashFlowUuid %}">
						                    </td>
					                        <td><a href="">{%= item.id %}</a></td>
					                        <td>{%= item.counterBankInfo %}</td>
					                        <td>{%= item.bankSequenceNo %}</td>
					                        <td>支付接口编号</td>
					                        <td>{%= item.counterAccountName %}</td>
					                        <td>{%= item.transactionAmount %}</td>
					                        <td>{%= item.transactionTime %}</td>
					                        <td>支付方式</td>
					                        <td>状态</td>
				                    	</tr>
		                			{% }) %}
	                		{% } %}
		                </tbody>
		            </table>
		        </div>
		        <div class="modal-footer">
		            <button type="button" class="btn btn-default cancel-dialog" data-dismiss="modal">取消</button>
		            <button type="button" class="btn btn-success submit">确定</button>
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
			            <span class="item current">商户凭证详情</span>
					</div>
					<div class="pull-right">
						<button type="button" class="btn btn-default hide">编辑</button>
						<c:if test="${detail.hasFails }">
							<button type="button" class="btn btn-default invalid">作废</button>
						</c:if>
						<button type="button" onclick="window.history.go(-1);" class="btn btn-default">&lt;&lt; 返回</button>
					</div>
				</div>

				<div class="col-layout-detail">
					<div class="top">
						<!-- <div class="block">
							<h5 class="hd">贷款信息</h5>
							<div class="bd">
								<div class="col">
									<p>合同编号：00001</p>
									<p>贷款客户编号：000001</p>
									<p>资产编号：3,000.00</p>
									<p>还款编号：000001</p>
								</div>
							</div>
						</div> -->
						<div class="block">
							<h5 class="hd">凭证信息</h5>
							<div class="bd">
								<div class="col">
									<p>凭证编号：${detail.voucherNo }</p>
									<p>凭证来源：${detail.voucherSource }</p>
									<p>凭证类型：${detail.voucherType }</p>
									<p>凭证金额：${detail.voucherAmount }</p>
									<p>凭证内容：</p>
								</div>
								<div class="col">
									<p>专户账号：${detail.receivableAccountNo }</p>
									<p>往来机构名称：${detail.paymentBank }</p>
									<p>机构账户号：${detail.paymentAccountNo }</p>
									<p>发生时间：${detail.time }</p>
								</div>
								<div class="col">
									<p>凭证状态：${detail.voucherStatus }</p>
									<p>接口请求编号：${detail.requestNo }</p>
									<p>备注：无</p>
								</div>
							</div>
						</div>
						<!-- <div class="block">
							<h5 class="hd">账户信息</h5>
							<div class="bd">
								<div class="col">
									<p>客户名称：张三</p>
									<p>账户开户行：中国邮政储蓄银行</p>
									<p>开户行所在地：浙江 杭州</p>
									<p>绑定账号：000001</p>
								</div>
							</div>
						</div> -->
					</div>
				</div>
				<div class="table-layout-detail">
					<div class="block">
						<h5 class="hd">
							凭证资金单据（银行流水）
							<c:if test="${empty detail.cashFlow}">
								<button type="button" class="btn btn-default pull-right cash-flow-select">流水选择</button>
							</c:if>
						</h5>
						<div class="bd">
							<table class="data-list">
								<thead>
									<tr>
										<th>支付编号</th>
										<th>付款方开户行</th>
										<th>支付机构流水号</th>
										<th>支付接口编号</th>
										<th>支付机构</th>
										<th>支付金额</th>
										<th>发生时间</th>
										<th>支付方式</th>
										<th>状态</th>
									</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${not empty detail.cashFlow}">
											<tr>
												<td><a href="">${detail.cashFlow.id}</a></td>
												<td>${detail.cashFlow.counterBankInfo}</td>
												<td>${detail.cashFlow.bankSequenceNo}</td>
												<td>无</td>
												<td>${detail.cashFlow.counterAccountName}</td>
												<td>${detail.cashFlow.transactionAmount}</td>
												<td>${detail.cashFlow.transactionTime}</td>
												<td>无</td>
												<td>无</td>
											</tr>
										</c:when>
										<c:otherwise>
											<tr>
												<td colspan="9" align="center">
													没有更多数据
												</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>
					</div>
					<div class="block voucher-business-list">
						<div class="lookup-params hide">
							<input class="real-value" type="hidden" name="detailId" value="${detailId}">
						</div>
						<h5 class="hd">凭证业务单据</h5>
						<div class="bd">
							<table class="data-list" 
								data-action="${ctx}/voucher/business/detail/query" 
								data-autoload="true">
								<thead>
									<tr>
										<th>还款编号</th>
										<th>贷款合同编号</th>
										<th>信托项目名称</th>
										<th>计划还款日期</th>
										<th>实际还款日期</th>
										<th>客户姓名</th>
										<th>应还款金额</th>
										<th>已还金额</th>
										<th>凭证关联金额</th>
										<th>还款状态</th>
										<th>核销状态</th>
										<th>校验状态</th>
										<th>明细备注</th>
										<th width="60">操作</th>
									</tr>
								</thead>
								<tbody>
									<script class="template" type="script/template">
										{% _.each(list, function(item) { %}
											<tr data-source-document-detailId={%= item.sourceDocumentDetailId%}>
												<td>{%= item.singleLoanContractNo %}</td>
												<td>{%= item.contractNo %}</td>
												<td>{%= item.financialContractName %}</td>
												<td>{%= new Date(item.assetRecycleDate).format('yyyy-MM-dd ') %}</td>
												<td>{%= new Date(item.actualRecycleDate).format('yyyy-MM-dd') %}</td>
												<td>{%= item.customerName %}</td>
												<td><span class="color-danger">{%= (+item.assetFairValue).formatMoney(2, '') %}</span></td>
												<td>{%= (+item.payedAmount).formatMoney(2, '') %}</td>
												<td><span class="color-danger">{%= (+item.detailAmount).formatMoney(2, '') %}</span></td>
												<td>{%= item.paymentStatus %}</td>
												<td>{%= item.detailStatus %}</td>
												<td>{%= item.verifyStatus %}</td>
												<td>{%= item.comment %}</td>
												<td>
													{% if(item.detailStatus == '未核销') { %}
														
													{% } %}
												</td>
											</tr>
										{% }) %}
									</script>
								</tbody>
							</table>
						</div>
						<div class="ft">
							<%-- 
							<c:if test="${detail.voucherStatus  == '未核销'}">
								<button type="button" class="btn btn-default onekey-writeoff">一键核销</button>
							</c:if>
							--%>
							<jsp:include page="/WEB-INF/include/plugins/assembly.jsp">
					            <jsp:param value="page-control" name="type"/>
					            <jsp:param value="true" name="advanced"/>
					        </jsp:include>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>
</body>
</html>
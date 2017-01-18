
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setBundle basename="ApplicationMessage" />
<fmt:setLocale value="zh-CN" />

<div class="modal-dialog demo2do-modal-dialog">

	<div class="modal-content">

		<div class="modal-header">
			<button type="button" class="close close-dialog" aria-label="关闭" data-dismiss="modal">
				<span aria-hidden="true">&times;</span>
			</button>
			<h4 class="modal-title" id="dialoglabel">结清单详情</h4>
		</div>
		<div class="modal-body" style="padding: 0px 30px 0px 15px;text-align: left;">
			<div class="row">
				<div class="text-align-right col-xs-2 col-md-2">
					<p>结清单号</p>
				</div>
				<div class="text-align-left col-xs-4 col-md-4">
					<p>
						<strong>${settlementOrder.settleOrderNo}</strong>
					</p>
				</div>
				<div class="text-algin-left col-xs-2 col-md-2">
					<p>还款期号</p>
				</div>
				<div class="text-algin-left col-xs-4 col-md-4">
					<p>
						<strong>${settlementOrder.assetSet.singleLoanContractNo}</strong>
					</p>
				</div>
			</div>
			<div class="row">
				<div class=" text-align-right col-xs-2 col-md-2">
					<p>合同编号</p>
				</div>
				<div class="text-align-left col-xs-4 col-md-4">
					<p>
						<strong>${settlementOrder.assetSet.contract.contractNo}</strong>
					</p>

				</div>
				<div class="text-algin-left col-xs-2 col-md-2">
					<p>应还日期</p>
				</div>
				<div class="text-algin-left col-xs-4 col-md-4">
					<p>
						<strong>${settlementOrder.assetSet.assetRecycleDate}</strong>
					</p>
				</div>
			</div>
			<div class="row">
				<div class="text-align-right col-xs-2 col-md-2">
					<p>商户编号</p>
				</div>
				<div class="text-align-left col-xs-4 col-md-4">
					<p>
						<strong>${settlementOrder.assetSet.contract.app.appId}</strong>
					</p>
				</div>
				<div class="text-algin-left col-xs-2 col-md-2">
					<p>发生时间</p>
				</div>
				<div class="text-algin-left col-xs-4 col-md-4">
					<p>
						<strong>${settlementOrder.lastModifyTime}</strong>
					</p>
				</div>
			</div>
			<div class="row">
				<div class="text-align-right col-xs-2 col-md-2">
					<p>商户名称</p>
				</div>
				<div class="text-align-left col-xs-4 col-md-4">
					<p>
						<strong>${settlementOrder.assetSet.contract.app.name}</strong>
					</p>
				</div>
				<div class="text-algin-left col-xs-2 col-md-2">
					<p>打款开户行</p>
				</div>
				<div class="text-algin-left col-xs-4 col-md-4">
					<p>
						<strong></strong>
					</p>
				</div>
			</div>
			<div class="row">
				<div class="text-align-right col-xs-2 col-md-2">
					<p>打款账号</p>
				</div>
				<div class="text-align-left col-xs-4 col-md-4">
					<p>
						<strong></strong>
					</p>
				</div>
				<div class="text-algin-left col-xs-2 col-md-2">
					<p>打款银行账户名称</p>
				</div>
				<div class="text-algin-left col-xs-4 col-md-4">
					<p>
						<strong></strong>
					</p>
				</div>
			</div>
			<div class="row">
				<div class="text-align-right col-xs-2 col-md-2">
					<p>本息金额</p>
				</div>
				<div class="text-align-left col-xs-4 col-md-4">
					<p>
						<strong>
							<fmt:formatNumber type='number' pattern='#,##0.00#' value='	${settlementOrder.assetSet.assetInitialValue}'/>
						</strong>
					</p>
				</div>
				<div class="text-algin-left col-xs-2 col-md-2">
					<p>结清金额</p>
				</div>
				<div class="text-algin-left col-xs-4 col-md-4">
					<p>
						<strong>
							<fmt:formatNumber type='number' pattern='#,##0.00#' value='	${settlementOrder.settlementAmount}'/>
						</strong>
					</p>
				</div>
			</div>
			<div class="row">
				 <div class="text-align-right col-xs-2 col-md-2">
					<p>补差状态</p>
				</div>
				<div class="text-algin-left col-xs-4 col-md-4">
					<p>
						<strong><fmt:message key="${settlementOrder.assetSet.guaranteeStatus.key}"/></strong>
					</p>
				</div>
				<div class="text-algin-left col-xs-2 col-md-2">
					<p>结清状态</p>
				</div>
				<div class="text-algin-left col-xs-4 col-md-4">
					<p>
						<strong><fmt:message key="${settlementOrder.assetSet.settlementStatus.key}"/></strong>
					</p>
				</div>
			</div>
		</div>

	</div>

</div>




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
<title>担保清算 - 五维金融金融管理平台</title>
</head>
<body>

	<%@ include file="/WEB-INF/include/header.jsp"%>

	<div class="web-g-main">
		<%@ include file="/WEB-INF/include/aside.jsp"%>
		<div class="content">
			<div class="lookup-params">
				<div class="inner">
					<input type="hidden" name="filter" value="true">
					<span class="item" style="width: 127px;"> 
						<select name="financialContractIds" class="form-control real-value selectpicker" multiple data-title="信托合同项目" data-actions-box="true">
							<c:forEach var="item" items="${financialContracts}">
								<option value="${item.id }">${item.contractName}(${item.contractNo })</option>
							</c:forEach>
						</select>
					</span>
					<span class="item">
						<select name="settlementStatus" id="settlementStatus" class="form-control real-value">
							<option value="-1">清算单状态</option>
							<c:forEach var="item" items="${settlementStatusList}">
								<option value="${item.ordinal}">
									<fmt:message key="${item.key}" />
								</option>
							</c:forEach>
						</select>
					</span>
					<span class="item">
						<input type="text" class="form-control real-value" id="settlementOrderNo" name="settlementOrderNo" value="" placeholder="请输入清算单号">
					</span>
					<span class="item">
						<input type="text" class="form-control real-value" name="repaymentNo" value="" id="repaymentNo" placeholder="请输入还款期号" >
					</span>
					<span class="item">
						<input type="text" class="form-control real-value" name="appNo" id="appNo" value="" placeholder="请输入商户编号" >
					</span>
					<span class="item">
		              <button type="button" id="lookup" class="btn btn-primary">查询</button>
		            </span>
				</div>
			</div>
			<div class="table-area">
				<table class="data-list" data-action="./settlement-order/query" data-autoload="true">
					<thead>
						<tr>
							<th>序号</th>
							<th>清算单号</th>
							<th>还款编号</th>
							<th>计划还款日期</th>
							<th>清算截止日</th>
							<th>商户编号</th>
							<th>计划还款金额</th>
							<th>差异天数</th>
							<th>差异罚息</th>
							<th>发生时间</th>
							<th>清算金额</th>
							<th>状态</th>
							<th>备注</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<script type="script/template" id="tableFieldTmpl">
							{% _.each(list, function(settlementOrder, index){ %}
								<tr data="{%= settlementOrder.settlementOrderUuid %}">
								    <td>
								    	<input type="checkbox" {%= settlementOrder.assetSet.settlementStatusMsg == '已清算' ? 'DISABLED':'' %}>
								    </td>
								    <td>{%= settlementOrder.settleOrderNo %}</td>
								    <td>{%= settlementOrder.assetSet.singleLoanContractNo %}</td>
								    <td>{%= settlementOrder.assetSet.assetRecycleDate %}</td>
								    <td>{%= new Date(settlementOrder.dueDate).format('yyyy-MM-dd') %}</td>
								    <td>{%= settlementOrder.assetSet.contract.app.appId %}</td>
								    <td>{%= (+settlementOrder.assetSet.assetInitialValue).formatMoney(2,'') %}</td>
								    <td>{%= settlementOrder.overdueDays %}</td>
								    <td>{%= (+settlementOrder.overduePenalty).formatMoney(2,'') %}</td>
								    <td>{%= settlementOrder.lastModifyTime %}</td>
								    <td>{%= (+settlementOrder.settlementAmount).formatMoney(2,'') %}</td>
								    <td>{%= settlementOrder.assetSet.settlementStatusMsg %}</td>
								    <td>{%= settlementOrder.comment %}</td>
								    <td>
								        <a href="${ctx}/settlement-order/settle/{%= settlementOrder.id %}/detail" class="hover-no-text-decoration" data-target="#detailDialog" data-toggle="modal" title="详情">详情</a>
								    </td>
								</tr>
							{% }); %}
						</script>
					</tbody>
				</table>
			</div>

			<div class="operations">
				<sec:authorize ifNotGranted="ROLE_TRUST_OBSERVER">
					<span class="item">
		              <button id="batchSubmit" class="btn">批量提交</button>
					</span>
					<span class="item">
						<button id="batchSettlement" class="btn">批量清算</button>
					</span>
				</sec:authorize>
				<span class="item">
					<button id="submit" class="btn export-excel" data-action="${ctx}/settlement-order/settle/export_settlement_excel">导出</button>
				</span>
				<jsp:include page="/WEB-INF/include/plugins/assembly.jsp">
		            <jsp:param value="page-control" name="type"/>
		            <jsp:param value="false" name="advanced"/>
	            </jsp:include>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>
    <script src="${ctx.resource}/js/bootstrap-modal-trigger.js"></script>

	<script type="text/javascript">
		// $("#submit").click(function() {
		// 	var data = $("#params").serialize();
		// 	window.location.href = "${ctx}/settlement-order/settle/export_settlement_excel?" + data;
		// });

		$("#batchSubmit").click(function() {
			var $checkedBox = $("tbody input[type='checkbox']:checked");
			if ($checkedBox.length <= 0) {
				return false;
			}
			var array = [];
			$checkedBox.each(function() {
				var voucherUuid = $(this).parents("tr").attr("data");
				array.push(voucherUuid);
			});
			$.post('${ctx}/settlement-order/batch-submit', {
				settlementOrderUuids : JSON.stringify(array)
			}, function(data) {
				window.location.reload();
			})
		});
		$("#batchSettlement").click(function() {
			var $checkedBox = $("tbody input[type='checkbox']:checked");
			if ($checkedBox.length <= 0) {
				return false;
			}
			var array = [];
			$checkedBox.each(function() {
				var voucherUuid = $(this).parents("tr").attr("data");
				array.push(voucherUuid);
			});
			$.post('${ctx}/settlement-order/batch-settlement', {
				settlementOrderUuids : JSON.stringify(array)
			}, function(data) {
				window.location.reload();
			})
		});
	</script>
</body>
</html>


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setBundle basename="ApplicationMessage" />
<fmt:setLocale value="zh-CN" />
<div>
	<div class="lookup-params">
	<div class="row">
				<span class="item">
					<input type="text" class="form-control" placeholder="来源单号" name="orderNo" value="${orderMatchQueryModel.orderNo}" aria-describedby="basic-addon1">
				</span>
			
				<span class="item">
					 <select name="orderType" class="form-control">
						<option value="-1">来源类型</option>
						<c:forEach var="item" items="${orderTypes}">
							<option value="${item.ordinal() }"
								<c:if test="${item.ordinal() eq orderMatchQueryModel.orderType }">selected</c:if>><fmt:message
									key="${item.key}" /></option>
						</c:forEach>
					</select>
				</span>
				<span class="item">
					<input type="text" class="form-control" name="customerName" placeholder="客户姓名" value="${orderMatchQueryModel.customerName}"  aria-describedby="basic-addon1">
				</span>
				<span class="item beginend-datepicker">
		              <jsp:include page="/WEB-INF/include/plugins/datetimepicker.jsp">
		                <jsp:param value="group" name="type"/>
		                <jsp:param value="assetRecycleStartDateString" name="paramName1"/>
		                <jsp:param value="assetRecycleEndDateString" name="paramName2"/>
		                <jsp:param value="${orderMatchQueryModel.assetRecycleStartDateString}" name="paramValue1"/>
		                <jsp:param value="${orderMatchQueryModel.assetRecycleEndDateString}" name="paramValue2"/>
		                <jsp:param value="请输入计划还款起始日期" name="placeholder1"/>
		                <jsp:param value="请输入计划还款终止日期" name="placeholder2"/>
		              </jsp:include>
				</span>
				
		
		<div class="col-md-3 pull-right">
			<div class="input-group">
				<button id="searchButton"
					class="btn btn-default btn-shadow btn-shadow-default demo2do-btn form-control"
					title="查询">
					<i class="glyphicon glyphicon-search"></i>&nbsp;&nbsp;查询
				</button>
			</div>
		</div>
	</div>
	</div>
	<div class="table-area">
	<table class="data-list">
		<thead>
			<tr>
				<th>序号</th>
				<th>来源单号</th>
				<th>还款期号</th>
				<th>应还日期</th>
				<th>结算日期</th>
				<th>客户姓名</th>
				<th>应还本金</th>
				<th>应还利息</th>
				<th>差异罚息</th>
				<th>差异天数</th>
				<th>发生时间</th>
				<th>应还金额</th>
				<th>已付金额</th>
				<th>关联金额</th>
				<th>状态</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody id="result">
			<c:forEach var="item" items="${orderMatchModelList}"
				varStatus="status">
				<tr data-id="${item.orderNo}">
					<td nowrap><input type="checkbox"
						${item.status.ordinal() ==2 ? 'DISABLED':''}>&nbsp;&nbsp;${status.index + 1}</td>
					<td>${item.orderNo}</td>
					<td>${item.singleLoanContractNo}</td>
					<td>${item.assetRecycleDate}</td>
					<td>${item.settlementDate}</td>
					<td>${item.customerName}</td>
					<td nowrap>${item.assetPrincipalValue}</td>
					<td nowrap>${item.assetInterestAmount}</td>
					<td nowrap>${item.penaltyInterestAmount}</td>
					<td>${item.overDueDays}</td>
					<td>${item.modifyTime}</td>
					<td nowrap>${item.amount}</td>
					<td nowrap>${item.paidAmount}</td>
					<td><input type="text" id="amount"></td>
					<td><fmt:message key="${item.status.key}" /></td>
					<td>${item.comment}</td>
			</c:forEach>
		</tbody>

	</table>
	
	<br>
	<div class="col-xs-2">
		<div class="btn-group">
			<button id="submitButton" name="submitButton"
				class="btn btn-default btn-shadow btn-shadow-default demo2do-btn">关联
			</button>
		</div>
	</div>
	</div>
</div>

<%-- <script src="${ctx.resource}/css/plugins/bootstrap-datetimepicker.css" ></script> --%>
<script>
	$('#searchButton').click(function() {
		var $orderNo = $("[name='orderNo']").val();
		var $orderType = $("[name='orderType']").val();
		var $customerName = $("[name='customerName']").val();
		var $assetRecycleStartDateString = $("[name='assetRecycleStartDateString']").val();
		var $assetRecycleEndDateString = $("[name='assetRecycleEndDateString']").val();
		$.ajax({
			type : 'get',
			url : '${ctx}/offline-payment-manage/search',
			data : {
				orderNo : $orderNo,
				orderType : $orderType,
				customerName : $customerName,
				assetRecycleStartDateString: $assetRecycleStartDateString,
				assetRecycleEndDateString: $assetRecycleEndDateString
			},
			success : function(resp) {
				var htm = $(resp).find('#result').html();
				$('#result').html(htm);
			}
		})
	});

	$('#submitButton').click(function() {
		var $item = $("tbody input[type='checkbox']:checked").parents('tr');
		var res = {};

		if ($item.length <= 0) {
			return false;
		}

		$item.each(function() {
			var $el = $(this);
			var key = $el.data('id');
			var value = $el.find('#amount').val();
			if (key && value) {
				res[key] = value;
			}
		});

		$.ajax({
			type : 'post',
			url : '${ctx}/offline-payment-manage/connection',
			data : {
				'orderNoAndValues' : JSON.stringify(res),
				'offlineBillUuid' : '${offlineBillUuid}'
			},
			dataType : 'json',
			success : function(resp) {
				if (resp.code == 0) {
					alert("关联成功！")
				} else {
					alert(resp.message)
				}
				window.location.reload();
			}
		});

	});
</script>

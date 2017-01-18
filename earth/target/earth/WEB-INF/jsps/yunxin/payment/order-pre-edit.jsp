<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setBundle basename="ApplicationMessage" />
<fmt:setLocale value="zh-CN" />

<div class="modal-dialog demo2do-modal-dialog">

	<div class="modal-content">

		<div class="modal-header clearfix">
			<button type="button" class="close close-dialog" data-dismiss="modal">&times;</button>
			<h4 class="modal-title" id="dialoglabel">
				结算单
			</h4>
		</div>
			<div class="modal-body" style="padding: 15px;">
				<table border="1">
					<tr>
						<th>结算单号</th>
						<td>${ order.orderNo }</td>
						<th>回款期号</th>
						<td>${ order.singleLoanContractNo }</td>
					</tr>
					<tr>
						<th>合同编号</th>
						<td>${ order.assetSet.contract.contractNo}</td>
						<th>计划还款日期</th>
						<td><fmt:formatDate value="${ order.assetRecycleDate}" pattern="yyyy-MM-dd"/></td>
					</tr>
					<tr>
						<th>贷款客户编号</th>
						<td>${ order.customer.source}</td>
						<th>应还款日期</th>
						<td><fmt:formatDate value="${ order.dueDate}" pattern="yyyy-MM-dd"/></td>
					</tr>
					<tr>
						<th>贷款客户姓名</th>
						<td>${ order.customer.name}</td>
						<th></th>
						<td></td>
					</tr>
					<tr>
						<th>本息金额（元）</th>
						<td><fmt:formatNumber type="number" pattern="#0.00" value="${order.assetInitialValue}"></fmt:formatNumber></td>
						<th>结算单应还金额</th>
						<td>${order.totalRent }元</td>
					</tr>
					<tr>
						<th>逾期天数</th>
						<td>${ order.numbersOfOverdueDays}</td>
						<th>逾期罚息利率</th>
						<td>${ order.assetSet.contract.penaltyInterest}</td>
					</tr>
					<tr>
						<th>逾期罚息金额</th>
						<td>${ orderViewDetail.contractAccount.bindId}</td>
						<th>调整金额</th>
						<td><input name="deltaAmount" type="text" onchange="changeAmount(this.value)"/>元</td>
					</tr>
					<tr>
						<th>备注</th>
						<td><input name="comment" type="text" value="${order.comment }" /></td>
						<th>应还金额</th>
						<td><span id="amountToBePaid" style="color:red;">${order.totalRent }</span>元</td>
					</tr>
				</table>
				<input name="repaymentBillId" type="hidden" value="${order.repaymentBillId }" />
				<button id="submitButton" name="submitButton"
					class="btn btn-default btn-shadow btn-shadow-default demo2do-btn">提交
				</button>
			</div>
		</div>
</div>
<script>
	$('#submitButton').click(function() {
		var $deltaAmount = $("input[name='deltaAmount']").val();
		var $comment = $("input[name='comment']").val();
		var $repaymentBillUuid = $("input[name='repaymentBillId']").val();

		$.ajax({
			type : 'post',
			url : '${ctx}/payment-manage/order/'+$repaymentBillUuid+'/edit',
			data : {
				'deltaAmount' : $deltaAmount,
				'comment' : $comment,
			},
			dataType : 'json',
			success : function(resp) {
				if (resp.code == 0) {
					alert("更改成功！")
				} else {
					alert(resp.message)
				}
				$('#edit-order-dialog .close-dialog').click();
				$("#lookup").click();
			}
		});

	});
	function changeAmount(deltaAmount){
		var patten = new RegExp("[-]{0,1}\\d+(\.\\d{0,2})?");
		if(patten.test(deltaAmount)){
			var deltaAmount = new Number(deltaAmount);
			var totalAmount = '${order.totalRent }';
			var payAmount = totalAmount-0+deltaAmount;
			if(payAmount >0 ){
				$("#amountToBePaid").text(payAmount);
			} else{
				alert('请输入正确的金额');
			}
		}else{
			alert('请输入正确的金额');
		}
	}
</script>

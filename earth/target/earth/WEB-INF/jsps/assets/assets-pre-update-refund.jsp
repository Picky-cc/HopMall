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
				退款
			</h4>
		</div>
			<div class="modal-body" style="padding: 15px;">
				<table border="1">
					<tr>
						<th>还款编号</th>
						<td>${ assetSet.singleLoanContractNo }</td>
						<th>合同编号</th>
						<td>${ assetSet.contract.contractNo }</td>
					</tr>
					<tr>
						<th>计划还款金额</th>
						<td>${ assetSet.assetInitialValue}</td>
						<th>还款金额</th>
						<td>${ assetSet.assetFairValue} </td>
					</tr>
					<tr>
						<th>计划还款日期</th>
						<td><fmt:formatDate value="${ assetSet.assetRecycleDate}" pattern="yyyy-MM-dd"/></td>
						<th>资金到账日期</th>
						<td><fmt:formatDate value="${ assetSet.actualRecycleDate}" pattern="yyyy-MM-dd"/></td>
					</tr>
					<tr>
						<th>退款金额</th>
						<td><input name="refundAmount" type="text" value="${assetSet.refundAmount }" /></td>
						<th>备注</th>
						<td><input name="comment" type="text" value="${assetSet.comment }" /></td>
					</tr>
				</table>
				<input name="assetSetId" type="hidden" value="${assetSet.id }" />
				<button type="button" id="refundButton" name="refundButton"
					class="btn btn-default btn-shadow btn-shadow-default demo2do-btn">提交
				</button>
			</div>
		</div>
</div>
<script>
	$('#refundButton').click(function() {
		var $refundAmount = $("input[name='refundAmount']").val();
		var $comment = $("input[name='comment']").val();
		var $assetSetId = $("input[name='assetSetId']").val();
		if(!valid($refundAmount)){
			alert("请输入正确的金额");
			return false;
		}
		$.ajax({
			type : 'post',
			url : '${ctx}/assets/'+$assetSetId+'/update-refund',
			data : {
				'refundAmount' : $refundAmount,
				'comment' : $comment,
			},
			dataType : 'json',
			success : function(resp) {
				if (resp.code == 0) {
					alert("更改成功！")
				} else {
					alert(resp.message)
				}
				$('#modal-dialog .close-dialog').click();
				$("#query").click();
			}
		});

	});
	function valid(amount){
		var patten = new RegExp("[-]?\\d+(\.\\d{0,2})?");
		if(patten.test(amount)){
			return true;
		}else{
			return false;
		}
	}
</script>

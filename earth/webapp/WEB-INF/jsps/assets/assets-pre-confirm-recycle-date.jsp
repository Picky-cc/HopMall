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
				确认还款日期
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
						<th>人工确认日期${date }</th>
						<c:set var="assetSetConfirmRecycleDate">
							<fmt:formatDate value="${ assetSet.confirmRecycleDate}" pattern="yyyy-MM-dd"/>
						</c:set>
						<td>
						<span class="item beginend-datepicker">
				              <jsp:include page="/WEB-INF/include/plugins/datetimepicker.jsp">
				                <jsp:param value="false" name="calendarbtn"/>
				                <jsp:param value="true" name="clearbtn"/>
				                <jsp:param value="confirmRecycleDateString" name="paramName1"/>
				                <jsp:param value='${assetSetConfirmRecycleDate}' name="paramValue1"/>
				                <jsp:param value="请输入确认还款日期" name="placeholder1"/>
				              </jsp:include>
				        </span>
						</td>
						<th>备注</th>
						<td><input name="comment" type="text" value="${assetSet.comment }" /></td>
					</tr>
				</table>
				<input name="assetSetId" type="hidden" value="${assetSet.id }" />
				<button type="button" id="confirmDateButton" name="confirmDateButton"
					class="btn btn-default btn-shadow btn-shadow-default demo2do-btn">提交
				</button>
			</div>
		</div>
</div>
<script>
	$('#confirmDateButton').click(function() {
		var $confirmRecycleDateString = $("input[name='confirmRecycleDateString']").val();
		var $comment = $("input[name='comment']").val();
		var $assetSetId = $("input[name='assetSetId']").val();
		if($confirmRecycleDateString == ""){
			alert("请输入时间");
			return false;
		}
		$.ajax({
			type : 'post',
			url : '${ctx}/assets/'+$assetSetId+'/confirm-recycle-date',
			data : {
				'confirmRecycleDateString' : $confirmRecycleDateString,
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
</script>

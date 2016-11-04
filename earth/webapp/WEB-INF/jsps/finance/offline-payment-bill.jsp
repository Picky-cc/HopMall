<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>线下支付单列表 - 五维金融金融管理平台</title>
</head>
<body>

	<%@ include file="/WEB-INF/include/header.jsp"%>

	<div class="web-g-main">
		<%@ include file="/WEB-INF/include/aside.jsp"%>
		<div class="content">
			<div class="scroller">
				<div class="lookup-params">
					<div class="inner clearfix">
						<div class="pull-left">
							<input type="hidden" name="filter" value="true">
							<span class="item">
								<input type="text" class="form-control real-value" name="accountName" value="" placeholder="输入账户姓名">
							</span> 
							<span class="item"> <input type="text" class="form-control real-value" name="payAcNo" value="" placeholder="请输入银行账户号">
							</span>
							<span class="item">
								<button type="submit" id="lookup" class="btn btn-primary">查询</button>
							</span> 
							<sec:authorize ifNotGranted="ROLE_TRUST_OBSERVER">
								<span class="item">
									<button id="pre-create-offline-bill" type="button" class="btn btn-primary">新增</button>
								</span>
							</sec:authorize>
						</div>
					</div>
				</div>
				<div class="table-area">
					<table class="data-list" data-action="./query" data-autoload="true">
						<thead>
							<tr>
								<th>序号</th>
								<th>支付单号</th>
								<th>银行名称</th>
								<th>账户姓名</th>
								<th>银行账户号</th>
								<th>支付机构流水号</th>
								<th>支付金额</th>
								<th>关联金额</th>
								<th>关联状态</th>
								<th>入账时间</th>
								<th>发生时间</th>
								<th>支付状态</th>
								<th>备注</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<script type="script/template" id="tableFieldTmpl">
							{% var OfflineBillStatus = { UNPAID:'失败',PAID:'成功' }; %}
							    {% _.each(list, function(item, index) { %}
							        <tr class="record-item">
							            <td>{%= index + 1 %}</td>
							            <td><a href="${ctx}/offline-payment-manage/payment/{%= item.offlineBill.id %}/detail">{%= item.offlineBill.offlineBillNo %}</a></td>
							            <td>{%= item.offlineBill.bankShowName %}</td>
							            <td>{%= item.offlineBill.payerAccountName %}</td>
							            <td>{%= item.offlineBill.payerAccountNo %}</td>
							            <td>{%= item.offlineBill.serialNo %}</td>
							            <td>{%= (+item.offlineBill.amount).formatMoney(2, '') %}</td>
							            <td>{%= (+item.sourceDocument.bookingAmount).formatMoney(2,'') %}</td>
							            <td>{%= item.offlineBillConnectionState %}</td>
							            <td>{%= item.offlineBill.tradeTime %}</td>
							            <td>{%= item.offlineBill.statusModifiedTime %}</td>
							            <td>{%= OfflineBillStatus[item.offlineBill.offlineBillStatus]%}</td>
							            <td>{%= item.offlineBill.comment %}</td>
							            <td> 
											<a href="${ctx}/offline-payment-manage/payment/{%= item.offlineBill.id %}/detail">详情</a>
							            </td>
							        </tr>
							    {% }); %}
							</script>
						</tbody>
					</table>
				</div>
			</div>
			<div class="operations">
				<jsp:include page="/WEB-INF/include/plugins/assembly.jsp">
		            <jsp:param value="page-control" name="type"/>
		            <jsp:param value="true" name="advanced"/>
	            </jsp:include>
			</div>
		</div>
	</div>

	<div class="modal fade in bs-example-modal-sm" id="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<form method="POST"
					action="${ctx}/offline-payment-manage/payment/create-offline-bill">
					<div class="modal-header">
						<button type="button" class="close close-dialog" aria-label="关闭">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="dialoglabel">新增线下支付单</h4>
					</div>
					<div class="modal-body">
						<p id="showMsg" style="margin: 0;"></p>
						<p id="resultMsg" style="margin: 0;"></p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default close-dialog">关闭</button>
						<button type="button" id="submitbutton" name="submitbutton"
							type="submit" class="btn btn-success">提交</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>
	<script src="${ctx.resource}/js/bootstrap-modal-trigger.js"></script>
	<script>
		(function($, global) {
			$('#pre-create-offline-bill').click(function() {
				$.get('${ctx}/offline-payment-manage/payment/pre-create-offline-bill', {}, function(data) {
					resultHandler(data, true);
				})
			});

			var resultHandler = function(message, isReload) {

				$('#dialog').find('#showMsg').html(message).end().modal();

				$('#dialog .close-dialog').click(function() {

					$('#dialog').modal('hide');

					if (isReload) {

						window.location.reload();
					}
					;

				});
			};

			$("button[name='submitbutton']").on("click",function() {
				$(this).attr('disabled', "true");
				$(this).html("创建中……");
				var $payerAccountName = $("input[name='payerAccountName']").val();
				var $tradeTimeString = $("input[name='tradeTimeString']").val();
				var $bankShowName = $("input[name='bankShowName']").val();
				var $amount = $("input[name='amount']").val();
				var $payerAccountNo = $("input[name='payerAccountNo']").val();
				var $serialNo = $("input[name='serialNo']").val();
				var $comment = $("input[name='comment']").val();
				$.ajax({
					type : 'POST',
					url : "${ctx}/offline-payment-manage/payment/create-offline-bill",
					data : {
						'payerAccountName' : $payerAccountName,
						'tradeTimeString' : $tradeTimeString,
						'bankShowName' : $bankShowName,
						'amount' : $amount,
						'payerAccountNo' : $payerAccountNo,
						'serialNo' : $serialNo,
						'comment' : $comment
					},
					dataType : 'json',
					error : function() {
						$('.close').click();
						alert('网络错误！稍后再试！');
						$(this).removeAttr("disabled");
						$(this).html("创建线下支付单");
					},
					success : function(data) {
						if (data.code == '0') {
							$('.close').click();
							alert("生成支付单号："+ data.data.offlineBillNo);
						} else {
							alert(data.message);
						}
						window.location.reload();
					}
				});
			});

		})(jQuery, window);
	</script>
</body>
</html>


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
  <title>manual-transactions- 五维金融金融管理平台</title>
</head>
<body>

  <%@ include file="/WEB-INF/include/header.jsp"%>
  
  <div class="web-g-main">

	<div class="aside-box">
		报文 ：
		<textarea id="showText" class="form-control" rows="30" cols=""></textarea>
	</div>
    <div class="content">	
		<div class="scroller">
			<table class="data-list">
				<thead>
					<tr>
						<th>id</th>
						<th>银行代码</th>
						<th>银行卡号</th>
						<th>客户名</th>
						<th>身份证号</th>
						<th>金额</th>
						<th>银联交易单号</th>
						<th data-title="0:实时代收，1批量代收">交易类型</th>
						<th>备注</th>
						<th>请求报文</th>
						<th>响应报文</th>
						<th>查询报文</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="transaction" items="${transactions}">
						<tr>
							<td>${transaction.id}</td>
							<td>${transaction.bankCode}</td>
							<td>${transaction.bankCardNo}</td>
							<td>${transaction.bankCardOwner}</td>
							<td>${transaction.idCardNo}</td>
							<td>${transaction.amount}</td>
							<td>${transaction.unionpayTransactionNo}</td>
							<td>${transaction.transactionType}</td>
							<td>${transaction.remark}</td>
							<td>
								<button name="detailBtn" type="button" class="btn btn-default">查看</button>
								<textarea style="display: none;">${transaction.requestPacket}</textarea>
							</td>
							<td>
								<button name="detailBtn" type="button" class="btn btn-default">查看</button>
								<textarea style="display: none;">${transaction.responsePacket}</textarea>
							</td>
							<td>
								<button name="detailBtn" type="button" class="btn btn-default">查看</button>
								<textarea style="display: none;">${transaction.queryResponsePacket}</textarea>
							</td>
							<td>
								<button name="queryResult" type="button" class="btn btn-primary" data-query-req-no="${transaction.unionpayTransactionNo}" data-payment-channel-id="${transaction.paymentChannelId}">查询结果</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
    </div>
  </div>

	<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>
	<script type="text/javascript">
		$(function(){
			$("button[name='detailBtn']").on("click", function(){
				$btn = $(this);
				$textarea = $btn.next();
				$("#showText").val($textarea.val());
			});
			$("button[name='queryResult']").on("click",function(){
				var queryReqNoVal = $(this).data("query-req-no");
				var paymentChannelIdVal = $(this).data("payment-channel-id");
				$.get("/manual-deduct/query-deduct", {
					queryReqNo : queryReqNoVal,
					paymentChannelId : paymentChannelIdVal
				}, function(data) {
					var result = jQuery.parseJSON(data);
					$("#showText").val(result.data.gzUnionPayResult);
				})
			});
		});
	</script>
</body>
</html>

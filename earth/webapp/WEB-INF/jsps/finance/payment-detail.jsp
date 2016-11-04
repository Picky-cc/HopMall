<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>

<fmt:setBundle basename="ApplicationMessage" />
<fmt:setLocale value="zh-CN" />

<%@ include file="/WEB-INF/include/meta.jsp"%>
<%@ include file="/WEB-INF/include/css-newlayout.jsp"%>
<title>线上支付单详情 - 五维金融金融管理平台</title>

</head>
<body>

	<%@ include file="/WEB-INF/include/header.jsp"%>

	<div class="web-g-main">

		<%@ include file="/WEB-INF/include/aside.jsp"%>

		<div class="content">
			<input type="hidden" name="overdueStatus" value="${assetSetModel.assetSet.overdueStatus.ordinal}">
			<div class="scroller">
				<div class="position-map">
					<div class="pull-left">
			            当前位置:
			            <span class="item current">线上支付单详情</span>
					</div>
				</div>
				
				<div class="col-layout-detail">
					<div class="top">
						<div class="block">
							<h5 class="hd">支付信息</h5>
							<div class="bd">
								<div class="col">
									<p>付款方名称 ：${transferApplication.contractAccount.payerName}</p>
									<p>付款方开户行 ：${transferApplication.contractAccount.bank}</p>
									<p>发生时间 ：${transferApplication.lastModifiedTime}</p>
									<p>发生金额 ：
										<fmt:formatNumber type='number' value='${transferApplication.amount}' pattern='#,##0.00#'/>
									</p>
								</div>

								<div class="col">
									<p>支付单号 ：${transferApplication.transferApplicationNo}</p>
									<p>支付接口编号 ：</p>
									<p>支付状态 ：<fmt:message key="${transferApplication.executingDeductStatus.key}" /></p>
									<p>备注 ：${transferApplication.comment}</p>
								</div>

								<div class="col">
									<p>支付机构 ：银联</p>
									<p>支付机构流水号 ：${transferApplication.unionPayOrderNo}</p>
									<p>支付方式 ：<fmt:message key="${transferApplication.paymentWay.key}"></fmt:message></p>
									<p>绑定号 ：${transferApplication.contractAccount.bindId}</p>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="table-layout-detail">
					<jsp:include page="/WEB-INF/include/system-operate-log.jsp">
						<jsp:param value="${transferApplication.transferApplicationUuid}" name="objectUuid" />
					</jsp:include>
				</div>
			

			</div>
		</div>
	</div>

	<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>
	
</body>
</html>
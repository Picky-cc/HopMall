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
<title>task- 五维金融金融管理平台</title>
</head>
<body>

	<%@ include file="/WEB-INF/include/header.jsp"%>


	<div class="web-g-main">

		<%@ include file="/WEB-INF/include/aside.jsp"%>
		<div class="content">
			<div class="static-info-wrapper">
				<div>Task 相关：</div>
				<div class="lookup-params">
					<span class="item">
						<button id="doEvaluateAssetTask" class="btn btn-primary">资产评估,生成结算单</button>
					</span>
					<span class="item">
						<button id="doTRTask_today" class="btn btn-primary">正常扣款</button>
					</span>
					<span class="item">
						<button id="doTRTask_today_overdue" class="btn btn-primary">逾期扣款</button>
					</span>
					<span class="item">
						<button id="doTRTask_prepayment" class="btn btn-primary">提前还款扣款</button>
					</span>
					<span class="item">
						<button id="doSyncOrderStatusTask_today" class="btn btn-primary">日结算单状态同步</button>
					</span>
					<span class="item">
						<button id="doGuaranteeOrderTask" class="btn btn-primary">生成担保单</button>
					</span>
					<span class="item">
						<button id="doSettlementOrderTask" class="btn btn-primary">生成清算单</button>
					</span>
					<span class="item">
						<button id="doQueryOrderResultTask" class="btn btn-primary">查询扣款结果</button>
					</span>
					<span class="item">
						<button id="doCreateRemindAndOverDueSmsQueneTask" class="btn btn-primary">还款提醒短信</button>
					</span>
					<span class="item">
						<button id="doSendSmsQueneTask" class="btn btn-primary">发送短信</button>
					</span>
				</div>
				<div>Unionpay 相关：</div>
				<div class="lookup-params">
					<span class="item">
						<button id="doRefreshUnionpayBankConfig" class="btn btn-primary">刷新银联银行配置</button>
					</span> <span class="item"> <a href="/manual-deduct/pre-deduct"
						target="_blank" class="btn btn-primary">银联手动交易</a>
					</span> <span class="item"> <a
						href="/manual-tasks/manual-transactions" target="_blank"
						class="btn btn-primary">查看银联手动交易列表</a>
					</span>
				</div>
				<div>导出相关：</div>
				<div class="lookup-params">
					<form id="myForm" action="" method="GET">
						<span> <select name="financialContractId"
							class="form-control">
								<option value="-1">信托项目号</option>
								<c:forEach var="item" items="${financialContractList}">
									<option value="${item.id }"
										<c:if test="${item.id eq financialContract.id }">selected</c:if>>
										${item.contractNo}</option>
								</c:forEach>
						</select>
						</span> <span class="item beginend-datepicker"> <jsp:include
								page="/WEB-INF/include/plugins/datetimepicker.jsp">
								<jsp:param value="group" name="type" />
								
								
								<jsp:param value="startDateString" name="paramName1" />
								<jsp:param value="endDateString" name="paramName2" />
								<jsp:param value="" name="paramValue1" />
								<jsp:param value="" name="paramValue2" />
								<jsp:param value="请输入发生起始日期" name="placeholder1" />
								<jsp:param value="请输入发生终止日期" name="placeholder2" />
							</jsp:include>
						</span>
						<span class="item">
							<button id="loanContractInfo" class="btn btn-primary">导出贷款信息表</button>
						</span>
						<span class="item">
							<button id="repaymentPlanDetail" class="btn btn-primary">还款计划明细汇总表</button>
						</span>
						<span class="item">
							<button id="overDueRepaymentPlanDetail" class="btn btn-primary">逾期还款明细表</button>
						</span>
					</form>
				</div>
				<div>接口相关：</div>
				<div class="lookup-params">
					<span class="item"> 
					<a href="/manual-apis" target="_blank" class="btn btn-primary">接口手动测试</a>
					</span>
				</div>
				<div>接口数据同步：</div>
				<div class="lookup-params">
				
				    <span class="item">
							<button id="doTaskDataSync" class="btn btn-primary">数据同步测试</button>
						</span>
					
				</div>
				<div id="messageDiv"></div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>
	<script type="text/javascript">
		$(function() {

			$("#loanContractInfo").click(function(e) {
				var data = $("#myForm").serialize();
				e.preventDefault();
				e.stopPropagation();
				window.open("${ctx}/contracts/exprot-loan-contract?" + data);

			});
			$("#repaymentPlanDetail")
					.click(
							function(e) {
								var data = $("#myForm").serialize();
								e.preventDefault();
								e.stopPropagation();
								window
										.open("${ctx}/assets/exprot-repayment-plan-detail?"
												+ data);

							});

			$("#overDueRepaymentPlanDetail")
					.click(
							function(e) {
								e.preventDefault();
								e.stopPropagation();
								var data = $("#myForm").serialize();
								window
										.open("${ctx}/assets/exprot-overDue-repayment-plan-detail?"
												+ data);
							});

		});

		$(function() {

			var ajaxGet = function(btnId, url) {
				$(btnId).click(
						function() {
							$btn = $(this);
							$btn.attr("disabled", "disabled");
							$.get(url, {}, function(data) {
								$btn.removeAttr("disabled");
								$("#messageDiv").append(
										"<p>" + new Date() + " : "
												+ $.parseJSON(data).message
												+ "</p>");
							})
						});
			}

			//资产评估,生成结算单
			ajaxGet("#doEvaluateAssetTask", "/manual-tasks/evaluate-asset");
			//正常扣款
			ajaxGet("#doTRTask_today", "/manual-tasks/trtask-today");
			//逾期扣款
			ajaxGet("#doTRTask_today_overdue", "/manual-tasks/trtask_overdue");
			//提前还款扣款
			ajaxGet("#doTRTask_prepayment", "/manual-tasks/trtask_prepayment");
			//日结算单状态同步
			ajaxGet("#doSyncOrderStatusTask_today",
					"/manual-tasks/sync-order-status");
			//生成担保单
			ajaxGet("#doGuaranteeOrderTask", "/manual-tasks/guarantee-order");
			//生成清算单
			ajaxGet("#doSettlementOrderTask", "/manual-tasks/settlement-order");
			//查询扣款结果
			ajaxGet("#doQueryOrderResultTask",
					"/manual-tasks/query-order-result");
			//还款提醒短信
			ajaxGet("#doCreateRemindAndOverDueSmsQueneTask",
					"/manual-tasks/create-sms");
			//发送短信
			ajaxGet("#doSendSmsQueneTask", "/manual-tasks/send-sms");
			//刷新银联银行配置
			ajaxGet("#doRefreshUnionpayBankConfig",
					"/manual-tasks/refresh-unionpay-bank-config");
			ajaxGet("#doTaskDataSync","/manual-tasks/data-sync");
		});
	</script>
</body>
</html>

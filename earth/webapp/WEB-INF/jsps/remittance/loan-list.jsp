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
<title>放款单列表 - 五维金融金融管理平台</title>

<script type="template/script" id="batchLoanInfoTmpl">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button class="close close-dialog" aria-label="关闭" data-dismiss="modal" type="button">
					<span aria-hidden="true">x</span>
				</button>
				<h4 class="modal-title" id="dialoglabel">批量放款信息预览</h4>
			</div>
			<div class="modal-body">
				<table class="data-list" data-action="" data-autoload="true">
					<thead>
						<tr>
							<th>放款编号</th>
							<th>合同编号</th>
							<th>计划放款日期</th>
							<th>放款金额</th>
							<th>付款方账户名</th>
							<th>收款方账户名</th>
							<th>放款状态</th>
							<th>备注</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><a href="#">FK000001</a></td>
							<td><a href="#">jfdsf4d4f4df4df-d59f6s</a></td>
							<td>2016-07-19</td>
							<td>3000</td>
							<td>韩梅梅</td>
							<td>李雷</td>
							<td>
								已创建
								<!--<span class="color-warning">异常</span> 
									<span class="color-danger">失败</span>   -->
							</td>
							<td></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="modal-footer">
				  <a href="#" class="btn btn-default cancel-dialog" data-dismiss="modal">取消</a>
				  <a href="#" class="btn btn-success submit">确认放款</a>
			</div>
		</div>
	</div>
</script>
</head>
<body>
	<%@ include file="/WEB-INF/include/header.jsp"%>
	<div class="web-g-main">
		<%@ include file="/WEB-INF/include/aside.jsp"%>
		<div class="content">
			<div class="scroller">
				<div class="lookup-params">
					<div class="inner clearfix">
						<span class="item">
							<select name="financialContractUuids" class="form-control real-value selectpicker" multiple data-actions-box="true" selectedTextFormat="static" data-title="信托合作项目">
								<c:forEach var="item" items="${financialContracts}">
									<option value="${item.financialContractUuid}" selected>${item.contractName}(${item.contractNo})</option>
								</c:forEach>
							</select>
						</span>
						<span class="item">
							<input type="text" class="form-control real-value" name="orderNo" value="" placeholder="放款编号">
						</span>
						<span class="item">
							<input type="text" class="form-control real-value" name="loanContractNo" value="" placeholder="贷款合同编号">
						</span>
						<span class="item beginend-datepicker">
							<jsp:include page="/WEB-INF/include/plugins/datetimepicker.jsp">
								<jsp:param value="group" name="type" />
								
								
								<jsp:param value="plannedStartDate" name="paramName1" />
								<jsp:param value="plannedEndDate" name="paramName2" />
								<jsp:param value="" name="paramValue1"/>
			             	   	<jsp:param value="" name="paramValue2"/>
								<jsp:param value="起始计划放款日期" name="placeHolder1" />
								<jsp:param value="终止计划放款日期" name="placeHolder2" />
							</jsp:include>
						</span>
						<span class="item">
							<input type="text" class="form-control real-value" name="payerAccountHolder" value="" placeholder="付款方账户名">
						</span>
						<span class="item">
							<input type="text" class="form-control real-value" name="cpBankAccountHolder" value="" placeholder="收款方账户名">
						</span>
						<span class="item">
							<select name="executionStatus" class="form-control real-value">
								<option value="-1">放款状态</option>
								<c:forEach var="item" items="${executionStatus}">
									<option value="${item.ordinal()}"><fmt:message key="${item.key}"/></option>
								</c:forEach>
							</select>
						</span>
						<span class="item">
							<select name="remittanceType" class="form-control real-value">
								<option value="-1">交易类型</option>
								<option value="0">代付</option>
								<option value="1">代收</option>
							</select>
						</span>
						<span class="item">
							<button id="lookup" class="btn btn-primary">
								查询
							</button>
						</span>
						<span class="item hide">
				            <a href="#" class="pull-right btn btn-success" id="batchLoan">
				                批量放款
				            </a>
						</span>
					</div>
				</div>	
				<div class="table-area">
					<table class="data-list" data-action="${ctx}/remittance/plan/query" data-autoload="true">
						<thead>
							<tr>
								<th>放款编号</th>
								<th>合同编号</th>
								<th>
									<a data-paramname="isAsc" class="sort none">
										计划执行日期<i class="icon"></i>
									</a>
								</th>
								<th>执行金额</th>
								<th>付款方账户名</th>
								<th>收款方账户名</th>
								<th>交易类型</th>
								<th>执行状态</th>
								<th>备注</th>
							</tr>
						</thead>
						<tbody>
							<script type="script/template" id="tableFieldTmpl">
								{% _.each(list,function(item,index){ %}
									<tr>
										<td><a href="${ctx}/remittance/plan/details/{%= item.remittancePlanUuid%}">{%= item.remittancePlanUuid%}</a></td>
										<td><a href="${ctx}/contracts/detail?uid={%= item.contractUniqueId%}">{%= item.contractNo%}</a></td>
										<td>{%= new Date(item.plannedPaymentDate).format('yyyy-MM-dd')%}</td>
										<td>{%= (+item.plannedTotalAmount).formatMoney(2,'')%}</td>
										<td>
											<span 	class="showPopover"
													data-container="body"
													data-placement="top"
													data-html="true"
													data-trigger="focus" 
													data-toggle="popover">
												{%= item.pgAccountInfo.accountName%}
											</span>
											{% if(!(_.isEmpty(item.pgAccountInfo.accountName))){ %}
												<i class="icon icon-bankcard" 
													data-container="body"
													data-placement="top"
													data-html="true"
													data-trigger="focus" 
													data-toggle="popover" >
												</i>
												<div class="hide account">
													<div>
														<span class='text-muted'>账户名:</span>{%= item.pgAccountInfo.accountName%}
														<br/>
														<span class='text-muted'>账户号:</span>{%= item.pgAccountInfo.accountNo%}
														<br/>
														<span class='text-muted'>开户行:</span>{%= item.pgAccountInfo.bankName%}
														<br/>
														<span class='text-muted'>所在地:</span>{%= item.pgAccountInfo.province%}&nbsp;{%= item.pgAccountInfo.city%}
														<br/>
														<span class='text-muted'>证件号:</span>{%= item.pgAccountInfo.idNumber%}
														<br/>
													</div>
												</div>
											{% } %}
										</td>
										<td>
											<span 	class="showPopover"
													data-container="body"
													data-placement="top"
													data-html="true"
													data-trigger="focus" 
													data-toggle="popover">
													{%= item.cpAccountInfo.accountName%}
											</span>
											{% if(!(_.isEmpty(item.cpAccountInfo.accountName))){ %}
												<i class="icon icon-bankcard"
													data-container="body"
													data-placement="top"
													data-html="true"
													data-trigger="focus" 
													data-toggle="popover"
													>
												</i>
												<div class="hide account">
													<div>
														<span class='text-muted'>账户名:</span>{%= item.cpAccountInfo.accountName%}
														<br/>
														<span class='text-muted'>账户号:</span>{%= item.cpAccountInfo.accountNo%}
														<br/>
														<span class='text-muted'>开户行:</span>{%= item.cpAccountInfo.bankName%}
														<br/>
														<span class='text-muted'>所在地:</span>{%= item.cpAccountInfo.province%}&nbsp;{%= item.cpAccountInfo.city%}
														<br/>
														<span class='text-muted'>证件号:</span>{%= item.cpAccountInfo.idNumber%}
														<br/>
													</div>
												</div>
											{% } %}
										</td>
										<td>{%= item.transactionType%}</td>
										<td>
											{% if(item.executionStatus =='异常'){ %}
												<span class="color-warning">{%= item.executionStatus%}</span> 
											{% }else if(item.executionStatus =='失败'){ %}
												<span class="color-danger">{%= item.executionStatus%}</span>
											{% }else{ %}
												{%= item.executionStatus%}
											{% } %}
										</td>
										<td>{%= item.transactionRemark%}</td>
									</tr>
								{% }) %}
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
	<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>

</body>
</html>>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

<fmt:setBundle basename="ApplicationMessage" />
<fmt:setLocale value="zh_CN" />

<%@ include file="/WEB-INF/include/meta.jsp"%>
<%@ include file="/WEB-INF/include/css-newlayout.jsp"%>
<title>结算单列表 - 五维金融金融管理平台</title>

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
							<select name="financialContractIds" class="form-control real-value selectpicker" multiple data-title="信托合同项目"  data-actions-box="true">
								<c:forEach var="item" items="${financialContracts}">
									<option value="${item.id }" selected>${item.contractName}(${item.contractNo })</option>
								</c:forEach>
							</select>
						</span>
						<span class="item"> 
							<select name="executingSettlingStatusInt" class="form-control real-value small" id="executingSettlingStatusSelect">
								<option value="-1">结算状态</option>
								<c:forEach var="item" items="${executingSettlingStatusList}">
									<option value="${item.ordinal()}">
										<fmt:message key="${item.key}" />
									</option>
								</c:forEach>
							</select>
						</span>
						<span class="item">
							<select name="overDueStatus" class="form-control real-value small" id="overDueStatusSelect">
								<option value="-1">差异状态</option>
								<c:forEach var="item" items="${overDueStatusList}">
									<option value="${item.ordinal()}">
										<fmt:message key="${item.key}" />
									</option>
								</c:forEach>
							</select>
						</span>
						<span class="item">
							<input type="text" placeholder="结算单号" class="form-control real-value" name="orderNo">
						</span>
						<span class="item">
							<input type="text" placeholder="还款编号" class="form-control real-value" name="singleLoanContractNo">
						</span>
						<span class="item beginend-datepicker">
							<jsp:include page="/WEB-INF/include/plugins/datetimepicker.jsp">
								<jsp:param value="group" name="type" />
								
								
								<jsp:param value="settlementStartDateString" name="paramName1" />
								<jsp:param value="settlementEndDateString" name="paramName2" />
								<jsp:param value="请输入结算起始日期" name="placeHolder1" />
								<jsp:param value="请输入结算终止日期" name="placeHolder2" />
							</jsp:include>
						</span>
						<span class="item beginend-datepicker">
							<jsp:include page="/WEB-INF/include/plugins/datetimepicker.jsp">
								<jsp:param value="group" name="type" />
								
								
								<jsp:param value="assetRecycleStartDateString" name="paramName1" />
								<jsp:param value="assetRecycleEndDateString" name="paramName2" />
								<jsp:param value="请输入计划还款起始日期" name="placeHolder1" />
								<jsp:param value="请输入计划还款终止日期" name="placeHolder2" />
							</jsp:include>
						</span>
						<span class="item">
							<button id="lookup" class="btn btn-primary">查询</button>
						</span>
					</div>
				</div>
				<div class="table-area">
					<table class="data-list"
						data-action="${ctx}/payment-manage/order/query"
						data-autoload="true">
						<thead>
							<tr>
								<th width="110">结算单号</th>
								<th>还款编号</th>
								<th width="120">
									<a data-paramname="assetRecycleDate" class="sort none">
										计划还款日期<i class="icon"></i>
									</a>
								</th>
								<th width="95">
									<a data-paramname="dueDate" class="sort none">
										结算日期<i class="icon"></i>
									</a>
								</th>
								<th>客户姓名</th>
								<th>
									<a data-paramname="assetInitialValue" class="sort none">
										计划还款金额（元）<i class="icon"></i>
									</a>
								</th>
								<th>
									<!-- <a data-paramname="penaltyAmount" class="sort none">
										<i class="icon"></i>
									</a> -->
									差异罚息
								</th>
								<th>
									<!-- <a data-paramname="numbersOfOverdueDays" class="sort none">
										<i class="icon"></i>
									</a> -->
									差异天数
								</th>
								<th width="100">
									<a data-paramname="modifyTime" class="sort none">
										发生时间<i class="icon"></i>
									</a>
								</th>
								<th>
									<a data-paramname="totalRent" class="sort none">
										结算金额（元）<i class="icon"></i>
									</a>
								</th>
								<th width="60">状态</th>
								<th>备注</th>
								<th width="60">操作</th>
							</tr>
						</thead>
						<tbody>
							<script type="script/template" id="tableFieldTmpl">
								{% var ExecutingSettlingStatus = { CREATE:'已创建', DOING:'处理中', SUCCESS:'成功', FAIL:'失败' }; %}
							    {% _.each(list, function(order, index) { %}
							        <tr>
										<td><a href="${ctx}/payment-manage/order/{%= order.id %}/detail">{%= order.orderNo %}</a></td>
										<td><a href="${ctx}/assets/{%= order.assetSet.id %}/detail">{%= order.singleLoanContractNo %}</a></td>
										<td>{%= new Date(order.assetRecycleDate).format('yyyy-MM-dd') %}</td>
										<td>{%= new Date(order.dueDate).format('yyyy-MM-dd') %}</td>
										<td>{%= order.customer.name %}</td>
										<td>{%=(+ order.assetInitialValue ).formatMoney(2,'')%}</td>
										<td>{%= (+order.penaltyAmount).formatMoney(2,'') %}</td>
										<td>{%= order.numbersOfOverdueDays %}</td>
										<td>{%= new Date(order.modifyTime).format('yyyy-MM-dd') %}</td>
										<td>{%= (+order.totalRent).formatMoney(2,'') %}</td>
										<td>{%= ExecutingSettlingStatus[order.executingSettlingStatus] %}</td>
										<td>{%= order.comment %}</td>
										<td>
											<a href="${ctx}/payment-manage/order/{%= order.id %}/detail">详情</a>
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

	<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>

</body>

</html>
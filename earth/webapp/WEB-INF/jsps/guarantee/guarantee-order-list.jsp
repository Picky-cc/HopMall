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
<title>担保补足 - 五维金融金融管理平台</title>
</head>
<body>
	<%@ include file="/WEB-INF/include/header.jsp"%>
	<div class="web-g-main">
		<%@ include file="/WEB-INF/include/aside.jsp"%>
		<div class="content">
			<div class="scroller">
				<div class="lookup-params">
					<div class="inner clearfix">
						<span class="item" style="width: 127px;"> 
							<select name="financialContractIds" class="form-control real-value selectpicker" multiple data-title="信托合同项目"  data-actions-box="true">
								<c:forEach var="item" items="${financialContracts}">
									<option value="${item.id }" <c:if test="${ (empty guaranteeOrderModel.financialContractIdList) || guaranteeOrderModel.financialContractIdList.contains(item.id) }">selected</c:if>>${item.contractName}(${item.contractNo })</option>
								</c:forEach>
							</select>
						</span>
					
						<span class="item">
							<select name="guaranteeStatus" class="form-control real-value">
								<option value="-1">担保状态</option>
								<c:forEach var="item" items="${GuaranteeStatus}">
									<option value="${item.ordinal() }" <c:if test="${guaranteeOrderModel.guaranteeStatus eq item.ordinal() }">selected</c:if>>
										<fmt:message key="${item.key}" />
									</option>
								</c:forEach>
							</select>
						</span>
						<span class="item">
						<input type="text" class="form-control real-value" placeholder="担保补足单号" name="orderNo" value="${guaranteeOrderModel.orderNo }">
						</span>
						<span class="item beginend-datepicker">
							<jsp:include page="/WEB-INF/include/plugins/datetimepicker.jsp">
								<jsp:param value="group" name="type" />
								
								
								<jsp:param value="startDate" name="paramName1" />
								<jsp:param value="endDate" name="paramName2" />
								<jsp:param value="请输入计划还款起始日期" name="placeHolder1" />
								<jsp:param value="请输入计划还款终止日期" name="placeHolder2" />
								<jsp:param value="${guaranteeOrderModel.startDate}" name="paramValue1"/>
			                	<jsp:param value="${guaranteeOrderModel.endDate}" name="paramValue2"/>
							</jsp:include>
						</span>
						<span class="item beginend-datepicker">
							<jsp:include page="/WEB-INF/include/plugins/datetimepicker.jsp">
								<jsp:param value="group" name="type" />
								
								
								<jsp:param value="dueStartDate" name="paramName1" />
								<jsp:param value="dueEndDate" name="paramName2" />
								<jsp:param value="请输入担保截止起始日期" name="placeHolder1" />
								<jsp:param value="请输入担保截止终止日期" name="placeHolder2" />
								<jsp:param value="${guaranteeOrderModel.dueStartDate}" name="paramValue1"/>
			                	<jsp:param value="${guaranteeOrderModel.dueEndDate}" name="paramValue2"/>
							</jsp:include>
						</span>
						<span class="item">
							<input type="text" class="form-control real-value" placeholder="还款期号" name="singleLoanContractNo" value="${guaranteeOrderModel.singleLoanContractNo }">
						</span>
						<span class="item">
							<input type="text" class="form-control real-value" placeholder="商户编号" name="appId" value="${guaranteeOrderModel.appId }">
						</span>
						<span class="item">
							<button id="lookup" class="btn btn-primary">
								查询
							</button>
						</span>
					</div>
				</div>
				<div class="table-area">
					<table class="data-list" data-action="./order/search" data-autoload="true">
						<thead>
							<tr>
								<th>序号</th>
								<th>担保补足号</th>
								<th>还款编号</th>
								<th>计划还款日期</th>
								<th>担保截止日</th>
								<th>商户编号</th>
								<th>计划还款金额</th>
								<th>差异天数</th>
								<th>发生时间</th>
								<th>担保金额</th>
								<th>担保状态</th>
								<th width="60">操作</th>
							</tr>
						</thead>
						<tbody>
							<script type="script/template" id="tableFieldTmpl">
							    {% _.each(list, function(order, index) { %}
							        <tr data="${order.repaymentBillId }">
							        	<td>{%= index + 1 %}</td>
							        	<td><a href="${ctx}/guarantee/order/{%= order.id %}/guarantee-detail">{%= order.orderNo %}</a></td>
							        	<td>
											<a href="${ctx}/assets/{%= order.assetSet.id %}/detail">{%= order.assetSet.singleLoanContractNo %}</a>
										</td>
							        	<td>{%= new Date(order.assetSet.assetRecycleDate).format('yyyy-MM-dd') %}</td>
							        	<td>{%= new Date(order.dueDate).format('yyyy-MM-dd') %}</td>
							        	<td>{%= order.assetSet.contract.app.appId %}</td>
							        	<td>{%= (+order.assetSet.assetInitialValue).formatMoney(2, '') %}</td>
							        	<td>{%= order.numbersOfGuranteeDueDays %}</td>
							        	<td>{%= order.modifyTime %}</td>
							        	<td>{%= (+order.totalRent).formatMoney(2, '') %}</td>
							        	<td>{%= order.assetSet.guaranteeStatusMsg %}</td>
							        	<td><a href="${ctx}/guarantee/order/{%= order.id %}/guarantee-detail">详情</a></td>
							        </tr>
							    {% }); %}
						    </script>
						</tbody>
					</table>
				</div>
			</div>

			<div class="operations">
				<span class="item">
					<button id="exportExcel" data-action="${ctx}/guarantee/order/export-excel" class="btn btn-default">导出EXCEL</button>
				</span>
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


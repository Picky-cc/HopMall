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
<title>批次管理 - 五维金融金融管理平台</title>

<script type="template/script" id="importAssertTmpl">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close close-dialog" aria-label="关闭" data-dismiss="modal"><span aria-hidden="true">×</span></button>
        <h4 class="modal-title" id="dialoglabel">导入资产包</h4>
      </div>
      <div class="modal-body form-wrapper">
        <form class="form adapt" enctype="multipart/form-data">
          <div class="field-row">
            <label for="" class="field-title require">信托合同</label>
            <div class="field-value">
	            <select id="financialContractNo" name="financialContractNo" class="form-control real-value">
	              	<c:forEach var="item" items="${financialContractList}">
	              		<option value="${item.id}"
	              			<c:if test="${financialContractNo eq item.contractNo}" >selected</c:if>>
	              			${item.contractNo}(${item.app.company.shortName})</option>
	              	</c:forEach>
	            </select>
            </div>
          </div>
          <div class="field-row">
            <label for="" class="field-title require">导入Excel文件</label>
            <div class="field-value">
              <input type="file" name="file" class="form-control real-value">
            </div>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default cancel-dialog" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-success submit">确定</button>
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
					<input type="hidden" name="filter" value="true" class="real-value">
					<span class="item">
						<select name="financialContractUuids" class="form-control real-value selectpicker" multiple data-actions-box="true" data-title="请选择信托合同项目">
							<c:forEach var="item" items="${financialContractList}">
								<option value="${item.financialContractUuid}" selected>${item.contractName}(${item.contractNo})</option>
							</c:forEach>
						</select>
					</span>
					<span class="item beginend-datepicker">
						<jsp:include page="/WEB-INF/include/plugins/datetimepicker.jsp">
							<jsp:param value="group" name="type" />
							
							
							<jsp:param value="startDate" name="paramName1" />
							<jsp:param value="endDate" name="paramName2" />
							<jsp:param value="导入起始时间" name="placeHolder1" />
							<jsp:param value="导入终止时间" name="placeHolder2" />
						</jsp:include>
					</span>
					<span class="item">
						<input type="text" class="form-control real-value" name="loanBatchCode" placeholder="请输入导入批次号">
					</span>
					<span class="item">
						<button id="lookup" class="btn btn-primary">
							查询
						</button>
					</span>
					<sec:authorize ifNotGranted="ROLE_TRUST_OBSERVER">
						<span class="item">
							<a class="pull-right btn btn-success" id="importAssert">
				                <i class="glyphicon glyphicon-plus"></i>导入资产包
				            </a>
						</span>
					</sec:authorize>
				</div>

				<div class="table-area">
					<table class="data-list" data-action="${ctx}/loan-batch/query" data-autoload="true">
						<thead>
							<tr>
								<th>序号</th>
								<th>导入批号</th>
								<th>发生时间</th>
								<th>贷款合同号段</th>
								<th>总金额</th>
								<th>数量</th>
								<th>状态</th>
								<th style="width: 170px">操作</th>
								<th style="width: 60px">详情</th>
							</tr>
						</thead>
						<tbody>
							<script type="script/template" id="tableFieldTmpl">
								{% _.each(list, function(item, index){ %}
									<tr data-id="{%= item.loanBatchId %}" data-code="{%= item.loanBatchCode %}">
										<td>{%= index + 1 %}</td>
										<td>{%= item.loanBatchCode %}</td>
										<td>
											{%= new Date(item.dateTime).format('yyyy-MM-dd') %}
										</td>
										<td>{%= item.loanBatchFromRange %}~{%= item.loanBatchThruRange %}</td>
										<td>
										    {%= (+item.loanBatchTotalAmount).formatMoney(2, '') %}
										</td>
										<td>{%= item.loanBatchTotalContractNum %}</td>
										<td>
											{%= item.available ? '已激活' : '未激活' %}
							            </td>
							            <td>
								            <a href="${ctx}/loan-batch/export-repayment-plan/{%= item.loanBatchId %}" class="export">导出还款计划</a>

								            <sec:authorize ifNotGranted="ROLE_TRUST_OBSERVER">

				            		            {% if(!item.available) { %}
				            		            	<a href="#" class="activate">激活</a>
				            		            {% } %}

				            		            {% if(!item.available) { %}
				            		            	<a href="#" class="delete">删除</a>
				            		            {% } %}

								            </sec:authorize>
							            </td>
							            <td>
							            	<a href="${ctx}/loan-batch/{%= item.loanBatchId %}/loanBatch-detail" 
							            		class="hover-no-text-decoration"
							            		data-target="#"
							                	data-toggle="modal">详情</a>
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
	<script src="${ctx.resource}/js/jquery.form.min.js"></script>
	<script src="${ctx.resource}/js/bootstrap-modal-trigger.js"></script>
	
</body>
</html>

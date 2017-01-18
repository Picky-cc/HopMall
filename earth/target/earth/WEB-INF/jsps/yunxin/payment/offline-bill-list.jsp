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
<title>线下支付单列表 - 五维金融金融管理平台</title>

</head>
<body>
	<%@ include file="/WEB-INF/include/header.jsp"%>

	<div class="web-g-main">
		<%@ include file="/WEB-INF/include/aside.jsp"%>
		<div data-commoncontent='true' class="content content-cashflow">
			<div class="scroller">
				<div class="lookup-params">
					<div class="inner clearfix">
						<span class="item">
							<select	name="executingSettlingStatusInt" class="form-control real-value small">
								<option value="-1">凭证状态</option>
								<c:forEach var="item" items="${journalVoucherStatusList}">
									<option value="${item.ordinal()}"
										<c:if test="${item.ordinal() == jvModel.journalVoucherStatus }">selected</c:if>><fmt:message
											key="${item.key}" /></option>
								</c:forEach>
							</select>
						</span>
						<span class="item has-suffix"> <input type="text" placeholder="银行账号" value="${jvModel.payAccountNo }"
							class="form-control real-value" name="payAccountNo"> <span
							class="glyphicon glyphicon-remove suffix clear-input"></span>
						</span>
						<span class="item has-suffix"> <input type="text" placeholder="账户姓名" value="${jvModel.payAccountName}"
							class="form-control real-value" name="payAccountName">
							<span class="glyphicon glyphicon-remove suffix clear-input"></span>
						</span>
						<span class="item beginend-datepicker"> <jsp:include
								page="/WEB-INF/include/plugins/datetimepicker.jsp">
								<jsp:param value="group" name="type" />
								
								
								<jsp:param value="startTime" name="paramName1" />
								<jsp:param value="endTime" name="paramName2" />
								<jsp:param value="请输入入账起始日期" name="placeholder1" />
								<jsp:param value="请输入入账终止日期" name="placeholder2" />
							</jsp:include>
						</span>
						<span class="item">
							<button id="lookup" class="btn btn-primary">
								<i class="glyphicon glyphicon-search"></i>查询
							</button>
						</span>
						<!--   <div class="pull-right item">
                <a href="../../add?projectId=115" class="pull-right btn btn-default btn-success">
                    <i class="glyphicon glyphicon-plus"></i>新增订单
                </a>
            </div> -->
					</div>
				</div>
				<div class="table-area">
					<table class="data-list"
						data-action="${ctx}/offline-payment-manage/joural-voucher/query"
						data-autoload="true">
						<thead>
							<tr>
								<th width="110">凭证号</th>
								<th>账户姓名</th>
								<th>银行账户号</th>
								<th>支付机构流水号</th>
								<th>制证金额</th>
								<th>入账时间</th>
								<th>凭证状态</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<script type="script/template" id="tableFieldTmpl">
    {% _.each(list, function(jv, index) { %}
        <tr class="record-item">
		  <td>{%= jv.journalVoucherUuid %}</td>
          <td>{%= jv.sourceDocumentCounterPartyName %}</td>
          <td>{%= jv.sourceDocumentCounterPartyAccount %}</td>
          <td>{%= jv.sourceDocumentCashFlowSerialNo %}</td>
          <td>{%= jv.bookingAmount %}</td>
		  <td>{%= new Date(jv.tradeTime).format('yyyy-MM-dd HH:mm:ss') %}</td>
		  <td></td>
		  <td><a href="#" class="hover-no-text-decoration" data-target="#" data-toggle="modal" title="详情">详情</a></td>
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
	            </jsp:include>
			</div>
		</div>

	</div>

	<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>
	<script src="${ctx.resource}/js/bootstrap-modal-trigger.js"></script>
</body>
</html>
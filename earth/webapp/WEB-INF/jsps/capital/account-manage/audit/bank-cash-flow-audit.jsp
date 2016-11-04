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
<title>银行现金流列表 - 五维金融金融管理平台</title>
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
								<select class="form-control real-value" name="hostAccountNo" >
									<option value="">信托项目</option>
									<c:forEach var="item" items="${financialContracts }">
										<option value="${item.capitalAccount.accountNo}">${item.capitalAccount.accountNo}${item.capitalAccount.bankName }(${item.contractName })</option>
									</c:forEach>
								</select>
							</span> 
							<span class="item"> 
								<select class="form-control real-value" name="accountSide" >
									<option value="-1">借贷</option>
                					<c:forEach var="item" items="${accountSideList }">
										<option value="${item.ordinal}"><fmt:message key="${item.key }"/></option>
									</c:forEach>
								</select>
							</span>
							<span class="item">
								<select class="form-control real-value" name="auditStatus" >
									<option value="-1">对账状态</option>
									<c:forEach var="item" items="${auditStatusList }">
										<option value="${item.ordinal}"><fmt:message key="${item.key }"/></option>
									</c:forEach>
								</select>
							</span>
							<span class="item">
								<input type="text" class="form-control large real-value " name="key" placeholder="流水、账户、户名、金额查询、摘要内容">
							</span> 
							<span class="item beginend-datepicker">
								<jsp:include page="/WEB-INF/include/plugins/datetimepicker.jsp">
									<jsp:param value="group" name="type" />
									<jsp:param value="true" name="pickTime"/>
									<jsp:param value="tradeStartTime" name="paramName1" />
									<jsp:param value="tradeEndTime" name="paramName2" />
									<jsp:param value="入账时间起始" name="placeHolder1" />
									<jsp:param value="入账时间终止" name="placeHolder2" />
								</jsp:include>
							</span>
							<span class="item">
								<button id="lookup" type="button" class="btn btn-primary">查询</button>
							</span>
						</div>
					</div>
				</div>
				<div class="table-area">
					<table class="data-list data-list-cashflow" data-action="${ctx}/capital/account-manager/cash-flow-audit/query" data-autoload="true">
						
						<thead>
							<tr>
								<th>流水号</th>
								<th>借贷标志</th>
								<th>交易金额</th>
								<th>瞬时余额</th>
								<th>银行账号</th>
								<th>银行账号名</th>
								<th>入账时间</th>
								<th>摘要</th>
								<th>对账状态</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<script type="script/template" id="tableFieldTmpl">
					            <td>{%= obj.bankSequenceNo %}</td>
								<td>{%= obj.accountSideMsg %}</td>
								<td>{%= obj.transactionAmount %}</td>
								<td>{%= obj.balance %}</td>
								<td>{%= obj.counterAccountNo %}</td>
								<td>{%= obj.counterAccountName %}</td>
								<td>{%= new Date(obj.transactionTime).format('yyyy-MM-dd HH:mm:ss') %}</td>
					            <td>{%= obj.remark %}</td>
								<td>{%= obj.auditStatusMsg %}</td>                                         
					            <td>
									<!-- 
									<button class="btn btn-basic btn-primary btn-recharge">充值</button>
									<a class="expand-bill" href="">
										<i class="icon icon-expand">
										</i>
									</a>
									-->
								</td>
							</script>
						
							<script type="script/template" id="CashBillTmpl">
							  <td colspan="100">
							    <div class="bill-card submited-card match-card">
							      <h3 class="hd">
							  		<span style="color:#666;">存疑金额：</span><span class="color-danger">20000</span>
								  </h3>
							      <div class="bd">
							        <table class="bill-table suspect-table" border="0">
							          	<thead>
								            <tr>
									            <th>充值单号</th>
									            <th>账户编号</th>
									            <th>客户名称</th>
									            <th>客户类型</th>
									            <th>贷款合同编号</th>
									            <th>信托项目名称</th>
									            <th>账户余额</th>
									            <th>充值金额</th>
									            <th>备注</th>
									            <th>状态</th>
									            <th>操作</th>
								            </tr>
							            </thead>
							          	<tbody class="record-list">
						                    
							            </tbody>
							        </table>
							    </div>
							    <div class="ft clearfix hide">
							    	<a class="add-bill" href="">+  新增充值账单</a>
							    </div>
							    </div>
							  </td>
							</script>

							<script type="script/template" id='CashBillItemTmpl'>
								<td>{%= obj.a %}</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td>
									{% if(obj.isRecharge){ %}
									<input type="text" name="" class="">
									{% } %}
								</td>
								<td>
									{% if(obj.isRecharge){ %}
									<input type="text" name="" class="">
									{% } %}
								</td>
								<td class="color-success">
								</td>
								<td>
									{% if(obj.isRecharge){ %}
									<button class="btn btn-basic btn-success">提交</button>
									{% } %}
								</td>
							</script>
						</tbody>
					</table>
				</div>
			</div>
			<div class="operations">
				<button data-action="./export-csv" type="button" class="btn export-excel">导出银行流水</button>
				<i class="icon icon-help" style="margin-left:22px;" data-title="由于业务数据量过大，时间跨度最长为3*24小时整；<br/>若需更多数据，可联系数据维护人员。"></i>
				<jsp:include page="/WEB-INF/include/plugins/assembly.jsp">
		            <jsp:param value="page-control" name="type"/>
		            <jsp:param value="true" name="advanced"/>
	            </jsp:include>
			</div>
		</div>
	</div>
	
	<script type="script/template" id='AddBillDialogTmpl'>
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close close-dialog" aria-label="关闭">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="dialoglabel">新增充值账单</h4>
				</div>
				<div class="modal-body form-wrapper">
					<form class="form adapt" novalidate="novalidate">
						<div class="field-row">
							<label for="" class="field-title">信托项目</label>
							<div class="field-value">
								<select class="form-control middle real-value" name=''>
									<option value="">请选择信托项目</option>
								</select>
	                        </div>
						</div>
						<div class="field-row">
							<label for="" class="field-title">客户类型</label>
							<div class="field-value">
								<select class="form-control middle real-value" name='type'>
									<option value="">请选择客户类型</option>
								</select>
	                        </div>
						</div>
						<div class="field-row">
							<label for="" class="field-title">关键字搜索</label>
							<div class="field-value">
								<input type="text" name="a" class="form-control  real-value" style="width:310px;">
								<div class="extend-form">
									<ul>
										<li><span class="color-danger">YXXXX</span>123456123</li>
										<li><span class="color-danger">YXXXX</span>123456123</li>
										<li><span class="color-danger">YXXXX</span>123456123</li>
									</ul>
								</div>
	                        </div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default close-dialog">关闭</button>
					<button type="button" id="submitbutton" name="submitbutton"
						type="submit" class="btn btn-success submit">提交</button>
				</div>
			</div>
		</div>
	</script>

	<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>
	<script src="${ctx.resource}/js/bootstrap-modal-trigger.js"></script>
	<script>
		/* $(function() {
			$('#dialog').modal();
		}); */
	</script>
</body>
</html>


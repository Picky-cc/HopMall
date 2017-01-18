<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>

<fmt:setBundle basename="ApplicationMessage" />
<fmt:setLocale value="zh-CN" />

<%@ include file="/WEB-INF/include/meta.jsp"%>
<%@ include file="/WEB-INF/include/css-newlayout.jsp"%>
<title>贷款合同详情 - 五维金融金融管理平台</title>

<style>
	.field-row-selectpicker .field-value {
		overflow: auto;
	}

	.field-row-selectpicker .field-value .dropdown-menu .form-control {
		height: 28px;
		width: 100%;
	}

	.field-row-selectpicker .field-value .dropdown-toggle {
		background-color: transparent!important;
		color: #555!important;
	}

	.field-row-selectpicker .field-value .dropdown-menu {
		position: static;
	}
</style>

<script type="template/script" id="terminateContractTmpl">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close close-dialog" aria-label="关闭" data-dismiss="modal"><span aria-hidden="true">×</span></button>
        <h4 class="modal-title" id="dialoglabel">是否确认中止该贷款合同？</h4>
      </div>
      <div class="modal-body form-wrapper">
        <form class="form adapt">
            <div class="field-row" style="padding: 0;">
          		<label for="" class="field-title">贷款合同编号:</label>
	            <div class="form-static">{%= obj.contractNo %}</div>
            </div>
	        <div class="field-row" style="padding: 0;">
	      		<label for="" class="field-title">客户姓名:</label>
	            <div class="form-static">{%= obj.customer %}</div>
	        </div>
	        <div class="field-row" style="padding: 0;">
          		<label for="" class="field-title">合同本金:</label>
	            <div class="form-static">{%= obj.amount %}</div>
            </div>
            <div class="field-row" style="padding: 0;">
          		<label for="" class="field-title">客户账号:</label>
	            <div class="form-static">{%= obj.bank %}</div>
            </div>
            <div class="field-row" style="padding: 0;">
          		<label for="" class="field-title">身份证:</label>
	            <div class="form-static">{%= obj.idCardNum %}</div>
            </div>
          <div class="field-row">
            <label for="" class="field-title require">备注</label>
            <div class="field-value">
              	<textarea name="comment" class="multiline-input form-control real-value" placeholder="请输入中止原因" cols="30" rows="10">{%= obj.comment %}</textarea>
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

<script type="template/script" id="editCustomerInfoTmpl">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close close-dialog" aria-label="关闭" data-dismiss="modal"><span aria-hidden="true">×</span></button>
        <h4 class="modal-title" id="dialoglabel">修改还款信息</h4>
      </div>
      <div class="modal-body form-wrapper">
        <form class="form adapt">
            <div class="field-row" style="padding: 0;">
          		<label for="" class="field-title">客户编号:</label>
	            <div class="form-static">{%= obj.customerSource %}</div>
            </div>
	        <div class="field-row" style="padding: 0;">
	      		<label for="" class="field-title">姓名:</label>
	            <div class="form-static">{%= obj.customer %}</div>
	        </div>
	        <div class="field-row" style="padding: 0;">
	      		<label for="" class="field-title">身份证:</label>
	            <div class="form-static">{%= obj.idCardNum %}</div>
	        </div>
	        <hr style="margin: 5px 0;border-style: dotted;">
	        <div class="field-row">
                <label for="" class="field-title">银行账号</label>
                <div class="field-value">
	                <input class="form-control real-value" name="bankAccount" >
                </div>
            </div>
	        <div class="field-row field-row-selectpicker">
                <label for="" class="field-title">开户行</label>
                <div class="field-value">
	                <select name="bankCode"  data-live-search="true" data-size="4" class="selectpicker">
						<option value="">请选择</option>
	                	<c:forEach var="coreBank" items="${coreBanks}" varStatus="status">
						     <option value="${coreBank.key}">${coreBank.value}</option>
						</c:forEach>
	                </select>
                </div>
            </div>
            <div class="field-row">
                <label for="" class="field-title">开户地</label>
                <div class="field-value">
	                <jsp:include page="/WEB-INF/include/plugins/assembly.jsp">
                    	<jsp:param value="area-select" name="type"/>
                    	<jsp:param value="city" name="minRank"/>
                    </jsp:include>
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

<script type="template/script" id='orderDetailTmpl'>
	<tr class="bill-card-box" style="background-color: #f5f5f5;">
	  <td colspan="100">
	    <div class="bill-card">
	      <h3 class="hd">
	        订单明细
	      </h3>
	      <div class="bd">
	        <table class="bill-table" border="0">
	          <thead>
	            <tr style= "background-color: #f5f5f5">
	                <th width="110">放款优先级</th>
	                <th>放款编号</th>
	                <th>计划执行日期</th>
	                <th>执行金额</th>
	                <th>付款方账户名</th>
	                <th>收款方账号名</th>
	                <th>执行状态</th>
	                <th>备注</th>
	            </tr>
	          </thead>
	          <tbody class="record-list">
	          		{% _.each(remittancePlans, function(item, index){ %}
		          		<tr class="record-item">
		          		    <td>{%= item.priorityLevel %}</td>
		          		    <td><a href="${ctx}/remittance/plan/details/{%= item.remittancePlanUuid %}">{%= item.remittancePlanUuid %}</a></td>
		          		    <td>{%= new Date(item.plannedPaymentDate).format('yyyy-MM-dd') %}</td>
		          		    <td>{%= (+item.plannedTotalAmount).formatMoney(2, '') %}</td>
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
		          		    <td>{%= item.executionStatusMsg %}</td>
		          		    <td>{%= item.executionRemark %}</td>
		          		</tr>
	          		{% }) %}
	          </tbody>
	        </table>
	      </div>
	    </div>
	  </td>
	</tr>
</script>

</head>
<body>

	<%@ include file="/WEB-INF/include/header.jsp"%>

	<div class="web-g-main">

		<%@ include file="/WEB-INF/include/aside.jsp"%>

		<div class="content">
			<div class="scroller">
				<div class="position-map">
					<div class="pull-left">
			            当前位置:
			            <span class="item current">贷款合同详情</span>
					</div>
				</div>
				
				<input type="hidden" value="${contract.id}" name="contractId">
				
				<div class="col-layout-detail">
					<div class="top">
						<div class="block">
							<h5 class="hd">贷款合同信息</h5>
							<div class="bd">
								<div class="col">
									<p>合同编号 ：<span class="contractNo">${contract.contractNo}</span></p>
									<p>贷款方式 ：
										<c:if test="${not empty contract.repaymentWay.key}">
											<fmt:message key="${contract.repaymentWay.key}" />
										</c:if>
									</p>
									<p>贷款利率 ：<fmt:formatNumber
												value="${contract.interestRate}" type="percent"
												maxFractionDigits="2" /></p>
									<p>罚息利率 ：<fmt:formatNumber
												value="${contract.penaltyInterest}" type="percent"
												maxFractionDigits="3" /></p>
									<p>合同状态 ：
									<c:if test="${not empty contract.state.key}">
										<fmt:message key="${contract.state.key}" />
									</c:if>
									<sec:authorize ifNotGranted="ROLE_TRUST_OBSERVER">
										<c:if test="${not empty canInvalidate && canInvalidate}">
											<a href="#" class="operate" id="terminateContract">合同终止</a>
										</c:if>
									</sec:authorize>
									</p>
								</div>
							</div>
						</div>
						<div class="block">
							<h5 class="hd">资产信息</h5>
							<div class="bd">
								<div class="col">
									<p>资产编号 ：${contract.house.address}</p>
									<p>生效日期 ：<fmt:formatDate value="${contract.beginDate}" pattern="yyyy/MM/dd" /></p>
									<p>还款期数 ：${contract.periods}</p>
									<p>本金总额 ：<span class="amount">${contract.totalAmount}</span></p>
								</div>
							</div>
						</div>
						<div class="block">
							<h5 class="hd">商户信息</h5>
							<div class="bd">
								<div class="col">
									<p>名称 ：${contract.app.name}</p>
									<p>编号 ：${contract.app.appId}</p>
									<p>信托编号 ：${financialContract.contractNo}</p>
								</div>
							</div>
						</div>
						<div class="block">
							<h5 class="hd">客户信息
								<sec:authorize ifNotGranted="ROLE_TRUST_OBSERVER">
									<a href="${ctx}/modifyContractAccount/repaymentInfo/query" class="edit-customer-info">[编辑]</a>
								</sec:authorize>
							</h5>
							<div class="bd">
								<div class="col">
									<p>姓名 ：<span class="customer">${contract.customer.name}</span></p>
									<p>编号 ：<span class="customer-source">${contract.customer.source}</span></p>
									<p>身份证 ：<span class="idcard-num">${contractAccount.idCardNum}</span></p>
									<p>银行账户 ：<span class="bank">${contractAccount.payAcNo}</span></p>
									<p>账户开户行 ：${contractAccount.bank}</p>
									<p>开户行所在地 ：${contractAccount.province}&nbsp;${contractAccount.city}</p>
								</div>
							</div>
						</div>
					</div>
					<%-- <div class="btm">
						<span class="block">已还本金 ：<span class="color-success"><fmt:formatNumber type='number' pattern='#,#0.00#' value='${fields.returnedPrincipal}' /></span></span>
						<span class="block">未还本金 ：<span class="color-danger"><fmt:formatNumber type='number' pattern='#,#0.00#' value='${fields.planningPrincipal}' /></span></span>
						<span class="block">已还利息 ：<span class="color-success"><fmt:formatNumber type='number' pattern='#,#0.00#' value='${fields.returnedInterest}' /></span></span>
						<span class="block">未还利息 ：<span class="color-danger"><fmt:formatNumber type='number' pattern='#,#0.00#' value='${fields.planningInterest}' /></span></span>
						<span class="block">已还期数 ：<span class="color-success">${fields.returnedPeriod}</span></span>
						<span class="block">未还期数 ：<span class="color-danger">${fields.planningPeriod}</span></span>
					</div> --%>
				</div>

				<div class="table-layout-detail">
					<div class="block">
						<h5 class="hd">合同状态：</h5>
						<div class="bd">
							<table>
								<thead>
									<tr>
										<th width="50%">还款总额:
											<span>${fields.returnedPrincipalAndInterest+fields.planningPrincipalAndInterest}&nbsp;&nbsp;&nbsp;共(${fields.returnedPeriod + fields.planningPeriod})期</span>
										</th>
										<th width="50%">放款总额: 
											<span><fmt:formatNumber type='number' pattern='#0.00' value='${raTotalAmount}'/></span>
										</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td height="200px">
											<div class="progress-status">
												<div>
													<span class="color-success">已还：
														<fmt:formatNumber value="${fields.returnedPrincipalAndInterest}" type="number" pattern="#0.00" /></span> |
													<span>未还：
														<fmt:formatNumber value="${fields.planningPrincipalAndInterest}" type="number" pattern="#0.00" /></span>
												</div>
												<div class="progress">
													<c:set var="sumPrincipalAndInterest">
														<fmt:formatNumber type='number' pattern='#0' value='${(fields.returnedPrincipalAndInterest + fields.planningPrincipalAndInterest)}'/>
													</c:set>
													<c:set var="percentPrincipalAndInterest">
														<c:if test="${sumPrincipalAndInterest != '0'}">
															${100 * fields.returnedPrincipalAndInterest / (fields.returnedPrincipalAndInterest + fields.planningPrincipalAndInterest)}
														</c:if>
													</c:set>
												  	<div class="progress-bar" role="progressbar" style="width: ${percentPrincipalAndInterest}%;">
												    	<span class="sr-only">${percentPrincipalAndInterest}%</span>
												  	</div>
												  	
												</div>
											</div>
											<div class="progress-status">
												<div>
													<span class="color-success">已还：${fields.returnedPeriod} 期</span> | 
													<span >未还：${fields.planningPeriod} 期</span>
												</div>
												<div class="progress">
													<c:set var="sumPeriod">
														<fmt:formatNumber type='number' pattern='#0' value='${(fields.returnedPeriod + fields.planningPeriod)}'/>
													</c:set>
													<c:set var="percentPeriod">
														<c:if test="${sumPeriod != '0'}">
															${100 * fields.returnedPeriod / (fields.returnedPeriod + fields.planningPeriod)}
														</c:if>
													</c:set>
												  	<div class="progress-bar" role="progressbar" style="width: ${percentPeriod}%">
												    	<span class="sr-only">${percentPeriod}%</span>
												  	</div>
												</div>
											</div>
										</td>
										<td>
											<div class="progress-status">
												<div>
													<span class="color-success">已放：
														<fmt:formatNumber value="${raPaidAmount}" type="number" pattern="#0.00" /></span> |
													<span >未放：
														<fmt:formatNumber value="${raNotPaidAmount}" type="number" pattern="#0.00" /></span>
												</div>
												<div class="progress">
													<c:set var="sumRaPaidAmount">
														<fmt:formatNumber type='number' pattern='#0' value='${(raPaidAmount + raNotPaidAmount)}'/>
													</c:set>
													<c:set var="percentRaPaidAmount">
														<c:if test="${sumRaPaidAmount != '0'}">
															${100 * raPaidAmount / (raPaidAmount + raNotPaidAmount)}
														</c:if>
													</c:set>
													<div class="progress-bar" role="progressbar" style="width: ${percentRaPaidAmount}%;">
														<span class="sr-only">${percentRaPaidAmount}%</span>
													</div>
												</div>
											</div>
											<div class="progress-status" style="visibility: hidden;">
												<div>
													<span class="color-success">已放：0</span> | 
													<span >未放：0</span>
												</div>
												<div class="progress">
												  <div class="progress-bar" role="progressbar">
												    <span class="sr-only">0%</span>
												  </div>
												</div>
											</div>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>

				<div class="table-layout-detail tabs">
					<ul class="nav nav-tabs tab-menu">
					  <li data-target="repay" class="tab-menu-item tab-menu-item-repay"><a href="#">还款</a></li>
					  <li data-target="release" class="tab-menu-item tab-menu-item-release"><a href="#">放款</a></li>
					</ul>
					<div class="tab-content" style="margin-top: 10px;">
						<div class="tab-content-item tab-content-item-repay">
							<div class="block">
								<h5 class="hd">还款计划
									<!-- <select class="form-control select-block">
										<option>版本5（执行中）</option>
									</select>
									<button class="btn btn-primary">编辑</button> -->
								</h5>
								<div class="bd">
									<table>
										<thead>
											<tr>
												<th>还款编号</th>
												<th>计划还款日期</th>
												<th>计划还款本金</th>
												<th>计划还款利息</th>
												<th>其他费用</th>
												<th>当期应还总额</th>
												<th>还款状态</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody>
											<c:choose>
												<c:when test="${not empty assetSetList}">
													<c:forEach var="assetSet" items="${assetSetList}"
														varStatus="status">
														<tr>
															<td>${assetSet.singleLoanContractNo}</td>
															<%-- <td <c:if test="${assetSet.id eq currentAssetSet.id}">class="current-order" </c:if> >
																${assetSet.singleLoanContractNo}
																<c:if test="${assetSet.id eq currentAssetSet.id}">
																	<span style="color: #ff961e;">当期</span>
																</c:if>
															</td> --%>
															<td>${assetSet.assetRecycleDate}</td>
															<%-- <td>${assetSet.currentPeriod}</td> --%>
															<td><fmt:formatNumber value="${assetSet.assetPrincipalValue}" type="number" pattern="#0.00" /></td>
															<td><fmt:formatNumber value="${assetSet.assetInterestValue}" type="number" pattern="#0.00" /></td>
															<%-- <td>${assetSet.overDueDays}</td> --%>
															<td>
																0.00
															</td>
															<td><fmt:formatNumber value="${assetSet.assetInitialValue}" type="number" pattern="#0.00" /></td>
															<c:choose>
															    <c:when test="${assetSet.paymentStatus.ordinal == 2}">
															        <td><span class="color-warning"><fmt:message key="${assetSet.paymentStatus.key}"></fmt:message></span></td>
															    </c:when>
															    <c:otherwise>
															        <td><fmt:message key="${assetSet.paymentStatus.key}"></fmt:message></td>
															    </c:otherwise>
															</c:choose>
															<td><a href="${ctx}/assets/${assetSet.id}/detail" class="color-primary">详情</a></td>
														</tr>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr>
														<td colspan="8" align="center">
															没有更多数据
														</td>
													</tr>
												</c:otherwise>
											</c:choose>
										</tbody>
									</table>
								</div>
								<div class="ft clearfix">
									<div class="pull-left hide">
										<span>张三</span>  编辑于
										<span style="margin-right:30px;">2016-12-01 15.22</span>  备注:
										<span>客户计划有变</span>
									</div>
						        	<div class="pull-right">
						            	<span class="summary">
						           		  	合计:
						           		  	<span>
						           		  		<fmt:formatNumber type="number" pattern="#0.00" value="${empty totalAssetPrincipalValue ? '0' : totalAssetPrincipalValue}"></fmt:formatNumber>
						           		  	</span>（本金）+
						           		  	<span>
						           		  		<fmt:formatNumber type="number" pattern="#0.00" value="${empty totalAssetInterestValue ? '0' : totalAssetInterestValue}"></fmt:formatNumber>
						           		  	</span>（利息）+
						           		  	<span>
						           		  		<fmt:formatNumber type="number" pattern="#0.00" value="${empty totalOtherFee ? '0' : totalOtherFee}"></fmt:formatNumber>
						           		  	</span> （其他费用）=
						           		  	<span class="total-amount">
						           		  		<fmt:formatNumber type="number" pattern="#0.00" value="${empty total ? '0' : total}"></fmt:formatNumber>
						           		  	</span>
						            	</span>
						          	</div>
						      	</div>
							</div>
						<%-- 
							<div class="block">
								<h5 class="hd">编辑还款计划
									<select class="form-control select-block">
										<option>版本5（执行中）</option>
									</select>
								</h5>
								<div class="bd">
									<table>
										<thead>
											<tr>
												<th>计划还款日期</th>
												<th>计划还款本金</th>
												<th>计划还款利息</th>
												<th>其他费用</th>
												<th>当期应还总额</th>
												<th>还款状态</th>
												<th width="60">操作</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>2016</td>
												<td>1</td>
												<td>1</td>
												<td>1</td>
												<td>1</td>
												<td>1</td>
												<td><a class="color-danger" href="">删除</a></td>
											</tr>
											<tr>
												<td>
													<span class="item datetimepick-wrapper">
											            <jsp:include page="/WEB-INF/include/plugins/datetimepicker.jsp">
											               	<jsp:param value="single" name="type"/>
											                <jsp:param value="true" name="calendarbtn"/>
											                <jsp:param value="false" name="clearbtn"/>
											                <jsp:param value="effectiveTime" name="paramName1"/>
											              </jsp:include>
										            </span>
												</td>
												<td>
													<input class="form-control " type="" name="">
												</td>
												<td>
													<input class="form-control" type="" name="">
												</td>
												<td>
													<input class="form-control" type="" name="">
												</td>
												<td>
													<input class="form-control" type="" name="">
												</td>
												<td></td>
												<td><a class="color-danger" href="">删除</a></td>
											</tr>
										</tbody>
									</table>
								</div>
								<div class="ft btn-add">
									<span>+  新增</span>
								</div>
								<div class="ft">
									<div class="pull-left">
										<span>备注：</span>
										<input class="form-control" type="" name="" style="width:300px;display: inline-block;">
									</div>	
						        	<div class="pull-right">
						            	<span class="summary">
						           		  	合计:
						           		  	<span>20000.00</span>（本金）+
						           		  	<span>100.00</span>（利息）+
						           		  	<span>0.00</span> （其他费用）=
						           		  	<span class="total-amount">10112.00</span>
						            	</span>
						            	<button class="btn btn-success">提交</button>
						            	<button class="btn btn-default">取消</button>
						          	</div>
						      	</div>
							</div>
						--%>
							<div class="block" style="display: none;">
								<h5 class="hd">还款记录</h5>
								<div class="bd">
									<table>
										<thead>
											<tr>
												<th>还款编号</th>
												<th>计划还款日期</th>
												<th>实际还款日期</th>
												<th>计划还款总额</th>
												<th>实际还款金额</th>
												<th>罚息</th>
												<th>还款类型</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody>
											
										</tbody>
									</table>
								</div>
								<div class="ft">
						        	<div class="pull-right">
						            	<span class="summary">
						           		  	合计:
						           		  	<span>20000.00</span>（本金）+
						           		  	<span>100.00</span>（利息）+
						           		  	<span>0.00</span> （其他费用）=
						           		  	<span class="total-amount">10112.00</span>
						            	</span>
						          	</div>
						      	</div>
							</div>
						</div>
						<div class="tab-content-item tab-content-item-release">
							<div class="block">
								<h5 class="hd">放款记录</h5>
								<div class="bd">
									<table>
										<thead>
											<tr>
												<th>订单编号</th>
												<th>计划放款金额</th>
												<th>放款策略类型</th>
												<th>受理时间</th>
												<th>审核人</th>
												<th>审核时间</th>
												<th>订单状态</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody>
											<c:choose>
												<c:when test="${not empty remittanceApplications}">
													<c:forEach var="item" items="${remittanceApplications}" varStatus="status">
														<tr data-remittance-application-uuid='${item.remittanceApplicationUuid}' 
															class="item-release" 
															style="background-color: #fff;">
															<td><a href="${ctx}/remittance/application/details/${item.remittanceApplicationUuid}">${item.remittanceApplicationUuid}</a></td>
															<td>
																<fmt:formatNumber type="number" pattern="#0.00" value="${empty item.plannedTotalAmount ? '0' : item.plannedTotalAmount}"></fmt:formatNumber>
															</td>
															<td>
																<fmt:message key="${item.remittanceStrategy.key}"></fmt:message>
															</td>
															<td>${item.createTime}</td>
															<td>${item.auditorName}</td>
															<td>${item.auditTime}</td>
															<td>
																<fmt:message key="${item.executionStatus.key}"></fmt:message>
															</td>
															<td>
																订单明细
																<span class="expand">
																	<i class="icon icon-expand"></i>
																</span>
															</td>
														</tr>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr>
														<td colspan="8" align="center">
															没有更多数据
														</td>
													</tr>
												</c:otherwise>
											</c:choose>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>

					<div class="block">
						<jsp:include page="/WEB-INF/include/system-operate-log.jsp">
				            <jsp:param value="${not empty contract ? contract.uuid : ''}" name="objectUuid"/>
			            </jsp:include>
					</div>
				</div>	

			</div>
		</div>
	</div>

	<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>
</body>
</html>
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
<title>放款单详情 - 五维金融金融管理平台</title>
<script type="template/script" id="resendRemittancePlanTmpl">
  	<div class="modal-dialog">
	    <div class="modal-content">
		    <div class="modal-header">
		        <button type="button" class="close close-dialog" aria-label="关闭" data-dismiss="modal">
		        	<span aria-hidden="true">×</span>
		        </button>
		        <h4 class="modal-title" id="dialoglabel">操作成功</h4>
		    </div>
	      	<div class="modal-body" >
	      		<div>放款单，重新执行成功。</div>
	      	</div>
	      	<div class="modal-footer">
		        <button type="button" class="btn btn-success submit">确定</button>
	      	</div>
	    </div>
  	</div>
</script>
<script type="template/script" id="modifyNoteTmpl">
  	<div class="modal-dialog">
	    <div class="modal-content">
	     	 <div class="modal-header">
		        <button type="button" class="close close-dialog" aria-label="关闭" data-dismiss="modal">
		        	<span aria-hidden="true">×</span>
		        </button>
	        	<h4 class="modal-title" id="dialoglabel">修改备注</h4>
	      	</div>
	      	<div class="modal-body form-wrapper">
		        <form class="form adapt" enctype="multipart/form-data">
		          	<div class="field-row">
		           		<label for="" class="field-title require">备注</label>
			            <div class="field-value">
			              	<textarea  name="" class="form-control real-value" placeholder="请输入备注(50字以内)" cols="30" rows="10"></textarea>
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

<script type="template/script" id="channelLoanTmpl">
  	<div class="modal-dialog">
	    <div class="modal-content">
		    <div class="modal-header">
		        <button type="button" class="close close-dialog" aria-label="关闭" data-dismiss="modal">
		        	<span aria-hidden="true">×</span>
		        </button>
		        <h4 class="modal-title" id="dialoglabel">通道打款</h4>
		    </div>
	      	<div class="modal-body form-wrapper">
		        <form class="form adapt" enctype="multipart/form-data">
		          	<div class="field-row">
			            <div class="field-value">
			             	<select id="" name="" class="form-control real-value">
			             		<option value="-1">请选择通道</option>
			             	</select>
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
				<div class="position-map">
					<div class="pull-left">
			            当前位置:
			            <span class="item current">放款单详情</span>
					</div>
					<div class="pull-right">
						<a href="#" class="btn btn-primary" id="modifyNote">修改备注</a>
						<a href="#" class="btn btn-primary hide" id="channelLoan" style="margin-left:10px">通道打款</a>
						<button type="button" onclick="window.history.go(-1);" class="btn btn-default">&lt;&lt; 返回</button>
					</div>
				</div>

				<div class="col-layout-detail">
					<div class="top">
						<div class="block">
							<h5 class="hd">贷款信息</h5>
							<div class="bd">
								<div class="col">
									<p>贷款合同编号：
										<a href="${ctx}/contracts/detail?uid=${remittancePlan.contractUniqueId}">
										${remittancePlan.contractNo}</a>
									</p>
									<p>计划单号：
										<a href="${ctx}/remittance/application/details/${remittancePlan.remittanceApplicationUuid}">${remittancePlan.remittanceApplicationUuid}</a>
									</p>
									<p>审核人：${remittanceApplication.auditorName }</p>
									<p>审核时间：<fmt:formatDate value="${remittanceApplication.auditTime}" pattern="yyyy-MM-dd"/></p>
									<p>来源单号：
										${remittancePlan.businessRecordNo}
									</p>
								</div>
							</div>
						</div>
						<div class="block">
							<h5 class="hd">放款单信息</h5>
							<div class="bd">
								<div class="col">
									<p>放款编号：${remittancePlan.remittancePlanUuid}</p>
									<p>计划执行金额：
										<fmt:formatNumber type="number" pattern="#,##0.00#" value="${remittancePlan.plannedTotalAmount}"/>
									</p>
									<p>计划执行日期：
										<fmt:formatDate value="${remittancePlan.plannedPaymentDate}" pattern="yyyy-MM-dd"/>
									</p>
									<p>状态变更时间：
										<fmt:formatDate value="${remittancePlan.lastModifiedTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
									</p>
									<p>执行状态: <fmt:message key='${remittancePlan.executionStatus.key}'/></p>
									<p>备注:${remittancePlan.executionRemark}</p>
									<p>放款类型:${remittancePlan.transactionType.ordinal == 0 ? '代付' : '代收'}</p>
								</div>
							</div>
						</div>
						<div class="block">
							<h5 class="hd">付款方账户信息</h5>
							<div class="bd">
								<div class="col">
									<p>付款方账户名：${remittancePlan.pgAccountInfo.accountName}</p>
									<p>付款方账户号：${remittancePlan.pgAccountInfo.accountNo}</p>
									<p>账户开户行：${remittancePlan.pgAccountInfo.bankName}</p>
									<p>开户行所在地：${remittancePlan.pgAccountInfo.province} &nbsp;${remittancePlan.pgAccountInfo.city}</p>
									<p>银行编号：${remittancePlan.pgAccountInfo.bankCode}</p>
								</div>
							</div>
						</div>
						<div class="block">
							<h5 class="hd">收款方账户信息</h5>
							<div class="bd">
								<div class="col">
									<p>收款方账户名：${remittancePlan.cpAccountInfo.accountName}</p>
									<p>收款方账户号：${remittancePlan.cpAccountInfo.accountNo}</p>
									<p>账户开户行：${remittancePlan.cpAccountInfo.bankName}</p>
									<p>开户行所在地：${remittancePlan.cpAccountInfo.province} &nbsp;${remittancePlan.cpAccountInfo.city}</p>
									<p>银行编号：${remittancePlan.cpAccountInfo.bankCode}</p>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="table-layout-detail">
					<div class="block">
						<div>
							<div class="pull-left">
								<h5 class="hd">线上代付单列表</h5>
							</div>
							<sec:authorize ifNotGranted="ROLE_TRUST_OBSERVER">
								<c:if test="${remittancePlan.executionStatus.ordinal() == 3 && remittanceApplication.executionStatus == 'ABNORMAL'}">
									<div class="pull-right">
										<button type="button" data-remittancePlanUuid="${remittancePlan.remittancePlanUuid}" class="btn btn-primary" id="resendRemittancePlan">重新执行</button>
									</div>
								</c:if>
							</sec:authorize>
						</div>
						<div class="bd">
							<table class="data-list" data-action="" data-autoload="true">
								<thead>
									<tr>
										<th>代付单号</th>
										<th>付款方账户名</th>
										<th>收款方账户名</th>
										<th>发生金额</th>
										<th>执行通道</th>
										<th>状态变更时间</th>
										<th>执行状态</th>
										<th>冲账状态</th>
										<th>备注</th>
									</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${not empty remittancePlanExecLogs}">
											<c:forEach var="item" items="${remittancePlanExecLogs}">
												<tr>
													<td><a href="${ctx}/capital/plan/execlog/details/${item.execReqNo}">${item.execReqNo}</a></td>
													<td>
														<span   class="showPopover"
																data-container="body"
																data-placement="top"
																data-html="true"
																data-trigger="focus" 
																data-toggle="popover">
															${item.pgAccountInfo.accountName}
														</span>
														<div class="hide account">
															<div>
																<span class='text-muted'>账户名:</span>${item.pgAccountInfo.accountName}
																<br/>
																<span class='text-muted'>账户号:</span>${item.pgAccountInfo.accountNo}
																<br/>
																<span class='text-muted'>开户行:</span>${item.pgAccountInfo.bankName}
																<br/>
																<span class='text-muted'>所在地:</span>${item.pgAccountInfo.province}&nbsp;${item.pgAccountInfo.city}
																<br/>
																<span class='text-muted'>证件号:</span>${item.pgAccountInfo.idNumber}
																<br/>
															</div>
														</div>
													</td>
													<td>
														<span   class="showPopover"
																data-container="body"
																data-placement="top"
																data-html="true"
																data-trigger="focus" 
																data-toggle="popover">
															${item.cpAccountInfo.accountName}
														</span>
														<div class="hide account">
															<div>
																<span class='text-muted'>账户名:</span>${item.cpAccountInfo.accountName}
																<br/>
																<span class='text-muted'>账户号:</span>${item.cpAccountInfo.accountNo}
																<br/>
																<span class='text-muted'>开户行:</span>${item.cpAccountInfo.bankName}
																<br/>
																<span class='text-muted'>所在地:</span>${item.cpAccountInfo.province}&nbsp;${item.cpAccountInfo.city}
																<br/>
																<span class='text-muted'>证件号:</span>${item.cpAccountInfo.idNumber}
																<br/>
															</div>
														</div>
													</td>
													<td>
														<fmt:formatNumber type="number" pattern="#,##0.00#" value="${item.plannedAmount}"/>
													</td>
													<td><fmt:message key='${item.paymentGateway.key}'/></td>
													<td><fmt:formatDate value="${item.lastModifiedTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
													<td>
														<c:choose>
															<c:when test="${item.executionStatus.ordinal() eq 3}">
																<span class="color-danger"><fmt:message key='${item.executionStatus.key}'/></span>  
															</c:when>
															<c:when test="${item.executionStatus.ordinal() eq 4}">
																<span class="color-warning"><fmt:message key='${item.executionStatus.key}'/></span>  
															</c:when>
															<c:otherwise>
																<span class=""><fmt:message key='${item.executionStatus.key}'/></span>  
															</c:otherwise>
														</c:choose>
													</td>
													<td><fmt:message key='${item.reverseStatus.key}'/></td>
													<td>${item.executionRemark}</td>
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

				<div class="table-layout-detail" style="display: none;">
					<div class="block">
						<h5 class="hd">代付撤销单</h5>
						<div class="bd">
							<table class="data-list" data-action="" data-autoload="true">
								<thead>
									<tr>
										<th>通道流水号</th>
										<th>通道名称</th>
										<th>发生时间</th>
										<th>退回账户</th>
										<th>交易类型</th>
										<th>金额</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>2334SHD99DD33</td>
										<td>银联代付</td>
										<td>2016-07-13 16:13:30.0</td>
										<td>622848033333333333</td>
										<td>冲账</td>
										<td>3000</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>

				<div class="table-layout-detail">
					<div class="block">
						<jsp:include page="/WEB-INF/include/system-operate-log.jsp">
				            <jsp:param value="${remittancePlan.remittancePlanUuid}" name="objectUuid"/>
			            </jsp:include>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>
</body>
</html>
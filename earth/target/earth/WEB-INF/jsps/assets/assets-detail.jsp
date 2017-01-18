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
<title>还款单详情 - 五维金融金融管理平台</title>

<script type="template/script" id="modifyOverdueStatusTmpl">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close close-dialog" aria-label="关闭" data-dismiss="modal"><span aria-hidden="true">×</span></button>
        <h4 class="modal-title" id="dialoglabel">修改逾期状态</h4>
      </div>
      <div class="modal-body form-wrapper">
        <form class="form adapt" enctype="multipart/form-data">
          <div class="field-row">
            <label for="" class="field-title require">修改逾期状态</label>
            <div class="field-value">
	            <select name="status" class="form-control real-value">
	              	<c:forEach var="item" items="${overdueStatusList}">
	              		<option value="${item.ordinal}"><fmt:message key="${item.key}"/></option>
	              	</c:forEach>
	            </select>
            </div>
          </div>
          <div class="field-row field-overdue-date hide">
            <label for="" class="field-title require">逾期起始日期</label>
            <div class="field-value">
		        <div 
		        	class="imitate-datetimepicker-input date input-group starttime-datepicker pull-left input-append emitbyclick" 
		            data-pick-time='false'
		            data-end-date='{%= obj.actualRecycleDate %}'>
		            <input 
		                class="form-control datetimepicker-form-control real-value" 
		                value="" 
		                placeholder="时间选择"
		                name="overdueDate"
		                size="16" 
		                type="text" 
		                readonly
		            >
		            <span class="add-on"><i></i></span>
		            <span class="input-group-addon">
		              <i class="glyphicon"></i>
		            </span>
		        </div>
            </div>
          </div>
          <div class="field-row">
            <label for="" class="field-title require">修改原因</label>
            <div class="field-value">
            	<select name="selectReason" class="form-control">
            		<option value="余额不足">余额不足</option>
        	  		<option value="">其他</option>
            	</select>
              	<textarea style="margin: 10px 0 0;" name="textareaReason" class="multiline-input form-control hide" placeholder="请输入原因（30个字以内）" cols="30" rows="10"></textarea>
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

<script type="template/script" id="modifyRemarkTmpl">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close close-dialog" aria-label="关闭" data-dismiss="modal"><span aria-hidden="true">×</span></button>
        <h4 class="modal-title" id="dialoglabel">修改备注</h4>
      </div>
      <div class="modal-body form-wrapper">
        <form class="form adapt">
          <div class="field-row">
            <label for="" class="field-title require">备注</label>
            <div class="field-value">
              	<textarea name="comment" class="multiline-input form-control real-value" placeholder="请输入原因（50个字以内）" cols="30" rows="10">{%= obj.comment %}</textarea>
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

<script type="template/script" id="refundTmpl">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close close-dialog" aria-label="关闭" data-dismiss="modal"><span aria-hidden="true">×</span></button>
        <h4 class="modal-title" id="dialoglabel">退款</h4>
      </div>
      <div class="modal-body form-wrapper">
        <form class="form adapt">
          <div class="field-row">
            <label for="" class="field-title require">退款金额</label>
            <div class="field-value small">
              	<input name="refundAmount" class="form-control real-value" placeholder="{%= obj.refundAmount %}" /> <span class="color-dim">元</span>
	        </div>
          </div>
          <div class="field-row">
            <label for="" class="field-title require">备注</label>
            <div class="field-value">
              	<textarea name="comment" class="multiline-input form-control real-value" placeholder="请输入原因（30个字以内）" cols="30" rows="10"></textarea>
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
			<input type="hidden" name="overdueStatus" value="${assetSet.overdueStatus.ordinal}">
			<div class="scroller">
				<div class="position-map">
					<div class="pull-left">
			            当前位置:
			            <span class="item current">还款单详情</span>
					</div>
				</div>
				
				<div class="col-layout-detail">
					<div class="top">
						<div class="block" style="width: 23%">
							<h5 class="hd">贷款信息</h5>
							<div class="bd">
								<div class="col">
									<p>合同编号 ：${assetSet.contractNo }</p>
									<p>贷款客户编号 ：${assetSet.customerNo }</p>
									<p>资产编号 ：${assetSet.assetNo }</p>
									<p>还款编号 ：${assetSet.singleLoanContractNo }</p>
								</div>
							</div>
						</div>
						<div class="block">
							<h5 class="hd">还款信息</h5>
							<div class="bd">
								<div class="col">
									<p>计划还款日期 ：<span class="asset-recycle-date">
                                                        <fmt:formatDate value="${assetSet.assetRecycleDate }" pattern="yyyy-MM-dd" />
                                                    </span></p>
									<p>差异天数 ：${assetSet.overDueDays }</p>
									<p>逾期天数 ：<span class="color-danger">${assetSet.auditOverdueDays }</span></p>
									<p>还款日期 ：<span class="actual-recycle-date">
                                                    <fmt:formatDate value="${assetSet.actualRecycleDate }" pattern="yyyy-MM-dd" />
                                                </span></p>
								</div>
								<div class="col">
									<p>计划还款金额 ：
										<fmt:formatNumber type='number' pattern='#,##0.00#' value='${assetSet.assetInitialValue}' />
									</p>
									<p>差异罚息金额 ：
										<fmt:formatNumber type='number' pattern='#,##0.00#' value='${assetSet.penaltyInterestAmount}' />
									</p>
									<p>还款金额 ：
										<span class="amount" data-value="${assetSet.amount}">
											<fmt:formatNumber type='number' pattern='#,##0.00#' value='${assetSet.amount}' />
										</span>
									</p>
									<p>退款金额 ：
										<span class="refund-amount" data-value='${assetSet.refundAmount}'>
											<fmt:formatNumber type='number' pattern='#,##0.00#' value='${assetSet.refundAmount}' />
										</span>
										<sec:authorize ifNotGranted="ROLE_TRUST_OBSERVER">
											<c:if test="${assetSet.clearAssetSet}">
												<button type="button" id="btnRefund" class="btn btn-primary operate">退款</button>
											</c:if>
										</sec:authorize>
									</p>
								</div>
								<div class="col">
									<p>担保状态 ：<fmt:message key="${assetSet.guaranteeStatus.key }"></fmt:message></p>
									<p>逾期状态 ：<span class="color-danger">
                                                    <fmt:message key="${assetSet.overdueStatus.key }"></fmt:message>
                                                </span>
        										<sec:authorize ifNotGranted="ROLE_TRUST_OBSERVER">
        											<c:if test="${assetSet.overdueStatus.ordinal() != 2 }">
        												<a href="#" class="operate" id="preAuditOverdue">修改</a>
        											</c:if>
        										</sec:authorize>
									</p>
									<p>还款状态 ：<fmt:message
												key="${assetSet.paymentStatus.key }"></fmt:message></p>
									<p>备注 ：<span class="comment">${assetSet.comment}</span>
										<sec:authorize ifNotGranted="ROLE_TRUST_OBSERVER">
											<a href="#" class="operate" id="modifyRemark">修改</a>
										</sec:authorize>
									</p>
								</div>
							</div>
						</div>
						<div class="block" style="width: 23%">
							<h5 class="hd">账户信息</h5>
							<div class="bd">
								<div class="col">
									<p>客户姓名 ：${assetSet.contract.customer.name}</p>
									<p>账户开户行 ：${contractAccount.bank}</p>
									<p>开户行所在地 ：${contractAccount.province}&nbsp;${contractAccount.city}</p>
									<p>绑定账号 ：${contractAccount.payAcNo}</p>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="table-layout-detail">
					<div class="block">
						<h5 class="hd">结算单列表</h5>
						<div class="bd">
							<table>
								<thead>
									<tr>
										<th>序号</th>
										<th>结算单号</th>
										<th>计划还款日期</th>
										<th>结算日期</th>
										<th>计划还款金额</th>
										<th>差异罚息</th>
										<th>差异天数</th>
										<th>发生时间</th>
										<th>结算金额</th>
										<th>状态</th>
										<th>备注</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="order" items="${orderList}" varStatus="status">
										<tr>
											<td>${status.index + 1}</td>
											<td><a href="${ctx}/payment-manage/order/${order.id}/detail">${order.orderNo }</a></td>
											<td><fmt:formatDate
												value="${order.assetSet.assetRecycleDate}"
												pattern="yyyy/MM/dd" /></td>
											<td><fmt:formatDate value="${order.dueDate}"
												pattern="yyyy/MM/dd" /></td>
											<td>
												<fmt:formatNumber type='number' pattern='#,##0.00#' value='${order.assetInitialValue }' />
											</td>
											<td>
												<fmt:formatNumber type='number' pattern='#,##0.00#' value='${order.totalRent - order.assetSet.assetInitialValue }' />
											</td>
											<td>${order.numbersOfOverdueDays }</td>
											<td>${order.modifyTime }</td>
											<td>
												<fmt:formatNumber type='number' pattern='#,##0.00#' value='${order.totalRent }' />
											</td>
                                            <c:choose>
                                                <c:when test='${order.executingSettlingStatus.alias == "fail"}'>
                                                    <td><span class="color-danger"><fmt:message key="${order.executingSettlingStatus.key }"></fmt:message></span></td>
                                                </c:when>
                                                <c:otherwise>
        											<td><fmt:message key="${order.executingSettlingStatus.key }"></fmt:message></td>
                                                </c:otherwise>
                                            </c:choose>
											<td>${order.comment }</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>

					<div class="block">
						<h5 class="hd">线上支付记录（近一日）<a href="" class="pull-right more">更多 ></a></h5>
						<div class="bd">
							<table>
								<thead>
									<tr>
										<th>序号</th>
										<th>支付编号</th>
										<th>结算单号</th>
										<th>银行名称</th>
										<th>银行账户号</th>
										<th>代扣金额</th>
										<th>发生时间</th>
										<th>状态</th>
										<th>备注</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="item" items="${transferApplicationList}"
										varStatus="status">
										<tr>
											<td>${status.index + 1}</td>
											<td><a href="${ctx}/payment-manage/payment/${item.id}/detail">${item.transferApplicationNo}</a></td>
											<td><a href="${ctx}/payment-manage/order/${item.order.id}/detail">${item.order.orderNo }</a></td>
											<td>${item.contractAccount.bank}</td>
											<td>${item.contractAccount.payAcNo}</td>
											<td>
												<fmt:formatNumber type='number' pattern='#,##0.00#' value='${item.amount}' />
											</td>
											<td>${item.lastModifiedTime}</td>
                                            <c:choose>
                                                <c:when test='${order.executingDeductStatus.ordinal == 3}'>
                                                    <td><span class="color-danger"><fmt:message key="${order.executingDeductStatus.key }"></fmt:message></span></td>
                                                </c:when>
                                                <c:when test='${order.executingDeductStatus.ordinal == 4}'>
                                                    <td><span class="color-warning"><fmt:message key="${order.executingDeductStatus.key }"></fmt:message></span></td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td><fmt:message key="${item.executingDeductStatus.key }"></fmt:message></td>
                                                </c:otherwise>
                                            </c:choose>
											<td>${item.comment}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>

					<div class="block hide">
						<h5 class="hd">线下支付记录</h5>
						<div class="bd">
							<table>
								<thead>
									<tr>
										<th>序号</th>
										<th>支付单号</th>
										<th>银行名称</th>
										<th>账户姓名</th>
										<th>支付机构流水号</th>
										<th>支付金额</th>
										<th>关联状态</th>
										<th>入账时间</th>
										<th>发生时间</th>
										<th>支付状态</th>
										<th>备注</th>
									</tr>
								</thead>
								<tbody>
										<tr>
											<td colspan="11" class="text-center">功能暂未开放，敬请期待！</td>
											<!-- <td><a href="">abckd1001</a></td>
											<td>测试开户行</td>
											<td>测试</td>
											<td>2016030203221541</td>
											<td>11.00</td>
											<td>已关联</td>
											<td>2016-06-07 08:45:00</td>
											<td>2016-06-07 08:45:00</td>
											<td><span class="color-warning">异常</span></td>
											<td></td> -->
										</tr>
								</tbody>
							</table>
						</div>
					</div>

					<div class="block">
						<h5 class="hd">担保补足单列表</h5>
						<div class="bd">
							<table>
								<thead>
									<tr>
										<th>序号</th>
										<th>担保补足单号</th>
										<th>计划还款日期</th>
										<th>担保截止日</th>
										<th>商户编号</th>
										<th>计划还款金额</th>
										<th>差异天数</th>
										<th>发生时间</th>
										<th>担保金额</th>
										<th>担保状态</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="item" items="${guaranteeOrderList}"
										varStatus="status">
										<tr>
											<td>${status.index + 1}</td>
											<td><a href="${ctx}/guarantee/order/${item.id}/guarantee-detail">${item.orderNo}</a></td>
											<td><fmt:formatDate
													value="${item.assetSet.assetRecycleDate}"
													pattern="yyyy-MM-dd" /></td>
											<td><fmt:formatDate value="${item.dueDate}"
													pattern="yyyy-MM-dd" /></td>
											<td>${item.assetSet.contract.app.appId}</td>
											<td>
												<fmt:formatNumber type='number' pattern='#,##0.00#' value='${item.assetSet.assetInitialValue}' />
											</td>
											<td>${item.numbersOfGuranteeDueDays}</td>
											<td>${item.modifyTime}</td>
											<td>
												<fmt:formatNumber type='number' pattern='#,##0.00#' value='${item.totalRent}' />
											</td>
											<td><fmt:message
													key="${item.assetSet.guaranteeStatus.key}"></fmt:message></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>

					<div class="block">
						<h5 class="hd">担保单清算列表</h5>
						<div class="bd">
							<table>
								<thead>
									<tr>
										<th>序号</th>
										<th>清算单号</th>
										<th>计划还款日期</th>
										<th>清算截止日</th>
										<th>商户编号</th>
										<th>计划还款金额</th>
										<th>差异天数</th>
										<th>差异罚息</th>
										<th>发生时间</th>
										<th>清算金额</th>
										<th>状态</th>
										<th>备注</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="settlementOrder" items="${settlementOrderList}"
									varStatus="status">
									<tr>
										<td>${status.index + 1}</td>
										<td><a href="">${settlementOrder.settleOrderNo}</a></td>
										<td>${settlementOrder.assetSet.assetRecycleDate}</td>
										<td><fmt:formatDate value="${settlementOrder.dueDate}"
												pattern="yyyy/MM/dd" /></td>
										<td>${settlementOrder.assetSet.contract.app.appId}</td>
										<td nowrap>
											<fmt:formatNumber type='number' pattern='#,##0.00#' value='	${settlementOrder.assetSet.assetInitialValue}' />
										</td>
										<td nowrap>${settlementOrder.overdueDays}</td>
										<td nowrap>
											<fmt:formatNumber type='number' pattern='#,##0.00#' value='${settlementOrder.overduePenalty}' />
										</td>
										<td>${settlementOrder.lastModifyTime}</td>
										<td nowrap>
											<fmt:formatNumber type='number' pattern='#,##0.00#' value='${settlementOrder.settlementAmount}' />
										</td>
										<td><fmt:message
												key="${settlementOrder.assetSet.settlementStatus.key}" /></td>
										<td>${settlementOrder.comment}</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
						</div>
					</div>

					<div class="block">
                        <jsp:include page="/WEB-INF/include/system-operate-log.jsp">
                            <jsp:param value="${assetSetUuid}" name="objectUuid"/>
                        </jsp:include>
					</div>
				</div>

			</div>
		</div>
	</div>

	<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>
	
</body>
</html>
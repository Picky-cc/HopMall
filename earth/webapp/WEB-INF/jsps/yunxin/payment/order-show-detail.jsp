<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

<fmt:setBundle basename="ApplicationMessage" />
<fmt:setLocale value="zh-CN" />

<%@ include file="/WEB-INF/include/meta.jsp"%>
<%@ include file="/WEB-INF/include/css-newlayout.jsp"%>
<title>结算单详情 - 五维金融金融管理平台</title>

<script type="template/script" id="modifyPenaltyTmpl">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close close-dialog" aria-label="关闭" data-dismiss="modal"><span aria-hidden="true">×</span></button>
        <h4 class="modal-title" id="dialoglabel">修改差异罚息金额</h4>
      </div>
      <div class="modal-body form-wrapper">
        <form class="form adapt">
            <div class="field-row" style="padding: 0;">
                <label for="" class="field-title">差异天数</label>
                <div class="field-value form-static">
                    {%= obj.numbersOfOverdueDays %}
                </div>
            </div>
            <div class="field-row" style="padding: 0;">
                <label for="" class="field-title">贷款客户姓名</label>
                <div class="field-value form-static">
                    {%= obj.customerName %}
                </div>
            </div>
            <div class="field-row" style="padding: 0;">
                <label for="" class="field-title">计划还款日期</label>
                <div class="field-value form-static">
                    {%= obj.assetRecycleDate %}
                </div>
            </div>
            <div class="field-row" style="padding: 0;">
                <label for="" class="field-title">原差异罚息金额</label>
                <div class="field-value form-static">
                    {%= obj.penalty %} <span class="color-dim">元</span>
                </div>
            </div>
          <div class="field-row">
            <label for="" class="field-title require">修改为</label>
            <div class="field-value small">
                <input name="penalty-amount" class="form-control real-value" value="{%= obj.penalty %}" /> <span class="color-dim">元</span>
            </div>
          </div>
          <div class="field-row">
            <label for="" class="field-title require">备注</label>
            <div class="field-value">
                <textarea name="comment" class="multiline-input form-control real-value" placeHolder="{%= obj.comment %}" cols="30" rows="10">{%= obj.comment %}</textarea>
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
            <input type="hidden" name="overdueStatus" value="${assetSetModel.assetSet.overdueStatus.ordinal}">
            <div class="scroller">
                <div class="position-map">
                    <div class="pull-left">
                        当前位置:
                        <span class="item current">结算单详情</span>
                    </div>
                </div>
                
                <div class="col-layout-detail">
                    <div class="top">
                        <div class="block" style="width: 23%">
                            <h5 class="hd">贷款信息</h5>
                            <div class="bd">
                                <div class="col">
                                    <p>合同编号 ：${ orderViewDetail.order.assetSet.contract.contractNo}</p>
                                    <p>贷款客户编号 ：${ orderViewDetail.customer.source}</p>
                                    <p>发生时间 ：<fmt:formatDate
                            value="${ orderViewDetail.order.modifyTime}"
                            pattern="yyyy-MM-dd HH:mm:ss" /></p>
                                    <p>贷款客户姓名 ：<span class="customer-name">${ orderViewDetail.customer.name}</span></p>
                                </div>
                            </div>
                        </div>
                        <div class="block">
                            <h5 class="hd">结算信息</h5>
                            <div class="bd">
                                <div class="col">
                                    <p>结算单号 ：${ orderViewDetail.order.orderNo }</p>
                                    <p>差异天数 ：<span class="numbers-of-overdue-days">${ orderViewDetail.order.numbersOfOverdueDays}</span></p>
                                    <p>差异罚息金额（元） ：
                                        <span class="penalty-amount" data-value="${orderViewDetail.order.penaltyAmount}">
                                            <fmt:formatNumber type="number" pattern="#0.00" value="${orderViewDetail.order.penaltyAmount}"></fmt:formatNumber>
                                        </span>
                                        <sec:authorize ifNotGranted="ROLE_TRUST_OBSERVER">
                                            <c:if test="${orderViewDetail.order.editable }">
                                                <a href="#" class="operate" id="modifyPenalty">修改</a>
                                            </c:if>
                                        </sec:authorize>
                                    </p>
                                    <p>结算金额（元） ：${ orderViewDetail.order.totalRent}</p>
                                    <p>结算状态 ：<fmt:message key="${ orderViewDetail.order.executingSettlingStatus.key}" /></p>
                                    <p>备注 ：<span class="comment">${orderViewDetail.order.comment }</span></p>
                                </div>
                            </div>
                        </div>
                        <div class="block">
                            <h5 class="hd">还款信息</h5>
                            <div class="bd">
                                <div class="col">
                                    <p>还款编号 ：<a href="${ctx}/assets/${ orderViewDetail.order.assetSet.id }/detail">${ orderViewDetail.order.assetSet.singleLoanContractNo }</a></p>
                                    <p>计划还款金额（元） ：${ orderViewDetail.order.assetInitialValue}</p>
                                    <p>计划还款日期 ：
                                        <span class="asset-recycle-date"><fmt:formatDate value="${ orderViewDetail.order.assetRecycleDate}" pattern="yyyy-MM-dd" /></span>
                                    </p>
                                    <p>还款账号 ：${ orderViewDetail.contractAccount.payAcNo}</p>
                                    <p>绑定号 ：${ orderViewDetail.contractAccount.bindId}</p>
                                </div>
                            </div>
                        </div>
                        <div class="block">
                            <h5 class="hd">支付信息</h5>
                            <div class="bd">
                                <div class="col">
                                    <p>支付机构名称 ：<c:if test="${ orderViewDetail.unionDeduct}">银联</c:if></p>
                                    <p>支付方式 ：<fmt:message key="${ orderViewDetail.paymentWay.key}"></fmt:message></p>
                                    <p>支付单号 ：${ orderViewDetail.paymentNoListString}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="table-layout-detail">
                    <div class="block">
                        <h5 class="hd">线上支付记录</h5>
                        <div class="bd">
                            <table>
                                <thead>
                                    <tr>
                                        <th>序号</th>
                                        <th>支付编号</th>
                                        <th>银行名称</th>
                                        <th>账户姓名</th>
                                        <th>银行账户号</th>
                                        <th>代扣金额</th>
                                        <th>发生时间</th>
                                        <th>状态</th>
                                        <th>备注</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="item" items="${transferApplications}" varStatus="status">
                                        <tr>
                                            <td>${status.index + 1}</td>
                                            <td><a href='${ctx}/payment-manage/payment/${item.id}/detail'>${item.transferApplicationNo}</a></td>
                                            <td>${item.contractAccount.bank}</td>
                                            <td>${item.contractAccount.payerName}</td>
                                            <td>${item.contractAccount.payAcNo}</td>
                                            <td>${item.amount}</td>
                                            <td>${item.lastModifiedTime}</td>
                                            <td><span class="color-warning">${item.executingDeductStatusMsg}</span></td>
                                            <td>${item.comment}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="block">
                        <h5 class="hd">线下支付记录<!-- <a href="" class="pull-right more">关联 ></a> --></h5>
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
                                        <th>状态</th>
                                        <th>备注</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="item" items="${payDetails}" varStatus="status">
                                        <tr>
                                            <td>${status.index + 1}</td>
											<td><a href="${ctx}/offline-payment-manage/payment/${item.offlineBill.id}/detail">${item.offlineBill.offlineBillNo}</a></td>
											<td>${item.offlineBill.bankShowName}</td>
                                            <td>${item.offlineBill.payerAccountName}</td>
                                            <td>${item.offlineBill.serialNo}</td>
                                            <td>${item.journalVoucher.bookingAmount}</td>
                                            <td><fmt:message key="${item.offlineBill.offlineBillConnectionState.key}" />
                                            <td>${item.offlineBill.tradeTime}</td>
                                            <td>${item.journalVoucher.createdDate}</td>
                                            <td><fmt:message key="${item.offlineBill.offlineBillStatus.key}" /></td>
                                            <td>${item.offlineBill.comment}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="block">
                        <jsp:include page="/WEB-INF/include/system-operate-log.jsp">
                            <jsp:param value="${orderViewDetail.order.repaymentBillId}" name="objectUuid"/>
                        </jsp:include>
                    </div>
                </div>

            </div>
        </div>
    </div>
    <%@ include file="/WEB-INF/include/script-newlayout.jsp"%>

</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page language="java" import="java.util.Date" %>

<fmt:formatDate var="currentDay" value="<%=new Date() %>" pattern="yyyy-MM-dd 00:00:00"/>
<div class="tab-content-item repayment" data-dashboard-item="true">
    <div class="list">
        <div class="item">
            <i class="icon icon-tag pull-right"></i>
            <div class="lt">
                <a data-path="${ctx}/remittance/application" data-hash="orderStatus=[1,3,4]&receiveStartDate=${currentDay}">
                    <p><strong data-total="${remittanceData.totalNums}">${remittanceData.totalNums}</strong></p>
                    <div>放款计划</div>
                </a>
            </div>
            <div class="rt">
                <div><a data-path="${ctx}/remittance/application" data-hash="orderStatus=[1]&receiveStartDate=${currentDay}">处理中：${remittanceData.processingRemittanceApplicationNums}</a></div>
                <div><a data-path="${ctx}/remittance/application" data-hash="orderStatus=[4]&receiveStartDate=${currentDay}">异常：${remittanceData.abnormalRemittanceApplicationNums}</a></div>
                <div><a data-path="${ctx}/remittance/application" data-hash="orderStatus=[3]&receiveStartDate=${currentDay}">失败：${remittanceData.failedRemittanceApplicationNums}</a></div>
            </div>
            <i class="more"></i>
        </div>
    </div>
</div>
